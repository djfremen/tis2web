/*    */ package com.eoos.gm.tis2web.profile.implementation.ui.html.home;
/*    */ 
/*    */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*    */ import com.eoos.gm.tis2web.frame.export.declaration.service.FrameService;
/*    */ import com.eoos.html.element.gtwo.DataRetrievalAbstraction;
/*    */ import com.eoos.html.element.gtwo.SelectBoxSelectionElement;
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
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
/*    */ 
/*    */ 
/*    */ public class EncodingSelection
/*    */   extends SelectBoxSelectionElement
/*    */ {
/* 30 */   public static final Integer WINDOWS = FrameService.ENC_WINDOWS;
/*    */   
/* 32 */   public static final Integer UTF8 = FrameService.ENC_UTF8;
/*    */   
/* 34 */   private static List options = new ArrayList(2);
/*    */   static {
/* 36 */     options.add(WINDOWS);
/* 37 */     options.add(UTF8);
/*    */   }
/*    */   
/*    */   private ClientContext context;
/*    */   
/*    */   public EncodingSelection(ClientContext context, String targetBookmark) {
/* 43 */     super(context.createID(), true, null, 1, targetBookmark);
/* 44 */     DataRetrievalAbstraction.DataCallback dataCallback = new DataRetrievalAbstraction.DataCallback()
/*    */       {
/*    */         public List getData() {
/* 47 */           return EncodingSelection.this.getData();
/*    */         }
/*    */       };
/* 50 */     setDataCallback(dataCallback);
/*    */     
/*    */     try {
/* 53 */       setValue(context.getSharedContext().getTextEncoding());
/* 54 */     } catch (Exception e) {
/* 55 */       setValue(WINDOWS);
/*    */     } 
/*    */     
/* 58 */     this.context = context;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public EncodingSelection(ClientContext context) {
/* 68 */     this(context, (String)null);
/*    */   }
/*    */   
/*    */   private List getData() {
/* 72 */     return options;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected String getDisplayValue(Object option) {
/* 81 */     if (option.equals(WINDOWS))
/* 82 */       return this.context.getLabel("encoding.windows.codepage"); 
/* 83 */     if (option.equals(UTF8)) {
/* 84 */       return this.context.getLabel("encoding.utf8");
/*    */     }
/* 86 */     throw new IllegalArgumentException();
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\profile\implementatio\\ui\html\home\EncodingSelection.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */