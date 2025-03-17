/*    */ package com.eoos.gm.tis2web.sps.client.ui.swing.template;
/*    */ 
/*    */ import com.eoos.datatype.gtwo.Pair;
/*    */ import com.eoos.gm.tis2web.frame.export.common.i18n.LabelResource;
/*    */ import com.eoos.gm.tis2web.sps.client.util.TableUtilities;
/*    */ import com.eoos.gm.tis2web.sps.common.gtwo.export.datamodel.ProgrammingSequence;
/*    */ import com.eoos.gm.tis2web.sps.common.system.LabelResourceProvider;
/*    */ import java.awt.Color;
/*    */ import java.awt.Dimension;
/*    */ import java.util.List;
/*    */ import javax.swing.JScrollPane;
/*    */ import javax.swing.JTable;
/*    */ import javax.swing.table.DefaultTableModel;
/*    */ import org.apache.log4j.Logger;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ReprogramStatusPSScrollPane
/*    */   extends JScrollPane
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/* 24 */   protected List state = null;
/*    */   
/* 26 */   protected JScrollPane scrollPane = new JScrollPane();
/*    */   
/* 28 */   protected static LabelResource resourceProvider = LabelResourceProvider.getInstance().getLabelResource();
/*    */   
/* 30 */   private static final Logger log = Logger.getLogger(ReprogramStatusPSScrollPane.class);
/*    */   
/*    */   public ReprogramStatusPSScrollPane(List state) {
/* 33 */     this.state = state;
/* 34 */     initialize();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private void initialize() {
/* 43 */     this.scrollPane.getViewport().setBackground(Color.WHITE);
/* 44 */     JTable table = getTable();
/* 45 */     this.scrollPane.setViewportView(table);
/* 46 */     Dimension d = this.scrollPane.getPreferredSize();
/* 47 */     this.scrollPane.setPreferredSize(new Dimension(d.width, (table.getPreferredSize()).height + 20));
/*    */   }
/*    */ 
/*    */   
/*    */   private JTable getTable() {
/* 52 */     JTable table = new JTable();
/*    */     try {
/* 54 */       DefaultTableModel mtable = new DefaultTableModel() {
/*    */           private static final long serialVersionUID = 1L;
/*    */           
/*    */           public boolean isCellEditable(int rowIndex, int columnIndex) {
/* 58 */             return false;
/*    */           }
/*    */         };
/* 61 */       mtable.setColumnCount(2);
/*    */       
/* 63 */       for (int i = 0; i < this.state.size(); i++) {
/* 64 */         Pair s = this.state.get(i);
/* 65 */         String label = null;
/* 66 */         Integer result = (Integer)s.getSecond();
/* 67 */         if (result.equals(ProgrammingSequence.FAIL)) {
/* 68 */           label = resourceProvider.getLabel(null, "programming-sequence.fail");
/* 69 */         } else if (result.equals(ProgrammingSequence.SKIP)) {
/* 70 */           label = resourceProvider.getLabel(null, "programming-sequence.skip");
/*    */         } else {
/* 72 */           label = resourceProvider.getLabel(null, "programming-sequence.success");
/*    */         } 
/* 74 */         String controller = (String)s.getFirst();
/* 75 */         controller = controller.replace('\t', ' ');
/* 76 */         mtable.addRow(new Object[] { label, controller });
/*    */       } 
/* 78 */       table.setModel(mtable);
/* 79 */       TableUtilities.setColumnMaxMin(table, 0);
/* 80 */       table.setShowGrid(false);
/* 81 */       table.setSelectionMode(0);
/* 82 */       table.setTableHeader(null);
/* 83 */     } catch (Exception e) {
/* 84 */       log.error("unable to create table, e:" + e);
/*    */     } 
/* 86 */     return table;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\clien\\ui\swing\template\ReprogramStatusPSScrollPane.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */