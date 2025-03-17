/*    */ package com.eoos.tokenizer;
/*    */ 
/*    */ import java.util.Iterator;
/*    */ 
/*    */ 
/*    */ public class CharacterTokenizer
/*    */   implements Tokenizer
/*    */ {
/*    */   protected String input;
/*    */   
/*    */   private class CTIterator
/*    */     implements Iterator
/*    */   {
/* 14 */     private int index = -1;
/*    */     
/*    */     public boolean hasNext() {
/* 17 */       return (this.index + 1 < CharacterTokenizer.this.input.length());
/*    */     }
/*    */     
/*    */     public Object next() {
/* 21 */       if (hasNext()) {
/* 22 */         this.index++;
/* 23 */         return new Character(CharacterTokenizer.this.input.charAt(this.index));
/*    */       } 
/* 25 */       return null;
/*    */     }
/*    */ 
/*    */     
/*    */     public void remove() {
/* 30 */       throw new UnsupportedOperationException();
/*    */     }
/*    */ 
/*    */     
/*    */     private CTIterator() {}
/*    */   }
/*    */ 
/*    */   
/*    */   public CharacterTokenizer(String input) {
/* 39 */     this.input = input;
/*    */   }
/*    */   
/*    */   public Iterator iterator() {
/* 43 */     return new CTIterator();
/*    */   }
/*    */   
/*    */   public static void main(String[] args) {
/* 47 */     CharacterTokenizer t = new CharacterTokenizer("test");
/* 48 */     Iterator iter = t.iterator();
/* 49 */     while (iter.hasNext())
/* 50 */       System.out.println(iter.next()); 
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\tokenizer\CharacterTokenizer.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */