/*     */ package com.eoos.gm.tis2web.sps.common.export;
/*     */ 
/*     */ import com.eoos.datatype.Denotation;
/*     */ import com.eoos.gm.tis2web.sps.common.gtwo.export.DisplayableValue;
/*     */ import com.eoos.gm.tis2web.sps.service.cai.Attribute;
/*     */ import com.eoos.gm.tis2web.sps.service.cai.AttributeValueMap;
/*     */ import com.eoos.gm.tis2web.sps.service.cai.Value;
/*     */ import java.io.ByteArrayOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.ObjectOutputStream;
/*     */ import java.io.OptionalDataException;
/*     */ import java.io.Serializable;
/*     */ import java.io.StreamCorruptedException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Locale;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class DataStorage
/*     */   implements AttributeValueMap, Serializable
/*     */ {
/*  27 */   private static final Logger log = Logger.getLogger(DataStorage.class);
/*     */   
/*     */   private static final long serialVersionUID = 1L;
/*     */   
/*  31 */   private static final Integer REMOVE = Integer.valueOf(0);
/*     */   
/*  33 */   private static final Integer ADD = Integer.valueOf(1);
/*     */   
/*  35 */   private static final Value NULL_VALUE = null;
/*     */   
/*  37 */   protected transient HashMap _lookup = new HashMap<Object, Object>();
/*     */   
/*  39 */   protected transient List data = new ArrayList();
/*     */   
/*  41 */   protected List delta = new ArrayList();
/*     */   
/*  43 */   protected transient int sequence = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private synchronized HashMap getLookup() {
/*  50 */     if (this._lookup == null) {
/*  51 */       this._lookup = new HashMap<Object, Object>();
/*     */     }
/*  53 */     return this._lookup;
/*     */   }
/*     */ 
/*     */   
/*     */   public void set(Attribute attribute, Value value) {
/*  58 */     this.delta.add(new Delta(ADD, attribute, value));
/*  59 */     Storage storage = (Storage)getLookup().get(attribute);
/*  60 */     if (storage == null) {
/*  61 */       storage = new Storage(++this.sequence, attribute, value);
/*  62 */       getLookup().put(attribute, storage);
/*  63 */       this.data.add(storage);
/*     */     } else {
/*  65 */       storage.setValue(++this.sequence, value);
/*  66 */       boolean clear = false;
/*  67 */       for (int i = 0; i < this.data.size(); i++) {
/*  68 */         if (clear) {
/*  69 */           Storage obsolete = this.data.get(i);
/*     */           
/*  71 */           getLookup().remove(obsolete.getAttribute());
/*  72 */           this.data.remove(i--);
/*     */         
/*     */         }
/*  75 */         else if (storage == this.data.get(i)) {
/*  76 */           clear = true;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void remove(Attribute attribute) {
/*  84 */     if (attribute == null)
/*     */       return; 
/*  86 */     this.delta.add(new Delta(REMOVE, attribute, NULL_VALUE));
/*  87 */     Storage storage = (Storage)getLookup().get(attribute);
/*  88 */     if (storage == null) {
/*     */       return;
/*     */     }
/*  91 */     boolean clear = false;
/*  92 */     for (int i = 0; i < this.data.size(); i++) {
/*  93 */       if (storage == this.data.get(i)) {
/*  94 */         clear = true;
/*     */       }
/*  96 */       if (clear) {
/*  97 */         Storage obsolete = this.data.get(i);
/*  98 */         getLookup().remove(obsolete.getAttribute());
/*  99 */         this.data.remove(i--);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public Value getValue(Attribute attribute) {
/* 106 */     Storage storage = (Storage)getLookup().get(attribute);
/* 107 */     return (storage != null) ? storage.getValue() : null;
/*     */   }
/*     */ 
/*     */   
/*     */   protected class Storage
/*     */     implements Serializable
/*     */   {
/*     */     private static final long serialVersionUID = 2L;
/*     */     protected int sequence;
/*     */     protected Attribute attribute;
/*     */     protected Value value;
/*     */     
/*     */     protected int getSequenceNo() {
/* 120 */       return this.sequence;
/*     */     }
/*     */     
/*     */     protected Attribute getAttribute() {
/* 124 */       return this.attribute;
/*     */     }
/*     */     
/*     */     protected Value getValue() {
/* 128 */       return this.value;
/*     */     }
/*     */     
/*     */     protected void setValue(int sequence, Value value) {
/* 132 */       this.sequence = sequence;
/* 133 */       this.value = value;
/*     */     }
/*     */     
/*     */     protected Storage(int sequence, Attribute attribute, Value value) {
/* 137 */       this.sequence = sequence;
/* 138 */       this.attribute = attribute;
/* 139 */       this.value = value;
/*     */     }
/*     */     
/*     */     public String toString() {
/* 143 */       String attr = this.attribute.toString();
/* 144 */       if (attr.indexOf("[key=") >= 0) {
/* 145 */         attr = attr.substring(attr.indexOf("[key="));
/*     */       }
/* 147 */       if (this.value == null)
/* 148 */         return attr + "=null [seq=" + this.sequence + "]"; 
/* 149 */       if (this.value instanceof DisplayableValue) {
/* 150 */         return attr + "=" + ((DisplayableValue)this.value).getDenotation(Locale.US) + " [seq=" + this.sequence + "]";
/*     */       }
/* 152 */       Object value = (this.value instanceof Denotation) ? ((Denotation)this.value).getDenotation(Locale.US) : this.value.toString();
/* 153 */       return attr + "=" + value + " [seq=" + this.sequence + "]";
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   protected static class Delta
/*     */     implements Serializable
/*     */   {
/*     */     private static final long serialVersionUID = 3L;
/*     */     
/*     */     protected Integer operation;
/*     */     
/*     */     protected Attribute attribute;
/*     */     protected Value value;
/*     */     
/*     */     protected Delta() {}
/*     */     
/*     */     protected Delta(Integer operation, Attribute attribute, Value value) {
/* 171 */       this.operation = operation;
/* 172 */       this.attribute = attribute;
/* 173 */       this.value = value;
/*     */     }
/*     */   }
/*     */   
/*     */   public Collection getAttributes() {
/* 178 */     List<Attribute> attributes = new ArrayList();
/* 179 */     for (int i = 0; i < this.data.size(); i++) {
/* 180 */       Storage storage = this.data.get(i);
/* 181 */       attributes.add(storage.getAttribute());
/*     */     } 
/* 183 */     return attributes;
/*     */   }
/*     */   
/*     */   public void dump() {
/* 187 */     log.debug(">>> data-storage dump: " + this.sequence + " (" + this + ")");
/* 188 */     for (int i = 0; i < this.data.size(); i++) {
/* 189 */       Storage storage = this.data.get(i);
/* 190 */       log.debug(i + ": " + storage.toString());
/*     */     } 
/*     */   }
/*     */   
/*     */   public void update(DataStorage update) {
/* 195 */     List<Delta> delta = update.delta;
/* 196 */     for (int i = 0; i < delta.size(); i++) {
/* 197 */       Delta diff = delta.get(i);
/* 198 */       if (diff.operation.equals(ADD)) {
/* 199 */         set(diff.attribute, diff.value);
/*     */       } else {
/* 201 */         remove(diff.attribute);
/*     */       } 
/*     */     } 
/* 204 */     this.delta = new ArrayList();
/*     */   }
/*     */   
/*     */   public void exchangeValue(Attribute attribute, Value value) {
/* 208 */     Storage storage = (Storage)getLookup().get(attribute);
/* 209 */     if (storage != null) {
/* 210 */       storage.value = value;
/*     */     }
/*     */   }
/*     */   
/*     */   public void exchange(Attribute attribute, Value value) {
/* 215 */     exchangeValue(attribute, value);
/*     */   }
/*     */   
/*     */   private void writeObject(ObjectOutputStream out) throws IOException {
/* 219 */     ByteArrayOutputStream baos = new ByteArrayOutputStream();
/* 220 */     for (int i = 0; i < this.delta.size(); i++) {
/* 221 */       Delta diff = this.delta.get(i);
/* 222 */       if (!(diff.attribute instanceof com.eoos.gm.tis2web.sps.common.gtwo.export.LocalAttribute)) {
/*     */         try {
/* 224 */           ObjectOutputStream oos = new ObjectOutputStream(baos);
/* 225 */           oos.writeObject(diff);
/* 226 */           oos.close();
/* 227 */           out.writeObject(diff);
/* 228 */         } catch (Exception e) {
/* 229 */           System.out.println("skip non-serializable attribute: " + diff.attribute);
/*     */         } 
/*     */       }
/*     */     } 
/* 233 */     baos.close();
/* 234 */     this.delta = new ArrayList();
/*     */   }
/*     */   
/*     */   private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
/* 238 */     this.delta = new ArrayList();
/* 239 */     boolean read = true;
/* 240 */     while (read) {
/*     */       try {
/* 242 */         Delta diff = (Delta)in.readObject();
/* 243 */         if (diff.operation != null) {
/* 244 */           this.delta.add(diff);
/*     */         }
/* 246 */       } catch (OptionalDataException e) {
/* 247 */         read = false;
/* 248 */       } catch (StreamCorruptedException s) {
/* 249 */         read = false;
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public int getSavePoint() {
/* 255 */     return this.sequence;
/*     */   }
/*     */   
/*     */   public void restoreSavePoint(int savepoint) {
/* 259 */     log.debug("rollback " + this);
/* 260 */     List<Attribute> rollback = new ArrayList(); int i;
/* 261 */     for (i = 0; i < this.data.size(); i++) {
/* 262 */       Storage storage = this.data.get(i);
/* 263 */       if (storage.getSequenceNo() > savepoint) {
/* 264 */         log.debug("rollback: " + storage.toString());
/* 265 */         rollback.add(storage.getAttribute());
/*     */       } 
/*     */     } 
/* 268 */     for (i = 0; i < rollback.size(); i++) {
/* 269 */       Attribute attribute = rollback.get(i);
/* 270 */       remove(attribute);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\common\export\DataStorage.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */