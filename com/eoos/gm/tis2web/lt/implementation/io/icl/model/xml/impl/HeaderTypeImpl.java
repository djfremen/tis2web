/*     */ package com.eoos.gm.tis2web.lt.implementation.io.icl.model.xml.impl;
/*     */ import com.eoos.gm.tis2web.lt.implementation.io.icl.model.xml.EngineType;
/*     */ import com.eoos.gm.tis2web.lt.implementation.io.icl.model.xml.HeaderType;
/*     */ import com.eoos.gm.tis2web.lt.implementation.io.icl.model.xml.ModelType;
/*     */ import com.eoos.gm.tis2web.lt.implementation.io.icl.model.xml.ReleaseType;
/*     */ import com.eoos.gm.tis2web.lt.implementation.io.icl.model.xml.TransmissionType;
/*     */ import com.eoos.gm.tis2web.lt.implementation.io.icl.model.xml.VINType;
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
/*     */ public class HeaderTypeImpl implements HeaderType, UnmarshallableObject, XMLSerializable, ValidatableObject {
/*     */   protected String _InspType;
/*     */   protected String _Date;
/*     */   protected String _Customer;
/*     */   protected String _ServType;
/*     */   protected String _Phone;
/*     */   protected String _DrvType;
/*     */   protected ModelType _Model;
/*     */   protected ReleaseType _Release;
/*  31 */   private static final Grammar schemaFragment = SchemaDeserializer.deserialize("¬í\000\005sr\000\037com.sun.msv.grammar.SequenceExp\000\000\000\000\000\000\000\001\002\000\000xr\000\035com.sun.msv.grammar.BinaryExp\000\000\000\000\000\000\000\001\002\000\002L\000\004exp1t\000 Lcom/sun/msv/grammar/Expression;L\000\004exp2q\000~\000\002xr\000\036com.sun.msv.grammar.Expressionø\030èN5~O\002\000\003I\000\016cachedHashCodeL\000\023epsilonReducibilityt\000\023Ljava/lang/Boolean;L\000\013expandedExpq\000~\000\002xp\037{Nsppsq\000~\000\000\035[«ppsq\000~\000\000\033v\tüppsq\000~\000\000\031VD4ppsq\000~\000\000\0276~lppsq\000~\000\000\025\026¸¤ppsq\000~\000\000\02319õppsq\000~\000\000\021K»Fppsq\000~\000\000\017f<ppsq\000~\000\000\r½èppsq\000~\000\000\013`ø ppsq\000~\000\000\t{yqppsq\000~\000\000\007úÂppsq\000~\000\000\005°|\bppsq\000~\000\000\003ÊýYppsr\000'com.sun.msv.grammar.trex.ElementPattern\000\000\000\000\000\000\000\001\002\000\001L\000\tnameClasst\000\037Lcom/sun/msv/grammar/NameClass;xr\000\036com.sun.msv.grammar.ElementExp\000\000\000\000\000\000\000\001\002\000\002Z\000\032ignoreUndeclaredAttributesL\000\fcontentModelq\000~\000\002xq\000~\000\003\001å~ªpp\000sr\000\033com.sun.msv.grammar.DataExp\000\000\000\000\000\000\000\001\002\000\003L\000\002dtt\000\037Lorg/relaxng/datatype/Datatype;L\000\006exceptq\000~\000\002L\000\004namet\000\035Lcom/sun/msv/util/StringPair;xq\000~\000\003\001å~ppsr\000#com.sun.msv.datatype.xsd.StringType\000\000\000\000\000\000\000\001\002\000\001Z\000\risAlwaysValidxr\000*com.sun.msv.datatype.xsd.BuiltinAtomicType\000\000\000\000\000\000\000\001\002\000\000xr\000%com.sun.msv.datatype.xsd.ConcreteType\000\000\000\000\000\000\000\001\002\000\000xr\000'com.sun.msv.datatype.xsd.XSDatatypeImpl\000\000\000\000\000\000\000\001\002\000\003L\000\fnamespaceUrit\000\022Ljava/lang/String;L\000\btypeNameq\000~\000 L\000\nwhiteSpacet\000.Lcom/sun/msv/datatype/xsd/WhiteSpaceProcessor;xpt\000 http://www.w3.org/2001/XMLSchemat\000\006stringsr\000.com.sun.msv.datatype.xsd.WhiteSpaceProcessor$1\023JMoIÛ¤G\002\000\000xr\000,com.sun.msv.datatype.xsd.WhiteSpaceProcessor\000\000\000\000\000\000\000\001\002\000\000xp\001sr\0000com.sun.msv.grammar.Expression$NullSetExpression\000\000\000\000\000\000\000\001\002\000\000xq\000~\000\003\000\000\000\nppsr\000\033com.sun.msv.util.StringPairÐt\036jB \002\000\002L\000\tlocalNameq\000~\000 L\000\fnamespaceURIq\000~\000 xpq\000~\000$q\000~\000#sr\000#com.sun.msv.grammar.SimpleNameClass\000\000\000\000\000\000\000\001\002\000\002L\000\tlocalNameq\000~\000 L\000\fnamespaceURIq\000~\000 xr\000\035com.sun.msv.grammar.NameClass\000\000\000\000\000\000\000\001\002\000\000xpt\000\004Maket\000\000sq\000~\000\024\001å~ªpp\000q\000~\000\033sq\000~\000,t\000\005Titleq\000~\0000sq\000~\000\024\001å~ªpp\000q\000~\000\033sq\000~\000,t\000\bServTypeq\000~\0000sr\000\035com.sun.msv.grammar.ChoiceExp\000\000\000\000\000\000\000\001\002\000\000xq\000~\000\001\001å~µppsq\000~\000\024\001å~ªsr\000\021java.lang.BooleanÍ rÕúî\002\000\001Z\000\005valuexp\000p\000q\000~\000\033sq\000~\000,t\000\007DrvTypeq\000~\0000sr\0000com.sun.msv.grammar.Expression$EpsilonExpression\000\000\000\000\000\000\000\001\002\000\000xq\000~\000\003\000\000\000\tsq\000~\000:\001psq\000~\000\024\001å~ªpp\000q\000~\000\033sq\000~\000,t\000\007Ordernoq\000~\0000sq\000~\000\024\001å~ªpp\000q\000~\000\033sq\000~\000,t\000\bCustomerq\000~\0000sq\000~\0007\002\037ÅÃppsq\000~\000\024\001\017âæpp\000sq\000~\000\024\001\017âÛpp\000sq\000~\0007\001\017âÐppsr\000 com.sun.msv.grammar.OneOrMoreExp\000\000\000\000\000\000\000\001\002\000\000xr\000\034com.sun.msv.grammar.UnaryExp\000\000\000\000\000\000\000\001\002\000\001L\000\003expq\000~\000\002xq\000~\000\003\001\017âÅq\000~\000;psr\000 com.sun.msv.grammar.AttributeExp\000\000\000\000\000\000\000\001\002\000\002L\000\003expq\000~\000\002L\000\tnameClassq\000~\000\025xq\000~\000\003\001\017âÂq\000~\000;psr\0002com.sun.msv.grammar.Expression$AnyStringExpression\000\000\000\000\000\000\000\001\002\000\000xq\000~\000\003\000\000\000\bq\000~\000@psr\000 com.sun.msv.grammar.AnyNameClass\000\000\000\000\000\000\000\001\002\000\000xq\000~\000-q\000~\000?sq\000~\000,t\0003com.eoos.gm.tis2web.lt.io.icl.model.xml.ReleaseTypet\000+http://java.sun.com/jaxb/xjc/dummy-elementssq\000~\000,t\000\007Releaseq\000~\0000sq\000~\000\024\001\017âÛpp\000sq\000~\0007\001\017âÐppsq\000~\000K\001\017âÅq\000~\000;psq\000~\000N\001\017âÂq\000~\000;pq\000~\000Qq\000~\000Sq\000~\000?sq\000~\000,t\000/com.eoos.gm.tis2web.lt.io.icl.model.xml.Releaseq\000~\000Vsq\000~\000\024\001å~ªpp\000q\000~\000\033sq\000~\000,t\000\004Dateq\000~\0000sq\000~\000\024\001å~ªpp\000q\000~\000\033sq\000~\000,t\000\005Phoneq\000~\0000sq\000~\000\024\001å~ªpp\000q\000~\000\033sq\000~\000,t\000\002Kmq\000~\0000sq\000~\000\024\001å~ªpp\000q\000~\000\033sq\000~\000,t\000\013Numberplateq\000~\0000sq\000~\0007\002\037ÅÃppsq\000~\000\024\001\017âæpp\000sq\000~\000\024\001\017âÛpp\000sq\000~\0007\001\017âÐppsq\000~\000K\001\017âÅq\000~\000;psq\000~\000N\001\017âÂq\000~\000;pq\000~\000Qq\000~\000Sq\000~\000?sq\000~\000,t\0001com.eoos.gm.tis2web.lt.io.icl.model.xml.ModelTypeq\000~\000Vsq\000~\000,t\000\005Modelq\000~\0000sq\000~\000\024\001\017âÛpp\000sq\000~\0007\001\017âÐppsq\000~\000K\001\017âÅq\000~\000;psq\000~\000N\001\017âÂq\000~\000;pq\000~\000Qq\000~\000Sq\000~\000?sq\000~\000,t\000-com.eoos.gm.tis2web.lt.io.icl.model.xml.Modelq\000~\000Vsq\000~\0007\002\037ÅÃppsq\000~\000\024\001\017âæpp\000sq\000~\000\024\001\017âÛpp\000sq\000~\0007\001\017âÐppsq\000~\000K\001\017âÅq\000~\000;psq\000~\000N\001\017âÂq\000~\000;pq\000~\000Qq\000~\000Sq\000~\000?sq\000~\000,t\0002com.eoos.gm.tis2web.lt.io.icl.model.xml.EngineTypeq\000~\000Vsq\000~\000,t\000\006Engineq\000~\0000sq\000~\000\024\001\017âÛpp\000sq\000~\0007\001\017âÐppsq\000~\000K\001\017âÅq\000~\000;psq\000~\000N\001\017âÂq\000~\000;pq\000~\000Qq\000~\000Sq\000~\000?sq\000~\000,t\000.com.eoos.gm.tis2web.lt.io.icl.model.xml.Engineq\000~\000Vsq\000~\0007\002\037ÅÃppsq\000~\000\024\001\017âæpp\000sq\000~\000\024\001\017âÛpp\000sq\000~\0007\001\017âÐppsq\000~\000K\001\017âÅq\000~\000;psq\000~\000N\001\017âÂq\000~\000;pq\000~\000Qq\000~\000Sq\000~\000?sq\000~\000,t\0008com.eoos.gm.tis2web.lt.io.icl.model.xml.TransmissionTypeq\000~\000Vsq\000~\000,t\000\fTransmissionq\000~\0000sq\000~\000\024\001\017âÛpp\000sq\000~\0007\001\017âÐppsq\000~\000K\001\017âÅq\000~\000;psq\000~\000N\001\017âÂq\000~\000;pq\000~\000Qq\000~\000Sq\000~\000?sq\000~\000,t\0004com.eoos.gm.tis2web.lt.io.icl.model.xml.Transmissionq\000~\000Vsq\000~\000\024\001å~ªpp\000q\000~\000\033sq\000~\000,t\000\bInspTypeq\000~\0000sq\000~\0007\002\037ÅÃppsq\000~\000\024\001\017âæpp\000sq\000~\000\024\001\017âÛpp\000sq\000~\0007\001\017âÐppsq\000~\000K\001\017âÅq\000~\000;psq\000~\000N\001\017âÂq\000~\000;pq\000~\000Qq\000~\000Sq\000~\000?sq\000~\000,t\000/com.eoos.gm.tis2web.lt.io.icl.model.xml.VINTypeq\000~\000Vsq\000~\000,t\000\003VINq\000~\0000sq\000~\000\024\001\017âÛpp\000sq\000~\0007\001\017âÐppsq\000~\000K\001\017âÅq\000~\000;psq\000~\000N\001\017âÂq\000~\000;pq\000~\000Qq\000~\000Sq\000~\000?sq\000~\000,t\000+com.eoos.gm.tis2web.lt.io.icl.model.xml.VINq\000~\000Vsr\000\"com.sun.msv.grammar.ExpressionPool\000\000\000\000\000\000\000\001\002\000\001L\000\bexpTablet\000/Lcom/sun/msv/grammar/ExpressionPool$ClosedHash;xpsr\000-com.sun.msv.grammar.ExpressionPool$ClosedHash×jÐNïèí\034\002\000\004I\000\005countI\000\tthresholdL\000\006parentq\000~\000¯[\000\005tablet\000![Lcom/sun/msv/grammar/Expression;xp\000\000\000)\000\000\0009pur\000![Lcom.sun.msv.grammar.Expression;Ö8DÃ]­§\n\002\000\000xp\000\000\000¿pppppq\000~\000\020pppppppppppppq\000~\000\fppppq\000~\000\006ppppppppppppppppppq\000~\000\017q\000~\0008ppppppppppppq\000~\000\013pppppppppppppq\000~\000\023ppppppppppppppq\000~\000Gq\000~\000kq\000~\000{q\000~\000\bq\000~\000q\000~\000pppq\000~\000\npppppppppppppq\000~\000\022q\000~\000Mq\000~\000[q\000~\000oq\000~\000wq\000~\000q\000~\000q\000~\000q\000~\000q\000~\000¢q\000~\000ªq\000~\000\005q\000~\000Jq\000~\000Zq\000~\000nq\000~\000vq\000~\000~q\000~\000q\000~\000q\000~\000q\000~\000¡q\000~\000©pppq\000~\000\016pppppppppppppppppppppppq\000~\000\021pppppppppppppq\000~\000\rppppq\000~\000\007ppppppppq\000~\000\tpppp"); protected String _Orderno; protected TransmissionType _Transmission; protected String _Make;
/*     */   protected VINType _VIN;
/*     */   
/*     */   private static final Class PRIMARY_INTERFACE_CLASS() {
/*  35 */     return HeaderType.class;
/*     */   }
/*     */   protected String _Title; protected EngineType _Engine; protected String _Numberplate; protected String _Km;
/*     */   public String getInspType() {
/*  39 */     return this._InspType;
/*     */   }
/*     */   
/*     */   public void setInspType(String value) {
/*  43 */     this._InspType = value;
/*     */   }
/*     */   
/*     */   public String getDate() {
/*  47 */     return this._Date;
/*     */   }
/*     */   
/*     */   public void setDate(String value) {
/*  51 */     this._Date = value;
/*     */   }
/*     */   
/*     */   public String getCustomer() {
/*  55 */     return this._Customer;
/*     */   }
/*     */   
/*     */   public void setCustomer(String value) {
/*  59 */     this._Customer = value;
/*     */   }
/*     */   
/*     */   public String getServType() {
/*  63 */     return this._ServType;
/*     */   }
/*     */   
/*     */   public void setServType(String value) {
/*  67 */     this._ServType = value;
/*     */   }
/*     */   
/*     */   public String getPhone() {
/*  71 */     return this._Phone;
/*     */   }
/*     */   
/*     */   public void setPhone(String value) {
/*  75 */     this._Phone = value;
/*     */   }
/*     */   
/*     */   public String getDrvType() {
/*  79 */     return this._DrvType;
/*     */   }
/*     */   
/*     */   public void setDrvType(String value) {
/*  83 */     this._DrvType = value;
/*     */   }
/*     */   
/*     */   public ModelType getModel() {
/*  87 */     return this._Model;
/*     */   }
/*     */   
/*     */   public void setModel(ModelType value) {
/*  91 */     this._Model = value;
/*     */   }
/*     */   
/*     */   public ReleaseType getRelease() {
/*  95 */     return this._Release;
/*     */   }
/*     */   
/*     */   public void setRelease(ReleaseType value) {
/*  99 */     this._Release = value;
/*     */   }
/*     */   
/*     */   public String getOrderno() {
/* 103 */     return this._Orderno;
/*     */   }
/*     */   
/*     */   public void setOrderno(String value) {
/* 107 */     this._Orderno = value;
/*     */   }
/*     */   
/*     */   public TransmissionType getTransmission() {
/* 111 */     return this._Transmission;
/*     */   }
/*     */   
/*     */   public void setTransmission(TransmissionType value) {
/* 115 */     this._Transmission = value;
/*     */   }
/*     */   
/*     */   public String getMake() {
/* 119 */     return this._Make;
/*     */   }
/*     */   
/*     */   public void setMake(String value) {
/* 123 */     this._Make = value;
/*     */   }
/*     */   
/*     */   public VINType getVIN() {
/* 127 */     return this._VIN;
/*     */   }
/*     */   
/*     */   public void setVIN(VINType value) {
/* 131 */     this._VIN = value;
/*     */   }
/*     */   
/*     */   public String getTitle() {
/* 135 */     return this._Title;
/*     */   }
/*     */   
/*     */   public void setTitle(String value) {
/* 139 */     this._Title = value;
/*     */   }
/*     */   
/*     */   public EngineType getEngine() {
/* 143 */     return this._Engine;
/*     */   }
/*     */   
/*     */   public void setEngine(EngineType value) {
/* 147 */     this._Engine = value;
/*     */   }
/*     */   
/*     */   public String getNumberplate() {
/* 151 */     return this._Numberplate;
/*     */   }
/*     */   
/*     */   public void setNumberplate(String value) {
/* 155 */     this._Numberplate = value;
/*     */   }
/*     */   
/*     */   public String getKm() {
/* 159 */     return this._Km;
/*     */   }
/*     */   
/*     */   public void setKm(String value) {
/* 163 */     this._Km = value;
/*     */   }
/*     */   
/*     */   public ContentHandlerEx getUnmarshaller(UnmarshallingContext context) {
/* 167 */     return new Unmarshaller(context);
/*     */   }
/*     */   
/*     */   public Class getPrimaryInterfaceClass() {
/* 171 */     return PRIMARY_INTERFACE_CLASS();
/*     */   }
/*     */   
/*     */   public void serializeElements(XMLSerializer context) throws SAXException {
/* 175 */     context.startElement("", "Make");
/* 176 */     context.endAttributes();
/*     */     try {
/* 178 */       context.text(this._Make);
/* 179 */     } catch (Exception e) {
/* 180 */       Util.handlePrintConversionException(this, e, context);
/*     */     } 
/* 182 */     context.endElement();
/* 183 */     context.startElement("", "Title");
/* 184 */     context.endAttributes();
/*     */     try {
/* 186 */       context.text(this._Title);
/* 187 */     } catch (Exception e) {
/* 188 */       Util.handlePrintConversionException(this, e, context);
/*     */     } 
/* 190 */     context.endElement();
/* 191 */     context.startElement("", "ServType");
/* 192 */     context.endAttributes();
/*     */     try {
/* 194 */       context.text(this._ServType);
/* 195 */     } catch (Exception e) {
/* 196 */       Util.handlePrintConversionException(this, e, context);
/*     */     } 
/* 198 */     context.endElement();
/* 199 */     if (this._DrvType != null) {
/* 200 */       context.startElement("", "DrvType");
/* 201 */       context.endAttributes();
/*     */       try {
/* 203 */         context.text(this._DrvType);
/* 204 */       } catch (Exception e) {
/* 205 */         Util.handlePrintConversionException(this, e, context);
/*     */       } 
/* 207 */       context.endElement();
/*     */     } 
/* 209 */     context.startElement("", "Orderno");
/* 210 */     context.endAttributes();
/*     */     try {
/* 212 */       context.text(this._Orderno);
/* 213 */     } catch (Exception e) {
/* 214 */       Util.handlePrintConversionException(this, e, context);
/*     */     } 
/* 216 */     context.endElement();
/* 217 */     context.startElement("", "Customer");
/* 218 */     context.endAttributes();
/*     */     try {
/* 220 */       context.text(this._Customer);
/* 221 */     } catch (Exception e) {
/* 222 */       Util.handlePrintConversionException(this, e, context);
/*     */     } 
/* 224 */     context.endElement();
/* 225 */     if (this._Release instanceof javax.xml.bind.Element) {
/* 226 */       context.childAsElements((XMLSerializable)this._Release);
/*     */     } else {
/* 228 */       context.startElement("", "Release");
/* 229 */       context.childAsAttributes((XMLSerializable)this._Release);
/* 230 */       context.endAttributes();
/* 231 */       context.childAsElements((XMLSerializable)this._Release);
/* 232 */       context.endElement();
/*     */     } 
/* 234 */     context.startElement("", "Date");
/* 235 */     context.endAttributes();
/*     */     try {
/* 237 */       context.text(this._Date);
/* 238 */     } catch (Exception e) {
/* 239 */       Util.handlePrintConversionException(this, e, context);
/*     */     } 
/* 241 */     context.endElement();
/* 242 */     context.startElement("", "Phone");
/* 243 */     context.endAttributes();
/*     */     try {
/* 245 */       context.text(this._Phone);
/* 246 */     } catch (Exception e) {
/* 247 */       Util.handlePrintConversionException(this, e, context);
/*     */     } 
/* 249 */     context.endElement();
/* 250 */     context.startElement("", "Km");
/* 251 */     context.endAttributes();
/*     */     try {
/* 253 */       context.text(this._Km);
/* 254 */     } catch (Exception e) {
/* 255 */       Util.handlePrintConversionException(this, e, context);
/*     */     } 
/* 257 */     context.endElement();
/* 258 */     context.startElement("", "Numberplate");
/* 259 */     context.endAttributes();
/*     */     try {
/* 261 */       context.text(this._Numberplate);
/* 262 */     } catch (Exception e) {
/* 263 */       Util.handlePrintConversionException(this, e, context);
/*     */     } 
/* 265 */     context.endElement();
/* 266 */     if (this._Model instanceof javax.xml.bind.Element) {
/* 267 */       context.childAsElements((XMLSerializable)this._Model);
/*     */     } else {
/* 269 */       context.startElement("", "Model");
/* 270 */       context.childAsAttributes((XMLSerializable)this._Model);
/* 271 */       context.endAttributes();
/* 272 */       context.childAsElements((XMLSerializable)this._Model);
/* 273 */       context.endElement();
/*     */     } 
/* 275 */     if (this._Engine instanceof javax.xml.bind.Element) {
/* 276 */       context.childAsElements((XMLSerializable)this._Engine);
/*     */     } else {
/* 278 */       context.startElement("", "Engine");
/* 279 */       context.childAsAttributes((XMLSerializable)this._Engine);
/* 280 */       context.endAttributes();
/* 281 */       context.childAsElements((XMLSerializable)this._Engine);
/* 282 */       context.endElement();
/*     */     } 
/* 284 */     if (this._Transmission instanceof javax.xml.bind.Element) {
/* 285 */       context.childAsElements((XMLSerializable)this._Transmission);
/*     */     } else {
/* 287 */       context.startElement("", "Transmission");
/* 288 */       context.childAsAttributes((XMLSerializable)this._Transmission);
/* 289 */       context.endAttributes();
/* 290 */       context.childAsElements((XMLSerializable)this._Transmission);
/* 291 */       context.endElement();
/*     */     } 
/* 293 */     context.startElement("", "InspType");
/* 294 */     context.endAttributes();
/*     */     try {
/* 296 */       context.text(this._InspType);
/* 297 */     } catch (Exception e) {
/* 298 */       Util.handlePrintConversionException(this, e, context);
/*     */     } 
/* 300 */     context.endElement();
/* 301 */     if (this._VIN instanceof javax.xml.bind.Element) {
/* 302 */       context.childAsElements((XMLSerializable)this._VIN);
/*     */     } else {
/* 304 */       context.startElement("", "VIN");
/* 305 */       context.childAsAttributes((XMLSerializable)this._VIN);
/* 306 */       context.endAttributes();
/* 307 */       context.childAsElements((XMLSerializable)this._VIN);
/* 308 */       context.endElement();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void serializeAttributes(XMLSerializer context) throws SAXException {}
/*     */ 
/*     */   
/*     */   public void serializeAttributeBodies(XMLSerializer context) throws SAXException {}
/*     */   
/*     */   public Class getPrimaryInterface() {
/* 319 */     return HeaderType.class;
/*     */   }
/*     */   
/*     */   public DocumentDeclaration createRawValidator() {
/* 323 */     return (DocumentDeclaration)new REDocumentDeclaration(schemaFragment);
/*     */   }
/*     */   
/*     */   public class Unmarshaller
/*     */     extends ContentHandlerEx {
/*     */     public Unmarshaller(UnmarshallingContext context) {
/* 329 */       super(context, "------------------------------------------------");
/*     */     }
/*     */     
/*     */     protected UnmarshallableObject owner() {
/* 333 */       return HeaderTypeImpl.this;
/*     */     }
/*     */     
/*     */     public void enterElement(String ___uri, String ___local, Attributes __atts) throws UnreportedException {
/* 337 */       switch (this.state) {
/*     */         case 20:
/* 339 */           if ("" == ___uri && "Date" == ___local) {
/* 340 */             this.context.pushAttributes(__atts);
/* 341 */             this.state = 21;
/*     */             return;
/*     */           } 
/*     */           break;
/*     */         case 32:
/* 346 */           if ("" == ___uri && "Model" == ___local) {
/* 347 */             this.context.pushAttributes(__atts);
/* 348 */             this.state = 33;
/*     */             return;
/*     */           } 
/*     */           break;
/*     */         case 9:
/* 353 */           if ("" == ___uri && "Orderno" == ___local) {
/* 354 */             this.context.pushAttributes(__atts);
/* 355 */             this.state = 12;
/*     */             return;
/*     */           } 
/* 358 */           if ("" == ___uri && "DrvType" == ___local) {
/* 359 */             this.context.pushAttributes(__atts);
/* 360 */             this.state = 10;
/*     */             return;
/*     */           } 
/*     */           break;
/*     */         case 14:
/* 365 */           if ("" == ___uri && "Customer" == ___local) {
/* 366 */             this.context.pushAttributes(__atts);
/* 367 */             this.state = 15;
/*     */             return;
/*     */           } 
/*     */           break;
/*     */         case 38:
/* 372 */           if ("" == ___uri && "Transmission" == ___local) {
/* 373 */             this.context.pushAttributes(__atts);
/* 374 */             this.state = 39;
/*     */             return;
/*     */           } 
/*     */           break;
/*     */         case 23:
/* 379 */           if ("" == ___uri && "Phone" == ___local) {
/* 380 */             this.context.pushAttributes(__atts);
/* 381 */             this.state = 24;
/*     */             return;
/*     */           } 
/*     */           break;
/*     */         case 3:
/* 386 */           if ("" == ___uri && "Title" == ___local) {
/* 387 */             this.context.pushAttributes(__atts);
/* 388 */             this.state = 4;
/*     */             return;
/*     */           } 
/*     */           break;
/*     */         case 39:
/* 393 */           if ("" == ___uri && "Label" == ___local) {
/* 394 */             HeaderTypeImpl.this._Transmission = (TransmissionTypeImpl)spawnChildFromEnterElement(TransmissionTypeImpl.class, 40, ___uri, ___local, __atts);
/*     */             return;
/*     */           } 
/*     */           break;
/*     */         case 33:
/* 399 */           if ("" == ___uri && "Label" == ___local) {
/* 400 */             HeaderTypeImpl.this._Model = (ModelTypeImpl)spawnChildFromEnterElement(ModelTypeImpl.class, 34, ___uri, ___local, __atts);
/*     */             return;
/*     */           } 
/*     */           break;
/*     */         case 44:
/* 405 */           if ("" == ___uri && "VIN" == ___local) {
/* 406 */             this.context.pushAttributes(__atts);
/* 407 */             this.state = 45;
/*     */             return;
/*     */           } 
/*     */           break;
/*     */         case 45:
/* 412 */           if ("" == ___uri && "Label" == ___local) {
/* 413 */             HeaderTypeImpl.this._VIN = (VINTypeImpl)spawnChildFromEnterElement(VINTypeImpl.class, 46, ___uri, ___local, __atts);
/*     */             return;
/*     */           } 
/*     */           break;
/*     */         case 17:
/* 418 */           if ("" == ___uri && "Release" == ___local) {
/* 419 */             this.context.pushAttributes(__atts);
/* 420 */             this.state = 18;
/*     */             return;
/*     */           } 
/*     */           break;
/*     */         case 18:
/* 425 */           if ("" == ___uri && "Label" == ___local) {
/* 426 */             HeaderTypeImpl.this._Release = (ReleaseTypeImpl)spawnChildFromEnterElement(ReleaseTypeImpl.class, 19, ___uri, ___local, __atts);
/*     */             return;
/*     */           } 
/*     */           break;
/*     */         case 26:
/* 431 */           if ("" == ___uri && "Km" == ___local) {
/* 432 */             this.context.pushAttributes(__atts);
/* 433 */             this.state = 27;
/*     */             return;
/*     */           } 
/*     */           break;
/*     */         case 6:
/* 438 */           if ("" == ___uri && "ServType" == ___local) {
/* 439 */             this.context.pushAttributes(__atts);
/* 440 */             this.state = 7;
/*     */             return;
/*     */           } 
/*     */           break;
/*     */         case 47:
/* 445 */           revertToParentFromEnterElement(___uri, ___local, __atts);
/*     */           return;
/*     */         case 29:
/* 448 */           if ("" == ___uri && "Numberplate" == ___local) {
/* 449 */             this.context.pushAttributes(__atts);
/* 450 */             this.state = 30;
/*     */             return;
/*     */           } 
/*     */           break;
/*     */         case 36:
/* 455 */           if ("" == ___uri && "Label" == ___local) {
/* 456 */             HeaderTypeImpl.this._Engine = (EngineTypeImpl)spawnChildFromEnterElement(EngineTypeImpl.class, 37, ___uri, ___local, __atts);
/*     */             return;
/*     */           } 
/*     */           break;
/*     */         case 35:
/* 461 */           if ("" == ___uri && "Engine" == ___local) {
/* 462 */             this.context.pushAttributes(__atts);
/* 463 */             this.state = 36;
/*     */             return;
/*     */           } 
/*     */           break;
/*     */         case 41:
/* 468 */           if ("" == ___uri && "InspType" == ___local) {
/* 469 */             this.context.pushAttributes(__atts);
/* 470 */             this.state = 42;
/*     */             return;
/*     */           } 
/*     */           break;
/*     */         case 0:
/* 475 */           if ("" == ___uri && "Make" == ___local) {
/* 476 */             this.context.pushAttributes(__atts);
/* 477 */             this.state = 1;
/*     */             return;
/*     */           } 
/*     */           break;
/*     */       } 
/* 482 */       super.enterElement(___uri, ___local, __atts);
/*     */     }
/*     */     
/*     */     public void leaveElement(String ___uri, String ___local) throws UnreportedException {
/* 486 */       switch (this.state) {
/*     */         case 37:
/* 488 */           if ("" == ___uri && "Engine" == ___local) {
/* 489 */             this.context.popAttributes();
/* 490 */             this.state = 38;
/*     */             return;
/*     */           } 
/*     */           break;
/*     */         case 46:
/* 495 */           if ("" == ___uri && "VIN" == ___local) {
/* 496 */             this.context.popAttributes();
/* 497 */             this.state = 47;
/*     */             return;
/*     */           } 
/*     */           break;
/*     */         case 34:
/* 502 */           if ("" == ___uri && "Model" == ___local) {
/* 503 */             this.context.popAttributes();
/* 504 */             this.state = 35;
/*     */             return;
/*     */           } 
/*     */           break;
/*     */         case 40:
/* 509 */           if ("" == ___uri && "Transmission" == ___local) {
/* 510 */             this.context.popAttributes();
/* 511 */             this.state = 41;
/*     */             return;
/*     */           } 
/*     */           break;
/*     */         case 11:
/* 516 */           if ("" == ___uri && "DrvType" == ___local) {
/* 517 */             this.context.popAttributes();
/* 518 */             this.state = 9;
/*     */             return;
/*     */           } 
/*     */           break;
/*     */         case 5:
/* 523 */           if ("" == ___uri && "Title" == ___local) {
/* 524 */             this.context.popAttributes();
/* 525 */             this.state = 6;
/*     */             return;
/*     */           } 
/*     */           break;
/*     */         case 8:
/* 530 */           if ("" == ___uri && "ServType" == ___local) {
/* 531 */             this.context.popAttributes();
/* 532 */             this.state = 9;
/*     */             return;
/*     */           } 
/*     */           break;
/*     */         case 25:
/* 537 */           if ("" == ___uri && "Phone" == ___local) {
/* 538 */             this.context.popAttributes();
/* 539 */             this.state = 26;
/*     */             return;
/*     */           } 
/*     */           break;
/*     */         case 31:
/* 544 */           if ("" == ___uri && "Numberplate" == ___local) {
/* 545 */             this.context.popAttributes();
/* 546 */             this.state = 32;
/*     */             return;
/*     */           } 
/*     */           break;
/*     */         case 13:
/* 551 */           if ("" == ___uri && "Orderno" == ___local) {
/* 552 */             this.context.popAttributes();
/* 553 */             this.state = 14;
/*     */             return;
/*     */           } 
/*     */           break;
/*     */         case 47:
/* 558 */           revertToParentFromLeaveElement(___uri, ___local);
/*     */           return;
/*     */         case 28:
/* 561 */           if ("" == ___uri && "Km" == ___local) {
/* 562 */             this.context.popAttributes();
/* 563 */             this.state = 29;
/*     */             return;
/*     */           } 
/*     */           break;
/*     */         case 2:
/* 568 */           if ("" == ___uri && "Make" == ___local) {
/* 569 */             this.context.popAttributes();
/* 570 */             this.state = 3;
/*     */             return;
/*     */           } 
/*     */           break;
/*     */         case 19:
/* 575 */           if ("" == ___uri && "Release" == ___local) {
/* 576 */             this.context.popAttributes();
/* 577 */             this.state = 20;
/*     */             return;
/*     */           } 
/*     */           break;
/*     */         case 43:
/* 582 */           if ("" == ___uri && "InspType" == ___local) {
/* 583 */             this.context.popAttributes();
/* 584 */             this.state = 44;
/*     */             return;
/*     */           } 
/*     */           break;
/*     */         case 22:
/* 589 */           if ("" == ___uri && "Date" == ___local) {
/* 590 */             this.context.popAttributes();
/* 591 */             this.state = 23;
/*     */             return;
/*     */           } 
/*     */           break;
/*     */         case 16:
/* 596 */           if ("" == ___uri && "Customer" == ___local) {
/* 597 */             this.context.popAttributes();
/* 598 */             this.state = 17;
/*     */             return;
/*     */           } 
/*     */           break;
/*     */       } 
/* 603 */       super.leaveElement(___uri, ___local);
/*     */     }
/*     */     
/*     */     public void enterAttribute(String ___uri, String ___local) throws UnreportedException {
/* 607 */       switch (this.state) {
/*     */         case 47:
/* 609 */           revertToParentFromEnterAttribute(___uri, ___local);
/*     */           return;
/*     */       } 
/* 612 */       super.enterAttribute(___uri, ___local);
/*     */     }
/*     */     
/*     */     public void leaveAttribute(String ___uri, String ___local) throws UnreportedException {
/* 616 */       switch (this.state) {
/*     */         case 47:
/* 618 */           revertToParentFromLeaveAttribute(___uri, ___local);
/*     */           return;
/*     */       } 
/* 621 */       super.leaveAttribute(___uri, ___local);
/*     */     }
/*     */     
/*     */     public void text(String value) throws UnreportedException {
/*     */       try {
/* 626 */         switch (this.state) {
/*     */           case 42:
/*     */             try {
/* 629 */               HeaderTypeImpl.this._InspType = value;
/* 630 */             } catch (Exception e) {
/* 631 */               handleParseConversionException(e);
/*     */             } 
/* 633 */             this.state = 43;
/*     */             return;
/*     */           case 15:
/*     */             try {
/* 637 */               HeaderTypeImpl.this._Customer = value;
/* 638 */             } catch (Exception e) {
/* 639 */               handleParseConversionException(e);
/*     */             } 
/* 641 */             this.state = 16;
/*     */             return;
/*     */           case 7:
/*     */             try {
/* 645 */               HeaderTypeImpl.this._ServType = value;
/* 646 */             } catch (Exception e) {
/* 647 */               handleParseConversionException(e);
/*     */             } 
/* 649 */             this.state = 8;
/*     */             return;
/*     */           case 4:
/*     */             try {
/* 653 */               HeaderTypeImpl.this._Title = value;
/* 654 */             } catch (Exception e) {
/* 655 */               handleParseConversionException(e);
/*     */             } 
/* 657 */             this.state = 5;
/*     */             return;
/*     */           case 21:
/*     */             try {
/* 661 */               HeaderTypeImpl.this._Date = value;
/* 662 */             } catch (Exception e) {
/* 663 */               handleParseConversionException(e);
/*     */             } 
/* 665 */             this.state = 22;
/*     */             return;
/*     */           case 1:
/*     */             try {
/* 669 */               HeaderTypeImpl.this._Make = value;
/* 670 */             } catch (Exception e) {
/* 671 */               handleParseConversionException(e);
/*     */             } 
/* 673 */             this.state = 2;
/*     */             return;
/*     */           case 30:
/*     */             try {
/* 677 */               HeaderTypeImpl.this._Numberplate = value;
/* 678 */             } catch (Exception e) {
/* 679 */               handleParseConversionException(e);
/*     */             } 
/* 681 */             this.state = 31;
/*     */             return;
/*     */           case 27:
/*     */             try {
/* 685 */               HeaderTypeImpl.this._Km = value;
/* 686 */             } catch (Exception e) {
/* 687 */               handleParseConversionException(e);
/*     */             } 
/* 689 */             this.state = 28;
/*     */             return;
/*     */           case 10:
/*     */             try {
/* 693 */               HeaderTypeImpl.this._DrvType = value;
/* 694 */             } catch (Exception e) {
/* 695 */               handleParseConversionException(e);
/*     */             } 
/* 697 */             this.state = 11;
/*     */             return;
/*     */           case 24:
/*     */             try {
/* 701 */               HeaderTypeImpl.this._Phone = value;
/* 702 */             } catch (Exception e) {
/* 703 */               handleParseConversionException(e);
/*     */             } 
/* 705 */             this.state = 25;
/*     */             return;
/*     */           case 47:
/* 708 */             revertToParentFromText(value);
/*     */             return;
/*     */           case 12:
/*     */             try {
/* 712 */               HeaderTypeImpl.this._Orderno = value;
/* 713 */             } catch (Exception e) {
/* 714 */               handleParseConversionException(e);
/*     */             } 
/* 716 */             this.state = 13;
/*     */             return;
/*     */         } 
/* 719 */       } catch (RuntimeException e) {
/* 720 */         handleUnexpectedTextException(value, e);
/*     */       } 
/*     */     }
/*     */     
/*     */     public void leaveChild(int nextState) throws UnreportedException {
/* 725 */       switch (nextState) {
/*     */         case 37:
/* 727 */           this.state = 37;
/*     */           return;
/*     */         case 46:
/* 730 */           this.state = 46;
/*     */           return;
/*     */         case 19:
/* 733 */           this.state = 19;
/*     */           return;
/*     */         case 34:
/* 736 */           this.state = 34;
/*     */           return;
/*     */         case 40:
/* 739 */           this.state = 40;
/*     */           return;
/*     */       } 
/* 742 */       super.leaveChild(nextState);
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\lt\implementation\io\icl\model\xml\impl\HeaderTypeImpl.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */