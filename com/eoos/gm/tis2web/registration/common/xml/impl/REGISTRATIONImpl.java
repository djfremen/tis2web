/*     */ package com.eoos.gm.tis2web.registration.common.xml.impl;
/*     */ import com.eoos.gm.tis2web.registration.common.xml.REGISTRATION;
/*     */ import com.sun.msv.verifier.DocumentDeclaration;
/*     */ import com.sun.xml.bind.RIElement;
/*     */ import com.sun.xml.bind.serializer.XMLSerializer;
/*     */ import com.sun.xml.bind.unmarshaller.ContentHandlerEx;
/*     */ import com.sun.xml.bind.unmarshaller.UnmarshallableObject;
/*     */ import com.sun.xml.bind.unmarshaller.UnmarshallingContext;
/*     */ import com.sun.xml.bind.unmarshaller.UnreportedException;
/*     */ import com.sun.xml.bind.validator.SchemaDeserializer;
/*     */ import org.xml.sax.Attributes;
/*     */ import org.xml.sax.SAXException;
/*     */ 
/*     */ public class REGISTRATIONImpl extends REGISTRATIONTypeImpl implements REGISTRATION, RIElement, UnmarshallableObject, XMLSerializable, ValidatableObject {
/*  15 */   private static final Grammar schemaFragment = SchemaDeserializer.deserialize("¬í\000\005sr\000'com.sun.msv.grammar.trex.ElementPattern\000\000\000\000\000\000\000\001\002\000\001L\000\tnameClasst\000\037Lcom/sun/msv/grammar/NameClass;xr\000\036com.sun.msv.grammar.ElementExp\000\000\000\000\000\000\000\001\002\000\002Z\000\032ignoreUndeclaredAttributesL\000\fcontentModelt\000 Lcom/sun/msv/grammar/Expression;xr\000\036com.sun.msv.grammar.Expressionø\030èN5~O\002\000\003I\000\016cachedHashCodeL\000\023epsilonReducibilityt\000\023Ljava/lang/Boolean;L\000\013expandedExpq\000~\000\003xp\t(\tpp\000sr\000\037com.sun.msv.grammar.SequenceExp\000\000\000\000\000\000\000\001\002\000\000xr\000\035com.sun.msv.grammar.BinaryExp\000\000\000\000\000\000\000\001\002\000\002L\000\004exp1q\000~\000\003L\000\004exp2q\000~\000\003xq\000~\000\004\t(\t~ppsq\000~\000\007\bmðppsq\000~\000\007\007Ö¥ppsq\000~\000\007\006c8ppsq\000~\000\007\005P®ppsq\000~\000\007\003NÄppsq\000~\000\007\002zâ\025ppsq\000~\000\007\001§A^ppsr\000\035com.sun.msv.grammar.ChoiceExp\000\000\000\000\000\000\000\001\002\000\000xq\000~\000\b\000Ó §ppsq\000~\000\000\000iÐXpp\000sq\000~\000\000\000iÐMpp\000sq\000~\000\021\000iÐBppsr\000 com.sun.msv.grammar.OneOrMoreExp\000\000\000\000\000\000\000\001\002\000\000xr\000\034com.sun.msv.grammar.UnaryExp\000\000\000\000\000\000\000\001\002\000\001L\000\003expq\000~\000\003xq\000~\000\004\000iÐ7sr\000\021java.lang.BooleanÍ rÕúî\002\000\001Z\000\005valuexp\000psr\000 com.sun.msv.grammar.AttributeExp\000\000\000\000\000\000\000\001\002\000\002L\000\003expq\000~\000\003L\000\tnameClassq\000~\000\001xq\000~\000\004\000iÐ4q\000~\000\032psr\0002com.sun.msv.grammar.Expression$AnyStringExpression\000\000\000\000\000\000\000\001\002\000\000xq\000~\000\004\000\000\000\bsq\000~\000\031\001psr\000 com.sun.msv.grammar.AnyNameClass\000\000\000\000\000\000\000\001\002\000\000xr\000\035com.sun.msv.grammar.NameClass\000\000\000\000\000\000\000\001\002\000\000xpsr\0000com.sun.msv.grammar.Expression$EpsilonExpression\000\000\000\000\000\000\000\001\002\000\000xq\000~\000\004\000\000\000\tq\000~\000\037psr\000#com.sun.msv.grammar.SimpleNameClass\000\000\000\000\000\000\000\001\002\000\002L\000\tlocalNamet\000\022Ljava/lang/String;L\000\fnamespaceURIq\000~\000&xq\000~\000!t\000:com.eoos.gm.tis2web.registration.common.xml.DEALERSHIPTypet\000+http://java.sun.com/jaxb/xjc/dummy-elementssq\000~\000%t\000\nDEALERSHIPt\000\000sq\000~\000\000\000iÐMpp\000sq\000~\000\021\000iÐBppsq\000~\000\026\000iÐ7q\000~\000\032psq\000~\000\033\000iÐ4q\000~\000\032pq\000~\000\036q\000~\000\"q\000~\000$sq\000~\000%t\0006com.eoos.gm.tis2web.registration.common.xml.DEALERSHIPq\000~\000)sq\000~\000\021\000Ó ²ppsq\000~\000\021\000Ó §q\000~\000\032psq\000~\000\000\000iÐXq\000~\000\032p\000sq\000~\000\000\000iÐMpp\000sq\000~\000\021\000iÐBppsq\000~\000\026\000iÐ7q\000~\000\032psq\000~\000\033\000iÐ4q\000~\000\032pq\000~\000\036q\000~\000\"q\000~\000$sq\000~\000%t\000;com.eoos.gm.tis2web.registration.common.xml.SOFTWAREKEYTypeq\000~\000)sq\000~\000%t\000\fSOFTWARE_KEYq\000~\000,sq\000~\000\000\000iÐMq\000~\000\032p\000sq\000~\000\021\000iÐBppsq\000~\000\026\000iÐ7q\000~\000\032psq\000~\000\033\000iÐ4q\000~\000\032pq\000~\000\036q\000~\000\"q\000~\000$sq\000~\000%t\0007com.eoos.gm.tis2web.registration.common.xml.SOFTWAREKEYq\000~\000)q\000~\000$sq\000~\000\021\000Ó ²ppsq\000~\000\021\000Ó §q\000~\000\032psq\000~\000\000\000iÐXq\000~\000\032p\000sq\000~\000\000\000iÐMpp\000sq\000~\000\021\000iÐBppsq\000~\000\026\000iÐ7q\000~\000\032psq\000~\000\033\000iÐ4q\000~\000\032pq\000~\000\036q\000~\000\"q\000~\000$sq\000~\000%t\000:com.eoos.gm.tis2web.registration.common.xml.LICENSEKEYTypeq\000~\000)sq\000~\000%t\000\013LICENSE_KEYq\000~\000,sq\000~\000\000\000iÐMq\000~\000\032p\000sq\000~\000\021\000iÐBppsq\000~\000\026\000iÐ7q\000~\000\032psq\000~\000\033\000iÐ4q\000~\000\032pq\000~\000\036q\000~\000\"q\000~\000$sq\000~\000%t\0006com.eoos.gm.tis2web.registration.common.xml.LICENSEKEYq\000~\000)q\000~\000$sq\000~\000\026\000Ó ªppsq\000~\000\021\000Ó §ppsq\000~\000\000\000iÐXpp\000sq\000~\000\000\000iÐMpp\000sq\000~\000\021\000iÐBppsq\000~\000\026\000iÐ7q\000~\000\032psq\000~\000\033\000iÐ4q\000~\000\032pq\000~\000\036q\000~\000\"q\000~\000$sq\000~\000%t\000<com.eoos.gm.tis2web.registration.common.xml.SUBSCRIPTIONTypeq\000~\000)sq\000~\000%t\000\fSUBSCRIPTIONq\000~\000,sq\000~\000\000\000iÐMpp\000sq\000~\000\021\000iÐBppsq\000~\000\026\000iÐ7q\000~\000\032psq\000~\000\033\000iÐ4q\000~\000\032pq\000~\000\036q\000~\000\"q\000~\000$sq\000~\000%t\0008com.eoos.gm.tis2web.registration.common.xml.SUBSCRIPTIONq\000~\000)sq\000~\000\033\002\002\båppsr\000\033com.sun.msv.grammar.DataExp\000\000\000\000\000\000\000\001\002\000\003L\000\002dtt\000\037Lorg/relaxng/datatype/Datatype;L\000\006exceptq\000~\000\003L\000\004namet\000\035Lcom/sun/msv/util/StringPair;xq\000~\000\004\000Ð/ppsr\000#com.sun.msv.datatype.xsd.StringType\000\000\000\000\000\000\000\001\002\000\001Z\000\risAlwaysValidxr\000*com.sun.msv.datatype.xsd.BuiltinAtomicType\000\000\000\000\000\000\000\001\002\000\000xr\000%com.sun.msv.datatype.xsd.ConcreteType\000\000\000\000\000\000\000\001\002\000\000xr\000'com.sun.msv.datatype.xsd.XSDatatypeImpl\000\000\000\000\000\000\000\001\002\000\003L\000\fnamespaceUriq\000~\000&L\000\btypeNameq\000~\000&L\000\nwhiteSpacet\000.Lcom/sun/msv/datatype/xsd/WhiteSpaceProcessor;xpt\000 http://www.w3.org/2001/XMLSchemat\000\006stringsr\000.com.sun.msv.datatype.xsd.WhiteSpaceProcessor$1\023JMoIÛ¤G\002\000\000xr\000,com.sun.msv.datatype.xsd.WhiteSpaceProcessor\000\000\000\000\000\000\000\001\002\000\000xp\001sr\0000com.sun.msv.grammar.Expression$NullSetExpression\000\000\000\000\000\000\000\001\002\000\000xq\000~\000\004\000\000\000\nppsr\000\033com.sun.msv.util.StringPairÐt\036jB \002\000\002L\000\tlocalNameq\000~\000&L\000\fnamespaceURIq\000~\000&xpq\000~\000rq\000~\000qsq\000~\000%t\000\013RequestDateq\000~\000,sq\000~\000\021\001H×ppsq\000~\000\033\001H×zq\000~\000\032pq\000~\000jsq\000~\000%t\000\fSubscriberIDq\000~\000,q\000~\000$sq\000~\000\033\000íshppq\000~\000jsq\000~\000%t\000\tRequestIDq\000~\000,sq\000~\000\021\000æ·Fppsq\000~\000\033\000æ·;q\000~\000\032pq\000~\000jsq\000~\000%t\000\020LicensedSessionsq\000~\000,q\000~\000$sq\000~\000\021\000º{ppsq\000~\000\033\000º{~q\000~\000\032psr\000\034com.sun.msv.grammar.ValueExp\000\000\000\000\000\000\000\001\002\000\003L\000\002dtq\000~\000hL\000\004nameq\000~\000iL\000\005valuet\000\022Ljava/lang/Object;xq\000~\000\004\000Ð&ppq\000~\000pq\000~\000yt\000\0031.0sq\000~\000%t\000\007Versionq\000~\000,q\000~\000$sq\000~\000%t\000\fREGISTRATIONq\000~\000,sr\000\"com.sun.msv.grammar.ExpressionPool\000\000\000\000\000\000\000\001\002\000\001L\000\bexpTablet\000/Lcom/sun/msv/grammar/ExpressionPool$ClosedHash;xpsr\000-com.sun.msv.grammar.ExpressionPool$ClosedHash×jÐNïèí\034\002\000\004I\000\005countI\000\tthresholdL\000\006parentq\000~\000[\000\005tablet\000![Lcom/sun/msv/grammar/Expression;xp\000\000\000\"\000\000\0009pur\000![Lcom.sun.msv.grammar.Expression;Ö8DÃ]­§\n\002\000\000xp\000\000\000¿pppppppppppppppppppppppppppq\000~\000\016pppppppppppppppppppppppppppppppppppppq\000~\000pppq\000~\000|q\000~\000\017q\000~\000\fpppppppppppppppppppppppppppppppppq\000~\000\020ppppppppppppq\000~\000\013ppppppppppppppppppq\000~\000\030q\000~\000/q\000~\0008q\000~\000\022q\000~\000@q\000~\0004q\000~\000Iq\000~\000Qq\000~\000Eq\000~\000Zq\000~\000bq\000~\000\025q\000~\000.q\000~\0007q\000~\000?q\000~\0003q\000~\000Hq\000~\000Pq\000~\000Dq\000~\000Yq\000~\000aq\000~\000Vq\000~\000Uppppppppppppppppppppppppppq\000~\000pq\000~\000\rq\000~\000\nq\000~\000\t");
/*     */ 
/*     */   
/*     */   public String ____jaxb_ri____getNamespaceURI() {
/*  19 */     return "";
/*     */   }
/*     */   
/*     */   public String ____jaxb_ri____getLocalName() {
/*  23 */     return "REGISTRATION";
/*     */   }
/*     */   
/*     */   private static final Class PRIMARY_INTERFACE_CLASS() {
/*  27 */     return REGISTRATION.class;
/*     */   }
/*     */   
/*     */   public ContentHandlerEx getUnmarshaller(UnmarshallingContext context) {
/*  31 */     return new Unmarshaller(context);
/*     */   }
/*     */   
/*     */   public Class getPrimaryInterfaceClass() {
/*  35 */     return PRIMARY_INTERFACE_CLASS();
/*     */   }
/*     */   
/*     */   public void serializeElements(XMLSerializer context) throws SAXException {
/*  39 */     context.startElement("", "REGISTRATION");
/*  40 */     super.serializeAttributes(context);
/*  41 */     context.endAttributes();
/*  42 */     super.serializeElements(context);
/*  43 */     context.endElement();
/*     */   }
/*     */ 
/*     */   
/*     */   public void serializeAttributes(XMLSerializer context) throws SAXException {}
/*     */ 
/*     */   
/*     */   public void serializeAttributeBodies(XMLSerializer context) throws SAXException {}
/*     */   
/*     */   public Class getPrimaryInterface() {
/*  53 */     return REGISTRATION.class;
/*     */   }
/*     */   
/*     */   public DocumentDeclaration createRawValidator() {
/*  57 */     return (DocumentDeclaration)new REDocumentDeclaration(schemaFragment);
/*     */   }
/*     */   
/*     */   public class Unmarshaller
/*     */     extends ContentHandlerEx {
/*     */     public Unmarshaller(UnmarshallingContext context) {
/*  63 */       super(context, "----");
/*     */     }
/*     */     
/*     */     protected UnmarshallableObject owner() {
/*  67 */       return REGISTRATIONImpl.this;
/*     */     }
/*     */     
/*     */     public void enterElement(String ___uri, String ___local, Attributes __atts) throws UnreportedException {
/*  71 */       switch (this.state) {
/*     */         case 1:
/*  73 */           if ("" == ___uri && "DEALERSHIP" == ___local) {
/*  74 */             REGISTRATIONImpl.this.getClass(); spawnSuperClassFromEnterElement(new REGISTRATIONTypeImpl.Unmarshaller(REGISTRATIONImpl.this, this.context), 2, ___uri, ___local, __atts);
/*     */             return;
/*     */           } 
/*     */           break;
/*     */         case 0:
/*  79 */           if ("" == ___uri && "REGISTRATION" == ___local) {
/*  80 */             this.context.pushAttributes(__atts);
/*  81 */             goto1();
/*     */             return;
/*     */           } 
/*     */           break;
/*     */         case 3:
/*  86 */           revertToParentFromEnterElement(___uri, ___local, __atts);
/*     */           return;
/*     */       } 
/*  89 */       super.enterElement(___uri, ___local, __atts);
/*     */     }
/*     */     
/*     */     public void leaveElement(String ___uri, String ___local) throws UnreportedException {
/*  93 */       switch (this.state) {
/*     */         case 2:
/*  95 */           if ("" == ___uri && "REGISTRATION" == ___local) {
/*  96 */             this.context.popAttributes();
/*  97 */             this.state = 3;
/*     */             return;
/*     */           } 
/*     */           break;
/*     */         case 3:
/* 102 */           revertToParentFromLeaveElement(___uri, ___local);
/*     */           return;
/*     */       } 
/* 105 */       super.leaveElement(___uri, ___local);
/*     */     }
/*     */     
/*     */     public void enterAttribute(String ___uri, String ___local) throws UnreportedException {
/* 109 */       switch (this.state) {
/*     */         case 1:
/* 111 */           if ("" == ___uri && "LicensedSessions" == ___local) {
/* 112 */             REGISTRATIONImpl.this.getClass(); spawnSuperClassFromEnterAttribute(new REGISTRATIONTypeImpl.Unmarshaller(REGISTRATIONImpl.this, this.context), 2, ___uri, ___local);
/*     */             return;
/*     */           } 
/* 115 */           if ("" == ___uri && "RequestDate" == ___local) {
/* 116 */             REGISTRATIONImpl.this.getClass(); spawnSuperClassFromEnterAttribute(new REGISTRATIONTypeImpl.Unmarshaller(REGISTRATIONImpl.this, this.context), 2, ___uri, ___local);
/*     */             return;
/*     */           } 
/* 119 */           if ("" == ___uri && "RequestID" == ___local) {
/* 120 */             REGISTRATIONImpl.this.getClass(); spawnSuperClassFromEnterAttribute(new REGISTRATIONTypeImpl.Unmarshaller(REGISTRATIONImpl.this, this.context), 2, ___uri, ___local);
/*     */             return;
/*     */           } 
/* 123 */           if ("" == ___uri && "SubscriberID" == ___local) {
/* 124 */             REGISTRATIONImpl.this.getClass(); spawnSuperClassFromEnterAttribute(new REGISTRATIONTypeImpl.Unmarshaller(REGISTRATIONImpl.this, this.context), 2, ___uri, ___local);
/*     */             return;
/*     */           } 
/* 127 */           if ("" == ___uri && "Version" == ___local) {
/* 128 */             REGISTRATIONImpl.this.getClass(); spawnSuperClassFromEnterAttribute(new REGISTRATIONTypeImpl.Unmarshaller(REGISTRATIONImpl.this, this.context), 2, ___uri, ___local);
/*     */             return;
/*     */           } 
/*     */           break;
/*     */         case 3:
/* 133 */           revertToParentFromEnterAttribute(___uri, ___local);
/*     */           return;
/*     */       } 
/* 136 */       super.enterAttribute(___uri, ___local);
/*     */     }
/*     */     
/*     */     public void leaveAttribute(String ___uri, String ___local) throws UnreportedException {
/* 140 */       switch (this.state) {
/*     */         case 3:
/* 142 */           revertToParentFromLeaveAttribute(___uri, ___local);
/*     */           return;
/*     */       } 
/* 145 */       super.leaveAttribute(___uri, ___local);
/*     */     }
/*     */     
/*     */     public void text(String value) throws UnreportedException {
/*     */       try {
/* 150 */         switch (this.state) {
/*     */           case 3:
/* 152 */             revertToParentFromText(value);
/*     */             return;
/*     */         } 
/* 155 */       } catch (RuntimeException e) {
/* 156 */         handleUnexpectedTextException(value, e);
/*     */       } 
/*     */     }
/*     */     
/*     */     public void leaveChild(int nextState) throws UnreportedException {
/* 161 */       switch (nextState) {
/*     */         case 2:
/* 163 */           this.state = 2;
/*     */           return;
/*     */       } 
/* 166 */       super.leaveChild(nextState);
/*     */     }
/*     */ 
/*     */     
/*     */     private void goto1() throws UnreportedException {
/* 171 */       this.state = 1;
/* 172 */       int idx = this.context.getAttribute("", "RequestDate");
/* 173 */       if (idx >= 0) {
/* 174 */         this.context.consumeAttribute(idx);
/*     */         return;
/*     */       } 
/* 177 */       idx = this.context.getAttribute("", "SubscriberID");
/* 178 */       if (idx >= 0) {
/* 179 */         this.context.consumeAttribute(idx);
/*     */         return;
/*     */       } 
/* 182 */       idx = this.context.getAttribute("", "RequestID");
/* 183 */       if (idx >= 0) {
/* 184 */         this.context.consumeAttribute(idx);
/*     */         return;
/*     */       } 
/* 187 */       idx = this.context.getAttribute("", "LicensedSessions");
/* 188 */       if (idx >= 0) {
/* 189 */         this.context.consumeAttribute(idx);
/*     */         return;
/*     */       } 
/* 192 */       idx = this.context.getAttribute("", "Version");
/* 193 */       if (idx >= 0) {
/* 194 */         this.context.consumeAttribute(idx);
/*     */         return;
/*     */       } 
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\registration\common\xml\impl\REGISTRATIONImpl.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */