/*     */ package com.eoos.util.v2;
/*     */ 
/*     */ import com.eoos.scsm.v2.objectpool.StringBufferPool;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.HashSet;
/*     */ import java.util.LinkedHashSet;
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
/*     */ import java.util.Locale;
/*     */ import java.util.Set;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class Alphabet
/*     */ {
/*     */   private Set characters;
/*     */   
/*     */   public Alphabet(Collection<?> characters) {
/*  33 */     AssertUtil.ensure(characters, AssertUtil.NOT_NULL);
/*  34 */     AssertUtil.ensure(characters, new AssertUtil.ElementOfType(Character.class));
/*     */     
/*  36 */     this.characters = Collections.unmodifiableSet(new LinkedHashSet(characters));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Alphabet alphabetOf(String string) {
/*  48 */     Collection<Character> characters = new HashSet();
/*  49 */     for (int i = 0; i < string.length(); i++) {
/*  50 */       characters.add(new Character(string.charAt(i)));
/*     */     }
/*  52 */     return new Alphabet(characters);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Alphabet lowerCaseAlphabetOf(String string) {
/*  66 */     return alphabetOf(string.toLowerCase(Locale.ENGLISH));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Alphabet upperCaseAlphabetOf(String string) {
/*  80 */     return alphabetOf(string.toUpperCase(Locale.ENGLISH));
/*     */   }
/*     */   
/*     */   public Set getCharacters() {
/*  84 */     return this.characters;
/*     */   }
/*     */   
/*     */   public String toString() {
/*  88 */     return "AlphabetImpl[chars:" + this.characters + "]";
/*     */   }
/*     */   
/*     */   public int size() {
/*  92 */     return this.characters.size();
/*     */   }
/*     */   
/*     */   public boolean contains(Character c) {
/*  96 */     return this.characters.contains(c);
/*     */   }
/*     */   
/*     */   public Alphabet remove(Character c) {
/* 100 */     Set newSet = new HashSet(this.characters);
/* 101 */     newSet.remove(c);
/* 102 */     return new Alphabet(newSet);
/*     */   }
/*     */   
/*     */   public Alphabet add(Character c) {
/* 106 */     Set<Character> newSet = new HashSet(this.characters);
/* 107 */     newSet.add(c);
/* 108 */     return new Alphabet(newSet);
/*     */   }
/*     */   
/*     */   public Alphabet union(Alphabet a) {
/* 112 */     Set newSet = new HashSet(this.characters);
/* 113 */     newSet.addAll(a.getCharacters());
/* 114 */     return new Alphabet(newSet);
/*     */   }
/*     */   
/*     */   public Alphabet intersect(Alphabet b) {
/* 118 */     Set newSet = new HashSet(this.characters);
/* 119 */     newSet.retainAll(b.getCharacters());
/* 120 */     return new Alphabet(newSet);
/*     */   }
/*     */   
/*     */   public String createRandomWord(int length) {
/* 124 */     StringBuffer tmp = StringBufferPool.getThreadInstance().get();
/*     */     try {
/* 126 */       List<Character> characters = new LinkedList(getCharacters());
/* 127 */       int lengthAlphabet = size();
/* 128 */       for (int i = 0; i < length; i++) {
/* 129 */         int index = (int)Math.round(Math.random() * (lengthAlphabet - 1));
/* 130 */         tmp.append(characters.get(index));
/*     */       } 
/*     */       
/* 133 */       return tmp.toString();
/*     */     } finally {
/*     */       
/* 136 */       StringBufferPool.getThreadInstance().free(tmp);
/*     */     } 
/*     */   }
/*     */   
/*     */   public Character[] toCharacterArray() {
/* 141 */     List list = new ArrayList(this.characters);
/* 142 */     return (Character[])list.toArray((Object[])new Character[this.characters.size()]);
/*     */   }
/*     */ 
/*     */   
/*     */   public static void main(String[] args) {
/* 147 */     Alphabet a = alphabetOf("abA");
/* 148 */     System.out.println(a);
/*     */     
/* 150 */     System.out.println(a.add(new Character('c')));
/*     */     
/* 152 */     System.out.println(a.intersect(alphabetOf("b")));
/*     */     
/* 154 */     System.out.println(a.union(alphabetOf("123")));
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoo\\util\v2\Alphabet.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */