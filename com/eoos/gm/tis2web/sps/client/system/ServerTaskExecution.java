/*     */ package com.eoos.gm.tis2web.sps.client.system;
/*     */ 
/*     */ import com.eoos.datatype.ExceptionWrapper;
/*     */ import com.eoos.gm.tis2web.sps.client.common.ClientAppContextProvider;
/*     */ import com.eoos.gm.tis2web.sps.client.system.classloader.ClientClassLoader;
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
/*  55 */   private int requestCount = 0;
/*     */ 
/*     */   
/*     */   private ServerTaskExecution() {
/*     */     try {
/*  60 */       String _url = System.getProperty("task.execution.url");
/*  61 */       if (_url == null)
/*     */         return; 
/*  63 */       this.url = new URL(_url);
/*  64 */       this.cookie = System.getProperty("cookie");
/*  65 */       this.cookie = new String(Base64EncodingUtil.decode(this.cookie), "utf-8");
/*  66 */       if (this.cookie == null || this.cookie.length() == 0) {
/*  67 */         this.cookie = "dummy";
/*     */       }
/*  69 */     } catch (Exception e) {
/*  70 */       throw new ExceptionWrapper(e);
/*     */     } 
/*  72 */     this.ccl = new ClientClassLoader(getClass().getClassLoader());
/*     */ 
/*     */     
/*     */     try {
/*  76 */       String _step = ClientAppContextProvider.getClientAppContext().getClientSettings().getProperty("transfer.observer.notification.step");
/*  77 */       this.notificationStep = Integer.parseInt(_step);
/*  78 */       log.debug("notification step set to: " + this.notificationStep);
/*  79 */     } catch (Throwable t) {
/*  80 */       log.warn("unable to load setting for notification step, using step 1");
/*     */     } 
/*     */     
/*  83 */     Runtime.getRuntime().addShutdownHook(new Thread() {
/*     */           public void run() {
/*     */             try {
/*  86 */               BigInteger total = ServerTaskExecution.this.byteCountInput.add(ServerTaskExecution.this.byteCountOutput);
/*  87 */               BigInteger total_uc = ServerTaskExecution.this.byteCountInputUncompressed.add(ServerTaskExecution.this.byteCountOutputUncompressed);
/*  88 */               ServerTaskExecution.log.debug("rc: " + ServerTaskExecution.this.requestCount);
/*  89 */               ServerTaskExecution.log.debug("total transfer volume:" + ByteUtil.toString(total.longValue()) + " (uncompressed:" + ByteUtil.toString(total_uc.longValue()) + ")");
/*  90 */               ServerTaskExecution.log.debug("............incoming:" + ByteUtil.toString(ServerTaskExecution.this.byteCountInput.longValue()) + " (uncompressed:" + ByteUtil.toString(ServerTaskExecution.this.byteCountInputUncompressed.longValue()) + ")");
/*  91 */               ServerTaskExecution.log.debug("............outgoing:" + ByteUtil.toString(ServerTaskExecution.this.byteCountOutput.longValue()) + " y(uncompressed:" + ByteUtil.toString(ServerTaskExecution.this.byteCountOutputUncompressed.longValue()) + ")");
/*     */             } finally {}
/*     */           }
/*     */         });
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/*  99 */       CookieHandlerUninstall.uninstallCookieHandler();
/* 100 */     } catch (Throwable t) {
/* 101 */       log.debug("unable to uninstall cookie handler - exception:" + t);
/*     */     } 
/*     */   }
/*     */   
/*     */   public static synchronized ServerTaskExecution getInstance() {
/* 106 */     if (instance == null) {
/* 107 */       instance = new ServerTaskExecution();
/*     */     }
/* 109 */     return instance;
/*     */   } public Object execute(Task task, ObservableInputStream.Observer inputObserver, ObservableOutputStream.Observer outputObserver) {
/*     */     try {
/*     */       ObservableOutputStream observableOutputStream;
/*     */       ObservableInputStream observableInputStream;
/* 114 */       log.debug("sending task: " + String.valueOf(task) + " to server (url:" + this.url + ")");
/* 115 */       long tsStart = System.currentTimeMillis();
/* 116 */       HttpURLConnection connection = (HttpURLConnection)this.url.openConnection();
/* 117 */       connection.setRequestMethod("POST");
/* 118 */       connection.setRequestProperty("Cookie", this.cookie);
/* 119 */       log.debug("set http request header \"Cookie\" to " + String.valueOf(this.cookie));
/* 120 */       connection.setDoOutput(true);
/* 121 */       connection.setDoInput(true);
/*     */       
/* 123 */       OutputStream os = connection.getOutputStream();
/* 124 */       OutputStreamCountWrapper os_cw = new OutputStreamCountWrapper(os);
/* 125 */       OutputStreamCountWrapper outputStreamCountWrapper2 = os_cw;
/* 126 */       GZIPOutputStream os_gz = new GZIPOutputStream((OutputStream)outputStreamCountWrapper2);
/* 127 */       GZIPOutputStream gZIPOutputStream1 = os_gz;
/* 128 */       OutputStreamCountWrapper os_cw_uncompressed = new OutputStreamCountWrapper(gZIPOutputStream1);
/* 129 */       OutputStreamCountWrapper outputStreamCountWrapper1 = os_cw_uncompressed;
/* 130 */       if (outputObserver != null) {
/* 131 */         ObservableOutputStream obos = new ObservableOutputStream((OutputStream)outputStreamCountWrapper1, this.notificationStep);
/* 132 */         obos.addObserver(outputObserver);
/* 133 */         observableOutputStream = obos;
/*     */       } 
/*     */       
/* 136 */       ObjectOutputStream oos = new ObjectOutputStream((OutputStream)observableOutputStream);
/* 137 */       oos.writeObject(task);
/* 138 */       oos.close();
/*     */       
/* 140 */       this.requestCount++;
/*     */ 
/*     */       
/* 143 */       incByteCountOutput(os_cw.getCount());
/* 144 */       incByteCountOutput_UC(os_cw_uncompressed.getCount());
/*     */       
/* 146 */       if (log.isDebugEnabled()) {
/* 147 */         String key = null;
/* 148 */         int i = 0;
/* 149 */         while ((key = connection.getHeaderFieldKey(i)) != null) {
/* 150 */           log.debug("received header :" + key + "->" + connection.getHeaderField(i));
/* 151 */           i++;
/*     */         } 
/*     */       } 
/*     */       
/* 155 */       int responseCode = connection.getResponseCode();
/* 156 */       if (responseCode != 200) {
/* 157 */         log.error("received http response code " + responseCode + ", throwing exception");
/* 158 */         throw new IOException("communication error (http response code: " + responseCode + ")");
/*     */       } 
/*     */       
/* 161 */       InputStream is = connection.getInputStream();
/* 162 */       InputStreamCountWrapper is_cw = new InputStreamCountWrapper(is);
/* 163 */       InputStreamCountWrapper inputStreamCountWrapper2 = is_cw;
/* 164 */       GZIPInputStream is_gz = new GZIPInputStream((InputStream)inputStreamCountWrapper2);
/* 165 */       GZIPInputStream gZIPInputStream1 = is_gz;
/* 166 */       InputStreamCountWrapper is_cw_uncompressed = new InputStreamCountWrapper(gZIPInputStream1);
/* 167 */       InputStreamCountWrapper inputStreamCountWrapper1 = is_cw_uncompressed;
/* 168 */       if (inputObserver != null) {
/* 169 */         ObservableInputStream obis = new ObservableInputStream((InputStream)inputStreamCountWrapper1, this.notificationStep);
/* 170 */         obis.addObserver(inputObserver);
/* 171 */         observableInputStream = obis;
/*     */       } 
/*     */       
/* 174 */       ObjectInputStream ois = new ObjectInputStream((InputStream)observableInputStream) {
/*     */           protected Class resolveClass(ObjectStreamClass osc) throws ClassNotFoundException, IOException {
/*     */             try {
/* 177 */               return Class.forName(osc.getName(), true, (ClassLoader)ServerTaskExecution.this.ccl);
/* 178 */             } catch (ClassNotFoundException e) {
/*     */               
/* 180 */               return super.resolveClass(osc);
/*     */             } 
/*     */           }
/*     */         };
/* 184 */       Object retValue = ois.readObject();
/* 185 */       log.debug("received and deserialized response object");
/* 186 */       ois.close();
/* 187 */       long tsEnd = System.currentTimeMillis();
/*     */ 
/*     */       
/* 190 */       incByteCountInput(is_cw.getCount());
/* 191 */       incByteCountInput_UC(is_cw_uncompressed.getCount());
/* 192 */       if (log.isDebugEnabled()) {
/*     */         
/*     */         try {
/* 195 */           long totalTransfer = os_cw.getCount().add(is_cw.getCount()).longValue();
/* 196 */           long duration = tsEnd - tsStart;
/* 197 */           log.debug("transfered " + ByteUtil.toString(totalTransfer) + " in " + duration + " ms");
/* 198 */           BigDecimal ratio = BigDecimal.valueOf(totalTransfer).setScale(3).divide(BigDecimal.valueOf(duration), 4);
/* 199 */           log.debug("... transfer speed :" + ratio.toString() + " kBytes/sec");
/* 200 */         } catch (Throwable t) {
/*     */         
/*     */         } finally {}
/*     */       }
/*     */       
/* 205 */       return retValue;
/*     */     }
/* 207 */     catch (Exception e) {
/* 208 */       CommunicationException ce = new CommunicationException(e, System.getProperty("server.name"));
/* 209 */       log.error("unable to communicate with server, throwing communication exception: " + ce, ce);
/* 210 */       throw ce;
/*     */     } 
/*     */   }
/*     */   
/*     */   private synchronized void incByteCountInput(BigInteger count) {
/* 215 */     this.byteCountInput = this.byteCountInput.add(count);
/*     */   }
/*     */   
/*     */   private synchronized void incByteCountOutput(BigInteger count) {
/* 219 */     this.byteCountOutput = this.byteCountOutput.add(count);
/*     */   }
/*     */   
/*     */   private synchronized void incByteCountInput_UC(BigInteger count) {
/* 223 */     this.byteCountInputUncompressed = this.byteCountInputUncompressed.add(count);
/*     */   }
/*     */   
/*     */   private synchronized void incByteCountOutput_UC(BigInteger count) {
/* 227 */     this.byteCountOutputUncompressed = this.byteCountOutputUncompressed.add(count);
/*     */   }
/*     */   
/*     */   public Object execute(Task task, ObservableInputStream.Observer inputObserver) {
/* 231 */     return execute(task, inputObserver, null);
/*     */   }
/*     */   
/*     */   public Object execute(Task task) {
/* 235 */     return execute(task, null, null);
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\client\system\ServerTaskExecution.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */