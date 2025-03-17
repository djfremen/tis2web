/*     */ package com.eoos.gm.tis2web.ctoc.implementation.service;
/*     */ 
/*     */ import com.eoos.gm.tis2web.ctoc.implementation.common.CTOCImpl2;
/*     */ import com.eoos.gm.tis2web.ctoc.implementation.common.db.CTOCMerge;
/*     */ import com.eoos.gm.tis2web.ctoc.implementation.common.db.CTOCRootElement;
/*     */ import com.eoos.gm.tis2web.ctoc.service.NewsCTOCService;
/*     */ import com.eoos.gm.tis2web.ctoc.service.cai.CTOC;
/*     */ import com.eoos.gm.tis2web.ctoc.service.cai.CTOCDomain;
/*     */ import com.eoos.gm.tis2web.ctoc.service.cai.CTOCProperty;
/*     */ import com.eoos.gm.tis2web.ctoc.service.cai.CTOCType;
/*     */ import com.eoos.gm.tis2web.vcr.v2.LVCAdapterProvider;
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
/*     */ public class NewsCTOCServiceImpl
/*     */   extends CTOCServiceImpl
/*     */   implements NewsCTOCService
/*     */ {
/*  31 */   private static final Logger log = Logger.getLogger(NewsCTOCServiceImpl.class);
/*     */   
/*  33 */   private final Object SYNC_CTOC = new Object();
/*     */   
/*  35 */   private CTOCImpl2 ctoc = null;
/*     */   
/*     */   public NewsCTOCServiceImpl() {
/*  38 */     super(null, LVCAdapterProvider.getGlobalAdapter().createRetrievalImpl(), null);
/*     */   }
/*     */   
/*     */   public void reset() {
/*  42 */     synchronized (this.SYNC_CTOC) {
/*  43 */       log.debug("resetting");
/*  44 */       this.ctoc = null;
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
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public CTOC getCTOC() {
/*     */     // Byte code:
/*     */     //   0: aload_0
/*     */     //   1: getfield SYNC_CTOC : Ljava/lang/Object;
/*     */     //   4: dup
/*     */     //   5: astore_1
/*     */     //   6: monitorenter
/*     */     //   7: aload_0
/*     */     //   8: getfield ctoc : Lcom/eoos/gm/tis2web/ctoc/implementation/common/CTOCImpl2;
/*     */     //   11: ifnonnull -> 409
/*     */     //   14: new java/util/HashSet
/*     */     //   17: dup
/*     */     //   18: invokespecial <init> : ()V
/*     */     //   21: astore_2
/*     */     //   22: invokestatic getInstance : ()Lcom/eoos/gm/tis2web/si/v2/SIDataAdapterFacade;
/*     */     //   25: invokevirtual getCTOCs : ()Ljava/util/Collection;
/*     */     //   28: invokeinterface iterator : ()Ljava/util/Iterator;
/*     */     //   33: astore_3
/*     */     //   34: aload_3
/*     */     //   35: invokeinterface hasNext : ()Z
/*     */     //   40: ifeq -> 79
/*     */     //   43: aload_3
/*     */     //   44: invokeinterface next : ()Ljava/lang/Object;
/*     */     //   49: checkcast com/eoos/gm/tis2web/ctoc/service/cai/CTOC
/*     */     //   52: astore #4
/*     */     //   54: aload_2
/*     */     //   55: aload #4
/*     */     //   57: getstatic com/eoos/gm/tis2web/ctoc/service/cai/CTOCDomain.SIT : Lcom/eoos/gm/tis2web/ctoc/service/cai/CTOCDomain;
/*     */     //   60: invokeinterface getCTOC : (Lcom/eoos/gm/tis2web/ctoc/service/cai/CTOCDomain;)Lcom/eoos/gm/tis2web/ctoc/service/cai/CTOCNode;
/*     */     //   65: invokeinterface getChildren : ()Ljava/util/List;
/*     */     //   70: invokeinterface addAll : (Ljava/util/Collection;)Z
/*     */     //   75: pop
/*     */     //   76: goto -> 34
/*     */     //   79: getstatic com/eoos/gm/tis2web/ctoc/implementation/service/NewsCTOCServiceImpl.log : Lorg/apache/log4j/Logger;
/*     */     //   82: new java/lang/StringBuilder
/*     */     //   85: dup
/*     */     //   86: invokespecial <init> : ()V
/*     */     //   89: ldc '... received '
/*     */     //   91: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
/*     */     //   94: aload_2
/*     */     //   95: invokeinterface size : ()I
/*     */     //   100: invokevirtual append : (I)Ljava/lang/StringBuilder;
/*     */     //   103: ldc ' SITs'
/*     */     //   105: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
/*     */     //   108: invokevirtual toString : ()Ljava/lang/String;
/*     */     //   111: invokevirtual debug : (Ljava/lang/Object;)V
/*     */     //   114: goto -> 144
/*     */     //   117: astore_3
/*     */     //   118: getstatic com/eoos/gm/tis2web/ctoc/implementation/service/NewsCTOCServiceImpl.log : Lorg/apache/log4j/Logger;
/*     */     //   121: new java/lang/StringBuilder
/*     */     //   124: dup
/*     */     //   125: invokespecial <init> : ()V
/*     */     //   128: ldc 'unable to receive SITs, ignoring - exception: '
/*     */     //   130: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
/*     */     //   133: aload_3
/*     */     //   134: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
/*     */     //   137: invokevirtual toString : ()Ljava/lang/String;
/*     */     //   140: aload_3
/*     */     //   141: invokevirtual warn : (Ljava/lang/Object;Ljava/lang/Throwable;)V
/*     */     //   144: new java/util/HashMap
/*     */     //   147: dup
/*     */     //   148: invokespecial <init> : ()V
/*     */     //   151: astore_3
/*     */     //   152: invokestatic getInstance : ()Lcom/eoos/gm/tis2web/news/service/NewsServiceProvider;
/*     */     //   155: invokevirtual getService : ()Lcom/eoos/gm/tis2web/news/service/NewsService;
/*     */     //   158: astore #4
/*     */     //   160: aload #4
/*     */     //   162: invokeinterface getInstances : ()Ljava/util/Iterator;
/*     */     //   167: astore #5
/*     */     //   169: aload #5
/*     */     //   171: invokeinterface hasNext : ()Z
/*     */     //   176: ifeq -> 371
/*     */     //   179: aload #5
/*     */     //   181: invokeinterface next : ()Ljava/lang/Object;
/*     */     //   186: checkcast java/lang/String
/*     */     //   189: astore #6
/*     */     //   191: aload #4
/*     */     //   193: aload #6
/*     */     //   195: invokeinterface getIOFactory : (Ljava/lang/String;)Lcom/eoos/gm/tis2web/ctoc/service/cai/IOFactory;
/*     */     //   200: invokeinterface getFactory : ()Lcom/eoos/gm/tis2web/ctoc/service/cai/CTOCFactory;
/*     */     //   205: astore #7
/*     */     //   207: aload_3
/*     */     //   208: getstatic com/eoos/gm/tis2web/ctoc/service/cai/CTOCType.NEWS : Lcom/eoos/gm/tis2web/ctoc/service/cai/CTOCType;
/*     */     //   211: aload #7
/*     */     //   213: invokevirtual put : (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
/*     */     //   216: pop
/*     */     //   217: aload #4
/*     */     //   219: aload #6
/*     */     //   221: invokeinterface getDatabaseLink : (Ljava/lang/String;)Lcom/eoos/gm/tis2web/frame/export/common/util/IDatabaseLink;
/*     */     //   226: astore #8
/*     */     //   228: aconst_null
/*     */     //   229: astore #9
/*     */     //   231: aload #8
/*     */     //   233: invokeinterface requestConnection : ()Ljava/sql/Connection;
/*     */     //   238: astore #9
/*     */     //   240: aload_0
/*     */     //   241: getfield ctoc : Lcom/eoos/gm/tis2web/ctoc/implementation/common/CTOCImpl2;
/*     */     //   244: ifnonnull -> 275
/*     */     //   247: aload_0
/*     */     //   248: new com/eoos/gm/tis2web/ctoc/implementation/common/CTOCImpl2
/*     */     //   251: dup
/*     */     //   252: aload #8
/*     */     //   254: aload #9
/*     */     //   256: getstatic com/eoos/gm/tis2web/ctoc/service/cai/CTOCDomain.NEWS : Lcom/eoos/gm/tis2web/ctoc/service/cai/CTOCDomain;
/*     */     //   259: aconst_null
/*     */     //   260: aload_3
/*     */     //   261: aload_2
/*     */     //   262: aload_0
/*     */     //   263: getfield lvcRetrieval : Lcom/eoos/gm/tis2web/vcr/v2/ILVCAdapter$Retrieval;
/*     */     //   266: invokespecial <init> : (Lcom/eoos/gm/tis2web/frame/export/common/util/IDatabaseLink;Ljava/sql/Connection;Lcom/eoos/gm/tis2web/ctoc/service/cai/CTOCDomain;Lcom/eoos/gm/tis2web/frame/export/common/datatype/CachingStrategy;Ljava/util/Map;Ljava/util/Collection;Lcom/eoos/gm/tis2web/vcr/v2/ILVCAdapter$Retrieval;)V
/*     */     //   269: putfield ctoc : Lcom/eoos/gm/tis2web/ctoc/implementation/common/CTOCImpl2;
/*     */     //   272: goto -> 308
/*     */     //   275: aload_0
/*     */     //   276: aload_0
/*     */     //   277: aload_0
/*     */     //   278: getfield ctoc : Lcom/eoos/gm/tis2web/ctoc/implementation/common/CTOCImpl2;
/*     */     //   281: new com/eoos/gm/tis2web/ctoc/implementation/common/CTOCImpl2
/*     */     //   284: dup
/*     */     //   285: aload #8
/*     */     //   287: aload #9
/*     */     //   289: getstatic com/eoos/gm/tis2web/ctoc/service/cai/CTOCDomain.NEWS : Lcom/eoos/gm/tis2web/ctoc/service/cai/CTOCDomain;
/*     */     //   292: aconst_null
/*     */     //   293: aload_3
/*     */     //   294: aload_2
/*     */     //   295: aload_0
/*     */     //   296: getfield lvcRetrieval : Lcom/eoos/gm/tis2web/vcr/v2/ILVCAdapter$Retrieval;
/*     */     //   299: invokespecial <init> : (Lcom/eoos/gm/tis2web/frame/export/common/util/IDatabaseLink;Ljava/sql/Connection;Lcom/eoos/gm/tis2web/ctoc/service/cai/CTOCDomain;Lcom/eoos/gm/tis2web/frame/export/common/datatype/CachingStrategy;Ljava/util/Map;Ljava/util/Collection;Lcom/eoos/gm/tis2web/vcr/v2/ILVCAdapter$Retrieval;)V
/*     */     //   302: invokevirtual merge : (Lcom/eoos/gm/tis2web/ctoc/implementation/common/CTOCImpl2;Lcom/eoos/gm/tis2web/ctoc/implementation/common/CTOCImpl2;)Lcom/eoos/gm/tis2web/ctoc/implementation/common/CTOCImpl2;
/*     */     //   305: putfield ctoc : Lcom/eoos/gm/tis2web/ctoc/implementation/common/CTOCImpl2;
/*     */     //   308: jsr -> 340
/*     */     //   311: goto -> 368
/*     */     //   314: astore #10
/*     */     //   316: getstatic com/eoos/gm/tis2web/ctoc/implementation/service/NewsCTOCServiceImpl.log : Lorg/apache/log4j/Logger;
/*     */     //   319: ldc 'failed to init news-ctoc provider'
/*     */     //   321: aload #10
/*     */     //   323: invokevirtual error : (Ljava/lang/Object;Ljava/lang/Throwable;)V
/*     */     //   326: jsr -> 340
/*     */     //   329: goto -> 368
/*     */     //   332: astore #11
/*     */     //   334: jsr -> 340
/*     */     //   337: aload #11
/*     */     //   339: athrow
/*     */     //   340: astore #12
/*     */     //   342: aload #8
/*     */     //   344: ifnull -> 366
/*     */     //   347: aload #9
/*     */     //   349: ifnull -> 366
/*     */     //   352: aload #8
/*     */     //   354: aload #9
/*     */     //   356: invokeinterface releaseConnection : (Ljava/sql/Connection;)V
/*     */     //   361: goto -> 366
/*     */     //   364: astore #13
/*     */     //   366: ret #12
/*     */     //   368: goto -> 169
/*     */     //   371: goto -> 409
/*     */     //   374: astore_2
/*     */     //   375: getstatic com/eoos/gm/tis2web/ctoc/implementation/service/NewsCTOCServiceImpl.log : Lorg/apache/log4j/Logger;
/*     */     //   378: new java/lang/StringBuilder
/*     */     //   381: dup
/*     */     //   382: invokespecial <init> : ()V
/*     */     //   385: ldc 'unable to init ctoc for news - exception:'
/*     */     //   387: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
/*     */     //   390: aload_2
/*     */     //   391: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
/*     */     //   394: invokevirtual toString : ()Ljava/lang/String;
/*     */     //   397: invokevirtual error : (Ljava/lang/Object;)V
/*     */     //   400: new com/eoos/datatype/ExceptionWrapper
/*     */     //   403: dup
/*     */     //   404: aload_2
/*     */     //   405: invokespecial <init> : (Ljava/lang/Throwable;)V
/*     */     //   408: athrow
/*     */     //   409: aload_0
/*     */     //   410: getfield ctoc : Lcom/eoos/gm/tis2web/ctoc/implementation/common/CTOCImpl2;
/*     */     //   413: aload_1
/*     */     //   414: monitorexit
/*     */     //   415: areturn
/*     */     //   416: astore #14
/*     */     //   418: aload_1
/*     */     //   419: monitorexit
/*     */     //   420: aload #14
/*     */     //   422: athrow
/*     */     // Line number table:
/*     */     //   Java source line number -> byte code offset
/*     */     //   #49	-> 0
/*     */     //   #50	-> 7
/*     */     //   #52	-> 14
/*     */     //   #55	-> 22
/*     */     //   #56	-> 43
/*     */     //   #57	-> 54
/*     */     //   #58	-> 76
/*     */     //   #59	-> 79
/*     */     //   #63	-> 114
/*     */     //   #61	-> 117
/*     */     //   #62	-> 118
/*     */     //   #65	-> 144
/*     */     //   #66	-> 152
/*     */     //   #67	-> 160
/*     */     //   #68	-> 169
/*     */     //   #69	-> 179
/*     */     //   #70	-> 191
/*     */     //   #71	-> 207
/*     */     //   #72	-> 217
/*     */     //   #73	-> 228
/*     */     //   #75	-> 231
/*     */     //   #76	-> 240
/*     */     //   #77	-> 247
/*     */     //   #79	-> 275
/*     */     //   #81	-> 308
/*     */     //   #90	-> 311
/*     */     //   #81	-> 314
/*     */     //   #82	-> 316
/*     */     //   #83	-> 326
/*     */     //   #90	-> 329
/*     */     //   #84	-> 332
/*     */     //   #86	-> 352
/*     */     //   #88	-> 361
/*     */     //   #87	-> 364
/*     */     //   #88	-> 366
/*     */     //   #91	-> 368
/*     */     //   #95	-> 371
/*     */     //   #92	-> 374
/*     */     //   #93	-> 375
/*     */     //   #94	-> 400
/*     */     //   #97	-> 409
/*     */     //   #98	-> 416
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	descriptor
/*     */     //   54	22	4	toc	Lcom/eoos/gm/tis2web/ctoc/service/cai/CTOC;
/*     */     //   34	45	3	iter	Ljava/util/Iterator;
/*     */     //   118	26	3	e	Ljava/lang/Exception;
/*     */     //   316	10	10	e	Ljava/lang/Exception;
/*     */     //   366	0	13	xx	Ljava/lang/Exception;
/*     */     //   191	177	6	instance	Ljava/lang/String;
/*     */     //   207	161	7	factory	Lcom/eoos/gm/tis2web/ctoc/service/cai/CTOCFactory;
/*     */     //   228	140	8	newsDB	Lcom/eoos/gm/tis2web/frame/export/common/util/IDatabaseLink;
/*     */     //   231	137	9	news	Ljava/sql/Connection;
/*     */     //   22	349	2	sits	Ljava/util/Collection;
/*     */     //   152	219	3	factories	Ljava/util/HashMap;
/*     */     //   160	211	4	service	Lcom/eoos/gm/tis2web/news/service/NewsService;
/*     */     //   169	202	5	instances	Ljava/util/Iterator;
/*     */     //   375	34	2	e	Ljava/lang/Exception;
/*     */     //   0	423	0	this	Lcom/eoos/gm/tis2web/ctoc/implementation/service/NewsCTOCServiceImpl;
/*     */     // Exception table:
/*     */     //   from	to	target	type
/*     */     //   7	415	416	finally
/*     */     //   14	371	374	java/lang/Exception
/*     */     //   22	114	117	java/lang/Exception
/*     */     //   231	308	314	java/lang/Exception
/*     */     //   231	311	332	finally
/*     */     //   314	329	332	finally
/*     */     //   332	337	332	finally
/*     */     //   352	361	364	java/lang/Exception
/*     */     //   416	420	416	finally
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
/*     */ 
/*     */ 
/*     */   
/*     */   protected CTOCImpl2 merge(CTOCImpl2 a, CTOCImpl2 b) {
/* 102 */     int ctype = CTOCType.CTOC.ord();
/* 103 */     CTOCRootElement result = new CTOCRootElement(0, ctype, true, false, null, null);
/* 104 */     result.add(CTOCProperty.ID, "0");
/* 105 */     result.add(CTOCProperty.VERSION, "0.0");
/* 106 */     CTOCMerge.merge(result, a.getCTOC(CTOCDomain.NEWS), b.getCTOC(CTOCDomain.NEWS), this.lvcRetrieval);
/* 107 */     a.replaceCTOC(result, CTOCDomain.NEWS);
/* 108 */     return a;
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\ctoc\implementation\service\NewsCTOCServiceImpl.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */