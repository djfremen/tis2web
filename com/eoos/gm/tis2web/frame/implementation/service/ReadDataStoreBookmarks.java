/*     */ package com.eoos.gm.tis2web.frame.implementation.service;
/*     */ 
/*     */ import com.eoos.gm.tis2web.lt.implementation.io.datamodel.bookmark.Bookmark;
/*     */ import com.eoos.gm.tis2web.si.implementation.io.datamodel.bookmark.Bookmark;
/*     */ import com.eoos.io.StreamUtil;
/*     */ import java.io.ByteArrayInputStream;
/*     */ import java.io.File;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.FileOutputStream;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.ObjectOutputStream;
/*     */ import java.sql.Connection;
/*     */ import java.sql.DriverManager;
/*     */ import java.sql.PreparedStatement;
/*     */ import java.sql.ResultSet;
/*     */ import java.sql.SQLException;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.LinkedList;
/*     */ import java.util.Map;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ReadDataStoreBookmarks
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*  28 */   private Connection conn = null;
/*  29 */   private PreparedStatement stmt = null;
/*  30 */   private ResultSet rs = null;
/*     */   
/*  32 */   private BookmarkStore bookmarks = new BookmarkStore();
/*  33 */   private Map store = new HashMap<Object, Object>();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void execute() throws Exception {
/*     */     try {
/*  40 */       connect();
/*  41 */       load();
/*  42 */       store();
/*     */     } finally {
/*  44 */       if (this.rs != null) {
/*  45 */         this.rs.close();
/*     */       }
/*  47 */       if (this.stmt != null) {
/*  48 */         this.stmt.close();
/*     */       }
/*  50 */       if (this.conn != null) {
/*  51 */         this.conn.close();
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   private void store() {
/*  57 */     File storage = new File("c:\\temp\\bookmarks.dat");
/*     */     try {
/*  59 */       FileOutputStream fout = new FileOutputStream(storage);
/*  60 */       ObjectOutputStream oos = new ObjectOutputStream(fout);
/*  61 */       oos.writeObject(this.bookmarks);
/*  62 */       oos.close();
/*  63 */     } catch (Exception e) {
/*  64 */       e.printStackTrace();
/*     */     } 
/*     */     try {
/*  67 */       FileInputStream fin = new FileInputStream(storage);
/*  68 */       ObjectInputStream ois = new ObjectInputStream(fin);
/*  69 */       BookmarkStore bookmarks2 = (BookmarkStore)ois.readObject();
/*  70 */       ois.close();
/*  71 */       if (!bookmarks2.equals(this.bookmarks)) {
/*  72 */         throw new Exception("Bookmark Export Failed");
/*     */       }
/*  74 */     } catch (Exception e) {
/*  75 */       e.printStackTrace();
/*     */     } 
/*  77 */     File settings = new File("c:\\temp\\store.dat");
/*     */     try {
/*  79 */       FileOutputStream fout = new FileOutputStream(settings);
/*  80 */       ObjectOutputStream oos = new ObjectOutputStream(fout);
/*  81 */       oos.writeObject(this.store);
/*  82 */       oos.close();
/*  83 */     } catch (Exception e) {
/*  84 */       e.printStackTrace();
/*     */     } 
/*     */   }
/*     */   
/*     */   private void load() throws SQLException {
/*  89 */     int records = 0;
/*     */     
/*  91 */     this.stmt = this.conn.prepareStatement("SELECT DATA_ID, DATA_BLOB, TS, TS_DISPLAY FROM DATA_STORE");
/*  92 */     this.rs = this.stmt.executeQuery();
/*  93 */     while (this.rs.next()) {
/*  94 */       Map settings = null;
/*  95 */       String id = this.rs.getString("DATA_ID");
/*  96 */       if (id.toLowerCase().endsWith(".sav")) {
/*     */         try {
/*  98 */           byte[] data = StreamUtil.readFully(this.rs.getBinaryStream("DATA_BLOB"));
/*  99 */           if (data != null) {
/* 100 */             ByteArrayInputStream bais = new ByteArrayInputStream(data);
/* 101 */             ObjectInputStream ois = new ObjectInputStream(bais);
/* 102 */             settings = (Map)ois.readObject();
/* 103 */             if (settings == null)
/*     */               continue; 
/* 105 */             Map objects = (Map)settings.get("persistent.objects");
/* 106 */             if (objects == null)
/*     */               continue; 
/* 108 */             boolean hasBookmarks = false;
/* 109 */             String name = null;
/* 110 */             LinkedList si = (LinkedList)objects.get("si.bookmarks");
/* 111 */             if (si != null) {
/* 112 */               hasBookmarks = true;
/* 113 */               Iterator<Bookmark> it = si.iterator();
/* 114 */               while (it.hasNext()) {
/* 115 */                 Bookmark sibm = it.next();
/*     */                 try {
/* 117 */                   name = sibm.getName(null, null);
/* 118 */                   if (name != null && name.trim().length() == 0) {
/* 119 */                     name = null;
/*     */                   }
/* 121 */                 } catch (Exception x) {}
/*     */                 
/* 123 */                 if (sibm.getSIOID() <= 0) {
/*     */                   continue;
/*     */                 }
/* 126 */                 this.bookmarks.add(id, 0, new Integer(sibm.getSIOID()), name);
/* 127 */                 records++;
/*     */               } 
/*     */               
/* 130 */               objects.remove("si.bookmarks");
/*     */             } 
/* 132 */             LinkedList lt = (LinkedList)objects.get("lt.bookmarks");
/* 133 */             if (lt != null) {
/* 134 */               hasBookmarks = true;
/* 135 */               Iterator<Bookmark> it = lt.iterator();
/* 136 */               while (it.hasNext()) {
/* 137 */                 Bookmark ltbm = it.next();
/*     */                 try {
/* 139 */                   name = ltbm.getName(null);
/* 140 */                   if (name != null && name.trim().length() == 0) {
/* 141 */                     name = null;
/*     */                   }
/* 143 */                   System.out.println(name);
/* 144 */                 } catch (Exception x) {}
/*     */                 
/* 146 */                 if (ltbm.getSIOID() <= 0) {
/*     */                   continue;
/*     */                 }
/* 149 */                 this.bookmarks.add(id, 1, new Integer(ltbm.getSIOID()), name);
/* 150 */                 records++;
/*     */               } 
/*     */               
/* 153 */               objects.remove("lt.bookmarks");
/*     */             } 
/* 155 */             ois.close();
/* 156 */             if (hasBookmarks) {
/* 157 */               this.store.put(id, settings);
/* 158 */               System.out.println(id);
/*     */             } 
/*     */           } 
/* 161 */         } catch (Exception x) {
/* 162 */           System.err.println(x);
/*     */         } 
/*     */       }
/*     */     } 
/* 166 */     System.out.println("records = " + records);
/*     */   }
/*     */   
/*     */   private void connect() throws Exception {
/*     */     try {
/* 171 */       Class.forName("oracle.jdbc.OracleDriver");
/* 172 */       String url = "jdbc:oracle:thin:@usplgmgssmdb001.gweb.eds.com:1526:tis2web1";
/* 173 */       this.conn = DriverManager.getConnection(url, "SC", "dgsglobal4pre");
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 178 */       this.conn.setAutoCommit(false);
/* 179 */     } catch (Exception e) {
/* 180 */       System.err.println(e);
/* 181 */       throw e;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static void main(String[] args) throws Exception {
/* 187 */     ReadDataStoreBookmarks task = new ReadDataStoreBookmarks();
/* 188 */     task.execute();
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\frame\implementation\service\ReadDataStoreBookmarks.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */