/*    */ package com.eoos.html.element.input;
/*    */ 
/*    */ import java.util.Map;
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
/*    */ public abstract class IDLinkElement
/*    */   extends LinkElement
/*    */ {
/*    */   private String id;
/*    */   
/*    */   public IDLinkElement(String name, String targetBookmark, String idPref) {
/* 20 */     super(name, targetBookmark);
/* 21 */     this.id = idPref + ":" + name;
/*    */   }
/*    */   
/*    */   protected void getAdditionalAttributes(Map<String, String> map) {
/* 25 */     super.getAdditionalAttributes(map);
/* 26 */     map.put("id", this.id);
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\html\element\input\IDLinkElement.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */