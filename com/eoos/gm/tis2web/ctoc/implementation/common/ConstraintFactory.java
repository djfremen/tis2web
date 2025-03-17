/*     */ package com.eoos.gm.tis2web.ctoc.implementation.common;
/*     */ 
/*     */ import com.eoos.gm.tis2web.frame.export.common.locale.LocaleInfo;
/*     */ import com.eoos.gm.tis2web.frame.export.common.locale.LocaleInfoProvider;
/*     */ import com.eoos.gm.tis2web.vcr.service.cai.ICRList;
/*     */ import com.eoos.gm.tis2web.vcr.service.cai.VCR;
/*     */ import com.eoos.gm.tis2web.vcr.service.cai.VCRAttribute;
/*     */ import com.eoos.gm.tis2web.vcr.service.cai.VCRExpression;
/*     */ import com.eoos.gm.tis2web.vcr.service.cai.VCRTerm;
/*     */ import com.eoos.gm.tis2web.vcr.v2.ILVCAdapter;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Locale;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
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
/*     */ public class ConstraintFactory
/*     */ {
/*     */   public static final String SCDS = "SCDS";
/*     */   public static final String TSB = "TSB";
/*     */   public static final String CPR = "CPR";
/*     */   public static final String WD = "WD";
/*     */   public static final String LT = "LT";
/*     */   public static final String SDL = "Software Download";
/*     */   public static final String FB = "Feedback";
/*     */   public static final String NEWS = "News";
/*     */   public static final String HELP = "Help";
/*     */   public static final int APPLICATION = 1;
/*     */   public static final int SIT = 2;
/*     */   public static final int MANUFACTURER = 3;
/*     */   public static final int GROUP = 4;
/*     */   public static final int COUNTRY = 5;
/*     */   public static final int LOCALE = 9;
/*     */   public static final String NULL_SIT = "SIT-0";
/*  56 */   protected static Map ApplicationDomain = new HashMap<Object, Object>();
/*     */   
/*  58 */   protected static Map ServiceInformationTypeDomain = new HashMap<Object, Object>();
/*     */   
/*  60 */   protected static Map ManufacturerDomain = new HashMap<Object, Object>();
/*     */   
/*  62 */   protected static Map GroupDomain = new HashMap<Object, Object>();
/*     */   
/*  64 */   protected static Map CountryDomain = new HashMap<Object, Object>();
/*     */   
/*     */   protected static final String NULL = "-NULL-";
/*     */   
/*  68 */   protected static int maxID = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String translateSIT(String sit) {
/*  74 */     if (sit == null) {
/*  75 */       return null;
/*     */     }
/*  77 */     if (sit.indexOf("SIT-") < 0) {
/*  78 */       sit = "SIT-" + sit;
/*     */     }
/*  80 */     if (sit.equalsIgnoreCase("SIT-10"))
/*  81 */       return "CPR"; 
/*  82 */     if (sit.equalsIgnoreCase("SIT-9"))
/*  83 */       return "LT"; 
/*  84 */     if (sit.equalsIgnoreCase("SIT-15"))
/*  85 */       return "WD"; 
/*  86 */     if (sit.equalsIgnoreCase("SIT-12")) {
/*  87 */       return "TSB";
/*     */     }
/*  89 */     return "SCDS";
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static VCR makeConstraintVCR(LocaleInfo locale, String application, Set sits, Set manufacturers, Set groups, String country, ILVCAdapter.Retrieval lvcRetrieval) {
/*  95 */     VCR vcr = lvcRetrieval.getLVCAdapter().makeVCR(makeConstraintExpressionVCR(locale, application, null, null, null, country, lvcRetrieval));
/*     */     
/*  97 */     if (!"rpo".equalsIgnoreCase(application) && !"kdr".equalsIgnoreCase(application) && !"vc".equalsIgnoreCase(application) && !"sas".equalsIgnoreCase(application) && !"sps_ci".equalsIgnoreCase(application) && !"sps".equalsIgnoreCase(application) && !"swdl".equalsIgnoreCase(application) && !"home".equalsIgnoreCase(application) && !"profile".equalsIgnoreCase(application))
/*     */     {
/*     */       
/* 100 */       if (sits != null && sits.size() > 0) {
/* 101 */         Iterator<String> iter = sits.iterator();
/* 102 */         while (iter.hasNext()) {
/* 103 */           String sit = iter.next();
/* 104 */           if (!sit.equals("SIT-0")) {
/* 105 */             vcr = extendConstraintVCR(vcr, 2, sit, lvcRetrieval);
/*     */           }
/*     */         } 
/*     */       } else {
/* 109 */         vcr = extendConstraintVCR(vcr, 2, "SIT-0", lvcRetrieval);
/*     */       } 
/*     */     }
/* 112 */     if (manufacturers != null) {
/* 113 */       Iterator<String> iter = manufacturers.iterator();
/* 114 */       while (iter.hasNext()) {
/* 115 */         String manufacturer = iter.next();
/* 116 */         vcr = extendConstraintVCR(vcr, 3, manufacturer, lvcRetrieval);
/*     */       } 
/*     */     } 
/*     */     
/* 120 */     if (groups != null) {
/* 121 */       Iterator<String> iter = groups.iterator();
/* 122 */       while (iter.hasNext()) {
/* 123 */         String group = iter.next();
/* 124 */         vcr = extendConstraintVCR(vcr, 4, group, lvcRetrieval);
/*     */       } 
/*     */     } 
/* 127 */     return vcr;
/*     */   }
/*     */ 
/*     */   
/*     */   public static VCR makeConstraintVCR(LocaleInfo locale, String application, String sit, String manufacturer, String group, String country, ILVCAdapter.Retrieval lvcRetrieval) {
/* 132 */     return lvcRetrieval.getLVCAdapter().makeVCR(makeConstraintExpressionVCR(locale, application, sit, manufacturer, group, country, lvcRetrieval));
/*     */   }
/*     */   
/*     */   public static VCR makeConstraintVCR(String application, String sit, String manufacturer, String group, String country, ILVCAdapter.Retrieval lvcRetrieval) {
/* 136 */     return lvcRetrieval.getLVCAdapter().makeVCR(makeConstraintExpressionVCR(null, application, sit, manufacturer, group, country, lvcRetrieval));
/*     */   }
/*     */   
/*     */   public static VCRExpression makeConstraintExpressionVCR(String application, String sit, String manufacturer, String group, String country, ILVCAdapter.Retrieval lvcRetrieval) {
/* 140 */     return makeConstraintExpressionVCR(null, application, sit, manufacturer, group, country, lvcRetrieval);
/*     */   }
/*     */ 
/*     */   
/*     */   public static VCRExpression makeConstraintExpressionVCR(LocaleInfo locale, String application, String sit, String manufacturer, String group, String country, ILVCAdapter.Retrieval lvcRetrieval) {
/* 145 */     VCRExpression expression = lvcRetrieval.getLVCAdapter().makeExpression();
/* 146 */     boolean valid = false;
/* 147 */     if (locale != null) {
/* 148 */       expression.add(lvcRetrieval.getLVCAdapter().makeAttribute(9, locale.getLocaleID().intValue()));
/* 149 */       valid = true;
/*     */     } 
/* 151 */     if (application != null) {
/* 152 */       expression.add(makeConstraintAttribute(1, ApplicationDomain, application, lvcRetrieval));
/* 153 */       valid = true;
/*     */     } 
/* 155 */     if (sit != null) {
/* 156 */       expression.add(makeConstraintAttribute(2, ServiceInformationTypeDomain, sit, lvcRetrieval));
/* 157 */       valid = true;
/*     */     } 
/* 159 */     if (manufacturer != null) {
/* 160 */       expression.add(makeConstraintAttribute(3, ManufacturerDomain, manufacturer, lvcRetrieval));
/* 161 */       valid = true;
/*     */     } 
/* 163 */     if (group != null) {
/* 164 */       expression.add(makeConstraintAttribute(4, GroupDomain, group, lvcRetrieval));
/* 165 */       valid = true;
/*     */     } 
/* 167 */     if (country != null) {
/* 168 */       expression.add(makeConstraintAttribute(5, CountryDomain, country, lvcRetrieval));
/* 169 */       valid = true;
/*     */     } 
/* 171 */     return valid ? expression : null;
/*     */   }
/*     */ 
/*     */   
/*     */   public static VCR extendConstraintVCR(VCR vcr, int dom, String value, ILVCAdapter.Retrieval lvcRetrieval) {
/* 176 */     Map domain = selectDomain(dom);
/* 177 */     VCRTerm term = lvcRetrieval.getLVCAdapter().makeTerm(makeConstraintAttribute(dom, domain, value, lvcRetrieval));
/* 178 */     return vcr.fold(term);
/*     */   }
/*     */   
/*     */   public static VCR extendConstraintVCR(VCR vcr, int dom, List<LocaleInfo> values, ILVCAdapter.Retrieval lvcRetrieval) {
/* 182 */     if (values == null || values.size() == 0) {
/* 183 */       return vcr;
/*     */     }
/* 185 */     if (dom != 9) {
/* 186 */       throw new IllegalArgumentException();
/*     */     }
/*     */     
/* 189 */     VCRTerm term = lvcRetrieval.getLVCAdapter().makeTerm();
/* 190 */     for (int i = 0; i < values.size(); i++) {
/* 191 */       LocaleInfo locale = values.get(i);
/* 192 */       term.add(lvcRetrieval.getLVCAdapter().makeAttribute(dom, locale.getLocaleID().intValue()));
/*     */     } 
/* 194 */     return (vcr != null) ? vcr.fold(term) : lvcRetrieval.getLVCAdapter().makeVCR(term);
/*     */   }
/*     */   
/*     */   private static VCRAttribute makeConstraintAttribute(int dom, Map<String, Integer> domain, String value, ILVCAdapter.Retrieval lvcRetrieval) {
/* 198 */     value = value.toUpperCase(Locale.ENGLISH);
/* 199 */     Integer encoding = (Integer)domain.get(value);
/* 200 */     if (encoding == null) {
/* 201 */       encoding = new Integer(++maxID);
/* 202 */       domain.put(value, encoding);
/*     */     } 
/* 204 */     return lvcRetrieval.getLVCAdapter().makeAttribute(dom, encoding.intValue());
/*     */   }
/*     */   
/*     */   private static Map selectDomain(int dom) {
/* 208 */     Map domain = null;
/* 209 */     if (dom == 1) {
/* 210 */       domain = ApplicationDomain;
/* 211 */     } else if (dom == 2) {
/* 212 */       domain = ServiceInformationTypeDomain;
/* 213 */     } else if (dom == 3) {
/* 214 */       domain = ManufacturerDomain;
/* 215 */     } else if (dom == 4) {
/* 216 */       domain = GroupDomain;
/* 217 */     } else if (dom == 5) {
/* 218 */       domain = CountryDomain;
/*     */     } else {
/* 220 */       throw new IllegalArgumentException();
/*     */     } 
/* 222 */     return domain;
/*     */   }
/*     */   
/*     */   protected static String lookupDomain(VCRAttribute attribute) {
/* 226 */     if (attribute.getDomainID() == 9) {
/* 227 */       return LocaleInfoProvider.getInstance().getLocale(attribute.getValueID()).getLocale();
/*     */     }
/* 229 */     Map domain = selectDomain(attribute.getDomainID());
/* 230 */     Iterator<String> it = domain.keySet().iterator();
/* 231 */     while (it.hasNext()) {
/* 232 */       String key = it.next();
/* 233 */       Integer encoding = (Integer)domain.get(key);
/* 234 */       if (encoding.intValue() == attribute.getValueID()) {
/* 235 */         return key;
/*     */       }
/*     */     } 
/* 238 */     return "unknown";
/*     */   }
/*     */   
/*     */   public static String toString(VCR vcr, ILVCAdapter.Retrieval lvcRetrieval) {
/* 242 */     if (VCR.NULL == vcr) {
/* 243 */       return "NO-CONSTRAINT";
/*     */     }
/* 245 */     StringBuffer constraint = new StringBuffer();
/* 246 */     ICRList<VCRExpression> iCRList = vcr.getExpressions();
/* 247 */     if (iCRList != null) {
/* 248 */       for (int k = 0; k < iCRList.size(); k++) {
/* 249 */         if (k > 0) {
/* 250 */           constraint.append(" or ");
/*     */         }
/* 252 */         constraint.append("[");
/* 253 */         VCRExpression expression = iCRList.get(k);
/* 254 */         List<VCRTerm> terms = expression.getTerms();
/* 255 */         if (terms != null) {
/* 256 */           for (int i = 0; i < terms.size(); i++) {
/* 257 */             if (i > 0) {
/* 258 */               constraint.append(" and ");
/*     */             }
/* 260 */             VCRTerm term = terms.get(i);
/* 261 */             List<VCRAttribute> attributes = term.getAttributes();
/* 262 */             for (int j = 0; j < attributes.size(); j++) {
/* 263 */               if (j > 0) {
/* 264 */                 constraint.append("|");
/*     */               }
/* 266 */               VCRAttribute attribute = attributes.get(j);
/* 267 */               String value = lookupDomain(attribute);
/* 268 */               constraint.append(value);
/*     */             } 
/*     */           } 
/*     */         }
/* 272 */         constraint.append("]");
/*     */       } 
/*     */     }
/* 275 */     return constraint.toString();
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\ctoc\implementation\common\ConstraintFactory.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */