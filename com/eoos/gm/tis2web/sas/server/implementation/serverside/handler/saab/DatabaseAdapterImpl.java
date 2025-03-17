/*     */ package com.eoos.gm.tis2web.sas.server.implementation.serverside.handler.saab;
/*     */ 
/*     */ import com.eoos.gm.tis2web.frame.export.common.util.DatabaseLink;
/*     */ import com.eoos.gm.tis2web.frame.export.common.util.IDatabaseLink;
/*     */ import com.eoos.gm.tis2web.sas.common.model.HardwareKey;
/*     */ import com.eoos.gm.tis2web.sas.common.model.VIN;
/*     */ import com.eoos.gm.tis2web.sas.common.model.reqres.SSAResponse;
/*     */ import com.eoos.gm.tis2web.sas.common.model.reqres.impl.SecurityCodesImpl;
/*     */ import java.sql.Connection;
/*     */ import java.sql.PreparedStatement;
/*     */ import java.sql.ResultSet;
/*     */ import java.util.Iterator;
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class DatabaseAdapterImpl
/*     */   implements DatabaseAdapter
/*     */ {
/*  23 */   private static final Logger log = Logger.getLogger(DatabaseAdapterImpl.class);
/*     */   
/*     */   private static final String STMT_GETVERSION = "SELECT Version from SecVersion";
/*     */   
/*     */   private static final String STMT_GETFREESHOT = "SELECT FreeShots from SecVersion";
/*     */   
/*     */   private static final String STMT_GETGROUPID = "SELECT GroupID FROM HWKGroups WHERE Ident=?";
/*     */   
/*     */   private static final String STMT_GETSECCODES = "SELECT SecCode_Immo, SecCode_Info FROM TsecCode WHERE CarLine=? and ModelYear=? and Plant=? and Chassis=? and GroupID=?";
/*     */   
/*  33 */   private IDatabaseLink dbLink = null;
/*     */   
/*     */   public DatabaseAdapterImpl(IDatabaseLink dbLink) {
/*  36 */     this.dbLink = dbLink;
/*     */   }
/*     */   
/*     */   private String trim(String string) {
/*  40 */     return (string == null) ? null : string.trim();
/*     */   }
/*     */   
/*     */   public Integer getVersion() throws DatabaseAdapter.Exception {
/*  44 */     Integer retValue = null;
/*  45 */     Connection connection = null;
/*     */     try {
/*  47 */       connection = this.dbLink.requestConnection();
/*  48 */       PreparedStatement stmt = connection.prepareStatement("SELECT Version from SecVersion");
/*  49 */       ResultSet rs = stmt.executeQuery();
/*  50 */       if (rs.next()) {
/*  51 */         String hex = trim(rs.getString("Version"));
/*  52 */         retValue = Integer.valueOf(Integer.parseInt(hex, 16));
/*     */       } 
/*  54 */       return retValue;
/*  55 */     } catch (Exception e) {
/*  56 */       throw new DatabaseAdapter.Exception(e);
/*     */     } finally {
/*     */       
/*  59 */       if (connection != null) {
/*  60 */         this.dbLink.releaseConnection(connection);
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   public Integer getFreeShot() throws DatabaseAdapter.Exception {
/*  66 */     Integer retValue = null;
/*  67 */     Connection connection = null;
/*     */     try {
/*  69 */       connection = this.dbLink.requestConnection();
/*  70 */       PreparedStatement stmt = connection.prepareStatement("SELECT FreeShots from SecVersion");
/*  71 */       ResultSet rs = stmt.executeQuery();
/*  72 */       if (rs.next()) {
/*  73 */         short s = rs.getShort("FreeShots");
/*  74 */         int freeshot = 0;
/*  75 */         for (int i = 0; i < s; i++) {
/*  76 */           int mask = 1 << i;
/*  77 */           freeshot |= mask;
/*     */         } 
/*     */         
/*  80 */         retValue = Integer.valueOf(freeshot);
/*     */       } 
/*  82 */       return retValue;
/*  83 */     } catch (Exception e) {
/*  84 */       throw new DatabaseAdapter.Exception(e);
/*     */     } finally {
/*  86 */       if (connection != null) {
/*  87 */         this.dbLink.releaseConnection(connection);
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   public List getSecurityCodes(List vins, HardwareKey hardwareKey) throws DatabaseAdapter.Exception {
/*  93 */     List<SecurityCodesImpl> retValue = new LinkedList();
/*  94 */     Connection connection = null;
/*     */     try {
/*  96 */       connection = this.dbLink.requestConnection();
/*     */ 
/*     */       
/*  99 */       Integer groupID = null;
/* 100 */       if (hardwareKey != null) {
/* 101 */         PreparedStatement preparedStatement = connection.prepareStatement("SELECT GroupID FROM HWKGroups WHERE Ident=?");
/* 102 */         preparedStatement.setString(1, hardwareKey.getEncoded());
/*     */         
/* 104 */         ResultSet rs = preparedStatement.executeQuery();
/* 105 */         if (rs.next()) {
/* 106 */           groupID = Integer.valueOf(rs.getInt("GroupID"));
/*     */         }
/*     */       } 
/*     */ 
/*     */       
/* 111 */       log.debug("preparing statement");
/* 112 */       PreparedStatement stmt = connection.prepareStatement("SELECT SecCode_Immo, SecCode_Info FROM TsecCode WHERE CarLine=? and ModelYear=? and Plant=? and Chassis=? and GroupID=?");
/*     */       
/* 114 */       for (Iterator<VIN> iter = vins.iterator(); iter.hasNext(); ) {
/* 115 */         VIN vin = iter.next();
/* 116 */         log.debug("retrieving security codes for vin: " + String.valueOf(vin));
/* 117 */         if (vin != null) {
/* 118 */           log.debug("searching entry for carline:" + vin.getCarlinePart() + ", modelyear:" + vin.getModelYearPart() + ", plant:" + vin.getPlantPart() + ", chassis:" + vin.getChassisPart());
/* 119 */           stmt.setString(1, vin.getCarlinePart());
/* 120 */           stmt.setString(2, vin.getModelYearPart());
/* 121 */           stmt.setString(3, vin.getPlantPart());
/* 122 */           stmt.setString(4, vin.getChassisPart());
/* 123 */           stmt.setInt(5, (groupID != null) ? groupID.intValue() : 0);
/*     */           
/* 125 */           ResultSet rs = stmt.executeQuery();
/* 126 */           if (rs.next()) {
/* 127 */             String secCodeImmo = rs.getString("SecCode_Immo");
/* 128 */             String secCodeInfo = rs.getString("SecCode_Info");
/* 129 */             log.debug("...entry found - adding secCodeImmo: " + secCodeImmo + ", secCodeInfo:" + secCodeInfo);
/* 130 */             retValue.add(new SecurityCodesImpl(secCodeImmo, secCodeInfo)); continue;
/*     */           } 
/* 132 */           log.debug("...no entry found for vin " + String.valueOf(vin) + ", adding NULL_CODES");
/* 133 */           retValue.add(SSAResponse.SecurityCodes.NULL_CODES);
/*     */           continue;
/*     */         } 
/* 136 */         log.debug("...vin is null, adding NULL_CODES");
/* 137 */         retValue.add(SSAResponse.SecurityCodes.NULL_CODES);
/*     */       } 
/*     */ 
/*     */       
/* 141 */       return retValue;
/* 142 */     } catch (Exception e) {
/* 143 */       throw new DatabaseAdapter.Exception(e);
/*     */     } finally {
/* 145 */       if (connection != null) {
/* 146 */         this.dbLink.releaseConnection(connection);
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   public static void main(String[] args) throws DatabaseAdapter.Exception {
/* 152 */     String driver = "transbase.jdbc.Driver";
/* 153 */     String url = "jdbc:transbase://alex3:3025/ssaiodb_050330";
/* 154 */     String usr = "tbadmin";
/* 155 */     String pwd = "";
/*     */     
/* 157 */     DatabaseLink databaseLink = new DatabaseLink(driver, url, usr, pwd);
/* 158 */     DatabaseAdapter adapter = new DatabaseAdapterImpl((IDatabaseLink)databaseLink);
/*     */     
/* 160 */     System.out.println(adapter.getVersion());
/* 161 */     System.out.println(Integer.toHexString(adapter.getFreeShot().intValue()));
/*     */     
/* 163 */     VIN vin1 = VIN.getInstance("xxxfxxxxx41007000");
/* 164 */     VIN vin2 = VIN.getInstance("w0l0tgf35y1234567");
/* 165 */     VIN vin3 = VIN.getInstance("xxxfxxxxx31007100");
/* 166 */     List<VIN> vins = new LinkedList();
/* 167 */     vins.add(vin1);
/* 168 */     vins.add(vin2);
/* 169 */     vins.add(vin3);
/* 170 */     for (Iterator<String[]> iter = adapter.getSecurityCodes(vins, null).iterator(); iter.hasNext(); ) {
/* 171 */       String[] secCodes = iter.next();
/* 172 */       System.out.println(secCodes[0]);
/* 173 */       System.out.println(secCodes[1]);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sas\server\implementation\serverside\handler\saab\DatabaseAdapterImpl.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */