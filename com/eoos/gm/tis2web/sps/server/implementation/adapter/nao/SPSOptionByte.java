/*     */ package com.eoos.gm.tis2web.sps.server.implementation.adapter.nao;
/*     */ 
/*     */ import com.eoos.datatype.gtwo.Pair;
/*     */ import com.eoos.datatype.gtwo.PairImpl;
/*     */ import com.eoos.gm.tis2web.frame.export.common.util.IDatabaseLink;
/*     */ import com.eoos.gm.tis2web.sps.common.gtwo.export.OptionByte;
/*     */ import com.eoos.gm.tis2web.sps.common.gtwo.export.SPSException;
/*     */ import com.eoos.gm.tis2web.sps.common.gtwo.implementation.CommonException;
/*     */ import com.eoos.gm.tis2web.sps.server.implementation.adapter.common.DBMS;
/*     */ import java.io.Serializable;
/*     */ import java.sql.Connection;
/*     */ import java.sql.ResultSet;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SPSOptionByte
/*     */   implements OptionByte, Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   protected static final int BYTE_DATA = -1;
/*     */   protected static final int DEFINED_BITS = 0;
/*     */   protected static final int TIRE_SIZE = 1;
/*  27 */   protected static final int[] mask = new int[] { 1, 2, 4, 8, 16, 32, 64, 128 };
/*     */   
/*     */   protected static final String EMISSION_OPTION = "#3";
/*     */   
/*     */   protected static final String BCM_OPTION = "#16";
/*     */   
/*  33 */   protected static Logger log = Logger.getLogger(SPSOptionByte.class);
/*     */   
/*  35 */   protected int order = -1;
/*     */   
/*     */   protected int Device_ID;
/*     */   
/*     */   protected int Block_No;
/*     */   
/*     */   protected int Byte_Offset;
/*     */   
/*     */   protected int Dependency_ID;
/*     */   
/*     */   protected int Data;
/*     */   
/*     */   protected transient int Defined_Bits;
/*     */   
/*     */   protected transient int Descrpt_ID;
/*     */   
/*     */   protected int Label_ID;
/*     */   
/*     */   protected int Default_Bits_Index;
/*     */   
/*     */   protected transient String Label;
/*     */   
/*     */   protected transient String Description;
/*     */   
/*     */   public boolean isByteData() {
/*  60 */     return (this.Label_ID == -1);
/*     */   }
/*     */   
/*     */   public boolean isBitData() {
/*  64 */     return (this.Label_ID == 0);
/*     */   }
/*     */   
/*     */   public boolean isTireSize() {
/*  68 */     return (this.Label_ID > 0);
/*     */   }
/*     */   
/*     */   public int getOrder() {
/*  72 */     return this.order;
/*     */   }
/*     */   
/*     */   public int getDeviceID() {
/*  76 */     return this.Device_ID;
/*     */   }
/*     */   
/*     */   public int getBlockNum() {
/*  80 */     return this.Block_No;
/*     */   }
/*     */   
/*     */   public int getByteNum() {
/*  84 */     return this.Byte_Offset;
/*     */   }
/*     */   
/*     */   public int getDependencyID() {
/*  88 */     return this.Dependency_ID;
/*     */   }
/*     */   
/*     */   public int getData() {
/*  92 */     return this.Data;
/*     */   }
/*     */   
/*     */   public int getType() {
/*  96 */     return 1;
/*     */   }
/*     */   
/*     */   public String toString() {
/* 100 */     return this.Block_No + "/" + this.Byte_Offset + "=" + this.Data + "(" + (isByteData() ? "byte" : "") + (isBitData() ? "bit" : "") + (isTireSize() ? "tire" : "") + ")";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static List loadOptionBytes(SPSSaturnData saturn, SPSSchemaAdapterNAO adapter) throws Exception {
/* 107 */     IDatabaseLink dblink = adapter.getDatabaseLink();
/* 108 */     saturn.getVehicle().getVIT1();
/* 109 */     List optionBytes = saturn.getVehicle().getOptionBytes(saturn.getDevice());
/* 110 */     if (optionBytes == null && saturn.getMode() == 0) {
/* 111 */       return null;
/*     */     }
/* 113 */     SPSVIN vin = (SPSVIN)saturn.getVehicle().getVIN();
/* 114 */     Connection conn = null;
/* 115 */     DBMS.PreparedStatement stmt = null;
/* 116 */     ResultSet rs = null;
/*     */     try {
/* 118 */       conn = dblink.requestConnection();
/* 119 */       String sql = DBMS.getSQL(dblink, "SELECT a.Block_No, a.Byte_Offset, a.Defined_Bits, a.Dependency_ID, a.Descrpt_ID, a.Label_ID, a.Default_Bits_Index, b.Label, c.Description FROM OptionByte a, RPO_Code_Labels b, OptionByte_Descrpt c WHERE a.Model_Year = ? AND a.Make = ? AND a.Line = ? AND (a.Series = '~' OR a.Series = ?)  AND (a.Engine = '~' OR a.Engine = ?)  AND (a.Body = '~' OR a.Body = ?)  AND (a.Device_ID = ? ) AND (a.Label_ID = b.RPO_Label_Id) AND (a.Descrpt_ID = c.Descrpt_ID) AND b.Language_Code = ? AND (c.Language_Code = b.Language_Code) ORDER BY a.Label_ID");
/* 120 */       stmt = DBMS.prepareSQLStatement(conn, sql);
/* 121 */       stmt.setString(1, DBMS.toString(vin.getModelYear()));
/* 122 */       stmt.setString(2, DBMS.toString(vin.getMake()));
/* 123 */       stmt.setString(3, DBMS.toString(vin.getLine()));
/* 124 */       stmt.setString(4, DBMS.toString(vin.getSeries()));
/* 125 */       stmt.setString(5, DBMS.toString(vin.getEngine()));
/* 126 */       stmt.setString(6, DBMS.toString(vin.getPosition4or6()));
/* 127 */       stmt.setInt(7, saturn.getDevice());
/* 128 */       stmt.setString(8, saturn.getLanguage().getID());
/* 129 */       int order = 0;
/* 130 */       rs = stmt.executeQuery();
/* 131 */       while (rs.next()) {
/* 132 */         SPSOptionByte descriptor = new SPSOptionByte();
/* 133 */         descriptor.order = order++;
/* 134 */         descriptor.Device_ID = saturn.getDevice();
/* 135 */         descriptor.Block_No = rs.getInt(1);
/* 136 */         descriptor.Byte_Offset = rs.getInt(2);
/* 137 */         descriptor.Defined_Bits = rs.getInt(3);
/* 138 */         descriptor.Dependency_ID = rs.getInt(4);
/* 139 */         descriptor.Descrpt_ID = rs.getInt(5);
/* 140 */         descriptor.Label_ID = rs.getInt(6);
/* 141 */         descriptor.Default_Bits_Index = rs.getInt(7);
/* 142 */         String label = rs.getString(8);
/* 143 */         if (!rs.wasNull()) {
/* 144 */           descriptor.Label = label.trim();
/*     */         }
/* 146 */         String description = rs.getString(9);
/* 147 */         if (!rs.wasNull()) {
/* 148 */           descriptor.Description = description.trim();
/*     */         }
/* 150 */         if (descriptor.Label_ID == -1) {
/* 151 */           log.debug("byte data");
/* 152 */           descriptor.Data = (byte)descriptor.Defined_Bits;
/* 153 */           saturn.getByteData().add(descriptor);
/* 154 */         } else if (descriptor.Label_ID == 0) {
/* 155 */           log.debug("bit data");
/* 156 */           saturn.getDefinedBits().add(descriptor);
/*     */         } else {
/* 158 */           log.debug("tire size");
/* 159 */           descriptor.Data = (byte)descriptor.Defined_Bits;
/* 160 */           saturn.getTireSize().add(descriptor);
/*     */         } 
/* 162 */         log.debug(order + ": device=" + descriptor.Device_ID + " block=" + descriptor.Block_No + " offset=" + descriptor.Byte_Offset);
/*     */       } 
/* 164 */       if (rs != null) {
/* 165 */         rs.close();
/* 166 */         rs = null;
/*     */       } 
/* 168 */       if (stmt != null) {
/* 169 */         stmt.close();
/* 170 */         stmt = null;
/*     */       } 
/* 172 */       List<SPSSpecialOption> options = new ArrayList();
/* 173 */       if (saturn.getTireSize().size() > 0) {
/* 174 */         for (int i = 0; i < saturn.getTireSize().size(); i++) {
/* 175 */           SPSOptionByte descriptor = saturn.getTireSize().get(i);
/* 176 */           SPSSpecialOption group = SPSSpecialOption.getSpecialOptionGroup(saturn.getLanguage(), descriptor.Label_ID, descriptor.Label, adapter);
/* 177 */           SPSSpecialOption option = new SPSSpecialOption(group, descriptor.Descrpt_ID, descriptor.Description);
/* 178 */           option.setOptionByte(descriptor);
/* 179 */           checkOptionByte(optionBytes, option);
/*     */ 
/*     */           
/* 182 */           options.add(option);
/*     */         } 
/*     */       }
/* 185 */       if (saturn.getDefinedBits().size() > 0) {
/* 186 */         for (int i = 0; i < saturn.getDefinedBits().size(); i++) {
/* 187 */           SPSOptionByte descriptor = saturn.getDefinedBits().get(i);
/* 188 */           List<Pair> groups = loadOptionBits(conn, saturn.getLanguage(), vin, saturn.getDevice(), descriptor, adapter);
/* 189 */           for (int j = 0; j < groups.size(); j++) {
/* 190 */             Pair pair = groups.get(j);
/* 191 */             SPSSpecialOption group = SPSSpecialOption.getSpecialOptionGroup(descriptor.Defined_Bits + ":" + i + ":" + j, (String)pair.getSecond());
/* 192 */             SPSSpecialOption yes = SPSSpecialOption.getSpecialOption(group, saturn.getLanguage(), "yes");
/* 193 */             yes.setOptionByte(descriptor, ((Integer)pair.getFirst()).byteValue());
/* 194 */             options.add(yes);
/* 195 */             SPSSpecialOption no = SPSSpecialOption.getSpecialOption(group, saturn.getLanguage(), "no");
/* 196 */             no.setOptionByte(descriptor, ((Integer)pair.getFirst()).byteValue());
/* 197 */             options.add(no);
/* 198 */             if (checkOptionBit(optionBytes, yes));
/*     */           } 
/*     */         } 
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 208 */       return (options.size() > 0) ? options : null;
/* 209 */     } catch (Exception e) {
/* 210 */       throw e;
/*     */     } finally {
/*     */       try {
/* 213 */         if (rs != null) {
/* 214 */           rs.close();
/*     */         }
/* 216 */         if (stmt != null) {
/* 217 */           stmt.close();
/*     */         }
/* 219 */         if (conn != null) {
/* 220 */           dblink.releaseConnection(conn);
/*     */         }
/* 222 */       } catch (Exception x) {}
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected static boolean checkOptionByte(List<OptionByte> optionBytes, SPSSpecialOption option) {
/* 228 */     if (optionBytes == null) {
/* 229 */       return false;
/*     */     }
/* 231 */     SPSOptionByte descriptor = option.getOptionByte();
/* 232 */     for (int i = 0; i < optionBytes.size(); i++) {
/* 233 */       OptionByte vit1 = optionBytes.get(i);
/* 234 */       if (vit1.getDeviceID() == descriptor.getDeviceID() && vit1.getBlockNum() == descriptor.getBlockNum() && vit1.getByteNum() == descriptor.getByteNum() && 
/* 235 */         vit1.getData() == descriptor.getData()) {
/* 236 */         return true;
/*     */       }
/*     */     } 
/*     */     
/* 240 */     return false;
/*     */   }
/*     */   
/*     */   protected static boolean checkOptionBit(List<OptionByte> optionBytes, SPSSpecialOption option) {
/* 244 */     if (optionBytes == null) {
/* 245 */       return false;
/*     */     }
/* 247 */     SPSOptionByte descriptor = option.getOptionByte();
/* 248 */     for (int i = 0; i < optionBytes.size(); i++) {
/* 249 */       OptionByte vit1 = optionBytes.get(i);
/* 250 */       if (vit1.getDeviceID() == descriptor.getDeviceID() && vit1.getBlockNum() == descriptor.getBlockNum() && vit1.getByteNum() == descriptor.getByteNum() && (
/* 251 */         mask[option.getBit()] & vit1.getData()) > 0) {
/* 252 */         return true;
/*     */       }
/*     */     } 
/*     */     
/* 256 */     return false;
/*     */   }
/*     */   
/*     */   protected static List determineBitPositions(int Defined_Bits) {
/* 260 */     List<Integer> positions = new ArrayList();
/* 261 */     int value = 1;
/* 262 */     for (int pos = 0; pos < 8; pos++) {
/* 263 */       if ((value & Defined_Bits) > 0) {
/* 264 */         positions.add(Integer.valueOf(pos));
/*     */       }
/* 266 */       value *= 2;
/*     */     } 
/* 268 */     return positions;
/*     */   }
/*     */   
/*     */   protected static List loadOptionBits(Connection conn, SPSLanguage language, SPSVIN vin, int device, SPSOptionByte descriptor, SPSSchemaAdapterNAO adapter) throws Exception {
/* 272 */     IDatabaseLink dblink = adapter.getDatabaseLink();
/* 273 */     List<Integer> positions = determineBitPositions(descriptor.Defined_Bits);
/* 274 */     List<PairImpl> options = new ArrayList();
/* 275 */     DBMS.PreparedStatement stmt = null;
/* 276 */     ResultSet rs = null;
/*     */     try {
/* 278 */       String sql = DBMS.getSQL(dblink, "SELECT b.Description FROM OptionByte_Bit_Descrpt a, OptionByte_Descrpt b WHERE a.Model_Year = ? AND a.Make = ? AND a.Line = ? AND (a.Series = '~' OR a.Series = ?) AND (a.Engine = '~' OR a.Engine = ?) AND (a.Body = '~' OR a.Body = ?) AND (a.Device_ID = -1 OR a.Device_ID = ?) AND (a.Block_No = -1 OR a.Block_No = ?) AND (a.Byte_Offset = -1 OR a.Byte_Offset = ?) AND a.Bit_Offset = ? AND ( a.Bit_Descrpt_ID = b.Descrpt_ID ) AND b.Language_Code = ?");
/* 279 */       stmt = DBMS.prepareSQLStatement(conn, sql);
/* 280 */       for (int i = 0; i < positions.size(); i++) {
/* 281 */         int bit = ((Integer)positions.get(i)).intValue();
/* 282 */         stmt.setString(1, DBMS.toString(vin.getModelYear()));
/* 283 */         stmt.setString(2, DBMS.toString(vin.getMake()));
/* 284 */         stmt.setString(3, DBMS.toString(vin.getLine()));
/* 285 */         stmt.setString(4, DBMS.toString(vin.getSeries()));
/* 286 */         stmt.setString(5, DBMS.toString(vin.getEngine()));
/* 287 */         stmt.setString(6, DBMS.toString(vin.getPosition4or6()));
/* 288 */         stmt.setInt(7, device);
/* 289 */         stmt.setInt(8, descriptor.Block_No);
/* 290 */         stmt.setInt(9, descriptor.Byte_Offset);
/* 291 */         stmt.setInt(10, bit);
/* 292 */         stmt.setString(11, language.getID());
/* 293 */         rs = stmt.executeQuery();
/* 294 */         while (rs.next()) {
/* 295 */           String description = rs.getString(1);
/* 296 */           if (!rs.wasNull()) {
/* 297 */             options.add(new PairImpl(Integer.valueOf(bit), description.trim()));
/*     */           }
/*     */         } 
/*     */       } 
/* 301 */     } catch (Exception e) {
/* 302 */       throw e;
/*     */     } finally {
/*     */       try {
/* 305 */         if (rs != null) {
/* 306 */           rs.close();
/*     */         }
/* 308 */         if (stmt != null) {
/* 309 */           stmt.close();
/*     */         }
/* 311 */       } catch (Exception x) {}
/*     */     } 
/*     */     
/* 314 */     return options;
/*     */   }
/*     */   
/*     */   protected static Integer queryDefaultBit(int defaultBitsIndex, String rpoCode, SPSSchemaAdapterNAO adapter) throws Exception {
/* 318 */     IDatabaseLink dblink = adapter.getDatabaseLink();
/* 319 */     Connection conn = null;
/* 320 */     DBMS.PreparedStatement stmt = null;
/* 321 */     ResultSet rs = null;
/*     */     try {
/* 323 */       conn = dblink.requestConnection();
/* 324 */       String sql = DBMS.getSQL(dblink, "SELECT Default_Bits FROM Default_Bits WHERE Default_Bits_Index=? AND (RPO_Code=? OR RPO_Code='~')");
/* 325 */       stmt = DBMS.prepareSQLStatement(conn, sql);
/* 326 */       stmt.setInt(1, defaultBitsIndex);
/* 327 */       stmt.setString(2, rpoCode);
/* 328 */       rs = stmt.executeQuery();
/* 329 */       if (rs.next()) {
/* 330 */         return Integer.valueOf(rs.getInt(1));
/*     */       }
/* 332 */     } catch (Exception e) {
/* 333 */       throw e;
/*     */     } finally {
/*     */       try {
/* 336 */         if (rs != null) {
/* 337 */           rs.close();
/*     */         }
/* 339 */         if (stmt != null) {
/* 340 */           stmt.close();
/*     */         }
/* 342 */         if (conn != null) {
/* 343 */           dblink.releaseConnection(conn);
/*     */         }
/* 345 */       } catch (Exception x) {}
/*     */     } 
/*     */     
/* 348 */     return null;
/*     */   }
/*     */   
/*     */   static List constructOptionBytes(List options, List tireSize, List definedBits, List<SPSOptionByte> byteData, SPSSchemaAdapterNAO adapter) throws Exception {
/* 352 */     List<SPSOptionByte> optionBytes = new ArrayList();
/* 353 */     if (tireSize != null && tireSize.size() > 0) {
/* 354 */       handleTireSize(optionBytes, options, adapter);
/*     */     }
/* 356 */     if (definedBits != null && definedBits.size() > 0) {
/* 357 */       String emissionRPO = checkEmissionRPO(options);
/* 358 */       String bcmRPO = checkBCMRPO(options);
/* 359 */       handleDefinedBits(optionBytes, options, definedBits, emissionRPO, bcmRPO, adapter);
/*     */     } 
/* 361 */     if (byteData != null && byteData.size() > 0) {
/* 362 */       for (int i = 0; i < byteData.size(); i++) {
/* 363 */         SPSOptionByte descriptor = byteData.get(i);
/* 364 */         handleOptionByteDependency(optionBytes, descriptor.getDependencyID(), adapter);
/* 365 */         optionBytes.add(descriptor);
/*     */       } 
/*     */     }
/* 368 */     return (optionBytes.size() > 0) ? optionBytes : null;
/*     */   }
/*     */   
/*     */   protected static void handleOptionByteDependency(List<SPSOptionByte> optionBytes, int dependencyID, SPSSchemaAdapterNAO adapter) throws Exception {
/* 372 */     IDatabaseLink dblink = adapter.getDatabaseLink();
/* 373 */     if (dependencyID <= 0) {
/*     */       return;
/*     */     }
/* 376 */     Connection conn = null;
/* 377 */     DBMS.PreparedStatement stmt = null;
/* 378 */     ResultSet rs = null;
/*     */     try {
/* 380 */       conn = dblink.requestConnection();
/* 381 */       String sql = DBMS.getSQL(dblink, "SELECT Device_ID, Block_No, Byte_Offset, Byte_Data FROM OptionByte_Dependency WHERE Dependency_ID = ?");
/* 382 */       stmt = DBMS.prepareSQLStatement(conn, sql);
/* 383 */       stmt.setInt(1, dependencyID);
/* 384 */       rs = stmt.executeQuery();
/* 385 */       if (rs.next()) {
/* 386 */         SPSOptionByte descriptor = new SPSOptionByte();
/* 387 */         descriptor.Device_ID = rs.getInt(1);
/* 388 */         descriptor.Block_No = rs.getInt(2);
/* 389 */         descriptor.Byte_Offset = rs.getInt(3);
/* 390 */         descriptor.Data = rs.getByte(4);
/* 391 */         for (int i = 0; i < optionBytes.size(); i++) {
/* 392 */           SPSOptionByte other = optionBytes.get(i);
/* 393 */           if (other.getDeviceID() == descriptor.getDeviceID() && other.getBlockNum() == descriptor.getBlockNum() && other.getByteNum() == descriptor.getByteNum()) {
/* 394 */             throw new SPSException(CommonException.ServerSideFailure);
/*     */           }
/*     */         } 
/*     */         
/* 398 */         optionBytes.add(descriptor);
/*     */       } 
/* 400 */     } catch (Exception e) {
/* 401 */       throw e;
/*     */     } finally {
/*     */       try {
/* 404 */         if (rs != null) {
/* 405 */           rs.close();
/*     */         }
/* 407 */         if (stmt != null) {
/* 408 */           stmt.close();
/*     */         }
/* 410 */         if (conn != null) {
/* 411 */           dblink.releaseConnection(conn);
/*     */         }
/* 413 */       } catch (Exception x) {}
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected static void handleTireSize(List<SPSOptionByte> optionBytes, List<SPSOption> options, SPSSchemaAdapterNAO adapter) throws Exception {
/* 419 */     if (options == null) {
/*     */       return;
/*     */     }
/* 422 */     for (int i = 0; i < options.size(); i++) {
/* 423 */       SPSOption option = options.get(i);
/* 424 */       if (option instanceof SPSSpecialOption) {
/* 425 */         SPSOptionByte descriptor = ((SPSSpecialOption)option).getOptionByte();
/* 426 */         if (descriptor.isTireSize()) {
/* 427 */           handleOptionByteDependency(optionBytes, descriptor.getDependencyID(), adapter);
/* 428 */           optionBytes.add(descriptor);
/*     */           return;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   protected static void handleDefinedBits(List optionBytes, List<SPSOption> options, List<SPSOptionByte> definedBits, String emissionRPO, String bcmRPO, SPSSchemaAdapterNAO adapter) throws Exception {
/* 436 */     if (options == null) {
/*     */       return;
/*     */     }
/* 439 */     List<SPSOptionByte> optionBytesWithBitData = new ArrayList();
/* 440 */     List<SPSOption> selections = new ArrayList(); int i;
/* 441 */     for (i = 0; i < options.size(); i++) {
/* 442 */       SPSOption option = options.get(i);
/* 443 */       if (option instanceof SPSSpecialOption) {
/* 444 */         SPSOptionByte descriptor = ((SPSSpecialOption)option).getOptionByte();
/* 445 */         if (descriptor.isBitData()) {
/* 446 */           selections.add(option);
/*     */         }
/*     */       } 
/*     */     } 
/* 450 */     for (i = 0; i < selections.size(); i++) {
/* 451 */       SPSSpecialOption option = (SPSSpecialOption)selections.get(i);
/* 452 */       SPSOptionByte descriptor = option.getOptionByte();
/* 453 */       handleOptionByteDependency(optionBytes, descriptor.getDependencyID(), adapter);
/* 454 */       SPSOptionByte target = findOptionByte(optionBytes, descriptor, emissionRPO, bcmRPO, adapter);
/* 455 */       if (option.isOptionYES()) {
/* 456 */         target.Data += mask[option.getBit()];
/*     */       }
/* 458 */       optionBytesWithBitData.add(descriptor);
/* 459 */       target.order = descriptor.order;
/*     */     } 
/* 461 */     for (i = 0; i < definedBits.size(); i++) {
/* 462 */       SPSOptionByte descriptor = definedBits.get(i);
/* 463 */       if (!lookupOptionByte(optionBytesWithBitData, descriptor)) {
/* 464 */         handleOptionByteDependency(optionBytes, descriptor.getDependencyID(), adapter);
/* 465 */         SPSOptionByte target = findOptionByte(optionBytes, descriptor, emissionRPO, bcmRPO, adapter);
/* 466 */         target.order = descriptor.order;
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   protected static boolean lookupOptionByte(List<SPSOptionByte> optionBytes, SPSOptionByte target) {
/* 472 */     for (int i = 0; i < optionBytes.size(); i++) {
/* 473 */       SPSOptionByte descriptor = optionBytes.get(i);
/* 474 */       if (target.getDeviceID() == descriptor.getDeviceID() && target.getBlockNum() == descriptor.getBlockNum() && target.getByteNum() == descriptor.getByteNum()) {
/* 475 */         return true;
/*     */       }
/*     */     } 
/* 478 */     return false;
/*     */   }
/*     */   
/*     */   protected static SPSOptionByte findOptionByte(List<SPSOptionByte> optionBytes, SPSOptionByte target, String emissionRPO, String bcmRPO, SPSSchemaAdapterNAO adapter) throws Exception {
/* 482 */     for (int i = 0; i < optionBytes.size(); i++) {
/* 483 */       SPSOptionByte sPSOptionByte = optionBytes.get(i);
/* 484 */       if (target.getDeviceID() == sPSOptionByte.getDeviceID() && target.getBlockNum() == sPSOptionByte.getBlockNum() && target.getByteNum() == sPSOptionByte.getByteNum()) {
/* 485 */         return sPSOptionByte;
/*     */       }
/*     */     } 
/* 488 */     SPSOptionByte descriptor = new SPSOptionByte();
/* 489 */     descriptor.Device_ID = target.getDeviceID();
/* 490 */     descriptor.Block_No = target.getBlockNum();
/* 491 */     descriptor.Byte_Offset = target.getByteNum();
/* 492 */     optionBytes.add(descriptor);
/* 493 */     if (target.isBitData()) {
/* 494 */       Integer defaultBits = queryDefaultBit(target.Default_Bits_Index, emissionRPO, adapter);
/* 495 */       if (defaultBits == null && bcmRPO != null) {
/* 496 */         defaultBits = queryDefaultBit(target.Default_Bits_Index, bcmRPO, adapter);
/*     */       }
/* 498 */       if (defaultBits != null) {
/* 499 */         descriptor.Data = defaultBits.byteValue();
/*     */       }
/*     */     } 
/* 502 */     return descriptor;
/*     */   }
/*     */   
/*     */   protected static String checkBCMRPO(List<SPSOption> options) {
/* 506 */     if (options != null) {
/* 507 */       for (int i = 0; i < options.size(); i++) {
/* 508 */         SPSOption option = options.get(i);
/* 509 */         if (isBCMRPO(option)) {
/* 510 */           return option.getID();
/*     */         }
/*     */       } 
/*     */     }
/* 514 */     return null;
/*     */   }
/*     */   
/*     */   protected static boolean isBCMRPO(SPSOption option) {
/* 518 */     SPSOption type = (SPSOption)option.getType();
/* 519 */     if (type != null && type.getID().equals("#16") && (
/* 520 */       option.getID().equals("-RFA") || option.getID().equals("+RFA") || option.getID().equals("EXPT"))) {
/* 521 */       return true;
/*     */     }
/*     */     
/* 524 */     return false;
/*     */   }
/*     */   
/*     */   protected static String checkEmissionRPO(List<SPSOption> options) {
/* 528 */     if (options != null) {
/* 529 */       for (int i = 0; i < options.size(); i++) {
/* 530 */         SPSOption option = options.get(i);
/* 531 */         if (isEmissionRPO(option)) {
/* 532 */           return option.getID();
/*     */         }
/*     */       } 
/*     */     }
/* 536 */     return "~";
/*     */   }
/*     */   
/*     */   protected static boolean isEmissionRPO(SPSOption option) {
/* 540 */     SPSOption type = (SPSOption)option.getType();
/* 541 */     if (type != null && type.getID().equals("#3")) {
/* 542 */       return true;
/*     */     }
/* 544 */     return false;
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\server\implementation\adapter\nao\SPSOptionByte.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */