/*    */ package com.eoos.gm.tis2web.sps.server.implementation.adapter.nao;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ import java.util.StringTokenizer;
/*    */ 
/*    */ public class SPSProgrammingInstructions
/*    */ {
/*    */   public static List tokenize(String list) {
/* 10 */     if (list == null || list.trim().length() == 0) {
/* 11 */       return null;
/*    */     }
/* 13 */     List<Integer> result = new ArrayList();
/* 14 */     StringTokenizer st = new StringTokenizer(list, ",", false);
/* 15 */     while (st.hasMoreTokens()) {
/* 16 */       String msg = st.nextToken();
/*    */       try {
/* 18 */         Integer id = Integer.valueOf(msg);
/* 19 */         if (id.intValue() > 0) {
/* 20 */           result.add(id);
/*    */         }
/* 22 */       } catch (Exception x) {}
/*    */     } 
/*    */     
/* 25 */     return (result.size() > 0) ? result : null;
/*    */   }
/*    */   
/*    */   public static String toString(List instructions) {
/* 29 */     if (instructions == null) {
/* 30 */       return "";
/*    */     }
/* 32 */     StringBuffer buf = new StringBuffer();
/* 33 */     for (int i = 0; i < instructions.size(); i++) {
/* 34 */       if (i > 0) {
/* 35 */         buf.append('/');
/*    */       }
/* 37 */       buf.append(instructions.get(i));
/*    */     } 
/* 39 */     return buf.toString();
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\server\implementation\adapter\nao\SPSProgrammingInstructions.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */