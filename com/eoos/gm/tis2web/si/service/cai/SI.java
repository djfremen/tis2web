/*    */ package com.eoos.gm.tis2web.si.service.cai;
/*    */ public interface SI { public static final String INSPECTIONS = "SIT-13"; public static final String BULLETINS = "SIT-12"; Collection getVersionInfo(); CTOCFactory getFactory(); SIO getSIO(int paramInt);
/*    */   SIO getSIO(CTOCType paramCTOCType, int paramInt);
/*    */   SIO lookupSIO(int paramInt);
/*    */   List loadSIOs(List paramList);
/*    */   MHTML getDocument(String paramString, LocaleInfo paramLocaleInfo) throws DocumentNotFoundException, DocumentContainerConstructionException, SearchNotResultException, CPRDocumentNotSupportedException;
/*    */   CTOCNode searchDocumentsByNumber(String paramString);
/*    */   CTOCNode searchDocumentsByPublicationID(String paramString);
/*    */   String getMimeType(int paramInt);
/*    */   String getMimeType4Image(int paramInt);
/*    */   SIOBlob getGraphic(int paramInt);
/*    */   SIOBlob getImage(int paramInt);
/*    */   SIOBlob getDocumentBlob(SIO paramSIO, LocaleInfo paramLocaleInfo);
/*    */   void loadProperties(List paramList);
/*    */   String getSubject(SIO paramSIO, LocaleInfo paramLocaleInfo);
/*    */   String getSubject(Integer paramInteger, LocaleInfo paramLocaleInfo);
/*    */   List provideTSBs();
/*    */   List provideDTCs();
/*    */   List provideIBs(Integer paramInteger);
/*    */   byte[] getScreenData(String paramString) throws Exception;
/*    */   public static interface MHTML { String getMIMEType();
/*    */     void writeContent(OutputStream param1OutputStream); }
/*    */   public static interface Retrieval { SI getSI();
/*    */     public static final class RI implements Retrieval { public RI(SI si) {
/* 25 */         this.si = si;
/*    */       }
/*    */       private SI si;
/*    */       public SI getSI() {
/* 29 */         return this.si;
/*    */       } }
/*    */      }
/*    */    }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\si\service\cai\SI.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */