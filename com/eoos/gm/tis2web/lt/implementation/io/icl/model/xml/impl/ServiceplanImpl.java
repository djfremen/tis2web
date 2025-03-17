/*     */ package com.eoos.gm.tis2web.lt.implementation.io.icl.model.xml.impl;
/*     */ import com.eoos.gm.tis2web.lt.implementation.io.icl.model.xml.Serviceplan;
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
/*     */ public class ServiceplanImpl extends ServiceplanTypeImpl implements Serviceplan, RIElement, UnmarshallableObject, XMLSerializable, ValidatableObject {
/*  15 */   private static final Grammar schemaFragment = SchemaDeserializer.deserialize("¬í\000\005sr\000'com.sun.msv.grammar.trex.ElementPattern\000\000\000\000\000\000\000\001\002\000\001L\000\tnameClasst\000\037Lcom/sun/msv/grammar/NameClass;xr\000\036com.sun.msv.grammar.ElementExp\000\000\000\000\000\000\000\001\002\000\002Z\000\032ignoreUndeclaredAttributesL\000\fcontentModelt\000 Lcom/sun/msv/grammar/Expression;xr\000\036com.sun.msv.grammar.Expressionø\030èN5~O\002\000\003I\000\016cachedHashCodeL\000\023epsilonReducibilityt\000\023Ljava/lang/Boolean;L\000\013expandedExpq\000~\000\003xp\b¬Iépp\000sr\000\037com.sun.msv.grammar.SequenceExp\000\000\000\000\000\000\000\001\002\000\000xr\000\035com.sun.msv.grammar.BinaryExp\000\000\000\000\000\000\000\001\002\000\002L\000\004exp1q\000~\000\003L\000\004exp2q\000~\000\003xq\000~\000\004\b¬IÞppsq\000~\000\007\006_QSppsq\000~\000\007\004?ppsr\000\035com.sun.msv.grammar.ChoiceExp\000\000\000\000\000\000\000\001\002\000\000xq\000~\000\b\002\037ÅÃppsq\000~\000\000\001\017âæpp\000sq\000~\000\000\001\017âÛpp\000sq\000~\000\f\001\017âÐppsr\000 com.sun.msv.grammar.OneOrMoreExp\000\000\000\000\000\000\000\001\002\000\000xr\000\034com.sun.msv.grammar.UnaryExp\000\000\000\000\000\000\000\001\002\000\001L\000\003expq\000~\000\003xq\000~\000\004\001\017âÅsr\000\021java.lang.BooleanÍ rÕúî\002\000\001Z\000\005valuexp\000psr\000 com.sun.msv.grammar.AttributeExp\000\000\000\000\000\000\000\001\002\000\002L\000\003expq\000~\000\003L\000\tnameClassq\000~\000\001xq\000~\000\004\001\017âÂq\000~\000\025psr\0002com.sun.msv.grammar.Expression$AnyStringExpression\000\000\000\000\000\000\000\001\002\000\000xq\000~\000\004\000\000\000\bsq\000~\000\024\001psr\000 com.sun.msv.grammar.AnyNameClass\000\000\000\000\000\000\000\001\002\000\000xr\000\035com.sun.msv.grammar.NameClass\000\000\000\000\000\000\000\001\002\000\000xpsr\0000com.sun.msv.grammar.Expression$EpsilonExpression\000\000\000\000\000\000\000\001\002\000\000xq\000~\000\004\000\000\000\tq\000~\000\032psr\000#com.sun.msv.grammar.SimpleNameClass\000\000\000\000\000\000\000\001\002\000\002L\000\tlocalNamet\000\022Ljava/lang/String;L\000\fnamespaceURIq\000~\000!xq\000~\000\034t\0002com.eoos.gm.tis2web.lt.io.icl.model.xml.HeaderTypet\000+http://java.sun.com/jaxb/xjc/dummy-elementssq\000~\000 t\000\006Headert\000\000sq\000~\000\000\001\017âÛpp\000sq\000~\000\f\001\017âÐppsq\000~\000\021\001\017âÅq\000~\000\025psq\000~\000\026\001\017âÂq\000~\000\025pq\000~\000\031q\000~\000\035q\000~\000\037sq\000~\000 t\000.com.eoos.gm.tis2web.lt.io.icl.model.xml.Headerq\000~\000$sq\000~\000\f\002\037ÅÃppsq\000~\000\000\001\017âæpp\000sq\000~\000\000\001\017âÛpp\000sq\000~\000\f\001\017âÐppsq\000~\000\021\001\017âÅq\000~\000\025psq\000~\000\026\001\017âÂq\000~\000\025pq\000~\000\031q\000~\000\035q\000~\000\037sq\000~\000 t\0005com.eoos.gm.tis2web.lt.io.icl.model.xml.PositionsTypeq\000~\000$sq\000~\000 t\000\tPositionsq\000~\000'sq\000~\000\000\001\017âÛpp\000sq\000~\000\f\001\017âÐppsq\000~\000\021\001\017âÅq\000~\000\025psq\000~\000\026\001\017âÂq\000~\000\025pq\000~\000\031q\000~\000\035q\000~\000\037sq\000~\000 t\0001com.eoos.gm.tis2web.lt.io.icl.model.xml.Positionsq\000~\000$sq\000~\000\f\002\037ÅÃppsq\000~\000\000\001\017âæpp\000sq\000~\000\000\001\017âÛpp\000sq\000~\000\f\001\017âÐppsq\000~\000\021\001\017âÅq\000~\000\025psq\000~\000\026\001\017âÂq\000~\000\025pq\000~\000\031q\000~\000\035q\000~\000\037sq\000~\000 t\0002com.eoos.gm.tis2web.lt.io.icl.model.xml.FooterTypeq\000~\000$sq\000~\000 t\000\006Footerq\000~\000'sq\000~\000\000\001\017âÛpp\000sq\000~\000\f\001\017âÐppsq\000~\000\021\001\017âÅq\000~\000\025psq\000~\000\026\001\017âÂq\000~\000\025pq\000~\000\031q\000~\000\035q\000~\000\037sq\000~\000 t\000.com.eoos.gm.tis2web.lt.io.icl.model.xml.Footerq\000~\000$sq\000~\000\026\002Løppsr\000\033com.sun.msv.grammar.DataExp\000\000\000\000\000\000\000\001\002\000\003L\000\002dtt\000\037Lorg/relaxng/datatype/Datatype;L\000\006exceptq\000~\000\003L\000\004namet\000\035Lcom/sun/msv/util/StringPair;xq\000~\000\004\001å~ppsr\000#com.sun.msv.datatype.xsd.StringType\000\000\000\000\000\000\000\001\002\000\001Z\000\risAlwaysValidxr\000*com.sun.msv.datatype.xsd.BuiltinAtomicType\000\000\000\000\000\000\000\001\002\000\000xr\000%com.sun.msv.datatype.xsd.ConcreteType\000\000\000\000\000\000\000\001\002\000\000xr\000'com.sun.msv.datatype.xsd.XSDatatypeImpl\000\000\000\000\000\000\000\001\002\000\003L\000\fnamespaceUriq\000~\000!L\000\btypeNameq\000~\000!L\000\nwhiteSpacet\000.Lcom/sun/msv/datatype/xsd/WhiteSpaceProcessor;xpt\000 http://www.w3.org/2001/XMLSchemat\000\006stringsr\000.com.sun.msv.datatype.xsd.WhiteSpaceProcessor$1\023JMoIÛ¤G\002\000\000xr\000,com.sun.msv.datatype.xsd.WhiteSpaceProcessor\000\000\000\000\000\000\000\001\002\000\000xp\001sr\0000com.sun.msv.grammar.Expression$NullSetExpression\000\000\000\000\000\000\000\001\002\000\000xq\000~\000\004\000\000\000\nppsr\000\033com.sun.msv.util.StringPairÐt\036jB \002\000\002L\000\tlocalNameq\000~\000!L\000\fnamespaceURIq\000~\000!xpq\000~\000Zq\000~\000Ysq\000~\000 t\000\blanguageq\000~\000'sq\000~\000 t\000\013Serviceplanq\000~\000'sr\000\"com.sun.msv.grammar.ExpressionPool\000\000\000\000\000\000\000\001\002\000\001L\000\bexpTablet\000/Lcom/sun/msv/grammar/ExpressionPool$ClosedHash;xpsr\000-com.sun.msv.grammar.ExpressionPool$ClosedHash×jÐNïèí\034\002\000\004I\000\005countI\000\tthresholdL\000\006parentq\000~\000g[\000\005tablet\000![Lcom/sun/msv/grammar/Expression;xp\000\000\000\022\000\000\0009pur\000![Lcom.sun.msv.grammar.Expression;Ö8DÃ]­§\n\002\000\000xp\000\000\000¿ppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppq\000~\000\tppppppppppppq\000~\000\nppppppppq\000~\000\rq\000~\000.q\000~\000>pppppppppppppppppppppq\000~\000\023q\000~\000*q\000~\0002q\000~\000:q\000~\000Bq\000~\000Jpppppq\000~\000\020q\000~\000)q\000~\0001q\000~\0009q\000~\000Aq\000~\000Ippppppppppppppppppppppppppppppppppppppppppppppppppq\000~\000\013ppppppppppppp");
/*     */ 
/*     */   
/*     */   public String ____jaxb_ri____getNamespaceURI() {
/*  19 */     return "";
/*     */   }
/*     */   
/*     */   public String ____jaxb_ri____getLocalName() {
/*  23 */     return "Serviceplan";
/*     */   }
/*     */   
/*     */   private static final Class PRIMARY_INTERFACE_CLASS() {
/*  27 */     return Serviceplan.class;
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
/*  39 */     context.startElement("", "Serviceplan");
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
/*  53 */     return Serviceplan.class;
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
/*  67 */       return ServiceplanImpl.this;
/*     */     }
/*     */     
/*     */     public void enterElement(String ___uri, String ___local, Attributes __atts) throws UnreportedException {
/*  71 */       switch (this.state) {
/*     */         case 1:
/*  73 */           if ("" == ___uri && "Header" == ___local) {
/*  74 */             ServiceplanImpl.this.getClass(); spawnSuperClassFromEnterElement(new ServiceplanTypeImpl.Unmarshaller(ServiceplanImpl.this, this.context), 2, ___uri, ___local, __atts);
/*     */             return;
/*     */           } 
/*     */           break;
/*     */         case 3:
/*  79 */           revertToParentFromEnterElement(___uri, ___local, __atts);
/*     */           return;
/*     */         case 0:
/*  82 */           if ("" == ___uri && "Serviceplan" == ___local) {
/*  83 */             this.context.pushAttributes(__atts);
/*  84 */             goto1();
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
/*  98 */           if ("" == ___uri && "Serviceplan" == ___local) {
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
/*     */         case 1:
/* 111 */           if ("" == ___uri && "language" == ___local) {
/* 112 */             ServiceplanImpl.this.getClass(); spawnSuperClassFromEnterAttribute(new ServiceplanTypeImpl.Unmarshaller(ServiceplanImpl.this, this.context), 2, ___uri, ___local);
/*     */             return;
/*     */           } 
/*     */           break;
/*     */         case 3:
/* 117 */           revertToParentFromEnterAttribute(___uri, ___local);
/*     */           return;
/*     */       } 
/* 120 */       super.enterAttribute(___uri, ___local);
/*     */     }
/*     */     
/*     */     public void leaveAttribute(String ___uri, String ___local) throws UnreportedException {
/* 124 */       switch (this.state) {
/*     */         case 3:
/* 126 */           revertToParentFromLeaveAttribute(___uri, ___local);
/*     */           return;
/*     */       } 
/* 129 */       super.leaveAttribute(___uri, ___local);
/*     */     }
/*     */     
/*     */     public void text(String value) throws UnreportedException {
/*     */       try {
/* 134 */         switch (this.state) {
/*     */           case 3:
/* 136 */             revertToParentFromText(value);
/*     */             return;
/*     */         } 
/* 139 */       } catch (RuntimeException e) {
/* 140 */         handleUnexpectedTextException(value, e);
/*     */       } 
/*     */     }
/*     */     
/*     */     public void leaveChild(int nextState) throws UnreportedException {
/* 145 */       switch (nextState) {
/*     */         case 2:
/* 147 */           this.state = 2;
/*     */           return;
/*     */       } 
/* 150 */       super.leaveChild(nextState);
/*     */     }
/*     */ 
/*     */     
/*     */     private void goto1() throws UnreportedException {
/* 155 */       this.state = 1;
/* 156 */       int idx = this.context.getAttribute("", "language");
/* 157 */       if (idx >= 0) {
/* 158 */         this.context.consumeAttribute(idx);
/*     */         return;
/*     */       } 
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\lt\implementation\io\icl\model\xml\impl\ServiceplanImpl.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */