/*     */ package com.eoos.util;
/*     */ 
/*     */ import com.eoos.datatype.SectionIndex;
/*     */ import com.eoos.scsm.v2.objectpool.StringBufferPool;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Locale;
/*     */ import java.util.StringTokenizer;
/*     */ import org.apache.regexp.RE;
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
/*     */ public class StringUtilities
/*     */ {
/*     */   public static void replaceSectionContent(StringBuffer origin, SectionIndex index, String replaceWith) {
/*  24 */     origin.replace(index.start, index.end, replaceWith);
/*     */   }
/*     */   
/*     */   public static String getSectionContent(StringBuffer origin, SectionIndex index) {
/*  28 */     if (index == null) {
/*  29 */       return null;
/*     */     }
/*  31 */     return origin.substring(index.start, index.end);
/*     */   }
/*     */   
/*     */   public static String getSectionContent(String origin, SectionIndex index) {
/*  35 */     if (origin == null || index == null || index.start < 0 || index.start > index.end || origin.length() < index.end) {
/*  36 */       return null;
/*     */     }
/*  38 */     return origin.substring(index.start, index.end);
/*     */   }
/*     */   
/*     */   public static SectionIndex getSectionIndex(String origin, String sectionContent, int startIndex) {
/*     */     try {
/*  43 */       int pos = origin.indexOf(sectionContent, startIndex);
/*  44 */       if (pos != -1) {
/*  45 */         return new SectionIndex(pos, pos + sectionContent.length());
/*     */       }
/*  47 */       return null;
/*     */     }
/*  49 */     catch (Exception e) {
/*  50 */       return null;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static SectionIndex getSectionIndex(String origin, String sectionStartIdentifier, String sectionEndIdentifier, int startIndex, boolean includeIdentifiers, boolean largestSection) {
/*     */     try {
/*  57 */       int sectionStart = -1;
/*  58 */       int sectionEnd = -1;
/*     */       
/*  60 */       int pos = -1;
/*     */       
/*  62 */       if ((pos = origin.indexOf(sectionStartIdentifier, startIndex)) == -1) {
/*  63 */         return null;
/*     */       }
/*  65 */       sectionStart = includeIdentifiers ? pos : (pos + sectionStartIdentifier.length());
/*     */ 
/*     */       
/*  68 */       if (largestSection) {
/*  69 */         pos = origin.lastIndexOf(sectionEndIdentifier);
/*     */       } else {
/*  71 */         pos = origin.indexOf(sectionEndIdentifier, sectionStart);
/*     */       } 
/*     */       
/*  74 */       if (pos == -1) {
/*  75 */         return null;
/*     */       }
/*  77 */       sectionEnd = includeIdentifiers ? (pos + sectionEndIdentifier.length()) : pos;
/*     */ 
/*     */       
/*  80 */       return new SectionIndex(sectionStart, sectionEnd);
/*  81 */     } catch (Exception e) {
/*  82 */       return null;
/*     */     } 
/*     */   }
/*     */   
/*     */   public static String replace(String origin, char searchFor, String replaceWith) {
/*  87 */     StringBuffer strSearchFor = StringBufferPool.getThreadInstance().get();
/*     */     try {
/*  89 */       strSearchFor.append(searchFor);
/*  90 */       return replace(origin, strSearchFor.toString(), replaceWith);
/*     */     } finally {
/*     */       
/*  93 */       StringBufferPool.getThreadInstance().free(strSearchFor);
/*     */     } 
/*     */   }
/*     */   
/*     */   public static String replace(String origin, String searchFor, String replaceWith) {
/*  98 */     StringBuffer strb = StringBufferPool.getThreadInstance().get(origin);
/*     */     try {
/* 100 */       int nLength = searchFor.length();
/* 101 */       int nPosition = 0;
/* 102 */       if (replaceWith == null) {
/* 103 */         replaceWith = "";
/*     */       }
/* 105 */       int nPositionCorrection = replaceWith.length() - searchFor.length();
/* 106 */       int nReplaceCount = 0;
/* 107 */       while (nPosition != -1) {
/* 108 */         nPosition = origin.indexOf(searchFor, nPosition);
/* 109 */         if (nPosition != -1) {
/* 110 */           strb.replace(nPosition + nReplaceCount * nPositionCorrection, nPosition + nLength + nReplaceCount * nPositionCorrection, replaceWith);
/* 111 */           nPosition += nLength;
/* 112 */           nReplaceCount++;
/*     */         } 
/*     */       } 
/* 115 */       return strb.toString();
/*     */     } finally {
/*     */       
/* 118 */       StringBufferPool.getThreadInstance().free(strb);
/*     */     } 
/*     */   }
/*     */   
/*     */   public static void replace(StringBuffer origin, String searchFor, String replaceWith) {
/* 123 */     int nLength = searchFor.length();
/* 124 */     if (replaceWith == null) {
/* 125 */       replaceWith = "";
/*     */     }
/* 127 */     int nPosition = 0;
/* 128 */     while (nPosition != -1) {
/* 129 */       nPosition = origin.toString().indexOf(searchFor, nPosition);
/* 130 */       if (nPosition != -1) {
/* 131 */         origin.replace(nPosition, nPosition + nLength, replaceWith);
/* 132 */         nPosition += replaceWith.length();
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public static String byteToHex(byte b) {
/* 138 */     char[] hexDigit = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
/* 139 */     int high = b >> 4 & 0xF;
/* 140 */     int low = b & 0xF;
/* 141 */     char[] array = { hexDigit[high], hexDigit[low] };
/* 142 */     return new String(array);
/*     */   }
/*     */ 
/*     */   
/*     */   public static String bytesToHex(byte[] b) {
/* 147 */     StringBuffer strbResult = StringBufferPool.getThreadInstance().get();
/*     */     try {
/* 149 */       for (int i = 0; i < b.length; i++) {
/* 150 */         strbResult.append(byteToHex(b[i]));
/*     */       }
/* 152 */       return strbResult.toString();
/*     */     } finally {
/*     */       
/* 155 */       StringBufferPool.getThreadInstance().free(strbResult);
/*     */     } 
/*     */   }
/*     */   
/*     */   public static byte hexCharToDigit(char charInput) {
/* 160 */     switch (charInput) {
/*     */       case '0':
/* 162 */         return 0;
/*     */       case '1':
/* 164 */         return 1;
/*     */       case '2':
/* 166 */         return 2;
/*     */       case '3':
/* 168 */         return 3;
/*     */       case '4':
/* 170 */         return 4;
/*     */       case '5':
/* 172 */         return 5;
/*     */       case '6':
/* 174 */         return 6;
/*     */       case '7':
/* 176 */         return 7;
/*     */       case '8':
/* 178 */         return 8;
/*     */       case '9':
/* 180 */         return 9;
/*     */       case 'a':
/* 182 */         return 10;
/*     */       case 'b':
/* 184 */         return 11;
/*     */       case 'c':
/* 186 */         return 12;
/*     */       case 'd':
/* 188 */         return 13;
/*     */       case 'e':
/* 190 */         return 14;
/*     */       case 'f':
/* 192 */         return 15;
/*     */     } 
/*     */     
/* 195 */     throw new IllegalArgumentException("illegal character:" + charInput);
/*     */   }
/*     */ 
/*     */   
/*     */   private static byte hexToByte(char highByte, char lowByte) {
/* 200 */     byte high = hexCharToDigit(highByte);
/* 201 */     byte low = hexCharToDigit(lowByte);
/*     */     
/* 203 */     return (byte)(high * 16 + low);
/*     */   }
/*     */ 
/*     */   
/*     */   public static byte[] hexToBytes(String strBytes) {
/* 208 */     byte[] abyteResult = new byte[strBytes.length() / 2];
/* 209 */     char[] charArray = strBytes.toCharArray();
/* 210 */     byte b = -1;
/* 211 */     int len = strBytes.length();
/*     */     
/* 213 */     for (int i = 0; i < len; i += 2) {
/* 214 */       b = hexToByte(charArray[i], charArray[i + 1]);
/* 215 */       abyteResult[i / 2] = b;
/*     */     } 
/* 217 */     return abyteResult;
/*     */   }
/*     */   
/*     */   public static SectionIndex getSectionIndex(String origin, RE sectionContent, int startIndex) {
/*     */     try {
/* 222 */       if (!sectionContent.match(origin, startIndex)) {
/* 223 */         return null;
/*     */       }
/* 225 */       return new SectionIndex(sectionContent.getParenStart(0), sectionContent.getParenEnd(0));
/*     */     }
/* 227 */     catch (Exception e) {
/* 228 */       return null;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static SectionIndex getSectionIndex(String origin, RE sectionStartIdentifier, RE sectionEndIdentifier, int startIndex, boolean includeIdentifiers, boolean largestSection) {
/*     */     try {
/* 235 */       int sectionStart = -1;
/* 236 */       int sectionEnd = -1;
/*     */ 
/*     */       
/* 239 */       if (!sectionStartIdentifier.match(origin, startIndex)) {
/* 240 */         return null;
/*     */       }
/* 242 */       sectionStart = includeIdentifiers ? sectionStartIdentifier.getParenStart(0) : sectionStartIdentifier.getParenEnd(0);
/*     */ 
/*     */       
/* 245 */       if (!sectionEndIdentifier.match(origin, sectionStartIdentifier.getParenEnd(0))) {
/* 246 */         return null;
/*     */       }
/* 248 */       if (largestSection) {
/* 249 */         int index = includeIdentifiers ? sectionEndIdentifier.getParenEnd(0) : sectionEndIdentifier.getParenStart(0);
/* 250 */         int searchIndex = index + 1;
/* 251 */         while (searchIndex < origin.length() && sectionEndIdentifier.match(origin, searchIndex)) {
/* 252 */           index = includeIdentifiers ? sectionEndIdentifier.getParenEnd(0) : sectionEndIdentifier.getParenStart(0);
/* 253 */           searchIndex = index + 1;
/*     */         } 
/* 255 */         sectionEnd = index;
/*     */       } else {
/* 257 */         sectionEnd = includeIdentifiers ? sectionEndIdentifier.getParenEnd(0) : sectionEndIdentifier.getParenStart(0);
/*     */       } 
/*     */ 
/*     */       
/* 261 */       return new SectionIndex(sectionStart, sectionEnd);
/* 262 */     } catch (Exception e) {
/* 263 */       return null;
/*     */     } 
/*     */   }
/*     */   
/*     */   public static Locale getLocale(String locale) {
/* 268 */     StringTokenizer tokenizer = new StringTokenizer(locale, "_");
/* 269 */     String[] tokens = { "", "", "" };
/*     */     
/* 271 */     for (int i = 0; i < 3 && tokenizer.hasMoreTokens(); i++) {
/* 272 */       tokens[i] = tokenizer.nextToken();
/*     */     }
/* 274 */     return new Locale(tokens[0], tokens[1], tokens[2]);
/*     */   }
/*     */   
/*     */   public static String removeWhitespaces(String input) {
/* 278 */     return replaceWhitespaces(input, null);
/*     */   }
/*     */   
/*     */   public static String replaceWhitespaces(String input, String replace) {
/* 282 */     if (input == null) {
/* 283 */       return null;
/*     */     }
/* 285 */     StringBuffer tmp = StringBufferPool.getThreadInstance().get();
/*     */     try {
/* 287 */       char[] ca = input.toCharArray();
/* 288 */       for (int i = 0; i < ca.length; i++) {
/* 289 */         if (!Character.isWhitespace(ca[i])) {
/* 290 */           tmp.append(ca[i]);
/*     */         }
/* 292 */         else if (replace != null) {
/* 293 */           tmp.append(replace);
/*     */         } 
/*     */       } 
/*     */       
/* 297 */       return tmp.toString();
/*     */     } finally {
/*     */       
/* 300 */       StringBufferPool.getThreadInstance().free(tmp);
/*     */     } 
/*     */   }
/*     */   
/*     */   public static String trimIdentifier(String identifier) {
/* 305 */     if (identifier == null) {
/* 306 */       return null;
/*     */     }
/* 308 */     return removeWhitespaces(identifier.toLowerCase(Locale.ENGLISH));
/*     */   }
/*     */ 
/*     */   
/*     */   public static String format(String theString, List listReplacers) {
/* 313 */     String strCurrentString = theString;
/*     */     
/* 315 */     Iterator iterReplacers = listReplacers.iterator();
/* 316 */     int nCurrentIndex = 0;
/* 317 */     for (int i = 0; i < listReplacers.size(); i++) {
/* 318 */       Object currentReplacer = iterReplacers.next();
/* 319 */       String strToInsert = currentReplacer.toString();
/*     */ 
/*     */       
/* 322 */       nCurrentIndex = strCurrentString.indexOf("%", nCurrentIndex + 1);
/*     */       
/* 324 */       if (nCurrentIndex == -1) {
/* 325 */         return "";
/*     */       }
/* 327 */       String strTmp1 = strCurrentString.substring(0, nCurrentIndex);
/* 328 */       String strTmp2 = strCurrentString.substring(nCurrentIndex + 1, strCurrentString.length());
/*     */       
/* 330 */       strCurrentString = null;
/* 331 */       strCurrentString = new String(strTmp1 + strToInsert + strTmp2);
/*     */ 
/*     */       
/* 334 */       nCurrentIndex += strToInsert.length();
/*     */     } 
/* 336 */     return strCurrentString;
/*     */   }
/*     */   
/*     */   public static void decodeUnicodeSequences(StringBuffer buffer) {
/* 340 */     for (int i = 0; i < buffer.length(); i++) {
/* 341 */       char c = buffer.charAt(i);
/*     */       try {
/* 343 */         if (c == '\\') {
/* 344 */           c = buffer.charAt(i + 1);
/* 345 */           if (c == 'u') {
/* 346 */             int value = 0;
/* 347 */             for (int j = 0; j < 4; j++) {
/* 348 */               c = buffer.charAt(i + 2 + j);
/* 349 */               byte b = hexCharToDigit(c);
/* 350 */               value = (value << 4) + b;
/*     */             } 
/* 352 */             buffer.replace(i, i + 6, new String(new char[] { (char)value }));
/*     */           } 
/*     */         } 
/* 355 */       } catch (IndexOutOfBoundsException e) {
/*     */       
/* 357 */       } catch (IllegalArgumentException e) {}
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoo\\util\StringUtilities.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */