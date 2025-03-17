/*     */ package com.eoos.gm.tis2web.lt.implementation.io.icl.model.xml.impl;
/*     */ import com.eoos.gm.tis2web.lt.implementation.io.icl.model.xml.FooterType;
/*     */ import com.sun.xml.bind.marshaller.Util;
/*     */ import com.sun.xml.bind.serializer.XMLSerializable;
/*     */ import com.sun.xml.bind.serializer.XMLSerializer;
/*     */ import com.sun.xml.bind.unmarshaller.UnmarshallingContext;
/*     */ import com.sun.xml.bind.unmarshaller.UnreportedException;
/*     */ import com.sun.xml.bind.util.ListImpl;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import org.xml.sax.Attributes;
/*     */ import org.xml.sax.SAXException;
/*     */ 
/*     */ public class FooterTypeImpl implements FooterType, UnmarshallableObject, XMLSerializable, ValidatableObject {
/*  15 */   protected ListImpl _PosFootnote = new ListImpl(new ArrayList());
/*     */   protected String _Check;
/*     */   protected String _Workshop;
/*     */   protected String _Confirmation;
/*  19 */   protected ListImpl _Footnote = new ListImpl(new ArrayList());
/*  20 */   protected ListImpl _StdFootnote = new ListImpl(new ArrayList());
/*     */   protected String _Mechanic;
/*     */   protected String _Defects;
/*  23 */   private static final Grammar schemaFragment = SchemaDeserializer.deserialize("¬í\000\005sr\000\037com.sun.msv.grammar.SequenceExp\000\000\000\000\000\000\000\001\002\000\000xr\000\035com.sun.msv.grammar.BinaryExp\000\000\000\000\000\000\000\001\002\000\002L\000\004exp1t\000 Lcom/sun/msv/grammar/Expression;L\000\004exp2q\000~\000\002xr\000\036com.sun.msv.grammar.Expressionø\030èN5~O\002\000\003I\000\016cachedHashCodeL\000\023epsilonReducibilityt\000\023Ljava/lang/Boolean;L\000\013expandedExpq\000~\000\002xp\017 Ïppsq\000~\000\000\r»\005 ppsq\000~\000\000\013Õqppsq\000~\000\000\tð\007Âppsq\000~\000\000\b\n\023ppsq\000~\000\000\006%\ndppsq\000~\000\000\004\005Dppsr\000\035com.sun.msv.grammar.ChoiceExp\000\000\000\000\000\000\000\001\002\000\000xq\000~\000\001\001å~¸ppsr\000 com.sun.msv.grammar.OneOrMoreExp\000\000\000\000\000\000\000\001\002\000\000xr\000\034com.sun.msv.grammar.UnaryExp\000\000\000\000\000\000\000\001\002\000\001L\000\003expq\000~\000\002xq\000~\000\003\001å~­sr\000\021java.lang.BooleanÍ rÕúî\002\000\001Z\000\005valuexp\000psr\000'com.sun.msv.grammar.trex.ElementPattern\000\000\000\000\000\000\000\001\002\000\001L\000\tnameClasst\000\037Lcom/sun/msv/grammar/NameClass;xr\000\036com.sun.msv.grammar.ElementExp\000\000\000\000\000\000\000\001\002\000\002Z\000\032ignoreUndeclaredAttributesL\000\fcontentModelq\000~\000\002xq\000~\000\003\001å~ªq\000~\000\022p\000sr\000\033com.sun.msv.grammar.DataExp\000\000\000\000\000\000\000\001\002\000\003L\000\002dtt\000\037Lorg/relaxng/datatype/Datatype;L\000\006exceptq\000~\000\002L\000\004namet\000\035Lcom/sun/msv/util/StringPair;xq\000~\000\003\001å~ppsr\000#com.sun.msv.datatype.xsd.StringType\000\000\000\000\000\000\000\001\002\000\001Z\000\risAlwaysValidxr\000*com.sun.msv.datatype.xsd.BuiltinAtomicType\000\000\000\000\000\000\000\001\002\000\000xr\000%com.sun.msv.datatype.xsd.ConcreteType\000\000\000\000\000\000\000\001\002\000\000xr\000'com.sun.msv.datatype.xsd.XSDatatypeImpl\000\000\000\000\000\000\000\001\002\000\003L\000\fnamespaceUrit\000\022Ljava/lang/String;L\000\btypeNameq\000~\000\037L\000\nwhiteSpacet\000.Lcom/sun/msv/datatype/xsd/WhiteSpaceProcessor;xpt\000 http://www.w3.org/2001/XMLSchemat\000\006stringsr\000.com.sun.msv.datatype.xsd.WhiteSpaceProcessor$1\023JMoIÛ¤G\002\000\000xr\000,com.sun.msv.datatype.xsd.WhiteSpaceProcessor\000\000\000\000\000\000\000\001\002\000\000xp\001sr\0000com.sun.msv.grammar.Expression$NullSetExpression\000\000\000\000\000\000\000\001\002\000\000xq\000~\000\003\000\000\000\nppsr\000\033com.sun.msv.util.StringPairÐt\036jB \002\000\002L\000\tlocalNameq\000~\000\037L\000\fnamespaceURIq\000~\000\037xpq\000~\000#q\000~\000\"sr\000#com.sun.msv.grammar.SimpleNameClass\000\000\000\000\000\000\000\001\002\000\002L\000\tlocalNameq\000~\000\037L\000\fnamespaceURIq\000~\000\037xr\000\035com.sun.msv.grammar.NameClass\000\000\000\000\000\000\000\001\002\000\000xpt\000\013PosFootnotet\000\000sr\0000com.sun.msv.grammar.Expression$EpsilonExpression\000\000\000\000\000\000\000\001\002\000\000xq\000~\000\003\000\000\000\tsq\000~\000\021\001psq\000~\000\f\002\037ÅÑppsq\000~\000\016\002\037ÅÆq\000~\000\022psq\000~\000\f\002\037ÅÃq\000~\000\022psq\000~\000\023\001\017âæq\000~\000\022p\000sq\000~\000\023\001\017âÛpp\000sq\000~\000\f\001\017âÐppsq\000~\000\016\001\017âÅq\000~\000\022psr\000 com.sun.msv.grammar.AttributeExp\000\000\000\000\000\000\000\001\002\000\002L\000\003expq\000~\000\002L\000\tnameClassq\000~\000\024xq\000~\000\003\001\017âÂq\000~\000\022psr\0002com.sun.msv.grammar.Expression$AnyStringExpression\000\000\000\000\000\000\000\001\002\000\000xq\000~\000\003\000\000\000\bq\000~\0002psr\000 com.sun.msv.grammar.AnyNameClass\000\000\000\000\000\000\000\001\002\000\000xq\000~\000,q\000~\0001sq\000~\000+t\0007com.eoos.gm.tis2web.lt.io.icl.model.xml.StdFootnoteTypet\000+http://java.sun.com/jaxb/xjc/dummy-elementssq\000~\000+t\000\013StdFootnoteq\000~\000/sq\000~\000\023\001\017âÛq\000~\000\022p\000sq\000~\000\f\001\017âÐppsq\000~\000\016\001\017âÅq\000~\000\022psq\000~\000:\001\017âÂq\000~\000\022pq\000~\000=q\000~\000?q\000~\0001sq\000~\000+t\0003com.eoos.gm.tis2web.lt.io.icl.model.xml.StdFootnoteq\000~\000Bq\000~\0001sq\000~\000\f\002\037ÅÑppsq\000~\000\016\002\037ÅÆq\000~\000\022psq\000~\000\f\002\037ÅÃq\000~\000\022psq\000~\000\023\001\017âæq\000~\000\022p\000sq\000~\000\023\001\017âÛpp\000sq\000~\000\f\001\017âÐppsq\000~\000\016\001\017âÅq\000~\000\022psq\000~\000:\001\017âÂq\000~\000\022pq\000~\000=q\000~\000?q\000~\0001sq\000~\000+t\0004com.eoos.gm.tis2web.lt.io.icl.model.xml.FootnoteTypeq\000~\000Bsq\000~\000+t\000\bFootnoteq\000~\000/sq\000~\000\023\001\017âÛq\000~\000\022p\000sq\000~\000\f\001\017âÐppsq\000~\000\016\001\017âÅq\000~\000\022psq\000~\000:\001\017âÂq\000~\000\022pq\000~\000=q\000~\000?q\000~\0001sq\000~\000+t\0000com.eoos.gm.tis2web.lt.io.icl.model.xml.Footnoteq\000~\000Bq\000~\0001sq\000~\000\023\001å~ªpp\000q\000~\000\032sq\000~\000+t\000\fConfirmationq\000~\000/sq\000~\000\023\001å~ªpp\000q\000~\000\032sq\000~\000+t\000\007Defectsq\000~\000/sq\000~\000\023\001å~ªpp\000q\000~\000\032sq\000~\000+t\000\bMechanicq\000~\000/sq\000~\000\023\001å~ªpp\000q\000~\000\032sq\000~\000+t\000\005Checkq\000~\000/sq\000~\000\023\001å~ªpp\000q\000~\000\032sq\000~\000+t\000\bWorkshopq\000~\000/sr\000\"com.sun.msv.grammar.ExpressionPool\000\000\000\000\000\000\000\001\002\000\001L\000\bexpTablet\000/Lcom/sun/msv/grammar/ExpressionPool$ClosedHash;xpsr\000-com.sun.msv.grammar.ExpressionPool$ClosedHash×jÐNïèí\034\002\000\004I\000\005countI\000\tthresholdL\000\006parentq\000~\000m[\000\005tablet\000![Lcom/sun/msv/grammar/Expression;xp\000\000\000\027\000\000\0009pur\000![Lcom.sun.msv.grammar.Expression;Ö8DÃ]­§\n\002\000\000xp\000\000\000¿pppppppppppppppppppppppppppq\000~\000\006ppppppppq\000~\000\020ppppppppppq\000~\000\rpppppppppppppppppq\000~\000\005q\000~\000\npppppppppppppppppppq\000~\0005q\000~\000Mpq\000~\0004q\000~\000Lpppppppppq\000~\0003q\000~\000Kppq\000~\000\tpppppq\000~\0009q\000~\000Gq\000~\000Qq\000~\000Ypppppppq\000~\0008q\000~\000Fq\000~\000Pq\000~\000Xpppppppppppppppppq\000~\000\bpppppppppq\000~\000\013pppppppppppppppppppppppppppq\000~\000\007pppppppppp");
/*     */ 
/*     */   
/*     */   private static final Class PRIMARY_INTERFACE_CLASS() {
/*  27 */     return FooterType.class;
/*     */   }
/*     */   
/*     */   public List getPosFootnote() {
/*  31 */     return (List)this._PosFootnote;
/*     */   }
/*     */   
/*     */   public String getCheck() {
/*  35 */     return this._Check;
/*     */   }
/*     */   
/*     */   public void setCheck(String value) {
/*  39 */     this._Check = value;
/*     */   }
/*     */   
/*     */   public String getWorkshop() {
/*  43 */     return this._Workshop;
/*     */   }
/*     */   
/*     */   public void setWorkshop(String value) {
/*  47 */     this._Workshop = value;
/*     */   }
/*     */   
/*     */   public String getConfirmation() {
/*  51 */     return this._Confirmation;
/*     */   }
/*     */   
/*     */   public void setConfirmation(String value) {
/*  55 */     this._Confirmation = value;
/*     */   }
/*     */   
/*     */   public List getFootnote() {
/*  59 */     return (List)this._Footnote;
/*     */   }
/*     */   
/*     */   public List getStdFootnote() {
/*  63 */     return (List)this._StdFootnote;
/*     */   }
/*     */   
/*     */   public String getMechanic() {
/*  67 */     return this._Mechanic;
/*     */   }
/*     */   
/*     */   public void setMechanic(String value) {
/*  71 */     this._Mechanic = value;
/*     */   }
/*     */   
/*     */   public String getDefects() {
/*  75 */     return this._Defects;
/*     */   }
/*     */   
/*     */   public void setDefects(String value) {
/*  79 */     this._Defects = value;
/*     */   }
/*     */   
/*     */   public ContentHandlerEx getUnmarshaller(UnmarshallingContext context) {
/*  83 */     return new Unmarshaller(context);
/*     */   }
/*     */   
/*     */   public Class getPrimaryInterfaceClass() {
/*  87 */     return PRIMARY_INTERFACE_CLASS();
/*     */   }
/*     */   
/*     */   public void serializeElements(XMLSerializer context) throws SAXException {
/*  91 */     int idx1 = 0;
/*  92 */     int len1 = this._PosFootnote.size();
/*  93 */     int idx5 = 0;
/*  94 */     int len5 = this._Footnote.size();
/*  95 */     int idx6 = 0;
/*  96 */     int len6 = this._StdFootnote.size();
/*  97 */     while (idx1 != len1) {
/*  98 */       context.startElement("", "PosFootnote");
/*  99 */       int idx_0 = idx1;
/*     */       try {
/* 101 */         idx_0++;
/* 102 */       } catch (Exception e) {
/* 103 */         Util.handlePrintConversionException(this, e, context);
/*     */       } 
/* 105 */       context.endAttributes();
/*     */       try {
/* 107 */         context.text((String)this._PosFootnote.get(idx1++));
/* 108 */       } catch (Exception e) {
/* 109 */         Util.handlePrintConversionException(this, e, context);
/*     */       } 
/* 111 */       context.endElement();
/*     */     } 
/* 113 */     while (idx6 != len6) {
/* 114 */       if (this._StdFootnote.get(idx6) instanceof javax.xml.bind.Element) {
/* 115 */         context.childAsElements((XMLSerializable)this._StdFootnote.get(idx6++)); continue;
/*     */       } 
/* 117 */       context.startElement("", "StdFootnote");
/* 118 */       int idx_1 = idx6;
/* 119 */       context.childAsAttributes((XMLSerializable)this._StdFootnote.get(idx_1++));
/* 120 */       context.endAttributes();
/* 121 */       context.childAsElements((XMLSerializable)this._StdFootnote.get(idx6++));
/* 122 */       context.endElement();
/*     */     } 
/*     */     
/* 125 */     while (idx5 != len5) {
/* 126 */       if (this._Footnote.get(idx5) instanceof javax.xml.bind.Element) {
/* 127 */         context.childAsElements((XMLSerializable)this._Footnote.get(idx5++)); continue;
/*     */       } 
/* 129 */       context.startElement("", "Footnote");
/* 130 */       int idx_2 = idx5;
/* 131 */       context.childAsAttributes((XMLSerializable)this._Footnote.get(idx_2++));
/* 132 */       context.endAttributes();
/* 133 */       context.childAsElements((XMLSerializable)this._Footnote.get(idx5++));
/* 134 */       context.endElement();
/*     */     } 
/*     */     
/* 137 */     context.startElement("", "Confirmation");
/* 138 */     context.endAttributes();
/*     */     try {
/* 140 */       context.text(this._Confirmation);
/* 141 */     } catch (Exception e) {
/* 142 */       Util.handlePrintConversionException(this, e, context);
/*     */     } 
/* 144 */     context.endElement();
/* 145 */     context.startElement("", "Defects");
/* 146 */     context.endAttributes();
/*     */     try {
/* 148 */       context.text(this._Defects);
/* 149 */     } catch (Exception e) {
/* 150 */       Util.handlePrintConversionException(this, e, context);
/*     */     } 
/* 152 */     context.endElement();
/* 153 */     context.startElement("", "Mechanic");
/* 154 */     context.endAttributes();
/*     */     try {
/* 156 */       context.text(this._Mechanic);
/* 157 */     } catch (Exception e) {
/* 158 */       Util.handlePrintConversionException(this, e, context);
/*     */     } 
/* 160 */     context.endElement();
/* 161 */     context.startElement("", "Check");
/* 162 */     context.endAttributes();
/*     */     try {
/* 164 */       context.text(this._Check);
/* 165 */     } catch (Exception e) {
/* 166 */       Util.handlePrintConversionException(this, e, context);
/*     */     } 
/* 168 */     context.endElement();
/* 169 */     context.startElement("", "Workshop");
/* 170 */     context.endAttributes();
/*     */     try {
/* 172 */       context.text(this._Workshop);
/* 173 */     } catch (Exception e) {
/* 174 */       Util.handlePrintConversionException(this, e, context);
/*     */     } 
/* 176 */     context.endElement();
/*     */   }
/*     */   
/*     */   public void serializeAttributes(XMLSerializer context) throws SAXException {
/* 180 */     this._PosFootnote.size();
/* 181 */     this._Footnote.size();
/* 182 */     this._StdFootnote.size();
/*     */   }
/*     */   
/*     */   public void serializeAttributeBodies(XMLSerializer context) throws SAXException {
/* 186 */     this._PosFootnote.size();
/* 187 */     this._Footnote.size();
/* 188 */     this._StdFootnote.size();
/*     */   }
/*     */   
/*     */   public Class getPrimaryInterface() {
/* 192 */     return FooterType.class;
/*     */   }
/*     */   
/*     */   public DocumentDeclaration createRawValidator() {
/* 196 */     return (DocumentDeclaration)new REDocumentDeclaration(schemaFragment);
/*     */   }
/*     */   
/*     */   public class Unmarshaller
/*     */     extends ContentHandlerEx {
/*     */     public Unmarshaller(UnmarshallingContext context) {
/* 202 */       super(context, "----------------------");
/*     */     }
/*     */     
/*     */     protected UnmarshallableObject owner() {
/* 206 */       return FooterTypeImpl.this;
/*     */     }
/*     */     
/*     */     public void enterElement(String ___uri, String ___local, Attributes __atts) throws UnreportedException {
/* 210 */       switch (this.state) {
/*     */         case 3:
/* 212 */           if ("" == ___uri && "Defects" == ___local) {
/* 213 */             this.context.pushAttributes(__atts);
/* 214 */             this.state = 4;
/*     */             return;
/*     */           } 
/*     */           break;
/*     */         case 6:
/* 219 */           if ("" == ___uri && "Mechanic" == ___local) {
/* 220 */             this.context.pushAttributes(__atts);
/* 221 */             this.state = 7;
/*     */             return;
/*     */           } 
/*     */           break;
/*     */         case 15:
/* 226 */           revertToParentFromEnterElement(___uri, ___local, __atts);
/*     */           return;
/*     */         case 0:
/* 229 */           if ("" == ___uri && "PosFootnote" == ___local) {
/* 230 */             this.context.pushAttributes(__atts);
/* 231 */             this.state = 16;
/*     */             return;
/*     */           } 
/* 234 */           if ("" == ___uri && "Footnote" == ___local) {
/* 235 */             this.context.pushAttributes(__atts);
/* 236 */             this.state = 20;
/*     */             return;
/*     */           } 
/* 239 */           if ("" == ___uri && "StdFootnote" == ___local) {
/* 240 */             this.context.pushAttributes(__atts);
/* 241 */             this.state = 18;
/*     */             return;
/*     */           } 
/* 244 */           if ("" == ___uri && "Confirmation" == ___local) {
/* 245 */             this.context.pushAttributes(__atts);
/* 246 */             this.state = 1;
/*     */             return;
/*     */           } 
/*     */           break;
/*     */         case 9:
/* 251 */           if ("" == ___uri && "Check" == ___local) {
/* 252 */             this.context.pushAttributes(__atts);
/* 253 */             this.state = 10;
/*     */             return;
/*     */           } 
/*     */           break;
/*     */         case 12:
/* 258 */           if ("" == ___uri && "Workshop" == ___local) {
/* 259 */             this.context.pushAttributes(__atts);
/* 260 */             this.state = 13;
/*     */             return;
/*     */           } 
/*     */           break;
/*     */         case 18:
/* 265 */           if ("" == ___uri && "Description" == ___local) {
/* 266 */             FooterTypeImpl.this._StdFootnote.add(spawnChildFromEnterElement(StdFootnoteTypeImpl.class, 19, ___uri, ___local, __atts));
/*     */             return;
/*     */           } 
/* 269 */           if ("" == ___uri && "Bullet" == ___local) {
/* 270 */             FooterTypeImpl.this._StdFootnote.add(spawnChildFromEnterElement(StdFootnoteTypeImpl.class, 19, ___uri, ___local, __atts));
/*     */             return;
/*     */           } 
/* 273 */           if ("" == ___uri && "Cplus" == ___local) {
/* 274 */             FooterTypeImpl.this._StdFootnote.add(spawnChildFromEnterElement(StdFootnoteTypeImpl.class, 19, ___uri, ___local, __atts));
/*     */             return;
/*     */           } 
/*     */           break;
/*     */         case 20:
/* 279 */           if ("" == ___uri && "Sign" == ___local) {
/* 280 */             FooterTypeImpl.this._Footnote.add(spawnChildFromEnterElement(FootnoteTypeImpl.class, 21, ___uri, ___local, __atts));
/*     */             return;
/*     */           } 
/*     */           break;
/*     */       } 
/* 285 */       super.enterElement(___uri, ___local, __atts);
/*     */     }
/*     */     
/*     */     public void leaveElement(String ___uri, String ___local) throws UnreportedException {
/* 289 */       switch (this.state) {
/*     */         case 21:
/* 291 */           if ("" == ___uri && "Footnote" == ___local) {
/* 292 */             this.context.popAttributes();
/* 293 */             this.state = 0;
/*     */             return;
/*     */           } 
/*     */           break;
/*     */         case 19:
/* 298 */           if ("" == ___uri && "StdFootnote" == ___local) {
/* 299 */             this.context.popAttributes();
/* 300 */             this.state = 0;
/*     */             return;
/*     */           } 
/*     */           break;
/*     */         case 11:
/* 305 */           if ("" == ___uri && "Check" == ___local) {
/* 306 */             this.context.popAttributes();
/* 307 */             this.state = 12;
/*     */             return;
/*     */           } 
/*     */           break;
/*     */         case 15:
/* 312 */           revertToParentFromLeaveElement(___uri, ___local);
/*     */           return;
/*     */         case 8:
/* 315 */           if ("" == ___uri && "Mechanic" == ___local) {
/* 316 */             this.context.popAttributes();
/* 317 */             this.state = 9;
/*     */             return;
/*     */           } 
/*     */           break;
/*     */         case 18:
/* 322 */           if ("" == ___uri && "StdFootnote" == ___local) {
/* 323 */             FooterTypeImpl.this._StdFootnote.add(spawnChildFromLeaveElement(StdFootnoteTypeImpl.class, 19, ___uri, ___local));
/*     */             return;
/*     */           } 
/*     */           break;
/*     */         case 14:
/* 328 */           if ("" == ___uri && "Workshop" == ___local) {
/* 329 */             this.context.popAttributes();
/* 330 */             this.state = 15;
/*     */             return;
/*     */           } 
/*     */           break;
/*     */         case 17:
/* 335 */           if ("" == ___uri && "PosFootnote" == ___local) {
/* 336 */             this.context.popAttributes();
/* 337 */             this.state = 0;
/*     */             return;
/*     */           } 
/*     */           break;
/*     */         case 2:
/* 342 */           if ("" == ___uri && "Confirmation" == ___local) {
/* 343 */             this.context.popAttributes();
/* 344 */             this.state = 3;
/*     */             return;
/*     */           } 
/*     */           break;
/*     */         case 5:
/* 349 */           if ("" == ___uri && "Defects" == ___local) {
/* 350 */             this.context.popAttributes();
/* 351 */             this.state = 6;
/*     */             return;
/*     */           } 
/*     */           break;
/*     */       } 
/* 356 */       super.leaveElement(___uri, ___local);
/*     */     }
/*     */     
/*     */     public void enterAttribute(String ___uri, String ___local) throws UnreportedException {
/* 360 */       switch (this.state) {
/*     */         case 15:
/* 362 */           revertToParentFromEnterAttribute(___uri, ___local);
/*     */           return;
/*     */       } 
/* 365 */       super.enterAttribute(___uri, ___local);
/*     */     }
/*     */     
/*     */     public void leaveAttribute(String ___uri, String ___local) throws UnreportedException {
/* 369 */       switch (this.state) {
/*     */         case 15:
/* 371 */           revertToParentFromLeaveAttribute(___uri, ___local);
/*     */           return;
/*     */       } 
/* 374 */       super.leaveAttribute(___uri, ___local);
/*     */     }
/*     */     
/*     */     public void text(String value) throws UnreportedException {
/*     */       try {
/* 379 */         switch (this.state) {
/*     */           case 7:
/*     */             try {
/* 382 */               FooterTypeImpl.this._Mechanic = value;
/* 383 */             } catch (Exception e) {
/* 384 */               handleParseConversionException(e);
/*     */             } 
/* 386 */             this.state = 8;
/*     */             return;
/*     */           case 10:
/*     */             try {
/* 390 */               FooterTypeImpl.this._Check = value;
/* 391 */             } catch (Exception e) {
/* 392 */               handleParseConversionException(e);
/*     */             } 
/* 394 */             this.state = 11;
/*     */             return;
/*     */           case 15:
/* 397 */             revertToParentFromText(value);
/*     */             return;
/*     */           case 1:
/*     */             try {
/* 401 */               FooterTypeImpl.this._Confirmation = value;
/* 402 */             } catch (Exception e) {
/* 403 */               handleParseConversionException(e);
/*     */             } 
/* 405 */             this.state = 2;
/*     */             return;
/*     */           case 4:
/*     */             try {
/* 409 */               FooterTypeImpl.this._Defects = value;
/* 410 */             } catch (Exception e) {
/* 411 */               handleParseConversionException(e);
/*     */             } 
/* 413 */             this.state = 5;
/*     */             return;
/*     */           case 16:
/*     */             try {
/* 417 */               FooterTypeImpl.this._PosFootnote.add(value);
/* 418 */             } catch (Exception e) {
/* 419 */               handleParseConversionException(e);
/*     */             } 
/* 421 */             this.state = 17;
/*     */             return;
/*     */           case 13:
/*     */             try {
/* 425 */               FooterTypeImpl.this._Workshop = value;
/* 426 */             } catch (Exception e) {
/* 427 */               handleParseConversionException(e);
/*     */             } 
/* 429 */             this.state = 14;
/*     */             return;
/*     */         } 
/* 432 */       } catch (RuntimeException e) {
/* 433 */         handleUnexpectedTextException(value, e);
/*     */       } 
/*     */     }
/*     */     
/*     */     public void leaveChild(int nextState) throws UnreportedException {
/* 438 */       switch (nextState) {
/*     */         case 21:
/* 440 */           this.state = 21;
/*     */           return;
/*     */         case 19:
/* 443 */           this.state = 19;
/*     */           return;
/*     */       } 
/* 446 */       super.leaveChild(nextState);
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\lt\implementation\io\icl\model\xml\impl\FooterTypeImpl.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */