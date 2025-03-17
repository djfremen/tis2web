/*     */ package com.eoos.gm.tis2web.registration.standalone.xml.impl;
/*     */ import com.eoos.gm.tis2web.registration.standalone.xml.TIS2WEBType;
/*     */ import com.sun.msv.verifier.DocumentDeclaration;
/*     */ import com.sun.xml.bind.marshaller.Util;
/*     */ import com.sun.xml.bind.serializer.XMLSerializable;
/*     */ import com.sun.xml.bind.serializer.XMLSerializer;
/*     */ import com.sun.xml.bind.unmarshaller.ContentHandlerEx;
/*     */ import com.sun.xml.bind.unmarshaller.UnmarshallingContext;
/*     */ import com.sun.xml.bind.unmarshaller.UnreportedException;
/*     */ import com.sun.xml.bind.util.ListImpl;
/*     */ import java.util.List;
/*     */ import org.xml.sax.Attributes;
/*     */ import org.xml.sax.SAXException;
/*     */ 
/*     */ public class TIS2WEBTypeImpl implements TIS2WEBType, UnmarshallableObject, XMLSerializable, ValidatableObject {
/*     */   protected String _FORM;
/*  17 */   protected ListImpl _AUTHORIZATION = new ListImpl(new ArrayList()); protected String _Version;
/*     */   protected String _EMAIL;
/*  19 */   private static final Grammar schemaFragment = SchemaDeserializer.deserialize("¬í\000\005sr\000\037com.sun.msv.grammar.SequenceExp\000\000\000\000\000\000\000\001\002\000\000xr\000\035com.sun.msv.grammar.BinaryExp\000\000\000\000\000\000\000\001\002\000\002L\000\004exp1t\000 Lcom/sun/msv/grammar/Expression;L\000\004exp2q\000~\000\002xr\000\036com.sun.msv.grammar.Expressionø\030èN5~O\002\000\003I\000\016cachedHashCodeL\000\023epsilonReducibilityt\000\023Ljava/lang/Boolean;L\000\013expandedExpq\000~\000\002xp\004Ð¬mppsq\000~\000\000\003èý\022ppsq\000~\000\000\002;ppsr\000 com.sun.msv.grammar.OneOrMoreExp\000\000\000\000\000\000\000\001\002\000\000xr\000\034com.sun.msv.grammar.UnaryExp\000\000\000\000\000\000\000\001\002\000\001L\000\003expq\000~\000\002xq\000~\000\003\000³öppsr\000\035com.sun.msv.grammar.ChoiceExp\000\000\000\000\000\000\000\001\002\000\000xq\000~\000\001\000³öppsr\000'com.sun.msv.grammar.trex.ElementPattern\000\000\000\000\000\000\000\001\002\000\001L\000\tnameClasst\000\037Lcom/sun/msv/grammar/NameClass;xr\000\036com.sun.msv.grammar.ElementExp\000\000\000\000\000\000\000\001\002\000\002Z\000\032ignoreUndeclaredAttributesL\000\fcontentModelq\000~\000\002xq\000~\000\003\000YûNpp\000sq\000~\000\r\000YûCpp\000sq\000~\000\013\000Yû8ppsq\000~\000\b\000Yû-sr\000\021java.lang.BooleanÍ rÕúî\002\000\001Z\000\005valuexp\000psr\000 com.sun.msv.grammar.AttributeExp\000\000\000\000\000\000\000\001\002\000\002L\000\003expq\000~\000\002L\000\tnameClassq\000~\000\016xq\000~\000\003\000Yû*q\000~\000\025psr\0002com.sun.msv.grammar.Expression$AnyStringExpression\000\000\000\000\000\000\000\001\002\000\000xq\000~\000\003\000\000\000\bsq\000~\000\024\001psr\000 com.sun.msv.grammar.AnyNameClass\000\000\000\000\000\000\000\001\002\000\000xr\000\035com.sun.msv.grammar.NameClass\000\000\000\000\000\000\000\001\002\000\000xpsr\0000com.sun.msv.grammar.Expression$EpsilonExpression\000\000\000\000\000\000\000\001\002\000\000xq\000~\000\003\000\000\000\tq\000~\000\032psr\000#com.sun.msv.grammar.SimpleNameClass\000\000\000\000\000\000\000\001\002\000\002L\000\tlocalNamet\000\022Ljava/lang/String;L\000\fnamespaceURIq\000~\000!xq\000~\000\034t\000Acom.eoos.gm.tis2web.registration.standalone.xml.AUTHORIZATIONTypet\000+http://java.sun.com/jaxb/xjc/dummy-elementssq\000~\000 t\000\rAUTHORIZATIONt\000\000sq\000~\000\r\000YûCpp\000sq\000~\000\013\000Yû8ppsq\000~\000\b\000Yû-q\000~\000\025psq\000~\000\026\000Yû*q\000~\000\025pq\000~\000\031q\000~\000\035q\000~\000\037sq\000~\000 t\000=com.eoos.gm.tis2web.registration.standalone.xml.AUTHORIZATIONq\000~\000$sq\000~\000\026\001÷ppsr\000\033com.sun.msv.grammar.DataExp\000\000\000\000\000\000\000\001\002\000\003L\000\002dtt\000\037Lorg/relaxng/datatype/Datatype;L\000\006exceptq\000~\000\002L\000\004namet\000\035Lcom/sun/msv/util/StringPair;xq\000~\000\003\000OÁfppsr\000#com.sun.msv.datatype.xsd.StringType\000\000\000\000\000\000\000\001\002\000\001Z\000\risAlwaysValidxr\000*com.sun.msv.datatype.xsd.BuiltinAtomicType\000\000\000\000\000\000\000\001\002\000\000xr\000%com.sun.msv.datatype.xsd.ConcreteType\000\000\000\000\000\000\000\001\002\000\000xr\000'com.sun.msv.datatype.xsd.XSDatatypeImpl\000\000\000\000\000\000\000\001\002\000\003L\000\fnamespaceUriq\000~\000!L\000\btypeNameq\000~\000!L\000\nwhiteSpacet\000.Lcom/sun/msv/datatype/xsd/WhiteSpaceProcessor;xpt\000 http://www.w3.org/2001/XMLSchemat\000\006stringsr\000.com.sun.msv.datatype.xsd.WhiteSpaceProcessor$1\023JMoIÛ¤G\002\000\000xr\000,com.sun.msv.datatype.xsd.WhiteSpaceProcessor\000\000\000\000\000\000\000\001\002\000\000xp\001sr\0000com.sun.msv.grammar.Expression$NullSetExpression\000\000\000\000\000\000\000\001\002\000\000xq\000~\000\003\000\000\000\nppsr\000\033com.sun.msv.util.StringPairÐt\036jB \002\000\002L\000\tlocalNameq\000~\000!L\000\fnamespaceURIq\000~\000!xpq\000~\000:q\000~\0009sq\000~\000 t\000\005EMAILq\000~\000'sq\000~\000\026\001­t{ppq\000~\0002sq\000~\000 t\000\004FORMq\000~\000'sq\000~\000\013\000ç¯Vppsq\000~\000\026\000ç¯Kq\000~\000\025psr\000\034com.sun.msv.grammar.ValueExp\000\000\000\000\000\000\000\001\002\000\003L\000\002dtq\000~\0000L\000\004nameq\000~\0001L\000\005valuet\000\022Ljava/lang/Object;xq\000~\000\003\000OÁ]ppq\000~\0008q\000~\000At\000\0031.0sq\000~\000 t\000\007Versionq\000~\000'q\000~\000\037sr\000\"com.sun.msv.grammar.ExpressionPool\000\000\000\000\000\000\000\001\002\000\001L\000\bexpTablet\000/Lcom/sun/msv/grammar/ExpressionPool$ClosedHash;xpsr\000-com.sun.msv.grammar.ExpressionPool$ClosedHash×jÐNïèí\034\002\000\004I\000\005countI\000\tthresholdL\000\006parentq\000~\000P[\000\005tablet\000![Lcom/sun/msv/grammar/Expression;xp\000\000\000\n\000\000\0009pur\000![Lcom.sun.msv.grammar.Expression;Ö8DÃ]­§\n\002\000\000xp\000\000\000¿ppppppppq\000~\000\fppq\000~\000\npppppppppppppppppppppppppppppppppppppppppppppppppppppppppppq\000~\000\023q\000~\000*pppppppppq\000~\000\022q\000~\000)ppppq\000~\000\005ppppppppppppppppppppppppppppq\000~\000\006ppppppppppppppppppppppppppppppppppppq\000~\000\007ppq\000~\000Gppppppppppppppppppppppppppppppppp");
/*     */ 
/*     */   
/*     */   private static final Class PRIMARY_INTERFACE_CLASS() {
/*  23 */     return TIS2WEBType.class;
/*     */   }
/*     */   
/*     */   public String getFORM() {
/*  27 */     return this._FORM;
/*     */   }
/*     */   
/*     */   public void setFORM(String value) {
/*  31 */     this._FORM = value;
/*     */   }
/*     */   
/*     */   public String getVersion() {
/*  35 */     return this._Version;
/*     */   }
/*     */   
/*     */   public void setVersion(String value) {
/*  39 */     this._Version = value;
/*     */   }
/*     */   
/*     */   public List getAUTHORIZATION() {
/*  43 */     return (List)this._AUTHORIZATION;
/*     */   }
/*     */   
/*     */   public String getEMAIL() {
/*  47 */     return this._EMAIL;
/*     */   }
/*     */   
/*     */   public void setEMAIL(String value) {
/*  51 */     this._EMAIL = value;
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
/*  63 */     int idx3 = 0;
/*  64 */     int len3 = this._AUTHORIZATION.size();
/*  65 */     while (idx3 != len3) {
/*  66 */       if (this._AUTHORIZATION.get(idx3) instanceof javax.xml.bind.Element) {
/*  67 */         context.childAsElements((XMLSerializable)this._AUTHORIZATION.get(idx3++)); continue;
/*     */       } 
/*  69 */       context.startElement("", "AUTHORIZATION");
/*  70 */       int idx_0 = idx3;
/*  71 */       context.childAsAttributes((XMLSerializable)this._AUTHORIZATION.get(idx_0++));
/*  72 */       context.endAttributes();
/*  73 */       context.childAsElements((XMLSerializable)this._AUTHORIZATION.get(idx3++));
/*  74 */       context.endElement();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void serializeAttributes(XMLSerializer context) throws SAXException {
/*  80 */     this._AUTHORIZATION.size();
/*  81 */     context.startAttribute("", "EMAIL");
/*     */     try {
/*  83 */       context.text(this._EMAIL);
/*  84 */     } catch (Exception e) {
/*  85 */       Util.handlePrintConversionException(this, e, context);
/*     */     } 
/*  87 */     context.endAttribute();
/*  88 */     context.startAttribute("", "FORM");
/*     */     try {
/*  90 */       context.text(this._FORM);
/*  91 */     } catch (Exception e) {
/*  92 */       Util.handlePrintConversionException(this, e, context);
/*     */     } 
/*  94 */     context.endAttribute();
/*  95 */     if (this._Version != null) {
/*  96 */       context.startAttribute("", "Version");
/*     */       try {
/*  98 */         context.text(this._Version);
/*  99 */       } catch (Exception e) {
/* 100 */         Util.handlePrintConversionException(this, e, context);
/*     */       } 
/* 102 */       context.endAttribute();
/*     */     } 
/*     */   }
/*     */   
/*     */   public void serializeAttributeBodies(XMLSerializer context) throws SAXException {
/* 107 */     this._AUTHORIZATION.size();
/*     */   }
/*     */   
/*     */   public Class getPrimaryInterface() {
/* 111 */     return TIS2WEBType.class;
/*     */   }
/*     */   
/*     */   public DocumentDeclaration createRawValidator() {
/* 115 */     return (DocumentDeclaration)new REDocumentDeclaration(schemaFragment);
/*     */   }
/*     */   
/*     */   public class Unmarshaller
/*     */     extends ContentHandlerEx {
/*     */     public Unmarshaller(UnmarshallingContext context) {
/* 121 */       super(context, "----------");
/*     */     }
/*     */     
/*     */     protected UnmarshallableObject owner() {
/* 125 */       return TIS2WEBTypeImpl.this;
/*     */     }
/*     */     
/*     */     public void enterElement(String ___uri, String ___local, Attributes __atts) throws UnreportedException {
/* 129 */       switch (this.state) {
/*     */         case 0:
/* 131 */           if ("" == ___uri && "AUTHORIZATION" == ___local) {
/* 132 */             this.context.pushAttributes(__atts);
/* 133 */             goto1();
/*     */             return;
/*     */           } 
/*     */           break;
/*     */         case 3:
/* 138 */           if ("" == ___uri && "AUTHORIZATION" == ___local) {
/* 139 */             this.context.pushAttributes(__atts);
/* 140 */             goto1();
/*     */             return;
/*     */           } 
/* 143 */           revertToParentFromEnterElement(___uri, ___local, __atts);
/*     */           return;
/*     */         case 1:
/* 146 */           if ("" == ___uri && "ORGANIZATION" == ___local) {
/* 147 */             TIS2WEBTypeImpl.this._AUTHORIZATION.add(spawnChildFromEnterElement(AUTHORIZATIONTypeImpl.class, 2, ___uri, ___local, __atts));
/*     */             return;
/*     */           } 
/*     */           break;
/*     */       } 
/* 152 */       super.enterElement(___uri, ___local, __atts);
/*     */     }
/*     */     
/*     */     public void leaveElement(String ___uri, String ___local) throws UnreportedException {
/* 156 */       switch (this.state) {
/*     */         case 2:
/* 158 */           if ("" == ___uri && "AUTHORIZATION" == ___local) {
/* 159 */             this.context.popAttributes();
/* 160 */             this.state = 3;
/*     */             return;
/*     */           } 
/*     */           break;
/*     */         case 3:
/* 165 */           revertToParentFromLeaveElement(___uri, ___local);
/*     */           return;
/*     */       } 
/* 168 */       super.leaveElement(___uri, ___local);
/*     */     }
/*     */     
/*     */     public void enterAttribute(String ___uri, String ___local) throws UnreportedException {
/* 172 */       switch (this.state) {
/*     */         case 0:
/* 174 */           if ("" == ___uri && "FORM" == ___local) {
/* 175 */             this.state = 4;
/*     */             return;
/*     */           } 
/* 178 */           if ("" == ___uri && "EMAIL" == ___local) {
/* 179 */             this.state = 6;
/*     */             return;
/*     */           } 
/* 182 */           if ("" == ___uri && "Version" == ___local) {
/* 183 */             this.state = 8;
/*     */             return;
/*     */           } 
/*     */           break;
/*     */         case 3:
/* 188 */           revertToParentFromEnterAttribute(___uri, ___local);
/*     */           return;
/*     */         case 1:
/* 191 */           if ("" == ___uri && "AuthorizationID" == ___local) {
/* 192 */             TIS2WEBTypeImpl.this._AUTHORIZATION.add(spawnChildFromEnterAttribute(AUTHORIZATIONTypeImpl.class, 2, ___uri, ___local));
/*     */             return;
/*     */           } 
/*     */           break;
/*     */       } 
/* 197 */       super.enterAttribute(___uri, ___local);
/*     */     }
/*     */     
/*     */     public void leaveAttribute(String ___uri, String ___local) throws UnreportedException {
/* 201 */       switch (this.state) {
/*     */         case 7:
/* 203 */           if ("" == ___uri && "EMAIL" == ___local) {
/* 204 */             goto0();
/*     */             return;
/*     */           } 
/*     */           break;
/*     */         case 3:
/* 209 */           revertToParentFromLeaveAttribute(___uri, ___local);
/*     */           return;
/*     */         case 9:
/* 212 */           if ("" == ___uri && "Version" == ___local) {
/* 213 */             goto0();
/*     */             return;
/*     */           } 
/*     */           break;
/*     */         case 5:
/* 218 */           if ("" == ___uri && "FORM" == ___local) {
/* 219 */             goto0();
/*     */             return;
/*     */           } 
/*     */           break;
/*     */       } 
/* 224 */       super.leaveAttribute(___uri, ___local);
/*     */     }
/*     */     
/*     */     public void text(String value) throws UnreportedException {
/*     */       try {
/* 229 */         switch (this.state) {
/*     */           case 6:
/*     */             try {
/* 232 */               TIS2WEBTypeImpl.this._EMAIL = value;
/* 233 */             } catch (Exception e) {
/* 234 */               handleParseConversionException(e);
/*     */             } 
/* 236 */             this.state = 7;
/*     */             return;
/*     */           case 4:
/*     */             try {
/* 240 */               TIS2WEBTypeImpl.this._FORM = value;
/* 241 */             } catch (Exception e) {
/* 242 */               handleParseConversionException(e);
/*     */             } 
/* 244 */             this.state = 5;
/*     */             return;
/*     */           case 3:
/* 247 */             revertToParentFromText(value);
/*     */             return;
/*     */           case 8:
/*     */             try {
/* 251 */               TIS2WEBTypeImpl.this._Version = value;
/* 252 */             } catch (Exception e) {
/* 253 */               handleParseConversionException(e);
/*     */             } 
/* 255 */             this.state = 9;
/*     */             return;
/*     */         } 
/* 258 */       } catch (RuntimeException e) {
/* 259 */         handleUnexpectedTextException(value, e);
/*     */       } 
/*     */     }
/*     */     
/*     */     public void leaveChild(int nextState) throws UnreportedException {
/* 264 */       switch (nextState) {
/*     */         case 2:
/* 266 */           this.state = 2;
/*     */           return;
/*     */       } 
/* 269 */       super.leaveChild(nextState);
/*     */     }
/*     */ 
/*     */     
/*     */     private void goto0() throws UnreportedException {
/* 274 */       this.state = 0;
/* 275 */       int idx = this.context.getAttribute("", "EMAIL");
/* 276 */       if (idx >= 0) {
/* 277 */         this.context.consumeAttribute(idx);
/*     */         return;
/*     */       } 
/* 280 */       idx = this.context.getAttribute("", "FORM");
/* 281 */       if (idx >= 0) {
/* 282 */         this.context.consumeAttribute(idx);
/*     */         return;
/*     */       } 
/* 285 */       idx = this.context.getAttribute("", "Version");
/* 286 */       if (idx >= 0) {
/* 287 */         this.context.consumeAttribute(idx);
/*     */         return;
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     private void goto1() throws UnreportedException {
/* 294 */       this.state = 1;
/* 295 */       int idx = this.context.getAttribute("", "AuthorizationID");
/* 296 */       if (idx >= 0) {
/* 297 */         this.context.consumeAttribute(idx);
/*     */         return;
/*     */       } 
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\registration\standalone\xml\impl\TIS2WEBTypeImpl.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */