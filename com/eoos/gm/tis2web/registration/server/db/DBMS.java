/*    */ package com.eoos.gm.tis2web.registration.server.db;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class DBMS
/*    */ {
/*    */   public static final int UNDEFINED = -1;
/*    */   public static final String LOAD_REGISTRATIONS = "SELECT r.\"RegistrationPK\", r.\"RequestID\", r.\"Organization\", r.\"RequestStatus\", r.\"RequestType\", r.\"RequestDate\", r.\"Sessions\", r.\"Dealership\", d.\"DealershipID\", d.\"DealershipName\", d.\"DealershipZIP\", d.\"DealershipCity\", d.\"DealershipState\", d.\"DealershipCountry\" FROM REGISTRATION r, Dealership d WHERE r.\"Dealership\" = d.\"DealershipPK\"";
/*    */   public static final String COMPLETE_REGISTRATION = "SELECT r.\"SubscriberID\", r.\"Authorization\", r.\"SoftwareKey\", r.\"LicenseKey\" FROM REGISTRATION r WHERE r.\"RegistrationPK\" = ?";
/*    */   public static final String COMPLETE_DEALERSHIP = "SELECT \"DealershipStreet\", \"DealershipLanguage\", \"DealershipPhone\", \"DealershipFax\", \"DealershipEmail\" FROM Dealership WHERE \"DealershipPK\" = ?";
/*    */   public static final String LOAD_DEALERSHIP_CONTACTS = "SELECT d.\"ContactName\", d.\"ContactLanguage\" FROM DealershipContact d WHERE d.\"DealershipPK\" = ?";
/*    */   public static final String STORE_REGISTRATION = "INSERT INTO Registration(\"RegistrationPK\", \"RequestID\", \"Organization\", \"RequestStatus\", \"RequestType\", \"RequestDate\", \"Dealership\", \"Sessions\", \"SubscriberID\", \"Authorization\", \"SoftwareKey\", \"LicenseKey\") VALUES(?,?,?,?,?,?,?,?,?,?,?,?)";
/*    */   public static final String STORE_DEALERSHIP = "INSERT INTO Dealership(\"DealershipPK\", \"DealershipID\", \"DealershipName\", \"DealershipStreet\", \"DealershipZIP\", \"DealershipCity\", \"DealershipState\", \"DealershipCountry\", \"DealershipLanguage\", \"DealershipPhone\", \"DealershipFax\", \"DealershipEmail\") VALUES(?,?,?,?,?,?,?,?,?,?,?,?)";
/*    */   public static final String STORE_DEALERSHIP_CONTACT = "INSERT INTO DealershipContact(\"DealershipPK\", \"ContactName\", \"ContactLanguage\") VALUES(?,?,?)";
/*    */   public static final String DELETE_REGISTRATION = "DELETE FROM Registration WHERE \"RegistrationPK\" = ?";
/*    */   public static final String DELETE_DEALERSHIP = "DELETE FROM Dealership WHERE \"DealershipPK\" = ?";
/*    */   public static final String DELETE_DEALERSHIP_CONTACTS = "DELETE FROM DealershipContact WHERE \"DealershipPK\" = ?";
/*    */   public static final String CHECK_DEALERSHIP = "SELECT \"DealershipPK\" FROM Dealership WHERE \"DealershipID\" = ?";
/*    */   public static final String CHECK_REGISTRATION = "SELECT \"SubscriberID\",\"RequestStatus\", \"SoftwareKey\" FROM Registration WHERE \"Dealership\" = ?";
/*    */   public static final String LOAD_REGISTRATION = "SELECT r.*, d.* FROM Registration r, Dealership d WHERE \"SubscriberID\" = ? AND r.\"Dealership\" = d.\"DealershipPK\"";
/*    */   public static final String RECORD_REGISTRATION = "UPDATE Registration SET \"RequestID\" = ?, \"RequestStatus\" = ?, \"RequestType\" = ?, \"RequestDate\" = ?,\"SoftwareKey\" = ?, \"Sessions\" = ? WHERE \"RegistrationPK\" = ?";
/*    */   public static final String RECORD_EXTENSION_REQUEST = "UPDATE Registration SET \"RequestID\" = ?, \"RequestStatus\" = ?, \"RequestType\" = ?, \"RequestDate\" = ?, \"SoftwareKey\" = ?, \"Authorization\" = ?, \"Sessions\" = ? WHERE \"RegistrationPK\" = ?";
/*    */   public static final String UPDATE_REGISTRATION = "UPDATE Registration SET \"RequestStatus\" = ?, \"Authorization\" = ?, \"LicenseKey\" = ?, \"Sessions\" = ?, \"RequestID\" = ? WHERE \"RegistrationPK\" = ?";
/*    */   public static final String REVOKE_REGISTRATION = "UPDATE Registration SET \"RequestStatus\" = ? WHERE \"RegistrationPK\" = ?";
/*    */   public static final String LOAD_AUTHORIZATIONS = "SELECT s.\"SubscriptionID\", s.\"Organization\", d.\"Locale\", d.\"Description\" FROM Subscription s, SubscriptionDescription d WHERE s.\"SubscriptionPK\" = d.\"Subscription\" ORDER BY s.\"SubscriptionID\"";
/*    */   
/*    */   public String getSQL(String sql) {
/* 31 */     return sql;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\registration\server\db\DBMS.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */