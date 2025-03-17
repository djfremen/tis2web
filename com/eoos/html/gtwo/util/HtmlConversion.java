/*    */ package com.eoos.html.gtwo.util;
/*    */ 
/*    */ import com.eoos.datatype.ExceptionWrapper;
/*    */ import com.eoos.datatype.gtwo.PairImpl;
/*    */ import com.eoos.scsm.v2.objectpool.StringBufferPool;
/*    */ import java.util.LinkedList;
/*    */ import java.util.List;
/*    */ import org.apache.regexp.RE;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class HtmlConversion
/*    */ {
/* 27 */   private static HtmlConversion instance = null;
/*    */   
/*    */   private static final String MASK_AMPERSAND = "#ampersand#";
/*    */   
/*    */   private static final String MASK_SEMICOLON = "#semicolon#";
/*    */   
/*    */   private RE reSpecialChars;
/*    */   
/* 35 */   private List replacements = new LinkedList();
/*    */ 
/*    */ 
/*    */   
/*    */   private HtmlConversion() {
/* 40 */     this.replacements.add(new PairImpl("&", "&amp;"));
/* 41 */     this.replacements.add(new PairImpl("\"", "&quot;"));
/* 42 */     this.replacements.add(new PairImpl("<", "&lt;"));
/* 43 */     this.replacements.add(new PairImpl(">", "&gt;"));
/*    */     try {
/* 45 */       this.reSpecialChars = new RE("&([^\\s;])*;");
/* 46 */     } catch (Exception e) {
/* 47 */       throw new ExceptionWrapper(e);
/*    */     } 
/*    */   }
/*    */   
/*    */   public static synchronized HtmlConversion getInstance() {
/* 52 */     if (instance == null) {
/* 53 */       instance = new HtmlConversion();
/*    */     }
/* 55 */     return instance;
/*    */   }
/*    */   
/*    */   public String convert(String text) {
/* 59 */     String retValue = null;
/* 60 */     if (text != null) {
/* 61 */       StringBuffer tmp = StringBufferPool.getThreadInstance().get(text);
/*    */       try {
/* 63 */         convert(tmp);
/* 64 */         retValue = tmp.toString();
/*    */       } finally {
/*    */         
/* 67 */         StringBufferPool.getThreadInstance().free(tmp);
/*    */       } 
/*    */     } 
/* 70 */     return retValue;
/*    */   }
/*    */   
/*    */   public void convert(StringBuffer text) {
/*    */     // Byte code:
/*    */     //   0: new com/eoos/datatype/SectionIndex
/*    */     //   3: dup
/*    */     //   4: iconst_0
/*    */     //   5: iconst_0
/*    */     //   6: invokespecial <init> : (II)V
/*    */     //   9: astore_2
/*    */     //   10: aload_1
/*    */     //   11: invokevirtual toString : ()Ljava/lang/String;
/*    */     //   14: aload_0
/*    */     //   15: getfield reSpecialChars : Lorg/apache/regexp/RE;
/*    */     //   18: aload_2
/*    */     //   19: getfield end : I
/*    */     //   22: invokestatic getSectionIndex : (Ljava/lang/String;Lorg/apache/regexp/RE;I)Lcom/eoos/datatype/SectionIndex;
/*    */     //   25: dup
/*    */     //   26: astore_2
/*    */     //   27: ifnull -> 105
/*    */     //   30: invokestatic getThreadInstance : ()Lcom/eoos/scsm/v2/objectpool/StringBufferPool;
/*    */     //   33: aload_1
/*    */     //   34: aload_2
/*    */     //   35: invokestatic getSectionContent : (Ljava/lang/StringBuffer;Lcom/eoos/datatype/SectionIndex;)Ljava/lang/String;
/*    */     //   38: invokevirtual get : (Ljava/lang/CharSequence;)Ljava/lang/StringBuffer;
/*    */     //   41: astore_3
/*    */     //   42: aload_3
/*    */     //   43: iconst_0
/*    */     //   44: iconst_1
/*    */     //   45: ldc '#ampersand#'
/*    */     //   47: invokevirtual replace : (IILjava/lang/String;)Ljava/lang/StringBuffer;
/*    */     //   50: pop
/*    */     //   51: aload_3
/*    */     //   52: aload_3
/*    */     //   53: invokevirtual length : ()I
/*    */     //   56: iconst_1
/*    */     //   57: isub
/*    */     //   58: aload_3
/*    */     //   59: invokevirtual length : ()I
/*    */     //   62: ldc '#semicolon#'
/*    */     //   64: invokevirtual replace : (IILjava/lang/String;)Ljava/lang/StringBuffer;
/*    */     //   67: pop
/*    */     //   68: aload_1
/*    */     //   69: aload_2
/*    */     //   70: aload_3
/*    */     //   71: invokevirtual toString : ()Ljava/lang/String;
/*    */     //   74: invokestatic replaceSectionContent : (Ljava/lang/StringBuffer;Lcom/eoos/datatype/SectionIndex;Ljava/lang/String;)V
/*    */     //   77: jsr -> 91
/*    */     //   80: goto -> 102
/*    */     //   83: astore #4
/*    */     //   85: jsr -> 91
/*    */     //   88: aload #4
/*    */     //   90: athrow
/*    */     //   91: astore #5
/*    */     //   93: invokestatic getThreadInstance : ()Lcom/eoos/scsm/v2/objectpool/StringBufferPool;
/*    */     //   96: aload_3
/*    */     //   97: invokevirtual free : (Ljava/lang/StringBuffer;)V
/*    */     //   100: ret #5
/*    */     //   102: goto -> 10
/*    */     //   105: aload_0
/*    */     //   106: getfield replacements : Ljava/util/List;
/*    */     //   109: invokeinterface iterator : ()Ljava/util/Iterator;
/*    */     //   114: astore_3
/*    */     //   115: aload_3
/*    */     //   116: invokeinterface hasNext : ()Z
/*    */     //   121: ifeq -> 158
/*    */     //   124: aload_3
/*    */     //   125: invokeinterface next : ()Ljava/lang/Object;
/*    */     //   130: checkcast com/eoos/datatype/gtwo/PairImpl
/*    */     //   133: astore #4
/*    */     //   135: aload_1
/*    */     //   136: aload #4
/*    */     //   138: invokevirtual getFirst : ()Ljava/lang/Object;
/*    */     //   141: checkcast java/lang/String
/*    */     //   144: aload #4
/*    */     //   146: invokevirtual getSecond : ()Ljava/lang/Object;
/*    */     //   149: checkcast java/lang/String
/*    */     //   152: invokestatic replace : (Ljava/lang/StringBuffer;Ljava/lang/String;Ljava/lang/String;)V
/*    */     //   155: goto -> 115
/*    */     //   158: aload_1
/*    */     //   159: ldc '#ampersand#'
/*    */     //   161: ldc '&'
/*    */     //   163: invokestatic replace : (Ljava/lang/StringBuffer;Ljava/lang/String;Ljava/lang/String;)V
/*    */     //   166: aload_1
/*    */     //   167: ldc '#semicolon#'
/*    */     //   169: ldc ';'
/*    */     //   171: invokestatic replace : (Ljava/lang/StringBuffer;Ljava/lang/String;Ljava/lang/String;)V
/*    */     //   174: return
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #75	-> 0
/*    */     //   #76	-> 10
/*    */     //   #77	-> 30
/*    */     //   #79	-> 42
/*    */     //   #80	-> 51
/*    */     //   #81	-> 68
/*    */     //   #83	-> 77
/*    */     //   #85	-> 80
/*    */     //   #84	-> 83
/*    */     //   #86	-> 102
/*    */     //   #88	-> 105
/*    */     //   #89	-> 124
/*    */     //   #90	-> 135
/*    */     //   #91	-> 155
/*    */     //   #94	-> 158
/*    */     //   #95	-> 166
/*    */     //   #97	-> 174
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   42	60	3	sc	Ljava/lang/StringBuffer;
/*    */     //   135	20	4	replacement	Lcom/eoos/datatype/gtwo/PairImpl;
/*    */     //   115	43	3	iter	Ljava/util/Iterator;
/*    */     //   0	175	0	this	Lcom/eoos/html/gtwo/util/HtmlConversion;
/*    */     //   0	175	1	text	Ljava/lang/StringBuffer;
/*    */     //   10	165	2	index	Lcom/eoos/datatype/SectionIndex;
/*    */     // Exception table:
/*    */     //   from	to	target	type
/*    */     //   42	80	83	finally
/*    */     //   83	88	83	finally
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\html\gtw\\util\HtmlConversion.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */