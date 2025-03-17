/*     */ package com.eoos.gm.tis2web.snapshot.client.system;
/*     */ 
/*     */ import com.eoos.datatype.ExceptionWrapper;
/*     */ import com.eoos.gm.tis2web.snapshot.client.system.classloader.ClientClassLoader;
/*     */ import com.eoos.io.InputStreamCountWrapper;
/*     */ import com.eoos.io.ObservableInputStream;
/*     */ import com.eoos.io.ObservableOutputStream;
/*     */ import com.eoos.io.OutputStreamCountWrapper;
/*     */ import com.eoos.util.ByteUtil;
/*     */ import com.eoos.util.CookieHandlerUninstall;
/*     */ import com.eoos.util.Task;
/*     */ import com.eoos.util.v2.Base64EncodingUtil;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.ObjectOutputStream;
/*     */ import java.io.ObjectStreamClass;
/*     */ import java.io.OutputStream;
/*     */ import java.io.StreamCorruptedException;
/*     */ import java.math.BigDecimal;
/*     */ import java.math.BigInteger;
/*     */ import java.net.HttpURLConnection;
/*     */ import java.net.URL;
/*     */ import java.util.zip.GZIPInputStream;
/*     */ import java.util.zip.GZIPOutputStream;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ServerTaskExecution
/*     */ {
/*  35 */   private static final Logger log = Logger.getLogger(ServerTaskExecution.class);
/*     */   
/*  37 */   private static ServerTaskExecution instance = null;
/*     */   
/*     */   private URL url;
/*     */   
/*     */   private String cookie;
/*     */   
/*     */   private ClientClassLoader ccl;
/*     */   
/*  45 */   private BigInteger byteCountInput = BigInteger.ZERO;
/*     */   
/*  47 */   private BigInteger byteCountOutput = BigInteger.ZERO;
/*     */   
/*  49 */   private BigInteger byteCountInputUncompressed = BigInteger.ZERO;
/*     */   
/*  51 */   private BigInteger byteCountOutputUncompressed = BigInteger.ZERO;
/*     */   
/*  53 */   private int notificationStep = 1;
/*     */ 
/*     */   
/*     */   private ServerTaskExecution() {
/*     */     try {
/*  58 */       String _url = System.getProperty("task.execution.url");
/*  59 */       this.url = new URL(_url);
/*  60 */       this.cookie = System.getProperty("cookie");
/*  61 */       this.cookie = new String(Base64EncodingUtil.decode(this.cookie), "utf-8");
/*  62 */       if (this.cookie == null || this.cookie.length() == 0) {
/*  63 */         this.cookie = "dummy";
/*     */       }
/*  65 */     } catch (Exception e) {
/*  66 */       throw new ExceptionWrapper(e);
/*     */     } 
/*  68 */     this.ccl = new ClientClassLoader(getClass().getClassLoader());
/*     */ 
/*     */     
/*     */     try {
/*  72 */       String _step = "512";
/*  73 */       this.notificationStep = Integer.parseInt(_step);
/*  74 */       log.debug("notification step set to: " + this.notificationStep);
/*  75 */     } catch (Throwable t) {
/*  76 */       log.warn("unable to load setting for notification step, using step 1");
/*     */     } 
/*     */     
/*  79 */     Runtime.getRuntime().addShutdownHook(new Thread() {
/*     */           public void run() {
/*     */             try {
/*  82 */               BigInteger total = ServerTaskExecution.this.byteCountInput.add(ServerTaskExecution.this.byteCountOutput);
/*  83 */               BigInteger total_uc = ServerTaskExecution.this.byteCountInputUncompressed.add(ServerTaskExecution.this.byteCountOutputUncompressed);
/*  84 */               ServerTaskExecution.log.debug("total transfer volume:" + ByteUtil.toString(total.longValue()) + " (uncompressed:" + ByteUtil.toString(total_uc.longValue()) + ")");
/*  85 */               ServerTaskExecution.log.debug("............incoming:" + ByteUtil.toString(ServerTaskExecution.this.byteCountInput.longValue()) + " (uncompressed:" + ByteUtil.toString(ServerTaskExecution.this.byteCountInputUncompressed.longValue()) + ")");
/*  86 */               ServerTaskExecution.log.debug("............outgoing:" + ByteUtil.toString(ServerTaskExecution.this.byteCountOutput.longValue()) + " y(uncompressed:" + ByteUtil.toString(ServerTaskExecution.this.byteCountOutputUncompressed.longValue()) + ")");
/*     */             } finally {}
/*     */           }
/*     */         });
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/*  94 */       CookieHandlerUninstall.uninstallCookieHandler();
/*  95 */     } catch (Throwable t) {
/*  96 */       log.debug("unable to uninstall cookie handler - exception:" + t);
/*     */     } 
/*     */   }
/*     */   
/*     */   public static synchronized ServerTaskExecution getInstance() {
/* 101 */     if (instance == null) {
/* 102 */       instance = new ServerTaskExecution();
/*     */     }
/* 104 */     return instance;
/*     */   }
/*     */   
/*     */   public Object execute(Task task, ObservableInputStream.Observer inputObserver, ObservableOutputStream.Observer outputObserver) {
/* 108 */     return execute(task, inputObserver, outputObserver, 0);
/*     */   } public Object execute(Task task, ObservableInputStream.Observer inputObserver, ObservableOutputStream.Observer outputObserver, int retry) {
/*     */     try {
/*     */       ObservableOutputStream observableOutputStream;
/*     */       ObservableInputStream observableInputStream;
/* 113 */       log.debug("sending task: " + String.valueOf(task) + " to server (url:" + this.url + ")");
/* 114 */       long tsStart = System.currentTimeMillis();
/* 115 */       HttpURLConnection connection = (HttpURLConnection)this.url.openConnection();
/* 116 */       connection.setRequestMethod("POST");
/* 117 */       connection.setRequestProperty("Cookie", this.cookie);
/* 118 */       log.debug("set http request header \"Cookie\" to " + String.valueOf(this.cookie));
/* 119 */       connection.setDoOutput(true);
/* 120 */       connection.setDoInput(true);
/*     */       
/* 122 */       OutputStream os = connection.getOutputStream();
/* 123 */       OutputStreamCountWrapper os_cw = new OutputStreamCountWrapper(os);
/* 124 */       OutputStreamCountWrapper outputStreamCountWrapper2 = os_cw;
/* 125 */       GZIPOutputStream os_gz = new GZIPOutputStream((OutputStream)outputStreamCountWrapper2);
/* 126 */       GZIPOutputStream gZIPOutputStream1 = os_gz;
/* 127 */       OutputStreamCountWrapper os_cw_uncompressed = new OutputStreamCountWrapper(gZIPOutputStream1);
/* 128 */       OutputStreamCountWrapper outputStreamCountWrapper1 = os_cw_uncompressed;
/* 129 */       if (outputObserver != null) {
/* 130 */         ObservableOutputStream obos = new ObservableOutputStream((OutputStream)outputStreamCountWrapper1, this.notificationStep);
/* 131 */         obos.addObserver(outputObserver);
/* 132 */         observableOutputStream = obos;
/*     */       } 
/*     */       
/* 135 */       ObjectOutputStream oos = new ObjectOutputStream((OutputStream)observableOutputStream);
/* 136 */       oos.writeObject(task);
/* 137 */       oos.close();
/*     */ 
/*     */       
/* 140 */       incByteCountOutput(os_cw.getCount());
/* 141 */       incByteCountOutput_UC(os_cw_uncompressed.getCount());
/*     */       
/* 143 */       if (log.isDebugEnabled()) {
/* 144 */         String key = null;
/* 145 */         int i = 0;
/* 146 */         while ((key = connection.getHeaderFieldKey(i)) != null) {
/* 147 */           log.debug("received header :" + key + "->" + connection.getHeaderField(i));
/* 148 */           i++;
/*     */         } 
/*     */       } 
/*     */       
/* 152 */       int responseCode = connection.getResponseCode();
/* 153 */       if (responseCode != 200) {
/* 154 */         log.error("received http response code " + responseCode + ", throwing exception");
/* 155 */         throw new IOException("communication error (http response code: " + responseCode + ")");
/*     */       } 
/*     */       
/* 158 */       InputStream is = connection.getInputStream();
/* 159 */       InputStreamCountWrapper is_cw = new InputStreamCountWrapper(is);
/* 160 */       InputStreamCountWrapper inputStreamCountWrapper2 = is_cw;
/* 161 */       GZIPInputStream is_gz = new GZIPInputStream((InputStream)inputStreamCountWrapper2);
/* 162 */       GZIPInputStream gZIPInputStream1 = is_gz;
/* 163 */       InputStreamCountWrapper is_cw_uncompressed = new InputStreamCountWrapper(gZIPInputStream1);
/* 164 */       InputStreamCountWrapper inputStreamCountWrapper1 = is_cw_uncompressed;
/* 165 */       if (inputObserver != null) {
/* 166 */         ObservableInputStream obis = new ObservableInputStream((InputStream)inputStreamCountWrapper1, this.notificationStep);
/* 167 */         obis.addObserver(inputObserver);
/* 168 */         observableInputStream = obis;
/*     */       } 
/*     */       
/*     */       try {
/* 172 */         ObjectInputStream ois = new ObjectInputStream((InputStream)observableInputStream) {
/*     */             protected Class resolveClass(ObjectStreamClass osc) throws ClassNotFoundException, IOException {
/*     */               try {
/* 175 */                 return Class.forName(osc.getName(), true, (ClassLoader)ServerTaskExecution.this.ccl);
/* 176 */               } catch (ClassNotFoundException e) {
/*     */                 
/* 178 */                 return super.resolveClass(osc);
/*     */               } 
/*     */             }
/*     */           };
/* 182 */         Object retValue = ois.readObject();
/* 183 */         log.debug("received and deserialized response object");
/* 184 */         ois.close();
/* 185 */         long tsEnd = System.currentTimeMillis();
/*     */ 
/*     */         
/* 188 */         incByteCountInput(is_cw.getCount());
/* 189 */         incByteCountInput_UC(is_cw_uncompressed.getCount());
/* 190 */         if (log.isDebugEnabled()) {
/*     */           
/*     */           try {
/* 193 */             long totalTransfer = os_cw.getCount().add(is_cw.getCount()).longValue();
/* 194 */             long duration = tsEnd - tsStart;
/* 195 */             log.debug("transfered " + ByteUtil.toString(totalTransfer) + " in " + duration + " ms");
/* 196 */             BigDecimal ratio = BigDecimal.valueOf(totalTransfer, 3).divide(BigDecimal.valueOf(duration, 3), 4);
/*     */             
/* 198 */             ratio = ratio.multiply(BigDecimal.valueOf(1000L));
/* 199 */             ratio = ratio.divide(BigDecimal.valueOf(1024L), 4);
/* 200 */             log.debug("... transfer speed :" + ratio.toString() + " kBytes/sec");
/* 201 */           } catch (Throwable t) {
/*     */           
/*     */           } finally {}
/*     */         }
/*     */         
/* 206 */         return retValue;
/* 207 */       } catch (StreamCorruptedException e) {
/* 208 */         throw e;
/*     */       }
/*     */     
/* 211 */     } catch (IOException e) {
/* 212 */       log.warn("unable to communicate with server - exception: " + e);
/* 213 */       if (retry > 0) {
/* 214 */         log.debug("******** RETRYING (retry counter:" + retry + ") ******");
/* 215 */         return execute(task, inputObserver, outputObserver, retry - 1);
/*     */       } 
/* 217 */       throw new ExceptionWrapper(e);
/*     */     }
/* 219 */     catch (Exception e) {
/* 220 */       throw new ExceptionWrapper(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private synchronized void incByteCountInput(BigInteger count) {
/* 226 */     this.byteCountInput = this.byteCountInput.add(count);
/*     */   }
/*     */   
/*     */   private synchronized void incByteCountOutput(BigInteger count) {
/* 230 */     this.byteCountOutput = this.byteCountOutput.add(count);
/*     */   }
/*     */   
/*     */   private synchronized void incByteCountInput_UC(BigInteger count) {
/* 234 */     this.byteCountInputUncompressed = this.byteCountInputUncompressed.add(count);
/*     */   }
/*     */   
/*     */   private synchronized void incByteCountOutput_UC(BigInteger count) {
/* 238 */     this.byteCountOutputUncompressed = this.byteCountOutputUncompressed.add(count);
/*     */   }
/*     */   
/*     */   public Object execute(Task task, ObservableInputStream.Observer inputObserver) {
/* 242 */     return execute(task, inputObserver, null);
/*     */   }
/*     */   
/*     */   public Object execute(Task task) {
/* 246 */     return execute(task, null, null);
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\snapshot\client\system\ServerTaskExecution.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */