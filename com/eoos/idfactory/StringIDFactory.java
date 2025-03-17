/*     */ package com.eoos.idfactory;
/*     */ 
/*     */ import com.eoos.scsm.v2.objectpool.StringBufferPool;
/*     */ import com.eoos.util.v2.Alphabet;
/*     */ import java.io.Externalizable;
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectInput;
/*     */ import java.io.ObjectOutput;
/*     */ import java.io.ObjectStreamException;
/*     */ import java.io.Serializable;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.Iterator;
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
/*     */ 
/*     */ 
/*     */ public final class StringIDFactory
/*     */   implements Serializable, IDFactory
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   private List characters;
/*     */   
/*     */   private static final class SerializationProxy
/*     */     implements Externalizable
/*     */   {
/*     */     private static final long serialVersionUID = 1L;
/*     */     private List characters;
/*     */     private List indices;
/*     */     
/*     */     private SerializationProxy(List characters, List indices) {
/*  32 */       this.characters = characters;
/*  33 */       this.indices = indices;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public SerializationProxy() {}
/*     */ 
/*     */     
/*     */     private Object readResolve() throws ObjectStreamException {
/*  42 */       return new StringIDFactory(this.characters, this.indices);
/*     */     }
/*     */     
/*     */     public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
/*  46 */       int count = in.readInt();
/*  47 */       this.characters = new ArrayList(count); int i;
/*  48 */       for (i = 0; i < count; i++) {
/*  49 */         Character c = new Character(in.readChar());
/*  50 */         this.characters.add(c);
/*     */       } 
/*     */       
/*  53 */       count = in.readInt();
/*  54 */       this.indices = new LinkedList();
/*  55 */       for (i = 0; i < count; i++) {
/*  56 */         Integer it = Integer.valueOf(in.readInt());
/*  57 */         this.indices.add(it);
/*     */       } 
/*     */     }
/*     */     
/*     */     public void writeExternal(ObjectOutput out) throws IOException {
/*  62 */       out.writeInt(this.characters.size());
/*  63 */       for (Iterator<Character> iterator = this.characters.iterator(); iterator.hasNext(); ) {
/*  64 */         Character c = iterator.next();
/*  65 */         out.writeChar(c.charValue());
/*     */       } 
/*     */       
/*  68 */       out.writeInt(this.indices.size());
/*  69 */       for (Iterator<Integer> iter = this.indices.iterator(); iter.hasNext(); ) {
/*  70 */         Integer i = iter.next();
/*  71 */         out.writeInt(i.intValue());
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  80 */   private List indices = new LinkedList();
/*     */ 
/*     */   
/*     */   public StringIDFactory(Alphabet alphabet) {
/*  84 */     List<Comparable> tmp = new ArrayList(alphabet.getCharacters());
/*  85 */     Collections.sort(tmp);
/*  86 */     this.characters = tmp;
/*  87 */     this.indices.add(Integer.valueOf(0));
/*     */   }
/*     */   
/*     */   private StringIDFactory(List characters, List indices) {
/*  91 */     this.characters = characters;
/*  92 */     this.indices = indices;
/*     */   }
/*     */   
/*     */   public Object createID() {
/*  96 */     Object retvalue = getIdentifier();
/*  97 */     incIndex();
/*  98 */     return retvalue;
/*     */   }
/*     */ 
/*     */   
/*     */   private void incIndex(int position) {
/* 103 */     if (position == this.indices.size()) {
/* 104 */       this.indices.add(Integer.valueOf(0));
/*     */     } else {
/* 106 */       Integer index = this.indices.get(position);
/* 107 */       if (index.intValue() == this.characters.size() - 1) {
/*     */         
/* 109 */         index = Integer.valueOf(0);
/* 110 */         incIndex(position + 1);
/*     */       } else {
/* 112 */         index = Integer.valueOf(index.intValue() + 1);
/*     */       } 
/* 114 */       this.indices.set(position, index);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void incIndex() {
/* 119 */     incIndex(0);
/*     */   }
/*     */   
/*     */   private String getIdentifier() {
/* 123 */     StringBuffer retvalue = StringBufferPool.getThreadInstance().get();
/*     */     try {
/* 125 */       for (int i = 0; i < this.indices.size(); i++) {
/* 126 */         int alphabetIndex = ((Integer)this.indices.get(i)).intValue();
/* 127 */         retvalue.append(this.characters.get(alphabetIndex));
/*     */       } 
/* 129 */       return retvalue.reverse().toString();
/*     */     } finally {
/* 131 */       StringBufferPool.getThreadInstance().free(retvalue);
/*     */     } 
/*     */   }
/*     */   
/*     */   private Object writeReplace() throws ObjectStreamException {
/* 136 */     return new SerializationProxy(this.characters, this.indices);
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\idfactory\StringIDFactory.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */