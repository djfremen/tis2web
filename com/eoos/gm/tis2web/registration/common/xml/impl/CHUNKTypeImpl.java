/*     */ package com.eoos.gm.tis2web.registration.common.xml.impl;
/*     */ import com.eoos.gm.tis2web.registration.common.xml.CHUNKType;
/*     */ import com.sun.msv.verifier.DocumentDeclaration;
/*     */ import com.sun.xml.bind.WhiteSpaceProcessor;
/*     */ import com.sun.xml.bind.marshaller.Util;
/*     */ import com.sun.xml.bind.serializer.XMLSerializer;
/*     */ import com.sun.xml.bind.unmarshaller.ContentHandlerEx;
/*     */ import com.sun.xml.bind.unmarshaller.UnmarshallableObject;
/*     */ import com.sun.xml.bind.unmarshaller.UnmarshallingContext;
/*     */ import com.sun.xml.bind.unmarshaller.UnreportedException;
/*     */ import com.sun.xml.bind.validator.SchemaDeserializer;
/*     */ import com.sun.xml.bind.validator.ValidatableObject;
/*     */ import javax.xml.bind.DatatypeConverter;
/*     */ import org.xml.sax.Attributes;
/*     */ import org.xml.sax.SAXException;
/*     */ 
/*     */ public class CHUNKTypeImpl implements CHUNKType, UnmarshallableObject, XMLSerializable, ValidatableObject {
/*  18 */   private static final Grammar schemaFragment = SchemaDeserializer.deserialize("¬í\000\005sr\000\037com.sun.msv.grammar.SequenceExp\000\000\000\000\000\000\000\001\002\000\000xr\000\035com.sun.msv.grammar.BinaryExp\000\000\000\000\000\000\000\001\002\000\002L\000\004exp1t\000 Lcom/sun/msv/grammar/Expression;L\000\004exp2q\000~\000\002xr\000\036com.sun.msv.grammar.Expressionø\030èN5~O\002\000\003I\000\016cachedHashCodeL\000\023epsilonReducibilityt\000\023Ljava/lang/Boolean;L\000\013expandedExpq\000~\000\002xp\002g\001ppsr\000 com.sun.msv.grammar.AttributeExp\000\000\000\000\000\000\000\001\002\000\002L\000\003expq\000~\000\002L\000\tnameClasst\000\037Lcom/sun/msv/grammar/NameClass;xq\000~\000\003\001\t¬mppsr\000\033com.sun.msv.grammar.DataExp\000\000\000\000\000\000\000\001\002\000\003L\000\002dtt\000\037Lorg/relaxng/datatype/Datatype;L\000\006exceptq\000~\000\002L\000\004namet\000\035Lcom/sun/msv/util/StringPair;xq\000~\000\003\000Ð/ppsr\000#com.sun.msv.datatype.xsd.StringType\000\000\000\000\000\000\000\001\002\000\001Z\000\risAlwaysValidxr\000*com.sun.msv.datatype.xsd.BuiltinAtomicType\000\000\000\000\000\000\000\001\002\000\000xr\000%com.sun.msv.datatype.xsd.ConcreteType\000\000\000\000\000\000\000\001\002\000\000xr\000'com.sun.msv.datatype.xsd.XSDatatypeImpl\000\000\000\000\000\000\000\001\002\000\003L\000\fnamespaceUrit\000\022Ljava/lang/String;L\000\btypeNameq\000~\000\021L\000\nwhiteSpacet\000.Lcom/sun/msv/datatype/xsd/WhiteSpaceProcessor;xpt\000 http://www.w3.org/2001/XMLSchemat\000\006stringsr\000.com.sun.msv.datatype.xsd.WhiteSpaceProcessor$1\023JMoIÛ¤G\002\000\000xr\000,com.sun.msv.datatype.xsd.WhiteSpaceProcessor\000\000\000\000\000\000\000\001\002\000\000xp\001sr\0000com.sun.msv.grammar.Expression$NullSetExpression\000\000\000\000\000\000\000\001\002\000\000xq\000~\000\003\000\000\000\nppsr\000\033com.sun.msv.util.StringPairÐt\036jB \002\000\002L\000\tlocalNameq\000~\000\021L\000\fnamespaceURIq\000~\000\021xpq\000~\000\025q\000~\000\024sr\000#com.sun.msv.grammar.SimpleNameClass\000\000\000\000\000\000\000\001\002\000\002L\000\tlocalNameq\000~\000\021L\000\fnamespaceURIq\000~\000\021xr\000\035com.sun.msv.grammar.NameClass\000\000\000\000\000\000\000\001\002\000\000xpt\000\004Datat\000\000sq\000~\000\006\001]U\026ppsq\000~\000\t\000*ðppsr\000!com.sun.msv.datatype.xsd.ByteType\000\000\000\000\000\000\000\001\002\000\000xr\000+com.sun.msv.datatype.xsd.IntegerDerivedTypeñ]&6k¾\002\000\000xq\000~\000\016q\000~\000\024t\000\004bytesr\000.com.sun.msv.datatype.xsd.WhiteSpaceProcessor$2z9îø,N\005\002\000\000xq\000~\000\027q\000~\000\032sq\000~\000\033q\000~\000'q\000~\000\024sq\000~\000\035t\000\007ChunkNoq\000~\000!sr\000\"com.sun.msv.grammar.ExpressionPool\000\000\000\000\000\000\000\001\002\000\001L\000\bexpTablet\000/Lcom/sun/msv/grammar/ExpressionPool$ClosedHash;xpsr\000-com.sun.msv.grammar.ExpressionPool$ClosedHash×jÐNïèí\034\002\000\004I\000\005countI\000\tthresholdL\000\006parentq\000~\000.[\000\005tablet\000![Lcom/sun/msv/grammar/Expression;xp\000\000\000\001\000\000\0009pur\000![Lcom.sun.msv.grammar.Expression;Ö8DÃ]­§\n\002\000\000xp\000\000\000¿pppppppppppppppppppppq\000~\000\005ppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppp"); protected String _Data; protected boolean has_ChunkNo;
/*     */   protected byte _ChunkNo;
/*     */   
/*     */   private static final Class PRIMARY_INTERFACE_CLASS() {
/*  22 */     return CHUNKType.class;
/*     */   }
/*     */   
/*     */   public String getData() {
/*  26 */     return this._Data;
/*     */   }
/*     */   
/*     */   public void setData(String value) {
/*  30 */     this._Data = value;
/*     */   }
/*     */   
/*     */   public byte getChunkNo() {
/*  34 */     return this._ChunkNo;
/*     */   }
/*     */   
/*     */   public void setChunkNo(byte value) {
/*  38 */     this._ChunkNo = value;
/*  39 */     this.has_ChunkNo = true;
/*     */   }
/*     */   
/*     */   public ContentHandlerEx getUnmarshaller(UnmarshallingContext context) {
/*  43 */     return new Unmarshaller(context);
/*     */   }
/*     */   
/*     */   public Class getPrimaryInterfaceClass() {
/*  47 */     return PRIMARY_INTERFACE_CLASS();
/*     */   }
/*     */ 
/*     */   
/*     */   public void serializeElements(XMLSerializer context) throws SAXException {}
/*     */   
/*     */   public void serializeAttributes(XMLSerializer context) throws SAXException {
/*  54 */     context.startAttribute("", "Data");
/*     */     try {
/*  56 */       context.text(this._Data);
/*  57 */     } catch (Exception e) {
/*  58 */       Util.handlePrintConversionException(this, e, context);
/*     */     } 
/*  60 */     context.endAttribute();
/*  61 */     context.startAttribute("", "ChunkNo");
/*     */     try {
/*  63 */       context.text(DatatypeConverter.printByte(this._ChunkNo));
/*  64 */     } catch (Exception e) {
/*  65 */       Util.handlePrintConversionException(this, e, context);
/*     */     } 
/*  67 */     context.endAttribute();
/*     */   }
/*     */ 
/*     */   
/*     */   public void serializeAttributeBodies(XMLSerializer context) throws SAXException {}
/*     */   
/*     */   public Class getPrimaryInterface() {
/*  74 */     return CHUNKType.class;
/*     */   }
/*     */   
/*     */   public DocumentDeclaration createRawValidator() {
/*  78 */     return (DocumentDeclaration)new REDocumentDeclaration(schemaFragment);
/*     */   }
/*     */   
/*     */   public class Unmarshaller
/*     */     extends ContentHandlerEx {
/*     */     public Unmarshaller(UnmarshallingContext context) {
/*  84 */       super(context, "-----");
/*     */     }
/*     */     
/*     */     protected UnmarshallableObject owner() {
/*  88 */       return CHUNKTypeImpl.this;
/*     */     }
/*     */     
/*     */     public void enterElement(String ___uri, String ___local, Attributes __atts) throws UnreportedException {
/*  92 */       switch (this.state) {
/*     */         case 0:
/*  94 */           revertToParentFromEnterElement(___uri, ___local, __atts);
/*     */           return;
/*     */       } 
/*  97 */       super.enterElement(___uri, ___local, __atts);
/*     */     }
/*     */     
/*     */     public void leaveElement(String ___uri, String ___local) throws UnreportedException {
/* 101 */       switch (this.state) {
/*     */         case 0:
/* 103 */           revertToParentFromLeaveElement(___uri, ___local);
/*     */           return;
/*     */       } 
/* 106 */       super.leaveElement(___uri, ___local);
/*     */     }
/*     */     
/*     */     public void enterAttribute(String ___uri, String ___local) throws UnreportedException {
/* 110 */       switch (this.state) {
/*     */         case 0:
/* 112 */           if ("" == ___uri && "Data" == ___local) {
/* 113 */             this.state = 3;
/*     */             return;
/*     */           } 
/* 116 */           if ("" == ___uri && "ChunkNo" == ___local) {
/* 117 */             this.state = 1;
/*     */             return;
/*     */           } 
/* 120 */           revertToParentFromEnterAttribute(___uri, ___local);
/*     */           return;
/*     */       } 
/* 123 */       super.enterAttribute(___uri, ___local);
/*     */     }
/*     */     
/*     */     public void leaveAttribute(String ___uri, String ___local) throws UnreportedException {
/* 127 */       switch (this.state) {
/*     */         case 0:
/* 129 */           revertToParentFromLeaveAttribute(___uri, ___local);
/*     */           return;
/*     */         case 4:
/* 132 */           if ("" == ___uri && "Data" == ___local) {
/* 133 */             goto0();
/*     */             return;
/*     */           } 
/*     */           break;
/*     */         case 2:
/* 138 */           if ("" == ___uri && "ChunkNo" == ___local) {
/* 139 */             goto0();
/*     */             return;
/*     */           } 
/*     */           break;
/*     */       } 
/* 144 */       super.leaveAttribute(___uri, ___local);
/*     */     }
/*     */     
/*     */     public void text(String value) throws UnreportedException {
/*     */       try {
/* 149 */         switch (this.state) {
/*     */           case 0:
/* 151 */             revertToParentFromText(value);
/*     */             return;
/*     */           case 1:
/*     */             try {
/* 155 */               CHUNKTypeImpl.this._ChunkNo = DatatypeConverter.parseByte(WhiteSpaceProcessor.collapse(value));
/* 156 */               CHUNKTypeImpl.this.has_ChunkNo = true;
/* 157 */             } catch (Exception e) {
/* 158 */               handleParseConversionException(e);
/*     */             } 
/* 160 */             this.state = 2;
/*     */             return;
/*     */           case 3:
/*     */             try {
/* 164 */               CHUNKTypeImpl.this._Data = value;
/* 165 */             } catch (Exception e) {
/* 166 */               handleParseConversionException(e);
/*     */             } 
/* 168 */             this.state = 4;
/*     */             return;
/*     */         } 
/* 171 */       } catch (RuntimeException e) {
/* 172 */         handleUnexpectedTextException(value, e);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     private void goto0() throws UnreportedException {
/* 178 */       this.state = 0;
/* 179 */       int idx = this.context.getAttribute("", "Data");
/* 180 */       if (idx >= 0) {
/* 181 */         this.context.consumeAttribute(idx);
/*     */         return;
/*     */       } 
/* 184 */       idx = this.context.getAttribute("", "ChunkNo");
/* 185 */       if (idx >= 0) {
/* 186 */         this.context.consumeAttribute(idx);
/*     */         return;
/*     */       } 
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\registration\common\xml\impl\CHUNKTypeImpl.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */