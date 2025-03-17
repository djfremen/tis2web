/*   */ package com.eoos.observable;public interface IObservableSupport { void addObserver(Object paramObject);
/*   */   void removeObserver(Object paramObject);
/*   */   void notifyObservers(Notification paramNotification);
/*   */   void notifyObservers(Notification paramNotification, Mode paramMode);
/*   */   
/* 6 */   public enum Mode { SYNCHRONOUS_NOTIFY, ASYNCHRONOUS_NOTIFY; }
/*   */    }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\observable\IObservableSupport.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */