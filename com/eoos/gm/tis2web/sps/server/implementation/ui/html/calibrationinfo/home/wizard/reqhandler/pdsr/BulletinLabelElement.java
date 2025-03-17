/*     */ package com.eoos.gm.tis2web.sps.server.implementation.ui.html.calibrationinfo.home.wizard.reqhandler.pdsr;
/*     */ 
/*     */ import com.eoos.datatype.ExceptionWrapper;
/*     */ import com.eoos.datatype.SectionIndex;
/*     */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*     */ import com.eoos.gm.tis2web.sps.common.gtwo.export.datamodel.Part;
/*     */ import com.eoos.gm.tis2web.sps.server.implementation.system.serverside.SPSServer;
/*     */ import com.eoos.html.element.HtmlElement;
/*     */ import com.eoos.html.element.HtmlElementContainerBase;
/*     */ import com.eoos.html.element.input.LinkElement;
/*     */ import com.eoos.html.element.input.tree.TreeControl;
/*     */ import com.eoos.util.StringUtilities;
/*     */ import java.util.Iterator;
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import org.apache.log4j.Logger;
/*     */ import org.apache.regexp.RE;
/*     */ import org.apache.regexp.RECompiler;
/*     */ import org.apache.regexp.REProgram;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class BulletinLabelElement
/*     */   extends HtmlElementContainerBase
/*     */ {
/*  27 */   private static final Logger log = Logger.getLogger(BulletinLabelElement.class);
/*     */   private static final REProgram linkProg;
/*     */   
/*     */   static {
/*     */     try {
/*  32 */       RECompiler comp = new RECompiler();
/*  33 */       linkProg = comp.compile("<A\\s+HREF=\"\\{LINK_SIO:ID:(.*)\">[^<]*</A>");
/*     */     }
/*  35 */     catch (Exception e) {
/*  36 */       throw new ExceptionWrapper(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private LabelElement labelElement;
/*  42 */   private List bulletinLinks = new LinkedList();
/*     */ 
/*     */   
/*     */   private ClientContext context;
/*     */ 
/*     */   
/*     */   public BulletinLabelElement(ClientContext context, TreeControl control, Object node) {
/*  49 */     this.context = context;
/*     */     
/*  51 */     this.labelElement = new LabelElement(context, control, node);
/*  52 */     addElement((HtmlElement)this.labelElement);
/*     */     
/*  54 */     if (((Part)node).getBulletins() != null) {
/*  55 */       for (Iterator<String> iter = ((Part)node).getBulletins().iterator(); iter.hasNext(); ) {
/*  56 */         String bulletinID = iter.next();
/*  57 */         LinkElement link = createBulletinLink(bulletinID);
/*  58 */         this.bulletinLinks.add(link);
/*  59 */         addElement((HtmlElement)link);
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private LinkElement createBulletinLink(final String bulletinID) {
/*  66 */     return new LinkElement(this.context.createID(), "_top") {
/*     */         protected String getLabel() {
/*  68 */           return bulletinID;
/*     */         }
/*     */         
/*     */         public Object onClick(Map submitParams) {
/*     */           try {
/*  73 */             StringBuffer bulletinCode = new StringBuffer(SPSServer.getInstance(BulletinLabelElement.this.context.getSessionID()).getBulletin(BulletinLabelElement.this.context.getLocale().toString(), bulletinID));
/*  74 */             SectionIndex index = null;
/*  75 */             RE link = new RE(BulletinLabelElement.linkProg);
/*  76 */             while ((index = StringUtilities.getSectionIndex(bulletinCode.toString(), link, 0)) != null) {
/*  77 */               StringUtilities.replaceSectionContent(bulletinCode, index, "");
/*     */             }
/*     */             
/*  80 */             return bulletinCode.toString();
/*  81 */           } catch (Exception e) {
/*  82 */             BulletinLabelElement.log.error("unable to retrieve bulletin " + bulletinID + " - exception: " + e, e);
/*  83 */             return "<html><body><span style=\"text-align: center; width:100%\"><b>" + BulletinLabelElement.this.context.getMessage("sps.cal.info.bulletin.not.found") + "</b></span></body></html>";
/*     */           } 
/*     */         }
/*     */         
/*     */         protected String getTargetFrame() {
/*  88 */           return "bulletin";
/*     */         }
/*     */       };
/*     */   }
/*     */ 
/*     */   
/*     */   public String getHtmlCode(Map params) {
/*  95 */     StringBuffer retValue = new StringBuffer(this.labelElement.getHtmlCode(params));
/*  96 */     if (!this.bulletinLinks.isEmpty()) {
/*  97 */       retValue.append(" (" + this.context.getLabel("bulletin.s") + ": ");
/*  98 */       for (Iterator<LinkElement> iter = this.bulletinLinks.iterator(); iter.hasNext(); ) {
/*  99 */         retValue.append(((LinkElement)iter.next()).getHtmlCode(params));
/* 100 */         retValue.append(", ");
/*     */       } 
/* 102 */       retValue.delete(retValue.length() - 2, retValue.length());
/* 103 */       retValue.append(")");
/*     */     } 
/* 105 */     return retValue.toString();
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\server\implementatio\\ui\html\calibrationinfo\home\wizard\reqhandler\pdsr\BulletinLabelElement.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */