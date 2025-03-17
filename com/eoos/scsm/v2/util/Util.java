/*      */ package com.eoos.scsm.v2.util;
/*      */ 
/*      */ import com.eoos.context.Context;
/*      */ import com.eoos.context.IContext;
/*      */ import com.eoos.observable.IObservableSupport;
/*      */ import com.eoos.observable.Notification;
/*      */ import com.eoos.observable.ObservableSupport;
/*      */ import com.eoos.scsm.v2.alphabet.Alphabet;
/*      */ import com.eoos.scsm.v2.io.InputStreamByteCount;
/*      */ import com.eoos.scsm.v2.io.StreamUtil;
/*      */ import com.eoos.scsm.v2.multiton.v4.IMultitonSupport;
/*      */ import com.eoos.scsm.v2.multiton.v4.WeakMultitonSupport;
/*      */ import com.eoos.scsm.v2.objectpool.StringBufferPool;
/*      */ import com.eoos.util.Transforming;
/*      */ import com.eoos.util.v2.Base64DecodingStream;
/*      */ import com.eoos.util.v2.Base64EncodingStream;
/*      */ import java.awt.Component;
/*      */ import java.awt.Point;
/*      */ import java.awt.Rectangle;
/*      */ import java.io.BufferedInputStream;
/*      */ import java.io.BufferedOutputStream;
/*      */ import java.io.File;
/*      */ import java.io.FileInputStream;
/*      */ import java.io.FileNotFoundException;
/*      */ import java.io.FileOutputStream;
/*      */ import java.io.IOException;
/*      */ import java.io.InputStream;
/*      */ import java.io.InputStreamReader;
/*      */ import java.io.LineNumberReader;
/*      */ import java.io.ObjectInputStream;
/*      */ import java.io.ObjectOutputStream;
/*      */ import java.io.OutputStream;
/*      */ import java.io.OutputStreamWriter;
/*      */ import java.io.PrintWriter;
/*      */ import java.io.Serializable;
/*      */ import java.io.StringReader;
/*      */ import java.io.StringWriter;
/*      */ import java.io.UnsupportedEncodingException;
/*      */ import java.lang.ref.SoftReference;
/*      */ import java.lang.ref.WeakReference;
/*      */ import java.lang.reflect.Array;
/*      */ import java.lang.reflect.InvocationTargetException;
/*      */ import java.math.BigInteger;
/*      */ import java.net.MalformedURLException;
/*      */ import java.net.URI;
/*      */ import java.net.URISyntaxException;
/*      */ import java.net.URL;
/*      */ import java.nio.charset.Charset;
/*      */ import java.security.DigestInputStream;
/*      */ import java.security.MessageDigest;
/*      */ import java.security.NoSuchAlgorithmException;
/*      */ import java.text.DateFormat;
/*      */ import java.text.SimpleDateFormat;
/*      */ import java.util.AbstractCollection;
/*      */ import java.util.AbstractList;
/*      */ import java.util.AbstractMap;
/*      */ import java.util.AbstractSet;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Arrays;
/*      */ import java.util.Calendar;
/*      */ import java.util.Collection;
/*      */ import java.util.Collections;
/*      */ import java.util.Comparator;
/*      */ import java.util.Date;
/*      */ import java.util.HashMap;
/*      */ import java.util.HashSet;
/*      */ import java.util.Iterator;
/*      */ import java.util.LinkedHashMap;
/*      */ import java.util.LinkedList;
/*      */ import java.util.List;
/*      */ import java.util.ListIterator;
/*      */ import java.util.Locale;
/*      */ import java.util.Map;
/*      */ import java.util.Properties;
/*      */ import java.util.Set;
/*      */ import java.util.TimeZone;
/*      */ import java.util.Timer;
/*      */ import java.util.TimerTask;
/*      */ import java.util.concurrent.Callable;
/*      */ import java.util.concurrent.ExecutionException;
/*      */ import java.util.concurrent.ExecutorService;
/*      */ import java.util.concurrent.Executors;
/*      */ import java.util.concurrent.Future;
/*      */ import java.util.concurrent.SynchronousQueue;
/*      */ import java.util.concurrent.ThreadFactory;
/*      */ import java.util.concurrent.ThreadPoolExecutor;
/*      */ import java.util.concurrent.TimeUnit;
/*      */ import java.util.regex.Matcher;
/*      */ import java.util.regex.Pattern;
/*      */ import java.util.zip.Deflater;
/*      */ import java.util.zip.DeflaterOutputStream;
/*      */ import java.util.zip.InflaterInputStream;
/*      */ import java.util.zip.ZipEntry;
/*      */ import java.util.zip.ZipInputStream;
/*      */ import java.util.zip.ZipOutputStream;
/*      */ import javax.servlet.http.HttpServletRequest;
/*      */ import javax.swing.SwingUtilities;
/*      */ import org.apache.log4j.Logger;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class Util
/*      */ {
/*  107 */   private static final Logger log = Logger.getLogger(Util.class);
/*      */   
/*  109 */   public static final Object NULL = new Object();
/*      */   
/*  111 */   private static final DateFormat DATEFORMAT_ISO8601 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  117 */   public static final Comparator COMPARATOR_TOSTRING = new Comparator()
/*      */     {
/*      */       public int compare(Object arg0, Object arg1) {
/*  120 */         return String.valueOf(arg0).compareTo(String.valueOf(arg1));
/*      */       }
/*      */     };
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  131 */   public static final Comparator COMPARATOR_NUMERIC_STRING = new Comparator()
/*      */     {
/*      */       public int compare(Object o1, Object o2) {
/*  134 */         int i1 = Integer.parseInt(String.valueOf(o1));
/*  135 */         int i2 = Integer.parseInt(String.valueOf(o2));
/*  136 */         return i1 - i2;
/*      */       }
/*      */     };
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  145 */   public static final Comparator NATURAL_COMPARATOR = new Comparator()
/*      */     {
/*      */       public int compare(Object o1, Object o2) {
/*  148 */         return ((Comparable<Object>)o1).compareTo(o2);
/*      */       }
/*      */     };
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  156 */   public static final Comparator EXT_NATURAL_COMPARATOR = new Comparator()
/*      */     {
/*      */       public int compare(Object o1, Object o2) {
/*  159 */         return Util.compare(o1, o2);
/*      */       }
/*      */     };
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean collectionEquals(Collection<?> c1, Collection<?> c2) {
/*  180 */     return equals(c1, c2) ? true : ((c1.containsAll(c2) && c2.containsAll(c1)));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean equals(Object obj, Object obj2) {
/*      */     boolean ret;
/*  198 */     if (obj == null || obj2 == null) {
/*  199 */       ret = (obj == obj2);
/*      */     }
/*  201 */     else if (System.identityHashCode(obj) < System.identityHashCode(obj2)) {
/*  202 */       ret = obj.equals(obj2);
/*      */     } else {
/*  204 */       ret = obj2.equals(obj);
/*      */     } 
/*      */     
/*  207 */     return ret;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean equalsNotNull(Object obj, Object obj2) {
/*  222 */     return (obj != null && obj2 != null && obj.equals(obj2));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Object rethrow(Throwable t) throws Exception {
/*  236 */     if (t instanceof Exception)
/*  237 */       throw (Exception)t; 
/*  238 */     if (t instanceof Error) {
/*  239 */       throw (Error)t;
/*      */     }
/*  241 */     throw new Error(t);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static long createRandom(long min, long max) {
/*  255 */     long offset = (long)Math.floor(Math.random() * (max + 1L - min));
/*  256 */     return min + offset;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void sleepRandom(long min, long max) throws InterruptedException {
/*  271 */     Thread.sleep(createRandom(min, max));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static CharSequence getClassName(Class c) {
/*  282 */     if (c == null)
/*  283 */       throw new IllegalArgumentException(); 
/*  284 */     if (c.isArray()) {
/*  285 */       StringBuffer stringBuffer = new StringBuffer(getClassName(c.getComponentType()).toString() + "[]");
/*  286 */       return stringBuffer;
/*      */     } 
/*  288 */     String tmp = c.getName();
/*  289 */     Package p = c.getPackage();
/*  290 */     return (p == null) ? tmp : tmp.substring(p.getName().length() + 1);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static CharSequence getClassName(Object obj) {
/*  302 */     if (obj != null) {
/*  303 */       return getClassName(obj.getClass());
/*      */     }
/*  305 */     return null;
/*      */   }
/*      */   
/*      */   private static final class MyVersionNumber
/*      */     extends AbstractVersionNumber implements Serializable {
/*      */     private static final long serialVersionUID = 1L;
/*      */     private final String[] parts;
/*      */     private final String versionNumber;
/*      */     
/*      */     private MyVersionNumber(String[] parts, String versionNumber) {
/*  315 */       this.parts = parts;
/*  316 */       this.versionNumber = versionNumber;
/*      */     }
/*      */     
/*      */     public String[] getParts() {
/*  320 */       return this.parts;
/*      */     }
/*      */     
/*      */     public String toString() {
/*  324 */       return this.versionNumber;
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   private static final class DurationRI
/*      */     implements Duration
/*      */   {
/*      */     private final int _month;
/*      */     
/*      */     private final int _days;
/*      */     
/*      */     private final int _minutes;
/*      */     
/*      */     private final int _hours;
/*      */     
/*      */     private final int _years;
/*      */     private final long _millis;
/*      */     private final int _seconds;
/*      */     private final int _weeks;
/*      */     
/*      */     private DurationRI(int _years, int _month, int _weeks, int _days, int _hours, int _minutes, int _seconds, long _millis) {
/*  346 */       this._month = _month;
/*  347 */       this._days = _days;
/*  348 */       this._minutes = _minutes;
/*  349 */       this._hours = _hours;
/*  350 */       this._years = _years;
/*  351 */       this._millis = _millis;
/*  352 */       this._seconds = _seconds;
/*  353 */       this._weeks = _weeks;
/*      */     }
/*      */     
/*      */     public int getYears() {
/*  357 */       return this._years;
/*      */     }
/*      */     
/*      */     public int getSeconds() {
/*  361 */       return this._seconds;
/*      */     }
/*      */     
/*      */     public int getMonths() {
/*  365 */       return this._month;
/*      */     }
/*      */     
/*      */     public int getMinutes() {
/*  369 */       return this._minutes;
/*      */     }
/*      */     
/*      */     public long getMillis() {
/*  373 */       return this._millis;
/*      */     }
/*      */     
/*      */     public int getHours() {
/*  377 */       return this._hours;
/*      */     }
/*      */     
/*      */     public int getDays() {
/*  381 */       return this._days;
/*      */     }
/*      */     
/*      */     public long getAsMillis() {
/*  385 */       return Util.resolveDuration(this);
/*      */     }
/*      */     
/*      */     public int getWeeks() {
/*  389 */       return this._weeks;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static final class ReverseComparator
/*      */     implements Comparator
/*      */   {
/*      */     private Comparator backend;
/*      */ 
/*      */ 
/*      */     
/*      */     private ReverseComparator(Comparator backend) {
/*  404 */       this.backend = backend;
/*      */     }
/*      */     
/*      */     public int compare(Object o1, Object o2) {
/*  408 */       return this.backend.compare(o1, o2) * -1;
/*      */     }
/*      */     
/*      */     public Comparator getBackend() {
/*  412 */       return this.backend;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Comparator reverseComparator(Comparator comparator) {
/*  427 */     if (comparator instanceof ReverseComparator) {
/*  428 */       return ((ReverseComparator)comparator).getBackend();
/*      */     }
/*  430 */     return new ReverseComparator(comparator);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int compare(long long1, long long2) {
/*  445 */     long diff = long1 - long2;
/*  446 */     return (diff == 0L) ? 0 : ((diff < 0L) ? -1 : 1);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int compare(boolean b1, boolean b2) {
/*  461 */     return (b1 == b2) ? 0 : (b1 ? -1 : 1);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int compare(Object obj1, Object obj2) {
/*  481 */     if (obj1 == null && obj2 == null)
/*  482 */       return 0; 
/*  483 */     if (obj1 == null && obj2 != null)
/*  484 */       return -1; 
/*  485 */     if (obj1 != null && obj2 == null)
/*  486 */       return 1; 
/*  487 */     if (obj1 instanceof Comparable)
/*  488 */       return ((Comparable<Object>)obj1).compareTo(obj2); 
/*  489 */     if (obj2 instanceof Comparable) {
/*  490 */       return ((Comparable<Object>)obj2).compareTo(obj1);
/*      */     }
/*  492 */     return obj1.hashCode() - obj2.hashCode();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Throwable getRootCause(Throwable t) {
/*  504 */     Throwable ret = t;
/*  505 */     while (ret.getCause() != null) {
/*  506 */       ret = ret.getCause();
/*      */     }
/*  508 */     return ret;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static StringBuffer createRandomWord(long minLength, long maxLength, Alphabet alphabet) {
/*  524 */     StringBuffer retValue = new StringBuffer();
/*  525 */     long length = createRandom(minLength, maxLength);
/*  526 */     List characters = alphabet.getElementList(); long i;
/*  527 */     for (i = 0L; i < length; i++) {
/*  528 */       Object c = characters.get((int)createRandom(0L, (characters.size() - 1)));
/*  529 */       retValue.append(c);
/*      */     } 
/*  531 */     return retValue;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static byte[] createRandomBinary(int minLength, int maxLength) {
/*  544 */     byte[] retValue = new byte[(int)createRandom(minLength, maxLength)];
/*  545 */     for (int i = 0; i < retValue.length; i++) {
/*  546 */       retValue[i] = (byte)(int)createRandom(-128L, 127L);
/*      */     }
/*  548 */     return retValue;
/*      */   }
/*      */   
/*      */   private static long getFactor(String unit) {
/*  552 */     if (unit.startsWith("d"))
/*  553 */       return 24L * getFactor("hours"); 
/*  554 */     if (unit.startsWith("h"))
/*  555 */       return 60L * getFactor("minutes"); 
/*  556 */     if (unit.startsWith("min"))
/*  557 */       return 60L * getFactor("seconds"); 
/*  558 */     if (unit.startsWith("s")) {
/*  559 */       return 1000L;
/*      */     }
/*  561 */     return 1L;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static long parseMillis(String period) {
/*  579 */     long ret = -1L;
/*  580 */     String[] parts = period.split("\\s*,\\s*");
/*  581 */     if (parts.length == 0) {
/*  582 */       throw new IllegalArgumentException();
/*      */     }
/*  584 */     for (int i = 0; i < parts.length; i++) {
/*  585 */       Pattern pattern = Pattern.compile("(\\d+)\\s*(.*)");
/*  586 */       Matcher matcher = pattern.matcher(parts[i]);
/*  587 */       if (matcher.matches()) {
/*  588 */         long tmp = Long.parseLong(matcher.group(1));
/*  589 */         String unit = matcher.group(2).toLowerCase(Locale.ENGLISH);
/*  590 */         tmp *= getFactor(unit);
/*  591 */         if (ret == -1L) {
/*  592 */           ret = 0L;
/*      */         }
/*  594 */         ret += tmp;
/*      */       } else {
/*  596 */         throw new IllegalArgumentException();
/*      */       } 
/*      */     } 
/*      */     
/*  600 */     return ret;
/*      */   }
/*      */ 
/*      */   
/*  604 */   private static ThreadLocal reentranceDetect = new ThreadLocal();
/*      */   
/*  606 */   private static final RuntimeException REENTRANCE = new RuntimeException();
/*      */   
/*      */   public static String toString(Object object) {
/*  609 */     StringBuffer buffer = new StringBuffer();
/*  610 */     Boolean reentered = reentranceDetect.get();
/*  611 */     if (reentered != null && reentered.booleanValue()) {
/*  612 */       throw REENTRANCE;
/*      */     }
/*  614 */     reentranceDetect.set(Boolean.TRUE);
/*      */     try {
/*  616 */       buffer.append(String.valueOf(object));
/*  617 */     } catch (RuntimeException e) {
/*  618 */       if (e != REENTRANCE) {
/*  619 */         throw e;
/*      */       }
/*      */     } 
/*  622 */     reentranceDetect.set(null);
/*      */ 
/*      */     
/*  625 */     if (object != null) {
/*  626 */       StringBuffer idPart = new StringBuffer();
/*  627 */       idPart.append(getClassName(object));
/*  628 */       idPart.append("@");
/*  629 */       idPart.append(Integer.toHexString(System.identityHashCode(object)));
/*      */       
/*  631 */       if (buffer.indexOf(idPart.toString()) == -1) {
/*  632 */         buffer.append(" <<");
/*  633 */         buffer.append(idPart);
/*  634 */         buffer.append(">>");
/*      */       } 
/*      */     } 
/*      */     
/*  638 */     return buffer.toString();
/*      */   }
/*      */ 
/*      */   
/*      */   public static String trim(String string, int length) {
/*  643 */     if (string == null) {
/*  644 */       return null;
/*      */     }
/*  646 */     String ret = string.trim();
/*  647 */     if (length != -1) {
/*  648 */       ret = ret.substring(0, Math.min(ret.length(), length));
/*      */     }
/*  650 */     return ret;
/*      */   }
/*      */ 
/*      */   
/*      */   public static String trim(String string) {
/*  655 */     return trim(string, -1);
/*      */   }
/*      */   
/*      */   public static String shuffle(String org) {
/*  659 */     if (org == null) {
/*  660 */       return null;
/*      */     }
/*  662 */     char[] tmp = org.toCharArray();
/*  663 */     for (int i = 0; i < org.length(); i++) {
/*  664 */       int index1 = (int)createRandom(0L, (org.length() - 1));
/*  665 */       int index2 = index1;
/*  666 */       while (index2 == index1) {
/*  667 */         index2 = (int)createRandom(0L, (org.length() - 1));
/*      */       }
/*  669 */       char rescue = tmp[index1];
/*  670 */       tmp[index1] = tmp[index2];
/*  671 */       tmp[index2] = rescue;
/*      */     } 
/*  673 */     return new String(tmp);
/*      */   }
/*      */ 
/*      */   
/*      */   public static boolean isLower(Comparable comparable, Comparable than) {
/*  678 */     return (compare(comparable, than) < 0);
/*      */   }
/*      */   
/*      */   public static boolean isHigher(Comparable comparable, Comparable than) {
/*  682 */     return isLower(than, comparable);
/*      */   }
/*      */   
/*      */   public static Locale parseLocale(String locale) {
/*  686 */     Locale ret = null;
/*  687 */     if (locale != null) {
/*  688 */       String[] parts = locale.split("_");
/*  689 */       if (parts.length > 2) {
/*  690 */         ret = new Locale(parts[0], parts[1], parts[2]);
/*  691 */       } else if (parts.length > 1) {
/*  692 */         ret = new Locale(parts[0], parts[1], "");
/*  693 */       } else if (parts.length > 0) {
/*  694 */         ret = new Locale(parts[0], "", "");
/*      */       } 
/*      */     } 
/*  697 */     return ret;
/*      */   }
/*      */   
/*      */   public static Locale parseCountry(String country) {
/*  701 */     if (country == null) {
/*  702 */       return null;
/*      */     }
/*  704 */     return new Locale("", country.trim());
/*      */   }
/*      */ 
/*      */   
/*      */   public static Comparator getLocaleComparator(final Locale locale) {
/*  709 */     return new Comparator()
/*      */       {
/*      */         public int compare(Object o1, Object o2) {
/*  712 */           Locale l1 = (Locale)o1;
/*  713 */           Locale l2 = (Locale)o2;
/*      */           
/*  715 */           int ret = l1.getDisplayLanguage(locale).compareTo(l2.getDisplayLanguage(locale));
/*  716 */           if (ret == 0) {
/*  717 */             ret = l1.getDisplayCountry(locale).compareTo(l2.getDisplayCountry(locale));
/*  718 */             if (ret == 0) {
/*  719 */               ret = l1.getDisplayVariant(locale).compareTo(l2.getDisplayVariant(locale));
/*      */             }
/*      */           } 
/*  722 */           return ret;
/*      */         }
/*      */       };
/*      */   }
/*      */ 
/*      */   
/*      */   public static Locale toLanguage(Locale locale) {
/*  729 */     return new Locale(locale.getLanguage(), "", "");
/*      */   }
/*      */   
/*      */   public static Locale toCountry(Locale locale) {
/*  733 */     return new Locale("", locale.getCountry(), "");
/*      */   }
/*      */ 
/*      */   
/*      */   public static boolean contains(String string, String part) {
/*  738 */     return (string != null && string.indexOf(part) != -1);
/*      */   }
/*      */   
/*      */   public static int occurenceCount(String string, String part) {
/*  742 */     int ret = 0;
/*  743 */     int index = -1;
/*  744 */     if (string != null) {
/*  745 */       while ((index = string.indexOf(part, index + 1)) != -1) {
/*  746 */         ret++;
/*      */       }
/*      */     }
/*  749 */     return ret;
/*      */   }
/*      */   
/*      */   public static String replaceOccurence(int occurence, String string, String part, String replacement) {
/*  753 */     int index = -1;
/*  754 */     for (int i = 0; i < occurence; i++) {
/*  755 */       index = string.indexOf(part, index + 1);
/*  756 */       if (index == -1) {
/*  757 */         throw new IllegalStateException("part does not have specified occurence");
/*      */       }
/*      */     } 
/*  760 */     return string.substring(0, index) + replacement + string.substring(index + part.length());
/*      */   }
/*      */ 
/*      */   
/*      */   public static boolean isNullOrEmpty(CharSequence string) {
/*  765 */     return (string == null || string.toString().trim().length() == 0);
/*      */   }
/*      */   
/*      */   public static boolean isNullOrEmpty(Collection collection) {
/*  769 */     return (collection == null || collection.size() == 0);
/*      */   }
/*      */   
/*      */   public static boolean isNullOrEmpty(Map map) {
/*  773 */     return (map == null || map.isEmpty());
/*      */   }
/*      */   
/*      */   public static boolean isNullOrEmpty(Object obj) {
/*  777 */     if (obj != null) {
/*  778 */       if (obj instanceof Collection)
/*  779 */         return isNullOrEmpty((Collection)obj); 
/*  780 */       if (obj.getClass().isArray())
/*  781 */         return (Array.getLength(obj) == 0); 
/*  782 */       if (obj instanceof Map)
/*  783 */         return isNullOrEmpty((Map)obj); 
/*  784 */       if (obj instanceof CharSequence) {
/*  785 */         return isNullOrEmpty((CharSequence)obj);
/*      */       }
/*  787 */       throw new IllegalArgumentException("unsupported argument type");
/*      */     } 
/*      */     
/*  790 */     return true;
/*      */   }
/*      */ 
/*      */   
/*      */   public static Throwable getCause(Throwable t, Class exceptionClass) {
/*  795 */     boolean found = false;
/*      */     
/*  797 */     while (!(found = exceptionClass.isAssignableFrom(t.getClass())) && t.getCause() != null) {
/*  798 */       t = t.getCause();
/*      */     }
/*  800 */     return found ? t : null;
/*      */   }
/*      */   
/*      */   public static boolean hasCause(Throwable t, Class exceptionClass) {
/*  804 */     return (getCause(t, exceptionClass) != null);
/*      */   }
/*      */   
/*      */   public static boolean contains_IgnoreCase(Collection c, String string) {
/*  808 */     boolean ret = false;
/*  809 */     if (!isNullOrEmpty(c)) {
/*  810 */       for (Iterator<String> iter = c.iterator(); iter.hasNext() && !ret; ) {
/*  811 */         String str = iter.next();
/*  812 */         if (str == null) {
/*  813 */           ret = (string == null); continue;
/*      */         } 
/*  815 */         ret = str.equalsIgnoreCase(string);
/*      */       } 
/*      */     }
/*      */     
/*  819 */     return ret;
/*      */   }
/*      */   
/*      */   public static byte[] parseBytes(String hexString) {
/*  823 */     if (hexString == null) {
/*  824 */       return null;
/*      */     }
/*  826 */     hexString = hexString.trim().toUpperCase();
/*  827 */     if (hexString.length() == 0)
/*  828 */       return new byte[0]; 
/*  829 */     if (hexString.length() % 2 != 0) {
/*  830 */       throw new IllegalArgumentException();
/*      */     }
/*  832 */     byte[] ret = new byte[hexString.length() / 2];
/*  833 */     for (int i = 0; i < ret.length; i++) {
/*  834 */       ret[i] = (byte)Integer.parseInt(hexString.substring(i * 2, i * 2 + 2), 16);
/*      */     }
/*  836 */     return ret;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static String toHexString(byte[] bytes) {
/*  842 */     if (bytes == null)
/*  843 */       return null; 
/*  844 */     if (bytes.length == 0) {
/*  845 */       return "";
/*      */     }
/*  847 */     StringBuffer buffer = StringBufferPool.getThreadInstance().get(bytes.length * 2);
/*      */     
/*      */     try {
/*  850 */       StringBuffer tmp = StringBufferPool.getThreadInstance().get();
/*      */       try {
/*  852 */         for (int i = 0; i < bytes.length; i++) {
/*  853 */           tmp.append("00");
/*  854 */           tmp.append(Integer.toHexString(bytes[i]));
/*  855 */           buffer.append(tmp.substring(tmp.length() - 2).toUpperCase());
/*  856 */           tmp.setLength(0);
/*      */         } 
/*      */       } finally {
/*  859 */         StringBufferPool.getThreadInstance().free(tmp);
/*      */       } 
/*  861 */       return buffer.toString();
/*      */     } finally {
/*  863 */       StringBufferPool.getThreadInstance().free(buffer);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public static int indexOf(Object[] array, Object obj) {
/*  869 */     int ret = -1;
/*  870 */     for (int i = 0; i < array.length && ret == -1; i++) {
/*  871 */       if (array[i].equals(obj)) {
/*  872 */         ret = i;
/*      */       }
/*      */     } 
/*  875 */     return ret;
/*      */   }
/*      */ 
/*      */   
/*  879 */   private static Pattern PATTERN_WHITESPACE = Pattern.compile("\\s+");
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static ThreadLocal context;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static String normalize(String string) {
/*  891 */     if (string == null) {
/*  892 */       return null;
/*      */     }
/*  894 */     String stage1 = string.trim().toLowerCase();
/*  895 */     String[] stage2 = PATTERN_WHITESPACE.split(stage1);
/*  896 */     StringBuffer ret = StringBufferPool.getThreadInstance().get(stage2[0]);
/*      */     try {
/*  898 */       for (int i = 1; i < stage2.length; i++) {
/*  899 */         ret.append(stage2[i]);
/*      */       }
/*  901 */       return ret.toString();
/*      */     } finally {
/*  903 */       StringBufferPool.getThreadInstance().free(ret);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static String normalizeAlphanumeric(String string) {
/*  919 */     if (string == null) {
/*  920 */       return null;
/*      */     }
/*  922 */     StringBuffer ret = new StringBuffer();
/*  923 */     for (int i = 0; i < string.length(); i++) {
/*  924 */       char c = string.charAt(i);
/*  925 */       if (Character.isLetterOrDigit(c)) {
/*  926 */         ret.append(Character.toLowerCase(c));
/*      */       }
/*      */     } 
/*  929 */     return ret.toString();
/*      */   }
/*      */ 
/*      */   
/*      */   public static URI toURI(URL url) {
/*  934 */     if (url == null) {
/*  935 */       return null;
/*      */     }
/*      */     try {
/*  938 */       return url.toURI();
/*  939 */     } catch (URISyntaxException e) {
/*  940 */       throw new RuntimeException(e);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static RuntimeException toRuntimeException(Throwable e) {
/*  947 */     if (e instanceof RuntimeException) {
/*  948 */       return (RuntimeException)e;
/*      */     }
/*  950 */     return new RuntimeException(e);
/*      */   }
/*      */ 
/*      */   
/*      */   public static List parseList(String strList) {
/*  955 */     return parseList(strList, "\\s*,\\s*", null);
/*      */   }
/*      */   
/*      */   public static List parseIntegerList(String strList) {
/*  959 */     return parseList(strList, "\\s*,\\s*", ObjectCreation.TO_INTEGER);
/*      */   }
/*      */   
/*      */   public static interface ObjectCreation<T>
/*      */   {
/*  964 */     public static final ObjectCreation TO_INTEGER = new ObjectCreation()
/*      */       {
/*      */         public Object createObject(String string) {
/*  967 */           if (Util.isNullOrEmpty(string)) {
/*  968 */             return null;
/*      */           }
/*  970 */           return Integer.valueOf(string);
/*      */         }
/*      */       };
/*      */ 
/*      */     
/*      */     T createObject(String param1String);
/*      */   }
/*      */   
/*      */   public static List parseList(String string, String delimiterPattern, ObjectCreation objectCreation) {
/*  979 */     if (string == null || delimiterPattern == null) {
/*  980 */       throw new IllegalArgumentException();
/*      */     }
/*  982 */     if (isNullOrEmpty(string)) {
/*  983 */       return Collections.EMPTY_LIST;
/*      */     }
/*      */     
/*  986 */     List<Object> ret = new LinkedList();
/*  987 */     String[] elements = string.split(delimiterPattern);
/*  988 */     for (int i = 0; i < elements.length; i++) {
/*  989 */       Object element = elements[i];
/*  990 */       if (objectCreation != null) {
/*  991 */         element = objectCreation.createObject(elements[i]);
/*      */       }
/*  993 */       ret.add(element);
/*      */     } 
/*  995 */     return ret;
/*      */   }
/*      */   
/*      */   public static Locale shortenLocale(Locale locale) {
/*  999 */     if (!isNullOrEmpty(locale.getVariant()))
/* 1000 */       return new Locale(locale.getLanguage(), locale.getCountry()); 
/* 1001 */     if (!isNullOrEmpty(locale.getCountry())) {
/* 1002 */       return new Locale(locale.getLanguage());
/*      */     }
/* 1004 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static IContext getThreadContext() {
/*      */     Context context;
/* 1011 */     IContext ret = context.get();
/* 1012 */     if (ret == null) {
/* 1013 */       context = new Context();
/* 1014 */       context.set(context);
/*      */     } 
/* 1016 */     return (IContext)context;
/*      */   }
/*      */   
/* 1019 */   private static Timer timer = null;
/*      */   
/* 1021 */   private static final Object SYNC_TIMER = new Object();
/*      */   
/*      */   public static Timer getTimer() {
/* 1024 */     synchronized (SYNC_TIMER) {
/* 1025 */       if (timer == null) {
/* 1026 */         timer = new Timer("Common Timer", true);
/*      */       }
/* 1028 */       return timer;
/*      */     } 
/*      */   }
/*      */   
/*      */   public static TimerTask createTimerTask(final Runnable runnable) {
/* 1033 */     return new TimerTask()
/*      */       {
/*      */         public void run() {
/*      */           try {
/* 1037 */             Util.executeAsynchronous(Executors.callable(runnable));
/* 1038 */           } catch (Throwable t) {
/* 1039 */             Util.log.error("unable to execute task - exception: ", t);
/*      */           } 
/*      */         }
/*      */       };
/*      */   }
/*      */ 
/*      */   
/*      */   public static void executeOnAWTThread(Runnable runnable, boolean synchronous) {
/* 1047 */     if (synchronous) {
/*      */       
/* 1049 */       if (SwingUtilities.isEventDispatchThread()) {
/* 1050 */         runnable.run();
/*      */       } else {
/*      */         try {
/* 1053 */           SwingUtilities.invokeAndWait(runnable);
/* 1054 */         } catch (InterruptedException e) {
/* 1055 */           Thread.currentThread().interrupt();
/* 1056 */         } catch (InvocationTargetException e) {
/* 1057 */           rethrowUncheckedException(e.getTargetException());
/*      */         } 
/*      */       } 
/*      */     } else {
/* 1061 */       SwingUtilities.invokeLater(runnable);
/*      */     } 
/*      */   }
/*      */   
/*      */   public static String capitalize(String string, Locale locale) {
/* 1066 */     if (string == null || string.trim().length() == 0) {
/* 1067 */       return string;
/*      */     }
/* 1069 */     locale = (locale != null) ? locale : Locale.ENGLISH;
/* 1070 */     StringBuffer buffer = new StringBuffer();
/* 1071 */     buffer.append(string.substring(0, 1).toUpperCase(locale)).append(string.substring(1).toLowerCase(locale));
/* 1072 */     return buffer.toString();
/*      */   }
/*      */ 
/*      */   
/*      */   public static Rectangle getScreenBounds(Component component) {
/* 1077 */     Rectangle ret = component.getBounds();
/* 1078 */     Point p = component.getLocationOnScreen();
/* 1079 */     ret.setLocation(p);
/* 1080 */     return ret;
/*      */   }
/*      */ 
/*      */   
/*      */   public static Thread createAndStartThread(Runnable runnable) {
/* 1085 */     Thread ret = new Thread(runnable);
/* 1086 */     ret.start();
/* 1087 */     return ret;
/*      */   }
/*      */   
/*      */   public static BigInteger[] getSeconds(BigInteger millis) {
/* 1091 */     return millis.divideAndRemainder(BigInteger.valueOf(1000L));
/*      */   }
/*      */   
/*      */   public static BigInteger[] getMinutes(BigInteger millis) {
/* 1095 */     return millis.divideAndRemainder(BigInteger.valueOf(60000L));
/*      */   }
/*      */   
/*      */   public static BigInteger[] getHours(BigInteger millis) {
/* 1099 */     return millis.divideAndRemainder(BigInteger.valueOf(3600000L));
/*      */   }
/*      */   
/*      */   public static BigInteger[] getDays(BigInteger millis) {
/* 1103 */     return millis.divideAndRemainder(BigInteger.valueOf(86400000L));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static BigInteger[] splitTimespan(BigInteger millis) {
/* 1112 */     BigInteger[] ret = new BigInteger[5];
/* 1113 */     BigInteger[] tmp = getDays(millis);
/* 1114 */     ret[0] = tmp[0];
/* 1115 */     tmp = getHours(tmp[1]);
/* 1116 */     ret[1] = tmp[0];
/* 1117 */     tmp = getMinutes(tmp[1]);
/* 1118 */     ret[2] = tmp[0];
/* 1119 */     tmp = getSeconds(tmp[1]);
/* 1120 */     ret[3] = tmp[0];
/* 1121 */     ret[4] = tmp[1];
/* 1122 */     return ret;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void splitTimespan(long millis, SplitTimespanCallback callback) {
/* 1138 */     BigInteger[] splitResult = splitTimespan(BigInteger.valueOf(millis));
/* 1139 */     callback.setDays(splitResult[0]);
/* 1140 */     callback.setHours(splitResult[1]);
/* 1141 */     callback.setMinutes(splitResult[2]);
/* 1142 */     callback.setSeconds(splitResult[3]);
/* 1143 */     callback.setMillis(splitResult[4]);
/*      */   }
/*      */ 
/*      */   
/*      */   public static boolean contains(String string, String part, boolean caseSensitive) {
/* 1148 */     if (string == null)
/* 1149 */       return (part == null); 
/* 1150 */     if (string.length() == 0) {
/* 1151 */       return (part.length() == 0);
/*      */     }
/* 1153 */     if (!caseSensitive) {
/* 1154 */       string = toLowerCase(string);
/* 1155 */       part = toLowerCase(part);
/*      */     } 
/* 1157 */     return (string.indexOf(part) != -1);
/*      */   }
/*      */ 
/*      */   
/*      */   public static void replace(StringBuffer buffer, String search, String replacement) {
/* 1162 */     if (isNullOrEmpty(buffer) || isNullOrEmpty(search)) {
/*      */       return;
/*      */     }
/* 1165 */     replacement = (replacement == null) ? "" : replacement;
/*      */     
/* 1167 */     int index = -1;
/* 1168 */     while ((index = buffer.indexOf(search, Math.max(0, index))) != -1) {
/* 1169 */       buffer.replace(index, index + search.length(), replacement);
/* 1170 */       index += replacement.length();
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public static void replaceIgnoreCase(StringBuffer buffer, String search, String replacement) {
/* 1176 */     if (isNullOrEmpty(buffer) || isNullOrEmpty(search)) {
/*      */       return;
/*      */     }
/* 1179 */     replacement = (replacement == null) ? "" : replacement;
/* 1180 */     StringBuffer duplicate = new StringBuffer(toLowerCase(buffer.toString()));
/* 1181 */     search = toLowerCase(search);
/* 1182 */     int targetIndexCorrection = 0;
/* 1183 */     int index = -1;
/* 1184 */     while ((index = duplicate.indexOf(search, Math.max(0, index))) != -1) {
/* 1185 */       buffer.replace(index + targetIndexCorrection, index + search.length() + targetIndexCorrection, replacement);
/* 1186 */       index += search.length();
/* 1187 */       targetIndexCorrection += replacement.length() - search.length();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static String formatDuration(long duration, String format) {
/* 1201 */     StringBuffer buffer = StringBufferPool.getThreadInstance().get(format);
/*      */     try {
/* 1203 */       BigInteger millis = BigInteger.valueOf(duration);
/* 1204 */       if (contains(format, "${days}", false)) {
/* 1205 */         BigInteger[] tmp = getDays(millis);
/* 1206 */         replace(buffer, "${days}", tmp[0].toString());
/* 1207 */         millis = tmp[1];
/*      */       } 
/* 1209 */       if (contains(format, "${hours}", false)) {
/* 1210 */         BigInteger[] tmp = getHours(millis);
/* 1211 */         replace(buffer, "${hours}", tmp[0].toString());
/* 1212 */         millis = tmp[1];
/*      */       } 
/* 1214 */       if (contains(format, "${minutes}", false)) {
/* 1215 */         BigInteger[] tmp = getMinutes(millis);
/* 1216 */         replace(buffer, "${minutes}", tmp[0].toString());
/* 1217 */         millis = tmp[1];
/*      */       } 
/* 1219 */       if (contains(format, "${seconds}", false)) {
/* 1220 */         BigInteger[] tmp = getSeconds(millis);
/* 1221 */         replace(buffer, "${seconds}", tmp[0].toString());
/* 1222 */         millis = tmp[1];
/*      */       } 
/* 1224 */       if (contains(format, "${millis}", false)) {
/* 1225 */         replace(buffer, "${millis}", millis.toString());
/*      */       }
/* 1227 */       return buffer.toString();
/*      */     } finally {
/* 1229 */       StringBufferPool.getThreadInstance().free(buffer);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public static BigInteger[] getKBytes(BigInteger bytes) {
/* 1235 */     return bytes.divideAndRemainder(BigInteger.valueOf(1000L));
/*      */   }
/*      */   
/*      */   public static BigInteger[] getMBytes(BigInteger bytes) {
/* 1239 */     return bytes.divideAndRemainder(BigInteger.valueOf(1000000L));
/*      */   }
/*      */   
/*      */   public static BigInteger[] getGBytes(BigInteger bytes) {
/* 1243 */     return bytes.divideAndRemainder(BigInteger.valueOf(1000000000L));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static BigInteger[] splitBytes_SIUnits(BigInteger bytes) {
/* 1250 */     BigInteger[] ret = new BigInteger[4];
/* 1251 */     BigInteger[] tmp = getGBytes(bytes);
/* 1252 */     ret[0] = tmp[0];
/* 1253 */     tmp = getMBytes(tmp[1]);
/* 1254 */     ret[1] = tmp[0];
/* 1255 */     tmp = getKBytes(tmp[1]);
/* 1256 */     ret[2] = tmp[0];
/* 1257 */     ret[3] = tmp[1];
/* 1258 */     return ret;
/*      */   }
/*      */   
/*      */   public static BigInteger[] getKiBytes(BigInteger bytes) {
/* 1262 */     return bytes.divideAndRemainder(BigInteger.valueOf(1024L));
/*      */   }
/*      */   
/*      */   public static BigInteger[] getMiBytes(BigInteger bytes) {
/* 1266 */     return bytes.divideAndRemainder(BigInteger.valueOf(1048576L));
/*      */   }
/*      */   
/*      */   public static BigInteger[] getGiBytes(BigInteger bytes) {
/* 1270 */     return bytes.divideAndRemainder(BigInteger.valueOf(1073741824L));
/*      */   }
/*      */   
/*      */   public static String[] append(String[] first, String[] second) {
/* 1274 */     String[] ret = new String[first.length + second.length];
/* 1275 */     System.arraycopy(first, 0, ret, 0, first.length);
/* 1276 */     System.arraycopy(second, 0, ret, first.length, second.length);
/* 1277 */     return ret;
/*      */   }
/*      */   
/* 1280 */   public static final String[] BYTES_UNITS_SI = new String[] { "GB", "MB", "KB", "B" };
/*      */   
/* 1282 */   public static final String[] BYTES_UNITS_BINARY = new String[] { "GiB", "MiB", "KiB", "B" }; public static final int PRECISION_GIGA = 0;
/*      */   public static final int PRECISION_MEGA = 1;
/*      */   public static final int PRECISION_KILO = 2;
/*      */   public static final int PRECISION_BYTES = 3;
/*      */   
/*      */   public static BigInteger[] splitBytes_BinaryUnits(BigInteger bytes) {
/* 1288 */     BigInteger[] ret = new BigInteger[4];
/* 1289 */     BigInteger[] tmp = getGBytes(bytes);
/* 1290 */     ret[0] = tmp[0];
/* 1291 */     tmp = getMBytes(tmp[1]);
/* 1292 */     ret[1] = tmp[0];
/* 1293 */     tmp = getKBytes(tmp[1]);
/* 1294 */     ret[2] = tmp[0];
/* 1295 */     ret[3] = tmp[1];
/* 1296 */     return ret;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static String formatBytes(long bytes, int precision) {
/* 1308 */     precision = Math.min(3, Math.max(0, precision));
/* 1309 */     StringBuffer ret = new StringBuffer();
/* 1310 */     BigInteger[] parts = splitBytes_SIUnits(BigInteger.valueOf(bytes));
/* 1311 */     for (int i = 0; i < precision + 1; i++) {
/* 1312 */       if (!parts[i].equals(BigInteger.ZERO) || (i == precision && ret.length() == 0)) {
/* 1313 */         ret.append(parts[i]).append(BYTES_UNITS_SI[i]).append(", ");
/*      */       }
/*      */     } 
/* 1316 */     ret.delete(ret.length() - 2, ret.length());
/* 1317 */     return ret.toString();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static String formatBytesSI(long _bytes, String format) {
/* 1329 */     StringBuffer buffer = new StringBuffer(format);
/* 1330 */     BigInteger bytes = BigInteger.valueOf(_bytes);
/* 1331 */     if (contains(format, "${giga}", false)) {
/* 1332 */       BigInteger[] tmp = getGBytes(bytes);
/* 1333 */       replace(buffer, "${giga}", tmp[0].toString());
/* 1334 */       bytes = tmp[1];
/*      */     } 
/* 1336 */     if (contains(format, "${mega}", false)) {
/* 1337 */       BigInteger[] tmp = getMBytes(bytes);
/* 1338 */       replace(buffer, "${mega}", tmp[0].toString());
/* 1339 */       bytes = tmp[1];
/*      */     } 
/* 1341 */     if (contains(format, "${kilo}", false)) {
/* 1342 */       BigInteger[] tmp = getKBytes(bytes);
/* 1343 */       replace(buffer, "${kilo}", tmp[0].toString());
/* 1344 */       bytes = tmp[1];
/*      */     } 
/* 1346 */     if (contains(format, "${bytes}", false)) {
/* 1347 */       replace(buffer, "${bytes}", bytes.toString());
/*      */     }
/* 1349 */     return buffer.toString();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Runnable createMemoryMonitor(final StringOutput out) {
/* 1358 */     return new Runnable()
/*      */       {
/*      */         public void run() {
/* 1361 */           Runtime runtime = Runtime.getRuntime();
/*      */           
/* 1363 */           long freeMemory = runtime.freeMemory();
/* 1364 */           long totalMemory = runtime.totalMemory();
/* 1365 */           long usedMemory = totalMemory - freeMemory;
/* 1366 */           long maxMemory = runtime.maxMemory();
/* 1367 */           out.write("memory snapshot - used: " + Util.formatBytes(usedMemory, 2) + ", free: " + Util.formatBytes(freeMemory, 2) + " (total: " + Util.formatBytes(totalMemory, 1) + ", max: " + Util.formatBytes(maxMemory, 1) + ")");
/*      */         }
/*      */       };
/*      */   }
/*      */ 
/*      */   
/*      */   public static Map invertMap(Map map) {
/* 1374 */     Map<Object, Object> ret = new HashMap<Object, Object>(map.size());
/* 1375 */     for (Iterator<Map.Entry> iter = map.entrySet().iterator(); iter.hasNext(); ) {
/* 1376 */       Map.Entry entry = iter.next();
/* 1377 */       ret.put(entry.getValue(), entry.getKey());
/*      */     } 
/* 1379 */     return ret;
/*      */   }
/*      */   
/*      */   public static Collection ensureCollection(Object object, boolean nullToEmpty) {
/* 1383 */     if (object instanceof Collection)
/* 1384 */       return (Collection)object; 
/* 1385 */     if (object != null)
/* 1386 */       return Collections.singleton(object); 
/* 1387 */     if (nullToEmpty) {
/* 1388 */       return Collections.EMPTY_SET;
/*      */     }
/* 1390 */     return null;
/*      */   }
/*      */ 
/*      */   
/*      */   public static Set ensureSet(Object object, boolean nullToEmpty) {
/* 1395 */     if (object instanceof Set)
/* 1396 */       return (Set)object; 
/* 1397 */     if (object != null)
/* 1398 */       return Collections.singleton(object); 
/* 1399 */     if (nullToEmpty) {
/* 1400 */       return Collections.EMPTY_SET;
/*      */     }
/* 1402 */     return null;
/*      */   }
/*      */ 
/*      */   
/*      */   public static List ensureList(Object object, boolean nullToEmpty) {
/* 1407 */     if (object instanceof List)
/* 1408 */       return (List)object; 
/* 1409 */     if (object != null)
/* 1410 */       return Collections.singletonList(object); 
/* 1411 */     if (nullToEmpty) {
/* 1412 */       return Collections.EMPTY_LIST;
/*      */     }
/* 1414 */     return null;
/*      */   }
/*      */ 
/*      */   
/*      */   public static Object throwRuntimeException(Exception e) {
/* 1419 */     throw toRuntimeException(e);
/*      */   }
/*      */   
/*      */   public static void extractZIP(File zipFile, File destDir) throws FileNotFoundException, IOException {
/* 1423 */     if (destDir == null) {
/* 1424 */       destDir = zipFile.getParentFile();
/*      */     }
/* 1426 */     if (!destDir.exists() && 
/* 1427 */       !destDir.mkdirs()) {
/* 1428 */       throw new IOException("unable to create destination directory");
/*      */     }
/*      */ 
/*      */     
/* 1432 */     ZipInputStream zis = new ZipInputStream(new BufferedInputStream(new FileInputStream(zipFile)));
/*      */     try {
/* 1434 */       ZipEntry entry = null;
/* 1435 */       while ((entry = zis.getNextEntry()) != null) {
/* 1436 */         File targetFile = new File(destDir, entry.getName());
/* 1437 */         if (!entry.isDirectory()) {
/* 1438 */           if (!targetFile.getParentFile().exists() && !targetFile.getParentFile().mkdirs()) {
/* 1439 */             throw new IOException("unable to create dir: " + String.valueOf(targetFile.getParentFile()));
/*      */           }
/* 1441 */           OutputStream os = new BufferedOutputStream(new FileOutputStream(targetFile));
/*      */           try {
/* 1443 */             StreamUtil.transfer(zis, os);
/*      */           } finally {
/* 1445 */             os.close();
/*      */           }
/*      */         
/* 1448 */         } else if (!targetFile.exists() && !targetFile.mkdirs()) {
/* 1449 */           throw new IOException("unable to create dir: " + String.valueOf(targetFile));
/*      */         } 
/*      */ 
/*      */         
/* 1453 */         zis.closeEntry();
/*      */       } 
/*      */     } finally {
/*      */       
/* 1457 */       zis.close();
/*      */     } 
/*      */   }
/*      */   
/*      */   public static boolean onlyDigits(String string) {
/* 1462 */     boolean ret = true;
/* 1463 */     for (int i = 0; i < string.length() && ret; i++) {
/* 1464 */       ret = Character.isDigit(string.charAt(i));
/*      */     }
/* 1466 */     return ret;
/*      */   }
/*      */ 
/*      */   
/*      */   public static VersionNumber parseVersionNumber(String versionNumber) {
/* 1471 */     if (versionNumber != null) {
/* 1472 */       String[] parts = versionNumber.trim().split("[\\-\\_\\.]");
/*      */       
/* 1474 */       return new MyVersionNumber(parts, versionNumber);
/*      */     } 
/* 1476 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static class VersionNumberComparator
/*      */     implements Comparator
/*      */   {
/*      */     private Callback callback;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public VersionNumberComparator(Callback callback) {
/* 1500 */       this.callback = callback;
/*      */     }
/*      */     
/*      */     public int compare(Object o1, Object o2) {
/* 1504 */       int ret = 0;
/* 1505 */       String[] parts1 = ((VersionNumber)o1).getParts();
/* 1506 */       String[] parts2 = ((VersionNumber)o2).getParts();
/* 1507 */       for (int i = 0; i < Math.min(parts1.length, parts2.length); i++) {
/* 1508 */         ret = this.callback.getComparator(i).compare(parts1[i], parts2[i]);
/*      */       }
/* 1510 */       if (ret == 0) {
/* 1511 */         ret = parts1.length - parts2.length;
/*      */       }
/* 1513 */       return ret;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public static interface Callback
/*      */     {
/*      */       Comparator getComparator(int param2Int);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/* 1525 */   public static final Pattern PATTERN_REPLACER = Pattern.compile("\\{(.*?)\\}");
/*      */   
/* 1527 */   public static final Pattern PATTERN_VARIABLE = Pattern.compile("\\$\\{(.*?)\\}");
/*      */   
/*      */   public static void replace(StringBuffer buffer, Pattern searchPattern, ReplacementCallback replacementCallback) {
/* 1530 */     final Matcher m = searchPattern.matcher(buffer);
/* 1531 */     ReplacementCallback.MatcherCallback matcherCallback = new ReplacementCallback.MatcherCallback()
/*      */       {
/*      */         public String getGroup(int group) {
/* 1534 */           return m.group(group);
/*      */         }
/*      */       };
/*      */     
/* 1538 */     int startIndex = 0;
/* 1539 */     while (m.find(startIndex)) {
/* 1540 */       CharSequence match = buffer.subSequence(m.start(), m.end());
/* 1541 */       CharSequence replacement = "";
/* 1542 */       if (replacementCallback != null) {
/* 1543 */         replacement = replacementCallback.getReplacement(match, matcherCallback);
/* 1544 */         replacement = (replacement != null) ? replacement : "";
/*      */       } 
/* 1546 */       buffer.replace(m.start(), m.end(), replacement.toString());
/* 1547 */       startIndex = m.end() + replacement.length() - match.length();
/*      */     } 
/*      */   }
/*      */   
/*      */   public static void delete(StringBuffer buffer, Pattern searchPattern) {
/* 1552 */     replace(buffer, searchPattern, (ReplacementCallback)null);
/*      */   }
/*      */   
/*      */   public static boolean equalStreams(InputStream is, InputStream is2, OutputStream pushbackStream) throws IOException {
/* 1556 */     if (pushbackStream == null) {
/* 1557 */       pushbackStream = new OutputStream()
/*      */         {
/*      */           public void write(int b) throws IOException {}
/*      */         };
/*      */     }
/*      */ 
/*      */     
/* 1564 */     int current = -1;
/* 1565 */     while ((current = is.read()) == is2.read()) {
/* 1566 */       if (current == -1) {
/* 1567 */         pushbackStream.flush();
/* 1568 */         return true;
/*      */       } 
/* 1570 */       pushbackStream.write(current);
/*      */     } 
/*      */     
/* 1573 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static final class StreamMetaRI
/*      */     implements StreamMeta
/*      */   {
/*      */     private byte[] hash;
/*      */ 
/*      */     
/*      */     private BigInteger size;
/*      */ 
/*      */     
/*      */     public StreamMetaRI(byte[] hash, BigInteger size) {
/* 1588 */       this.hash = hash;
/* 1589 */       this.size = size;
/*      */     }
/*      */     
/*      */     public BigInteger getSize() {
/* 1593 */       return this.size;
/*      */     }
/*      */     
/*      */     public byte[] getHash() {
/* 1597 */       return this.hash;
/*      */     }
/*      */     
/*      */     public int hashCode() {
/* 1601 */       int ret = Util.StreamMeta.class.hashCode();
/* 1602 */       ret = HashCalc.addHashCode(ret, this.hash);
/* 1603 */       ret = HashCalc.addHashCode(ret, this.size);
/* 1604 */       return ret;
/*      */     }
/*      */     
/*      */     public boolean equals(Object obj) {
/* 1608 */       if (this == obj)
/* 1609 */         return true; 
/* 1610 */       if (obj instanceof Util.StreamMeta) {
/* 1611 */         Util.StreamMeta other = (Util.StreamMeta)obj;
/* 1612 */         boolean ret = Arrays.equals(this.hash, other.getHash());
/* 1613 */         ret = (ret && this.size.equals(other.getSize()));
/* 1614 */         return ret;
/*      */       } 
/* 1616 */       return false;
/*      */     }
/*      */   }
/*      */   
/*      */   public static StreamMeta getStreamMeta(InputStream is) throws IOException {
/*      */     MessageDigest digest;
/* 1622 */     Counter counter = new Counter();
/*      */     
/*      */     try {
/* 1625 */       digest = MessageDigest.getInstance("MD5");
/* 1626 */     } catch (NoSuchAlgorithmException e) {
/* 1627 */       throw new RuntimeException(e);
/*      */     } 
/*      */     
/* 1630 */     InputStreamByteCount inputStreamByteCount = new InputStreamByteCount(is, counter);
/* 1631 */     DigestInputStream digestInputStream = new DigestInputStream((InputStream)inputStreamByteCount, digest);
/* 1632 */     BufferedInputStream bufferedInputStream = new BufferedInputStream(digestInputStream);
/*      */     
/* 1634 */     byte[] buffer = new byte[102400];
/* 1635 */     while (bufferedInputStream.read(buffer) != -1);
/*      */ 
/*      */     
/* 1638 */     return new StreamMetaRI(digest.digest(), counter.getCount());
/*      */   }
/*      */   
/*      */   public static File createTmpFile() throws IOException {
/* 1642 */     File ret = File.createTempFile("jtf", null);
/* 1643 */     ret.deleteOnExit();
/* 1644 */     return ret;
/*      */   }
/*      */   
/*      */   public static VersionNumber normalizeVersionNumber(VersionNumber versionNumber, int partCount) {
/* 1648 */     String[] parts = versionNumber.getParts();
/* 1649 */     if (parts.length == partCount) {
/* 1650 */       return versionNumber;
/*      */     }
/* 1652 */     final String[] newParts = new String[partCount];
/* 1653 */     System.arraycopy(parts, 0, newParts, 0, Math.min(partCount, parts.length));
/* 1654 */     for (int i = partCount - parts.length; i > 0; i--) {
/* 1655 */       newParts[parts.length - 1 + i] = "0";
/*      */     }
/* 1657 */     return new AbstractVersionNumber()
/*      */       {
/*      */         public String[] getParts() {
/* 1660 */           return newParts;
/*      */         }
/*      */       };
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static Set normalizeVersionNumbers(Collection versionNumbers) {
/* 1668 */     Set<VersionNumber> ret = new HashSet();
/* 1669 */     int maxSize = 0; Iterator<VersionNumber> iter;
/* 1670 */     for (iter = versionNumbers.iterator(); iter.hasNext();) {
/* 1671 */       maxSize = Math.max(maxSize, (((VersionNumber)iter.next()).getParts()).length);
/*      */     }
/*      */     
/* 1674 */     for (iter = versionNumbers.iterator(); iter.hasNext();) {
/* 1675 */       ret.add(normalizeVersionNumber(iter.next(), maxSize));
/*      */     }
/* 1677 */     return ret;
/*      */   }
/*      */ 
/*      */   
/*      */   public static File createTmpDir(String name) throws IOException {
/* 1682 */     String _tmpDir = System.getProperty("java.io.tmpdir");
/* 1683 */     File ret = new File(_tmpDir, name);
/* 1684 */     if (!ret.mkdir() && !ret.exists()) {
/* 1685 */       throw new IOException("unable to create directory " + ret);
/*      */     }
/* 1687 */     return ret;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void rethrowRuntimeException(Exception e) {
/* 1699 */     if (e instanceof RuntimeException) {
/* 1700 */       throw (RuntimeException)e;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void rethrowIOException(Exception e) throws IOException {
/* 1713 */     if (e instanceof IOException) {
/* 1714 */       throw (IOException)e;
/*      */     }
/*      */   }
/*      */   
/*      */   public static String toExternal(Object obj) throws IOException {
/* 1719 */     StringWriter sw = new StringWriter();
/* 1720 */     Base64EncodingStream base64EncodingStream = new Base64EncodingStream(sw);
/* 1721 */     Deflater deflater = new Deflater(9);
/*      */     try {
/* 1723 */       DeflaterOutputStream deflaterOutputStream = new DeflaterOutputStream((OutputStream)base64EncodingStream, deflater);
/* 1724 */       ObjectOutputStream oos = new ObjectOutputStream(deflaterOutputStream);
/*      */       try {
/* 1726 */         oos.writeObject(obj);
/*      */       } finally {
/* 1728 */         oos.close();
/*      */       } 
/*      */     } finally {
/* 1731 */       deflater.end();
/*      */     } 
/* 1733 */     return sw.toString();
/*      */   }
/*      */   
/*      */   public static Object fromExternal(String data) throws IOException, ClassNotFoundException {
/* 1737 */     StringReader sr = new StringReader(data);
/* 1738 */     Base64DecodingStream base64DecodingStream = new Base64DecodingStream(sr);
/* 1739 */     InflaterInputStream inflaterInputStream = new InflaterInputStream((InputStream)base64DecodingStream);
/* 1740 */     ObjectInputStream ois = new ObjectInputStream(inflaterInputStream);
/*      */     try {
/* 1742 */       return ois.readObject();
/*      */     } finally {
/* 1744 */       ois.close();
/*      */     } 
/*      */   }
/*      */   
/*      */   public static InputStream getLocalResource(Class clazz, String localResourceName) {
/* 1749 */     String fullName = clazz.getPackage().getName().replace('.', '/') + "/" + localResourceName;
/* 1750 */     return clazz.getClassLoader().getResourceAsStream(fullName);
/*      */   }
/*      */   
/*      */   public static byte[] getUTF8Bytes(String string) {
/* 1754 */     if (string == null) {
/* 1755 */       return null;
/*      */     }
/*      */     try {
/* 1758 */       return string.getBytes("utf-8");
/* 1759 */     } catch (UnsupportedEncodingException e) {
/* 1760 */       throw new RuntimeException(e);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean isAlive(Thread t) {
/* 1767 */     return (t != null && t.isAlive());
/*      */   }
/*      */   
/*      */   public static String formatDate(long date) {
/* 1771 */     synchronized (DATEFORMAT_ISO8601) {
/* 1772 */       return DATEFORMAT_ISO8601.format(new Date(date));
/*      */     } 
/*      */   }
/*      */   
/*      */   public static String toLowerCase(String string) {
/* 1777 */     if (string == null) {
/* 1778 */       return null;
/*      */     }
/* 1780 */     return string.toLowerCase(Locale.ENGLISH);
/*      */   }
/*      */ 
/*      */   
/*      */   public static String toUpperCase(String string) {
/* 1785 */     if (string == null) {
/* 1786 */       return null;
/*      */     }
/* 1788 */     return string.toUpperCase(Locale.ENGLISH);
/*      */   }
/*      */ 
/*      */   
/*      */   public static TimerTask runAt(Runnable runnable, long at) {
/* 1793 */     TimerTask ret = createTimerTask(runnable);
/* 1794 */     getTimer().schedule(ret, new Date(at));
/* 1795 */     return ret;
/*      */   }
/*      */   
/*      */   public static TimerTask runAfter(Runnable runnable, long delay) {
/* 1799 */     TimerTask ret = createTimerTask(runnable);
/* 1800 */     getTimer().schedule(ret, delay);
/* 1801 */     return ret;
/*      */   }
/*      */   
/*      */   public static void ensureNotAWTThread() {
/* 1805 */     if (SwingUtilities.isEventDispatchThread()) {
/* 1806 */       throw new IllegalStateException("current thread MUST NOT be the AWT thread!!!!");
/*      */     }
/*      */   }
/*      */   
/*      */   public static void ensureAWTThread() {
/* 1811 */     if (!SwingUtilities.isEventDispatchThread()) {
/* 1812 */       throw new IllegalStateException("current thread MUST be the AWT thread!!!!");
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1820 */   private static final Notification MODIFICATION = new Notification()
/*      */     {
/*      */       public void notify(Object observer) {
/* 1823 */         ((Util.ModificationObserver)observer).onModification();
/*      */       }
/*      */     };
/*      */ 
/*      */   
/*      */   public static Set observeSet(final Set set, ModificationObserver observer) {
/* 1829 */     final ObservableSupport observableSupport = new ObservableSupport();
/* 1830 */     observableSupport.addObserver(observer);
/*      */     
/* 1832 */     return new AbstractSet()
/*      */       {
/*      */         public boolean add(Object o) {
/* 1835 */           boolean ret = set.add(o);
/* 1836 */           if (ret) {
/* 1837 */             observableSupport.notifyObservers(Util.MODIFICATION);
/*      */           }
/* 1839 */           return ret;
/*      */         }
/*      */         
/*      */         public int size() {
/* 1843 */           return set.size();
/*      */         }
/*      */         
/*      */         public Iterator iterator() {
/* 1847 */           final Iterator orgIter = set.iterator();
/* 1848 */           return new Iterator()
/*      */             {
/*      */               public void remove() {
/* 1851 */                 orgIter.remove();
/* 1852 */                 observableSupport.notifyObservers(Util.MODIFICATION);
/*      */               }
/*      */               
/*      */               public Object next() {
/* 1856 */                 return orgIter.next();
/*      */               }
/*      */               
/*      */               public boolean hasNext() {
/* 1860 */                 return orgIter.hasNext();
/*      */               }
/*      */             };
/*      */         }
/*      */       };
/*      */   }
/*      */ 
/*      */   
/*      */   public static Map observeMap(final Map map, final ModificationObserver observer) {
/* 1869 */     final ObservableSupport observableSupport = new ObservableSupport();
/* 1870 */     observableSupport.addObserver(observer);
/*      */     
/* 1872 */     return new AbstractMap<Object, Object>()
/*      */       {
/*      */         public Object put(Object key, Object value) {
/* 1875 */           Object ret = map.put(key, value);
/* 1876 */           if (!Util.equals(ret, value)) {
/* 1877 */             observableSupport.notifyObservers(Util.MODIFICATION);
/*      */           }
/* 1879 */           return ret;
/*      */         }
/*      */         
/*      */         public Set entrySet() {
/* 1883 */           return Util.observeSet(map.entrySet(), observer);
/*      */         }
/*      */       };
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean createRandomBoolean() {
/* 1891 */     return (createRandom(0L, 1L) == 1L);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static final class FileCollector
/*      */     implements FileVisitor
/*      */   {
/* 1907 */     private List files = new LinkedList();
/*      */     
/*      */     public static final int FILES_ONLY = 0;
/*      */     
/*      */     public static final int FILES_AND_DIRECTORIES = 1;
/*      */     
/*      */     public static final int DIRECTORIES_ONLY = 2;
/*      */     
/*      */     private int mode;
/*      */     
/*      */     public FileCollector(int mode) {
/* 1918 */       this.mode = mode;
/*      */     }
/*      */     
/*      */     public FileCollector() {
/* 1922 */       this(1);
/*      */     }
/*      */     
/*      */     public boolean onVisit(File file) {
/* 1926 */       if (this.mode == 1 || (this.mode == 0 && file.isFile()) || (this.mode == 2 && file.isDirectory())) {
/* 1927 */         this.files.add(file);
/*      */       }
/* 1929 */       return true;
/*      */     }
/*      */     
/*      */     public List getFiles() {
/* 1933 */       return this.files;
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public static final class CopyVisitor
/*      */     implements FileVisitor
/*      */   {
/*      */     private File rootDir;
/*      */     private File targetDir;
/*      */     
/*      */     public CopyVisitor(File rootDir, File targetDir) {
/* 1945 */       this.targetDir = targetDir;
/* 1946 */       this.rootDir = rootDir;
/*      */     }
/*      */     
/*      */     public boolean onVisit(File file) {
/*      */       try {
/*      */         File targetFile;
/* 1952 */         if (this.rootDir.isFile() && file.equals(this.rootDir)) {
/* 1953 */           targetFile = new File(this.targetDir, file.getName());
/*      */         } else {
/* 1955 */           URI relativeURI = Util.relativize(file, this.rootDir);
/* 1956 */           targetFile = new File(this.targetDir, relativeURI.getPath());
/*      */         } 
/* 1958 */         if (Util.log.isDebugEnabled()) {
/* 1959 */           Util.log.debug("copying " + file + " to " + targetFile);
/*      */         }
/*      */         
/* 1962 */         if (file.isDirectory()) {
/* 1963 */           targetFile.mkdirs();
/*      */         } else {
/* 1965 */           targetFile.getParentFile().mkdirs();
/* 1966 */           InputStream fis = new BufferedInputStream(new FileInputStream(file));
/*      */           try {
/* 1968 */             OutputStream fos = new BufferedOutputStream(new FileOutputStream(targetFile));
/*      */             try {
/* 1970 */               StreamUtil.transfer(fis, fos);
/*      */             } finally {
/*      */               
/* 1973 */               fos.close();
/*      */             } 
/*      */           } finally {
/* 1976 */             fis.close();
/*      */           } 
/*      */         } 
/*      */ 
/*      */         
/* 1981 */         return true;
/* 1982 */       } catch (Exception e) {
/* 1983 */         throw new RuntimeException(e);
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public static final class MatchingCollector
/*      */     implements FileVisitor
/*      */   {
/*      */     private File rootDir;
/*      */     private Iterable<Pattern> patterns;
/* 1994 */     private List files = new LinkedList();
/*      */     
/*      */     public MatchingCollector(File rootDir, Pattern relativeNamePattern) {
/* 1997 */       this.rootDir = rootDir;
/* 1998 */       this.patterns = Collections.singleton(relativeNamePattern);
/*      */     }
/*      */     
/*      */     public MatchingCollector(File rootDir, Iterable<Pattern> relativeNamePatterns) {
/* 2002 */       this.rootDir = rootDir;
/* 2003 */       this.patterns = relativeNamePatterns;
/*      */     }
/*      */     
/*      */     public List getFiles() {
/* 2007 */       return this.files;
/*      */     }
/*      */     
/*      */     private String getRelativeName(File file) {
/* 2011 */       URI relativeURI = Util.relativize(file, this.rootDir);
/* 2012 */       return relativeURI.getPath();
/*      */     }
/*      */     
/*      */     public boolean onVisit(File file) {
/* 2016 */       Util.log.debug("examinating " + String.valueOf(file) + "...");
/* 2017 */       String relativeName = getRelativeName(file);
/* 2018 */       Util.log.debug("...relative name : " + relativeName);
/*      */       
/* 2020 */       for (Pattern pattern : this.patterns) {
/* 2021 */         Matcher matcher = pattern.matcher(relativeName);
/* 2022 */         if (matcher.matches()) {
/* 2023 */           Util.log.debug("...match");
/* 2024 */           this.files.add(file);
/*      */           break;
/*      */         } 
/* 2027 */         Util.log.debug("...no match");
/*      */       } 
/*      */       
/* 2030 */       return true;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void visitFiles(File directory, FileVisitor fileVisitor) {
/* 2044 */     if (fileVisitor != null && 
/* 2045 */       fileVisitor.onVisit(directory) && directory.isDirectory()) {
/* 2046 */       File[] childs = directory.listFiles();
/* 2047 */       if (!isNullOrEmpty(childs)) {
/* 2048 */         for (int i = 0; i < childs.length; i++) {
/* 2049 */           visitFiles(childs[i], fileVisitor);
/*      */         }
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static URI relativize(File file, File root) {
/* 2058 */     URI rootURI = root.toURI();
/* 2059 */     return rootURI.relativize(file.toURI());
/*      */   }
/*      */   
/*      */   public static void createZIPArchive(OutputStream os, File dir) throws IOException {
/* 2063 */     FileCollector collector = new FileCollector(1);
/* 2064 */     visitFiles(dir, collector);
/* 2065 */     createZIPArchive(os, collector.getFiles(), dir);
/*      */   }
/*      */   
/*      */   public static void createZIPArchive(OutputStream os, List<File> files, File entryRoot) throws IOException {
/* 2069 */     createZIPArchive(os, files.iterator(), entryRoot);
/*      */   }
/*      */   
/*      */   public static void createZIPArchive(OutputStream os, Iterator<File> iterator, File entryRoot) throws IOException {
/* 2073 */     ZipOutputStream zos = new ZipOutputStream(os);
/*      */     try {
/* 2075 */       while (iterator.hasNext()) {
/* 2076 */         File file = iterator.next();
/* 2077 */         if (!file.equals(entryRoot)) {
/*      */           String entryName;
/* 2079 */           if (entryRoot != null) {
/* 2080 */             entryName = relativize(file, entryRoot).getPath();
/*      */           } else {
/* 2082 */             entryName = file.getPath();
/*      */           } 
/* 2084 */           zos.putNextEntry(new ZipEntry(entryName));
/* 2085 */           if (file.isFile()) {
/* 2086 */             InputStream fis = new BufferedInputStream(new FileInputStream(file));
/*      */             try {
/* 2088 */               StreamUtil.transfer(fis, zos);
/*      */             } finally {
/* 2090 */               fis.close();
/*      */             } 
/*      */           } 
/* 2093 */           zos.flush();
/*      */         } 
/*      */       } 
/*      */     } finally {
/*      */       
/* 2098 */       zos.finish();
/*      */     } 
/*      */   }
/*      */   
/*      */   public static boolean deleteDir(File dir) {
/* 2103 */     boolean ret = true;
/* 2104 */     if (dir == null || !dir.exists()) {
/* 2105 */       ret = false;
/*      */     } else {
/* 2107 */       FileCollector collector = new FileCollector();
/* 2108 */       visitFiles(dir, collector);
/* 2109 */       List<?> files = collector.getFiles();
/* 2110 */       Collections.sort(files, reverseComparator(NATURAL_COMPARATOR));
/*      */       
/* 2112 */       for (Iterator<?> iter = files.iterator(); iter.hasNext(); ) {
/* 2113 */         File file = (File)iter.next();
/* 2114 */         ret = (ret && file.delete());
/*      */       } 
/*      */     } 
/* 2117 */     return ret;
/*      */   }
/*      */ 
/*      */   
/*      */   public static long[] splitDateAndTime(long date) {
/* 2122 */     Calendar c = Calendar.getInstance();
/* 2123 */     c.setTimeInMillis(date);
/* 2124 */     c.set(11, 0);
/* 2125 */     c.set(12, 0);
/* 2126 */     c.set(13, 0);
/* 2127 */     c.set(14, 0);
/* 2128 */     long base = c.getTimeInMillis();
/* 2129 */     return new long[] { base, date - base };
/*      */   }
/*      */   
/*      */   public static void sortLines(File textFile) throws IOException {
/* 2133 */     List<String> lines = new LinkedList();
/*      */     
/* 2135 */     LineNumberReader lnr = new LineNumberReader(new InputStreamReader(new BufferedInputStream(new FileInputStream(textFile)), Charset.forName("utf-8")));
/*      */     try {
/* 2137 */       String line = null;
/* 2138 */       while ((line = lnr.readLine()) != null) {
/* 2139 */         lines.add(line);
/*      */       }
/*      */     } finally {
/* 2142 */       lnr.close();
/*      */     } 
/* 2144 */     Collections.sort(lines);
/*      */     
/* 2146 */     File tmpFile = File.createTempFile("jtf", null);
/* 2147 */     PrintWriter pw = new PrintWriter(new OutputStreamWriter(new BufferedOutputStream(new FileOutputStream(tmpFile)), Charset.forName("utf-8")));
/*      */     try {
/* 2149 */       for (Iterator<String> iter = lines.iterator(); iter.hasNext(); ) {
/* 2150 */         String line = iter.next();
/* 2151 */         pw.println(line);
/*      */       } 
/*      */     } finally {
/* 2154 */       pw.close();
/*      */     } 
/*      */     
/* 2157 */     File bak = new File(textFile.getParent(), textFile.getName() + ".bak");
/* 2158 */     if (!textFile.renameTo(bak)) {
/* 2159 */       throw new IOException("failed to rename " + String.valueOf(textFile) + " to " + String.valueOf(bak));
/*      */     }
/* 2161 */     if (!tmpFile.renameTo(textFile)) {
/* 2162 */       throw new IOException("failed to rename " + String.valueOf(tmpFile) + " to " + String.valueOf(textFile));
/*      */     }
/*      */     
/* 2165 */     bak.delete();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void toProxyList(List list, ProxyRetrieval proxyRetrieval) {
/* 2174 */     for (ListIterator<Object> li = list.listIterator(); li.hasNext();) {
/* 2175 */       li.set(proxyRetrieval.getProxy(li.next()));
/*      */     }
/*      */   }
/*      */   
/*      */   public static int calcSimilarity(String string1, String string2) {
/* 2180 */     int total = 0;
/* 2181 */     for (int i = 2; i < 6; i++) {
/* 2182 */       total += calcSimilarity(string1, string2, i) * i;
/* 2183 */       total += calcSimilarity(normalize(string1), normalize(string2), i) * i;
/*      */     } 
/* 2185 */     return total;
/*      */   }
/*      */   
/*      */   public static int calcSimilarity(String string1, String string2, int atomLength) {
/* 2189 */     int hitCount = 0;
/* 2190 */     Set<String> set = new HashSet(); int i;
/* 2191 */     for (i = 0; i < string1.length() + 1 - atomLength; i++) {
/* 2192 */       String atom = string1.substring(i, i + atomLength);
/* 2193 */       set.add(atom);
/*      */     } 
/*      */ 
/*      */     
/* 2197 */     for (i = 0; i < string2.length() + 1 - atomLength; i++) {
/* 2198 */       String atom = string2.substring(i, i + atomLength);
/* 2199 */       if (set.contains(atom)) {
/* 2200 */         hitCount++;
/*      */       }
/*      */     } 
/* 2203 */     return hitCount;
/*      */   }
/*      */   
/*      */   public static String getAbbreviation(String string) {
/* 2207 */     StringBuffer ret = new StringBuffer();
/* 2208 */     if (string != null) {
/* 2209 */       boolean next = true;
/* 2210 */       for (int i = 0; i < string.length(); i++) {
/* 2211 */         if (Character.isLetterOrDigit(string.charAt(i))) {
/* 2212 */           if (next || Character.isUpperCase(string.charAt(i))) {
/* 2213 */             ret.append(string.charAt(i));
/* 2214 */             next = false;
/*      */           } 
/*      */         } else {
/* 2217 */           next = true;
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     
/* 2222 */     return ret.toString();
/*      */   }
/*      */   
/*      */   public static void logTimezone(Logger log) {
/* 2226 */     TimeZone zone = TimeZone.getDefault();
/* 2227 */     long offset = zone.getOffset(System.currentTimeMillis());
/* 2228 */     log.info("current timezone is " + zone.getDisplayName(true, 1, Locale.ENGLISH) + ", offset (to UTC): " + getHours(BigInteger.valueOf(offset))[0] + " hour(s)");
/*      */   }
/*      */   
/* 2231 */   private static final Object[] htmlSimpleReplacements = new Object[] { { "<", "&lt;" }, { ">", "&gt;" }, { "\"", "&quot;" } };
/*      */   
/* 2233 */   private static final Pattern PATTERN_AMPERSAND = Pattern.compile("\\&(?![a-z]+;)", 2);
/*      */   
/*      */   public static void escapeReservedHTMLChars(StringBuffer string) {
/* 2236 */     if (string != null) {
/* 2237 */       for (int i = 0; i < htmlSimpleReplacements.length; i++) {
/* 2238 */         String[] replacement = (String[])htmlSimpleReplacements[i];
/* 2239 */         replace(string, replacement[0], replacement[1]);
/*      */       } 
/* 2241 */       replace(string, PATTERN_AMPERSAND, new ReplacementCallback()
/*      */           {
/*      */             public CharSequence getReplacement(CharSequence match, Util.ReplacementCallback.MatcherCallback matcherCallback) {
/* 2244 */               return "&amp;";
/*      */             }
/*      */           });
/*      */     } 
/*      */   }
/*      */   
/*      */   public static String escapeReservedHTMLChars(String string) {
/* 2251 */     if (!isNullOrEmpty(string)) {
/* 2252 */       StringBuffer ret = StringBufferPool.getThreadInstance().get(string);
/*      */       try {
/* 2254 */         escapeReservedHTMLChars(ret);
/* 2255 */         return ret.toString();
/*      */       } finally {
/* 2257 */         StringBufferPool.getThreadInstance().free(ret);
/*      */       } 
/*      */     } 
/* 2260 */     return string;
/*      */   }
/*      */ 
/*      */   
/*      */   public static void rethrowUncheckedException(Throwable t) {
/* 2265 */     if (t instanceof RuntimeException)
/* 2266 */       throw (RuntimeException)t; 
/* 2267 */     if (t instanceof Error) {
/* 2268 */       throw (Error)t;
/*      */     }
/* 2270 */     throw new Error("unhandled throwable class " + t.getClass(), t);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean startWith(String string, String prefix) {
/* 2286 */     if (string == null) {
/* 2287 */       return (prefix == null);
/*      */     }
/* 2289 */     return string.startsWith(prefix);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean endsWith(String string, String suffix) {
/* 2304 */     if (string == null) {
/* 2305 */       return (suffix == null);
/*      */     }
/* 2307 */     return string.endsWith(suffix);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 2316 */   private static final Pattern PATTERN_ISO8601DURATION = Pattern.compile("(?i)P((\\d+)Y)?((\\d+)M)?((\\d+)W)?((\\d+)D)?(T((\\d+)H)?((\\d+)M)?((\\d+)S)?((\\d+)(MS)?)?)?");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Duration parseDuration(String iso8601Duration) {
/* 2355 */     if (!isNullOrEmpty(iso8601Duration)) {
/* 2356 */       Matcher matcher = PATTERN_ISO8601DURATION.matcher(iso8601Duration);
/* 2357 */       if (matcher.matches()) {
/*      */         long _millis; int _seconds, _minutes, _hours, _days, _weeks, _month, _years;
/* 2359 */         String millis = matcher.group(17);
/* 2360 */         if (!isNullOrEmpty(millis)) {
/* 2361 */           _millis = Long.parseLong(millis);
/*      */         } else {
/* 2363 */           _millis = 0L;
/*      */         } 
/*      */ 
/*      */         
/* 2367 */         String seconds = matcher.group(15);
/* 2368 */         if (!isNullOrEmpty(seconds)) {
/* 2369 */           _seconds = Integer.parseInt(seconds);
/*      */         } else {
/* 2371 */           _seconds = 0;
/*      */         } 
/*      */ 
/*      */         
/* 2375 */         String minutes = matcher.group(13);
/* 2376 */         if (!isNullOrEmpty(minutes)) {
/* 2377 */           _minutes = Integer.parseInt(minutes);
/*      */         } else {
/* 2379 */           _minutes = 0;
/*      */         } 
/*      */ 
/*      */         
/* 2383 */         String hours = matcher.group(11);
/* 2384 */         if (!isNullOrEmpty(hours)) {
/* 2385 */           _hours = Integer.parseInt(hours);
/*      */         } else {
/* 2387 */           _hours = 0;
/*      */         } 
/*      */ 
/*      */         
/* 2391 */         String days = matcher.group(8);
/* 2392 */         if (!isNullOrEmpty(days)) {
/* 2393 */           _days = Integer.parseInt(days);
/*      */         } else {
/* 2395 */           _days = 0;
/*      */         } 
/*      */ 
/*      */         
/* 2399 */         String weeks = matcher.group(6);
/* 2400 */         if (!isNullOrEmpty(weeks)) {
/* 2401 */           _weeks = Integer.parseInt(weeks);
/*      */         } else {
/* 2403 */           _weeks = 0;
/*      */         } 
/*      */ 
/*      */         
/* 2407 */         String months = matcher.group(4);
/* 2408 */         if (!isNullOrEmpty(months)) {
/* 2409 */           _month = Integer.parseInt(months);
/*      */         } else {
/* 2411 */           _month = 0;
/*      */         } 
/*      */ 
/*      */         
/* 2415 */         String years = matcher.group(2);
/* 2416 */         if (!isNullOrEmpty(years)) {
/* 2417 */           _years = Integer.parseInt(years);
/*      */         } else {
/* 2419 */           _years = 0;
/*      */         } 
/*      */         
/* 2422 */         return new DurationRI(_years, _month, _weeks, _days, _hours, _minutes, _seconds, _millis);
/*      */       } 
/*      */       
/* 2425 */       throw new IllegalArgumentException("unknown format");
/*      */     } 
/*      */     
/* 2428 */     throw new IllegalArgumentException("null or empty");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static long resolveDuration(Duration duration) {
/* 2443 */     Calendar c = Calendar.getInstance();
/* 2444 */     long baseTime = c.getTimeInMillis();
/* 2445 */     if (duration.getMillis() != 0L) {
/* 2446 */       c.setTimeInMillis(c.getTimeInMillis() + duration.getMillis());
/*      */     }
/*      */     
/* 2449 */     c.add(13, duration.getSeconds());
/* 2450 */     c.add(12, duration.getMinutes());
/* 2451 */     c.add(10, duration.getHours());
/* 2452 */     c.add(6, duration.getDays());
/* 2453 */     c.add(3, duration.getWeeks());
/* 2454 */     c.add(2, duration.getMonths());
/* 2455 */     c.add(1, duration.getYears());
/*      */     
/* 2457 */     return c.getTimeInMillis() - baseTime;
/*      */   }
/*      */   
/*      */   public static void checkInterruption() throws InterruptedException {
/* 2461 */     if (Thread.interrupted()) {
/* 2462 */       throw new InterruptedException();
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean sleep(long millis) {
/*      */     try {
/* 2475 */       Thread.sleep(millis);
/* 2476 */       return true;
/* 2477 */     } catch (InterruptedException e) {
/* 2478 */       Thread.currentThread().interrupt();
/* 2479 */       return false;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void interrupt(Thread thread) {
/*      */     do {
/* 2493 */       thread.interrupt();
/* 2494 */     } while (thread.isAlive() && sleep(10L));
/*      */   }
/*      */   
/*      */   public static Object prepareCast(Object obj, Class targetType) {
/* 2498 */     if (targetType.isInstance(obj)) {
/* 2499 */       return obj;
/*      */     }
/* 2501 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static StringBuffer toUnicodeEscape(char c) {
/* 2513 */     StringBuffer ret = new StringBuffer("\\u");
/* 2514 */     if (c < '\020') {
/* 2515 */       ret.append("0");
/*      */     }
/* 2517 */     if (c < 'Ā') {
/* 2518 */       ret.append("0");
/*      */     }
/* 2520 */     if (c < 'က') {
/* 2521 */       ret.append("0");
/*      */     }
/* 2523 */     return ret.append(Integer.toHexString(c));
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static interface Exclude
/*      */   {
/* 2530 */     public static final Exclude STD = new Exclude()
/*      */       {
/*      */         public boolean exclude(char c) {
/* 2533 */           return ('\031' < c && c < '');
/*      */         }
/*      */       };
/*      */     
/* 2537 */     public static final Exclude NONE = new Exclude() {
/*      */         public boolean exclude(char c) {
/* 2539 */           return false;
/*      */         }
/*      */       };
/*      */     
/* 2543 */     public static final Exclude LETTER_DIGITS_SPACE = new Exclude() {
/*      */         public boolean exclude(char c) {
/* 2545 */           boolean ret = (c > '/' && c < '@');
/* 2546 */           ret = (ret || (c > '@' && c < '['));
/* 2547 */           ret = (ret || (c > '`' && c < '{'));
/* 2548 */           ret = (ret || c == ' ');
/* 2549 */           return ret;
/*      */         }
/*      */       };
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     boolean exclude(char param1Char);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static StringBuffer toUnicode(String string, Exclude callback) {
/* 2571 */     StringBuffer ret = new StringBuffer(string.length() * 2);
/* 2572 */     for (int i = 0; i < string.length(); i++) {
/* 2573 */       char c = string.charAt(i);
/* 2574 */       if (callback != null && callback.exclude(c)) {
/* 2575 */         ret.append(c);
/*      */       } else {
/* 2577 */         ret.append(toUnicodeEscape(c));
/*      */       } 
/*      */     } 
/* 2580 */     return ret;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static StringBuffer fromUnicode(String string) {
/* 2591 */     StringBuffer ret = new StringBuffer();
/* 2592 */     int index1 = 0;
/* 2593 */     int index2 = -1;
/* 2594 */     while ((index2 = string.indexOf("\\u", index1)) != -1) {
/* 2595 */       ret.append(string.substring(index1, index2));
/* 2596 */       index1 = index2 + 6;
/* 2597 */       ret.append(fromUnicodeEscape(string.substring(index2, index1)));
/*      */     } 
/* 2599 */     ret.append(string.substring(index1));
/* 2600 */     return ret;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static char fromUnicodeEscape(String sequence) {
/* 2613 */     if (sequence.indexOf("\\u") == 0 && sequence.length() == 6) {
/* 2614 */       return (char)Integer.parseInt(sequence.substring(2), 16);
/*      */     }
/* 2616 */     throw new IllegalArgumentException();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static String toString(List list) {
/* 2622 */     String ret = null;
/* 2623 */     if (isNullOrEmpty(list)) {
/* 2624 */       ret = "";
/*      */     } else {
/* 2626 */       StringBuffer tmp = new StringBuffer();
/* 2627 */       for (int i = 0; i < list.size(); i++) {
/* 2628 */         tmp.append(list.get(i));
/* 2629 */         if (i + 1 < list.size()) {
/* 2630 */           tmp.append(", ");
/*      */         }
/*      */       } 
/* 2633 */       ret = tmp.toString();
/*      */     } 
/* 2635 */     return ret;
/*      */   }
/*      */   
/*      */   public static URI shortenPath(URI uri) {
/* 2639 */     String orgPath = uri.getPath();
/* 2640 */     if (isNullOrEmpty(orgPath)) {
/* 2641 */       return null;
/*      */     }
/* 2643 */     String newPath = null;
/* 2644 */     int index = orgPath.lastIndexOf('/');
/* 2645 */     if (index != -1) {
/* 2646 */       newPath = orgPath.substring(0, index);
/*      */     }
/*      */     try {
/* 2649 */       return new URI(uri.getScheme(), uri.getAuthority(), newPath, null, null);
/* 2650 */     } catch (URISyntaxException e) {
/* 2651 */       throw new RuntimeException(e);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static String getSource(StackTraceElement ste) {
/* 2658 */     String fileName = ste.getFileName();
/* 2659 */     int lineNumber = ste.getLineNumber();
/* 2660 */     return (fileName != null && lineNumber >= 0) ? (fileName + ":" + lineNumber) : ((fileName != null) ? fileName : "<Unknown Source>");
/*      */   }
/*      */   
/*      */   public static CharSequence compactStackTrace(Throwable t, int skip, int maxStackTraceElements) {
/* 2664 */     StringBuffer ret = new StringBuffer();
/*      */     
/* 2666 */     StackTraceElement[] elements = t.getStackTrace();
/* 2667 */     for (int i = 0; i < elements.length && (maxStackTraceElements == -1 || i < maxStackTraceElements); i++) {
/* 2668 */       if (i > 0) {
/* 2669 */         ret.append(" < ");
/*      */       }
/* 2671 */       ret.append(getSource(elements[i]));
/*      */     } 
/* 2673 */     return ret;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static AlphabetCallback createAlphabetCallback(final String alphabet) {
/* 2685 */     return new AlphabetCallback()
/*      */       {
/*      */         public int getLength() {
/* 2688 */           return alphabet.length();
/*      */         }
/*      */         
/*      */         public int getIndex(char c) {
/* 2692 */           return alphabet.indexOf(c);
/*      */         }
/*      */         
/*      */         public char getChar(int index) {
/* 2696 */           return alphabet.charAt(index);
/*      */         }
/*      */       };
/*      */   }
/*      */   
/*      */   public static String toPath(Package pkg, String file) {
/* 2702 */     StringBuffer tmp = StringBufferPool.getThreadInstance().get(pkg.getName());
/*      */     try {
/* 2704 */       replace(tmp, ".", "/");
/* 2705 */       if (file != null) {
/* 2706 */         tmp.append(file.startsWith("/") ? "" : ("/" + file));
/*      */       }
/* 2708 */       return tmp.toString();
/*      */     } finally {
/*      */       
/* 2711 */       StringBufferPool.getThreadInstance().free(tmp);
/*      */     } 
/*      */   }
/*      */   
/*      */   public static URI toURI(String uri) {
/*      */     try {
/* 2717 */       return new URI(uri);
/* 2718 */     } catch (URISyntaxException e) {
/* 2719 */       throw new RuntimeException(e);
/*      */     } 
/*      */   }
/*      */   
/*      */   public static InputStream createRandomBinaryStream(long length) {
/* 2724 */     final ICounter count = new Counter(BigInteger.valueOf(length));
/* 2725 */     return new InputStream()
/*      */       {
/*      */         public int read() throws IOException
/*      */         {
/* 2729 */           if (count.getCount().longValue() > 0L) {
/* 2730 */             count.dec();
/* 2731 */             return (byte)(int)Util.createRandom(-128L, 127L);
/*      */           } 
/* 2733 */           return -1;
/*      */         }
/*      */       };
/*      */   }
/*      */ 
/*      */   
/*      */   public static String getMatchingGroup(Pattern pattern, int group, CharSequence input) {
/* 2740 */     String ret = null;
/* 2741 */     Matcher matcher = pattern.matcher(input);
/* 2742 */     if (matcher.find()) {
/* 2743 */       ret = matcher.group(group);
/*      */     }
/* 2745 */     return ret;
/*      */   }
/*      */ 
/*      */   
/*      */   public static Pattern toPattern(String wildcardString, boolean caseSensitive) {
/* 2750 */     StringBuffer buffer = new StringBuffer("(?s)");
/* 2751 */     for (int i = 0; i < wildcardString.length(); i++) {
/* 2752 */       char c = wildcardString.charAt(i);
/* 2753 */       if (Character.isLetterOrDigit(c) || Character.isWhitespace(c)) {
/* 2754 */         buffer.append(c);
/* 2755 */       } else if (c == '*') {
/* 2756 */         buffer.append(".*?");
/* 2757 */       } else if (c == '?') {
/* 2758 */         buffer.append(".");
/*      */       } else {
/* 2760 */         buffer.append('\\').append(c);
/*      */       } 
/*      */     } 
/* 2763 */     return Pattern.compile(buffer.toString(), caseSensitive ? 0 : 2);
/*      */   }
/*      */   
/*      */   public static void rethrowInterruptedException(Throwable throwable) throws InterruptedException {
/* 2767 */     if (throwable instanceof InterruptedException) {
/* 2768 */       throw (InterruptedException)throwable;
/*      */     }
/*      */   }
/*      */   
/* 2772 */   private static ExecutorService executorService = new ThreadPoolExecutor(0, 2147483647, 60L, TimeUnit.SECONDS, new SynchronousQueue<Runnable>(), new ThreadFactory() {
/* 2773 */         private final ICounter counter = new Counter();
/*      */         
/*      */         public synchronized Thread newThread(Runnable r) {
/* 2776 */           this.counter.inc();
/* 2777 */           return new Thread(r, "ExecSrvc-" + this.counter.getCount());
/*      */         }
/*      */       });
/*      */   
/*      */   public static Future executeAsynchronous(Callable<?> callable) {
/* 2782 */     return executorService.submit(callable);
/*      */   }
/*      */   
/*      */   public static void executeAsynchronous(Runnable runnable) {
/* 2786 */     executorService.execute(runnable);
/*      */   }
/*      */   
/*      */   public static ExecutorService getExecutorService() {
/* 2790 */     return executorService;
/*      */   }
/*      */   
/*      */   public static List waitTillFinished(Collection<Future> futures) throws InterruptedException, ExecutionException {
/* 2794 */     List ret = new LinkedList();
/* 2795 */     for (Iterator<Future> iter = futures.iterator(); iter.hasNext();) {
/* 2796 */       ret.add(((Future)iter.next()).get());
/*      */     }
/* 2798 */     return ret;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 2806 */   private static final Notification SHUTDOWN_NOTIFICATION = new Notification()
/*      */     {
/*      */       public void notify(Object observer) {
/* 2809 */         if (observer instanceof WeakReference) {
/* 2810 */           observer = ((WeakReference)observer).get();
/*      */         }
/* 2812 */         if (observer != null) {
/* 2813 */           ((Util.ShutdownListener)observer).onShutdown();
/*      */         }
/*      */       }
/*      */     };
/*      */   
/* 2818 */   private static ObservableSupport shutdownListeners = new ObservableSupport();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static ShutdownListener addShutdownListener(ShutdownListener listener) {
/* 2829 */     shutdownListeners.addObserver(new WeakReference<ShutdownListener>(listener));
/* 2830 */     return listener;
/*      */   }
/*      */   
/*      */   public static void signalShutdown() {
/* 2834 */     shutdownListeners.notifyObservers(SHUTDOWN_NOTIFICATION);
/* 2835 */     synchronized (SYNC_TIMER) {
/* 2836 */       timer.cancel();
/* 2837 */       timer = null;
/*      */     } 
/*      */     
/* 2840 */     executorService.shutdown();
/*      */   }
/*      */ 
/*      */   
/*      */   public static Iterator createTransformingIterator(final Iterator delegate, final Transforming transforming) {
/* 2845 */     return new Iterator()
/*      */       {
/*      */         public boolean hasNext() {
/* 2848 */           return delegate.hasNext();
/*      */         }
/*      */         
/*      */         public Object next() {
/* 2852 */           return transforming.transform(delegate.next());
/*      */         }
/*      */         
/*      */         public void remove() {
/* 2856 */           delegate.remove();
/*      */         }
/*      */       };
/*      */   }
/*      */   
/*      */   public static Collection transformCollection(final Collection collection, final Transforming transforming) {
/* 2862 */     if (isNullOrEmpty(collection)) {
/* 2863 */       return collection;
/*      */     }
/* 2865 */     return new AbstractCollection()
/*      */       {
/*      */         public Iterator iterator() {
/* 2868 */           return Util.createTransformingIterator(collection.iterator(), transforming);
/*      */         }
/*      */ 
/*      */         
/*      */         public int size() {
/* 2873 */           return collection.size();
/*      */         }
/*      */       };
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static Set transformSet(final Set set, final Transforming transforming) {
/* 2881 */     if (isNullOrEmpty(set)) {
/* 2882 */       return set;
/*      */     }
/* 2884 */     return new AbstractSet()
/*      */       {
/*      */         public Iterator iterator() {
/* 2887 */           return Util.createTransformingIterator(set.iterator(), transforming);
/*      */         }
/*      */ 
/*      */         
/*      */         public int size() {
/* 2892 */           return set.size();
/*      */         }
/*      */       };
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static List transformList(final List list, final Transforming transforming) {
/* 2900 */     if (isNullOrEmpty(list)) {
/* 2901 */       return list;
/*      */     }
/* 2903 */     return new AbstractList()
/*      */       {
/*      */         public Object get(int index)
/*      */         {
/* 2907 */           return transforming.transform(list.get(index));
/*      */         }
/*      */ 
/*      */         
/*      */         public int size() {
/* 2912 */           return list.size();
/*      */         }
/*      */       };
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void burstMemory(int allocationStepSize, StringOutput output) {
/*      */     try {
/* 2932 */       output = (output != null) ? output : new StringOutput()
/*      */         {
/*      */           public void write(String string) {
/* 2935 */             Util.log.debug(string);
/*      */           }
/*      */         };
/*      */       
/* 2939 */       Runnable mm = createMemoryMonitor(output);
/*      */       
/* 2941 */       SoftReference<List> allocated = new SoftReference(new LinkedList());
/* 2942 */       boolean finished = false;
/*      */       
/* 2944 */       while (!finished) {
/* 2945 */         mm.run();
/* 2946 */         byte[] data = new byte[allocationStepSize];
/* 2947 */         List<byte[]> tmp = allocated.get();
/* 2948 */         if (tmp != null) {
/* 2949 */           tmp.add(data);
/*      */           continue;
/*      */         } 
/* 2952 */         log.debug("reference has been collected, finishing");
/* 2953 */         finished = true;
/*      */       }
/*      */     
/* 2956 */     } catch (OutOfMemoryError t) {}
/*      */   }
/*      */ 
/*      */   
/*      */   public static void unwrapInterruptedException(Throwable t) throws InterruptedException {
/* 2961 */     if (hasCause(t, InterruptedException.class)) {
/* 2962 */       throw (InterruptedException)getCause(t, InterruptedException.class);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void checkInterruption2() {
/* 2975 */     if (Thread.interrupted()) {
/* 2976 */       throw new UncheckedInterruptedException(new InterruptedException());
/*      */     }
/*      */   }
/*      */   
/*      */   public static Comparator createTransformingComparator(final Transforming transforming) {
/* 2981 */     return new Comparator()
/*      */       {
/*      */         public int compare(Object o1, Object o2) {
/* 2984 */           return Util.compare(transforming.transform(o1), transforming.transform(o2));
/*      */         }
/*      */       };
/*      */   }
/*      */   
/*      */   public static URL toURL(HttpServletRequest request, String path) {
/*      */     try {
/* 2991 */       return new URL(request.getProtocol(), request.getServerName(), request.getServerPort(), path);
/* 2992 */     } catch (MalformedURLException e) {
/* 2993 */       throw new RuntimeException(e);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Properties readProperties(File file) throws IOException {
/* 3010 */     return toProperties((Map)readProperties(file, new LinkedHashMap<String, String>()));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Map<String, String> readProperties(File file, Map<String, String> map) throws IOException {
/* 3025 */     if (file.exists()) {
/* 3026 */       for (String line : StreamUtil.readTextFile(file, Charset.forName("utf8"))) {
/* 3027 */         if (!isNullOrEmpty(line) && !line.startsWith("#")) {
/* 3028 */           int index = line.indexOf('=');
/* 3029 */           if (index == -1) {
/* 3030 */             map.put(trim(line), ""); continue;
/* 3031 */           }  if (index == line.length() - 1) {
/* 3032 */             map.put(trim(line.substring(0, index)), ""); continue;
/*      */           } 
/* 3034 */           map.put(trim(line.substring(0, index)), trim(line.substring(index + 1)));
/*      */         } 
/*      */       } 
/*      */     }
/*      */     
/* 3039 */     return map;
/*      */   }
/*      */   
/*      */   public static Properties toProperties(Map<? extends CharSequence, ? extends CharSequence> map) {
/* 3043 */     Properties ret = new Properties();
/* 3044 */     ret.putAll(map);
/* 3045 */     return ret;
/*      */   }
/*      */   
/*      */   public static Map<String, String> toMap(Properties properties) {
/* 3049 */     Map<String, String> ret = new HashMap<String, String>();
/* 3050 */     for (Map.Entry<Object, Object> entry : properties.entrySet()) {
/* 3051 */       ret.put((String)entry.getKey(), (String)entry.getValue());
/*      */     }
/* 3053 */     return ret;
/*      */   }
/*      */   
/*      */   public static void writeProperties(File file, Properties properties) throws IOException {
/* 3057 */     writeProperties(file, (Map)toMap(properties));
/*      */   }
/*      */   
/*      */   public static void writeProperties(File file, Map<? extends CharSequence, ? extends CharSequence> properties) throws IOException {
/* 3061 */     List<CharSequence> lines = new LinkedList<CharSequence>();
/* 3062 */     for (Map.Entry<? extends CharSequence, ? extends CharSequence> entry : properties.entrySet()) {
/* 3063 */       StringBuilder tmp = new StringBuilder();
/* 3064 */       tmp.append(entry.getKey()).append("=").append(entry.getValue());
/* 3065 */       lines.add(tmp);
/*      */     } 
/* 3067 */     StreamUtil.writeTextFile(file, Charset.forName("utf8"), lines);
/*      */   }
/*      */   
/*      */   public static List toList(Collection<?> collection, Comparator<?> c) {
/* 3071 */     List<?> tmp = new ArrayList(collection);
/* 3072 */     Collections.sort(tmp, c);
/* 3073 */     return tmp;
/*      */   }
/*      */   
/*      */   public static boolean beParanoid() {
/* 3077 */     return Boolean.getBoolean("de.tue.sma.paranoid");
/*      */   }
/*      */   
/*      */   public static String unescapeReservedHTMLChars(String string) {
/* 3081 */     if (!isNullOrEmpty(string)) {
/* 3082 */       StringBuffer ret = new StringBuffer(string);
/* 3083 */       unescapeReservedHTMLChars(ret);
/* 3084 */       return ret.toString();
/*      */     } 
/* 3086 */     return string;
/*      */   }
/*      */ 
/*      */   
/*      */   public static void unescapeReservedHTMLChars(StringBuffer string) {
/* 3091 */     if (string != null) {
/* 3092 */       for (int i = 0; i < htmlSimpleReplacements.length; i++) {
/* 3093 */         String[] replacement = (String[])htmlSimpleReplacements[i];
/* 3094 */         replace(string, replacement[1], replacement[0]);
/*      */       } 
/* 3096 */       replace(string, "&amp;", "&");
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Time parseTime(final String string) {
/* 3116 */     if (isNullOrEmpty(string)) {
/* 3117 */       return null;
/*      */     }
/* 3119 */     final List<Integer> parts = parseList(string, ":", ObjectCreation.TO_INTEGER);
/* 3120 */     if (parts.size() < 2) {
/* 3121 */       throw new IllegalArgumentException(string + " does not denote a valid time");
/*      */     }
/* 3123 */     return new Time()
/*      */       {
/*      */         public int getSeconds() {
/* 3126 */           return (parts.size() > 2) ? ((Integer)parts.get(2)).intValue() : 0;
/*      */         }
/*      */         
/*      */         public int getMinutes() {
/* 3130 */           return ((Integer)parts.get(1)).intValue();
/*      */         }
/*      */         
/*      */         public int getHours() {
/* 3134 */           return ((Integer)parts.get(0)).intValue();
/*      */         }
/*      */ 
/*      */         
/*      */         public String toString() {
/* 3139 */           return string;
/*      */         }
/*      */ 
/*      */         
/*      */         public boolean equals(Object obj) {
/* 3144 */           if (this == obj)
/* 3145 */             return true; 
/* 3146 */           if (obj instanceof Util.Time) {
/* 3147 */             Util.Time other = (Util.Time)obj;
/* 3148 */             boolean ret = (getHours() == other.getHours());
/* 3149 */             ret = (ret && getMinutes() == other.getMinutes());
/* 3150 */             ret = (ret && getSeconds() == other.getSeconds());
/* 3151 */             return ret;
/*      */           } 
/* 3153 */           return false;
/*      */         }
/*      */ 
/*      */ 
/*      */         
/*      */         public int hashCode() {
/* 3159 */           int ret = Util.Time.class.hashCode();
/* 3160 */           ret = HashCalc.addHashCode(ret, getHours());
/* 3161 */           ret = HashCalc.addHashCode(ret, getMinutes());
/* 3162 */           ret = HashCalc.addHashCode(ret, getSeconds());
/* 3163 */           return ret;
/*      */         }
/*      */         
/*      */         public int compareTo(Util.Time time) {
/* 3167 */           int ret = getHours() - time.getHours();
/* 3168 */           if (ret == 0) {
/* 3169 */             ret = getMinutes() - time.getMinutes();
/*      */           }
/* 3171 */           if (ret == 0) {
/* 3172 */             ret = getSeconds() - time.getSeconds();
/*      */           }
/* 3174 */           return ret;
/*      */         }
/*      */       };
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Date toDate(Date day, Time time, boolean future) {
/* 3193 */     Calendar c = Calendar.getInstance();
/* 3194 */     if (day != null) {
/* 3195 */       c.setTime(day);
/*      */     }
/* 3197 */     c.set(11, time.getHours());
/* 3198 */     c.set(12, time.getMinutes());
/* 3199 */     c.set(13, time.getSeconds());
/* 3200 */     if (c.getTimeInMillis() < System.currentTimeMillis() && future) {
/* 3201 */       c.set(5, c.get(5) + 1);
/*      */     }
/* 3203 */     return c.getTime();
/*      */   }
/*      */   
/*      */   public static URL toURL(URI uri) {
/*      */     try {
/* 3208 */       return (uri != null) ? uri.toURL() : null;
/* 3209 */     } catch (MalformedURLException e) {
/* 3210 */       throw new RuntimeException(e);
/*      */     } 
/*      */   }
/*      */   
/* 3214 */   private static WeakMultitonSupport stringSupport = new WeakMultitonSupport(new IMultitonSupport.CreationCallback()
/*      */       {
/*      */         public Object createInstance(Object key) {
/* 3217 */           return key;
/*      */         }
/*      */       });
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static String toMultiton(String string) {
/* 3228 */     return (String)stringSupport.getInstance(string, true);
/*      */   }
/*      */   
/* 3231 */   private static ThreadLocal<WeakMultitonSupport> tlStringSupport = new ThreadLocal<WeakMultitonSupport>();
/*      */   
/* 3233 */   public static long ONE_SECOND = 1000L;
/* 3234 */   public static long ONE_MINUTE = 60L * ONE_SECOND;
/* 3235 */   public static long ONE_HOUR = 60L * ONE_MINUTE;
/* 3236 */   public static long ONE_DAY = 24L * ONE_HOUR;
/*      */   
/*      */   public static String toThreadLocalMultiton(String string) {
/* 3239 */     WeakMultitonSupport wms = tlStringSupport.get();
/* 3240 */     if (wms == null) {
/* 3241 */       wms = new WeakMultitonSupport(new IMultitonSupport.CreationCallback()
/*      */           {
/*      */             public Object createInstance(Object key) {
/* 3244 */               return key;
/*      */             }
/*      */           });
/*      */     }
/* 3248 */     return (String)wms.getInstance(string, true);
/*      */   }
/*      */ 
/*      */   
/*      */   public static int incPriority() {
/* 3253 */     int currentPriority = Thread.currentThread().getPriority();
/* 3254 */     if (currentPriority < 10) {
/* 3255 */       Thread.currentThread().setPriority(currentPriority + 1);
/*      */     }
/* 3257 */     return currentPriority;
/*      */   }
/*      */   
/*      */   public static int decPriority() {
/* 3261 */     int currentPriority = Thread.currentThread().getPriority();
/* 3262 */     if (currentPriority > 1) {
/* 3263 */       Thread.currentThread().setPriority(currentPriority - 1);
/*      */     }
/* 3265 */     return currentPriority;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static VersionNumber parseVersionFile(File file) throws IOException {
/* 3276 */     VersionNumber ret = null;
/* 3277 */     FileInputStream fis = new FileInputStream(file);
/* 3278 */     LineNumberReader lnr = new LineNumberReader(new InputStreamReader(fis, Charset.forName("utf-8")));
/*      */     try {
/* 3280 */       String version = lnr.readLine();
/* 3281 */       if (!isNullOrEmpty(version)) {
/* 3282 */         ret = parseVersionNumber(version);
/*      */       }
/*      */     } finally {
/* 3285 */       lnr.close();
/*      */     } 
/* 3287 */     return ret;
/*      */   }
/*      */   
/*      */   public static void writeVersionFile(File file, VersionNumber version) throws IOException {
/* 3291 */     FileOutputStream fos = new FileOutputStream(file);
/*      */     try {
/* 3293 */       PrintWriter pw = new PrintWriter(new OutputStreamWriter(fos, Charset.forName("utf-8")));
/*      */       try {
/* 3295 */         pw.println(version.toString());
/*      */       } finally {
/*      */         
/* 3298 */         pw.close();
/*      */       } 
/*      */     } finally {
/*      */       
/* 3302 */       fos.close();
/*      */     } 
/*      */   }
/*      */   
/*      */   public static void unzip(File file, File destDir) throws IOException {
/* 3307 */     ZipInputStream zis = new ZipInputStream(new FileInputStream(file));
/* 3308 */     ZipEntry entry = null;
/* 3309 */     while ((entry = zis.getNextEntry()) != null) {
/* 3310 */       String filename = entry.getName();
/* 3311 */       File destFile = new File(destDir, filename);
/* 3312 */       if (entry.isDirectory()) {
/* 3313 */         if (!destFile.exists() && !destFile.mkdirs())
/* 3314 */           throw new IOException("unable to create directory " + destFile); 
/*      */         continue;
/*      */       } 
/* 3317 */       File dir = destFile.getParentFile();
/* 3318 */       if (dir == null || dir.exists() || dir.mkdirs()) {
/* 3319 */         OutputStream os = new BufferedOutputStream(new FileOutputStream(destFile));
/*      */         try {
/* 3321 */           StreamUtil.transfer(zis, os);
/*      */         } finally {
/* 3323 */           os.close();
/*      */         }  continue;
/*      */       } 
/* 3326 */       throw new IOException("unable to create directoy " + destFile.getParentFile());
/*      */     } 
/*      */   }
/*      */   
/*      */   public static interface Time extends Comparable<Time> {
/*      */     int getHours();
/*      */     
/*      */     int getMinutes();
/*      */     
/*      */     int getSeconds();
/*      */   }
/*      */   
/*      */   public static interface ShutdownListener {
/*      */     void onShutdown();
/*      */   }
/*      */   
/*      */   public static interface Duration {
/*      */     long getMillis();
/*      */     
/*      */     int getSeconds();
/*      */     
/*      */     int getMinutes();
/*      */     
/*      */     int getHours();
/*      */     
/*      */     int getDays();
/*      */     
/*      */     int getWeeks();
/*      */     
/*      */     int getMonths();
/*      */     
/*      */     int getYears();
/*      */     
/*      */     long getAsMillis();
/*      */   }
/*      */   
/*      */   public static interface ProxyRetrieval {
/*      */     Object getProxy(Object param1Object);
/*      */   }
/*      */   
/*      */   public static interface FileVisitor {
/*      */     boolean onVisit(File param1File);
/*      */   }
/*      */   
/*      */   public static interface ModificationObserver {
/*      */     void onModification();
/*      */   }
/*      */   
/*      */   public static interface StreamMeta {
/*      */     BigInteger getSize();
/*      */     
/*      */     byte[] getHash();
/*      */   }
/*      */   
/*      */   public static interface ReplacementCallback {
/*      */     CharSequence getReplacement(CharSequence param1CharSequence, MatcherCallback param1MatcherCallback);
/*      */     
/*      */     public static interface MatcherCallback {
/*      */       String getGroup(int param2Int);
/*      */     }
/*      */   }
/*      */   
/*      */   public static interface StringOutput {
/*      */     void write(String param1String);
/*      */   }
/*      */   
/*      */   public static interface SplitTimespanCallback {
/*      */     void setDays(BigInteger param1BigInteger);
/*      */     
/*      */     void setHours(BigInteger param1BigInteger);
/*      */     
/*      */     void setMinutes(BigInteger param1BigInteger);
/*      */     
/*      */     void setSeconds(BigInteger param1BigInteger);
/*      */     
/*      */     void setMillis(BigInteger param1BigInteger);
/*      */   }
/*      */   
/*      */   public static interface MatcherCallback {
/*      */     String getGroup(int param1Int);
/*      */   }
/*      */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\scsm\v\\util\Util.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */