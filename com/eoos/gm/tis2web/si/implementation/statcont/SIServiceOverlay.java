/*     */ package com.eoos.gm.tis2web.si.implementation.statcont;
/*     */ 
/*     */ import com.eoos.condition.Condition;
/*     */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*     */ import com.eoos.gm.tis2web.frame.export.common.ClientContextProvider;
/*     */ import com.eoos.gm.tis2web.frame.export.common.sharedcontext.SharedContextProxy;
/*     */ import com.eoos.gm.tis2web.si.implementation.statcont.datamodel.system.DataProvider;
/*     */ import com.eoos.gm.tis2web.si.implementation.statcont.ui.html.main.MainPage;
/*     */ import com.eoos.gm.tis2web.vc.v2.base.provider.CfgProvider;
/*     */ import com.eoos.gm.tis2web.vc.v2.configuration.VehicleConfigurationUtil;
/*     */ import com.eoos.gm.tis2web.vc.v2.service.IVCFacade;
/*     */ import com.eoos.gm.tis2web.vc.v2.service.VCFacade;
/*     */ import com.eoos.instantiation.Singleton;
/*     */ import com.eoos.scsm.v2.util.Util;
/*     */ import com.eoos.util.AssertUtil;
/*     */ import java.io.LineNumberReader;
/*     */ import java.io.StringReader;
/*     */ import java.util.HashSet;
/*     */ import java.util.Iterator;
/*     */ import java.util.Locale;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import java.util.StringTokenizer;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ public class SIServiceOverlay
/*     */   implements Singleton, CfgProvider
/*     */ {
/*  29 */   private static final Logger log = Logger.getLogger(SIServiceOverlay.class);
/*     */   
/*  31 */   private static SIServiceOverlay instance = null;
/*     */   
/*  33 */   private static final Condition CONDITION_TOKENIZER_LINE_FINISHED = new Condition()
/*     */     {
/*     */       public boolean check(Object obj) {
/*  36 */         return !((StringTokenizer)obj).hasMoreTokens();
/*     */       }
/*     */     };
/*     */ 
/*     */   
/*     */   private static class MatchData
/*     */   {
/*     */     private String locale;
/*     */     
/*     */     private String make;
/*     */     
/*     */     private String model;
/*     */     private String modelyear;
/*     */     
/*     */     private MatchData(String locale, String make, String model, String modelyear) {
/*  51 */       this.locale = locale;
/*  52 */       this.make = normalize(make);
/*  53 */       this.model = normalize(model);
/*  54 */       this.modelyear = normalize(modelyear);
/*     */     }
/*     */     
/*     */     private String normalize(String str) {
/*  58 */       if (str == null) {
/*  59 */         return "";
/*     */       }
/*  61 */       str = str.toLowerCase(Locale.ENGLISH);
/*  62 */       if (str.length() < 1) {
/*  63 */         return str;
/*     */       }
/*  65 */       while (Character.isWhitespace(str.charAt(0))) {
/*  66 */         str = str.substring(1);
/*     */       }
/*     */       
/*  69 */       while (Character.isWhitespace(str.charAt(str.length() - 1))) {
/*  70 */         str = str.substring(0, str.length() - 1);
/*     */       }
/*     */       
/*  73 */       return str;
/*     */     }
/*     */     
/*     */     private boolean matchLocale(String locale, String userLocale) {
/*  77 */       if (locale.equals("*")) {
/*  78 */         return true;
/*     */       }
/*  80 */       return (userLocale != null && userLocale.indexOf(locale) != -1);
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean matches(MatchData md) {
/*  85 */       boolean retValue = true;
/*  86 */       retValue = (retValue && matchLocale(this.locale, md.locale));
/*  87 */       retValue = (retValue && this.make.equals(md.make));
/*  88 */       if (md.model == "" || md.modelyear == "")
/*     */       {
/*  90 */         return retValue;
/*     */       }
/*     */       
/*  93 */       retValue = (retValue && this.model.equals(md.model));
/*  94 */       retValue = (retValue && this.modelyear.equals(md.modelyear));
/*  95 */       return retValue;
/*     */     }
/*     */   }
/*     */   
/*     */   private static class StartTableEntry
/*     */   {
/*     */     private SIServiceOverlay.MatchData md;
/*     */     private String startDocument;
/*     */     
/*     */     public StartTableEntry(SIServiceOverlay.MatchData md, String startDocument) {
/* 105 */       this.md = md;
/* 106 */       if (!startDocument.startsWith("/")) {
/* 107 */         startDocument = "/" + startDocument;
/*     */       }
/* 109 */       this.startDocument = startDocument.toLowerCase(Locale.ENGLISH);
/*     */     }
/*     */     
/*     */     public SIServiceOverlay.MatchData getMatchData() {
/* 113 */       return this.md;
/*     */     }
/*     */     
/*     */     public String getStartDocument() {
/* 117 */       return this.startDocument;
/*     */     }
/*     */   }
/*     */ 
/*     */   
/* 122 */   private Set startTableEntries = new HashSet();
/*     */   
/* 124 */   private Set configurations = new HashSet();
/*     */ 
/*     */ 
/*     */   
/*     */   private SIServiceOverlay() {
/*     */     try {
/* 130 */       String data = new String(DataProvider.getInstance().getData("vc2staticstart"), "UTF-8");
/* 131 */       StringReader reader = new StringReader(data);
/* 132 */       LineNumberReader lnr = new LineNumberReader(reader);
/*     */       
/* 134 */       String line = null;
/* 135 */       int count = 0;
/* 136 */       while ((line = lnr.readLine()) != null) {
/* 137 */         count++;
/*     */         try {
/* 139 */           StringTokenizer tokenizer = new StringTokenizer(line, "\t");
/* 140 */           String locale = tokenizer.nextToken();
/* 141 */           String make = tokenizer.nextToken();
/* 142 */           String model = tokenizer.nextToken();
/* 143 */           String modelyear = tokenizer.nextToken();
/*     */           
/* 145 */           this.configurations.add(VehicleConfigurationUtil.createVC(make, model, modelyear, null, null));
/*     */           
/* 147 */           MatchData md = new MatchData(locale, make, model, modelyear);
/* 148 */           String startDoc = tokenizer.nextToken();
/* 149 */           AssertUtil.ensure(tokenizer, CONDITION_TOKENIZER_LINE_FINISHED);
/*     */           
/* 151 */           this.startTableEntries.add(new StartTableEntry(md, startDoc));
/* 152 */         } catch (Exception e) {
/* 153 */           log.error("unable to read/parse entry in vc2staticstart - line:" + count);
/*     */         }
/*     */       
/*     */       } 
/* 157 */     } catch (Exception e) {
/* 158 */       log.error("exception: " + e, e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static synchronized SIServiceOverlay getInstance() {
/* 165 */     if (instance == null) {
/* 166 */       instance = new SIServiceOverlay();
/*     */     }
/* 168 */     return instance;
/*     */   }
/*     */   
/*     */   public Object getUI(String sessionID, Map parameters) {
/* 172 */     ClientContext context = ClientContextProvider.getInstance().getContext(sessionID);
/* 173 */     SharedContextProxy.getInstance(context).update();
/* 174 */     return MainPage.getInstance(context).getHtmlCode(parameters);
/*     */   }
/*     */   
/*     */   public boolean isActive(ClientContext context) {
/* 178 */     boolean retValue = false;
/* 179 */     IVCFacade facade = VCFacade.getInstance(context);
/* 180 */     if (!Util.isNullOrEmpty(facade.getCurrentSalesmake()) && this.startTableEntries.size() > 0) {
/* 181 */       String make = facade.getCurrentSalesmake();
/* 182 */       String model = facade.getCurrentModel();
/* 183 */       String modelyear = facade.getCurrentModelyear();
/* 184 */       MatchData vd = new MatchData(String.valueOf(context.getLocale()), make, model, modelyear);
/* 185 */       for (Iterator<StartTableEntry> iter = this.startTableEntries.iterator(); iter.hasNext() && !retValue; ) {
/* 186 */         StartTableEntry entry = iter.next();
/* 187 */         retValue = entry.getMatchData().matches(vd);
/*     */       } 
/*     */     } 
/* 190 */     return retValue;
/*     */   }
/*     */   
/*     */   public String getStartDocument(ClientContext context) {
/* 194 */     IVCFacade facade = VCFacade.getInstance(context);
/*     */     
/* 196 */     String make = facade.getCurrentSalesmake();
/* 197 */     String model = facade.getCurrentModel();
/* 198 */     String modelyear = facade.getCurrentModelyear();
/* 199 */     MatchData vd = new MatchData(String.valueOf(context.getLocale()), make, model, modelyear);
/* 200 */     for (Iterator<StartTableEntry> iter = this.startTableEntries.iterator(); iter.hasNext(); ) {
/* 201 */       StartTableEntry entry = iter.next();
/* 202 */       if (entry.getMatchData().matches(vd)) {
/* 203 */         return entry.getStartDocument();
/*     */       }
/*     */     } 
/* 206 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public Set getConfigurations() {
/* 211 */     return this.configurations;
/*     */   }
/*     */   
/*     */   public Set getKeys() {
/* 215 */     return VehicleConfigurationUtil.KEY_SET;
/*     */   }
/*     */   
/*     */   public long getLastModified() {
/* 219 */     return 0L;
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\si\implementation\statcont\SIServiceOverlay.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */