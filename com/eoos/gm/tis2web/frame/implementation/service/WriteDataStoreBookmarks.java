/*     */ package com.eoos.gm.tis2web.frame.implementation.service;
/*     */ 
/*     */ import com.eoos.gm.tis2web.lt.implementation.io.datamodel.bookmark.Bookmark;
/*     */ import com.eoos.gm.tis2web.si.implementation.io.datamodel.bookmark.Bookmark;
/*     */ import com.eoos.io.StreamUtil;
/*     */ import com.eoos.jdbc.JDBCUtil;
/*     */ import java.io.ByteArrayInputStream;
/*     */ import java.io.ByteArrayOutputStream;
/*     */ import java.io.File;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.ObjectOutputStream;
/*     */ import java.sql.Connection;
/*     */ import java.sql.DriverManager;
/*     */ import java.sql.PreparedStatement;
/*     */ import java.sql.ResultSet;
/*     */ import java.text.DateFormat;
/*     */ import java.text.SimpleDateFormat;
/*     */ import java.util.Date;
/*     */ import java.util.Iterator;
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ 
/*     */ 
/*     */ public class WriteDataStoreBookmarks
/*     */ {
/*  28 */   private static final DateFormat TSFORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
/*     */   
/*  30 */   private Connection conn = null;
/*  31 */   private PreparedStatement stmt = null;
/*  32 */   private ResultSet rs = null;
/*  33 */   private int inserts = 0;
/*  34 */   private int updates = 0;
/*     */ 
/*     */   
/*     */   private Map store;
/*     */ 
/*     */   
/*     */   private BookmarkStore bookmarks;
/*     */ 
/*     */   
/*     */   public void execute() throws Exception {
/*     */     try {
/*  45 */       connect();
/*  46 */       load();
/*  47 */       Iterator<String> it = this.store.keySet().iterator();
/*  48 */       while (it.hasNext()) {
/*  49 */         String user = it.next();
/*  50 */         Map settings = load(user);
/*  51 */         if (settings != null) {
/*  52 */           merge(user, settings);
/*  53 */           this.store.put(user, settings);
/*  54 */           System.out.println("merge " + user);
/*  55 */           this.updates++; continue;
/*     */         } 
/*  57 */         migrate(user);
/*  58 */         System.out.println("migrate " + user);
/*  59 */         this.inserts++;
/*     */       } 
/*     */     } finally {
/*     */       
/*  63 */       if (this.stmt != null) {
/*  64 */         this.stmt.close();
/*     */       }
/*     */     } 
/*  67 */     store();
/*  68 */     System.out.println("merge:" + this.updates + "  migrate:" + this.inserts);
/*     */   }
/*     */   
/*     */   private void store() throws Exception {
/*  72 */     String queryClear = "delete from DATA_STORE where DATA_ID = ?";
/*  73 */     PreparedStatement stmtClear = this.conn.prepareStatement(queryClear);
/*  74 */     String queryBlobInsert = "insert into DATA_STORE values (?,?,?,?)";
/*  75 */     PreparedStatement stmtCreate = this.conn.prepareStatement(queryBlobInsert);
/*     */     try {
/*  77 */       Iterator<String> it = this.store.keySet().iterator();
/*  78 */       while (it.hasNext()) {
/*  79 */         String user = it.next();
/*  80 */         store(stmtClear, stmtCreate, user, this.store.get(user));
/*     */       } 
/*  82 */     } catch (Exception e) {
/*  83 */       System.err.println(e);
/*     */       try {
/*  85 */         this.conn.rollback();
/*  86 */       } catch (Exception x) {
/*  87 */         System.err.println(x);
/*     */       } 
/*     */     } finally {
/*  90 */       this.conn.commit();
/*  91 */       JDBCUtil.close(stmtClear);
/*  92 */       JDBCUtil.close(stmtCreate);
/*  93 */       JDBCUtil.close(this.conn);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void store(PreparedStatement stmtClear, PreparedStatement stmtCreate, String user, Object settings) throws Exception {
/*  98 */     ByteArrayOutputStream baos = new ByteArrayOutputStream();
/*  99 */     ObjectOutputStream oos = new ObjectOutputStream(baos);
/* 100 */     oos.writeObject(settings);
/* 101 */     oos.close();
/* 102 */     byte[] data = baos.toByteArray();
/* 103 */     stmtClear.setString(1, user);
/* 104 */     stmtClear.executeUpdate();
/* 105 */     stmtCreate.setString(1, user);
/* 106 */     stmtCreate.setBinaryStream(2, new ByteArrayInputStream(data), data.length);
/* 107 */     long ts = System.currentTimeMillis();
/* 108 */     stmtCreate.setLong(3, ts);
/* 109 */     synchronized (TSFORMAT) {
/* 110 */       stmtCreate.setString(4, TSFORMAT.format(new Date(ts)));
/*     */     } 
/* 112 */     if (stmtCreate.executeUpdate() != 1) {
/* 113 */       throw new IllegalStateException();
/*     */     }
/*     */   }
/*     */   
/*     */   private void migrate(String user) throws Exception {
/* 118 */     Map settings = (Map)this.store.get(user);
/* 119 */     Map<String, LinkedList> objects = (Map)settings.get("persistent.objects");
/* 120 */     if (objects == null)
/* 121 */       throw new Exception("Internal Error"); 
/* 122 */     LinkedList si = (LinkedList)objects.get("si.bookmarks");
/* 123 */     LinkedList lt = (LinkedList)objects.get("lt.bookmarks");
/* 124 */     BookmarkStore.BookmarkList bmlist = (BookmarkStore.BookmarkList)this.bookmarks.get(user);
/* 125 */     List<BookmarkStore.BookmarkRecord> sibm = bmlist.getBookmarksSI();
/* 126 */     if (sibm != null) {
/* 127 */       if (si == null) {
/* 128 */         si = new LinkedList();
/* 129 */         objects.put("si.bookmarks", si);
/*     */       } 
/* 131 */       for (int i = 0; i < sibm.size(); i++) {
/* 132 */         BookmarkStore.BookmarkRecord record = sibm.get(i);
/* 133 */         insertBookmarkSI(si, record);
/*     */       } 
/*     */     } 
/* 136 */     List<BookmarkStore.BookmarkRecord> ltbm = bmlist.getBookmarksSI();
/* 137 */     if (ltbm != null) {
/* 138 */       if (lt == null) {
/* 139 */         lt = new LinkedList();
/* 140 */         objects.put("lt.bookmarks", lt);
/*     */       } 
/* 142 */       for (int i = 0; i < ltbm.size(); i++) {
/* 143 */         BookmarkStore.BookmarkRecord record = ltbm.get(i);
/* 144 */         insertBookmarkSI(lt, record);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private void merge(String user, Map settings) throws Exception {
/* 150 */     Map objects = (Map)settings.get("persistent.objects");
/* 151 */     if (objects == null)
/* 152 */       throw new Exception("Internal Error"); 
/* 153 */     LinkedList si = (LinkedList)objects.get("si.bookmarks");
/* 154 */     if (si != null) {
/* 155 */       mergeBookmarksSI(user, si);
/*     */     }
/* 157 */     LinkedList lt = (LinkedList)objects.get("lt.bookmarks");
/* 158 */     if (lt != null) {
/* 159 */       mergeBookmarksLT(user, lt);
/*     */     }
/*     */   }
/*     */   
/*     */   private void mergeBookmarksLT(String user, LinkedList lt) {
/* 164 */     BookmarkStore.BookmarkList bmlist = (BookmarkStore.BookmarkList)this.bookmarks.get(user);
/* 165 */     List<BookmarkStore.BookmarkRecord> ltbm = bmlist.getBookmarksLT();
/* 166 */     if (ltbm != null) {
/* 167 */       for (int i = 0; i < ltbm.size(); i++) {
/* 168 */         BookmarkStore.BookmarkRecord record = ltbm.get(i);
/* 169 */         if (!containsBookmarkLT(lt, record)) {
/* 170 */           insertBookmarkLT(lt, record);
/*     */         }
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   private void insertBookmarkLT(LinkedList<Bookmark> lt, BookmarkStore.BookmarkRecord record) {
/* 177 */     Bookmark ltbm = new Bookmark(record.getSioID().intValue(), record.getName());
/* 178 */     lt.add(ltbm);
/*     */   }
/*     */   
/*     */   private boolean containsBookmarkLT(LinkedList lt, BookmarkStore.BookmarkRecord record) {
/* 182 */     Iterator<Bookmark> it = lt.iterator();
/* 183 */     while (it.hasNext()) {
/* 184 */       Bookmark ltbm = it.next();
/* 185 */       if (ltbm.getSIOID() <= 0)
/*     */         continue; 
/* 187 */       if (ltbm.getSIOID() == record.getSioID().intValue()) {
/* 188 */         return true;
/*     */       }
/*     */     } 
/* 191 */     return false;
/*     */   }
/*     */   
/*     */   private void mergeBookmarksSI(String user, LinkedList si) {
/* 195 */     BookmarkStore.BookmarkList bmlist = (BookmarkStore.BookmarkList)this.bookmarks.get(user);
/* 196 */     List<BookmarkStore.BookmarkRecord> sibm = bmlist.getBookmarksSI();
/* 197 */     if (sibm != null) {
/* 198 */       for (int i = 0; i < sibm.size(); i++) {
/* 199 */         BookmarkStore.BookmarkRecord record = sibm.get(i);
/* 200 */         if (!containsBookmarkSI(si, record)) {
/* 201 */           insertBookmarkSI(si, record);
/*     */         }
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   private void insertBookmarkSI(LinkedList<Bookmark> si, BookmarkStore.BookmarkRecord record) {
/* 208 */     Bookmark sibm = new Bookmark(record.getSioID().intValue(), record.getName());
/* 209 */     si.add(sibm);
/*     */   }
/*     */   
/*     */   private boolean containsBookmarkSI(LinkedList si, BookmarkStore.BookmarkRecord record) {
/* 213 */     Iterator<Bookmark> it = si.iterator();
/* 214 */     while (it.hasNext()) {
/* 215 */       Bookmark sibm = it.next();
/* 216 */       if (sibm.getSIOID() <= 0)
/*     */         continue; 
/* 218 */       if (sibm.getSIOID() == record.getSioID().intValue()) {
/* 219 */         return true;
/*     */       }
/*     */     } 
/* 222 */     return false;
/*     */   }
/*     */   
/*     */   private Map load(String user) throws Exception {
/*     */     try {
/* 227 */       if (this.stmt == null) {
/* 228 */         this.stmt = this.conn.prepareStatement("SELECT DATA_ID, DATA_BLOB, TS, TS_DISPLAY FROM DATA_STORE WHERE DATA_ID = ?");
/*     */       }
/* 230 */       this.stmt.setString(1, user);
/* 231 */       this.rs = this.stmt.executeQuery();
/* 232 */       if (this.rs.next()) {
/* 233 */         Map settings = null;
/* 234 */         String id = this.rs.getString("DATA_ID");
/* 235 */         if (id.toLowerCase().endsWith(".sav")) {
/*     */           try {
/* 237 */             byte[] data = StreamUtil.readFully(this.rs.getBinaryStream("DATA_BLOB"));
/* 238 */             if (data != null) {
/* 239 */               ByteArrayInputStream bais = new ByteArrayInputStream(data);
/* 240 */               ObjectInputStream ois = new ObjectInputStream(bais);
/* 241 */               settings = (Map)ois.readObject();
/* 242 */               if (settings == null)
/* 243 */                 return null; 
/* 244 */               Map objects = (Map)settings.get("persistent.objects");
/* 245 */               if (objects == null)
/* 246 */                 return null; 
/* 247 */               ois.close();
/*     */             } 
/* 249 */           } catch (Exception x) {
/* 250 */             return null;
/*     */           } 
/*     */         }
/* 253 */         return settings;
/*     */       } 
/*     */       
/* 256 */       return null;
/*     */     } finally {
/* 258 */       if (this.rs != null) {
/*     */         try {
/* 260 */           this.rs.close();
/* 261 */         } catch (Exception x) {}
/*     */         
/* 263 */         this.rs = null;
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private void load() throws Exception {
/* 269 */     File storage = new File("c:\\temp\\store.dat");
/*     */     try {
/* 271 */       FileInputStream fin = new FileInputStream(storage);
/* 272 */       ObjectInputStream ois = new ObjectInputStream(fin);
/* 273 */       this.store = (Map)ois.readObject();
/* 274 */       ois.close();
/* 275 */     } catch (Exception e) {
/* 276 */       e.printStackTrace();
/*     */     } 
/* 278 */     storage = new File("c:\\temp\\bookmarks.dat");
/*     */     try {
/* 280 */       FileInputStream fin = new FileInputStream(storage);
/* 281 */       ObjectInputStream ois = new ObjectInputStream(fin);
/* 282 */       this.bookmarks = (BookmarkStore)ois.readObject();
/* 283 */       ois.close();
/* 284 */     } catch (Exception e) {
/* 285 */       e.printStackTrace();
/*     */     } 
/*     */   }
/*     */   
/*     */   private void connect() throws Exception {
/*     */     try {
/* 291 */       Class.forName("oracle.jdbc.OracleDriver");
/* 292 */       String url = "jdbc:oracle:thin:@gershwin.eoos-technologies.com:1521:tis2web";
/* 293 */       this.conn = DriverManager.getConnection(url, "SC_CLUSTER", "dgs");
/*     */ 
/*     */       
/* 296 */       this.conn.setAutoCommit(false);
/* 297 */     } catch (Exception e) {
/* 298 */       System.err.println(e);
/* 299 */       throw e;
/*     */     } 
/*     */   }
/*     */   
/*     */   public static void main(String[] args) throws Exception {
/* 304 */     WriteDataStoreBookmarks task = new WriteDataStoreBookmarks();
/* 305 */     task.execute();
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\frame\implementation\service\WriteDataStoreBookmarks.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */