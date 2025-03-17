/*    */ package com.eoos.gm.tis2web.si.implementation.io.ui.html.document;
/*    */ 
/*    */ import com.eoos.gm.tis2web.frame.export.common.ApplicationContext;
/*    */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*    */ import com.eoos.gm.tis2web.frame.export.common.ui.html.DialogBase;
/*    */ import com.eoos.gm.tis2web.si.implementation.io.ui.html.document.replacer.Replacer;
/*    */ import com.eoos.html.element.HtmlElement;
/*    */ import com.eoos.util.StringUtilities;
/*    */ import java.util.Collections;
/*    */ import java.util.Comparator;
/*    */ import java.util.List;
/*    */ import java.util.Map;
/*    */ 
/*    */ public class SIOLinkDialog extends DialogBase {
/*    */   private static String template;
/*    */   
/*    */   static {
/*    */     try {
/* 19 */       template = ApplicationContext.getInstance().loadFile(SIOLinkDialog.class, "linkpanel.html", null).toString();
/* 20 */     } catch (Exception e) {
/* 21 */       throw new RuntimeException();
/*    */     } 
/*    */   }
/*    */   protected SIOLinkElement listElement;
/*    */   
/*    */   public static class LinkComparator implements Comparator { public int compare(Object o1, Object o2) {
/* 27 */       Replacer.SIOLink l1 = (Replacer.SIOLink)o1;
/* 28 */       Replacer.SIOLink l2 = (Replacer.SIOLink)o2;
/*    */       
/* 30 */       return l1.getQualifier().compareTo(l2.getQualifier());
/*    */     } }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public SIOLinkDialog(ClientContext context, SIOLUDocumentContainer container, List<?> links) {
/* 37 */     super(context);
/* 38 */     Collections.sort(links, new LinkComparator());
/* 39 */     this.listElement = new SIOLinkElement(context, container, links);
/* 40 */     addElement((HtmlElement)this.listElement);
/*    */   }
/*    */   
/*    */   protected String getTitle(Map params) {
/* 44 */     return this.context.getLabel("si.sio-link.dialog");
/*    */   }
/*    */   
/*    */   protected String getContent(Map params) {
/* 48 */     StringBuffer code = new StringBuffer(template);
/* 49 */     StringUtilities.replace(code, "{LINK_LIST}", this.listElement.getHtmlCode(params));
/* 50 */     return code.toString();
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\si\implementation\i\\ui\html\document\SIOLinkDialog.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */