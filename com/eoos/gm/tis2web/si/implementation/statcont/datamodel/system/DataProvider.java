/*    */ package com.eoos.gm.tis2web.si.implementation.statcont.datamodel.system;
/*    */ 
/*    */ import com.eoos.condition.Condition;
/*    */ import com.eoos.datatype.ExceptionWrapper;
/*    */ import com.eoos.gm.tis2web.frame.export.common.ApplicationContext;
/*    */ import com.eoos.gm.tis2web.frame.export.common.util.Tis2webUtil;
/*    */ import com.eoos.io.StreamUtil;
/*    */ import com.eoos.util.AssertUtil;
/*    */ import java.io.File;
/*    */ import java.io.FileInputStream;
/*    */ import org.apache.log4j.Logger;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class DataProvider
/*    */ {
/* 17 */   private static final Logger log = Logger.getLogger(DataProvider.class);
/*    */   
/* 19 */   private static DataProvider instance = null;
/*    */   
/* 21 */   private File directory = null;
/*    */   
/*    */   private RTCache cache;
/*    */   
/*    */   private DataProvider() throws Exception {
/* 26 */     String _dir = ApplicationContext.getInstance().getProperty("component.si.static.dir");
/* 27 */     this.directory = new File(_dir);
/* 28 */     AssertUtil.ensure(this.directory, new Condition() {
/*    */           public boolean check(Object obj) {
/*    */             try {
/* 31 */               boolean retValue = true;
/* 32 */               File dir = (File)obj;
/* 33 */               retValue = (retValue && dir.exists());
/* 34 */               retValue = (retValue && dir.isDirectory());
/* 35 */               retValue = (retValue && dir.canRead());
/* 36 */               return retValue;
/* 37 */             } catch (Throwable t) {
/* 38 */               return false;
/*    */             } 
/*    */           }
/*    */         });
/*    */     
/* 43 */     log.debug("initialized data provider for SI static content under directory " + this.directory);
/*    */     
/* 45 */     this.cache = new RTCache(Tis2webUtil.createStdCache(), new RTCache.Retrieval()
/*    */         {
/*    */           public byte[] get(String path) throws Exception {
/* 48 */             byte[] retValue = null;
/* 49 */             File file = new File(DataProvider.this.directory, path);
/* 50 */             retValue = StreamUtil.readFully(new FileInputStream(file));
/* 51 */             return retValue;
/*    */           }
/*    */         });
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public static synchronized DataProvider getInstance() {
/* 59 */     if (instance == null) {
/*    */       try {
/* 61 */         instance = new DataProvider();
/* 62 */       } catch (Exception e) {
/* 63 */         throw new ExceptionWrapper(e);
/*    */       } 
/*    */     }
/*    */     
/* 67 */     return instance;
/*    */   }
/*    */   
/*    */   public byte[] getData(String path) throws Exception {
/* 71 */     return this.cache.get(path);
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\si\implementation\statcont\datamodel\system\DataProvider.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */