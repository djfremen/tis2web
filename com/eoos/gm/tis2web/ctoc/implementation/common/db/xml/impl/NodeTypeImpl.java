/*     */ package com.eoos.gm.tis2web.ctoc.implementation.common.db.xml.impl;
/*     */ import com.eoos.gm.tis2web.ctoc.implementation.common.db.xml.NodeType;
/*     */ import com.sun.msv.verifier.DocumentDeclaration;
/*     */ import com.sun.xml.bind.marshaller.Util;
/*     */ import com.sun.xml.bind.serializer.XMLSerializable;
/*     */ import com.sun.xml.bind.serializer.XMLSerializer;
/*     */ import com.sun.xml.bind.unmarshaller.ContentHandlerEx;
/*     */ import com.sun.xml.bind.unmarshaller.UnmarshallingContext;
/*     */ import com.sun.xml.bind.unmarshaller.UnreportedException;
/*     */ import com.sun.xml.bind.util.ListImpl;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import org.xml.sax.Attributes;
/*     */ import org.xml.sax.SAXException;
/*     */ 
/*     */ public class NodeTypeImpl implements NodeType, UnmarshallableObject, XMLSerializable, ValidatableObject {
/*  17 */   protected ListImpl _Node = new ListImpl(new ArrayList());
/*     */   
/*     */   protected String _NodeID;
/*     */   protected String _Version;
/*  21 */   protected ListImpl _Constraint = new ListImpl(new ArrayList());
/*     */   
/*  23 */   protected ListImpl _InformationObject = new ListImpl(new ArrayList());
/*     */   
/*  25 */   protected ListImpl _Label = new ListImpl(new ArrayList());
/*     */   
/*  27 */   private static final Grammar schemaFragment = SchemaDeserializer.deserialize("¬í\000\005sr\000\037com.sun.msv.grammar.SequenceExp\000\000\000\000\000\000\000\001\002\000\000xr\000\035com.sun.msv.grammar.BinaryExp\000\000\000\000\000\000\000\001\002\000\002L\000\004exp1t\000 Lcom/sun/msv/grammar/Expression;L\000\004exp2q\000~\000\002xr\000\036com.sun.msv.grammar.Expressionø\030èN5~O\002\000\003I\000\016cachedHashCodeL\000\023epsilonReducibilityt\000\023Ljava/lang/Boolean;L\000\013expandedExpq\000~\000\002xp\nº!\bppsq\000~\000\000\007ê©vppsq\000~\000\000\006\003æ ppsq\000~\000\000\004ìôppsq\000~\000\000\003\001óHppsr\000 com.sun.msv.grammar.OneOrMoreExp\000\000\000\000\000\000\000\001\002\000\000xr\000\034com.sun.msv.grammar.UnaryExp\000\000\000\000\000\000\000\001\002\000\001L\000\003expq\000~\000\002xq\000~\000\003\001ùppsr\000'com.sun.msv.grammar.trex.ElementPattern\000\000\000\000\000\000\000\001\002\000\001L\000\tnameClasst\000\037Lcom/sun/msv/grammar/NameClass;xr\000\036com.sun.msv.grammar.ElementExp\000\000\000\000\000\000\000\001\002\000\002Z\000\032ignoreUndeclaredAttributesL\000\fcontentModelq\000~\000\002xq\000~\000\003\001ùpp\000sq\000~\000\r\001ùpp\000sr\000\035com.sun.msv.grammar.ChoiceExp\000\000\000\000\000\000\000\001\002\000\000xq\000~\000\001\001ùppsq\000~\000\n\001ùxsr\000\021java.lang.BooleanÍ rÕúî\002\000\001Z\000\005valuexp\000psr\000 com.sun.msv.grammar.AttributeExp\000\000\000\000\000\000\000\001\002\000\002L\000\003expq\000~\000\002L\000\tnameClassq\000~\000\016xq\000~\000\003\001ùuq\000~\000\026psr\0002com.sun.msv.grammar.Expression$AnyStringExpression\000\000\000\000\000\000\000\001\002\000\000xq\000~\000\003\000\000\000\bsq\000~\000\025\001psr\000 com.sun.msv.grammar.AnyNameClass\000\000\000\000\000\000\000\001\002\000\000xr\000\035com.sun.msv.grammar.NameClass\000\000\000\000\000\000\000\001\002\000\000xpsr\0000com.sun.msv.grammar.Expression$EpsilonExpression\000\000\000\000\000\000\000\001\002\000\000xq\000~\000\003\000\000\000\tq\000~\000\033psr\000#com.sun.msv.grammar.SimpleNameClass\000\000\000\000\000\000\000\001\002\000\002L\000\tlocalNamet\000\022Ljava/lang/String;L\000\fnamespaceURIq\000~\000\"xq\000~\000\035t\0000com.eoos.gm.tis2web.ctoc.common.db.xml.LabelTypet\000+http://java.sun.com/jaxb/xjc/dummy-elementssq\000~\000!t\000\005Labelt\000\000sq\000~\000\022\001ù§ppsq\000~\000\n\001ùq\000~\000\026psq\000~\000\r\001ùq\000~\000\026p\000sq\000~\000\r\001ùpp\000sq\000~\000\022\001ùppsq\000~\000\n\001ùxq\000~\000\026psq\000~\000\027\001ùuq\000~\000\026pq\000~\000\032q\000~\000\036q\000~\000 sq\000~\000!t\0005com.eoos.gm.tis2web.ctoc.common.db.xml.ConstraintTypeq\000~\000%sq\000~\000!t\000\nConstraintq\000~\000(q\000~\000 sq\000~\000\022\001ù§ppsq\000~\000\n\001ùq\000~\000\026psq\000~\000\r\001ùq\000~\000\026p\000sq\000~\000\r\001ùpp\000sq\000~\000\022\001ùppsq\000~\000\n\001ùxq\000~\000\026psq\000~\000\027\001ùuq\000~\000\026pq\000~\000\032q\000~\000\036q\000~\000 sq\000~\000!t\000/com.eoos.gm.tis2web.ctoc.common.db.xml.NodeTypeq\000~\000%sq\000~\000!t\000\004Nodeq\000~\000(q\000~\000 sq\000~\000\022\001ù§ppsq\000~\000\n\001ùq\000~\000\026psq\000~\000\r\001ùq\000~\000\026p\000sq\000~\000\r\001ùpp\000sq\000~\000\022\001ùppsq\000~\000\n\001ùxq\000~\000\026psq\000~\000\027\001ùuq\000~\000\026pq\000~\000\032q\000~\000\036q\000~\000 sq\000~\000!t\000<com.eoos.gm.tis2web.ctoc.common.db.xml.InformationObjectTypeq\000~\000%sq\000~\000!t\000\021InformationObjectq\000~\000(q\000~\000 sq\000~\000\027\001æÂÑppsr\000\033com.sun.msv.grammar.DataExp\000\000\000\000\000\000\000\001\002\000\003L\000\002dtt\000\037Lorg/relaxng/datatype/Datatype;L\000\006exceptq\000~\000\002L\000\004namet\000\035Lcom/sun/msv/util/StringPair;xq\000~\000\003\001»ÒOppsr\000#com.sun.msv.datatype.xsd.StringType\000\000\000\000\000\000\000\001\002\000\001Z\000\risAlwaysValidxr\000*com.sun.msv.datatype.xsd.BuiltinAtomicType\000\000\000\000\000\000\000\001\002\000\000xr\000%com.sun.msv.datatype.xsd.ConcreteType\000\000\000\000\000\000\000\001\002\000\000xr\000'com.sun.msv.datatype.xsd.XSDatatypeImpl\000\000\000\000\000\000\000\001\002\000\003L\000\fnamespaceUriq\000~\000\"L\000\btypeNameq\000~\000\"L\000\nwhiteSpacet\000.Lcom/sun/msv/datatype/xsd/WhiteSpaceProcessor;xpt\000 http://www.w3.org/2001/XMLSchemat\000\006stringsr\000.com.sun.msv.datatype.xsd.WhiteSpaceProcessor$1\023JMoIÛ¤G\002\000\000xr\000,com.sun.msv.datatype.xsd.WhiteSpaceProcessor\000\000\000\000\000\000\000\001\002\000\000xp\001sr\0000com.sun.msv.grammar.Expression$NullSetExpression\000\000\000\000\000\000\000\001\002\000\000xq\000~\000\003\000\000\000\nppsr\000\033com.sun.msv.util.StringPairÐt\036jB \002\000\002L\000\tlocalNameq\000~\000\"L\000\fnamespaceURIq\000~\000\"xpq\000~\000Vq\000~\000Usq\000~\000!t\000\006NodeIDq\000~\000(sq\000~\000\027\002Ïwppq\000~\000Nsq\000~\000!t\000\007Versionq\000~\000(sr\000\"com.sun.msv.grammar.ExpressionPool\000\000\000\000\000\000\000\001\002\000\001L\000\bexpTablet\000/Lcom/sun/msv/grammar/ExpressionPool$ClosedHash;xpsr\000-com.sun.msv.grammar.ExpressionPool$ClosedHash×jÐNïèí\034\002\000\004I\000\005countI\000\tthresholdL\000\006parentq\000~\000d[\000\005tablet\000![Lcom/sun/msv/grammar/Expression;xp\000\000\000\024\000\000\0009pur\000![Lcom.sun.msv.grammar.Expression;Ö8DÃ]­§\n\002\000\000xp\000\000\000¿pppppppppppppppppppq\000~\000\006pppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppq\000~\000\007ppppppppppppppppppppppq\000~\000\bpppppppppq\000~\000\024q\000~\000.q\000~\0009q\000~\000Dpppppppq\000~\000\023q\000~\000-q\000~\000\tq\000~\0008q\000~\000Cppppppppppppppppppppq\000~\000\fq\000~\000*q\000~\0005q\000~\000@pppppppq\000~\000)q\000~\0004q\000~\000?ppppppppppppppq\000~\000\005pppppppppp");
/*     */ 
/*     */   
/*     */   private static final Class PRIMARY_INTERFACE_CLASS() {
/*  31 */     return NodeType.class;
/*     */   }
/*     */   
/*     */   public String getNodeID() {
/*  35 */     return this._NodeID;
/*     */   }
/*     */   
/*     */   public void setNodeID(String value) {
/*  39 */     this._NodeID = value;
/*     */   }
/*     */   
/*     */   public List getNode() {
/*  43 */     return (List)this._Node;
/*     */   }
/*     */   
/*     */   public String getVersion() {
/*  47 */     return this._Version;
/*     */   }
/*     */   
/*     */   public void setVersion(String value) {
/*  51 */     this._Version = value;
/*     */   }
/*     */   
/*     */   public List getConstraint() {
/*  55 */     return (List)this._Constraint;
/*     */   }
/*     */   
/*     */   public List getInformationObject() {
/*  59 */     return (List)this._InformationObject;
/*     */   }
/*     */   
/*     */   public List getLabel() {
/*  63 */     return (List)this._Label;
/*     */   }
/*     */   
/*     */   public ContentHandlerEx getUnmarshaller(UnmarshallingContext context) {
/*  67 */     return new Unmarshaller(context);
/*     */   }
/*     */   
/*     */   public Class getPrimaryInterfaceClass() {
/*  71 */     return PRIMARY_INTERFACE_CLASS();
/*     */   }
/*     */   
/*     */   public void serializeElements(XMLSerializer context) throws SAXException {
/*  75 */     int idx2 = 0;
/*  76 */     int len2 = this._Node.size();
/*  77 */     int idx4 = 0;
/*  78 */     int len4 = this._Constraint.size();
/*  79 */     int idx5 = 0;
/*  80 */     int len5 = this._InformationObject.size();
/*  81 */     int idx6 = 0;
/*  82 */     int len6 = this._Label.size();
/*  83 */     while (idx6 != len6) {
/*  84 */       if (this._Label.get(idx6) instanceof javax.xml.bind.Element) {
/*  85 */         context.childAsElements((XMLSerializable)this._Label.get(idx6++)); continue;
/*     */       } 
/*  87 */       context.startElement("", "Label");
/*  88 */       int idx_0 = idx6;
/*  89 */       context.childAsAttributes((XMLSerializable)this._Label.get(idx_0++));
/*  90 */       context.endAttributes();
/*  91 */       context.childAsElements((XMLSerializable)this._Label.get(idx6++));
/*  92 */       context.endElement();
/*     */     } 
/*     */     
/*  95 */     while (idx4 != len4) {
/*  96 */       if (this._Constraint.get(idx4) instanceof javax.xml.bind.Element) {
/*  97 */         context.childAsElements((XMLSerializable)this._Constraint.get(idx4++)); continue;
/*     */       } 
/*  99 */       context.startElement("", "Constraint");
/* 100 */       int idx_1 = idx4;
/* 101 */       context.childAsAttributes((XMLSerializable)this._Constraint.get(idx_1++));
/* 102 */       context.endAttributes();
/* 103 */       context.childAsElements((XMLSerializable)this._Constraint.get(idx4++));
/* 104 */       context.endElement();
/*     */     } 
/*     */     
/* 107 */     while (idx2 != len2) {
/* 108 */       if (this._Node.get(idx2) instanceof javax.xml.bind.Element) {
/* 109 */         context.childAsElements((XMLSerializable)this._Node.get(idx2++)); continue;
/*     */       } 
/* 111 */       context.startElement("", "Node");
/* 112 */       int idx_2 = idx2;
/* 113 */       context.childAsAttributes((XMLSerializable)this._Node.get(idx_2++));
/* 114 */       context.endAttributes();
/* 115 */       context.childAsElements((XMLSerializable)this._Node.get(idx2++));
/* 116 */       context.endElement();
/*     */     } 
/*     */     
/* 119 */     while (idx5 != len5) {
/* 120 */       if (this._InformationObject.get(idx5) instanceof javax.xml.bind.Element) {
/* 121 */         context.childAsElements((XMLSerializable)this._InformationObject.get(idx5++)); continue;
/*     */       } 
/* 123 */       context.startElement("", "InformationObject");
/* 124 */       int idx_3 = idx5;
/* 125 */       context.childAsAttributes((XMLSerializable)this._InformationObject.get(idx_3++));
/* 126 */       context.endAttributes();
/* 127 */       context.childAsElements((XMLSerializable)this._InformationObject.get(idx5++));
/* 128 */       context.endElement();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void serializeAttributes(XMLSerializer context) throws SAXException {
/* 134 */     this._Node.size();
/* 135 */     this._Constraint.size();
/* 136 */     this._InformationObject.size();
/* 137 */     this._Label.size();
/* 138 */     context.startAttribute("", "NodeID");
/*     */     try {
/* 140 */       context.text(this._NodeID);
/* 141 */     } catch (Exception e) {
/* 142 */       Util.handlePrintConversionException(this, e, context);
/*     */     } 
/* 144 */     context.endAttribute();
/* 145 */     context.startAttribute("", "Version");
/*     */     try {
/* 147 */       context.text(this._Version);
/* 148 */     } catch (Exception e) {
/* 149 */       Util.handlePrintConversionException(this, e, context);
/*     */     } 
/* 151 */     context.endAttribute();
/*     */   }
/*     */   
/*     */   public void serializeAttributeBodies(XMLSerializer context) throws SAXException {
/* 155 */     this._Node.size();
/* 156 */     this._Constraint.size();
/* 157 */     this._InformationObject.size();
/* 158 */     this._Label.size();
/*     */   }
/*     */   
/*     */   public Class getPrimaryInterface() {
/* 162 */     return NodeType.class;
/*     */   }
/*     */   
/*     */   public DocumentDeclaration createRawValidator() {
/* 166 */     return (DocumentDeclaration)new REDocumentDeclaration(schemaFragment);
/*     */   }
/*     */   
/*     */   public class Unmarshaller
/*     */     extends ContentHandlerEx {
/*     */     public Unmarshaller(UnmarshallingContext context) {
/* 172 */       super(context, "--------------");
/*     */     }
/*     */     
/*     */     protected UnmarshallableObject owner() {
/* 176 */       return NodeTypeImpl.this;
/*     */     }
/*     */     
/*     */     public void enterElement(String ___uri, String ___local, Attributes __atts) throws UnreportedException {
/* 180 */       switch (this.state) {
/*     */         case 0:
/* 182 */           if ("" == ___uri && "Label" == ___local) {
/* 183 */             this.context.pushAttributes(__atts);
/* 184 */             goto1();
/*     */             return;
/*     */           } 
/*     */           break;
/*     */         case 3:
/* 189 */           if ("" == ___uri && "Label" == ___local) {
/* 190 */             this.context.pushAttributes(__atts);
/* 191 */             goto1();
/*     */             return;
/*     */           } 
/* 194 */           if ("" == ___uri && "Node" == ___local) {
/* 195 */             this.context.pushAttributes(__atts);
/* 196 */             goto6();
/*     */             return;
/*     */           } 
/* 199 */           if ("" == ___uri && "Constraint" == ___local) {
/* 200 */             this.context.pushAttributes(__atts);
/* 201 */             goto4();
/*     */             return;
/*     */           } 
/* 204 */           if ("" == ___uri && "InformationObject" == ___local) {
/* 205 */             this.context.pushAttributes(__atts);
/* 206 */             goto8();
/*     */             return;
/*     */           } 
/* 209 */           revertToParentFromEnterElement(___uri, ___local, __atts);
/*     */           return;
/*     */         case 6:
/* 212 */           if ("" == ___uri && "Label" == ___local) {
/* 213 */             NodeTypeImpl.this._Node.add(spawnChildFromEnterElement(NodeTypeImpl.class, 7, ___uri, ___local, __atts));
/*     */             return;
/*     */           } 
/*     */           break;
/*     */         case 8:
/* 218 */           if ("" == ___uri && "Label" == ___local) {
/* 219 */             NodeTypeImpl.this._InformationObject.add(spawnChildFromEnterElement(InformationObjectTypeImpl.class, 9, ___uri, ___local, __atts));
/*     */             return;
/*     */           } 
/*     */           break;
/*     */       } 
/* 224 */       super.enterElement(___uri, ___local, __atts);
/*     */     }
/*     */     
/*     */     public void leaveElement(String ___uri, String ___local) throws UnreportedException {
/* 228 */       switch (this.state) {
/*     */         case 9:
/* 230 */           if ("" == ___uri && "InformationObject" == ___local) {
/* 231 */             this.context.popAttributes();
/* 232 */             this.state = 3;
/*     */             return;
/*     */           } 
/*     */           break;
/*     */         case 2:
/* 237 */           if ("" == ___uri && "Label" == ___local) {
/* 238 */             this.context.popAttributes();
/* 239 */             this.state = 3;
/*     */             return;
/*     */           } 
/*     */           break;
/*     */         case 4:
/* 244 */           if ("" == ___uri && "Constraint" == ___local) {
/* 245 */             NodeTypeImpl.this._Constraint.add(spawnChildFromLeaveElement(ConstraintTypeImpl.class, 5, ___uri, ___local));
/*     */             return;
/*     */           } 
/*     */           break;
/*     */         case 3:
/* 250 */           revertToParentFromLeaveElement(___uri, ___local);
/*     */           return;
/*     */         case 5:
/* 253 */           if ("" == ___uri && "Constraint" == ___local) {
/* 254 */             this.context.popAttributes();
/* 255 */             this.state = 3;
/*     */             return;
/*     */           } 
/*     */           break;
/*     */         case 7:
/* 260 */           if ("" == ___uri && "Node" == ___local) {
/* 261 */             this.context.popAttributes();
/* 262 */             this.state = 3;
/*     */             return;
/*     */           } 
/*     */           break;
/*     */       } 
/* 267 */       super.leaveElement(___uri, ___local);
/*     */     }
/*     */     
/*     */     public void enterAttribute(String ___uri, String ___local) throws UnreportedException {
/* 271 */       switch (this.state) {
/*     */         case 1:
/* 273 */           if ("" == ___uri && "Locale" == ___local) {
/* 274 */             NodeTypeImpl.this._Label.add(spawnChildFromEnterAttribute(LabelTypeImpl.class, 2, ___uri, ___local));
/*     */             return;
/*     */           } 
/* 277 */           if ("" == ___uri && "Label" == ___local) {
/* 278 */             NodeTypeImpl.this._Label.add(spawnChildFromEnterAttribute(LabelTypeImpl.class, 2, ___uri, ___local));
/*     */             return;
/*     */           } 
/*     */           break;
/*     */         case 0:
/* 283 */           if ("" == ___uri && "Version" == ___local) {
/* 284 */             this.state = 10;
/*     */             return;
/*     */           } 
/* 287 */           if ("" == ___uri && "NodeID" == ___local) {
/* 288 */             this.state = 12;
/*     */             return;
/*     */           } 
/*     */           break;
/*     */         case 4:
/* 293 */           if ("" == ___uri && "Manufacturer" == ___local) {
/* 294 */             NodeTypeImpl.this._Constraint.add(spawnChildFromEnterAttribute(ConstraintTypeImpl.class, 5, ___uri, ___local));
/*     */             return;
/*     */           } 
/* 297 */           if ("" == ___uri && "Country" == ___local) {
/* 298 */             NodeTypeImpl.this._Constraint.add(spawnChildFromEnterAttribute(ConstraintTypeImpl.class, 5, ___uri, ___local));
/*     */             return;
/*     */           } 
/* 301 */           if ("" == ___uri && "Application" == ___local) {
/* 302 */             NodeTypeImpl.this._Constraint.add(spawnChildFromEnterAttribute(ConstraintTypeImpl.class, 5, ___uri, ___local));
/*     */             return;
/*     */           } 
/* 305 */           if ("" == ___uri && "UserGroup" == ___local) {
/* 306 */             NodeTypeImpl.this._Constraint.add(spawnChildFromEnterAttribute(ConstraintTypeImpl.class, 5, ___uri, ___local));
/*     */             return;
/*     */           } 
/* 309 */           if ("" == ___uri && "ServiceInformationType" == ___local) {
/* 310 */             NodeTypeImpl.this._Constraint.add(spawnChildFromEnterAttribute(ConstraintTypeImpl.class, 5, ___uri, ___local));
/*     */             return;
/*     */           } 
/*     */           break;
/*     */         case 3:
/* 315 */           revertToParentFromEnterAttribute(___uri, ___local);
/*     */           return;
/*     */         case 6:
/* 318 */           if ("" == ___uri && "Version" == ___local) {
/* 319 */             NodeTypeImpl.this._Node.add(spawnChildFromEnterAttribute(NodeTypeImpl.class, 7, ___uri, ___local));
/*     */             return;
/*     */           } 
/* 322 */           if ("" == ___uri && "NodeID" == ___local) {
/* 323 */             NodeTypeImpl.this._Node.add(spawnChildFromEnterAttribute(NodeTypeImpl.class, 7, ___uri, ___local));
/*     */             return;
/*     */           } 
/*     */           break;
/*     */         case 8:
/* 328 */           if ("" == ___uri && "InformationObjectID" == ___local) {
/* 329 */             NodeTypeImpl.this._InformationObject.add(spawnChildFromEnterAttribute(InformationObjectTypeImpl.class, 9, ___uri, ___local));
/*     */             return;
/*     */           } 
/* 332 */           if ("" == ___uri && "ServiceInformationType" == ___local) {
/* 333 */             NodeTypeImpl.this._InformationObject.add(spawnChildFromEnterAttribute(InformationObjectTypeImpl.class, 9, ___uri, ___local));
/*     */             return;
/*     */           } 
/* 336 */           if ("" == ___uri && "Page" == ___local) {
/* 337 */             NodeTypeImpl.this._InformationObject.add(spawnChildFromEnterAttribute(InformationObjectTypeImpl.class, 9, ___uri, ___local));
/*     */             return;
/*     */           } 
/* 340 */           if ("" == ___uri && "Version" == ___local) {
/* 341 */             NodeTypeImpl.this._InformationObject.add(spawnChildFromEnterAttribute(InformationObjectTypeImpl.class, 9, ___uri, ___local));
/*     */             return;
/*     */           } 
/*     */           break;
/*     */       } 
/* 346 */       super.enterAttribute(___uri, ___local);
/*     */     }
/*     */     
/*     */     public void leaveAttribute(String ___uri, String ___local) throws UnreportedException {
/* 350 */       switch (this.state) {
/*     */         case 11:
/* 352 */           if ("" == ___uri && "Version" == ___local) {
/* 353 */             goto0();
/*     */             return;
/*     */           } 
/*     */           break;
/*     */         case 3:
/* 358 */           revertToParentFromLeaveAttribute(___uri, ___local);
/*     */           return;
/*     */         case 13:
/* 361 */           if ("" == ___uri && "NodeID" == ___local) {
/* 362 */             goto0();
/*     */             return;
/*     */           } 
/*     */           break;
/*     */       } 
/* 367 */       super.leaveAttribute(___uri, ___local);
/*     */     }
/*     */     
/*     */     public void text(String value) throws UnreportedException {
/*     */       try {
/* 372 */         switch (this.state) {
/*     */           case 10:
/*     */             try {
/* 375 */               NodeTypeImpl.this._Version = value;
/* 376 */             } catch (Exception e) {
/* 377 */               handleParseConversionException(e);
/*     */             } 
/* 379 */             this.state = 11;
/*     */             return;
/*     */           case 3:
/* 382 */             revertToParentFromText(value);
/*     */             return;
/*     */           case 12:
/*     */             try {
/* 386 */               NodeTypeImpl.this._NodeID = value;
/* 387 */             } catch (Exception e) {
/* 388 */               handleParseConversionException(e);
/*     */             } 
/* 390 */             this.state = 13;
/*     */             return;
/*     */         } 
/* 393 */       } catch (RuntimeException e) {
/* 394 */         handleUnexpectedTextException(value, e);
/*     */       } 
/*     */     }
/*     */     
/*     */     public void leaveChild(int nextState) throws UnreportedException {
/* 399 */       switch (nextState) {
/*     */         case 9:
/* 401 */           this.state = 9;
/*     */           return;
/*     */         case 2:
/* 404 */           this.state = 2;
/*     */           return;
/*     */         case 5:
/* 407 */           this.state = 5;
/*     */           return;
/*     */         case 7:
/* 410 */           this.state = 7;
/*     */           return;
/*     */       } 
/* 413 */       super.leaveChild(nextState);
/*     */     }
/*     */ 
/*     */     
/*     */     private void goto1() throws UnreportedException {
/* 418 */       this.state = 1;
/* 419 */       int idx = this.context.getAttribute("", "Label");
/* 420 */       if (idx >= 0) {
/* 421 */         this.context.consumeAttribute(idx);
/*     */         return;
/*     */       } 
/* 424 */       idx = this.context.getAttribute("", "Locale");
/* 425 */       if (idx >= 0) {
/* 426 */         this.context.consumeAttribute(idx);
/*     */         return;
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     private void goto0() throws UnreportedException {
/* 433 */       this.state = 0;
/* 434 */       int idx = this.context.getAttribute("", "NodeID");
/* 435 */       if (idx >= 0) {
/* 436 */         this.context.consumeAttribute(idx);
/*     */         return;
/*     */       } 
/* 439 */       idx = this.context.getAttribute("", "Version");
/* 440 */       if (idx >= 0) {
/* 441 */         this.context.consumeAttribute(idx);
/*     */         return;
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     private void goto4() throws UnreportedException {
/* 448 */       this.state = 4;
/* 449 */       int idx = this.context.getAttribute("", "Manufacturer");
/* 450 */       if (idx >= 0) {
/* 451 */         this.context.consumeAttribute(idx);
/*     */         return;
/*     */       } 
/* 454 */       idx = this.context.getAttribute("", "Application");
/* 455 */       if (idx >= 0) {
/* 456 */         this.context.consumeAttribute(idx);
/*     */         return;
/*     */       } 
/* 459 */       idx = this.context.getAttribute("", "UserGroup");
/* 460 */       if (idx >= 0) {
/* 461 */         this.context.consumeAttribute(idx);
/*     */         return;
/*     */       } 
/* 464 */       idx = this.context.getAttribute("", "Country");
/* 465 */       if (idx >= 0) {
/* 466 */         this.context.consumeAttribute(idx);
/*     */         return;
/*     */       } 
/* 469 */       idx = this.context.getAttribute("", "ServiceInformationType");
/* 470 */       if (idx >= 0) {
/* 471 */         this.context.consumeAttribute(idx);
/*     */         return;
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     private void goto6() throws UnreportedException {
/* 478 */       this.state = 6;
/* 479 */       int idx = this.context.getAttribute("", "NodeID");
/* 480 */       if (idx >= 0) {
/* 481 */         this.context.consumeAttribute(idx);
/*     */         return;
/*     */       } 
/* 484 */       idx = this.context.getAttribute("", "Version");
/* 485 */       if (idx >= 0) {
/* 486 */         this.context.consumeAttribute(idx);
/*     */         return;
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     private void goto8() throws UnreportedException {
/* 493 */       this.state = 8;
/* 494 */       int idx = this.context.getAttribute("", "Page");
/* 495 */       if (idx >= 0) {
/* 496 */         this.context.consumeAttribute(idx);
/*     */         return;
/*     */       } 
/* 499 */       idx = this.context.getAttribute("", "InformationObjectID");
/* 500 */       if (idx >= 0) {
/* 501 */         this.context.consumeAttribute(idx);
/*     */         return;
/*     */       } 
/* 504 */       idx = this.context.getAttribute("", "ServiceInformationType");
/* 505 */       if (idx >= 0) {
/* 506 */         this.context.consumeAttribute(idx);
/*     */         return;
/*     */       } 
/* 509 */       idx = this.context.getAttribute("", "Version");
/* 510 */       if (idx >= 0) {
/* 511 */         this.context.consumeAttribute(idx);
/*     */         return;
/*     */       } 
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\ctoc\implementation\common\db\xml\impl\NodeTypeImpl.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */