/*    */ package com.eoos.gm.tis2web.frame.implementation.admin.log4j.ui.html.main;
/*    */ 
/*    */ import com.eoos.gm.tis2web.frame.export.common.ApplicationContext;
/*    */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*    */ import com.eoos.html.element.HtmlElement;
/*    */ import com.eoos.html.element.HtmlElementContainerBase;
/*    */ import com.eoos.html.element.gtwo.PagedElement;
/*    */ import com.eoos.util.v2.StringUtilities;
/*    */ import java.util.Map;
/*    */ import org.apache.log4j.Logger;
/*    */ 
/*    */ public class MainPanel
/*    */   extends HtmlElementContainerBase
/*    */ {
/* 15 */   private static final Logger log = Logger.getLogger(MainPanel.class);
/*    */   private static String template;
/*    */   
/*    */   static {
/*    */     try {
/* 20 */       template = ApplicationContext.getInstance().loadFile(MainPanel.class, "mainpanel.html", null).toString();
/* 21 */     } catch (Exception e) {
/* 22 */       log.error("error loading template - error:" + e, e);
/* 23 */       throw new RuntimeException();
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   private HtmlElement pagedList;
/* 29 */   private final Object SYNC_STATE = new Object();
/*    */ 
/*    */   
/*    */   private MainPanel(ClientContext context) {
/* 33 */     AppenderListElement listElement = new AppenderListElement(context);
/* 34 */     this.pagedList = (HtmlElement)new PagedElement(context.createID(), (HtmlElement)listElement, 20, 20);
/* 35 */     addElement(this.pagedList);
/*    */   }
/*    */ 
/*    */   
/*    */   public static MainPanel getInstance(ClientContext context) {
/* 40 */     synchronized (context.getLockObject()) {
/* 41 */       MainPanel instance = (MainPanel)context.getObject(MainPanel.class);
/* 42 */       if (instance == null) {
/* 43 */         instance = new MainPanel(context);
/* 44 */         context.storeObject(MainPanel.class, instance);
/*    */       } 
/* 46 */       return instance;
/*    */     } 
/*    */   }
/*    */   
/*    */   public String getHtmlCode(Map params) {
/* 51 */     synchronized (this.SYNC_STATE) {
/* 52 */       StringBuffer tmp = new StringBuffer(template);
/* 53 */       StringUtilities.replace(tmp, "{LIST}", this.pagedList.getHtmlCode(params));
/* 54 */       return tmp.toString();
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\frame\implementation\admin\log4\\ui\html\main\MainPanel.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */