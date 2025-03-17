/*     */ package com.eoos.gm.tis2web.sps.server.implementation.adapter.nao;
/*     */ import com.eoos.gm.tis2web.frame.export.common.util.DatabaseStatistics;
/*     */ import com.eoos.gm.tis2web.frame.export.common.util.IDatabaseLink;
/*     */ import com.eoos.gm.tis2web.sps.server.implementation.adapter.common.DBMS;
/*     */ import com.eoos.util.StringUtilities;
/*     */ import edu.umd.cs.findbugs.annotations.SuppressWarnings;
/*     */ import java.sql.ResultSet;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ 
/*     */ @SuppressWarnings({"NM_SAME_SIMPLE_NAME_AS_SUPERCLASS"})
/*     */ public class DBMS extends DBMS {
/*  13 */   protected static Map defaultSQL = new HashMap<Object, Object>();
/*     */   
/*  15 */   protected static Map tbSQL = new HashMap<Object, Object>();
/*     */   
/*  17 */   protected static Map msSQL = new HashMap<Object, Object>();
/*     */ 
/*     */   
/*     */   public static final String COP = "SELECT * FROM COP";
/*     */ 
/*     */   
/*     */   public static final String COP_CONTROLLERS = "SELECT Controller_Index_No,Model_Year,Make,Line,Series,Engine,Pre_RPO_Code,Post_RPO_Code,VIN_Pos,VIN_Value,Beginning_Sequence,Ending_Sequence FROM COP_Controllers";
/*     */   
/*     */   public static final String COP_USAGE = "SELECT Usage_Index_No, Controller_Index_No FROM COP_Usage_Associate";
/*     */   
/*     */   public static final String HARDWARE_LIST = "SELECT Hardware_Indx, Hardware_List, Description_Id FROM Hardware_List";
/*     */   
/*     */   public static final String HARDWARE_DESCRIPTION = "SELECT Description_Id FROM Hardware_List WHERE Hardware_Indx = ?";
/*     */   
/*     */   public static final String RPO_DESCRIPTIONS = "SELECT RPO_Code, Language_Code, Description FROM RPO_Description";
/*     */   
/*     */   public static final String RPO_CODE_LABELS = "SELECT RPO_Label_Id, Language_Code, Label FROM RPO_Code_Labels";
/*     */   
/*     */   public static final String RPO_POST_OPTIONS = "SELECT Post_RPO_Code, RPO_Label_Id FROM Post_Option_Label_Ids";
/*     */   
/*     */   public static final String PART_DESCRIPTIONS = "SELECT Description_Id, Language_Code, Rep_Description, Short_Description FROM Part_Description";
/*     */   
/*     */   public static final String VEHICLE_OPTION_LIST = "SELECT RPO_Code, RPO_Label_Id FROM Option_List WHERE Model_Year = ? AND Make = ? AND Line = ? AND (Series = '~' OR Series = ?) AND (Engine = '~' OR Engine = ?)";
/*     */   
/*     */   public static final String DEFAULT_BITS = "SELECT Default_Bits FROM Default_Bits WHERE Default_Bits_Index=? AND (RPO_Code=? OR RPO_Code='~')";
/*     */   
/*     */   public static final String DEFAULT_BITS_INFO = "SELECT DISTINCT Default_Bits_Index, RPO_Code, Default_Bits FROM Default_Bits WHERE Default_Bits_Index IN (#list#)";
/*     */   
/*     */   public static final String OPTION_BYTE_INFO = "SELECT Device_ID, Block_No, Byte_Offset, Defined_Bits, Dependency_ID, Descrpt_ID, Label_ID, Default_Bits_Index FROM OptionByte WHERE Model_Year = ? AND Make = ? AND Line = ? AND (Series = '~' OR Series = ?)  AND (Engine = '~' OR Engine = ?)  AND (Body = '~' OR Body = ?) ORDER BY Device_ID, Label_ID";
/*     */   
/*     */   public static final String OPTION_BYTES = "SELECT a.Block_No, a.Byte_Offset, a.Defined_Bits, a.Dependency_ID, a.Descrpt_ID, a.Label_ID, a.Default_Bits_Index, b.Label, c.Description FROM OptionByte a, RPO_Code_Labels b, OptionByte_Descrpt c WHERE a.Model_Year = ? AND a.Make = ? AND a.Line = ? AND (a.Series = '~' OR a.Series = ?)  AND (a.Engine = '~' OR a.Engine = ?)  AND (a.Body = '~' OR a.Body = ?)  AND (a.Device_ID = ? ) AND (a.Label_ID = b.RPO_Label_Id) AND (a.Descrpt_ID = c.Descrpt_ID) AND b.Language_Code = ? AND (c.Language_Code = b.Language_Code) ORDER BY a.Label_ID";
/*     */   
/*     */   public static final String OPTION_BYTE_DEPENDENCY = "SELECT Device_ID, Block_No, Byte_Offset, Byte_Data FROM OptionByte_Dependency WHERE Dependency_ID = ?";
/*     */   
/*     */   public static final String OPTION_BYTE_DEPENDENCY_INFO = "SELECT DISTINCT Dependency_ID, Device_ID, Block_No, Byte_Offset, Byte_Data FROM OptionByte_Dependency WHERE Dependency_ID IN (#list#)";
/*     */   
/*     */   public static final String OPTION_BIT_INFO = "SELECT Device_ID, Block_No, Byte_Offset, Bit_Offset, Bit_Descrpt_ID FROM OptionByte_Bit_Descrpt WHERE Model_Year = ? AND Make = ? AND Line = ? AND (Series = '~' OR Series = ?) AND (Engine = '~' OR Engine = ?) AND (Body = '~' OR Body = ?)";
/*     */   
/*     */   public static final String OPTION_BIT = "SELECT b.Description FROM OptionByte_Bit_Descrpt a, OptionByte_Descrpt b WHERE a.Model_Year = ? AND a.Make = ? AND a.Line = ? AND (a.Series = '~' OR a.Series = ?) AND (a.Engine = '~' OR a.Engine = ?) AND (a.Body = '~' OR a.Body = ?) AND (a.Device_ID = -1 OR a.Device_ID = ?) AND (a.Block_No = -1 OR a.Block_No = ?) AND (a.Byte_Offset = -1 OR a.Byte_Offset = ?) AND a.Bit_Offset = ? AND ( a.Bit_Descrpt_ID = b.Descrpt_ID ) AND b.Language_Code = ?";
/*     */   
/*     */   public static final String OPTION_BYTE_DESCRIPTIONS = "SELECT DISTINCT Descrpt_ID, Description FROM OptionByte_Descrpt WHERE Descrpt_ID IN (#list#) AND Language_Code = ?";
/*     */   
/*     */   public static final String EIL = "SELECT DISTINCT End_Item_No FROM EIL";
/*     */   
/*     */   public static final String CVN = "SELECT Part_No, CVN FROM CVN";
/*     */   
/*     */   public static final String SPS_SECURITY = "SELECT Sec_List FROM Security_Groups WHERE Sec_Id = ?";
/*     */   
/*     */   public static final String SPS_PROGRAMMING_TYPES = "SELECT Language_Code, Method_Id, Description FROM Programming_Method_Description";
/*     */   
/*     */   public static final String VCI_CONTROLLER_DESCRIPTIONS = "SELECT Controller_Id, Language_Code, Controller_Name, Description, Desc_Indx, Prog_Type_ID FROM Controller_Description";
/*     */   
/*     */   public static final String VCI_CONTROLLER_DESCRIPTIONS2 = "SELECT Controller_Id, Language_Code, Controller_Name, Description, Desc_Indx FROM Controller_Description";
/*     */   
/*     */   public static final String VCI_SUPPORTED_CONTROLLERS = "SELECT Controller_Id,RPO_Code,Pre_RPO_Code,Post_RPO_Code,Device_Id,ReqMthID,Programming_Methods,Vci,Pre_Prog_Msgs,Post_Prog_Msgs,VIN_Pos,VIN_Value,VIN_Digit_4_OR_6,Beginning_Sequence,Ending_Sequence,REPLACE_MESSAGE_ID,REPLACE_ID,Desc_Indx,Sec_Id,Suppress_Sequence_Controller,SecCode_Required,Engine,Series,Line FROM Supported_Controllers WHERE Model_Year = ? AND Make = ? AND Line = ? AND (Series = '~' OR Series = ?) AND (Engine = '~' OR Engine = ?)";
/*     */   
/*     */   public static final String VCI_ORIGIN_PARTS = "Select Origin_Part_No_String, Module_Id_String From VCI_Origin_Members_String Where VCI = ?";
/*     */   
/*     */   public static final String VCI_ORIGIN_PARTS_Quick_Update = "Select Origin_Part_No_String, Module_Id_String, Date_Time From VCI_Origin_Members_String Where VCI = ?";
/*     */   
/*     */   public static final String VCI_AsBuilt_ID_Quick_Update = "Select VIN_Id, Date_Time from VCI_AsBuilt_Id where VIN_1To8 = ? and VIN_10 = ? and VIN_11 = ?";
/*     */   
/*     */   public static final String VCI_AsBuilt_ID = "Select VIN_Id from VCI_AsBuilt_Id where VIN_1To8 = ? and VIN_10 = ? and VIN_11 = ?";
/*     */   
/*     */   public static final String VCI_AsBuilt_String = "Select VCI_String, Controller_Id_String from VCI_AsBuilt_String where VIN_12To17 = ? and VIN_Id = ?";
/*     */   
/*     */   public static final String VCI_PROGRAMMING_MESSAGE = "SELECT Description FROM Pre_Post_Prog_Msg where Message_Id=? and Language_Code=?";
/*     */   
/*     */   public static final String CALIBRATION_FILE = "SELECT Calibration_File FROM Calibration_Files WHERE Calibration_Part_No = ?";
/*     */   
/*     */   public static final String CALIBRATION_FILE_INFO = "SELECT Calibration_Part_No, FILESIZE, FILECHECKSUM, DOWNLOAD_SITE FROM Calibration_Files WHERE Calibration_Part_No IN (#list#)";
/*     */   
/*     */   public static final String CALIBRATION_FILE_INFO_EIL = "SELECT a.Module_Id, a.Calibration_Part_No, FILESIZE, FILECHECKSUM, b.DOWNLOAD_SITE  FROM EIL a LEFT OUTER JOIN Calibration_Files b ON a.Calibration_Part_No = b.Calibration_Part_No WHERE a.End_Item_No = ? ORDER BY a.Module_Id";
/*     */   
/*     */   public static final String CALIBRATION_INFO_EIL = "SELECT Module_Id, Calibration_Part_No FROM EIL WHERE End_Item_No = ? ORDER BY Module_Id";
/*     */   
/*     */   public static final String REQUEST_METHOD_DATA = "SELECT ReqMethID,DeviceID,Protocol,Pin_Link,ReadVIN,VINReadType,VINAddresses,ReadParts,PartReadType,PartAddresses,PartFormat,PartLength,PartStartByte,ReadHWID,HWIDReadType,HWIDAddresses,HWIDFormat,HWIDLength,HWIDStartByte,ReadSecuritySeed,SpecialReqType,ReadCVN FROM Request_Method_Data where ReqMethID=?";
/*     */   
/*     */   public static final String LOAD_REQUEST_METHOD_DATA = "SELECT ReqMethID,DeviceID,Protocol,Pin_Link,ReadVIN,VINReadType,VINAddresses,ReadParts,PartReadType,PartAddresses,PartFormat,PartLength,PartStartByte,ReadHWID,HWIDReadType,HWIDAddresses,HWIDFormat,HWIDLength,HWIDStartByte,ReadSecuritySeed,SpecialReqType,ReadCVN FROM Request_Method_Data";
/*     */   
/*     */   public static final String VTDNAVINFO = "SELECT count(*) FROM VTDNavInfo WHERE Value = ?";
/*     */   
/*     */   public static final String VTDSECURITY = "SELECT DISTINCT Device_Id, Algo_No FROM VTD_Security_Info WHERE Model_Year = ? AND Make = ? AND Line = ? AND (Series = '~' OR Series = ?) AND (Engine = '~' OR Engine = ?) AND (Plant = '~' OR Plant = ?)";
/*     */   
/*     */   public static final String PROM_TRUCK_MAKES = "SELECT Model_Year, Truck_Make, Truck_Description, Car_Make, Car_Description FROM PROM_Convert_Truck_Make";
/*     */   
/*     */   public static final String PROM_VIN_LINE = "SELECT SRS_Body FROM PROM_VIN_Line Where Model_year = ? AND SRS_CorT = ? AND SRS_Make = ? AND SRS_Series Like ?";
/*     */   
/*     */   public static final String PROM_VIN_ENGINE = "SELECT ENG_RPO FROM PROM_VIN_Engine Where Model_Year = ? AND ENG_CorT = ? AND ENG_Divuse = ? AND ENG_Code = ? AND (ENG_Body Like ? OR ENG_Body Like ? OR ENG_Body = '*')";
/*     */   
/*     */   public static final String PROM_DATA = "SELECT DISTINCT PROM_No, Transm_Type, Air_Flag, PROM_Part_No, Broadcast_Code, Program_Part_No, OEM_No, ECU_Part_No, Scanner_ID FROM PROM_Data WHERE PROM_No IN (#list#)";
/*     */   
/*     */   public static final String PROM_ECU = "SELECT ECU_Part_No, Controller, Language_Code, Description FROM PROM_ECU";
/*     */   
/*     */   public static final String PROM_SUPPORTED_CONTROLLERS = "SELECT DISTINCT a.PROM_No, a.RPO_Code, a.RPO_Type, a.RPO_Exists, a.RPO_Code_Label_ID FROM PROM_Supported_PROMs a, PROM_Supported_PROMs b WHERE b.Model_Year = ? AND b.Make = ? AND b.Line = ? AND (b.Series = ? OR b.Series = '~') AND b.RPO_Code = ? AND a.Model_Year = b.Model_Year AND a.Make = b.Make AND a.Line = b.Line AND a.Series = b.Series AND a.PROM_No = b.PROM_No";
/*     */   
/*     */   public static final String PROM_RPO_DESCRIPTIONS = "SELECT RPO_Code, Language_Code, RPO_Type_no, Minimum_Model_Year, Maximum_Model_Year, Description FROM PROM_RPO_Description";
/*     */   
/*     */   public static final String PROM_RPO_CODE_LABELS = "SELECT RPO_Label_Id, Language_Code, Label FROM PROM_RPO_Code_Labels";
/*     */   
/*     */   public static final String PROM_EMISSION_TYPES = "SELECT RPO_Type, RPO_Code_Label_ID, Language_Code, Description FROM PROM_Emission_Type";
/*     */   
/*     */   public static final String PROM_MODEL_YEARS = "SELECT Model_Year, Year_VIN FROM PROM_U_Model_Year";
/*     */   
/*     */   public static final String PROM_PROGRAMMING_TYPES = "SELECT Language_Code, Method_Id, Description FROM PROM_Prog_Method_Description";
/*     */   
/*     */   public static final String PROM_ORIGIN = "SELECT DISTINCT Origin_Part_No FROM PROM_COP WHERE New_Part_No = ? AND Language_Code = ?";
/*     */   
/*     */   public static final String PROM_COP = "SELECT Origin_Part_No, Old_Part_No, New_Part_No, Rep_Type, Rep_Description, Release_Comments FROM PROM_COP WHERE Origin_Part_No IN (#list#) AND Language_Code = ?";
/*     */   
/*     */   public static final String PROM_OPTIONS = "SELECT DISTINCT PROM_No, RPO_Code, RPO_Type, RPO_Exists, RPO_Code_Label_ID FROM PROM_Supported_PROMs WHERE Model_Year = ? AND Make = ? AND Line = ? AND PROM_No IN (#list#)";
/*     */   
/*     */   public static final String PROM_CALPAK = "SELECT DISTINCT a.ECU_Part_No, b.Calpak_Part_No, c.Description FROM PROM_ECU a, PROM_ECU_Calpak b, PROM_Calpak c WHERE b.ECU_Part_No = a.ECU_Part_No AND b.Calpak_Part_No = c.Calpak_Part_No AND c.Language_Code = ? AND a.Language_Code = c.Language_Code AND a.ECU_Part_No IN (#list#)";
/*     */   
/*     */   public static final String BULLETIN = "SELECT BULLETIN_CHARSET, BULLETIN FROM BULLETINS WHERE LANGUAGE = ? AND BULLETIN_ID like ?";
/*     */   
/*     */   public static final String BULLETIN_ESI = "SELECT SIO_ID FROM SIO_PROPERTY WHERE PROPERTY_TYPE = ? AND PROPERTY like ?";
/*     */   
/*     */   public static final String BULLETIN_SUBJECT = "SELECT SUBJECT FROM SIO_SUBJECT WHERE LOCALE = ? AND SIO_ID = ?";
/*     */   
/*     */   public static final String BULLETIN_TEXT = "SELECT TEXT_CHARSET, TEXT FROM SIO_TEXT WHERE LOCALE = ? AND SIO_ID = ?";
/*     */   
/*     */   public static final String BULLETIN_IMAGE = "SELECT GRAPHIC_MIMETYPE, GRAPHIC FROM SIO_IMAGE WHERE GRAPHIC_ID = ?";
/*     */   
/*     */   public static final String BULLETIN_GRAPHIC = "SELECT GRAPHIC_MIMETYPE, GRAPHIC FROM SIO_GRAPHIC WHERE GRAPHIC_ID = ?";
/*     */   
/*     */   public static final String HTML_INSTRUCTION = "SELECT HTML_CHARSET, HTML FROM HTML_INSTRUCTIONS WHERE LANGUAGE = ? AND INSTRUCTION_ID = ?";
/*     */   
/*     */   public static final String INSTRUCTION_GRAPHIC = "SELECT GRAPHIC_MIMETYPE, GRAPHIC FROM INSTRUCTION_GRAPHICS WHERE GRAPHIC_ID = ?";
/*     */   
/*     */   public static final String SUPPORTED_TPM = "SELECT Model_Year, Make, Line, TPM_Index FROM Supported_TPM";
/*     */   
/*     */   public static final String TPM_LOAD_RANGE_INDEX = "SELECT TPM_Index, Option1, Option2, Option3, Option4, Option5 FROM TPM_LoadRange_Index";
/*     */   
/*     */   public static final String TPM_EXTENDED_TYPE = "SELECT Description, Value FROM TPM_Extended_Type1";
/*     */   
/*     */   public static final String TPM_LOAD_RANGE_C = "SELECT Description, Value FROM TPM_LoadRangeC_Type1";
/*     */   
/*     */   public static final String TPM_LOAD_RANGE_D = "SELECT Description, Value FROM TPM_LoadRangeD_Type1";
/*     */   
/*     */   public static final String TPM_LOAD_RANGE_E = "SELECT Description, Value FROM TPM_LoadRangeE_Type1";
/*     */   
/*     */   public static final String TPM_STANDARD_TYPE = "SELECT Description, Value FROM TPM_Standard_Type1";
/*     */   
/*     */   public static final String WIN_LANGUAGES = "SELECT LCID, ACP, Language_Acronym FROM Win_Languages";
/*     */   
/*     */   public static final String TYPE4_STRINGS = "SELECT String_Id, Language_Code, Description FROM Type4_String WHERE Language_Code = ?";
/*     */   
/*     */   public static final String TYPE4_RELEASE = "SELECT RELEASE_TEXT FROM TYPE4_RELEASE WHERE CALIBRATION_PART_NO = ? AND LANGUAGE_CODE = ?";
/*     */   
/*     */   public static final String PROGRAMMING_SEQUENCES = "SELECT PROGRAMMING_SEQUENCE_ID, CONTROLLER_ORDER, CONTROLLER_ID, PRE_PROGRAMMING_INST, POST_PROGRAMMING_INST, ON_FAILURE FROM PROGRAMMING_SEQUENCE ORDER BY 1,2";
/*     */   
/*     */   public static final String PROGRAMMING_SEQUENCES2 = "SELECT PROGRAMMING_SEQUENCE_ID, CONTROLLER_ORDER, CONTROLLER_ID, PRE_PROGRAMMING_INST, POST_PROGRAMMING_INST, ON_FAILURE, REPLACE_MODE_SUPPORTED FROM PROGRAMMING_SEQUENCE ORDER BY 1,2";
/*     */   
/*     */   public static final String PROGRAMMING_SEQUENCE_CONSTRAINTS = "SELECT PROGRAMMING_SEQUENCE_ID, PART_NO FROM PROGRAMMING_SEQUENCE_CONSTRNTS";
/*     */   
/*     */   public static final String REPLACE_INSTRUCTIONS = "SELECT DESCRIPTION FROM REPLACE_INSTRUCTIONS WHERE REPLACE_MESSAGE_ID=? and LANGUAGE_CODE=?";
/*     */   
/*     */   public static final String REPLACE_ATTRIBUTES = "SELECT REPLACE_ID, ATTRIBUTE FROM REPLACE_ATTRIBUTES";
/*     */   
/*     */   public static final String XML_VCI = "SELECT RPO_STRING_ID, MODEL_DESIGNATOR_ID, XML_ID FROM XML_VCI WHERE VCI = ?";
/*     */   
/*     */   public static final String RPO_STRING = "SELECT RPO_STRING FROM RPO_STRING WHERE RPO_STRING_ID = ?";
/*     */   
/*     */   public static final String MODEL_DESIGNATOR = "SELECT MODEL_DESIGNATOR FROM MODEL_DESIGNATOR WHERE MODEL_DESIGNATOR_ID = ?";
/*     */   
/*     */   public static final String XML_PARTS = "SELECT XML_ORIGIN_PART_NBR, TYPE4_ORIGIN_PART_NBR FROM XML_PARTS WHERE XML_ID = ?";
/*     */   
/*     */   public static final String SECURITY_CODE = "SELECT SecCode FROM SecCode_Asbuilt WHERE VIN = ?";
/*     */   
/*     */   public static final String TB_RPO_DESCRIPTIONS = "SELECT RPO_Code, Language_Code CAST CHAR(*), Description FROM RPO_Description";
/*     */   
/*     */   public static final String TB_RPO_CODE_LABELS = "SELECT RPO_Label_Id, Language_Code CAST CHAR(*), Label FROM RPO_Code_Labels";
/*     */   
/*     */   public static final String TB_PART_DESCRIPTIONS = "SELECT Description_Id, Language_Code CAST CHAR(*), Rep_Description, Short_Description FROM Part_Description";
/*     */   
/*     */   public static final String TB_SPS_PROGRAMMING_TYPES = "SELECT Language_Code CAST CHAR(*), Method_Id, Description FROM Programming_Method_Description";
/*     */   
/*     */   public static final String TB_VCI_CONTROLLER_DESCRIPTIONS = "SELECT Controller_Id, Language_Code CAST CHAR(*), Controller_Name, Description, Desc_Indx, Prog_Type_ID FROM Controller_Description";
/*     */   
/*     */   public static final String TB_VCI_CONTROLLER_DESCRIPTIONS2 = "SELECT Controller_Id, Language_Code CAST CHAR(*), Controller_Name, Description, Desc_Indx FROM Controller_Description";
/*     */   
/*     */   public static final String TB_VCI_PROGRAMMING_MESSAGE = "SELECT Description FROM Pre_Post_Prog_Msg where Message_Id = ? and Language_Code CAST CHAR(*) = ?";
/*     */   
/*     */   public static final String TB_CALIBRATION_FILE_INFO = "SELECT Calibration_Part_No, SIZE OF Calibration_File as FILESIZE FROM Calibration_Files WHERE Calibration_Part_No IN (#list#)";
/*     */   
/*     */   public static final String TB_CALIBRATION_FILE_INFO_EIL = "SELECT a.Module_Id, a.Calibration_Part_No, SIZE OF b.Calibration_File as FILESIZE FROM EIL a LEFT OUTER JOIN Calibration_Files b ON a.Calibration_Part_No = b.Calibration_Part_No WHERE a.End_Item_No = ? ORDER BY a.Module_Id";
/*     */   
/*     */   public static final String TB_PROM_ECU = "SELECT ECU_Part_No, Controller, Language_Code CAST CHAR(*), Description FROM PROM_ECU";
/*     */   
/*     */   public static final String TB_PROM_RPO_DESCRIPTIONS = "SELECT RPO_Code, Language_Code CAST CHAR(*), RPO_Type_no, Minimum_Model_Year, Maximum_Model_Year, Description FROM PROM_RPO_Description";
/*     */   
/*     */   public static final String TB_PROM_RPO_CODE_LABELS = "SELECT RPO_Label_Id, Language_Code CAST CHAR(*), Label FROM PROM_RPO_Code_Labels";
/*     */   
/*     */   public static final String TB_PROM_EMISSION_TYPES = "SELECT RPO_Type, RPO_Code_Label_ID, Language_Code CAST CHAR(*), Description FROM PROM_Emission_Type";
/*     */   
/*     */   public static final String TB_PROM_PROGRAMMING_TYPES = "SELECT Language_Code CAST CHAR(*), Method_Id, Description FROM PROM_Prog_Method_Description";
/*     */   
/*     */   public static final String TB_PROM_ORIGIN = "SELECT DISTINCT Origin_Part_No FROM PROM_COP WHERE New_Part_No = ? AND Language_Code CAST CHAR(*) = ?";
/*     */   
/*     */   public static final String TB_PROM_CALPAK = "SELECT DISTINCT a.ECU_Part_No, b.Calpak_Part_No, c.Description FROM PROM_ECU a, PROM_ECU_Calpak b, PROM_Calpak c WHERE b.ECU_Part_No = a.ECU_Part_No AND b.Calpak_Part_No = c.Calpak_Part_No AND c.Language_Code CAST CHAR(*) = ? AND a.Language_Code = c.Language_Code AND a.ECU_Part_No IN (#list#)";
/*     */   
/*     */   public static final String TB_VCI_SUPPORTED_CONTROLLERS = "SELECT Controller_Id,RPO_Code,Pre_RPO_Code,Post_RPO_Code,Device_Id,ReqMthID,Programming_Methods,Vci,Pre_Prog_Msgs,Post_Prog_Msgs,VIN_Pos,VIN_Value,VIN_Digit_4_OR_6,Beginning_Sequence,Ending_Sequence,REPLACE_MESSAGE_ID,REPLACE_ID,Desc_Indx,Sec_Id,Suppress_Sequence_Controller,SecCode_Required,Engine,Series,Line FROM Supported_Controllers WHERE Model_Year = ? AND Make = ? AND Line = ? AND (Series = '~' OR Series = ?) AND (Engine = '~' OR Engine = ?)";
/*     */   
/*     */   public static final String TB_REPLACE_INSTRUCTIONS = "SELECT DESCRIPTION FROM REPLACE_INSTRUCTIONS WHERE REPLACE_MESSAGE_ID=? and LANGUAGE_CODE=?";
/*     */   
/*     */   public static final String TB_REPLACE_ATTRIBUTES = "SELECT REPLACE_ID, ATTRIBUTE FROM REPLACE_ATTRIBUTES";
/*     */   
/*     */   public static final String TB_PROGRAMMING_SEQUENCES = "SELECT Programming_Sequence_Id, Controller_Order, Controller_Id, Pre_Programming_Inst, Post_Programming_Inst, On_Failure FROM Programming_Sequence ORDER BY 1,2";
/*     */   
/*     */   public static final String TB_PROGRAMMING_SEQUENCES2 = "SELECT Programming_Sequence_Id, Controller_Order, Controller_Id, Pre_Programming_Inst, Post_Programming_Inst, On_Failure, Replace_Mode_Supported FROM Programming_Sequence ORDER BY 1,2";
/*     */   
/*     */   public static final String TB_PROGRAMMING_SEQUENCE_CONSTRAINTS = "SELECT Programming_Sequence_Id, Part_No FROM Programming_Sequence_Constrnts";
/*     */   
/*     */   public static final String TB_XML_VCI = "SELECT RPO_String_ID, Model_Designator_ID, XML_ID FROM XML_VCI WHERE VCI = ?";
/*     */   
/*     */   public static final String TB_RPO_STRING = "SELECT RPO_String  FROM RPO_String  WHERE RPO_String_ID = ?";
/*     */   
/*     */   public static final String TB_MODEL_DESIGNATOR = "SELECT Model_Designator  FROM Model_Designator  WHERE Model_Designator_ID = ?";
/*     */   
/*     */   public static final String TB_XML_PARTS = "SELECT XML_Origin_Part_Nbr, Type4_Origin_Part_Nbr  FROM XML_Parts  WHERE XML_ID = ?";
/*     */   
/*     */   public static final String TSQL_PROM_TRUCK_MAKES = "SELECT Model_Yr, Truck_Make, Truck_Description, Car_Make, Car_Description FROM PROM_Convert_Truck_Make";
/*     */   
/*     */   public static final String TSQL_VCI_SUPPORTED_CONTROLLERS = "SELECT Controller_Id, RPO_Code,Pre_RPO_Code,Post_RPO_Code,Device_Id,ReqMthID,Programming_Methods,Vci,Pre_Prog_Msgs,Post_Prog_Msgs,VIN_Pos,VIN_Value,VIN_Digit_4_OR_6,Beginning_Sequence,Ending_Sequence,REPLACE_MESSAGE_ID,REPLACE_ID,After_Market,Desc_Indx,Engine,Series,Line,Sec_Id FROM Supported_Controllers WHERE Model_Year = ? AND Make = ? AND Line = ? AND (Series = '~' OR Series = ?) AND (Engine = '~' OR Engine = ?)";
/*     */   
/*     */   public static final String TSQL_VCI_AsBuilt = "Select Controller_Id, VCI from VCI_AsBuilt where VIN = ?";
/*     */   
/*     */   public static final String TSQL_VCI_ORIGIN_PARTS = "Select Origin_Part_No, Module_Id From VCI_Origin_Members Where VCI = ? Order By 2";
/*     */ 
/*     */   
/*     */   static {
/* 249 */     defaultSQL.put("SELECT * FROM COP", "SELECT * FROM COP");
/* 250 */     defaultSQL.put("SELECT Controller_Index_No,Model_Year,Make,Line,Series,Engine,Pre_RPO_Code,Post_RPO_Code,VIN_Pos,VIN_Value,Beginning_Sequence,Ending_Sequence FROM COP_Controllers", "SELECT Controller_Index_No,Model_Year,Make,Line,Series,Engine,Pre_RPO_Code,Post_RPO_Code,VIN_Pos,VIN_Value,Beginning_Sequence,Ending_Sequence FROM COP_Controllers");
/* 251 */     defaultSQL.put("SELECT Usage_Index_No, Controller_Index_No FROM COP_Usage_Associate", "SELECT Usage_Index_No, Controller_Index_No FROM COP_Usage_Associate");
/* 252 */     defaultSQL.put("SELECT Hardware_Indx, Hardware_List, Description_Id FROM Hardware_List", "SELECT Hardware_Indx, Hardware_List, Description_Id FROM Hardware_List");
/* 253 */     defaultSQL.put("SELECT Description_Id FROM Hardware_List WHERE Hardware_Indx = ?", "SELECT Description_Id FROM Hardware_List WHERE Hardware_Indx = ?");
/* 254 */     defaultSQL.put("SELECT RPO_Code, Language_Code, Description FROM RPO_Description", "SELECT RPO_Code, Language_Code, Description FROM RPO_Description");
/* 255 */     defaultSQL.put("SELECT RPO_Label_Id, Language_Code, Label FROM RPO_Code_Labels", "SELECT RPO_Label_Id, Language_Code, Label FROM RPO_Code_Labels");
/* 256 */     defaultSQL.put("SELECT Post_RPO_Code, RPO_Label_Id FROM Post_Option_Label_Ids", "SELECT Post_RPO_Code, RPO_Label_Id FROM Post_Option_Label_Ids");
/* 257 */     defaultSQL.put("SELECT Description_Id, Language_Code, Rep_Description, Short_Description FROM Part_Description", "SELECT Description_Id, Language_Code, Rep_Description, Short_Description FROM Part_Description");
/* 258 */     defaultSQL.put("SELECT RPO_Code, RPO_Label_Id FROM Option_List WHERE Model_Year = ? AND Make = ? AND Line = ? AND (Series = '~' OR Series = ?) AND (Engine = '~' OR Engine = ?)", "SELECT RPO_Code, RPO_Label_Id FROM Option_List WHERE Model_Year = ? AND Make = ? AND Line = ? AND (Series = '~' OR Series = ?) AND (Engine = '~' OR Engine = ?)");
/* 259 */     defaultSQL.put("SELECT Default_Bits FROM Default_Bits WHERE Default_Bits_Index=? AND (RPO_Code=? OR RPO_Code='~')", "SELECT Default_Bits FROM Default_Bits WHERE Default_Bits_Index=? AND (RPO_Code=? OR RPO_Code='~')");
/* 260 */     defaultSQL.put("SELECT DISTINCT Default_Bits_Index, RPO_Code, Default_Bits FROM Default_Bits WHERE Default_Bits_Index IN (#list#)", "SELECT DISTINCT Default_Bits_Index, RPO_Code, Default_Bits FROM Default_Bits WHERE Default_Bits_Index IN (#list#)");
/* 261 */     defaultSQL.put("SELECT Device_ID, Block_No, Byte_Offset, Defined_Bits, Dependency_ID, Descrpt_ID, Label_ID, Default_Bits_Index FROM OptionByte WHERE Model_Year = ? AND Make = ? AND Line = ? AND (Series = '~' OR Series = ?)  AND (Engine = '~' OR Engine = ?)  AND (Body = '~' OR Body = ?) ORDER BY Device_ID, Label_ID", "SELECT Device_ID, Block_No, Byte_Offset, Defined_Bits, Dependency_ID, Descrpt_ID, Label_ID, Default_Bits_Index FROM OptionByte WHERE Model_Year = ? AND Make = ? AND Line = ? AND (Series = '~' OR Series = ?)  AND (Engine = '~' OR Engine = ?)  AND (Body = '~' OR Body = ?) ORDER BY Device_ID, Label_ID");
/* 262 */     defaultSQL.put("SELECT a.Block_No, a.Byte_Offset, a.Defined_Bits, a.Dependency_ID, a.Descrpt_ID, a.Label_ID, a.Default_Bits_Index, b.Label, c.Description FROM OptionByte a, RPO_Code_Labels b, OptionByte_Descrpt c WHERE a.Model_Year = ? AND a.Make = ? AND a.Line = ? AND (a.Series = '~' OR a.Series = ?)  AND (a.Engine = '~' OR a.Engine = ?)  AND (a.Body = '~' OR a.Body = ?)  AND (a.Device_ID = ? ) AND (a.Label_ID = b.RPO_Label_Id) AND (a.Descrpt_ID = c.Descrpt_ID) AND b.Language_Code = ? AND (c.Language_Code = b.Language_Code) ORDER BY a.Label_ID", "SELECT a.Block_No, a.Byte_Offset, a.Defined_Bits, a.Dependency_ID, a.Descrpt_ID, a.Label_ID, a.Default_Bits_Index, b.Label, c.Description FROM OptionByte a, RPO_Code_Labels b, OptionByte_Descrpt c WHERE a.Model_Year = ? AND a.Make = ? AND a.Line = ? AND (a.Series = '~' OR a.Series = ?)  AND (a.Engine = '~' OR a.Engine = ?)  AND (a.Body = '~' OR a.Body = ?)  AND (a.Device_ID = ? ) AND (a.Label_ID = b.RPO_Label_Id) AND (a.Descrpt_ID = c.Descrpt_ID) AND b.Language_Code = ? AND (c.Language_Code = b.Language_Code) ORDER BY a.Label_ID");
/* 263 */     defaultSQL.put("SELECT Device_ID, Block_No, Byte_Offset, Byte_Data FROM OptionByte_Dependency WHERE Dependency_ID = ?", "SELECT Device_ID, Block_No, Byte_Offset, Byte_Data FROM OptionByte_Dependency WHERE Dependency_ID = ?");
/* 264 */     defaultSQL.put("SELECT DISTINCT Dependency_ID, Device_ID, Block_No, Byte_Offset, Byte_Data FROM OptionByte_Dependency WHERE Dependency_ID IN (#list#)", "SELECT DISTINCT Dependency_ID, Device_ID, Block_No, Byte_Offset, Byte_Data FROM OptionByte_Dependency WHERE Dependency_ID IN (#list#)");
/* 265 */     defaultSQL.put("SELECT Device_ID, Block_No, Byte_Offset, Bit_Offset, Bit_Descrpt_ID FROM OptionByte_Bit_Descrpt WHERE Model_Year = ? AND Make = ? AND Line = ? AND (Series = '~' OR Series = ?) AND (Engine = '~' OR Engine = ?) AND (Body = '~' OR Body = ?)", "SELECT Device_ID, Block_No, Byte_Offset, Bit_Offset, Bit_Descrpt_ID FROM OptionByte_Bit_Descrpt WHERE Model_Year = ? AND Make = ? AND Line = ? AND (Series = '~' OR Series = ?) AND (Engine = '~' OR Engine = ?) AND (Body = '~' OR Body = ?)");
/* 266 */     defaultSQL.put("SELECT b.Description FROM OptionByte_Bit_Descrpt a, OptionByte_Descrpt b WHERE a.Model_Year = ? AND a.Make = ? AND a.Line = ? AND (a.Series = '~' OR a.Series = ?) AND (a.Engine = '~' OR a.Engine = ?) AND (a.Body = '~' OR a.Body = ?) AND (a.Device_ID = -1 OR a.Device_ID = ?) AND (a.Block_No = -1 OR a.Block_No = ?) AND (a.Byte_Offset = -1 OR a.Byte_Offset = ?) AND a.Bit_Offset = ? AND ( a.Bit_Descrpt_ID = b.Descrpt_ID ) AND b.Language_Code = ?", "SELECT b.Description FROM OptionByte_Bit_Descrpt a, OptionByte_Descrpt b WHERE a.Model_Year = ? AND a.Make = ? AND a.Line = ? AND (a.Series = '~' OR a.Series = ?) AND (a.Engine = '~' OR a.Engine = ?) AND (a.Body = '~' OR a.Body = ?) AND (a.Device_ID = -1 OR a.Device_ID = ?) AND (a.Block_No = -1 OR a.Block_No = ?) AND (a.Byte_Offset = -1 OR a.Byte_Offset = ?) AND a.Bit_Offset = ? AND ( a.Bit_Descrpt_ID = b.Descrpt_ID ) AND b.Language_Code = ?");
/* 267 */     defaultSQL.put("SELECT DISTINCT Descrpt_ID, Description FROM OptionByte_Descrpt WHERE Descrpt_ID IN (#list#) AND Language_Code = ?", "SELECT DISTINCT Descrpt_ID, Description FROM OptionByte_Descrpt WHERE Descrpt_ID IN (#list#) AND Language_Code = ?");
/* 268 */     defaultSQL.put("SELECT DISTINCT End_Item_No FROM EIL", "SELECT DISTINCT End_Item_No FROM EIL");
/* 269 */     defaultSQL.put("SELECT Part_No, CVN FROM CVN", "SELECT Part_No, CVN FROM CVN");
/* 270 */     defaultSQL.put("SELECT Sec_List FROM Security_Groups WHERE Sec_Id = ?", "SELECT Sec_List FROM Security_Groups WHERE Sec_Id = ?");
/* 271 */     defaultSQL.put("SELECT Language_Code, Method_Id, Description FROM Programming_Method_Description", "SELECT Language_Code, Method_Id, Description FROM Programming_Method_Description");
/* 272 */     defaultSQL.put("SELECT Controller_Id, Language_Code, Controller_Name, Description, Desc_Indx, Prog_Type_ID FROM Controller_Description", "SELECT Controller_Id, Language_Code, Controller_Name, Description, Desc_Indx, Prog_Type_ID FROM Controller_Description");
/* 273 */     defaultSQL.put("SELECT Controller_Id, Language_Code, Controller_Name, Description, Desc_Indx FROM Controller_Description", "SELECT Controller_Id, Language_Code, Controller_Name, Description, Desc_Indx FROM Controller_Description");
/* 274 */     defaultSQL.put("SELECT Controller_Id,RPO_Code,Pre_RPO_Code,Post_RPO_Code,Device_Id,ReqMthID,Programming_Methods,Vci,Pre_Prog_Msgs,Post_Prog_Msgs,VIN_Pos,VIN_Value,VIN_Digit_4_OR_6,Beginning_Sequence,Ending_Sequence,REPLACE_MESSAGE_ID,REPLACE_ID,Desc_Indx,Sec_Id,Suppress_Sequence_Controller,SecCode_Required,Engine,Series,Line FROM Supported_Controllers WHERE Model_Year = ? AND Make = ? AND Line = ? AND (Series = '~' OR Series = ?) AND (Engine = '~' OR Engine = ?)", "SELECT Controller_Id,RPO_Code,Pre_RPO_Code,Post_RPO_Code,Device_Id,ReqMthID,Programming_Methods,Vci,Pre_Prog_Msgs,Post_Prog_Msgs,VIN_Pos,VIN_Value,VIN_Digit_4_OR_6,Beginning_Sequence,Ending_Sequence,REPLACE_MESSAGE_ID,REPLACE_ID,Desc_Indx,Sec_Id,Suppress_Sequence_Controller,SecCode_Required,Engine,Series,Line FROM Supported_Controllers WHERE Model_Year = ? AND Make = ? AND Line = ? AND (Series = '~' OR Series = ?) AND (Engine = '~' OR Engine = ?)");
/* 275 */     defaultSQL.put("Select Origin_Part_No_String, Module_Id_String From VCI_Origin_Members_String Where VCI = ?", "Select Origin_Part_No_String, Module_Id_String From VCI_Origin_Members_String Where VCI = ?");
/* 276 */     defaultSQL.put("Select VIN_Id from VCI_AsBuilt_Id where VIN_1To8 = ? and VIN_10 = ? and VIN_11 = ?", "Select VIN_Id from VCI_AsBuilt_Id where VIN_1To8 = ? and VIN_10 = ? and VIN_11 = ?");
/* 277 */     defaultSQL.put("Select VCI_String, Controller_Id_String from VCI_AsBuilt_String where VIN_12To17 = ? and VIN_Id = ?", "Select VCI_String, Controller_Id_String from VCI_AsBuilt_String where VIN_12To17 = ? and VIN_Id = ?");
/* 278 */     defaultSQL.put("SELECT Description FROM Pre_Post_Prog_Msg where Message_Id=? and Language_Code=?", "SELECT Description FROM Pre_Post_Prog_Msg where Message_Id=? and Language_Code=?");
/* 279 */     defaultSQL.put("SELECT Calibration_File FROM Calibration_Files WHERE Calibration_Part_No = ?", "SELECT Calibration_File FROM Calibration_Files WHERE Calibration_Part_No = ?");
/* 280 */     defaultSQL.put("SELECT Calibration_Part_No, FILESIZE, FILECHECKSUM, DOWNLOAD_SITE FROM Calibration_Files WHERE Calibration_Part_No IN (#list#)", "SELECT Calibration_Part_No, FILESIZE, FILECHECKSUM, DOWNLOAD_SITE FROM Calibration_Files WHERE Calibration_Part_No IN (#list#)");
/* 281 */     defaultSQL.put("SELECT a.Module_Id, a.Calibration_Part_No, FILESIZE, FILECHECKSUM, b.DOWNLOAD_SITE  FROM EIL a LEFT OUTER JOIN Calibration_Files b ON a.Calibration_Part_No = b.Calibration_Part_No WHERE a.End_Item_No = ? ORDER BY a.Module_Id", "SELECT a.Module_Id, a.Calibration_Part_No, FILESIZE, FILECHECKSUM, b.DOWNLOAD_SITE  FROM EIL a LEFT OUTER JOIN Calibration_Files b ON a.Calibration_Part_No = b.Calibration_Part_No WHERE a.End_Item_No = ? ORDER BY a.Module_Id");
/* 282 */     defaultSQL.put("SELECT Module_Id, Calibration_Part_No FROM EIL WHERE End_Item_No = ? ORDER BY Module_Id", "SELECT Module_Id, Calibration_Part_No FROM EIL WHERE End_Item_No = ? ORDER BY Module_Id");
/* 283 */     defaultSQL.put("SELECT ReqMethID,DeviceID,Protocol,Pin_Link,ReadVIN,VINReadType,VINAddresses,ReadParts,PartReadType,PartAddresses,PartFormat,PartLength,PartStartByte,ReadHWID,HWIDReadType,HWIDAddresses,HWIDFormat,HWIDLength,HWIDStartByte,ReadSecuritySeed,SpecialReqType,ReadCVN FROM Request_Method_Data where ReqMethID=?", "SELECT ReqMethID,DeviceID,Protocol,Pin_Link,ReadVIN,VINReadType,VINAddresses,ReadParts,PartReadType,PartAddresses,PartFormat,PartLength,PartStartByte,ReadHWID,HWIDReadType,HWIDAddresses,HWIDFormat,HWIDLength,HWIDStartByte,ReadSecuritySeed,SpecialReqType,ReadCVN FROM Request_Method_Data where ReqMethID=?");
/* 284 */     defaultSQL.put("SELECT ReqMethID,DeviceID,Protocol,Pin_Link,ReadVIN,VINReadType,VINAddresses,ReadParts,PartReadType,PartAddresses,PartFormat,PartLength,PartStartByte,ReadHWID,HWIDReadType,HWIDAddresses,HWIDFormat,HWIDLength,HWIDStartByte,ReadSecuritySeed,SpecialReqType,ReadCVN FROM Request_Method_Data", "SELECT ReqMethID,DeviceID,Protocol,Pin_Link,ReadVIN,VINReadType,VINAddresses,ReadParts,PartReadType,PartAddresses,PartFormat,PartLength,PartStartByte,ReadHWID,HWIDReadType,HWIDAddresses,HWIDFormat,HWIDLength,HWIDStartByte,ReadSecuritySeed,SpecialReqType,ReadCVN FROM Request_Method_Data");
/* 285 */     defaultSQL.put("SELECT count(*) FROM VTDNavInfo WHERE Value = ?", "SELECT count(*) FROM VTDNavInfo WHERE Value = ?");
/* 286 */     defaultSQL.put("SELECT DISTINCT Device_Id, Algo_No FROM VTD_Security_Info WHERE Model_Year = ? AND Make = ? AND Line = ? AND (Series = '~' OR Series = ?) AND (Engine = '~' OR Engine = ?) AND (Plant = '~' OR Plant = ?)", "SELECT DISTINCT Device_Id, Algo_No FROM VTD_Security_Info WHERE Model_Year = ? AND Make = ? AND Line = ? AND (Series = '~' OR Series = ?) AND (Engine = '~' OR Engine = ?) AND (Plant = '~' OR Plant = ?)");
/* 287 */     defaultSQL.put("SELECT Model_Year, Truck_Make, Truck_Description, Car_Make, Car_Description FROM PROM_Convert_Truck_Make", "SELECT Model_Year, Truck_Make, Truck_Description, Car_Make, Car_Description FROM PROM_Convert_Truck_Make");
/* 288 */     defaultSQL.put("SELECT SRS_Body FROM PROM_VIN_Line Where Model_year = ? AND SRS_CorT = ? AND SRS_Make = ? AND SRS_Series Like ?", "SELECT SRS_Body FROM PROM_VIN_Line Where Model_year = ? AND SRS_CorT = ? AND SRS_Make = ? AND SRS_Series Like ?");
/* 289 */     defaultSQL.put("SELECT ENG_RPO FROM PROM_VIN_Engine Where Model_Year = ? AND ENG_CorT = ? AND ENG_Divuse = ? AND ENG_Code = ? AND (ENG_Body Like ? OR ENG_Body Like ? OR ENG_Body = '*')", "SELECT ENG_RPO FROM PROM_VIN_Engine Where Model_Year = ? AND ENG_CorT = ? AND ENG_Divuse = ? AND ENG_Code = ? AND (ENG_Body Like ? OR ENG_Body Like ? OR ENG_Body = '*')");
/* 290 */     defaultSQL.put("SELECT DISTINCT PROM_No, Transm_Type, Air_Flag, PROM_Part_No, Broadcast_Code, Program_Part_No, OEM_No, ECU_Part_No, Scanner_ID FROM PROM_Data WHERE PROM_No IN (#list#)", "SELECT DISTINCT PROM_No, Transm_Type, Air_Flag, PROM_Part_No, Broadcast_Code, Program_Part_No, OEM_No, ECU_Part_No, Scanner_ID FROM PROM_Data WHERE PROM_No IN (#list#)");
/* 291 */     defaultSQL.put("SELECT ECU_Part_No, Controller, Language_Code, Description FROM PROM_ECU", "SELECT ECU_Part_No, Controller, Language_Code, Description FROM PROM_ECU");
/* 292 */     defaultSQL.put("SELECT DISTINCT a.PROM_No, a.RPO_Code, a.RPO_Type, a.RPO_Exists, a.RPO_Code_Label_ID FROM PROM_Supported_PROMs a, PROM_Supported_PROMs b WHERE b.Model_Year = ? AND b.Make = ? AND b.Line = ? AND (b.Series = ? OR b.Series = '~') AND b.RPO_Code = ? AND a.Model_Year = b.Model_Year AND a.Make = b.Make AND a.Line = b.Line AND a.Series = b.Series AND a.PROM_No = b.PROM_No", "SELECT DISTINCT a.PROM_No, a.RPO_Code, a.RPO_Type, a.RPO_Exists, a.RPO_Code_Label_ID FROM PROM_Supported_PROMs a, PROM_Supported_PROMs b WHERE b.Model_Year = ? AND b.Make = ? AND b.Line = ? AND (b.Series = ? OR b.Series = '~') AND b.RPO_Code = ? AND a.Model_Year = b.Model_Year AND a.Make = b.Make AND a.Line = b.Line AND a.Series = b.Series AND a.PROM_No = b.PROM_No");
/* 293 */     defaultSQL.put("SELECT RPO_Code, Language_Code, RPO_Type_no, Minimum_Model_Year, Maximum_Model_Year, Description FROM PROM_RPO_Description", "SELECT RPO_Code, Language_Code, RPO_Type_no, Minimum_Model_Year, Maximum_Model_Year, Description FROM PROM_RPO_Description");
/* 294 */     defaultSQL.put("SELECT RPO_Label_Id, Language_Code, Label FROM PROM_RPO_Code_Labels", "SELECT RPO_Label_Id, Language_Code, Label FROM PROM_RPO_Code_Labels");
/* 295 */     defaultSQL.put("SELECT RPO_Type, RPO_Code_Label_ID, Language_Code, Description FROM PROM_Emission_Type", "SELECT RPO_Type, RPO_Code_Label_ID, Language_Code, Description FROM PROM_Emission_Type");
/* 296 */     defaultSQL.put("SELECT Model_Year, Year_VIN FROM PROM_U_Model_Year", "SELECT Model_Year, Year_VIN FROM PROM_U_Model_Year");
/* 297 */     defaultSQL.put("SELECT Language_Code, Method_Id, Description FROM PROM_Prog_Method_Description", "SELECT Language_Code, Method_Id, Description FROM PROM_Prog_Method_Description");
/* 298 */     defaultSQL.put("SELECT DISTINCT Origin_Part_No FROM PROM_COP WHERE New_Part_No = ? AND Language_Code = ?", "SELECT DISTINCT Origin_Part_No FROM PROM_COP WHERE New_Part_No = ? AND Language_Code = ?");
/* 299 */     defaultSQL.put("SELECT Origin_Part_No, Old_Part_No, New_Part_No, Rep_Type, Rep_Description, Release_Comments FROM PROM_COP WHERE Origin_Part_No IN (#list#) AND Language_Code = ?", "SELECT Origin_Part_No, Old_Part_No, New_Part_No, Rep_Type, Rep_Description, Release_Comments FROM PROM_COP WHERE Origin_Part_No IN (#list#) AND Language_Code = ?");
/* 300 */     defaultSQL.put("SELECT DISTINCT PROM_No, RPO_Code, RPO_Type, RPO_Exists, RPO_Code_Label_ID FROM PROM_Supported_PROMs WHERE Model_Year = ? AND Make = ? AND Line = ? AND PROM_No IN (#list#)", "SELECT DISTINCT PROM_No, RPO_Code, RPO_Type, RPO_Exists, RPO_Code_Label_ID FROM PROM_Supported_PROMs WHERE Model_Year = ? AND Make = ? AND Line = ? AND PROM_No IN (#list#)");
/* 301 */     defaultSQL.put("SELECT DISTINCT a.ECU_Part_No, b.Calpak_Part_No, c.Description FROM PROM_ECU a, PROM_ECU_Calpak b, PROM_Calpak c WHERE b.ECU_Part_No = a.ECU_Part_No AND b.Calpak_Part_No = c.Calpak_Part_No AND c.Language_Code = ? AND a.Language_Code = c.Language_Code AND a.ECU_Part_No IN (#list#)", "SELECT DISTINCT a.ECU_Part_No, b.Calpak_Part_No, c.Description FROM PROM_ECU a, PROM_ECU_Calpak b, PROM_Calpak c WHERE b.ECU_Part_No = a.ECU_Part_No AND b.Calpak_Part_No = c.Calpak_Part_No AND c.Language_Code = ? AND a.Language_Code = c.Language_Code AND a.ECU_Part_No IN (#list#)");
/* 302 */     defaultSQL.put("SELECT BULLETIN_CHARSET, BULLETIN FROM BULLETINS WHERE LANGUAGE = ? AND BULLETIN_ID like ?", "SELECT BULLETIN_CHARSET, BULLETIN FROM BULLETINS WHERE LANGUAGE = ? AND BULLETIN_ID like ?");
/* 303 */     defaultSQL.put("SELECT SIO_ID FROM SIO_PROPERTY WHERE PROPERTY_TYPE = ? AND PROPERTY like ?", "SELECT SIO_ID FROM SIO_PROPERTY WHERE PROPERTY_TYPE = ? AND PROPERTY like ?");
/* 304 */     defaultSQL.put("SELECT SUBJECT FROM SIO_SUBJECT WHERE LOCALE = ? AND SIO_ID = ?", "SELECT SUBJECT FROM SIO_SUBJECT WHERE LOCALE = ? AND SIO_ID = ?");
/* 305 */     defaultSQL.put("SELECT TEXT_CHARSET, TEXT FROM SIO_TEXT WHERE LOCALE = ? AND SIO_ID = ?", "SELECT TEXT_CHARSET, TEXT FROM SIO_TEXT WHERE LOCALE = ? AND SIO_ID = ?");
/* 306 */     defaultSQL.put("SELECT GRAPHIC_MIMETYPE, GRAPHIC FROM SIO_IMAGE WHERE GRAPHIC_ID = ?", "SELECT GRAPHIC_MIMETYPE, GRAPHIC FROM SIO_IMAGE WHERE GRAPHIC_ID = ?");
/* 307 */     defaultSQL.put("SELECT GRAPHIC_MIMETYPE, GRAPHIC FROM SIO_GRAPHIC WHERE GRAPHIC_ID = ?", "SELECT GRAPHIC_MIMETYPE, GRAPHIC FROM SIO_GRAPHIC WHERE GRAPHIC_ID = ?");
/* 308 */     defaultSQL.put("SELECT HTML_CHARSET, HTML FROM HTML_INSTRUCTIONS WHERE LANGUAGE = ? AND INSTRUCTION_ID = ?", "SELECT HTML_CHARSET, HTML FROM HTML_INSTRUCTIONS WHERE LANGUAGE = ? AND INSTRUCTION_ID = ?");
/* 309 */     defaultSQL.put("SELECT GRAPHIC_MIMETYPE, GRAPHIC FROM INSTRUCTION_GRAPHICS WHERE GRAPHIC_ID = ?", "SELECT GRAPHIC_MIMETYPE, GRAPHIC FROM INSTRUCTION_GRAPHICS WHERE GRAPHIC_ID = ?");
/* 310 */     defaultSQL.put("SELECT Model_Year, Make, Line, TPM_Index FROM Supported_TPM", "SELECT Model_Year, Make, Line, TPM_Index FROM Supported_TPM");
/* 311 */     defaultSQL.put("SELECT TPM_Index, Option1, Option2, Option3, Option4, Option5 FROM TPM_LoadRange_Index", "SELECT TPM_Index, Option1, Option2, Option3, Option4, Option5 FROM TPM_LoadRange_Index");
/* 312 */     defaultSQL.put("SELECT Description, Value FROM TPM_Extended_Type1", "SELECT Description, Value FROM TPM_Extended_Type1");
/* 313 */     defaultSQL.put("SELECT Description, Value FROM TPM_LoadRangeC_Type1", "SELECT Description, Value FROM TPM_LoadRangeC_Type1");
/* 314 */     defaultSQL.put("SELECT Description, Value FROM TPM_LoadRangeD_Type1", "SELECT Description, Value FROM TPM_LoadRangeD_Type1");
/* 315 */     defaultSQL.put("SELECT Description, Value FROM TPM_LoadRangeE_Type1", "SELECT Description, Value FROM TPM_LoadRangeE_Type1");
/* 316 */     defaultSQL.put("SELECT Description, Value FROM TPM_Standard_Type1", "SELECT Description, Value FROM TPM_Standard_Type1");
/* 317 */     defaultSQL.put("SELECT LCID, ACP, Language_Acronym FROM Win_Languages", "SELECT LCID, ACP, Language_Acronym FROM Win_Languages");
/* 318 */     defaultSQL.put("SELECT String_Id, Language_Code, Description FROM Type4_String WHERE Language_Code = ?", "SELECT String_Id, Language_Code, Description FROM Type4_String WHERE Language_Code = ?");
/* 319 */     defaultSQL.put("SELECT RELEASE_TEXT FROM TYPE4_RELEASE WHERE CALIBRATION_PART_NO = ? AND LANGUAGE_CODE = ?", "SELECT RELEASE_TEXT FROM TYPE4_RELEASE WHERE CALIBRATION_PART_NO = ? AND LANGUAGE_CODE = ?");
/* 320 */     defaultSQL.put("SELECT PROGRAMMING_SEQUENCE_ID, CONTROLLER_ORDER, CONTROLLER_ID, PRE_PROGRAMMING_INST, POST_PROGRAMMING_INST, ON_FAILURE FROM PROGRAMMING_SEQUENCE ORDER BY 1,2", "SELECT PROGRAMMING_SEQUENCE_ID, CONTROLLER_ORDER, CONTROLLER_ID, PRE_PROGRAMMING_INST, POST_PROGRAMMING_INST, ON_FAILURE FROM PROGRAMMING_SEQUENCE ORDER BY 1,2");
/* 321 */     defaultSQL.put("SELECT PROGRAMMING_SEQUENCE_ID, CONTROLLER_ORDER, CONTROLLER_ID, PRE_PROGRAMMING_INST, POST_PROGRAMMING_INST, ON_FAILURE, REPLACE_MODE_SUPPORTED FROM PROGRAMMING_SEQUENCE ORDER BY 1,2", "SELECT PROGRAMMING_SEQUENCE_ID, CONTROLLER_ORDER, CONTROLLER_ID, PRE_PROGRAMMING_INST, POST_PROGRAMMING_INST, ON_FAILURE, REPLACE_MODE_SUPPORTED FROM PROGRAMMING_SEQUENCE ORDER BY 1,2");
/* 322 */     defaultSQL.put("SELECT PROGRAMMING_SEQUENCE_ID, PART_NO FROM PROGRAMMING_SEQUENCE_CONSTRNTS", "SELECT PROGRAMMING_SEQUENCE_ID, PART_NO FROM PROGRAMMING_SEQUENCE_CONSTRNTS");
/* 323 */     defaultSQL.put("SELECT DESCRIPTION FROM REPLACE_INSTRUCTIONS WHERE REPLACE_MESSAGE_ID=? and LANGUAGE_CODE=?", "SELECT DESCRIPTION FROM REPLACE_INSTRUCTIONS WHERE REPLACE_MESSAGE_ID=? and LANGUAGE_CODE=?");
/* 324 */     defaultSQL.put("SELECT REPLACE_ID, ATTRIBUTE FROM REPLACE_ATTRIBUTES", "SELECT REPLACE_ID, ATTRIBUTE FROM REPLACE_ATTRIBUTES");
/* 325 */     defaultSQL.put("SELECT RPO_STRING_ID, MODEL_DESIGNATOR_ID, XML_ID FROM XML_VCI WHERE VCI = ?", "SELECT RPO_STRING_ID, MODEL_DESIGNATOR_ID, XML_ID FROM XML_VCI WHERE VCI = ?");
/* 326 */     defaultSQL.put("SELECT RPO_STRING FROM RPO_STRING WHERE RPO_STRING_ID = ?", "SELECT RPO_STRING FROM RPO_STRING WHERE RPO_STRING_ID = ?");
/* 327 */     defaultSQL.put("SELECT MODEL_DESIGNATOR FROM MODEL_DESIGNATOR WHERE MODEL_DESIGNATOR_ID = ?", "SELECT MODEL_DESIGNATOR FROM MODEL_DESIGNATOR WHERE MODEL_DESIGNATOR_ID = ?");
/* 328 */     defaultSQL.put("SELECT XML_ORIGIN_PART_NBR, TYPE4_ORIGIN_PART_NBR FROM XML_PARTS WHERE XML_ID = ?", "SELECT XML_ORIGIN_PART_NBR, TYPE4_ORIGIN_PART_NBR FROM XML_PARTS WHERE XML_ID = ?");
/* 329 */     defaultSQL.put("SELECT SecCode FROM SecCode_Asbuilt WHERE VIN = ?", "SELECT SecCode FROM SecCode_Asbuilt WHERE VIN = ?");
/* 330 */     defaultSQL.put("Select VIN_Id, Date_Time from VCI_AsBuilt_Id where VIN_1To8 = ? and VIN_10 = ? and VIN_11 = ?", "Select VIN_Id, Date_Time from VCI_AsBuilt_Id where VIN_1To8 = ? and VIN_10 = ? and VIN_11 = ?");
/* 331 */     defaultSQL.put("Select Origin_Part_No_String, Module_Id_String, Date_Time From VCI_Origin_Members_String Where VCI = ?", "Select Origin_Part_No_String, Module_Id_String, Date_Time From VCI_Origin_Members_String Where VCI = ?");
/* 332 */     tbSQL.put("SELECT RPO_Code, Language_Code, Description FROM RPO_Description", "SELECT RPO_Code, Language_Code CAST CHAR(*), Description FROM RPO_Description");
/* 333 */     tbSQL.put("SELECT RPO_Label_Id, Language_Code, Label FROM RPO_Code_Labels", "SELECT RPO_Label_Id, Language_Code CAST CHAR(*), Label FROM RPO_Code_Labels");
/* 334 */     tbSQL.put("SELECT Description_Id, Language_Code, Rep_Description, Short_Description FROM Part_Description", "SELECT Description_Id, Language_Code CAST CHAR(*), Rep_Description, Short_Description FROM Part_Description");
/* 335 */     tbSQL.put("SELECT Language_Code, Method_Id, Description FROM Programming_Method_Description", "SELECT Language_Code CAST CHAR(*), Method_Id, Description FROM Programming_Method_Description");
/* 336 */     tbSQL.put("SELECT Controller_Id, Language_Code, Controller_Name, Description, Desc_Indx, Prog_Type_ID FROM Controller_Description", "SELECT Controller_Id, Language_Code CAST CHAR(*), Controller_Name, Description, Desc_Indx, Prog_Type_ID FROM Controller_Description");
/* 337 */     tbSQL.put("SELECT Controller_Id, Language_Code, Controller_Name, Description, Desc_Indx FROM Controller_Description", "SELECT Controller_Id, Language_Code CAST CHAR(*), Controller_Name, Description, Desc_Indx FROM Controller_Description");
/* 338 */     tbSQL.put("SELECT Description FROM Pre_Post_Prog_Msg where Message_Id=? and Language_Code=?", "SELECT Description FROM Pre_Post_Prog_Msg where Message_Id = ? and Language_Code CAST CHAR(*) = ?");
/* 339 */     tbSQL.put("SELECT Calibration_Part_No, FILESIZE, FILECHECKSUM, DOWNLOAD_SITE FROM Calibration_Files WHERE Calibration_Part_No IN (#list#)", "SELECT Calibration_Part_No, SIZE OF Calibration_File as FILESIZE FROM Calibration_Files WHERE Calibration_Part_No IN (#list#)");
/* 340 */     tbSQL.put("SELECT a.Module_Id, a.Calibration_Part_No, FILESIZE, FILECHECKSUM, b.DOWNLOAD_SITE  FROM EIL a LEFT OUTER JOIN Calibration_Files b ON a.Calibration_Part_No = b.Calibration_Part_No WHERE a.End_Item_No = ? ORDER BY a.Module_Id", "SELECT a.Module_Id, a.Calibration_Part_No, SIZE OF b.Calibration_File as FILESIZE FROM EIL a LEFT OUTER JOIN Calibration_Files b ON a.Calibration_Part_No = b.Calibration_Part_No WHERE a.End_Item_No = ? ORDER BY a.Module_Id");
/* 341 */     tbSQL.put("SELECT ECU_Part_No, Controller, Language_Code, Description FROM PROM_ECU", "SELECT ECU_Part_No, Controller, Language_Code CAST CHAR(*), Description FROM PROM_ECU");
/* 342 */     tbSQL.put("SELECT RPO_Code, Language_Code, RPO_Type_no, Minimum_Model_Year, Maximum_Model_Year, Description FROM PROM_RPO_Description", "SELECT RPO_Code, Language_Code CAST CHAR(*), RPO_Type_no, Minimum_Model_Year, Maximum_Model_Year, Description FROM PROM_RPO_Description");
/* 343 */     tbSQL.put("SELECT RPO_Label_Id, Language_Code, Label FROM PROM_RPO_Code_Labels", "SELECT RPO_Label_Id, Language_Code CAST CHAR(*), Label FROM PROM_RPO_Code_Labels");
/* 344 */     tbSQL.put("SELECT RPO_Type, RPO_Code_Label_ID, Language_Code, Description FROM PROM_Emission_Type", "SELECT RPO_Type, RPO_Code_Label_ID, Language_Code CAST CHAR(*), Description FROM PROM_Emission_Type");
/* 345 */     tbSQL.put("SELECT Language_Code, Method_Id, Description FROM PROM_Prog_Method_Description", "SELECT Language_Code CAST CHAR(*), Method_Id, Description FROM PROM_Prog_Method_Description");
/* 346 */     tbSQL.put("SELECT DISTINCT Origin_Part_No FROM PROM_COP WHERE New_Part_No = ? AND Language_Code = ?", "SELECT DISTINCT Origin_Part_No FROM PROM_COP WHERE New_Part_No = ? AND Language_Code CAST CHAR(*) = ?");
/* 347 */     tbSQL.put("SELECT DISTINCT a.ECU_Part_No, b.Calpak_Part_No, c.Description FROM PROM_ECU a, PROM_ECU_Calpak b, PROM_Calpak c WHERE b.ECU_Part_No = a.ECU_Part_No AND b.Calpak_Part_No = c.Calpak_Part_No AND c.Language_Code = ? AND a.Language_Code = c.Language_Code AND a.ECU_Part_No IN (#list#)", "SELECT DISTINCT a.ECU_Part_No, b.Calpak_Part_No, c.Description FROM PROM_ECU a, PROM_ECU_Calpak b, PROM_Calpak c WHERE b.ECU_Part_No = a.ECU_Part_No AND b.Calpak_Part_No = c.Calpak_Part_No AND c.Language_Code CAST CHAR(*) = ? AND a.Language_Code = c.Language_Code AND a.ECU_Part_No IN (#list#)");
/* 348 */     tbSQL.put("SELECT Controller_Id,RPO_Code,Pre_RPO_Code,Post_RPO_Code,Device_Id,ReqMthID,Programming_Methods,Vci,Pre_Prog_Msgs,Post_Prog_Msgs,VIN_Pos,VIN_Value,VIN_Digit_4_OR_6,Beginning_Sequence,Ending_Sequence,REPLACE_MESSAGE_ID,REPLACE_ID,Desc_Indx,Sec_Id,Suppress_Sequence_Controller,SecCode_Required,Engine,Series,Line FROM Supported_Controllers WHERE Model_Year = ? AND Make = ? AND Line = ? AND (Series = '~' OR Series = ?) AND (Engine = '~' OR Engine = ?)", "SELECT Controller_Id,RPO_Code,Pre_RPO_Code,Post_RPO_Code,Device_Id,ReqMthID,Programming_Methods,Vci,Pre_Prog_Msgs,Post_Prog_Msgs,VIN_Pos,VIN_Value,VIN_Digit_4_OR_6,Beginning_Sequence,Ending_Sequence,REPLACE_MESSAGE_ID,REPLACE_ID,Desc_Indx,Sec_Id,Suppress_Sequence_Controller,SecCode_Required,Engine,Series,Line FROM Supported_Controllers WHERE Model_Year = ? AND Make = ? AND Line = ? AND (Series = '~' OR Series = ?) AND (Engine = '~' OR Engine = ?)");
/* 349 */     tbSQL.put("SELECT PROGRAMMING_SEQUENCE_ID, CONTROLLER_ORDER, CONTROLLER_ID, PRE_PROGRAMMING_INST, POST_PROGRAMMING_INST, ON_FAILURE FROM PROGRAMMING_SEQUENCE ORDER BY 1,2", "SELECT Programming_Sequence_Id, Controller_Order, Controller_Id, Pre_Programming_Inst, Post_Programming_Inst, On_Failure FROM Programming_Sequence ORDER BY 1,2");
/* 350 */     tbSQL.put("SELECT PROGRAMMING_SEQUENCE_ID, CONTROLLER_ORDER, CONTROLLER_ID, PRE_PROGRAMMING_INST, POST_PROGRAMMING_INST, ON_FAILURE, REPLACE_MODE_SUPPORTED FROM PROGRAMMING_SEQUENCE ORDER BY 1,2", "SELECT Programming_Sequence_Id, Controller_Order, Controller_Id, Pre_Programming_Inst, Post_Programming_Inst, On_Failure, Replace_Mode_Supported FROM Programming_Sequence ORDER BY 1,2");
/* 351 */     tbSQL.put("SELECT PROGRAMMING_SEQUENCE_ID, PART_NO FROM PROGRAMMING_SEQUENCE_CONSTRNTS", "SELECT Programming_Sequence_Id, Part_No FROM Programming_Sequence_Constrnts");
/* 352 */     tbSQL.put("SELECT RPO_STRING_ID, MODEL_DESIGNATOR_ID, XML_ID FROM XML_VCI WHERE VCI = ?", "SELECT RPO_String_ID, Model_Designator_ID, XML_ID FROM XML_VCI WHERE VCI = ?");
/* 353 */     tbSQL.put("SELECT RPO_STRING FROM RPO_STRING WHERE RPO_STRING_ID = ?", "SELECT RPO_String  FROM RPO_String  WHERE RPO_String_ID = ?");
/* 354 */     tbSQL.put("SELECT MODEL_DESIGNATOR FROM MODEL_DESIGNATOR WHERE MODEL_DESIGNATOR_ID = ?", "SELECT Model_Designator  FROM Model_Designator  WHERE Model_Designator_ID = ?");
/* 355 */     tbSQL.put("SELECT XML_ORIGIN_PART_NBR, TYPE4_ORIGIN_PART_NBR FROM XML_PARTS WHERE XML_ID = ?", "SELECT XML_Origin_Part_Nbr, Type4_Origin_Part_Nbr  FROM XML_Parts  WHERE XML_ID = ?");
/* 356 */     msSQL.put("SELECT Model_Year, Truck_Make, Truck_Description, Car_Make, Car_Description FROM PROM_Convert_Truck_Make", "SELECT Model_Yr, Truck_Make, Truck_Description, Car_Make, Car_Description FROM PROM_Convert_Truck_Make");
/* 357 */     msSQL.put("SELECT Controller_Id,RPO_Code,Pre_RPO_Code,Post_RPO_Code,Device_Id,ReqMthID,Programming_Methods,Vci,Pre_Prog_Msgs,Post_Prog_Msgs,VIN_Pos,VIN_Value,VIN_Digit_4_OR_6,Beginning_Sequence,Ending_Sequence,REPLACE_MESSAGE_ID,REPLACE_ID,Desc_Indx,Sec_Id,Suppress_Sequence_Controller,SecCode_Required,Engine,Series,Line FROM Supported_Controllers WHERE Model_Year = ? AND Make = ? AND Line = ? AND (Series = '~' OR Series = ?) AND (Engine = '~' OR Engine = ?)", "SELECT Controller_Id, RPO_Code,Pre_RPO_Code,Post_RPO_Code,Device_Id,ReqMthID,Programming_Methods,Vci,Pre_Prog_Msgs,Post_Prog_Msgs,VIN_Pos,VIN_Value,VIN_Digit_4_OR_6,Beginning_Sequence,Ending_Sequence,REPLACE_MESSAGE_ID,REPLACE_ID,After_Market,Desc_Indx,Engine,Series,Line,Sec_Id FROM Supported_Controllers WHERE Model_Year = ? AND Make = ? AND Line = ? AND (Series = '~' OR Series = ?) AND (Engine = '~' OR Engine = ?)");
/* 358 */     msSQL.put("Select Controller_Id, VCI from VCI_AsBuilt where VIN = ?", "Select Controller_Id, VCI from VCI_AsBuilt where VIN = ?");
/* 359 */     msSQL.put("Select Origin_Part_No, Module_Id From VCI_Origin_Members Where VCI = ? Order By 2", "Select Origin_Part_No, Module_Id From VCI_Origin_Members Where VCI = ? Order By 2");
/*     */   }
/*     */   
/*     */   public static String getSQL(IDatabaseLink dblink, String query) {
/* 363 */     if (dblink.getDBMS() == 3) {
/* 364 */       String sql = (String)msSQL.get(query);
/* 365 */       return (sql != null) ? sql : (String)defaultSQL.get(query);
/* 366 */     }  if (dblink.getDBMS() == 2) {
/* 367 */       String sql = (String)tbSQL.get(query);
/* 368 */       return (sql != null) ? sql : (String)defaultSQL.get(query);
/*     */     } 
/* 370 */     DatabaseStatistics.getInstance().register(query);
/* 371 */     return (String)defaultSQL.get(query);
/*     */   }
/*     */ 
/*     */   
/*     */   public static String getSQL(IDatabaseLink dblink, String query, int lparams) {
/* 376 */     String sql = getSQL(dblink, query);
/* 377 */     StringBuffer list = new StringBuffer();
/* 378 */     for (int i = 0; i < lparams; i++) {
/* 379 */       if (i > 0) {
/* 380 */         list.append(',');
/*     */       }
/* 382 */       list.append('?');
/*     */     } 
/* 384 */     return StringUtilities.replace(sql, "#list#", list.toString());
/*     */   }
/*     */   
/*     */   public static String getString(IDatabaseLink dblink, SPSLanguage lg, ResultSet rs, String attribute) throws Exception {
/* 388 */     if (dblink.getDBMS() == 1) {
/* 389 */       return rs.getString(attribute);
/*     */     }
/* 391 */     return transform("windows-" + lg.getCP(), rs.getBytes(attribute));
/*     */   }
/*     */ 
/*     */   
/*     */   public static String getString(IDatabaseLink dblink, SPSLanguage lg, ResultSet rs, int attribute) throws Exception {
/* 396 */     if (dblink.getDBMS() == 1) {
/* 397 */       return rs.getString(attribute).trim();
/*     */     }
/* 399 */     return transform("windows-" + lg.getCP(), rs.getBytes(attribute));
/*     */   }
/*     */ 
/*     */   
/*     */   protected static String transform(String cp, byte[] bytes) throws Exception {
/* 404 */     if (bytes == null)
/* 405 */       return null; 
/* 406 */     String result = new String(bytes, cp);
/* 407 */     result = result.trim();
/* 408 */     if (cp.equalsIgnoreCase("windows-932")) {
/* 409 */       StringBuffer buffer = new StringBuffer(result.length());
/* 410 */       for (int i = 0; i < result.length(); i++) {
/* 411 */         char c = result.charAt(i);
/* 412 */         switch (c) {
/*     */           case '～':
/* 414 */             c = '〜';
/*     */             break;
/*     */           case '－':
/* 417 */             c = '−';
/*     */             break;
/*     */           case '＼':
/* 420 */             c = '\\';
/*     */             break;
/*     */           case '∥':
/* 423 */             c = '‖';
/*     */             break;
/*     */           case '￠':
/* 426 */             c = '¢';
/*     */             break;
/*     */           case '￡':
/* 429 */             c = '£';
/*     */             break;
/*     */           case '￢':
/* 432 */             c = '¬';
/*     */             break;
/*     */           case '￤':
/* 435 */             c = '¦';
/*     */             break;
/*     */         } 
/*     */         
/* 439 */         buffer.append(c);
/*     */       } 
/* 441 */       result = buffer.toString();
/*     */     } 
/* 443 */     return result;
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\server\implementation\adapter\nao\DBMS.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */