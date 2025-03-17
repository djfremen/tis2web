/*     */ package com.eoos.gm.tis2web.registration.common;
/*     */ 
/*     */ import com.eoos.gm.tis2web.registration.service.cai.AuthorizationStatus;
/*     */ import com.eoos.gm.tis2web.registration.service.cai.InstallationType;
/*     */ import com.eoos.gm.tis2web.registration.service.cai.SoftwareKeyException;
/*     */ import java.io.UnsupportedEncodingException;
/*     */ import java.security.MessageDigest;
/*     */ import java.security.NoSuchAlgorithmException;
/*     */ import java.util.Calendar;
/*     */ import java.util.Locale;
/*     */ import javax.crypto.Cipher;
/*     */ import javax.crypto.spec.SecretKeySpec;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SoftwareKey
/*     */ {
/*  20 */   private static final Logger log = Logger.getLogger(SoftwareKey.class);
/*     */ 
/*     */ 
/*     */   
/*     */   private String hardwareID;
/*     */ 
/*     */ 
/*     */   
/*     */   private String hardwareHashID;
/*     */ 
/*     */ 
/*     */   
/*     */   private String hardwareInfo;
/*     */ 
/*     */   
/*     */   private InstallationType installationType;
/*     */ 
/*     */   
/*     */   private int users;
/*     */ 
/*     */   
/*     */   private String authorizationID;
/*     */ 
/*     */   
/*  44 */   private AuthorizationStatus authorizationStatus = AuthorizationStatus.TEMPORARY;
/*  45 */   private String subscriberID = "TIS2WEB";
/*  46 */   private long timestamp = System.currentTimeMillis();
/*     */   private String softwareKey;
/*     */   
/*     */   public SoftwareKey(String subscriberID, String softwareKey) {
/*  50 */     this();
/*  51 */     this.subscriberID = subscriberID.toUpperCase(Locale.ENGLISH);
/*  52 */     decode(decrypt(softwareKey));
/*  53 */     this.softwareKey = softwareKey;
/*     */   }
/*     */   public SoftwareKey() {}
/*     */   public SoftwareKey(String softwareKey) {
/*  57 */     this();
/*  58 */     decode(toBytes(softwareKey));
/*     */   }
/*     */   
/*     */   public SoftwareKey(String subscriberID, String licenseKey, String hardwareID, long timestamp) {
/*  62 */     this();
/*  63 */     this.subscriberID = subscriberID;
/*  64 */     this.softwareKey = licenseKey;
/*  65 */     decode(decrypt(licenseKey));
/*  66 */     this.hardwareID = hardwareID;
/*  67 */     this.timestamp = timestamp;
/*     */   }
/*     */   
/*     */   public void migrate() {
/*  71 */     this.authorizationStatus = AuthorizationStatus.MIGRATED;
/*     */   }
/*     */   
/*     */   public void authorize() {
/*  75 */     this.authorizationStatus = AuthorizationStatus.AUTHORIZED;
/*     */   }
/*     */   
/*     */   public void revoke() {
/*  79 */     this.authorizationStatus = AuthorizationStatus.REVOKED;
/*     */   }
/*     */   
/*     */   public void reject() {
/*  83 */     this.authorizationStatus = AuthorizationStatus.TEMPORARY;
/*     */   }
/*     */   
/*     */   public void invalidate() {
/*  87 */     this.softwareKey = null;
/*     */   }
/*     */   
/*     */   public String getAuthorizationID() {
/*  91 */     return this.authorizationID;
/*     */   }
/*     */   
/*     */   public void setAuthorizationID(String authorizationID) {
/*  95 */     this.authorizationID = authorizationID;
/*     */   }
/*     */   
/*     */   public AuthorizationStatus getAuthorizationStatus() {
/*  99 */     return this.authorizationStatus;
/*     */   }
/*     */   
/*     */   public String getHardwareHashID() {
/* 103 */     return this.hardwareHashID;
/*     */   }
/*     */   
/*     */   public String getHardwareID() {
/* 107 */     return this.hardwareID;
/*     */   }
/*     */   
/*     */   public void setHardwareID(String hardwareID) {
/* 111 */     this.hardwareID = hardwareID;
/*     */     try {
/* 113 */       this.hardwareHashID = getDigest(hardwareID.getBytes("US-ASCII"));
/* 114 */     } catch (UnsupportedEncodingException e) {
/* 115 */       log.error("getBytes() failed", e);
/* 116 */       this.hardwareHashID = getDigest(hardwareID.getBytes());
/*     */     } 
/*     */   }
/*     */   
/*     */   public void setHardwareInfo(String hardwareInfo) {
/* 121 */     this.hardwareInfo = hardwareInfo;
/*     */   }
/*     */   
/*     */   public String getHardwareInfo() {
/* 125 */     return this.hardwareInfo;
/*     */   }
/*     */   
/*     */   public InstallationType getInstallationType() {
/* 129 */     return this.installationType;
/*     */   }
/*     */   
/*     */   public void setInstallationType(InstallationType installationType) {
/* 133 */     this.installationType = installationType;
/*     */   }
/*     */   
/*     */   public int getUsers() {
/* 137 */     return this.users;
/*     */   }
/*     */   
/*     */   public void setUsers(int users) {
/* 141 */     this.users = users;
/*     */   }
/*     */   
/*     */   public String getLicenseKey() {
/* 145 */     return encrypt(encode());
/*     */   }
/*     */   
/*     */   public String getSoftwareKey() {
/* 149 */     if (this.softwareKey == null) {
/* 150 */       this.softwareKey = encrypt(encode());
/*     */     }
/* 152 */     return this.softwareKey;
/*     */   }
/*     */   
/*     */   public void setSoftwareKey(String softwareKey) {
/* 156 */     this.softwareKey = softwareKey;
/*     */   }
/*     */   
/*     */   public String getSubscriberID() {
/* 160 */     return this.subscriberID;
/*     */   }
/*     */   
/*     */   public void setSubscriberID(String subscriberID) {
/* 164 */     this.subscriberID = subscriberID.toUpperCase(Locale.ENGLISH);
/*     */   }
/*     */   
/*     */   public long getTimestamp() {
/* 168 */     return this.timestamp;
/*     */   }
/*     */   
/*     */   public void setTimestamp(long timestamp) {
/* 172 */     this.timestamp = timestamp;
/*     */   }
/*     */   
/*     */   private String encrypt(byte[] payload) {
/*     */     try {
/* 177 */       byte[] key = keyAES(getSubscriberID());
/* 178 */       return bytesToHex(encryptAES(key, payload));
/* 179 */     } catch (Exception e) {
/* 180 */       log.error("unable to encrypt, throwing SoftwareKeyException  - exception:" + e, e);
/* 181 */       throw new SoftwareKeyException();
/*     */     } 
/*     */   }
/*     */   
/*     */   private byte[] encode() {
/* 186 */     byte[] payload = new byte[31];
/*     */     try {
/* 188 */       copy(payload, 0, getHardwareHashID().getBytes("US-ASCII"));
/* 189 */     } catch (UnsupportedEncodingException e) {
/* 190 */       log.error("getBytes() failed", e);
/* 191 */       copy(payload, 0, getHardwareHashID().getBytes());
/*     */     } 
/* 193 */     payload[10] = (byte)getInstallationType().ord();
/* 194 */     payload[11] = (byte)getUsers();
/* 195 */     payload[12] = (byte)getAuthorizationStatus().ord();
/*     */     try {
/* 197 */       copy(payload, 13, pad(getAuthorizationID(), 10).getBytes("US-ASCII"));
/* 198 */     } catch (UnsupportedEncodingException e) {
/* 199 */       log.error("getBytes() failed", e);
/* 200 */       copy(payload, 13, pad(getAuthorizationID(), 10).getBytes());
/*     */     } 
/*     */     try {
/* 203 */       copy(payload, 26, seconds(this.timestamp).getBytes("US-ASCII"));
/* 204 */     } catch (UnsupportedEncodingException e) {
/* 205 */       log.error("getBytes() failed", e);
/* 206 */       copy(payload, 26, seconds(this.timestamp).getBytes());
/*     */     } 
/* 208 */     payload[24] = checksum(payload, 0, 22);
/* 209 */     payload[25] = checksum(payload, 0, 30);
/* 210 */     return payload;
/*     */   }
/*     */   
/*     */   private byte[] decrypt(String encryption) {
/*     */     try {
/* 215 */       byte[] key = keyAES(getSubscriberID());
/* 216 */       return decryptAES(key, toBytes(encryption));
/* 217 */     } catch (Exception e) {
/* 218 */       if (!"TIS2WEB".equals(getSubscriberID())) {
/*     */         try {
/* 220 */           byte[] key = keyAES("TIS2WEB");
/* 221 */           return decryptAES(key, toBytes(encryption));
/* 222 */         } catch (Exception x) {}
/*     */       }
/*     */       
/* 225 */       log.error("unable to decrypt, throwing SoftwareKeyException  - exception:" + e, e);
/* 226 */       throw new SoftwareKeyException();
/*     */     } 
/*     */   }
/*     */   
/*     */   private void decode(byte[] payload) {
/* 231 */     if (payload[23] != 0) {
/*     */       return;
/*     */     }
/* 234 */     if (payload[24] != checksum(payload, 0, 22)) {
/*     */       return;
/*     */     }
/* 237 */     if (payload[25] != (byte)(checksum(payload, 0, 30) - payload[25])) {
/*     */       return;
/*     */     }
/* 240 */     byte[] hwid = new byte[10];
/* 241 */     copy(hwid, 0, payload);
/* 242 */     this.hardwareHashID = new String(hwid);
/* 243 */     this.installationType = InstallationType.get(payload[10]);
/* 244 */     this.users = payload[11];
/* 245 */     this.authorizationStatus = AuthorizationStatus.get(payload[12]);
/* 246 */     byte[] subscription = new byte[10];
/* 247 */     transfer(subscription, 13, payload);
/* 248 */     String subscriptionID = new String(subscription);
/* 249 */     this.authorizationID = subscriptionID.replace('~', ' ').trim();
/* 250 */     byte[] ts = new byte[5];
/* 251 */     transfer(ts, 26, payload);
/* 252 */     this.timestamp = Long.parseLong(new String(ts));
/*     */   }
/*     */   
/*     */   private void copy(byte[] target, int start, byte[] source) {
/* 256 */     for (int i = start, j = 0; j < source.length && i < target.length; i++, j++) {
/* 257 */       target[i] = source[j];
/*     */     }
/*     */   }
/*     */   
/*     */   private void transfer(byte[] target, int start, byte[] source) {
/* 262 */     for (int i = start, j = 0; i < source.length && j < target.length; i++, j++) {
/* 263 */       target[j] = source[i];
/*     */     }
/*     */   }
/*     */   
/*     */   private String seconds(long timestamp) {
/* 268 */     Calendar date = Calendar.getInstance();
/* 269 */     date.setTimeInMillis(timestamp);
/* 270 */     int seconds = date.get(13) + date.get(12) * 60 + date.get(10) * 60 * 60;
/* 271 */     return pad(seconds, 5);
/*     */   }
/*     */   
/*     */   private String pad(int value, int length) {
/* 275 */     String data = Integer.toString(value);
/* 276 */     if (data.length() < length) {
/* 277 */       StringBuffer padding = new StringBuffer();
/* 278 */       while (padding.length() < length - data.length()) {
/* 279 */         padding.append('0');
/*     */       }
/* 281 */       data = padding + data;
/*     */     } 
/* 283 */     return data;
/*     */   }
/*     */   
/*     */   private String pad(String value, int length) {
/* 287 */     if (value == null) {
/* 288 */       value = "";
/*     */     }
/* 290 */     StringBuffer buffer = new StringBuffer();
/* 291 */     buffer.append(value);
/* 292 */     while (buffer.length() < length) {
/* 293 */       buffer.append('~');
/*     */     }
/* 295 */     return buffer.toString().substring(0, length);
/*     */   }
/*     */   
/*     */   private String getDigest(byte[] buffer) {
/*     */     try {
/* 300 */       MessageDigest md5 = MessageDigest.getInstance("MD5");
/* 301 */       md5.update(buffer);
/* 302 */       byte[] digest = md5.digest();
/* 303 */       digest[0] = fold(digest[0], digest[10]);
/* 304 */       digest[1] = fold(digest[1], digest[11]);
/* 305 */       digest[2] = fold(digest[2], digest[12]);
/* 306 */       digest[3] = fold(digest[3], digest[13]);
/* 307 */       digest[4] = fold(digest[4], digest[14]);
/* 308 */       digest[5] = fold(digest[5], digest[15]);
/* 309 */       digest[0] = fold(digest[0], digest[9]);
/* 310 */       digest[1] = fold(digest[1], digest[8]);
/* 311 */       digest[2] = fold(digest[2], digest[7]);
/* 312 */       digest[3] = fold(digest[3], digest[6]);
/* 313 */       digest[4] = fold(digest[4], digest[5]);
/* 314 */       return bytesToHex(false, digest, 5);
/* 315 */     } catch (NoSuchAlgorithmException e) {
/*     */       
/* 317 */       return null;
/*     */     } 
/*     */   }
/*     */   private byte checksum(byte[] data, int start, int stop) {
/* 321 */     int sum = 0;
/* 322 */     for (int i = start; i <= stop; i++) {
/* 323 */       sum += data[i];
/*     */     }
/* 325 */     return (byte)(sum % 255);
/*     */   }
/*     */   
/*     */   private byte fold(byte a, byte b) {
/* 329 */     int hash = a + b;
/* 330 */     return (byte)hash;
/*     */   }
/*     */   
/*     */   public static String bytesToHex(byte[] data) {
/* 334 */     return bytesToHex(false, data, data.length);
/*     */   }
/*     */   
/*     */   public static String bytesToHex(boolean format, byte[] data) {
/* 338 */     return bytesToHex(format, data, data.length);
/*     */   }
/*     */   
/*     */   public static String bytesToHex(boolean format, byte[] data, int length) {
/* 342 */     StringBuffer buffer = new StringBuffer();
/* 343 */     for (int i = 0; i < length; i++) {
/* 344 */       buffer.append(toHex(data[i]));
/* 345 */       if (format && (i + 1) % 2 == 0 && i < length - 1) {
/* 346 */         buffer.append("-");
/*     */       }
/*     */     } 
/* 349 */     return buffer.toString().toUpperCase(Locale.ENGLISH);
/*     */   }
/*     */   
/*     */   public static byte[] toBytes(String hex) {
/* 353 */     byte[] bytes = new byte[hex.length() / 2];
/* 354 */     for (int i = 0; i < hex.length() / 2; i++) {
/* 355 */       bytes[i] = (byte)Integer.parseInt(hex.substring(i * 2, i * 2 + 2), 16);
/*     */     }
/* 357 */     return bytes;
/*     */   }
/*     */   
/*     */   public static String toHex(byte b) {
/* 361 */     Integer I = Integer.valueOf(b << 24 >>> 24);
/* 362 */     int i = I.intValue();
/* 363 */     if (i < 16) {
/* 364 */       return "0" + Integer.toString(i, 16);
/*     */     }
/* 366 */     return Integer.toString(i, 16);
/*     */   }
/*     */   
/*     */   private byte[] keyAES(String key) {
/* 370 */     byte[] buffer = null;
/*     */     try {
/* 372 */       buffer = ("##" + pad(key, 10) + "####").getBytes("US-ASCII");
/* 373 */     } catch (UnsupportedEncodingException e) {
/* 374 */       log.error("getBytes() failed", e);
/* 375 */       buffer = ("##" + pad(key, 10) + "####").getBytes();
/*     */     } 
/* 377 */     return buffer;
/*     */   }
/*     */   
/*     */   private byte[] encryptAES(byte[] key, byte[] payload) throws Exception {
/* 381 */     SecretKeySpec skeySpec = new SecretKeySpec(key, "AES");
/* 382 */     Cipher cipher = Cipher.getInstance("AES");
/* 383 */     cipher.init(1, skeySpec);
/* 384 */     byte[] encrypted = cipher.doFinal(payload);
/* 385 */     return encrypted;
/*     */   }
/*     */ 
/*     */   
/*     */   private byte[] decryptAES(byte[] key, byte[] encryption) throws Exception {
/* 390 */     SecretKeySpec skeySpec = new SecretKeySpec(key, "AES");
/* 391 */     Cipher cipher = Cipher.getInstance("AES");
/* 392 */     cipher.init(2, skeySpec);
/* 393 */     return cipher.doFinal(encryption);
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\registration\common\SoftwareKey.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */