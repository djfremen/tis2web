package com.eoos.html.renderer.menu;

import java.util.Map;

public abstract class TopTabMenuRenderer extends MenuRenderer {
  private static final String template = "<table class=\"tabmenu\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\"><tr>{ITEMS}</tr></table>";
  
  private static final String templateActiveItem = "<td style=\"vertical-align: bottom\" ><table class=\"activeitem\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\"><tr><td><img src=\"{IMAGE:edge-left.gif}\" alt=\"\" width=\"6\" height=\"6\" border=\"0\"></td><td style=\"background-image:url({IMAGE:border-top.gif})\"  height=\"1\"></td><td><img src=\"{IMAGE:edge-right.gif}\" alt=\"\" width=\"6\" height=\"6\" border=\"0\"></td></tr><tr><td class=\"bottomborderactive\" style=\"background-image:url({IMAGE:border-left.gif})\"  width=\"6\">&nbsp;</td><td class=\"bottomborderactive\" nowrap=\"nowrap\">{ITEM_CODE}</td><td class=\"bottomborderactive\" style=\"background-image:url({IMAGE:border-right.gif})\" width=\"6\">&nbsp;</td></tr></table></td>";
  
  private static final String templateInactiveItem = "<td style=\"vertical-align: bottom\"><table class=\"inactiveitem\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\"><tr><td><img src=\"{IMAGE:edge-left.gif}\" alt=\"\" width=\"6\" height=\"6\" border=\"0\"></td><td style=\"background-image:url({IMAGE:border-top.gif})\" height=\"1\"></td><td><img src=\"{IMAGE:edge-right.gif}\" alt=\"\" width=\"6\" height=\"6\" border=\"0\"></td></tr><tr><td class=\"bottomborder\" style=\"background-image:url({IMAGE:border-left.gif})\" width=\"6\">&nbsp;</td><td class=\"bottomborder\" nowrap=\"nowrap\">{ITEM_CODE}</td><td class=\"bottomborder\" style=\"background-image:url({IMAGE:border-right.gif})\"  width=\"6\">&nbsp;</td></tr></table></td>";
  
  private static final String codeEndSeparator = "<td width=\"99%\"><table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\"><tr><td class=\"bottomborder\" align=\"right\">{REMAINDER}</td></tr></table></td>";
  
  public String getHtmlCode(MenuRenderer.Callback callback, Map params) {
    // Byte code:
    //   0: invokestatic getThreadInstance : ()Lcom/eoos/scsm/v2/objectpool/StringBufferPool;
    //   3: ldc '<table class="tabmenu" border="0" cellpadding="0" cellspacing="0" width="100%"><tr>{ITEMS}</tr></table>'
    //   5: invokevirtual get : (Ljava/lang/CharSequence;)Ljava/lang/StringBuffer;
    //   8: astore_3
    //   9: aload_1
    //   10: aload_2
    //   11: invokeinterface init : (Ljava/util/Map;)V
    //   16: aload_1
    //   17: invokeinterface getItems : ()Ljava/util/List;
    //   22: invokeinterface iterator : ()Ljava/util/Iterator;
    //   27: astore #4
    //   29: aload #4
    //   31: invokeinterface hasNext : ()Z
    //   36: ifeq -> 159
    //   39: aload #4
    //   41: invokeinterface next : ()Ljava/lang/Object;
    //   46: astore #5
    //   48: invokestatic getThreadInstance : ()Lcom/eoos/scsm/v2/objectpool/StringBufferPool;
    //   51: invokevirtual get : ()Ljava/lang/StringBuffer;
    //   54: astore #6
    //   56: aload_1
    //   57: aload #5
    //   59: invokeinterface isActive : (Ljava/lang/Object;)Z
    //   64: ifeq -> 78
    //   67: aload #6
    //   69: ldc '<td style="vertical-align: bottom" ><table class="activeitem" border="0" cellpadding="0" cellspacing="0"><tr><td><img src="{IMAGE:edge-left.gif}" alt="" width="6" height="6" border="0"></td><td style="background-image:url({IMAGE:border-top.gif})"  height="1"></td><td><img src="{IMAGE:edge-right.gif}" alt="" width="6" height="6" border="0"></td></tr><tr><td class="bottomborderactive" style="background-image:url({IMAGE:border-left.gif})"  width="6">&nbsp;</td><td class="bottomborderactive" nowrap="nowrap">{ITEM_CODE}</td><td class="bottomborderactive" style="background-image:url({IMAGE:border-right.gif})" width="6">&nbsp;</td></tr></table></td>'
    //   71: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuffer;
    //   74: pop
    //   75: goto -> 86
    //   78: aload #6
    //   80: ldc '<td style="vertical-align: bottom"><table class="inactiveitem" border="0" cellpadding="0" cellspacing="0"><tr><td><img src="{IMAGE:edge-left.gif}" alt="" width="6" height="6" border="0"></td><td style="background-image:url({IMAGE:border-top.gif})" height="1"></td><td><img src="{IMAGE:edge-right.gif}" alt="" width="6" height="6" border="0"></td></tr><tr><td class="bottomborder" style="background-image:url({IMAGE:border-left.gif})" width="6">&nbsp;</td><td class="bottomborder" nowrap="nowrap">{ITEM_CODE}</td><td class="bottomborder" style="background-image:url({IMAGE:border-right.gif})"  width="6">&nbsp;</td></tr></table></td>'
    //   82: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuffer;
    //   85: pop
    //   86: aload #6
    //   88: ldc '{ITEM_CODE}'
    //   90: aload_1
    //   91: aload #5
    //   93: invokeinterface getCode : (Ljava/lang/Object;)Ljava/lang/String;
    //   98: invokestatic replace : (Ljava/lang/StringBuffer;Ljava/lang/String;Ljava/lang/String;)V
    //   101: aload_3
    //   102: ldc '{ITEMS}'
    //   104: new java/lang/StringBuilder
    //   107: dup
    //   108: invokespecial <init> : ()V
    //   111: aload #6
    //   113: invokevirtual toString : ()Ljava/lang/String;
    //   116: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   119: ldc '{ITEMS}'
    //   121: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   124: invokevirtual toString : ()Ljava/lang/String;
    //   127: invokestatic replace : (Ljava/lang/StringBuffer;Ljava/lang/String;Ljava/lang/String;)V
    //   130: jsr -> 144
    //   133: goto -> 156
    //   136: astore #7
    //   138: jsr -> 144
    //   141: aload #7
    //   143: athrow
    //   144: astore #8
    //   146: invokestatic getThreadInstance : ()Lcom/eoos/scsm/v2/objectpool/StringBufferPool;
    //   149: aload #6
    //   151: invokevirtual free : (Ljava/lang/StringBuffer;)V
    //   154: ret #8
    //   156: goto -> 29
    //   159: aload_3
    //   160: ldc '{ITEMS}'
    //   162: ldc '<td width="99%"><table border="0" cellpadding="0" cellspacing="0" width="100%"><tr><td class="bottomborder" align="right">{REMAINDER}</td></tr></table></td>'
    //   164: invokestatic replace : (Ljava/lang/StringBuffer;Ljava/lang/String;Ljava/lang/String;)V
    //   167: aload_3
    //   168: ldc '{IMAGE:border-top.gif}'
    //   170: aload_0
    //   171: ldc 'border-top.gif'
    //   173: invokevirtual getImageSrc : (Ljava/lang/String;)Ljava/lang/String;
    //   176: invokestatic escapeReservedHTMLChars : (Ljava/lang/String;)Ljava/lang/String;
    //   179: invokestatic replace : (Ljava/lang/StringBuffer;Ljava/lang/String;Ljava/lang/String;)V
    //   182: aload_3
    //   183: ldc '{IMAGE:edge-right.gif}'
    //   185: aload_0
    //   186: ldc 'edge-right.gif'
    //   188: invokevirtual getImageSrc : (Ljava/lang/String;)Ljava/lang/String;
    //   191: invokestatic escapeReservedHTMLChars : (Ljava/lang/String;)Ljava/lang/String;
    //   194: invokestatic replace : (Ljava/lang/StringBuffer;Ljava/lang/String;Ljava/lang/String;)V
    //   197: aload_3
    //   198: ldc '{IMAGE:edge-left.gif}'
    //   200: aload_0
    //   201: ldc 'edge-left.gif'
    //   203: invokevirtual getImageSrc : (Ljava/lang/String;)Ljava/lang/String;
    //   206: invokestatic escapeReservedHTMLChars : (Ljava/lang/String;)Ljava/lang/String;
    //   209: invokestatic replace : (Ljava/lang/StringBuffer;Ljava/lang/String;Ljava/lang/String;)V
    //   212: aload_3
    //   213: ldc '{IMAGE:border-right.gif}'
    //   215: aload_0
    //   216: ldc 'border-right.gif'
    //   218: invokevirtual getImageSrc : (Ljava/lang/String;)Ljava/lang/String;
    //   221: invokestatic escapeReservedHTMLChars : (Ljava/lang/String;)Ljava/lang/String;
    //   224: invokestatic replace : (Ljava/lang/StringBuffer;Ljava/lang/String;Ljava/lang/String;)V
    //   227: aload_3
    //   228: ldc '{IMAGE:border-left.gif}'
    //   230: aload_0
    //   231: ldc 'border-left.gif'
    //   233: invokevirtual getImageSrc : (Ljava/lang/String;)Ljava/lang/String;
    //   236: invokestatic escapeReservedHTMLChars : (Ljava/lang/String;)Ljava/lang/String;
    //   239: invokestatic replace : (Ljava/lang/StringBuffer;Ljava/lang/String;Ljava/lang/String;)V
    //   242: ldc '&nbsp;'
    //   244: astore #5
    //   246: aload_1
    //   247: instanceof com/eoos/html/renderer/menu/MenuRenderer$Remainder
    //   250: ifeq -> 273
    //   253: aload_1
    //   254: checkcast com/eoos/html/renderer/menu/MenuRenderer$Remainder
    //   257: invokeinterface getRemainder : ()Lcom/eoos/html/element/HtmlElement;
    //   262: aload_2
    //   263: invokeinterface getHtmlCode : (Ljava/util/Map;)Ljava/lang/String;
    //   268: astore #5
    //   270: goto -> 277
    //   273: ldc '<table><tr><td>&nbsp;</td></tr></table>'
    //   275: astore #5
    //   277: aload_3
    //   278: ldc '{REMAINDER}'
    //   280: aload #5
    //   282: invokestatic replace : (Ljava/lang/StringBuffer;Ljava/lang/String;Ljava/lang/String;)V
    //   285: aload_3
    //   286: invokevirtual toString : ()Ljava/lang/String;
    //   289: astore #6
    //   291: jsr -> 305
    //   294: aload #6
    //   296: areturn
    //   297: astore #9
    //   299: jsr -> 305
    //   302: aload #9
    //   304: athrow
    //   305: astore #10
    //   307: invokestatic getThreadInstance : ()Lcom/eoos/scsm/v2/objectpool/StringBufferPool;
    //   310: aload_3
    //   311: invokevirtual free : (Ljava/lang/StringBuffer;)V
    //   314: ret #10
    // Line number table:
    //   Java source line number -> byte code offset
    //   #39	-> 0
    //   #41	-> 9
    //   #42	-> 16
    //   #43	-> 29
    //   #44	-> 39
    //   #45	-> 48
    //   #47	-> 56
    //   #48	-> 67
    //   #50	-> 78
    //   #53	-> 86
    //   #54	-> 101
    //   #55	-> 130
    //   #57	-> 133
    //   #56	-> 136
    //   #58	-> 156
    //   #59	-> 159
    //   #61	-> 167
    //   #62	-> 182
    //   #63	-> 197
    //   #64	-> 212
    //   #65	-> 227
    //   #67	-> 242
    //   #68	-> 246
    //   #69	-> 253
    //   #71	-> 273
    //   #73	-> 277
    //   #75	-> 285
    //   #78	-> 297
    // Local variable table:
    //   start	length	slot	name	descriptor
    //   48	108	5	item	Ljava/lang/Object;
    //   56	100	6	itemCode	Ljava/lang/StringBuffer;
    //   29	268	4	iter	Ljava/util/Iterator;
    //   246	51	5	remainder	Ljava/lang/String;
    //   0	316	0	this	Lcom/eoos/html/renderer/menu/TopTabMenuRenderer;
    //   0	316	1	callback	Lcom/eoos/html/renderer/menu/MenuRenderer$Callback;
    //   0	316	2	params	Ljava/util/Map;
    //   9	307	3	code	Ljava/lang/StringBuffer;
    // Exception table:
    //   from	to	target	type
    //   9	294	297	finally
    //   56	133	136	finally
    //   136	141	136	finally
    //   297	302	297	finally
  }
  
  protected abstract String getImageSrc(String paramString);
}


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\html\renderer\menu\TopTabMenuRenderer.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */