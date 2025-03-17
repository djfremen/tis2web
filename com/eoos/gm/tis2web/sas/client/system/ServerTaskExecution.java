/*     */ package com.eoos.gm.tis2web.sas.client.system;
/*     */ 
/*     */ import com.eoos.datatype.ExceptionWrapper;
/*     */ import com.eoos.io.InputStreamCountWrapper;
/*     */ import com.eoos.io.ObservableInputStream;
/*     */ import com.eoos.io.ObservableOutputStream;
/*     */ import com.eoos.io.OutputStreamCountWrapper;
/*     */ import com.eoos.math.AverageCalculator;
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
/*  34 */   private static final Logger log = Logger.getLogger(ServerTaskExecution.class);
/*     */   
/*  36 */   private static ServerTaskExecution instance = null;
/*     */   
/*     */   private URL url;
/*     */   
/*     */   private String cookie;
/*     */   
/*     */   private ClientClassLoader ccl;
/*     */   
/*  44 */   private AverageCalculator transferSpeedCalculator = new AverageCalculator(3);
/*     */   
/*  46 */   private int notificationStep = 1024;
/*     */   
/*     */   protected ServerTaskExecution() {
/*  49 */     init();
/*     */   }
/*     */   
/*     */   protected void init() {
/*     */     try {
/*  54 */       String _url = System.getProperty("task.execution.url");
/*  55 */       this.url = new URL(_url);
/*  56 */       this.cookie = System.getProperty("cookie");
/*  57 */       this.cookie = new String(Base64EncodingUtil.decode(this.cookie), "utf-8");
/*  58 */       if (this.cookie == null || this.cookie.length() == 0) {
/*  59 */         this.cookie = "dummy";
/*     */       }
/*  61 */     } catch (Exception e) {
/*  62 */       throw new ExceptionWrapper(e);
/*     */     } 
/*  64 */     this.ccl = new ClientClassLoader();
/*     */     
/*  66 */     Runtime.getRuntime().addShutdownHook(new Thread() {
/*     */           public void run() {
/*     */             try {
/*  69 */               ServerTaskExecution.log.debug("********************* average transfer speed: " + ServerTaskExecution.this.transferSpeedCalculator.getCurrentAverage() + " kBytes/sec");
/*     */             } finally {}
/*     */           }
/*     */         });
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/*  77 */       CookieHandlerUninstall.uninstallCookieHandler();
/*  78 */     } catch (Throwable t) {
/*  79 */       log.debug("unable to uninstall cookie handler - exception:" + t);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static synchronized ServerTaskExecution getInstance() {
/*  85 */     if (instance == null) {
/*  86 */       instance = new ServerTaskExecution();
/*     */     }
/*  88 */     return instance;
/*     */   }
/*     */   
/*     */   public Object execute(Task task, ObservableInputStream.Observer inputObserver, ObservableOutputStream.Observer outputObserver) {
/*  92 */     return execute(task, inputObserver, outputObserver, 3);
/*     */   }
/*     */   
/*     */   public Object execute(Task task, ObservableInputStream.Observer inputObserver, ObservableOutputStream.Observer outputObserver, int retry) {
/*     */     try {
/*     */       ObservableInputStream observableInputStream;
/*  98 */       log.debug("sending task: " + String.valueOf(task) + " to server (url:" + this.url + ")");
/*  99 */       long tsStart = System.currentTimeMillis();
/* 100 */       HttpURLConnection connection = (HttpURLConnection)this.url.openConnection();
/* 101 */       connection.setRequestMethod("POST");
/* 102 */       connection.setRequestProperty("Cookie", this.cookie);
/* 103 */       connection.setDoOutput(true);
/* 104 */       connection.setDoInput(true);
/*     */       
/* 106 */       OutputStream os = connection.getOutputStream();
/* 107 */       OutputStreamCountWrapper os_cw = new OutputStreamCountWrapper(os);
/* 108 */       OutputStreamCountWrapper outputStreamCountWrapper1 = os_cw;
/* 109 */       GZIPOutputStream gZIPOutputStream = new GZIPOutputStream((OutputStream)outputStreamCountWrapper1);
/* 110 */       ObservableOutputStream obos = new ObservableOutputStream(gZIPOutputStream, this.notificationStep);
/* 111 */       if (outputObserver != null) {
/* 112 */         obos.addObserver(outputObserver);
/*     */       }
/* 114 */       ObservableOutputStream observableOutputStream1 = obos;
/*     */       
/* 116 */       ObjectOutputStream oos = new ObjectOutputStream((OutputStream)observableOutputStream1);
/* 117 */       oos.writeObject(task);
/* 118 */       oos.close();
/*     */       
/* 120 */       int responseCode = connection.getResponseCode();
/* 121 */       if (responseCode != 200) {
/* 122 */         log.error("received http response code " + responseCode + ", throwing exception");
/* 123 */         throw new IOException("communication error (http response code: " + responseCode + ")");
/*     */       } 
/*     */       
/* 126 */       InputStream is = connection.getInputStream();
/* 127 */       InputStreamCountWrapper is_cw = new InputStreamCountWrapper(is);
/* 128 */       InputStreamCountWrapper inputStreamCountWrapper1 = is_cw;
/* 129 */       GZIPInputStream gZIPInputStream = new GZIPInputStream((InputStream)inputStreamCountWrapper1);
/* 130 */       if (inputObserver != null) {
/* 131 */         ObservableInputStream obis = new ObservableInputStream(gZIPInputStream, this.notificationStep);
/* 132 */         obis.addObserver(inputObserver);
/* 133 */         observableInputStream = obis;
/*     */       } 
/*     */       
/*     */       try {
/* 137 */         ObjectInputStream ois = new ObjectInputStream((InputStream)observableInputStream) {
/*     */             protected Class resolveClass(ObjectStreamClass osc) throws ClassNotFoundException, IOException {
/*     */               try {
/* 140 */                 return Class.forName(osc.getName(), true, ServerTaskExecution.this.ccl);
/* 141 */               } catch (ClassNotFoundException e) {
/*     */                 
/* 143 */                 return super.resolveClass(osc);
/*     */               } 
/*     */             }
/*     */           };
/* 147 */         Object retValue = ois.readObject();
/* 148 */         log.debug("received and deserialized response object");
/* 149 */         ois.close();
/* 150 */         long tsEnd = System.currentTimeMillis();
/*     */         
/* 152 */         if (log.isDebugEnabled()) {
/*     */           
/*     */           try {
/* 155 */             BigInteger byteCount = os_cw.getCount().add(is_cw.getCount());
/* 156 */             BigDecimal speed = calculateTransferSpeed(tsStart, tsEnd, byteCount);
/* 157 */             this.transferSpeedCalculator.add(speed);
/* 158 */           } catch (Throwable t) {
/*     */           
/*     */           } finally {}
/*     */         }
/*     */         
/* 163 */         return retValue;
/* 164 */       } catch (StreamCorruptedException e) {
/* 165 */         throw e;
/*     */       }
/*     */     
/* 168 */     } catch (IOException e) {
/* 169 */       log.warn("unable to communicate with server - exception: " + e);
/* 170 */       if (retry > 0) {
/* 171 */         log.debug("******** RETRYING (retry counter:" + retry + ") ******");
/* 172 */         return execute(task, inputObserver, outputObserver, retry - 1);
/*     */       } 
/* 174 */       throw new ExceptionWrapper(e);
/*     */     }
/* 176 */     catch (Exception e) {
/* 177 */       throw new ExceptionWrapper(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private BigDecimal calculateTransferSpeed(long startTime, long endTime, BigInteger byteCount) {
/* 183 */     BigInteger duration = BigInteger.valueOf(endTime - startTime);
/* 184 */     BigDecimal speed = (new BigDecimal(byteCount, 3)).divide(new BigDecimal(duration, 3), 4);
/*     */ 
/*     */     
/* 187 */     speed = speed.multiply(BigDecimal.valueOf(1000L));
/* 188 */     speed = speed.divide(BigDecimal.valueOf(1024L), 4);
/* 189 */     return speed;
/*     */   }
/*     */ 
/*     */   
/*     */   public Object execute(Task task, ObservableInputStream.Observer inputObserver) {
/* 194 */     return execute(task, inputObserver, null);
/*     */   }
/*     */   
/*     */   public Object execute(Task task) {
/* 198 */     return execute(task, null, null);
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sas\client\system\ServerTaskExecution.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */