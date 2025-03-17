/*     */ package com.eoos.gm.tis2web.lt.implementation.io.icl.model.xml.impl;
/*     */ import com.eoos.gm.tis2web.lt.implementation.io.icl.model.xml.TransmissionType;
/*     */ import com.sun.msv.verifier.DocumentDeclaration;
/*     */ import com.sun.msv.verifier.regexp.REDocumentDeclaration;
/*     */ import com.sun.xml.bind.marshaller.Util;
/*     */ import com.sun.xml.bind.serializer.XMLSerializer;
/*     */ import com.sun.xml.bind.unmarshaller.ContentHandlerEx;
/*     */ import com.sun.xml.bind.unmarshaller.UnmarshallableObject;
/*     */ import com.sun.xml.bind.unmarshaller.UnmarshallingContext;
/*     */ import com.sun.xml.bind.unmarshaller.UnreportedException;
/*     */ import com.sun.xml.bind.validator.SchemaDeserializer;
/*     */ import com.sun.xml.bind.validator.ValidatableObject;
/*     */ import org.xml.sax.Attributes;
/*     */ import org.xml.sax.SAXException;
/*     */ 
/*     */ public class TransmissionTypeImpl implements TransmissionType, UnmarshallableObject, XMLSerializable, ValidatableObject {
/*  17 */   private static final Grammar schemaFragment = SchemaDeserializer.deserialize("¬í\000\005sr\000\037com.sun.msv.grammar.SequenceExp\000\000\000\000\000\000\000\001\002\000\000xr\000\035com.sun.msv.grammar.BinaryExp\000\000\000\000\000\000\000\001\002\000\002L\000\004exp1t\000 Lcom/sun/msv/grammar/Expression;L\000\004exp2q\000~\000\002xr\000\036com.sun.msv.grammar.Expressionø\030èN5~O\002\000\003I\000\016cachedHashCodeL\000\023epsilonReducibilityt\000\023Ljava/lang/Boolean;L\000\013expandedExpq\000~\000\002xp\003Êýdppsr\000'com.sun.msv.grammar.trex.ElementPattern\000\000\000\000\000\000\000\001\002\000\001L\000\tnameClasst\000\037Lcom/sun/msv/grammar/NameClass;xr\000\036com.sun.msv.grammar.ElementExp\000\000\000\000\000\000\000\001\002\000\002Z\000\032ignoreUndeclaredAttributesL\000\fcontentModelq\000~\000\002xq\000~\000\003\001å~ªpp\000sr\000\033com.sun.msv.grammar.DataExp\000\000\000\000\000\000\000\001\002\000\003L\000\002dtt\000\037Lorg/relaxng/datatype/Datatype;L\000\006exceptq\000~\000\002L\000\004namet\000\035Lcom/sun/msv/util/StringPair;xq\000~\000\003\001å~ppsr\000#com.sun.msv.datatype.xsd.StringType\000\000\000\000\000\000\000\001\002\000\001Z\000\risAlwaysValidxr\000*com.sun.msv.datatype.xsd.BuiltinAtomicType\000\000\000\000\000\000\000\001\002\000\000xr\000%com.sun.msv.datatype.xsd.ConcreteType\000\000\000\000\000\000\000\001\002\000\000xr\000'com.sun.msv.datatype.xsd.XSDatatypeImpl\000\000\000\000\000\000\000\001\002\000\003L\000\fnamespaceUrit\000\022Ljava/lang/String;L\000\btypeNameq\000~\000\022L\000\nwhiteSpacet\000.Lcom/sun/msv/datatype/xsd/WhiteSpaceProcessor;xpt\000 http://www.w3.org/2001/XMLSchemat\000\006stringsr\000.com.sun.msv.datatype.xsd.WhiteSpaceProcessor$1\023JMoIÛ¤G\002\000\000xr\000,com.sun.msv.datatype.xsd.WhiteSpaceProcessor\000\000\000\000\000\000\000\001\002\000\000xp\001sr\0000com.sun.msv.grammar.Expression$NullSetExpression\000\000\000\000\000\000\000\001\002\000\000xq\000~\000\003\000\000\000\nppsr\000\033com.sun.msv.util.StringPairÐt\036jB \002\000\002L\000\tlocalNameq\000~\000\022L\000\fnamespaceURIq\000~\000\022xpq\000~\000\026q\000~\000\025sr\000#com.sun.msv.grammar.SimpleNameClass\000\000\000\000\000\000\000\001\002\000\002L\000\tlocalNameq\000~\000\022L\000\fnamespaceURIq\000~\000\022xr\000\035com.sun.msv.grammar.NameClass\000\000\000\000\000\000\000\001\002\000\000xpt\000\005Labelt\000\000sr\000\035com.sun.msv.grammar.ChoiceExp\000\000\000\000\000\000\000\001\002\000\000xq\000~\000\001\001å~µppsq\000~\000\006\001å~ªsr\000\021java.lang.BooleanÍ rÕúî\002\000\001Z\000\005valuexp\000p\000q\000~\000\rsq\000~\000\036t\000\005Valueq\000~\000\"sr\0000com.sun.msv.grammar.Expression$EpsilonExpression\000\000\000\000\000\000\000\001\002\000\000xq\000~\000\003\000\000\000\tsq\000~\000&\001psr\000\"com.sun.msv.grammar.ExpressionPool\000\000\000\000\000\000\000\001\002\000\001L\000\bexpTablet\000/Lcom/sun/msv/grammar/ExpressionPool$ClosedHash;xpsr\000-com.sun.msv.grammar.ExpressionPool$ClosedHash×jÐNïèí\034\002\000\004I\000\005countI\000\tthresholdL\000\006parentq\000~\000.[\000\005tablet\000![Lcom/sun/msv/grammar/Expression;xp\000\000\000\002\000\000\0009pur\000![Lcom.sun.msv.grammar.Expression;Ö8DÃ]­§\n\002\000\000xp\000\000\000¿ppppppppppppppppppppppppppppppppppppppppppppq\000~\000$pppppppppppppppppppppppppppppppppppppq\000~\000\005pppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppp"); protected String _Value;
/*     */   protected String _Label;
/*     */   
/*     */   private static final Class PRIMARY_INTERFACE_CLASS() {
/*  21 */     return TransmissionType.class;
/*     */   }
/*     */   
/*     */   public String getValue() {
/*  25 */     return this._Value;
/*     */   }
/*     */   
/*     */   public void setValue(String value) {
/*  29 */     this._Value = value;
/*     */   }
/*     */   
/*     */   public String getLabel() {
/*  33 */     return this._Label;
/*     */   }
/*     */   
/*     */   public void setLabel(String value) {
/*  37 */     this._Label = value;
/*     */   }
/*     */   
/*     */   public ContentHandlerEx getUnmarshaller(UnmarshallingContext context) {
/*  41 */     return new Unmarshaller(context);
/*     */   }
/*     */   
/*     */   public Class getPrimaryInterfaceClass() {
/*  45 */     return PRIMARY_INTERFACE_CLASS();
/*     */   }
/*     */   
/*     */   public void serializeElements(XMLSerializer context) throws SAXException {
/*  49 */     context.startElement("", "Label");
/*  50 */     context.endAttributes();
/*     */     try {
/*  52 */       context.text(this._Label);
/*  53 */     } catch (Exception e) {
/*  54 */       Util.handlePrintConversionException(this, e, context);
/*     */     } 
/*  56 */     context.endElement();
/*  57 */     if (this._Value != null) {
/*  58 */       context.startElement("", "Value");
/*  59 */       context.endAttributes();
/*     */       try {
/*  61 */         context.text(this._Value);
/*  62 */       } catch (Exception e) {
/*  63 */         Util.handlePrintConversionException(this, e, context);
/*     */       } 
/*  65 */       context.endElement();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void serializeAttributes(XMLSerializer context) throws SAXException {}
/*     */ 
/*     */   
/*     */   public void serializeAttributeBodies(XMLSerializer context) throws SAXException {}
/*     */   
/*     */   public Class getPrimaryInterface() {
/*  76 */     return TransmissionType.class;
/*     */   }
/*     */   
/*     */   public DocumentDeclaration createRawValidator() {
/*  80 */     return (DocumentDeclaration)new REDocumentDeclaration(schemaFragment);
/*     */   }
/*     */   
/*     */   public class Unmarshaller
/*     */     extends ContentHandlerEx {
/*     */     public Unmarshaller(UnmarshallingContext context) {
/*  86 */       super(context, "------");
/*     */     }
/*     */     
/*     */     protected UnmarshallableObject owner() {
/*  90 */       return TransmissionTypeImpl.this;
/*     */     }
/*     */     
/*     */     public void enterElement(String ___uri, String ___local, Attributes __atts) throws UnreportedException {
/*  94 */       switch (this.state) {
/*     */         case 3:
/*  96 */           if ("" == ___uri && "Value" == ___local) {
/*  97 */             this.context.pushAttributes(__atts);
/*  98 */             this.state = 4;
/*     */             return;
/*     */           } 
/* 101 */           revertToParentFromEnterElement(___uri, ___local, __atts);
/*     */           return;
/*     */         case 0:
/* 104 */           if ("" == ___uri && "Label" == ___local) {
/* 105 */             this.context.pushAttributes(__atts);
/* 106 */             this.state = 1;
/*     */             return;
/*     */           } 
/*     */           break;
/*     */       } 
/* 111 */       super.enterElement(___uri, ___local, __atts);
/*     */     }
/*     */     
/*     */     public void leaveElement(String ___uri, String ___local) throws UnreportedException {
/* 115 */       switch (this.state) {
/*     */         case 5:
/* 117 */           if ("" == ___uri && "Value" == ___local) {
/* 118 */             this.context.popAttributes();
/* 119 */             this.state = 3;
/*     */             return;
/*     */           } 
/*     */           break;
/*     */         case 3:
/* 124 */           revertToParentFromLeaveElement(___uri, ___local);
/*     */           return;
/*     */         case 2:
/* 127 */           if ("" == ___uri && "Label" == ___local) {
/* 128 */             this.context.popAttributes();
/* 129 */             this.state = 3;
/*     */             return;
/*     */           } 
/*     */           break;
/*     */       } 
/* 134 */       super.leaveElement(___uri, ___local);
/*     */     }
/*     */     
/*     */     public void enterAttribute(String ___uri, String ___local) throws UnreportedException {
/* 138 */       switch (this.state) {
/*     */         case 3:
/* 140 */           revertToParentFromEnterAttribute(___uri, ___local);
/*     */           return;
/*     */       } 
/* 143 */       super.enterAttribute(___uri, ___local);
/*     */     }
/*     */     
/*     */     public void leaveAttribute(String ___uri, String ___local) throws UnreportedException {
/* 147 */       switch (this.state) {
/*     */         case 3:
/* 149 */           revertToParentFromLeaveAttribute(___uri, ___local);
/*     */           return;
/*     */       } 
/* 152 */       super.leaveAttribute(___uri, ___local);
/*     */     }
/*     */     
/*     */     public void text(String value) throws UnreportedException {
/*     */       try {
/* 157 */         switch (this.state) {
/*     */           case 3:
/* 159 */             revertToParentFromText(value);
/*     */             return;
/*     */           case 4:
/*     */             try {
/* 163 */               TransmissionTypeImpl.this._Value = value;
/* 164 */             } catch (Exception e) {
/* 165 */               handleParseConversionException(e);
/*     */             } 
/* 167 */             this.state = 5;
/*     */             return;
/*     */           case 1:
/*     */             try {
/* 171 */               TransmissionTypeImpl.this._Label = value;
/* 172 */             } catch (Exception e) {
/* 173 */               handleParseConversionException(e);
/*     */             } 
/* 175 */             this.state = 2;
/*     */             return;
/*     */         } 
/* 178 */       } catch (RuntimeException e) {
/* 179 */         handleUnexpectedTextException(value, e);
/*     */       } 
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\lt\implementation\io\icl\model\xml\impl\TransmissionTypeImpl.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */