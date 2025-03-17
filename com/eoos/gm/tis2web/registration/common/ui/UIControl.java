/*    */ package com.eoos.gm.tis2web.registration.common.ui;
/*    */ 
/*    */ import com.eoos.gm.tis2web.frame.export.common.ApplicationContext;
/*    */ import java.util.Iterator;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class UIControl
/*    */ {
/*    */   public static final String INITIAL_REGISTRATION = "initial_registration";
/*    */   public static final String HARDWARE_KEY_MIGRATION = "hardware_key_migration";
/*    */   public static final String EXTEND_AUTHORIZATION = "extend_authorization";
/*    */   public static final String SERVER_REGISTRATION = "server_registration";
/*    */   public static final String SERVER_MIGRATION = "server_migration";
/*    */   public static final String SERVER_EXTENSION = "server_extension";
/*    */   public static final String ADMIN_DETAIL = "admin_detail";
/*    */   public static final int HIDE = 0;
/*    */   public static final int DISPLAY = 1;
/*    */   public static final int EDIT = 2;
/*    */   private String screen;
/*    */   private static final String PREFIX = "frame.registration.standalone.";
/*    */   
/*    */   public UIControl(String screen) {
/* 25 */     this.screen = screen;
/*    */   }
/*    */   
/*    */   public String getInformationMessageKey() {
/* 29 */     return "admin_detail".equals(this.screen) ? null : ("registration.message." + this.screen);
/*    */   }
/*    */   
/*    */   public int getSoftwareKeyComponentMode() {
/* 33 */     if ("initial_registration".equals(this.screen) || "hardware_key_migration".equals(this.screen) || "extend_authorization".equals(this.screen))
/* 34 */       return 1; 
/* 35 */     if ("server_registration".equals(this.screen) || "server_migration".equals(this.screen) || "server_extension".equals(this.screen))
/* 36 */       return 2; 
/* 37 */     if ("admin_detail".equals(this.screen)) {
/* 38 */       return 2;
/*    */     }
/* 40 */     return 0;
/*    */   }
/*    */   
/*    */   public int getLicenceKeyComponentMode() {
/* 44 */     if ("initial_registration".equals(this.screen) || "hardware_key_migration".equals(this.screen) || "extend_authorization".equals(this.screen))
/* 45 */       return 2; 
/* 46 */     if ("admin_detail".equals(this.screen)) {
/* 47 */       return 1;
/*    */     }
/* 49 */     return 0;
/*    */   }
/*    */   
/*    */   public int getSubscriberComponentMode() {
/* 53 */     if ("initial_registration".equals(this.screen) || "server_registration".equals(this.screen) || "server_migration".equals(this.screen) || "server_extension".equals(this.screen))
/* 54 */       return 2; 
/* 55 */     if ("hardware_key_migration".equals(this.screen) || "extend_authorization".equals(this.screen))
/* 56 */       return 1; 
/* 57 */     if ("admin_detail".equals(this.screen)) {
/* 58 */       return 2;
/*    */     }
/* 60 */     return 0;
/*    */   }
/*    */   
/*    */   public int getSubscriptionComponentMode() {
/* 64 */     if ("initial_registration".equals(this.screen) || "hardware_key_migration".equals(this.screen) || "extend_authorization".equals(this.screen)) {
/* 65 */       return 1;
/*    */     }
/* 67 */     return 0;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getSubscriptionControlMode() {
/* 72 */     if ("extend_authorization".equals(this.screen) || "server_extension".equals(this.screen) || "admin_detail".equals(this.screen)) {
/* 73 */       return 2;
/*    */     }
/* 75 */     return 0;
/*    */   }
/*    */   
/*    */   public boolean isFirstEntrySubscriptionControlFixed() {
/* 79 */     return (getSubscriptionControlMode() == 2 && !"extend_authorization".equals(this.screen));
/*    */   }
/*    */   
/*    */   public boolean isAdminDetailScreen() {
/* 83 */     return "admin_detail".equals(this.screen);
/*    */   }
/*    */   
/*    */   public static boolean checkMode(String screen) {
/*    */     try {
/* 88 */       ApplicationContext context = ApplicationContext.getInstance();
/* 89 */       for (Iterator<String> iter = ApplicationContext.getInstance().getKeys().iterator(); iter.hasNext(); ) {
/* 90 */         String key = iter.next();
/* 91 */         if (key.equals("frame.registration.standalone.softwarekey") && 
/* 92 */           context.getProperty("frame.registration.standalone.softwarekey").equalsIgnoreCase("false")) {
/* 93 */           return false;
/*    */         }
/*    */       }
/*    */     
/* 97 */     } catch (Exception e) {}
/*    */     
/* 99 */     return true;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\registration\commo\\ui\UIControl.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */