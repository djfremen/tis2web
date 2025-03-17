/*     */ package com.eoos.gm.tis2web.frame.ws.e5.common.gen;
/*     */ 
/*     */ import javax.xml.bind.JAXBElement;
/*     */ import javax.xml.bind.annotation.XmlElementDecl;
/*     */ import javax.xml.bind.annotation.XmlRegistry;
/*     */ import javax.xml.namespace.QName;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @XmlRegistry
/*     */ public class ObjectFactory
/*     */ {
/*  27 */   private static final QName _Locales_QNAME = new QName("http://eoos-technologies.com/gm/t2w/euro5/types", "locales");
/*  28 */   private static final QName _Makes_QNAME = new QName("http://eoos-technologies.com/gm/t2w/euro5/types", "makes");
/*  29 */   private static final QName _FamilyDetails_QNAME = new QName("http://eoos-technologies.com/gm/t2w/euro5/types", "familyDetails");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public GetDocument createGetDocument() {
/*  43 */     return new GetDocument();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public InvParam createInvParam() {
/*  51 */     return new InvParam();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public InvToken createInvToken() {
/*  59 */     return new InvToken();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Str100List createStr100List() {
/*  67 */     return new Str100List();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public VehDesc createVehDesc() {
/*  75 */     return new VehDesc();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public FamilyList createFamilyList() {
/*  83 */     return new FamilyList();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public GetLocales createGetLocales() {
/*  91 */     return new GetLocales();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Rpo createRpo() {
/*  99 */     return new Rpo();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Document createDocument() {
/* 107 */     return new Document();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Family createFamily() {
/* 115 */     return new Family();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public VehDetails createVehDetails() {
/* 123 */     return new VehDetails();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BaseRequest createBaseRequest() {
/* 131 */     return new BaseRequest();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public MakeOpts createMakeOpts() {
/* 139 */     return new MakeOpts();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public GetFamilyDetails createGetFamilyDetails() {
/* 147 */     return new GetFamilyDetails();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public LocaleList createLocaleList() {
/* 155 */     return new LocaleList();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public MissingDoc createMissingDoc() {
/* 163 */     return new MissingDoc();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public GetMakes createGetMakes() {
/* 171 */     return new GetMakes();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ResolveVin createResolveVin() {
/* 179 */     return new ResolveVin();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public RpoList createRpoList() {
/* 187 */     return new RpoList();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public FatalError createFatalError() {
/* 195 */     return new FatalError();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @XmlElementDecl(namespace = "http://eoos-technologies.com/gm/t2w/euro5/types", name = "locales")
/*     */   public JAXBElement<LocaleList> createLocales(LocaleList value) {
/* 204 */     return new JAXBElement<LocaleList>(_Locales_QNAME, LocaleList.class, null, value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @XmlElementDecl(namespace = "http://eoos-technologies.com/gm/t2w/euro5/types", name = "makes")
/*     */   public JAXBElement<Str100List> createMakes(Str100List value) {
/* 213 */     return new JAXBElement<Str100List>(_Makes_QNAME, Str100List.class, null, value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @XmlElementDecl(namespace = "http://eoos-technologies.com/gm/t2w/euro5/types", name = "familyDetails")
/*     */   public JAXBElement<FamilyList> createFamilyDetails(FamilyList value) {
/* 222 */     return new JAXBElement<FamilyList>(_FamilyDetails_QNAME, FamilyList.class, null, value);
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\frame\ws\e5\common\gen\ObjectFactory.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */