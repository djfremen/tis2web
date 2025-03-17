/*    */ package com.eoos.gm.tis2web.registration.common.ui;
/*    */ 
/*    */ import com.eoos.scsm.v2.util.Util;
/*    */ import java.util.regex.Pattern;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class EmailAddressValidator
/*    */ {
/*    */   private static final String sp = "\\!\\#\\$\\%\\&\\'\\*\\+\\-\\/\\=\\?\\^\\_\\`\\{\\|\\}\\~";
/*    */   private static final String atext = "[a-zA-Z0-9\\!\\#\\$\\%\\&\\'\\*\\+\\-\\/\\=\\?\\^\\_\\`\\{\\|\\}\\~]";
/*    */   private static final String atom = "[a-zA-Z0-9\\!\\#\\$\\%\\&\\'\\*\\+\\-\\/\\=\\?\\^\\_\\`\\{\\|\\}\\~]+";
/*    */   private static final String dotAtom = "\\.[a-zA-Z0-9\\!\\#\\$\\%\\&\\'\\*\\+\\-\\/\\=\\?\\^\\_\\`\\{\\|\\}\\~]+";
/*    */   private static final String localPart = "[a-zA-Z0-9\\!\\#\\$\\%\\&\\'\\*\\+\\-\\/\\=\\?\\^\\_\\`\\{\\|\\}\\~]+(\\.[a-zA-Z0-9\\!\\#\\$\\%\\&\\'\\*\\+\\-\\/\\=\\?\\^\\_\\`\\{\\|\\}\\~]+)*";
/*    */   private static final String letter = "[a-zA-Z]";
/*    */   private static final String letDig = "[a-zA-Z0-9]";
/*    */   private static final String letDigHyp = "[a-zA-Z0-9-]";
/*    */   public static final String rfcLabel = "[a-zA-Z0-9][a-zA-Z0-9-]{0,61}[a-zA-Z0-9]";
/*    */   private static final String domain = "[a-zA-Z0-9][a-zA-Z0-9-]{0,61}[a-zA-Z0-9](\\.[a-zA-Z0-9][a-zA-Z0-9-]{0,61}[a-zA-Z0-9])*\\.[a-zA-Z]{2,6}";
/*    */   private static final String addrSpec = "^[a-zA-Z0-9\\!\\#\\$\\%\\&\\'\\*\\+\\-\\/\\=\\?\\^\\_\\`\\{\\|\\}\\~]+(\\.[a-zA-Z0-9\\!\\#\\$\\%\\&\\'\\*\\+\\-\\/\\=\\?\\^\\_\\`\\{\\|\\}\\~]+)*@[a-zA-Z0-9][a-zA-Z0-9-]{0,61}[a-zA-Z0-9](\\.[a-zA-Z0-9][a-zA-Z0-9-]{0,61}[a-zA-Z0-9])*\\.[a-zA-Z]{2,6}$";
/* 25 */   public static final Pattern VALID_PATTERN = Pattern.compile("^[a-zA-Z0-9\\!\\#\\$\\%\\&\\'\\*\\+\\-\\/\\=\\?\\^\\_\\`\\{\\|\\}\\~]+(\\.[a-zA-Z0-9\\!\\#\\$\\%\\&\\'\\*\\+\\-\\/\\=\\?\\^\\_\\`\\{\\|\\}\\~]+)*@[a-zA-Z0-9][a-zA-Z0-9-]{0,61}[a-zA-Z0-9](\\.[a-zA-Z0-9][a-zA-Z0-9-]{0,61}[a-zA-Z0-9])*\\.[a-zA-Z]{2,6}$");
/*    */   
/*    */   public static boolean isValid(String emailString) {
/* 28 */     return !Util.isNullOrEmpty(emailString);
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\registration\commo\\ui\EmailAddressValidator.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */