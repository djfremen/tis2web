/*     */ package com.eoos.gm.tis2web.registration.common.xml.impl;
/*     */ import com.eoos.gm.tis2web.registration.common.xml.SOFTWAREKEY;
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
/*     */ public class SOFTWAREKEYImpl extends SOFTWAREKEYTypeImpl implements SOFTWAREKEY, RIElement, UnmarshallableObject, XMLSerializable, ValidatableObject {
/*  15 */   private static final Grammar schemaFragment = SchemaDeserializer.deserialize("¬í\000\005sr\000'com.sun.msv.grammar.trex.ElementPattern\000\000\000\000\000\000\000\001\002\000\001L\000\tnameClasst\000\037Lcom/sun/msv/grammar/NameClass;xr\000\036com.sun.msv.grammar.ElementExp\000\000\000\000\000\000\000\001\002\000\002Z\000\032ignoreUndeclaredAttributesL\000\fcontentModelt\000 Lcom/sun/msv/grammar/Expression;xr\000\036com.sun.msv.grammar.Expressionø\030èN5~O\002\000\003I\000\016cachedHashCodeL\000\023epsilonReducibilityt\000\023Ljava/lang/Boolean;L\000\013expandedExpq\000~\000\003xp\r:\013vpp\000sr\000\035com.sun.msv.grammar.ChoiceExp\000\000\000\000\000\000\000\001\002\000\000xr\000\035com.sun.msv.grammar.BinaryExp\000\000\000\000\000\000\000\001\002\000\002L\000\004exp1q\000~\000\003L\000\004exp2q\000~\000\003xq\000~\000\004\r:\013kppsr\000\037com.sun.msv.grammar.SequenceExp\000\000\000\000\000\000\000\001\002\000\000xq\000~\000\b\r:\013`sr\000\021java.lang.BooleanÍ rÕúî\002\000\001Z\000\005valuexp\000psq\000~\000\007\000Ó §q\000~\000\rpsq\000~\000\000\000iÐXq\000~\000\rp\000sq\000~\000\000\000iÐMpp\000sq\000~\000\007\000iÐBppsr\000 com.sun.msv.grammar.OneOrMoreExp\000\000\000\000\000\000\000\001\002\000\000xr\000\034com.sun.msv.grammar.UnaryExp\000\000\000\000\000\000\000\001\002\000\001L\000\003expq\000~\000\003xq\000~\000\004\000iÐ7q\000~\000\rpsr\000 com.sun.msv.grammar.AttributeExp\000\000\000\000\000\000\000\001\002\000\002L\000\003expq\000~\000\003L\000\tnameClassq\000~\000\001xq\000~\000\004\000iÐ4q\000~\000\rpsr\0002com.sun.msv.grammar.Expression$AnyStringExpression\000\000\000\000\000\000\000\001\002\000\000xq\000~\000\004\000\000\000\bsq\000~\000\f\001psr\000 com.sun.msv.grammar.AnyNameClass\000\000\000\000\000\000\000\001\002\000\000xr\000\035com.sun.msv.grammar.NameClass\000\000\000\000\000\000\000\001\002\000\000xpsr\0000com.sun.msv.grammar.Expression$EpsilonExpression\000\000\000\000\000\000\000\001\002\000\000xq\000~\000\004\000\000\000\tq\000~\000\031psr\000#com.sun.msv.grammar.SimpleNameClass\000\000\000\000\000\000\000\001\002\000\002L\000\tlocalNamet\000\022Ljava/lang/String;L\000\fnamespaceURIq\000~\000 xq\000~\000\033t\0005com.eoos.gm.tis2web.registration.common.xml.CHUNKTypet\000+http://java.sun.com/jaxb/xjc/dummy-elementssq\000~\000\037t\000\005CHUNKt\000\000sq\000~\000\000\000iÐMq\000~\000\rp\000sq\000~\000\007\000iÐBppsq\000~\000\022\000iÐ7q\000~\000\rpsq\000~\000\025\000iÐ4q\000~\000\rpq\000~\000\030q\000~\000\034q\000~\000\036sq\000~\000\037t\0001com.eoos.gm.tis2web.registration.common.xml.CHUNKq\000~\000#sq\000~\000\007\ffj´ppsq\000~\000\n\ffj©q\000~\000\rpsq\000~\000\007\000Ó §q\000~\000\rpsq\000~\000\000\000iÐXq\000~\000\rp\000sq\000~\000\000\000iÐMpp\000sq\000~\000\007\000iÐBppsq\000~\000\022\000iÐ7q\000~\000\rpsq\000~\000\025\000iÐ4q\000~\000\rpq\000~\000\030q\000~\000\034q\000~\000\036sq\000~\000\037q\000~\000\"q\000~\000#q\000~\000$sq\000~\000\000\000iÐMq\000~\000\rp\000sq\000~\000\007\000iÐBppsq\000~\000\022\000iÐ7q\000~\000\rpsq\000~\000\025\000iÐ4q\000~\000\rpq\000~\000\030q\000~\000\034q\000~\000\036sq\000~\000\037q\000~\000,q\000~\000#sq\000~\000\007\013Éýppsq\000~\000\n\013Éòq\000~\000\rpsq\000~\000\007\000Ó §q\000~\000\rpsq\000~\000\000\000iÐXq\000~\000\rp\000sq\000~\000\000\000iÐMpp\000sq\000~\000\007\000iÐBppsq\000~\000\022\000iÐ7q\000~\000\rpsq\000~\000\025\000iÐ4q\000~\000\rpq\000~\000\030q\000~\000\034q\000~\000\036sq\000~\000\037q\000~\000\"q\000~\000#q\000~\000$sq\000~\000\000\000iÐMq\000~\000\rp\000sq\000~\000\007\000iÐBppsq\000~\000\022\000iÐ7q\000~\000\rpsq\000~\000\025\000iÐ4q\000~\000\rpq\000~\000\030q\000~\000\034q\000~\000\036sq\000~\000\037q\000~\000,q\000~\000#sq\000~\000\007\n¿)Fppsq\000~\000\n\n¿);q\000~\000\rpsq\000~\000\007\000Ó §q\000~\000\rpsq\000~\000\000\000iÐXq\000~\000\rp\000sq\000~\000\000\000iÐMpp\000sq\000~\000\007\000iÐBppsq\000~\000\022\000iÐ7q\000~\000\rpsq\000~\000\025\000iÐ4q\000~\000\rpq\000~\000\030q\000~\000\034q\000~\000\036sq\000~\000\037q\000~\000\"q\000~\000#q\000~\000$sq\000~\000\000\000iÐMq\000~\000\rp\000sq\000~\000\007\000iÐBppsq\000~\000\022\000iÐ7q\000~\000\rpsq\000~\000\025\000iÐ4q\000~\000\rpq\000~\000\030q\000~\000\034q\000~\000\036sq\000~\000\037q\000~\000,q\000~\000#sq\000~\000\007\tëppsq\000~\000\n\tëq\000~\000\rpsq\000~\000\007\000Ó §q\000~\000\rpsq\000~\000\000\000iÐXq\000~\000\rp\000sq\000~\000\000\000iÐMpp\000sq\000~\000\007\000iÐBppsq\000~\000\022\000iÐ7q\000~\000\rpsq\000~\000\025\000iÐ4q\000~\000\rpq\000~\000\030q\000~\000\034q\000~\000\036sq\000~\000\037q\000~\000\"q\000~\000#q\000~\000$sq\000~\000\000\000iÐMq\000~\000\rp\000sq\000~\000\007\000iÐBppsq\000~\000\022\000iÐ7q\000~\000\rpsq\000~\000\025\000iÐ4q\000~\000\rpq\000~\000\030q\000~\000\034q\000~\000\036sq\000~\000\037q\000~\000,q\000~\000#sq\000~\000\007\t\027çØppsq\000~\000\n\t\027çÍq\000~\000\rpsq\000~\000\007\000Ó §q\000~\000\rpsq\000~\000\000\000iÐXq\000~\000\rp\000sq\000~\000\000\000iÐMpp\000sq\000~\000\007\000iÐBppsq\000~\000\022\000iÐ7q\000~\000\rpsq\000~\000\025\000iÐ4q\000~\000\rpq\000~\000\030q\000~\000\034q\000~\000\036sq\000~\000\037q\000~\000\"q\000~\000#q\000~\000$sq\000~\000\000\000iÐMq\000~\000\rp\000sq\000~\000\007\000iÐBppsq\000~\000\022\000iÐ7q\000~\000\rpsq\000~\000\025\000iÐ4q\000~\000\rpq\000~\000\030q\000~\000\034q\000~\000\036sq\000~\000\037q\000~\000,q\000~\000#sq\000~\000\007\bDG!ppsq\000~\000\n\bDG\026q\000~\000\rpsq\000~\000\007\000Ó §q\000~\000\rpsq\000~\000\000\000iÐXq\000~\000\rp\000sq\000~\000\000\000iÐMpp\000sq\000~\000\007\000iÐBppsq\000~\000\022\000iÐ7q\000~\000\rpsq\000~\000\025\000iÐ4q\000~\000\rpq\000~\000\030q\000~\000\034q\000~\000\036sq\000~\000\037q\000~\000\"q\000~\000#q\000~\000$sq\000~\000\000\000iÐMq\000~\000\rp\000sq\000~\000\007\000iÐBppsq\000~\000\022\000iÐ7q\000~\000\rpsq\000~\000\025\000iÐ4q\000~\000\rpq\000~\000\030q\000~\000\034q\000~\000\036sq\000~\000\037q\000~\000,q\000~\000#sq\000~\000\007\007p¦jppsq\000~\000\n\007p¦_q\000~\000\rpsq\000~\000\007\000Ó §q\000~\000\rpsq\000~\000\000\000iÐXq\000~\000\rp\000sq\000~\000\000\000iÐMpp\000sq\000~\000\007\000iÐBppsq\000~\000\022\000iÐ7q\000~\000\rpsq\000~\000\025\000iÐ4q\000~\000\rpq\000~\000\030q\000~\000\034q\000~\000\036sq\000~\000\037q\000~\000\"q\000~\000#q\000~\000$sq\000~\000\000\000iÐMq\000~\000\rp\000sq\000~\000\007\000iÐBppsq\000~\000\022\000iÐ7q\000~\000\rpsq\000~\000\025\000iÐ4q\000~\000\rpq\000~\000\030q\000~\000\034q\000~\000\036sq\000~\000\037q\000~\000,q\000~\000#sq\000~\000\007\006\005³ppsq\000~\000\n\006\005¨q\000~\000\rpsq\000~\000\007\000Ó §q\000~\000\rpsq\000~\000\000\000iÐXq\000~\000\rp\000sq\000~\000\000\000iÐMpp\000sq\000~\000\007\000iÐBppsq\000~\000\022\000iÐ7q\000~\000\rpsq\000~\000\025\000iÐ4q\000~\000\rpq\000~\000\030q\000~\000\034q\000~\000\036sq\000~\000\037q\000~\000\"q\000~\000#q\000~\000$sq\000~\000\000\000iÐMq\000~\000\rp\000sq\000~\000\007\000iÐBppsq\000~\000\022\000iÐ7q\000~\000\rpsq\000~\000\025\000iÐ4q\000~\000\rpq\000~\000\030q\000~\000\034q\000~\000\036sq\000~\000\037q\000~\000,q\000~\000#sq\000~\000\007\005Édüppsq\000~\000\n\005Édñq\000~\000\rpsq\000~\000\007\000Ó §q\000~\000\rpsq\000~\000\000\000iÐXq\000~\000\rp\000sq\000~\000\000\000iÐMpp\000sq\000~\000\007\000iÐBppsq\000~\000\022\000iÐ7q\000~\000\rpsq\000~\000\025\000iÐ4q\000~\000\rpq\000~\000\030q\000~\000\034q\000~\000\036sq\000~\000\037q\000~\000\"q\000~\000#q\000~\000$sq\000~\000\000\000iÐMq\000~\000\rp\000sq\000~\000\007\000iÐBppsq\000~\000\022\000iÐ7q\000~\000\rpsq\000~\000\025\000iÐ4q\000~\000\rpq\000~\000\030q\000~\000\034q\000~\000\036sq\000~\000\037q\000~\000,q\000~\000#sq\000~\000\007\004õÄEppsq\000~\000\n\004õÄ:q\000~\000\rpsq\000~\000\007\000Ó §q\000~\000\rpsq\000~\000\000\000iÐXq\000~\000\rp\000sq\000~\000\000\000iÐMpp\000sq\000~\000\007\000iÐBppsq\000~\000\022\000iÐ7q\000~\000\rpsq\000~\000\025\000iÐ4q\000~\000\rpq\000~\000\030q\000~\000\034q\000~\000\036sq\000~\000\037q\000~\000\"q\000~\000#q\000~\000$sq\000~\000\000\000iÐMq\000~\000\rp\000sq\000~\000\007\000iÐBppsq\000~\000\022\000iÐ7q\000~\000\rpsq\000~\000\025\000iÐ4q\000~\000\rpq\000~\000\030q\000~\000\034q\000~\000\036sq\000~\000\037q\000~\000,q\000~\000#sq\000~\000\007\004\"#ppsq\000~\000\n\004\"#q\000~\000\rpsq\000~\000\007\000Ó §q\000~\000\rpsq\000~\000\000\000iÐXq\000~\000\rp\000sq\000~\000\000\000iÐMpp\000sq\000~\000\007\000iÐBppsq\000~\000\022\000iÐ7q\000~\000\rpsq\000~\000\025\000iÐ4q\000~\000\rpq\000~\000\030q\000~\000\034q\000~\000\036sq\000~\000\037q\000~\000\"q\000~\000#q\000~\000$sq\000~\000\000\000iÐMq\000~\000\rp\000sq\000~\000\007\000iÐBppsq\000~\000\022\000iÐ7q\000~\000\rpsq\000~\000\025\000iÐ4q\000~\000\rpq\000~\000\030q\000~\000\034q\000~\000\036sq\000~\000\037q\000~\000,q\000~\000#sq\000~\000\007\003N×ppsq\000~\000\n\003NÌq\000~\000\rpsq\000~\000\007\000Ó §q\000~\000\rpsq\000~\000\000\000iÐXq\000~\000\rp\000sq\000~\000\000\000iÐMpp\000sq\000~\000\007\000iÐBppsq\000~\000\022\000iÐ7q\000~\000\rpsq\000~\000\025\000iÐ4q\000~\000\rpq\000~\000\030q\000~\000\034q\000~\000\036sq\000~\000\037q\000~\000\"q\000~\000#q\000~\000$sq\000~\000\000\000iÐMq\000~\000\rp\000sq\000~\000\007\000iÐBppsq\000~\000\022\000iÐ7q\000~\000\rpsq\000~\000\025\000iÐ4q\000~\000\rpq\000~\000\030q\000~\000\034q\000~\000\036sq\000~\000\037q\000~\000,q\000~\000#sq\000~\000\007\002zâ ppsq\000~\000\n\002zâ\025q\000~\000\rpsq\000~\000\007\000Ó §q\000~\000\rpsq\000~\000\000\000iÐXq\000~\000\rp\000sq\000~\000\000\000iÐMpp\000sq\000~\000\007\000iÐBppsq\000~\000\022\000iÐ7q\000~\000\rpsq\000~\000\025\000iÐ4q\000~\000\rpq\000~\000\030q\000~\000\034q\000~\000\036sq\000~\000\037q\000~\000\"q\000~\000#q\000~\000$sq\000~\000\000\000iÐMq\000~\000\rp\000sq\000~\000\007\000iÐBppsq\000~\000\022\000iÐ7q\000~\000\rpsq\000~\000\025\000iÐ4q\000~\000\rpq\000~\000\030q\000~\000\034q\000~\000\036sq\000~\000\037q\000~\000,q\000~\000#sq\000~\000\007\001§Aippsq\000~\000\n\001§A^q\000~\000\rpsq\000~\000\007\000Ó §q\000~\000\rpsq\000~\000\000\000iÐXq\000~\000\rp\000sq\000~\000\000\000iÐMpp\000sq\000~\000\007\000iÐBppsq\000~\000\022\000iÐ7q\000~\000\rpsq\000~\000\025\000iÐ4q\000~\000\rpq\000~\000\030q\000~\000\034q\000~\000\036sq\000~\000\037q\000~\000\"q\000~\000#q\000~\000$sq\000~\000\000\000iÐMq\000~\000\rp\000sq\000~\000\007\000iÐBppsq\000~\000\022\000iÐ7q\000~\000\rpsq\000~\000\025\000iÐ4q\000~\000\rpq\000~\000\030q\000~\000\034q\000~\000\036sq\000~\000\037q\000~\000,q\000~\000#sq\000~\000\007\000Ó ²ppsq\000~\000\007\000Ó §q\000~\000\rpsq\000~\000\000\000iÐXq\000~\000\rp\000sq\000~\000\000\000iÐMpp\000sq\000~\000\007\000iÐBppsq\000~\000\022\000iÐ7q\000~\000\rpsq\000~\000\025\000iÐ4q\000~\000\rpq\000~\000\030q\000~\000\034q\000~\000\036sq\000~\000\037q\000~\000\"q\000~\000#q\000~\000$sq\000~\000\000\000iÐMq\000~\000\rp\000sq\000~\000\007\000iÐBppsq\000~\000\022\000iÐ7q\000~\000\rpsq\000~\000\025\000iÐ4q\000~\000\rpq\000~\000\030q\000~\000\034q\000~\000\036sq\000~\000\037q\000~\000,q\000~\000#q\000~\000\036q\000~\000\036q\000~\000\036q\000~\000\036q\000~\000\036q\000~\000\036q\000~\000\036q\000~\000\036q\000~\000\036q\000~\000\036q\000~\000\036q\000~\000\036q\000~\000\036q\000~\000\036q\000~\000\036q\000~\000\036sq\000~\000\037t\000\fSOFTWARE_KEYq\000~\000&sr\000\"com.sun.msv.grammar.ExpressionPool\000\000\000\000\000\000\000\001\002\000\001L\000\bexpTablet\000/Lcom/sun/msv/grammar/ExpressionPool$ClosedHash;xpsr\000-com.sun.msv.grammar.ExpressionPool$ClosedHash×jÐNïèí\034\002\000\004I\000\005countI\000\tthresholdL\000\006parentq\000~\001\001[\000\005tablet\000![Lcom/sun/msv/grammar/Expression;xp\000\000\000o\000\000\000rpur\000![Lcom.sun.msv.grammar.Expression;Ö8DÃ]­§\n\002\000\000xp\000\000\001q\000~\000q\000~\000q\000~\000q\000~\000q\000~\000}q\000~\000xq\000~\000oq\000~\000jq\000~\000kq\000~\000aq\000~\000bq\000~\000\\q\000~\000]q\000~\000Sq\000~\000Nq\000~\000Eq\000~\000@q\000~\0007q\000~\0002q\000~\000(q\000~\000\021q\000~\000Tq\000~\000Oq\000~\000Fq\000~\000Aq\000~\0008q\000~\0003q\000~\000q\000~\000q\000~\000q\000~\000uq\000~\000gq\000~\000Yq\000~\000Kq\000~\000=q\000~\000/q\000~\000\016q\000~\000)q\000~\000\024q\000~\000¾q\000~\000¿q\000~\000­q\000~\000Äq\000~\000Ãq\000~\000»q\000~\000Íq\000~\000Ìq\000~\000Òq\000~\000Ñq\000~\000Éq\000~\000Ûq\000~\000Úq\000~\000àq\000~\000ßq\000~\000×q\000~\000éq\000~\000èq\000~\000îq\000~\000íq\000~\000åq\000~\000öq\000~\000õq\000~\000ûq\000~\000úq\000~\000òq\000~\000ñq\000~\000q\000~\000q\000~\000tq\000~\000sq\000~\000äpppq\000~\000fppppppq\000~\000ãpppq\000~\000epppppppppppppppppppppppppppq\000~\000Öpppq\000~\000Xppppppq\000~\000Õpppq\000~\000Wpppppppppppppppppppppppppppq\000~\000Èpppq\000~\000Jppppppq\000~\000Çpppq\000~\000Ipppppppppppppppppppppppppppq\000~\000ºpppq\000~\000<ppppppq\000~\000¹pppq\000~\000;pppppppppppppppppppppppppppq\000~\000¬pppq\000~\000.ppppppq\000~\000«pppq\000~\000-pppppppppppppppppppppppppppq\000~\000pppq\000~\000\013ppppppq\000~\000pppq\000~\000\tpppppppppppppppppppppppppppq\000~\000ppppppppppq\000~\000ppppppppppppppppppppppppppppq\000~\000¶q\000~\000±q\000~\000¨q\000~\000£q\000~\000q\000~\000q\000~\000q\000~\000q\000~\000~q\000~\000yq\000~\000pq\000~\000µq\000~\000°q\000~\000§q\000~\000¢");
/*     */ 
/*     */   
/*     */   public String ____jaxb_ri____getNamespaceURI() {
/*  19 */     return "";
/*     */   }
/*     */   
/*     */   public String ____jaxb_ri____getLocalName() {
/*  23 */     return "SOFTWARE_KEY";
/*     */   }
/*     */   
/*     */   private static final Class PRIMARY_INTERFACE_CLASS() {
/*  27 */     return SOFTWAREKEY.class;
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
/*  39 */     context.startElement("", "SOFTWARE_KEY");
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
/*  53 */     return SOFTWAREKEY.class;
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
/*  67 */       return SOFTWAREKEYImpl.this;
/*     */     }
/*     */     
/*     */     public void enterElement(String ___uri, String ___local, Attributes __atts) throws UnreportedException {
/*  71 */       switch (this.state) {
/*     */         case 0:
/*  73 */           if ("" == ___uri && "SOFTWARE_KEY" == ___local) {
/*  74 */             this.context.pushAttributes(__atts);
/*  75 */             this.state = 1;
/*     */             return;
/*     */           } 
/*     */           break;
/*     */         case 1:
/*  80 */           if ("" == ___uri && "CHUNK" == ___local) {
/*  81 */             SOFTWAREKEYImpl.this.getClass(); spawnSuperClassFromEnterElement(new SOFTWAREKEYTypeImpl.Unmarshaller(SOFTWAREKEYImpl.this, this.context), 2, ___uri, ___local, __atts);
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
/*     */         case 1:
/*  95 */           if ("" == ___uri && "SOFTWARE_KEY" == ___local) {
/*  96 */             SOFTWAREKEYImpl.this.getClass(); spawnSuperClassFromLeaveElement(new SOFTWAREKEYTypeImpl.Unmarshaller(SOFTWAREKEYImpl.this, this.context), 2, ___uri, ___local);
/*     */             return;
/*     */           } 
/*     */           break;
/*     */         case 3:
/* 101 */           revertToParentFromLeaveElement(___uri, ___local);
/*     */           return;
/*     */         case 2:
/* 104 */           if ("" == ___uri && "SOFTWARE_KEY" == ___local) {
/* 105 */             this.context.popAttributes();
/* 106 */             this.state = 3;
/*     */             return;
/*     */           } 
/*     */           break;
/*     */       } 
/* 111 */       super.leaveElement(___uri, ___local);
/*     */     }
/*     */     
/*     */     public void enterAttribute(String ___uri, String ___local) throws UnreportedException {
/* 115 */       switch (this.state) {
/*     */         case 3:
/* 117 */           revertToParentFromEnterAttribute(___uri, ___local);
/*     */           return;
/*     */       } 
/* 120 */       super.enterAttribute(___uri, ___local);
/*     */     }
/*     */     
/*     */     public void leaveAttribute(String ___uri, String ___local) throws UnreportedException {
/* 124 */       switch (this.state) {
/*     */         case 3:
/* 126 */           revertToParentFromLeaveAttribute(___uri, ___local);
/*     */           return;
/*     */       } 
/* 129 */       super.leaveAttribute(___uri, ___local);
/*     */     }
/*     */     
/*     */     public void text(String value) throws UnreportedException {
/*     */       try {
/* 134 */         switch (this.state) {
/*     */           case 3:
/* 136 */             revertToParentFromText(value);
/*     */             return;
/*     */         } 
/* 139 */       } catch (RuntimeException e) {
/* 140 */         handleUnexpectedTextException(value, e);
/*     */       } 
/*     */     }
/*     */     
/*     */     public void leaveChild(int nextState) throws UnreportedException {
/* 145 */       switch (nextState) {
/*     */         case 2:
/* 147 */           this.state = 2;
/*     */           return;
/*     */       } 
/* 150 */       super.leaveChild(nextState);
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\registration\common\xml\impl\SOFTWAREKEYImpl.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */