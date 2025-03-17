/*     */ package com.eoos.gm.tis2web.frame.hwk;
/*     */ 
/*     */ import com.eoos.gm.tis2web.frame.export.common.ApplicationContext;
/*     */ import com.eoos.gm.tis2web.frame.export.common.util.DatabaseLink;
/*     */ import com.eoos.gm.tis2web.frame.export.common.util.IDatabaseLink;
/*     */ import com.eoos.propcfg.Configuration;
/*     */ import com.eoos.propcfg.SubConfigurationWrapper;
/*     */ import com.eoos.scsm.v2.condition.Condition;
/*     */ import com.eoos.scsm.v2.util.AssertUtil;
/*     */ import java.sql.Connection;
/*     */ import java.sql.PreparedStatement;
/*     */ import java.sql.ResultSet;
/*     */ import java.sql.SQLException;
/*     */ import java.text.DateFormat;
/*     */ import java.text.DecimalFormat;
/*     */ import java.text.NumberFormat;
/*     */ import java.text.SimpleDateFormat;
/*     */ import java.util.Date;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class HWKReplacementProvider
/*     */ {
/*  27 */   private static final Logger log = Logger.getLogger(HWKReplacementProvider.class);
/*     */   
/*     */   private IDatabaseLink dblink;
/*     */   
/*  31 */   private static HWKReplacementProvider instance = null;
/*     */   
/*     */   private HWKReplacementProvider() {
/*  34 */     SubConfigurationWrapper subConfigurationWrapper = new SubConfigurationWrapper((Configuration)ApplicationContext.getInstance(), "frame.adapter.hwkreplacement.db.");
/*     */     
/*  36 */     this.dblink = DatabaseLink.openDatabase((Configuration)subConfigurationWrapper);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private HWKReplacementProvider(IDatabaseLink dbLink) {
/*  44 */     this.dblink = dbLink;
/*     */   }
/*     */   
/*     */   public static synchronized HWKReplacementProvider getInstance() {
/*  48 */     if (instance == null) {
/*  49 */       instance = new HWKReplacementProvider();
/*     */     }
/*  51 */     return instance;
/*     */   }
/*     */   
/*  54 */   private static final Condition ASSERTCONDITION_ID = (Condition)new AssertUtil.ExtCondition()
/*     */     {
/*     */       public boolean check(Object obj) {
/*  57 */         String id = (String)obj;
/*  58 */         boolean ret = (id != null);
/*  59 */         ret = (ret && id.equals(id.trim()));
/*  60 */         ret = (ret && id.length() == 10);
/*  61 */         ret = (ret && Character.isLetter(id.charAt(0)));
/*  62 */         for (int i = 1; i < 10 && ret; i++) {
/*  63 */           ret = (ret && Character.isDigit(id.charAt(i)));
/*     */         }
/*  65 */         return ret;
/*     */       }
/*     */       
/*     */       public String getExplanation(Object testedObject) {
/*  69 */         return "invalid ID <" + String.valueOf(testedObject) + "> (expected format: <[letter]([digit]){9}>";
/*     */       }
/*     */     };
/*     */ 
/*     */   
/*  74 */   private static NumberFormat COUNT_FORMATTER = new DecimalFormat("000000000");
/*     */   
/*     */   private static String getNextID(String previous) {
/*  77 */     StringBuffer tmp = new StringBuffer();
/*  78 */     if (previous != null) {
/*  79 */       AssertUtil.ensure(previous, ASSERTCONDITION_ID);
/*  80 */       tmp.append(previous.charAt(0));
/*  81 */       int count = Integer.parseInt(previous.substring(1));
/*  82 */       tmp.append(COUNT_FORMATTER.format(++count));
/*     */     } else {
/*  84 */       tmp.append('O');
/*  85 */       tmp.append(COUNT_FORMATTER.format(0L));
/*     */     } 
/*  87 */     String ret = tmp.toString();
/*  88 */     AssertUtil.ensure(ret, ASSERTCONDITION_ID);
/*  89 */     return ret;
/*     */   }
/*     */ 
/*     */   
/*     */   public void resetID(String id) throws Exception {
/*  94 */     AssertUtil.ensure(id, ASSERTCONDITION_ID);
/*  95 */     Connection connection = this.dblink.requestConnection();
/*     */     try {
/*  97 */       connection.setReadOnly(false);
/*  98 */       PreparedStatement stmt = connection.prepareStatement("update keygen_HWKReplacement set current_key=? ");
/*     */       try {
/* 100 */         stmt.setString(1, id);
/* 101 */         if (stmt.executeUpdate() != 1) {
/* 102 */           throw new IllegalStateException();
/*     */         }
/* 104 */         connection.commit();
/* 105 */       } catch (SQLException e) {
/*     */         try {
/* 107 */           connection.rollback();
/* 108 */         } catch (SQLException e1) {}
/*     */       } finally {
/*     */         
/* 111 */         stmt.close();
/*     */       } 
/*     */     } finally {
/*     */       
/* 115 */       this.dblink.releaseConnection(connection);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private String getID() {
/* 121 */     String ret = null;
/*     */     try {
/* 123 */       Connection connection = this.dblink.requestConnection();
/*     */       
/*     */       try {
/* 126 */         int maxCount = 10; do {
/*     */           String currentID;
/* 128 */           PreparedStatement stmt = connection.prepareStatement("select current_key from keygen_HWKReplacement ");
/*     */           try {
/* 130 */             ResultSet rs = stmt.executeQuery();
/*     */             try {
/* 132 */               if (!rs.next()) {
/* 133 */                 currentID = null;
/*     */               } else {
/* 135 */                 currentID = rs.getString(1);
/*     */               } 
/*     */             } finally {
/* 138 */               rs.close();
/*     */             } 
/*     */           } finally {
/* 141 */             stmt.close();
/*     */           } 
/* 143 */           ret = getNextID(currentID);
/* 144 */           stmt = connection.prepareStatement("update keygen_HWKReplacement set current_key=? where current_key =?");
/*     */           try {
/* 146 */             stmt.setString(1, ret);
/* 147 */             stmt.setString(2, currentID);
/* 148 */             if (stmt.executeUpdate() != 1) {
/* 149 */               log.warn("failed to create ID:" + ret + " (already in use), generating new one (retrying)");
/* 150 */               ret = null;
/*     */             } 
/*     */           } finally {
/* 153 */             stmt.close();
/*     */           } 
/* 155 */         } while (ret == null && --maxCount > 0);
/* 156 */         if (ret == null) {
/* 157 */           throw new IllegalStateException("unable to create ID");
/*     */         }
/* 159 */         connection.commit();
/* 160 */       } catch (Exception e) {
/* 161 */         connection.rollback();
/* 162 */         throw e;
/*     */       } finally {
/* 164 */         this.dblink.releaseConnection(connection);
/*     */       } 
/* 166 */       return ret;
/* 167 */     } catch (RuntimeException e) {
/* 168 */       throw e;
/* 169 */     } catch (Exception e) {
/* 170 */       throw new RuntimeException(e);
/*     */     } 
/*     */   }
/*     */   
/* 174 */   private final DateFormat DATE_FORMAT = new SimpleDateFormat("yyyyMMdd HH:mm");
/*     */   
/*     */   public String getReplacement(String userName) throws Exception {
/* 177 */     String ret = null;
/* 178 */     Connection connection = this.dblink.requestConnection();
/*     */     try {
/* 180 */       PreparedStatement stmt = connection.prepareStatement("select replacement_id from HWKReplacement where user_id=? ");
/*     */       try {
/* 182 */         stmt.setString(1, userName);
/* 183 */         ResultSet rs = stmt.executeQuery();
/*     */         try {
/* 185 */           if (rs.next()) {
/* 186 */             ret = rs.getString(1);
/*     */           }
/*     */         } finally {
/* 189 */           rs.close();
/*     */         } 
/*     */       } finally {
/*     */         
/* 193 */         stmt.close();
/*     */       } 
/*     */       
/* 196 */       if (ret == null) {
/* 197 */         ret = getID();
/* 198 */         stmt = connection.prepareStatement("insert into HWKReplacement(user_id,replacement_id,creation_date) values(?,?,?)");
/*     */         try {
/* 200 */           stmt.setString(1, userName);
/* 201 */           stmt.setString(2, ret);
/* 202 */           stmt.setString(3, this.DATE_FORMAT.format(new Date()));
/* 203 */           if (stmt.executeUpdate() != 1) {
/* 204 */             throw new IllegalStateException("unable to insert replacement");
/*     */           }
/*     */         } finally {
/* 207 */           stmt.close();
/*     */         } 
/*     */       } 
/*     */       
/* 211 */       connection.commit();
/* 212 */       return ret;
/*     */     } finally {
/* 214 */       this.dblink.releaseConnection(connection);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\frame\hwk\HWKReplacementProvider.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */