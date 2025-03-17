/*     */ package com.eoos.gm.tis2web.lt.implementation.io.icl.model.xml.impl;
/*     */ import com.eoos.gm.tis2web.lt.implementation.io.icl.model.xml.FooterType;
/*     */ import com.eoos.gm.tis2web.lt.implementation.io.icl.model.xml.HeaderType;
/*     */ import com.eoos.gm.tis2web.lt.implementation.io.icl.model.xml.PositionsType;
/*     */ import com.eoos.gm.tis2web.lt.implementation.io.icl.model.xml.ServiceplanType;
/*     */ import com.sun.msv.verifier.DocumentDeclaration;
/*     */ import com.sun.xml.bind.serializer.XMLSerializable;
/*     */ import com.sun.xml.bind.serializer.XMLSerializer;
/*     */ import com.sun.xml.bind.unmarshaller.ContentHandlerEx;
/*     */ import com.sun.xml.bind.unmarshaller.UnmarshallableObject;
/*     */ import com.sun.xml.bind.unmarshaller.UnmarshallingContext;
/*     */ import com.sun.xml.bind.unmarshaller.UnreportedException;
/*     */ import com.sun.xml.bind.validator.SchemaDeserializer;
/*     */ import org.xml.sax.Attributes;
/*     */ import org.xml.sax.SAXException;
/*     */ 
/*     */ public class ServiceplanTypeImpl implements ServiceplanType, UnmarshallableObject, XMLSerializable, ValidatableObject {
/*     */   protected PositionsType _Positions;
/*  19 */   private static final Grammar schemaFragment = SchemaDeserializer.deserialize("¬í\000\005sr\000\037com.sun.msv.grammar.SequenceExp\000\000\000\000\000\000\000\001\002\000\000xr\000\035com.sun.msv.grammar.BinaryExp\000\000\000\000\000\000\000\001\002\000\002L\000\004exp1t\000 Lcom/sun/msv/grammar/Expression;L\000\004exp2q\000~\000\002xr\000\036com.sun.msv.grammar.Expressionø\030èN5~O\002\000\003I\000\016cachedHashCodeL\000\023epsilonReducibilityt\000\023Ljava/lang/Boolean;L\000\013expandedExpq\000~\000\002xp\b¬IÞppsq\000~\000\000\006_QSppsq\000~\000\000\004?ppsr\000\035com.sun.msv.grammar.ChoiceExp\000\000\000\000\000\000\000\001\002\000\000xq\000~\000\001\002\037ÅÃppsr\000'com.sun.msv.grammar.trex.ElementPattern\000\000\000\000\000\000\000\001\002\000\001L\000\tnameClasst\000\037Lcom/sun/msv/grammar/NameClass;xr\000\036com.sun.msv.grammar.ElementExp\000\000\000\000\000\000\000\001\002\000\002Z\000\032ignoreUndeclaredAttributesL\000\fcontentModelq\000~\000\002xq\000~\000\003\001\017âæpp\000sq\000~\000\n\001\017âÛpp\000sq\000~\000\b\001\017âÐppsr\000 com.sun.msv.grammar.OneOrMoreExp\000\000\000\000\000\000\000\001\002\000\000xr\000\034com.sun.msv.grammar.UnaryExp\000\000\000\000\000\000\000\001\002\000\001L\000\003expq\000~\000\002xq\000~\000\003\001\017âÅsr\000\021java.lang.BooleanÍ rÕúî\002\000\001Z\000\005valuexp\000psr\000 com.sun.msv.grammar.AttributeExp\000\000\000\000\000\000\000\001\002\000\002L\000\003expq\000~\000\002L\000\tnameClassq\000~\000\013xq\000~\000\003\001\017âÂq\000~\000\024psr\0002com.sun.msv.grammar.Expression$AnyStringExpression\000\000\000\000\000\000\000\001\002\000\000xq\000~\000\003\000\000\000\bsq\000~\000\023\001psr\000 com.sun.msv.grammar.AnyNameClass\000\000\000\000\000\000\000\001\002\000\000xr\000\035com.sun.msv.grammar.NameClass\000\000\000\000\000\000\000\001\002\000\000xpsr\0000com.sun.msv.grammar.Expression$EpsilonExpression\000\000\000\000\000\000\000\001\002\000\000xq\000~\000\003\000\000\000\tq\000~\000\031psr\000#com.sun.msv.grammar.SimpleNameClass\000\000\000\000\000\000\000\001\002\000\002L\000\tlocalNamet\000\022Ljava/lang/String;L\000\fnamespaceURIq\000~\000 xq\000~\000\033t\0002com.eoos.gm.tis2web.lt.io.icl.model.xml.HeaderTypet\000+http://java.sun.com/jaxb/xjc/dummy-elementssq\000~\000\037t\000\006Headert\000\000sq\000~\000\n\001\017âÛpp\000sq\000~\000\b\001\017âÐppsq\000~\000\020\001\017âÅq\000~\000\024psq\000~\000\025\001\017âÂq\000~\000\024pq\000~\000\030q\000~\000\034q\000~\000\036sq\000~\000\037t\000.com.eoos.gm.tis2web.lt.io.icl.model.xml.Headerq\000~\000#sq\000~\000\b\002\037ÅÃppsq\000~\000\n\001\017âæpp\000sq\000~\000\n\001\017âÛpp\000sq\000~\000\b\001\017âÐppsq\000~\000\020\001\017âÅq\000~\000\024psq\000~\000\025\001\017âÂq\000~\000\024pq\000~\000\030q\000~\000\034q\000~\000\036sq\000~\000\037t\0005com.eoos.gm.tis2web.lt.io.icl.model.xml.PositionsTypeq\000~\000#sq\000~\000\037t\000\tPositionsq\000~\000&sq\000~\000\n\001\017âÛpp\000sq\000~\000\b\001\017âÐppsq\000~\000\020\001\017âÅq\000~\000\024psq\000~\000\025\001\017âÂq\000~\000\024pq\000~\000\030q\000~\000\034q\000~\000\036sq\000~\000\037t\0001com.eoos.gm.tis2web.lt.io.icl.model.xml.Positionsq\000~\000#sq\000~\000\b\002\037ÅÃppsq\000~\000\n\001\017âæpp\000sq\000~\000\n\001\017âÛpp\000sq\000~\000\b\001\017âÐppsq\000~\000\020\001\017âÅq\000~\000\024psq\000~\000\025\001\017âÂq\000~\000\024pq\000~\000\030q\000~\000\034q\000~\000\036sq\000~\000\037t\0002com.eoos.gm.tis2web.lt.io.icl.model.xml.FooterTypeq\000~\000#sq\000~\000\037t\000\006Footerq\000~\000&sq\000~\000\n\001\017âÛpp\000sq\000~\000\b\001\017âÐppsq\000~\000\020\001\017âÅq\000~\000\024psq\000~\000\025\001\017âÂq\000~\000\024pq\000~\000\030q\000~\000\034q\000~\000\036sq\000~\000\037t\000.com.eoos.gm.tis2web.lt.io.icl.model.xml.Footerq\000~\000#sq\000~\000\025\002Løppsr\000\033com.sun.msv.grammar.DataExp\000\000\000\000\000\000\000\001\002\000\003L\000\002dtt\000\037Lorg/relaxng/datatype/Datatype;L\000\006exceptq\000~\000\002L\000\004namet\000\035Lcom/sun/msv/util/StringPair;xq\000~\000\003\001å~ppsr\000#com.sun.msv.datatype.xsd.StringType\000\000\000\000\000\000\000\001\002\000\001Z\000\risAlwaysValidxr\000*com.sun.msv.datatype.xsd.BuiltinAtomicType\000\000\000\000\000\000\000\001\002\000\000xr\000%com.sun.msv.datatype.xsd.ConcreteType\000\000\000\000\000\000\000\001\002\000\000xr\000'com.sun.msv.datatype.xsd.XSDatatypeImpl\000\000\000\000\000\000\000\001\002\000\003L\000\fnamespaceUriq\000~\000 L\000\btypeNameq\000~\000 L\000\nwhiteSpacet\000.Lcom/sun/msv/datatype/xsd/WhiteSpaceProcessor;xpt\000 http://www.w3.org/2001/XMLSchemat\000\006stringsr\000.com.sun.msv.datatype.xsd.WhiteSpaceProcessor$1\023JMoIÛ¤G\002\000\000xr\000,com.sun.msv.datatype.xsd.WhiteSpaceProcessor\000\000\000\000\000\000\000\001\002\000\000xp\001sr\0000com.sun.msv.grammar.Expression$NullSetExpression\000\000\000\000\000\000\000\001\002\000\000xq\000~\000\003\000\000\000\nppsr\000\033com.sun.msv.util.StringPairÐt\036jB \002\000\002L\000\tlocalNameq\000~\000 L\000\fnamespaceURIq\000~\000 xpq\000~\000Yq\000~\000Xsq\000~\000\037t\000\blanguageq\000~\000&sr\000\"com.sun.msv.grammar.ExpressionPool\000\000\000\000\000\000\000\001\002\000\001L\000\bexpTablet\000/Lcom/sun/msv/grammar/ExpressionPool$ClosedHash;xpsr\000-com.sun.msv.grammar.ExpressionPool$ClosedHash×jÐNïèí\034\002\000\004I\000\005countI\000\tthresholdL\000\006parentq\000~\000d[\000\005tablet\000![Lcom/sun/msv/grammar/Expression;xp\000\000\000\022\000\000\0009pur\000![Lcom.sun.msv.grammar.Expression;Ö8DÃ]­§\n\002\000\000xp\000\000\000¿ppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppq\000~\000\005ppppppppppppq\000~\000\006ppppppppq\000~\000\tq\000~\000-q\000~\000=pppppppppppppppppppppq\000~\000\022q\000~\000)q\000~\0001q\000~\0009q\000~\000Aq\000~\000Ipppppq\000~\000\017q\000~\000(q\000~\0000q\000~\0008q\000~\000@q\000~\000Hppppppppppppppppppppppppppppppppppppppppppppppppppq\000~\000\007ppppppppppppp"); protected FooterType _Footer; protected HeaderType _Header;
/*     */   protected String _Language;
/*     */   
/*     */   private static final Class PRIMARY_INTERFACE_CLASS() {
/*  23 */     return ServiceplanType.class;
/*     */   }
/*     */   
/*     */   public PositionsType getPositions() {
/*  27 */     return this._Positions;
/*     */   }
/*     */   
/*     */   public void setPositions(PositionsType value) {
/*  31 */     this._Positions = value;
/*     */   }
/*     */   
/*     */   public FooterType getFooter() {
/*  35 */     return this._Footer;
/*     */   }
/*     */   
/*     */   public void setFooter(FooterType value) {
/*  39 */     this._Footer = value;
/*     */   }
/*     */   
/*     */   public HeaderType getHeader() {
/*  43 */     return this._Header;
/*     */   }
/*     */   
/*     */   public void setHeader(HeaderType value) {
/*  47 */     this._Header = value;
/*     */   }
/*     */   
/*     */   public String getLanguage() {
/*  51 */     return this._Language;
/*     */   }
/*     */   
/*     */   public void setLanguage(String value) {
/*  55 */     this._Language = value;
/*     */   }
/*     */   
/*     */   public ContentHandlerEx getUnmarshaller(UnmarshallingContext context) {
/*  59 */     return new Unmarshaller(context);
/*     */   }
/*     */   
/*     */   public Class getPrimaryInterfaceClass() {
/*  63 */     return PRIMARY_INTERFACE_CLASS();
/*     */   }
/*     */   
/*     */   public void serializeElements(XMLSerializer context) throws SAXException {
/*  67 */     if (this._Header instanceof javax.xml.bind.Element) {
/*  68 */       context.childAsElements((XMLSerializable)this._Header);
/*     */     } else {
/*  70 */       context.startElement("", "Header");
/*  71 */       context.childAsAttributes((XMLSerializable)this._Header);
/*  72 */       context.endAttributes();
/*  73 */       context.childAsElements((XMLSerializable)this._Header);
/*  74 */       context.endElement();
/*     */     } 
/*  76 */     if (this._Positions instanceof javax.xml.bind.Element) {
/*  77 */       context.childAsElements((XMLSerializable)this._Positions);
/*     */     } else {
/*  79 */       context.startElement("", "Positions");
/*  80 */       context.childAsAttributes((XMLSerializable)this._Positions);
/*  81 */       context.endAttributes();
/*  82 */       context.childAsElements((XMLSerializable)this._Positions);
/*  83 */       context.endElement();
/*     */     } 
/*  85 */     if (this._Footer instanceof javax.xml.bind.Element) {
/*  86 */       context.childAsElements((XMLSerializable)this._Footer);
/*     */     } else {
/*  88 */       context.startElement("", "Footer");
/*  89 */       context.childAsAttributes((XMLSerializable)this._Footer);
/*  90 */       context.endAttributes();
/*  91 */       context.childAsElements((XMLSerializable)this._Footer);
/*  92 */       context.endElement();
/*     */     } 
/*     */   }
/*     */   
/*     */   public void serializeAttributes(XMLSerializer context) throws SAXException {
/*  97 */     context.startAttribute("", "language");
/*     */     try {
/*  99 */       context.text(this._Language);
/* 100 */     } catch (Exception e) {
/* 101 */       Util.handlePrintConversionException(this, e, context);
/*     */     } 
/* 103 */     context.endAttribute();
/*     */   }
/*     */ 
/*     */   
/*     */   public void serializeAttributeBodies(XMLSerializer context) throws SAXException {}
/*     */   
/*     */   public Class getPrimaryInterface() {
/* 110 */     return ServiceplanType.class;
/*     */   }
/*     */   
/*     */   public DocumentDeclaration createRawValidator() {
/* 114 */     return (DocumentDeclaration)new REDocumentDeclaration(schemaFragment);
/*     */   }
/*     */   
/*     */   public class Unmarshaller
/*     */     extends ContentHandlerEx {
/*     */     public Unmarshaller(UnmarshallingContext context) {
/* 120 */       super(context, "------------");
/*     */     }
/*     */     
/*     */     protected UnmarshallableObject owner() {
/* 124 */       return ServiceplanTypeImpl.this;
/*     */     }
/*     */     
/*     */     public void enterElement(String ___uri, String ___local, Attributes __atts) throws UnreportedException {
/* 128 */       switch (this.state) {
/*     */         case 3:
/* 130 */           if ("" == ___uri && "Make" == ___local) {
/* 131 */             ServiceplanTypeImpl.this._Header = (HeaderTypeImpl)spawnChildFromEnterElement(HeaderTypeImpl.class, 4, ___uri, ___local, __atts);
/*     */             return;
/*     */           } 
/*     */           break;
/*     */         case 11:
/* 136 */           revertToParentFromEnterElement(___uri, ___local, __atts);
/*     */           return;
/*     */         case 0:
/* 139 */           if ("" == ___uri && "Header" == ___local) {
/* 140 */             this.context.pushAttributes(__atts);
/* 141 */             this.state = 3;
/*     */             return;
/*     */           } 
/*     */           break;
/*     */         case 8:
/* 146 */           if ("" == ___uri && "Footer" == ___local) {
/* 147 */             this.context.pushAttributes(__atts);
/* 148 */             this.state = 9;
/*     */             return;
/*     */           } 
/*     */           break;
/*     */         case 6:
/* 153 */           if ("" == ___uri && "Title" == ___local) {
/* 154 */             ServiceplanTypeImpl.this._Positions = (PositionsTypeImpl)spawnChildFromEnterElement(PositionsTypeImpl.class, 7, ___uri, ___local, __atts);
/*     */             return;
/*     */           } 
/*     */           break;
/*     */         case 5:
/* 159 */           if ("" == ___uri && "Positions" == ___local) {
/* 160 */             this.context.pushAttributes(__atts);
/* 161 */             this.state = 6;
/*     */             return;
/*     */           } 
/*     */           break;
/*     */         case 9:
/* 166 */           if ("" == ___uri && "PosFootnote" == ___local) {
/* 167 */             ServiceplanTypeImpl.this._Footer = (FooterTypeImpl)spawnChildFromEnterElement(FooterTypeImpl.class, 10, ___uri, ___local, __atts);
/*     */             return;
/*     */           } 
/* 170 */           if ("" == ___uri && "Footnote" == ___local) {
/* 171 */             ServiceplanTypeImpl.this._Footer = (FooterTypeImpl)spawnChildFromEnterElement(FooterTypeImpl.class, 10, ___uri, ___local, __atts);
/*     */             return;
/*     */           } 
/* 174 */           if ("" == ___uri && "StdFootnote" == ___local) {
/* 175 */             ServiceplanTypeImpl.this._Footer = (FooterTypeImpl)spawnChildFromEnterElement(FooterTypeImpl.class, 10, ___uri, ___local, __atts);
/*     */             return;
/*     */           } 
/* 178 */           if ("" == ___uri && "Confirmation" == ___local) {
/* 179 */             ServiceplanTypeImpl.this._Footer = (FooterTypeImpl)spawnChildFromEnterElement(FooterTypeImpl.class, 10, ___uri, ___local, __atts);
/*     */             return;
/*     */           } 
/*     */           break;
/*     */       } 
/* 184 */       super.enterElement(___uri, ___local, __atts);
/*     */     }
/*     */     
/*     */     public void leaveElement(String ___uri, String ___local) throws UnreportedException {
/* 188 */       switch (this.state) {
/*     */         case 11:
/* 190 */           revertToParentFromLeaveElement(___uri, ___local);
/*     */           return;
/*     */         case 4:
/* 193 */           if ("" == ___uri && "Header" == ___local) {
/* 194 */             this.context.popAttributes();
/* 195 */             this.state = 5;
/*     */             return;
/*     */           } 
/*     */           break;
/*     */         case 10:
/* 200 */           if ("" == ___uri && "Footer" == ___local) {
/* 201 */             this.context.popAttributes();
/* 202 */             this.state = 11;
/*     */             return;
/*     */           } 
/*     */           break;
/*     */         case 7:
/* 207 */           if ("" == ___uri && "Positions" == ___local) {
/* 208 */             this.context.popAttributes();
/* 209 */             this.state = 8;
/*     */             return;
/*     */           } 
/*     */           break;
/*     */       } 
/* 214 */       super.leaveElement(___uri, ___local);
/*     */     }
/*     */     
/*     */     public void enterAttribute(String ___uri, String ___local) throws UnreportedException {
/* 218 */       switch (this.state) {
/*     */         case 11:
/* 220 */           revertToParentFromEnterAttribute(___uri, ___local);
/*     */           return;
/*     */         case 0:
/* 223 */           if ("" == ___uri && "language" == ___local) {
/* 224 */             this.state = 1;
/*     */             return;
/*     */           } 
/*     */           break;
/*     */       } 
/* 229 */       super.enterAttribute(___uri, ___local);
/*     */     }
/*     */     
/*     */     public void leaveAttribute(String ___uri, String ___local) throws UnreportedException {
/* 233 */       switch (this.state) {
/*     */         case 11:
/* 235 */           revertToParentFromLeaveAttribute(___uri, ___local);
/*     */           return;
/*     */         case 2:
/* 238 */           if ("" == ___uri && "language" == ___local) {
/* 239 */             goto0();
/*     */             return;
/*     */           } 
/*     */           break;
/*     */       } 
/* 244 */       super.leaveAttribute(___uri, ___local);
/*     */     }
/*     */     
/*     */     public void text(String value) throws UnreportedException {
/*     */       try {
/* 249 */         switch (this.state) {
/*     */           case 11:
/* 251 */             revertToParentFromText(value);
/*     */             return;
/*     */           case 1:
/*     */             try {
/* 255 */               ServiceplanTypeImpl.this._Language = value;
/* 256 */             } catch (Exception e) {
/* 257 */               handleParseConversionException(e);
/*     */             } 
/* 259 */             this.state = 2;
/*     */             return;
/*     */         } 
/* 262 */       } catch (RuntimeException e) {
/* 263 */         handleUnexpectedTextException(value, e);
/*     */       } 
/*     */     }
/*     */     
/*     */     public void leaveChild(int nextState) throws UnreportedException {
/* 268 */       switch (nextState) {
/*     */         case 4:
/* 270 */           this.state = 4;
/*     */           return;
/*     */         case 10:
/* 273 */           this.state = 10;
/*     */           return;
/*     */         case 7:
/* 276 */           this.state = 7;
/*     */           return;
/*     */       } 
/* 279 */       super.leaveChild(nextState);
/*     */     }
/*     */ 
/*     */     
/*     */     private void goto0() throws UnreportedException {
/* 284 */       this.state = 0;
/* 285 */       int idx = this.context.getAttribute("", "language");
/* 286 */       if (idx >= 0) {
/* 287 */         this.context.consumeAttribute(idx);
/*     */         return;
/*     */       } 
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\lt\implementation\io\icl\model\xml\impl\ServiceplanTypeImpl.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */