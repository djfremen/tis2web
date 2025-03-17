/*    */ package com.eoos.gm.tis2web.sps.common.gtwo.implementation.avmap;
/*    */ 
/*    */ import com.eoos.datatype.SimpleMultiton;
/*    */ import com.eoos.gm.tis2web.sps.service.cai.Attribute;
/*    */ import com.eoos.gm.tis2web.sps.service.cai.AttributeValueMap;
/*    */ import com.eoos.gm.tis2web.sps.service.cai.Value;
/*    */ import java.io.IOException;
/*    */ import java.io.NotSerializableException;
/*    */ import java.io.ObjectInputStream;
/*    */ import java.io.ObjectOutputStream;
/*    */ import java.io.Serializable;
/*    */ import java.util.Collection;
/*    */ import java.util.HashMap;
/*    */ import java.util.Iterator;
/*    */ import java.util.Map;
/*    */ import org.apache.log4j.Logger;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class AttributeValueMapImpl
/*    */   extends AttributeValueMapImplBase
/*    */   implements Serializable
/*    */ {
/* 25 */   private static final Logger log = Logger.getLogger(AttributeValueMapImpl.class);
/*    */   
/*    */   private static final long serialVersionUID = 1L;
/*    */   
/* 29 */   private static final Object EOD = SimpleMultiton.getInstance("EOD");
/*    */   
/*    */   protected transient Map map;
/*    */ 
/*    */   
/*    */   public AttributeValueMapImpl() {
/* 35 */     this.map = new HashMap<Object, Object>();
/*    */   }
/*    */   
/*    */   public AttributeValueMapImpl(AttributeValueMap map) {
/* 39 */     this.map = new HashMap<Object, Object>();
/* 40 */     for (Iterator<Attribute> iter = map.getAttributes().iterator(); iter.hasNext(); ) {
/* 41 */       Attribute attribute = iter.next();
/* 42 */       Value value = map.getValue(attribute);
/* 43 */       set(attribute, value);
/*    */     } 
/*    */   }
/*    */   
/*    */   public void set(Attribute attribute, Value value) {
/* 48 */     this.map.put(attribute, value);
/*    */   }
/*    */ 
/*    */   
/*    */   public void exchange(Attribute attribute, Value value) {}
/*    */   
/*    */   public void remove(Attribute attribute) {
/* 55 */     this.map.remove(attribute);
/*    */   }
/*    */   
/*    */   public Collection getAttributes() {
/* 59 */     return this.map.keySet();
/*    */   }
/*    */   
/*    */   public Value getValue(Attribute attribute) {
/* 63 */     return (Value)this.map.get(attribute);
/*    */   }
/*    */   
/*    */   private void writeObject(ObjectOutputStream stream) throws IOException {
/* 67 */     stream.defaultWriteObject();
/*    */     
/* 69 */     for (Iterator<Map.Entry> iter = this.map.entrySet().iterator(); iter.hasNext(); ) {
/* 70 */       Map.Entry entry = iter.next();
/* 71 */       Object key = entry.getKey();
/* 72 */       if (!(key instanceof com.eoos.gm.tis2web.sps.common.gtwo.export.LocalAttribute)) {
/*    */         try {
/* 74 */           stream.writeObject(key);
/* 75 */           stream.writeObject(entry.getValue());
/* 76 */         } catch (NotSerializableException e) {
/* 77 */           log.error("unable to serialize entry with key: " + String.valueOf(key));
/* 78 */           throw e;
/*    */         } 
/*    */         continue;
/*    */       } 
/* 82 */       log.debug("ignoring attribute marked as local:" + String.valueOf(key));
/*    */     } 
/*    */ 
/*    */     
/* 86 */     stream.writeObject(EOD);
/*    */   }
/*    */ 
/*    */   
/*    */   private void readObject(ObjectInputStream stream) throws IOException, ClassNotFoundException {
/* 91 */     stream.defaultReadObject();
/* 92 */     this.map = new HashMap<Object, Object>();
/* 93 */     Object key = null;
/* 94 */     while ((key = stream.readObject()) != EOD) {
/* 95 */       Object value = stream.readObject();
/* 96 */       set((Attribute)key, (Value)value);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\common\gtwo\implementation\avmap\AttributeValueMapImpl.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */