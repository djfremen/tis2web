/*     */ package com.eoos.gm.tis2web.sps.client.tool.testdriver.impl.ctrl;
/*     */ 
/*     */ import com.eoos.datatype.gtwo.Pair;
/*     */ import com.eoos.gm.tis2web.sps.client.common.ClientAppContextProvider;
/*     */ import com.eoos.gm.tis2web.sps.client.tool.testdriver.impl.io.IOUtil;
/*     */ import com.eoos.gm.tis2web.sps.client.tool.testdriver.impl.io.LogWriter;
/*     */ import com.eoos.gm.tis2web.sps.common.VIT;
/*     */ import com.eoos.gm.tis2web.sps.common.gtwo.export.datamodel.History;
/*     */ import com.eoos.gm.tis2web.sps.common.gtwo.export.datamodel.Option;
/*     */ import com.eoos.gm.tis2web.sps.common.gtwo.export.datamodel.ProgrammingDataUnit;
/*     */ import com.eoos.gm.tis2web.sps.common.gtwo.implementation.AVUtil;
/*     */ import com.eoos.gm.tis2web.sps.common.gtwo.implementation.CommonAttribute;
/*     */ import com.eoos.gm.tis2web.sps.common.gtwo.implementation.ListValueImpl;
/*     */ import com.eoos.gm.tis2web.sps.service.cai.Attribute;
/*     */ import com.eoos.gm.tis2web.sps.service.cai.AttributeValueMap;
/*     */ import com.eoos.gm.tis2web.sps.service.cai.AttributeValueMapExt;
/*     */ import java.io.File;
/*     */ import java.io.FileWriter;
/*     */ import java.sql.Timestamp;
/*     */ import java.text.SimpleDateFormat;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Locale;
/*     */ import java.util.Vector;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class TestDriverLog
/*     */ {
/*  32 */   private static TestDriverLog instance = null;
/*     */   
/*  34 */   private LogWriter log = null;
/*     */   
/*  36 */   private AttributeValueMapExt data = null;
/*     */   
/*  38 */   private String fileName = null;
/*     */   
/*     */   public static TestDriverLog getInstance() {
/*  41 */     if (instance == null) {
/*  42 */       instance = new TestDriverLog();
/*     */     }
/*  44 */     return instance;
/*     */   }
/*     */   
/*     */   public void writeSummary(Vector<Vector> summary) {
/*  48 */     LogWriter log = null;
/*     */     
/*  50 */     try { File directory = IOUtil.getDirectory();
/*  51 */       File file = new File(directory, "summary.log");
/*  52 */       log = new LogWriter(new FileWriter(file.getAbsolutePath(), false));
/*  53 */       for (int i = 0; i < summary.size(); i++) {
/*  54 */         Vector record = summary.get(i);
/*  55 */         for (int j = 0; j < record.size(); j++) {
/*  56 */           Object entry = record.get(j);
/*  57 */           log.write(entry.toString());
/*  58 */           if (j == 0) {
/*  59 */             log.write(":\t");
/*     */           }
/*     */         } 
/*  62 */         log.newLine();
/*     */       }  }
/*  64 */     catch (Exception x) {  }
/*     */     finally
/*  66 */     { if (log != null) {
/*     */         try {
/*  68 */           log.close();
/*  69 */         } catch (Exception x) {}
/*     */       } }
/*     */   
/*     */   }
/*     */ 
/*     */   
/*     */   public void writeException(String fileName, String exception) throws Exception {
/*  76 */     this.log = new LogWriter(new FileWriter(fileName, true));
/*  77 */     File file = new File(fileName);
/*  78 */     if (file.length() > 0L) {
/*  79 */       this.log.newLine();
/*     */     }
/*  81 */     this.log.writeAttr("Exception", exception);
/*  82 */     this.log.close();
/*     */   }
/*     */   
/*     */   public boolean writeDate() {
/*  86 */     SimpleDateFormat formater = new SimpleDateFormat("HH:mm:ss MM/dd/yy");
/*  87 */     String time = formater.format(new Timestamp(System.currentTimeMillis()));
/*     */     try {
/*  89 */       this.log.writeAttr("Date", time);
/*  90 */       Locale locale = ClientAppContextProvider.getClientAppContext().getLocale();
/*  91 */       this.log.writeAttr("Locale", locale.toString());
/*  92 */     } catch (Exception e) {
/*  93 */       return false;
/*     */     } 
/*  95 */     return true;
/*     */   }
/*     */   
/*     */   public boolean writeInfos(String fileName, VIT vit1) throws Exception {
/*  99 */     boolean res = false;
/* 100 */     this.log = new LogWriter(new FileWriter(fileName, true));
/* 101 */     File file = new File(fileName);
/* 102 */     if (file.length() > 0L) {
/* 103 */       this.log.newLine();
/*     */     }
/* 105 */     if (writeDate()) {
/* 106 */       String val = vit1.getAttrValue("vit_id");
/* 107 */       if (val != null) {
/*     */         try {
/* 109 */           this.log.writeAttr("VIT_ID", val);
/* 110 */           res = true;
/* 111 */         } catch (Exception e) {}
/*     */       }
/*     */     } 
/*     */     
/* 115 */     this.log.close();
/* 116 */     return res;
/*     */   }
/*     */   
/*     */   public void setDataInformation(AttributeValueMapExt data) {
/* 120 */     this.data = data;
/*     */   }
/*     */   
/*     */   public boolean writeAttr(String attrDesc, Attribute attr) {
/* 124 */     boolean res = false;
/*     */     try {
/* 126 */       Object val = AVUtil.accessValue((AttributeValueMap)this.data, attr);
/* 127 */       this.log.writeAttr(attrDesc, (val == null) ? "" : val.toString());
/* 128 */       res = true;
/* 129 */     } catch (Exception e) {
/* 130 */       res = false;
/*     */     } 
/* 132 */     return res;
/*     */   }
/*     */   
/*     */   public boolean writeOptAttrs() {
/* 136 */     boolean res = false;
/*     */     try {
/* 138 */       Iterator<Attribute> it = this.data.getAttributes().iterator();
/* 139 */       while (it.hasNext()) {
/* 140 */         Attribute attribute = it.next();
/* 141 */         if (!(attribute instanceof Option) || (
/* 142 */           (Option)attribute).getID().startsWith("$")) {
/*     */           continue;
/*     */         }
/* 145 */         Option option = (Option)this.data.getValue(attribute);
/* 146 */         String val = option.getDenotation(null);
/* 147 */         this.log.writeOptAttr(((Option)attribute).getDenotation(null), val);
/*     */       } 
/*     */       
/* 150 */       res = true;
/* 151 */     } catch (Exception e) {
/* 152 */       res = false;
/*     */     } 
/* 154 */     return res;
/*     */   }
/*     */   
/*     */   public void writePrePostAttr() {
/* 158 */     if (this.fileName == null) {
/*     */       return;
/*     */     }
/* 161 */     String CONTROLLER_SPECIFIC_INSTRUCTIONS = "Controller Specific Instructions";
/* 162 */     String NOT_AVAILABLE = "NOT_AVAILABLE!";
/* 163 */     String SEMICOLON = "; ";
/*     */     
/*     */     try {
/* 166 */       this.log = new LogWriter(new FileWriter(this.fileName, true));
/* 167 */       ListValueImpl preInstrList = null;
/* 168 */       ListValueImpl postInstrList = null;
/*     */       try {
/* 170 */         preInstrList = (ListValueImpl)AVUtil.accessValue((AttributeValueMap)this.data, CommonAttribute.PRE_PROGRAMMING_INSTRUCTIONS_DATA);
/* 171 */         postInstrList = (ListValueImpl)AVUtil.accessValue((AttributeValueMap)this.data, CommonAttribute.POST_PROGRAMMING_INSTRUCTIONS_DATA);
/* 172 */       } catch (Exception ex) {
/* 173 */         Logger.getLogger(TestDriverLog.class).warn("unable to retrieve pre/postprogramming instructions, ignoring - exception: " + ex, ex);
/*     */       } 
/*     */       
/* 176 */       StringBuffer pre_post_instr = null;
/* 177 */       Iterator preIterator = null;
/* 178 */       if (preInstrList != null) {
/* 179 */         preIterator = preInstrList.getItems().iterator();
/*     */       }
/* 181 */       Iterator postIterator = null;
/* 182 */       if (postInstrList != null) {
/* 183 */         postIterator = postInstrList.getItems().iterator();
/*     */       }
/* 185 */       while (preIterator != null && preIterator.hasNext()) {
/* 186 */         if (pre_post_instr == null)
/* 187 */           pre_post_instr = new StringBuffer(); 
/* 188 */         pre_post_instr.append(preIterator.next());
/* 189 */         pre_post_instr.append("; ");
/*     */       } 
/*     */       
/* 192 */       while (postIterator != null && postIterator.hasNext()) {
/* 193 */         if (pre_post_instr == null)
/* 194 */           pre_post_instr = new StringBuffer(); 
/* 195 */         Object object = postIterator.next();
/* 196 */         pre_post_instr.append(object);
/* 197 */         pre_post_instr.append("; ");
/*     */       } 
/*     */       
/* 200 */       if (pre_post_instr != null) {
/* 201 */         String pre_post_instrString = pre_post_instr.toString().substring(0, pre_post_instr.toString().length() - 2);
/* 202 */         this.log.writeAttr("Controller Specific Instructions", pre_post_instrString);
/*     */       } else {
/* 204 */         this.log.writeAttr("Controller Specific Instructions", "NOT_AVAILABLE!");
/*     */       } 
/*     */       
/* 207 */       this.log.close();
/*     */     }
/* 209 */     catch (Exception e) {
/*     */       try {
/* 211 */         this.log.writeAttr("Controller Specific Instructions", "NOT_AVAILABLE!");
/* 212 */         this.log.close();
/*     */       }
/* 214 */       catch (Exception e1) {
/* 215 */         Logger.getLogger(TestDriverLog.class).warn("unable to  write, ignoring - exception: " + e1, e1);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public boolean writeDescs() {
/* 221 */     boolean res = false;
/*     */     try {
/* 223 */       ListValueImpl lstSW = (ListValueImpl)AVUtil.accessValue((AttributeValueMap)this.data, CommonAttribute.SUMMARY_DIGEST);
/* 224 */       if (lstSW != null) {
/* 225 */         List<History> lst = lstSW.getItems();
/* 226 */         History curSW = lst.get(0);
/* 227 */         History selSW = lst.get(1);
/* 228 */         String desc = selSW.getDescription();
/* 229 */         if (desc == null) {
/* 230 */           desc = "NOT_AVAILABLE!";
/*     */         }
/*     */         try {
/* 233 */           this.log.writeAttr("Description", desc);
/* 234 */         } catch (Exception x) {
/* 235 */           this.log.writeAttr("Description", "NOT_AVAILABLE!");
/*     */         } 
/* 237 */         for (int i = 0; i < selSW.getAttributes().size(); i++) {
/* 238 */           String swDesc = "";
/* 239 */           String oldSWDesc = "";
/* 240 */           String newSWDesc = "";
/*     */           
/*     */           try {
/* 243 */             swDesc = (String)((Pair)selSW.getAttributes().get(i)).getFirst();
/* 244 */           } catch (Exception x) {}
/*     */           
/*     */           try {
/* 247 */             oldSWDesc = (String)((Pair)curSW.getAttributes().get(i)).getSecond();
/* 248 */           } catch (Exception x) {}
/*     */           
/*     */           try {
/* 251 */             newSWDesc = (String)((Pair)selSW.getAttributes().get(i)).getSecond();
/* 252 */           } catch (Exception x) {}
/*     */           
/* 254 */           this.log.writeAttr("SW Description", swDesc + ";" + oldSWDesc + ";" + newSWDesc);
/*     */         } 
/*     */       } 
/* 257 */       res = true;
/* 258 */     } catch (Exception e) {
/* 259 */       res = false;
/*     */     } 
/* 261 */     return res;
/*     */   }
/*     */   
/*     */   public boolean writeReprogInfos(String fileName, List<ProgrammingDataUnit> blobsInfo) throws Exception {
/* 265 */     boolean res = false;
/* 266 */     this.log = new LogWriter(new FileWriter(fileName, true));
/* 267 */     this.fileName = fileName;
/* 268 */     res = writeAttr("VIN", CommonAttribute.VIN);
/* 269 */     res = writeAttr("Salesmake", CommonAttribute.SALESMAKE);
/* 270 */     res = writeAttr("Model Year", CommonAttribute.MODELYEAR);
/* 271 */     res = writeAttr("Model", CommonAttribute.MODEL);
/* 272 */     if (this.data.getValue(CommonAttribute.CONTROLLER_NAME) != null) {
/* 273 */       res = writeAttr("System Type", CommonAttribute.CONTROLLER_NAME);
/*     */     } else {
/* 275 */       res = writeAttr("System Type", CommonAttribute.CONTROLLER);
/*     */     } 
/* 277 */     res = writeOptAttrs();
/* 278 */     res = writeDescs();
/* 279 */     if (blobsInfo != null) {
/* 280 */       for (int i = 0; i < blobsInfo.size(); i++) {
/* 281 */         ProgrammingDataUnit dataUnit = blobsInfo.get(i);
/* 282 */         this.log.writeAttr("Module Info", Integer.toString(i + 1) + "," + dataUnit.getBlobName());
/*     */       } 
/*     */     }
/* 285 */     this.log.close();
/* 286 */     return res;
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\client\tool\testdriver\impl\ctrl\TestDriverLog.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */