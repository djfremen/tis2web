/*     */ package com.eoos.gm.tis2web.registration.standalone.xml.impl;
/*     */ import com.eoos.gm.tis2web.registration.standalone.xml.ORGANIZATIONType;
/*     */ import com.sun.msv.verifier.DocumentDeclaration;
/*     */ import com.sun.msv.verifier.regexp.REDocumentDeclaration;
/*     */ import com.sun.xml.bind.WhiteSpaceProcessor;
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
/*     */ public class ORGANIZATIONTypeImpl implements ORGANIZATIONType, UnmarshallableObject, XMLSerializable, ValidatableObject {
/*  18 */   private static final Grammar schemaFragment = SchemaDeserializer.deserialize("¬í\000\005sr\000\037com.sun.msv.grammar.SequenceExp\000\000\000\000\000\000\000\001\002\000\000xr\000\035com.sun.msv.grammar.BinaryExp\000\000\000\000\000\000\000\001\002\000\002L\000\004exp1t\000 Lcom/sun/msv/grammar/Expression;L\000\004exp2q\000~\000\002xr\000\036com.sun.msv.grammar.Expressionø\030èN5~O\002\000\003I\000\016cachedHashCodeL\000\023epsilonReducibilityt\000\023Ljava/lang/Boolean;L\000\013expandedExpq\000~\000\002xp\003WQßppsq\000~\000\000\002@{Lppsr\000 com.sun.msv.grammar.AttributeExp\000\000\000\000\000\000\000\001\002\000\002L\000\003expq\000~\000\002L\000\tnameClasst\000\037Lcom/sun/msv/grammar/NameClass;xq\000~\000\003\001\n\017ppsr\000\033com.sun.msv.grammar.DataExp\000\000\000\000\000\000\000\001\002\000\003L\000\002dtt\000\037Lorg/relaxng/datatype/Datatype;L\000\006exceptq\000~\000\002L\000\004namet\000\035Lcom/sun/msv/util/StringPair;xq\000~\000\003\000OÁfppsr\000#com.sun.msv.datatype.xsd.StringType\000\000\000\000\000\000\000\001\002\000\001Z\000\risAlwaysValidxr\000*com.sun.msv.datatype.xsd.BuiltinAtomicType\000\000\000\000\000\000\000\001\002\000\000xr\000%com.sun.msv.datatype.xsd.ConcreteType\000\000\000\000\000\000\000\001\002\000\000xr\000'com.sun.msv.datatype.xsd.XSDatatypeImpl\000\000\000\000\000\000\000\001\002\000\003L\000\fnamespaceUrit\000\022Ljava/lang/String;L\000\btypeNameq\000~\000\022L\000\nwhiteSpacet\000.Lcom/sun/msv/datatype/xsd/WhiteSpaceProcessor;xpt\000 http://www.w3.org/2001/XMLSchemat\000\006stringsr\000.com.sun.msv.datatype.xsd.WhiteSpaceProcessor$1\023JMoIÛ¤G\002\000\000xr\000,com.sun.msv.datatype.xsd.WhiteSpaceProcessor\000\000\000\000\000\000\000\001\002\000\000xp\001sr\0000com.sun.msv.grammar.Expression$NullSetExpression\000\000\000\000\000\000\000\001\002\000\000xq\000~\000\003\000\000\000\nppsr\000\033com.sun.msv.util.StringPairÐt\036jB \002\000\002L\000\tlocalNameq\000~\000\022L\000\fnamespaceURIq\000~\000\022xpq\000~\000\026q\000~\000\025sr\000#com.sun.msv.grammar.SimpleNameClass\000\000\000\000\000\000\000\001\002\000\002L\000\tlocalNameq\000~\000\022L\000\fnamespaceURIq\000~\000\022xr\000\035com.sun.msv.grammar.NameClass\000\000\000\000\000\000\000\001\002\000\000xpt\000\004FORMt\000\000sq\000~\000\007\0015á8ppq\000~\000\rsq\000~\000\036t\000\005EMAILq\000~\000\"sq\000~\000\007\001\026Öppsq\000~\000\n\000ãÆ4ppsr\000)com.sun.msv.datatype.xsd.EnumerationFacet\000\000\000\000\000\000\000\001\002\000\001L\000\006valuest\000\017Ljava/util/Set;xr\0009com.sun.msv.datatype.xsd.DataTypeWithValueConstraintFacet\"§RoÊÇT\002\000\000xr\000*com.sun.msv.datatype.xsd.DataTypeWithFacet\000\000\000\000\000\000\000\001\002\000\005Z\000\fisFacetFixedZ\000\022needValueCheckFlagL\000\bbaseTypet\000)Lcom/sun/msv/datatype/xsd/XSDatatypeImpl;L\000\fconcreteTypet\000'Lcom/sun/msv/datatype/xsd/ConcreteType;L\000\tfacetNameq\000~\000\022xq\000~\000\021q\000~\000\"psr\000.com.sun.msv.datatype.xsd.WhiteSpaceProcessor$2z9îø,N\005\002\000\000xq\000~\000\030\000\000sr\000$com.sun.msv.datatype.xsd.NmtokenType\000\000\000\000\000\000\000\001\002\000\000xr\000\"com.sun.msv.datatype.xsd.TokenType\000\000\000\000\000\000\000\001\002\000\000xq\000~\000\016q\000~\000\025t\000\007NMTOKENq\000~\0000\000q\000~\0003t\000\013enumerationsr\000\021java.util.HashSetºD¸·4\003\000\000xpw\f\000\000\000\020?@\000\000\000\000\000\002t\000\003NAOt\000\003GMExq\000~\000\033sq\000~\000\034t\000\017NMTOKEN-derivedq\000~\000\"sq\000~\000\036t\000\016OrganizationIDq\000~\000\"sr\000\"com.sun.msv.grammar.ExpressionPool\000\000\000\000\000\000\000\001\002\000\001L\000\bexpTablet\000/Lcom/sun/msv/grammar/ExpressionPool$ClosedHash;xpsr\000-com.sun.msv.grammar.ExpressionPool$ClosedHash×jÐNïèí\034\002\000\004I\000\005countI\000\tthresholdL\000\006parentq\000~\000?[\000\005tablet\000![Lcom/sun/msv/grammar/Expression;xp\000\000\000\002\000\000\0009pur\000![Lcom.sun.msv.grammar.Expression;Ö8DÃ]­§\n\002\000\000xp\000\000\000¿ppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppq\000~\000\006pppppppppppppq\000~\000\005pppppppppppppppppppppppppppppppppppppppppppppppppppppppppp"); protected String _FORM; protected String _EMAIL;
/*     */   protected String _OrganizationID;
/*     */   
/*     */   private static final Class PRIMARY_INTERFACE_CLASS() {
/*  22 */     return ORGANIZATIONType.class;
/*     */   }
/*     */   
/*     */   public String getFORM() {
/*  26 */     return this._FORM;
/*     */   }
/*     */   
/*     */   public void setFORM(String value) {
/*  30 */     this._FORM = value;
/*     */   }
/*     */   
/*     */   public String getEMAIL() {
/*  34 */     return this._EMAIL;
/*     */   }
/*     */   
/*     */   public void setEMAIL(String value) {
/*  38 */     this._EMAIL = value;
/*     */   }
/*     */   
/*     */   public String getOrganizationID() {
/*  42 */     return this._OrganizationID;
/*     */   }
/*     */   
/*     */   public void setOrganizationID(String value) {
/*  46 */     this._OrganizationID = value;
/*     */   }
/*     */   
/*     */   public ContentHandlerEx getUnmarshaller(UnmarshallingContext context) {
/*  50 */     return new Unmarshaller(context);
/*     */   }
/*     */   
/*     */   public Class getPrimaryInterfaceClass() {
/*  54 */     return PRIMARY_INTERFACE_CLASS();
/*     */   }
/*     */ 
/*     */   
/*     */   public void serializeElements(XMLSerializer context) throws SAXException {}
/*     */   
/*     */   public void serializeAttributes(XMLSerializer context) throws SAXException {
/*  61 */     context.startAttribute("", "FORM");
/*     */     try {
/*  63 */       context.text(this._FORM);
/*  64 */     } catch (Exception e) {
/*  65 */       Util.handlePrintConversionException(this, e, context);
/*     */     } 
/*  67 */     context.endAttribute();
/*  68 */     context.startAttribute("", "EMAIL");
/*     */     try {
/*  70 */       context.text(this._EMAIL);
/*  71 */     } catch (Exception e) {
/*  72 */       Util.handlePrintConversionException(this, e, context);
/*     */     } 
/*  74 */     context.endAttribute();
/*  75 */     context.startAttribute("", "OrganizationID");
/*     */     try {
/*  77 */       context.text(this._OrganizationID);
/*  78 */     } catch (Exception e) {
/*  79 */       Util.handlePrintConversionException(this, e, context);
/*     */     } 
/*  81 */     context.endAttribute();
/*     */   }
/*     */ 
/*     */   
/*     */   public void serializeAttributeBodies(XMLSerializer context) throws SAXException {}
/*     */   
/*     */   public Class getPrimaryInterface() {
/*  88 */     return ORGANIZATIONType.class;
/*     */   }
/*     */   
/*     */   public DocumentDeclaration createRawValidator() {
/*  92 */     return (DocumentDeclaration)new REDocumentDeclaration(schemaFragment);
/*     */   }
/*     */   
/*     */   public class Unmarshaller
/*     */     extends ContentHandlerEx {
/*     */     public Unmarshaller(UnmarshallingContext context) {
/*  98 */       super(context, "-------");
/*     */     }
/*     */     
/*     */     protected UnmarshallableObject owner() {
/* 102 */       return ORGANIZATIONTypeImpl.this;
/*     */     }
/*     */     
/*     */     public void enterElement(String ___uri, String ___local, Attributes __atts) throws UnreportedException {
/* 106 */       switch (this.state) {
/*     */         case 0:
/* 108 */           revertToParentFromEnterElement(___uri, ___local, __atts);
/*     */           return;
/*     */       } 
/* 111 */       super.enterElement(___uri, ___local, __atts);
/*     */     }
/*     */     
/*     */     public void leaveElement(String ___uri, String ___local) throws UnreportedException {
/* 115 */       switch (this.state) {
/*     */         case 0:
/* 117 */           revertToParentFromLeaveElement(___uri, ___local);
/*     */           return;
/*     */       } 
/* 120 */       super.leaveElement(___uri, ___local);
/*     */     }
/*     */     
/*     */     public void enterAttribute(String ___uri, String ___local) throws UnreportedException {
/* 124 */       switch (this.state) {
/*     */         case 0:
/* 126 */           if ("" == ___uri && "OrganizationID" == ___local) {
/* 127 */             this.state = 1;
/*     */             return;
/*     */           } 
/* 130 */           if ("" == ___uri && "FORM" == ___local) {
/* 131 */             this.state = 3;
/*     */             return;
/*     */           } 
/* 134 */           if ("" == ___uri && "EMAIL" == ___local) {
/* 135 */             this.state = 5;
/*     */             return;
/*     */           } 
/* 138 */           revertToParentFromEnterAttribute(___uri, ___local);
/*     */           return;
/*     */       } 
/* 141 */       super.enterAttribute(___uri, ___local);
/*     */     }
/*     */     
/*     */     public void leaveAttribute(String ___uri, String ___local) throws UnreportedException {
/* 145 */       switch (this.state) {
/*     */         case 4:
/* 147 */           if ("" == ___uri && "FORM" == ___local) {
/* 148 */             goto0();
/*     */             return;
/*     */           } 
/*     */           break;
/*     */         case 2:
/* 153 */           if ("" == ___uri && "OrganizationID" == ___local) {
/* 154 */             goto0();
/*     */             return;
/*     */           } 
/*     */           break;
/*     */         case 6:
/* 159 */           if ("" == ___uri && "EMAIL" == ___local) {
/* 160 */             goto0();
/*     */             return;
/*     */           } 
/*     */           break;
/*     */         case 0:
/* 165 */           revertToParentFromLeaveAttribute(___uri, ___local);
/*     */           return;
/*     */       } 
/* 168 */       super.leaveAttribute(___uri, ___local);
/*     */     }
/*     */     
/*     */     public void text(String value) throws UnreportedException {
/*     */       try {
/* 173 */         switch (this.state) {
/*     */           case 3:
/*     */             try {
/* 176 */               ORGANIZATIONTypeImpl.this._FORM = value;
/* 177 */             } catch (Exception e) {
/* 178 */               handleParseConversionException(e);
/*     */             } 
/* 180 */             this.state = 4;
/*     */             return;
/*     */           case 5:
/*     */             try {
/* 184 */               ORGANIZATIONTypeImpl.this._EMAIL = value;
/* 185 */             } catch (Exception e) {
/* 186 */               handleParseConversionException(e);
/*     */             } 
/* 188 */             this.state = 6;
/*     */             return;
/*     */           case 1:
/*     */             try {
/* 192 */               ORGANIZATIONTypeImpl.this._OrganizationID = WhiteSpaceProcessor.collapse(value);
/* 193 */             } catch (Exception e) {
/* 194 */               handleParseConversionException(e);
/*     */             } 
/* 196 */             this.state = 2;
/*     */             return;
/*     */           case 0:
/* 199 */             revertToParentFromText(value);
/*     */             return;
/*     */         } 
/* 202 */       } catch (RuntimeException e) {
/* 203 */         handleUnexpectedTextException(value, e);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     private void goto0() throws UnreportedException {
/* 209 */       this.state = 0;
/* 210 */       int idx = this.context.getAttribute("", "FORM");
/* 211 */       if (idx >= 0) {
/* 212 */         this.context.consumeAttribute(idx);
/*     */         return;
/*     */       } 
/* 215 */       idx = this.context.getAttribute("", "EMAIL");
/* 216 */       if (idx >= 0) {
/* 217 */         this.context.consumeAttribute(idx);
/*     */         return;
/*     */       } 
/* 220 */       idx = this.context.getAttribute("", "OrganizationID");
/* 221 */       if (idx >= 0) {
/* 222 */         this.context.consumeAttribute(idx);
/*     */         return;
/*     */       } 
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\registration\standalone\xml\impl\ORGANIZATIONTypeImpl.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */