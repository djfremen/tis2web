/*     */ package com.eoos.gm.tis2web.registration.standalone.xml.impl;
/*     */ import com.eoos.gm.tis2web.registration.standalone.xml.DESCRIPTIONType;
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
/*     */ public class DESCRIPTIONTypeImpl implements DESCRIPTIONType, UnmarshallableObject, XMLSerializable, ValidatableObject {
/*  17 */   private static final Grammar schemaFragment = SchemaDeserializer.deserialize("¬í\000\005sr\000\037com.sun.msv.grammar.SequenceExp\000\000\000\000\000\000\000\001\002\000\000xr\000\035com.sun.msv.grammar.BinaryExp\000\000\000\000\000\000\000\001\002\000\002L\000\004exp1t\000 Lcom/sun/msv/grammar/Expression;L\000\004exp2q\000~\000\002xr\000\036com.sun.msv.grammar.Expressionø\030èN5~O\002\000\003I\000\016cachedHashCodeL\000\023epsilonReducibilityt\000\023Ljava/lang/Boolean;L\000\013expandedExpq\000~\000\002xp\002¿XDppsr\000 com.sun.msv.grammar.AttributeExp\000\000\000\000\000\000\000\001\002\000\002L\000\003expq\000~\000\002L\000\tnameClasst\000\037Lcom/sun/msv/grammar/NameClass;xq\000~\000\003\001\002þqppsr\000\033com.sun.msv.grammar.DataExp\000\000\000\000\000\000\000\001\002\000\003L\000\002dtt\000\037Lorg/relaxng/datatype/Datatype;L\000\006exceptq\000~\000\002L\000\004namet\000\035Lcom/sun/msv/util/StringPair;xq\000~\000\003\000OÁfppsr\000#com.sun.msv.datatype.xsd.StringType\000\000\000\000\000\000\000\001\002\000\001Z\000\risAlwaysValidxr\000*com.sun.msv.datatype.xsd.BuiltinAtomicType\000\000\000\000\000\000\000\001\002\000\000xr\000%com.sun.msv.datatype.xsd.ConcreteType\000\000\000\000\000\000\000\001\002\000\000xr\000'com.sun.msv.datatype.xsd.XSDatatypeImpl\000\000\000\000\000\000\000\001\002\000\003L\000\fnamespaceUrit\000\022Ljava/lang/String;L\000\btypeNameq\000~\000\021L\000\nwhiteSpacet\000.Lcom/sun/msv/datatype/xsd/WhiteSpaceProcessor;xpt\000 http://www.w3.org/2001/XMLSchemat\000\006stringsr\000.com.sun.msv.datatype.xsd.WhiteSpaceProcessor$1\023JMoIÛ¤G\002\000\000xr\000,com.sun.msv.datatype.xsd.WhiteSpaceProcessor\000\000\000\000\000\000\000\001\002\000\000xp\001sr\0000com.sun.msv.grammar.Expression$NullSetExpression\000\000\000\000\000\000\000\001\002\000\000xq\000~\000\003\000\000\000\nppsr\000\033com.sun.msv.util.StringPairÐt\036jB \002\000\002L\000\tlocalNameq\000~\000\021L\000\fnamespaceURIq\000~\000\021xpq\000~\000\025q\000~\000\024sr\000#com.sun.msv.grammar.SimpleNameClass\000\000\000\000\000\000\000\001\002\000\002L\000\tlocalNameq\000~\000\021L\000\fnamespaceURIq\000~\000\021xr\000\035com.sun.msv.grammar.NameClass\000\000\000\000\000\000\000\001\002\000\000xpt\000\006Localet\000\000sq\000~\000\006\001¼YÎppq\000~\000\fsq\000~\000\035t\000\013Descriptionq\000~\000!sr\000\"com.sun.msv.grammar.ExpressionPool\000\000\000\000\000\000\000\001\002\000\001L\000\bexpTablet\000/Lcom/sun/msv/grammar/ExpressionPool$ClosedHash;xpsr\000-com.sun.msv.grammar.ExpressionPool$ClosedHash×jÐNïèí\034\002\000\004I\000\005countI\000\tthresholdL\000\006parentq\000~\000&[\000\005tablet\000![Lcom/sun/msv/grammar/Expression;xp\000\000\000\001\000\000\0009pur\000![Lcom.sun.msv.grammar.Expression;Ö8DÃ]­§\n\002\000\000xp\000\000\000¿pppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppq\000~\000\005ppppppp"); protected String _Locale;
/*     */   protected String _Description;
/*     */   
/*     */   private static final Class PRIMARY_INTERFACE_CLASS() {
/*  21 */     return DESCRIPTIONType.class;
/*     */   }
/*     */   
/*     */   public String getLocale() {
/*  25 */     return this._Locale;
/*     */   }
/*     */   
/*     */   public void setLocale(String value) {
/*  29 */     this._Locale = value;
/*     */   }
/*     */   
/*     */   public String getDescription() {
/*  33 */     return this._Description;
/*     */   }
/*     */   
/*     */   public void setDescription(String value) {
/*  37 */     this._Description = value;
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
/*     */   
/*     */   public void serializeElements(XMLSerializer context) throws SAXException {}
/*     */   
/*     */   public void serializeAttributes(XMLSerializer context) throws SAXException {
/*  52 */     context.startAttribute("", "Locale");
/*     */     try {
/*  54 */       context.text(this._Locale);
/*  55 */     } catch (Exception e) {
/*  56 */       Util.handlePrintConversionException(this, e, context);
/*     */     } 
/*  58 */     context.endAttribute();
/*  59 */     context.startAttribute("", "Description");
/*     */     try {
/*  61 */       context.text(this._Description);
/*  62 */     } catch (Exception e) {
/*  63 */       Util.handlePrintConversionException(this, e, context);
/*     */     } 
/*  65 */     context.endAttribute();
/*     */   }
/*     */ 
/*     */   
/*     */   public void serializeAttributeBodies(XMLSerializer context) throws SAXException {}
/*     */   
/*     */   public Class getPrimaryInterface() {
/*  72 */     return DESCRIPTIONType.class;
/*     */   }
/*     */   
/*     */   public DocumentDeclaration createRawValidator() {
/*  76 */     return (DocumentDeclaration)new REDocumentDeclaration(schemaFragment);
/*     */   }
/*     */   
/*     */   public class Unmarshaller
/*     */     extends ContentHandlerEx {
/*     */     public Unmarshaller(UnmarshallingContext context) {
/*  82 */       super(context, "-----");
/*     */     }
/*     */     
/*     */     protected UnmarshallableObject owner() {
/*  86 */       return DESCRIPTIONTypeImpl.this;
/*     */     }
/*     */     
/*     */     public void enterElement(String ___uri, String ___local, Attributes __atts) throws UnreportedException {
/*  90 */       switch (this.state) {
/*     */         case 0:
/*  92 */           revertToParentFromEnterElement(___uri, ___local, __atts);
/*     */           return;
/*     */       } 
/*  95 */       super.enterElement(___uri, ___local, __atts);
/*     */     }
/*     */     
/*     */     public void leaveElement(String ___uri, String ___local) throws UnreportedException {
/*  99 */       switch (this.state) {
/*     */         case 0:
/* 101 */           revertToParentFromLeaveElement(___uri, ___local);
/*     */           return;
/*     */       } 
/* 104 */       super.leaveElement(___uri, ___local);
/*     */     }
/*     */     
/*     */     public void enterAttribute(String ___uri, String ___local) throws UnreportedException {
/* 108 */       switch (this.state) {
/*     */         case 0:
/* 110 */           if ("" == ___uri && "Locale" == ___local) {
/* 111 */             this.state = 1;
/*     */             return;
/*     */           } 
/* 114 */           if ("" == ___uri && "Description" == ___local) {
/* 115 */             this.state = 3;
/*     */             return;
/*     */           } 
/* 118 */           revertToParentFromEnterAttribute(___uri, ___local);
/*     */           return;
/*     */       } 
/* 121 */       super.enterAttribute(___uri, ___local);
/*     */     }
/*     */     
/*     */     public void leaveAttribute(String ___uri, String ___local) throws UnreportedException {
/* 125 */       switch (this.state) {
/*     */         case 2:
/* 127 */           if ("" == ___uri && "Locale" == ___local) {
/* 128 */             goto0();
/*     */             return;
/*     */           } 
/*     */           break;
/*     */         case 0:
/* 133 */           revertToParentFromLeaveAttribute(___uri, ___local);
/*     */           return;
/*     */         case 4:
/* 136 */           if ("" == ___uri && "Description" == ___local) {
/* 137 */             goto0();
/*     */             return;
/*     */           } 
/*     */           break;
/*     */       } 
/* 142 */       super.leaveAttribute(___uri, ___local);
/*     */     }
/*     */     
/*     */     public void text(String value) throws UnreportedException {
/*     */       try {
/* 147 */         switch (this.state) {
/*     */           case 0:
/* 149 */             revertToParentFromText(value);
/*     */             return;
/*     */           case 3:
/*     */             try {
/* 153 */               DESCRIPTIONTypeImpl.this._Description = value;
/* 154 */             } catch (Exception e) {
/* 155 */               handleParseConversionException(e);
/*     */             } 
/* 157 */             this.state = 4;
/*     */             return;
/*     */           case 1:
/*     */             try {
/* 161 */               DESCRIPTIONTypeImpl.this._Locale = value;
/* 162 */             } catch (Exception e) {
/* 163 */               handleParseConversionException(e);
/*     */             } 
/* 165 */             this.state = 2;
/*     */             return;
/*     */         } 
/* 168 */       } catch (RuntimeException e) {
/* 169 */         handleUnexpectedTextException(value, e);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     private void goto0() throws UnreportedException {
/* 175 */       this.state = 0;
/* 176 */       int idx = this.context.getAttribute("", "Locale");
/* 177 */       if (idx >= 0) {
/* 178 */         this.context.consumeAttribute(idx);
/*     */         return;
/*     */       } 
/* 181 */       idx = this.context.getAttribute("", "Description");
/* 182 */       if (idx >= 0) {
/* 183 */         this.context.consumeAttribute(idx);
/*     */         return;
/*     */       } 
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\registration\standalone\xml\impl\DESCRIPTIONTypeImpl.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */