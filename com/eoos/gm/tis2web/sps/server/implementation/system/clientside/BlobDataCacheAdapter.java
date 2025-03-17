/*    */ package com.eoos.gm.tis2web.sps.server.implementation.system.clientside;
/*    */ 
/*    */ import com.eoos.cache.Cache;
/*    */ import com.eoos.gm.tis2web.sps.common.gtwo.export.datamodel.ProgrammingDataUnit;
/*    */ import com.eoos.gm.tis2web.sps.service.cai.AttributeValueMap;
/*    */ import com.eoos.io.ObservableInputStream;
/*    */ import com.eoos.util.StringUtilities;
/*    */ import java.security.MessageDigest;
/*    */ import java.security.NoSuchAlgorithmException;
/*    */ import java.util.Arrays;
/*    */ import org.apache.log4j.Logger;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class BlobDataCacheAdapter
/*    */ {
/* 19 */   private static final Logger log = Logger.getLogger(BlobDataCacheAdapter.class);
/*    */ 
/*    */   
/*    */   private Cache cache;
/*    */ 
/*    */   
/*    */   private Retrieval retrieval;
/*    */ 
/*    */ 
/*    */   
/*    */   public BlobDataCacheAdapter(Cache cache, Retrieval retrieval) {
/* 30 */     this.cache = cache;
/* 31 */     this.retrieval = retrieval;
/*    */   }
/*    */ 
/*    */   
/*    */   public Object get(Object _dataUnit, ObservableInputStream.Observer observer, AttributeValueMap avMap) throws Exception {
/* 36 */     Object retValue = null;
/*    */     
/* 38 */     ProgrammingDataUnit dataUnit = (ProgrammingDataUnit)_dataUnit;
/* 39 */     Object identifier = StringUtilities.trimIdentifier(dataUnit.getBlobName() + dataUnit.getBlobID());
/* 40 */     if (dataUnit.getCheckSum() != null && (dataUnit.getCheckSum()).length > 0) {
/*    */       try {
/* 42 */         log.debug("cache lookup for " + String.valueOf(dataUnit));
/* 43 */         retValue = this.cache.lookup(identifier);
/* 44 */         if (retValue != null) {
/* 45 */           log.debug("cache hit - verifying checksum");
/* 46 */           verifyChecksum(dataUnit, retValue);
/*    */         } 
/* 48 */       } catch (Exception e) {
/* 49 */         log.warn("unable to lookup cache for identifier:" + String.valueOf(identifier) + " - exception:" + e, e);
/* 50 */         log.warn("removing corresponding cache entry");
/*    */         try {
/* 52 */           this.cache.remove(identifier);
/* 53 */         } catch (Exception ee) {}
/*    */         
/* 55 */         retValue = null;
/*    */       } 
/*    */     }
/* 58 */     if (retValue == null) {
/* 59 */       if (dataUnit.getCheckSum() != null && (dataUnit.getCheckSum()).length > 0) {
/* 60 */         log.debug("cache miss - read through");
/*    */       }
/* 62 */       retValue = this.retrieval.get(dataUnit, observer, avMap);
/*    */       try {
/* 64 */         if (retValue != null && dataUnit.getCheckSum() != null) {
/* 65 */           this.cache.store(identifier, retValue);
/*    */         }
/* 67 */       } catch (Exception e) {
/* 68 */         log.warn("unable to cache data for identifier:" + String.valueOf(identifier) + " - exception:" + e, e);
/*    */       } 
/*    */     } 
/* 71 */     return retValue;
/*    */   }
/*    */ 
/*    */   
/*    */   private void verifyChecksum(ProgrammingDataUnit dataUnit, Object data) throws NoSuchAlgorithmException, InvalidChecksumException {
/* 76 */     byte[] checksum = dataUnit.getCheckSum();
/* 77 */     if (checksum != null) {
/* 78 */       MessageDigest md5 = MessageDigest.getInstance("MD5");
/* 79 */       byte[] calculatedChecksum = md5.digest((byte[])data);
/* 80 */       if (!Arrays.equals(checksum, calculatedChecksum)) {
/* 81 */         log.error("checksum verification for " + String.valueOf(dataUnit) + " failed *****");
/* 82 */         log.error("....given checksum: " + StringUtilities.bytesToHex(checksum));
/* 83 */         log.error("....calculated checksum: " + StringUtilities.bytesToHex(calculatedChecksum));
/* 84 */         log.error("***** throwing InvalidChecksumException");
/* 85 */         throw new InvalidChecksumException();
/*    */       } 
/* 87 */       log.debug("successfully verified checksum for: " + String.valueOf(dataUnit));
/*    */     } else {
/*    */       
/* 90 */       log.debug("skipped checksum verification (no checksum provided) for:" + String.valueOf(dataUnit));
/*    */     } 
/*    */   }
/*    */   
/*    */   public static interface Retrieval {
/*    */     Object get(Object param1Object, ObservableInputStream.Observer param1Observer, AttributeValueMap param1AttributeValueMap) throws Exception;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\server\implementation\system\clientside\BlobDataCacheAdapter.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */