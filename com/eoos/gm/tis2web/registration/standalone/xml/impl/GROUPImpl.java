/*     */ package com.eoos.gm.tis2web.registration.standalone.xml.impl;
/*     */ import com.eoos.gm.tis2web.registration.standalone.xml.GROUP;
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
/*     */ public class GROUPImpl extends GROUPTypeImpl implements GROUP, RIElement, UnmarshallableObject, XMLSerializable, ValidatableObject {
/*  15 */   private static final Grammar schemaFragment = SchemaDeserializer.deserialize("¬í\000\005sr\000'com.sun.msv.grammar.trex.ElementPattern\000\000\000\000\000\000\000\001\002\000\001L\000\tnameClasst\000\037Lcom/sun/msv/grammar/NameClass;xr\000\036com.sun.msv.grammar.ElementExp\000\000\000\000\000\000\000\001\002\000\002Z\000\032ignoreUndeclaredAttributesL\000\fcontentModelt\000 Lcom/sun/msv/grammar/Expression;xr\000\036com.sun.msv.grammar.Expressionø\030èN5~O\002\000\003I\000\016cachedHashCodeL\000\023epsilonReducibilityt\000\023Ljava/lang/Boolean;L\000\013expandedExpq\000~\000\003xp\002\025¿Rpp\000sr\000 com.sun.msv.grammar.AttributeExp\000\000\000\000\000\000\000\001\002\000\002L\000\003expq\000~\000\003L\000\tnameClassq\000~\000\001xq\000~\000\004\002\025¿Gppsr\000\033com.sun.msv.grammar.DataExp\000\000\000\000\000\000\000\001\002\000\003L\000\002dtt\000\037Lorg/relaxng/datatype/Datatype;L\000\006exceptq\000~\000\003L\000\004namet\000\035Lcom/sun/msv/util/StringPair;xq\000~\000\004\000OÁfppsr\000#com.sun.msv.datatype.xsd.StringType\000\000\000\000\000\000\000\001\002\000\001Z\000\risAlwaysValidxr\000*com.sun.msv.datatype.xsd.BuiltinAtomicType\000\000\000\000\000\000\000\001\002\000\000xr\000%com.sun.msv.datatype.xsd.ConcreteType\000\000\000\000\000\000\000\001\002\000\000xr\000'com.sun.msv.datatype.xsd.XSDatatypeImpl\000\000\000\000\000\000\000\001\002\000\003L\000\fnamespaceUrit\000\022Ljava/lang/String;L\000\btypeNameq\000~\000\021L\000\nwhiteSpacet\000.Lcom/sun/msv/datatype/xsd/WhiteSpaceProcessor;xpt\000 http://www.w3.org/2001/XMLSchemat\000\006stringsr\000.com.sun.msv.datatype.xsd.WhiteSpaceProcessor$1\023JMoIÛ¤G\002\000\000xr\000,com.sun.msv.datatype.xsd.WhiteSpaceProcessor\000\000\000\000\000\000\000\001\002\000\000xp\001sr\0000com.sun.msv.grammar.Expression$NullSetExpression\000\000\000\000\000\000\000\001\002\000\000xq\000~\000\004\000\000\000\nppsr\000\033com.sun.msv.util.StringPairÐt\036jB \002\000\002L\000\tlocalNameq\000~\000\021L\000\fnamespaceURIq\000~\000\021xpq\000~\000\025q\000~\000\024sr\000#com.sun.msv.grammar.SimpleNameClass\000\000\000\000\000\000\000\001\002\000\002L\000\tlocalNameq\000~\000\021L\000\fnamespaceURIq\000~\000\021xr\000\035com.sun.msv.grammar.NameClass\000\000\000\000\000\000\000\001\002\000\000xpt\000\007GroupIDt\000\000sq\000~\000\035t\000\005GROUPq\000~\000!sr\000\"com.sun.msv.grammar.ExpressionPool\000\000\000\000\000\000\000\001\002\000\001L\000\bexpTablet\000/Lcom/sun/msv/grammar/ExpressionPool$ClosedHash;xpsr\000-com.sun.msv.grammar.ExpressionPool$ClosedHash×jÐNïèí\034\002\000\004I\000\005countI\000\tthresholdL\000\006parentq\000~\000%[\000\005tablet\000![Lcom/sun/msv/grammar/Expression;xp\000\000\000\000\000\000\0009pur\000![Lcom.sun.msv.grammar.Expression;Ö8DÃ]­§\n\002\000\000xp\000\000\000¿ppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppp");
/*     */ 
/*     */   
/*     */   public String ____jaxb_ri____getNamespaceURI() {
/*  19 */     return "";
/*     */   }
/*     */   
/*     */   public String ____jaxb_ri____getLocalName() {
/*  23 */     return "GROUP";
/*     */   }
/*     */   
/*     */   private static final Class PRIMARY_INTERFACE_CLASS() {
/*  27 */     return GROUP.class;
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
/*  39 */     context.startElement("", "GROUP");
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
/*  53 */     return GROUP.class;
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
/*  67 */       return GROUPImpl.this;
/*     */     }
/*     */     
/*     */     public void enterElement(String ___uri, String ___local, Attributes __atts) throws UnreportedException {
/*  71 */       switch (this.state) {
/*     */         case 3:
/*  73 */           revertToParentFromEnterElement(___uri, ___local, __atts);
/*     */           return;
/*     */         case 0:
/*  76 */           if ("" == ___uri && "GROUP" == ___local) {
/*  77 */             this.context.pushAttributes(__atts);
/*  78 */             goto1();
/*     */             return;
/*     */           } 
/*     */           break;
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
/*  92 */           if ("" == ___uri && "GROUP" == ___local) {
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
/* 108 */           if ("" == ___uri && "GroupID" == ___local) {
/* 109 */             GROUPImpl.this.getClass(); spawnSuperClassFromEnterAttribute(new GROUPTypeImpl.Unmarshaller(GROUPImpl.this, this.context), 2, ___uri, ___local);
/*     */             return;
/*     */           } 
/*     */           break;
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
/*     */ 
/*     */     
/*     */     private void goto1() throws UnreportedException {
/* 149 */       this.state = 1;
/* 150 */       int idx = this.context.getAttribute("", "GroupID");
/* 151 */       if (idx >= 0) {
/* 152 */         this.context.consumeAttribute(idx);
/*     */         return;
/*     */       } 
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\registration\standalone\xml\impl\GROUPImpl.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */