/*     */ package com.eoos.gm.tis2web.vc.v2.impl.ui.html.dialog;
/*     */ 
/*     */ import com.eoos.gm.tis2web.frame.export.common.ApplicationContext;
/*     */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*     */ import com.eoos.gm.tis2web.frame.export.common.ui.html.DialogBase;
/*     */ import com.eoos.gm.tis2web.frame.export.common.ui.html.Page;
/*     */ import com.eoos.gm.tis2web.vc.v2.base.configuration.IConfiguration;
/*     */ import com.eoos.gm.tis2web.vc.v2.base.provider.AbstractCfgDataProviderWrapper;
/*     */ import com.eoos.gm.tis2web.vc.v2.base.provider.CfgDataProvider;
/*     */ import com.eoos.gm.tis2web.vc.v2.base.provider.DialogDataProvider;
/*     */ import com.eoos.gm.tis2web.vc.v2.base.value.MultiValue;
/*     */ import com.eoos.gm.tis2web.vc.v2.base.value.Value;
/*     */ import com.eoos.gm.tis2web.vc.v2.configuration.VehicleConfigurationUtil;
/*     */ import com.eoos.gm.tis2web.vc.v2.service.VCService;
/*     */ import com.eoos.gm.tis2web.vc.v2.service.VCServiceProvider;
/*     */ import com.eoos.gm.tis2web.vc.v2.vin.VIN;
/*     */ import com.eoos.html.base.ClientContextBase;
/*     */ import com.eoos.html.element.HtmlElement;
/*     */ import com.eoos.html.element.HtmlElementContainer;
/*     */ import com.eoos.html.element.gtwo.DataRetrievalAbstraction;
/*     */ import com.eoos.html.element.gtwo.SelectBoxSelectionElement;
/*     */ import com.eoos.html.element.input.ClickButtonElement;
/*     */ import com.eoos.html.element.input.TextInputElement;
/*     */ import com.eoos.scsm.v2.collection.CollectionUtil;
/*     */ import com.eoos.scsm.v2.util.StringUtilities;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.Comparator;
/*     */ import java.util.Iterator;
/*     */ import java.util.LinkedHashSet;
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import org.apache.log4j.Logger;
/*     */ import org.apache.regexp.RE;
/*     */ 
/*     */ public class VCDialog extends DialogBase {
/*  39 */   private static final Logger log = Logger.getLogger(VCDialog.class);
/*     */   
/*  41 */   public static final Object KEY_MAKE = VehicleConfigurationUtil.KEY_MAKE;
/*     */   
/*  43 */   public static final Object KEY_MODEL = VehicleConfigurationUtil.KEY_MODEL;
/*     */   
/*  45 */   public static final Object KEY_MODELYEAR = VehicleConfigurationUtil.KEY_MODELYEAR;
/*     */   
/*  47 */   public static final Object KEY_ENGINE = VehicleConfigurationUtil.KEY_ENGINE;
/*     */   
/*  49 */   public static final Object KEY_TRANSMISSION = VehicleConfigurationUtil.KEY_TRANSMISSION;
/*     */   
/*     */   private static final String TEMPLATE;
/*     */   private static final String ROWTEMPLATE;
/*     */   
/*     */   static {
/*     */     try {
/*  56 */       String tmp = ApplicationContext.getInstance().loadFile(VCDialog.class, "dialog.html", null).toString();
/*  57 */       RE re = new RE("\\{ROWTEMPLATE/(.*?)/ROWTEMPLATE\\}", 5);
/*  58 */       if (re.match(tmp)) {
/*  59 */         tmp = tmp.substring(0, re.getParenStart(0)) + "{ROWS}" + tmp.substring(re.getParenEnd(0), tmp.length());
/*  60 */         ROWTEMPLATE = re.getParen(1);
/*  61 */         TEMPLATE = tmp;
/*     */       } else {
/*  63 */         throw new IllegalStateException("missing row template 1");
/*     */       } 
/*  65 */     } catch (Exception e) {
/*  66 */       log.error("could not init templates - error:" + e, e);
/*  67 */       throw new RuntimeException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private VCService.DialogCallback callback;
/*     */   
/*     */   private CfgDataProvider dataProvider;
/*     */   
/*     */   private VCService.ValueComparator valueComparator;
/*     */   
/*  78 */   private IConfiguration currentCfg = VehicleConfigurationUtil.cfgManagement.getEmptyConfiguration();
/*     */   
/*  80 */   private List vcInputElements = new LinkedList();
/*     */   
/*     */   private ClickButtonElement buttonOK;
/*     */   
/*     */   private ClickButtonElement buttonCancel;
/*     */   
/*     */   private ClickButtonElement buttonReset;
/*     */   
/*     */   private VCService vcService;
/*     */   
/*     */   private TextInputElement inputVIN;
/*     */   
/*  92 */   private VIN appliedVIN = null;
/*     */   
/*  94 */   private Set appliedVINKeys = Collections.synchronizedSet(new LinkedHashSet());
/*     */   
/*     */   private ClickButtonElement applyVIN;
/*     */   
/*  98 */   static final Value VALUE_UNDEFINED = VehicleConfigurationUtil.valueManagement.getANY();
/*     */   
/*     */   public VCDialog(final ClientContext context, final VCService.DialogCallback callback) {
/* 101 */     super(context);
/* 102 */     this.vcService = VCServiceProvider.getInstance().getService(context);
/* 103 */     this.callback = callback;
/* 104 */     this.inputVIN = new TextInputElement("vc.attributename.vin", 17, 17) {
/*     */         protected String getStyleClass() {
/* 106 */           return "vininput";
/*     */         }
/*     */         
/*     */         public boolean isDisabled() {
/* 110 */           boolean ret = super.isDisabled();
/*     */ 
/*     */           
/* 113 */           for (int i = 0; i < VehicleConfigurationUtil.KEYS.length; i++) {
/* 114 */             ret = (ret || callback.isReadonly(VehicleConfigurationUtil.KEYS[i]));
/*     */           }
/* 116 */           return ret;
/*     */         }
/*     */       };
/*     */     
/* 120 */     addElement((HtmlElement)this.inputVIN);
/* 121 */     if (callback.getVINResolver() == null) {
/* 122 */       this.inputVIN.setDisabled(Boolean.TRUE);
/*     */     }
/*     */     
/* 125 */     this.applyVIN = new ClickButtonElement(context.createID(), null)
/*     */       {
/*     */         protected String getLabel() {
/* 128 */           return context.getLabel("apply");
/*     */         }
/*     */         
/*     */         public Object onClick(Map submitParams) {
/* 132 */           return VCDialog.this.onApplyVIN((String)VCDialog.this.inputVIN.getValue());
/*     */         }
/*     */ 
/*     */         
/*     */         public boolean isDisabled() {
/* 137 */           return VCDialog.this.inputVIN.isDisabled();
/*     */         }
/*     */       };
/*     */     
/* 141 */     addElement((HtmlElement)this.applyVIN);
/*     */     
/* 143 */     this.valueComparator = new VCService.ValueComparator(context)
/*     */       {
/*     */         public int compare(Object value1, Object value2) {
/* 146 */           if (value1 == VCDialog.VALUE_UNDEFINED && value2 == VCDialog.VALUE_UNDEFINED)
/* 147 */             return 0; 
/* 148 */           if (value1 == VCDialog.VALUE_UNDEFINED || value2 == VCDialog.VALUE_UNDEFINED) {
/* 149 */             return (value1 == VCDialog.VALUE_UNDEFINED) ? -1 : 1;
/*     */           }
/* 151 */           return super.compare(value1, value2);
/*     */         }
/*     */ 
/*     */         
/*     */         public Comparator toReverse() {
/* 156 */           final null backend = this;
/* 157 */           final Comparator reverseComparator = super.toReverse();
/* 158 */           return new Comparator()
/*     */             {
/*     */               public int compare(Object obj1, Object obj2) {
/* 161 */                 if (obj1 == VCDialog.VALUE_UNDEFINED || obj2 == VCDialog.VALUE_UNDEFINED) {
/* 162 */                   return backend.compare(obj1, obj2);
/*     */                 }
/* 164 */                 return reverseComparator.compare(obj1, obj2);
/*     */               }
/*     */             };
/*     */         }
/*     */       };
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 173 */     this.dataProvider = createProviderFacade(callback.getDataProvider());
/*     */     
/* 175 */     this.buttonOK = new ClickButtonElement("buttonOK", null) {
/*     */         protected String getLabel() {
/* 177 */           return context.getLabel("ok");
/*     */         }
/*     */         
/*     */         public Object onClick(Map submitParams) {
/*     */           try {
/* 182 */             return VCDialog.this.onOK();
/* 183 */           } catch (Exception e) {
/* 184 */             VCDialog.log.error("unable to handle 'ok' action - exception:" + e, e);
/* 185 */             return null;
/*     */           } 
/*     */         }
/*     */       };
/*     */     
/* 190 */     addElement((HtmlElement)this.buttonOK);
/*     */     
/* 192 */     this.buttonCancel = new ClickButtonElement("buttonCancel", null) {
/*     */         protected String getLabel() {
/* 194 */           return context.getLabel("cancel");
/*     */         }
/*     */         
/*     */         public Object onClick(Map submitParams) {
/*     */           try {
/* 199 */             return VCDialog.this.onCancel();
/* 200 */           } catch (Exception e) {
/* 201 */             VCDialog.log.error("unable to handle 'cancel' action - exception:" + e, e);
/* 202 */             return null;
/*     */           } 
/*     */         }
/*     */       };
/*     */     
/* 207 */     addElement((HtmlElement)this.buttonCancel);
/*     */     
/* 209 */     this.buttonReset = new ClickButtonElement("buttonReset", null) {
/*     */         protected String getLabel() {
/* 211 */           return context.getLabel("reset");
/*     */         }
/*     */         
/*     */         public Object onClick(Map submitParams) {
/*     */           try {
/* 216 */             VCDialog.this.onReset();
/* 217 */             return null;
/* 218 */           } catch (Exception e) {
/* 219 */             VCDialog.log.error("unable to handle 'reset' action - exception:" + e, e);
/* 220 */             return null;
/*     */           } 
/*     */         }
/*     */       };
/*     */     
/* 225 */     addElement((HtmlElement)this.buttonReset);
/*     */     
/* 227 */     IConfiguration cfg = callback.getStorage().getCfg();
/* 228 */     VIN vin = callback.getStorage().getVIN();
/* 229 */     setCurrentCfg(cfg, vin);
/* 230 */     if (vin != null) {
/* 231 */       this.inputVIN.setValue(vin.toString());
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private CfgDataProvider createProviderFacade(CfgDataProvider dp) {
/* 237 */     CfgDataProvider ret = dp;
/* 238 */     DialogDataProvider dialogDataProvider = new DialogDataProvider(ret, VehicleConfigurationUtil.cfgUtil, VALUE_UNDEFINED);
/* 239 */     return (CfgDataProvider)new AbstractCfgDataProviderWrapper((CfgDataProvider)dialogDataProvider)
/*     */       {
/*     */         public Set getValues(Object key, IConfiguration currentCfg) {
/* 242 */           Set ret = super.getValues(key, currentCfg);
/* 243 */           if (ret.remove(null)) {
/* 244 */             VCDialog.log.warn("removed <null> value from result collection");
/*     */           }
/* 246 */           return ret;
/*     */         }
/*     */       };
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected String getID() {
/* 254 */     return "vehicleconfigdialog";
/*     */   }
/*     */   
/*     */   private void setCurrentCfg(IConfiguration resolvedCfg, VIN vin) {
/* 258 */     setCurrentCfg(resolvedCfg);
/* 259 */     this.appliedVIN = vin;
/* 260 */     this.appliedVINKeys.clear();
/* 261 */     if (resolvedCfg != null && vin != null) {
/* 262 */       this.appliedVINKeys.addAll(resolvedCfg.getKeys());
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private void setCurrentCfg(IConfiguration cfg) {
/* 268 */     if (cfg != null) {
/* 269 */       this.currentCfg = cfg;
/*     */     } else {
/* 271 */       this.currentCfg = VehicleConfigurationUtil.cfgManagement.getEmptyConfiguration();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private Object onOK() {
/* 277 */     IConfiguration currentCfg = this.currentCfg;
/*     */     
/* 279 */     boolean mandatorySet = true;
/* 280 */     for (int i = 0; i < VehicleConfigurationUtil.KEYS.length && mandatorySet; i++) {
/* 281 */       Object key = VehicleConfigurationUtil.KEYS[i];
/* 282 */       if (this.callback.isMandatory(key, currentCfg)) {
/* 283 */         mandatorySet = (this.currentCfg.getValue(key) != null);
/*     */       }
/*     */     } 
/*     */     
/* 287 */     if (mandatorySet) {
/*     */ 
/*     */       
/* 290 */       VIN vin = this.appliedVIN;
/* 291 */       this.callback.getStorage().storeCfg(currentCfg, vin);
/*     */     } else {
/* 293 */       return getErrorPopup(this.context.getMessage("vc.error.missing.mandatory.input"));
/*     */     } 
/* 295 */     return onClose(false);
/*     */   }
/*     */   
/*     */   private Object onCancel() {
/* 299 */     return onClose(true);
/*     */   }
/*     */   
/*     */   private Object onClose(boolean cancelled) {
/* 303 */     Object ret = this.callback.onClose(cancelled);
/* 304 */     if (ret != null && ret != this) {
/* 305 */       unregister();
/*     */     }
/* 307 */     return ret;
/*     */   }
/*     */   
/*     */   private static final class InternalException
/*     */     extends Exception {
/*     */     private static final long serialVersionUID = 1L;
/* 313 */     public static final InternalException UNRESOLVABLE_VIN = new InternalException();
/*     */   }
/*     */   
/*     */   private Object onApplyVIN(String _vin) {
/* 317 */     final HtmlElementContainer returnUI = getTopLevelContainer();
/*     */     
/*     */     try {
/* 320 */       final VIN vin = VehicleConfigurationUtil.getVIN(_vin);
/*     */       
/* 322 */       Set cfgs = this.callback.getVINResolver().resolveVIN(vin);
/* 323 */       IConfiguration cfg = null;
/*     */       
/* 325 */       for (Iterator<IConfiguration> iter = cfgs.iterator(); iter.hasNext(); ) {
/* 326 */         IConfiguration vc = iter.next();
/* 327 */         if (!VehicleConfigurationUtil.cfgUtil.supportsFully(this.callback.getDataProvider(), vc)) {
/* 328 */           if (log.isDebugEnabled()) {
/* 329 */             log.debug("the resolved cfg: " + vc + " is not supported by the data provider, filtering out");
/*     */           }
/* 331 */           iter.remove();
/*     */         } 
/*     */       } 
/*     */       
/* 335 */       if (cfgs.size() == 0)
/* 336 */         throw InternalException.UNRESOLVABLE_VIN; 
/* 337 */       if (cfgs.size() > 1) {
/* 338 */         ConfigurationSelectionDialog dlg = new ConfigurationSelectionDialog(getContext(), cfgs)
/*     */           {
/*     */             protected Object onClose(IConfiguration selectedConfiguration) {
/* 341 */               if (selectedConfiguration != null) {
/* 342 */                 VCDialog.this.setCurrentCfg(selectedConfiguration, vin);
/*     */               }
/* 344 */               return returnUI;
/*     */             }
/*     */           };
/*     */         
/* 348 */         return dlg;
/*     */       } 
/* 350 */       cfg = (IConfiguration)CollectionUtil.getFirst(cfgs);
/* 351 */       setCurrentCfg(cfg, vin);
/* 352 */       return null;
/*     */     }
/* 354 */     catch (com.eoos.gm.tis2web.vc.v2.vin.VIN.InvalidVINException e) {
/* 355 */       return getErrorPopup(this.context.getMessage("vcdialog.invalid.vin.exception"));
/* 356 */     } catch (InternalException e) {
/* 357 */       return getErrorPopup(this.context.getMessage("vcdialog.unable.to.resolve.exception"));
/* 358 */     } catch (Exception e) {
/* 359 */       log.error("unable to resolve vin - exception:" + e, e);
/* 360 */       return getErrorPopup(this.context.getMessage("vcdialog.unable.to.resolve.exception"));
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected String getContent(Map params) {
/* 366 */     StringBuffer ret = new StringBuffer(TEMPLATE);
/* 367 */     removeElements(this.vcInputElements);
/* 368 */     this.vcInputElements.clear();
/*     */     
/* 370 */     IConfiguration currentVC = this.currentCfg;
/* 371 */     StringBuffer rows = new StringBuffer();
/* 372 */     Object[] keys = { KEY_MAKE, KEY_MODEL, KEY_MODELYEAR, KEY_ENGINE, KEY_TRANSMISSION };
/* 373 */     for (int i = 0; i < keys.length; i++) {
/* 374 */       StringBuffer row = new StringBuffer(ROWTEMPLATE);
/* 375 */       final Object key = keys[i];
/* 376 */       final boolean readonly = this.callback.isReadonly(key);
/* 377 */       final boolean mandatory = this.callback.isMandatory(key, currentVC);
/*     */       
/* 379 */       StringUtilities.replace(row, "{LABEL}", VehicleConfigurationUtil.keyToString(key, this.context.getLocale()) + (mandatory ? "<span class=\"mandatory mark\">*</span>" : ""));
/*     */       
/* 381 */       boolean reverseOrdering = (key == KEY_MODELYEAR);
/*     */       
/* 383 */       final List data = readonly ? Collections.<Value>singletonList(this.currentCfg.getValue(key)) : CollectionUtil.toSortedList(this.dataProvider.getValues(key, this.currentCfg), reverseOrdering ? this.valueComparator.toReverse() : (Comparator)this.valueComparator);
/* 384 */       DataRetrievalAbstraction.DataCallback dataCallback = new DataRetrievalAbstraction.DataCallback()
/*     */         {
/*     */           public List getData() {
/* 387 */             return data;
/*     */           }
/*     */         };
/*     */       
/* 391 */       String parameterName = "vc.attributename.";
/* 392 */       if (key == KEY_MAKE) {
/* 393 */         parameterName = parameterName + "salesmake";
/* 394 */       } else if (key == KEY_MODEL) {
/* 395 */         parameterName = parameterName + "model";
/* 396 */       } else if (key == KEY_MODELYEAR) {
/* 397 */         parameterName = parameterName + "modelyear";
/* 398 */       } else if (key == KEY_ENGINE) {
/* 399 */         parameterName = parameterName + "engine";
/* 400 */       } else if (key == KEY_TRANSMISSION) {
/* 401 */         parameterName = parameterName + "transmission";
/*     */       } 
/*     */       
/* 404 */       SelectBoxSelectionElement inputElement = new SelectBoxSelectionElement(parameterName, true, dataCallback, 1) {
/*     */           protected boolean autoSubmitOnChange() {
/* 406 */             return !readonly;
/*     */           }
/*     */           
/*     */           protected String getDisplayValue(Object option) {
/* 410 */             if (option == VCDialog.VALUE_UNDEFINED) {
/* 411 */               return mandatory ? VCDialog.this.context.getLabel("vc.please.select") : VCDialog.this.context.getLabel("vc.no.selection");
/*     */             }
/* 413 */             Object dv = CollectionUtil.getFirst(((MultiValue)option).getValues());
/* 414 */             return VCDialog.this.vcService.getDisplayValue(dv);
/*     */           }
/*     */ 
/*     */           
/*     */           protected Object onChange(Map submitParams) {
/* 419 */             if (VCDialog.this.appliedVINKeys.contains(key)) {
/* 420 */               Page.ConfirmationCallback callback = new Page.ConfirmationCallback()
/*     */                 {
/*     */                   public Object onConfirm() {
/* 423 */                     VCDialog.this.appliedVIN = null;
/* 424 */                     VCDialog.this.inputVIN.setValue("");
/* 425 */                     VCDialog.this.appliedVINKeys.clear();
/* 426 */                     return VCDialog.null.this.onChangeCommit();
/*     */                   }
/*     */                   
/*     */                   public Object onCancel() {
/* 430 */                     return VCDialog.this.getTopLevelContainer();
/*     */                   }
/*     */                 };
/*     */               
/* 434 */               return VCDialog.this.getConfirmationPopup(VCDialog.this.context.getMessage("vc.message.attribute.tied.to.vin"), callback);
/*     */             } 
/* 436 */             return onChangeCommit();
/*     */           }
/*     */ 
/*     */ 
/*     */           
/*     */           protected Object onChangeCommit() {
/* 442 */             Value value = (Value)getValue();
/* 443 */             if (value == VCDialog.VALUE_UNDEFINED) {
/* 444 */               VCDialog.this.currentCfg = VehicleConfigurationUtil.cfgManagement.removeAttribute(VCDialog.this.currentCfg, key);
/*     */             } else {
/* 446 */               VCDialog.this.currentCfg = VehicleConfigurationUtil.cfgManagement.setAttribute(VCDialog.this.currentCfg, key, value);
/*     */             } 
/* 448 */             return VCDialog.this.getTopLevelContainer();
/*     */           }
/*     */         };
/*     */       
/* 452 */       Object currentValue = this.currentCfg.getValue(key);
/* 453 */       if (currentValue == null) {
/* 454 */         currentValue = VALUE_UNDEFINED;
/*     */       }
/* 456 */       inputElement.setValue(currentValue);
/*     */       
/* 458 */       if (readonly) {
/* 459 */         inputElement.setDisabled(Boolean.TRUE);
/*     */ 
/*     */         
/* 462 */         this.inputVIN.setDisabled(Boolean.TRUE);
/*     */       } 
/* 464 */       StringUtilities.replace(row, "{INPUT}", inputElement.getHtmlCode(params));
/* 465 */       rows.append(row);
/* 466 */       this.vcInputElements.add(inputElement);
/*     */     } 
/* 468 */     addAllElements(this.vcInputElements);
/* 469 */     StringUtilities.replace(ret, "{ROWS}", rows);
/*     */     
/* 471 */     StringUtilities.replace(ret, "{BUTTON_RESET}", this.buttonReset.getHtmlCode(params));
/* 472 */     StringUtilities.replace(ret, "{BUTTON_OK}", this.buttonOK.getHtmlCode(params));
/* 473 */     StringUtilities.replace(ret, "{BUTTON_CANCEL}", this.buttonCancel.getHtmlCode(params));
/*     */     
/* 475 */     StringUtilities.replace(ret, "{LABEL_VIN}", this.context.getLabel("vin"));
/* 476 */     StringUtilities.replace(ret, "{INPUT_VIN}", this.inputVIN.getHtmlCode(params));
/* 477 */     StringUtilities.replace(ret, "{BUTTON_APPLY}", this.applyVIN.getHtmlCode(params));
/*     */     
/* 479 */     return ret.toString();
/*     */   }
/*     */ 
/*     */   
/*     */   protected String getTitle(Map params) {
/* 484 */     return "<h2>" + this.context.getLabel("vehicle.configuration") + "</h2>";
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void onReset() throws Exception {
/* 490 */     IConfiguration newCfg = this.currentCfg;
/*     */     
/* 492 */     for (int i = 0; i < VehicleConfigurationUtil.KEYS.length; i++) {
/* 493 */       Object key = VehicleConfigurationUtil.KEYS[i];
/* 494 */       if (!this.callback.isReadonly(key)) {
/* 495 */         newCfg = VehicleConfigurationUtil.cfgManagement.removeAttribute(newCfg, key);
/*     */       }
/*     */     } 
/* 498 */     setCurrentCfg(newCfg, (VIN)null);
/* 499 */     if (!this.inputVIN.isDisabled())
/* 500 */       this.inputVIN.setValue(null); 
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\vc\v2\imp\\ui\html\dialog\VCDialog.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */