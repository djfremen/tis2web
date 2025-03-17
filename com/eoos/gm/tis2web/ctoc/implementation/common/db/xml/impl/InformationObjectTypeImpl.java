/*     */ package com.eoos.gm.tis2web.ctoc.implementation.common.db.xml.impl;
/*     */ import com.eoos.gm.tis2web.ctoc.implementation.common.db.xml.InformationObjectType;
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
/*     */ public class InformationObjectTypeImpl implements InformationObjectType, UnmarshallableObject, XMLSerializable, ValidatableObject {
/*  17 */   protected ListImpl _Constraint = new ListImpl(new ArrayList());
/*     */   protected String _Version;
/*  19 */   protected ListImpl _Label = new ListImpl(new ArrayList());
/*     */   
/*     */   protected String _ServiceInformationType;
/*     */   
/*     */   protected String _InformationObjectID;
/*     */   
/*  25 */   protected ListImpl _FileReference = new ListImpl(new ArrayList());
/*     */   
/*     */   protected String _Page;
/*     */   
/*  29 */   private static final Grammar schemaFragment = SchemaDeserializer.deserialize("¬í\000\005sr\000\037com.sun.msv.grammar.SequenceExp\000\000\000\000\000\000\000\001\002\000\000xr\000\035com.sun.msv.grammar.BinaryExp\000\000\000\000\000\000\000\001\002\000\002L\000\004exp1t\000 Lcom/sun/msv/grammar/Expression;L\000\004exp2q\000~\000\002xr\000\036com.sun.msv.grammar.Expressionø\030èN5~O\002\000\003I\000\016cachedHashCodeL\000\023epsilonReducibilityt\000\023Ljava/lang/Boolean;L\000\013expandedExpq\000~\000\002xp\017Ê2Çppsq\000~\000\000\rÏhbppsq\000~\000\000\013Y±³ppsq\000~\000\000\b$L\001ppsq\000~\000\000\004ìéppsq\000~\000\000\003\001óHppsr\000 com.sun.msv.grammar.OneOrMoreExp\000\000\000\000\000\000\000\001\002\000\000xr\000\034com.sun.msv.grammar.UnaryExp\000\000\000\000\000\000\000\001\002\000\001L\000\003expq\000~\000\002xq\000~\000\003\001ùppsr\000'com.sun.msv.grammar.trex.ElementPattern\000\000\000\000\000\000\000\001\002\000\001L\000\tnameClasst\000\037Lcom/sun/msv/grammar/NameClass;xr\000\036com.sun.msv.grammar.ElementExp\000\000\000\000\000\000\000\001\002\000\002Z\000\032ignoreUndeclaredAttributesL\000\fcontentModelq\000~\000\002xq\000~\000\003\001ùpp\000sq\000~\000\016\001ùpp\000sr\000\035com.sun.msv.grammar.ChoiceExp\000\000\000\000\000\000\000\001\002\000\000xq\000~\000\001\001ùppsq\000~\000\013\001ùxsr\000\021java.lang.BooleanÍ rÕúî\002\000\001Z\000\005valuexp\000psr\000 com.sun.msv.grammar.AttributeExp\000\000\000\000\000\000\000\001\002\000\002L\000\003expq\000~\000\002L\000\tnameClassq\000~\000\017xq\000~\000\003\001ùuq\000~\000\027psr\0002com.sun.msv.grammar.Expression$AnyStringExpression\000\000\000\000\000\000\000\001\002\000\000xq\000~\000\003\000\000\000\bsq\000~\000\026\001psr\000 com.sun.msv.grammar.AnyNameClass\000\000\000\000\000\000\000\001\002\000\000xr\000\035com.sun.msv.grammar.NameClass\000\000\000\000\000\000\000\001\002\000\000xpsr\0000com.sun.msv.grammar.Expression$EpsilonExpression\000\000\000\000\000\000\000\001\002\000\000xq\000~\000\003\000\000\000\tq\000~\000\034psr\000#com.sun.msv.grammar.SimpleNameClass\000\000\000\000\000\000\000\001\002\000\002L\000\tlocalNamet\000\022Ljava/lang/String;L\000\fnamespaceURIq\000~\000#xq\000~\000\036t\0000com.eoos.gm.tis2web.ctoc.common.db.xml.LabelTypet\000+http://java.sun.com/jaxb/xjc/dummy-elementssq\000~\000\"t\000\005Labelt\000\000sq\000~\000\023\001ù§ppsq\000~\000\013\001ùq\000~\000\027psq\000~\000\016\001ùq\000~\000\027p\000sq\000~\000\016\001ùpp\000sq\000~\000\023\001ùppsq\000~\000\013\001ùxq\000~\000\027psq\000~\000\030\001ùuq\000~\000\027pq\000~\000\033q\000~\000\037q\000~\000!sq\000~\000\"t\0005com.eoos.gm.tis2web.ctoc.common.db.xml.ConstraintTypeq\000~\000&sq\000~\000\"t\000\nConstraintq\000~\000)q\000~\000!sq\000~\000\013\001ùppsq\000~\000\016\001ùpp\000sq\000~\000\016\001ùpp\000sq\000~\000\023\001ùppsq\000~\000\013\001ùxq\000~\000\027psq\000~\000\030\001ùuq\000~\000\027pq\000~\000\033q\000~\000\037q\000~\000!sq\000~\000\"t\0008com.eoos.gm.tis2web.ctoc.common.db.xml.FileReferenceTypeq\000~\000&sq\000~\000\"t\000\rFileReferenceq\000~\000)sq\000~\000\023\003¡_\023ppsq\000~\000\030\003¡_\bq\000~\000\027psr\000\033com.sun.msv.grammar.DataExp\000\000\000\000\000\000\000\001\002\000\003L\000\002dtt\000\037Lorg/relaxng/datatype/Datatype;L\000\006exceptq\000~\000\002L\000\004namet\000\035Lcom/sun/msv/util/StringPair;xq\000~\000\003\001»ÒOppsr\000#com.sun.msv.datatype.xsd.StringType\000\000\000\000\000\000\000\001\002\000\001Z\000\risAlwaysValidxr\000*com.sun.msv.datatype.xsd.BuiltinAtomicType\000\000\000\000\000\000\000\001\002\000\000xr\000%com.sun.msv.datatype.xsd.ConcreteType\000\000\000\000\000\000\000\001\002\000\000xr\000'com.sun.msv.datatype.xsd.XSDatatypeImpl\000\000\000\000\000\000\000\001\002\000\003L\000\fnamespaceUriq\000~\000#L\000\btypeNameq\000~\000#L\000\nwhiteSpacet\000.Lcom/sun/msv/datatype/xsd/WhiteSpaceProcessor;xpt\000 http://www.w3.org/2001/XMLSchemat\000\006stringsr\000.com.sun.msv.datatype.xsd.WhiteSpaceProcessor$1\023JMoIÛ¤G\002\000\000xr\000,com.sun.msv.datatype.xsd.WhiteSpaceProcessor\000\000\000\000\000\000\000\001\002\000\000xp\001sr\0000com.sun.msv.grammar.Expression$NullSetExpression\000\000\000\000\000\000\000\001\002\000\000xq\000~\000\003\000\000\000\nppsr\000\033com.sun.msv.util.StringPairÐt\036jB \002\000\002L\000\tlocalNameq\000~\000#L\000\fnamespaceURIq\000~\000#xpq\000~\000Lq\000~\000Ksq\000~\000\"t\000\004Pageq\000~\000)q\000~\000!sq\000~\000\030\0035e­ppq\000~\000Dsq\000~\000\"t\000\023InformationObjectIDq\000~\000)sq\000~\000\030\002u¶ªppq\000~\000Dsq\000~\000\"t\000\026ServiceInformationTypeq\000~\000)sq\000~\000\030\001úÊ`ppq\000~\000Dsq\000~\000\"t\000\007Versionq\000~\000)sr\000\"com.sun.msv.grammar.ExpressionPool\000\000\000\000\000\000\000\001\002\000\001L\000\bexpTablet\000/Lcom/sun/msv/grammar/ExpressionPool$ClosedHash;xpsr\000-com.sun.msv.grammar.ExpressionPool$ClosedHash×jÐNïèí\034\002\000\004I\000\005countI\000\tthresholdL\000\006parentq\000~\000`[\000\005tablet\000![Lcom/sun/msv/grammar/Expression;xp\000\000\000\021\000\000\0009pur\000![Lcom.sun.msv.grammar.Expression;Ö8DÃ]­§\n\002\000\000xp\000\000\000¿pppppppppppppppppppppppppppppppppppppppppppppppppppppppppq\000~\000?pppppppppppppppppppppppppppppppppppppq\000~\000\tppppppppppppppppq\000~\000\006pppq\000~\000\025q\000~\000/q\000~\0009ppppppppq\000~\000\024q\000~\000.q\000~\000\nq\000~\0008ppppppppppppppppppppq\000~\000\005q\000~\000\rq\000~\000+q\000~\0005ppq\000~\000\bpppppq\000~\000*pppppppppppppppppppppppppq\000~\000\007p");
/*     */ 
/*     */   
/*     */   private static final Class PRIMARY_INTERFACE_CLASS() {
/*  33 */     return InformationObjectType.class;
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
/*     */   public List getConstraint() {
/*  45 */     return (List)this._Constraint;
/*     */   }
/*     */   
/*     */   public List getLabel() {
/*  49 */     return (List)this._Label;
/*     */   }
/*     */   
/*     */   public String getServiceInformationType() {
/*  53 */     return this._ServiceInformationType;
/*     */   }
/*     */   
/*     */   public void setServiceInformationType(String value) {
/*  57 */     this._ServiceInformationType = value;
/*     */   }
/*     */   
/*     */   public String getInformationObjectID() {
/*  61 */     return this._InformationObjectID;
/*     */   }
/*     */   
/*     */   public void setInformationObjectID(String value) {
/*  65 */     this._InformationObjectID = value;
/*     */   }
/*     */   
/*     */   public List getFileReference() {
/*  69 */     return (List)this._FileReference;
/*     */   }
/*     */   
/*     */   public String getPage() {
/*  73 */     return this._Page;
/*     */   }
/*     */   
/*     */   public void setPage(String value) {
/*  77 */     this._Page = value;
/*     */   }
/*     */   
/*     */   public ContentHandlerEx getUnmarshaller(UnmarshallingContext context) {
/*  81 */     return new Unmarshaller(context);
/*     */   }
/*     */   
/*     */   public Class getPrimaryInterfaceClass() {
/*  85 */     return PRIMARY_INTERFACE_CLASS();
/*     */   }
/*     */   
/*     */   public void serializeElements(XMLSerializer context) throws SAXException {
/*  89 */     int idx2 = 0;
/*  90 */     int len2 = this._Constraint.size();
/*  91 */     int idx3 = 0;
/*  92 */     int len3 = this._Label.size();
/*  93 */     int idx6 = 0;
/*  94 */     int len6 = this._FileReference.size();
/*  95 */     while (idx3 != len3) {
/*  96 */       if (this._Label.get(idx3) instanceof javax.xml.bind.Element) {
/*  97 */         context.childAsElements((XMLSerializable)this._Label.get(idx3++)); continue;
/*     */       } 
/*  99 */       context.startElement("", "Label");
/* 100 */       int idx_0 = idx3;
/* 101 */       context.childAsAttributes((XMLSerializable)this._Label.get(idx_0++));
/* 102 */       context.endAttributes();
/* 103 */       context.childAsElements((XMLSerializable)this._Label.get(idx3++));
/* 104 */       context.endElement();
/*     */     } 
/*     */     
/* 107 */     while (idx2 != len2) {
/* 108 */       if (this._Constraint.get(idx2) instanceof javax.xml.bind.Element) {
/* 109 */         context.childAsElements((XMLSerializable)this._Constraint.get(idx2++)); continue;
/*     */       } 
/* 111 */       context.startElement("", "Constraint");
/* 112 */       int idx_1 = idx2;
/* 113 */       context.childAsAttributes((XMLSerializable)this._Constraint.get(idx_1++));
/* 114 */       context.endAttributes();
/* 115 */       context.childAsElements((XMLSerializable)this._Constraint.get(idx2++));
/* 116 */       context.endElement();
/*     */     } 
/*     */     
/* 119 */     while (idx6 != len6) {
/* 120 */       if (this._FileReference.get(idx6) instanceof javax.xml.bind.Element) {
/* 121 */         context.childAsElements((XMLSerializable)this._FileReference.get(idx6++)); continue;
/*     */       } 
/* 123 */       context.startElement("", "FileReference");
/* 124 */       int idx_2 = idx6;
/* 125 */       context.childAsAttributes((XMLSerializable)this._FileReference.get(idx_2++));
/* 126 */       context.endAttributes();
/* 127 */       context.childAsElements((XMLSerializable)this._FileReference.get(idx6++));
/* 128 */       context.endElement();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void serializeAttributes(XMLSerializer context) throws SAXException {
/* 134 */     this._Constraint.size();
/* 135 */     this._Label.size();
/* 136 */     this._FileReference.size();
/* 137 */     if (this._Page != null) {
/* 138 */       context.startAttribute("", "Page");
/*     */       try {
/* 140 */         context.text(this._Page);
/* 141 */       } catch (Exception e) {
/* 142 */         Util.handlePrintConversionException(this, e, context);
/*     */       } 
/* 144 */       context.endAttribute();
/*     */     } 
/* 146 */     context.startAttribute("", "InformationObjectID");
/*     */     try {
/* 148 */       context.text(this._InformationObjectID);
/* 149 */     } catch (Exception e) {
/* 150 */       Util.handlePrintConversionException(this, e, context);
/*     */     } 
/* 152 */     context.endAttribute();
/* 153 */     context.startAttribute("", "ServiceInformationType");
/*     */     try {
/* 155 */       context.text(this._ServiceInformationType);
/* 156 */     } catch (Exception e) {
/* 157 */       Util.handlePrintConversionException(this, e, context);
/*     */     } 
/* 159 */     context.endAttribute();
/* 160 */     context.startAttribute("", "Version");
/*     */     try {
/* 162 */       context.text(this._Version);
/* 163 */     } catch (Exception e) {
/* 164 */       Util.handlePrintConversionException(this, e, context);
/*     */     } 
/* 166 */     context.endAttribute();
/*     */   }
/*     */   
/*     */   public void serializeAttributeBodies(XMLSerializer context) throws SAXException {
/* 170 */     this._Constraint.size();
/* 171 */     this._Label.size();
/* 172 */     this._FileReference.size();
/*     */   }
/*     */   
/*     */   public Class getPrimaryInterface() {
/* 176 */     return InformationObjectType.class;
/*     */   }
/*     */   
/*     */   public DocumentDeclaration createRawValidator() {
/* 180 */     return (DocumentDeclaration)new REDocumentDeclaration(schemaFragment);
/*     */   }
/*     */   
/*     */   public class Unmarshaller
/*     */     extends ContentHandlerEx {
/*     */     public Unmarshaller(UnmarshallingContext context) {
/* 186 */       super(context, "-----------------");
/*     */     }
/*     */     
/*     */     protected UnmarshallableObject owner() {
/* 190 */       return InformationObjectTypeImpl.this;
/*     */     }
/*     */     
/*     */     public void enterElement(String ___uri, String ___local, Attributes __atts) throws UnreportedException {
/* 194 */       switch (this.state) {
/*     */         case 3:
/* 196 */           if ("" == ___uri && "Constraint" == ___local) {
/* 197 */             this.context.pushAttributes(__atts);
/* 198 */             goto4();
/*     */             return;
/*     */           } 
/* 201 */           if ("" == ___uri && "Label" == ___local) {
/* 202 */             this.context.pushAttributes(__atts);
/* 203 */             goto1();
/*     */             return;
/*     */           } 
/* 206 */           if ("" == ___uri && "FileReference" == ___local) {
/* 207 */             this.context.pushAttributes(__atts);
/* 208 */             goto6();
/*     */             return;
/*     */           } 
/*     */           break;
/*     */         case 8:
/* 213 */           if ("" == ___uri && "FileReference" == ___local) {
/* 214 */             this.context.pushAttributes(__atts);
/* 215 */             goto6();
/*     */             return;
/*     */           } 
/* 218 */           revertToParentFromEnterElement(___uri, ___local, __atts);
/*     */           return;
/*     */         case 0:
/* 221 */           if ("" == ___uri && "Label" == ___local) {
/* 222 */             this.context.pushAttributes(__atts);
/* 223 */             goto1();
/*     */             return;
/*     */           } 
/*     */           break;
/*     */       } 
/* 228 */       super.enterElement(___uri, ___local, __atts);
/*     */     }
/*     */     
/*     */     public void leaveElement(String ___uri, String ___local) throws UnreportedException {
/* 232 */       switch (this.state) {
/*     */         case 7:
/* 234 */           if ("" == ___uri && "FileReference" == ___local) {
/* 235 */             this.context.popAttributes();
/* 236 */             this.state = 8;
/*     */             return;
/*     */           } 
/*     */           break;
/*     */         case 4:
/* 241 */           if ("" == ___uri && "Constraint" == ___local) {
/* 242 */             InformationObjectTypeImpl.this._Constraint.add(spawnChildFromLeaveElement(ConstraintTypeImpl.class, 5, ___uri, ___local));
/*     */             return;
/*     */           } 
/*     */           break;
/*     */         case 2:
/* 247 */           if ("" == ___uri && "Label" == ___local) {
/* 248 */             this.context.popAttributes();
/* 249 */             this.state = 3;
/*     */             return;
/*     */           } 
/*     */           break;
/*     */         case 5:
/* 254 */           if ("" == ___uri && "Constraint" == ___local) {
/* 255 */             this.context.popAttributes();
/* 256 */             this.state = 3;
/*     */             return;
/*     */           } 
/*     */           break;
/*     */         case 8:
/* 261 */           revertToParentFromLeaveElement(___uri, ___local);
/*     */           return;
/*     */       } 
/* 264 */       super.leaveElement(___uri, ___local);
/*     */     }
/*     */     
/*     */     public void enterAttribute(String ___uri, String ___local) throws UnreportedException {
/* 268 */       switch (this.state) {
/*     */         case 4:
/* 270 */           if ("" == ___uri && "Manufacturer" == ___local) {
/* 271 */             InformationObjectTypeImpl.this._Constraint.add(spawnChildFromEnterAttribute(ConstraintTypeImpl.class, 5, ___uri, ___local));
/*     */             return;
/*     */           } 
/* 274 */           if ("" == ___uri && "Country" == ___local) {
/* 275 */             InformationObjectTypeImpl.this._Constraint.add(spawnChildFromEnterAttribute(ConstraintTypeImpl.class, 5, ___uri, ___local));
/*     */             return;
/*     */           } 
/* 278 */           if ("" == ___uri && "Application" == ___local) {
/* 279 */             InformationObjectTypeImpl.this._Constraint.add(spawnChildFromEnterAttribute(ConstraintTypeImpl.class, 5, ___uri, ___local));
/*     */             return;
/*     */           } 
/* 282 */           if ("" == ___uri && "UserGroup" == ___local) {
/* 283 */             InformationObjectTypeImpl.this._Constraint.add(spawnChildFromEnterAttribute(ConstraintTypeImpl.class, 5, ___uri, ___local));
/*     */             return;
/*     */           } 
/* 286 */           if ("" == ___uri && "ServiceInformationType" == ___local) {
/* 287 */             InformationObjectTypeImpl.this._Constraint.add(spawnChildFromEnterAttribute(ConstraintTypeImpl.class, 5, ___uri, ___local));
/*     */             return;
/*     */           } 
/*     */           break;
/*     */         case 6:
/* 292 */           if ("" == ___uri && "Locale" == ___local) {
/* 293 */             InformationObjectTypeImpl.this._FileReference.add(spawnChildFromEnterAttribute(FileReferenceTypeImpl.class, 7, ___uri, ___local));
/*     */             return;
/*     */           } 
/* 296 */           if ("" == ___uri && "File" == ___local) {
/* 297 */             InformationObjectTypeImpl.this._FileReference.add(spawnChildFromEnterAttribute(FileReferenceTypeImpl.class, 7, ___uri, ___local));
/*     */             return;
/*     */           } 
/*     */           break;
/*     */         case 8:
/* 302 */           revertToParentFromEnterAttribute(___uri, ___local);
/*     */           return;
/*     */         case 0:
/* 305 */           if ("" == ___uri && "InformationObjectID" == ___local) {
/* 306 */             this.state = 9;
/*     */             return;
/*     */           } 
/* 309 */           if ("" == ___uri && "ServiceInformationType" == ___local) {
/* 310 */             this.state = 13;
/*     */             return;
/*     */           } 
/* 313 */           if ("" == ___uri && "Page" == ___local) {
/* 314 */             this.state = 11;
/*     */             return;
/*     */           } 
/* 317 */           if ("" == ___uri && "Version" == ___local) {
/* 318 */             this.state = 15;
/*     */             return;
/*     */           } 
/*     */           break;
/*     */         case 1:
/* 323 */           if ("" == ___uri && "Locale" == ___local) {
/* 324 */             InformationObjectTypeImpl.this._Label.add(spawnChildFromEnterAttribute(LabelTypeImpl.class, 2, ___uri, ___local));
/*     */             return;
/*     */           } 
/* 327 */           if ("" == ___uri && "Label" == ___local) {
/* 328 */             InformationObjectTypeImpl.this._Label.add(spawnChildFromEnterAttribute(LabelTypeImpl.class, 2, ___uri, ___local));
/*     */             return;
/*     */           } 
/*     */           break;
/*     */       } 
/* 333 */       super.enterAttribute(___uri, ___local);
/*     */     }
/*     */     
/*     */     public void leaveAttribute(String ___uri, String ___local) throws UnreportedException {
/* 337 */       switch (this.state) {
/*     */         case 14:
/* 339 */           if ("" == ___uri && "ServiceInformationType" == ___local) {
/* 340 */             goto0();
/*     */             return;
/*     */           } 
/*     */           break;
/*     */         case 10:
/* 345 */           if ("" == ___uri && "InformationObjectID" == ___local) {
/* 346 */             goto0();
/*     */             return;
/*     */           } 
/*     */           break;
/*     */         case 8:
/* 351 */           revertToParentFromLeaveAttribute(___uri, ___local);
/*     */           return;
/*     */         case 16:
/* 354 */           if ("" == ___uri && "Version" == ___local) {
/* 355 */             goto0();
/*     */             return;
/*     */           } 
/*     */           break;
/*     */         case 12:
/* 360 */           if ("" == ___uri && "Page" == ___local) {
/* 361 */             goto0();
/*     */             return;
/*     */           } 
/*     */           break;
/*     */       } 
/* 366 */       super.leaveAttribute(___uri, ___local);
/*     */     }
/*     */     
/*     */     public void text(String value) throws UnreportedException {
/*     */       try {
/* 371 */         switch (this.state) {
/*     */           case 13:
/*     */             try {
/* 374 */               InformationObjectTypeImpl.this._ServiceInformationType = value;
/* 375 */             } catch (Exception e) {
/* 376 */               handleParseConversionException(e);
/*     */             } 
/* 378 */             this.state = 14;
/*     */             return;
/*     */           case 15:
/*     */             try {
/* 382 */               InformationObjectTypeImpl.this._Version = value;
/* 383 */             } catch (Exception e) {
/* 384 */               handleParseConversionException(e);
/*     */             } 
/* 386 */             this.state = 16;
/*     */             return;
/*     */           case 9:
/*     */             try {
/* 390 */               InformationObjectTypeImpl.this._InformationObjectID = value;
/* 391 */             } catch (Exception e) {
/* 392 */               handleParseConversionException(e);
/*     */             } 
/* 394 */             this.state = 10;
/*     */             return;
/*     */           case 8:
/* 397 */             revertToParentFromText(value);
/*     */             return;
/*     */           case 11:
/*     */             try {
/* 401 */               InformationObjectTypeImpl.this._Page = value;
/* 402 */             } catch (Exception e) {
/* 403 */               handleParseConversionException(e);
/*     */             } 
/* 405 */             this.state = 12;
/*     */             return;
/*     */         } 
/* 408 */       } catch (RuntimeException e) {
/* 409 */         handleUnexpectedTextException(value, e);
/*     */       } 
/*     */     }
/*     */     
/*     */     public void leaveChild(int nextState) throws UnreportedException {
/* 414 */       switch (nextState) {
/*     */         case 7:
/* 416 */           this.state = 7;
/*     */           return;
/*     */         case 5:
/* 419 */           this.state = 5;
/*     */           return;
/*     */         case 2:
/* 422 */           this.state = 2;
/*     */           return;
/*     */       } 
/* 425 */       super.leaveChild(nextState);
/*     */     }
/*     */ 
/*     */     
/*     */     private void goto4() throws UnreportedException {
/* 430 */       this.state = 4;
/* 431 */       int idx = this.context.getAttribute("", "Manufacturer");
/* 432 */       if (idx >= 0) {
/* 433 */         this.context.consumeAttribute(idx);
/*     */         return;
/*     */       } 
/* 436 */       idx = this.context.getAttribute("", "Application");
/* 437 */       if (idx >= 0) {
/* 438 */         this.context.consumeAttribute(idx);
/*     */         return;
/*     */       } 
/* 441 */       idx = this.context.getAttribute("", "UserGroup");
/* 442 */       if (idx >= 0) {
/* 443 */         this.context.consumeAttribute(idx);
/*     */         return;
/*     */       } 
/* 446 */       idx = this.context.getAttribute("", "Country");
/* 447 */       if (idx >= 0) {
/* 448 */         this.context.consumeAttribute(idx);
/*     */         return;
/*     */       } 
/* 451 */       idx = this.context.getAttribute("", "ServiceInformationType");
/* 452 */       if (idx >= 0) {
/* 453 */         this.context.consumeAttribute(idx);
/*     */         return;
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     private void goto6() throws UnreportedException {
/* 460 */       this.state = 6;
/* 461 */       int idx = this.context.getAttribute("", "File");
/* 462 */       if (idx >= 0) {
/* 463 */         this.context.consumeAttribute(idx);
/*     */         return;
/*     */       } 
/* 466 */       idx = this.context.getAttribute("", "Locale");
/* 467 */       if (idx >= 0) {
/* 468 */         this.context.consumeAttribute(idx);
/*     */         return;
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     private void goto0() throws UnreportedException {
/* 475 */       this.state = 0;
/* 476 */       int idx = this.context.getAttribute("", "Page");
/* 477 */       if (idx >= 0) {
/* 478 */         this.context.consumeAttribute(idx);
/*     */         return;
/*     */       } 
/* 481 */       idx = this.context.getAttribute("", "InformationObjectID");
/* 482 */       if (idx >= 0) {
/* 483 */         this.context.consumeAttribute(idx);
/*     */         return;
/*     */       } 
/* 486 */       idx = this.context.getAttribute("", "ServiceInformationType");
/* 487 */       if (idx >= 0) {
/* 488 */         this.context.consumeAttribute(idx);
/*     */         return;
/*     */       } 
/* 491 */       idx = this.context.getAttribute("", "Version");
/* 492 */       if (idx >= 0) {
/* 493 */         this.context.consumeAttribute(idx);
/*     */         return;
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     private void goto1() throws UnreportedException {
/* 500 */       this.state = 1;
/* 501 */       int idx = this.context.getAttribute("", "Label");
/* 502 */       if (idx >= 0) {
/* 503 */         this.context.consumeAttribute(idx);
/*     */         return;
/*     */       } 
/* 506 */       idx = this.context.getAttribute("", "Locale");
/* 507 */       if (idx >= 0) {
/* 508 */         this.context.consumeAttribute(idx);
/*     */         return;
/*     */       } 
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\ctoc\implementation\common\db\xml\impl\InformationObjectTypeImpl.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */