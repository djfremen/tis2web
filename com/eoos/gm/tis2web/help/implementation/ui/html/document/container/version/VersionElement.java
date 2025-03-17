/*    */ package com.eoos.gm.tis2web.help.implementation.ui.html.document.container.version;
/*    */ 
/*    */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*    */ import com.eoos.gm.tis2web.frame.export.common.datatype.DBVersionInformation;
/*    */ import com.eoos.html.element.HtmlElement;
/*    */ import com.eoos.html.element.HtmlLabel;
/*    */ import com.eoos.html.element.gtwo.DataRetrievalAbstraction;
/*    */ import com.eoos.html.element.gtwo.ListElement;
/*    */ import com.eoos.util.StringUtilities;
/*    */ import java.util.Collections;
/*    */ import java.util.List;
/*    */ import java.util.Map;
/*    */ import org.apache.log4j.Logger;
/*    */ 
/*    */ public class VersionElement
/*    */   extends ListElement
/*    */   implements DataRetrievalAbstraction.DataCallback {
/* 18 */   private static final Logger log = Logger.getLogger(VersionElement.class);
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected ClientContext context;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected List data;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private final Map ADDATT_ODDROW;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private final Map ADDATT_EVENROW;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public VersionElement(ClientContext context, List data) {
/* 69 */     this.ADDATT_ODDROW = Collections.singletonMap("class", "odd");
/*    */     
/* 71 */     this.ADDATT_EVENROW = Collections.singletonMap("class", "even"); setDataCallback(this); this.context = context;
/*    */     this.data = data;
/*    */   }
/* 74 */   protected int getColumnCount() { return 1; } protected HtmlElement getHeader(int columnIndex) { throw new IllegalArgumentException(); } protected void getAdditionalAttributesRow(int rowIndex, Map map) { if (rowIndex % 2 == 0)
/* 75 */     { map.putAll(this.ADDATT_EVENROW); }
/*    */     else
/* 77 */     { map.putAll(this.ADDATT_ODDROW); }  }
/*    */   protected boolean enableHeader() { return false; }
/*    */   protected HtmlElement getContent(Object data, int columnIndex) { if (data instanceof String)
/*    */       return (HtmlElement)new HtmlLabel(data.toString());  if (data instanceof DBVersionInformation) { DBVersionInformation vi = (DBVersionInformation)data; try { String text = vi.getReleaseDescription(); text = StringUtilities.replace(text, "\r\n", "<br>"); text = StringUtilities.replace(text, "\n", "<br>"); if (vi.getReleaseVersion() != null)
/*    */           text = text + "<br>(" + this.context.getLabel("module.version") + " = " + vi.getReleaseVersion() + ")";  return (HtmlElement)new HtmlLabel(text); } catch (Exception e) { log.error("unable to retrieve version description, returing empty label - exception:" + e, e); return (HtmlElement)new HtmlLabel("n/a"); }  }  throw new IllegalArgumentException(); }
/* 82 */   public List getData() { return this.data; } protected void getAdditionalAttributesTable(Map<String, String> map) { super.getAdditionalAttributesTable(map);
/* 83 */     map.put("id", "databaselist"); }
/*    */ 
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\help\implementatio\\ui\html\document\container\version\VersionElement.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */