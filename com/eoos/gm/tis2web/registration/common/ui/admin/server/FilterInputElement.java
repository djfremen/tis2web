/*     */ package com.eoos.gm.tis2web.registration.common.ui.admin.server;
/*     */ 
/*     */ import com.eoos.automat.Acceptor;
/*     */ import com.eoos.automat.StringAcceptor;
/*     */ import com.eoos.filter.Filter;
/*     */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*     */ import com.eoos.gm.tis2web.registration.common.ui.base.DateSelectionElement;
/*     */ import com.eoos.gm.tis2web.registration.common.ui.datamodel.AuthorizationRequest;
/*     */ import com.eoos.html.element.HtmlElement;
/*     */ import com.eoos.html.element.HtmlElementContainerBase;
/*     */ import com.eoos.html.element.gtwo.DataRetrievalAbstraction;
/*     */ import com.eoos.html.element.gtwo.SelectBoxSelectionElement;
/*     */ import com.eoos.html.element.input.TextInputElement;
/*     */ import com.eoos.propcfg.Configuration;
/*     */ import com.eoos.propcfg.TypeDecorator;
/*     */ import com.eoos.util.v2.StringUtilities;
/*     */ import java.util.Date;
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ public class FilterInputElement
/*     */   extends HtmlElementContainerBase
/*     */ {
/*  26 */   private static final Logger log = Logger.getLogger(FilterInputElement.class);
/*     */   
/*     */   private static final String TEMPLATE = "<table><tr><th>{LABEL_REQUESTID}:</th><td>{INPUT_REQUESTID}</td><th>{LABEL_FROM}:</th><td>{INPUT_FROM}</td></tr><tr><th>{LABEL_DEALERSHIPID}:</th><td>{INPUT_DEALERSHIPID}</td><th>{LABEL_UNTIL}:</th><td>{INPUT_UNTIL}</td></tr><tr><th>{LABEL_DEALERSHIP}:</th><td>{INPUT_DEALERSHIP}</td><th>{LABEL_STATUS}:</th><td>{INPUT_STATUS}</td></tr></table>";
/*     */   
/*  30 */   private static final Object NO_RESTRICTION = new Object();
/*     */ 
/*     */   
/*     */   private ClientContext context;
/*     */ 
/*     */   
/*     */   private DateSelectionElement inputFrom;
/*     */   
/*     */   private DateSelectionElement inputUntil;
/*     */   
/*     */   private SelectBoxSelectionElement inputStatus;
/*     */   
/*     */   private TextInputElement inputRequestID;
/*     */   
/*     */   private TextInputElement space;
/*     */   
/*     */   private TextInputElement inputDealerShipID;
/*     */   
/*     */   private TextInputElement inputDealership;
/*     */ 
/*     */   
/*     */   public FilterInputElement(final ClientContext context) {
/*  52 */     this.context = context;
/*     */     
/*  54 */     Configuration cfg = ASServiceImpl_Registration.getInstance().getConfiguration();
/*  55 */     TypeDecorator td = new TypeDecorator(cfg);
/*     */     
/*  57 */     this.inputFrom = new DateSelectionElement(context.createID(), 0, context.getLocale(), 1995);
/*  58 */     addElement((HtmlElement)this.inputFrom);
/*     */ 
/*     */     
/*  61 */     int offsetStartDate = 0;
/*     */     try {
/*  63 */       offsetStartDate = Math.abs(td.getNumber("search.start.date.offset").intValue());
/*  64 */     } catch (Exception e) {
/*  65 */       log.warn("unable to read configuration parameter 'search.start.date.offset', setting to default: 24)");
/*  66 */       offsetStartDate = 24;
/*     */     } 
/*  68 */     long defaultTime = System.currentTimeMillis();
/*  69 */     defaultTime -= (offsetStartDate * 3600000);
/*  70 */     this.inputFrom.setValue(new Date(defaultTime));
/*     */     
/*  72 */     this.inputUntil = new DateSelectionElement(context.createID(), 0, context.getLocale(), 1995);
/*  73 */     addElement((HtmlElement)this.inputUntil);
/*  74 */     this.inputUntil.setValue(new Date(System.currentTimeMillis()));
/*     */     
/*  76 */     final List<AuthorizationRequest.Status> statusData = new LinkedList();
/*     */     
/*  78 */     statusData.add(AuthorizationRequest.STATUS_PENDING);
/*  79 */     statusData.add(AuthorizationRequest.STATUS_AUTHORIZED);
/*     */     
/*  81 */     DataRetrievalAbstraction.DataCallback dataCallback = new DataRetrievalAbstraction.DataCallback()
/*     */       {
/*     */         public List getData() {
/*  84 */           return statusData;
/*     */         }
/*     */       };
/*     */ 
/*     */     
/*  89 */     this.inputStatus = new SelectBoxSelectionElement(context.createID(), true, dataCallback, 1)
/*     */       {
/*     */         protected String getDisplayValue(Object option) {
/*  92 */           if (option == AuthorizationRequest.STATUS_AUTHORIZED)
/*  93 */             return context.getLabel("authorized"); 
/*  94 */           if (option == AuthorizationRequest.STATUS_PENDING) {
/*  95 */             return context.getLabel("pending");
/*     */           }
/*  97 */           return context.getLabel("no.restriction");
/*     */         }
/*     */       };
/*     */ 
/*     */     
/* 102 */     addElement((HtmlElement)this.inputStatus);
/* 103 */     this.inputStatus.setValue(AuthorizationRequest.STATUS_PENDING);
/*     */     
/* 105 */     this.inputRequestID = new TextInputElement(context.createID(), 30, 30);
/* 106 */     addElement((HtmlElement)this.inputRequestID);
/*     */     
/* 108 */     this.space = new TextInputElement(context.createID(), 30, 30) {
/*     */         protected void getAdditionalAttributes(Map<String, String> map) {
/* 110 */           map.put("style", "visibility:hidden; height:1px; overflow:hidden;");
/*     */         }
/*     */       };
/* 113 */     this.space.setValue("                                ");
/*     */     
/* 115 */     this.inputDealerShipID = new TextInputElement(context.createID(), 30, 30);
/* 116 */     addElement((HtmlElement)this.inputDealerShipID);
/*     */     
/* 118 */     this.inputDealership = new TextInputElement(context.createID(), 30, 30);
/* 119 */     addElement((HtmlElement)this.inputDealership);
/*     */   }
/*     */ 
/*     */   
/*     */   public String getHtmlCode(Map params) {
/* 124 */     StringBuffer ret = new StringBuffer("<table><tr><th>{LABEL_REQUESTID}:</th><td>{INPUT_REQUESTID}</td><th>{LABEL_FROM}:</th><td>{INPUT_FROM}</td></tr><tr><th>{LABEL_DEALERSHIPID}:</th><td>{INPUT_DEALERSHIPID}</td><th>{LABEL_UNTIL}:</th><td>{INPUT_UNTIL}</td></tr><tr><th>{LABEL_DEALERSHIP}:</th><td>{INPUT_DEALERSHIP}</td><th>{LABEL_STATUS}:</th><td>{INPUT_STATUS}</td></tr></table>");
/* 125 */     StringUtilities.replace(ret, "{LABEL_FROM}", this.context.getLabel("date.filter.start"));
/* 126 */     StringUtilities.replace(ret, "{LABEL_UNTIL}", this.context.getLabel("date.filter.end"));
/* 127 */     StringUtilities.replace(ret, "{INPUT_FROM}", this.inputFrom.getHtmlCode(params));
/* 128 */     StringUtilities.replace(ret, "{INPUT_UNTIL}", this.inputUntil.getHtmlCode(params));
/* 129 */     StringUtilities.replace(ret, "{LABEL_STATUS}", this.context.getLabel("status"));
/* 130 */     StringUtilities.replace(ret, "{INPUT_STATUS}", this.inputStatus.getHtmlCode(params));
/* 131 */     StringUtilities.replace(ret, "{LABEL_REQUESTID}", this.context.getLabel("request.id"));
/* 132 */     StringUtilities.replace(ret, "{INPUT_REQUESTID}", this.inputRequestID.getHtmlCode(params) + this.space.getHtmlCode(params));
/* 133 */     StringUtilities.replace(ret, "{LABEL_DEALERSHIPID}", this.context.getLabel("dealership.id"));
/* 134 */     StringUtilities.replace(ret, "{INPUT_DEALERSHIPID}", this.inputDealerShipID.getHtmlCode(params));
/* 135 */     StringUtilities.replace(ret, "{LABEL_DEALERSHIP}", this.context.getLabel("dealership"));
/* 136 */     StringUtilities.replace(ret, "{INPUT_DEALERSHIP}", this.inputDealership.getHtmlCode(params));
/*     */     
/* 138 */     return ret.toString();
/*     */   }
/*     */   
/*     */   public Date getDateFrom() {
/* 142 */     return (Date)this.inputFrom.getValue();
/*     */   }
/*     */   
/*     */   public Date getDateUntil() {
/* 146 */     return (Date)this.inputUntil.getValue();
/*     */   }
/*     */   
/*     */   public AuthorizationRequest.Status getStatus() {
/* 150 */     Object tmp = this.inputStatus.getValue();
/* 151 */     if (tmp == NO_RESTRICTION) {
/* 152 */       return null;
/*     */     }
/* 154 */     return (AuthorizationRequest.Status)tmp;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getRequestID() {
/* 159 */     return trim((String)this.inputRequestID.getValue());
/*     */   }
/*     */   
/*     */   public String getDealershipID() {
/* 163 */     return trim((String)this.inputDealerShipID.getValue());
/*     */   }
/*     */   
/*     */   public String getDealership() {
/* 167 */     return trim((String)this.inputDealership.getValue());
/*     */   }
/*     */   
/*     */   private String trim(String data) {
/* 171 */     if (data != null) {
/* 172 */       data = data.trim();
/* 173 */       return (data.length() > 0) ? data : null;
/*     */     } 
/* 175 */     return null;
/*     */   }
/*     */   public Object getValue() {
/*     */     StringAcceptor stringAcceptor1, stringAcceptor3, stringAcceptor5;
/* 179 */     final Date from = getDateFrom();
/* 180 */     final Date until = getDateUntil();
/* 181 */     final AuthorizationRequest.Status status = getStatus();
/*     */     
/* 183 */     String requestID = getRequestID();
/* 184 */     Acceptor _acceptorRequestID = null;
/* 185 */     if (requestID != null && requestID.trim().length() > 0) {
/* 186 */       stringAcceptor1 = StringAcceptor.create(requestID, true);
/*     */     }
/* 188 */     final StringAcceptor acceptorRequestID = stringAcceptor1;
/*     */     
/* 190 */     String dealershipID = getDealershipID();
/* 191 */     Acceptor _acceptorDealershipID = null;
/* 192 */     if (dealershipID != null && dealershipID.trim().length() > 0) {
/* 193 */       stringAcceptor3 = StringAcceptor.create(dealershipID, true);
/*     */     }
/* 195 */     final StringAcceptor acceptorDealershipID = stringAcceptor3;
/*     */     
/* 197 */     String dealership = getDealership();
/* 198 */     Acceptor _acceptorDealership = null;
/* 199 */     if (dealership != null && dealership.trim().length() > 0) {
/* 200 */       stringAcceptor5 = StringAcceptor.create(dealership, true);
/*     */     }
/* 202 */     final StringAcceptor acceptorDealership = stringAcceptor5;
/*     */     
/* 204 */     return new Filter()
/*     */       {
/*     */         public boolean include(Object obj) {
/* 207 */           boolean ret = true;
/*     */           try {
/* 209 */             AuthorizationRequest request = (AuthorizationRequest)obj;
/* 210 */             if (status != null) {
/* 211 */               ret = (ret && request.getStatus().equals(status));
/*     */             }
/* 213 */             ret = (ret && request.getRequestDate() >= from.getTime());
/* 214 */             ret = (ret && request.getRequestDate() <= until.getTime());
/*     */             
/* 216 */             if (acceptorRequestID != null) {
/* 217 */               ret = (ret && acceptorRequestID.accept(request.getRequestID()));
/*     */             }
/* 219 */             if (acceptorDealershipID != null) {
/* 220 */               ret = (ret && acceptorDealershipID.accept(request.getDealershipInfo().getDealershipID()));
/*     */             }
/* 222 */             if (acceptorDealership != null) {
/* 223 */               ret = (ret && acceptorDealership.accept(request.getDealershipInfo().getDealership()));
/*     */             }
/* 225 */           } catch (Exception e) {
/* 226 */             FilterInputElement.log.warn("unable to determine filter status of: " + String.valueOf(obj) + ", excluding - exception:" + e, e);
/* 227 */             ret = false;
/*     */           } 
/* 229 */           return ret;
/*     */         }
/*     */       };
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\registration\commo\\ui\admin\server\FilterInputElement.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */