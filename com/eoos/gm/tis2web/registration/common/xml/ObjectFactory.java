/*     */ package com.eoos.gm.tis2web.registration.common.xml;
/*     */ 
/*     */ import com.eoos.gm.tis2web.registration.common.xml.impl.CHUNKImpl;
/*     */ import com.eoos.gm.tis2web.registration.common.xml.impl.CHUNKTypeImpl;
/*     */ import com.eoos.gm.tis2web.registration.common.xml.impl.DEALERSHIPCONTACTImpl;
/*     */ import com.eoos.gm.tis2web.registration.common.xml.impl.DEALERSHIPCONTACTTypeImpl;
/*     */ import com.eoos.gm.tis2web.registration.common.xml.impl.DEALERSHIPImpl;
/*     */ import com.eoos.gm.tis2web.registration.common.xml.impl.DEALERSHIPTypeImpl;
/*     */ import com.eoos.gm.tis2web.registration.common.xml.impl.LICENSEKEYImpl;
/*     */ import com.eoos.gm.tis2web.registration.common.xml.impl.LICENSEKEYTypeImpl;
/*     */ import com.eoos.gm.tis2web.registration.common.xml.impl.REGISTRATIONImpl;
/*     */ import com.eoos.gm.tis2web.registration.common.xml.impl.REGISTRATIONTypeImpl;
/*     */ import com.eoos.gm.tis2web.registration.common.xml.impl.SOFTWAREKEYImpl;
/*     */ import com.eoos.gm.tis2web.registration.common.xml.impl.SOFTWAREKEYTypeImpl;
/*     */ import com.eoos.gm.tis2web.registration.common.xml.impl.SUBSCRIPTIONImpl;
/*     */ import com.eoos.gm.tis2web.registration.common.xml.impl.SUBSCRIPTIONTypeImpl;
/*     */ import com.sun.xml.bind.DefaultJAXBContextImpl;
/*     */ import com.sun.xml.bind.GrammarInfo;
/*     */ import java.util.HashMap;
/*     */ import javax.xml.bind.JAXBException;
/*     */ import javax.xml.bind.PropertyException;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ObjectFactory
/*     */   extends DefaultJAXBContextImpl
/*     */ {
/*  28 */   private static HashMap defaultImplementations = new HashMap<Object, Object>();
/*     */   
/*     */   static {
/*  31 */     defaultImplementations.put(DEALERSHIPCONTACT.class, DEALERSHIPCONTACTImpl.class);
/*  32 */     defaultImplementations.put(SOFTWAREKEY.class, SOFTWAREKEYImpl.class);
/*  33 */     defaultImplementations.put(SUBSCRIPTIONType.class, SUBSCRIPTIONTypeImpl.class);
/*  34 */     defaultImplementations.put(DEALERSHIPType.class, DEALERSHIPTypeImpl.class);
/*  35 */     defaultImplementations.put(REGISTRATION.class, REGISTRATIONImpl.class);
/*  36 */     defaultImplementations.put(LICENSEKEYType.class, LICENSEKEYTypeImpl.class);
/*  37 */     defaultImplementations.put(CHUNKType.class, CHUNKTypeImpl.class);
/*  38 */     defaultImplementations.put(DEALERSHIPCONTACTType.class, DEALERSHIPCONTACTTypeImpl.class);
/*  39 */     defaultImplementations.put(SUBSCRIPTION.class, SUBSCRIPTIONImpl.class);
/*  40 */     defaultImplementations.put(SOFTWAREKEYType.class, SOFTWAREKEYTypeImpl.class);
/*  41 */     defaultImplementations.put(REGISTRATIONType.class, REGISTRATIONTypeImpl.class);
/*  42 */     defaultImplementations.put(CHUNK.class, CHUNKImpl.class);
/*  43 */     defaultImplementations.put(DEALERSHIP.class, DEALERSHIPImpl.class);
/*  44 */     defaultImplementations.put(LICENSEKEY.class, LICENSEKEYImpl.class);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ObjectFactory() {
/*  53 */     super(new GrammarInfoImpl(null));
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
/*  66 */     return super.newInstance(javaContentInterface);
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
/*  81 */     return super.getProperty(name);
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
/*  97 */     super.setProperty(name, value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DEALERSHIPCONTACT createDEALERSHIPCONTACT() throws JAXBException {
/* 107 */     return (DEALERSHIPCONTACT)newInstance(DEALERSHIPCONTACT.class);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SOFTWAREKEY createSOFTWAREKEY() throws JAXBException {
/* 117 */     return (SOFTWAREKEY)newInstance(SOFTWAREKEY.class);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SUBSCRIPTIONType createSUBSCRIPTIONType() throws JAXBException {
/* 127 */     return (SUBSCRIPTIONType)newInstance(SUBSCRIPTIONType.class);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DEALERSHIPType createDEALERSHIPType() throws JAXBException {
/* 137 */     return (DEALERSHIPType)newInstance(DEALERSHIPType.class);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public REGISTRATION createREGISTRATION() throws JAXBException {
/* 147 */     return (REGISTRATION)newInstance(REGISTRATION.class);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public LICENSEKEYType createLICENSEKEYType() throws JAXBException {
/* 157 */     return (LICENSEKEYType)newInstance(LICENSEKEYType.class);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public CHUNKType createCHUNKType() throws JAXBException {
/* 167 */     return (CHUNKType)newInstance(CHUNKType.class);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DEALERSHIPCONTACTType createDEALERSHIPCONTACTType() throws JAXBException {
/* 177 */     return (DEALERSHIPCONTACTType)newInstance(DEALERSHIPCONTACTType.class);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SUBSCRIPTION createSUBSCRIPTION() throws JAXBException {
/* 187 */     return (SUBSCRIPTION)newInstance(SUBSCRIPTION.class);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SOFTWAREKEYType createSOFTWAREKEYType() throws JAXBException {
/* 197 */     return (SOFTWAREKEYType)newInstance(SOFTWAREKEYType.class);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public REGISTRATIONType createREGISTRATIONType() throws JAXBException {
/* 207 */     return (REGISTRATIONType)newInstance(REGISTRATIONType.class);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public CHUNK createCHUNK() throws JAXBException {
/* 217 */     return (CHUNK)newInstance(CHUNK.class);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DEALERSHIP createDEALERSHIP() throws JAXBException {
/* 227 */     return (DEALERSHIP)newInstance(DEALERSHIP.class);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public LICENSEKEY createLICENSEKEY() throws JAXBException {
/* 237 */     return (LICENSEKEY)newInstance(LICENSEKEY.class);
/*     */   }
/*     */   
/*     */   private static class GrammarInfoImpl
/*     */     extends GrammarInfo {
/*     */     public Class getDefaultImplementation(Class javaContentInterface) {
/* 243 */       return (Class)ObjectFactory.defaultImplementations.get(javaContentInterface);
/*     */     }
/*     */     private GrammarInfoImpl() {}
/*     */     public Class getRootElement(String uri, String local) {
/* 247 */       if ("" == uri && "SOFTWARE_KEY" == local) {
/* 248 */         return SOFTWAREKEYImpl.class;
/*     */       }
/* 250 */       if ("" == uri && "LICENSE_KEY" == local) {
/* 251 */         return LICENSEKEYImpl.class;
/*     */       }
/* 253 */       if ("" == uri && "CHUNK" == local) {
/* 254 */         return CHUNKImpl.class;
/*     */       }
/* 256 */       if ("" == uri && "DEALERSHIP" == local) {
/* 257 */         return DEALERSHIPImpl.class;
/*     */       }
/* 259 */       if ("" == uri && "DEALERSHIP_CONTACT" == local) {
/* 260 */         return DEALERSHIPCONTACTImpl.class;
/*     */       }
/* 262 */       if ("" == uri && "REGISTRATION" == local) {
/* 263 */         return REGISTRATIONImpl.class;
/*     */       }
/* 265 */       if ("" == uri && "SUBSCRIPTION" == local) {
/* 266 */         return SUBSCRIPTIONImpl.class;
/*     */       }
/* 268 */       return null;
/*     */     }
/*     */     
/*     */     public String[] getProbePoints() {
/* 272 */       return new String[] { "", "SOFTWARE_KEY", "", "LICENSE_KEY", "", "CHUNK", "", "DEALERSHIP", "", "DEALERSHIP_CONTACT", "", "REGISTRATION", "", "SUBSCRIPTION" };
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\registration\common\xml\ObjectFactory.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */