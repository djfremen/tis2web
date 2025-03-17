/*     */ package com.eoos.gm.tis2web.lt.implementation.io.icl.model.xml.impl;
/*     */ import com.eoos.gm.tis2web.lt.implementation.io.icl.model.xml.Position;
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
/*     */ public class PositionImpl extends PositionTypeImpl implements Position, RIElement, UnmarshallableObject, XMLSerializable, ValidatableObject {
/*  15 */   private static final Grammar schemaFragment = SchemaDeserializer.deserialize("¬í\000\005sr\000'com.sun.msv.grammar.trex.ElementPattern\000\000\000\000\000\000\000\001\002\000\001L\000\tnameClasst\000\037Lcom/sun/msv/grammar/NameClass;xr\000\036com.sun.msv.grammar.ElementExp\000\000\000\000\000\000\000\001\002\000\002Z\000\032ignoreUndeclaredAttributesL\000\fcontentModelt\000 Lcom/sun/msv/grammar/Expression;xr\000\036com.sun.msv.grammar.Expressionø\030èN5~O\002\000\003I\000\016cachedHashCodeL\000\023epsilonReducibilityt\000\023Ljava/lang/Boolean;L\000\013expandedExpq\000~\000\003xp\røÍpp\000sr\000\037com.sun.msv.grammar.SequenceExp\000\000\000\000\000\000\000\001\002\000\000xr\000\035com.sun.msv.grammar.BinaryExp\000\000\000\000\000\000\000\001\002\000\002L\000\004exp1q\000~\000\003L\000\004exp2q\000~\000\003xq\000~\000\004\røÂppsq\000~\000\007\n*Nãppsq\000~\000\007\b\n\rppsq\000~\000\007\005êÃ7ppsq\000~\000\007\004\005D}ppsr\000\035com.sun.msv.grammar.ChoiceExp\000\000\000\000\000\000\000\001\002\000\000xq\000~\000\b\002\037ÅÎppsq\000~\000\016\002\037ÅÃsr\000\021java.lang.BooleanÍ rÕúî\002\000\001Z\000\005valuexp\000psq\000~\000\000\001\017âæq\000~\000\022p\000sq\000~\000\000\001\017âÛpp\000sq\000~\000\016\001\017âÐppsr\000 com.sun.msv.grammar.OneOrMoreExp\000\000\000\000\000\000\000\001\002\000\000xr\000\034com.sun.msv.grammar.UnaryExp\000\000\000\000\000\000\000\001\002\000\001L\000\003expq\000~\000\003xq\000~\000\004\001\017âÅq\000~\000\022psr\000 com.sun.msv.grammar.AttributeExp\000\000\000\000\000\000\000\001\002\000\002L\000\003expq\000~\000\003L\000\tnameClassq\000~\000\001xq\000~\000\004\001\017âÂq\000~\000\022psr\0002com.sun.msv.grammar.Expression$AnyStringExpression\000\000\000\000\000\000\000\001\002\000\000xq\000~\000\004\000\000\000\bsq\000~\000\021\001psr\000 com.sun.msv.grammar.AnyNameClass\000\000\000\000\000\000\000\001\002\000\000xr\000\035com.sun.msv.grammar.NameClass\000\000\000\000\000\000\000\001\002\000\000xpsr\0000com.sun.msv.grammar.Expression$EpsilonExpression\000\000\000\000\000\000\000\001\002\000\000xq\000~\000\004\000\000\000\tq\000~\000\035psr\000#com.sun.msv.grammar.SimpleNameClass\000\000\000\000\000\000\000\001\002\000\002L\000\tlocalNamet\000\022Ljava/lang/String;L\000\fnamespaceURIq\000~\000$xq\000~\000\037t\0007com.eoos.gm.tis2web.lt.io.icl.model.xml.StdFootnoteTypet\000+http://java.sun.com/jaxb/xjc/dummy-elementssq\000~\000#t\000\013StdFootnotet\000\000sq\000~\000\000\001\017âÛq\000~\000\022p\000sq\000~\000\016\001\017âÐppsq\000~\000\026\001\017âÅq\000~\000\022psq\000~\000\031\001\017âÂq\000~\000\022pq\000~\000\034q\000~\000 q\000~\000\"sq\000~\000#t\0003com.eoos.gm.tis2web.lt.io.icl.model.xml.StdFootnoteq\000~\000'q\000~\000\"sq\000~\000\000\001å~ªpp\000sr\000\033com.sun.msv.grammar.DataExp\000\000\000\000\000\000\000\001\002\000\003L\000\002dtt\000\037Lorg/relaxng/datatype/Datatype;L\000\006exceptq\000~\000\003L\000\004namet\000\035Lcom/sun/msv/util/StringPair;xq\000~\000\004\001å~ppsr\000#com.sun.msv.datatype.xsd.StringType\000\000\000\000\000\000\000\001\002\000\001Z\000\risAlwaysValidxr\000*com.sun.msv.datatype.xsd.BuiltinAtomicType\000\000\000\000\000\000\000\001\002\000\000xr\000%com.sun.msv.datatype.xsd.ConcreteType\000\000\000\000\000\000\000\001\002\000\000xr\000'com.sun.msv.datatype.xsd.XSDatatypeImpl\000\000\000\000\000\000\000\001\002\000\003L\000\fnamespaceUriq\000~\000$L\000\btypeNameq\000~\000$L\000\nwhiteSpacet\000.Lcom/sun/msv/datatype/xsd/WhiteSpaceProcessor;xpt\000 http://www.w3.org/2001/XMLSchemat\000\006stringsr\000.com.sun.msv.datatype.xsd.WhiteSpaceProcessor$1\023JMoIÛ¤G\002\000\000xr\000,com.sun.msv.datatype.xsd.WhiteSpaceProcessor\000\000\000\000\000\000\000\001\002\000\000xp\001sr\0000com.sun.msv.grammar.Expression$NullSetExpression\000\000\000\000\000\000\000\001\002\000\000xq\000~\000\004\000\000\000\nppsr\000\033com.sun.msv.util.StringPairÐt\036jB \002\000\002L\000\tlocalNameq\000~\000$L\000\fnamespaceURIq\000~\000$xpq\000~\000=q\000~\000<sq\000~\000#t\000\004Textq\000~\000*sq\000~\000\016\001å~µppsq\000~\000\000\001å~ªq\000~\000\022p\000q\000~\0005sq\000~\000#t\000\007Commentq\000~\000*q\000~\000\"sq\000~\000\016\002\037ÅÑppsq\000~\000\026\002\037ÅÆq\000~\000\022psq\000~\000\016\002\037ÅÃq\000~\000\022psq\000~\000\000\001\017âæq\000~\000\022p\000sq\000~\000\000\001\017âÛpp\000sq\000~\000\016\001\017âÐppsq\000~\000\026\001\017âÅq\000~\000\022psq\000~\000\031\001\017âÂq\000~\000\022pq\000~\000\034q\000~\000 q\000~\000\"sq\000~\000#t\0004com.eoos.gm.tis2web.lt.io.icl.model.xml.FootnoteTypeq\000~\000'sq\000~\000#t\000\bFootnoteq\000~\000*sq\000~\000\000\001\017âÛq\000~\000\022p\000sq\000~\000\016\001\017âÐppsq\000~\000\026\001\017âÅq\000~\000\022psq\000~\000\031\001\017âÂq\000~\000\022pq\000~\000\034q\000~\000 q\000~\000\"sq\000~\000#t\0000com.eoos.gm.tis2web.lt.io.icl.model.xml.Footnoteq\000~\000'q\000~\000\"sq\000~\000\016\002\037ÅÑppsq\000~\000\026\002\037ÅÆq\000~\000\022psq\000~\000\016\002\037ÅÃq\000~\000\022psq\000~\000\000\001\017âæq\000~\000\022p\000sq\000~\000\000\001\017âÛpp\000sq\000~\000\016\001\017âÐppsq\000~\000\026\001\017âÅq\000~\000\022psq\000~\000\031\001\017âÂq\000~\000\022pq\000~\000\034q\000~\000 q\000~\000\"sq\000~\000#t\0005com.eoos.gm.tis2web.lt.io.icl.model.xml.ParameterTypeq\000~\000'sq\000~\000#t\000\tParameterq\000~\000*sq\000~\000\000\001\017âÛq\000~\000\022p\000sq\000~\000\016\001\017âÐppsq\000~\000\026\001\017âÅq\000~\000\022psq\000~\000\031\001\017âÂq\000~\000\022pq\000~\000\034q\000~\000 q\000~\000\"sq\000~\000#t\0001com.eoos.gm.tis2web.lt.io.icl.model.xml.Parameterq\000~\000'q\000~\000\"sq\000~\000\031\003V©Úppq\000~\0005sq\000~\000#t\000\002nrq\000~\000*sq\000~\000#t\000\bPositionq\000~\000*sr\000\"com.sun.msv.grammar.ExpressionPool\000\000\000\000\000\000\000\001\002\000\001L\000\bexpTablet\000/Lcom/sun/msv/grammar/ExpressionPool$ClosedHash;xpsr\000-com.sun.msv.grammar.ExpressionPool$ClosedHash×jÐNïèí\034\002\000\004I\000\005countI\000\tthresholdL\000\006parentq\000~\000u[\000\005tablet\000![Lcom/sun/msv/grammar/Expression;xp\000\000\000\032\000\000\0009pur\000![Lcom.sun.msv.grammar.Expression;Ö8DÃ]­§\n\002\000\000xp\000\000\000¿ppppppppppppq\000~\000\npppppppppppppppppppppppppppppppq\000~\000Gpppppppppppppppppppppppppppppppppppppppppq\000~\000\020q\000~\000Mq\000~\000_q\000~\000Lq\000~\000^ppppppq\000~\000\017q\000~\000\013pq\000~\000Kq\000~\000]ppppppppq\000~\000\030q\000~\000-q\000~\000Qq\000~\000Yq\000~\000cq\000~\000kq\000~\000\tppppq\000~\000\025q\000~\000,q\000~\000Pq\000~\000Xq\000~\000bq\000~\000jppppppppq\000~\000\rppppppppppppppppppppppppppppppppppppppppppppppppq\000~\000\fpppppp");
/*     */ 
/*     */   
/*     */   public String ____jaxb_ri____getNamespaceURI() {
/*  19 */     return "";
/*     */   }
/*     */   
/*     */   public String ____jaxb_ri____getLocalName() {
/*  23 */     return "Position";
/*     */   }
/*     */   
/*     */   private static final Class PRIMARY_INTERFACE_CLASS() {
/*  27 */     return Position.class;
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
/*  39 */     context.startElement("", "Position");
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
/*  53 */     return Position.class;
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
/*  67 */       return PositionImpl.this;
/*     */     }
/*     */     
/*     */     public void enterElement(String ___uri, String ___local, Attributes __atts) throws UnreportedException {
/*  71 */       switch (this.state) {
/*     */         case 1:
/*  73 */           if ("" == ___uri && "Text" == ___local) {
/*  74 */             PositionImpl.this.getClass(); spawnSuperClassFromEnterElement(new PositionTypeImpl.Unmarshaller(PositionImpl.this, this.context), 2, ___uri, ___local, __atts);
/*     */             return;
/*     */           } 
/*  77 */           if ("" == ___uri && "StdFootnote" == ___local) {
/*  78 */             PositionImpl.this.getClass(); spawnSuperClassFromEnterElement(new PositionTypeImpl.Unmarshaller(PositionImpl.this, this.context), 2, ___uri, ___local, __atts);
/*     */             return;
/*     */           } 
/*     */           break;
/*     */         case 0:
/*  83 */           if ("" == ___uri && "Position" == ___local) {
/*  84 */             this.context.pushAttributes(__atts);
/*  85 */             goto1();
/*     */             return;
/*     */           } 
/*     */           break;
/*     */         case 3:
/*  90 */           revertToParentFromEnterElement(___uri, ___local, __atts);
/*     */           return;
/*     */       } 
/*  93 */       super.enterElement(___uri, ___local, __atts);
/*     */     }
/*     */     
/*     */     public void leaveElement(String ___uri, String ___local) throws UnreportedException {
/*  97 */       switch (this.state) {
/*     */         case 2:
/*  99 */           if ("" == ___uri && "Position" == ___local) {
/* 100 */             this.context.popAttributes();
/* 101 */             this.state = 3;
/*     */             return;
/*     */           } 
/*     */           break;
/*     */         case 3:
/* 106 */           revertToParentFromLeaveElement(___uri, ___local);
/*     */           return;
/*     */       } 
/* 109 */       super.leaveElement(___uri, ___local);
/*     */     }
/*     */     
/*     */     public void enterAttribute(String ___uri, String ___local) throws UnreportedException {
/* 113 */       switch (this.state) {
/*     */         case 1:
/* 115 */           if ("" == ___uri && "nr" == ___local) {
/* 116 */             PositionImpl.this.getClass(); spawnSuperClassFromEnterAttribute(new PositionTypeImpl.Unmarshaller(PositionImpl.this, this.context), 2, ___uri, ___local);
/*     */             return;
/*     */           } 
/*     */           break;
/*     */         case 3:
/* 121 */           revertToParentFromEnterAttribute(___uri, ___local);
/*     */           return;
/*     */       } 
/* 124 */       super.enterAttribute(___uri, ___local);
/*     */     }
/*     */     
/*     */     public void leaveAttribute(String ___uri, String ___local) throws UnreportedException {
/* 128 */       switch (this.state) {
/*     */         case 3:
/* 130 */           revertToParentFromLeaveAttribute(___uri, ___local);
/*     */           return;
/*     */       } 
/* 133 */       super.leaveAttribute(___uri, ___local);
/*     */     }
/*     */     
/*     */     public void text(String value) throws UnreportedException {
/*     */       try {
/* 138 */         switch (this.state) {
/*     */           case 3:
/* 140 */             revertToParentFromText(value);
/*     */             return;
/*     */         } 
/* 143 */       } catch (RuntimeException e) {
/* 144 */         handleUnexpectedTextException(value, e);
/*     */       } 
/*     */     }
/*     */     
/*     */     public void leaveChild(int nextState) throws UnreportedException {
/* 149 */       switch (nextState) {
/*     */         case 2:
/* 151 */           this.state = 2;
/*     */           return;
/*     */       } 
/* 154 */       super.leaveChild(nextState);
/*     */     }
/*     */ 
/*     */     
/*     */     private void goto1() throws UnreportedException {
/* 159 */       this.state = 1;
/* 160 */       int idx = this.context.getAttribute("", "nr");
/* 161 */       if (idx >= 0) {
/* 162 */         this.context.consumeAttribute(idx);
/*     */         return;
/*     */       } 
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\lt\implementation\io\icl\model\xml\impl\PositionImpl.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */