/*     */ package com.eoos.gm.tis2web.lt.implementation.io.icl.model.xml.impl;
/*     */ import com.eoos.gm.tis2web.lt.implementation.io.icl.model.xml.PositionsType;
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
/*     */ public class PositionsTypeImpl implements PositionsType, UnmarshallableObject, XMLSerializable, ValidatableObject {
/*  16 */   protected ListImpl _Position = new ListImpl(new ArrayList()); protected String _State;
/*     */   protected String _Title;
/*  18 */   private static final Grammar schemaFragment = SchemaDeserializer.deserialize("¬í\000\005sr\000\037com.sun.msv.grammar.SequenceExp\000\000\000\000\000\000\000\001\002\000\000xr\000\035com.sun.msv.grammar.BinaryExp\000\000\000\000\000\000\000\001\002\000\002L\000\004exp1t\000 Lcom/sun/msv/grammar/Expression;L\000\004exp2q\000~\000\002xr\000\036com.sun.msv.grammar.Expressionø\030èN5~O\002\000\003I\000\016cachedHashCodeL\000\023epsilonReducibilityt\000\023Ljava/lang/Boolean;L\000\013expandedExpq\000~\000\002xp\005êÃ$ppsq\000~\000\000\003ÊýYppsr\000'com.sun.msv.grammar.trex.ElementPattern\000\000\000\000\000\000\000\001\002\000\001L\000\tnameClasst\000\037Lcom/sun/msv/grammar/NameClass;xr\000\036com.sun.msv.grammar.ElementExp\000\000\000\000\000\000\000\001\002\000\002Z\000\032ignoreUndeclaredAttributesL\000\fcontentModelq\000~\000\002xq\000~\000\003\001å~ªpp\000sr\000\033com.sun.msv.grammar.DataExp\000\000\000\000\000\000\000\001\002\000\003L\000\002dtt\000\037Lorg/relaxng/datatype/Datatype;L\000\006exceptq\000~\000\002L\000\004namet\000\035Lcom/sun/msv/util/StringPair;xq\000~\000\003\001å~ppsr\000#com.sun.msv.datatype.xsd.StringType\000\000\000\000\000\000\000\001\002\000\001Z\000\risAlwaysValidxr\000*com.sun.msv.datatype.xsd.BuiltinAtomicType\000\000\000\000\000\000\000\001\002\000\000xr\000%com.sun.msv.datatype.xsd.ConcreteType\000\000\000\000\000\000\000\001\002\000\000xr\000'com.sun.msv.datatype.xsd.XSDatatypeImpl\000\000\000\000\000\000\000\001\002\000\003L\000\fnamespaceUrit\000\022Ljava/lang/String;L\000\btypeNameq\000~\000\023L\000\nwhiteSpacet\000.Lcom/sun/msv/datatype/xsd/WhiteSpaceProcessor;xpt\000 http://www.w3.org/2001/XMLSchemat\000\006stringsr\000.com.sun.msv.datatype.xsd.WhiteSpaceProcessor$1\023JMoIÛ¤G\002\000\000xr\000,com.sun.msv.datatype.xsd.WhiteSpaceProcessor\000\000\000\000\000\000\000\001\002\000\000xp\001sr\0000com.sun.msv.grammar.Expression$NullSetExpression\000\000\000\000\000\000\000\001\002\000\000xq\000~\000\003\000\000\000\nppsr\000\033com.sun.msv.util.StringPairÐt\036jB \002\000\002L\000\tlocalNameq\000~\000\023L\000\fnamespaceURIq\000~\000\023xpq\000~\000\027q\000~\000\026sr\000#com.sun.msv.grammar.SimpleNameClass\000\000\000\000\000\000\000\001\002\000\002L\000\tlocalNameq\000~\000\023L\000\fnamespaceURIq\000~\000\023xr\000\035com.sun.msv.grammar.NameClass\000\000\000\000\000\000\000\001\002\000\000xpt\000\005Titlet\000\000sq\000~\000\007\001å~ªpp\000q\000~\000\016sq\000~\000\037t\000\005Stateq\000~\000#sr\000 com.sun.msv.grammar.OneOrMoreExp\000\000\000\000\000\000\000\001\002\000\000xr\000\034com.sun.msv.grammar.UnaryExp\000\000\000\000\000\000\000\001\002\000\001L\000\003expq\000~\000\002xq\000~\000\003\002\037ÅÆppsr\000\035com.sun.msv.grammar.ChoiceExp\000\000\000\000\000\000\000\001\002\000\000xq\000~\000\001\002\037ÅÃppsq\000~\000\007\001\017âæpp\000sq\000~\000\007\001\017âÛpp\000sq\000~\000*\001\017âÐppsq\000~\000'\001\017âÅsr\000\021java.lang.BooleanÍ rÕúî\002\000\001Z\000\005valuexp\000psr\000 com.sun.msv.grammar.AttributeExp\000\000\000\000\000\000\000\001\002\000\002L\000\003expq\000~\000\002L\000\tnameClassq\000~\000\bxq\000~\000\003\001\017âÂq\000~\0001psr\0002com.sun.msv.grammar.Expression$AnyStringExpression\000\000\000\000\000\000\000\001\002\000\000xq\000~\000\003\000\000\000\bsq\000~\0000\001psr\000 com.sun.msv.grammar.AnyNameClass\000\000\000\000\000\000\000\001\002\000\000xq\000~\000 sr\0000com.sun.msv.grammar.Expression$EpsilonExpression\000\000\000\000\000\000\000\001\002\000\000xq\000~\000\003\000\000\000\tq\000~\0006psq\000~\000\037t\0004com.eoos.gm.tis2web.lt.io.icl.model.xml.PositionTypet\000+http://java.sun.com/jaxb/xjc/dummy-elementssq\000~\000\037t\000\bPositionq\000~\000#sq\000~\000\007\001\017âÛpp\000sq\000~\000*\001\017âÐppsq\000~\000'\001\017âÅq\000~\0001psq\000~\0002\001\017âÂq\000~\0001pq\000~\0005q\000~\0008q\000~\000:sq\000~\000\037t\0000com.eoos.gm.tis2web.lt.io.icl.model.xml.Positionq\000~\000=sr\000\"com.sun.msv.grammar.ExpressionPool\000\000\000\000\000\000\000\001\002\000\001L\000\bexpTablet\000/Lcom/sun/msv/grammar/ExpressionPool$ClosedHash;xpsr\000-com.sun.msv.grammar.ExpressionPool$ClosedHash×jÐNïèí\034\002\000\004I\000\005countI\000\tthresholdL\000\006parentq\000~\000G[\000\005tablet\000![Lcom/sun/msv/grammar/Expression;xp\000\000\000\b\000\000\0009pur\000![Lcom.sun.msv.grammar.Expression;Ö8DÃ]­§\n\002\000\000xp\000\000\000¿pppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppq\000~\000\006ppppppppppppppq\000~\000+ppq\000~\000)ppppppppppppppppppppq\000~\000/q\000~\000Bpppppppppq\000~\000.q\000~\000Appppppppppppppppppppppppppppppppppppppppppq\000~\000\005ppppppppppppppppppppppppp");
/*     */ 
/*     */   
/*     */   private static final Class PRIMARY_INTERFACE_CLASS() {
/*  22 */     return PositionsType.class;
/*     */   }
/*     */   
/*     */   public String getState() {
/*  26 */     return this._State;
/*     */   }
/*     */   
/*     */   public void setState(String value) {
/*  30 */     this._State = value;
/*     */   }
/*     */   
/*     */   public List getPosition() {
/*  34 */     return (List)this._Position;
/*     */   }
/*     */   
/*     */   public String getTitle() {
/*  38 */     return this._Title;
/*     */   }
/*     */   
/*     */   public void setTitle(String value) {
/*  42 */     this._Title = value;
/*     */   }
/*     */   
/*     */   public ContentHandlerEx getUnmarshaller(UnmarshallingContext context) {
/*  46 */     return new Unmarshaller(context);
/*     */   }
/*     */   
/*     */   public Class getPrimaryInterfaceClass() {
/*  50 */     return PRIMARY_INTERFACE_CLASS();
/*     */   }
/*     */   
/*     */   public void serializeElements(XMLSerializer context) throws SAXException {
/*  54 */     int idx2 = 0;
/*  55 */     int len2 = this._Position.size();
/*  56 */     context.startElement("", "Title");
/*  57 */     context.endAttributes();
/*     */     try {
/*  59 */       context.text(this._Title);
/*  60 */     } catch (Exception e) {
/*  61 */       Util.handlePrintConversionException(this, e, context);
/*     */     } 
/*  63 */     context.endElement();
/*  64 */     context.startElement("", "State");
/*  65 */     context.endAttributes();
/*     */     try {
/*  67 */       context.text(this._State);
/*  68 */     } catch (Exception e) {
/*  69 */       Util.handlePrintConversionException(this, e, context);
/*     */     } 
/*  71 */     context.endElement();
/*  72 */     while (idx2 != len2) {
/*  73 */       if (this._Position.get(idx2) instanceof javax.xml.bind.Element) {
/*  74 */         context.childAsElements((XMLSerializable)this._Position.get(idx2++)); continue;
/*     */       } 
/*  76 */       context.startElement("", "Position");
/*  77 */       int idx_2 = idx2;
/*  78 */       context.childAsAttributes((XMLSerializable)this._Position.get(idx_2++));
/*  79 */       context.endAttributes();
/*  80 */       context.childAsElements((XMLSerializable)this._Position.get(idx2++));
/*  81 */       context.endElement();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void serializeAttributes(XMLSerializer context) throws SAXException {
/*  87 */     this._Position.size();
/*     */   }
/*     */   
/*     */   public void serializeAttributeBodies(XMLSerializer context) throws SAXException {
/*  91 */     this._Position.size();
/*     */   }
/*     */   
/*     */   public Class getPrimaryInterface() {
/*  95 */     return PositionsType.class;
/*     */   }
/*     */   
/*     */   public DocumentDeclaration createRawValidator() {
/*  99 */     return (DocumentDeclaration)new REDocumentDeclaration(schemaFragment);
/*     */   }
/*     */   
/*     */   public class Unmarshaller
/*     */     extends ContentHandlerEx {
/*     */     public Unmarshaller(UnmarshallingContext context) {
/* 105 */       super(context, "----------");
/*     */     }
/*     */     
/*     */     protected UnmarshallableObject owner() {
/* 109 */       return PositionsTypeImpl.this;
/*     */     }
/*     */     
/*     */     public void enterElement(String ___uri, String ___local, Attributes __atts) throws UnreportedException {
/* 113 */       switch (this.state) {
/*     */         case 6:
/* 115 */           if ("" == ___uri && "Position" == ___local) {
/* 116 */             this.context.pushAttributes(__atts);
/* 117 */             goto7();
/*     */             return;
/*     */           } 
/*     */           break;
/*     */         case 7:
/* 122 */           if ("" == ___uri && "Text" == ___local) {
/* 123 */             PositionsTypeImpl.this._Position.add(spawnChildFromEnterElement(PositionTypeImpl.class, 8, ___uri, ___local, __atts));
/*     */             return;
/*     */           } 
/* 126 */           if ("" == ___uri && "StdFootnote" == ___local) {
/* 127 */             PositionsTypeImpl.this._Position.add(spawnChildFromEnterElement(PositionTypeImpl.class, 8, ___uri, ___local, __atts));
/*     */             return;
/*     */           } 
/*     */           break;
/*     */         case 9:
/* 132 */           if ("" == ___uri && "Position" == ___local) {
/* 133 */             this.context.pushAttributes(__atts);
/* 134 */             goto7();
/*     */             return;
/*     */           } 
/* 137 */           revertToParentFromEnterElement(___uri, ___local, __atts);
/*     */           return;
/*     */         case 3:
/* 140 */           if ("" == ___uri && "State" == ___local) {
/* 141 */             this.context.pushAttributes(__atts);
/* 142 */             this.state = 4;
/*     */             return;
/*     */           } 
/*     */           break;
/*     */         case 0:
/* 147 */           if ("" == ___uri && "Title" == ___local) {
/* 148 */             this.context.pushAttributes(__atts);
/* 149 */             this.state = 1;
/*     */             return;
/*     */           } 
/*     */           break;
/*     */       } 
/* 154 */       super.enterElement(___uri, ___local, __atts);
/*     */     }
/*     */     
/*     */     public void leaveElement(String ___uri, String ___local) throws UnreportedException {
/* 158 */       switch (this.state) {
/*     */         case 8:
/* 160 */           if ("" == ___uri && "Position" == ___local) {
/* 161 */             this.context.popAttributes();
/* 162 */             this.state = 9;
/*     */             return;
/*     */           } 
/*     */           break;
/*     */         case 9:
/* 167 */           revertToParentFromLeaveElement(___uri, ___local);
/*     */           return;
/*     */         case 5:
/* 170 */           if ("" == ___uri && "State" == ___local) {
/* 171 */             this.context.popAttributes();
/* 172 */             this.state = 6;
/*     */             return;
/*     */           } 
/*     */           break;
/*     */         case 2:
/* 177 */           if ("" == ___uri && "Title" == ___local) {
/* 178 */             this.context.popAttributes();
/* 179 */             this.state = 3;
/*     */             return;
/*     */           } 
/*     */           break;
/*     */       } 
/* 184 */       super.leaveElement(___uri, ___local);
/*     */     }
/*     */     
/*     */     public void enterAttribute(String ___uri, String ___local) throws UnreportedException {
/* 188 */       switch (this.state) {
/*     */         case 7:
/* 190 */           if ("" == ___uri && "nr" == ___local) {
/* 191 */             PositionsTypeImpl.this._Position.add(spawnChildFromEnterAttribute(PositionTypeImpl.class, 8, ___uri, ___local));
/*     */             return;
/*     */           } 
/*     */           break;
/*     */         case 9:
/* 196 */           revertToParentFromEnterAttribute(___uri, ___local);
/*     */           return;
/*     */       } 
/* 199 */       super.enterAttribute(___uri, ___local);
/*     */     }
/*     */     
/*     */     public void leaveAttribute(String ___uri, String ___local) throws UnreportedException {
/* 203 */       switch (this.state) {
/*     */         case 9:
/* 205 */           revertToParentFromLeaveAttribute(___uri, ___local);
/*     */           return;
/*     */       } 
/* 208 */       super.leaveAttribute(___uri, ___local);
/*     */     }
/*     */     
/*     */     public void text(String value) throws UnreportedException {
/*     */       try {
/* 213 */         switch (this.state) {
/*     */           case 1:
/*     */             try {
/* 216 */               PositionsTypeImpl.this._Title = value;
/* 217 */             } catch (Exception e) {
/* 218 */               handleParseConversionException(e);
/*     */             } 
/* 220 */             this.state = 2;
/*     */             return;
/*     */           case 9:
/* 223 */             revertToParentFromText(value);
/*     */             return;
/*     */           case 4:
/*     */             try {
/* 227 */               PositionsTypeImpl.this._State = value;
/* 228 */             } catch (Exception e) {
/* 229 */               handleParseConversionException(e);
/*     */             } 
/* 231 */             this.state = 5;
/*     */             return;
/*     */         } 
/* 234 */       } catch (RuntimeException e) {
/* 235 */         handleUnexpectedTextException(value, e);
/*     */       } 
/*     */     }
/*     */     
/*     */     public void leaveChild(int nextState) throws UnreportedException {
/* 240 */       switch (nextState) {
/*     */         case 8:
/* 242 */           this.state = 8;
/*     */           return;
/*     */       } 
/* 245 */       super.leaveChild(nextState);
/*     */     }
/*     */ 
/*     */     
/*     */     private void goto7() throws UnreportedException {
/* 250 */       this.state = 7;
/* 251 */       int idx = this.context.getAttribute("", "nr");
/* 252 */       if (idx >= 0) {
/* 253 */         this.context.consumeAttribute(idx);
/*     */         return;
/*     */       } 
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\lt\implementation\io\icl\model\xml\impl\PositionsTypeImpl.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */