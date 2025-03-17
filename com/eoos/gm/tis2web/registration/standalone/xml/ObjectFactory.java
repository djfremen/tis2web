/*     */ package com.eoos.gm.tis2web.registration.standalone.xml;
/*     */ 
/*     */ import com.eoos.gm.tis2web.registration.standalone.xml.impl.AUTHORIZATIONImpl;
/*     */ import com.eoos.gm.tis2web.registration.standalone.xml.impl.AUTHORIZATIONTypeImpl;
/*     */ import com.eoos.gm.tis2web.registration.standalone.xml.impl.DESCRIPTIONImpl;
/*     */ import com.eoos.gm.tis2web.registration.standalone.xml.impl.DESCRIPTIONTypeImpl;
/*     */ import com.eoos.gm.tis2web.registration.standalone.xml.impl.GROUPImpl;
/*     */ import com.eoos.gm.tis2web.registration.standalone.xml.impl.GROUPTypeImpl;
/*     */ import com.eoos.gm.tis2web.registration.standalone.xml.impl.ORGANIZATIONImpl;
/*     */ import com.eoos.gm.tis2web.registration.standalone.xml.impl.ORGANIZATIONTypeImpl;
/*     */ import com.eoos.gm.tis2web.registration.standalone.xml.impl.TIS2WEBImpl;
/*     */ import com.eoos.gm.tis2web.registration.standalone.xml.impl.TIS2WEBTypeImpl;
/*     */ import com.sun.xml.bind.DefaultJAXBContextImpl;
/*     */ import com.sun.xml.bind.GrammarInfo;
/*     */ import java.util.HashMap;
/*     */ import javax.xml.bind.JAXBException;
/*     */ import javax.xml.bind.PropertyException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ObjectFactory
/*     */   extends DefaultJAXBContextImpl
/*     */ {
/*  28 */   private static HashMap defaultImplementations = new HashMap<Object, Object>();
/*     */   
/*     */   static {
/*  31 */     defaultImplementations.put(ORGANIZATIONType.class, ORGANIZATIONTypeImpl.class);
/*  32 */     defaultImplementations.put(ORGANIZATION.class, ORGANIZATIONImpl.class);
/*  33 */     defaultImplementations.put(DESCRIPTION.class, DESCRIPTIONImpl.class);
/*  34 */     defaultImplementations.put(AUTHORIZATION.class, AUTHORIZATIONImpl.class);
/*  35 */     defaultImplementations.put(TIS2WEBType.class, TIS2WEBTypeImpl.class);
/*  36 */     defaultImplementations.put(GROUP.class, GROUPImpl.class);
/*  37 */     defaultImplementations.put(AUTHORIZATIONType.class, AUTHORIZATIONTypeImpl.class);
/*  38 */     defaultImplementations.put(TIS2WEB.class, TIS2WEBImpl.class);
/*  39 */     defaultImplementations.put(GROUPType.class, GROUPTypeImpl.class);
/*  40 */     defaultImplementations.put(DESCRIPTIONType.class, DESCRIPTIONTypeImpl.class);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ObjectFactory() {
/*  49 */     super(new GrammarInfoImpl(null));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object newInstance(Class javaContentInterface) throws JAXBException {
/*  62 */     return super.newInstance(javaContentInterface);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object getProperty(String name) throws PropertyException {
/*  77 */     return super.getProperty(name);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setProperty(String name, Object value) throws PropertyException {
/*  93 */     super.setProperty(name, value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ORGANIZATIONType createORGANIZATIONType() throws JAXBException {
/* 103 */     return (ORGANIZATIONType)newInstance(ORGANIZATIONType.class);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ORGANIZATION createORGANIZATION() throws JAXBException {
/* 113 */     return (ORGANIZATION)newInstance(ORGANIZATION.class);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DESCRIPTION createDESCRIPTION() throws JAXBException {
/* 123 */     return (DESCRIPTION)newInstance(DESCRIPTION.class);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public AUTHORIZATION createAUTHORIZATION() throws JAXBException {
/* 133 */     return (AUTHORIZATION)newInstance(AUTHORIZATION.class);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TIS2WEBType createTIS2WEBType() throws JAXBException {
/* 143 */     return (TIS2WEBType)newInstance(TIS2WEBType.class);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public GROUP createGROUP() throws JAXBException {
/* 153 */     return (GROUP)newInstance(GROUP.class);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public AUTHORIZATIONType createAUTHORIZATIONType() throws JAXBException {
/* 163 */     return (AUTHORIZATIONType)newInstance(AUTHORIZATIONType.class);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TIS2WEB createTIS2WEB() throws JAXBException {
/* 173 */     return (TIS2WEB)newInstance(TIS2WEB.class);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public GROUPType createGROUPType() throws JAXBException {
/* 183 */     return (GROUPType)newInstance(GROUPType.class);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DESCRIPTIONType createDESCRIPTIONType() throws JAXBException {
/* 193 */     return (DESCRIPTIONType)newInstance(DESCRIPTIONType.class);
/*     */   }
/*     */   
/*     */   private static class GrammarInfoImpl
/*     */     extends GrammarInfo {
/*     */     public Class getDefaultImplementation(Class javaContentInterface) {
/* 199 */       return (Class)ObjectFactory.defaultImplementations.get(javaContentInterface);
/*     */     }
/*     */     private GrammarInfoImpl() {}
/*     */     public Class getRootElement(String uri, String local) {
/* 203 */       if ("" == uri && "GROUP" == local) {
/* 204 */         return GROUPImpl.class;
/*     */       }
/* 206 */       if ("" == uri && "ORGANIZATION" == local) {
/* 207 */         return ORGANIZATIONImpl.class;
/*     */       }
/* 209 */       if ("" == uri && "AUTHORIZATION" == local) {
/* 210 */         return AUTHORIZATIONImpl.class;
/*     */       }
/* 212 */       if ("" == uri && "TIS2WEB" == local) {
/* 213 */         return TIS2WEBImpl.class;
/*     */       }
/* 215 */       if ("" == uri && "DESCRIPTION" == local) {
/* 216 */         return DESCRIPTIONImpl.class;
/*     */       }
/* 218 */       return null;
/*     */     }
/*     */     
/*     */     public String[] getProbePoints() {
/* 222 */       return new String[] { "", "GROUP", "", "ORGANIZATION", "", "AUTHORIZATION", "", "TIS2WEB", "", "DESCRIPTION" };
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\registration\standalone\xml\ObjectFactory.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */