/*     */ package com.eoos.gm.tis2web.sps.server.implementation.adapter.nao;
/*     */ 
/*     */ import com.eoos.util.StringUtilities;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ 
/*     */ public class SPSPartPROM
/*     */   extends SPSPart
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*  11 */   protected transient List options = new ArrayList();
/*     */   
/*     */   protected int PROMPartNo;
/*     */   
/*     */   protected String BroadcastCode;
/*     */   
/*     */   protected int ECUPartNo;
/*     */   
/*  19 */   protected int ScannerID = -1;
/*     */   
/*     */   protected String comment;
/*     */   
/*     */   protected int CalpakPartNo;
/*     */   
/*     */   protected String CalpakDescription;
/*     */   
/*     */   protected String changeReason;
/*     */   
/*     */   protected String pno;
/*     */   
/*     */   int getECUPartNo() {
/*  32 */     return this.ECUPartNo;
/*     */   }
/*     */   
/*     */   public String getPartNumber() {
/*  36 */     return this.pno;
/*     */   }
/*     */   
/*     */   public String toString() {
/*  40 */     return this.pno + ((this.label == null) ? "" : this.label);
/*     */   }
/*     */   
/*     */   public SPSPartPROM(int id, SPSSchemaAdapterNAO adapter) {
/*  44 */     super(adapter);
/*  45 */     this.id = id;
/*     */   }
/*     */   
/*     */   void setPROMPartNo(int PROMPartNo) {
/*  49 */     this.PROMPartNo = PROMPartNo;
/*     */   }
/*     */   
/*     */   void setBroadcastCode(String BroadcastCode) {
/*  53 */     this.BroadcastCode = BroadcastCode;
/*     */   }
/*     */   
/*     */   void setECUPartNo(int ECUPartNo) {
/*  57 */     this.ECUPartNo = ECUPartNo;
/*     */   }
/*     */   
/*     */   void setScannerID(char modelYearVIN, int ScannerID) {
/*  61 */     if (modelYearVIN < 'F') {
/*  62 */       this.ScannerID = -1;
/*     */     } else {
/*  64 */       this.ScannerID = ScannerID;
/*     */     } 
/*     */   }
/*     */   
/*     */   void setComment(String comment) {
/*  69 */     this.comment = comment;
/*     */   }
/*     */   
/*     */   void addOption(SPSOption option) {
/*  73 */     this.options.add(option);
/*     */   }
/*     */   
/*     */   void setCalpakPartNo(int CalpakPartNo) {
/*  77 */     this.CalpakPartNo = CalpakPartNo;
/*     */   }
/*     */   
/*     */   void setCalpakDescription(String CalpakDescription) {
/*  81 */     this.CalpakDescription = CalpakDescription;
/*     */   }
/*     */   
/*     */   void setBulletinID(String bulletinID) {
/*  85 */     this.changeReason = bulletinID;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  90 */     if (bulletinID.indexOf("S/B") >= 0)
/*     */     {
/*  92 */       this.bulletinID = bulletinID.substring(bulletinID.indexOf("S/B"));
/*     */     }
/*     */   }
/*     */   
/*     */   protected String list(List<SPSOption> options) {
/*  97 */     if (options == null) {
/*  98 */       return "";
/*     */     }
/* 100 */     StringBuffer display = new StringBuffer();
/* 101 */     String lastTypeDescription = "";
/* 102 */     for (int i = 0; i < options.size(); i++) {
/* 103 */       SPSOption option = options.get(i);
/* 104 */       String tdescription = option.getType().getDescription();
/* 105 */       if (tdescription.indexOf("RPO") >= 0) {
/* 106 */         tdescription = StringUtilities.replace(tdescription, "RPO", "").trim();
/*     */       }
/* 108 */       if (lastTypeDescription.equals(tdescription)) {
/*     */ 
/*     */         
/* 111 */         display.append("<tr><td></td><td>" + option.getDescription() + ";" + "</td></tr>");
/*     */       } else {
/* 113 */         lastTypeDescription = tdescription;
/*     */ 
/*     */         
/* 116 */         display.append("<tr><td>" + tdescription + " :" + "</td>" + "<td>" + option.getDescription() + ";" + "</td></tr>");
/*     */       } 
/*     */     } 
/* 119 */     return display.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   void update(SPSLanguage language) {
/* 125 */     this.pno = this.PROMPartNo + ", " + this.ScannerID + ", " + this.BroadcastCode;
/* 126 */     if (this.bulletinID == null || this.bulletinID.length() > 0);
/*     */ 
/*     */     
/* 129 */     this.description = "<table>";
/* 130 */     if (this.changeReason != null)
/*     */     {
/*     */       
/* 133 */       this.description += "<tr> <td>" + this.changeReason + "</td>" + "</tr>";
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 139 */     if (this.ECUPartNo > 0)
/*     */     {
/*     */       
/* 142 */       this.description += "<tr> <td> ECU: </td><td>" + this.ECUPartNo + " " + SPSControllerPROM.getDescription(language, this.ECUPartNo, this.adapter) + "</td>" + "</tr>";
/*     */     }
/* 144 */     this.description += list(this.options);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 150 */     if (this.CalpakPartNo > 0) {
/* 151 */       this.description += "<tr> <td> Calpak: </td><td>" + this.CalpakPartNo + " " + this.CalpakDescription + "</td>" + "</tr>";
/*     */     }
/* 153 */     this.description += "</table>";
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\server\implementation\adapter\nao\SPSPartPROM.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */