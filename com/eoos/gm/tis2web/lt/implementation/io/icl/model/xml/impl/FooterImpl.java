/*     */ package com.eoos.gm.tis2web.lt.implementation.io.icl.model.xml.impl;
/*     */ import com.eoos.gm.tis2web.lt.implementation.io.icl.model.xml.Footer;
/*     */ import com.sun.msv.verifier.DocumentDeclaration;
/*     */ import com.sun.xml.bind.RIElement;
/*     */ import com.sun.xml.bind.serializer.XMLSerializer;
/*     */ import com.sun.xml.bind.unmarshaller.ContentHandlerEx;
/*     */ import com.sun.xml.bind.unmarshaller.UnmarshallableObject;
/*     */ import com.sun.xml.bind.unmarshaller.UnmarshallingContext;
/*     */ import com.sun.xml.bind.unmarshaller.UnreportedException;
/*     */ import com.sun.xml.bind.validator.SchemaDeserializer;
/*     */ import org.xml.sax.Attributes;
/*     */ import org.xml.sax.SAXException;
/*     */ 
/*     */ public class FooterImpl extends FooterTypeImpl implements Footer, RIElement, UnmarshallableObject, XMLSerializable, ValidatableObject {
/*  15 */   private static final Grammar schemaFragment = SchemaDeserializer.deserialize("¬í\000\005sr\000'com.sun.msv.grammar.trex.ElementPattern\000\000\000\000\000\000\000\001\002\000\001L\000\tnameClasst\000\037Lcom/sun/msv/grammar/NameClass;xr\000\036com.sun.msv.grammar.ElementExp\000\000\000\000\000\000\000\001\002\000\002Z\000\032ignoreUndeclaredAttributesL\000\fcontentModelt\000 Lcom/sun/msv/grammar/Expression;xr\000\036com.sun.msv.grammar.Expressionø\030èN5~O\002\000\003I\000\016cachedHashCodeL\000\023epsilonReducibilityt\000\023Ljava/lang/Boolean;L\000\013expandedExpq\000~\000\003xp\017 Úpp\000sr\000\037com.sun.msv.grammar.SequenceExp\000\000\000\000\000\000\000\001\002\000\000xr\000\035com.sun.msv.grammar.BinaryExp\000\000\000\000\000\000\000\001\002\000\002L\000\004exp1q\000~\000\003L\000\004exp2q\000~\000\003xq\000~\000\004\017 Ïppsq\000~\000\007\r»\005 ppsq\000~\000\007\013Õqppsq\000~\000\007\tð\007Âppsq\000~\000\007\b\n\023ppsq\000~\000\007\006%\ndppsq\000~\000\007\004\005Dppsr\000\035com.sun.msv.grammar.ChoiceExp\000\000\000\000\000\000\000\001\002\000\000xq\000~\000\b\001å~¸ppsr\000 com.sun.msv.grammar.OneOrMoreExp\000\000\000\000\000\000\000\001\002\000\000xr\000\034com.sun.msv.grammar.UnaryExp\000\000\000\000\000\000\000\001\002\000\001L\000\003expq\000~\000\003xq\000~\000\004\001å~­sr\000\021java.lang.BooleanÍ rÕúî\002\000\001Z\000\005valuexp\000psq\000~\000\000\001å~ªq\000~\000\026p\000sr\000\033com.sun.msv.grammar.DataExp\000\000\000\000\000\000\000\001\002\000\003L\000\002dtt\000\037Lorg/relaxng/datatype/Datatype;L\000\006exceptq\000~\000\003L\000\004namet\000\035Lcom/sun/msv/util/StringPair;xq\000~\000\004\001å~ppsr\000#com.sun.msv.datatype.xsd.StringType\000\000\000\000\000\000\000\001\002\000\001Z\000\risAlwaysValidxr\000*com.sun.msv.datatype.xsd.BuiltinAtomicType\000\000\000\000\000\000\000\001\002\000\000xr\000%com.sun.msv.datatype.xsd.ConcreteType\000\000\000\000\000\000\000\001\002\000\000xr\000'com.sun.msv.datatype.xsd.XSDatatypeImpl\000\000\000\000\000\000\000\001\002\000\003L\000\fnamespaceUrit\000\022Ljava/lang/String;L\000\btypeNameq\000~\000 L\000\nwhiteSpacet\000.Lcom/sun/msv/datatype/xsd/WhiteSpaceProcessor;xpt\000 http://www.w3.org/2001/XMLSchemat\000\006stringsr\000.com.sun.msv.datatype.xsd.WhiteSpaceProcessor$1\023JMoIÛ¤G\002\000\000xr\000,com.sun.msv.datatype.xsd.WhiteSpaceProcessor\000\000\000\000\000\000\000\001\002\000\000xp\001sr\0000com.sun.msv.grammar.Expression$NullSetExpression\000\000\000\000\000\000\000\001\002\000\000xq\000~\000\004\000\000\000\nppsr\000\033com.sun.msv.util.StringPairÐt\036jB \002\000\002L\000\tlocalNameq\000~\000 L\000\fnamespaceURIq\000~\000 xpq\000~\000$q\000~\000#sr\000#com.sun.msv.grammar.SimpleNameClass\000\000\000\000\000\000\000\001\002\000\002L\000\tlocalNameq\000~\000 L\000\fnamespaceURIq\000~\000 xr\000\035com.sun.msv.grammar.NameClass\000\000\000\000\000\000\000\001\002\000\000xpt\000\013PosFootnotet\000\000sr\0000com.sun.msv.grammar.Expression$EpsilonExpression\000\000\000\000\000\000\000\001\002\000\000xq\000~\000\004\000\000\000\tsq\000~\000\025\001psq\000~\000\020\002\037ÅÑppsq\000~\000\022\002\037ÅÆq\000~\000\026psq\000~\000\020\002\037ÅÃq\000~\000\026psq\000~\000\000\001\017âæq\000~\000\026p\000sq\000~\000\000\001\017âÛpp\000sq\000~\000\020\001\017âÐppsq\000~\000\022\001\017âÅq\000~\000\026psr\000 com.sun.msv.grammar.AttributeExp\000\000\000\000\000\000\000\001\002\000\002L\000\003expq\000~\000\003L\000\tnameClassq\000~\000\001xq\000~\000\004\001\017âÂq\000~\000\026psr\0002com.sun.msv.grammar.Expression$AnyStringExpression\000\000\000\000\000\000\000\001\002\000\000xq\000~\000\004\000\000\000\bq\000~\0003psr\000 com.sun.msv.grammar.AnyNameClass\000\000\000\000\000\000\000\001\002\000\000xq\000~\000-q\000~\0002sq\000~\000,t\0007com.eoos.gm.tis2web.lt.io.icl.model.xml.StdFootnoteTypet\000+http://java.sun.com/jaxb/xjc/dummy-elementssq\000~\000,t\000\013StdFootnoteq\000~\0000sq\000~\000\000\001\017âÛq\000~\000\026p\000sq\000~\000\020\001\017âÐppsq\000~\000\022\001\017âÅq\000~\000\026psq\000~\000;\001\017âÂq\000~\000\026pq\000~\000>q\000~\000@q\000~\0002sq\000~\000,t\0003com.eoos.gm.tis2web.lt.io.icl.model.xml.StdFootnoteq\000~\000Cq\000~\0002sq\000~\000\020\002\037ÅÑppsq\000~\000\022\002\037ÅÆq\000~\000\026psq\000~\000\020\002\037ÅÃq\000~\000\026psq\000~\000\000\001\017âæq\000~\000\026p\000sq\000~\000\000\001\017âÛpp\000sq\000~\000\020\001\017âÐppsq\000~\000\022\001\017âÅq\000~\000\026psq\000~\000;\001\017âÂq\000~\000\026pq\000~\000>q\000~\000@q\000~\0002sq\000~\000,t\0004com.eoos.gm.tis2web.lt.io.icl.model.xml.FootnoteTypeq\000~\000Csq\000~\000,t\000\bFootnoteq\000~\0000sq\000~\000\000\001\017âÛq\000~\000\026p\000sq\000~\000\020\001\017âÐppsq\000~\000\022\001\017âÅq\000~\000\026psq\000~\000;\001\017âÂq\000~\000\026pq\000~\000>q\000~\000@q\000~\0002sq\000~\000,t\0000com.eoos.gm.tis2web.lt.io.icl.model.xml.Footnoteq\000~\000Cq\000~\0002sq\000~\000\000\001å~ªpp\000q\000~\000\033sq\000~\000,t\000\fConfirmationq\000~\0000sq\000~\000\000\001å~ªpp\000q\000~\000\033sq\000~\000,t\000\007Defectsq\000~\0000sq\000~\000\000\001å~ªpp\000q\000~\000\033sq\000~\000,t\000\bMechanicq\000~\0000sq\000~\000\000\001å~ªpp\000q\000~\000\033sq\000~\000,t\000\005Checkq\000~\0000sq\000~\000\000\001å~ªpp\000q\000~\000\033sq\000~\000,t\000\bWorkshopq\000~\0000sq\000~\000,t\000\006Footerq\000~\0000sr\000\"com.sun.msv.grammar.ExpressionPool\000\000\000\000\000\000\000\001\002\000\001L\000\bexpTablet\000/Lcom/sun/msv/grammar/ExpressionPool$ClosedHash;xpsr\000-com.sun.msv.grammar.ExpressionPool$ClosedHash×jÐNïèí\034\002\000\004I\000\005countI\000\tthresholdL\000\006parentq\000~\000p[\000\005tablet\000![Lcom/sun/msv/grammar/Expression;xp\000\000\000\027\000\000\0009pur\000![Lcom.sun.msv.grammar.Expression;Ö8DÃ]­§\n\002\000\000xp\000\000\000¿pppppppppppppppppppppppppppq\000~\000\nppppppppq\000~\000\024ppppppppppq\000~\000\021pppppppppppppppppq\000~\000\tq\000~\000\016pppppppppppppppppppq\000~\0006q\000~\000Npq\000~\0005q\000~\000Mpppppppppq\000~\0004q\000~\000Lppq\000~\000\rpppppq\000~\000:q\000~\000Hq\000~\000Rq\000~\000Zpppppppq\000~\0009q\000~\000Gq\000~\000Qq\000~\000Ypppppppppppppppppq\000~\000\fpppppppppq\000~\000\017pppppppppppppppppppppppppppq\000~\000\013pppppppppp");
/*     */ 
/*     */   
/*     */   public String ____jaxb_ri____getNamespaceURI() {
/*  19 */     return "";
/*     */   }
/*     */   
/*     */   public String ____jaxb_ri____getLocalName() {
/*  23 */     return "Footer";
/*     */   }
/*     */   
/*     */   private static final Class PRIMARY_INTERFACE_CLASS() {
/*  27 */     return Footer.class;
/*     */   }
/*     */   
/*     */   public ContentHandlerEx getUnmarshaller(UnmarshallingContext context) {
/*  31 */     return new Unmarshaller(context);
/*     */   }
/*     */   
/*     */   public Class getPrimaryInterfaceClass() {
/*  35 */     return PRIMARY_INTERFACE_CLASS();
/*     */   }
/*     */   
/*     */   public void serializeElements(XMLSerializer context) throws SAXException {
/*  39 */     context.startElement("", "Footer");
/*  40 */     super.serializeAttributes(context);
/*  41 */     context.endAttributes();
/*  42 */     super.serializeElements(context);
/*  43 */     context.endElement();
/*     */   }
/*     */ 
/*     */   
/*     */   public void serializeAttributes(XMLSerializer context) throws SAXException {}
/*     */ 
/*     */   
/*     */   public void serializeAttributeBodies(XMLSerializer context) throws SAXException {}
/*     */   
/*     */   public Class getPrimaryInterface() {
/*  53 */     return Footer.class;
/*     */   }
/*     */   
/*     */   public DocumentDeclaration createRawValidator() {
/*  57 */     return (DocumentDeclaration)new REDocumentDeclaration(schemaFragment);
/*     */   }
/*     */   
/*     */   public class Unmarshaller
/*     */     extends ContentHandlerEx {
/*     */     public Unmarshaller(UnmarshallingContext context) {
/*  63 */       super(context, "----");
/*     */     }
/*     */     
/*     */     protected UnmarshallableObject owner() {
/*  67 */       return FooterImpl.this;
/*     */     }
/*     */     
/*     */     public void enterElement(String ___uri, String ___local, Attributes __atts) throws UnreportedException {
/*  71 */       switch (this.state) {
/*     */         case 3:
/*  73 */           revertToParentFromEnterElement(___uri, ___local, __atts);
/*     */           return;
/*     */         case 0:
/*  76 */           if ("" == ___uri && "Footer" == ___local) {
/*  77 */             this.context.pushAttributes(__atts);
/*  78 */             this.state = 1;
/*     */             return;
/*     */           } 
/*     */           break;
/*     */         case 1:
/*  83 */           if ("" == ___uri && "PosFootnote" == ___local) {
/*  84 */             FooterImpl.this.getClass(); spawnSuperClassFromEnterElement(new FooterTypeImpl.Unmarshaller(FooterImpl.this, this.context), 2, ___uri, ___local, __atts);
/*     */             return;
/*     */           } 
/*  87 */           if ("" == ___uri && "Footnote" == ___local) {
/*  88 */             FooterImpl.this.getClass(); spawnSuperClassFromEnterElement(new FooterTypeImpl.Unmarshaller(FooterImpl.this, this.context), 2, ___uri, ___local, __atts);
/*     */             return;
/*     */           } 
/*  91 */           if ("" == ___uri && "StdFootnote" == ___local) {
/*  92 */             FooterImpl.this.getClass(); spawnSuperClassFromEnterElement(new FooterTypeImpl.Unmarshaller(FooterImpl.this, this.context), 2, ___uri, ___local, __atts);
/*     */             return;
/*     */           } 
/*  95 */           if ("" == ___uri && "Confirmation" == ___local) {
/*  96 */             FooterImpl.this.getClass(); spawnSuperClassFromEnterElement(new FooterTypeImpl.Unmarshaller(FooterImpl.this, this.context), 2, ___uri, ___local, __atts);
/*     */             return;
/*     */           } 
/*     */           break;
/*     */       } 
/* 101 */       super.enterElement(___uri, ___local, __atts);
/*     */     }
/*     */     
/*     */     public void leaveElement(String ___uri, String ___local) throws UnreportedException {
/* 105 */       switch (this.state) {
/*     */         case 3:
/* 107 */           revertToParentFromLeaveElement(___uri, ___local);
/*     */           return;
/*     */         case 2:
/* 110 */           if ("" == ___uri && "Footer" == ___local) {
/* 111 */             this.context.popAttributes();
/* 112 */             this.state = 3;
/*     */             return;
/*     */           } 
/*     */           break;
/*     */       } 
/* 117 */       super.leaveElement(___uri, ___local);
/*     */     }
/*     */     
/*     */     public void enterAttribute(String ___uri, String ___local) throws UnreportedException {
/* 121 */       switch (this.state) {
/*     */         case 3:
/* 123 */           revertToParentFromEnterAttribute(___uri, ___local);
/*     */           return;
/*     */       } 
/* 126 */       super.enterAttribute(___uri, ___local);
/*     */     }
/*     */     
/*     */     public void leaveAttribute(String ___uri, String ___local) throws UnreportedException {
/* 130 */       switch (this.state) {
/*     */         case 3:
/* 132 */           revertToParentFromLeaveAttribute(___uri, ___local);
/*     */           return;
/*     */       } 
/* 135 */       super.leaveAttribute(___uri, ___local);
/*     */     }
/*     */     
/*     */     public void text(String value) throws UnreportedException {
/*     */       try {
/* 140 */         switch (this.state) {
/*     */           case 3:
/* 142 */             revertToParentFromText(value);
/*     */             return;
/*     */         } 
/* 145 */       } catch (RuntimeException e) {
/* 146 */         handleUnexpectedTextException(value, e);
/*     */       } 
/*     */     }
/*     */     
/*     */     public void leaveChild(int nextState) throws UnreportedException {
/* 151 */       switch (nextState) {
/*     */         case 2:
/* 153 */           this.state = 2;
/*     */           return;
/*     */       } 
/* 156 */       super.leaveChild(nextState);
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\lt\implementation\io\icl\model\xml\impl\FooterImpl.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */