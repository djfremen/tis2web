/*     */ package com.eoos.gm.tis2web.sids.implementation;
/*     */ 
/*     */ import com.eoos.gm.tis2web.sids.service.cai.ServiceID;
/*     */ import edu.umd.cs.findbugs.annotations.SuppressWarnings;
/*     */ import java.util.List;
/*     */ import java.util.Locale;
/*     */ import java.util.Map;
/*     */ 
/*     */ 
/*     */ public class BaseVehicle
/*     */ {
/*     */   public static final String VDS = "vds";
/*     */   public static final String MY = "my";
/*     */   public static final String VIS = "vis";
/*     */   protected ServiceID serviceID;
/*     */   protected int vehicleID;
/*     */   protected String wmi;
/*     */   protected String vds;
/*     */   protected String my;
/*     */   protected String vis;
/*     */   protected VehicleQualification qualifier;
/*     */   
/*     */   public VehicleQualification getQualifier() {
/*  24 */     return this.qualifier;
/*     */   }
/*     */   
/*     */   public String getMy() {
/*  28 */     return this.my;
/*     */   }
/*     */   
/*     */   public ServiceID getServiceID() {
/*  32 */     return this.serviceID;
/*     */   }
/*     */   
/*     */   public String getVds() {
/*  36 */     return this.vds;
/*     */   }
/*     */   
/*     */   public int getVehicleID() {
/*  40 */     return this.vehicleID;
/*     */   }
/*     */   
/*     */   public String getVis() {
/*  44 */     return this.vis;
/*     */   }
/*     */   
/*     */   public String getWmi() {
/*  48 */     return this.wmi;
/*     */   }
/*     */   
/*     */   public BaseVehicle(ServiceID serviceID, int vehicleID, String wmi, String vds, String my, String vis, VehicleQualification qualifier) {
/*  52 */     this.serviceID = serviceID;
/*  53 */     this.vehicleID = vehicleID;
/*  54 */     this.wmi = (wmi == null) ? null : wmi.trim();
/*  55 */     this.vds = (vds == null) ? null : vds.trim();
/*  56 */     this.my = (my == null) ? null : my.trim();
/*  57 */     this.vis = (vis == null) ? null : vis.trim();
/*  58 */     this.qualifier = qualifier;
/*     */   }
/*     */   
/*     */   public boolean match(String wmi, String vds, String my, String vis) {
/*  62 */     if (!getWmi().equals(wmi))
/*  63 */       return false; 
/*  64 */     if (!match(getVds(), vds))
/*  65 */       return false; 
/*  66 */     if (!match(getMy(), my))
/*  67 */       return false; 
/*  68 */     if (!match(getVis(), vis)) {
/*  69 */       return false;
/*     */     }
/*  71 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean match(String template, String value) {
/*  76 */     if (template == null) {
/*  77 */       return true;
/*     */     }
/*  79 */     for (int i = 0; i < template.length(); i++) {
/*  80 */       char c = template.charAt(i);
/*  81 */       if (c != '*' && c != '#')
/*     */       {
/*  83 */         if (c != value.charAt(i)) {
/*  84 */           return false;
/*     */         }
/*     */       }
/*     */     } 
/*  88 */     return true;
/*     */   }
/*     */   
/*     */   protected boolean isDefault(String value) {
/*  92 */     return (value != null && value.indexOf('#') >= 0);
/*     */   }
/*     */   
/*     */   public boolean isDefault() {
/*  96 */     return (isDefault(this.vds) || isDefault(this.my) || isDefault(this.vis));
/*     */   }
/*     */   
/*     */   public String getDefaultAttribute() {
/* 100 */     if (isDefault(this.vds))
/* 101 */       return "vds"; 
/* 102 */     if (isDefault("my"))
/* 103 */       return "my"; 
/* 104 */     if (isDefault(this.vis)) {
/* 105 */       return "vis";
/*     */     }
/* 107 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   protected int getPrefixLength(String template) {
/* 112 */     if (template == null) {
/* 113 */       return 0;
/*     */     }
/* 115 */     int pos = template.indexOf('#');
/* 116 */     if (pos < 0) {
/* 117 */       throw new IllegalArgumentException("SIDS default value template = '" + template + "'");
/*     */     }
/* 119 */     return pos;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @SuppressWarnings({"ES_COMPARING_PARAMETER_STRING_WITH_EQ"})
/*     */   public int getDefaultPrefixLength(String attribute) {
/* 126 */     if (attribute == "vds")
/* 127 */       return getPrefixLength(this.vds); 
/* 128 */     if (attribute == "my")
/* 129 */       return getPrefixLength(this.my); 
/* 130 */     if (attribute == "vis") {
/* 131 */       return getPrefixLength(this.vis);
/*     */     }
/* 133 */     return 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean qualifies(Map selections) {
/* 138 */     List<VehicleAttribute> attributes = this.qualifier.getAttributes();
/* 139 */     if (attributes == null) {
/* 140 */       throw new DataModelException("no vehicle attributes for conflict resolution (vehicle=" + this.vehicleID + ")");
/*     */     }
/* 142 */     for (int i = 0; i < attributes.size(); i++) {
/* 143 */       VehicleAttribute attribute = attributes.get(i);
/* 144 */       Integer valueID = (Integer)selections.get(attribute.getID());
/* 145 */       if (valueID != null && 
/* 146 */         !this.qualifier.match(attribute, valueID)) {
/* 147 */         return false;
/*     */       }
/*     */     } 
/*     */     
/* 151 */     return true;
/*     */   }
/*     */   
/*     */   public String toString() {
/* 155 */     StringBuffer buffer = new StringBuffer();
/* 156 */     buffer.append(this.serviceID.toString().toUpperCase(Locale.ENGLISH) + " " + this.vehicleID + '/' + this.wmi + '/' + this.vds + '/' + this.my + '/' + this.vis);
/* 157 */     if (this.qualifier != null) {
/* 158 */       buffer.append(" " + this.qualifier);
/*     */     }
/* 160 */     return buffer.toString();
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sids\implementation\BaseVehicle.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */