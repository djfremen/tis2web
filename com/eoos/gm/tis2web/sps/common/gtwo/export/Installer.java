/*    */ package com.eoos.gm.tis2web.sps.common.gtwo.export;
/*    */ 
/*    */ import com.eoos.util.MultitonSupport;
/*    */ import java.io.ObjectStreamException;
/*    */ import java.io.Serializable;
/*    */ import java.util.Locale;
/*    */ 
/*    */ public interface Installer
/*    */   extends Serializable {
/*    */   public static class Type implements Serializable {
/*    */     private static final long serialVersionUID = 1L;
/*    */     
/* 13 */     private static MultitonSupport multitonSupport = new MultitonSupport(new MultitonSupport.CreationCallback() {
/*    */           public Object createObject(Object identifier) {
/* 15 */             return new Installer.Type((String)identifier);
/*    */           }
/*    */         });
/*    */     
/*    */     private String key;
/*    */     
/*    */     private Type(String key) {
/* 22 */       this.key = key;
/*    */     }
/*    */     
/*    */     public static synchronized Type getInstance(String key) {
/* 26 */       return (Type)multitonSupport.getInstance(key.toLowerCase(Locale.ENGLISH));
/*    */     }
/*    */     
/*    */     private Object readResolve() throws ObjectStreamException {
/* 30 */       return getInstance(this.key);
/*    */     }
/*    */   }
/*    */   
/*    */   public static class InstallStatus
/*    */     implements Serializable {
/*    */     private static final long serialVersionUID = 1L;
/*    */     
/* 38 */     private static MultitonSupport multitonSupport = new MultitonSupport(new MultitonSupport.CreationCallback() {
/*    */           public Object createObject(Object identifier) {
/* 40 */             return new Installer.Type((String)identifier);
/*    */           }
/*    */         });
/*    */     
/*    */     private String key;
/*    */     
/*    */     private InstallStatus(String key) {
/* 47 */       this.key = key;
/*    */     }
/*    */     
/*    */     public static synchronized InstallStatus getInstance(String key) {
/* 51 */       return (InstallStatus)multitonSupport.getInstance(key.toLowerCase(Locale.ENGLISH));
/*    */     }
/*    */     
/*    */     private Object readResolve() throws ObjectStreamException {
/* 55 */       return getInstance(this.key);
/*    */     }
/*    */   }
/*    */ 
/*    */   
/* 60 */   public static final InstallStatus STATUS_SUCCESS = new InstallStatus("success");
/* 61 */   public static final InstallStatus STATUS_FAILURE = new InstallStatus("failure");
/* 62 */   public static final InstallStatus STATUS_UNKNOWN = new InstallStatus("unknown");
/*    */   
/* 64 */   public static final Type TYPE_EXE = Type.getInstance("exe");
/*    */   
/* 66 */   public static final Type TYPE_MSI = Type.getInstance("msi");
/*    */   
/*    */   Type getType();
/*    */   
/*    */   byte[] getData() throws Exception;
/*    */   
/*    */   String getCmdLineParams();
/*    */   
/*    */   String getFilename();
/*    */   
/*    */   byte[] getChecksum();
/*    */   
/*    */   InstallStatus getInstallStatus(Integer paramInteger);
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\common\gtwo\export\Installer.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */