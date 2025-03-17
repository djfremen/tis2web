/*     */ package com.eoos.gm.tis2web.lt.implementation.io.icl.model.xml.impl;
/*     */ import com.eoos.gm.tis2web.lt.implementation.io.icl.model.xml.PositionType;
/*     */ import com.eoos.gm.tis2web.lt.implementation.io.icl.model.xml.StdFootnoteType;
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
/*     */ public class PositionTypeImpl implements PositionType, UnmarshallableObject, XMLSerializable, ValidatableObject {
/*     */   protected String _Comment;
/*  17 */   protected ListImpl _Footnote = new ListImpl(new ArrayList()); protected String _Nr;
/*     */   protected StdFootnoteType _StdFootnote;
/*     */   protected String _Text;
/*  20 */   protected ListImpl _Parameter = new ListImpl(new ArrayList());
/*  21 */   private static final Grammar schemaFragment = SchemaDeserializer.deserialize("¬í\000\005sr\000\037com.sun.msv.grammar.SequenceExp\000\000\000\000\000\000\000\001\002\000\000xr\000\035com.sun.msv.grammar.BinaryExp\000\000\000\000\000\000\000\001\002\000\002L\000\004exp1t\000 Lcom/sun/msv/grammar/Expression;L\000\004exp2q\000~\000\002xr\000\036com.sun.msv.grammar.Expressionø\030èN5~O\002\000\003I\000\016cachedHashCodeL\000\023epsilonReducibilityt\000\023Ljava/lang/Boolean;L\000\013expandedExpq\000~\000\002xp\røÂppsq\000~\000\000\n*Nãppsq\000~\000\000\b\n\rppsq\000~\000\000\005êÃ7ppsq\000~\000\000\004\005D}ppsr\000\035com.sun.msv.grammar.ChoiceExp\000\000\000\000\000\000\000\001\002\000\000xq\000~\000\001\002\037ÅÎppsq\000~\000\n\002\037ÅÃsr\000\021java.lang.BooleanÍ rÕúî\002\000\001Z\000\005valuexp\000psr\000'com.sun.msv.grammar.trex.ElementPattern\000\000\000\000\000\000\000\001\002\000\001L\000\tnameClasst\000\037Lcom/sun/msv/grammar/NameClass;xr\000\036com.sun.msv.grammar.ElementExp\000\000\000\000\000\000\000\001\002\000\002Z\000\032ignoreUndeclaredAttributesL\000\fcontentModelq\000~\000\002xq\000~\000\003\001\017âæq\000~\000\016p\000sq\000~\000\017\001\017âÛpp\000sq\000~\000\n\001\017âÐppsr\000 com.sun.msv.grammar.OneOrMoreExp\000\000\000\000\000\000\000\001\002\000\000xr\000\034com.sun.msv.grammar.UnaryExp\000\000\000\000\000\000\000\001\002\000\001L\000\003expq\000~\000\002xq\000~\000\003\001\017âÅq\000~\000\016psr\000 com.sun.msv.grammar.AttributeExp\000\000\000\000\000\000\000\001\002\000\002L\000\003expq\000~\000\002L\000\tnameClassq\000~\000\020xq\000~\000\003\001\017âÂq\000~\000\016psr\0002com.sun.msv.grammar.Expression$AnyStringExpression\000\000\000\000\000\000\000\001\002\000\000xq\000~\000\003\000\000\000\bsq\000~\000\r\001psr\000 com.sun.msv.grammar.AnyNameClass\000\000\000\000\000\000\000\001\002\000\000xr\000\035com.sun.msv.grammar.NameClass\000\000\000\000\000\000\000\001\002\000\000xpsr\0000com.sun.msv.grammar.Expression$EpsilonExpression\000\000\000\000\000\000\000\001\002\000\000xq\000~\000\003\000\000\000\tq\000~\000\034psr\000#com.sun.msv.grammar.SimpleNameClass\000\000\000\000\000\000\000\001\002\000\002L\000\tlocalNamet\000\022Ljava/lang/String;L\000\fnamespaceURIq\000~\000#xq\000~\000\036t\0007com.eoos.gm.tis2web.lt.io.icl.model.xml.StdFootnoteTypet\000+http://java.sun.com/jaxb/xjc/dummy-elementssq\000~\000\"t\000\013StdFootnotet\000\000sq\000~\000\017\001\017âÛq\000~\000\016p\000sq\000~\000\n\001\017âÐppsq\000~\000\025\001\017âÅq\000~\000\016psq\000~\000\030\001\017âÂq\000~\000\016pq\000~\000\033q\000~\000\037q\000~\000!sq\000~\000\"t\0003com.eoos.gm.tis2web.lt.io.icl.model.xml.StdFootnoteq\000~\000&q\000~\000!sq\000~\000\017\001å~ªpp\000sr\000\033com.sun.msv.grammar.DataExp\000\000\000\000\000\000\000\001\002\000\003L\000\002dtt\000\037Lorg/relaxng/datatype/Datatype;L\000\006exceptq\000~\000\002L\000\004namet\000\035Lcom/sun/msv/util/StringPair;xq\000~\000\003\001å~ppsr\000#com.sun.msv.datatype.xsd.StringType\000\000\000\000\000\000\000\001\002\000\001Z\000\risAlwaysValidxr\000*com.sun.msv.datatype.xsd.BuiltinAtomicType\000\000\000\000\000\000\000\001\002\000\000xr\000%com.sun.msv.datatype.xsd.ConcreteType\000\000\000\000\000\000\000\001\002\000\000xr\000'com.sun.msv.datatype.xsd.XSDatatypeImpl\000\000\000\000\000\000\000\001\002\000\003L\000\fnamespaceUriq\000~\000#L\000\btypeNameq\000~\000#L\000\nwhiteSpacet\000.Lcom/sun/msv/datatype/xsd/WhiteSpaceProcessor;xpt\000 http://www.w3.org/2001/XMLSchemat\000\006stringsr\000.com.sun.msv.datatype.xsd.WhiteSpaceProcessor$1\023JMoIÛ¤G\002\000\000xr\000,com.sun.msv.datatype.xsd.WhiteSpaceProcessor\000\000\000\000\000\000\000\001\002\000\000xp\001sr\0000com.sun.msv.grammar.Expression$NullSetExpression\000\000\000\000\000\000\000\001\002\000\000xq\000~\000\003\000\000\000\nppsr\000\033com.sun.msv.util.StringPairÐt\036jB \002\000\002L\000\tlocalNameq\000~\000#L\000\fnamespaceURIq\000~\000#xpq\000~\000<q\000~\000;sq\000~\000\"t\000\004Textq\000~\000)sq\000~\000\n\001å~µppsq\000~\000\017\001å~ªq\000~\000\016p\000q\000~\0004sq\000~\000\"t\000\007Commentq\000~\000)q\000~\000!sq\000~\000\n\002\037ÅÑppsq\000~\000\025\002\037ÅÆq\000~\000\016psq\000~\000\n\002\037ÅÃq\000~\000\016psq\000~\000\017\001\017âæq\000~\000\016p\000sq\000~\000\017\001\017âÛpp\000sq\000~\000\n\001\017âÐppsq\000~\000\025\001\017âÅq\000~\000\016psq\000~\000\030\001\017âÂq\000~\000\016pq\000~\000\033q\000~\000\037q\000~\000!sq\000~\000\"t\0004com.eoos.gm.tis2web.lt.io.icl.model.xml.FootnoteTypeq\000~\000&sq\000~\000\"t\000\bFootnoteq\000~\000)sq\000~\000\017\001\017âÛq\000~\000\016p\000sq\000~\000\n\001\017âÐppsq\000~\000\025\001\017âÅq\000~\000\016psq\000~\000\030\001\017âÂq\000~\000\016pq\000~\000\033q\000~\000\037q\000~\000!sq\000~\000\"t\0000com.eoos.gm.tis2web.lt.io.icl.model.xml.Footnoteq\000~\000&q\000~\000!sq\000~\000\n\002\037ÅÑppsq\000~\000\025\002\037ÅÆq\000~\000\016psq\000~\000\n\002\037ÅÃq\000~\000\016psq\000~\000\017\001\017âæq\000~\000\016p\000sq\000~\000\017\001\017âÛpp\000sq\000~\000\n\001\017âÐppsq\000~\000\025\001\017âÅq\000~\000\016psq\000~\000\030\001\017âÂq\000~\000\016pq\000~\000\033q\000~\000\037q\000~\000!sq\000~\000\"t\0005com.eoos.gm.tis2web.lt.io.icl.model.xml.ParameterTypeq\000~\000&sq\000~\000\"t\000\tParameterq\000~\000)sq\000~\000\017\001\017âÛq\000~\000\016p\000sq\000~\000\n\001\017âÐppsq\000~\000\025\001\017âÅq\000~\000\016psq\000~\000\030\001\017âÂq\000~\000\016pq\000~\000\033q\000~\000\037q\000~\000!sq\000~\000\"t\0001com.eoos.gm.tis2web.lt.io.icl.model.xml.Parameterq\000~\000&q\000~\000!sq\000~\000\030\003V©Úppq\000~\0004sq\000~\000\"t\000\002nrq\000~\000)sr\000\"com.sun.msv.grammar.ExpressionPool\000\000\000\000\000\000\000\001\002\000\001L\000\bexpTablet\000/Lcom/sun/msv/grammar/ExpressionPool$ClosedHash;xpsr\000-com.sun.msv.grammar.ExpressionPool$ClosedHash×jÐNïèí\034\002\000\004I\000\005countI\000\tthresholdL\000\006parentq\000~\000r[\000\005tablet\000![Lcom/sun/msv/grammar/Expression;xp\000\000\000\032\000\000\0009pur\000![Lcom.sun.msv.grammar.Expression;Ö8DÃ]­§\n\002\000\000xp\000\000\000¿ppppppppppppq\000~\000\006pppppppppppppppppppppppppppppppq\000~\000Fpppppppppppppppppppppppppppppppppppppppppq\000~\000\fq\000~\000Lq\000~\000^q\000~\000Kq\000~\000]ppppppq\000~\000\013q\000~\000\007pq\000~\000Jq\000~\000\\ppppppppq\000~\000\027q\000~\000,q\000~\000Pq\000~\000Xq\000~\000bq\000~\000jq\000~\000\005ppppq\000~\000\024q\000~\000+q\000~\000Oq\000~\000Wq\000~\000aq\000~\000ippppppppq\000~\000\tppppppppppppppppppppppppppppppppppppppppppppppppq\000~\000\bpppppp");
/*     */ 
/*     */   
/*     */   private static final Class PRIMARY_INTERFACE_CLASS() {
/*  25 */     return PositionType.class;
/*     */   }
/*     */   
/*     */   public String getComment() {
/*  29 */     return this._Comment;
/*     */   }
/*     */   
/*     */   public void setComment(String value) {
/*  33 */     this._Comment = value;
/*     */   }
/*     */   
/*     */   public String getNr() {
/*  37 */     return this._Nr;
/*     */   }
/*     */   
/*     */   public void setNr(String value) {
/*  41 */     this._Nr = value;
/*     */   }
/*     */   
/*     */   public List getFootnote() {
/*  45 */     return (List)this._Footnote;
/*     */   }
/*     */   
/*     */   public StdFootnoteType getStdFootnote() {
/*  49 */     return this._StdFootnote;
/*     */   }
/*     */   
/*     */   public void setStdFootnote(StdFootnoteType value) {
/*  53 */     this._StdFootnote = value;
/*     */   }
/*     */   
/*     */   public String getText() {
/*  57 */     return this._Text;
/*     */   }
/*     */   
/*     */   public void setText(String value) {
/*  61 */     this._Text = value;
/*     */   }
/*     */   
/*     */   public List getParameter() {
/*  65 */     return (List)this._Parameter;
/*     */   }
/*     */   
/*     */   public ContentHandlerEx getUnmarshaller(UnmarshallingContext context) {
/*  69 */     return new Unmarshaller(context);
/*     */   }
/*     */   
/*     */   public Class getPrimaryInterfaceClass() {
/*  73 */     return PRIMARY_INTERFACE_CLASS();
/*     */   }
/*     */   
/*     */   public void serializeElements(XMLSerializer context) throws SAXException {
/*  77 */     int idx3 = 0;
/*  78 */     int len3 = this._Footnote.size();
/*  79 */     int idx6 = 0;
/*  80 */     int len6 = this._Parameter.size();
/*  81 */     if (this._StdFootnote != null) {
/*  82 */       if (this._StdFootnote instanceof javax.xml.bind.Element) {
/*  83 */         context.childAsElements((XMLSerializable)this._StdFootnote);
/*     */       } else {
/*  85 */         context.startElement("", "StdFootnote");
/*  86 */         context.childAsAttributes((XMLSerializable)this._StdFootnote);
/*  87 */         context.endAttributes();
/*  88 */         context.childAsElements((XMLSerializable)this._StdFootnote);
/*  89 */         context.endElement();
/*     */       } 
/*     */     }
/*  92 */     context.startElement("", "Text");
/*  93 */     context.endAttributes();
/*     */     try {
/*  95 */       context.text(this._Text);
/*  96 */     } catch (Exception e) {
/*  97 */       Util.handlePrintConversionException(this, e, context);
/*     */     } 
/*  99 */     context.endElement();
/* 100 */     if (this._Comment != null) {
/* 101 */       context.startElement("", "Comment");
/* 102 */       context.endAttributes();
/*     */       try {
/* 104 */         context.text(this._Comment);
/* 105 */       } catch (Exception e) {
/* 106 */         Util.handlePrintConversionException(this, e, context);
/*     */       } 
/* 108 */       context.endElement();
/*     */     } 
/* 110 */     while (idx3 != len3) {
/* 111 */       if (this._Footnote.get(idx3) instanceof javax.xml.bind.Element) {
/* 112 */         context.childAsElements((XMLSerializable)this._Footnote.get(idx3++)); continue;
/*     */       } 
/* 114 */       context.startElement("", "Footnote");
/* 115 */       int idx_3 = idx3;
/* 116 */       context.childAsAttributes((XMLSerializable)this._Footnote.get(idx_3++));
/* 117 */       context.endAttributes();
/* 118 */       context.childAsElements((XMLSerializable)this._Footnote.get(idx3++));
/* 119 */       context.endElement();
/*     */     } 
/*     */     
/* 122 */     while (idx6 != len6) {
/* 123 */       if (this._Parameter.get(idx6) instanceof javax.xml.bind.Element) {
/* 124 */         context.childAsElements((XMLSerializable)this._Parameter.get(idx6++)); continue;
/*     */       } 
/* 126 */       context.startElement("", "Parameter");
/* 127 */       int idx_4 = idx6;
/* 128 */       context.childAsAttributes((XMLSerializable)this._Parameter.get(idx_4++));
/* 129 */       context.endAttributes();
/* 130 */       context.childAsElements((XMLSerializable)this._Parameter.get(idx6++));
/* 131 */       context.endElement();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void serializeAttributes(XMLSerializer context) throws SAXException {
/* 137 */     this._Footnote.size();
/* 138 */     this._Parameter.size();
/* 139 */     context.startAttribute("", "nr");
/*     */     try {
/* 141 */       context.text(this._Nr);
/* 142 */     } catch (Exception e) {
/* 143 */       Util.handlePrintConversionException(this, e, context);
/*     */     } 
/* 145 */     context.endAttribute();
/*     */   }
/*     */   
/*     */   public void serializeAttributeBodies(XMLSerializer context) throws SAXException {
/* 149 */     this._Footnote.size();
/* 150 */     this._Parameter.size();
/*     */   }
/*     */   
/*     */   public Class getPrimaryInterface() {
/* 154 */     return PositionType.class;
/*     */   }
/*     */   
/*     */   public DocumentDeclaration createRawValidator() {
/* 158 */     return (DocumentDeclaration)new REDocumentDeclaration(schemaFragment);
/*     */   }
/*     */   
/*     */   public class Unmarshaller
/*     */     extends ContentHandlerEx {
/*     */     public Unmarshaller(UnmarshallingContext context) {
/* 164 */       super(context, "--------------");
/*     */     }
/*     */     
/*     */     protected UnmarshallableObject owner() {
/* 168 */       return PositionTypeImpl.this;
/*     */     }
/*     */     
/*     */     public void enterElement(String ___uri, String ___local, Attributes __atts) throws UnreportedException {
/* 172 */       switch (this.state) {
/*     */         case 0:
/* 174 */           if ("" == ___uri && "Text" == ___local) {
/* 175 */             this.context.pushAttributes(__atts);
/* 176 */             this.state = 5;
/*     */             return;
/*     */           } 
/* 179 */           if ("" == ___uri && "StdFootnote" == ___local) {
/* 180 */             this.context.pushAttributes(__atts);
/* 181 */             this.state = 3;
/*     */             return;
/*     */           } 
/*     */           break;
/*     */         case 12:
/* 186 */           if ("" == ___uri && "Name" == ___local) {
/* 187 */             PositionTypeImpl.this._Parameter.add(spawnChildFromEnterElement(ParameterTypeImpl.class, 13, ___uri, ___local, __atts));
/*     */             return;
/*     */           } 
/*     */           break;
/*     */         case 3:
/* 192 */           if ("" == ___uri && "Description" == ___local) {
/* 193 */             PositionTypeImpl.this._StdFootnote = (StdFootnoteTypeImpl)spawnChildFromEnterElement(StdFootnoteTypeImpl.class, 4, ___uri, ___local, __atts);
/*     */             return;
/*     */           } 
/* 196 */           if ("" == ___uri && "Bullet" == ___local) {
/* 197 */             PositionTypeImpl.this._StdFootnote = (StdFootnoteTypeImpl)spawnChildFromEnterElement(StdFootnoteTypeImpl.class, 4, ___uri, ___local, __atts);
/*     */             return;
/*     */           } 
/* 200 */           if ("" == ___uri && "Cplus" == ___local) {
/* 201 */             PositionTypeImpl.this._StdFootnote = (StdFootnoteTypeImpl)spawnChildFromEnterElement(StdFootnoteTypeImpl.class, 4, ___uri, ___local, __atts);
/*     */             return;
/*     */           } 
/*     */           break;
/*     */         case 8:
/* 206 */           if ("" == ___uri && "Sign" == ___local) {
/* 207 */             PositionTypeImpl.this._Footnote.add(spawnChildFromEnterElement(FootnoteTypeImpl.class, 9, ___uri, ___local, __atts));
/*     */             return;
/*     */           } 
/*     */           break;
/*     */         case 7:
/* 212 */           if ("" == ___uri && "Parameter" == ___local) {
/* 213 */             this.context.pushAttributes(__atts);
/* 214 */             this.state = 12;
/*     */             return;
/*     */           } 
/* 217 */           if ("" == ___uri && "Comment" == ___local) {
/* 218 */             this.context.pushAttributes(__atts);
/* 219 */             this.state = 10;
/*     */             return;
/*     */           } 
/* 222 */           if ("" == ___uri && "Footnote" == ___local) {
/* 223 */             this.context.pushAttributes(__atts);
/* 224 */             this.state = 8;
/*     */             return;
/*     */           } 
/* 227 */           revertToParentFromEnterElement(___uri, ___local, __atts);
/*     */           return;
/*     */       } 
/* 230 */       super.enterElement(___uri, ___local, __atts);
/*     */     }
/*     */     
/*     */     public void leaveElement(String ___uri, String ___local) throws UnreportedException {
/* 234 */       switch (this.state) {
/*     */         case 11:
/* 236 */           if ("" == ___uri && "Comment" == ___local) {
/* 237 */             this.context.popAttributes();
/* 238 */             this.state = 7;
/*     */             return;
/*     */           } 
/*     */           break;
/*     */         case 4:
/* 243 */           if ("" == ___uri && "StdFootnote" == ___local) {
/* 244 */             this.context.popAttributes();
/* 245 */             goto0();
/*     */             return;
/*     */           } 
/*     */           break;
/*     */         case 13:
/* 250 */           if ("" == ___uri && "Parameter" == ___local) {
/* 251 */             this.context.popAttributes();
/* 252 */             this.state = 7;
/*     */             return;
/*     */           } 
/*     */           break;
/*     */         case 3:
/* 257 */           if ("" == ___uri && "StdFootnote" == ___local) {
/* 258 */             PositionTypeImpl.this._StdFootnote = (StdFootnoteTypeImpl)spawnChildFromLeaveElement(StdFootnoteTypeImpl.class, 4, ___uri, ___local);
/*     */             return;
/*     */           } 
/*     */           break;
/*     */         case 7:
/* 263 */           revertToParentFromLeaveElement(___uri, ___local);
/*     */           return;
/*     */         case 9:
/* 266 */           if ("" == ___uri && "Footnote" == ___local) {
/* 267 */             this.context.popAttributes();
/* 268 */             this.state = 7;
/*     */             return;
/*     */           } 
/*     */           break;
/*     */         case 6:
/* 273 */           if ("" == ___uri && "Text" == ___local) {
/* 274 */             this.context.popAttributes();
/* 275 */             this.state = 7;
/*     */             return;
/*     */           } 
/*     */           break;
/*     */       } 
/* 280 */       super.leaveElement(___uri, ___local);
/*     */     }
/*     */     
/*     */     public void enterAttribute(String ___uri, String ___local) throws UnreportedException {
/* 284 */       switch (this.state) {
/*     */         case 0:
/* 286 */           if ("" == ___uri && "nr" == ___local) {
/* 287 */             this.state = 1;
/*     */             return;
/*     */           } 
/*     */           break;
/*     */         case 7:
/* 292 */           revertToParentFromEnterAttribute(___uri, ___local);
/*     */           return;
/*     */       } 
/* 295 */       super.enterAttribute(___uri, ___local);
/*     */     }
/*     */     
/*     */     public void leaveAttribute(String ___uri, String ___local) throws UnreportedException {
/* 299 */       switch (this.state) {
/*     */         case 2:
/* 301 */           if ("" == ___uri && "nr" == ___local) {
/* 302 */             goto0();
/*     */             return;
/*     */           } 
/*     */           break;
/*     */         case 7:
/* 307 */           revertToParentFromLeaveAttribute(___uri, ___local);
/*     */           return;
/*     */       } 
/* 310 */       super.leaveAttribute(___uri, ___local);
/*     */     }
/*     */     
/*     */     public void text(String value) throws UnreportedException {
/*     */       try {
/* 315 */         switch (this.state) {
/*     */           case 5:
/*     */             try {
/* 318 */               PositionTypeImpl.this._Text = value;
/* 319 */             } catch (Exception e) {
/* 320 */               handleParseConversionException(e);
/*     */             } 
/* 322 */             this.state = 6;
/*     */             return;
/*     */           case 10:
/*     */             try {
/* 326 */               PositionTypeImpl.this._Comment = value;
/* 327 */             } catch (Exception e) {
/* 328 */               handleParseConversionException(e);
/*     */             } 
/* 330 */             this.state = 11;
/*     */             return;
/*     */           case 1:
/*     */             try {
/* 334 */               PositionTypeImpl.this._Nr = value;
/* 335 */             } catch (Exception e) {
/* 336 */               handleParseConversionException(e);
/*     */             } 
/* 338 */             this.state = 2;
/*     */             return;
/*     */           case 7:
/* 341 */             revertToParentFromText(value);
/*     */             return;
/*     */         } 
/* 344 */       } catch (RuntimeException e) {
/* 345 */         handleUnexpectedTextException(value, e);
/*     */       } 
/*     */     }
/*     */     
/*     */     public void leaveChild(int nextState) throws UnreportedException {
/* 350 */       switch (nextState) {
/*     */         case 4:
/* 352 */           this.state = 4;
/*     */           return;
/*     */         case 13:
/* 355 */           this.state = 13;
/*     */           return;
/*     */         case 9:
/* 358 */           this.state = 9;
/*     */           return;
/*     */       } 
/* 361 */       super.leaveChild(nextState);
/*     */     }
/*     */ 
/*     */     
/*     */     private void goto0() throws UnreportedException {
/* 366 */       this.state = 0;
/* 367 */       int idx = this.context.getAttribute("", "nr");
/* 368 */       if (idx >= 0) {
/* 369 */         this.context.consumeAttribute(idx);
/*     */         return;
/*     */       } 
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\lt\implementation\io\icl\model\xml\impl\PositionTypeImpl.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */