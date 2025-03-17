/*     */ package com.eoos.gm.tis2web.sps.client.tool.navigation.impl;
/*     */ 
/*     */ import com.eoos.datatype.gtwo.Pair;
/*     */ import com.eoos.datatype.gtwo.PairImpl;
/*     */ import com.eoos.gm.tis2web.sps.client.common.impl.ClientAppContextImpl;
/*     */ import com.eoos.scsm.v2.util.Util;
/*     */ import com.eoos.util.StringUtilities;
/*     */ import java.io.BufferedReader;
/*     */ import java.io.CharArrayReader;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.FileReader;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.Comparator;
/*     */ import java.util.HashMap;
/*     */ import java.util.HashSet;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Locale;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import java.util.StringTokenizer;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ public class Navigator
/*     */ {
/*     */   private static final String SALESMAKE = "Salesmake";
/*     */   private static final String MODELYEAR = "Model Year";
/*     */   private static final String VEHICLETYPE = "Vehicle Type";
/*     */   private static final String CARLINE = "Car Line";
/*     */   private static final String ENGINETYPE = "Engine Type(S)";
/*     */   private static final String FUELTYPE = "Fuel Type";
/*     */   private static final String TRANSMISSION = "Transmission";
/*     */   private static final int DEVICE_ID_INDEX = 7;
/*     */   private static final int PROTOCOL_INDEX = 8;
/*     */   private static final int COMM_PARAM_INDEX = 9;
/*     */   private static final int METHOD_GROUP_ID_INDEX = 10;
/*     */   private static final int BRAND_INDEX = 11;
/*     */   private static final int COLUMN_NUMBER = 11;
/*     */   private static final int NUMBER_OF_ATTRIBUTES = 7;
/*     */   
/*     */   private class NavigationTable
/*     */   {
/*     */     private List content;
/*     */     private List filter;
/*     */     private List filteredRows;
/*     */     private List attributes;
/*     */     
/*     */     private NavigationTable() {
/*  51 */       this.content = new ArrayList();
/*  52 */       this.filter = new ArrayList();
/*  53 */       this.filteredRows = new ArrayList();
/*  54 */       this.attributes = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 137 */       this.attributes = new ArrayList(7);
/* 138 */       this.attributes.add("Salesmake");
/* 139 */       this.attributes.add("Model Year");
/* 140 */       this.attributes.add("Vehicle Type");
/* 141 */       this.attributes.add("Car Line");
/* 142 */       this.attributes.add("Engine Type(S)");
/* 143 */       this.attributes.add("Fuel Type");
/* 144 */       this.attributes.add("Transmission");
/*     */       
/* 146 */       resetFilter();
/*     */     }
/*     */     
/*     */     public boolean initialize(List brands) { int i;
/* 150 */       if (brands == null) {
/* 151 */         Navigator.log.error("Brand list is null");
/* 152 */         return false;
/*     */       } 
/* 154 */       if (brands.size() == 0) {
/* 155 */         Navigator.log.error("Brand list is empty");
/* 156 */         return false;
/*     */       } 
/* 158 */       boolean ret = true;
/* 159 */       Iterator<String> it = brands.iterator();
/* 160 */       while (it.hasNext()) {
/* 161 */         String brand = it.next();
/* 162 */         if (loadContent(brand) == true) {
/* 163 */           Navigator.log.info("Navigation table was successfully loaded");
/* 164 */           i = ret & true; continue;
/*     */         } 
/* 166 */         Navigator.log.error("Could not load navigation table for brand: '" + brand + "'");
/* 167 */         i &= 0x0;
/*     */       } 
/*     */ 
/*     */       
/* 171 */       return i; }
/*     */     private class ACLNavigationFilter {
/*     */       private static final int ACL_ENTRY_BRAND_INDEX = 0;
/*     */       private static final int ACL_ENTRY_FULL_DATA_LENGTH = 5; private class ACLFilterEntry {
/* 175 */         private static final int ACL_ENTRY_DATA_LENGTH = 4; List aclEntry = null; ACLFilterEntry(List<?> aclEntry) { this.aclEntry = new ArrayList(aclEntry); } private boolean isValid(List<String> navigationRow) { for (int i = 0; i < 4; i++) { if (((String)this.aclEntry.get(i)).compareTo(navigationRow.get(i)) != 0 && ((String)this.aclEntry.get(i)).compareTo("*") != 0) return false;  }  return true; } } Map aclNavigationFilter = new HashMap<Object, Object>(); public ACLNavigationFilter() { createFilter(); } private void createFilter() { Collection ntf = ClientAppContextImpl.getInstance().getNavigationTableFilter().getEntries(); Iterator<String> it = ntf.iterator(); while (it.hasNext()) { StringTokenizer st = new StringTokenizer(it.next(), "#"); List<String> buffer = new ArrayList(); while (st.hasMoreTokens() == true) { String token = st.nextToken(); buffer.add(token); }  if (buffer.size() == 5) { String brand = ((String)buffer.get(0)).toUpperCase(Locale.ENGLISH); buffer.remove(0); if (this.aclNavigationFilter.containsKey(brand)) { List<ACLFilterEntry> list = (List)this.aclNavigationFilter.get(brand); list.add(new ACLFilterEntry(buffer)); continue; }  List<ACLFilterEntry> aclFilterEntries = new ArrayList(); aclFilterEntries.add(new ACLFilterEntry(buffer)); this.aclNavigationFilter.put(brand, aclFilterEntries); continue; }  Navigator.log.error("Could not create navigation ACL filter entry. Size is invalid: " + buffer.size() + " Row ACL data: " + (String)it.next()); }  Navigator.log.debug("Number of navigation ACL filter entries: " + this.aclNavigationFilter.size()); } public boolean isValid(List<String> navigationRow) { List aclEntries = (List)this.aclNavigationFilter.get(((String)navigationRow.get(11)).toUpperCase(Locale.ENGLISH)); if (aclEntries != null) { Iterator<ACLFilterEntry> it = aclEntries.iterator(); while (it.hasNext()) { if (((ACLFilterEntry)it.next()).isValid(navigationRow)) return true;  }  } else { return true; }  return false; } } public List getAttributes() { return this.attributes; }
/*     */ 
/*     */     
/*     */     public List getValues(String attribute) {
/* 179 */       List<Comparable> values = getValues(getAttributeIndex(attribute));
/* 180 */       if (attribute.compareToIgnoreCase("Model Year") != 0) {
/* 181 */         Collections.sort(values);
/*     */       } else {
/* 183 */         Collections.sort(values, new Comparator<Comparable>() {
/*     */               public int compare(Object o1, Object o2) {
/* 185 */                 int result = 0;
/* 186 */                 if (((String)o1).compareToIgnoreCase((String)o2) < 0) {
/* 187 */                   result = 1;
/* 188 */                 } else if (((String)o1).compareToIgnoreCase((String)o2) > 0) {
/* 189 */                   result = -1;
/*     */                 } 
/* 191 */                 return result;
/*     */               }
/*     */             });
/*     */       } 
/* 195 */       return values;
/*     */     }
/*     */     
/*     */     public boolean setFilter(String attribute, String value) {
/* 199 */       boolean ret = false;
/* 200 */       for (int i = 0; i < this.attributes.size(); i++) {
/* 201 */         if (((String)this.attributes.get(i)).compareToIgnoreCase(attribute) == 0) {
/* 202 */           if (value.compareToIgnoreCase("OTHER") == 0 || value.compareToIgnoreCase("ANY") == 0) {
/* 203 */             value = new String("DONTCARE");
/*     */           }
/* 205 */           this.filter.set(i, value);
/* 206 */           ret = true;
/*     */           break;
/*     */         } 
/*     */       } 
/* 210 */       filterContent();
/* 211 */       return ret;
/*     */     }
/*     */     
/*     */     public boolean resetFilter() {
/* 215 */       this.filter.clear();
/* 216 */       for (int i = 0; i < 7; i++) {
/* 217 */         this.filter.add(null);
/*     */       }
/* 219 */       this.filteredRows.clear();
/* 220 */       return true;
/*     */     }
/*     */     
/*     */     public Integer getMethodGroupID() {
/* 224 */       if (this.filteredRows.size() == 1 && 
/* 225 */         IsAllAttributesSet(this.filteredRows.get(0)) == true) {
/* 226 */         List<String> mgids = getValues(10);
/* 227 */         if (mgids != null && mgids.size() == 1) {
/* 228 */           return Integer.valueOf(mgids.get(0));
/*     */         }
/*     */       } 
/*     */       
/* 232 */       return null;
/*     */     }
/*     */     
/*     */     public RIMParams getRIMParams() {
/* 236 */       RIMParams rimp = null;
/* 237 */       List<String> dids = getValues(7), prts = getValues(8), cprs = getValues(9);
/* 238 */       if (dids != null && prts != null && cprs != null && 
/* 239 */         dids.size() == 1 && prts.size() == 1 && cprs.size() == 1) {
/* 240 */         rimp = new RIMParams(Integer.parseInt(dids.get(0)), Integer.parseInt(prts.get(0)), Integer.parseInt(cprs.get(0)));
/*     */       }
/*     */       
/* 243 */       return rimp;
/*     */     }
/*     */     
/*     */     public String getBrand() {
/* 247 */       String brand = null;
/* 248 */       List<String> brands = getValues(11);
/* 249 */       if (brands != null && 
/* 250 */         brands.size() == 1) {
/* 251 */         brand = new String(brands.get(0));
/*     */       }
/*     */       
/* 254 */       return brand;
/*     */     }
/*     */     
/*     */     private int getAttributeIndex(String attribute) {
/* 258 */       int index = 0;
/* 259 */       for (int i = 0; i < 7; i++) {
/* 260 */         if (((String)this.attributes.get(i)).compareToIgnoreCase(attribute) == 0) {
/* 261 */           index = i;
/*     */           break;
/*     */         } 
/*     */       } 
/* 265 */       return index;
/*     */     }
/*     */     
/*     */     private boolean loadContent(String brand) {
/* 269 */       return loadTable(brand, createFileName(brand));
/*     */     }
/*     */     
/*     */     private String createFileName(String brand) {
/* 273 */       StringBuffer fileName = new StringBuffer(System.getProperty("user.home"));
/* 274 */       fileName.append("\\SPS\\navTables\\");
/* 275 */       fileName.append("VCS_");
/* 276 */       fileName.append(brand);
/* 277 */       fileName.append("_NAV.table");
/* 278 */       Navigator.log.debug("Navigation table file: " + fileName);
/* 279 */       return fileName.toString();
/*     */     }
/*     */     
/*     */     private boolean loadTable(String brand, String fileName) {
/* 283 */       boolean ret = false;
/* 284 */       BufferedReader br = null;
/*     */       try {
/* 286 */         br = new BufferedReader(new FileReader(fileName));
/* 287 */         ACLNavigationFilter aclFilter = new ACLNavigationFilter();
/*     */         while (true) {
/* 289 */           String line = br.readLine();
/* 290 */           if (line != null) {
/* 291 */             List<String> row = new ArrayList();
/* 292 */             StringTokenizer st = new StringTokenizer(line, "\t");
/* 293 */             while (st.hasMoreTokens() == true) {
/* 294 */               String token = st.nextToken();
/* 295 */               row.add(token);
/*     */             } 
/* 297 */             if (row.size() == 11) {
/* 298 */               if (IsUniqueRow(row) == true) {
/* 299 */                 row.add(brand);
/* 300 */                 if (aclFilter.isValid(row) == true)
/* 301 */                   this.content.add(row); 
/*     */                 continue;
/*     */               } 
/* 304 */               Navigator.log.error("Not unique navigation row: " + row + " Brand: " + brand);
/*     */               continue;
/*     */             } 
/* 307 */             Navigator.log.error("Navigation table entry contains invalid data ( incorrect number of columns ) : " + line);
/*     */             
/*     */             continue;
/*     */           } 
/*     */           
/*     */           break;
/*     */         } 
/* 314 */         ret = true;
/* 315 */       } catch (Exception e) {
/* 316 */         Navigator.log.error("Could not load navigation table. File name:  " + fileName + ". " + e);
/*     */       } finally {
/*     */         try {
/* 319 */           if (br != null) {
/* 320 */             br.close();
/*     */           }
/* 322 */         } catch (Exception e) {
/* 323 */           Navigator.log.error("" + fileName + " " + e);
/*     */         } 
/*     */       } 
/* 326 */       return ret;
/*     */     }
/*     */     
/*     */     private boolean compareToFilter(List<String> row) {
/* 330 */       boolean ret = false;
/* 331 */       if (row.size() >= this.filter.size()) {
/* 332 */         for (int i = 0; i < this.filter.size(); i++) {
/* 333 */           if (this.filter.get(i) != null) {
/* 334 */             if (((String)this.filter.get(i)).compareToIgnoreCase(row.get(i)) == 0) {
/* 335 */               ret = true;
/*     */             } else {
/* 337 */               ret = false;
/*     */               break;
/*     */             } 
/*     */           } else {
/* 341 */             ret = true;
/*     */             break;
/*     */           } 
/*     */         } 
/*     */       }
/* 346 */       return ret;
/*     */     }
/*     */     
/*     */     private List getValues(int columnIndex) {
/* 350 */       filterContent();
/* 351 */       Set<String> buffer = new HashSet();
/* 352 */       Iterator<List> it = this.filteredRows.iterator();
/* 353 */       while (it.hasNext()) {
/* 354 */         List<String> row = it.next();
/* 355 */         buffer.add(row.get(columnIndex));
/*     */       } 
/* 357 */       List<String> values = new ArrayList();
/* 358 */       it = (Iterator)buffer.iterator();
/* 359 */       while (it.hasNext()) {
/* 360 */         String value = (String)it.next();
/* 361 */         if (value.compareToIgnoreCase("DONTCARE") == 0) {
/* 362 */           if (buffer.size() == 1) {
/* 363 */             values.add(new String("ANY")); continue;
/*     */           } 
/* 365 */           values.add(new String("OTHER"));
/*     */           continue;
/*     */         } 
/* 368 */         values.add(new String(value));
/*     */       } 
/*     */       
/* 371 */       return values;
/*     */     }
/*     */     
/*     */     private boolean IsAllAttributesSet(List<String> row) {
/* 375 */       boolean ret = false;
/* 376 */       for (int i = 0; i < 7; i++) {
/* 377 */         if (((String)row.get(i)).compareToIgnoreCase("DONTCARE") != 0) {
/* 378 */           if (this.filter.get(i) == null) {
/* 379 */             ret = false;
/*     */             break;
/*     */           } 
/* 382 */           ret = true;
/*     */         } 
/*     */       } 
/*     */       
/* 386 */       return ret;
/*     */     }
/*     */     
/*     */     private void filterContent() {
/* 390 */       this.filteredRows.clear();
/* 391 */       Iterator<List> it = this.content.iterator();
/* 392 */       while (it.hasNext()) {
/* 393 */         List row = it.next();
/* 394 */         if (row != null) {
/* 395 */           if (row.size() != 0) {
/* 396 */             if (compareToFilter(row) == true)
/* 397 */               this.filteredRows.add(row); 
/*     */             continue;
/*     */           } 
/* 400 */           Navigator.log.error("Row is empty");
/*     */           continue;
/*     */         } 
/* 403 */         Navigator.log.error("Invalid row");
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     private boolean IsUniqueRow(List row) {
/* 409 */       boolean ret = true;
/* 410 */       if (row != null) {
/* 411 */         Iterator<List> it = this.content.iterator();
/* 412 */         while (it.hasNext()) {
/* 413 */           List currentRow = it.next();
/* 414 */           if (currentRow != null) {
/* 415 */             if (areRowsEqual(row, currentRow) == true) {
/* 416 */               ret = false;
/*     */               break;
/*     */             } 
/* 419 */             ret = true;
/*     */             continue;
/*     */           } 
/* 422 */           Navigator.log.error("Invalid navigation row - row is null");
/*     */         } 
/*     */       } else {
/*     */         
/* 426 */         Navigator.log.error("Invalid input parameter - row is null");
/*     */       } 
/* 428 */       return ret;
/*     */     }
/*     */     
/*     */     private boolean areRowsEqual(List<String> row1, List<String> row2) {
/* 432 */       boolean ret = true;
/* 433 */       int length = (row1.size() > row2.size()) ? row2.size() : row1.size();
/* 434 */       for (int i = 0; i < length; i++) {
/* 435 */         String value1 = row1.get(i);
/* 436 */         String value2 = row2.get(i);
/* 437 */         if (value1.compareToIgnoreCase(value2) != 0) {
/* 438 */           ret = false;
/*     */           break;
/*     */         } 
/*     */       } 
/* 442 */       return ret;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private class LanguageTable
/*     */   {
/* 452 */     Map content = new HashMap<Object, Object>();
/*     */     public boolean initialize(List brands, Locale locale) {
/*     */       int i;
/* 455 */       if (brands == null) {
/* 456 */         Navigator.log.error("Brand list is null");
/* 457 */         return false;
/*     */       } 
/* 459 */       if (brands.size() == 0) {
/* 460 */         Navigator.log.error("Brand list is empty");
/* 461 */         return false;
/*     */       } 
/* 463 */       boolean ret = true;
/* 464 */       if (locale != null) {
/* 465 */         Iterator<String> it = brands.iterator();
/* 466 */         while (it.hasNext()) {
/* 467 */           String brand = it.next();
/* 468 */           if (loadContent(brand, locale) == true) {
/* 469 */             Navigator.log.info("Language table was successfully loaded ");
/* 470 */             i = ret & true; continue;
/*     */           } 
/* 472 */           Navigator.log.error("Could not load language table for brand: " + brand);
/* 473 */           i &= 0x0;
/*     */         }
/*     */       
/*     */       } else {
/*     */         
/* 478 */         Navigator.log.error("Locale is null");
/*     */       } 
/* 480 */       return i;
/*     */     }
/*     */     
/*     */     public String translate(String enuString) {
/* 484 */       String translated = (String)this.content.get(enuString);
/* 485 */       if (translated == null) {
/* 486 */         Navigator.log.error("Could not translate string: " + enuString);
/*     */       }
/* 488 */       return translated;
/*     */     }
/*     */     
/*     */     private boolean loadContent(String brand, Locale locale) {
/* 492 */       if (loadUsingLangCountry(brand, locale) == true) {
/* 493 */         return true;
/*     */       }
/* 495 */       if (loadUsingLang(brand, locale) == true) {
/* 496 */         return true;
/*     */       }
/* 498 */       if (loadUsingDefaultLang(brand) == true) {
/* 499 */         return true;
/*     */       }
/* 501 */       return false;
/*     */     }
/*     */     
/*     */     private boolean loadUsingLangCountry(String brand, Locale locale) {
/* 505 */       boolean ret = false;
/* 506 */       String country = locale.getCountry();
/* 507 */       if (country.length() > 0) {
/* 508 */         String language = locale.getLanguage();
/* 509 */         if (language.length() > 0) {
/* 510 */           ret = loadTable(createFileName(brand, language + "_" + country));
/*     */         } else {
/* 512 */           Navigator.log.error("Locale object does not contain language");
/*     */         } 
/*     */       } else {
/* 515 */         Navigator.log.error("Locale object does not contain country");
/*     */       } 
/* 517 */       return ret;
/*     */     }
/*     */     
/*     */     private boolean loadUsingLang(String brand, Locale locale) {
/* 521 */       boolean ret = false;
/* 522 */       String language = locale.getLanguage();
/* 523 */       if (language.length() > 0) {
/* 524 */         ret = loadTable(createFileName(brand, language));
/*     */       } else {
/* 526 */         Navigator.log.error("Locale object does not contain language");
/*     */       } 
/* 528 */       return ret;
/*     */     }
/*     */     
/*     */     private boolean loadUsingDefaultLang(String brand) {
/* 532 */       return loadTable(createFileName(brand, "en"));
/*     */     }
/*     */     
/*     */     private String createFileName(String brand, String locale) {
/* 536 */       StringBuffer fileName = new StringBuffer(System.getProperty("user.home"));
/* 537 */       fileName.append("\\SPS\\navTables\\");
/* 538 */       fileName.append("VCS_");
/* 539 */       fileName.append(brand);
/* 540 */       fileName.append("_NAV_RES_");
/* 541 */       fileName.append(locale);
/* 542 */       fileName.append(".properties");
/* 543 */       Navigator.log.debug("Language table file: '" + fileName + "'");
/* 544 */       return fileName.toString();
/*     */     }
/*     */     
/*     */     private boolean loadTable(String fileName) {
/* 548 */       boolean ret = false;
/* 549 */       FileInputStream fis = null;
/*     */       try {
/* 551 */         fis = new FileInputStream(fileName);
/* 552 */         byte[] buffer = new byte[fis.available()];
/* 553 */         fis.read(buffer);
/* 554 */         StringBuffer sb = new StringBuffer(new String(buffer, "UTF-8"));
/* 555 */         StringUtilities.decodeUnicodeSequences(sb);
/* 556 */         BufferedReader br = new BufferedReader(new CharArrayReader(sb.toString().toCharArray()));
/*     */         while (true) {
/* 558 */           String line = br.readLine();
/* 559 */           if (line != null) {
/* 560 */             StringTokenizer st = new StringTokenizer(line, "=");
/* 561 */             if (st.hasMoreTokens() == true) {
/* 562 */               String key = st.nextToken();
/* 563 */               if (st.hasMoreTokens() == true) {
/* 564 */                 String val = st.nextToken();
/* 565 */                 if (key != null && val != null) {
/* 566 */                   this.content.put(key, val);
/*     */                 }
/*     */               } 
/*     */             } 
/*     */             
/*     */             continue;
/*     */           } 
/*     */           break;
/*     */         } 
/* 575 */         ret = true;
/* 576 */       } catch (Exception e) {
/* 577 */         Navigator.log.error("Could not load language table. File name:  " + fileName + ". " + e);
/*     */       } finally {
/*     */         try {
/* 580 */           fis.close();
/* 581 */         } catch (Exception e) {
/* 582 */           Navigator.log.error("Could not close file: " + fileName + " " + e);
/*     */         } 
/*     */       } 
/* 585 */       return ret;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     private LanguageTable() {}
/*     */   }
/*     */   
/* 593 */   private static Logger log = Logger.getLogger(Navigator.class);
/*     */   
/*     */   private static final String VCS_FILENAME_PREFIX = "VCS_";
/*     */   
/* 597 */   private NavigationTable navTable = new NavigationTable();
/* 598 */   private LanguageTable lngTable = new LanguageTable();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean initialize(List brands, Locale locale) {
/* 604 */     boolean ret = false;
/* 605 */     if (brands == null) {
/* 606 */       log.error("Brand list is null");
/* 607 */       return ret;
/*     */     } 
/* 609 */     if (brands.size() == 0) {
/* 610 */       log.error("Brand list is empty");
/* 611 */       return ret;
/*     */     } 
/* 613 */     if (this.navTable.initialize(brands)) {
/* 614 */       if (locale == null) {
/* 615 */         locale = new Locale("en", "US");
/* 616 */         log.error("Invalid locale. Use default locale: en_US");
/*     */       } 
/* 618 */       if (this.lngTable.initialize(brands, locale)) {
/* 619 */         ret = true;
/*     */       } else {
/* 621 */         log.error("Could not initialize language table");
/*     */       } 
/*     */     } else {
/* 624 */       log.error("Could not initialize navigation table");
/*     */     } 
/*     */     
/* 627 */     return ret;
/*     */   }
/*     */   
/*     */   public Pair[] getAttributes() {
/* 631 */     return createTranslatedPairs(this.navTable.getAttributes());
/*     */   }
/*     */   
/*     */   public Pair[] getValues(String attribute) {
/* 635 */     return createTranslatedPairs(this.navTable.getValues(attribute));
/*     */   }
/*     */   
/*     */   public boolean setFilter(String attribute, String value) {
/* 639 */     return this.navTable.setFilter(attribute, value);
/*     */   }
/*     */   
/*     */   public boolean resetFilter() {
/* 643 */     return this.navTable.resetFilter();
/*     */   }
/*     */   
/*     */   public Integer getMethodGroupID() {
/* 647 */     return this.navTable.getMethodGroupID();
/*     */   }
/*     */   
/*     */   public RIMParams getRIMParams() {
/* 651 */     return this.navTable.getRIMParams();
/*     */   }
/*     */   
/*     */   private Pair[] createTranslatedPairs(List<String> strings) {
/* 655 */     int arrayLength = strings.size();
/* 656 */     Pair[] translated = new Pair[arrayLength];
/* 657 */     for (int i = 0; i < arrayLength; i++) {
/* 658 */       String string = strings.get(i);
/* 659 */       if (!Util.isNullOrEmpty(string)) {
/* 660 */         String nativeString = this.lngTable.translate(string);
/* 661 */         if (nativeString == null || nativeString.length() == 0) {
/* 662 */           log.error("Could not translate string: " + string);
/* 663 */           nativeString = new String(string);
/*     */         } 
/* 665 */         translated[i] = (Pair)new PairImpl(string, nativeString);
/*     */       } else {
/* 667 */         log.error("String to translation is null or empty");
/*     */       } 
/*     */     } 
/* 670 */     return translated;
/*     */   }
/*     */   
/*     */   public String getMethodGroupProviderID() {
/* 674 */     return this.navTable.getBrand();
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\client\tool\navigation\impl\Navigator.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */