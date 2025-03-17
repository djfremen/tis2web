/*     */ package com.eoos.gm.tis2web.lt.implementation.io.icl.model.xml.impl;
/*     */ import com.eoos.gm.tis2web.lt.implementation.io.icl.model.xml.Header;
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
/*     */ public class HeaderImpl extends HeaderTypeImpl implements Header, RIElement, UnmarshallableObject, XMLSerializable, ValidatableObject {
/*  15 */   private static final Grammar schemaFragment = SchemaDeserializer.deserialize("¬í\000\005sr\000'com.sun.msv.grammar.trex.ElementPattern\000\000\000\000\000\000\000\001\002\000\001L\000\tnameClasst\000\037Lcom/sun/msv/grammar/NameClass;xr\000\036com.sun.msv.grammar.ElementExp\000\000\000\000\000\000\000\001\002\000\002Z\000\032ignoreUndeclaredAttributesL\000\fcontentModelt\000 Lcom/sun/msv/grammar/Expression;xr\000\036com.sun.msv.grammar.Expressionø\030èN5~O\002\000\003I\000\016cachedHashCodeL\000\023epsilonReducibilityt\000\023Ljava/lang/Boolean;L\000\013expandedExpq\000~\000\003xp\037{N~pp\000sr\000\037com.sun.msv.grammar.SequenceExp\000\000\000\000\000\000\000\001\002\000\000xr\000\035com.sun.msv.grammar.BinaryExp\000\000\000\000\000\000\000\001\002\000\002L\000\004exp1q\000~\000\003L\000\004exp2q\000~\000\003xq\000~\000\004\037{Nsppsq\000~\000\007\035[«ppsq\000~\000\007\033v\tüppsq\000~\000\007\031VD4ppsq\000~\000\007\0276~lppsq\000~\000\007\025\026¸¤ppsq\000~\000\007\02319õppsq\000~\000\007\021K»Fppsq\000~\000\007\017f<ppsq\000~\000\007\r½èppsq\000~\000\007\013`ø ppsq\000~\000\007\t{yqppsq\000~\000\007\007úÂppsq\000~\000\007\005°|\bppsq\000~\000\007\003ÊýYppsq\000~\000\000\001å~ªpp\000sr\000\033com.sun.msv.grammar.DataExp\000\000\000\000\000\000\000\001\002\000\003L\000\002dtt\000\037Lorg/relaxng/datatype/Datatype;L\000\006exceptq\000~\000\003L\000\004namet\000\035Lcom/sun/msv/util/StringPair;xq\000~\000\004\001å~ppsr\000#com.sun.msv.datatype.xsd.StringType\000\000\000\000\000\000\000\001\002\000\001Z\000\risAlwaysValidxr\000*com.sun.msv.datatype.xsd.BuiltinAtomicType\000\000\000\000\000\000\000\001\002\000\000xr\000%com.sun.msv.datatype.xsd.ConcreteType\000\000\000\000\000\000\000\001\002\000\000xr\000'com.sun.msv.datatype.xsd.XSDatatypeImpl\000\000\000\000\000\000\000\001\002\000\003L\000\fnamespaceUrit\000\022Ljava/lang/String;L\000\btypeNameq\000~\000!L\000\nwhiteSpacet\000.Lcom/sun/msv/datatype/xsd/WhiteSpaceProcessor;xpt\000 http://www.w3.org/2001/XMLSchemat\000\006stringsr\000.com.sun.msv.datatype.xsd.WhiteSpaceProcessor$1\023JMoIÛ¤G\002\000\000xr\000,com.sun.msv.datatype.xsd.WhiteSpaceProcessor\000\000\000\000\000\000\000\001\002\000\000xp\001sr\0000com.sun.msv.grammar.Expression$NullSetExpression\000\000\000\000\000\000\000\001\002\000\000xq\000~\000\004\000\000\000\nppsr\000\033com.sun.msv.util.StringPairÐt\036jB \002\000\002L\000\tlocalNameq\000~\000!L\000\fnamespaceURIq\000~\000!xpq\000~\000%q\000~\000$sr\000#com.sun.msv.grammar.SimpleNameClass\000\000\000\000\000\000\000\001\002\000\002L\000\tlocalNameq\000~\000!L\000\fnamespaceURIq\000~\000!xr\000\035com.sun.msv.grammar.NameClass\000\000\000\000\000\000\000\001\002\000\000xpt\000\004Maket\000\000sq\000~\000\000\001å~ªpp\000q\000~\000\034sq\000~\000-t\000\005Titleq\000~\0001sq\000~\000\000\001å~ªpp\000q\000~\000\034sq\000~\000-t\000\bServTypeq\000~\0001sr\000\035com.sun.msv.grammar.ChoiceExp\000\000\000\000\000\000\000\001\002\000\000xq\000~\000\b\001å~µppsq\000~\000\000\001å~ªsr\000\021java.lang.BooleanÍ rÕúî\002\000\001Z\000\005valuexp\000p\000q\000~\000\034sq\000~\000-t\000\007DrvTypeq\000~\0001sr\0000com.sun.msv.grammar.Expression$EpsilonExpression\000\000\000\000\000\000\000\001\002\000\000xq\000~\000\004\000\000\000\tsq\000~\000;\001psq\000~\000\000\001å~ªpp\000q\000~\000\034sq\000~\000-t\000\007Ordernoq\000~\0001sq\000~\000\000\001å~ªpp\000q\000~\000\034sq\000~\000-t\000\bCustomerq\000~\0001sq\000~\0008\002\037ÅÃppsq\000~\000\000\001\017âæpp\000sq\000~\000\000\001\017âÛpp\000sq\000~\0008\001\017âÐppsr\000 com.sun.msv.grammar.OneOrMoreExp\000\000\000\000\000\000\000\001\002\000\000xr\000\034com.sun.msv.grammar.UnaryExp\000\000\000\000\000\000\000\001\002\000\001L\000\003expq\000~\000\003xq\000~\000\004\001\017âÅq\000~\000<psr\000 com.sun.msv.grammar.AttributeExp\000\000\000\000\000\000\000\001\002\000\002L\000\003expq\000~\000\003L\000\tnameClassq\000~\000\001xq\000~\000\004\001\017âÂq\000~\000<psr\0002com.sun.msv.grammar.Expression$AnyStringExpression\000\000\000\000\000\000\000\001\002\000\000xq\000~\000\004\000\000\000\bq\000~\000Apsr\000 com.sun.msv.grammar.AnyNameClass\000\000\000\000\000\000\000\001\002\000\000xq\000~\000.q\000~\000@sq\000~\000-t\0003com.eoos.gm.tis2web.lt.io.icl.model.xml.ReleaseTypet\000+http://java.sun.com/jaxb/xjc/dummy-elementssq\000~\000-t\000\007Releaseq\000~\0001sq\000~\000\000\001\017âÛpp\000sq\000~\0008\001\017âÐppsq\000~\000L\001\017âÅq\000~\000<psq\000~\000O\001\017âÂq\000~\000<pq\000~\000Rq\000~\000Tq\000~\000@sq\000~\000-t\000/com.eoos.gm.tis2web.lt.io.icl.model.xml.Releaseq\000~\000Wsq\000~\000\000\001å~ªpp\000q\000~\000\034sq\000~\000-t\000\004Dateq\000~\0001sq\000~\000\000\001å~ªpp\000q\000~\000\034sq\000~\000-t\000\005Phoneq\000~\0001sq\000~\000\000\001å~ªpp\000q\000~\000\034sq\000~\000-t\000\002Kmq\000~\0001sq\000~\000\000\001å~ªpp\000q\000~\000\034sq\000~\000-t\000\013Numberplateq\000~\0001sq\000~\0008\002\037ÅÃppsq\000~\000\000\001\017âæpp\000sq\000~\000\000\001\017âÛpp\000sq\000~\0008\001\017âÐppsq\000~\000L\001\017âÅq\000~\000<psq\000~\000O\001\017âÂq\000~\000<pq\000~\000Rq\000~\000Tq\000~\000@sq\000~\000-t\0001com.eoos.gm.tis2web.lt.io.icl.model.xml.ModelTypeq\000~\000Wsq\000~\000-t\000\005Modelq\000~\0001sq\000~\000\000\001\017âÛpp\000sq\000~\0008\001\017âÐppsq\000~\000L\001\017âÅq\000~\000<psq\000~\000O\001\017âÂq\000~\000<pq\000~\000Rq\000~\000Tq\000~\000@sq\000~\000-t\000-com.eoos.gm.tis2web.lt.io.icl.model.xml.Modelq\000~\000Wsq\000~\0008\002\037ÅÃppsq\000~\000\000\001\017âæpp\000sq\000~\000\000\001\017âÛpp\000sq\000~\0008\001\017âÐppsq\000~\000L\001\017âÅq\000~\000<psq\000~\000O\001\017âÂq\000~\000<pq\000~\000Rq\000~\000Tq\000~\000@sq\000~\000-t\0002com.eoos.gm.tis2web.lt.io.icl.model.xml.EngineTypeq\000~\000Wsq\000~\000-t\000\006Engineq\000~\0001sq\000~\000\000\001\017âÛpp\000sq\000~\0008\001\017âÐppsq\000~\000L\001\017âÅq\000~\000<psq\000~\000O\001\017âÂq\000~\000<pq\000~\000Rq\000~\000Tq\000~\000@sq\000~\000-t\000.com.eoos.gm.tis2web.lt.io.icl.model.xml.Engineq\000~\000Wsq\000~\0008\002\037ÅÃppsq\000~\000\000\001\017âæpp\000sq\000~\000\000\001\017âÛpp\000sq\000~\0008\001\017âÐppsq\000~\000L\001\017âÅq\000~\000<psq\000~\000O\001\017âÂq\000~\000<pq\000~\000Rq\000~\000Tq\000~\000@sq\000~\000-t\0008com.eoos.gm.tis2web.lt.io.icl.model.xml.TransmissionTypeq\000~\000Wsq\000~\000-t\000\fTransmissionq\000~\0001sq\000~\000\000\001\017âÛpp\000sq\000~\0008\001\017âÐppsq\000~\000L\001\017âÅq\000~\000<psq\000~\000O\001\017âÂq\000~\000<pq\000~\000Rq\000~\000Tq\000~\000@sq\000~\000-t\0004com.eoos.gm.tis2web.lt.io.icl.model.xml.Transmissionq\000~\000Wsq\000~\000\000\001å~ªpp\000q\000~\000\034sq\000~\000-t\000\bInspTypeq\000~\0001sq\000~\0008\002\037ÅÃppsq\000~\000\000\001\017âæpp\000sq\000~\000\000\001\017âÛpp\000sq\000~\0008\001\017âÐppsq\000~\000L\001\017âÅq\000~\000<psq\000~\000O\001\017âÂq\000~\000<pq\000~\000Rq\000~\000Tq\000~\000@sq\000~\000-t\000/com.eoos.gm.tis2web.lt.io.icl.model.xml.VINTypeq\000~\000Wsq\000~\000-t\000\003VINq\000~\0001sq\000~\000\000\001\017âÛpp\000sq\000~\0008\001\017âÐppsq\000~\000L\001\017âÅq\000~\000<psq\000~\000O\001\017âÂq\000~\000<pq\000~\000Rq\000~\000Tq\000~\000@sq\000~\000-t\000+com.eoos.gm.tis2web.lt.io.icl.model.xml.VINq\000~\000Wsq\000~\000-t\000\006Headerq\000~\0001sr\000\"com.sun.msv.grammar.ExpressionPool\000\000\000\000\000\000\000\001\002\000\001L\000\bexpTablet\000/Lcom/sun/msv/grammar/ExpressionPool$ClosedHash;xpsr\000-com.sun.msv.grammar.ExpressionPool$ClosedHash×jÐNïèí\034\002\000\004I\000\005countI\000\tthresholdL\000\006parentq\000~\000²[\000\005tablet\000![Lcom/sun/msv/grammar/Expression;xp\000\000\000)\000\000\0009pur\000![Lcom.sun.msv.grammar.Expression;Ö8DÃ]­§\n\002\000\000xp\000\000\000¿pppppq\000~\000\024pppppppppppppq\000~\000\020ppppq\000~\000\nppppppppppppppppppq\000~\000\023q\000~\0009ppppppppppppq\000~\000\017pppppppppppppq\000~\000\027ppppppppppppppq\000~\000Hq\000~\000lq\000~\000|q\000~\000\fq\000~\000q\000~\000pppq\000~\000\016pppppppppppppq\000~\000\026q\000~\000Nq\000~\000\\q\000~\000pq\000~\000xq\000~\000q\000~\000q\000~\000q\000~\000q\000~\000£q\000~\000«q\000~\000\tq\000~\000Kq\000~\000[q\000~\000oq\000~\000wq\000~\000q\000~\000q\000~\000q\000~\000q\000~\000¢q\000~\000ªpppq\000~\000\022pppppppppppppppppppppppq\000~\000\025pppppppppppppq\000~\000\021ppppq\000~\000\013ppppppppq\000~\000\rpppp");
/*     */ 
/*     */   
/*     */   public String ____jaxb_ri____getNamespaceURI() {
/*  19 */     return "";
/*     */   }
/*     */   
/*     */   public String ____jaxb_ri____getLocalName() {
/*  23 */     return "Header";
/*     */   }
/*     */   
/*     */   private static final Class PRIMARY_INTERFACE_CLASS() {
/*  27 */     return Header.class;
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
/*  39 */     context.startElement("", "Header");
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
/*  53 */     return Header.class;
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
/*  67 */       return HeaderImpl.this;
/*     */     }
/*     */     
/*     */     public void enterElement(String ___uri, String ___local, Attributes __atts) throws UnreportedException {
/*  71 */       switch (this.state) {
/*     */         case 3:
/*  73 */           revertToParentFromEnterElement(___uri, ___local, __atts);
/*     */           return;
/*     */         case 1:
/*  76 */           if ("" == ___uri && "Make" == ___local) {
/*  77 */             HeaderImpl.this.getClass(); spawnSuperClassFromEnterElement(new HeaderTypeImpl.Unmarshaller(HeaderImpl.this, this.context), 2, ___uri, ___local, __atts);
/*     */             return;
/*     */           } 
/*     */           break;
/*     */         case 0:
/*  82 */           if ("" == ___uri && "Header" == ___local) {
/*  83 */             this.context.pushAttributes(__atts);
/*  84 */             this.state = 1;
/*     */             return;
/*     */           } 
/*     */           break;
/*     */       } 
/*  89 */       super.enterElement(___uri, ___local, __atts);
/*     */     }
/*     */     
/*     */     public void leaveElement(String ___uri, String ___local) throws UnreportedException {
/*  93 */       switch (this.state) {
/*     */         case 3:
/*  95 */           revertToParentFromLeaveElement(___uri, ___local);
/*     */           return;
/*     */         case 2:
/*  98 */           if ("" == ___uri && "Header" == ___local) {
/*  99 */             this.context.popAttributes();
/* 100 */             this.state = 3;
/*     */             return;
/*     */           } 
/*     */           break;
/*     */       } 
/* 105 */       super.leaveElement(___uri, ___local);
/*     */     }
/*     */     
/*     */     public void enterAttribute(String ___uri, String ___local) throws UnreportedException {
/* 109 */       switch (this.state) {
/*     */         case 3:
/* 111 */           revertToParentFromEnterAttribute(___uri, ___local);
/*     */           return;
/*     */       } 
/* 114 */       super.enterAttribute(___uri, ___local);
/*     */     }
/*     */     
/*     */     public void leaveAttribute(String ___uri, String ___local) throws UnreportedException {
/* 118 */       switch (this.state) {
/*     */         case 3:
/* 120 */           revertToParentFromLeaveAttribute(___uri, ___local);
/*     */           return;
/*     */       } 
/* 123 */       super.leaveAttribute(___uri, ___local);
/*     */     }
/*     */     
/*     */     public void text(String value) throws UnreportedException {
/*     */       try {
/* 128 */         switch (this.state) {
/*     */           case 3:
/* 130 */             revertToParentFromText(value);
/*     */             return;
/*     */         } 
/* 133 */       } catch (RuntimeException e) {
/* 134 */         handleUnexpectedTextException(value, e);
/*     */       } 
/*     */     }
/*     */     
/*     */     public void leaveChild(int nextState) throws UnreportedException {
/* 139 */       switch (nextState) {
/*     */         case 2:
/* 141 */           this.state = 2;
/*     */           return;
/*     */       } 
/* 144 */       super.leaveChild(nextState);
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\lt\implementation\io\icl\model\xml\impl\HeaderImpl.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */