/*     */ package com.eoos.gm.tis2web.sas.client.ui.main;
/*     */ 
/*     */ import com.eoos.datatype.ExceptionCause;
/*     */ import com.eoos.datatype.ExceptionWrapper;
/*     */ import com.eoos.gm.tis2web.sas.client.ExecutionAdapter;
/*     */ import com.eoos.gm.tis2web.sas.client.ExecutionAdapterDeltaImpl;
/*     */ import com.eoos.gm.tis2web.sas.client.ExecutionAdapterImpl;
/*     */ import com.eoos.gm.tis2web.sas.client.ExecutionFacade;
/*     */ import com.eoos.gm.tis2web.sas.client.req.ConfirmationRequest;
/*     */ import com.eoos.gm.tis2web.sas.client.req.Request;
/*     */ import com.eoos.gm.tis2web.sas.client.req.RequestException;
/*     */ import com.eoos.gm.tis2web.sas.client.req.ToolSelectionRequest;
/*     */ import com.eoos.gm.tis2web.sas.client.req.VINValidationRequest;
/*     */ import com.eoos.gm.tis2web.sas.client.system.SASClientContextProvider;
/*     */ import com.eoos.gm.tis2web.sas.client.ui.panel.ToolSelectionPanel;
/*     */ import com.eoos.gm.tis2web.sas.client.ui.panel.WizardStepPanel;
/*     */ import com.eoos.gm.tis2web.sas.client.ui.util.ButtonHandle;
/*     */ import com.eoos.gm.tis2web.sas.common.model.AccessType;
/*     */ import com.eoos.gm.tis2web.sas.common.system.LabelResourceProvider;
/*     */ import com.eoos.thread.AsynchronousExecutionCallback2;
/*     */ import com.eoos.thread.ProgressInfo;
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
/*     */ import java.util.Locale;
/*     */ import javax.swing.JDialog;
/*     */ import javax.swing.JOptionPane;
/*     */ import javax.swing.JPanel;
/*     */ import javax.swing.SwingUtilities;
/*     */ import org.apache.log4j.Logger;
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
/*     */ public class UIFactory
/*     */ {
/*  51 */   private static final Logger log = Logger.getLogger(UIFactory.class);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Locale getLocale() {
/*  58 */     return SASClientContextProvider.getInstance().getContext().getLocale();
/*     */   }
/*     */   
/*     */   private String getLabel(String key) {
/*  62 */     return LabelResourceProvider.getInstance().getLabelResource().getLabel(getLocale(), key);
/*     */   }
/*     */   
/*     */   private String getMessage(String key) {
/*  66 */     return LabelResourceProvider.getInstance().getLabelResource().getMessage(getLocale(), key);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void start() {
/*  72 */     ExecutionAdapterImpl executionAdapterImpl = new ExecutionAdapterImpl();
/*  73 */     AsynchronousExecutionCallback2 callback = new AsynchronousExecutionCallback2() {
/*     */         public void onFinished(Object result) {
/*  75 */           MainFrame.getInstance().hideSplashScreen();
/*  76 */           MainFrame.getInstance().push((JPanel)result);
/*  77 */           MainFrame.getInstance().setVisible(true);
/*     */         }
/*     */ 
/*     */         
/*     */         public void onException(Throwable t) {}
/*     */ 
/*     */         
/*     */         public boolean onProcessing(ProgressInfo progressInfo) {
/*  85 */           return true;
/*     */         }
/*     */       };
/*  88 */     next((ExecutionAdapter)executionAdapterImpl, callback);
/*     */   }
/*     */ 
/*     */   
/*     */   private void next(final ExecutionAdapter adapter, final AsynchronousExecutionCallback2 callback) {
/*  93 */     AsynchronousExecutionCallback2 callback2 = new AsynchronousExecutionCallback2() {
/*     */         public void onFinished(final Object result) {
/*  95 */           SwingUtilities.invokeLater(new Runnable() {
/*     */                 public void run() {
/*  97 */                   JPanel panel = UIFactory.this.createSummaryPanel((AccessType[])result);
/*  98 */                   callback.onFinished(panel);
/*     */                 }
/*     */               });
/*     */         }
/*     */         
/*     */         public void onException(final Throwable _t) {
/* 104 */           SwingUtilities.invokeLater(new Runnable() {
/*     */                 public void run() {
/* 106 */                   JPanel panel = null;
/* 107 */                   Throwable t = _t;
/* 108 */                   if (t instanceof ExceptionWrapper) {
/* 109 */                     t = ((ExceptionWrapper)t).getWrappedException();
/*     */                   }
/*     */                   
/* 112 */                   if (t instanceof RequestException) {
/* 113 */                     Request request = ((RequestException)t).getRequest();
/* 114 */                     if (request instanceof ConfirmationRequest) {
/* 115 */                       UIFactory.this.confirmationPopup(adapter, (ConfirmationRequest)request, callback);
/* 116 */                       panel = null;
/*     */                     } else {
/* 118 */                       panel = UIFactory.this.createPanel(adapter, request);
/*     */                     } 
/* 120 */                   } else if (t instanceof com.eoos.thread.AbortionException) {
/* 121 */                     panel = null;
/*     */                   } else {
/* 123 */                     UIFactory.this.errorPopup(t);
/* 124 */                     panel = null;
/*     */                   } 
/* 126 */                   callback.onFinished(panel);
/*     */                 }
/*     */               });
/*     */         }
/*     */         
/*     */         public boolean onProcessing(ProgressInfo progressInfo) {
/* 132 */           return callback.onProcessing(progressInfo);
/*     */         }
/*     */       };
/*     */     
/* 136 */     ExecutionFacade.getInstance().execute(adapter, callback2);
/*     */   }
/*     */ 
/*     */   
/*     */   private void errorPopup(Throwable t) {
/* 141 */     log.error("an error occurred " + t, t);
/* 142 */     JOptionPane pane = new JOptionPane(getErrorMessage(t), 0);
/* 143 */     JDialog popup = pane.createDialog(MainFrame.getInstance(), getLabel("error"));
/* 144 */     popup.pack();
/* 145 */     popup.setVisible(true);
/*     */   }
/*     */   
/*     */   private void confirmationPopup(ExecutionAdapter _adapter, ConfirmationRequest request, AsynchronousExecutionCallback2 callback) {
/* 149 */     JOptionPane pane = new JOptionPane(getConfirmationMessage(request), 2, 2);
/* 150 */     JDialog popup = pane.createDialog(MainFrame.getInstance(), getLabel("warning"));
/* 151 */     popup.pack();
/* 152 */     popup.setVisible(true);
/*     */     
/* 154 */     Integer value = (Integer)pane.getValue();
/* 155 */     if (value != null && value.intValue() == 0) {
/* 156 */       ExecutionAdapterDeltaImpl executionAdapterDeltaImpl = new ExecutionAdapterDeltaImpl(_adapter);
/* 157 */       request.confirm((ExecutionAdapter)executionAdapterDeltaImpl);
/* 158 */       next((ExecutionAdapter)executionAdapterDeltaImpl, callback);
/*     */     } 
/*     */   }
/*     */   
/*     */   private Object[] getErrorMessage(Throwable t) {
/* 163 */     if (t instanceof com.eoos.gm.tis2web.frame.export.common.InvalidSessionException)
/* 164 */       return new Object[] { getMessage("error.invalid.session") }; 
/* 165 */     if (t instanceof com.eoos.gm.tis2web.sas.common.model.exception.BadRequestException)
/* 166 */       return new Object[] { getMessage("error.bad.request") }; 
/* 167 */     if (t instanceof com.eoos.gm.tis2web.sas.common.model.exception.InvalidHardwareKeyException)
/* 168 */       return new Object[] { getMessage("error.invalid.hwk") }; 
/* 169 */     if (t instanceof com.eoos.gm.tis2web.sas.common.model.exception.MissingPermissionException) {
/* 170 */       return new Object[] { getMessage("error.missing.permission") };
/*     */     }
/* 172 */     if (t instanceof com.eoos.gm.tis2web.sas.common.model.exception.ReadRequestException)
/* 173 */       return new Object[] { getMessage("error.read.request") }; 
/* 174 */     if (t instanceof com.eoos.gm.tis2web.sas.common.model.exception.SalesOrganisationReadException)
/* 175 */       return new Object[] { getMessage("error.read.so") }; 
/* 176 */     if (t instanceof com.eoos.gm.tis2web.sas.common.model.exception.SystemException)
/* 177 */       return new Object[] { getMessage("error.system") }; 
/* 178 */     if (t instanceof com.eoos.gm.tis2web.frame.export.common.hwk.exception.UnavailableHardwareKeyException) {
/* 179 */       if (t instanceof com.eoos.gm.tis2web.frame.export.common.hwk.exception.SystemDriverNotInstalledException) {
/* 180 */         return new Object[] { getMessage("error.system.driver.not.installed") };
/*     */       }
/* 182 */       return new Object[] { getMessage("error.hwk.read") };
/*     */     } 
/* 184 */     if (t instanceof com.eoos.gm.tis2web.sas.common.model.exception.WriteResponseException)
/* 185 */       return new Object[] { getMessage("error.write.response") }; 
/* 186 */     if (t instanceof com.eoos.gm.tis2web.sas.common.model.exception.InvalidVINException)
/* 187 */       return new Object[] { getMessage("error.invalid.vin") }; 
/* 188 */     if (t instanceof ExceptionCause) {
/* 189 */       return getErrorMessage(((ExceptionCause)t).getCause());
/*     */     }
/* 191 */     return new Object[] { getMessage("error.occurred") };
/*     */   }
/*     */ 
/*     */   
/*     */   private Object[] getConfirmationMessage(ConfirmationRequest request) {
/* 196 */     if (request instanceof com.eoos.gm.tis2web.sas.client.req.IncompleteResponseConfirmationRequest) {
/* 197 */       return new Object[] { getMessage("warning.incomplete.ssa.response"), getMessage("warning.incomplete.ssa.response.2") };
/*     */     }
/* 199 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   private JPanel createSummaryPanel(AccessType[] types) {
/* 204 */     List<String> messages = new LinkedList();
/* 205 */     if (types != null) {
/* 206 */       for (int i = 0; i < types.length; i++) {
/* 207 */         messages.add(getMessage("enabled.access.type") + " " + getLabel("access.type." + types[i].toString()));
/*     */       }
/*     */     } else {
/* 210 */       messages.add(getMessage("no.request.found"));
/*     */     } 
/* 212 */     String[] _messages = messages.<String>toArray(new String[messages.size()]);
/* 213 */     log.debug("converted messages : " + String.valueOf(_messages));
/* 214 */     MessageDisplayWizardCallback callback = new MessageDisplayWizardCallback(_messages);
/*     */     
/* 216 */     callback.getButtonHandle().setVisible(ButtonHandle.CANCEL, false);
/* 217 */     callback.getButtonHandle().setLabel(ButtonHandle.NEXT, callback.getLabel("button.finish"));
/*     */     
/* 219 */     return (JPanel)new WizardStepPanel(callback) {
/*     */         private static final long serialVersionUID = 1L;
/*     */         
/*     */         public void onNext() {
/* 223 */           MainFrame.getInstance().exit();
/*     */         }
/*     */         
/*     */         public void onCancel() {
/* 227 */           UIFactory.this.onCancel();
/*     */         }
/*     */         
/*     */         protected void createNextPanel(JPanel contentPanel, AsynchronousExecutionCallback2 callback) {
/* 231 */           throw new UnsupportedOperationException();
/*     */         }
/*     */       };
/*     */   }
/*     */ 
/*     */   
/*     */   private JPanel createPanel(ExecutionAdapter adapter, Request request) {
/* 238 */     if (request instanceof ToolSelectionRequest)
/* 239 */       return createToolSelectionPanel(adapter, (ToolSelectionRequest)request); 
/* 240 */     if (request instanceof VINValidationRequest)
/* 241 */       return createVINValidationPanel(adapter, (VINValidationRequest)request); 
/* 242 */     if (request instanceof ConfirmationRequest) {
/* 243 */       return createConfirmationPanel(adapter, (ConfirmationRequest)request);
/*     */     }
/* 245 */     throw new IllegalArgumentException("unknown request type " + request.getClass().getName());
/*     */   }
/*     */ 
/*     */   
/*     */   private JPanel createToolSelectionPanel(final ExecutionAdapter _adapter, ToolSelectionRequest request) {
/* 250 */     ToolSelectionWizardCallback callback = new ToolSelectionWizardCallback(request.getTools());
/*     */     
/* 252 */     return (JPanel)new WizardStepPanel(callback)
/*     */       {
/*     */         private static final long serialVersionUID = 1L;
/*     */         
/*     */         protected void createNextPanel(JPanel contentPanel, AsynchronousExecutionCallback2 callback) {
/* 257 */           ExecutionAdapterDeltaImpl executionAdapterDeltaImpl = new ExecutionAdapterDeltaImpl(_adapter);
/*     */ 
/*     */           
/* 260 */           executionAdapterDeltaImpl.setValue(ExecutionAdapter.Key.TOOL, ((ToolSelectionPanel)contentPanel).getSelectedTool());
/*     */           
/* 262 */           UIFactory.this.next((ExecutionAdapter)executionAdapterDeltaImpl, callback);
/*     */         }
/*     */         
/*     */         public void onCancel() {
/* 266 */           UIFactory.this.onCancel();
/*     */         }
/*     */       };
/*     */   }
/*     */ 
/*     */   
/*     */   private JPanel createVINValidationPanel(final ExecutionAdapter _adapter, VINValidationRequest request) {
/* 273 */     VINValidationWizardCallback callback = new VINValidationWizardCallback(request.getVIN());
/*     */     
/* 275 */     return (JPanel)new WizardStepPanel(callback)
/*     */       {
/*     */         private static final long serialVersionUID = 1L;
/*     */         
/*     */         public void onCancel() {
/* 280 */           UIFactory.this.onCancel();
/*     */         }
/*     */ 
/*     */         
/*     */         protected void createNextPanel(JPanel contentPanel, AsynchronousExecutionCallback2 callback) {
/* 285 */           ExecutionAdapterDeltaImpl executionAdapterDeltaImpl = new ExecutionAdapterDeltaImpl(_adapter);
/*     */           
/* 287 */           executionAdapterDeltaImpl.setValue(ExecutionAdapter.Key.VINVALIDATION, Boolean.TRUE);
/*     */           
/* 289 */           UIFactory.this.next((ExecutionAdapter)executionAdapterDeltaImpl, callback);
/*     */         }
/*     */       };
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private JPanel createConfirmationPanel(final ExecutionAdapter _adapter, final ConfirmationRequest request) {
/* 297 */     MessageDisplayWizardCallback callback = new MessageDisplayWizardCallback(String.valueOf(request));
/*     */     
/* 299 */     return (JPanel)new WizardStepPanel(callback) {
/*     */         private static final long serialVersionUID = 1L;
/*     */         
/*     */         protected void createNextPanel(JPanel contentPanel, AsynchronousExecutionCallback2 callback) {
/* 303 */           ExecutionAdapterDeltaImpl executionAdapterDeltaImpl = new ExecutionAdapterDeltaImpl(_adapter);
/*     */           
/* 305 */           request.confirm((ExecutionAdapter)executionAdapterDeltaImpl);
/*     */           
/* 307 */           UIFactory.this.next((ExecutionAdapter)executionAdapterDeltaImpl, callback);
/*     */         }
/*     */         
/*     */         public void onCancel() {
/* 311 */           UIFactory.this.onCancel();
/*     */         }
/*     */       };
/*     */   }
/*     */   
/*     */   private void onCancel() {
/* 317 */     MainFrame.getInstance().exit();
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sas\clien\\ui\main\UIFactory.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */