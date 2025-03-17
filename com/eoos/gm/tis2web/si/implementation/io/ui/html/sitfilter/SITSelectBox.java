/*     */ package com.eoos.gm.tis2web.si.implementation.io.ui.html.sitfilter;
/*     */ 
/*     */ import com.eoos.gm.tis2web.acl.service.ACLService;
/*     */ import com.eoos.gm.tis2web.acl.service.ACLServiceProvider;
/*     */ import com.eoos.gm.tis2web.ctoc.service.CTOCServiceUtil;
/*     */ import com.eoos.gm.tis2web.ctoc.service.cai.CTOCNode;
/*     */ import com.eoos.gm.tis2web.ctoc.service.cai.SITOCElement;
/*     */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*     */ import com.eoos.gm.tis2web.frame.export.common.WIS;
/*     */ import com.eoos.gm.tis2web.frame.export.common.locale.LocaleInfoProvider;
/*     */ import com.eoos.gm.tis2web.frame.export.common.sharedcontext.SharedContextProxy;
/*     */ import com.eoos.gm.tis2web.si.implementation.io.datamodel.sitfilter.SIT;
/*     */ import com.eoos.gm.tis2web.vc.v2.configuration.VehicleConfigurationUtil;
/*     */ import com.eoos.gm.tis2web.vc.v2.service.VCFacade;
/*     */ import com.eoos.gm.tis2web.vc.v2.service.VehicleCfgStorage;
/*     */ import com.eoos.gm.tis2web.vc.v2.value.Make;
/*     */ import com.eoos.html.element.input.SelectBoxSelectionElement;
/*     */ import com.eoos.scsm.v2.util.Util;
/*     */ import java.util.Collections;
/*     */ import java.util.HashMap;
/*     */ import java.util.HashSet;
/*     */ import java.util.Iterator;
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SITSelectBox
/*     */   extends SelectBoxSelectionElement
/*     */ {
/*     */   public class Comparator
/*     */     implements java.util.Comparator
/*     */   {
/*     */     public int compare(Object obj, Object obj2) {
/*     */       try {
/*  42 */         String label = SITSelectBox.this.getDisplayValue(obj);
/*  43 */         String label2 = SITSelectBox.this.getDisplayValue(obj2);
/*  44 */         return label.compareTo(label2);
/*  45 */       } catch (Exception e) {
/*  46 */         return 0;
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*  51 */   protected Comparator comparator = new Comparator();
/*     */   
/*     */   protected ClientContext context;
/*     */ 
/*     */   
/*     */   protected Set getSaabSITs() {
/*     */     try {
/*  58 */       ACLService aclMI = ACLServiceProvider.getInstance().getService();
/*  59 */       SharedContextProxy scp = SharedContextProxy.getInstance(this.context);
/*  60 */       HashSet<String> manufacturers = new HashSet();
/*  61 */       manufacturers.add(WIS.SAAB_MAKE);
/*  62 */       Map<Object, Object> ug2m = new HashMap<Object, Object>();
/*  63 */       ug2m.put(WIS.SAAB_GROUP, manufacturers);
/*  64 */       return aclMI.getAuthorizedResources("SIT", ug2m, scp.getCountry());
/*  65 */     } catch (Exception e) {
/*  66 */       throw new RuntimeException();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean isSaabSIT(Set sits, CTOCNode sit) {
/*  72 */     String target = CTOCServiceUtil.extractSITKey(sit).toString();
/*  73 */     for (Iterator<String> iter = sits.iterator(); iter.hasNext(); ) {
/*  74 */       String wis = iter.next();
/*  75 */       if (target.equals(wis)) {
/*  76 */         return true;
/*     */       }
/*     */     } 
/*  79 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void filterSITs(List sits) {
/*  84 */     if (WIS.hasSaabData(this.context)) {
/*  85 */       Set wis = getSaabSITs();
/*  86 */       for (Iterator<CTOCNode> iter = sits.iterator(); iter.hasNext(); ) {
/*  87 */         CTOCNode sit = iter.next();
/*  88 */         if (!isSaabSIT(wis, sit)) {
/*  89 */           iter.remove();
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*  95 */   private Make currentMake = null;
/*     */ 
/*     */   
/*     */   public SITSelectBox(final ClientContext context) {
/*  99 */     super("SITSelection", true, null, 1);
/* 100 */     this.context = context;
/* 101 */     VCFacade.getInstance(context).addObserver(new VehicleCfgStorage.Observer()
/*     */         {
/*     */           public void onVehicleConfigurationChange() {
/* 104 */             Make newMake = null;
/* 105 */             if (VCFacade.getInstance(context).getCfg() != null) {
/* 106 */               newMake = VehicleConfigurationUtil.getMake(VCFacade.getInstance(context).getCfg());
/*     */             }
/* 108 */             SITSelectBox.this.onVehicleChange(!Util.equals(SITSelectBox.this.currentMake, newMake));
/*     */           }
/*     */         });
/*     */     
/* 112 */     init();
/*     */   }
/*     */   
/*     */   protected void onVehicleChange(boolean salesmakeChange) {
/* 116 */     if (salesmakeChange) {
/* 117 */       SIT.getInstance(this.context).setSelectedSIT(null);
/*     */     }
/* 119 */     init();
/*     */   }
/*     */   
/*     */   protected void init() {
/* 123 */     if (VCFacade.getInstance(this.context).getCfg() != null) {
/* 124 */       this.currentMake = VehicleConfigurationUtil.getMake(VCFacade.getInstance(this.context).getCfg());
/*     */     }
/* 126 */     List<?> options = SIT.getInstance(this.context).getSITS();
/* 127 */     if (options != null) {
/* 128 */       filterSITs(options);
/* 129 */       Collections.sort(options, this.comparator);
/*     */     } else {
/* 131 */       options = new LinkedList();
/*     */     } 
/* 133 */     options.add(0, null);
/* 134 */     setOptions(options);
/*     */   }
/*     */   
/*     */   protected String getDisplayValue(Object option) {
/* 138 */     if (option == null) {
/* 139 */       return this.context.getLabel("no.selection");
/*     */     }
/* 141 */     return ((SITOCElement)option).getLabel(LocaleInfoProvider.getInstance().getLocale(this.context.getLocale()));
/*     */   }
/*     */   
/*     */   protected String getSubmitValue(Object option) {
/* 145 */     if (option == null) {
/* 146 */       return "null";
/*     */     }
/* 148 */     return String.valueOf(getOptions().indexOf(option));
/*     */   }
/*     */ 
/*     */   
/*     */   protected Object getOption(String submitValue) {
/* 153 */     if (submitValue.equals("null")) {
/* 154 */       return null;
/*     */     }
/*     */     try {
/* 157 */       int index = Integer.parseInt(submitValue);
/* 158 */       return getOptions().get(index);
/* 159 */     } catch (Exception e) {
/* 160 */       return null;
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\si\implementation\i\\ui\html\sitfilter\SITSelectBox.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */