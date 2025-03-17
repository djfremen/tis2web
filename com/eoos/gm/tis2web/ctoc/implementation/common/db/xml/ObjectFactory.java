/*     */ package com.eoos.gm.tis2web.ctoc.implementation.common.db.xml;
/*     */ 
/*     */ import com.eoos.gm.tis2web.ctoc.implementation.common.db.xml.impl.CTOCImpl;
/*     */ import com.eoos.gm.tis2web.ctoc.implementation.common.db.xml.impl.CTOCTypeImpl;
/*     */ import com.eoos.gm.tis2web.ctoc.implementation.common.db.xml.impl.ConstraintTypeImpl;
/*     */ import com.eoos.gm.tis2web.ctoc.implementation.common.db.xml.impl.FileReferenceTypeImpl;
/*     */ import com.eoos.gm.tis2web.ctoc.implementation.common.db.xml.impl.InformationObjectTypeImpl;
/*     */ import com.eoos.gm.tis2web.ctoc.implementation.common.db.xml.impl.LabelTypeImpl;
/*     */ import com.eoos.gm.tis2web.ctoc.implementation.common.db.xml.impl.NodeTypeImpl;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ObjectFactory
/*     */   extends DefaultJAXBContextImpl
/*     */ {
/*  28 */   private static HashMap defaultImplementations = new HashMap<Object, Object>();
/*     */   
/*     */   static {
/*  31 */     defaultImplementations.put(ConstraintType.class, ConstraintTypeImpl.class);
/*  32 */     defaultImplementations.put(FileReferenceType.class, FileReferenceTypeImpl.class);
/*  33 */     defaultImplementations.put(CTOC.class, CTOCImpl.class);
/*  34 */     defaultImplementations.put(LabelType.class, LabelTypeImpl.class);
/*  35 */     defaultImplementations.put(InformationObjectType.class, InformationObjectTypeImpl.class);
/*  36 */     defaultImplementations.put(NodeType.class, NodeTypeImpl.class);
/*  37 */     defaultImplementations.put(CTOCType.class, CTOCTypeImpl.class);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ObjectFactory() {
/*  46 */     super(new GrammarInfoImpl(null));
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
/*  59 */     return super.newInstance(javaContentInterface);
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
/*  74 */     return super.getProperty(name);
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
/*  90 */     super.setProperty(name, value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ConstraintType createConstraintType() throws JAXBException {
/* 100 */     return (ConstraintType)newInstance(ConstraintType.class);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public FileReferenceType createFileReferenceType() throws JAXBException {
/* 110 */     return (FileReferenceType)newInstance(FileReferenceType.class);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public CTOC createCTOC() throws JAXBException {
/* 120 */     return (CTOC)newInstance(CTOC.class);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public LabelType createLabelType() throws JAXBException {
/* 130 */     return (LabelType)newInstance(LabelType.class);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public InformationObjectType createInformationObjectType() throws JAXBException {
/* 140 */     return (InformationObjectType)newInstance(InformationObjectType.class);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public NodeType createNodeType() throws JAXBException {
/* 150 */     return (NodeType)newInstance(NodeType.class);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public CTOCType createCTOCType() throws JAXBException {
/* 160 */     return (CTOCType)newInstance(CTOCType.class);
/*     */   }
/*     */   
/*     */   private static class GrammarInfoImpl
/*     */     extends GrammarInfo {
/*     */     public Class getDefaultImplementation(Class javaContentInterface) {
/* 166 */       return (Class)ObjectFactory.defaultImplementations.get(javaContentInterface);
/*     */     }
/*     */     private GrammarInfoImpl() {}
/*     */     public Class getRootElement(String uri, String local) {
/* 170 */       if ("" == uri && "CTOC" == local) {
/* 171 */         return CTOCImpl.class;
/*     */       }
/* 173 */       return null;
/*     */     }
/*     */     
/*     */     public String[] getProbePoints() {
/* 177 */       return new String[] { "", "CTOC" };
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\ctoc\implementation\common\db\xml\ObjectFactory.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */