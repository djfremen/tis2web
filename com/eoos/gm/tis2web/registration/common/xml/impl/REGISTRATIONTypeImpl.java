/*     */ package com.eoos.gm.tis2web.registration.common.xml.impl;
/*     */ import com.eoos.gm.tis2web.registration.common.xml.DEALERSHIPType;
/*     */ import com.eoos.gm.tis2web.registration.common.xml.LICENSEKEYType;
/*     */ import com.eoos.gm.tis2web.registration.common.xml.REGISTRATIONType;
/*     */ import com.eoos.gm.tis2web.registration.common.xml.SOFTWAREKEYType;
/*     */ import com.sun.msv.verifier.DocumentDeclaration;
/*     */ import com.sun.xml.bind.marshaller.Util;
/*     */ import com.sun.xml.bind.serializer.XMLSerializable;
/*     */ import com.sun.xml.bind.serializer.XMLSerializer;
/*     */ import com.sun.xml.bind.unmarshaller.ContentHandlerEx;
/*     */ import com.sun.xml.bind.unmarshaller.UnmarshallingContext;
/*     */ import com.sun.xml.bind.unmarshaller.UnreportedException;
/*     */ import com.sun.xml.bind.util.ListImpl;
/*     */ import org.xml.sax.Attributes;
/*     */ import org.xml.sax.SAXException;
/*     */ 
/*     */ public class REGISTRATIONTypeImpl implements REGISTRATIONType, UnmarshallableObject, XMLSerializable, ValidatableObject {
/*     */   protected String _SubscriberID;
/*     */   protected String _LicensedSessions;
/*     */   protected SOFTWAREKEYType _SOFTWAREKEY;
/*  21 */   protected ListImpl _SUBSCRIPTION = new ListImpl(new ArrayList()); protected DEALERSHIPType _DEALERSHIP; protected String _Version; protected String _RequestID;
/*     */   protected LICENSEKEYType _LICENSEKEY;
/*     */   protected String _RequestDate;
/*  24 */   private static final Grammar schemaFragment = SchemaDeserializer.deserialize("¬í\000\005sr\000\037com.sun.msv.grammar.SequenceExp\000\000\000\000\000\000\000\001\002\000\000xr\000\035com.sun.msv.grammar.BinaryExp\000\000\000\000\000\000\000\001\002\000\002L\000\004exp1t\000 Lcom/sun/msv/grammar/Expression;L\000\004exp2q\000~\000\002xr\000\036com.sun.msv.grammar.Expressionø\030èN5~O\002\000\003I\000\016cachedHashCodeL\000\023epsilonReducibilityt\000\023Ljava/lang/Boolean;L\000\013expandedExpq\000~\000\002xp\t(\t~ppsq\000~\000\000\bmðppsq\000~\000\000\007Ö¥ppsq\000~\000\000\006c8ppsq\000~\000\000\005P®ppsq\000~\000\000\003NÄppsq\000~\000\000\002zâ\025ppsq\000~\000\000\001§A^ppsr\000\035com.sun.msv.grammar.ChoiceExp\000\000\000\000\000\000\000\001\002\000\000xq\000~\000\001\000Ó §ppsr\000'com.sun.msv.grammar.trex.ElementPattern\000\000\000\000\000\000\000\001\002\000\001L\000\tnameClasst\000\037Lcom/sun/msv/grammar/NameClass;xr\000\036com.sun.msv.grammar.ElementExp\000\000\000\000\000\000\000\001\002\000\002Z\000\032ignoreUndeclaredAttributesL\000\fcontentModelq\000~\000\002xq\000~\000\003\000iÐXpp\000sq\000~\000\017\000iÐMpp\000sq\000~\000\r\000iÐBppsr\000 com.sun.msv.grammar.OneOrMoreExp\000\000\000\000\000\000\000\001\002\000\000xr\000\034com.sun.msv.grammar.UnaryExp\000\000\000\000\000\000\000\001\002\000\001L\000\003expq\000~\000\002xq\000~\000\003\000iÐ7sr\000\021java.lang.BooleanÍ rÕúî\002\000\001Z\000\005valuexp\000psr\000 com.sun.msv.grammar.AttributeExp\000\000\000\000\000\000\000\001\002\000\002L\000\003expq\000~\000\002L\000\tnameClassq\000~\000\020xq\000~\000\003\000iÐ4q\000~\000\031psr\0002com.sun.msv.grammar.Expression$AnyStringExpression\000\000\000\000\000\000\000\001\002\000\000xq\000~\000\003\000\000\000\bsq\000~\000\030\001psr\000 com.sun.msv.grammar.AnyNameClass\000\000\000\000\000\000\000\001\002\000\000xr\000\035com.sun.msv.grammar.NameClass\000\000\000\000\000\000\000\001\002\000\000xpsr\0000com.sun.msv.grammar.Expression$EpsilonExpression\000\000\000\000\000\000\000\001\002\000\000xq\000~\000\003\000\000\000\tq\000~\000\036psr\000#com.sun.msv.grammar.SimpleNameClass\000\000\000\000\000\000\000\001\002\000\002L\000\tlocalNamet\000\022Ljava/lang/String;L\000\fnamespaceURIq\000~\000%xq\000~\000 t\000:com.eoos.gm.tis2web.registration.common.xml.DEALERSHIPTypet\000+http://java.sun.com/jaxb/xjc/dummy-elementssq\000~\000$t\000\nDEALERSHIPt\000\000sq\000~\000\017\000iÐMpp\000sq\000~\000\r\000iÐBppsq\000~\000\025\000iÐ7q\000~\000\031psq\000~\000\032\000iÐ4q\000~\000\031pq\000~\000\035q\000~\000!q\000~\000#sq\000~\000$t\0006com.eoos.gm.tis2web.registration.common.xml.DEALERSHIPq\000~\000(sq\000~\000\r\000Ó ²ppsq\000~\000\r\000Ó §q\000~\000\031psq\000~\000\017\000iÐXq\000~\000\031p\000sq\000~\000\017\000iÐMpp\000sq\000~\000\r\000iÐBppsq\000~\000\025\000iÐ7q\000~\000\031psq\000~\000\032\000iÐ4q\000~\000\031pq\000~\000\035q\000~\000!q\000~\000#sq\000~\000$t\000;com.eoos.gm.tis2web.registration.common.xml.SOFTWAREKEYTypeq\000~\000(sq\000~\000$t\000\fSOFTWARE_KEYq\000~\000+sq\000~\000\017\000iÐMq\000~\000\031p\000sq\000~\000\r\000iÐBppsq\000~\000\025\000iÐ7q\000~\000\031psq\000~\000\032\000iÐ4q\000~\000\031pq\000~\000\035q\000~\000!q\000~\000#sq\000~\000$t\0007com.eoos.gm.tis2web.registration.common.xml.SOFTWAREKEYq\000~\000(q\000~\000#sq\000~\000\r\000Ó ²ppsq\000~\000\r\000Ó §q\000~\000\031psq\000~\000\017\000iÐXq\000~\000\031p\000sq\000~\000\017\000iÐMpp\000sq\000~\000\r\000iÐBppsq\000~\000\025\000iÐ7q\000~\000\031psq\000~\000\032\000iÐ4q\000~\000\031pq\000~\000\035q\000~\000!q\000~\000#sq\000~\000$t\000:com.eoos.gm.tis2web.registration.common.xml.LICENSEKEYTypeq\000~\000(sq\000~\000$t\000\013LICENSE_KEYq\000~\000+sq\000~\000\017\000iÐMq\000~\000\031p\000sq\000~\000\r\000iÐBppsq\000~\000\025\000iÐ7q\000~\000\031psq\000~\000\032\000iÐ4q\000~\000\031pq\000~\000\035q\000~\000!q\000~\000#sq\000~\000$t\0006com.eoos.gm.tis2web.registration.common.xml.LICENSEKEYq\000~\000(q\000~\000#sq\000~\000\025\000Ó ªppsq\000~\000\r\000Ó §ppsq\000~\000\017\000iÐXpp\000sq\000~\000\017\000iÐMpp\000sq\000~\000\r\000iÐBppsq\000~\000\025\000iÐ7q\000~\000\031psq\000~\000\032\000iÐ4q\000~\000\031pq\000~\000\035q\000~\000!q\000~\000#sq\000~\000$t\000<com.eoos.gm.tis2web.registration.common.xml.SUBSCRIPTIONTypeq\000~\000(sq\000~\000$t\000\fSUBSCRIPTIONq\000~\000+sq\000~\000\017\000iÐMpp\000sq\000~\000\r\000iÐBppsq\000~\000\025\000iÐ7q\000~\000\031psq\000~\000\032\000iÐ4q\000~\000\031pq\000~\000\035q\000~\000!q\000~\000#sq\000~\000$t\0008com.eoos.gm.tis2web.registration.common.xml.SUBSCRIPTIONq\000~\000(sq\000~\000\032\002\002\båppsr\000\033com.sun.msv.grammar.DataExp\000\000\000\000\000\000\000\001\002\000\003L\000\002dtt\000\037Lorg/relaxng/datatype/Datatype;L\000\006exceptq\000~\000\002L\000\004namet\000\035Lcom/sun/msv/util/StringPair;xq\000~\000\003\000Ð/ppsr\000#com.sun.msv.datatype.xsd.StringType\000\000\000\000\000\000\000\001\002\000\001Z\000\risAlwaysValidxr\000*com.sun.msv.datatype.xsd.BuiltinAtomicType\000\000\000\000\000\000\000\001\002\000\000xr\000%com.sun.msv.datatype.xsd.ConcreteType\000\000\000\000\000\000\000\001\002\000\000xr\000'com.sun.msv.datatype.xsd.XSDatatypeImpl\000\000\000\000\000\000\000\001\002\000\003L\000\fnamespaceUriq\000~\000%L\000\btypeNameq\000~\000%L\000\nwhiteSpacet\000.Lcom/sun/msv/datatype/xsd/WhiteSpaceProcessor;xpt\000 http://www.w3.org/2001/XMLSchemat\000\006stringsr\000.com.sun.msv.datatype.xsd.WhiteSpaceProcessor$1\023JMoIÛ¤G\002\000\000xr\000,com.sun.msv.datatype.xsd.WhiteSpaceProcessor\000\000\000\000\000\000\000\001\002\000\000xp\001sr\0000com.sun.msv.grammar.Expression$NullSetExpression\000\000\000\000\000\000\000\001\002\000\000xq\000~\000\003\000\000\000\nppsr\000\033com.sun.msv.util.StringPairÐt\036jB \002\000\002L\000\tlocalNameq\000~\000%L\000\fnamespaceURIq\000~\000%xpq\000~\000qq\000~\000psq\000~\000$t\000\013RequestDateq\000~\000+sq\000~\000\r\001H×ppsq\000~\000\032\001H×zq\000~\000\031pq\000~\000isq\000~\000$t\000\fSubscriberIDq\000~\000+q\000~\000#sq\000~\000\032\000íshppq\000~\000isq\000~\000$t\000\tRequestIDq\000~\000+sq\000~\000\r\000æ·Fppsq\000~\000\032\000æ·;q\000~\000\031pq\000~\000isq\000~\000$t\000\020LicensedSessionsq\000~\000+q\000~\000#sq\000~\000\r\000º{ppsq\000~\000\032\000º{~q\000~\000\031psr\000\034com.sun.msv.grammar.ValueExp\000\000\000\000\000\000\000\001\002\000\003L\000\002dtq\000~\000gL\000\004nameq\000~\000hL\000\005valuet\000\022Ljava/lang/Object;xq\000~\000\003\000Ð&ppq\000~\000oq\000~\000xt\000\0031.0sq\000~\000$t\000\007Versionq\000~\000+q\000~\000#sr\000\"com.sun.msv.grammar.ExpressionPool\000\000\000\000\000\000\000\001\002\000\001L\000\bexpTablet\000/Lcom/sun/msv/grammar/ExpressionPool$ClosedHash;xpsr\000-com.sun.msv.grammar.ExpressionPool$ClosedHash×jÐNïèí\034\002\000\004I\000\005countI\000\tthresholdL\000\006parentq\000~\000[\000\005tablet\000![Lcom/sun/msv/grammar/Expression;xp\000\000\000\"\000\000\0009pur\000![Lcom.sun.msv.grammar.Expression;Ö8DÃ]­§\n\002\000\000xp\000\000\000¿pppppppppppppppppppppppppppq\000~\000\npppppppppppppppppppppppppppppppppppppq\000~\000pppq\000~\000{q\000~\000\013q\000~\000\bpppppppppppppppppppppppppppppppppq\000~\000\fppppppppppppq\000~\000\007ppppppppppppppppppq\000~\000\027q\000~\000.q\000~\0007q\000~\000\016q\000~\000?q\000~\0003q\000~\000Hq\000~\000Pq\000~\000Dq\000~\000Yq\000~\000aq\000~\000\024q\000~\000-q\000~\0006q\000~\000>q\000~\0002q\000~\000Gq\000~\000Oq\000~\000Cq\000~\000Xq\000~\000`q\000~\000Uq\000~\000Tppppppppppppppppppppppppppq\000~\000pq\000~\000\tq\000~\000\006q\000~\000\005");
/*     */ 
/*     */   
/*     */   private static final Class PRIMARY_INTERFACE_CLASS() {
/*  28 */     return REGISTRATIONType.class;
/*     */   }
/*     */   
/*     */   public String getSubscriberID() {
/*  32 */     return this._SubscriberID;
/*     */   }
/*     */   
/*     */   public void setSubscriberID(String value) {
/*  36 */     this._SubscriberID = value;
/*     */   }
/*     */   
/*     */   public String getLicensedSessions() {
/*  40 */     return this._LicensedSessions;
/*     */   }
/*     */   
/*     */   public void setLicensedSessions(String value) {
/*  44 */     this._LicensedSessions = value;
/*     */   }
/*     */   
/*     */   public SOFTWAREKEYType getSOFTWAREKEY() {
/*  48 */     return this._SOFTWAREKEY;
/*     */   }
/*     */   
/*     */   public void setSOFTWAREKEY(SOFTWAREKEYType value) {
/*  52 */     this._SOFTWAREKEY = value;
/*     */   }
/*     */   
/*     */   public DEALERSHIPType getDEALERSHIP() {
/*  56 */     return this._DEALERSHIP;
/*     */   }
/*     */   
/*     */   public void setDEALERSHIP(DEALERSHIPType value) {
/*  60 */     this._DEALERSHIP = value;
/*     */   }
/*     */   
/*     */   public String getVersion() {
/*  64 */     return this._Version;
/*     */   }
/*     */   
/*     */   public void setVersion(String value) {
/*  68 */     this._Version = value;
/*     */   }
/*     */   
/*     */   public String getRequestID() {
/*  72 */     return this._RequestID;
/*     */   }
/*     */   
/*     */   public void setRequestID(String value) {
/*  76 */     this._RequestID = value;
/*     */   }
/*     */   
/*     */   public List getSUBSCRIPTION() {
/*  80 */     return (List)this._SUBSCRIPTION;
/*     */   }
/*     */   
/*     */   public LICENSEKEYType getLICENSEKEY() {
/*  84 */     return this._LICENSEKEY;
/*     */   }
/*     */   
/*     */   public void setLICENSEKEY(LICENSEKEYType value) {
/*  88 */     this._LICENSEKEY = value;
/*     */   }
/*     */   
/*     */   public String getRequestDate() {
/*  92 */     return this._RequestDate;
/*     */   }
/*     */   
/*     */   public void setRequestDate(String value) {
/*  96 */     this._RequestDate = value;
/*     */   }
/*     */   
/*     */   public ContentHandlerEx getUnmarshaller(UnmarshallingContext context) {
/* 100 */     return new Unmarshaller(context);
/*     */   }
/*     */   
/*     */   public Class getPrimaryInterfaceClass() {
/* 104 */     return PRIMARY_INTERFACE_CLASS();
/*     */   }
/*     */   
/*     */   public void serializeElements(XMLSerializer context) throws SAXException {
/* 108 */     int idx7 = 0;
/* 109 */     int len7 = this._SUBSCRIPTION.size();
/* 110 */     if (this._DEALERSHIP instanceof javax.xml.bind.Element) {
/* 111 */       context.childAsElements((XMLSerializable)this._DEALERSHIP);
/*     */     } else {
/* 113 */       context.startElement("", "DEALERSHIP");
/* 114 */       context.childAsAttributes((XMLSerializable)this._DEALERSHIP);
/* 115 */       context.endAttributes();
/* 116 */       context.childAsElements((XMLSerializable)this._DEALERSHIP);
/* 117 */       context.endElement();
/*     */     } 
/* 119 */     if (this._SOFTWAREKEY != null) {
/* 120 */       if (this._SOFTWAREKEY instanceof javax.xml.bind.Element) {
/* 121 */         context.childAsElements((XMLSerializable)this._SOFTWAREKEY);
/*     */       } else {
/* 123 */         context.startElement("", "SOFTWARE_KEY");
/* 124 */         context.childAsAttributes((XMLSerializable)this._SOFTWAREKEY);
/* 125 */         context.endAttributes();
/* 126 */         context.childAsElements((XMLSerializable)this._SOFTWAREKEY);
/* 127 */         context.endElement();
/*     */       } 
/*     */     }
/* 130 */     if (this._LICENSEKEY != null) {
/* 131 */       if (this._LICENSEKEY instanceof javax.xml.bind.Element) {
/* 132 */         context.childAsElements((XMLSerializable)this._LICENSEKEY);
/*     */       } else {
/* 134 */         context.startElement("", "LICENSE_KEY");
/* 135 */         context.childAsAttributes((XMLSerializable)this._LICENSEKEY);
/* 136 */         context.endAttributes();
/* 137 */         context.childAsElements((XMLSerializable)this._LICENSEKEY);
/* 138 */         context.endElement();
/*     */       } 
/*     */     }
/* 141 */     while (idx7 != len7) {
/* 142 */       if (this._SUBSCRIPTION.get(idx7) instanceof javax.xml.bind.Element) {
/* 143 */         context.childAsElements((XMLSerializable)this._SUBSCRIPTION.get(idx7++)); continue;
/*     */       } 
/* 145 */       context.startElement("", "SUBSCRIPTION");
/* 146 */       int idx_3 = idx7;
/* 147 */       context.childAsAttributes((XMLSerializable)this._SUBSCRIPTION.get(idx_3++));
/* 148 */       context.endAttributes();
/* 149 */       context.childAsElements((XMLSerializable)this._SUBSCRIPTION.get(idx7++));
/* 150 */       context.endElement();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void serializeAttributes(XMLSerializer context) throws SAXException {
/* 156 */     this._SUBSCRIPTION.size();
/* 157 */     context.startAttribute("", "RequestDate");
/*     */     try {
/* 159 */       context.text(this._RequestDate);
/* 160 */     } catch (Exception e) {
/* 161 */       Util.handlePrintConversionException(this, e, context);
/*     */     } 
/* 163 */     context.endAttribute();
/* 164 */     if (this._SubscriberID != null) {
/* 165 */       context.startAttribute("", "SubscriberID");
/*     */       try {
/* 167 */         context.text(this._SubscriberID);
/* 168 */       } catch (Exception e) {
/* 169 */         Util.handlePrintConversionException(this, e, context);
/*     */       } 
/* 171 */       context.endAttribute();
/*     */     } 
/* 173 */     context.startAttribute("", "RequestID");
/*     */     try {
/* 175 */       context.text(this._RequestID);
/* 176 */     } catch (Exception e) {
/* 177 */       Util.handlePrintConversionException(this, e, context);
/*     */     } 
/* 179 */     context.endAttribute();
/* 180 */     if (this._LicensedSessions != null) {
/* 181 */       context.startAttribute("", "LicensedSessions");
/*     */       try {
/* 183 */         context.text(this._LicensedSessions);
/* 184 */       } catch (Exception e) {
/* 185 */         Util.handlePrintConversionException(this, e, context);
/*     */       } 
/* 187 */       context.endAttribute();
/*     */     } 
/* 189 */     if (this._Version != null) {
/* 190 */       context.startAttribute("", "Version");
/*     */       try {
/* 192 */         context.text(this._Version);
/* 193 */       } catch (Exception e) {
/* 194 */         Util.handlePrintConversionException(this, e, context);
/*     */       } 
/* 196 */       context.endAttribute();
/*     */     } 
/*     */   }
/*     */   
/*     */   public void serializeAttributeBodies(XMLSerializer context) throws SAXException {
/* 201 */     this._SUBSCRIPTION.size();
/*     */   }
/*     */   
/*     */   public Class getPrimaryInterface() {
/* 205 */     return REGISTRATIONType.class;
/*     */   }
/*     */   
/*     */   public DocumentDeclaration createRawValidator() {
/* 209 */     return (DocumentDeclaration)new REDocumentDeclaration(schemaFragment);
/*     */   }
/*     */   
/*     */   public class Unmarshaller
/*     */     extends ContentHandlerEx {
/*     */     public Unmarshaller(UnmarshallingContext context) {
/* 215 */       super(context, "---------------------");
/*     */     }
/*     */     
/*     */     protected UnmarshallableObject owner() {
/* 219 */       return REGISTRATIONTypeImpl.this;
/*     */     }
/*     */     
/*     */     public void enterElement(String ___uri, String ___local, Attributes __atts) throws UnreportedException {
/* 223 */       switch (this.state) {
/*     */         case 9:
/* 225 */           if ("" == ___uri && "SOFTWARE_KEY" == ___local) {
/* 226 */             this.context.pushAttributes(__atts);
/* 227 */             this.state = 12;
/*     */             return;
/*     */           } 
/* 230 */           if ("" == ___uri && "SUBSCRIPTION" == ___local) {
/* 231 */             this.context.pushAttributes(__atts);
/* 232 */             goto14();
/*     */             return;
/*     */           } 
/* 235 */           if ("" == ___uri && "LICENSE_KEY" == ___local) {
/* 236 */             this.context.pushAttributes(__atts);
/* 237 */             this.state = 10;
/*     */             return;
/*     */           } 
/*     */           break;
/*     */         case 16:
/* 242 */           if ("" == ___uri && "SUBSCRIPTION" == ___local) {
/* 243 */             this.context.pushAttributes(__atts);
/* 244 */             goto14();
/*     */             return;
/*     */           } 
/* 247 */           revertToParentFromEnterElement(___uri, ___local, __atts);
/*     */           return;
/*     */         case 12:
/* 250 */           if ("" == ___uri && "CHUNK" == ___local) {
/* 251 */             REGISTRATIONTypeImpl.this._SOFTWAREKEY = (SOFTWAREKEYTypeImpl)spawnChildFromEnterElement(SOFTWAREKEYTypeImpl.class, 13, ___uri, ___local, __atts);
/*     */             return;
/*     */           } 
/*     */           break;
/*     */         case 10:
/* 256 */           if ("" == ___uri && "CHUNK" == ___local) {
/* 257 */             REGISTRATIONTypeImpl.this._LICENSEKEY = (LICENSEKEYTypeImpl)spawnChildFromEnterElement(LICENSEKEYTypeImpl.class, 11, ___uri, ___local, __atts);
/*     */             return;
/*     */           } 
/*     */           break;
/*     */         case 7:
/* 262 */           if ("" == ___uri && "DEALERSHIP_CONTACT" == ___local) {
/* 263 */             REGISTRATIONTypeImpl.this._DEALERSHIP = (DEALERSHIPTypeImpl)spawnChildFromEnterElement(DEALERSHIPTypeImpl.class, 8, ___uri, ___local, __atts);
/*     */             return;
/*     */           } 
/*     */           break;
/*     */         case 0:
/* 268 */           if ("" == ___uri && "DEALERSHIP" == ___local) {
/* 269 */             this.context.pushAttributes(__atts);
/* 270 */             goto7();
/*     */             return;
/*     */           } 
/*     */           break;
/*     */       } 
/* 275 */       super.enterElement(___uri, ___local, __atts);
/*     */     }
/*     */     
/*     */     public void leaveElement(String ___uri, String ___local) throws UnreportedException {
/* 279 */       switch (this.state) {
/*     */         case 13:
/* 281 */           if ("" == ___uri && "SOFTWARE_KEY" == ___local) {
/* 282 */             this.context.popAttributes();
/* 283 */             this.state = 9;
/*     */             return;
/*     */           } 
/*     */           break;
/*     */         case 11:
/* 288 */           if ("" == ___uri && "LICENSE_KEY" == ___local) {
/* 289 */             this.context.popAttributes();
/* 290 */             this.state = 9;
/*     */             return;
/*     */           } 
/*     */           break;
/*     */         case 15:
/* 295 */           if ("" == ___uri && "SUBSCRIPTION" == ___local) {
/* 296 */             this.context.popAttributes();
/* 297 */             this.state = 16;
/*     */             return;
/*     */           } 
/*     */           break;
/*     */         case 16:
/* 302 */           revertToParentFromLeaveElement(___uri, ___local);
/*     */           return;
/*     */         case 12:
/* 305 */           if ("" == ___uri && "SOFTWARE_KEY" == ___local) {
/* 306 */             REGISTRATIONTypeImpl.this._SOFTWAREKEY = (SOFTWAREKEYTypeImpl)spawnChildFromLeaveElement(SOFTWAREKEYTypeImpl.class, 13, ___uri, ___local);
/*     */             return;
/*     */           } 
/*     */           break;
/*     */         case 10:
/* 311 */           if ("" == ___uri && "LICENSE_KEY" == ___local) {
/* 312 */             REGISTRATIONTypeImpl.this._LICENSEKEY = (LICENSEKEYTypeImpl)spawnChildFromLeaveElement(LICENSEKEYTypeImpl.class, 11, ___uri, ___local);
/*     */             return;
/*     */           } 
/*     */           break;
/*     */         case 8:
/* 317 */           if ("" == ___uri && "DEALERSHIP" == ___local) {
/* 318 */             this.context.popAttributes();
/* 319 */             this.state = 9;
/*     */             return;
/*     */           } 
/*     */           break;
/*     */       } 
/* 324 */       super.leaveElement(___uri, ___local);
/*     */     }
/*     */     
/*     */     public void enterAttribute(String ___uri, String ___local) throws UnreportedException {
/* 328 */       switch (this.state) {
/*     */         case 16:
/* 330 */           revertToParentFromEnterAttribute(___uri, ___local);
/*     */           return;
/*     */         case 7:
/* 333 */           if ("" == ___uri && "DealershipZIP" == ___local) {
/* 334 */             REGISTRATIONTypeImpl.this._DEALERSHIP = (DEALERSHIPTypeImpl)spawnChildFromEnterAttribute(DEALERSHIPTypeImpl.class, 8, ___uri, ___local);
/*     */             return;
/*     */           } 
/* 337 */           if ("" == ___uri && "DealershipCity" == ___local) {
/* 338 */             REGISTRATIONTypeImpl.this._DEALERSHIP = (DEALERSHIPTypeImpl)spawnChildFromEnterAttribute(DEALERSHIPTypeImpl.class, 8, ___uri, ___local);
/*     */             return;
/*     */           } 
/* 341 */           if ("" == ___uri && "DealershipCountry" == ___local) {
/* 342 */             REGISTRATIONTypeImpl.this._DEALERSHIP = (DEALERSHIPTypeImpl)spawnChildFromEnterAttribute(DEALERSHIPTypeImpl.class, 8, ___uri, ___local);
/*     */             return;
/*     */           } 
/* 345 */           if ("" == ___uri && "DealershipName" == ___local) {
/* 346 */             REGISTRATIONTypeImpl.this._DEALERSHIP = (DEALERSHIPTypeImpl)spawnChildFromEnterAttribute(DEALERSHIPTypeImpl.class, 8, ___uri, ___local);
/*     */             return;
/*     */           } 
/* 349 */           if ("" == ___uri && "DealershipStreet" == ___local) {
/* 350 */             REGISTRATIONTypeImpl.this._DEALERSHIP = (DEALERSHIPTypeImpl)spawnChildFromEnterAttribute(DEALERSHIPTypeImpl.class, 8, ___uri, ___local);
/*     */             return;
/*     */           } 
/* 353 */           if ("" == ___uri && "DealershipState" == ___local) {
/* 354 */             REGISTRATIONTypeImpl.this._DEALERSHIP = (DEALERSHIPTypeImpl)spawnChildFromEnterAttribute(DEALERSHIPTypeImpl.class, 8, ___uri, ___local);
/*     */             return;
/*     */           } 
/* 357 */           if ("" == ___uri && "DealershipPhone" == ___local) {
/* 358 */             REGISTRATIONTypeImpl.this._DEALERSHIP = (DEALERSHIPTypeImpl)spawnChildFromEnterAttribute(DEALERSHIPTypeImpl.class, 8, ___uri, ___local);
/*     */             return;
/*     */           } 
/* 361 */           if ("" == ___uri && "DealershipFax" == ___local) {
/* 362 */             REGISTRATIONTypeImpl.this._DEALERSHIP = (DEALERSHIPTypeImpl)spawnChildFromEnterAttribute(DEALERSHIPTypeImpl.class, 8, ___uri, ___local);
/*     */             return;
/*     */           } 
/* 365 */           if ("" == ___uri && "DealershipCode" == ___local) {
/* 366 */             REGISTRATIONTypeImpl.this._DEALERSHIP = (DEALERSHIPTypeImpl)spawnChildFromEnterAttribute(DEALERSHIPTypeImpl.class, 8, ___uri, ___local);
/*     */             return;
/*     */           } 
/* 369 */           if ("" == ___uri && "DealershipEmail" == ___local) {
/* 370 */             REGISTRATIONTypeImpl.this._DEALERSHIP = (DEALERSHIPTypeImpl)spawnChildFromEnterAttribute(DEALERSHIPTypeImpl.class, 8, ___uri, ___local);
/*     */             return;
/*     */           } 
/* 373 */           if ("" == ___uri && "DealershipLanguage" == ___local) {
/* 374 */             REGISTRATIONTypeImpl.this._DEALERSHIP = (DEALERSHIPTypeImpl)spawnChildFromEnterAttribute(DEALERSHIPTypeImpl.class, 8, ___uri, ___local);
/*     */             return;
/*     */           } 
/*     */           break;
/*     */         case 14:
/* 379 */           if ("" == ___uri && "SubscriptionID" == ___local) {
/* 380 */             REGISTRATIONTypeImpl.this._SUBSCRIPTION.add(spawnChildFromEnterAttribute(SUBSCRIPTIONTypeImpl.class, 15, ___uri, ___local));
/*     */             return;
/*     */           } 
/* 383 */           if ("" == ___uri && "Description" == ___local) {
/* 384 */             REGISTRATIONTypeImpl.this._SUBSCRIPTION.add(spawnChildFromEnterAttribute(SUBSCRIPTIONTypeImpl.class, 15, ___uri, ___local));
/*     */             return;
/*     */           } 
/*     */           break;
/*     */         case 0:
/* 389 */           if ("" == ___uri && "RequestDate" == ___local) {
/* 390 */             this.state = 5;
/*     */             return;
/*     */           } 
/* 393 */           if ("" == ___uri && "LicensedSessions" == ___local) {
/* 394 */             this.state = 3;
/*     */             return;
/*     */           } 
/* 397 */           if ("" == ___uri && "SubscriberID" == ___local) {
/* 398 */             this.state = 19;
/*     */             return;
/*     */           } 
/* 401 */           if ("" == ___uri && "RequestID" == ___local) {
/* 402 */             this.state = 1;
/*     */             return;
/*     */           } 
/* 405 */           if ("" == ___uri && "Version" == ___local) {
/* 406 */             this.state = 17;
/*     */             return;
/*     */           } 
/*     */           break;
/*     */       } 
/* 411 */       super.enterAttribute(___uri, ___local);
/*     */     }
/*     */     
/*     */     public void leaveAttribute(String ___uri, String ___local) throws UnreportedException {
/* 415 */       switch (this.state) {
/*     */         case 4:
/* 417 */           if ("" == ___uri && "LicensedSessions" == ___local) {
/* 418 */             goto0();
/*     */             return;
/*     */           } 
/*     */           break;
/*     */         case 6:
/* 423 */           if ("" == ___uri && "RequestDate" == ___local) {
/* 424 */             goto0();
/*     */             return;
/*     */           } 
/*     */           break;
/*     */         case 16:
/* 429 */           revertToParentFromLeaveAttribute(___uri, ___local);
/*     */           return;
/*     */         case 20:
/* 432 */           if ("" == ___uri && "SubscriberID" == ___local) {
/* 433 */             goto0();
/*     */             return;
/*     */           } 
/*     */           break;
/*     */         case 18:
/* 438 */           if ("" == ___uri && "Version" == ___local) {
/* 439 */             goto0();
/*     */             return;
/*     */           } 
/*     */           break;
/*     */         case 2:
/* 444 */           if ("" == ___uri && "RequestID" == ___local) {
/* 445 */             goto0();
/*     */             return;
/*     */           } 
/*     */           break;
/*     */       } 
/* 450 */       super.leaveAttribute(___uri, ___local);
/*     */     }
/*     */     
/*     */     public void text(String value) throws UnreportedException {
/*     */       try {
/* 455 */         switch (this.state) {
/*     */           case 19:
/*     */             try {
/* 458 */               REGISTRATIONTypeImpl.this._SubscriberID = value;
/* 459 */             } catch (Exception e) {
/* 460 */               handleParseConversionException(e);
/*     */             } 
/* 462 */             this.state = 20;
/*     */             return;
/*     */           case 16:
/* 465 */             revertToParentFromText(value);
/*     */             return;
/*     */           case 5:
/*     */             try {
/* 469 */               REGISTRATIONTypeImpl.this._RequestDate = value;
/* 470 */             } catch (Exception e) {
/* 471 */               handleParseConversionException(e);
/*     */             } 
/* 473 */             this.state = 6;
/*     */             return;
/*     */           case 1:
/*     */             try {
/* 477 */               REGISTRATIONTypeImpl.this._RequestID = value;
/* 478 */             } catch (Exception e) {
/* 479 */               handleParseConversionException(e);
/*     */             } 
/* 481 */             this.state = 2;
/*     */             return;
/*     */           case 17:
/*     */             try {
/* 485 */               REGISTRATIONTypeImpl.this._Version = value;
/* 486 */             } catch (Exception e) {
/* 487 */               handleParseConversionException(e);
/*     */             } 
/* 489 */             this.state = 18;
/*     */             return;
/*     */           case 3:
/*     */             try {
/* 493 */               REGISTRATIONTypeImpl.this._LicensedSessions = value;
/* 494 */             } catch (Exception e) {
/* 495 */               handleParseConversionException(e);
/*     */             } 
/* 497 */             this.state = 4;
/*     */             return;
/*     */         } 
/* 500 */       } catch (RuntimeException e) {
/* 501 */         handleUnexpectedTextException(value, e);
/*     */       } 
/*     */     }
/*     */     
/*     */     public void leaveChild(int nextState) throws UnreportedException {
/* 506 */       switch (nextState) {
/*     */         case 13:
/* 508 */           this.state = 13;
/*     */           return;
/*     */         case 11:
/* 511 */           this.state = 11;
/*     */           return;
/*     */         case 8:
/* 514 */           this.state = 8;
/*     */           return;
/*     */         case 15:
/* 517 */           this.state = 15;
/*     */           return;
/*     */       } 
/* 520 */       super.leaveChild(nextState);
/*     */     }
/*     */ 
/*     */     
/*     */     private void goto7() throws UnreportedException {
/* 525 */       this.state = 7;
/* 526 */       int idx = this.context.getAttribute("", "DealershipEmail");
/* 527 */       if (idx >= 0) {
/* 528 */         this.context.consumeAttribute(idx);
/*     */         return;
/*     */       } 
/* 531 */       idx = this.context.getAttribute("", "DealershipState");
/* 532 */       if (idx >= 0) {
/* 533 */         this.context.consumeAttribute(idx);
/*     */         return;
/*     */       } 
/* 536 */       idx = this.context.getAttribute("", "DealershipFax");
/* 537 */       if (idx >= 0) {
/* 538 */         this.context.consumeAttribute(idx);
/*     */         return;
/*     */       } 
/* 541 */       idx = this.context.getAttribute("", "DealershipLanguage");
/* 542 */       if (idx >= 0) {
/* 543 */         this.context.consumeAttribute(idx);
/*     */         return;
/*     */       } 
/* 546 */       idx = this.context.getAttribute("", "DealershipPhone");
/* 547 */       if (idx >= 0) {
/* 548 */         this.context.consumeAttribute(idx);
/*     */         return;
/*     */       } 
/* 551 */       idx = this.context.getAttribute("", "DealershipCountry");
/* 552 */       if (idx >= 0) {
/* 553 */         this.context.consumeAttribute(idx);
/*     */         return;
/*     */       } 
/* 556 */       idx = this.context.getAttribute("", "DealershipZIP");
/* 557 */       if (idx >= 0) {
/* 558 */         this.context.consumeAttribute(idx);
/*     */         return;
/*     */       } 
/* 561 */       idx = this.context.getAttribute("", "DealershipStreet");
/* 562 */       if (idx >= 0) {
/* 563 */         this.context.consumeAttribute(idx);
/*     */         return;
/*     */       } 
/* 566 */       idx = this.context.getAttribute("", "DealershipName");
/* 567 */       if (idx >= 0) {
/* 568 */         this.context.consumeAttribute(idx);
/*     */         return;
/*     */       } 
/* 571 */       idx = this.context.getAttribute("", "DealershipCode");
/* 572 */       if (idx >= 0) {
/* 573 */         this.context.consumeAttribute(idx);
/*     */         return;
/*     */       } 
/* 576 */       idx = this.context.getAttribute("", "DealershipCity");
/* 577 */       if (idx >= 0) {
/* 578 */         this.context.consumeAttribute(idx);
/*     */         return;
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     private void goto14() throws UnreportedException {
/* 585 */       this.state = 14;
/* 586 */       int idx = this.context.getAttribute("", "Description");
/* 587 */       if (idx >= 0) {
/* 588 */         this.context.consumeAttribute(idx);
/*     */         return;
/*     */       } 
/* 591 */       idx = this.context.getAttribute("", "SubscriptionID");
/* 592 */       if (idx >= 0) {
/* 593 */         this.context.consumeAttribute(idx);
/*     */         return;
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     private void goto0() throws UnreportedException {
/* 600 */       this.state = 0;
/* 601 */       int idx = this.context.getAttribute("", "RequestDate");
/* 602 */       if (idx >= 0) {
/* 603 */         this.context.consumeAttribute(idx);
/*     */         return;
/*     */       } 
/* 606 */       idx = this.context.getAttribute("", "SubscriberID");
/* 607 */       if (idx >= 0) {
/* 608 */         this.context.consumeAttribute(idx);
/*     */         return;
/*     */       } 
/* 611 */       idx = this.context.getAttribute("", "RequestID");
/* 612 */       if (idx >= 0) {
/* 613 */         this.context.consumeAttribute(idx);
/*     */         return;
/*     */       } 
/* 616 */       idx = this.context.getAttribute("", "LicensedSessions");
/* 617 */       if (idx >= 0) {
/* 618 */         this.context.consumeAttribute(idx);
/*     */         return;
/*     */       } 
/* 621 */       idx = this.context.getAttribute("", "Version");
/* 622 */       if (idx >= 0) {
/* 623 */         this.context.consumeAttribute(idx);
/*     */         return;
/*     */       } 
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\registration\common\xml\impl\REGISTRATIONTypeImpl.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */