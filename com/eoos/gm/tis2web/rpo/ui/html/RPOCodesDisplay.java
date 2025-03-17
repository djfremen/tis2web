/*     */ package com.eoos.gm.tis2web.rpo.ui.html;
/*     */ 
/*     */ import com.eoos.gm.tis2web.frame.export.common.ApplicationContext;
/*     */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*     */ import com.eoos.gm.tis2web.frame.export.common.ResourceController;
/*     */ import com.eoos.gm.tis2web.frame.export.common.ui.html.DialogBase;
/*     */ import com.eoos.gm.tis2web.frame.export.common.ui.html.PrintViewLink;
/*     */ import com.eoos.gm.tis2web.rpo.api.RPO;
/*     */ import com.eoos.gm.tis2web.rpo.api.RPOContainer;
/*     */ import com.eoos.gm.tis2web.rpo.api.RPOFamily;
/*     */ import com.eoos.html.element.HtmlElement;
/*     */ import com.eoos.html.element.input.ClickButtonElement;
/*     */ import com.eoos.html.element.input.LinkElement;
/*     */ import com.eoos.scsm.v2.util.StringUtilities;
/*     */ import com.eoos.scsm.v2.util.Util;
/*     */ import java.util.Iterator;
/*     */ import java.util.Locale;
/*     */ import java.util.Map;
/*     */ import java.util.regex.Matcher;
/*     */ import java.util.regex.Pattern;
/*     */ import org.apache.regexp.RE;
/*     */ import org.apache.regexp.RESyntaxException;
/*     */ 
/*     */ public class RPOCodesDisplay
/*     */   extends DialogBase {
/*  26 */   private static final Pattern PATTERN_NORPOS = Pattern.compile("<!--\\s+NORPOS.*?/NORPOS\\s+-->", 42);
/*     */   private static String template;
/*     */   
/*     */   static {
/*     */     try {
/*  31 */       template = ApplicationContext.getInstance().loadFile(RPOCodesDisplay.class, "rpocodes.html", null).toString();
/*  32 */     } catch (Exception e) {
/*  33 */       throw new RuntimeException("unable to load template", e);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private static final String templateCODE = "<a href=\"#{IDCODE}\" onmouseover=\"Tip('{DESCRIPTION}')\" onmouseout=\"UnTip()\">{CODE}</a>";
/*     */   
/*     */   private static final String templateRPODetail = "<tr id=\"{IDCODE}\"><td>{CODE}</td><td>{FAMILY}</td><td>{DESCRIPTION}</td></tr>";
/*     */   
/*     */   private ClientContext context;
/*     */   
/*     */   private String vin;
/*     */   
/*     */   private RPOContainer container;
/*     */   
/*     */   private LinkElement linkPrint;
/*     */   
/*     */   private ClickButtonElement buttonClose;
/*     */   
/*     */   public RPOCodesDisplay(final ClientContext context, String vin, RPOContainer container) {
/*  53 */     super(context);
/*  54 */     this.context = context;
/*  55 */     this.vin = vin.toUpperCase(Locale.ENGLISH);
/*  56 */     this.container = container;
/*     */     
/*  58 */     this.buttonClose = new ClickButtonElement(context.createID(), null)
/*     */       {
/*     */         protected String getLabel() {
/*  61 */           return context.getLabel("close");
/*     */         }
/*     */         
/*     */         public Object onClick(Map submitParams) {
/*  65 */           return MainPage.getInstance(context);
/*     */         }
/*     */       };
/*     */     
/*  69 */     addElement((HtmlElement)this.buttonClose);
/*     */     
/*  71 */     this.linkPrint = (LinkElement)new PrintViewLink(context)
/*     */       {
/*     */         public Object onClick(Map submitParams) {
/*  74 */           StringBuffer tmp = new StringBuffer(RPOCodesDisplay.this.getHtmlCode(submitParams));
/*     */           
/*  76 */           Util.delete(tmp, Pattern.compile("<!--\\s*XPRINT.*?/XPRINT\\s*-->", 32));
/*     */ 
/*     */           
/*  79 */           Pattern patternHyperlink = Pattern.compile("<a\\s+[^>]*>(.*?)</a>");
/*  80 */           Util.replace(tmp, patternHyperlink, new Util.ReplacementCallback()
/*     */               {
/*     */                 public CharSequence getReplacement(CharSequence match, Util.ReplacementCallback.MatcherCallback matcherCallback) {
/*  83 */                   return matcherCallback.getGroup(1);
/*     */                 }
/*     */               });
/*     */ 
/*     */           
/*  88 */           Util.delete(tmp, Pattern.compile("<script.*?</script>", 32));
/*     */           
/*  90 */           Pattern patternDialog = Pattern.compile("<table\\s+id=\"rpocodesdialog\"\\s+(class=\"dialog\".*?)>.*?<td\\s+(.*?)>(.*?)</td>.*?<td\\s+(.*?)>", 32);
/*  91 */           Matcher matcher = patternDialog.matcher(tmp);
/*  92 */           if (matcher.find()) {
/*  93 */             int indexCorrection = 0;
/*  94 */             tmp.delete(matcher.start(1), matcher.end(1));
/*  95 */             indexCorrection = 0 - matcher.end(1) - matcher.start(1);
/*     */             
/*  97 */             tmp.delete(matcher.start(2) + indexCorrection, matcher.end(2) + indexCorrection);
/*  98 */             indexCorrection += 0 - matcher.end(2) - matcher.start(2);
/*     */             
/* 100 */             String group3 = tmp.substring(matcher.start(3) + indexCorrection, matcher.end(3) + indexCorrection);
/* 101 */             String replacement = "<h1>" + group3 + "</h1>";
/* 102 */             tmp.replace(matcher.start(3) + indexCorrection, matcher.end(3) + indexCorrection, replacement);
/* 103 */             indexCorrection += replacement.length() - group3.length();
/*     */             
/* 105 */             tmp.delete(matcher.start(4) + indexCorrection, matcher.end(4) + indexCorrection);
/* 106 */             indexCorrection += 0 - matcher.end(4) - matcher.start(4);
/*     */           } 
/*     */ 
/*     */           
/* 110 */           return tmp.toString();
/*     */         }
/*     */       };
/*     */ 
/*     */     
/* 115 */     addElement((HtmlElement)this.linkPrint);
/*     */   }
/*     */ 
/*     */   
/*     */   protected String getID() {
/* 120 */     return "rpocodesdialog";
/*     */   }
/*     */   protected String getContent(final Map params) {
/*     */     RE pattern;
/* 124 */     StringBuffer ret = new StringBuffer(template);
/*     */ 
/*     */     
/*     */     try {
/* 128 */       pattern = new RE("\\{.*?\\}");
/* 129 */     } catch (RESyntaxException e) {
/* 130 */       throw new RuntimeException(e);
/*     */     } 
/*     */     
/* 133 */     StringUtilities.replace(ret, pattern, 0, new StringUtilities.ReplaceCallback()
/*     */         {
/*     */           public CharSequence getReplacement(CharSequence sectionContent) {
/* 136 */             CharSequence ret = sectionContent;
/* 137 */             if (sectionContent.equals("{TOOLTIPSCRIPT}")) {
/* 138 */               ret = Util.escapeReservedHTMLChars(ResourceController.getInstance(RPOCodesDisplay.this.context).getURL("common/wz_tooltip.js", "text/javascript"));
/*     */             }
/* 140 */             if (sectionContent.equals("{LABEL_VIN}")) {
/* 141 */               ret = RPOCodesDisplay.this.context.getLabel("vin");
/* 142 */             } else if (sectionContent.equals("{LINK_PRINTVIEW}")) {
/* 143 */               ret = RPOCodesDisplay.this.linkPrint.getHtmlCode(params);
/* 144 */             } else if (sectionContent.equals("{VIN}")) {
/* 145 */               ret = RPOCodesDisplay.this.vin;
/* 146 */             } else if (sectionContent.equals("{LABEL_MODELDESIG}")) {
/* 147 */               ret = RPOCodesDisplay.this.context.getLabel("model.designator");
/* 148 */             } else if (sectionContent.equals("{MODELDESIG}")) {
/* 149 */               ret = RPOCodesDisplay.this.container.getModelDesignator();
/* 150 */             } else if (sectionContent.equals("{LABEL_VEHNNUM}")) {
/* 151 */               ret = RPOCodesDisplay.this.context.getLabel("vehicle.number");
/* 152 */             } else if (sectionContent.equals("{VEHNUM}")) {
/* 153 */               ret = RPOCodesDisplay.this.container.getVehicleNumber();
/* 154 */               if (Util.isNullOrEmpty(ret)) {
/* 155 */                 ret = RPOCodesDisplay.this.context.getLabel("not.available");
/*     */               }
/* 157 */             } else if (sectionContent.equals("{LABEL_OPTIONCODES}")) {
/* 158 */               ret = RPOCodesDisplay.this.context.getLabel("vehicle.option.codes");
/* 159 */             } else if (sectionContent.equals("{LABEL_CODE}")) {
/* 160 */               ret = RPOCodesDisplay.this.context.getLabel("rpo.code");
/* 161 */             } else if (sectionContent.equals("{LABEL_FAMILY}")) {
/* 162 */               ret = RPOCodesDisplay.this.context.getLabel("family");
/* 163 */             } else if (sectionContent.equals("{LABEL_DESCRIPTION}")) {
/* 164 */               ret = RPOCodesDisplay.this.context.getLabel("description");
/* 165 */             } else if (sectionContent.equals("{BUTTON_CLOSE}")) {
/* 166 */               ret = RPOCodesDisplay.this.buttonClose.getHtmlCode(params);
/*     */             } 
/* 168 */             return ret;
/*     */           }
/*     */         });
/*     */ 
/*     */     
/* 173 */     boolean hasContent = false;
/* 174 */     for (Iterator<RPO> iter = this.container.getRPOs().iterator(); iter.hasNext(); ) {
/* 175 */       StringBuffer code; RPO rpo = iter.next();
/*     */       
/* 177 */       String description = getDescription(rpo);
/* 178 */       String id = "ID" + rpo.getCode();
/* 179 */       if (Util.isNullOrEmpty(description)) {
/* 180 */         code = new StringBuffer(rpo.getCode());
/*     */       } else {
/* 182 */         code = new StringBuffer("<a href=\"#{IDCODE}\" onmouseover=\"Tip('{DESCRIPTION}')\" onmouseout=\"UnTip()\">{CODE}</a>");
/* 183 */         StringUtilities.replace(code, "{IDCODE}", id);
/* 184 */         StringUtilities.replace(code, "{CODE}", rpo.getCode());
/* 185 */         StringUtilities.replace(code, "{DESCRIPTION}", rpo.getDescription(this.context.getLocale()));
/*     */       } 
/* 187 */       StringUtilities.replace(ret, "{OPTIONCODES}", code.toString() + ", {OPTIONCODES}");
/*     */       
/* 189 */       if (!Util.isNullOrEmpty(description)) {
/* 190 */         hasContent = true;
/* 191 */         StringBuffer row = new StringBuffer("<tr id=\"{IDCODE}\"><td>{CODE}</td><td>{FAMILY}</td><td>{DESCRIPTION}</td></tr>");
/* 192 */         StringUtilities.replace(row, "{IDCODE}", id);
/* 193 */         StringUtilities.replace(row, "{CODE}", rpo.getCode());
/* 194 */         StringUtilities.replace(row, "{FAMILY}", Util.escapeReservedHTMLChars(getFamilyDescription(rpo)));
/* 195 */         StringUtilities.replace(row, "{DESCRIPTION}", Util.escapeReservedHTMLChars(rpo.getDescription(this.context.getLocale())));
/* 196 */         StringUtilities.replace(ret, "{ROWS}", row + "\n{ROWS}");
/*     */       } 
/*     */     } 
/* 199 */     StringUtilities.replace(ret, ", {OPTIONCODES}", "");
/* 200 */     StringUtilities.replace(ret, "\n{ROWS}", "");
/*     */     
/* 202 */     if (!hasContent) {
/* 203 */       Util.delete(ret, PATTERN_NORPOS);
/*     */     }
/*     */     
/* 206 */     return ret.toString();
/*     */   }
/*     */   
/*     */   private String getDescription(RPO rpo) {
/* 210 */     return rpo.getDescription(this.context.getLocale());
/*     */   }
/*     */   
/*     */   private String getFamilyDescription(RPO rpo) {
/* 214 */     String ret = null;
/* 215 */     if (rpo != null) {
/* 216 */       RPOFamily family = rpo.getFamily();
/* 217 */       if (family != null) {
/* 218 */         ret = family.getDescription(this.context.getLocale());
/*     */       }
/*     */     } 
/* 221 */     return ret;
/*     */   }
/*     */   
/*     */   protected String getTitle(Map params) {
/* 225 */     return this.context.getLabel("rpo.codes");
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\rp\\ui\html\RPOCodesDisplay.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */