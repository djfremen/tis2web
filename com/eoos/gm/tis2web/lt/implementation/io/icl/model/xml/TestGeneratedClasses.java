/*     */ package com.eoos.gm.tis2web.lt.implementation.io.icl.model.xml;
/*     */ 
/*     */ import com.eoos.gm.tis2web.lt.implementation.io.icl.model.xml.impl.EngineImpl;
/*     */ import com.eoos.gm.tis2web.lt.implementation.io.icl.model.xml.impl.FooterImpl;
/*     */ import com.eoos.gm.tis2web.lt.implementation.io.icl.model.xml.impl.HeaderImpl;
/*     */ import com.eoos.gm.tis2web.lt.implementation.io.icl.model.xml.impl.ModelImpl;
/*     */ import com.eoos.gm.tis2web.lt.implementation.io.icl.model.xml.impl.PositionImpl;
/*     */ import com.eoos.gm.tis2web.lt.implementation.io.icl.model.xml.impl.PositionsImpl;
/*     */ import com.eoos.gm.tis2web.lt.implementation.io.icl.model.xml.impl.ReleaseImpl;
/*     */ import com.eoos.gm.tis2web.lt.implementation.io.icl.model.xml.impl.ServiceplanImpl;
/*     */ import com.eoos.gm.tis2web.lt.implementation.io.icl.model.xml.impl.TransmissionImpl;
/*     */ import com.eoos.gm.tis2web.lt.implementation.io.icl.model.xml.impl.VINImpl;
/*     */ import java.io.File;
/*     */ import java.io.FileOutputStream;
/*     */ import java.io.IOException;
/*     */ import javax.xml.bind.JAXBContext;
/*     */ import javax.xml.bind.JAXBException;
/*     */ import javax.xml.bind.Marshaller;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ public class TestGeneratedClasses
/*     */ {
/*     */   private Serviceplan sp;
/*     */   
/*     */   public void populate() {
/*  92 */     this.sp.setLanguage("de_DE");
/*     */     
/*  94 */     HeaderImpl headerImpl = new HeaderImpl();
/*  95 */     this.sp.setHeader((HeaderType)headerImpl);
/*  96 */     headerImpl.setMake("Opel");
/*  97 */     headerImpl.setTitle("Der Titel");
/*  98 */     headerImpl.setServType("Service Type");
/*  99 */     headerImpl.setDrvType("Drive Type");
/* 100 */     headerImpl.setOrderno("");
/* 101 */     headerImpl.setCustomer("Frank Dietrich");
/* 102 */     headerImpl.setDate("26.02.2003");
/* 103 */     headerImpl.setPhone("I don't give it to you");
/* 104 */     headerImpl.setKm("10.000");
/* 105 */     headerImpl.setNumberplate("ich weiss nicht was soll es bedeuten");
/* 106 */     headerImpl.setInspType("Inspektionstyp");
/* 107 */     headerImpl.setRelease((ReleaseType)new ReleaseImpl());
/* 108 */     headerImpl.getRelease().setLabel("ReleaseLabel");
/* 109 */     headerImpl.getRelease().setValue("ReleaseValue");
/* 110 */     headerImpl.setModel((ModelType)new ModelImpl());
/* 111 */     headerImpl.getModel().setLabel("ModelLabel");
/* 112 */     headerImpl.getModel().setValue("ModelValue");
/* 113 */     headerImpl.setEngine((EngineType)new EngineImpl());
/* 114 */     headerImpl.getEngine().setLabel("EngineLabel");
/* 115 */     headerImpl.getEngine().setValue("EngineValue");
/* 116 */     headerImpl.setTransmission((TransmissionType)new TransmissionImpl());
/* 117 */     headerImpl.getTransmission().setLabel("TransmissionLabel");
/* 118 */     headerImpl.getTransmission().setValue("TransmissionValue");
/* 119 */     headerImpl.setVIN((VINType)new VINImpl());
/* 120 */     headerImpl.getVIN().setLabel("VINLabel");
/* 121 */     headerImpl.getVIN().setValue("VINValue");
/*     */     
/* 123 */     PositionsImpl positionsImpl = new PositionsImpl();
/* 124 */     this.sp.setPositions((PositionsType)positionsImpl);
/* 125 */     positionsImpl.setTitle("Positions Title");
/* 126 */     positionsImpl.setState("Positions State");
/* 127 */     this.sp.setFooter((FooterType)new FooterImpl());
/*     */     
/* 129 */     PositionImpl positionImpl = new PositionImpl();
/* 130 */     positionsImpl.getPosition().add(positionImpl);
/* 131 */     positionImpl.setText("Text Position 1");
/* 132 */     positionImpl.setNr("1");
/*     */     
/* 134 */     FooterImpl footerImpl = new FooterImpl();
/* 135 */     this.sp.setFooter((FooterType)footerImpl);
/* 136 */     footerImpl.setConfirmation("Footer Confirmation");
/* 137 */     footerImpl.setDefects("Footer Defects");
/* 138 */     footerImpl.setMechanic("Frank der Groï¿½e");
/* 139 */     footerImpl.setCheck("Footer Check");
/* 140 */     footerImpl.setWorkshop("Frank's Laboratories");
/*     */   }
/*     */   
/*     */   public static void marshal(Serviceplan sp, String fn) throws IOException {
/* 144 */     File out = new File(fn);
/* 145 */     FileOutputStream fos = null;
/*     */     try {
/* 147 */       fos = new FileOutputStream(out);
/* 148 */     } catch (Exception ex) {
/* 149 */       System.out.println(ex);
/*     */     } 
/*     */     try {
/* 152 */       JAXBContext jc = JAXBContext.newInstance("com.eoos.gm.tis2web.lt.io.icl.model.xml");
/* 153 */       Marshaller mm = jc.createMarshaller();
/* 154 */       mm.marshal(sp, fos);
/* 155 */     } catch (JAXBException je) {
/* 156 */       System.out.println(je);
/*     */     } finally {
/*     */       
/* 159 */       if (fos != null) {
/* 160 */         fos.close();
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void main(String[] args) {
/* 169 */     if (args.length != 1) {
/*     */       
/* 171 */       args = new String[1];
/* 172 */       args[0] = "c:\\test.xml";
/*     */     } 
/*     */ 
/*     */     
/* 176 */     TestGeneratedClasses tc = new TestGeneratedClasses();
/* 177 */     tc.sp = (Serviceplan)new ServiceplanImpl();
/* 178 */     tc.populate();
/*     */     try {
/* 180 */       marshal(tc.sp, args[0]);
/* 181 */     } catch (Exception ex) {
/* 182 */       System.out.println(ex);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\lt\implementation\io\icl\model\xml\TestGeneratedClasses.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */