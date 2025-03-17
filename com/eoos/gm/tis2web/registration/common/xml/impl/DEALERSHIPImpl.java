/*     */ package com.eoos.gm.tis2web.registration.common.xml.impl;
/*     */ import com.eoos.gm.tis2web.registration.common.xml.DEALERSHIP;
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
/*     */ public class DEALERSHIPImpl extends DEALERSHIPTypeImpl implements DEALERSHIP, RIElement, UnmarshallableObject, XMLSerializable, ValidatableObject {
/*  15 */   private static final Grammar schemaFragment = SchemaDeserializer.deserialize("¬í\000\005sr\000'com.sun.msv.grammar.trex.ElementPattern\000\000\000\000\000\000\000\001\002\000\001L\000\tnameClasst\000\037Lcom/sun/msv/grammar/NameClass;xr\000\036com.sun.msv.grammar.ElementExp\000\000\000\000\000\000\000\001\002\000\002Z\000\032ignoreUndeclaredAttributesL\000\fcontentModelt\000 Lcom/sun/msv/grammar/Expression;xr\000\036com.sun.msv.grammar.Expressionø\030èN5~O\002\000\003I\000\016cachedHashCodeL\000\023epsilonReducibilityt\000\023Ljava/lang/Boolean;L\000\013expandedExpq\000~\000\003xp\020`\005:pp\000sr\000\037com.sun.msv.grammar.SequenceExp\000\000\000\000\000\000\000\001\002\000\000xr\000\035com.sun.msv.grammar.BinaryExp\000\000\000\000\000\000\000\001\002\000\002L\000\004exp1q\000~\000\003L\000\004exp2q\000~\000\003xq\000~\000\004\020`\005/ppsq\000~\000\007\017ºn|ppsq\000~\000\007\r¾\003\003ppsq\000~\000\007\fUûppsq\000~\000\007\013µppsq\000~\000\007\tÖ\bppsq\000~\000\007\bâ°#ppsq\000~\000\007\007±«\017ppsq\000~\000\007\006#.Ïppsq\000~\000\007\005\000§ppsq\000~\000\007\002§äbppsr\000 com.sun.msv.grammar.OneOrMoreExp\000\000\000\000\000\000\000\001\002\000\000xr\000\034com.sun.msv.grammar.UnaryExp\000\000\000\000\000\000\000\001\002\000\001L\000\003expq\000~\000\003xq\000~\000\004\000Ó ªppsr\000\035com.sun.msv.grammar.ChoiceExp\000\000\000\000\000\000\000\001\002\000\000xq\000~\000\b\000Ó §ppsq\000~\000\000\000iÐXpp\000sq\000~\000\000\000iÐMpp\000sq\000~\000\027\000iÐBppsq\000~\000\024\000iÐ7sr\000\021java.lang.BooleanÍ rÕúî\002\000\001Z\000\005valuexp\000psr\000 com.sun.msv.grammar.AttributeExp\000\000\000\000\000\000\000\001\002\000\002L\000\003expq\000~\000\003L\000\tnameClassq\000~\000\001xq\000~\000\004\000iÐ4q\000~\000\036psr\0002com.sun.msv.grammar.Expression$AnyStringExpression\000\000\000\000\000\000\000\001\002\000\000xq\000~\000\004\000\000\000\bsq\000~\000\035\001psr\000 com.sun.msv.grammar.AnyNameClass\000\000\000\000\000\000\000\001\002\000\000xr\000\035com.sun.msv.grammar.NameClass\000\000\000\000\000\000\000\001\002\000\000xpsr\0000com.sun.msv.grammar.Expression$EpsilonExpression\000\000\000\000\000\000\000\001\002\000\000xq\000~\000\004\000\000\000\tq\000~\000#psr\000#com.sun.msv.grammar.SimpleNameClass\000\000\000\000\000\000\000\001\002\000\002L\000\tlocalNamet\000\022Ljava/lang/String;L\000\fnamespaceURIq\000~\000*xq\000~\000%t\000Acom.eoos.gm.tis2web.registration.common.xml.DEALERSHIPCONTACTTypet\000+http://java.sun.com/jaxb/xjc/dummy-elementssq\000~\000)t\000\022DEALERSHIP_CONTACTt\000\000sq\000~\000\000\000iÐMpp\000sq\000~\000\027\000iÐBppsq\000~\000\024\000iÐ7q\000~\000\036psq\000~\000\037\000iÐ4q\000~\000\036pq\000~\000\"q\000~\000&q\000~\000(sq\000~\000)t\000=com.eoos.gm.tis2web.registration.common.xml.DEALERSHIPCONTACTq\000~\000-sq\000~\000\027\001ÔC³ppsq\000~\000\037\001ÔC¨q\000~\000\036psr\000\033com.sun.msv.grammar.DataExp\000\000\000\000\000\000\000\001\002\000\003L\000\002dtt\000\037Lorg/relaxng/datatype/Datatype;L\000\006exceptq\000~\000\003L\000\004namet\000\035Lcom/sun/msv/util/StringPair;xq\000~\000\004\000Ð/ppsr\000#com.sun.msv.datatype.xsd.StringType\000\000\000\000\000\000\000\001\002\000\001Z\000\risAlwaysValidxr\000*com.sun.msv.datatype.xsd.BuiltinAtomicType\000\000\000\000\000\000\000\001\002\000\000xr\000%com.sun.msv.datatype.xsd.ConcreteType\000\000\000\000\000\000\000\001\002\000\000xr\000'com.sun.msv.datatype.xsd.XSDatatypeImpl\000\000\000\000\000\000\000\001\002\000\003L\000\fnamespaceUriq\000~\000*L\000\btypeNameq\000~\000*L\000\nwhiteSpacet\000.Lcom/sun/msv/datatype/xsd/WhiteSpaceProcessor;xpt\000 http://www.w3.org/2001/XMLSchemat\000\006stringsr\000.com.sun.msv.datatype.xsd.WhiteSpaceProcessor$1\023JMoIÛ¤G\002\000\000xr\000,com.sun.msv.datatype.xsd.WhiteSpaceProcessor\000\000\000\000\000\000\000\001\002\000\000xp\001sr\0000com.sun.msv.grammar.Expression$NullSetExpression\000\000\000\000\000\000\000\001\002\000\000xq\000~\000\004\000\000\000\nppsr\000\033com.sun.msv.util.StringPairÐt\036jB \002\000\002L\000\tlocalNameq\000~\000*L\000\fnamespaceURIq\000~\000*xpq\000~\000Dq\000~\000Csq\000~\000)t\000\017DealershipEmailq\000~\0000q\000~\000(sq\000~\000\027\002XÃ ppsq\000~\000\037\002XÃ\025q\000~\000\036pq\000~\000<sq\000~\000)t\000\017DealershipStateq\000~\0000q\000~\000(sq\000~\000\027\001\"Cppsq\000~\000\037\001\"8q\000~\000\036pq\000~\000<sq\000~\000)t\000\rDealershipFaxq\000~\0000q\000~\000(sq\000~\000\037\001|;ppq\000~\000<sq\000~\000)t\000\022DealershipLanguageq\000~\0000sq\000~\000\027\0011\005\017ppsq\000~\000\037\0011\005\004q\000~\000\036pq\000~\000<sq\000~\000)t\000\017DealershipPhoneq\000~\0000q\000~\000(sq\000~\000\037\000óXWppq\000~\000<sq\000~\000)t\000\021DealershipCountryq\000~\0000sq\000~\000\037\001ß\013ppq\000~\000<sq\000~\000)t\000\rDealershipZIPq\000~\0000sq\000~\000\037\000ýgppq\000~\000<sq\000~\000)t\000\020DealershipStreetq\000~\0000sq\000~\000\037\001hk\003ppq\000~\000<sq\000~\000)t\000\016DealershipNameq\000~\0000sq\000~\000\037\001üktppq\000~\000<sq\000~\000)t\000\016DealershipCodeq\000~\0000sq\000~\000\037\000¥®ppq\000~\000<sq\000~\000)t\000\016DealershipCityq\000~\0000sq\000~\000)t\000\nDEALERSHIPq\000~\0000sr\000\"com.sun.msv.grammar.ExpressionPool\000\000\000\000\000\000\000\001\002\000\001L\000\bexpTablet\000/Lcom/sun/msv/grammar/ExpressionPool$ClosedHash;xpsr\000-com.sun.msv.grammar.ExpressionPool$ClosedHash×jÐNïèí\034\002\000\004I\000\005countI\000\tthresholdL\000\006parentq\000~\000r[\000\005tablet\000![Lcom/sun/msv/grammar/Expression;xp\000\000\000\025\000\000\0009pur\000![Lcom.sun.msv.grammar.Expression;Ö8DÃ]­§\n\002\000\000xp\000\000\000¿ppppppppppq\000~\000\rpppppppq\000~\0007ppppppppppppppppppppppq\000~\000Rppppppppppppppppppppppppppppppq\000~\000\020ppppppppppppppppppppppppq\000~\000Ypppppppppppppppppq\000~\000\016ppppppppppppq\000~\000\013q\000~\000\022pppppppq\000~\000\034q\000~\0003pq\000~\000\030pq\000~\000\tq\000~\000\026ppppq\000~\000\033q\000~\0002q\000~\000Nppppppppppppppq\000~\000\fq\000~\000\023pppq\000~\000\npppq\000~\000\017q\000~\000\021ppppppppppppppp");
/*     */ 
/*     */   
/*     */   public String ____jaxb_ri____getNamespaceURI() {
/*  19 */     return "";
/*     */   }
/*     */   
/*     */   public String ____jaxb_ri____getLocalName() {
/*  23 */     return "DEALERSHIP";
/*     */   }
/*     */   
/*     */   private static final Class PRIMARY_INTERFACE_CLASS() {
/*  27 */     return DEALERSHIP.class;
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
/*  39 */     context.startElement("", "DEALERSHIP");
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
/*  53 */     return DEALERSHIP.class;
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
/*  67 */       return DEALERSHIPImpl.this;
/*     */     }
/*     */     
/*     */     public void enterElement(String ___uri, String ___local, Attributes __atts) throws UnreportedException {
/*  71 */       switch (this.state) {
/*     */         case 0:
/*  73 */           if ("" == ___uri && "DEALERSHIP" == ___local) {
/*  74 */             this.context.pushAttributes(__atts);
/*  75 */             goto1();
/*     */             return;
/*     */           } 
/*     */           break;
/*     */         case 3:
/*  80 */           revertToParentFromEnterElement(___uri, ___local, __atts);
/*     */           return;
/*     */         case 1:
/*  83 */           if ("" == ___uri && "DEALERSHIP_CONTACT" == ___local) {
/*  84 */             DEALERSHIPImpl.this.getClass(); spawnSuperClassFromEnterElement(new DEALERSHIPTypeImpl.Unmarshaller(DEALERSHIPImpl.this, this.context), 2, ___uri, ___local, __atts);
/*     */             return;
/*     */           } 
/*     */           break;
/*     */       } 
/*  89 */       super.enterElement(___uri, ___local, __atts);
/*     */     }
/*     */     
/*     */     public void leaveElement(String ___uri, String ___local) throws UnreportedException {
/*  93 */       switch (this.state) {
/*     */         case 3:
/*  95 */           revertToParentFromLeaveElement(___uri, ___local);
/*     */           return;
/*     */         case 2:
/*  98 */           if ("" == ___uri && "DEALERSHIP" == ___local) {
/*  99 */             this.context.popAttributes();
/* 100 */             this.state = 3;
/*     */             return;
/*     */           } 
/*     */           break;
/*     */       } 
/* 105 */       super.leaveElement(___uri, ___local);
/*     */     }
/*     */     
/*     */     public void enterAttribute(String ___uri, String ___local) throws UnreportedException {
/* 109 */       switch (this.state) {
/*     */         case 3:
/* 111 */           revertToParentFromEnterAttribute(___uri, ___local);
/*     */           return;
/*     */         case 1:
/* 114 */           if ("" == ___uri && "DealershipZIP" == ___local) {
/* 115 */             DEALERSHIPImpl.this.getClass(); spawnSuperClassFromEnterAttribute(new DEALERSHIPTypeImpl.Unmarshaller(DEALERSHIPImpl.this, this.context), 2, ___uri, ___local);
/*     */             return;
/*     */           } 
/* 118 */           if ("" == ___uri && "DealershipCity" == ___local) {
/* 119 */             DEALERSHIPImpl.this.getClass(); spawnSuperClassFromEnterAttribute(new DEALERSHIPTypeImpl.Unmarshaller(DEALERSHIPImpl.this, this.context), 2, ___uri, ___local);
/*     */             return;
/*     */           } 
/* 122 */           if ("" == ___uri && "DealershipCountry" == ___local) {
/* 123 */             DEALERSHIPImpl.this.getClass(); spawnSuperClassFromEnterAttribute(new DEALERSHIPTypeImpl.Unmarshaller(DEALERSHIPImpl.this, this.context), 2, ___uri, ___local);
/*     */             return;
/*     */           } 
/* 126 */           if ("" == ___uri && "DealershipName" == ___local) {
/* 127 */             DEALERSHIPImpl.this.getClass(); spawnSuperClassFromEnterAttribute(new DEALERSHIPTypeImpl.Unmarshaller(DEALERSHIPImpl.this, this.context), 2, ___uri, ___local);
/*     */             return;
/*     */           } 
/* 130 */           if ("" == ___uri && "DealershipStreet" == ___local) {
/* 131 */             DEALERSHIPImpl.this.getClass(); spawnSuperClassFromEnterAttribute(new DEALERSHIPTypeImpl.Unmarshaller(DEALERSHIPImpl.this, this.context), 2, ___uri, ___local);
/*     */             return;
/*     */           } 
/* 134 */           if ("" == ___uri && "DealershipState" == ___local) {
/* 135 */             DEALERSHIPImpl.this.getClass(); spawnSuperClassFromEnterAttribute(new DEALERSHIPTypeImpl.Unmarshaller(DEALERSHIPImpl.this, this.context), 2, ___uri, ___local);
/*     */             return;
/*     */           } 
/* 138 */           if ("" == ___uri && "DealershipPhone" == ___local) {
/* 139 */             DEALERSHIPImpl.this.getClass(); spawnSuperClassFromEnterAttribute(new DEALERSHIPTypeImpl.Unmarshaller(DEALERSHIPImpl.this, this.context), 2, ___uri, ___local);
/*     */             return;
/*     */           } 
/* 142 */           if ("" == ___uri && "DealershipFax" == ___local) {
/* 143 */             DEALERSHIPImpl.this.getClass(); spawnSuperClassFromEnterAttribute(new DEALERSHIPTypeImpl.Unmarshaller(DEALERSHIPImpl.this, this.context), 2, ___uri, ___local);
/*     */             return;
/*     */           } 
/* 146 */           if ("" == ___uri && "DealershipCode" == ___local) {
/* 147 */             DEALERSHIPImpl.this.getClass(); spawnSuperClassFromEnterAttribute(new DEALERSHIPTypeImpl.Unmarshaller(DEALERSHIPImpl.this, this.context), 2, ___uri, ___local);
/*     */             return;
/*     */           } 
/* 150 */           if ("" == ___uri && "DealershipEmail" == ___local) {
/* 151 */             DEALERSHIPImpl.this.getClass(); spawnSuperClassFromEnterAttribute(new DEALERSHIPTypeImpl.Unmarshaller(DEALERSHIPImpl.this, this.context), 2, ___uri, ___local);
/*     */             return;
/*     */           } 
/* 154 */           if ("" == ___uri && "DealershipLanguage" == ___local) {
/* 155 */             DEALERSHIPImpl.this.getClass(); spawnSuperClassFromEnterAttribute(new DEALERSHIPTypeImpl.Unmarshaller(DEALERSHIPImpl.this, this.context), 2, ___uri, ___local);
/*     */             return;
/*     */           } 
/*     */           break;
/*     */       } 
/* 160 */       super.enterAttribute(___uri, ___local);
/*     */     }
/*     */     
/*     */     public void leaveAttribute(String ___uri, String ___local) throws UnreportedException {
/* 164 */       switch (this.state) {
/*     */         case 3:
/* 166 */           revertToParentFromLeaveAttribute(___uri, ___local);
/*     */           return;
/*     */       } 
/* 169 */       super.leaveAttribute(___uri, ___local);
/*     */     }
/*     */     
/*     */     public void text(String value) throws UnreportedException {
/*     */       try {
/* 174 */         switch (this.state) {
/*     */           case 3:
/* 176 */             revertToParentFromText(value);
/*     */             return;
/*     */         } 
/* 179 */       } catch (RuntimeException e) {
/* 180 */         handleUnexpectedTextException(value, e);
/*     */       } 
/*     */     }
/*     */     
/*     */     public void leaveChild(int nextState) throws UnreportedException {
/* 185 */       switch (nextState) {
/*     */         case 2:
/* 187 */           this.state = 2;
/*     */           return;
/*     */       } 
/* 190 */       super.leaveChild(nextState);
/*     */     }
/*     */ 
/*     */     
/*     */     private void goto1() throws UnreportedException {
/* 195 */       this.state = 1;
/* 196 */       int idx = this.context.getAttribute("", "DealershipEmail");
/* 197 */       if (idx >= 0) {
/* 198 */         this.context.consumeAttribute(idx);
/*     */         return;
/*     */       } 
/* 201 */       idx = this.context.getAttribute("", "DealershipState");
/* 202 */       if (idx >= 0) {
/* 203 */         this.context.consumeAttribute(idx);
/*     */         return;
/*     */       } 
/* 206 */       idx = this.context.getAttribute("", "DealershipFax");
/* 207 */       if (idx >= 0) {
/* 208 */         this.context.consumeAttribute(idx);
/*     */         return;
/*     */       } 
/* 211 */       idx = this.context.getAttribute("", "DealershipLanguage");
/* 212 */       if (idx >= 0) {
/* 213 */         this.context.consumeAttribute(idx);
/*     */         return;
/*     */       } 
/* 216 */       idx = this.context.getAttribute("", "DealershipPhone");
/* 217 */       if (idx >= 0) {
/* 218 */         this.context.consumeAttribute(idx);
/*     */         return;
/*     */       } 
/* 221 */       idx = this.context.getAttribute("", "DealershipCountry");
/* 222 */       if (idx >= 0) {
/* 223 */         this.context.consumeAttribute(idx);
/*     */         return;
/*     */       } 
/* 226 */       idx = this.context.getAttribute("", "DealershipZIP");
/* 227 */       if (idx >= 0) {
/* 228 */         this.context.consumeAttribute(idx);
/*     */         return;
/*     */       } 
/* 231 */       idx = this.context.getAttribute("", "DealershipStreet");
/* 232 */       if (idx >= 0) {
/* 233 */         this.context.consumeAttribute(idx);
/*     */         return;
/*     */       } 
/* 236 */       idx = this.context.getAttribute("", "DealershipName");
/* 237 */       if (idx >= 0) {
/* 238 */         this.context.consumeAttribute(idx);
/*     */         return;
/*     */       } 
/* 241 */       idx = this.context.getAttribute("", "DealershipCode");
/* 242 */       if (idx >= 0) {
/* 243 */         this.context.consumeAttribute(idx);
/*     */         return;
/*     */       } 
/* 246 */       idx = this.context.getAttribute("", "DealershipCity");
/* 247 */       if (idx >= 0) {
/* 248 */         this.context.consumeAttribute(idx);
/*     */         return;
/*     */       } 
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\registration\common\xml\impl\DEALERSHIPImpl.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */