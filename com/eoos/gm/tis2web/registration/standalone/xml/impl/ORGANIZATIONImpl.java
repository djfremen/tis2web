/*     */ package com.eoos.gm.tis2web.registration.standalone.xml.impl;
/*     */ import com.eoos.gm.tis2web.registration.standalone.xml.ORGANIZATION;
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
/*     */ public class ORGANIZATIONImpl extends ORGANIZATIONTypeImpl implements ORGANIZATION, RIElement, UnmarshallableObject, XMLSerializable, ValidatableObject {
/*  15 */   private static final Grammar schemaFragment = SchemaDeserializer.deserialize("¬í\000\005sr\000'com.sun.msv.grammar.trex.ElementPattern\000\000\000\000\000\000\000\001\002\000\001L\000\tnameClasst\000\037Lcom/sun/msv/grammar/NameClass;xr\000\036com.sun.msv.grammar.ElementExp\000\000\000\000\000\000\000\001\002\000\002Z\000\032ignoreUndeclaredAttributesL\000\fcontentModelt\000 Lcom/sun/msv/grammar/Expression;xr\000\036com.sun.msv.grammar.Expressionø\030èN5~O\002\000\003I\000\016cachedHashCodeL\000\023epsilonReducibilityt\000\023Ljava/lang/Boolean;L\000\013expandedExpq\000~\000\003xp\003WQêpp\000sr\000\037com.sun.msv.grammar.SequenceExp\000\000\000\000\000\000\000\001\002\000\000xr\000\035com.sun.msv.grammar.BinaryExp\000\000\000\000\000\000\000\001\002\000\002L\000\004exp1q\000~\000\003L\000\004exp2q\000~\000\003xq\000~\000\004\003WQßppsq\000~\000\007\002@{Lppsr\000 com.sun.msv.grammar.AttributeExp\000\000\000\000\000\000\000\001\002\000\002L\000\003expq\000~\000\003L\000\tnameClassq\000~\000\001xq\000~\000\004\001\n\017ppsr\000\033com.sun.msv.grammar.DataExp\000\000\000\000\000\000\000\001\002\000\003L\000\002dtt\000\037Lorg/relaxng/datatype/Datatype;L\000\006exceptq\000~\000\003L\000\004namet\000\035Lcom/sun/msv/util/StringPair;xq\000~\000\004\000OÁfppsr\000#com.sun.msv.datatype.xsd.StringType\000\000\000\000\000\000\000\001\002\000\001Z\000\risAlwaysValidxr\000*com.sun.msv.datatype.xsd.BuiltinAtomicType\000\000\000\000\000\000\000\001\002\000\000xr\000%com.sun.msv.datatype.xsd.ConcreteType\000\000\000\000\000\000\000\001\002\000\000xr\000'com.sun.msv.datatype.xsd.XSDatatypeImpl\000\000\000\000\000\000\000\001\002\000\003L\000\fnamespaceUrit\000\022Ljava/lang/String;L\000\btypeNameq\000~\000\025L\000\nwhiteSpacet\000.Lcom/sun/msv/datatype/xsd/WhiteSpaceProcessor;xpt\000 http://www.w3.org/2001/XMLSchemat\000\006stringsr\000.com.sun.msv.datatype.xsd.WhiteSpaceProcessor$1\023JMoIÛ¤G\002\000\000xr\000,com.sun.msv.datatype.xsd.WhiteSpaceProcessor\000\000\000\000\000\000\000\001\002\000\000xp\001sr\0000com.sun.msv.grammar.Expression$NullSetExpression\000\000\000\000\000\000\000\001\002\000\000xq\000~\000\004\000\000\000\nppsr\000\033com.sun.msv.util.StringPairÐt\036jB \002\000\002L\000\tlocalNameq\000~\000\025L\000\fnamespaceURIq\000~\000\025xpq\000~\000\031q\000~\000\030sr\000#com.sun.msv.grammar.SimpleNameClass\000\000\000\000\000\000\000\001\002\000\002L\000\tlocalNameq\000~\000\025L\000\fnamespaceURIq\000~\000\025xr\000\035com.sun.msv.grammar.NameClass\000\000\000\000\000\000\000\001\002\000\000xpt\000\004FORMt\000\000sq\000~\000\013\0015á8ppq\000~\000\020sq\000~\000!t\000\005EMAILq\000~\000%sq\000~\000\013\001\026Öppsq\000~\000\r\000ãÆ4ppsr\000)com.sun.msv.datatype.xsd.EnumerationFacet\000\000\000\000\000\000\000\001\002\000\001L\000\006valuest\000\017Ljava/util/Set;xr\0009com.sun.msv.datatype.xsd.DataTypeWithValueConstraintFacet\"§RoÊÇT\002\000\000xr\000*com.sun.msv.datatype.xsd.DataTypeWithFacet\000\000\000\000\000\000\000\001\002\000\005Z\000\fisFacetFixedZ\000\022needValueCheckFlagL\000\bbaseTypet\000)Lcom/sun/msv/datatype/xsd/XSDatatypeImpl;L\000\fconcreteTypet\000'Lcom/sun/msv/datatype/xsd/ConcreteType;L\000\tfacetNameq\000~\000\025xq\000~\000\024q\000~\000%psr\000.com.sun.msv.datatype.xsd.WhiteSpaceProcessor$2z9îø,N\005\002\000\000xq\000~\000\033\000\000sr\000$com.sun.msv.datatype.xsd.NmtokenType\000\000\000\000\000\000\000\001\002\000\000xr\000\"com.sun.msv.datatype.xsd.TokenType\000\000\000\000\000\000\000\001\002\000\000xq\000~\000\021q\000~\000\030t\000\007NMTOKENq\000~\0003\000q\000~\0006t\000\013enumerationsr\000\021java.util.HashSetºD¸·4\003\000\000xpw\f\000\000\000\020?@\000\000\000\000\000\002t\000\003NAOt\000\003GMExq\000~\000\036sq\000~\000\037t\000\017NMTOKEN-derivedq\000~\000%sq\000~\000!t\000\016OrganizationIDq\000~\000%sq\000~\000!t\000\fORGANIZATIONq\000~\000%sr\000\"com.sun.msv.grammar.ExpressionPool\000\000\000\000\000\000\000\001\002\000\001L\000\bexpTablet\000/Lcom/sun/msv/grammar/ExpressionPool$ClosedHash;xpsr\000-com.sun.msv.grammar.ExpressionPool$ClosedHash×jÐNïèí\034\002\000\004I\000\005countI\000\tthresholdL\000\006parentq\000~\000D[\000\005tablet\000![Lcom/sun/msv/grammar/Expression;xp\000\000\000\002\000\000\0009pur\000![Lcom.sun.msv.grammar.Expression;Ö8DÃ]­§\n\002\000\000xp\000\000\000¿ppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppq\000~\000\npppppppppppppq\000~\000\tpppppppppppppppppppppppppppppppppppppppppppppppppppppppppp");
/*     */ 
/*     */   
/*     */   public String ____jaxb_ri____getNamespaceURI() {
/*  19 */     return "";
/*     */   }
/*     */   
/*     */   public String ____jaxb_ri____getLocalName() {
/*  23 */     return "ORGANIZATION";
/*     */   }
/*     */   
/*     */   private static final Class PRIMARY_INTERFACE_CLASS() {
/*  27 */     return ORGANIZATION.class;
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
/*  39 */     context.startElement("", "ORGANIZATION");
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
/*  53 */     return ORGANIZATION.class;
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
/*  67 */       return ORGANIZATIONImpl.this;
/*     */     }
/*     */     
/*     */     public void enterElement(String ___uri, String ___local, Attributes __atts) throws UnreportedException {
/*  71 */       switch (this.state) {
/*     */         case 0:
/*  73 */           if ("" == ___uri && "ORGANIZATION" == ___local) {
/*  74 */             this.context.pushAttributes(__atts);
/*  75 */             goto1();
/*     */             return;
/*     */           } 
/*     */           break;
/*     */         case 3:
/*  80 */           revertToParentFromEnterElement(___uri, ___local, __atts);
/*     */           return;
/*     */       } 
/*  83 */       super.enterElement(___uri, ___local, __atts);
/*     */     }
/*     */     
/*     */     public void leaveElement(String ___uri, String ___local) throws UnreportedException {
/*  87 */       switch (this.state) {
/*     */         case 3:
/*  89 */           revertToParentFromLeaveElement(___uri, ___local);
/*     */           return;
/*     */         case 2:
/*  92 */           if ("" == ___uri && "ORGANIZATION" == ___local) {
/*  93 */             this.context.popAttributes();
/*  94 */             this.state = 3;
/*     */             return;
/*     */           } 
/*     */           break;
/*     */       } 
/*  99 */       super.leaveElement(___uri, ___local);
/*     */     }
/*     */     
/*     */     public void enterAttribute(String ___uri, String ___local) throws UnreportedException {
/* 103 */       switch (this.state) {
/*     */         case 3:
/* 105 */           revertToParentFromEnterAttribute(___uri, ___local);
/*     */           return;
/*     */         case 1:
/* 108 */           if ("" == ___uri && "OrganizationID" == ___local) {
/* 109 */             ORGANIZATIONImpl.this.getClass(); spawnSuperClassFromEnterAttribute(new ORGANIZATIONTypeImpl.Unmarshaller(ORGANIZATIONImpl.this, this.context), 2, ___uri, ___local);
/*     */             return;
/*     */           } 
/* 112 */           if ("" == ___uri && "FORM" == ___local) {
/* 113 */             ORGANIZATIONImpl.this.getClass(); spawnSuperClassFromEnterAttribute(new ORGANIZATIONTypeImpl.Unmarshaller(ORGANIZATIONImpl.this, this.context), 2, ___uri, ___local);
/*     */             return;
/*     */           } 
/* 116 */           if ("" == ___uri && "EMAIL" == ___local) {
/* 117 */             ORGANIZATIONImpl.this.getClass(); spawnSuperClassFromEnterAttribute(new ORGANIZATIONTypeImpl.Unmarshaller(ORGANIZATIONImpl.this, this.context), 2, ___uri, ___local);
/*     */             return;
/*     */           } 
/*     */           break;
/*     */       } 
/* 122 */       super.enterAttribute(___uri, ___local);
/*     */     }
/*     */     
/*     */     public void leaveAttribute(String ___uri, String ___local) throws UnreportedException {
/* 126 */       switch (this.state) {
/*     */         case 3:
/* 128 */           revertToParentFromLeaveAttribute(___uri, ___local);
/*     */           return;
/*     */       } 
/* 131 */       super.leaveAttribute(___uri, ___local);
/*     */     }
/*     */     
/*     */     public void text(String value) throws UnreportedException {
/*     */       try {
/* 136 */         switch (this.state) {
/*     */           case 3:
/* 138 */             revertToParentFromText(value);
/*     */             return;
/*     */         } 
/* 141 */       } catch (RuntimeException e) {
/* 142 */         handleUnexpectedTextException(value, e);
/*     */       } 
/*     */     }
/*     */     
/*     */     public void leaveChild(int nextState) throws UnreportedException {
/* 147 */       switch (nextState) {
/*     */         case 2:
/* 149 */           this.state = 2;
/*     */           return;
/*     */       } 
/* 152 */       super.leaveChild(nextState);
/*     */     }
/*     */ 
/*     */     
/*     */     private void goto1() throws UnreportedException {
/* 157 */       this.state = 1;
/* 158 */       int idx = this.context.getAttribute("", "FORM");
/* 159 */       if (idx >= 0) {
/* 160 */         this.context.consumeAttribute(idx);
/*     */         return;
/*     */       } 
/* 163 */       idx = this.context.getAttribute("", "EMAIL");
/* 164 */       if (idx >= 0) {
/* 165 */         this.context.consumeAttribute(idx);
/*     */         return;
/*     */       } 
/* 168 */       idx = this.context.getAttribute("", "OrganizationID");
/* 169 */       if (idx >= 0) {
/* 170 */         this.context.consumeAttribute(idx);
/*     */         return;
/*     */       } 
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\registration\standalone\xml\impl\ORGANIZATIONImpl.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */