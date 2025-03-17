/*     */ package com.eoos.gm.tis2web.sps.client.util;
/*     */ 
/*     */ import java.awt.FontMetrics;
/*     */ import java.util.Vector;
/*     */ import javax.swing.JLabel;
/*     */ import javax.swing.JTable;
/*     */ import javax.swing.table.DefaultTableModel;
/*     */ import javax.swing.table.TableColumn;
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
/*     */ public class TableUtilities
/*     */ {
/*  21 */   protected static final Logger log = Logger.getLogger(TableUtilities.class);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int getHeightFont(JTable table) {
/*  28 */     FontMetrics u_fmCell = table.getFontMetrics(table.getFont());
/*  29 */     int height = u_fmCell.getHeight();
/*  30 */     return height;
/*     */   }
/*     */   
/*     */   public static void setColumnsSize(JTable u_jTable) {
/*     */     try {
/*  35 */       int nrOfCol = u_jTable.getColumnCount();
/*  36 */       int nrOfRows = u_jTable.getRowCount();
/*     */ 
/*     */ 
/*     */       
/*  40 */       FontMetrics u_fmCell = u_jTable.getFontMetrics(u_jTable.getFont());
/*  41 */       FontMetrics u_fmHeader = u_jTable.getFontMetrics(u_jTable.getFont());
/*     */       
/*  43 */       DefaultTableModel u_model = (DefaultTableModel)u_jTable.getModel();
/*  44 */       for (int i = 0; i < nrOfCol; i++) {
/*     */         
/*  46 */         int max = u_fmHeader.stringWidth(u_model.getColumnName(i));
/*  47 */         TableColumn colModel = u_jTable.getColumnModel().getColumn(i);
/*  48 */         String u_value = null;
/*     */         
/*  50 */         for (int j = 0; j < nrOfRows; j++) {
/*  51 */           u_value = String.valueOf(u_jTable.getValueAt(j, i));
/*  52 */           if (u_value != null) {
/*  53 */             int width = u_fmCell.stringWidth(u_value);
/*  54 */             max = Math.max(max, width);
/*     */           } 
/*     */         } 
/*     */         
/*  58 */         colModel.setPreferredWidth(max + 20);
/*     */       } 
/*  60 */     } catch (Exception e) {
/*  61 */       log.error("Exception in setColumnsSize method: " + e.getMessage());
/*     */     } 
/*     */   }
/*     */   
/*     */   public static void setColumnSize(JTable u_jTable, int col) {
/*     */     try {
/*  67 */       int nrOfCol = u_jTable.getColumnCount();
/*  68 */       int nrOfRows = u_jTable.getRowCount();
/*     */       
/*  70 */       if (nrOfCol == 0 || nrOfRows == 0) {
/*     */         return;
/*     */       }
/*     */       
/*  74 */       FontMetrics u_fmCell = u_jTable.getFontMetrics(u_jTable.getFont());
/*  75 */       FontMetrics u_fmHeader = u_jTable.getFontMetrics(u_jTable.getFont());
/*     */       
/*  77 */       int max = u_fmHeader.stringWidth(u_jTable.getModel().getColumnName(col));
/*  78 */       u_jTable.getColumnModel().getColumn(col);
/*  79 */       String u_value = null;
/*     */       
/*  81 */       for (int j = 0; j < nrOfRows; j++) {
/*  82 */         u_value = String.valueOf(u_jTable.getValueAt(j, col));
/*  83 */         if (u_value != null) {
/*  84 */           u_value = Transform.convertHtmlToString(u_value);
/*  85 */           int width = u_fmCell.stringWidth(u_value);
/*  86 */           max = Math.max(max, width);
/*     */         } 
/*     */       } 
/*     */       
/*  90 */       u_jTable.getColumnModel().getColumn(col).setPreferredWidth(max + 40);
/*  91 */     } catch (Exception e) {
/*  92 */       log.error("Exception in setColumnSize() method :" + e.getMessage());
/*     */     } 
/*     */   }
/*     */   
/*     */   public static void setColumnMaxMin(JTable u_jTable, int col) {
/*     */     try {
/*  98 */       int nrOfCol = u_jTable.getColumnCount();
/*  99 */       int nrOfRows = u_jTable.getRowCount();
/*     */       
/* 101 */       if (nrOfCol == 0 || nrOfRows == 0)
/*     */         return; 
/* 103 */       u_jTable.getFontMetrics(u_jTable.getFont());
/* 104 */       u_jTable.getFontMetrics(u_jTable.getFont());
/*     */       
/* 106 */       JLabel labelHeader = new JLabel(u_jTable.getModel().getColumnName(col));
/* 107 */       double max = labelHeader.getPreferredSize().getWidth();
/* 108 */       u_jTable.getColumnModel().getColumn(col);
/* 109 */       String u_value = null;
/*     */       
/* 111 */       for (int j = 0; j < nrOfRows; j++) {
/* 112 */         u_value = String.valueOf(u_jTable.getValueAt(j, col));
/* 113 */         if (u_value != null) {
/* 114 */           JLabel label = new JLabel(u_value);
/* 115 */           double width = label.getPreferredSize().getWidth();
/* 116 */           max = Math.max(max, width);
/*     */         } 
/*     */       } 
/*     */       
/* 120 */       max += 20.0D;
/* 121 */       u_jTable.getColumnModel().getColumn(col).setMaxWidth((int)max);
/* 122 */       u_jTable.getColumnModel().getColumn(col).setMinWidth((int)max);
/*     */     }
/* 124 */     catch (Exception e) {
/* 125 */       log.error("Exception in setColumnMaxMin() method :" + e.getMessage());
/*     */     } 
/*     */   }
/*     */   
/*     */   public static void setAllColumnsMaxMin(JTable u_jTable, int table_width) {
/*     */     try {
/* 131 */       int widthColumns = 0;
/* 132 */       int lastColIndex = u_jTable.getColumnCount() - 1;
/*     */       
/* 134 */       for (int i = 0; i < lastColIndex; i++) {
/* 135 */         setColumnMaxMin(u_jTable, i);
/* 136 */         int widthCol = u_jTable.getColumnModel().getColumn(i).getMinWidth();
/* 137 */         widthColumns += widthCol;
/*     */       } 
/*     */       
/* 140 */       int lastColWidth = table_width - widthColumns;
/* 141 */       if (lastColWidth < 0) {
/*     */         
/* 143 */         u_jTable.setAutoResizeMode(0);
/*     */         
/* 145 */         setColumnMaxMin(u_jTable, lastColIndex);
/*     */       } else {
/* 147 */         u_jTable.getColumnModel().getColumn(lastColIndex).setMaxWidth(lastColWidth);
/* 148 */         u_jTable.getColumnModel().getColumn(lastColIndex).setMinWidth(lastColWidth);
/*     */       } 
/* 150 */     } catch (Exception e) {
/* 151 */       log.error("Exception in setAllColumnsMaxMin() method :" + e.getMessage());
/*     */     } 
/*     */   }
/*     */   
/*     */   public static int getWidthCell(JTable u_jTable, int row, int col) {
/* 156 */     int width = 0;
/*     */     
/*     */     try {
/* 159 */       int nrOfCol = u_jTable.getColumnCount();
/* 160 */       int nrOfRows = u_jTable.getRowCount();
/*     */       
/* 162 */       if (nrOfCol == 0 || nrOfRows == 0) {
/* 163 */         return 0;
/*     */       }
/*     */       
/* 166 */       FontMetrics u_fmCell = u_jTable.getFontMetrics(u_jTable.getFont());
/* 167 */       FontMetrics u_fmHeader = u_jTable.getFontMetrics(u_jTable.getFont());
/*     */       
/* 169 */       u_fmHeader.stringWidth(u_jTable.getModel().getColumnName(col));
/* 170 */       u_jTable.getColumnModel().getColumn(col);
/* 171 */       String u_value = null;
/*     */       
/* 173 */       u_value = String.valueOf(u_jTable.getValueAt(row, col)).toString();
/* 174 */       if (u_value != null) {
/* 175 */         u_value = Transform.convertHtmlToString(u_value);
/* 176 */         width = u_fmCell.stringWidth(u_value);
/*     */       }
/*     */     
/* 179 */     } catch (Exception e) {
/* 180 */       log.error("Exception in getWidthCell() method :" + e.getMessage());
/*     */     } 
/*     */     
/* 183 */     return width;
/*     */   }
/*     */ 
/*     */   
/*     */   public static void fillValueWithSpace(JTable u_jTable, int col) {
/*     */     try {
/* 189 */       int nrOfCol = u_jTable.getColumnCount();
/* 190 */       int nrOfRows = u_jTable.getRowCount();
/*     */       
/* 192 */       if (nrOfCol == 0 || nrOfRows == 0)
/*     */         return; 
/* 194 */       DefaultTableModel model = (DefaultTableModel)u_jTable.getModel();
/*     */       
/* 196 */       int max = 0;
/* 197 */       String u_value = null;
/*     */       int j;
/* 199 */       for (j = 0; j < nrOfRows; j++) {
/* 200 */         u_value = String.valueOf(u_jTable.getValueAt(j, col));
/* 201 */         if (u_value != null) {
/* 202 */           int length = u_value.length();
/* 203 */           max = Math.max(max, length);
/*     */         } 
/*     */       } 
/*     */       
/* 207 */       for (j = 0; j < nrOfRows; j++) {
/* 208 */         u_value = String.valueOf(u_jTable.getValueAt(j, col));
/* 209 */         if (u_value != null) {
/* 210 */           int length = u_value.length();
/* 211 */           if (max > length) {
/* 212 */             StringBuffer buf = new StringBuffer();
/* 213 */             for (int i = 0; i < max - length; i++) {
/* 214 */               buf.append("  ");
/*     */             }
/* 216 */             buf.append(u_value);
/* 217 */             model.setValueAt(buf.toString(), j, col);
/*     */           }
/*     */         
/*     */         } 
/*     */       } 
/* 222 */     } catch (Exception e) {
/* 223 */       log.error("Exception in fillValueWithSpace method :" + e.getMessage());
/*     */     } 
/*     */   }
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
/*     */   private static String getAdjustStringLength(String u_crtString, int maxLength, FontMetrics fm) {
/* 238 */     int widthString = 0;
/* 239 */     String resultString = null;
/*     */     
/*     */     try {
/* 242 */       if (fm.stringWidth(u_crtString) < maxLength) {
/* 243 */         return "";
/*     */       }
/* 245 */       for (int i = 1; widthString < maxLength; i++) {
/* 246 */         resultString = u_crtString.substring(0, i);
/* 247 */         widthString = fm.stringWidth(resultString);
/*     */       } 
/*     */       
/* 250 */       String lastChar = resultString.substring(resultString.length() - 1, resultString.length());
/* 251 */       String nextChar = u_crtString.substring(resultString.length(), resultString.length() + 1);
/*     */       
/* 253 */       int j = 1;
/* 254 */       while (!isEndOfWord(lastChar) && !nextChar.equals(" ") && 
/* 255 */         resultString.length() - j >= 0)
/*     */       {
/* 257 */         resultString = resultString.substring(0, resultString.length() - j);
/* 258 */         if (!resultString.equals("")) {
/* 259 */           lastChar = resultString.substring(resultString.length() - 1, resultString.length());
/*     */         }
/*     */       }
/*     */     
/* 263 */     } catch (Exception e) {
/* 264 */       log.error("Exception in getAdjustStringLength() method " + e.getMessage());
/*     */     } 
/* 266 */     return resultString;
/*     */   }
/*     */ 
/*     */   
/*     */   private static boolean isEndOfWord(String u_value) {
/* 271 */     if (u_value.equals(" ") || u_value.equals(":") || u_value.equals(".") || u_value.equals("!") || u_value.equals("/")) {
/* 272 */       return true;
/*     */     }
/* 274 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int getMaxWidthString(JTable u_table, int col) {
/* 286 */     int maxColWidth = 300;
/* 287 */     int maxWidthString = 0;
/*     */ 
/*     */     
/*     */     try {
/* 291 */       FontMetrics fm = u_table.getFontMetrics(u_table.getFont());
/* 292 */       maxWidthString = fm.stringWidth((String)u_table.getColumnModel().getColumn(col).getHeaderValue());
/* 293 */       for (int iRow = 1; iRow < u_table.getRowCount(); iRow++) {
/* 294 */         int widthString = fm.stringWidth(String.valueOf(u_table.getValueAt(iRow, col)));
/*     */         
/* 296 */         if (widthString < maxColWidth) {
/* 297 */           maxWidthString = Math.max(maxWidthString, widthString);
/*     */         }
/*     */         
/* 300 */         if (widthString >= maxColWidth && 
/* 301 */           iRow % 2 == 0) {
/* 302 */           String crtValue = String.valueOf(u_table.getModel().getValueAt(iRow, col));
/* 303 */           String firstPartString = crtValue.substring(0, crtValue.length() / 2);
/* 304 */           maxWidthString = Math.max(maxWidthString, fm.stringWidth(firstPartString));
/*     */         }
/*     */       
/*     */       }
/*     */     
/* 309 */     } catch (Exception e) {
/* 310 */       log.error("Exception in getMaxWidthString method:" + e.getMessage());
/*     */     } 
/*     */     
/* 313 */     return maxWidthString;
/*     */   }
/*     */   
/*     */   private static Vector getNewVectorData(String u_data, int nrCol) {
/* 317 */     Vector<String> newData = new Vector();
/* 318 */     for (int i = 0; i < nrCol; i++) {
/* 319 */       newData.add("");
/*     */     }
/* 321 */     newData.add(u_data);
/*     */     
/* 323 */     return newData;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static void splittingCellsColumn(JTable u_table, int col, int maxColWidth) {
/*     */     try {
/* 330 */       TableColumn colModel = u_table.getColumnModel().getColumn(col);
/* 331 */       colModel.setPreferredWidth(maxColWidth + 20);
/* 332 */       FontMetrics fm = u_table.getFontMetrics(u_table.getFont());
/*     */       
/* 334 */       DefaultTableModel model = (DefaultTableModel)u_table.getModel();
/*     */       
/* 336 */       for (int iRow = 0; iRow < model.getRowCount(); iRow++) {
/*     */         
/* 338 */         if (model.getValueAt(iRow, col) != null && !model.getValueAt(iRow, col).equals("")) {
/* 339 */           String crtValue = String.valueOf(model.getValueAt(iRow, col));
/* 340 */           String firstPartOfString = getAdjustStringLength(crtValue, maxColWidth, fm);
/*     */ 
/*     */           
/* 343 */           if (!firstPartOfString.equals("")) {
/* 344 */             model.setValueAt(firstPartOfString, iRow, col);
/* 345 */             String secondPartOfString = crtValue.substring(firstPartOfString.length(), crtValue.length());
/*     */             
/* 347 */             if (!secondPartOfString.equals("")) {
/* 348 */               if (iRow + 1 == model.getRowCount()) {
/*     */                 
/* 350 */                 model.addRow(getNewVectorData(secondPartOfString, col));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */               
/*     */               }
/* 358 */               else if (model.getValueAt(iRow + 1, col).equals("")) {
/* 359 */                 model.setValueAt(secondPartOfString, iRow + 1, col);
/*     */               } else {
/*     */                 
/* 362 */                 model.insertRow(iRow + 1, getNewVectorData(secondPartOfString, col));
/*     */               }
/*     */             
/*     */             }
/*     */           }
/*     */         
/*     */         }
/*     */       
/*     */       } 
/* 371 */     } catch (Exception e) {
/* 372 */       log.error("Exception in splittingCellsColumn method:" + e.getMessage());
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\clien\\util\TableUtilities.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */