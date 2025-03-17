/*     */ package com.eoos.gm.tis2web.sps.server.implementation.adapter.gme;
/*     */ import com.eoos.gm.tis2web.frame.export.common.util.IDatabaseLink;
/*     */ import com.eoos.gm.tis2web.sps.server.implementation.adapter.common.DBMS;
/*     */ import com.eoos.util.StringUtilities;
/*     */ import edu.umd.cs.findbugs.annotations.SuppressWarnings;
/*     */ import java.sql.ResultSet;
/*     */ import java.util.HashMap;
/*     */ import java.util.Locale;
/*     */ import java.util.Map;
/*     */ 
/*     */ @SuppressWarnings({"NM_SAME_SIMPLE_NAME_AS_SUPERCLASS"})
/*     */ public class DBMS extends DBMS {
/*  13 */   protected static Map defaultSQL = new HashMap<Object, Object>();
/*     */   
/*  15 */   protected static Map tbSQL = new HashMap<Object, Object>();
/*     */   
/*  17 */   protected static int TRANSBASE_VERSION_OLD = 5;
/*     */   
/*  19 */   protected static int TRANSBASE_VERSION_NEW = 6;
/*     */   
/*  21 */   protected static int transbaseVersion = TRANSBASE_VERSION_OLD;
/*     */ 
/*     */   
/*     */   public static final String XML_SUPPORT = "SELECT ControllerID FROM SPS_XML_Support";
/*     */ 
/*     */   
/*     */   public static final String XML_FUNCTION_SUPPORT = "SELECT FunctionID FROM SPS_XML_Support";
/*     */ 
/*     */   
/*     */   public static final String XML_VCI = "SELECT RPO_String_ID, Model_Designator_ID, XML_ID FROM XML_VCI WHERE VCI = ?";
/*     */ 
/*     */   
/*     */   public static final String RPO_STRING = "SELECT RPO_String FROM RPO_String WHERE RPO_String_ID = ?";
/*     */ 
/*     */   
/*     */   public static final String MODEL_DESIGNATOR = "SELECT Model_Designator FROM Model_Designator WHERE Model_Designator_ID = ?";
/*     */ 
/*     */   
/*     */   public static final String SETTINGS = "SELECT SalesMakeCode, Attribute, Value FROM SPS_DBSettings";
/*     */ 
/*     */   
/*     */   public static final String SYSTEM_TYPES = "SELECT SystemType, LanguageID, Description FROM SPS_SystemTypes";
/*     */ 
/*     */   
/*     */   public static final String CVN = "SELECT DISTINCT SalesMakeCode, CVN FROM SPS_CVN WHERE LOWER(PartNumber) = ?";
/*     */ 
/*     */   
/*     */   public static final String IDENT_TYPES = "SELECT DISTINCT b.IdentType, b.VIT1Ident, b.VIT1Pos, a.VINPattern, a.SNFrom, a.SNTo, a.HWID, a.ModelYearCode FROM SPS_Schema a, SPS_IdentDescription b WHERE a.WMI = ?  AND ((a.SalesMakeCode = ?) OR (a.SalesMakeCode is null))  AND a.Protocol = ?  AND b.VITType = ?  AND b.IdentType = a.IdentType";
/*     */ 
/*     */   
/*     */   public static final String IDENT_TYPES_MODELYEAR = " AND (a.ModelYearCode is null OR a.ModelYearCode IN (?, 0, -1))";
/*     */ 
/*     */   
/*     */   public static final String IDENT_TYPES_SYSTEMTYPE = " AND (a.SystemType is null OR a.SystemType IN (0, ?))";
/*     */ 
/*     */   
/*     */   public static final String SALES_MAKES = "SELECT SalesMakeCode, SalesMake FROM SPS_SalesMake";
/*     */ 
/*     */   
/*     */   public static final String MODELS = "SELECT DISTINCT ModelCode, Description FROM SPS_ModelDescription";
/*     */ 
/*     */   
/*     */   public static final String MODEL_YEARS = "SELECT ModelYearCode, ModelYear FROM SPS_ModelYear";
/*     */ 
/*     */   
/*     */   public static final String VEHICLES = "SELECT v.VehicleID FROM SPS_Vehicle v, SPS_ModelDescription md, SPS_ModelYear my, SPS_SalesMake sm WHERE sm.SalesMake = ?  AND sm.SalesMakeCode = v.SalesMakeCode  AND md.Description = ?  AND md.LanguageID = ?  AND md.ModelCode = v.ModelCode  AND my.ModelYear = ?  AND my.ModelYearCode = v.ModelYearCode  AND v.SystemType = ?";
/*     */ 
/*     */   
/*     */   public static final String ECU_DESCRIPTION = "SELECT ECUID, ServiceECUFlag, ReleaseDate, ValidFrom, ValidTo FROM SPS_ECUDescription";
/*     */ 
/*     */   
/*     */   public static final String ECU_VEHICLES = "SELECT VehicleID FROM SPS_Configuration WHERE ECUID = ?";
/*     */ 
/*     */   
/*     */   public static final String VEHICLE = "SELECT v.VehicleID FROM SPS_Vehicle v WHERE v.SalesMakeCode = ?  AND v.ModelCode = ?  AND v.ModelYearCode = ?";
/*     */ 
/*     */   
/*     */   public static final String FUNCTION_VEHICLES = "SELECT v.VehicleID, v.DeviceID, c.ECUID FROM SPS_Configuration c right outer join SPS_Vehicle v on v.VehicleID = c.VehicleID WHERE v.SalesMakeCode = ?  AND v.ModelCode = ?  AND v.ModelYearCode = ? AND v.FunctionID = ?";
/*     */ 
/*     */   
/*     */   public static final String CONTROLLER_VEHICLES = "SELECT v.VehicleID FROM SPS_Configuration c, SPS_Vehicle v, SPS_SalesMake m, SPS_ModelDescription d, SPS_ModelYear y WHERE c.ECUID = ?  AND c.VehicleID = v.VehicleID  AND m.SalesMake = ?  AND m.SalesMakeCode = v.SalesMakeCode  AND v.ModelCode = d.ModelCode  AND d.LanguageID = ?  AND d.Description = ?  AND v.ModelYearCode = y.ModelYearCode  AND y.ModelYear = ?  AND v.SystemType = ?";
/*     */ 
/*     */   
/*     */   public static final String VEHICLE_CONTROLLERS = "SELECT c.ECUID FROM SPS_Configuration c WHERE c.VehicleID = ?";
/*     */ 
/*     */   
/*     */   public static final String ALL_VEHICLE_CONTROLLERS = "SELECT v.VehicleID, v.DeviceID, v.ControllerID, c.ECUID FROM SPS_Configuration c, SPS_Vehicle v WHERE v.VehicleID = c.VehicleID (+)  AND v.SalesMakeCode = ?  AND v.ModelCode = ?  AND v.ModelYearCode = ?";
/*     */   
/*     */   public static final String ALL_VEHICLE_CONTROLLER_FUNCTIONS = "SELECT v.VehicleID, v.DeviceID, v.FunctionID, c.ECUID FROM SPS_Configuration c, SPS_Vehicle v WHERE v.VehicleID = c.VehicleID (+)  AND v.SalesMakeCode = ?  AND v.ModelCode = ?  AND v.ModelYearCode = ?";
/*     */   
/*     */   public static final String VCI_VEHICLE_CONTROLLERS = "SELECT DISTINCT c.VehicleID, c.ECUID, i.DeviceID, i.ControllerID FROM SPS_Configuration c, SPS_ECUDescription d, SPS_AsBuilt_Vehicle v, SPS_AsBuilt_VCI i WHERE c.VehicleID IN (#VEHICLE_LIST#) AND d.ECUID = c.ECUID AND d.ServiceECUFlag = 0 AND d.ValidFrom <= ? AND d.ValidTo >= ? AND c.VehicleID = v.VehicleID AND v.VCI = i.VCI ";
/*     */   
/*     */   public static final String VCI_VEHICLE_FUNCTIONS = "SELECT DISTINCT c.VehicleID, c.ECUID, i.DeviceID, i.FunctionID FROM SPS_Configuration c, SPS_ECUDescription d, SPS_AsBuilt_Vehicle v, SPS_AsBuilt_VCI i WHERE c.VehicleID IN (#VEHICLE_LIST#) AND d.ECUID = c.ECUID AND d.ServiceECUFlag = 0 AND d.ValidFrom <= ? AND d.ValidTo >= ? AND c.VehicleID = v.VehicleID AND v.VCI = i.VCI ";
/*     */   
/*     */   public static final String ALL_VEHICLE_CONTROLLER_SEQUENCES = "SELECT v.BaseVehicleID, v.ControllerID, v.SequenceID, v.WMI, v.VDS, v.VIS, o.CategoryCode, o.OptionGroup, o.OptionOrder FROM SPS_BaseVehicle v left outer join SPS_BaseVehOption o on v.BaseVehicleID = o.BaseVehicleID WHERE v.SalesMakeCode = ? AND v.ModelCode = ? AND v.ModelYearCode = ?";
/*     */   
/*     */   public static final String CONTROLLER_DESCRIPTIONS = "SELECT ControllerID, LanguageID, Description, ShortDescr FROM SPS_Controller";
/*     */   
/*     */   public static final String CONTROLLER_FUNCTIONS = "SELECT ControllerID, FunctionID, LanguageID, Description, ShortDescr FROM SPS_Function";
/*     */   
/*     */   public static final String CONTROLLER_SEQUENCES = "SELECT SequenceID, FunctionID, ReqInfoID, FunctionOrder, OnSameSW FROM SPS_Sequence ORDER BY 1,2,4";
/*     */   
/*     */   public static final String CONTROLLER_SEQUENCE_DISPLAYS = "SELECT SequenceID, LanguageID, Description, DisplayOrder FROM SPS_SeqDisplay";
/*     */   
/*     */   public static final String VIN_IDENTIFIERS = "SELECT v.VINID FROM SPS_AsBuilt_VIN v, SPS_AsBuilt_VINDescription d, SPS_AsBuilt_VINSerial s  WHERE d.VINDescID = v.VINDescID  AND v.VINSerID = s.VINSerID  AND d.WMI = ?  AND d.VDS = ?  AND d.VIN_9 = ?  AND d.VIN_10 = ?  AND d.VIN_11 = ?  AND s.SerNum = ?";
/*     */   
/*     */   public static final String REQUEST_INFO_METHODS = "SELECT ReqInfoID, ReqInfoMeth_Group FROM SPS_ReqInfo";
/*     */   
/*     */   public static final String REQUEST_INFO_CATEGORIES = "SELECT DISTINCT v.ReqInfoID, v.WMI, v.VDS, v.VIS, o.CategoryCode, o.OptionGroup, o.OptionOrder FROM SPS_BaseVehicle v, SPS_BaseVehOption o WHERE v.ReqInfoID = o.ReqInfoID (+)  AND v.SalesMakeCode = ?  AND ((v.ModelCode = ?) OR (v.ModelCode is null))  AND ((v.ModelYearCode = ?) OR (v.ModelYearCode is null))  AND (v.ControllerID = ?)ORDER BY o.OptionOrder";
/*     */   
/*     */   public static final String VEHICLE_CATEGORIES = "SELECT DISTINCT VehicleID, CategoryCode, OptionGroup, OptionOrder, NoPreEval FROM SPS_Option o WHERE VehicleID IN (#list#)ORDER BY OptionOrder";
/*     */   
/*     */   public static final String FREE_OPTIONS = "SELECT OptionCode, LanguageID, Description FROM SPS_FreeOptions";
/*     */   
/*     */   public static final String OPTION_CATEGORIES = "SELECT CategoryCode, LanguageID, Description FROM SPS_Categories";
/*     */   
/*     */   public static final String OPTION_CATEGORIES_VIT1 = "SELECT CategoryCode, VITType, CatValue, Position FROM SPS_VITCategories";
/*     */   
/*     */   public static final String FREE_OPTION_SET = "SELECT CategoryCode, OptionCode FROM SPS_FreeOptionSet";
/*     */   
/*     */   public static final String OPTION_GROUPS = "SELECT OptionGroup, OptionCode FROM SPS_OptionGroup";
/*     */   
/*     */   public static final String ENGINE_OPTIONS = "SELECT DISTINCT Description FROM SPS_FreeOptions WHERE Description LIKE 'Z%'";
/*     */   
/*     */   public static final String OPTION_VINRange = "SELECT RangeCode, FromSN, ToSN FROM SPS_VINRange";
/*     */   
/*     */   public static final String OPTION_VINCode = "SELECT VINCode, WMI, VINPattern FROM SPS_VehicleCode";
/*     */   
/*     */   public static final String OPTION_V10Code = "SELECT Pos10Code, Pos10Value FROM SPS_VINPos10";
/*     */   
/*     */   public static final String VIT1_OPTIONS = "SELECT OptionCode, OptValue FROM SPS_VITOptions";
/*     */   
/*     */   public static final String HWO_OPTIONS = "SELECT DISTINCT g.OptionCode, h.HWName FROM SPS_Option s, SPS_OptionGroup g, SPS_HWDescription h WHERE s.CategoryCode = 'HWO' AND s.OptionGroup = g.OptionGroup AND g.OptionCode = h.HWID";
/*     */   
/*     */   public static final String HWO_IDENTS = "SELECT DISTINCT l.HWLocID, l.IdentOrder, l.VITType, l.VIT1Ident, l.VITPos FROM SPS_HWDescription h, SPS_HWLocation l WHERE h.HWID = ? AND h.HWLocID = l.HWLocID ORDER BY 1,2";
/*     */   
/*     */   public static final String OPTION_VEHICLES = "SELECT DISTINCT o.VehicleID, c.Description, f.Description FROM SPS_Option o, SPS_Categories c, SPS_FreeOptions f, SPS_OptionGroup g WHERE o.VehicleID IN (#list#)  AND o.OptionOrder = ?  AND o.CategoryCode = c.CategoryCode  AND c.LanguageID = ?  AND o.OptionGroup = g.OptionGroup  AND g.OptionCode = f.OptionCode  AND f.LanguageID = ?";
/*     */   
/*     */   public static final String HARDWARE_LOCATIONS = "SELECT HWLocID, IdentOrder, VITType, VIT1Ident, VITPos FROM SPS_HWLocation ORDER BY 1, 2";
/*     */   
/*     */   public static final String HARDWARE_CHECK = "SELECT DISTINCT hd.HWName, hl.IdentOrder, hl.VIT1Ident, hl.VITPos FROM SPS_HWDescription hd, SPS_HWLocation hl WHERE hd.HWID = ?  AND hd.HWLocID = hl.HWLocID   AND hl.VITType = ? ORDER BY hl.IdentOrder";
/*     */   
/*     */   public static final String ECU_SOFTWARE = "SELECT ECUID FROM SPS_ECUSoftware WHERE IdentType = ? AND (IdentValue = ? OR IdentValue = '*')";
/*     */   
/*     */   public static final String ECU_SOFTWARE2 = "SELECT ECUID FROM SPS_ECUSoftware WHERE IdentType = ? AND IdentValue = ?";
/*     */   
/*     */   public static final String VIT1_IDENTIFIER = "SELECT l.VIT1Ident, l.VITPos, l.IdentOrder FROM SPS_HWLocation l WHERE l.VITType = ?  AND l.HWLocID IN ( SELECT d.HWLocID FROM SPS_Hardware h, SPS_HWDescription d WHERE h.ECUID = ?  AND h.HWID = d.HWID ) ORDER BY l.IdentOrder";
/*     */   
/*     */   public static final String CONTROLLER_VIT1 = "SELECT i.VIT1Ident,i.VIT1Pos,d.Display,s.IdentValue FROM SPS_ECUSoftware s,SPS_IdentDescription i,SPS_IdentDisplay d WHERE s.ECUID = ?  AND s.IdentType = d.IdentType  AND d.LanguageID = ?  AND s.IdentType = i.IdentType  AND i.VITType = ?";
/*     */   
/*     */   public static final String HARDWARE_DESCRIPTION = "SELECT HWLocID, HWName FROM SPS_HWDescription WHERE HWID = ?";
/*     */   
/*     */   public static final String ECU_HARDWARE = "SELECT h.ECUID, d.HWName, d.HWLocID FROM SPS_Hardware h, SPS_HWDescription d WHERE h.ECUID IN (#list#)  AND h.HWID = d.HWID";
/*     */   
/*     */   public static final String CONTROLLER_HARDWARE = "SELECT d.HWName, d.HWLocID FROM SPS_Hardware h, SPS_HWDescription d WHERE h.ECUID = ?  AND h.HWID = d.HWID";
/*     */   
/*     */   public static final String CONTROLLER_DESCRIPTION = "SELECT d.ECUID, d.ReleaseDate FROM SPS_ECUDescription d WHERE d.ECUID IN (#list#)  AND d.ServiceECUFlag = 0  AND d.ValidFrom <= ?  AND d.ValidTo >= ?ORDER BY d.ReleaseDate DESC";
/*     */   
/*     */   public static final String END_MODEL = "SELECT a.IdentValue, b.VIT1Pos FROM SPS_ECUSoftware a,SPS_IdentDescription b WHERE a.ECUID = ?  AND b.IdentType = a.IdentType   AND UPPER(b.VIT2Ident) = UPPER('END_MODEL')   AND UPPER(b.VITType) = UPPER('A7')";
/*     */   
/*     */   public static final String SOFTWARE = "SELECT a.ECUID FROM SPS_ECUSoftware a WHERE IdentType = ? AND IdentValue = ?   INTERSECT SELECT a.ECUID FROM SPS_ECUSoftware a WHERE IdentType = 2 AND IdentValue = ?";
/*     */   
/*     */   public static final String CONTROLLER_FIELDFIX = "SELECT d.Description FROM SPS_FieldFixDescription d, SPS_FieldFixInformation i WHERE i.FFID = d.FFID  AND i.ECUID = ?  AND d.LanguageID = ?";
/*     */   
/*     */   public static final String CONTROLLER_PrePI = "SELECT FFID, Doc_Ref FROM SPS_PreProgramming WHERE ECUID = ?";
/*     */   
/*     */   public static final String CONTROLLER_PostPI = "SELECT FFID, Doc_Ref FROM SPS_PostProgramming WHERE ECUID = ?";
/*     */   
/*     */   public static final String PROGRAMMING_INSTRUCTION = "SELECT Description FROM SPS_FieldFixDescription WHERE FFID = ? AND LanguageID = ?";
/*     */   
/*     */   public static final String CONTROLLER_MODULES = "SELECT m.ModuleID, m.ModuleOrder, m.ModuleName, m.ModuleType FROM SPS_Modules m WHERE m.ECUID = ? ORDER BY m.ModuleOrder";
/*     */   
/*     */   public static final String MODULE_DESCRIPTIONS = "SELECT m.ModuleID, d.Description FROM SPS_Modules m, SPS_ModuleInfo i, SPS_FieldFixDescription d WHERE i.FFID = d.FFID  AND i.ModuleID = m.ModuleID  AND m.ECUID = ?  AND NOT m.ModuleType = 0  AND d.LanguageID = ?";
/*     */   
/*     */   public static final String MODULE_DESCRIPTION = "SELECT d.Description FROM SPS_ModuleInfo i, SPS_FieldFixDescription d WHERE i.FFID = d.FFID  AND i.ModuleID = ?  AND d.LanguageID = ?";
/*     */   
/*     */   public static final String MODULE_BLOB_INFO = "SELECT BLOBSIZE, BLOBCHECKSUM, DOWNLOAD_SITE FROM SPS_Blobs WHERE ModuleID = ?";
/*     */   
/*     */   public static final String MODULE_BLOB = "SELECT ModuleBlob FROM SPS_Blobs WHERE ModuleID = ?";
/*     */   
/*     */   public static final String IDENT_DISPLAYS = "SELECT IdentType, Display, LanguageID FROM SPS_IdentDisplay";
/*     */   
/*     */   public static final String HISTORY = "SELECT id.Display, es.IdentValue, ed.ReleaseDate, es.ECUID, id.IdentType FROM SPS_ECUSoftware es, SPS_ECUDescription ed, SPS_IdentDisplay id WHERE es.ECUID = ed.ECUID  AND es.IdentType = id.IdentType  AND id.LanguageID = ?  AND es.ECUID IN (#list#) ORDER BY ed.ReleaseDate DESC, id.IdentType";
/*     */   
/*     */   public static final String IDENT_DESCRIPTIONS = "SELECT DISTINCT IdentType, VITType, VIT1Ident, VIT1Pos FROM SPS_IdentDescription";
/*     */   
/*     */   public static final String HISTORY_DESCRIPTION = "SELECT ffi.ECUID, ffd.Description, ffi.Doc_Ref FROM SPS_FieldFixInformation ffi, SPS_FieldFixDescription ffd WHERE ffi.FFID = ffd.FFID  AND ffd.LanguageID = ?  AND ffi.ECUID IN (#list#) ORDER BY ffi.ECUID DESC";
/*     */   
/*     */   public static final String DOCUMENT_REFERENCE = "SELECT Doc_URI, LinkDescr_ID FROM SPS_DocRef WHERE Doc_Ref = ?";
/*     */   
/*     */   public static final String HREF_LABEL = "SELECT Description FROM SPS_FieldFixDescription WHERE FFID = ? AND LanguageID = ?";
/*     */   
/*     */   public static final String DATABASE_SECURITY = "SELECT TabName, Info FROM SPS_General";
/*     */   
/*     */   public static final String CONTROLLER_SECURITY = "SELECT SecGroupID, ECUID FROM SPS_Security";
/*     */   
/*     */   public static final String OPTION_SECURITY = "SELECT SecGroupID, OptionCode FROM SPS_SecOption";
/*     */   
/*     */   public static final String SECURITY = "SELECT SecurityID FROM SPS_SecGroups WHERE SecGroupID = ?";
/*     */   
/*     */   public static final String SECURITY_GROUP = "SELECT SecGroupID FROM SPS_SecGroups WHERE SecurityID = ?";
/*     */   
/*     */   public static final String EXCL_SECURITY = "SELECT SecGroupID, ECUID FROM SPS_ExclSecurity";
/*     */   
/*     */   public static final String HTML_INSTRUCTION = "SELECT HTML_CHARSET, HTML FROM HTML_INSTRUCTIONS WHERE LANGUAGE = ? AND INSTRUCTION_ID = ?";
/*     */   
/*     */   public static final String INSTRUCTION_GRAPHIC = "SELECT GRAPHIC_MIMETYPE, GRAPHIC FROM INSTRUCTION_GRAPHICS WHERE GRAPHIC_ID = ?";
/*     */   
/*     */   public static final String SECURITY_CODE_REQUIRED = "SELECT * FROM SPS_SecCodeRequired WHERE VehicleID = ?";
/*     */   
/*     */   public static final String SECURITY_CODE = "SELECT SecurityCode FROM SPS_SecurityCode WHERE VIN = ?";
/*     */   
/*     */   public static final String ASBUILT_VINID = "select distinct VINID from SPS_AsBuilt_VIN v, SPS_AsBuilt_VINDescription d, SPS_AsBuilt_VINSerial s where v.VINDescID = d.VINDescID  and d.WMI = ?  and d.VIN_4to8 = ?  and ((d.VIN_9 = ?) or (d.VIN_9 = '?'))  and d.VIN_10 = ?  and d.VIN_11 = ?  and v.VINSerID = s.VINSerID  and s.VIN_12to17 = ?";
/*     */   
/*     */   public static final String ASBUILT_VEHICLE_CONTROLLERS = "SELECT v.VehicleID, s.DeviceID, s.ControllerID, s.VCI, c.ECUID FROM SPS_AsBuilt_Vehicle v, SPS_AsBuilt_VCI s, SPS_Configuration c WHERE s.VINID = ? AND s.VCI = v.VCI AND v.VehicleID = c.VehicleID (+)";
/*     */   
/*     */   public static final String ASBUILT_VEHICLE_FUNCTIONS = "SELECT v.VehicleID, s.DeviceID, s.VCI, c.ECUID, s.FunctionID FROM SPS_AsBuilt_Vehicle v left outer join SPS_Configuration c on v.VehicleID = c.VehicleID, SPS_AsBuilt_VCI s WHERE s.VINID = ? AND s.VCI = v.VCI";
/*     */   
/*     */   public static final String ASBUILT_VEHICLE_FUNCTION = "SELECT v.VehicleID, s.DeviceID, s.VCI, c.ECUID FROM SPS_AsBuilt_Vehicle v left outer join SPS_Configuration c on v.VehicleID = c.VehicleID, SPS_AsBuilt_VCI s WHERE s.VINID = ? AND s.VCI = v.VCI AND s.FunctionID = ?";
/*     */   
/*     */   public static final String ASBUILT_VEHICLES = "SELECT DISTINCT v.VehicleID FROM SPS_AsBuilt_Vehicle v, SPS_AsBuilt_VCI s, SPS_Configuration c WHERE s.VINID = ?  AND c.ECUID = ?  AND v.VCI = s.VCI  AND v.VehicleID = c.VehicleID";
/*     */   
/*     */   public static final String DEALERVCI_VEHICLES = "SELECT v.VehicleID FROM SPS_AsBuilt_Vehicle v WHERE v.VCI = ?";
/*     */   
/*     */   public static final String VCI_PROGRAMMING_NEEDED = "SELECT v.VehicleID FROM   SPS_Vehicle v WHERE  v.SalesMakeCode = ? AND    v.ModelCode = ? AND    v.ModelYearCode = ? AND    v.DeviceID = ? INTERSECT SELECT c.VehicleID FROM   SPS_Configuration c";
/*     */   
/*     */   public static final String WINLANGUAGES = "SELECT DISTINCT LanguageAcronym, ACP, LCID FROM WinLanguages";
/*     */   
/*     */   public static final String TYPE4_STRINGS = "SELECT String_ID, Language_Code, Description FROM Type4_String order by Language_Code";
/*     */   
/*     */   public static final String SECURITY_ENFORCED = "SELECT * FROM SPS_SecCodeRequired WHERE VehicleID = ?";
/*     */   
/*     */   public static final String RPO_FILTER = "SELECT RPOFilter FROM SPS_RPOFilter WHERE ECUID = ?";
/*     */ 
/*     */   
/*     */   static {
/* 237 */     defaultSQL.put("SELECT RPOFilter FROM SPS_RPOFilter WHERE ECUID = ?", "SELECT RPOFilter FROM SPS_RPOFilter WHERE ECUID = ?");
/* 238 */     defaultSQL.put("SELECT * FROM SPS_SecCodeRequired WHERE VehicleID = ?", "SELECT * FROM SPS_SecCodeRequired WHERE VehicleID = ?");
/* 239 */     defaultSQL.put("SELECT ControllerID FROM SPS_XML_Support", "SELECT ControllerID FROM SPS_XML_Support");
/* 240 */     defaultSQL.put("SELECT FunctionID FROM SPS_XML_Support", "SELECT FunctionID FROM SPS_XML_Support");
/* 241 */     defaultSQL.put("SELECT RPO_String_ID, Model_Designator_ID, XML_ID FROM XML_VCI WHERE VCI = ?", "SELECT RPO_String_ID, Model_Designator_ID, XML_ID FROM XML_VCI WHERE VCI = ?");
/* 242 */     defaultSQL.put("SELECT RPO_String FROM RPO_String WHERE RPO_String_ID = ?", "SELECT RPO_String FROM RPO_String WHERE RPO_String_ID = ?");
/* 243 */     defaultSQL.put("SELECT Model_Designator FROM Model_Designator WHERE Model_Designator_ID = ?", "SELECT Model_Designator FROM Model_Designator WHERE Model_Designator_ID = ?");
/* 244 */     defaultSQL.put("SELECT DISTINCT c.VehicleID, c.ECUID, i.DeviceID, i.ControllerID FROM SPS_Configuration c, SPS_ECUDescription d, SPS_AsBuilt_Vehicle v, SPS_AsBuilt_VCI i WHERE c.VehicleID IN (#VEHICLE_LIST#) AND d.ECUID = c.ECUID AND d.ServiceECUFlag = 0 AND d.ValidFrom <= ? AND d.ValidTo >= ? AND c.VehicleID = v.VehicleID AND v.VCI = i.VCI ", "SELECT DISTINCT c.VehicleID, c.ECUID, i.DeviceID, i.ControllerID FROM SPS_Configuration c, SPS_ECUDescription d, SPS_AsBuilt_Vehicle v, SPS_AsBuilt_VCI i WHERE c.VehicleID IN (#VEHICLE_LIST#) AND d.ECUID = c.ECUID AND d.ServiceECUFlag = 0 AND d.ValidFrom <= ? AND d.ValidTo >= ? AND c.VehicleID = v.VehicleID AND v.VCI = i.VCI ");
/* 245 */     defaultSQL.put("SELECT DISTINCT c.VehicleID, c.ECUID, i.DeviceID, i.FunctionID FROM SPS_Configuration c, SPS_ECUDescription d, SPS_AsBuilt_Vehicle v, SPS_AsBuilt_VCI i WHERE c.VehicleID IN (#VEHICLE_LIST#) AND d.ECUID = c.ECUID AND d.ServiceECUFlag = 0 AND d.ValidFrom <= ? AND d.ValidTo >= ? AND c.VehicleID = v.VehicleID AND v.VCI = i.VCI ", "SELECT DISTINCT c.VehicleID, c.ECUID, i.DeviceID, i.FunctionID FROM SPS_Configuration c, SPS_ECUDescription d, SPS_AsBuilt_Vehicle v, SPS_AsBuilt_VCI i WHERE c.VehicleID IN (#VEHICLE_LIST#) AND d.ECUID = c.ECUID AND d.ServiceECUFlag = 0 AND d.ValidFrom <= ? AND d.ValidTo >= ? AND c.VehicleID = v.VehicleID AND v.VCI = i.VCI ");
/* 246 */     defaultSQL.put("SELECT v.VehicleID FROM SPS_AsBuilt_Vehicle v WHERE v.VCI = ?", "SELECT v.VehicleID FROM SPS_AsBuilt_Vehicle v WHERE v.VCI = ?");
/* 247 */     defaultSQL.put("SELECT v.VehicleID FROM   SPS_Vehicle v WHERE  v.SalesMakeCode = ? AND    v.ModelCode = ? AND    v.ModelYearCode = ? AND    v.DeviceID = ? INTERSECT SELECT c.VehicleID FROM   SPS_Configuration c", "SELECT v.VehicleID FROM   SPS_Vehicle v WHERE  v.SalesMakeCode = ? AND    v.ModelCode = ? AND    v.ModelYearCode = ? AND    v.DeviceID = ? INTERSECT SELECT c.VehicleID FROM   SPS_Configuration c");
/* 248 */     defaultSQL.put("select distinct VINID from SPS_AsBuilt_VIN v, SPS_AsBuilt_VINDescription d, SPS_AsBuilt_VINSerial s where v.VINDescID = d.VINDescID  and d.WMI = ?  and d.VIN_4to8 = ?  and ((d.VIN_9 = ?) or (d.VIN_9 = '?'))  and d.VIN_10 = ?  and d.VIN_11 = ?  and v.VINSerID = s.VINSerID  and s.VIN_12to17 = ?", "select distinct VINID from SPS_AsBuilt_VIN v, SPS_AsBuilt_VINDescription d, SPS_AsBuilt_VINSerial s where v.VINDescID = d.VINDescID  and d.WMI = ?  and d.VIN_4to8 = ?  and ((d.VIN_9 = ?) or (d.VIN_9 = '?'))  and d.VIN_10 = ?  and d.VIN_11 = ?  and v.VINSerID = s.VINSerID  and s.VIN_12to17 = ?");
/* 249 */     defaultSQL.put("SELECT v.VehicleID, s.DeviceID, s.ControllerID, s.VCI, c.ECUID FROM SPS_AsBuilt_Vehicle v, SPS_AsBuilt_VCI s, SPS_Configuration c WHERE s.VINID = ? AND s.VCI = v.VCI AND v.VehicleID = c.VehicleID (+)", "SELECT v.VehicleID, s.DeviceID, s.ControllerID, s.VCI, c.ECUID FROM SPS_AsBuilt_Vehicle v, SPS_AsBuilt_VCI s, SPS_Configuration c WHERE s.VINID = ? AND s.VCI = v.VCI AND v.VehicleID = c.VehicleID (+)");
/* 250 */     defaultSQL.put("SELECT v.VehicleID, s.DeviceID, s.VCI, c.ECUID, s.FunctionID FROM SPS_AsBuilt_Vehicle v left outer join SPS_Configuration c on v.VehicleID = c.VehicleID, SPS_AsBuilt_VCI s WHERE s.VINID = ? AND s.VCI = v.VCI", "SELECT v.VehicleID, s.DeviceID, s.VCI, c.ECUID, s.FunctionID FROM SPS_AsBuilt_Vehicle v left outer join SPS_Configuration c on v.VehicleID = c.VehicleID, SPS_AsBuilt_VCI s WHERE s.VINID = ? AND s.VCI = v.VCI");
/* 251 */     defaultSQL.put("SELECT v.VehicleID, s.DeviceID, s.VCI, c.ECUID FROM SPS_AsBuilt_Vehicle v left outer join SPS_Configuration c on v.VehicleID = c.VehicleID, SPS_AsBuilt_VCI s WHERE s.VINID = ? AND s.VCI = v.VCI AND s.FunctionID = ?", "SELECT v.VehicleID, s.DeviceID, s.VCI, c.ECUID FROM SPS_AsBuilt_Vehicle v left outer join SPS_Configuration c on v.VehicleID = c.VehicleID, SPS_AsBuilt_VCI s WHERE s.VINID = ? AND s.VCI = v.VCI AND s.FunctionID = ?");
/* 252 */     defaultSQL.put("SELECT DISTINCT v.VehicleID FROM SPS_AsBuilt_Vehicle v, SPS_AsBuilt_VCI s, SPS_Configuration c WHERE s.VINID = ?  AND c.ECUID = ?  AND v.VCI = s.VCI  AND v.VehicleID = c.VehicleID", "SELECT DISTINCT v.VehicleID FROM SPS_AsBuilt_Vehicle v, SPS_AsBuilt_VCI s, SPS_Configuration c WHERE s.VINID = ?  AND c.ECUID = ?  AND v.VCI = s.VCI  AND v.VehicleID = c.VehicleID");
/* 253 */     defaultSQL.put("SELECT SalesMakeCode, Attribute, Value FROM SPS_DBSettings", "SELECT SalesMakeCode, Attribute, Value FROM SPS_DBSettings");
/* 254 */     defaultSQL.put("SELECT SystemType, LanguageID, Description FROM SPS_SystemTypes", "SELECT SystemType, LanguageID, Description FROM SPS_SystemTypes");
/* 255 */     defaultSQL.put("SELECT DISTINCT SalesMakeCode, CVN FROM SPS_CVN WHERE LOWER(PartNumber) = ?", "SELECT DISTINCT SalesMakeCode, CVN FROM SPS_CVN WHERE LOWER(PartNumber) = ?");
/* 256 */     defaultSQL.put("SELECT DISTINCT b.IdentType, b.VIT1Ident, b.VIT1Pos, a.VINPattern, a.SNFrom, a.SNTo, a.HWID, a.ModelYearCode FROM SPS_Schema a, SPS_IdentDescription b WHERE a.WMI = ?  AND ((a.SalesMakeCode = ?) OR (a.SalesMakeCode is null))  AND a.Protocol = ?  AND b.VITType = ?  AND b.IdentType = a.IdentType", "SELECT DISTINCT b.IdentType, b.VIT1Ident, b.VIT1Pos, a.VINPattern, a.SNFrom, a.SNTo, a.HWID, a.ModelYearCode FROM SPS_Schema a, SPS_IdentDescription b WHERE a.WMI = ?  AND ((a.SalesMakeCode = ?) OR (a.SalesMakeCode is null))  AND a.Protocol = ?  AND b.VITType = ?  AND b.IdentType = a.IdentType");
/* 257 */     defaultSQL.put("SELECT DISTINCT IdentType, VITType, VIT1Ident, VIT1Pos FROM SPS_IdentDescription", "SELECT DISTINCT IdentType, VITType, VIT1Ident, VIT1Pos FROM SPS_IdentDescription");
/* 258 */     defaultSQL.put(" AND (a.ModelYearCode is null OR a.ModelYearCode IN (?, 0, -1))", " AND (a.ModelYearCode is null OR a.ModelYearCode IN (?, 0, -1))");
/* 259 */     defaultSQL.put(" AND (a.SystemType is null OR a.SystemType IN (0, ?))", " AND (a.SystemType is null OR a.SystemType IN (0, ?))");
/* 260 */     defaultSQL.put("SELECT SalesMakeCode, SalesMake FROM SPS_SalesMake", "SELECT SalesMakeCode, SalesMake FROM SPS_SalesMake");
/* 261 */     defaultSQL.put("SELECT DISTINCT ModelCode, Description FROM SPS_ModelDescription", "SELECT DISTINCT ModelCode, Description FROM SPS_ModelDescription");
/* 262 */     defaultSQL.put("SELECT ModelYearCode, ModelYear FROM SPS_ModelYear", "SELECT ModelYearCode, ModelYear FROM SPS_ModelYear");
/*     */     
/* 264 */     defaultSQL.put("SELECT ECUID, ServiceECUFlag, ReleaseDate, ValidFrom, ValidTo FROM SPS_ECUDescription", "SELECT ECUID, ServiceECUFlag, ReleaseDate, ValidFrom, ValidTo FROM SPS_ECUDescription");
/* 265 */     defaultSQL.put("SELECT VehicleID FROM SPS_Configuration WHERE ECUID = ?", "SELECT VehicleID FROM SPS_Configuration WHERE ECUID = ?");
/* 266 */     defaultSQL.put("SELECT v.VehicleID FROM SPS_Vehicle v WHERE v.SalesMakeCode = ?  AND v.ModelCode = ?  AND v.ModelYearCode = ?", "SELECT v.VehicleID FROM SPS_Vehicle v WHERE v.SalesMakeCode = ?  AND v.ModelCode = ?  AND v.ModelYearCode = ?");
/* 267 */     defaultSQL.put("SELECT v.VehicleID, v.DeviceID, c.ECUID FROM SPS_Configuration c right outer join SPS_Vehicle v on v.VehicleID = c.VehicleID WHERE v.SalesMakeCode = ?  AND v.ModelCode = ?  AND v.ModelYearCode = ? AND v.FunctionID = ?", "SELECT v.VehicleID, v.DeviceID, c.ECUID FROM SPS_Configuration c right outer join SPS_Vehicle v on v.VehicleID = c.VehicleID WHERE v.SalesMakeCode = ?  AND v.ModelCode = ?  AND v.ModelYearCode = ? AND v.FunctionID = ?");
/*     */     
/* 269 */     defaultSQL.put("SELECT c.ECUID FROM SPS_Configuration c WHERE c.VehicleID = ?", "SELECT c.ECUID FROM SPS_Configuration c WHERE c.VehicleID = ?");
/* 270 */     defaultSQL.put("SELECT v.VehicleID, v.DeviceID, v.ControllerID, c.ECUID FROM SPS_Configuration c, SPS_Vehicle v WHERE v.VehicleID = c.VehicleID (+)  AND v.SalesMakeCode = ?  AND v.ModelCode = ?  AND v.ModelYearCode = ?", "SELECT v.VehicleID, v.DeviceID, v.ControllerID, c.ECUID FROM SPS_Configuration c, SPS_Vehicle v WHERE v.VehicleID = c.VehicleID (+)  AND v.SalesMakeCode = ?  AND v.ModelCode = ?  AND v.ModelYearCode = ?");
/* 271 */     defaultSQL.put("SELECT v.VehicleID, v.DeviceID, v.FunctionID, c.ECUID FROM SPS_Configuration c, SPS_Vehicle v WHERE v.VehicleID = c.VehicleID (+)  AND v.SalesMakeCode = ?  AND v.ModelCode = ?  AND v.ModelYearCode = ?", "SELECT v.VehicleID, v.DeviceID, v.FunctionID, c.ECUID FROM SPS_Configuration c, SPS_Vehicle v WHERE v.VehicleID = c.VehicleID (+)  AND v.SalesMakeCode = ?  AND v.ModelCode = ?  AND v.ModelYearCode = ?");
/* 272 */     defaultSQL.put("SELECT v.BaseVehicleID, v.ControllerID, v.SequenceID, v.WMI, v.VDS, v.VIS, o.CategoryCode, o.OptionGroup, o.OptionOrder FROM SPS_BaseVehicle v left outer join SPS_BaseVehOption o on v.BaseVehicleID = o.BaseVehicleID WHERE v.SalesMakeCode = ? AND v.ModelCode = ? AND v.ModelYearCode = ?", "SELECT v.BaseVehicleID, v.ControllerID, v.SequenceID, v.WMI, v.VDS, v.VIS, o.CategoryCode, o.OptionGroup, o.OptionOrder FROM SPS_BaseVehicle v left outer join SPS_BaseVehOption o on v.BaseVehicleID = o.BaseVehicleID WHERE v.SalesMakeCode = ? AND v.ModelCode = ? AND v.ModelYearCode = ?");
/* 273 */     defaultSQL.put("SELECT ControllerID, LanguageID, Description, ShortDescr FROM SPS_Controller", "SELECT ControllerID, LanguageID, Description, ShortDescr FROM SPS_Controller");
/* 274 */     defaultSQL.put("SELECT ControllerID, FunctionID, LanguageID, Description, ShortDescr FROM SPS_Function", "SELECT ControllerID, FunctionID, LanguageID, Description, ShortDescr FROM SPS_Function");
/* 275 */     defaultSQL.put("SELECT SequenceID, FunctionID, ReqInfoID, FunctionOrder, OnSameSW FROM SPS_Sequence ORDER BY 1,2,4", "SELECT SequenceID, FunctionID, ReqInfoID, FunctionOrder, OnSameSW FROM SPS_Sequence ORDER BY 1,2,4");
/* 276 */     defaultSQL.put("SELECT SequenceID, LanguageID, Description, DisplayOrder FROM SPS_SeqDisplay", "SELECT SequenceID, LanguageID, Description, DisplayOrder FROM SPS_SeqDisplay");
/* 277 */     defaultSQL.put("SELECT v.VINID FROM SPS_AsBuilt_VIN v, SPS_AsBuilt_VINDescription d, SPS_AsBuilt_VINSerial s  WHERE d.VINDescID = v.VINDescID  AND v.VINSerID = s.VINSerID  AND d.WMI = ?  AND d.VDS = ?  AND d.VIN_9 = ?  AND d.VIN_10 = ?  AND d.VIN_11 = ?  AND s.SerNum = ?", "SELECT v.VINID FROM SPS_AsBuilt_VIN v, SPS_AsBuilt_VINDescription d, SPS_AsBuilt_VINSerial s  WHERE d.VINDescID = v.VINDescID  AND v.VINSerID = s.VINSerID  AND d.WMI = ?  AND d.VDS = ?  AND d.VIN_9 = ?  AND d.VIN_10 = ?  AND d.VIN_11 = ?  AND s.SerNum = ?");
/* 278 */     defaultSQL.put("SELECT ReqInfoID, ReqInfoMeth_Group FROM SPS_ReqInfo", "SELECT ReqInfoID, ReqInfoMeth_Group FROM SPS_ReqInfo");
/* 279 */     defaultSQL.put("SELECT DISTINCT v.ReqInfoID, v.WMI, v.VDS, v.VIS, o.CategoryCode, o.OptionGroup, o.OptionOrder FROM SPS_BaseVehicle v, SPS_BaseVehOption o WHERE v.ReqInfoID = o.ReqInfoID (+)  AND v.SalesMakeCode = ?  AND ((v.ModelCode = ?) OR (v.ModelCode is null))  AND ((v.ModelYearCode = ?) OR (v.ModelYearCode is null))  AND (v.ControllerID = ?)ORDER BY o.OptionOrder", "SELECT DISTINCT v.ReqInfoID, v.WMI, v.VDS, v.VIS, o.CategoryCode, o.OptionGroup, o.OptionOrder FROM SPS_BaseVehicle v, SPS_BaseVehOption o WHERE v.ReqInfoID = o.ReqInfoID (+)  AND v.SalesMakeCode = ?  AND ((v.ModelCode = ?) OR (v.ModelCode is null))  AND ((v.ModelYearCode = ?) OR (v.ModelYearCode is null))  AND (v.ControllerID = ?)ORDER BY o.OptionOrder");
/* 280 */     defaultSQL.put("SELECT DISTINCT VehicleID, CategoryCode, OptionGroup, OptionOrder, NoPreEval FROM SPS_Option o WHERE VehicleID IN (#list#)ORDER BY OptionOrder", "SELECT DISTINCT VehicleID, CategoryCode, OptionGroup, OptionOrder, NoPreEval FROM SPS_Option o WHERE VehicleID IN (#list#)ORDER BY OptionOrder");
/* 281 */     defaultSQL.put("SELECT OptionCode, LanguageID, Description FROM SPS_FreeOptions", "SELECT OptionCode, LanguageID, Description FROM SPS_FreeOptions");
/* 282 */     defaultSQL.put("SELECT CategoryCode, LanguageID, Description FROM SPS_Categories", "SELECT CategoryCode, LanguageID, Description FROM SPS_Categories");
/* 283 */     defaultSQL.put("SELECT CategoryCode, VITType, CatValue, Position FROM SPS_VITCategories", "SELECT CategoryCode, VITType, CatValue, Position FROM SPS_VITCategories");
/* 284 */     defaultSQL.put("SELECT CategoryCode, OptionCode FROM SPS_FreeOptionSet", "SELECT CategoryCode, OptionCode FROM SPS_FreeOptionSet");
/* 285 */     defaultSQL.put("SELECT OptionGroup, OptionCode FROM SPS_OptionGroup", "SELECT OptionGroup, OptionCode FROM SPS_OptionGroup");
/* 286 */     defaultSQL.put("SELECT DISTINCT Description FROM SPS_FreeOptions WHERE Description LIKE 'Z%'", "SELECT DISTINCT Description FROM SPS_FreeOptions WHERE Description LIKE 'Z%'");
/* 287 */     defaultSQL.put("SELECT RangeCode, FromSN, ToSN FROM SPS_VINRange", "SELECT RangeCode, FromSN, ToSN FROM SPS_VINRange");
/* 288 */     defaultSQL.put("SELECT VINCode, WMI, VINPattern FROM SPS_VehicleCode", "SELECT VINCode, WMI, VINPattern FROM SPS_VehicleCode");
/* 289 */     defaultSQL.put("SELECT Pos10Code, Pos10Value FROM SPS_VINPos10", "SELECT Pos10Code, Pos10Value FROM SPS_VINPos10");
/* 290 */     defaultSQL.put("SELECT OptionCode, OptValue FROM SPS_VITOptions", "SELECT OptionCode, OptValue FROM SPS_VITOptions");
/* 291 */     defaultSQL.put("SELECT DISTINCT g.OptionCode, h.HWName FROM SPS_Option s, SPS_OptionGroup g, SPS_HWDescription h WHERE s.CategoryCode = 'HWO' AND s.OptionGroup = g.OptionGroup AND g.OptionCode = h.HWID", "SELECT DISTINCT g.OptionCode, h.HWName FROM SPS_Option s, SPS_OptionGroup g, SPS_HWDescription h WHERE s.CategoryCode = 'HWO' AND s.OptionGroup = g.OptionGroup AND g.OptionCode = h.HWID");
/* 292 */     defaultSQL.put("SELECT DISTINCT l.HWLocID, l.IdentOrder, l.VITType, l.VIT1Ident, l.VITPos FROM SPS_HWDescription h, SPS_HWLocation l WHERE h.HWID = ? AND h.HWLocID = l.HWLocID ORDER BY 1,2", "SELECT DISTINCT l.HWLocID, l.IdentOrder, l.VITType, l.VIT1Ident, l.VITPos FROM SPS_HWDescription h, SPS_HWLocation l WHERE h.HWID = ? AND h.HWLocID = l.HWLocID ORDER BY 1,2");
/* 293 */     defaultSQL.put("SELECT DISTINCT o.VehicleID, c.Description, f.Description FROM SPS_Option o, SPS_Categories c, SPS_FreeOptions f, SPS_OptionGroup g WHERE o.VehicleID IN (#list#)  AND o.OptionOrder = ?  AND o.CategoryCode = c.CategoryCode  AND c.LanguageID = ?  AND o.OptionGroup = g.OptionGroup  AND g.OptionCode = f.OptionCode  AND f.LanguageID = ?", "SELECT DISTINCT o.VehicleID, c.Description, f.Description FROM SPS_Option o, SPS_Categories c, SPS_FreeOptions f, SPS_OptionGroup g WHERE o.VehicleID IN (#list#)  AND o.OptionOrder = ?  AND o.CategoryCode = c.CategoryCode  AND c.LanguageID = ?  AND o.OptionGroup = g.OptionGroup  AND g.OptionCode = f.OptionCode  AND f.LanguageID = ?");
/* 294 */     defaultSQL.put("SELECT HWLocID, IdentOrder, VITType, VIT1Ident, VITPos FROM SPS_HWLocation ORDER BY 1, 2", "SELECT HWLocID, IdentOrder, VITType, VIT1Ident, VITPos FROM SPS_HWLocation ORDER BY 1, 2");
/* 295 */     defaultSQL.put("SELECT DISTINCT hd.HWName, hl.IdentOrder, hl.VIT1Ident, hl.VITPos FROM SPS_HWDescription hd, SPS_HWLocation hl WHERE hd.HWID = ?  AND hd.HWLocID = hl.HWLocID   AND hl.VITType = ? ORDER BY hl.IdentOrder", "SELECT DISTINCT hd.HWName, hl.IdentOrder, hl.VIT1Ident, hl.VITPos FROM SPS_HWDescription hd, SPS_HWLocation hl WHERE hd.HWID = ?  AND hd.HWLocID = hl.HWLocID   AND hl.VITType = ? ORDER BY hl.IdentOrder");
/* 296 */     defaultSQL.put("SELECT ECUID FROM SPS_ECUSoftware WHERE IdentType = ? AND (IdentValue = ? OR IdentValue = '*')", "SELECT ECUID FROM SPS_ECUSoftware WHERE IdentType = ? AND (IdentValue = ? OR IdentValue = '*')");
/* 297 */     defaultSQL.put("SELECT ECUID FROM SPS_ECUSoftware WHERE IdentType = ? AND IdentValue = ?", "SELECT ECUID FROM SPS_ECUSoftware WHERE IdentType = ? AND IdentValue = ?");
/* 298 */     defaultSQL.put("SELECT l.VIT1Ident, l.VITPos, l.IdentOrder FROM SPS_HWLocation l WHERE l.VITType = ?  AND l.HWLocID IN ( SELECT d.HWLocID FROM SPS_Hardware h, SPS_HWDescription d WHERE h.ECUID = ?  AND h.HWID = d.HWID ) ORDER BY l.IdentOrder", "SELECT l.VIT1Ident, l.VITPos, l.IdentOrder FROM SPS_HWLocation l WHERE l.VITType = ?  AND l.HWLocID IN ( SELECT d.HWLocID FROM SPS_Hardware h, SPS_HWDescription d WHERE h.ECUID = ?  AND h.HWID = d.HWID ) ORDER BY l.IdentOrder");
/* 299 */     defaultSQL.put("SELECT i.VIT1Ident,i.VIT1Pos,d.Display,s.IdentValue FROM SPS_ECUSoftware s,SPS_IdentDescription i,SPS_IdentDisplay d WHERE s.ECUID = ?  AND s.IdentType = d.IdentType  AND d.LanguageID = ?  AND s.IdentType = i.IdentType  AND i.VITType = ?", "SELECT i.VIT1Ident,i.VIT1Pos,d.Display,s.IdentValue FROM SPS_ECUSoftware s,SPS_IdentDescription i,SPS_IdentDisplay d WHERE s.ECUID = ?  AND s.IdentType = d.IdentType  AND d.LanguageID = ?  AND s.IdentType = i.IdentType  AND i.VITType = ?");
/* 300 */     defaultSQL.put("SELECT HWLocID, HWName FROM SPS_HWDescription WHERE HWID = ?", "SELECT HWLocID, HWName FROM SPS_HWDescription WHERE HWID = ?");
/* 301 */     defaultSQL.put("SELECT h.ECUID, d.HWName, d.HWLocID FROM SPS_Hardware h, SPS_HWDescription d WHERE h.ECUID IN (#list#)  AND h.HWID = d.HWID", "SELECT h.ECUID, d.HWName, d.HWLocID FROM SPS_Hardware h, SPS_HWDescription d WHERE h.ECUID IN (#list#)  AND h.HWID = d.HWID");
/* 302 */     defaultSQL.put("SELECT d.HWName, d.HWLocID FROM SPS_Hardware h, SPS_HWDescription d WHERE h.ECUID = ?  AND h.HWID = d.HWID", "SELECT d.HWName, d.HWLocID FROM SPS_Hardware h, SPS_HWDescription d WHERE h.ECUID = ?  AND h.HWID = d.HWID");
/* 303 */     defaultSQL.put("SELECT d.ECUID, d.ReleaseDate FROM SPS_ECUDescription d WHERE d.ECUID IN (#list#)  AND d.ServiceECUFlag = 0  AND d.ValidFrom <= ?  AND d.ValidTo >= ?ORDER BY d.ReleaseDate DESC", "SELECT d.ECUID, d.ReleaseDate FROM SPS_ECUDescription d WHERE d.ECUID IN (#list#)  AND d.ServiceECUFlag = 0  AND d.ValidFrom <= ?  AND d.ValidTo >= ?ORDER BY d.ReleaseDate DESC");
/* 304 */     defaultSQL.put("SELECT a.IdentValue, b.VIT1Pos FROM SPS_ECUSoftware a,SPS_IdentDescription b WHERE a.ECUID = ?  AND b.IdentType = a.IdentType   AND UPPER(b.VIT2Ident) = UPPER('END_MODEL')   AND UPPER(b.VITType) = UPPER('A7')", "SELECT a.IdentValue, b.VIT1Pos FROM SPS_ECUSoftware a,SPS_IdentDescription b WHERE a.ECUID = ?  AND b.IdentType = a.IdentType   AND UPPER(b.VIT2Ident) = UPPER('END_MODEL')   AND UPPER(b.VITType) = UPPER('A7')");
/* 305 */     defaultSQL.put("SELECT a.ECUID FROM SPS_ECUSoftware a WHERE IdentType = ? AND IdentValue = ?   INTERSECT SELECT a.ECUID FROM SPS_ECUSoftware a WHERE IdentType = 2 AND IdentValue = ?", "SELECT a.ECUID FROM SPS_ECUSoftware a WHERE IdentType = ? AND IdentValue = ?   INTERSECT SELECT a.ECUID FROM SPS_ECUSoftware a WHERE IdentType = 2 AND IdentValue = ?");
/* 306 */     defaultSQL.put("SELECT d.Description FROM SPS_FieldFixDescription d, SPS_FieldFixInformation i WHERE i.FFID = d.FFID  AND i.ECUID = ?  AND d.LanguageID = ?", "SELECT d.Description FROM SPS_FieldFixDescription d, SPS_FieldFixInformation i WHERE i.FFID = d.FFID  AND i.ECUID = ?  AND d.LanguageID = ?");
/* 307 */     defaultSQL.put("SELECT FFID, Doc_Ref FROM SPS_PreProgramming WHERE ECUID = ?", "SELECT FFID, Doc_Ref FROM SPS_PreProgramming WHERE ECUID = ?");
/* 308 */     defaultSQL.put("SELECT FFID, Doc_Ref FROM SPS_PostProgramming WHERE ECUID = ?", "SELECT FFID, Doc_Ref FROM SPS_PostProgramming WHERE ECUID = ?");
/* 309 */     defaultSQL.put("SELECT Description FROM SPS_FieldFixDescription WHERE FFID = ? AND LanguageID = ?", "SELECT Description FROM SPS_FieldFixDescription WHERE FFID = ? AND LanguageID = ?");
/* 310 */     defaultSQL.put("SELECT m.ModuleID, m.ModuleOrder, m.ModuleName, m.ModuleType FROM SPS_Modules m WHERE m.ECUID = ? ORDER BY m.ModuleOrder", "SELECT m.ModuleID, m.ModuleOrder, m.ModuleName, m.ModuleType FROM SPS_Modules m WHERE m.ECUID = ? ORDER BY m.ModuleOrder");
/* 311 */     defaultSQL.put("SELECT m.ModuleID, d.Description FROM SPS_Modules m, SPS_ModuleInfo i, SPS_FieldFixDescription d WHERE i.FFID = d.FFID  AND i.ModuleID = m.ModuleID  AND m.ECUID = ?  AND NOT m.ModuleType = 0  AND d.LanguageID = ?", "SELECT m.ModuleID, d.Description FROM SPS_Modules m, SPS_ModuleInfo i, SPS_FieldFixDescription d WHERE i.FFID = d.FFID  AND i.ModuleID = m.ModuleID  AND m.ECUID = ?  AND NOT m.ModuleType = 0  AND d.LanguageID = ?");
/* 312 */     defaultSQL.put("SELECT d.Description FROM SPS_ModuleInfo i, SPS_FieldFixDescription d WHERE i.FFID = d.FFID  AND i.ModuleID = ?  AND d.LanguageID = ?", "SELECT d.Description FROM SPS_ModuleInfo i, SPS_FieldFixDescription d WHERE i.FFID = d.FFID  AND i.ModuleID = ?  AND d.LanguageID = ?");
/* 313 */     defaultSQL.put("SELECT BLOBSIZE, BLOBCHECKSUM, DOWNLOAD_SITE FROM SPS_Blobs WHERE ModuleID = ?", "SELECT BLOBSIZE, BLOBCHECKSUM, DOWNLOAD_SITE FROM SPS_Blobs WHERE ModuleID = ?");
/* 314 */     defaultSQL.put("SELECT ModuleBlob FROM SPS_Blobs WHERE ModuleID = ?", "SELECT ModuleBlob FROM SPS_Blobs WHERE ModuleID = ?");
/* 315 */     defaultSQL.put("SELECT IdentType, Display, LanguageID FROM SPS_IdentDisplay", "SELECT IdentType, Display, LanguageID FROM SPS_IdentDisplay");
/* 316 */     defaultSQL.put("SELECT id.Display, es.IdentValue, ed.ReleaseDate, es.ECUID, id.IdentType FROM SPS_ECUSoftware es, SPS_ECUDescription ed, SPS_IdentDisplay id WHERE es.ECUID = ed.ECUID  AND es.IdentType = id.IdentType  AND id.LanguageID = ?  AND es.ECUID IN (#list#) ORDER BY ed.ReleaseDate DESC, id.IdentType", "SELECT id.Display, es.IdentValue, ed.ReleaseDate, es.ECUID, id.IdentType FROM SPS_ECUSoftware es, SPS_ECUDescription ed, SPS_IdentDisplay id WHERE es.ECUID = ed.ECUID  AND es.IdentType = id.IdentType  AND id.LanguageID = ?  AND es.ECUID IN (#list#) ORDER BY ed.ReleaseDate DESC, id.IdentType");
/* 317 */     defaultSQL.put("SELECT ffi.ECUID, ffd.Description, ffi.Doc_Ref FROM SPS_FieldFixInformation ffi, SPS_FieldFixDescription ffd WHERE ffi.FFID = ffd.FFID  AND ffd.LanguageID = ?  AND ffi.ECUID IN (#list#) ORDER BY ffi.ECUID DESC", "SELECT ffi.ECUID, ffd.Description, ffi.Doc_Ref FROM SPS_FieldFixInformation ffi, SPS_FieldFixDescription ffd WHERE ffi.FFID = ffd.FFID  AND ffd.LanguageID = ?  AND ffi.ECUID IN (#list#) ORDER BY ffi.ECUID DESC");
/* 318 */     defaultSQL.put("SELECT Doc_URI, LinkDescr_ID FROM SPS_DocRef WHERE Doc_Ref = ?", "SELECT Doc_URI, LinkDescr_ID FROM SPS_DocRef WHERE Doc_Ref = ?");
/* 319 */     defaultSQL.put("SELECT Description FROM SPS_FieldFixDescription WHERE FFID = ? AND LanguageID = ?", "SELECT Description FROM SPS_FieldFixDescription WHERE FFID = ? AND LanguageID = ?");
/* 320 */     defaultSQL.put("SELECT TabName, Info FROM SPS_General", "SELECT TabName, Info FROM SPS_General");
/* 321 */     defaultSQL.put("SELECT SecGroupID, ECUID FROM SPS_Security", "SELECT SecGroupID, ECUID FROM SPS_Security");
/* 322 */     defaultSQL.put("SELECT SecGroupID, OptionCode FROM SPS_SecOption", "SELECT SecGroupID, OptionCode FROM SPS_SecOption");
/* 323 */     defaultSQL.put("SELECT SecurityID FROM SPS_SecGroups WHERE SecGroupID = ?", "SELECT SecurityID FROM SPS_SecGroups WHERE SecGroupID = ?");
/* 324 */     defaultSQL.put("SELECT SecGroupID FROM SPS_SecGroups WHERE SecurityID = ?", "SELECT SecGroupID FROM SPS_SecGroups WHERE SecurityID = ?");
/* 325 */     defaultSQL.put("SELECT SecGroupID, ECUID FROM SPS_ExclSecurity", "SELECT SecGroupID, ECUID FROM SPS_ExclSecurity");
/* 326 */     defaultSQL.put("SELECT HTML_CHARSET, HTML FROM HTML_INSTRUCTIONS WHERE LANGUAGE = ? AND INSTRUCTION_ID = ?", "SELECT HTML_CHARSET, HTML FROM HTML_INSTRUCTIONS WHERE LANGUAGE = ? AND INSTRUCTION_ID = ?");
/* 327 */     defaultSQL.put("SELECT GRAPHIC_MIMETYPE, GRAPHIC FROM INSTRUCTION_GRAPHICS WHERE GRAPHIC_ID = ?", "SELECT GRAPHIC_MIMETYPE, GRAPHIC FROM INSTRUCTION_GRAPHICS WHERE GRAPHIC_ID = ?");
/* 328 */     defaultSQL.put("SELECT * FROM SPS_SecCodeRequired WHERE VehicleID = ?", "SELECT * FROM SPS_SecCodeRequired WHERE VehicleID = ?");
/* 329 */     defaultSQL.put("SELECT SecurityCode FROM SPS_SecurityCode WHERE VIN = ?", "SELECT SecurityCode FROM SPS_SecurityCode WHERE VIN = ?");
/* 330 */     tbSQL.put("SELECT DISTINCT LanguageAcronym, ACP, LCID FROM WinLanguages", "SELECT DISTINCT LanguageAcronym, ACP, LCID FROM WinLanguages");
/* 331 */     tbSQL.put("SELECT String_ID, Language_Code, Description FROM Type4_String order by Language_Code", "SELECT String_ID, Language_Code, Description FROM Type4_String order by Language_Code");
/* 332 */     tbSQL.put("SELECT BLOBSIZE, BLOBCHECKSUM, DOWNLOAD_SITE FROM SPS_Blobs WHERE ModuleID = ?", "SELECT SIZE OF ModuleBlob, 'abcd' FROM SPS_Blobs WHERE ModuleID = ?");
/* 333 */     tbSQL.put(" AND (a.ModelYearCode is null OR a.ModelYearCode IN (?, 0, -1))", " AND (a.ModelYearCode = ? OR a.ModelYearCode IN (0, -1, NULL))");
/* 334 */     tbSQL.put(" AND (a.SystemType is null OR a.SystemType IN (0, ?))", " AND (a.SystemType = ? OR a.SystemType IN (0, NULL))");
/* 335 */     tbSQL.put("SELECT DISTINCT v.ReqInfoID, v.WMI, v.VDS, v.VIS, o.CategoryCode, o.OptionGroup, o.OptionOrder FROM SPS_BaseVehicle v, SPS_BaseVehOption o WHERE v.ReqInfoID = o.ReqInfoID (+)  AND v.SalesMakeCode = ?  AND ((v.ModelCode = ?) OR (v.ModelCode is null))  AND ((v.ModelYearCode = ?) OR (v.ModelYearCode is null))  AND (v.ControllerID = ?)ORDER BY o.OptionOrder", "SELECT DISTINCT v.ReqInfoID, v.WMI, v.VDS, v.VIS, o.CategoryCode, o.OptionGroup, o.OptionOrder FROM SPS_BaseVehicle v left outer join SPS_BaseVehOption o on v.ReqInfoID = o.ReqInfoID WHERE   v.SalesMakeCode = ?  AND ((v.ModelCode = ?) OR (v.ModelCode is null))  AND ((v.ModelYearCode = ?) OR (v.ModelYearCode is null))  AND (v.ControllerID = ?)ORDER BY o.OptionOrder");
/* 336 */     tbSQL.put("SELECT v.VehicleID, v.DeviceID, v.ControllerID, c.ECUID FROM SPS_Configuration c, SPS_Vehicle v WHERE v.VehicleID = c.VehicleID (+)  AND v.SalesMakeCode = ?  AND v.ModelCode = ?  AND v.ModelYearCode = ?", "SELECT v.VehicleID, v.DeviceID, v.ControllerID, c.ECUID FROM SPS_Configuration c right outer join SPS_Vehicle v on v.VehicleID = c.VehicleID WHERE  v.SalesMakeCode = ?  AND v.ModelCode = ?  AND v.ModelYearCode = ?");
/* 337 */     tbSQL.put("SELECT v.VehicleID, v.DeviceID, v.FunctionID, c.ECUID FROM SPS_Configuration c, SPS_Vehicle v WHERE v.VehicleID = c.VehicleID (+)  AND v.SalesMakeCode = ?  AND v.ModelCode = ?  AND v.ModelYearCode = ?", "SELECT v.VehicleID, v.DeviceID, v.FunctionID, c.ECUID FROM SPS_Configuration c right outer join SPS_Vehicle v on v.VehicleID = c.VehicleID WHERE  v.SalesMakeCode = ?  AND v.ModelCode = ?  AND v.ModelYearCode = ?");
/* 338 */     tbSQL.put("SELECT v.VehicleID, s.DeviceID, s.ControllerID, s.VCI, c.ECUID FROM SPS_AsBuilt_Vehicle v, SPS_AsBuilt_VCI s, SPS_Configuration c WHERE s.VINID = ? AND s.VCI = v.VCI AND v.VehicleID = c.VehicleID (+)", "SELECT v.VehicleID, s.DeviceID, s.ControllerID, s.VCI, c.ECUID FROM SPS_AsBuilt_Vehicle v left outer join SPS_Configuration c on v.VehicleID = c.VehicleID, SPS_AsBuilt_VCI s WHERE s.VINID = ? AND s.VCI = v.VCI");
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
/*     */   protected static String replace(String query, String source, String target) {
/* 367 */     return StringUtilities.replace(query, source, target);
/*     */   }
/*     */   
/*     */   public static String getSQL(IDatabaseLink dblink, String query) {
/* 371 */     if (dblink.getDBMS() == 3)
/* 372 */       return (String)defaultSQL.get(query); 
/* 373 */     if (dblink.getDBMS() == 2) {
/* 374 */       String sql = (String)tbSQL.get(query);
/* 375 */       if (sql != null) {
/* 376 */         if (getTransbaseVersion() == TRANSBASE_VERSION_NEW) {
/* 377 */           sql = sql.toUpperCase(Locale.ENGLISH);
/*     */         }
/*     */       } else {
/* 380 */         sql = (String)defaultSQL.get(query);
/*     */       } 
/* 382 */       if (getTransbaseVersion() == TRANSBASE_VERSION_OLD) {
/* 383 */         return (sql == null) ? null : sql;
/*     */       }
/* 385 */       return (sql == null) ? null : sql.toUpperCase(Locale.ENGLISH);
/*     */     } 
/*     */     
/* 388 */     return (String)defaultSQL.get(query);
/*     */   }
/*     */ 
/*     */   
/*     */   public static String getSQL(IDatabaseLink dblink, String query, int lparams) {
/* 393 */     String sql = getSQL(dblink, query);
/* 394 */     StringBuffer list = new StringBuffer();
/* 395 */     for (int i = 0; i < lparams; i++) {
/* 396 */       if (i > 0) {
/* 397 */         list.append(',');
/*     */       }
/* 399 */       list.append('?');
/*     */     } 
/* 401 */     if (dblink.getDBMS() == 2) {
/* 402 */       if (getTransbaseVersion() == TRANSBASE_VERSION_NEW) {
/* 403 */         return StringUtilities.replace(sql, "#LIST#", list.toString());
/*     */       }
/* 405 */       return StringUtilities.replace(sql, "#list#", list.toString());
/*     */     } 
/*     */     
/* 408 */     return StringUtilities.replace(sql, "#list#", list.toString());
/*     */   }
/*     */ 
/*     */   
/*     */   public static String getLanguageCode(IDatabaseLink dblink, SPSLanguage language) {
/* 413 */     if (dblink.getDBMS() == 2) {
/* 414 */       String languageCode = (language != null) ? language.getLocale() : "en_GB";
/* 415 */       if (getTransbaseVersion() == TRANSBASE_VERSION_OLD) {
/* 416 */         languageCode = (language != null) ? SPSLanguage.getLocaleTLA(language.getLocale()) : "ENU";
/*     */       }
/* 418 */       return languageCode;
/*     */     } 
/* 420 */     return language.getID();
/*     */   }
/*     */ 
/*     */   
/*     */   public static String trimString(String data) {
/* 425 */     return (data == null) ? null : data.trim();
/*     */   }
/*     */   
/*     */   public static String getString(IDatabaseLink dblink, SPSLanguage language, ResultSet rs, int attribute) throws Exception {
/* 429 */     if (dblink.getDBMS() == 1) {
/* 430 */       return rs.getString(attribute).trim();
/*     */     }
/* 432 */     String locale = (language != null) ? language.getLocale() : "en_GB";
/* 433 */     return transform(dblink, "windows-" + SPSLanguage.getCP(locale), rs.getBytes(attribute));
/*     */   }
/*     */ 
/*     */   
/*     */   protected static String transform(IDatabaseLink dblink, String cp, byte[] bytes) throws Exception {
/* 438 */     if (bytes == null)
/* 439 */       return null; 
/* 440 */     String result = new String(bytes);
/* 441 */     if (getTransbaseVersion() == TRANSBASE_VERSION_NEW) {
/* 442 */       result = new String(bytes, "UTF-8");
/*     */     }
/* 444 */     else if (cp != null) {
/* 445 */       result = new String(bytes, cp);
/*     */     } 
/* 447 */     result = result.trim();
/* 448 */     if (cp.equalsIgnoreCase("windows-932")) {
/* 449 */       StringBuffer buffer = new StringBuffer(result.length());
/* 450 */       for (int i = 0; i < result.length(); i++) {
/* 451 */         char c = result.charAt(i);
/* 452 */         switch (c) {
/*     */           case '～':
/* 454 */             c = '〜';
/*     */             break;
/*     */           case '－':
/* 457 */             c = '−';
/*     */             break;
/*     */           case '＼':
/* 460 */             c = '\\';
/*     */             break;
/*     */           case '∥':
/* 463 */             c = '‖';
/*     */             break;
/*     */           case '￠':
/* 466 */             c = '¢';
/*     */             break;
/*     */           case '￡':
/* 469 */             c = '£';
/*     */             break;
/*     */           case '￢':
/* 472 */             c = '¬';
/*     */             break;
/*     */           case '￤':
/* 475 */             c = '¦';
/*     */             break;
/*     */         } 
/*     */         
/* 479 */         buffer.append(c);
/*     */       } 
/* 481 */       result = buffer.toString();
/*     */     } 
/* 483 */     return result;
/*     */   }
/*     */   
/*     */   protected static void setTransbaseVersion(int transbase) {
/* 487 */     transbaseVersion = transbase;
/*     */   }
/*     */   
/*     */   protected static int getTransbaseVersion() {
/* 491 */     return transbaseVersion;
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\server\implementation\adapter\gme\DBMS.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */