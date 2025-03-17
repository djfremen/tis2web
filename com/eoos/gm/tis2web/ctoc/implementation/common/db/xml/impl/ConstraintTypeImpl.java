/*     */ package com.eoos.gm.tis2web.ctoc.implementation.common.db.xml.impl;
/*     */ 
/*     */ import com.eoos.gm.tis2web.ctoc.implementation.common.db.xml.ConstraintType;
/*     */ import com.sun.msv.grammar.Grammar;
/*     */ import com.sun.msv.verifier.DocumentDeclaration;
/*     */ import com.sun.msv.verifier.regexp.REDocumentDeclaration;
/*     */ import com.sun.xml.bind.marshaller.Util;
/*     */ import com.sun.xml.bind.serializer.XMLSerializable;
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
/*     */ public class ConstraintTypeImpl implements ConstraintType, UnmarshallableObject, XMLSerializable, ValidatableObject {
/*     */   protected String _UserGroup;
/*     */   protected String _Country;
/*     */   protected String _Application;
/*     */   protected String _ServiceInformationType;
/*     */   protected String _Manufacturer;
/*  25 */   private static final Grammar schemaFragment = SchemaDeserializer.deserialize("¬í\000\005sr\000\037com.sun.msv.grammar.SequenceExp\000\000\000\000\000\000\000\001\002\000\000xr\000\035com.sun.msv.grammar.BinaryExp\000\000\000\000\000\000\000\001\002\000\002L\000\004exp1t\000 Lcom/sun/msv/grammar/Expression;L\000\004exp2q\000~\000\002xr\000\036com.sun.msv.grammar.Expressionø\030èN5~O\002\000\003I\000\016cachedHashCodeL\000\023epsilonReducibilityt\000\023Ljava/lang/Boolean;L\000\013expandedExpq\000~\000\002xp\013%Içppsq\000~\000\000\bcº/ppsq\000~\000\000\006»(ppsq\000~\000\000\004~'rppsr\000\035com.sun.msv.grammar.ChoiceExp\000\000\000\000\000\000\000\001\002\000\000xq\000~\000\001\002¢\030áppsr\000 com.sun.msv.grammar.AttributeExp\000\000\000\000\000\000\000\001\002\000\002L\000\003expq\000~\000\002L\000\tnameClasst\000\037Lcom/sun/msv/grammar/NameClass;xq\000~\000\003\002¢\030Ösr\000\021java.lang.BooleanÍ rÕúî\002\000\001Z\000\005valuexp\000psr\000\033com.sun.msv.grammar.DataExp\000\000\000\000\000\000\000\001\002\000\003L\000\002dtt\000\037Lorg/relaxng/datatype/Datatype;L\000\006exceptq\000~\000\002L\000\004namet\000\035Lcom/sun/msv/util/StringPair;xq\000~\000\003\001»ÒOppsr\000#com.sun.msv.datatype.xsd.StringType\000\000\000\000\000\000\000\001\002\000\001Z\000\risAlwaysValidxr\000*com.sun.msv.datatype.xsd.BuiltinAtomicType\000\000\000\000\000\000\000\001\002\000\000xr\000%com.sun.msv.datatype.xsd.ConcreteType\000\000\000\000\000\000\000\001\002\000\000xr\000'com.sun.msv.datatype.xsd.XSDatatypeImpl\000\000\000\000\000\000\000\001\002\000\003L\000\fnamespaceUrit\000\022Ljava/lang/String;L\000\btypeNameq\000~\000\030L\000\nwhiteSpacet\000.Lcom/sun/msv/datatype/xsd/WhiteSpaceProcessor;xpt\000 http://www.w3.org/2001/XMLSchemat\000\006stringsr\000.com.sun.msv.datatype.xsd.WhiteSpaceProcessor$1\023JMoIÛ¤G\002\000\000xr\000,com.sun.msv.datatype.xsd.WhiteSpaceProcessor\000\000\000\000\000\000\000\001\002\000\000xp\001sr\0000com.sun.msv.grammar.Expression$NullSetExpression\000\000\000\000\000\000\000\001\002\000\000xq\000~\000\003\000\000\000\nppsr\000\033com.sun.msv.util.StringPairÐt\036jB \002\000\002L\000\tlocalNameq\000~\000\030L\000\fnamespaceURIq\000~\000\030xpq\000~\000\034q\000~\000\033sr\000#com.sun.msv.grammar.SimpleNameClass\000\000\000\000\000\000\000\001\002\000\002L\000\tlocalNameq\000~\000\030L\000\fnamespaceURIq\000~\000\030xr\000\035com.sun.msv.grammar.NameClass\000\000\000\000\000\000\000\001\002\000\000xpt\000\fManufacturert\000\000sr\0000com.sun.msv.grammar.Expression$EpsilonExpression\000\000\000\000\000\000\000\001\002\000\000xq\000~\000\003\000\000\000\tsq\000~\000\016\001psq\000~\000\t\001Ü\016ppsq\000~\000\013\001Ü\016q\000~\000\017pq\000~\000\023sq\000~\000$t\000\013Applicationq\000~\000(q\000~\000*sq\000~\000\t\002\013±ppsq\000~\000\013\002\013¦q\000~\000\017pq\000~\000\023sq\000~\000$t\000\tUserGroupq\000~\000(q\000~\000*sq\000~\000\t\001Ùÿ\002ppsq\000~\000\013\001Ùþ÷q\000~\000\017pq\000~\000\023sq\000~\000$t\000\007Countryq\000~\000(q\000~\000*sq\000~\000\t\002Á³ppsq\000~\000\013\002Á¨q\000~\000\017pq\000~\000\023sq\000~\000$t\000\026ServiceInformationTypeq\000~\000(q\000~\000*sr\000\"com.sun.msv.grammar.ExpressionPool\000\000\000\000\000\000\000\001\002\000\001L\000\bexpTablet\000/Lcom/sun/msv/grammar/ExpressionPool$ClosedHash;xpsr\000-com.sun.msv.grammar.ExpressionPool$ClosedHash×jÐNïèí\034\002\000\004I\000\005countI\000\tthresholdL\000\006parentq\000~\000=[\000\005tablet\000![Lcom/sun/msv/grammar/Expression;xp\000\000\000\t\000\000\0009pur\000![Lcom.sun.msv.grammar.Expression;Ö8DÃ]­§\n\002\000\000xp\000\000\000¿ppppppppppppppppq\000~\000\006ppppppppppppppppppppppppppppppppppppppppppq\000~\000\007pppppppq\000~\000\bpppppppppppppppppppppppppppq\000~\0008pq\000~\000\nppppppppppppppppppq\000~\000\005ppppppppppppppppppppppppppq\000~\0004ppppppppppppq\000~\000,pppppppppppppppppppppq\000~\0000pppppppppppp");
/*     */ 
/*     */   
/*     */   private static final Class PRIMARY_INTERFACE_CLASS() {
/*  29 */     return ConstraintType.class;
/*     */   }
/*     */   
/*     */   public String getUserGroup() {
/*  33 */     return this._UserGroup;
/*     */   }
/*     */   
/*     */   public void setUserGroup(String value) {
/*  37 */     this._UserGroup = value;
/*     */   }
/*     */   
/*     */   public String getCountry() {
/*  41 */     return this._Country;
/*     */   }
/*     */   
/*     */   public void setCountry(String value) {
/*  45 */     this._Country = value;
/*     */   }
/*     */   
/*     */   public String getApplication() {
/*  49 */     return this._Application;
/*     */   }
/*     */   
/*     */   public void setApplication(String value) {
/*  53 */     this._Application = value;
/*     */   }
/*     */   
/*     */   public String getServiceInformationType() {
/*  57 */     return this._ServiceInformationType;
/*     */   }
/*     */   
/*     */   public void setServiceInformationType(String value) {
/*  61 */     this._ServiceInformationType = value;
/*     */   }
/*     */   
/*     */   public String getManufacturer() {
/*  65 */     return this._Manufacturer;
/*     */   }
/*     */   
/*     */   public void setManufacturer(String value) {
/*  69 */     this._Manufacturer = value;
/*     */   }
/*     */   
/*     */   public ContentHandlerEx getUnmarshaller(UnmarshallingContext context) {
/*  73 */     return new Unmarshaller(context);
/*     */   }
/*     */   
/*     */   public Class getPrimaryInterfaceClass() {
/*  77 */     return PRIMARY_INTERFACE_CLASS();
/*     */   }
/*     */ 
/*     */   
/*     */   public void serializeElements(XMLSerializer context) throws SAXException {}
/*     */   
/*     */   public void serializeAttributes(XMLSerializer context) throws SAXException {
/*  84 */     if (this._Manufacturer != null) {
/*  85 */       context.startAttribute("", "Manufacturer");
/*     */       try {
/*  87 */         context.text(this._Manufacturer);
/*  88 */       } catch (Exception e) {
/*  89 */         Util.handlePrintConversionException(this, e, context);
/*     */       } 
/*  91 */       context.endAttribute();
/*     */     } 
/*  93 */     if (this._Application != null) {
/*  94 */       context.startAttribute("", "Application");
/*     */       try {
/*  96 */         context.text(this._Application);
/*  97 */       } catch (Exception e) {
/*  98 */         Util.handlePrintConversionException(this, e, context);
/*     */       } 
/* 100 */       context.endAttribute();
/*     */     } 
/* 102 */     if (this._UserGroup != null) {
/* 103 */       context.startAttribute("", "UserGroup");
/*     */       try {
/* 105 */         context.text(this._UserGroup);
/* 106 */       } catch (Exception e) {
/* 107 */         Util.handlePrintConversionException(this, e, context);
/*     */       } 
/* 109 */       context.endAttribute();
/*     */     } 
/* 111 */     if (this._Country != null) {
/* 112 */       context.startAttribute("", "Country");
/*     */       try {
/* 114 */         context.text(this._Country);
/* 115 */       } catch (Exception e) {
/* 116 */         Util.handlePrintConversionException(this, e, context);
/*     */       } 
/* 118 */       context.endAttribute();
/*     */     } 
/* 120 */     if (this._ServiceInformationType != null) {
/* 121 */       context.startAttribute("", "ServiceInformationType");
/*     */       try {
/* 123 */         context.text(this._ServiceInformationType);
/* 124 */       } catch (Exception e) {
/* 125 */         Util.handlePrintConversionException(this, e, context);
/*     */       } 
/* 127 */       context.endAttribute();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void serializeAttributeBodies(XMLSerializer context) throws SAXException {}
/*     */   
/*     */   public Class getPrimaryInterface() {
/* 135 */     return ConstraintType.class;
/*     */   }
/*     */   
/*     */   public DocumentDeclaration createRawValidator() {
/* 139 */     return (DocumentDeclaration)new REDocumentDeclaration(schemaFragment);
/*     */   }
/*     */   
/*     */   public class Unmarshaller
/*     */     extends ContentHandlerEx {
/*     */     public Unmarshaller(UnmarshallingContext context) {
/* 145 */       super(context, "-----------");
/*     */     }
/*     */     
/*     */     protected UnmarshallableObject owner() {
/* 149 */       return ConstraintTypeImpl.this;
/*     */     }
/*     */     
/*     */     public void enterElement(String ___uri, String ___local, Attributes __atts) throws UnreportedException {
/* 153 */       switch (this.state) {
/*     */         case 0:
/* 155 */           revertToParentFromEnterElement(___uri, ___local, __atts);
/*     */           return;
/*     */       } 
/* 158 */       super.enterElement(___uri, ___local, __atts);
/*     */     }
/*     */     
/*     */     public void leaveElement(String ___uri, String ___local) throws UnreportedException {
/* 162 */       switch (this.state) {
/*     */         case 0:
/* 164 */           revertToParentFromLeaveElement(___uri, ___local);
/*     */           return;
/*     */       } 
/* 167 */       super.leaveElement(___uri, ___local);
/*     */     }
/*     */     
/*     */     public void enterAttribute(String ___uri, String ___local) throws UnreportedException {
/* 171 */       switch (this.state) {
/*     */         case 0:
/* 173 */           if ("" == ___uri && "Manufacturer" == ___local) {
/* 174 */             this.state = 3;
/*     */             return;
/*     */           } 
/* 177 */           if ("" == ___uri && "Country" == ___local) {
/* 178 */             this.state = 9;
/*     */             return;
/*     */           } 
/* 181 */           if ("" == ___uri && "Application" == ___local) {
/* 182 */             this.state = 1;
/*     */             return;
/*     */           } 
/* 185 */           if ("" == ___uri && "UserGroup" == ___local) {
/* 186 */             this.state = 7;
/*     */             return;
/*     */           } 
/* 189 */           if ("" == ___uri && "ServiceInformationType" == ___local) {
/* 190 */             this.state = 5;
/*     */             return;
/*     */           } 
/* 193 */           revertToParentFromEnterAttribute(___uri, ___local);
/*     */           return;
/*     */       } 
/* 196 */       super.enterAttribute(___uri, ___local);
/*     */     }
/*     */     
/*     */     public void leaveAttribute(String ___uri, String ___local) throws UnreportedException {
/* 200 */       switch (this.state) {
/*     */         case 2:
/* 202 */           if ("" == ___uri && "Application" == ___local) {
/* 203 */             goto0();
/*     */             return;
/*     */           } 
/*     */           break;
/*     */         case 0:
/* 208 */           revertToParentFromLeaveAttribute(___uri, ___local);
/*     */           return;
/*     */         case 10:
/* 211 */           if ("" == ___uri && "Country" == ___local) {
/* 212 */             goto0();
/*     */             return;
/*     */           } 
/*     */           break;
/*     */         case 8:
/* 217 */           if ("" == ___uri && "UserGroup" == ___local) {
/* 218 */             goto0();
/*     */             return;
/*     */           } 
/*     */           break;
/*     */         case 6:
/* 223 */           if ("" == ___uri && "ServiceInformationType" == ___local) {
/* 224 */             goto0();
/*     */             return;
/*     */           } 
/*     */           break;
/*     */         case 4:
/* 229 */           if ("" == ___uri && "Manufacturer" == ___local) {
/* 230 */             goto0();
/*     */             return;
/*     */           } 
/*     */           break;
/*     */       } 
/* 235 */       super.leaveAttribute(___uri, ___local);
/*     */     }
/*     */     
/*     */     public void text(String value) throws UnreportedException {
/*     */       try {
/* 240 */         switch (this.state) {
/*     */           case 5:
/*     */             try {
/* 243 */               ConstraintTypeImpl.this._ServiceInformationType = value;
/* 244 */             } catch (Exception e) {
/* 245 */               handleParseConversionException(e);
/*     */             } 
/* 247 */             this.state = 6;
/*     */             return;
/*     */           case 1:
/*     */             try {
/* 251 */               ConstraintTypeImpl.this._Application = value;
/* 252 */             } catch (Exception e) {
/* 253 */               handleParseConversionException(e);
/*     */             } 
/* 255 */             this.state = 2;
/*     */             return;
/*     */           case 0:
/* 258 */             revertToParentFromText(value);
/*     */             return;
/*     */           case 9:
/*     */             try {
/* 262 */               ConstraintTypeImpl.this._Country = value;
/* 263 */             } catch (Exception e) {
/* 264 */               handleParseConversionException(e);
/*     */             } 
/* 266 */             this.state = 10;
/*     */             return;
/*     */           case 7:
/*     */             try {
/* 270 */               ConstraintTypeImpl.this._UserGroup = value;
/* 271 */             } catch (Exception e) {
/* 272 */               handleParseConversionException(e);
/*     */             } 
/* 274 */             this.state = 8;
/*     */             return;
/*     */           case 3:
/*     */             try {
/* 278 */               ConstraintTypeImpl.this._Manufacturer = value;
/* 279 */             } catch (Exception e) {
/* 280 */               handleParseConversionException(e);
/*     */             } 
/* 282 */             this.state = 4;
/*     */             return;
/*     */         } 
/* 285 */       } catch (RuntimeException e) {
/* 286 */         handleUnexpectedTextException(value, e);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     private void goto0() throws UnreportedException {
/* 292 */       this.state = 0;
/* 293 */       int idx = this.context.getAttribute("", "Manufacturer");
/* 294 */       if (idx >= 0) {
/* 295 */         this.context.consumeAttribute(idx);
/*     */         return;
/*     */       } 
/* 298 */       idx = this.context.getAttribute("", "Application");
/* 299 */       if (idx >= 0) {
/* 300 */         this.context.consumeAttribute(idx);
/*     */         return;
/*     */       } 
/* 303 */       idx = this.context.getAttribute("", "UserGroup");
/* 304 */       if (idx >= 0) {
/* 305 */         this.context.consumeAttribute(idx);
/*     */         return;
/*     */       } 
/* 308 */       idx = this.context.getAttribute("", "Country");
/* 309 */       if (idx >= 0) {
/* 310 */         this.context.consumeAttribute(idx);
/*     */         return;
/*     */       } 
/* 313 */       idx = this.context.getAttribute("", "ServiceInformationType");
/* 314 */       if (idx >= 0) {
/* 315 */         this.context.consumeAttribute(idx);
/*     */         return;
/*     */       } 
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\ctoc\implementation\common\db\xml\impl\ConstraintTypeImpl.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */