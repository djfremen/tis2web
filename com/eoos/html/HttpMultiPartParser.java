/*     */ package com.eoos.html;
/*     */ 
/*     */ import java.io.ByteArrayOutputStream;
/*     */ import java.io.File;
/*     */ import java.io.FileOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.OutputStream;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Hashtable;
/*     */ import java.util.Locale;
/*     */ import java.util.StringTokenizer;
/*     */ import javax.servlet.ServletInputStream;
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
/*     */ public class HttpMultiPartParser
/*     */ {
/*  69 */   private final int ONE_MB = 1048576;
/*     */   
/*  71 */   private int maxFileSize = 5242880;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean skipMaxStream = false;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Hashtable parseData(ServletInputStream data, String boundary, String saveInDir) throws IllegalArgumentException, IOException {
/*  84 */     return processData(data, boundary, saveInDir);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Hashtable parseData(ServletInputStream data, String boundary) throws IllegalArgumentException, IOException {
/*  94 */     return processData(data, boundary, null);
/*     */   }
/*     */   
/*     */   private Hashtable processData(ServletInputStream is, String boundary, String saveInDir) throws IllegalArgumentException, IOException {
/*  98 */     if (is == null) {
/*  99 */       throw new IllegalArgumentException("InputStream");
/*     */     }
/* 101 */     if (boundary == null || boundary.trim().length() < 1) {
/* 102 */       throw new IllegalArgumentException("boundary");
/*     */     }
/*     */     
/* 105 */     boundary = "--" + boundary;
/*     */     
/* 107 */     StringTokenizer stLine = null, stFields = null;
/* 108 */     FileInfo fileInfo = null;
/* 109 */     Hashtable<Object, Object> dataTable = new Hashtable<Object, Object>(5);
/* 110 */     String line = null, field = null, paramName = null;
/* 111 */     boolean saveFiles = (saveInDir != null && saveInDir.trim().length() > 0), isFile = false;
/*     */ 
/*     */     
/* 114 */     if (saveFiles) {
/* 115 */       File f = new File(saveInDir);
/* 116 */       f.mkdirs();
/*     */     } 
/*     */     
/* 119 */     line = getLine(is);
/* 120 */     if (line == null || !line.startsWith(boundary)) {
/* 121 */       throw new IOException("Boundary not found; boundary = " + boundary + ", line = " + line);
/*     */     }
/* 123 */     String lastParam = null;
/*     */     
/* 125 */     while (line != null) {
/*     */ 
/*     */       
/* 128 */       if (line == null || !line.startsWith(boundary)) {
/* 129 */         return dataTable;
/*     */       }
/*     */       
/* 132 */       line = getLine(is);
/* 133 */       if (line == null) {
/* 134 */         return dataTable;
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 140 */       stLine = new StringTokenizer(line, ";\r\n");
/* 141 */       if (stLine.countTokens() < 2) {
/* 142 */         throw new IllegalArgumentException("Bad data in second line");
/*     */       }
/*     */       
/* 145 */       line = stLine.nextToken().toLowerCase(Locale.ENGLISH);
/* 146 */       if (line.indexOf("form-data") < 0) {
/* 147 */         throw new IllegalArgumentException("Bad data in second line");
/*     */       }
/*     */ 
/*     */       
/* 151 */       stFields = new StringTokenizer(stLine.nextToken(), "=\"");
/* 152 */       if (stFields.countTokens() < 2) {
/* 153 */         throw new IllegalArgumentException("Bad data in second line");
/*     */       }
/*     */       
/* 156 */       fileInfo = new FileInfo();
/* 157 */       stFields.nextToken();
/* 158 */       paramName = stFields.nextToken();
/*     */ 
/*     */ 
/*     */       
/* 162 */       isFile = false;
/* 163 */       if (stLine.hasMoreTokens()) {
/* 164 */         field = stLine.nextToken();
/* 165 */         stFields = new StringTokenizer(field, "=\"");
/* 166 */         if (stFields.countTokens() > 1) {
/* 167 */           if (stFields.nextToken().trim().equalsIgnoreCase("filename")) {
/* 168 */             fileInfo.setName(paramName);
/* 169 */             String value = stFields.nextToken();
/* 170 */             if (value != null && value.trim().length() > 0) {
/* 171 */               fileInfo.setClientFileName(value);
/* 172 */               isFile = true;
/*     */             } else {
/*     */               
/* 175 */               line = getLine(is);
/* 176 */               line = getLine(is);
/* 177 */               line = getLine(is);
/* 178 */               line = getLine(is);
/*     */               continue;
/*     */             } 
/*     */           } 
/* 182 */         } else if (field.toLowerCase(Locale.ENGLISH).indexOf("filename") >= 0) {
/*     */           
/* 184 */           line = getLine(is);
/* 185 */           line = getLine(is);
/* 186 */           line = getLine(is);
/* 187 */           line = getLine(is);
/*     */ 
/*     */           
/*     */           continue;
/*     */         } 
/*     */       } 
/*     */       
/* 194 */       boolean skipBlankLine = true;
/* 195 */       if (isFile) {
/* 196 */         line = getLine(is);
/* 197 */         if (line == null) {
/* 198 */           return dataTable;
/*     */         }
/*     */         
/* 201 */         if (line.trim().length() < 1) {
/* 202 */           skipBlankLine = false;
/*     */         } else {
/* 204 */           stLine = new StringTokenizer(line, ": ");
/* 205 */           if (stLine.countTokens() < 2) {
/* 206 */             throw new IllegalArgumentException("Bad data in third line");
/*     */           }
/* 208 */           stLine.nextToken();
/* 209 */           fileInfo.setFileContentType(stLine.nextToken());
/*     */         } 
/*     */       } 
/*     */ 
/*     */       
/* 214 */       if (skipBlankLine) {
/*     */         
/* 216 */         line = getLine(is);
/* 217 */         if (line == null) {
/* 218 */           return dataTable;
/*     */         }
/*     */       } 
/*     */       
/* 222 */       if (!isFile) {
/* 223 */         line = getLine(is);
/* 224 */         if (line == null) {
/* 225 */           return dataTable;
/*     */         }
/* 227 */         if (lastParam != null && paramName.compareTo(lastParam) == 0) {
/* 228 */           ArrayList<String> al = (ArrayList)dataTable.get(lastParam);
/* 229 */           al.add(line);
/*     */         } else {
/* 231 */           ArrayList<String> al = new ArrayList();
/* 232 */           al.add(line);
/* 233 */           dataTable.put(paramName, al);
/* 234 */           lastParam = paramName;
/*     */         } 
/* 236 */         line = getLine(is);
/*     */         
/*     */         continue;
/*     */       } 
/*     */       
/* 241 */       OutputStream os = null;
/*     */       
/*     */       try {
/* 244 */         String path = null;
/* 245 */         if (saveFiles) {
/* 246 */           os = new FileOutputStream(path = getFileName(saveInDir, fileInfo.getClientFileName()));
/*     */         } else {
/* 248 */           os = new ByteArrayOutputStream(1048576);
/*     */         } 
/* 250 */         boolean readingContent = true;
/* 251 */         byte[] previousLine = new byte[2097152];
/* 252 */         byte[] temp = null;
/* 253 */         byte[] currentLine = new byte[2097152];
/*     */         
/* 255 */         int total = 0;
/*     */         
/*     */         int read;
/* 258 */         if ((read = is.readLine(previousLine, 0, previousLine.length)) == -1) {
/* 259 */           line = null;
/*     */           
/*     */           break;
/*     */         } 
/*     */         
/* 264 */         while (readingContent) {
/* 265 */           int read3; if ((read3 = is.readLine(currentLine, 0, currentLine.length)) == -1) {
/* 266 */             line = null;
/*     */             
/*     */             break;
/*     */           } 
/*     */           
/* 271 */           if (compareBoundary(boundary, currentLine)) {
/* 272 */             String hl = new String(previousLine, 0, read);
/*     */             
/* 274 */             int ndx2 = hl.lastIndexOf('\n');
/*     */ 
/*     */             
/* 277 */             if (ndx2 >= 0) {
/* 278 */               hl = hl.substring(0, ndx2 - 1);
/* 279 */               total += hl.length();
/* 280 */               if (total > this.maxFileSize)
/* 281 */                 throw new FileSizeException(total, true); 
/* 282 */               os.write(hl.getBytes(), 0, hl.length());
/*     */             } else {
/* 284 */               total += read;
/* 285 */               if (total > this.maxFileSize)
/* 286 */                 throw new FileSizeException(total, true); 
/* 287 */               os.write(previousLine, 0, read);
/*     */             } 
/* 289 */             os.flush();
/* 290 */             line = new String(currentLine, 0, read3);
/*     */             break;
/*     */           } 
/* 293 */           total += read;
/* 294 */           if (total > this.maxFileSize) {
/* 295 */             throw new FileSizeException(total, false);
/*     */           }
/* 297 */           os.write(previousLine, 0, read);
/* 298 */           os.flush();
/*     */           
/* 300 */           temp = currentLine;
/* 301 */           currentLine = previousLine;
/* 302 */           previousLine = temp;
/* 303 */           read = read3;
/*     */         } 
/*     */ 
/*     */ 
/*     */         
/* 308 */         os.close();
/* 309 */         temp = null;
/* 310 */         previousLine = null;
/* 311 */         currentLine = null;
/*     */         
/* 313 */         if (!saveFiles) {
/* 314 */           ByteArrayOutputStream baos = (ByteArrayOutputStream)os;
/* 315 */           fileInfo.setFileContents(baos.toByteArray());
/*     */         } else {
/* 317 */           fileInfo.setLocalFile(new File(path));
/* 318 */           os = null;
/*     */         } 
/*     */         
/* 321 */         fileInfo.setComplete();
/* 322 */         dataTable.put(paramName, fileInfo);
/*     */       
/*     */       }
/* 325 */       catch (FileSizeException fse) {
/* 326 */         if (os != null) {
/* 327 */           os.close();
/*     */         }
/* 329 */         fileInfo.setMaxSize(this.maxFileSize);
/* 330 */         dataTable.put(paramName, fileInfo);
/* 331 */         if (this.skipMaxStream) {
/* 332 */           return dataTable;
/*     */         }
/* 334 */         if (!fse.onBoundary) {
/* 335 */           byte[] dropLine = new byte[2097152];
/*     */           int rd;
/* 337 */           while ((rd = is.readLine(dropLine, 0, dropLine.length)) != -1 && !compareBoundary(boundary, dropLine));
/*     */           
/* 339 */           line = new String(dropLine, 0, rd);
/*     */         }
/*     */       
/*     */       }
/* 343 */       catch (Exception e) {
/* 344 */         e.printStackTrace();
/*     */       } 
/*     */     } 
/*     */     
/* 348 */     return dataTable;
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean compareBoundary(String boundary, byte[] ba) {
/* 353 */     if (boundary == null || ba == null) {
/* 354 */       return false;
/*     */     }
/* 356 */     for (int i = 0; i < boundary.length(); i++) {
/* 357 */       if ((byte)boundary.charAt(i) != ba[i])
/* 358 */         return false; 
/*     */     } 
/* 360 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private synchronized String getLine(ServletInputStream sis) throws IOException {
/* 366 */     byte[] b = new byte[1024];
/* 367 */     int read = sis.readLine(b, 0, b.length);
/* 368 */     String line = null;
/*     */ 
/*     */     
/* 371 */     line = new String(b, 0, read);
/*     */     int index;
/* 373 */     if (read != -1 && (index = line.indexOf('\n')) >= 0) {
/* 374 */       line = line.substring(0, index - 1);
/*     */     }
/*     */     
/* 377 */     b = null;
/* 378 */     return line;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private String getFileName(String dir, String fileName) throws IllegalArgumentException {
/* 385 */     String path = null;
/*     */     
/* 387 */     if (dir == null || fileName == null) {
/* 388 */       throw new IllegalArgumentException("dir or fileName is null");
/*     */     }
/* 390 */     int index = fileName.lastIndexOf('/');
/* 391 */     String name = null;
/* 392 */     if (index >= 0) {
/* 393 */       name = fileName.substring(index + 1);
/*     */     } else {
/* 395 */       name = fileName;
/*     */     } 
/* 397 */     index = name.lastIndexOf('\\');
/* 398 */     if (index >= 0) {
/* 399 */       fileName = name.substring(index + 1);
/*     */     }
/* 401 */     path = dir + File.separator + fileName;
/* 402 */     if (File.separatorChar == '/') {
/* 403 */       return path.replace('\\', File.separatorChar);
/*     */     }
/* 405 */     return path.replace('/', File.separatorChar);
/*     */   }
/*     */   
/*     */   public void setMaxFileSize(int n) {
/* 409 */     this.maxFileSize = n;
/*     */   }
/*     */   
/*     */   public void setSkipMaxStream() {
/* 413 */     this.skipMaxStream = true;
/*     */   }
/*     */ 
/*     */   
/*     */   class FileSizeException
/*     */     extends Exception
/*     */   {
/*     */     private static final long serialVersionUID = 1L;
/*     */     
/*     */     private int attempt;
/*     */     
/*     */     private boolean onBoundary = false;
/*     */ 
/*     */     
/*     */     public FileSizeException(String msg) {
/* 428 */       super(msg);
/*     */     }
/*     */ 
/*     */     
/*     */     public FileSizeException(int n, boolean onBoundary) {
/* 433 */       this.attempt = n;
/* 434 */       this.onBoundary = onBoundary;
/*     */     }
/*     */     
/*     */     public int getAttempt() {
/* 438 */       return this.attempt;
/*     */     }
/*     */     
/*     */     public boolean isOnBoundary() {
/* 442 */       return this.onBoundary;
/*     */     }
/*     */     
/*     */     public FileSizeException() {}
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\html\HttpMultiPartParser.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */