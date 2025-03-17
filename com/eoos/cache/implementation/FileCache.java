/*    */ package com.eoos.cache.implementation;
/*    */ 
/*    */ import com.eoos.cache.Cache;
/*    */ import com.eoos.persistence.implementation.datastore.FileSystemObjectStore;
/*    */ import java.io.File;
/*    */ import java.util.Collection;
/*    */ import java.util.Iterator;
/*    */ import java.util.List;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class FileCache
/*    */   implements Cache
/*    */ {
/*    */   private FileSystemObjectStore objectStore;
/*    */   
/*    */   public FileCache(File directory) {
/* 19 */     this.objectStore = new FileSystemObjectStore(directory, true);
/*    */   }
/*    */   
/*    */   public Object lookup(Object key) {
/* 23 */     return this.objectStore.load(key);
/*    */   }
/*    */   
/*    */   public void store(Object key, Object information) {
/* 27 */     this.objectStore.store(key, information);
/*    */   }
/*    */   
/*    */   public void remove(Object key) {
/* 31 */     this.objectStore.delete(key);
/*    */   }
/*    */   
/*    */   public Collection getKeys() {
/* 35 */     return this.objectStore.getIdentifiers();
/*    */   }
/*    */   
/*    */   public void clear() {
/* 39 */     for (Iterator iter = getKeys().iterator(); iter.hasNext();) {
/* 40 */       remove(iter.next());
/*    */     }
/*    */   }
/*    */   
/*    */   public long size() {
/* 45 */     return this.objectStore.getSize();
/*    */   }
/*    */   
/*    */   public List getFiles() {
/* 49 */     return this.objectStore.getFiles();
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\cache\implementation\FileCache.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */