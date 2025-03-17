/*     */ package com.eoos.gm.tis2web.registration.standalone.xml.impl;
/*     */ import com.eoos.gm.tis2web.registration.standalone.xml.TIS2WEB;
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
/*     */ public class TIS2WEBImpl extends TIS2WEBTypeImpl implements TIS2WEB, RIElement, UnmarshallableObject, XMLSerializable, ValidatableObject {
/*  15 */   private static final Grammar schemaFragment = SchemaDeserializer.deserialize("¬í\000\005sr\000'com.sun.msv.grammar.trex.ElementPattern\000\000\000\000\000\000\000\001\002\000\001L\000\tnameClasst\000\037Lcom/sun/msv/grammar/NameClass;xr\000\036com.sun.msv.grammar.ElementExp\000\000\000\000\000\000\000\001\002\000\002Z\000\032ignoreUndeclaredAttributesL\000\fcontentModelt\000 Lcom/sun/msv/grammar/Expression;xr\000\036com.sun.msv.grammar.Expressionø\030èN5~O\002\000\003I\000\016cachedHashCodeL\000\023epsilonReducibilityt\000\023Ljava/lang/Boolean;L\000\013expandedExpq\000~\000\003xp\004Ð¬xpp\000sr\000\037com.sun.msv.grammar.SequenceExp\000\000\000\000\000\000\000\001\002\000\000xr\000\035com.sun.msv.grammar.BinaryExp\000\000\000\000\000\000\000\001\002\000\002L\000\004exp1q\000~\000\003L\000\004exp2q\000~\000\003xq\000~\000\004\004Ð¬mppsq\000~\000\007\003èý\022ppsq\000~\000\007\002;ppsr\000 com.sun.msv.grammar.OneOrMoreExp\000\000\000\000\000\000\000\001\002\000\000xr\000\034com.sun.msv.grammar.UnaryExp\000\000\000\000\000\000\000\001\002\000\001L\000\003expq\000~\000\003xq\000~\000\004\000³öppsr\000\035com.sun.msv.grammar.ChoiceExp\000\000\000\000\000\000\000\001\002\000\000xq\000~\000\b\000³öppsq\000~\000\000\000YûNpp\000sq\000~\000\000\000YûCpp\000sq\000~\000\017\000Yû8ppsq\000~\000\f\000Yû-sr\000\021java.lang.BooleanÍ rÕúî\002\000\001Z\000\005valuexp\000psr\000 com.sun.msv.grammar.AttributeExp\000\000\000\000\000\000\000\001\002\000\002L\000\003expq\000~\000\003L\000\tnameClassq\000~\000\001xq\000~\000\004\000Yû*q\000~\000\026psr\0002com.sun.msv.grammar.Expression$AnyStringExpression\000\000\000\000\000\000\000\001\002\000\000xq\000~\000\004\000\000\000\bsq\000~\000\025\001psr\000 com.sun.msv.grammar.AnyNameClass\000\000\000\000\000\000\000\001\002\000\000xr\000\035com.sun.msv.grammar.NameClass\000\000\000\000\000\000\000\001\002\000\000xpsr\0000com.sun.msv.grammar.Expression$EpsilonExpression\000\000\000\000\000\000\000\001\002\000\000xq\000~\000\004\000\000\000\tq\000~\000\033psr\000#com.sun.msv.grammar.SimpleNameClass\000\000\000\000\000\000\000\001\002\000\002L\000\tlocalNamet\000\022Ljava/lang/String;L\000\fnamespaceURIq\000~\000\"xq\000~\000\035t\000Acom.eoos.gm.tis2web.registration.standalone.xml.AUTHORIZATIONTypet\000+http://java.sun.com/jaxb/xjc/dummy-elementssq\000~\000!t\000\rAUTHORIZATIONt\000\000sq\000~\000\000\000YûCpp\000sq\000~\000\017\000Yû8ppsq\000~\000\f\000Yû-q\000~\000\026psq\000~\000\027\000Yû*q\000~\000\026pq\000~\000\032q\000~\000\036q\000~\000 sq\000~\000!t\000=com.eoos.gm.tis2web.registration.standalone.xml.AUTHORIZATIONq\000~\000%sq\000~\000\027\001÷ppsr\000\033com.sun.msv.grammar.DataExp\000\000\000\000\000\000\000\001\002\000\003L\000\002dtt\000\037Lorg/relaxng/datatype/Datatype;L\000\006exceptq\000~\000\003L\000\004namet\000\035Lcom/sun/msv/util/StringPair;xq\000~\000\004\000OÁfppsr\000#com.sun.msv.datatype.xsd.StringType\000\000\000\000\000\000\000\001\002\000\001Z\000\risAlwaysValidxr\000*com.sun.msv.datatype.xsd.BuiltinAtomicType\000\000\000\000\000\000\000\001\002\000\000xr\000%com.sun.msv.datatype.xsd.ConcreteType\000\000\000\000\000\000\000\001\002\000\000xr\000'com.sun.msv.datatype.xsd.XSDatatypeImpl\000\000\000\000\000\000\000\001\002\000\003L\000\fnamespaceUriq\000~\000\"L\000\btypeNameq\000~\000\"L\000\nwhiteSpacet\000.Lcom/sun/msv/datatype/xsd/WhiteSpaceProcessor;xpt\000 http://www.w3.org/2001/XMLSchemat\000\006stringsr\000.com.sun.msv.datatype.xsd.WhiteSpaceProcessor$1\023JMoIÛ¤G\002\000\000xr\000,com.sun.msv.datatype.xsd.WhiteSpaceProcessor\000\000\000\000\000\000\000\001\002\000\000xp\001sr\0000com.sun.msv.grammar.Expression$NullSetExpression\000\000\000\000\000\000\000\001\002\000\000xq\000~\000\004\000\000\000\nppsr\000\033com.sun.msv.util.StringPairÐt\036jB \002\000\002L\000\tlocalNameq\000~\000\"L\000\fnamespaceURIq\000~\000\"xpq\000~\000;q\000~\000:sq\000~\000!t\000\005EMAILq\000~\000(sq\000~\000\027\001­t{ppq\000~\0003sq\000~\000!t\000\004FORMq\000~\000(sq\000~\000\017\000ç¯Vppsq\000~\000\027\000ç¯Kq\000~\000\026psr\000\034com.sun.msv.grammar.ValueExp\000\000\000\000\000\000\000\001\002\000\003L\000\002dtq\000~\0001L\000\004nameq\000~\0002L\000\005valuet\000\022Ljava/lang/Object;xq\000~\000\004\000OÁ]ppq\000~\0009q\000~\000Bt\000\0031.0sq\000~\000!t\000\007Versionq\000~\000(q\000~\000 sq\000~\000!t\000\007TIS2WEBq\000~\000(sr\000\"com.sun.msv.grammar.ExpressionPool\000\000\000\000\000\000\000\001\002\000\001L\000\bexpTablet\000/Lcom/sun/msv/grammar/ExpressionPool$ClosedHash;xpsr\000-com.sun.msv.grammar.ExpressionPool$ClosedHash×jÐNïèí\034\002\000\004I\000\005countI\000\tthresholdL\000\006parentq\000~\000S[\000\005tablet\000![Lcom/sun/msv/grammar/Expression;xp\000\000\000\n\000\000\0009pur\000![Lcom.sun.msv.grammar.Expression;Ö8DÃ]­§\n\002\000\000xp\000\000\000¿ppppppppq\000~\000\020ppq\000~\000\016pppppppppppppppppppppppppppppppppppppppppppppppppppppppppppq\000~\000\024q\000~\000+pppppppppq\000~\000\023q\000~\000*ppppq\000~\000\tppppppppppppppppppppppppppppq\000~\000\nppppppppppppppppppppppppppppppppppppq\000~\000\013ppq\000~\000Hppppppppppppppppppppppppppppppppp");
/*     */ 
/*     */   
/*     */   public String ____jaxb_ri____getNamespaceURI() {
/*  19 */     return "";
/*     */   }
/*     */   
/*     */   public String ____jaxb_ri____getLocalName() {
/*  23 */     return "TIS2WEB";
/*     */   }
/*     */   
/*     */   private static final Class PRIMARY_INTERFACE_CLASS() {
/*  27 */     return TIS2WEB.class;
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
/*  39 */     context.startElement("", "TIS2WEB");
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
/*  53 */     return TIS2WEB.class;
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
/*  67 */       return TIS2WEBImpl.this;
/*     */     }
/*     */     
/*     */     public void enterElement(String ___uri, String ___local, Attributes __atts) throws UnreportedException {
/*  71 */       switch (this.state) {
/*     */         case 3:
/*  73 */           revertToParentFromEnterElement(___uri, ___local, __atts);
/*     */           return;
/*     */         case 0:
/*  76 */           if ("" == ___uri && "TIS2WEB" == ___local) {
/*  77 */             this.context.pushAttributes(__atts);
/*  78 */             goto1();
/*     */             return;
/*     */           } 
/*     */           break;
/*     */         case 1:
/*  83 */           if ("" == ___uri && "AUTHORIZATION" == ___local) {
/*  84 */             TIS2WEBImpl.this.getClass(); spawnSuperClassFromEnterElement(new TIS2WEBTypeImpl.Unmarshaller(TIS2WEBImpl.this, this.context), 2, ___uri, ___local, __atts);
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
/*  98 */           if ("" == ___uri && "TIS2WEB" == ___local) {
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
/*     */         case 1:
/* 114 */           if ("" == ___uri && "FORM" == ___local) {
/* 115 */             TIS2WEBImpl.this.getClass(); spawnSuperClassFromEnterAttribute(new TIS2WEBTypeImpl.Unmarshaller(TIS2WEBImpl.this, this.context), 2, ___uri, ___local);
/*     */             return;
/*     */           } 
/* 118 */           if ("" == ___uri && "EMAIL" == ___local) {
/* 119 */             TIS2WEBImpl.this.getClass(); spawnSuperClassFromEnterAttribute(new TIS2WEBTypeImpl.Unmarshaller(TIS2WEBImpl.this, this.context), 2, ___uri, ___local);
/*     */             return;
/*     */           } 
/* 122 */           if ("" == ___uri && "Version" == ___local) {
/* 123 */             TIS2WEBImpl.this.getClass(); spawnSuperClassFromEnterAttribute(new TIS2WEBTypeImpl.Unmarshaller(TIS2WEBImpl.this, this.context), 2, ___uri, ___local);
/*     */             return;
/*     */           } 
/*     */           break;
/*     */       } 
/* 128 */       super.enterAttribute(___uri, ___local);
/*     */     }
/*     */     
/*     */     public void leaveAttribute(String ___uri, String ___local) throws UnreportedException {
/* 132 */       switch (this.state) {
/*     */         case 3:
/* 134 */           revertToParentFromLeaveAttribute(___uri, ___local);
/*     */           return;
/*     */       } 
/* 137 */       super.leaveAttribute(___uri, ___local);
/*     */     }
/*     */     
/*     */     public void text(String value) throws UnreportedException {
/*     */       try {
/* 142 */         switch (this.state) {
/*     */           case 3:
/* 144 */             revertToParentFromText(value);
/*     */             return;
/*     */         } 
/* 147 */       } catch (RuntimeException e) {
/* 148 */         handleUnexpectedTextException(value, e);
/*     */       } 
/*     */     }
/*     */     
/*     */     public void leaveChild(int nextState) throws UnreportedException {
/* 153 */       switch (nextState) {
/*     */         case 2:
/* 155 */           this.state = 2;
/*     */           return;
/*     */       } 
/* 158 */       super.leaveChild(nextState);
/*     */     }
/*     */ 
/*     */     
/*     */     private void goto1() throws UnreportedException {
/* 163 */       this.state = 1;
/* 164 */       int idx = this.context.getAttribute("", "EMAIL");
/* 165 */       if (idx >= 0) {
/* 166 */         this.context.consumeAttribute(idx);
/*     */         return;
/*     */       } 
/* 169 */       idx = this.context.getAttribute("", "FORM");
/* 170 */       if (idx >= 0) {
/* 171 */         this.context.consumeAttribute(idx);
/*     */         return;
/*     */       } 
/* 174 */       idx = this.context.getAttribute("", "Version");
/* 175 */       if (idx >= 0) {
/* 176 */         this.context.consumeAttribute(idx);
/*     */         return;
/*     */       } 
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\registration\standalone\xml\impl\TIS2WEBImpl.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */