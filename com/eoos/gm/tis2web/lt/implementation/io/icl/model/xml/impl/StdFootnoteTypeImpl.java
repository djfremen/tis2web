/*     */ package com.eoos.gm.tis2web.lt.implementation.io.icl.model.xml.impl;
/*     */ import com.eoos.gm.tis2web.lt.implementation.io.icl.model.xml.BulletType;
/*     */ import com.eoos.gm.tis2web.lt.implementation.io.icl.model.xml.CplusType;
/*     */ import com.eoos.gm.tis2web.lt.implementation.io.icl.model.xml.StdFootnoteType;
/*     */ import com.sun.msv.verifier.DocumentDeclaration;
/*     */ import com.sun.xml.bind.marshaller.Util;
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
/*     */ public class StdFootnoteTypeImpl implements StdFootnoteType, UnmarshallableObject, XMLSerializable, ValidatableObject {
/*  18 */   private static final Grammar schemaFragment = SchemaDeserializer.deserialize("¬í\000\005sr\000\037com.sun.msv.grammar.SequenceExp\000\000\000\000\000\000\000\001\002\000\000xr\000\035com.sun.msv.grammar.BinaryExp\000\000\000\000\000\000\000\001\002\000\002L\000\004exp1t\000 Lcom/sun/msv/grammar/Expression;L\000\004exp2q\000~\000\002xr\000\036com.sun.msv.grammar.Expressionø\030èN5~O\002\000\003I\000\016cachedHashCodeL\000\023epsilonReducibilityt\000\023Ljava/lang/Boolean;L\000\013expandedExpq\000~\000\002xp\006%\n[ppsq\000~\000\000\004?¡ppsr\000\035com.sun.msv.grammar.ChoiceExp\000\000\000\000\000\000\000\001\002\000\000xq\000~\000\001\002\037ÅÎppsq\000~\000\007\002\037ÅÃsr\000\021java.lang.BooleanÍ rÕúî\002\000\001Z\000\005valuexp\000psr\000'com.sun.msv.grammar.trex.ElementPattern\000\000\000\000\000\000\000\001\002\000\001L\000\tnameClasst\000\037Lcom/sun/msv/grammar/NameClass;xr\000\036com.sun.msv.grammar.ElementExp\000\000\000\000\000\000\000\001\002\000\002Z\000\032ignoreUndeclaredAttributesL\000\fcontentModelq\000~\000\002xq\000~\000\003\001\017âæq\000~\000\013p\000sq\000~\000\f\001\017âÛpp\000sq\000~\000\007\001\017âÐppsr\000 com.sun.msv.grammar.OneOrMoreExp\000\000\000\000\000\000\000\001\002\000\000xr\000\034com.sun.msv.grammar.UnaryExp\000\000\000\000\000\000\000\001\002\000\001L\000\003expq\000~\000\002xq\000~\000\003\001\017âÅq\000~\000\013psr\000 com.sun.msv.grammar.AttributeExp\000\000\000\000\000\000\000\001\002\000\002L\000\003expq\000~\000\002L\000\tnameClassq\000~\000\rxq\000~\000\003\001\017âÂq\000~\000\013psr\0002com.sun.msv.grammar.Expression$AnyStringExpression\000\000\000\000\000\000\000\001\002\000\000xq\000~\000\003\000\000\000\bsq\000~\000\n\001psr\000 com.sun.msv.grammar.AnyNameClass\000\000\000\000\000\000\000\001\002\000\000xr\000\035com.sun.msv.grammar.NameClass\000\000\000\000\000\000\000\001\002\000\000xpsr\0000com.sun.msv.grammar.Expression$EpsilonExpression\000\000\000\000\000\000\000\001\002\000\000xq\000~\000\003\000\000\000\tq\000~\000\031psr\000#com.sun.msv.grammar.SimpleNameClass\000\000\000\000\000\000\000\001\002\000\002L\000\tlocalNamet\000\022Ljava/lang/String;L\000\fnamespaceURIq\000~\000 xq\000~\000\033t\0001com.eoos.gm.tis2web.lt.io.icl.model.xml.CplusTypet\000+http://java.sun.com/jaxb/xjc/dummy-elementssq\000~\000\037t\000\005Cplust\000\000sq\000~\000\f\001\017âÛq\000~\000\013p\000sq\000~\000\007\001\017âÐppsq\000~\000\022\001\017âÅq\000~\000\013psq\000~\000\025\001\017âÂq\000~\000\013pq\000~\000\030q\000~\000\034q\000~\000\036sq\000~\000\037t\000-com.eoos.gm.tis2web.lt.io.icl.model.xml.Cplusq\000~\000#q\000~\000\036sq\000~\000\007\002\037ÅÎppsq\000~\000\007\002\037ÅÃq\000~\000\013psq\000~\000\f\001\017âæq\000~\000\013p\000sq\000~\000\f\001\017âÛpp\000sq\000~\000\007\001\017âÐppsq\000~\000\022\001\017âÅq\000~\000\013psq\000~\000\025\001\017âÂq\000~\000\013pq\000~\000\030q\000~\000\034q\000~\000\036sq\000~\000\037t\0002com.eoos.gm.tis2web.lt.io.icl.model.xml.BulletTypeq\000~\000#sq\000~\000\037t\000\006Bulletq\000~\000&sq\000~\000\f\001\017âÛq\000~\000\013p\000sq\000~\000\007\001\017âÐppsq\000~\000\022\001\017âÅq\000~\000\013psq\000~\000\025\001\017âÂq\000~\000\013pq\000~\000\030q\000~\000\034q\000~\000\036sq\000~\000\037t\000.com.eoos.gm.tis2web.lt.io.icl.model.xml.Bulletq\000~\000#q\000~\000\036sq\000~\000\007\001å~µppsq\000~\000\f\001å~ªq\000~\000\013p\000sr\000\033com.sun.msv.grammar.DataExp\000\000\000\000\000\000\000\001\002\000\003L\000\002dtt\000\037Lorg/relaxng/datatype/Datatype;L\000\006exceptq\000~\000\002L\000\004namet\000\035Lcom/sun/msv/util/StringPair;xq\000~\000\003\001å~ppsr\000#com.sun.msv.datatype.xsd.StringType\000\000\000\000\000\000\000\001\002\000\001Z\000\risAlwaysValidxr\000*com.sun.msv.datatype.xsd.BuiltinAtomicType\000\000\000\000\000\000\000\001\002\000\000xr\000%com.sun.msv.datatype.xsd.ConcreteType\000\000\000\000\000\000\000\001\002\000\000xr\000'com.sun.msv.datatype.xsd.XSDatatypeImpl\000\000\000\000\000\000\000\001\002\000\003L\000\fnamespaceUriq\000~\000 L\000\btypeNameq\000~\000 L\000\nwhiteSpacet\000.Lcom/sun/msv/datatype/xsd/WhiteSpaceProcessor;xpt\000 http://www.w3.org/2001/XMLSchemat\000\006stringsr\000.com.sun.msv.datatype.xsd.WhiteSpaceProcessor$1\023JMoIÛ¤G\002\000\000xr\000,com.sun.msv.datatype.xsd.WhiteSpaceProcessor\000\000\000\000\000\000\000\001\002\000\000xp\001sr\0000com.sun.msv.grammar.Expression$NullSetExpression\000\000\000\000\000\000\000\001\002\000\000xq\000~\000\003\000\000\000\nppsr\000\033com.sun.msv.util.StringPairÐt\036jB \002\000\002L\000\tlocalNameq\000~\000 L\000\fnamespaceURIq\000~\000 xpq\000~\000Kq\000~\000Jsq\000~\000\037t\000\013Descriptionq\000~\000&q\000~\000\036sr\000\"com.sun.msv.grammar.ExpressionPool\000\000\000\000\000\000\000\001\002\000\001L\000\bexpTablet\000/Lcom/sun/msv/grammar/ExpressionPool$ClosedHash;xpsr\000-com.sun.msv.grammar.ExpressionPool$ClosedHash×jÐNïèí\034\002\000\004I\000\005countI\000\tthresholdL\000\006parentq\000~\000V[\000\005tablet\000![Lcom/sun/msv/grammar/Expression;xp\000\000\000\017\000\000\0009pur\000![Lcom.sun.msv.grammar.Expression;Ö8DÃ]­§\n\002\000\000xp\000\000\000¿ppppppppq\000~\000\006pppppppppppppppppppppppppppppppppppq\000~\000>ppppppppppppq\000~\000\005ppppppppppppppppppppppppppppq\000~\000\tq\000~\000.pppppppppq\000~\000\bq\000~\000-pppppppppppq\000~\000\024q\000~\000)q\000~\0002q\000~\000:pppppppq\000~\000\021q\000~\000(q\000~\0001q\000~\0009pppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppp"); protected String _Description; protected BulletType _Bullet;
/*     */   protected CplusType _Cplus;
/*     */   
/*     */   private static final Class PRIMARY_INTERFACE_CLASS() {
/*  22 */     return StdFootnoteType.class;
/*     */   }
/*     */   
/*     */   public String getDescription() {
/*  26 */     return this._Description;
/*     */   }
/*     */   
/*     */   public void setDescription(String value) {
/*  30 */     this._Description = value;
/*     */   }
/*     */   
/*     */   public BulletType getBullet() {
/*  34 */     return this._Bullet;
/*     */   }
/*     */   
/*     */   public void setBullet(BulletType value) {
/*  38 */     this._Bullet = value;
/*     */   }
/*     */   
/*     */   public CplusType getCplus() {
/*  42 */     return this._Cplus;
/*     */   }
/*     */   
/*     */   public void setCplus(CplusType value) {
/*  46 */     this._Cplus = value;
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
/*     */   public void serializeElements(XMLSerializer context) throws SAXException {
/*  58 */     if (this._Cplus != null) {
/*  59 */       if (this._Cplus instanceof javax.xml.bind.Element) {
/*  60 */         context.childAsElements((XMLSerializable)this._Cplus);
/*     */       } else {
/*  62 */         context.startElement("", "Cplus");
/*  63 */         context.childAsAttributes((XMLSerializable)this._Cplus);
/*  64 */         context.endAttributes();
/*  65 */         context.childAsElements((XMLSerializable)this._Cplus);
/*  66 */         context.endElement();
/*     */       } 
/*     */     }
/*  69 */     if (this._Bullet != null) {
/*  70 */       if (this._Bullet instanceof javax.xml.bind.Element) {
/*  71 */         context.childAsElements((XMLSerializable)this._Bullet);
/*     */       } else {
/*  73 */         context.startElement("", "Bullet");
/*  74 */         context.childAsAttributes((XMLSerializable)this._Bullet);
/*  75 */         context.endAttributes();
/*  76 */         context.childAsElements((XMLSerializable)this._Bullet);
/*  77 */         context.endElement();
/*     */       } 
/*     */     }
/*  80 */     if (this._Description != null) {
/*  81 */       context.startElement("", "Description");
/*  82 */       context.endAttributes();
/*     */       try {
/*  84 */         context.text(this._Description);
/*  85 */       } catch (Exception e) {
/*  86 */         Util.handlePrintConversionException(this, e, context);
/*     */       } 
/*  88 */       context.endElement();
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
/*  99 */     return StdFootnoteType.class;
/*     */   }
/*     */   
/*     */   public DocumentDeclaration createRawValidator() {
/* 103 */     return (DocumentDeclaration)new REDocumentDeclaration(schemaFragment);
/*     */   }
/*     */   
/*     */   public class Unmarshaller
/*     */     extends ContentHandlerEx {
/*     */     public Unmarshaller(UnmarshallingContext context) {
/* 109 */       super(context, "-------");
/*     */     }
/*     */     
/*     */     protected UnmarshallableObject owner() {
/* 113 */       return StdFootnoteTypeImpl.this;
/*     */     }
/*     */     
/*     */     public void enterElement(String ___uri, String ___local, Attributes __atts) throws UnreportedException {
/* 117 */       switch (this.state) {
/*     */         case 0:
/* 119 */           if ("" == ___uri && "Bullet" == ___local) {
/* 120 */             this.context.pushAttributes(__atts);
/* 121 */             goto5();
/*     */             return;
/*     */           } 
/* 124 */           if ("" == ___uri && "Description" == ___local) {
/* 125 */             this.context.pushAttributes(__atts);
/* 126 */             this.state = 1;
/*     */             return;
/*     */           } 
/* 129 */           if ("" == ___uri && "Cplus" == ___local) {
/* 130 */             this.context.pushAttributes(__atts);
/* 131 */             goto3();
/*     */             return;
/*     */           } 
/* 134 */           revertToParentFromEnterElement(___uri, ___local, __atts);
/*     */           return;
/*     */       } 
/* 137 */       super.enterElement(___uri, ___local, __atts);
/*     */     }
/*     */     
/*     */     public void leaveElement(String ___uri, String ___local) throws UnreportedException {
/* 141 */       switch (this.state) {
/*     */         case 5:
/* 143 */           if ("" == ___uri && "Bullet" == ___local) {
/* 144 */             StdFootnoteTypeImpl.this._Bullet = (BulletTypeImpl)spawnChildFromLeaveElement(BulletTypeImpl.class, 6, ___uri, ___local);
/*     */             return;
/*     */           } 
/*     */           break;
/*     */         case 4:
/* 149 */           if ("" == ___uri && "Cplus" == ___local) {
/* 150 */             this.context.popAttributes();
/* 151 */             this.state = 0;
/*     */             return;
/*     */           } 
/*     */           break;
/*     */         case 2:
/* 156 */           if ("" == ___uri && "Description" == ___local) {
/* 157 */             this.context.popAttributes();
/* 158 */             this.state = 0;
/*     */             return;
/*     */           } 
/*     */           break;
/*     */         case 6:
/* 163 */           if ("" == ___uri && "Bullet" == ___local) {
/* 164 */             this.context.popAttributes();
/* 165 */             this.state = 0;
/*     */             return;
/*     */           } 
/*     */           break;
/*     */         case 3:
/* 170 */           if ("" == ___uri && "Cplus" == ___local) {
/* 171 */             StdFootnoteTypeImpl.this._Cplus = (CplusTypeImpl)spawnChildFromLeaveElement(CplusTypeImpl.class, 4, ___uri, ___local);
/*     */             return;
/*     */           } 
/*     */           break;
/*     */         case 0:
/* 176 */           revertToParentFromLeaveElement(___uri, ___local);
/*     */           return;
/*     */       } 
/* 179 */       super.leaveElement(___uri, ___local);
/*     */     }
/*     */     
/*     */     public void enterAttribute(String ___uri, String ___local) throws UnreportedException {
/* 183 */       switch (this.state) {
/*     */         case 5:
/* 185 */           if ("" == ___uri && "dummy" == ___local) {
/* 186 */             StdFootnoteTypeImpl.this._Bullet = (BulletTypeImpl)spawnChildFromEnterAttribute(BulletTypeImpl.class, 6, ___uri, ___local);
/*     */             return;
/*     */           } 
/*     */           break;
/*     */         case 3:
/* 191 */           if ("" == ___uri && "dummy" == ___local) {
/* 192 */             StdFootnoteTypeImpl.this._Cplus = (CplusTypeImpl)spawnChildFromEnterAttribute(CplusTypeImpl.class, 4, ___uri, ___local);
/*     */             return;
/*     */           } 
/*     */           break;
/*     */         case 0:
/* 197 */           revertToParentFromEnterAttribute(___uri, ___local);
/*     */           return;
/*     */       } 
/* 200 */       super.enterAttribute(___uri, ___local);
/*     */     }
/*     */     
/*     */     public void leaveAttribute(String ___uri, String ___local) throws UnreportedException {
/* 204 */       switch (this.state) {
/*     */         case 0:
/* 206 */           revertToParentFromLeaveAttribute(___uri, ___local);
/*     */           return;
/*     */       } 
/* 209 */       super.leaveAttribute(___uri, ___local);
/*     */     }
/*     */     
/*     */     public void text(String value) throws UnreportedException {
/*     */       try {
/* 214 */         switch (this.state) {
/*     */           case 1:
/*     */             try {
/* 217 */               StdFootnoteTypeImpl.this._Description = value;
/* 218 */             } catch (Exception e) {
/* 219 */               handleParseConversionException(e);
/*     */             } 
/* 221 */             this.state = 2;
/*     */             return;
/*     */           case 0:
/* 224 */             revertToParentFromText(value);
/*     */             return;
/*     */         } 
/* 227 */       } catch (RuntimeException e) {
/* 228 */         handleUnexpectedTextException(value, e);
/*     */       } 
/*     */     }
/*     */     
/*     */     public void leaveChild(int nextState) throws UnreportedException {
/* 233 */       switch (nextState) {
/*     */         case 4:
/* 235 */           this.state = 4;
/*     */           return;
/*     */         case 6:
/* 238 */           this.state = 6;
/*     */           return;
/*     */       } 
/* 241 */       super.leaveChild(nextState);
/*     */     }
/*     */ 
/*     */     
/*     */     private void goto5() throws UnreportedException {
/* 246 */       this.state = 5;
/* 247 */       int idx = this.context.getAttribute("", "dummy");
/* 248 */       if (idx >= 0) {
/* 249 */         this.context.consumeAttribute(idx);
/*     */         return;
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     private void goto3() throws UnreportedException {
/* 256 */       this.state = 3;
/* 257 */       int idx = this.context.getAttribute("", "dummy");
/* 258 */       if (idx >= 0) {
/* 259 */         this.context.consumeAttribute(idx);
/*     */         return;
/*     */       } 
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\lt\implementation\io\icl\model\xml\impl\StdFootnoteTypeImpl.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */