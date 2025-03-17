/*     */ package com.eoos.gm.tis2web.registration.common.xml.impl;
/*     */ import com.eoos.gm.tis2web.registration.common.xml.LICENSEKEYType;
/*     */ import com.sun.msv.verifier.DocumentDeclaration;
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
/*     */ public class LICENSEKEYTypeImpl implements LICENSEKEYType, UnmarshallableObject, XMLSerializable, ValidatableObject {
/*  15 */   protected ListImpl _CHUNK = new ListImpl(new ArrayList());
/*  16 */   private static final Grammar schemaFragment = SchemaDeserializer.deserialize("¬í\000\005sr\000\035com.sun.msv.grammar.ChoiceExp\000\000\000\000\000\000\000\001\002\000\000xr\000\035com.sun.msv.grammar.BinaryExp\000\000\000\000\000\000\000\001\002\000\002L\000\004exp1t\000 Lcom/sun/msv/grammar/Expression;L\000\004exp2q\000~\000\002xr\000\036com.sun.msv.grammar.Expressionø\030èN5~O\002\000\003I\000\016cachedHashCodeL\000\023epsilonReducibilityt\000\023Ljava/lang/Boolean;L\000\013expandedExpq\000~\000\002xp\r:\013kppsr\000\037com.sun.msv.grammar.SequenceExp\000\000\000\000\000\000\000\001\002\000\000xq\000~\000\001\r:\013`sr\000\021java.lang.BooleanÍ rÕúî\002\000\001Z\000\005valuexp\000psq\000~\000\000\000Ó §q\000~\000\tpsr\000'com.sun.msv.grammar.trex.ElementPattern\000\000\000\000\000\000\000\001\002\000\001L\000\tnameClasst\000\037Lcom/sun/msv/grammar/NameClass;xr\000\036com.sun.msv.grammar.ElementExp\000\000\000\000\000\000\000\001\002\000\002Z\000\032ignoreUndeclaredAttributesL\000\fcontentModelq\000~\000\002xq\000~\000\003\000iÐXq\000~\000\tp\000sq\000~\000\013\000iÐMpp\000sq\000~\000\000\000iÐBppsr\000 com.sun.msv.grammar.OneOrMoreExp\000\000\000\000\000\000\000\001\002\000\000xr\000\034com.sun.msv.grammar.UnaryExp\000\000\000\000\000\000\000\001\002\000\001L\000\003expq\000~\000\002xq\000~\000\003\000iÐ7q\000~\000\tpsr\000 com.sun.msv.grammar.AttributeExp\000\000\000\000\000\000\000\001\002\000\002L\000\003expq\000~\000\002L\000\tnameClassq\000~\000\fxq\000~\000\003\000iÐ4q\000~\000\tpsr\0002com.sun.msv.grammar.Expression$AnyStringExpression\000\000\000\000\000\000\000\001\002\000\000xq\000~\000\003\000\000\000\bsq\000~\000\b\001psr\000 com.sun.msv.grammar.AnyNameClass\000\000\000\000\000\000\000\001\002\000\000xr\000\035com.sun.msv.grammar.NameClass\000\000\000\000\000\000\000\001\002\000\000xpsr\0000com.sun.msv.grammar.Expression$EpsilonExpression\000\000\000\000\000\000\000\001\002\000\000xq\000~\000\003\000\000\000\tq\000~\000\030psr\000#com.sun.msv.grammar.SimpleNameClass\000\000\000\000\000\000\000\001\002\000\002L\000\tlocalNamet\000\022Ljava/lang/String;L\000\fnamespaceURIq\000~\000\037xq\000~\000\032t\0005com.eoos.gm.tis2web.registration.common.xml.CHUNKTypet\000+http://java.sun.com/jaxb/xjc/dummy-elementssq\000~\000\036t\000\005CHUNKt\000\000sq\000~\000\013\000iÐMq\000~\000\tp\000sq\000~\000\000\000iÐBppsq\000~\000\021\000iÐ7q\000~\000\tpsq\000~\000\024\000iÐ4q\000~\000\tpq\000~\000\027q\000~\000\033q\000~\000\035sq\000~\000\036t\0001com.eoos.gm.tis2web.registration.common.xml.CHUNKq\000~\000\"sq\000~\000\000\ffj´ppsq\000~\000\006\ffj©q\000~\000\tpsq\000~\000\000\000Ó §q\000~\000\tpsq\000~\000\013\000iÐXq\000~\000\tp\000sq\000~\000\013\000iÐMpp\000sq\000~\000\000\000iÐBppsq\000~\000\021\000iÐ7q\000~\000\tpsq\000~\000\024\000iÐ4q\000~\000\tpq\000~\000\027q\000~\000\033q\000~\000\035sq\000~\000\036q\000~\000!q\000~\000\"q\000~\000#sq\000~\000\013\000iÐMq\000~\000\tp\000sq\000~\000\000\000iÐBppsq\000~\000\021\000iÐ7q\000~\000\tpsq\000~\000\024\000iÐ4q\000~\000\tpq\000~\000\027q\000~\000\033q\000~\000\035sq\000~\000\036q\000~\000+q\000~\000\"sq\000~\000\000\013Éýppsq\000~\000\006\013Éòq\000~\000\tpsq\000~\000\000\000Ó §q\000~\000\tpsq\000~\000\013\000iÐXq\000~\000\tp\000sq\000~\000\013\000iÐMpp\000sq\000~\000\000\000iÐBppsq\000~\000\021\000iÐ7q\000~\000\tpsq\000~\000\024\000iÐ4q\000~\000\tpq\000~\000\027q\000~\000\033q\000~\000\035sq\000~\000\036q\000~\000!q\000~\000\"q\000~\000#sq\000~\000\013\000iÐMq\000~\000\tp\000sq\000~\000\000\000iÐBppsq\000~\000\021\000iÐ7q\000~\000\tpsq\000~\000\024\000iÐ4q\000~\000\tpq\000~\000\027q\000~\000\033q\000~\000\035sq\000~\000\036q\000~\000+q\000~\000\"sq\000~\000\000\n¿)Fppsq\000~\000\006\n¿);q\000~\000\tpsq\000~\000\000\000Ó §q\000~\000\tpsq\000~\000\013\000iÐXq\000~\000\tp\000sq\000~\000\013\000iÐMpp\000sq\000~\000\000\000iÐBppsq\000~\000\021\000iÐ7q\000~\000\tpsq\000~\000\024\000iÐ4q\000~\000\tpq\000~\000\027q\000~\000\033q\000~\000\035sq\000~\000\036q\000~\000!q\000~\000\"q\000~\000#sq\000~\000\013\000iÐMq\000~\000\tp\000sq\000~\000\000\000iÐBppsq\000~\000\021\000iÐ7q\000~\000\tpsq\000~\000\024\000iÐ4q\000~\000\tpq\000~\000\027q\000~\000\033q\000~\000\035sq\000~\000\036q\000~\000+q\000~\000\"sq\000~\000\000\tëppsq\000~\000\006\tëq\000~\000\tpsq\000~\000\000\000Ó §q\000~\000\tpsq\000~\000\013\000iÐXq\000~\000\tp\000sq\000~\000\013\000iÐMpp\000sq\000~\000\000\000iÐBppsq\000~\000\021\000iÐ7q\000~\000\tpsq\000~\000\024\000iÐ4q\000~\000\tpq\000~\000\027q\000~\000\033q\000~\000\035sq\000~\000\036q\000~\000!q\000~\000\"q\000~\000#sq\000~\000\013\000iÐMq\000~\000\tp\000sq\000~\000\000\000iÐBppsq\000~\000\021\000iÐ7q\000~\000\tpsq\000~\000\024\000iÐ4q\000~\000\tpq\000~\000\027q\000~\000\033q\000~\000\035sq\000~\000\036q\000~\000+q\000~\000\"sq\000~\000\000\t\027çØppsq\000~\000\006\t\027çÍq\000~\000\tpsq\000~\000\000\000Ó §q\000~\000\tpsq\000~\000\013\000iÐXq\000~\000\tp\000sq\000~\000\013\000iÐMpp\000sq\000~\000\000\000iÐBppsq\000~\000\021\000iÐ7q\000~\000\tpsq\000~\000\024\000iÐ4q\000~\000\tpq\000~\000\027q\000~\000\033q\000~\000\035sq\000~\000\036q\000~\000!q\000~\000\"q\000~\000#sq\000~\000\013\000iÐMq\000~\000\tp\000sq\000~\000\000\000iÐBppsq\000~\000\021\000iÐ7q\000~\000\tpsq\000~\000\024\000iÐ4q\000~\000\tpq\000~\000\027q\000~\000\033q\000~\000\035sq\000~\000\036q\000~\000+q\000~\000\"sq\000~\000\000\bDG!ppsq\000~\000\006\bDG\026q\000~\000\tpsq\000~\000\000\000Ó §q\000~\000\tpsq\000~\000\013\000iÐXq\000~\000\tp\000sq\000~\000\013\000iÐMpp\000sq\000~\000\000\000iÐBppsq\000~\000\021\000iÐ7q\000~\000\tpsq\000~\000\024\000iÐ4q\000~\000\tpq\000~\000\027q\000~\000\033q\000~\000\035sq\000~\000\036q\000~\000!q\000~\000\"q\000~\000#sq\000~\000\013\000iÐMq\000~\000\tp\000sq\000~\000\000\000iÐBppsq\000~\000\021\000iÐ7q\000~\000\tpsq\000~\000\024\000iÐ4q\000~\000\tpq\000~\000\027q\000~\000\033q\000~\000\035sq\000~\000\036q\000~\000+q\000~\000\"sq\000~\000\000\007p¦jppsq\000~\000\006\007p¦_q\000~\000\tpsq\000~\000\000\000Ó §q\000~\000\tpsq\000~\000\013\000iÐXq\000~\000\tp\000sq\000~\000\013\000iÐMpp\000sq\000~\000\000\000iÐBppsq\000~\000\021\000iÐ7q\000~\000\tpsq\000~\000\024\000iÐ4q\000~\000\tpq\000~\000\027q\000~\000\033q\000~\000\035sq\000~\000\036q\000~\000!q\000~\000\"q\000~\000#sq\000~\000\013\000iÐMq\000~\000\tp\000sq\000~\000\000\000iÐBppsq\000~\000\021\000iÐ7q\000~\000\tpsq\000~\000\024\000iÐ4q\000~\000\tpq\000~\000\027q\000~\000\033q\000~\000\035sq\000~\000\036q\000~\000+q\000~\000\"sq\000~\000\000\006\005³ppsq\000~\000\006\006\005¨q\000~\000\tpsq\000~\000\000\000Ó §q\000~\000\tpsq\000~\000\013\000iÐXq\000~\000\tp\000sq\000~\000\013\000iÐMpp\000sq\000~\000\000\000iÐBppsq\000~\000\021\000iÐ7q\000~\000\tpsq\000~\000\024\000iÐ4q\000~\000\tpq\000~\000\027q\000~\000\033q\000~\000\035sq\000~\000\036q\000~\000!q\000~\000\"q\000~\000#sq\000~\000\013\000iÐMq\000~\000\tp\000sq\000~\000\000\000iÐBppsq\000~\000\021\000iÐ7q\000~\000\tpsq\000~\000\024\000iÐ4q\000~\000\tpq\000~\000\027q\000~\000\033q\000~\000\035sq\000~\000\036q\000~\000+q\000~\000\"sq\000~\000\000\005Édüppsq\000~\000\006\005Édñq\000~\000\tpsq\000~\000\000\000Ó §q\000~\000\tpsq\000~\000\013\000iÐXq\000~\000\tp\000sq\000~\000\013\000iÐMpp\000sq\000~\000\000\000iÐBppsq\000~\000\021\000iÐ7q\000~\000\tpsq\000~\000\024\000iÐ4q\000~\000\tpq\000~\000\027q\000~\000\033q\000~\000\035sq\000~\000\036q\000~\000!q\000~\000\"q\000~\000#sq\000~\000\013\000iÐMq\000~\000\tp\000sq\000~\000\000\000iÐBppsq\000~\000\021\000iÐ7q\000~\000\tpsq\000~\000\024\000iÐ4q\000~\000\tpq\000~\000\027q\000~\000\033q\000~\000\035sq\000~\000\036q\000~\000+q\000~\000\"sq\000~\000\000\004õÄEppsq\000~\000\006\004õÄ:q\000~\000\tpsq\000~\000\000\000Ó §q\000~\000\tpsq\000~\000\013\000iÐXq\000~\000\tp\000sq\000~\000\013\000iÐMpp\000sq\000~\000\000\000iÐBppsq\000~\000\021\000iÐ7q\000~\000\tpsq\000~\000\024\000iÐ4q\000~\000\tpq\000~\000\027q\000~\000\033q\000~\000\035sq\000~\000\036q\000~\000!q\000~\000\"q\000~\000#sq\000~\000\013\000iÐMq\000~\000\tp\000sq\000~\000\000\000iÐBppsq\000~\000\021\000iÐ7q\000~\000\tpsq\000~\000\024\000iÐ4q\000~\000\tpq\000~\000\027q\000~\000\033q\000~\000\035sq\000~\000\036q\000~\000+q\000~\000\"sq\000~\000\000\004\"#ppsq\000~\000\006\004\"#q\000~\000\tpsq\000~\000\000\000Ó §q\000~\000\tpsq\000~\000\013\000iÐXq\000~\000\tp\000sq\000~\000\013\000iÐMpp\000sq\000~\000\000\000iÐBppsq\000~\000\021\000iÐ7q\000~\000\tpsq\000~\000\024\000iÐ4q\000~\000\tpq\000~\000\027q\000~\000\033q\000~\000\035sq\000~\000\036q\000~\000!q\000~\000\"q\000~\000#sq\000~\000\013\000iÐMq\000~\000\tp\000sq\000~\000\000\000iÐBppsq\000~\000\021\000iÐ7q\000~\000\tpsq\000~\000\024\000iÐ4q\000~\000\tpq\000~\000\027q\000~\000\033q\000~\000\035sq\000~\000\036q\000~\000+q\000~\000\"sq\000~\000\000\003N×ppsq\000~\000\006\003NÌq\000~\000\tpsq\000~\000\000\000Ó §q\000~\000\tpsq\000~\000\013\000iÐXq\000~\000\tp\000sq\000~\000\013\000iÐMpp\000sq\000~\000\000\000iÐBppsq\000~\000\021\000iÐ7q\000~\000\tpsq\000~\000\024\000iÐ4q\000~\000\tpq\000~\000\027q\000~\000\033q\000~\000\035sq\000~\000\036q\000~\000!q\000~\000\"q\000~\000#sq\000~\000\013\000iÐMq\000~\000\tp\000sq\000~\000\000\000iÐBppsq\000~\000\021\000iÐ7q\000~\000\tpsq\000~\000\024\000iÐ4q\000~\000\tpq\000~\000\027q\000~\000\033q\000~\000\035sq\000~\000\036q\000~\000+q\000~\000\"sq\000~\000\000\002zâ ppsq\000~\000\006\002zâ\025q\000~\000\tpsq\000~\000\000\000Ó §q\000~\000\tpsq\000~\000\013\000iÐXq\000~\000\tp\000sq\000~\000\013\000iÐMpp\000sq\000~\000\000\000iÐBppsq\000~\000\021\000iÐ7q\000~\000\tpsq\000~\000\024\000iÐ4q\000~\000\tpq\000~\000\027q\000~\000\033q\000~\000\035sq\000~\000\036q\000~\000!q\000~\000\"q\000~\000#sq\000~\000\013\000iÐMq\000~\000\tp\000sq\000~\000\000\000iÐBppsq\000~\000\021\000iÐ7q\000~\000\tpsq\000~\000\024\000iÐ4q\000~\000\tpq\000~\000\027q\000~\000\033q\000~\000\035sq\000~\000\036q\000~\000+q\000~\000\"sq\000~\000\000\001§Aippsq\000~\000\006\001§A^q\000~\000\tpsq\000~\000\000\000Ó §q\000~\000\tpsq\000~\000\013\000iÐXq\000~\000\tp\000sq\000~\000\013\000iÐMpp\000sq\000~\000\000\000iÐBppsq\000~\000\021\000iÐ7q\000~\000\tpsq\000~\000\024\000iÐ4q\000~\000\tpq\000~\000\027q\000~\000\033q\000~\000\035sq\000~\000\036q\000~\000!q\000~\000\"q\000~\000#sq\000~\000\013\000iÐMq\000~\000\tp\000sq\000~\000\000\000iÐBppsq\000~\000\021\000iÐ7q\000~\000\tpsq\000~\000\024\000iÐ4q\000~\000\tpq\000~\000\027q\000~\000\033q\000~\000\035sq\000~\000\036q\000~\000+q\000~\000\"sq\000~\000\000\000Ó ²ppsq\000~\000\000\000Ó §q\000~\000\tpsq\000~\000\013\000iÐXq\000~\000\tp\000sq\000~\000\013\000iÐMpp\000sq\000~\000\000\000iÐBppsq\000~\000\021\000iÐ7q\000~\000\tpsq\000~\000\024\000iÐ4q\000~\000\tpq\000~\000\027q\000~\000\033q\000~\000\035sq\000~\000\036q\000~\000!q\000~\000\"q\000~\000#sq\000~\000\013\000iÐMq\000~\000\tp\000sq\000~\000\000\000iÐBppsq\000~\000\021\000iÐ7q\000~\000\tpsq\000~\000\024\000iÐ4q\000~\000\tpq\000~\000\027q\000~\000\033q\000~\000\035sq\000~\000\036q\000~\000+q\000~\000\"q\000~\000\035q\000~\000\035q\000~\000\035q\000~\000\035q\000~\000\035q\000~\000\035q\000~\000\035q\000~\000\035q\000~\000\035q\000~\000\035q\000~\000\035q\000~\000\035q\000~\000\035q\000~\000\035q\000~\000\035q\000~\000\035sr\000\"com.sun.msv.grammar.ExpressionPool\000\000\000\000\000\000\000\001\002\000\001L\000\bexpTablet\000/Lcom/sun/msv/grammar/ExpressionPool$ClosedHash;xpsr\000-com.sun.msv.grammar.ExpressionPool$ClosedHash×jÐNïèí\034\002\000\004I\000\005countI\000\tthresholdL\000\006parentq\000~\000þ[\000\005tablet\000![Lcom/sun/msv/grammar/Expression;xp\000\000\000o\000\000\000rpur\000![Lcom.sun.msv.grammar.Expression;Ö8DÃ]­§\n\002\000\000xp\000\000\001q\000~\000q\000~\000q\000~\000q\000~\000q\000~\000|q\000~\000wq\000~\000nq\000~\000iq\000~\000jq\000~\000`q\000~\000aq\000~\000[q\000~\000\\q\000~\000Rq\000~\000Mq\000~\000Dq\000~\000?q\000~\0006q\000~\0001q\000~\000'q\000~\000\020q\000~\000Sq\000~\000Nq\000~\000Eq\000~\000@q\000~\0007q\000~\0002q\000~\000q\000~\000q\000~\000q\000~\000tq\000~\000fq\000~\000Xq\000~\000Jq\000~\000<q\000~\000.q\000~\000\nq\000~\000(q\000~\000\023q\000~\000½q\000~\000¾q\000~\000¬q\000~\000Ãq\000~\000Âq\000~\000ºq\000~\000Ìq\000~\000Ëq\000~\000Ñq\000~\000Ðq\000~\000Èq\000~\000Úq\000~\000Ùq\000~\000ßq\000~\000Þq\000~\000Öq\000~\000èq\000~\000çq\000~\000íq\000~\000ìq\000~\000äq\000~\000õq\000~\000ôq\000~\000úq\000~\000ùq\000~\000ñq\000~\000ðq\000~\000q\000~\000q\000~\000sq\000~\000rq\000~\000ãpppq\000~\000eppppppq\000~\000âpppq\000~\000dpppppppppppppppppppppppppppq\000~\000Õpppq\000~\000Wppppppq\000~\000Ôpppq\000~\000Vpppppppppppppppppppppppppppq\000~\000Çpppq\000~\000Ippppppq\000~\000Æpppq\000~\000Hpppppppppppppppppppppppppppq\000~\000¹pppq\000~\000;ppppppq\000~\000¸pppq\000~\000:pppppppppppppppppppppppppppq\000~\000«pppq\000~\000-ppppppq\000~\000ªpppq\000~\000,pppppppppppppppppppppppppppq\000~\000pppq\000~\000\007ppppppq\000~\000pppq\000~\000\005pppppppppppppppppppppppppppq\000~\000ppppppppppq\000~\000ppppppppppppppppppppppppppppq\000~\000µq\000~\000°q\000~\000§q\000~\000¢q\000~\000q\000~\000q\000~\000q\000~\000q\000~\000}q\000~\000xq\000~\000oq\000~\000´q\000~\000¯q\000~\000¦q\000~\000¡");
/*     */ 
/*     */   
/*     */   private static final Class PRIMARY_INTERFACE_CLASS() {
/*  20 */     return LICENSEKEYType.class;
/*     */   }
/*     */   
/*     */   public List getCHUNK() {
/*  24 */     return (List)this._CHUNK;
/*     */   }
/*     */   
/*     */   public ContentHandlerEx getUnmarshaller(UnmarshallingContext context) {
/*  28 */     return new Unmarshaller(context);
/*     */   }
/*     */   
/*     */   public Class getPrimaryInterfaceClass() {
/*  32 */     return PRIMARY_INTERFACE_CLASS();
/*     */   }
/*     */   
/*     */   public void serializeElements(XMLSerializer context) throws SAXException {
/*  36 */     int idx1 = 0;
/*  37 */     int len1 = this._CHUNK.size();
/*  38 */     while (idx1 != len1) {
/*  39 */       if (this._CHUNK.get(idx1) instanceof javax.xml.bind.Element) {
/*  40 */         context.childAsElements((XMLSerializable)this._CHUNK.get(idx1++)); continue;
/*     */       } 
/*  42 */       context.startElement("", "CHUNK");
/*  43 */       int idx_0 = idx1;
/*  44 */       context.childAsAttributes((XMLSerializable)this._CHUNK.get(idx_0++));
/*  45 */       context.endAttributes();
/*  46 */       context.childAsElements((XMLSerializable)this._CHUNK.get(idx1++));
/*  47 */       context.endElement();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void serializeAttributes(XMLSerializer context) throws SAXException {
/*  53 */     this._CHUNK.size();
/*     */   }
/*     */   
/*     */   public void serializeAttributeBodies(XMLSerializer context) throws SAXException {
/*  57 */     this._CHUNK.size();
/*     */   }
/*     */   
/*     */   public Class getPrimaryInterface() {
/*  61 */     return LICENSEKEYType.class;
/*     */   }
/*     */   
/*     */   public DocumentDeclaration createRawValidator() {
/*  65 */     return (DocumentDeclaration)new REDocumentDeclaration(schemaFragment);
/*     */   }
/*     */   
/*     */   public class Unmarshaller
/*     */     extends ContentHandlerEx {
/*     */     public Unmarshaller(UnmarshallingContext context) {
/*  71 */       super(context, "---");
/*     */     }
/*     */     
/*     */     protected UnmarshallableObject owner() {
/*  75 */       return LICENSEKEYTypeImpl.this;
/*     */     }
/*     */     
/*     */     public void enterElement(String ___uri, String ___local, Attributes __atts) throws UnreportedException {
/*  79 */       switch (this.state) {
/*     */         case 0:
/*  81 */           if ("" == ___uri && "CHUNK" == ___local) {
/*  82 */             this.context.pushAttributes(__atts);
/*  83 */             goto1();
/*     */             return;
/*     */           } 
/*  86 */           revertToParentFromEnterElement(___uri, ___local, __atts);
/*     */           return;
/*     */       } 
/*  89 */       super.enterElement(___uri, ___local, __atts);
/*     */     }
/*     */     
/*     */     public void leaveElement(String ___uri, String ___local) throws UnreportedException {
/*  93 */       switch (this.state) {
/*     */         case 2:
/*  95 */           if ("" == ___uri && "CHUNK" == ___local) {
/*  96 */             this.context.popAttributes();
/*  97 */             this.state = 0;
/*     */             return;
/*     */           } 
/*     */           break;
/*     */         case 0:
/* 102 */           revertToParentFromLeaveElement(___uri, ___local);
/*     */           return;
/*     */       } 
/* 105 */       super.leaveElement(___uri, ___local);
/*     */     }
/*     */     
/*     */     public void enterAttribute(String ___uri, String ___local) throws UnreportedException {
/* 109 */       switch (this.state) {
/*     */         case 1:
/* 111 */           if ("" == ___uri && "ChunkNo" == ___local) {
/* 112 */             LICENSEKEYTypeImpl.this._CHUNK.add(spawnChildFromEnterAttribute(CHUNKTypeImpl.class, 2, ___uri, ___local));
/*     */             return;
/*     */           } 
/* 115 */           if ("" == ___uri && "Data" == ___local) {
/* 116 */             LICENSEKEYTypeImpl.this._CHUNK.add(spawnChildFromEnterAttribute(CHUNKTypeImpl.class, 2, ___uri, ___local));
/*     */             return;
/*     */           } 
/*     */           break;
/*     */         case 0:
/* 121 */           revertToParentFromEnterAttribute(___uri, ___local);
/*     */           return;
/*     */       } 
/* 124 */       super.enterAttribute(___uri, ___local);
/*     */     }
/*     */     
/*     */     public void leaveAttribute(String ___uri, String ___local) throws UnreportedException {
/* 128 */       switch (this.state) {
/*     */         case 0:
/* 130 */           revertToParentFromLeaveAttribute(___uri, ___local);
/*     */           return;
/*     */       } 
/* 133 */       super.leaveAttribute(___uri, ___local);
/*     */     }
/*     */     
/*     */     public void text(String value) throws UnreportedException {
/*     */       try {
/* 138 */         switch (this.state) {
/*     */           case 0:
/* 140 */             revertToParentFromText(value);
/*     */             return;
/*     */         } 
/* 143 */       } catch (RuntimeException e) {
/* 144 */         handleUnexpectedTextException(value, e);
/*     */       } 
/*     */     }
/*     */     
/*     */     public void leaveChild(int nextState) throws UnreportedException {
/* 149 */       switch (nextState) {
/*     */         case 2:
/* 151 */           this.state = 2;
/*     */           return;
/*     */       } 
/* 154 */       super.leaveChild(nextState);
/*     */     }
/*     */ 
/*     */     
/*     */     private void goto1() throws UnreportedException {
/* 159 */       this.state = 1;
/* 160 */       int idx = this.context.getAttribute("", "Data");
/* 161 */       if (idx >= 0) {
/* 162 */         this.context.consumeAttribute(idx);
/*     */         return;
/*     */       } 
/* 165 */       idx = this.context.getAttribute("", "ChunkNo");
/* 166 */       if (idx >= 0) {
/* 167 */         this.context.consumeAttribute(idx);
/*     */         return;
/*     */       } 
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\registration\common\xml\impl\LICENSEKEYTypeImpl.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */