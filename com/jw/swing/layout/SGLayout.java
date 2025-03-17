/*     */ package com.jw.swing.layout;
/*     */ 
/*     */ import java.awt.Component;
/*     */ import java.awt.Container;
/*     */ import java.awt.Dimension;
/*     */ import java.awt.LayoutManager;
/*     */ import java.io.Serializable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SGLayout
/*     */   implements LayoutManager, Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*  19 */   public static int LEFT = 0;
/*  20 */   public static int CENTER = 1;
/*  21 */   public static int RIGHT = 2;
/*  22 */   public static int FILL = 4;
/*  23 */   public static int TOP = 8;
/*  24 */   public static int BOTTOM = 16;
/*     */   protected int rows;
/*     */   protected int cols;
/*  27 */   protected int topBorder = 0, leftBorder = 0, bottomBorder = 0, rightBorder = 0; protected int vgap; protected int hgap;
/*  28 */   protected int minW = 10; protected int minH = 10; protected double[] rowScale;
/*     */   protected double[] columnScale;
/*  30 */   protected int hAlignment = FILL; protected int vAlignment = FILL;
/*     */   
/*     */   protected int[][] hAlignments;
/*     */   
/*     */   protected int[][] vAlignments;
/*     */   
/*     */   protected int[] rowSizes;
/*     */   
/*     */   protected int[] columnSizes;
/*     */ 
/*     */   
/*     */   public SGLayout() {
/*  42 */     this(2, 2, FILL, FILL, 0, 0);
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
/*     */   
/*     */   public SGLayout(int rows, int cols) {
/*  57 */     this(rows, cols, FILL, FILL, 0, 0);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SGLayout(int rows, int cols, int hgap, int vgap) {
/*  77 */     this(rows, cols, FILL, FILL, hgap, vgap);
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
/*     */   public SGLayout(int rows, int cols, int hAlignment, int vAlignment, int hgap, int vgap) {
/* 101 */     this.hgap = hgap;
/* 102 */     this.vgap = vgap;
/* 103 */     this.hAlignment = hAlignment;
/* 104 */     this.vAlignment = vAlignment;
/*     */     
/* 106 */     setDimensions(rows, cols);
/*     */   }
/*     */   
/*     */   private void setScaleValues() {
/* 110 */     this.rowScale = new double[this.rows];
/* 111 */     this.columnScale = new double[this.cols];
/* 112 */     for (int i = 0; i < this.rows; i++)
/* 113 */       this.rowScale[i] = 1.0D; 
/* 114 */     for (int j = 0; j < this.cols; j++)
/* 115 */       this.columnScale[j] = 1.0D; 
/*     */   }
/*     */   
/*     */   private void setAlignments() {
/* 119 */     this.hAlignments = new int[this.rows][this.cols];
/* 120 */     this.vAlignments = new int[this.rows][this.cols];
/* 121 */     for (int i = 0; i < this.rows; i++) {
/* 122 */       for (int j = 0; j < this.cols; j++) {
/* 123 */         this.hAlignments[i][j] = this.hAlignment;
/* 124 */         this.vAlignments[i][j] = this.vAlignment;
/*     */       } 
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
/*     */   private void setDimensions(int rows, int cols) {
/* 139 */     this.rows = rows;
/* 140 */     this.cols = cols;
/*     */     
/* 142 */     setScaleValues();
/* 143 */     setAlignments();
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
/*     */ 
/*     */ 
/*     */   
/*     */   public void setMargins(int topBorder, int leftBorder, int bottomBorder, int rightBorder) {
/* 160 */     this.topBorder = topBorder;
/* 161 */     this.leftBorder = leftBorder;
/* 162 */     this.bottomBorder = bottomBorder;
/* 163 */     this.rightBorder = rightBorder;
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
/*     */   public void setRowScale(int index, double prop) {
/* 176 */     if (index >= 0 && index < this.rows) {
/* 177 */       this.rowScale[index] = prop;
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
/*     */   public void setColumnScale(int index, double prop) {
/* 191 */     if (index >= 0 && index < this.cols) {
/* 192 */       this.columnScale[index] = prop;
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
/*     */   public void setAlignment(int row, int column, int h, int v) {
/* 218 */     if (row < this.rows && column < this.cols) {
/* 219 */       this.hAlignments[row][column] = h;
/* 220 */       this.vAlignments[row][column] = v;
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
/*     */   
/*     */   public void setRowAlignment(int row, int h, int v) {
/* 236 */     if (row < this.rows) {
/* 237 */       for (int column = 0; column < this.cols; column++) {
/* 238 */         this.hAlignments[row][column] = h;
/* 239 */         this.vAlignments[row][column] = v;
/*     */       } 
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
/*     */   
/*     */   public void setColumnAlignment(int column, int h, int v) {
/* 256 */     if (column < this.cols) {
/* 257 */       for (int row = 0; row < this.rows; row++) {
/* 258 */         this.hAlignments[row][column] = h;
/* 259 */         this.vAlignments[row][column] = v;
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addLayoutComponent(String name, Component comp) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void removeLayoutComponent(Component comp) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Dimension preferredLayoutSize(Container parent) {
/* 286 */     synchronized (parent.getTreeLock()) {
/* 287 */       int ncomponents = parent.getComponentCount();
/* 288 */       int nrows = this.rows;
/* 289 */       int ncols = this.cols;
/*     */       
/* 291 */       if (nrows > 0) {
/* 292 */         ncols = (ncomponents + nrows - 1) / nrows;
/*     */       } else {
/* 294 */         nrows = (ncomponents + ncols - 1) / ncols;
/*     */       } 
/* 296 */       int totalWidth = 0;
/* 297 */       int totalHeight = 0;
/*     */       
/* 299 */       for (int i = 0; i < nrows; i++) {
/* 300 */         int prefWidth = 0, prefHeight = 0;
/*     */         
/* 302 */         for (int j = 0; j < ncols; j++) {
/* 303 */           int index = i * ncols + j;
/* 304 */           if (index < ncomponents) {
/*     */ 
/*     */             
/* 307 */             Component comp = parent.getComponent(index);
/* 308 */             Dimension d = comp.getPreferredSize();
/* 309 */             if (d.width < this.minW) {
/* 310 */               prefWidth += this.minW;
/*     */             } else {
/* 312 */               prefWidth += d.width;
/* 313 */             }  if (d.height > prefHeight)
/* 314 */               prefHeight = d.height; 
/*     */           } 
/* 316 */         }  if (prefWidth > totalWidth)
/* 317 */           totalWidth = prefWidth; 
/* 318 */         totalHeight += prefHeight;
/*     */       } 
/* 320 */       return new Dimension(totalWidth + this.leftBorder + this.rightBorder + (ncols - 1) * this.hgap, totalHeight + this.topBorder + this.bottomBorder + (nrows - 1) * this.vgap);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Dimension minimumLayoutSize(Container parent) {
/* 340 */     synchronized (parent.getTreeLock()) {
/* 341 */       int ncomponents = parent.getComponentCount();
/* 342 */       int nrows = this.rows;
/* 343 */       int ncols = this.cols;
/*     */       
/* 345 */       if (nrows > 0) {
/* 346 */         ncols = (ncomponents + nrows - 1) / nrows;
/*     */       } else {
/* 348 */         nrows = (ncomponents + ncols - 1) / ncols;
/*     */       } 
/* 350 */       int totalWidth = 0;
/* 351 */       int totalHeight = 0;
/*     */       
/* 353 */       for (int i = 0; i < nrows; i++) {
/* 354 */         int minWidth = 0, minHeight = 0;
/* 355 */         for (int j = 0; j < ncols; j++) {
/* 356 */           int index = i * ncols + j;
/* 357 */           if (index < ncomponents) {
/*     */ 
/*     */             
/* 360 */             Component comp = parent.getComponent(index);
/* 361 */             Dimension d = comp.getMinimumSize();
/* 362 */             int width = d.width;
/* 363 */             if (width < this.minW)
/* 364 */               width = this.minW; 
/* 365 */             minWidth += width;
/* 366 */             if (minHeight > d.height)
/* 367 */               minHeight = d.height; 
/*     */           } 
/* 369 */         }  if (totalWidth > minWidth)
/* 370 */           totalWidth = minWidth; 
/* 371 */         if (minHeight < this.minH)
/* 372 */           minHeight = this.minH; 
/* 373 */         totalHeight += minHeight;
/*     */       } 
/*     */ 
/*     */       
/* 377 */       return new Dimension(totalWidth + this.leftBorder + this.rightBorder + (ncols - 1) * this.hgap, totalHeight + this.topBorder + this.bottomBorder + (nrows - 1) * this.vgap);
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
/*     */   public void layoutContainer(Container parent) {
/* 390 */     int nComps = parent.getComponentCount();
/* 391 */     int y = this.topBorder;
/* 392 */     new Dimension(60, 30);
/*     */     
/* 394 */     allocateMaxSizes(parent);
/*     */     
/* 396 */     for (int i = 0; i < this.rows; i++) {
/* 397 */       int x = this.leftBorder;
/* 398 */       for (int j = 0; j < this.cols; j++) {
/* 399 */         int componentIndex = i * this.cols + j;
/* 400 */         if (componentIndex <= nComps - 1) {
/*     */ 
/*     */           
/* 403 */           Component c = parent.getComponent(componentIndex);
/* 404 */           if (c.isVisible())
/* 405 */             setComponentBounds(c, i, j, x, y); 
/* 406 */           x += this.columnSizes[j] + this.hgap;
/*     */         } 
/* 408 */       }  y += this.rowSizes[i] + this.vgap;
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
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void setComponentBounds(Component c, int row, int column, int left, int top) {
/* 427 */     Dimension d = c.getPreferredSize();
/* 428 */     int finalWidth = this.columnSizes[column];
/* 429 */     int finalHeight = this.rowSizes[row];
/*     */     
/* 431 */     int xSpace = finalWidth - d.width;
/* 432 */     if (xSpace > 0) {
/* 433 */       int alignment = this.hAlignments[row][column];
/* 434 */       if (alignment == RIGHT) {
/* 435 */         left += xSpace;
/* 436 */       } else if (alignment == CENTER) {
/* 437 */         left += xSpace / 2;
/*     */       } 
/* 439 */       if (alignment != FILL) {
/* 440 */         finalWidth = d.width;
/*     */       }
/*     */     } 
/* 443 */     int ySpace = finalHeight - d.height;
/* 444 */     if (ySpace > 0) {
/* 445 */       int vAlignment = this.vAlignments[row][column];
/* 446 */       if (vAlignment == BOTTOM) {
/* 447 */         top += ySpace;
/* 448 */       } else if (vAlignment == CENTER) {
/* 449 */         top += ySpace / 2;
/*     */       } 
/* 451 */       if (vAlignment != FILL)
/* 452 */         finalHeight = d.height; 
/*     */     } 
/* 454 */     c.setBounds(left, top, finalWidth, finalHeight);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void allocateMaxSizes(Container parent) {
/* 465 */     this.rowSizes = new int[this.rows];
/* 466 */     this.columnSizes = new int[this.cols];
/* 467 */     Dimension thisSize = parent.getSize();
/* 468 */     int width = thisSize.width - this.leftBorder - this.rightBorder - (this.cols - 1) * this.hgap;
/* 469 */     int height = thisSize.height - this.topBorder - this.bottomBorder - (this.rows - 1) * this.vgap;
/*     */     
/* 471 */     double totalRowProps = 0.0D;
/* 472 */     for (int i = 0; i < this.rows; i++) {
/* 473 */       totalRowProps += this.rowScale[i];
/*     */     }
/*     */     
/* 476 */     double totalColumnProps = 0.0D;
/* 477 */     for (int j = 0; j < this.cols; j++) {
/* 478 */       totalColumnProps += this.columnScale[j];
/*     */     }
/*     */     
/* 481 */     for (int p = 0; p < this.rows; p++) {
/* 482 */       this.rowSizes[p] = (int)(this.rowScale[p] * height / totalRowProps);
/*     */     }
/* 484 */     for (int q = 0; q < this.cols; q++)
/* 485 */       this.columnSizes[q] = (int)(this.columnScale[q] * width / totalColumnProps); 
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\jw\swing\layout\SGLayout.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */