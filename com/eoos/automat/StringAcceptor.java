/*     */ package com.eoos.automat;
/*     */ 
/*     */ import com.eoos.condition.Condition;
/*     */ import com.eoos.tokenizer.CharacterTokenizer;
/*     */ import java.util.HashSet;
/*     */ import java.util.Iterator;
/*     */ import java.util.Set;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class StringAcceptor
/*     */   implements Acceptor
/*     */ {
/*  15 */   public static final Character WILDCARD_ALL = Character.valueOf('*');
/*     */   
/*  17 */   public static final Character WILDCARD_ONE = Character.valueOf('?');
/*     */   
/*     */   private AutomatImpl processor;
/*     */   
/*     */   private State endState;
/*     */   
/*     */   private String pattern;
/*     */   
/*     */   private StringAcceptor(final Set transitions, final State start, State end) {
/*  26 */     this.processor = new AutomatImpl(new AutomatImpl.Callback()
/*     */         {
/*     */           public Set getTransitions() {
/*  29 */             return transitions;
/*     */           }
/*     */           
/*     */           public State getStartState() {
/*  33 */             return start;
/*     */           }
/*     */         });
/*     */ 
/*     */     
/*  38 */     this.endState = end;
/*     */   }
/*     */ 
/*     */   
/*     */   public static StringAcceptor create(String pattern, final boolean ignoreCase) {
/*  43 */     State startState = new StateImpl();
/*  44 */     Set<Transition> transitions = new HashSet();
/*     */     
/*  46 */     State currentState = startState;
/*     */     
/*  48 */     for (Iterator<Character> iter = (new CharacterTokenizer(pattern)).iterator(); iter.hasNext(); ) {
/*  49 */       final Character c = iter.next();
/*     */       
/*  51 */       if (c.equals(WILDCARD_ALL)) {
/*     */         
/*  53 */         Transition transition1 = new TransitionImpl(currentState, currentState, Condition.TRUE);
/*  54 */         transitions.add(transition1); continue;
/*     */       } 
/*  56 */       if (c.equals(WILDCARD_ONE)) {
/*  57 */         State state = new StateImpl();
/*  58 */         Transition transition1 = new TransitionImpl(currentState, state, Condition.TRUE);
/*  59 */         currentState = state;
/*  60 */         transitions.add(transition1);
/*     */         continue;
/*     */       } 
/*  63 */       State newState = new StateImpl();
/*  64 */       Transition transition = new TransitionImpl(currentState, newState, new Condition()
/*     */           {
/*     */             public boolean check(Object obj) {
/*  67 */               if (!ignoreCase) {
/*  68 */                 return c.equals(obj);
/*     */               }
/*  70 */               Character c1 = Character.valueOf(Character.toLowerCase(c.charValue()));
/*  71 */               Character c2 = Character.valueOf(Character.toLowerCase(((Character)obj).charValue()));
/*  72 */               return c1.equals(c2);
/*     */             }
/*     */           });
/*     */ 
/*     */       
/*  77 */       currentState = newState;
/*  78 */       transitions.add(transition);
/*     */     } 
/*     */ 
/*     */     
/*  82 */     State endState = currentState;
/*  83 */     StringAcceptor ret = new StringAcceptor(transitions, startState, endState);
/*  84 */     ret.pattern = pattern;
/*  85 */     return ret;
/*     */   }
/*     */   
/*     */   private boolean acceptString(String input) {
/*  89 */     this.processor.reset();
/*  90 */     for (Iterator<Character> iter = (new CharacterTokenizer(input)).iterator(); iter.hasNext();) {
/*  91 */       this.processor.process(iter.next());
/*     */     }
/*  93 */     return this.processor.getCurrentStates().contains(this.endState);
/*     */   }
/*     */   
/*     */   public synchronized boolean accept(Object object) {
/*  97 */     if (!(object instanceof String)) {
/*  98 */       throw new IllegalArgumentException("argument has to be of type String");
/*     */     }
/* 100 */     return acceptString((String)object);
/*     */   }
/*     */   
/*     */   public String toString() {
/* 104 */     return "Acceptor[" + this.pattern + "]";
/*     */   }
/*     */   
/*     */   public static void main(String[] args) {
/* 108 */     StringAcceptor acceptor = create("?", true);
/* 109 */     System.out.println(acceptor.accept("e:\\projects\\tis2web\\wrk\\log\\tis2web.log"));
/* 110 */     System.out.println(acceptor.accept(""));
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\automat\StringAcceptor.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */