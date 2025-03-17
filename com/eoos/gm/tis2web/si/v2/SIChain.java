/*     */ package com.eoos.gm.tis2web.si.v2;
/*     */ 
/*     */ import com.eoos.gm.tis2web.ctoc.service.cai.CTOCFactory;
/*     */ import com.eoos.gm.tis2web.ctoc.service.cai.CTOCNode;
/*     */ import com.eoos.gm.tis2web.ctoc.service.cai.CTOCType;
/*     */ import com.eoos.gm.tis2web.frame.export.common.locale.LocaleInfo;
/*     */ import com.eoos.gm.tis2web.si.service.cai.SI;
/*     */ import com.eoos.gm.tis2web.si.service.cai.SIO;
/*     */ import com.eoos.gm.tis2web.si.service.cai.SIOBlob;
/*     */ import com.eoos.scsm.v2.collection.CollectionUtil;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.Iterator;
/*     */ import java.util.LinkedHashSet;
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
/*     */ import java.util.Set;
/*     */ 
/*     */ public class SIChain
/*     */   implements SI
/*     */ {
/*     */   private Collection delegates;
/*     */   
/*     */   public SIChain(Collection delegates) {
/*  25 */     this.delegates = delegates;
/*     */   }
/*     */   
/*     */   public CTOCFactory getFactory() {
/*  29 */     throw new UnsupportedOperationException();
/*     */   }
/*     */   
/*     */   public SIOBlob getGraphic(final int sioID) {
/*  33 */     return (SIOBlob)CollectionUtil.foreachUntilNotNull(this.delegates, new CollectionUtil.ForeachCallback()
/*     */         {
/*     */           public Object executeOperation(Object item) throws Exception {
/*     */             try {
/*  37 */               return ((SIDataAdapter)item).getSI().getGraphic(sioID);
/*  38 */             } catch (Exception e) {
/*  39 */               return null;
/*     */             } 
/*     */           }
/*     */         });
/*     */   }
/*     */   
/*     */   public SIOBlob getImage(final int sioID) {
/*  46 */     return (SIOBlob)CollectionUtil.foreachUntilNotNull(this.delegates, new CollectionUtil.ForeachCallback()
/*     */         {
/*     */           public Object executeOperation(Object item) throws Exception {
/*     */             try {
/*  50 */               return ((SIDataAdapter)item).getSI().getImage(sioID);
/*  51 */             } catch (Exception e) {
/*  52 */               return null;
/*     */             } 
/*     */           }
/*     */         });
/*     */   }
/*     */   
/*     */   public SIO getSIO(final CTOCType ctype, final int sioID) {
/*  59 */     return (SIO)CollectionUtil.foreachUntilNotNull(this.delegates, new CollectionUtil.ForeachCallback()
/*     */         {
/*     */           public Object executeOperation(Object item) throws Exception {
/*     */             try {
/*  63 */               return ((SIDataAdapter)item).getSI().getSIO(ctype, sioID);
/*  64 */             } catch (Exception e) {
/*  65 */               return null;
/*     */             } 
/*     */           }
/*     */         });
/*     */   }
/*     */   
/*     */   public SIO getSIO(final int sioID) {
/*  72 */     return (SIO)CollectionUtil.foreachUntilNotNull(this.delegates, new CollectionUtil.ForeachCallback()
/*     */         {
/*     */           public Object executeOperation(Object item) throws Exception {
/*     */             try {
/*  76 */               return ((SIDataAdapter)item).getSI().getSIO(sioID);
/*  77 */             } catch (Exception e) {
/*  78 */               return null;
/*     */             } 
/*     */           }
/*     */         });
/*     */   }
/*     */ 
/*     */   
/*     */   public Collection getVersionInfo() {
/*  86 */     Collection ret = new LinkedList();
/*  87 */     for (Iterator<SIDataAdapter> iter = this.delegates.iterator(); iter.hasNext();) {
/*  88 */       ret.addAll(((SIDataAdapter)iter.next()).getSI().getVersionInfo());
/*     */     }
/*  90 */     return ret;
/*     */   }
/*     */   
/*     */   public List loadSIOs(List sios) {
/*  94 */     return null;
/*     */   }
/*     */   
/*     */   public void loadProperties(final List sios) {
/*  98 */     CollectionUtil.foreach(this.delegates, new CollectionUtil.ForeachCallback()
/*     */         {
/*     */           public Object executeOperation(Object item) throws Exception {
/* 101 */             ((SIDataAdapter)item).getSI().loadProperties(sios);
/*     */             
/* 103 */             return null;
/*     */           }
/*     */         });
/*     */   }
/*     */   
/*     */   public SIO lookupSIO(int sioID) {
/* 109 */     throw new UnsupportedOperationException();
/*     */   }
/*     */   
/*     */   public List provideDTCs() {
/* 113 */     final Set<?> ret = new LinkedHashSet();
/* 114 */     CollectionUtil.foreach(this.delegates, new CollectionUtil.ForeachCallback()
/*     */         {
/*     */           public Object executeOperation(Object item) throws Exception {
/* 117 */             List dtcs = ((SIDataAdapter)item).getSI().provideDTCs();
/* 118 */             ret.addAll(dtcs);
/* 119 */             return ret;
/*     */           }
/*     */         });
/* 122 */     return new ArrayList(ret);
/*     */   }
/*     */   
/*     */   public List provideIBs(final Integer sitqId) {
/* 126 */     final Set<?> ret = new LinkedHashSet();
/* 127 */     CollectionUtil.foreach(this.delegates, new CollectionUtil.ForeachCallback()
/*     */         {
/*     */           public Object executeOperation(Object item) throws Exception {
/* 130 */             List ibs = ((SIDataAdapter)item).getSI().provideIBs(sitqId);
/* 131 */             ret.addAll(ibs);
/* 132 */             return ret;
/*     */           }
/*     */         });
/* 135 */     return new ArrayList(ret);
/*     */   }
/*     */   
/*     */   public List provideTSBs() {
/* 139 */     final Set<?> ret = new LinkedHashSet();
/* 140 */     CollectionUtil.foreach(this.delegates, new CollectionUtil.ForeachCallback()
/*     */         {
/*     */           public Object executeOperation(Object item) throws Exception {
/* 143 */             List tsbs = ((SIDataAdapter)item).getSI().provideTSBs();
/* 144 */             ret.addAll(tsbs);
/* 145 */             return ret;
/*     */           }
/*     */         });
/* 148 */     return new ArrayList(ret);
/*     */   }
/*     */   
/*     */   public CTOCNode searchDocumentsByNumber(final String number) {
/* 152 */     return (CTOCNode)CollectionUtil.foreachUntilNotNull(this.delegates, new CollectionUtil.ForeachCallback()
/*     */         {
/*     */           public Object executeOperation(Object item) throws Exception {
/* 155 */             return ((SIDataAdapter)item).getSI().searchDocumentsByNumber(number);
/*     */           }
/*     */         });
/*     */   }
/*     */   
/*     */   public CTOCNode searchDocumentsByPublicationID(final String publicationID) {
/* 161 */     return (CTOCNode)CollectionUtil.foreachUntilNotNull(this.delegates, new CollectionUtil.ForeachCallback()
/*     */         {
/*     */           public Object executeOperation(Object item) throws Exception {
/* 164 */             return ((SIDataAdapter)item).getSI().searchDocumentsByPublicationID(publicationID);
/*     */           }
/*     */         });
/*     */   }
/*     */   
/*     */   public String getSubject(final SIO sioId, final LocaleInfo locale) {
/* 170 */     return (String)CollectionUtil.foreachUntilNotNull(this.delegates, new CollectionUtil.ForeachCallback()
/*     */         {
/*     */           public Object executeOperation(Object item) throws Exception {
/* 173 */             return ((SIDataAdapter)item).getSI().getSubject(sioId, locale);
/*     */           }
/*     */         });
/*     */   }
/*     */   
/*     */   public String getSubject(final Integer sioId, final LocaleInfo locale) {
/* 179 */     return (String)CollectionUtil.foreachUntilNotNull(this.delegates, new CollectionUtil.ForeachCallback()
/*     */         {
/*     */           public Object executeOperation(Object item) throws Exception {
/* 182 */             return ((SIDataAdapter)item).getSI().getSubject(sioId, locale);
/*     */           }
/*     */         });
/*     */   }
/*     */   
/*     */   public String getMimeType(int sioID) {
/* 188 */     String ret = null;
/* 189 */     for (Iterator<SIDataAdapter> iter = this.delegates.iterator(); iter.hasNext() && ret == null;) {
/* 190 */       ret = ((SIDataAdapter)iter.next()).getSI().getMimeType(sioID);
/*     */     }
/* 192 */     return ret;
/*     */   }
/*     */ 
/*     */   
/*     */   public SI.MHTML getDocument(final String docNumber, final LocaleInfo locale) {
/* 197 */     return (SI.MHTML)CollectionUtil.foreachUntilNotNull(this.delegates, new CollectionUtil.ForeachCallback()
/*     */         {
/*     */           public Object executeOperation(Object item) throws Exception {
/* 200 */             return ((SIDataAdapter)item).getSI().getDocument(docNumber, locale);
/*     */           }
/*     */         });
/*     */   }
/*     */   
/*     */   public SIOBlob getDocumentBlob(final SIO sio, final LocaleInfo locale) {
/* 206 */     return (SIOBlob)CollectionUtil.foreachUntilNotNull(this.delegates, new CollectionUtil.ForeachCallback()
/*     */         {
/*     */           public Object executeOperation(Object item) throws Exception {
/* 209 */             return ((SIDataAdapter)item).getSI().getDocumentBlob(sio, locale);
/*     */           }
/*     */         });
/*     */   }
/*     */   
/*     */   public String getMimeType4Image(int sioID) {
/* 215 */     String ret = null;
/* 216 */     for (Iterator<SIDataAdapter> iter = this.delegates.iterator(); iter.hasNext() && ret == null;) {
/* 217 */       ret = ((SIDataAdapter)iter.next()).getSI().getMimeType4Image(sioID);
/*     */     }
/* 219 */     return ret;
/*     */   }
/*     */   
/*     */   public byte[] getScreenData(final String identifier) throws Exception {
/* 223 */     return (byte[])CollectionUtil.foreachUntilNotNull(this.delegates, new CollectionUtil.ForeachCallback()
/*     */         {
/*     */           public Object executeOperation(Object item) throws Exception {
/* 226 */             return ((SIDataAdapter)item).getSI().getScreenData(identifier);
/*     */           }
/*     */         });
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\si\v2\SIChain.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */