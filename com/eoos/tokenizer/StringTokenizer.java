/*    */ package com.eoos.tokenizer;
/*    */ 
/*    */ import java.util.Iterator;
/*    */ 
/*    */ public class StringTokenizer
/*    */   implements Tokenizer {
/*    */   protected String data;
/*    */   protected String delimiter;
/*    */   
/*    */   protected class STIterator
/*    */     implements Iterator {
/* 12 */     protected int index = 0;
/*    */ 
/*    */ 
/*    */ 
/*    */     
/*    */     public boolean hasNext() {
/* 18 */       return (StringTokenizer.this.data.indexOf(StringTokenizer.this.delimiter, this.index) != -1 || this.index < StringTokenizer.this.data.length() - 1);
/*    */     }
/*    */ 
/*    */ 
/*    */ 
/*    */     
/*    */     public Object next() {
/* 25 */       String retValue = null;
/* 26 */       if (this.index < StringTokenizer.this.data.length()) {
/* 27 */         int pastEndIndex = StringTokenizer.this.data.indexOf(StringTokenizer.this.delimiter, this.index);
/* 28 */         if (pastEndIndex == -1 && this.index < StringTokenizer.this.data.length()) {
/* 29 */           pastEndIndex = StringTokenizer.this.data.length();
/*    */         }
/*    */         
/* 32 */         retValue = StringTokenizer.this.data.substring(this.index, pastEndIndex);
/* 33 */         this.index = pastEndIndex + StringTokenizer.this.delimiter.length();
/*    */       } 
/* 35 */       return retValue;
/*    */     }
/*    */ 
/*    */ 
/*    */ 
/*    */     
/*    */     public void remove() {
/* 42 */       throw new UnsupportedOperationException();
/*    */     }
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public StringTokenizer(String data, String delimiter) {
/* 52 */     this.data = (data == null) ? "" : data;
/* 53 */     this.delimiter = delimiter;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Iterator iterator() {
/* 60 */     return new STIterator();
/*    */   }
/*    */   
/*    */   public static void main(String[] args) {
/* 64 */     String test = "hallo;das;;ist; ein;Test";
/* 65 */     StringTokenizer st = new StringTokenizer(test, ";");
/* 66 */     Iterator iter = st.iterator();
/* 67 */     while (iter.hasNext())
/* 68 */       System.out.println(iter.next()); 
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\tokenizer\StringTokenizer.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */