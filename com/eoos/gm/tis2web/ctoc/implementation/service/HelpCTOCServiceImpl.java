/*     */ package com.eoos.gm.tis2web.ctoc.implementation.service;
/*     */ 
/*     */ import com.eoos.gm.tis2web.ctoc.implementation.common.CTOCImpl2;
/*     */ import com.eoos.gm.tis2web.ctoc.implementation.common.db.CTOCMerge;
/*     */ import com.eoos.gm.tis2web.ctoc.implementation.common.db.CTOCRootElement;
/*     */ import com.eoos.gm.tis2web.ctoc.service.HelpCTOCService;
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
/*     */ public class HelpCTOCServiceImpl
/*     */   extends CTOCServiceImpl
/*     */   implements HelpCTOCService
/*     */ {
/*  31 */   private static final Logger log = Logger.getLogger(HelpCTOCServiceImpl.class);
/*     */   
/*  33 */   private final Object SYNC_CTOC = new Object();
/*     */   
/*  35 */   private CTOCImpl2 ctoc = null;
/*     */   
/*     */   public HelpCTOCServiceImpl() {
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
/*     */     //   11: ifnonnull -> 419
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
/*     */     //   79: getstatic com/eoos/gm/tis2web/ctoc/implementation/service/HelpCTOCServiceImpl.log : Lorg/apache/log4j/Logger;
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
/*     */     //   118: getstatic com/eoos/gm/tis2web/ctoc/implementation/service/HelpCTOCServiceImpl.log : Lorg/apache/log4j/Logger;
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
/*     */     //   152: invokestatic getInstance : ()Lcom/eoos/gm/tis2web/help/service/HelpServiceProvider;
/*     */     //   155: invokevirtual getService : ()Lcom/eoos/gm/tis2web/help/service/HelpService;
/*     */     //   158: astore #4
/*     */     //   160: aload #4
/*     */     //   162: invokeinterface getInstances : ()Ljava/util/Iterator;
/*     */     //   167: astore #5
/*     */     //   169: aload #5
/*     */     //   171: invokeinterface hasNext : ()Z
/*     */     //   176: ifeq -> 381
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
/*     */     //   208: getstatic com/eoos/gm/tis2web/ctoc/service/cai/CTOCType.HELP : Lcom/eoos/gm/tis2web/ctoc/service/cai/CTOCType;
/*     */     //   211: aload #7
/*     */     //   213: invokevirtual put : (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
/*     */     //   216: pop
/*     */     //   217: aload_3
/*     */     //   218: getstatic com/eoos/gm/tis2web/ctoc/service/cai/CTOCType.VERSION : Lcom/eoos/gm/tis2web/ctoc/service/cai/CTOCType;
/*     */     //   221: aload #7
/*     */     //   223: invokevirtual put : (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
/*     */     //   226: pop
/*     */     //   227: aload #4
/*     */     //   229: aload #6
/*     */     //   231: invokeinterface getDatabaseLink : (Ljava/lang/String;)Lcom/eoos/gm/tis2web/frame/export/common/util/IDatabaseLink;
/*     */     //   236: astore #8
/*     */     //   238: aconst_null
/*     */     //   239: astore #9
/*     */     //   241: aload #8
/*     */     //   243: invokeinterface requestConnection : ()Ljava/sql/Connection;
/*     */     //   248: astore #9
/*     */     //   250: aload_0
/*     */     //   251: getfield ctoc : Lcom/eoos/gm/tis2web/ctoc/implementation/common/CTOCImpl2;
/*     */     //   254: ifnonnull -> 285
/*     */     //   257: aload_0
/*     */     //   258: new com/eoos/gm/tis2web/ctoc/implementation/common/CTOCImpl2
/*     */     //   261: dup
/*     */     //   262: aload #8
/*     */     //   264: aload #9
/*     */     //   266: getstatic com/eoos/gm/tis2web/ctoc/service/cai/CTOCDomain.HELP : Lcom/eoos/gm/tis2web/ctoc/service/cai/CTOCDomain;
/*     */     //   269: aconst_null
/*     */     //   270: aload_3
/*     */     //   271: aload_2
/*     */     //   272: aload_0
/*     */     //   273: getfield lvcRetrieval : Lcom/eoos/gm/tis2web/vcr/v2/ILVCAdapter$Retrieval;
/*     */     //   276: invokespecial <init> : (Lcom/eoos/gm/tis2web/frame/export/common/util/IDatabaseLink;Ljava/sql/Connection;Lcom/eoos/gm/tis2web/ctoc/service/cai/CTOCDomain;Lcom/eoos/gm/tis2web/frame/export/common/datatype/CachingStrategy;Ljava/util/Map;Ljava/util/Collection;Lcom/eoos/gm/tis2web/vcr/v2/ILVCAdapter$Retrieval;)V
/*     */     //   279: putfield ctoc : Lcom/eoos/gm/tis2web/ctoc/implementation/common/CTOCImpl2;
/*     */     //   282: goto -> 318
/*     */     //   285: aload_0
/*     */     //   286: aload_0
/*     */     //   287: aload_0
/*     */     //   288: getfield ctoc : Lcom/eoos/gm/tis2web/ctoc/implementation/common/CTOCImpl2;
/*     */     //   291: new com/eoos/gm/tis2web/ctoc/implementation/common/CTOCImpl2
/*     */     //   294: dup
/*     */     //   295: aload #8
/*     */     //   297: aload #9
/*     */     //   299: getstatic com/eoos/gm/tis2web/ctoc/service/cai/CTOCDomain.HELP : Lcom/eoos/gm/tis2web/ctoc/service/cai/CTOCDomain;
/*     */     //   302: aconst_null
/*     */     //   303: aload_3
/*     */     //   304: aload_2
/*     */     //   305: aload_0
/*     */     //   306: getfield lvcRetrieval : Lcom/eoos/gm/tis2web/vcr/v2/ILVCAdapter$Retrieval;
/*     */     //   309: invokespecial <init> : (Lcom/eoos/gm/tis2web/frame/export/common/util/IDatabaseLink;Ljava/sql/Connection;Lcom/eoos/gm/tis2web/ctoc/service/cai/CTOCDomain;Lcom/eoos/gm/tis2web/frame/export/common/datatype/CachingStrategy;Ljava/util/Map;Ljava/util/Collection;Lcom/eoos/gm/tis2web/vcr/v2/ILVCAdapter$Retrieval;)V
/*     */     //   312: invokevirtual merge : (Lcom/eoos/gm/tis2web/ctoc/implementation/common/CTOCImpl2;Lcom/eoos/gm/tis2web/ctoc/implementation/common/CTOCImpl2;)Lcom/eoos/gm/tis2web/ctoc/implementation/common/CTOCImpl2;
/*     */     //   315: putfield ctoc : Lcom/eoos/gm/tis2web/ctoc/implementation/common/CTOCImpl2;
/*     */     //   318: jsr -> 350
/*     */     //   321: goto -> 378
/*     */     //   324: astore #10
/*     */     //   326: getstatic com/eoos/gm/tis2web/ctoc/implementation/service/HelpCTOCServiceImpl.log : Lorg/apache/log4j/Logger;
/*     */     //   329: ldc 'failed to init help-ctoc provider'
/*     */     //   331: aload #10
/*     */     //   333: invokevirtual error : (Ljava/lang/Object;Ljava/lang/Throwable;)V
/*     */     //   336: jsr -> 350
/*     */     //   339: goto -> 378
/*     */     //   342: astore #11
/*     */     //   344: jsr -> 350
/*     */     //   347: aload #11
/*     */     //   349: athrow
/*     */     //   350: astore #12
/*     */     //   352: aload #8
/*     */     //   354: ifnull -> 376
/*     */     //   357: aload #9
/*     */     //   359: ifnull -> 376
/*     */     //   362: aload #8
/*     */     //   364: aload #9
/*     */     //   366: invokeinterface releaseConnection : (Ljava/sql/Connection;)V
/*     */     //   371: goto -> 376
/*     */     //   374: astore #13
/*     */     //   376: ret #12
/*     */     //   378: goto -> 169
/*     */     //   381: goto -> 419
/*     */     //   384: astore_2
/*     */     //   385: getstatic com/eoos/gm/tis2web/ctoc/implementation/service/HelpCTOCServiceImpl.log : Lorg/apache/log4j/Logger;
/*     */     //   388: new java/lang/StringBuilder
/*     */     //   391: dup
/*     */     //   392: invokespecial <init> : ()V
/*     */     //   395: ldc 'unable to init ctoc for help - exception:'
/*     */     //   397: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
/*     */     //   400: aload_2
/*     */     //   401: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
/*     */     //   404: invokevirtual toString : ()Ljava/lang/String;
/*     */     //   407: invokevirtual error : (Ljava/lang/Object;)V
/*     */     //   410: new com/eoos/datatype/ExceptionWrapper
/*     */     //   413: dup
/*     */     //   414: aload_2
/*     */     //   415: invokespecial <init> : (Ljava/lang/Throwable;)V
/*     */     //   418: athrow
/*     */     //   419: aload_0
/*     */     //   420: getfield ctoc : Lcom/eoos/gm/tis2web/ctoc/implementation/common/CTOCImpl2;
/*     */     //   423: aload_1
/*     */     //   424: monitorexit
/*     */     //   425: areturn
/*     */     //   426: astore #14
/*     */     //   428: aload_1
/*     */     //   429: monitorexit
/*     */     //   430: aload #14
/*     */     //   432: athrow
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
/*     */     //   #73	-> 227
/*     */     //   #74	-> 238
/*     */     //   #76	-> 241
/*     */     //   #77	-> 250
/*     */     //   #78	-> 257
/*     */     //   #80	-> 285
/*     */     //   #82	-> 318
/*     */     //   #91	-> 321
/*     */     //   #82	-> 324
/*     */     //   #83	-> 326
/*     */     //   #84	-> 336
/*     */     //   #91	-> 339
/*     */     //   #85	-> 342
/*     */     //   #87	-> 362
/*     */     //   #89	-> 371
/*     */     //   #88	-> 374
/*     */     //   #89	-> 376
/*     */     //   #92	-> 378
/*     */     //   #96	-> 381
/*     */     //   #93	-> 384
/*     */     //   #94	-> 385
/*     */     //   #95	-> 410
/*     */     //   #98	-> 419
/*     */     //   #99	-> 426
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	descriptor
/*     */     //   54	22	4	toc	Lcom/eoos/gm/tis2web/ctoc/service/cai/CTOC;
/*     */     //   34	45	3	iter	Ljava/util/Iterator;
/*     */     //   118	26	3	e	Ljava/lang/Exception;
/*     */     //   326	10	10	e	Ljava/lang/Exception;
/*     */     //   376	0	13	xx	Ljava/lang/Exception;
/*     */     //   191	187	6	instance	Ljava/lang/String;
/*     */     //   207	171	7	factory	Lcom/eoos/gm/tis2web/ctoc/service/cai/CTOCFactory;
/*     */     //   238	140	8	helpDB	Lcom/eoos/gm/tis2web/frame/export/common/util/IDatabaseLink;
/*     */     //   241	137	9	help	Ljava/sql/Connection;
/*     */     //   22	359	2	sits	Ljava/util/Collection;
/*     */     //   152	229	3	factories	Ljava/util/HashMap;
/*     */     //   160	221	4	service	Lcom/eoos/gm/tis2web/help/service/HelpService;
/*     */     //   169	212	5	instances	Ljava/util/Iterator;
/*     */     //   385	34	2	e	Ljava/lang/Exception;
/*     */     //   0	433	0	this	Lcom/eoos/gm/tis2web/ctoc/implementation/service/HelpCTOCServiceImpl;
/*     */     // Exception table:
/*     */     //   from	to	target	type
/*     */     //   7	425	426	finally
/*     */     //   14	381	384	java/lang/Exception
/*     */     //   22	114	117	java/lang/Exception
/*     */     //   241	318	324	java/lang/Exception
/*     */     //   241	321	342	finally
/*     */     //   324	339	342	finally
/*     */     //   342	347	342	finally
/*     */     //   362	371	374	java/lang/Exception
/*     */     //   426	430	426	finally
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
/*     */   protected CTOCImpl2 merge(CTOCImpl2 a, CTOCImpl2 b) {
/* 103 */     int ctype = CTOCType.CTOC.ord();
/* 104 */     CTOCRootElement result = new CTOCRootElement(0, ctype, true, false, null, null);
/* 105 */     result.add(CTOCProperty.ID, "0");
/* 106 */     result.add(CTOCProperty.VERSION, "0.0");
/* 107 */     CTOCMerge.merge(result, a.getCTOC(CTOCDomain.HELP), b.getCTOC(CTOCDomain.HELP), this.lvcRetrieval);
/* 108 */     a.replaceCTOC(result, CTOCDomain.HELP);
/* 109 */     return a;
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\ctoc\implementation\service\HelpCTOCServiceImpl.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */