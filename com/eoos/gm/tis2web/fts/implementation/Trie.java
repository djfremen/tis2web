/*     */ package com.eoos.gm.tis2web.fts.implementation;
/*     */ 
/*     */ import java.util.Iterator;
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
/*     */ 
/*     */ 
/*     */ public class Trie
/*     */ {
/*  10 */   public static final Object NO_SUCH_KEY = new String("Object:NO_SUCH_KEY");
/*     */   private int m_size;
/*     */   private List m_strings;
/*     */   private TrieNode m_head;
/*     */   private boolean m_sentinel;
/*     */   
/*     */   private class TrieNode { Trie.TrieItem element;
/*     */     
/*     */     TrieNode(Trie.TrieItem element) {
/*  19 */       this.element = element;
/*  20 */       this.children = new LinkedList();
/*     */     }
/*     */     List children;
/*     */     public void add(TrieNode child) {
/*  24 */       this.children.add(child);
/*     */     }
/*     */     
/*     */     public int size() {
/*  28 */       return this.children.size();
/*     */     }
/*     */     
/*     */     public Iterator children() {
/*  32 */       return this.children.iterator();
/*     */     }
/*     */     
/*     */     public TrieNode getChild(Trie.TrieItem item) {
/*  36 */       for (int i = 0; i < this.children.size(); i++) {
/*  37 */         TrieNode child = this.children.get(i);
/*  38 */         if (item.equals(child.element)) {
/*  39 */           return child;
/*     */         }
/*     */       } 
/*  42 */       return null;
/*     */     } }
/*     */ 
/*     */ 
/*     */   
/*     */   private class TrieItem
/*     */     implements Comparable
/*     */   {
/*     */     public int index;
/*     */     
/*     */     public int begin;
/*     */     
/*     */     public int end;
/*     */     private List strings;
/*     */     public Object value;
/*     */     
/*     */     TrieItem(int index, int begin, int end, List strings, Object value) {
/*  59 */       this.index = index;
/*  60 */       this.begin = begin;
/*  61 */       this.end = end;
/*  62 */       this.strings = strings;
/*  63 */       this.value = (value == null) ? Trie.NO_SUCH_KEY : value;
/*     */     }
/*     */ 
/*     */     
/*     */     public String getString() {
/*  68 */       return ((String)this.strings.get(this.index)).substring(this.begin, this.end + 1);
/*     */     }
/*     */ 
/*     */     
/*     */     public String toString() {
/*  73 */       return getString() + ":" + this.index + "," + this.begin + "," + this.end;
/*     */     }
/*     */ 
/*     */     
/*     */     public int compareTo(Object o) {
/*  78 */       return getString().compareTo(((TrieItem)o).getString());
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean equals(Object o) {
/*  83 */       return (o == null) ? false : ((compareTo(o) == 0));
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
/*     */   public Trie() {
/* 104 */     this.m_strings = new LinkedList();
/* 105 */     this.m_head = new TrieNode(null);
/*     */ 
/*     */ 
/*     */     
/* 109 */     this.m_size = 0;
/* 110 */     this.m_sentinel = false;
/*     */   }
/*     */   
/*     */   public Trie(boolean m_sentinel) {
/* 114 */     this();
/* 115 */     this.m_sentinel = m_sentinel;
/*     */   }
/*     */ 
/*     */   
/*     */   public int size() {
/* 120 */     return this.m_size;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isEmpty() {
/* 125 */     return (size() == 0);
/*     */   }
/*     */ 
/*     */   
/*     */   private TrieNode find(TrieNode node, String key) {
/* 130 */     Iterator<TrieNode> i = node.children();
/* 131 */     while (i.hasNext()) {
/*     */       
/* 133 */       TrieNode candidate = i.next();
/* 134 */       String s = candidate.element.getString();
/* 135 */       if (key.startsWith(s)) {
/* 136 */         key = key.substring(s.length());
/* 137 */         if (key.length() > 0) {
/* 138 */           return find(candidate, key);
/*     */         }
/* 140 */         return candidate;
/*     */       } 
/*     */     } 
/*     */     
/* 144 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object findElement(String key) {
/* 152 */     if (this.m_sentinel) {
/* 153 */       key = key + "\000";
/*     */     }
/* 155 */     TrieNode node = find(this.m_head, key);
/* 156 */     if (node != null) {
/* 157 */       return node.element.value;
/*     */     }
/* 159 */     return NO_SUCH_KEY;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private TrieNode match(TrieNode node, String key) {
/* 165 */     TrieNode best = null;
/* 166 */     String value = null;
/* 167 */     Iterator<TrieNode> i = node.children();
/* 168 */     while (i.hasNext()) {
/*     */       
/* 170 */       TrieNode candidate = i.next();
/* 171 */       String s = candidate.element.getString();
/* 172 */       if (key.startsWith(s)) {
/* 173 */         String reference = null;
/* 174 */         if (candidate.element.value != NO_SUCH_KEY) {
/* 175 */           reference = candidate.element.value.toString();
/*     */         }
/* 177 */         key = key.substring(s.length());
/* 178 */         if (key.length() > 0) {
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 183 */           TrieNode match = match(candidate, key);
/* 184 */           if (match == null) {
/* 185 */             if (reference != null) {
/* 186 */               match = candidate;
/*     */             }
/* 188 */           } else if (match.element.value != NO_SUCH_KEY) {
/* 189 */             reference = match.element.value.toString();
/*     */           } else {
/* 191 */             reference = null;
/*     */           } 
/* 193 */           if (reference != null && (best == null || value.length() < reference.length())) {
/* 194 */             best = match;
/* 195 */             value = reference;
/*     */           }  continue;
/*     */         } 
/* 198 */         if (reference != null && (best == null || value.length() < reference.length())) {
/* 199 */           best = candidate;
/* 200 */           value = reference;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 205 */     return best;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object matchElement(String key) {
/* 213 */     if (this.m_sentinel) {
/* 214 */       key = key + "\000";
/*     */     }
/* 216 */     TrieNode node = match(this.m_head, key);
/* 217 */     if (node != null) {
/* 218 */       return node.element.value;
/*     */     }
/* 220 */     return NO_SUCH_KEY;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void insertItem(String key, Object value) {
/* 226 */     if (this.m_sentinel) {
/* 227 */       key = key + "\000";
/*     */     }
/*     */     
/* 230 */     if (findElement(key) != NO_SUCH_KEY) {
/*     */       return;
/*     */     }
/* 233 */     this.m_strings.add(key);
/* 234 */     int index = this.m_strings.size() - 1;
/* 235 */     TrieNode node = this.m_head;
/* 236 */     for (int i = 0; i < key.length(); i++) {
/* 237 */       TrieItem item = new TrieItem(index, i, i, this.m_strings, null);
/*     */       
/* 239 */       TrieNode next = node.getChild(item);
/* 240 */       if (next == null) {
/*     */         
/* 242 */         next = new TrieNode(item);
/* 243 */         node.add(next);
/*     */       } 
/* 245 */       node = next;
/*     */     } 
/*     */     
/* 248 */     node.element.value = value;
/*     */   }
/*     */ 
/*     */   
/*     */   private TrieNode compress0(TrieNode node) {
/* 253 */     if (node.size() == 1 && node.element.value == NO_SUCH_KEY) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 259 */       TrieNode trieNode = compress0(node.children().next());
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 264 */       TrieItem child = trieNode.element;
/*     */       
/* 266 */       child.begin--;
/* 267 */       return trieNode;
/*     */     } 
/*     */ 
/*     */     
/* 271 */     TrieNode new_node = new TrieNode(node.element);
/* 272 */     Iterator<TrieNode> i = node.children();
/* 273 */     while (i.hasNext()) {
/* 274 */       new_node.add(compress0(i.next()));
/*     */     }
/* 276 */     return new_node;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void compress() {
/* 283 */     Iterator<TrieNode> i = this.m_head.children();
/* 284 */     TrieNode new_head = new TrieNode(null);
/* 285 */     while (i.hasNext()) {
/* 286 */       new_head.add(compress0(i.next()));
/*     */     }
/*     */     
/* 289 */     this.m_head = new_head;
/*     */   }
/*     */ 
/*     */   
/*     */   private String ancestryToString(TrieNode node, int indent) {
/* 294 */     String s = "";
/* 295 */     for (int i = 0; i < indent; i++) {
/* 296 */       s = s + " ";
/*     */     }
/* 298 */     TrieItem item = node.element;
/* 299 */     s = s + item.toString() + "\n";
/* 300 */     Iterator<TrieNode> iterator = node.children();
/* 301 */     while (iterator.hasNext()) {
/* 302 */       s = s + ancestryToString(iterator.next(), indent + 1);
/*     */     }
/* 304 */     return s;
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 309 */     String s = "";
/* 310 */     Iterator<String> i = this.m_strings.iterator();
/* 311 */     while (i.hasNext()) {
/* 312 */       s = s + i.next() + "\n";
/*     */     }
/* 314 */     i = this.m_head.children();
/* 315 */     while (i.hasNext()) {
/* 316 */       s = s + ancestryToString((TrieNode)i.next(), 0);
/*     */     }
/* 318 */     return s;
/*     */   }
/*     */   
/*     */   public static void main(String[] args) {
/* 322 */     Trie trie = new Trie();
/* 323 */     trie.insertItem("4JX1", "4JX1");
/* 324 */     trie.insertItem("C16NE", "C16NE");
/* 325 */     trie.insertItem("C16NE2", "C16NE2");
/* 326 */     trie.insertItem("C12NE", "C12NE");
/* 327 */     trie.compress();
/* 328 */     System.out.println(trie.toString());
/* 329 */     System.out.println("C16     -> " + trie.findElement("C16"));
/* 330 */     System.out.println("C16NE   -> " + trie.findElement("C16NE"));
/* 331 */     System.out.println("C16NEzt -> " + trie.findElement("C16NEzt"));
/* 332 */     System.out.println("C16     ~> " + trie.matchElement("C16"));
/* 333 */     System.out.println("C16NE   ~> " + trie.matchElement("C16NE"));
/* 334 */     System.out.println("C16NE2  ~> " + trie.matchElement("C16NE2"));
/* 335 */     System.out.println("C16NEzt ~> " + trie.matchElement("C16NEzt"));
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\fts\implementation\Trie.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */