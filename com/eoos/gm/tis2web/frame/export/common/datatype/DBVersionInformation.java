/*    */ package com.eoos.gm.tis2web.frame.export.common.datatype;
/*    */ 
/*    */ import java.util.Date;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class DBVersionInformation
/*    */ {
/*    */   public static final String VERSION = "db.version";
/*    */   public static final String DESCRIPTION = "db.description";
/*    */   protected String creator;
/*    */   protected String releaseID;
/*    */   protected Date releaseDate;
/*    */   protected String releaseDescription;
/*    */   protected String releaseVersion;
/*    */   
/*    */   public DBVersionInformation(String releaseID, Date releaseDate, String releaseDescription, String releaseVersion) {
/* 21 */     this.releaseID = releaseID;
/* 22 */     this.releaseDate = releaseDate;
/* 23 */     this.releaseDescription = releaseDescription;
/* 24 */     this.releaseVersion = releaseVersion;
/*    */   }
/*    */   
/*    */   public DBVersionInformation(String creator, String releaseID, Date releaseDate, String releaseDescription, String releaseVersion) {
/* 28 */     this.creator = creator;
/* 29 */     this.releaseID = releaseID;
/* 30 */     this.releaseDate = releaseDate;
/* 31 */     this.releaseDescription = releaseDescription;
/* 32 */     this.releaseVersion = releaseVersion;
/*    */   }
/*    */   
/*    */   public String getReleaseDescription() {
/* 36 */     return this.releaseDescription;
/*    */   }
/*    */   
/*    */   public String getReleaseVersion() {
/* 40 */     return this.releaseVersion;
/*    */   }
/*    */   
/*    */   public String getCreator() {
/* 44 */     return this.creator;
/*    */   }
/*    */   
/*    */   public Date getReleaseDate() {
/* 48 */     return this.releaseDate;
/*    */   }
/*    */   
/*    */   public String getReleaseID() {
/* 52 */     return this.releaseID;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\frame\export\common\datatype\DBVersionInformation.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */