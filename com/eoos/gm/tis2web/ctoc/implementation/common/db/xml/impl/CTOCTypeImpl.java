/*     */ package com.eoos.gm.tis2web.ctoc.implementation.common.db.xml.impl;
/*     */ 
/*     */ import com.eoos.gm.tis2web.ctoc.implementation.common.db.xml.CTOCType;
/*     */ import com.eoos.gm.tis2web.ctoc.implementation.common.db.xml.NodeType;
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
/*     */ public class CTOCTypeImpl implements CTOCType, UnmarshallableObject, XMLSerializable, ValidatableObject {
/*  21 */   private static final Grammar schemaFragment = SchemaDeserializer.deserialize("¬í\000\005sr\000\037com.sun.msv.grammar.SequenceExp\000\000\000\000\000\000\000\001\002\000\000xr\000\035com.sun.msv.grammar.BinaryExp\000\000\000\000\000\000\000\001\002\000\002L\000\004exp1t\000 Lcom/sun/msv/grammar/Expression;L\000\004exp2q\000~\000\002xr\000\036com.sun.msv.grammar.Expressionø\030èN5~O\002\000\003I\000\016cachedHashCodeL\000\023epsilonReducibilityt\000\023Ljava/lang/Boolean;L\000\013expandedExpq\000~\000\002xp\006ï\003\nppsq\000~\000\000\004©dUppsr\000'com.sun.msv.grammar.trex.ElementPattern\000\000\000\000\000\000\000\001\002\000\001L\000\tnameClasst\000\037Lcom/sun/msv/grammar/NameClass;xr\000\036com.sun.msv.grammar.ElementExp\000\000\000\000\000\000\000\001\002\000\002Z\000\032ignoreUndeclaredAttributesL\000\fcontentModelq\000~\000\002xq\000~\000\003\001ùpp\000sq\000~\000\007\001ùpp\000sr\000\035com.sun.msv.grammar.ChoiceExp\000\000\000\000\000\000\000\001\002\000\000xq\000~\000\001\001ùppsr\000 com.sun.msv.grammar.OneOrMoreExp\000\000\000\000\000\000\000\001\002\000\000xr\000\034com.sun.msv.grammar.UnaryExp\000\000\000\000\000\000\000\001\002\000\001L\000\003expq\000~\000\002xq\000~\000\003\001ùxsr\000\021java.lang.BooleanÍ rÕúî\002\000\001Z\000\005valuexp\000psr\000 com.sun.msv.grammar.AttributeExp\000\000\000\000\000\000\000\001\002\000\002L\000\003expq\000~\000\002L\000\tnameClassq\000~\000\bxq\000~\000\003\001ùuq\000~\000\022psr\0002com.sun.msv.grammar.Expression$AnyStringExpression\000\000\000\000\000\000\000\001\002\000\000xq\000~\000\003\000\000\000\bsq\000~\000\021\001psr\000 com.sun.msv.grammar.AnyNameClass\000\000\000\000\000\000\000\001\002\000\000xr\000\035com.sun.msv.grammar.NameClass\000\000\000\000\000\000\000\001\002\000\000xpsr\0000com.sun.msv.grammar.Expression$EpsilonExpression\000\000\000\000\000\000\000\001\002\000\000xq\000~\000\003\000\000\000\tq\000~\000\027psr\000#com.sun.msv.grammar.SimpleNameClass\000\000\000\000\000\000\000\001\002\000\002L\000\tlocalNamet\000\022Ljava/lang/String;L\000\fnamespaceURIq\000~\000\036xq\000~\000\031t\000/com.eoos.gm.tis2web.ctoc.common.db.xml.NodeTypet\000+http://java.sun.com/jaxb/xjc/dummy-elementssq\000~\000\035t\000\004Nodet\000\000sq\000~\000\023\003(j·ppsr\000\033com.sun.msv.grammar.DataExp\000\000\000\000\000\000\000\001\002\000\003L\000\002dtt\000\037Lorg/relaxng/datatype/Datatype;L\000\006exceptq\000~\000\002L\000\004namet\000\035Lcom/sun/msv/util/StringPair;xq\000~\000\003\001»ÒOppsr\000#com.sun.msv.datatype.xsd.StringType\000\000\000\000\000\000\000\001\002\000\001Z\000\risAlwaysValidxr\000*com.sun.msv.datatype.xsd.BuiltinAtomicType\000\000\000\000\000\000\000\001\002\000\000xr\000%com.sun.msv.datatype.xsd.ConcreteType\000\000\000\000\000\000\000\001\002\000\000xr\000'com.sun.msv.datatype.xsd.XSDatatypeImpl\000\000\000\000\000\000\000\001\002\000\003L\000\fnamespaceUriq\000~\000\036L\000\btypeNameq\000~\000\036L\000\nwhiteSpacet\000.Lcom/sun/msv/datatype/xsd/WhiteSpaceProcessor;xpt\000 http://www.w3.org/2001/XMLSchemat\000\006stringsr\000.com.sun.msv.datatype.xsd.WhiteSpaceProcessor$1\023JMoIÛ¤G\002\000\000xr\000,com.sun.msv.datatype.xsd.WhiteSpaceProcessor\000\000\000\000\000\000\000\001\002\000\000xp\001sr\0000com.sun.msv.grammar.Expression$NullSetExpression\000\000\000\000\000\000\000\001\002\000\000xq\000~\000\003\000\000\000\nppsr\000\033com.sun.msv.util.StringPairÐt\036jB \002\000\002L\000\tlocalNameq\000~\000\036L\000\fnamespaceURIq\000~\000\036xpq\000~\0001q\000~\0000sq\000~\000\035t\000\013Applicationq\000~\000$sq\000~\000\f\002E°ppsq\000~\000\023\002E¥q\000~\000\022psr\000\034com.sun.msv.grammar.ValueExp\000\000\000\000\000\000\000\001\002\000\003L\000\002dtq\000~\000'L\000\004nameq\000~\000(L\000\005valuet\000\022Ljava/lang/Object;xq\000~\000\003\001»ÒFppq\000~\000/q\000~\0008t\000\0030.0sq\000~\000\035t\000\007Versionq\000~\000$q\000~\000\034sr\000\"com.sun.msv.grammar.ExpressionPool\000\000\000\000\000\000\000\001\002\000\001L\000\bexpTablet\000/Lcom/sun/msv/grammar/ExpressionPool$ClosedHash;xpsr\000-com.sun.msv.grammar.ExpressionPool$ClosedHash×jÐNïèí\034\002\000\004I\000\005countI\000\tthresholdL\000\006parentq\000~\000D[\000\005tablet\000![Lcom/sun/msv/grammar/Expression;xp\000\000\000\005\000\000\0009pur\000![Lcom.sun.msv.grammar.Expression;Ö8DÃ]­§\n\002\000\000xp\000\000\000¿ppppppppppppppppppppppppppq\000~\000\006pppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppq\000~\000\020ppppppppq\000~\000;pq\000~\000\rppppppppppppppppppppppppppppq\000~\000\005pppppppppppppppppppppppppppppppppp"); protected NodeType _Node; protected String _Version;
/*     */   protected String _Application;
/*     */   
/*     */   private static final Class PRIMARY_INTERFACE_CLASS() {
/*  25 */     return CTOCType.class;
/*     */   }
/*     */   
/*     */   public NodeType getNode() {
/*  29 */     return this._Node;
/*     */   }
/*     */   
/*     */   public void setNode(NodeType value) {
/*  33 */     this._Node = value;
/*     */   }
/*     */   
/*     */   public String getVersion() {
/*  37 */     return this._Version;
/*     */   }
/*     */   
/*     */   public void setVersion(String value) {
/*  41 */     this._Version = value;
/*     */   }
/*     */   
/*     */   public String getApplication() {
/*  45 */     return this._Application;
/*     */   }
/*     */   
/*     */   public void setApplication(String value) {
/*  49 */     this._Application = value;
/*     */   }
/*     */   
/*     */   public ContentHandlerEx getUnmarshaller(UnmarshallingContext context) {
/*  53 */     return new Unmarshaller(context);
/*     */   }
/*     */   
/*     */   public Class getPrimaryInterfaceClass() {
/*  57 */     return PRIMARY_INTERFACE_CLASS();
/*     */   }
/*     */   
/*     */   public void serializeElements(XMLSerializer context) throws SAXException {
/*  61 */     if (this._Node instanceof javax.xml.bind.Element) {
/*  62 */       context.childAsElements((XMLSerializable)this._Node);
/*     */     } else {
/*  64 */       context.startElement("", "Node");
/*  65 */       context.childAsAttributes((XMLSerializable)this._Node);
/*  66 */       context.endAttributes();
/*  67 */       context.childAsElements((XMLSerializable)this._Node);
/*  68 */       context.endElement();
/*     */     } 
/*     */   }
/*     */   
/*     */   public void serializeAttributes(XMLSerializer context) throws SAXException {
/*  73 */     context.startAttribute("", "Application");
/*     */     try {
/*  75 */       context.text(this._Application);
/*  76 */     } catch (Exception e) {
/*  77 */       Util.handlePrintConversionException(this, e, context);
/*     */     } 
/*  79 */     context.endAttribute();
/*  80 */     if (this._Version != null) {
/*  81 */       context.startAttribute("", "Version");
/*     */       try {
/*  83 */         context.text(this._Version);
/*  84 */       } catch (Exception e) {
/*  85 */         Util.handlePrintConversionException(this, e, context);
/*     */       } 
/*  87 */       context.endAttribute();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void serializeAttributeBodies(XMLSerializer context) throws SAXException {}
/*     */   
/*     */   public Class getPrimaryInterface() {
/*  95 */     return CTOCType.class;
/*     */   }
/*     */   
/*     */   public DocumentDeclaration createRawValidator() {
/*  99 */     return (DocumentDeclaration)new REDocumentDeclaration(schemaFragment);
/*     */   }
/*     */   
/*     */   public class Unmarshaller
/*     */     extends ContentHandlerEx {
/*     */     public Unmarshaller(UnmarshallingContext context) {
/* 105 */       super(context, "--------");
/*     */     }
/*     */     
/*     */     protected UnmarshallableObject owner() {
/* 109 */       return CTOCTypeImpl.this;
/*     */     }
/*     */     
/*     */     public void enterElement(String ___uri, String ___local, Attributes __atts) throws UnreportedException {
/* 113 */       switch (this.state) {
/*     */         case 1:
/* 115 */           if ("" == ___uri && "Label" == ___local) {
/* 116 */             CTOCTypeImpl.this._Node = (NodeTypeImpl)spawnChildFromEnterElement(NodeTypeImpl.class, 2, ___uri, ___local, __atts);
/*     */             return;
/*     */           } 
/*     */           break;
/*     */         case 0:
/* 121 */           if ("" == ___uri && "Node" == ___local) {
/* 122 */             this.context.pushAttributes(__atts);
/* 123 */             goto1();
/*     */             return;
/*     */           } 
/*     */           break;
/*     */         case 3:
/* 128 */           revertToParentFromEnterElement(___uri, ___local, __atts);
/*     */           return;
/*     */       } 
/* 131 */       super.enterElement(___uri, ___local, __atts);
/*     */     }
/*     */     
/*     */     public void leaveElement(String ___uri, String ___local) throws UnreportedException {
/* 135 */       switch (this.state) {
/*     */         case 2:
/* 137 */           if ("" == ___uri && "Node" == ___local) {
/* 138 */             this.context.popAttributes();
/* 139 */             this.state = 3;
/*     */             return;
/*     */           } 
/*     */           break;
/*     */         case 3:
/* 144 */           revertToParentFromLeaveElement(___uri, ___local);
/*     */           return;
/*     */       } 
/* 147 */       super.leaveElement(___uri, ___local);
/*     */     }
/*     */     
/*     */     public void enterAttribute(String ___uri, String ___local) throws UnreportedException {
/* 151 */       switch (this.state) {
/*     */         case 1:
/* 153 */           if ("" == ___uri && "Version" == ___local) {
/* 154 */             CTOCTypeImpl.this._Node = (NodeTypeImpl)spawnChildFromEnterAttribute(NodeTypeImpl.class, 2, ___uri, ___local);
/*     */             return;
/*     */           } 
/* 157 */           if ("" == ___uri && "NodeID" == ___local) {
/* 158 */             CTOCTypeImpl.this._Node = (NodeTypeImpl)spawnChildFromEnterAttribute(NodeTypeImpl.class, 2, ___uri, ___local);
/*     */             return;
/*     */           } 
/*     */           break;
/*     */         case 0:
/* 163 */           if ("" == ___uri && "Application" == ___local) {
/* 164 */             this.state = 4;
/*     */             return;
/*     */           } 
/* 167 */           if ("" == ___uri && "Version" == ___local) {
/* 168 */             this.state = 6;
/*     */             return;
/*     */           } 
/*     */           break;
/*     */         case 3:
/* 173 */           revertToParentFromEnterAttribute(___uri, ___local);
/*     */           return;
/*     */       } 
/* 176 */       super.enterAttribute(___uri, ___local);
/*     */     }
/*     */     
/*     */     public void leaveAttribute(String ___uri, String ___local) throws UnreportedException {
/* 180 */       switch (this.state) {
/*     */         case 7:
/* 182 */           if ("" == ___uri && "Version" == ___local) {
/* 183 */             goto0();
/*     */             return;
/*     */           } 
/*     */           break;
/*     */         case 3:
/* 188 */           revertToParentFromLeaveAttribute(___uri, ___local);
/*     */           return;
/*     */         case 5:
/* 191 */           if ("" == ___uri && "Application" == ___local) {
/* 192 */             goto0();
/*     */             return;
/*     */           } 
/*     */           break;
/*     */       } 
/* 197 */       super.leaveAttribute(___uri, ___local);
/*     */     }
/*     */     
/*     */     public void text(String value) throws UnreportedException {
/*     */       try {
/* 202 */         switch (this.state) {
/*     */           case 6:
/*     */             try {
/* 205 */               CTOCTypeImpl.this._Version = value;
/* 206 */             } catch (Exception e) {
/* 207 */               handleParseConversionException(e);
/*     */             } 
/* 209 */             this.state = 7;
/*     */             return;
/*     */           case 4:
/*     */             try {
/* 213 */               CTOCTypeImpl.this._Application = value;
/* 214 */             } catch (Exception e) {
/* 215 */               handleParseConversionException(e);
/*     */             } 
/* 217 */             this.state = 5;
/*     */             return;
/*     */           case 3:
/* 220 */             revertToParentFromText(value);
/*     */             return;
/*     */         } 
/* 223 */       } catch (RuntimeException e) {
/* 224 */         handleUnexpectedTextException(value, e);
/*     */       } 
/*     */     }
/*     */     
/*     */     public void leaveChild(int nextState) throws UnreportedException {
/* 229 */       switch (nextState) {
/*     */         case 2:
/* 231 */           this.state = 2;
/*     */           return;
/*     */       } 
/* 234 */       super.leaveChild(nextState);
/*     */     }
/*     */ 
/*     */     
/*     */     private void goto1() throws UnreportedException {
/* 239 */       this.state = 1;
/* 240 */       int idx = this.context.getAttribute("", "NodeID");
/* 241 */       if (idx >= 0) {
/* 242 */         this.context.consumeAttribute(idx);
/*     */         return;
/*     */       } 
/* 245 */       idx = this.context.getAttribute("", "Version");
/* 246 */       if (idx >= 0) {
/* 247 */         this.context.consumeAttribute(idx);
/*     */         return;
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     private void goto0() throws UnreportedException {
/* 254 */       this.state = 0;
/* 255 */       int idx = this.context.getAttribute("", "Application");
/* 256 */       if (idx >= 0) {
/* 257 */         this.context.consumeAttribute(idx);
/*     */         return;
/*     */       } 
/* 260 */       idx = this.context.getAttribute("", "Version");
/* 261 */       if (idx >= 0) {
/* 262 */         this.context.consumeAttribute(idx);
/*     */         return;
/*     */       } 
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\ctoc\implementation\common\db\xml\impl\CTOCTypeImpl.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */