/*     */ package com.eoos.gm.tis2web.registration.common.xml.impl;
/*     */ import com.eoos.gm.tis2web.registration.common.xml.DEALERSHIPType;
/*     */ import com.sun.msv.verifier.DocumentDeclaration;
/*     */ import com.sun.msv.verifier.regexp.REDocumentDeclaration;
/*     */ import com.sun.xml.bind.marshaller.Util;
/*     */ import com.sun.xml.bind.serializer.XMLSerializable;
/*     */ import com.sun.xml.bind.serializer.XMLSerializer;
/*     */ import com.sun.xml.bind.unmarshaller.ContentHandlerEx;
/*     */ import com.sun.xml.bind.unmarshaller.UnmarshallableObject;
/*     */ import com.sun.xml.bind.unmarshaller.UnmarshallingContext;
/*     */ import com.sun.xml.bind.unmarshaller.UnreportedException;
/*     */ import com.sun.xml.bind.util.ListImpl;
/*     */ import com.sun.xml.bind.validator.SchemaDeserializer;
/*     */ import com.sun.xml.bind.validator.ValidatableObject;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import org.xml.sax.Attributes;
/*     */ import org.xml.sax.SAXException;
/*     */ 
/*     */ public class DEALERSHIPTypeImpl implements DEALERSHIPType, UnmarshallableObject, XMLSerializable, ValidatableObject {
/*     */   protected String _DealershipCity;
/*     */   protected String _DealershipName;
/*     */   protected String _DealershipEmail;
/*     */   protected String _DealershipCode;
/*     */   protected String _DealershipZIP;
/*  26 */   protected ListImpl _DEALERSHIPCONTACT = new ListImpl(new ArrayList()); protected String _DealershipLanguage; protected String _DealershipCountry; protected String _DealershipFax; protected String _DealershipStreet; protected String _DealershipPhone; protected String _DealershipState;
/*  27 */   private static final Grammar schemaFragment = SchemaDeserializer.deserialize("¬í\000\005sr\000\037com.sun.msv.grammar.SequenceExp\000\000\000\000\000\000\000\001\002\000\000xr\000\035com.sun.msv.grammar.BinaryExp\000\000\000\000\000\000\000\001\002\000\002L\000\004exp1t\000 Lcom/sun/msv/grammar/Expression;L\000\004exp2q\000~\000\002xr\000\036com.sun.msv.grammar.Expressionø\030èN5~O\002\000\003I\000\016cachedHashCodeL\000\023epsilonReducibilityt\000\023Ljava/lang/Boolean;L\000\013expandedExpq\000~\000\002xp\020`\005/ppsq\000~\000\000\017ºn|ppsq\000~\000\000\r¾\003\003ppsq\000~\000\000\fUûppsq\000~\000\000\013µppsq\000~\000\000\tÖ\bppsq\000~\000\000\bâ°#ppsq\000~\000\000\007±«\017ppsq\000~\000\000\006#.Ïppsq\000~\000\000\005\000§ppsq\000~\000\000\002§äbppsr\000 com.sun.msv.grammar.OneOrMoreExp\000\000\000\000\000\000\000\001\002\000\000xr\000\034com.sun.msv.grammar.UnaryExp\000\000\000\000\000\000\000\001\002\000\001L\000\003expq\000~\000\002xq\000~\000\003\000Ó ªppsr\000\035com.sun.msv.grammar.ChoiceExp\000\000\000\000\000\000\000\001\002\000\000xq\000~\000\001\000Ó §ppsr\000'com.sun.msv.grammar.trex.ElementPattern\000\000\000\000\000\000\000\001\002\000\001L\000\tnameClasst\000\037Lcom/sun/msv/grammar/NameClass;xr\000\036com.sun.msv.grammar.ElementExp\000\000\000\000\000\000\000\001\002\000\002Z\000\032ignoreUndeclaredAttributesL\000\fcontentModelq\000~\000\002xq\000~\000\003\000iÐXpp\000sq\000~\000\025\000iÐMpp\000sq\000~\000\023\000iÐBppsq\000~\000\020\000iÐ7sr\000\021java.lang.BooleanÍ rÕúî\002\000\001Z\000\005valuexp\000psr\000 com.sun.msv.grammar.AttributeExp\000\000\000\000\000\000\000\001\002\000\002L\000\003expq\000~\000\002L\000\tnameClassq\000~\000\026xq\000~\000\003\000iÐ4q\000~\000\035psr\0002com.sun.msv.grammar.Expression$AnyStringExpression\000\000\000\000\000\000\000\001\002\000\000xq\000~\000\003\000\000\000\bsq\000~\000\034\001psr\000 com.sun.msv.grammar.AnyNameClass\000\000\000\000\000\000\000\001\002\000\000xr\000\035com.sun.msv.grammar.NameClass\000\000\000\000\000\000\000\001\002\000\000xpsr\0000com.sun.msv.grammar.Expression$EpsilonExpression\000\000\000\000\000\000\000\001\002\000\000xq\000~\000\003\000\000\000\tq\000~\000\"psr\000#com.sun.msv.grammar.SimpleNameClass\000\000\000\000\000\000\000\001\002\000\002L\000\tlocalNamet\000\022Ljava/lang/String;L\000\fnamespaceURIq\000~\000)xq\000~\000$t\000Acom.eoos.gm.tis2web.registration.common.xml.DEALERSHIPCONTACTTypet\000+http://java.sun.com/jaxb/xjc/dummy-elementssq\000~\000(t\000\022DEALERSHIP_CONTACTt\000\000sq\000~\000\025\000iÐMpp\000sq\000~\000\023\000iÐBppsq\000~\000\020\000iÐ7q\000~\000\035psq\000~\000\036\000iÐ4q\000~\000\035pq\000~\000!q\000~\000%q\000~\000'sq\000~\000(t\000=com.eoos.gm.tis2web.registration.common.xml.DEALERSHIPCONTACTq\000~\000,sq\000~\000\023\001ÔC³ppsq\000~\000\036\001ÔC¨q\000~\000\035psr\000\033com.sun.msv.grammar.DataExp\000\000\000\000\000\000\000\001\002\000\003L\000\002dtt\000\037Lorg/relaxng/datatype/Datatype;L\000\006exceptq\000~\000\002L\000\004namet\000\035Lcom/sun/msv/util/StringPair;xq\000~\000\003\000Ð/ppsr\000#com.sun.msv.datatype.xsd.StringType\000\000\000\000\000\000\000\001\002\000\001Z\000\risAlwaysValidxr\000*com.sun.msv.datatype.xsd.BuiltinAtomicType\000\000\000\000\000\000\000\001\002\000\000xr\000%com.sun.msv.datatype.xsd.ConcreteType\000\000\000\000\000\000\000\001\002\000\000xr\000'com.sun.msv.datatype.xsd.XSDatatypeImpl\000\000\000\000\000\000\000\001\002\000\003L\000\fnamespaceUriq\000~\000)L\000\btypeNameq\000~\000)L\000\nwhiteSpacet\000.Lcom/sun/msv/datatype/xsd/WhiteSpaceProcessor;xpt\000 http://www.w3.org/2001/XMLSchemat\000\006stringsr\000.com.sun.msv.datatype.xsd.WhiteSpaceProcessor$1\023JMoIÛ¤G\002\000\000xr\000,com.sun.msv.datatype.xsd.WhiteSpaceProcessor\000\000\000\000\000\000\000\001\002\000\000xp\001sr\0000com.sun.msv.grammar.Expression$NullSetExpression\000\000\000\000\000\000\000\001\002\000\000xq\000~\000\003\000\000\000\nppsr\000\033com.sun.msv.util.StringPairÐt\036jB \002\000\002L\000\tlocalNameq\000~\000)L\000\fnamespaceURIq\000~\000)xpq\000~\000Cq\000~\000Bsq\000~\000(t\000\017DealershipEmailq\000~\000/q\000~\000'sq\000~\000\023\002XÃ ppsq\000~\000\036\002XÃ\025q\000~\000\035pq\000~\000;sq\000~\000(t\000\017DealershipStateq\000~\000/q\000~\000'sq\000~\000\023\001\"Cppsq\000~\000\036\001\"8q\000~\000\035pq\000~\000;sq\000~\000(t\000\rDealershipFaxq\000~\000/q\000~\000'sq\000~\000\036\001|;ppq\000~\000;sq\000~\000(t\000\022DealershipLanguageq\000~\000/sq\000~\000\023\0011\005\017ppsq\000~\000\036\0011\005\004q\000~\000\035pq\000~\000;sq\000~\000(t\000\017DealershipPhoneq\000~\000/q\000~\000'sq\000~\000\036\000óXWppq\000~\000;sq\000~\000(t\000\021DealershipCountryq\000~\000/sq\000~\000\036\001ß\013ppq\000~\000;sq\000~\000(t\000\rDealershipZIPq\000~\000/sq\000~\000\036\000ýgppq\000~\000;sq\000~\000(t\000\020DealershipStreetq\000~\000/sq\000~\000\036\001hk\003ppq\000~\000;sq\000~\000(t\000\016DealershipNameq\000~\000/sq\000~\000\036\001üktppq\000~\000;sq\000~\000(t\000\016DealershipCodeq\000~\000/sq\000~\000\036\000¥®ppq\000~\000;sq\000~\000(t\000\016DealershipCityq\000~\000/sr\000\"com.sun.msv.grammar.ExpressionPool\000\000\000\000\000\000\000\001\002\000\001L\000\bexpTablet\000/Lcom/sun/msv/grammar/ExpressionPool$ClosedHash;xpsr\000-com.sun.msv.grammar.ExpressionPool$ClosedHash×jÐNïèí\034\002\000\004I\000\005countI\000\tthresholdL\000\006parentq\000~\000o[\000\005tablet\000![Lcom/sun/msv/grammar/Expression;xp\000\000\000\025\000\000\0009pur\000![Lcom.sun.msv.grammar.Expression;Ö8DÃ]­§\n\002\000\000xp\000\000\000¿ppppppppppq\000~\000\tpppppppq\000~\0006ppppppppppppppppppppppq\000~\000Qppppppppppppppppppppppppppppppq\000~\000\fppppppppppppppppppppppppq\000~\000Xpppppppppppppppppq\000~\000\nppppppppppppq\000~\000\007q\000~\000\016pppppppq\000~\000\033q\000~\0002pq\000~\000\024pq\000~\000\005q\000~\000\022ppppq\000~\000\032q\000~\0001q\000~\000Mppppppppppppppq\000~\000\bq\000~\000\017pppq\000~\000\006pppq\000~\000\013q\000~\000\rppppppppppppppp");
/*     */ 
/*     */   
/*     */   private static final Class PRIMARY_INTERFACE_CLASS() {
/*  31 */     return DEALERSHIPType.class;
/*     */   }
/*     */   
/*     */   public String getDealershipCity() {
/*  35 */     return this._DealershipCity;
/*     */   }
/*     */   
/*     */   public void setDealershipCity(String value) {
/*  39 */     this._DealershipCity = value;
/*     */   }
/*     */   
/*     */   public String getDealershipName() {
/*  43 */     return this._DealershipName;
/*     */   }
/*     */   
/*     */   public void setDealershipName(String value) {
/*  47 */     this._DealershipName = value;
/*     */   }
/*     */   
/*     */   public String getDealershipEmail() {
/*  51 */     return this._DealershipEmail;
/*     */   }
/*     */   
/*     */   public void setDealershipEmail(String value) {
/*  55 */     this._DealershipEmail = value;
/*     */   }
/*     */   
/*     */   public String getDealershipCode() {
/*  59 */     return this._DealershipCode;
/*     */   }
/*     */   
/*     */   public void setDealershipCode(String value) {
/*  63 */     this._DealershipCode = value;
/*     */   }
/*     */   
/*     */   public String getDealershipZIP() {
/*  67 */     return this._DealershipZIP;
/*     */   }
/*     */   
/*     */   public void setDealershipZIP(String value) {
/*  71 */     this._DealershipZIP = value;
/*     */   }
/*     */   
/*     */   public String getDealershipLanguage() {
/*  75 */     return this._DealershipLanguage;
/*     */   }
/*     */   
/*     */   public void setDealershipLanguage(String value) {
/*  79 */     this._DealershipLanguage = value;
/*     */   }
/*     */   
/*     */   public String getDealershipCountry() {
/*  83 */     return this._DealershipCountry;
/*     */   }
/*     */   
/*     */   public void setDealershipCountry(String value) {
/*  87 */     this._DealershipCountry = value;
/*     */   }
/*     */   
/*     */   public String getDealershipFax() {
/*  91 */     return this._DealershipFax;
/*     */   }
/*     */   
/*     */   public void setDealershipFax(String value) {
/*  95 */     this._DealershipFax = value;
/*     */   }
/*     */   
/*     */   public String getDealershipStreet() {
/*  99 */     return this._DealershipStreet;
/*     */   }
/*     */   
/*     */   public void setDealershipStreet(String value) {
/* 103 */     this._DealershipStreet = value;
/*     */   }
/*     */   
/*     */   public String getDealershipPhone() {
/* 107 */     return this._DealershipPhone;
/*     */   }
/*     */   
/*     */   public void setDealershipPhone(String value) {
/* 111 */     this._DealershipPhone = value;
/*     */   }
/*     */   
/*     */   public String getDealershipState() {
/* 115 */     return this._DealershipState;
/*     */   }
/*     */   
/*     */   public void setDealershipState(String value) {
/* 119 */     this._DealershipState = value;
/*     */   }
/*     */   
/*     */   public List getDEALERSHIPCONTACT() {
/* 123 */     return (List)this._DEALERSHIPCONTACT;
/*     */   }
/*     */   
/*     */   public ContentHandlerEx getUnmarshaller(UnmarshallingContext context) {
/* 127 */     return new Unmarshaller(context);
/*     */   }
/*     */   
/*     */   public Class getPrimaryInterfaceClass() {
/* 131 */     return PRIMARY_INTERFACE_CLASS();
/*     */   }
/*     */   
/*     */   public void serializeElements(XMLSerializer context) throws SAXException {
/* 135 */     int idx12 = 0;
/* 136 */     int len12 = this._DEALERSHIPCONTACT.size();
/* 137 */     while (idx12 != len12) {
/* 138 */       if (this._DEALERSHIPCONTACT.get(idx12) instanceof javax.xml.bind.Element) {
/* 139 */         context.childAsElements((XMLSerializable)this._DEALERSHIPCONTACT.get(idx12++)); continue;
/*     */       } 
/* 141 */       context.startElement("", "DEALERSHIP_CONTACT");
/* 142 */       int idx_0 = idx12;
/* 143 */       context.childAsAttributes((XMLSerializable)this._DEALERSHIPCONTACT.get(idx_0++));
/* 144 */       context.endAttributes();
/* 145 */       context.childAsElements((XMLSerializable)this._DEALERSHIPCONTACT.get(idx12++));
/* 146 */       context.endElement();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void serializeAttributes(XMLSerializer context) throws SAXException {
/* 152 */     this._DEALERSHIPCONTACT.size();
/* 153 */     if (this._DealershipEmail != null) {
/* 154 */       context.startAttribute("", "DealershipEmail");
/*     */       try {
/* 156 */         context.text(this._DealershipEmail);
/* 157 */       } catch (Exception e) {
/* 158 */         Util.handlePrintConversionException(this, e, context);
/*     */       } 
/* 160 */       context.endAttribute();
/*     */     } 
/* 162 */     if (this._DealershipState != null) {
/* 163 */       context.startAttribute("", "DealershipState");
/*     */       try {
/* 165 */         context.text(this._DealershipState);
/* 166 */       } catch (Exception e) {
/* 167 */         Util.handlePrintConversionException(this, e, context);
/*     */       } 
/* 169 */       context.endAttribute();
/*     */     } 
/* 171 */     if (this._DealershipFax != null) {
/* 172 */       context.startAttribute("", "DealershipFax");
/*     */       try {
/* 174 */         context.text(this._DealershipFax);
/* 175 */       } catch (Exception e) {
/* 176 */         Util.handlePrintConversionException(this, e, context);
/*     */       } 
/* 178 */       context.endAttribute();
/*     */     } 
/* 180 */     context.startAttribute("", "DealershipLanguage");
/*     */     try {
/* 182 */       context.text(this._DealershipLanguage);
/* 183 */     } catch (Exception e) {
/* 184 */       Util.handlePrintConversionException(this, e, context);
/*     */     } 
/* 186 */     context.endAttribute();
/* 187 */     if (this._DealershipPhone != null) {
/* 188 */       context.startAttribute("", "DealershipPhone");
/*     */       try {
/* 190 */         context.text(this._DealershipPhone);
/* 191 */       } catch (Exception e) {
/* 192 */         Util.handlePrintConversionException(this, e, context);
/*     */       } 
/* 194 */       context.endAttribute();
/*     */     } 
/* 196 */     context.startAttribute("", "DealershipCountry");
/*     */     try {
/* 198 */       context.text(this._DealershipCountry);
/* 199 */     } catch (Exception e) {
/* 200 */       Util.handlePrintConversionException(this, e, context);
/*     */     } 
/* 202 */     context.endAttribute();
/* 203 */     context.startAttribute("", "DealershipZIP");
/*     */     try {
/* 205 */       context.text(this._DealershipZIP);
/* 206 */     } catch (Exception e) {
/* 207 */       Util.handlePrintConversionException(this, e, context);
/*     */     } 
/* 209 */     context.endAttribute();
/* 210 */     context.startAttribute("", "DealershipStreet");
/*     */     try {
/* 212 */       context.text(this._DealershipStreet);
/* 213 */     } catch (Exception e) {
/* 214 */       Util.handlePrintConversionException(this, e, context);
/*     */     } 
/* 216 */     context.endAttribute();
/* 217 */     context.startAttribute("", "DealershipName");
/*     */     try {
/* 219 */       context.text(this._DealershipName);
/* 220 */     } catch (Exception e) {
/* 221 */       Util.handlePrintConversionException(this, e, context);
/*     */     } 
/* 223 */     context.endAttribute();
/* 224 */     context.startAttribute("", "DealershipCode");
/*     */     try {
/* 226 */       context.text(this._DealershipCode);
/* 227 */     } catch (Exception e) {
/* 228 */       Util.handlePrintConversionException(this, e, context);
/*     */     } 
/* 230 */     context.endAttribute();
/* 231 */     context.startAttribute("", "DealershipCity");
/*     */     try {
/* 233 */       context.text(this._DealershipCity);
/* 234 */     } catch (Exception e) {
/* 235 */       Util.handlePrintConversionException(this, e, context);
/*     */     } 
/* 237 */     context.endAttribute();
/*     */   }
/*     */   
/*     */   public void serializeAttributeBodies(XMLSerializer context) throws SAXException {
/* 241 */     this._DEALERSHIPCONTACT.size();
/*     */   }
/*     */   
/*     */   public Class getPrimaryInterface() {
/* 245 */     return DEALERSHIPType.class;
/*     */   }
/*     */   
/*     */   public DocumentDeclaration createRawValidator() {
/* 249 */     return (DocumentDeclaration)new REDocumentDeclaration(schemaFragment);
/*     */   }
/*     */   
/*     */   public class Unmarshaller
/*     */     extends ContentHandlerEx {
/*     */     public Unmarshaller(UnmarshallingContext context) {
/* 255 */       super(context, "--------------------------");
/*     */     }
/*     */     
/*     */     protected UnmarshallableObject owner() {
/* 259 */       return DEALERSHIPTypeImpl.this;
/*     */     }
/*     */     
/*     */     public void enterElement(String ___uri, String ___local, Attributes __atts) throws UnreportedException {
/* 263 */       switch (this.state) {
/*     */         case 0:
/* 265 */           if ("" == ___uri && "DEALERSHIP_CONTACT" == ___local) {
/* 266 */             this.context.pushAttributes(__atts);
/* 267 */             goto13();
/*     */             return;
/*     */           } 
/*     */           break;
/*     */         case 15:
/* 272 */           if ("" == ___uri && "DEALERSHIP_CONTACT" == ___local) {
/* 273 */             this.context.pushAttributes(__atts);
/* 274 */             goto13();
/*     */             return;
/*     */           } 
/* 277 */           revertToParentFromEnterElement(___uri, ___local, __atts);
/*     */           return;
/*     */       } 
/* 280 */       super.enterElement(___uri, ___local, __atts);
/*     */     }
/*     */     
/*     */     public void leaveElement(String ___uri, String ___local) throws UnreportedException {
/* 284 */       switch (this.state) {
/*     */         case 14:
/* 286 */           if ("" == ___uri && "DEALERSHIP_CONTACT" == ___local) {
/* 287 */             this.context.popAttributes();
/* 288 */             this.state = 15;
/*     */             return;
/*     */           } 
/*     */           break;
/*     */         case 15:
/* 293 */           revertToParentFromLeaveElement(___uri, ___local);
/*     */           return;
/*     */       } 
/* 296 */       super.leaveElement(___uri, ___local);
/*     */     }
/*     */     
/*     */     public void enterAttribute(String ___uri, String ___local) throws UnreportedException {
/* 300 */       switch (this.state) {
/*     */         case 0:
/* 302 */           if ("" == ___uri && "DealershipZIP" == ___local) {
/* 303 */             this.state = 9;
/*     */             return;
/*     */           } 
/* 306 */           if ("" == ___uri && "DealershipCountry" == ___local) {
/* 307 */             this.state = 5;
/*     */             return;
/*     */           } 
/* 310 */           if ("" == ___uri && "DealershipCity" == ___local) {
/* 311 */             this.state = 1;
/*     */             return;
/*     */           } 
/* 314 */           if ("" == ___uri && "DealershipName" == ___local) {
/* 315 */             this.state = 20;
/*     */             return;
/*     */           } 
/* 318 */           if ("" == ___uri && "DealershipState" == ___local) {
/* 319 */             this.state = 22;
/*     */             return;
/*     */           } 
/* 322 */           if ("" == ___uri && "DealershipStreet" == ___local) {
/* 323 */             this.state = 11;
/*     */             return;
/*     */           } 
/* 326 */           if ("" == ___uri && "DealershipFax" == ___local) {
/* 327 */             this.state = 24;
/*     */             return;
/*     */           } 
/* 330 */           if ("" == ___uri && "DealershipPhone" == ___local) {
/* 331 */             this.state = 3;
/*     */             return;
/*     */           } 
/* 334 */           if ("" == ___uri && "DealershipEmail" == ___local) {
/* 335 */             this.state = 18;
/*     */             return;
/*     */           } 
/* 338 */           if ("" == ___uri && "DealershipCode" == ___local) {
/* 339 */             this.state = 16;
/*     */             return;
/*     */           } 
/* 342 */           if ("" == ___uri && "DealershipLanguage" == ___local) {
/* 343 */             this.state = 7;
/*     */             return;
/*     */           } 
/*     */           break;
/*     */         case 13:
/* 348 */           if ("" == ___uri && "ContactName" == ___local) {
/* 349 */             DEALERSHIPTypeImpl.this._DEALERSHIPCONTACT.add(spawnChildFromEnterAttribute(DEALERSHIPCONTACTTypeImpl.class, 14, ___uri, ___local));
/*     */             return;
/*     */           } 
/* 352 */           if ("" == ___uri && "ContactLanguage" == ___local) {
/* 353 */             DEALERSHIPTypeImpl.this._DEALERSHIPCONTACT.add(spawnChildFromEnterAttribute(DEALERSHIPCONTACTTypeImpl.class, 14, ___uri, ___local));
/*     */             return;
/*     */           } 
/*     */           break;
/*     */         case 15:
/* 358 */           revertToParentFromEnterAttribute(___uri, ___local);
/*     */           return;
/*     */       } 
/* 361 */       super.enterAttribute(___uri, ___local);
/*     */     }
/*     */     
/*     */     public void leaveAttribute(String ___uri, String ___local) throws UnreportedException {
/* 365 */       switch (this.state) {
/*     */         case 17:
/* 367 */           if ("" == ___uri && "DealershipCode" == ___local) {
/* 368 */             goto0();
/*     */             return;
/*     */           } 
/*     */           break;
/*     */         case 25:
/* 373 */           if ("" == ___uri && "DealershipFax" == ___local) {
/* 374 */             goto0();
/*     */             return;
/*     */           } 
/*     */           break;
/*     */         case 10:
/* 379 */           if ("" == ___uri && "DealershipZIP" == ___local) {
/* 380 */             goto0();
/*     */             return;
/*     */           } 
/*     */           break;
/*     */         case 19:
/* 385 */           if ("" == ___uri && "DealershipEmail" == ___local) {
/* 386 */             goto0();
/*     */             return;
/*     */           } 
/*     */           break;
/*     */         case 8:
/* 391 */           if ("" == ___uri && "DealershipLanguage" == ___local) {
/* 392 */             goto0();
/*     */             return;
/*     */           } 
/*     */           break;
/*     */         case 6:
/* 397 */           if ("" == ___uri && "DealershipCountry" == ___local) {
/* 398 */             goto0();
/*     */             return;
/*     */           } 
/*     */           break;
/*     */         case 4:
/* 403 */           if ("" == ___uri && "DealershipPhone" == ___local) {
/* 404 */             goto0();
/*     */             return;
/*     */           } 
/*     */           break;
/*     */         case 15:
/* 409 */           revertToParentFromLeaveAttribute(___uri, ___local);
/*     */           return;
/*     */         case 21:
/* 412 */           if ("" == ___uri && "DealershipName" == ___local) {
/* 413 */             goto0();
/*     */             return;
/*     */           } 
/*     */           break;
/*     */         case 12:
/* 418 */           if ("" == ___uri && "DealershipStreet" == ___local) {
/* 419 */             goto0();
/*     */             return;
/*     */           } 
/*     */           break;
/*     */         case 2:
/* 424 */           if ("" == ___uri && "DealershipCity" == ___local) {
/* 425 */             goto0();
/*     */             return;
/*     */           } 
/*     */           break;
/*     */         case 23:
/* 430 */           if ("" == ___uri && "DealershipState" == ___local) {
/* 431 */             goto0();
/*     */             return;
/*     */           } 
/*     */           break;
/*     */       } 
/* 436 */       super.leaveAttribute(___uri, ___local);
/*     */     }
/*     */     
/*     */     public void text(String value) throws UnreportedException {
/*     */       try {
/* 441 */         switch (this.state) {
/*     */           case 9:
/*     */             try {
/* 444 */               DEALERSHIPTypeImpl.this._DealershipZIP = value;
/* 445 */             } catch (Exception e) {
/* 446 */               handleParseConversionException(e);
/*     */             } 
/* 448 */             this.state = 10;
/*     */             return;
/*     */           case 7:
/*     */             try {
/* 452 */               DEALERSHIPTypeImpl.this._DealershipLanguage = value;
/* 453 */             } catch (Exception e) {
/* 454 */               handleParseConversionException(e);
/*     */             } 
/* 456 */             this.state = 8;
/*     */             return;
/*     */           case 16:
/*     */             try {
/* 460 */               DEALERSHIPTypeImpl.this._DealershipCode = value;
/* 461 */             } catch (Exception e) {
/* 462 */               handleParseConversionException(e);
/*     */             } 
/* 464 */             this.state = 17;
/*     */             return;
/*     */           case 15:
/* 467 */             revertToParentFromText(value);
/*     */             return;
/*     */           case 3:
/*     */             try {
/* 471 */               DEALERSHIPTypeImpl.this._DealershipPhone = value;
/* 472 */             } catch (Exception e) {
/* 473 */               handleParseConversionException(e);
/*     */             } 
/* 475 */             this.state = 4;
/*     */             return;
/*     */           case 18:
/*     */             try {
/* 479 */               DEALERSHIPTypeImpl.this._DealershipEmail = value;
/* 480 */             } catch (Exception e) {
/* 481 */               handleParseConversionException(e);
/*     */             } 
/* 483 */             this.state = 19;
/*     */             return;
/*     */           case 20:
/*     */             try {
/* 487 */               DEALERSHIPTypeImpl.this._DealershipName = value;
/* 488 */             } catch (Exception e) {
/* 489 */               handleParseConversionException(e);
/*     */             } 
/* 491 */             this.state = 21;
/*     */             return;
/*     */           case 11:
/*     */             try {
/* 495 */               DEALERSHIPTypeImpl.this._DealershipStreet = value;
/* 496 */             } catch (Exception e) {
/* 497 */               handleParseConversionException(e);
/*     */             } 
/* 499 */             this.state = 12;
/*     */             return;
/*     */           case 24:
/*     */             try {
/* 503 */               DEALERSHIPTypeImpl.this._DealershipFax = value;
/* 504 */             } catch (Exception e) {
/* 505 */               handleParseConversionException(e);
/*     */             } 
/* 507 */             this.state = 25;
/*     */             return;
/*     */           case 5:
/*     */             try {
/* 511 */               DEALERSHIPTypeImpl.this._DealershipCountry = value;
/* 512 */             } catch (Exception e) {
/* 513 */               handleParseConversionException(e);
/*     */             } 
/* 515 */             this.state = 6;
/*     */             return;
/*     */           case 1:
/*     */             try {
/* 519 */               DEALERSHIPTypeImpl.this._DealershipCity = value;
/* 520 */             } catch (Exception e) {
/* 521 */               handleParseConversionException(e);
/*     */             } 
/* 523 */             this.state = 2;
/*     */             return;
/*     */           case 22:
/*     */             try {
/* 527 */               DEALERSHIPTypeImpl.this._DealershipState = value;
/* 528 */             } catch (Exception e) {
/* 529 */               handleParseConversionException(e);
/*     */             } 
/* 531 */             this.state = 23;
/*     */             return;
/*     */         } 
/* 534 */       } catch (RuntimeException e) {
/* 535 */         handleUnexpectedTextException(value, e);
/*     */       } 
/*     */     }
/*     */     
/*     */     public void leaveChild(int nextState) throws UnreportedException {
/* 540 */       switch (nextState) {
/*     */         case 14:
/* 542 */           this.state = 14;
/*     */           return;
/*     */       } 
/* 545 */       super.leaveChild(nextState);
/*     */     }
/*     */ 
/*     */     
/*     */     private void goto0() throws UnreportedException {
/* 550 */       this.state = 0;
/* 551 */       int idx = this.context.getAttribute("", "DealershipEmail");
/* 552 */       if (idx >= 0) {
/* 553 */         this.context.consumeAttribute(idx);
/*     */         return;
/*     */       } 
/* 556 */       idx = this.context.getAttribute("", "DealershipState");
/* 557 */       if (idx >= 0) {
/* 558 */         this.context.consumeAttribute(idx);
/*     */         return;
/*     */       } 
/* 561 */       idx = this.context.getAttribute("", "DealershipFax");
/* 562 */       if (idx >= 0) {
/* 563 */         this.context.consumeAttribute(idx);
/*     */         return;
/*     */       } 
/* 566 */       idx = this.context.getAttribute("", "DealershipLanguage");
/* 567 */       if (idx >= 0) {
/* 568 */         this.context.consumeAttribute(idx);
/*     */         return;
/*     */       } 
/* 571 */       idx = this.context.getAttribute("", "DealershipPhone");
/* 572 */       if (idx >= 0) {
/* 573 */         this.context.consumeAttribute(idx);
/*     */         return;
/*     */       } 
/* 576 */       idx = this.context.getAttribute("", "DealershipCountry");
/* 577 */       if (idx >= 0) {
/* 578 */         this.context.consumeAttribute(idx);
/*     */         return;
/*     */       } 
/* 581 */       idx = this.context.getAttribute("", "DealershipZIP");
/* 582 */       if (idx >= 0) {
/* 583 */         this.context.consumeAttribute(idx);
/*     */         return;
/*     */       } 
/* 586 */       idx = this.context.getAttribute("", "DealershipStreet");
/* 587 */       if (idx >= 0) {
/* 588 */         this.context.consumeAttribute(idx);
/*     */         return;
/*     */       } 
/* 591 */       idx = this.context.getAttribute("", "DealershipName");
/* 592 */       if (idx >= 0) {
/* 593 */         this.context.consumeAttribute(idx);
/*     */         return;
/*     */       } 
/* 596 */       idx = this.context.getAttribute("", "DealershipCode");
/* 597 */       if (idx >= 0) {
/* 598 */         this.context.consumeAttribute(idx);
/*     */         return;
/*     */       } 
/* 601 */       idx = this.context.getAttribute("", "DealershipCity");
/* 602 */       if (idx >= 0) {
/* 603 */         this.context.consumeAttribute(idx);
/*     */         return;
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     private void goto13() throws UnreportedException {
/* 610 */       this.state = 13;
/* 611 */       int idx = this.context.getAttribute("", "ContactName");
/* 612 */       if (idx >= 0) {
/* 613 */         this.context.consumeAttribute(idx);
/*     */         return;
/*     */       } 
/* 616 */       idx = this.context.getAttribute("", "ContactLanguage");
/* 617 */       if (idx >= 0) {
/* 618 */         this.context.consumeAttribute(idx);
/*     */         return;
/*     */       } 
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\registration\common\xml\impl\DEALERSHIPTypeImpl.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */