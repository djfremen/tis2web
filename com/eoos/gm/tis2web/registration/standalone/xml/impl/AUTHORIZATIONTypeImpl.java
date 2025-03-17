/*     */ package com.eoos.gm.tis2web.registration.standalone.xml.impl;
/*     */ import com.eoos.gm.tis2web.registration.standalone.xml.AUTHORIZATIONType;
/*     */ import com.eoos.gm.tis2web.registration.standalone.xml.GROUPType;
/*     */ import com.eoos.gm.tis2web.registration.standalone.xml.ORGANIZATIONType;
/*     */ import com.sun.msv.verifier.DocumentDeclaration;
/*     */ import com.sun.xml.bind.serializer.XMLSerializable;
/*     */ import com.sun.xml.bind.serializer.XMLSerializer;
/*     */ import com.sun.xml.bind.unmarshaller.ContentHandlerEx;
/*     */ import com.sun.xml.bind.unmarshaller.UnmarshallingContext;
/*     */ import com.sun.xml.bind.unmarshaller.UnreportedException;
/*     */ import com.sun.xml.bind.util.ListImpl;
/*     */ import org.xml.sax.Attributes;
/*     */ import org.xml.sax.SAXException;
/*     */ 
/*     */ public class AUTHORIZATIONTypeImpl implements AUTHORIZATIONType, UnmarshallableObject, XMLSerializable, ValidatableObject {
/*  16 */   protected ListImpl _DESCRIPTION = new ListImpl(new ArrayList()); protected ORGANIZATIONType _ORGANIZATION;
/*     */   protected GROUPType _GROUP;
/*     */   protected String _AuthorizationID;
/*  19 */   private static final Grammar schemaFragment = SchemaDeserializer.deserialize("¬í\000\005sr\000\037com.sun.msv.grammar.SequenceExp\000\000\000\000\000\000\000\001\002\000\000xr\000\035com.sun.msv.grammar.BinaryExp\000\000\000\000\000\000\000\001\002\000\002L\000\004exp1t\000 Lcom/sun/msv/grammar/Expression;L\000\004exp2q\000~\000\002xr\000\036com.sun.msv.grammar.Expressionø\030èN5~O\002\000\003I\000\016cachedHashCodeL\000\023epsilonReducibilityt\000\023Ljava/lang/Boolean;L\000\013expandedExpq\000~\000\002xp\004((ppsq\000~\000\000\002\033ãÆppsq\000~\000\000\001gí.ppsr\000\035com.sun.msv.grammar.ChoiceExp\000\000\000\000\000\000\000\001\002\000\000xq\000~\000\001\000³öppsr\000'com.sun.msv.grammar.trex.ElementPattern\000\000\000\000\000\000\000\001\002\000\001L\000\tnameClasst\000\037Lcom/sun/msv/grammar/NameClass;xr\000\036com.sun.msv.grammar.ElementExp\000\000\000\000\000\000\000\001\002\000\002Z\000\032ignoreUndeclaredAttributesL\000\fcontentModelq\000~\000\002xq\000~\000\003\000YûNpp\000sq\000~\000\n\000YûCpp\000sq\000~\000\b\000Yû8ppsr\000 com.sun.msv.grammar.OneOrMoreExp\000\000\000\000\000\000\000\001\002\000\000xr\000\034com.sun.msv.grammar.UnaryExp\000\000\000\000\000\000\000\001\002\000\001L\000\003expq\000~\000\002xq\000~\000\003\000Yû-sr\000\021java.lang.BooleanÍ rÕúî\002\000\001Z\000\005valuexp\000psr\000 com.sun.msv.grammar.AttributeExp\000\000\000\000\000\000\000\001\002\000\002L\000\003expq\000~\000\002L\000\tnameClassq\000~\000\013xq\000~\000\003\000Yû*q\000~\000\024psr\0002com.sun.msv.grammar.Expression$AnyStringExpression\000\000\000\000\000\000\000\001\002\000\000xq\000~\000\003\000\000\000\bsq\000~\000\023\001psr\000 com.sun.msv.grammar.AnyNameClass\000\000\000\000\000\000\000\001\002\000\000xr\000\035com.sun.msv.grammar.NameClass\000\000\000\000\000\000\000\001\002\000\000xpsr\0000com.sun.msv.grammar.Expression$EpsilonExpression\000\000\000\000\000\000\000\001\002\000\000xq\000~\000\003\000\000\000\tq\000~\000\031psr\000#com.sun.msv.grammar.SimpleNameClass\000\000\000\000\000\000\000\001\002\000\002L\000\tlocalNamet\000\022Ljava/lang/String;L\000\fnamespaceURIq\000~\000 xq\000~\000\033t\000@com.eoos.gm.tis2web.registration.standalone.xml.ORGANIZATIONTypet\000+http://java.sun.com/jaxb/xjc/dummy-elementssq\000~\000\037t\000\fORGANIZATIONt\000\000sq\000~\000\n\000YûCpp\000sq\000~\000\b\000Yû8ppsq\000~\000\020\000Yû-q\000~\000\024psq\000~\000\025\000Yû*q\000~\000\024pq\000~\000\030q\000~\000\034q\000~\000\036sq\000~\000\037t\000<com.eoos.gm.tis2web.registration.standalone.xml.ORGANIZATIONq\000~\000#sq\000~\000\020\000³öppsq\000~\000\b\000³öppsq\000~\000\n\000YûNpp\000sq\000~\000\n\000YûCpp\000sq\000~\000\b\000Yû8ppsq\000~\000\020\000Yû-q\000~\000\024psq\000~\000\025\000Yû*q\000~\000\024pq\000~\000\030q\000~\000\034q\000~\000\036sq\000~\000\037t\000?com.eoos.gm.tis2web.registration.standalone.xml.DESCRIPTIONTypeq\000~\000#sq\000~\000\037t\000\013DESCRIPTIONq\000~\000&sq\000~\000\n\000YûCpp\000sq\000~\000\b\000Yû8ppsq\000~\000\020\000Yû-q\000~\000\024psq\000~\000\025\000Yû*q\000~\000\024pq\000~\000\030q\000~\000\034q\000~\000\036sq\000~\000\037t\000;com.eoos.gm.tis2web.registration.standalone.xml.DESCRIPTIONq\000~\000#sq\000~\000\b\000³öppsq\000~\000\n\000YûNpp\000sq\000~\000\n\000YûCpp\000sq\000~\000\b\000Yû8ppsq\000~\000\020\000Yû-q\000~\000\024psq\000~\000\025\000Yû*q\000~\000\024pq\000~\000\030q\000~\000\034q\000~\000\036sq\000~\000\037t\0009com.eoos.gm.tis2web.registration.standalone.xml.GROUPTypeq\000~\000#sq\000~\000\037t\000\005GROUPq\000~\000&sq\000~\000\n\000YûCpp\000sq\000~\000\b\000Yû8ppsq\000~\000\020\000Yû-q\000~\000\024psq\000~\000\025\000Yû*q\000~\000\024pq\000~\000\030q\000~\000\034q\000~\000\036sq\000~\000\037t\0005com.eoos.gm.tis2web.registration.standalone.xml.GROUPq\000~\000#sq\000~\000\025\002\f]ppsr\000\033com.sun.msv.grammar.DataExp\000\000\000\000\000\000\000\001\002\000\003L\000\002dtt\000\037Lorg/relaxng/datatype/Datatype;L\000\006exceptq\000~\000\002L\000\004namet\000\035Lcom/sun/msv/util/StringPair;xq\000~\000\003\000OÁfppsr\000#com.sun.msv.datatype.xsd.StringType\000\000\000\000\000\000\000\001\002\000\001Z\000\risAlwaysValidxr\000*com.sun.msv.datatype.xsd.BuiltinAtomicType\000\000\000\000\000\000\000\001\002\000\000xr\000%com.sun.msv.datatype.xsd.ConcreteType\000\000\000\000\000\000\000\001\002\000\000xr\000'com.sun.msv.datatype.xsd.XSDatatypeImpl\000\000\000\000\000\000\000\001\002\000\003L\000\fnamespaceUriq\000~\000 L\000\btypeNameq\000~\000 L\000\nwhiteSpacet\000.Lcom/sun/msv/datatype/xsd/WhiteSpaceProcessor;xpt\000 http://www.w3.org/2001/XMLSchemat\000\006stringsr\000.com.sun.msv.datatype.xsd.WhiteSpaceProcessor$1\023JMoIÛ¤G\002\000\000xr\000,com.sun.msv.datatype.xsd.WhiteSpaceProcessor\000\000\000\000\000\000\000\001\002\000\000xp\001sr\0000com.sun.msv.grammar.Expression$NullSetExpression\000\000\000\000\000\000\000\001\002\000\000xq\000~\000\003\000\000\000\nppsr\000\033com.sun.msv.util.StringPairÐt\036jB \002\000\002L\000\tlocalNameq\000~\000 L\000\fnamespaceURIq\000~\000 xpq\000~\000Zq\000~\000Ysq\000~\000\037t\000\017AuthorizationIDq\000~\000&sr\000\"com.sun.msv.grammar.ExpressionPool\000\000\000\000\000\000\000\001\002\000\001L\000\bexpTablet\000/Lcom/sun/msv/grammar/ExpressionPool$ClosedHash;xpsr\000-com.sun.msv.grammar.ExpressionPool$ClosedHash×jÐNïèí\034\002\000\004I\000\005countI\000\tthresholdL\000\006parentq\000~\000e[\000\005tablet\000![Lcom/sun/msv/grammar/Expression;xp\000\000\000\023\000\000\0009pur\000![Lcom.sun.msv.grammar.Expression;Ö8DÃ]­§\n\002\000\000xp\000\000\000¿ppppppppq\000~\000\tq\000~\000.q\000~\000>q\000~\000-ppppppppppppq\000~\000\007ppppppppppppq\000~\000\006pppppppq\000~\000\005pppppppppppppppppppppppppq\000~\000\022q\000~\000)q\000~\0002q\000~\000:q\000~\000Bq\000~\000Jpppppq\000~\000\017q\000~\000(q\000~\0001q\000~\0009q\000~\000Aq\000~\000Ippppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppp");
/*     */ 
/*     */   
/*     */   private static final Class PRIMARY_INTERFACE_CLASS() {
/*  23 */     return AUTHORIZATIONType.class;
/*     */   }
/*     */   
/*     */   public ORGANIZATIONType getORGANIZATION() {
/*  27 */     return this._ORGANIZATION;
/*     */   }
/*     */   
/*     */   public void setORGANIZATION(ORGANIZATIONType value) {
/*  31 */     this._ORGANIZATION = value;
/*     */   }
/*     */   
/*     */   public List getDESCRIPTION() {
/*  35 */     return (List)this._DESCRIPTION;
/*     */   }
/*     */   
/*     */   public GROUPType getGROUP() {
/*  39 */     return this._GROUP;
/*     */   }
/*     */   
/*     */   public void setGROUP(GROUPType value) {
/*  43 */     this._GROUP = value;
/*     */   }
/*     */   
/*     */   public String getAuthorizationID() {
/*  47 */     return this._AuthorizationID;
/*     */   }
/*     */   
/*     */   public void setAuthorizationID(String value) {
/*  51 */     this._AuthorizationID = value;
/*     */   }
/*     */   
/*     */   public ContentHandlerEx getUnmarshaller(UnmarshallingContext context) {
/*  55 */     return new Unmarshaller(context);
/*     */   }
/*     */   
/*     */   public Class getPrimaryInterfaceClass() {
/*  59 */     return PRIMARY_INTERFACE_CLASS();
/*     */   }
/*     */   
/*     */   public void serializeElements(XMLSerializer context) throws SAXException {
/*  63 */     int idx2 = 0;
/*  64 */     int len2 = this._DESCRIPTION.size();
/*  65 */     if (this._ORGANIZATION instanceof javax.xml.bind.Element) {
/*  66 */       context.childAsElements((XMLSerializable)this._ORGANIZATION);
/*     */     } else {
/*  68 */       context.startElement("", "ORGANIZATION");
/*  69 */       context.childAsAttributes((XMLSerializable)this._ORGANIZATION);
/*  70 */       context.endAttributes();
/*  71 */       context.childAsElements((XMLSerializable)this._ORGANIZATION);
/*  72 */       context.endElement();
/*     */     } 
/*  74 */     while (idx2 != len2) {
/*  75 */       if (this._DESCRIPTION.get(idx2) instanceof javax.xml.bind.Element) {
/*  76 */         context.childAsElements((XMLSerializable)this._DESCRIPTION.get(idx2++)); continue;
/*     */       } 
/*  78 */       context.startElement("", "DESCRIPTION");
/*  79 */       int idx_1 = idx2;
/*  80 */       context.childAsAttributes((XMLSerializable)this._DESCRIPTION.get(idx_1++));
/*  81 */       context.endAttributes();
/*  82 */       context.childAsElements((XMLSerializable)this._DESCRIPTION.get(idx2++));
/*  83 */       context.endElement();
/*     */     } 
/*     */     
/*  86 */     if (this._GROUP instanceof javax.xml.bind.Element) {
/*  87 */       context.childAsElements((XMLSerializable)this._GROUP);
/*     */     } else {
/*  89 */       context.startElement("", "GROUP");
/*  90 */       context.childAsAttributes((XMLSerializable)this._GROUP);
/*  91 */       context.endAttributes();
/*  92 */       context.childAsElements((XMLSerializable)this._GROUP);
/*  93 */       context.endElement();
/*     */     } 
/*     */   }
/*     */   
/*     */   public void serializeAttributes(XMLSerializer context) throws SAXException {
/*  98 */     this._DESCRIPTION.size();
/*  99 */     context.startAttribute("", "AuthorizationID");
/*     */     try {
/* 101 */       context.text(this._AuthorizationID);
/* 102 */     } catch (Exception e) {
/* 103 */       Util.handlePrintConversionException(this, e, context);
/*     */     } 
/* 105 */     context.endAttribute();
/*     */   }
/*     */   
/*     */   public void serializeAttributeBodies(XMLSerializer context) throws SAXException {
/* 109 */     this._DESCRIPTION.size();
/*     */   }
/*     */   
/*     */   public Class getPrimaryInterface() {
/* 113 */     return AUTHORIZATIONType.class;
/*     */   }
/*     */   
/*     */   public DocumentDeclaration createRawValidator() {
/* 117 */     return (DocumentDeclaration)new REDocumentDeclaration(schemaFragment);
/*     */   }
/*     */   
/*     */   public class Unmarshaller
/*     */     extends ContentHandlerEx {
/*     */     public Unmarshaller(UnmarshallingContext context) {
/* 123 */       super(context, "------------");
/*     */     }
/*     */     
/*     */     protected UnmarshallableObject owner() {
/* 127 */       return AUTHORIZATIONTypeImpl.this;
/*     */     }
/*     */     
/*     */     public void enterElement(String ___uri, String ___local, Attributes __atts) throws UnreportedException {
/* 131 */       switch (this.state) {
/*     */         case 0:
/* 133 */           if ("" == ___uri && "ORGANIZATION" == ___local) {
/* 134 */             this.context.pushAttributes(__atts);
/* 135 */             goto1();
/*     */             return;
/*     */           } 
/*     */           break;
/*     */         case 6:
/* 140 */           if ("" == ___uri && "DESCRIPTION" == ___local) {
/* 141 */             this.context.pushAttributes(__atts);
/* 142 */             goto4();
/*     */             return;
/*     */           } 
/* 145 */           if ("" == ___uri && "GROUP" == ___local) {
/* 146 */             this.context.pushAttributes(__atts);
/* 147 */             goto7();
/*     */             return;
/*     */           } 
/*     */           break;
/*     */         case 3:
/* 152 */           if ("" == ___uri && "DESCRIPTION" == ___local) {
/* 153 */             this.context.pushAttributes(__atts);
/* 154 */             goto4();
/*     */             return;
/*     */           } 
/*     */           break;
/*     */         case 9:
/* 159 */           revertToParentFromEnterElement(___uri, ___local, __atts);
/*     */           return;
/*     */       } 
/* 162 */       super.enterElement(___uri, ___local, __atts);
/*     */     }
/*     */     
/*     */     public void leaveElement(String ___uri, String ___local) throws UnreportedException {
/* 166 */       switch (this.state) {
/*     */         case 9:
/* 168 */           revertToParentFromLeaveElement(___uri, ___local);
/*     */           return;
/*     */         case 8:
/* 171 */           if ("" == ___uri && "GROUP" == ___local) {
/* 172 */             this.context.popAttributes();
/* 173 */             this.state = 9;
/*     */             return;
/*     */           } 
/*     */           break;
/*     */         case 5:
/* 178 */           if ("" == ___uri && "DESCRIPTION" == ___local) {
/* 179 */             this.context.popAttributes();
/* 180 */             this.state = 6;
/*     */             return;
/*     */           } 
/*     */           break;
/*     */         case 2:
/* 185 */           if ("" == ___uri && "ORGANIZATION" == ___local) {
/* 186 */             this.context.popAttributes();
/* 187 */             this.state = 3;
/*     */             return;
/*     */           } 
/*     */           break;
/*     */       } 
/* 192 */       super.leaveElement(___uri, ___local);
/*     */     }
/*     */     
/*     */     public void enterAttribute(String ___uri, String ___local) throws UnreportedException {
/* 196 */       switch (this.state) {
/*     */         case 7:
/* 198 */           if ("" == ___uri && "GroupID" == ___local) {
/* 199 */             AUTHORIZATIONTypeImpl.this._GROUP = (GROUPTypeImpl)spawnChildFromEnterAttribute(GROUPTypeImpl.class, 8, ___uri, ___local);
/*     */             return;
/*     */           } 
/*     */           break;
/*     */         case 0:
/* 204 */           if ("" == ___uri && "AuthorizationID" == ___local) {
/* 205 */             this.state = 10;
/*     */             return;
/*     */           } 
/*     */           break;
/*     */         case 9:
/* 210 */           revertToParentFromEnterAttribute(___uri, ___local);
/*     */           return;
/*     */         case 4:
/* 213 */           if ("" == ___uri && "Locale" == ___local) {
/* 214 */             AUTHORIZATIONTypeImpl.this._DESCRIPTION.add(spawnChildFromEnterAttribute(DESCRIPTIONTypeImpl.class, 5, ___uri, ___local));
/*     */             return;
/*     */           } 
/* 217 */           if ("" == ___uri && "Description" == ___local) {
/* 218 */             AUTHORIZATIONTypeImpl.this._DESCRIPTION.add(spawnChildFromEnterAttribute(DESCRIPTIONTypeImpl.class, 5, ___uri, ___local));
/*     */             return;
/*     */           } 
/*     */           break;
/*     */         case 1:
/* 223 */           if ("" == ___uri && "OrganizationID" == ___local) {
/* 224 */             AUTHORIZATIONTypeImpl.this._ORGANIZATION = (ORGANIZATIONTypeImpl)spawnChildFromEnterAttribute(ORGANIZATIONTypeImpl.class, 2, ___uri, ___local);
/*     */             return;
/*     */           } 
/* 227 */           if ("" == ___uri && "FORM" == ___local) {
/* 228 */             AUTHORIZATIONTypeImpl.this._ORGANIZATION = (ORGANIZATIONTypeImpl)spawnChildFromEnterAttribute(ORGANIZATIONTypeImpl.class, 2, ___uri, ___local);
/*     */             return;
/*     */           } 
/* 231 */           if ("" == ___uri && "EMAIL" == ___local) {
/* 232 */             AUTHORIZATIONTypeImpl.this._ORGANIZATION = (ORGANIZATIONTypeImpl)spawnChildFromEnterAttribute(ORGANIZATIONTypeImpl.class, 2, ___uri, ___local);
/*     */             return;
/*     */           } 
/*     */           break;
/*     */       } 
/* 237 */       super.enterAttribute(___uri, ___local);
/*     */     }
/*     */     
/*     */     public void leaveAttribute(String ___uri, String ___local) throws UnreportedException {
/* 241 */       switch (this.state) {
/*     */         case 9:
/* 243 */           revertToParentFromLeaveAttribute(___uri, ___local);
/*     */           return;
/*     */         case 11:
/* 246 */           if ("" == ___uri && "AuthorizationID" == ___local) {
/* 247 */             goto0();
/*     */             return;
/*     */           } 
/*     */           break;
/*     */       } 
/* 252 */       super.leaveAttribute(___uri, ___local);
/*     */     }
/*     */     
/*     */     public void text(String value) throws UnreportedException {
/*     */       try {
/* 257 */         switch (this.state) {
/*     */           case 10:
/*     */             try {
/* 260 */               AUTHORIZATIONTypeImpl.this._AuthorizationID = value;
/* 261 */             } catch (Exception e) {
/* 262 */               handleParseConversionException(e);
/*     */             } 
/* 264 */             this.state = 11;
/*     */             return;
/*     */           case 9:
/* 267 */             revertToParentFromText(value);
/*     */             return;
/*     */         } 
/* 270 */       } catch (RuntimeException e) {
/* 271 */         handleUnexpectedTextException(value, e);
/*     */       } 
/*     */     }
/*     */     
/*     */     public void leaveChild(int nextState) throws UnreportedException {
/* 276 */       switch (nextState) {
/*     */         case 8:
/* 278 */           this.state = 8;
/*     */           return;
/*     */         case 5:
/* 281 */           this.state = 5;
/*     */           return;
/*     */         case 2:
/* 284 */           this.state = 2;
/*     */           return;
/*     */       } 
/* 287 */       super.leaveChild(nextState);
/*     */     }
/*     */ 
/*     */     
/*     */     private void goto7() throws UnreportedException {
/* 292 */       this.state = 7;
/* 293 */       int idx = this.context.getAttribute("", "GroupID");
/* 294 */       if (idx >= 0) {
/* 295 */         this.context.consumeAttribute(idx);
/*     */         return;
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     private void goto0() throws UnreportedException {
/* 302 */       this.state = 0;
/* 303 */       int idx = this.context.getAttribute("", "AuthorizationID");
/* 304 */       if (idx >= 0) {
/* 305 */         this.context.consumeAttribute(idx);
/*     */         return;
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     private void goto4() throws UnreportedException {
/* 312 */       this.state = 4;
/* 313 */       int idx = this.context.getAttribute("", "Locale");
/* 314 */       if (idx >= 0) {
/* 315 */         this.context.consumeAttribute(idx);
/*     */         return;
/*     */       } 
/* 318 */       idx = this.context.getAttribute("", "Description");
/* 319 */       if (idx >= 0) {
/* 320 */         this.context.consumeAttribute(idx);
/*     */         return;
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     private void goto1() throws UnreportedException {
/* 327 */       this.state = 1;
/* 328 */       int idx = this.context.getAttribute("", "FORM");
/* 329 */       if (idx >= 0) {
/* 330 */         this.context.consumeAttribute(idx);
/*     */         return;
/*     */       } 
/* 333 */       idx = this.context.getAttribute("", "EMAIL");
/* 334 */       if (idx >= 0) {
/* 335 */         this.context.consumeAttribute(idx);
/*     */         return;
/*     */       } 
/* 338 */       idx = this.context.getAttribute("", "OrganizationID");
/* 339 */       if (idx >= 0) {
/* 340 */         this.context.consumeAttribute(idx);
/*     */         return;
/*     */       } 
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\registration\standalone\xml\impl\AUTHORIZATIONTypeImpl.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */