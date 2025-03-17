/*     */ package com.eoos.gm.tis2web.lt.implementation.io.icl.model.xml.impl;
/*     */ import com.eoos.gm.tis2web.lt.implementation.io.icl.model.xml.ParameterType;
/*     */ import com.sun.msv.verifier.DocumentDeclaration;
/*     */ import com.sun.xml.bind.marshaller.Util;
/*     */ import com.sun.xml.bind.serializer.XMLSerializer;
/*     */ import com.sun.xml.bind.unmarshaller.ContentHandlerEx;
/*     */ import com.sun.xml.bind.unmarshaller.UnmarshallingContext;
/*     */ import com.sun.xml.bind.unmarshaller.UnreportedException;
/*     */ import com.sun.xml.bind.util.ListImpl;
/*     */ import java.util.List;
/*     */ import org.xml.sax.Attributes;
/*     */ import org.xml.sax.SAXException;
/*     */ 
/*     */ public class ParameterTypeImpl implements ParameterType, UnmarshallableObject, XMLSerializable, ValidatableObject {
/*  15 */   protected ListImpl _Value = new ListImpl(new ArrayList());
/*     */   protected String _Name;
/*  17 */   private static final Grammar schemaFragment = SchemaDeserializer.deserialize("¬í\000\005sr\000\037com.sun.msv.grammar.SequenceExp\000\000\000\000\000\000\000\001\002\000\000xr\000\035com.sun.msv.grammar.BinaryExp\000\000\000\000\000\000\000\001\002\000\002L\000\004exp1t\000 Lcom/sun/msv/grammar/Expression;L\000\004exp2q\000~\000\002xr\000\036com.sun.msv.grammar.Expressionø\030èN5~O\002\000\003I\000\016cachedHashCodeL\000\023epsilonReducibilityt\000\023Ljava/lang/Boolean;L\000\013expandedExpq\000~\000\002xp\003Êý\\ppsr\000'com.sun.msv.grammar.trex.ElementPattern\000\000\000\000\000\000\000\001\002\000\001L\000\tnameClasst\000\037Lcom/sun/msv/grammar/NameClass;xr\000\036com.sun.msv.grammar.ElementExp\000\000\000\000\000\000\000\001\002\000\002Z\000\032ignoreUndeclaredAttributesL\000\fcontentModelq\000~\000\002xq\000~\000\003\001å~ªpp\000sr\000\033com.sun.msv.grammar.DataExp\000\000\000\000\000\000\000\001\002\000\003L\000\002dtt\000\037Lorg/relaxng/datatype/Datatype;L\000\006exceptq\000~\000\002L\000\004namet\000\035Lcom/sun/msv/util/StringPair;xq\000~\000\003\001å~ppsr\000#com.sun.msv.datatype.xsd.StringType\000\000\000\000\000\000\000\001\002\000\001Z\000\risAlwaysValidxr\000*com.sun.msv.datatype.xsd.BuiltinAtomicType\000\000\000\000\000\000\000\001\002\000\000xr\000%com.sun.msv.datatype.xsd.ConcreteType\000\000\000\000\000\000\000\001\002\000\000xr\000'com.sun.msv.datatype.xsd.XSDatatypeImpl\000\000\000\000\000\000\000\001\002\000\003L\000\fnamespaceUrit\000\022Ljava/lang/String;L\000\btypeNameq\000~\000\022L\000\nwhiteSpacet\000.Lcom/sun/msv/datatype/xsd/WhiteSpaceProcessor;xpt\000 http://www.w3.org/2001/XMLSchemat\000\006stringsr\000.com.sun.msv.datatype.xsd.WhiteSpaceProcessor$1\023JMoIÛ¤G\002\000\000xr\000,com.sun.msv.datatype.xsd.WhiteSpaceProcessor\000\000\000\000\000\000\000\001\002\000\000xp\001sr\0000com.sun.msv.grammar.Expression$NullSetExpression\000\000\000\000\000\000\000\001\002\000\000xq\000~\000\003\000\000\000\nppsr\000\033com.sun.msv.util.StringPairÐt\036jB \002\000\002L\000\tlocalNameq\000~\000\022L\000\fnamespaceURIq\000~\000\022xpq\000~\000\026q\000~\000\025sr\000#com.sun.msv.grammar.SimpleNameClass\000\000\000\000\000\000\000\001\002\000\002L\000\tlocalNameq\000~\000\022L\000\fnamespaceURIq\000~\000\022xr\000\035com.sun.msv.grammar.NameClass\000\000\000\000\000\000\000\001\002\000\000xpt\000\004Namet\000\000sr\000 com.sun.msv.grammar.OneOrMoreExp\000\000\000\000\000\000\000\001\002\000\000xr\000\034com.sun.msv.grammar.UnaryExp\000\000\000\000\000\000\000\001\002\000\001L\000\003expq\000~\000\002xq\000~\000\003\001å~­ppsq\000~\000\006\001å~ªpp\000q\000~\000\rsq\000~\000\036t\000\005Valueq\000~\000\"sr\000\"com.sun.msv.grammar.ExpressionPool\000\000\000\000\000\000\000\001\002\000\001L\000\bexpTablet\000/Lcom/sun/msv/grammar/ExpressionPool$ClosedHash;xpsr\000-com.sun.msv.grammar.ExpressionPool$ClosedHash×jÐNïèí\034\002\000\004I\000\005countI\000\tthresholdL\000\006parentq\000~\000*[\000\005tablet\000![Lcom/sun/msv/grammar/Expression;xp\000\000\000\002\000\000\0009pur\000![Lcom.sun.msv.grammar.Expression;Ö8DÃ]­§\n\002\000\000xp\000\000\000¿ppppppppppppppppppppppppppppppppppppq\000~\000%pppppppppppppppppppppppppppppppppppppq\000~\000\005pppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppp");
/*     */ 
/*     */   
/*     */   private static final Class PRIMARY_INTERFACE_CLASS() {
/*  21 */     return ParameterType.class;
/*     */   }
/*     */   
/*     */   public List getValue() {
/*  25 */     return (List)this._Value;
/*     */   }
/*     */   
/*     */   public String getName() {
/*  29 */     return this._Name;
/*     */   }
/*     */   
/*     */   public void setName(String value) {
/*  33 */     this._Name = value;
/*     */   }
/*     */   
/*     */   public ContentHandlerEx getUnmarshaller(UnmarshallingContext context) {
/*  37 */     return new Unmarshaller(context);
/*     */   }
/*     */   
/*     */   public Class getPrimaryInterfaceClass() {
/*  41 */     return PRIMARY_INTERFACE_CLASS();
/*     */   }
/*     */   
/*     */   public void serializeElements(XMLSerializer context) throws SAXException {
/*  45 */     int idx1 = 0;
/*  46 */     int len1 = this._Value.size();
/*  47 */     context.startElement("", "Name");
/*  48 */     context.endAttributes();
/*     */     try {
/*  50 */       context.text(this._Name);
/*  51 */     } catch (Exception e) {
/*  52 */       Util.handlePrintConversionException(this, e, context);
/*     */     } 
/*  54 */     context.endElement();
/*  55 */     while (idx1 != len1) {
/*  56 */       context.startElement("", "Value");
/*  57 */       int idx_1 = idx1;
/*     */       try {
/*  59 */         idx_1++;
/*  60 */       } catch (Exception e) {
/*  61 */         Util.handlePrintConversionException(this, e, context);
/*     */       } 
/*  63 */       context.endAttributes();
/*     */       try {
/*  65 */         context.text((String)this._Value.get(idx1++));
/*  66 */       } catch (Exception e) {
/*  67 */         Util.handlePrintConversionException(this, e, context);
/*     */       } 
/*  69 */       context.endElement();
/*     */     } 
/*     */   }
/*     */   
/*     */   public void serializeAttributes(XMLSerializer context) throws SAXException {
/*  74 */     this._Value.size();
/*     */   }
/*     */   
/*     */   public void serializeAttributeBodies(XMLSerializer context) throws SAXException {
/*  78 */     this._Value.size();
/*     */   }
/*     */   
/*     */   public Class getPrimaryInterface() {
/*  82 */     return ParameterType.class;
/*     */   }
/*     */   
/*     */   public DocumentDeclaration createRawValidator() {
/*  86 */     return (DocumentDeclaration)new REDocumentDeclaration(schemaFragment);
/*     */   }
/*     */   
/*     */   public class Unmarshaller
/*     */     extends ContentHandlerEx {
/*     */     public Unmarshaller(UnmarshallingContext context) {
/*  92 */       super(context, "-------");
/*     */     }
/*     */     
/*     */     protected UnmarshallableObject owner() {
/*  96 */       return ParameterTypeImpl.this;
/*     */     }
/*     */     
/*     */     public void enterElement(String ___uri, String ___local, Attributes __atts) throws UnreportedException {
/* 100 */       switch (this.state) {
/*     */         case 3:
/* 102 */           if ("" == ___uri && "Value" == ___local) {
/* 103 */             this.context.pushAttributes(__atts);
/* 104 */             this.state = 4;
/*     */             return;
/*     */           } 
/*     */           break;
/*     */         case 0:
/* 109 */           if ("" == ___uri && "Name" == ___local) {
/* 110 */             this.context.pushAttributes(__atts);
/* 111 */             this.state = 1;
/*     */             return;
/*     */           } 
/*     */           break;
/*     */         case 6:
/* 116 */           if ("" == ___uri && "Value" == ___local) {
/* 117 */             this.context.pushAttributes(__atts);
/* 118 */             this.state = 4;
/*     */             return;
/*     */           } 
/* 121 */           revertToParentFromEnterElement(___uri, ___local, __atts);
/*     */           return;
/*     */       } 
/* 124 */       super.enterElement(___uri, ___local, __atts);
/*     */     }
/*     */     
/*     */     public void leaveElement(String ___uri, String ___local) throws UnreportedException {
/* 128 */       switch (this.state) {
/*     */         case 2:
/* 130 */           if ("" == ___uri && "Name" == ___local) {
/* 131 */             this.context.popAttributes();
/* 132 */             this.state = 3;
/*     */             return;
/*     */           } 
/*     */           break;
/*     */         case 5:
/* 137 */           if ("" == ___uri && "Value" == ___local) {
/* 138 */             this.context.popAttributes();
/* 139 */             this.state = 6;
/*     */             return;
/*     */           } 
/*     */           break;
/*     */         case 6:
/* 144 */           revertToParentFromLeaveElement(___uri, ___local);
/*     */           return;
/*     */       } 
/* 147 */       super.leaveElement(___uri, ___local);
/*     */     }
/*     */     
/*     */     public void enterAttribute(String ___uri, String ___local) throws UnreportedException {
/* 151 */       switch (this.state) {
/*     */         case 6:
/* 153 */           revertToParentFromEnterAttribute(___uri, ___local);
/*     */           return;
/*     */       } 
/* 156 */       super.enterAttribute(___uri, ___local);
/*     */     }
/*     */     
/*     */     public void leaveAttribute(String ___uri, String ___local) throws UnreportedException {
/* 160 */       switch (this.state) {
/*     */         case 6:
/* 162 */           revertToParentFromLeaveAttribute(___uri, ___local);
/*     */           return;
/*     */       } 
/* 165 */       super.leaveAttribute(___uri, ___local);
/*     */     }
/*     */     
/*     */     public void text(String value) throws UnreportedException {
/*     */       try {
/* 170 */         switch (this.state) {
/*     */           case 4:
/*     */             try {
/* 173 */               ParameterTypeImpl.this._Value.add(value);
/* 174 */             } catch (Exception e) {
/* 175 */               handleParseConversionException(e);
/*     */             } 
/* 177 */             this.state = 5;
/*     */             return;
/*     */           case 1:
/*     */             try {
/* 181 */               ParameterTypeImpl.this._Name = value;
/* 182 */             } catch (Exception e) {
/* 183 */               handleParseConversionException(e);
/*     */             } 
/* 185 */             this.state = 2;
/*     */             return;
/*     */           case 6:
/* 188 */             revertToParentFromText(value);
/*     */             return;
/*     */         } 
/* 191 */       } catch (RuntimeException e) {
/* 192 */         handleUnexpectedTextException(value, e);
/*     */       } 
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\lt\implementation\io\icl\model\xml\impl\ParameterTypeImpl.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */