/*     */ package com.eoos.gm.tis2web.sps.server.implementation.adapter.global;
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
/*     */   public static final String COP = "SELECT * FROM COP";
/*     */   
/*     */   public static final String COP_CONTROLLERS = "SELECT Controller_Index_No,Model_Year,Make,Line,Series,Engine,Pre_RPO_Code,Post_RPO_Code,VIN_Pos,VIN_Value,Beginning_Sequence,Ending_Sequence FROM COP_Controllers";
/*     */   
/*     */   public static final String COP_USAGE = "SELECT Usage_Index_No, Controller_Index_No FROM COP_Usage_Associate";
/*     */   
/*     */   public static final String HARDWARE_LIST = "SELECT Hardware_Indx, Hardware_List, Description_Id FROM Hardware_List";
/*     */   
/*     */   public static final String HARDWARE_DESCRIPTION = "SELECT Description_Id FROM Hardware_List WHERE Hardware_Indx = ?";
/*     */   
/*     */   public static final String VCI_HARDWARE = "SELECT VCI, Hardware_Indx FROM VCI_Hardware";
/*     */   
/*     */   public static final String RPO_DESCRIPTIONS = "SELECT RPO_Code, Language_Code, Description FROM RPO_Description";
/*     */   
/*     */   public static final String RPO_CODE_LABELS = "SELECT RPO_Label_Id, Language_Code, Label FROM RPO_Code_Labels";
/*     */   
/*     */   public static final String RPO_POST_OPTIONS = "SELECT Post_RPO_Code, RPO_Label_Id FROM Post_Option_Label_Ids";
/*     */   
/*     */   public static final String RPO_OPTION_LISTS = "SELECT Option_List_Id, RPO_Code, RPO_Label_Id, Order FROM RPO_Option_List ORDER BY 4";
/*     */   
/*     */   public static final String PART_DESCRIPTIONS = "SELECT Description_Id, Language_Code, Rep_Description, Short_Description FROM Part_Description";
/*     */   
/*     */   public static final String VEHICLE_OPTION_LIST = "SELECT RPO_Code, RPO_Label_Id FROM Option_List WHERE Model_Year = ? AND Make = ? AND Line = ? AND (Series = '~' OR Series = ?) AND (Engine = '~' OR Engine = ?)";
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
/*     */   public static final String VCI_SUPPORTED_CONTROLLERS = "SELECT Controller_Id,RPO_Code,Pre_RPO_Code,Post_RPO_Code,Device_Id,ReqMthID,Programming_Methods,Vci,Pre_Prog_Msgs,Post_Prog_Msgs,VIN_Pos,VIN_Value,VIN_Digit_4_OR_6,Beginning_Sequence,Ending_Sequence,REPLACE_MESSAGE_ID,REPLACE_ID,Desc_Indx,Sec_Id,Suppress_Sequence_Controller,SecCode_required,Option_List_Id,Engine,Series,Line FROM Supported_Controllers WHERE Model_Year = ? AND Make = ? AND Line = ? AND (Series = '~' OR Series = ?) AND (Engine = '~' OR Engine = ?)";
/*     */   
/*     */   public static final String VCI_ORIGIN_PARTS_Quick_Update = "Select Origin_Part_No_String, Module_Id_String, Date_Time From VCI_Origin_Members_String Where VCI = ?";
/*     */   
/*     */   public static final String VCI_AsBuilt_ID_Quick_Update = "Select VIN_Id, Date_Time from VCI_AsBuilt_Id where VIN_1To8 = ? and VIN_10 = ? and VIN_11 = ?";
/*     */   
/*     */   public static final String VCI_ORIGIN_PARTS = "Select Origin_Part_No_String, Module_Id_String From VCI_Origin_Members_String Where VCI = ?";
/*     */   
/*     */   public static final String VCI_AsBuilt_ID = "Select VIN_Id from VCI_AsBuilt_Id where VIN_1To8 = ? and VIN_10 = ? and VIN_11 = ?";
/*     */   
/*     */   public static final String VCI_AsBuilt_String = "Select VCI_String, Controller_Id_String from VCI_AsBuilt_String where VIN_12To17 = ? and VIN_Id = ?";
/*     */   
/*     */   public static final String VCI_PROGRAMMING_MESSAGE = "SELECT Description FROM Pre_Post_Prog_Msg where Message_Id=? and Language_Code=?";
/*     */   
/*     */   public static final String CALIBRATION_FILE = "SELECT Calibration_File FROM Calibration_Files WHERE Calibration_Part_No = ?";
/*     */   
/*     */   public static final String CALIBRATION_FILE_INFO = "SELECT Calibration_Part_No, FILESIZE, FILECHECKSUM FROM Calibration_Files WHERE Calibration_Part_No IN (#list#)";
/*     */   
/*     */   public static final String CALIBRATION_FILE_INFO_EIL = "SELECT a.Module_Id, a.Calibration_Part_No, FILESIZE, FILECHECKSUM  FROM EIL a LEFT OUTER JOIN Calibration_Files b ON a.Calibration_Part_No = b.Calibration_Part_No WHERE a.End_Item_No = ? ORDER BY a.Module_Id";
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
/*     */   public static final String REPLACE_INSTRUCTIONS = "SELECT DESCRIPTION FROM REPLACE_INSTRUCTIONS WHERE Replace_Message_Id=? and Language_Code=?";
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
/*     */   public static final String SETTINGS = "SELECT ServiceCode, VIN, Attribute, Value FROM DB_Settings";
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
/*     */   public static final String TB_VCI_SUPPORTED_CONTROLLERS = "SELECT Controller_Id,RPO_Code,Pre_RPO_Code,Post_RPO_Code,Device_Id,ReqMthID,Programming_Methods,Vci,Pre_Prog_Msgs,Post_Prog_Msgs,VIN_Pos,VIN_Value,VIN_Digit_4_OR_6,Beginning_Sequence,Ending_Sequence,REPLACE_MESSAGE_ID,REPLACE_ID,Desc_Indx,Sec_Id,Suppress_Sequence_Controller,Engine,Series,Line FROM Supported_Controllers WHERE Model_Year = ? AND Make = ? AND Line = ? AND (Series = '~' OR Series = ?) AND (Engine = '~' OR Engine = ?)";
/*     */   
/*     */   public static final String TB_REPLACE_INSTRUCTIONS = "SELECT DESCRIPTION FROM REPLACE_INSTRUCTIONS WHERE REPLACE_MESSAGE_ID=? and LANGUAGE_CODE=?";
/*     */   
/*     */   public static final String TB_REPLACE_ATTRIBUTES = "SELECT REPLACE_ID, ATTRIBUTE FROM REPLACE_ATTRIBUTES";
/*     */   
/*     */   public static final String TB_PROGRAMMING_SEQUENCES = "SELECT Programming_Sequence_Id, Controller_Order, Controller_Id, Pre_Programming_Inst, Post_Programming_Inst, On_Failure FROM Programming_Sequence ORDER BY 1,2";
/*     */   
/*     */   public static final String TB_PROGRAMMING_SEQUENCES2 = "SELECT Programming_Sequence_Id, Controller_Order, Controller_Id, Pre_Programming_Inst, Post_Programming_Inst, On_Failure, Replace_Mode_Supported FROM Programming_Sequence ORDER BY 1,2";
/*     */   public static final String TB_PROGRAMMING_SEQUENCE_CONSTRAINTS = "SELECT Programming_Sequence_Id, Part_No FROM Programming_Sequence_Constrnts";
/*     */   public static final String TSQL_VCI_SUPPORTED_CONTROLLERS = "SELECT Controller_Id, RPO_Code,Pre_RPO_Code,Post_RPO_Code,Device_Id,ReqMthID,Programming_Methods,Vci,Pre_Prog_Msgs,Post_Prog_Msgs,VIN_Pos,VIN_Value,VIN_Digit_4_OR_6,Beginning_Sequence,Ending_Sequence,REPLACE_MESSAGE_ID,REPLACE_ID,After_Market,Desc_Indx,Engine,Series,Line,Sec_Id FROM Supported_Controllers WHERE Model_Year = ? AND Make = ? AND Line = ? AND (Series = '~' OR Series = ?) AND (Engine = '~' OR Engine = ?)";
/*     */   
/*     */   static {
/* 174 */     defaultSQL.put("SELECT * FROM COP", "SELECT * FROM COP");
/* 175 */     defaultSQL.put("SELECT Controller_Index_No,Model_Year,Make,Line,Series,Engine,Pre_RPO_Code,Post_RPO_Code,VIN_Pos,VIN_Value,Beginning_Sequence,Ending_Sequence FROM COP_Controllers", "SELECT Controller_Index_No,Model_Year,Make,Line,Series,Engine,Pre_RPO_Code,Post_RPO_Code,VIN_Pos,VIN_Value,Beginning_Sequence,Ending_Sequence FROM COP_Controllers");
/* 176 */     defaultSQL.put("SELECT Usage_Index_No, Controller_Index_No FROM COP_Usage_Associate", "SELECT Usage_Index_No, Controller_Index_No FROM COP_Usage_Associate");
/* 177 */     defaultSQL.put("SELECT Hardware_Indx, Hardware_List, Description_Id FROM Hardware_List", "SELECT Hardware_Indx, Hardware_List, Description_Id FROM Hardware_List");
/* 178 */     defaultSQL.put("SELECT Description_Id FROM Hardware_List WHERE Hardware_Indx = ?", "SELECT Description_Id FROM Hardware_List WHERE Hardware_Indx = ?");
/* 179 */     defaultSQL.put("SELECT VCI, Hardware_Indx FROM VCI_Hardware", "SELECT VCI, Hardware_Indx FROM VCI_Hardware");
/* 180 */     defaultSQL.put("SELECT RPO_Code, Language_Code, Description FROM RPO_Description", "SELECT RPO_Code, Language_Code, Description FROM RPO_Description");
/* 181 */     defaultSQL.put("SELECT RPO_Label_Id, Language_Code, Label FROM RPO_Code_Labels", "SELECT RPO_Label_Id, Language_Code, Label FROM RPO_Code_Labels");
/* 182 */     defaultSQL.put("SELECT Post_RPO_Code, RPO_Label_Id FROM Post_Option_Label_Ids", "SELECT Post_RPO_Code, RPO_Label_Id FROM Post_Option_Label_Ids");
/* 183 */     defaultSQL.put("SELECT Description_Id, Language_Code, Rep_Description, Short_Description FROM Part_Description", "SELECT Description_Id, Language_Code, Rep_Description, Short_Description FROM Part_Description");
/* 184 */     defaultSQL.put("SELECT RPO_Code, RPO_Label_Id FROM Option_List WHERE Model_Year = ? AND Make = ? AND Line = ? AND (Series = '~' OR Series = ?) AND (Engine = '~' OR Engine = ?)", "SELECT RPO_Code, RPO_Label_Id FROM Option_List WHERE Model_Year = ? AND Make = ? AND Line = ? AND (Series = '~' OR Series = ?) AND (Engine = '~' OR Engine = ?)");
/* 185 */     defaultSQL.put("SELECT DISTINCT End_Item_No FROM EIL", "SELECT DISTINCT End_Item_No FROM EIL");
/* 186 */     defaultSQL.put("SELECT Part_No, CVN FROM CVN", "SELECT Part_No, CVN FROM CVN");
/* 187 */     defaultSQL.put("SELECT Sec_List FROM Security_Groups WHERE Sec_Id = ?", "SELECT Sec_List FROM Security_Groups WHERE Sec_Id = ?");
/* 188 */     defaultSQL.put("SELECT Language_Code, Method_Id, Description FROM Programming_Method_Description", "SELECT Language_Code, Method_Id, Description FROM Programming_Method_Description");
/* 189 */     defaultSQL.put("SELECT Controller_Id, Language_Code, Controller_Name, Description, Desc_Indx, Prog_Type_ID FROM Controller_Description", "SELECT Controller_Id, Language_Code, Controller_Name, Description, Desc_Indx, Prog_Type_ID FROM Controller_Description");
/* 190 */     defaultSQL.put("SELECT Controller_Id, Language_Code, Controller_Name, Description, Desc_Indx FROM Controller_Description", "SELECT Controller_Id, Language_Code, Controller_Name, Description, Desc_Indx FROM Controller_Description");
/* 191 */     defaultSQL.put("SELECT Controller_Id,RPO_Code,Pre_RPO_Code,Post_RPO_Code,Device_Id,ReqMthID,Programming_Methods,Vci,Pre_Prog_Msgs,Post_Prog_Msgs,VIN_Pos,VIN_Value,VIN_Digit_4_OR_6,Beginning_Sequence,Ending_Sequence,REPLACE_MESSAGE_ID,REPLACE_ID,Desc_Indx,Sec_Id,Suppress_Sequence_Controller,SecCode_required,Option_List_Id,Engine,Series,Line FROM Supported_Controllers WHERE Model_Year = ? AND Make = ? AND Line = ? AND (Series = '~' OR Series = ?) AND (Engine = '~' OR Engine = ?)", "SELECT Controller_Id,RPO_Code,Pre_RPO_Code,Post_RPO_Code,Device_Id,ReqMthID,Programming_Methods,Vci,Pre_Prog_Msgs,Post_Prog_Msgs,VIN_Pos,VIN_Value,VIN_Digit_4_OR_6,Beginning_Sequence,Ending_Sequence,REPLACE_MESSAGE_ID,REPLACE_ID,Desc_Indx,Sec_Id,Suppress_Sequence_Controller,SecCode_required,Option_List_Id,Engine,Series,Line FROM Supported_Controllers WHERE Model_Year = ? AND Make = ? AND Line = ? AND (Series = '~' OR Series = ?) AND (Engine = '~' OR Engine = ?)");
/* 192 */     defaultSQL.put("Select Origin_Part_No_String, Module_Id_String From VCI_Origin_Members_String Where VCI = ?", "Select Origin_Part_No_String, Module_Id_String From VCI_Origin_Members_String Where VCI = ?");
/* 193 */     defaultSQL.put("Select VIN_Id from VCI_AsBuilt_Id where VIN_1To8 = ? and VIN_10 = ? and VIN_11 = ?", "Select VIN_Id from VCI_AsBuilt_Id where VIN_1To8 = ? and VIN_10 = ? and VIN_11 = ?");
/* 194 */     defaultSQL.put("Select VCI_String, Controller_Id_String from VCI_AsBuilt_String where VIN_12To17 = ? and VIN_Id = ?", "Select VCI_String, Controller_Id_String from VCI_AsBuilt_String where VIN_12To17 = ? and VIN_Id = ?");
/* 195 */     defaultSQL.put("SELECT Description FROM Pre_Post_Prog_Msg where Message_Id=? and Language_Code=?", "SELECT Description FROM Pre_Post_Prog_Msg where Message_Id=? and Language_Code=?");
/* 196 */     defaultSQL.put("SELECT Calibration_File FROM Calibration_Files WHERE Calibration_Part_No = ?", "SELECT Calibration_File FROM Calibration_Files WHERE Calibration_Part_No = ?");
/* 197 */     defaultSQL.put("SELECT Calibration_Part_No, FILESIZE, FILECHECKSUM FROM Calibration_Files WHERE Calibration_Part_No IN (#list#)", "SELECT Calibration_Part_No, FILESIZE, FILECHECKSUM FROM Calibration_Files WHERE Calibration_Part_No IN (#list#)");
/* 198 */     defaultSQL.put("SELECT a.Module_Id, a.Calibration_Part_No, FILESIZE, FILECHECKSUM  FROM EIL a LEFT OUTER JOIN Calibration_Files b ON a.Calibration_Part_No = b.Calibration_Part_No WHERE a.End_Item_No = ? ORDER BY a.Module_Id", "SELECT a.Module_Id, a.Calibration_Part_No, FILESIZE, FILECHECKSUM  FROM EIL a LEFT OUTER JOIN Calibration_Files b ON a.Calibration_Part_No = b.Calibration_Part_No WHERE a.End_Item_No = ? ORDER BY a.Module_Id");
/* 199 */     defaultSQL.put("SELECT Module_Id, Calibration_Part_No FROM EIL WHERE End_Item_No = ? ORDER BY Module_Id", "SELECT Module_Id, Calibration_Part_No FROM EIL WHERE End_Item_No = ? ORDER BY Module_Id");
/* 200 */     defaultSQL.put("SELECT ReqMethID,DeviceID,Protocol,Pin_Link,ReadVIN,VINReadType,VINAddresses,ReadParts,PartReadType,PartAddresses,PartFormat,PartLength,PartStartByte,ReadHWID,HWIDReadType,HWIDAddresses,HWIDFormat,HWIDLength,HWIDStartByte,ReadSecuritySeed,SpecialReqType,ReadCVN FROM Request_Method_Data where ReqMethID=?", "SELECT ReqMethID,DeviceID,Protocol,Pin_Link,ReadVIN,VINReadType,VINAddresses,ReadParts,PartReadType,PartAddresses,PartFormat,PartLength,PartStartByte,ReadHWID,HWIDReadType,HWIDAddresses,HWIDFormat,HWIDLength,HWIDStartByte,ReadSecuritySeed,SpecialReqType,ReadCVN FROM Request_Method_Data where ReqMethID=?");
/* 201 */     defaultSQL.put("SELECT ReqMethID,DeviceID,Protocol,Pin_Link,ReadVIN,VINReadType,VINAddresses,ReadParts,PartReadType,PartAddresses,PartFormat,PartLength,PartStartByte,ReadHWID,HWIDReadType,HWIDAddresses,HWIDFormat,HWIDLength,HWIDStartByte,ReadSecuritySeed,SpecialReqType,ReadCVN FROM Request_Method_Data", "SELECT ReqMethID,DeviceID,Protocol,Pin_Link,ReadVIN,VINReadType,VINAddresses,ReadParts,PartReadType,PartAddresses,PartFormat,PartLength,PartStartByte,ReadHWID,HWIDReadType,HWIDAddresses,HWIDFormat,HWIDLength,HWIDStartByte,ReadSecuritySeed,SpecialReqType,ReadCVN FROM Request_Method_Data");
/* 202 */     defaultSQL.put("SELECT count(*) FROM VTDNavInfo WHERE Value = ?", "SELECT count(*) FROM VTDNavInfo WHERE Value = ?");
/* 203 */     defaultSQL.put("SELECT DISTINCT Device_Id, Algo_No FROM VTD_Security_Info WHERE Model_Year = ? AND Make = ? AND Line = ? AND (Series = '~' OR Series = ?) AND (Engine = '~' OR Engine = ?) AND (Plant = '~' OR Plant = ?)", "SELECT DISTINCT Device_Id, Algo_No FROM VTD_Security_Info WHERE Model_Year = ? AND Make = ? AND Line = ? AND (Series = '~' OR Series = ?) AND (Engine = '~' OR Engine = ?) AND (Plant = '~' OR Plant = ?)");
/* 204 */     defaultSQL.put("SELECT BULLETIN_CHARSET, BULLETIN FROM BULLETINS WHERE LANGUAGE = ? AND BULLETIN_ID like ?", "SELECT BULLETIN_CHARSET, BULLETIN FROM BULLETINS WHERE LANGUAGE = ? AND BULLETIN_ID like ?");
/* 205 */     defaultSQL.put("SELECT SIO_ID FROM SIO_PROPERTY WHERE PROPERTY_TYPE = ? AND PROPERTY like ?", "SELECT SIO_ID FROM SIO_PROPERTY WHERE PROPERTY_TYPE = ? AND PROPERTY like ?");
/* 206 */     defaultSQL.put("SELECT SUBJECT FROM SIO_SUBJECT WHERE LOCALE = ? AND SIO_ID = ?", "SELECT SUBJECT FROM SIO_SUBJECT WHERE LOCALE = ? AND SIO_ID = ?");
/* 207 */     defaultSQL.put("SELECT TEXT_CHARSET, TEXT FROM SIO_TEXT WHERE LOCALE = ? AND SIO_ID = ?", "SELECT TEXT_CHARSET, TEXT FROM SIO_TEXT WHERE LOCALE = ? AND SIO_ID = ?");
/* 208 */     defaultSQL.put("SELECT GRAPHIC_MIMETYPE, GRAPHIC FROM SIO_IMAGE WHERE GRAPHIC_ID = ?", "SELECT GRAPHIC_MIMETYPE, GRAPHIC FROM SIO_IMAGE WHERE GRAPHIC_ID = ?");
/* 209 */     defaultSQL.put("SELECT GRAPHIC_MIMETYPE, GRAPHIC FROM SIO_GRAPHIC WHERE GRAPHIC_ID = ?", "SELECT GRAPHIC_MIMETYPE, GRAPHIC FROM SIO_GRAPHIC WHERE GRAPHIC_ID = ?");
/* 210 */     defaultSQL.put("SELECT HTML_CHARSET, HTML FROM HTML_INSTRUCTIONS WHERE LANGUAGE = ? AND INSTRUCTION_ID = ?", "SELECT HTML_CHARSET, HTML FROM HTML_INSTRUCTIONS WHERE LANGUAGE = ? AND INSTRUCTION_ID = ?");
/* 211 */     defaultSQL.put("SELECT GRAPHIC_MIMETYPE, GRAPHIC FROM INSTRUCTION_GRAPHICS WHERE GRAPHIC_ID = ?", "SELECT GRAPHIC_MIMETYPE, GRAPHIC FROM INSTRUCTION_GRAPHICS WHERE GRAPHIC_ID = ?");
/* 212 */     defaultSQL.put("SELECT Model_Year, Make, Line, TPM_Index FROM Supported_TPM", "SELECT Model_Year, Make, Line, TPM_Index FROM Supported_TPM");
/* 213 */     defaultSQL.put("SELECT TPM_Index, Option1, Option2, Option3, Option4, Option5 FROM TPM_LoadRange_Index", "SELECT TPM_Index, Option1, Option2, Option3, Option4, Option5 FROM TPM_LoadRange_Index");
/* 214 */     defaultSQL.put("SELECT Description, Value FROM TPM_Extended_Type1", "SELECT Description, Value FROM TPM_Extended_Type1");
/* 215 */     defaultSQL.put("SELECT Description, Value FROM TPM_LoadRangeC_Type1", "SELECT Description, Value FROM TPM_LoadRangeC_Type1");
/* 216 */     defaultSQL.put("SELECT Description, Value FROM TPM_LoadRangeD_Type1", "SELECT Description, Value FROM TPM_LoadRangeD_Type1");
/* 217 */     defaultSQL.put("SELECT Description, Value FROM TPM_LoadRangeE_Type1", "SELECT Description, Value FROM TPM_LoadRangeE_Type1");
/* 218 */     defaultSQL.put("SELECT Description, Value FROM TPM_Standard_Type1", "SELECT Description, Value FROM TPM_Standard_Type1");
/* 219 */     defaultSQL.put("SELECT LCID, ACP, Language_Acronym FROM Win_Languages", "SELECT LCID, ACP, Language_Acronym FROM Win_Languages");
/* 220 */     defaultSQL.put("SELECT String_Id, Language_Code, Description FROM Type4_String WHERE Language_Code = ?", "SELECT String_Id, Language_Code, Description FROM Type4_String WHERE Language_Code = ?");
/* 221 */     defaultSQL.put("SELECT RELEASE_TEXT FROM TYPE4_RELEASE WHERE CALIBRATION_PART_NO = ? AND LANGUAGE_CODE = ?", "SELECT RELEASE_TEXT FROM TYPE4_RELEASE WHERE CALIBRATION_PART_NO = ? AND LANGUAGE_CODE = ?");
/* 222 */     defaultSQL.put("SELECT PROGRAMMING_SEQUENCE_ID, CONTROLLER_ORDER, CONTROLLER_ID, PRE_PROGRAMMING_INST, POST_PROGRAMMING_INST, ON_FAILURE FROM PROGRAMMING_SEQUENCE ORDER BY 1,2", "SELECT PROGRAMMING_SEQUENCE_ID, CONTROLLER_ORDER, CONTROLLER_ID, PRE_PROGRAMMING_INST, POST_PROGRAMMING_INST, ON_FAILURE FROM PROGRAMMING_SEQUENCE ORDER BY 1,2");
/* 223 */     defaultSQL.put("SELECT PROGRAMMING_SEQUENCE_ID, CONTROLLER_ORDER, CONTROLLER_ID, PRE_PROGRAMMING_INST, POST_PROGRAMMING_INST, ON_FAILURE, REPLACE_MODE_SUPPORTED FROM PROGRAMMING_SEQUENCE ORDER BY 1,2", "SELECT PROGRAMMING_SEQUENCE_ID, CONTROLLER_ORDER, CONTROLLER_ID, PRE_PROGRAMMING_INST, POST_PROGRAMMING_INST, ON_FAILURE, REPLACE_MODE_SUPPORTED FROM PROGRAMMING_SEQUENCE ORDER BY 1,2");
/* 224 */     defaultSQL.put("SELECT PROGRAMMING_SEQUENCE_ID, PART_NO FROM PROGRAMMING_SEQUENCE_CONSTRNTS", "SELECT PROGRAMMING_SEQUENCE_ID, PART_NO FROM PROGRAMMING_SEQUENCE_CONSTRNTS");
/* 225 */     defaultSQL.put("SELECT DESCRIPTION FROM REPLACE_INSTRUCTIONS WHERE Replace_Message_Id=? and Language_Code=?", "SELECT DESCRIPTION FROM REPLACE_INSTRUCTIONS WHERE Replace_Message_Id=? and Language_Code=?");
/* 226 */     defaultSQL.put("SELECT REPLACE_ID, ATTRIBUTE FROM REPLACE_ATTRIBUTES", "SELECT REPLACE_ID, ATTRIBUTE FROM REPLACE_ATTRIBUTES");
/* 227 */     defaultSQL.put("SELECT RPO_STRING_ID, MODEL_DESIGNATOR_ID, XML_ID FROM XML_VCI WHERE VCI = ?", "SELECT RPO_STRING_ID, MODEL_DESIGNATOR_ID, XML_ID FROM XML_VCI WHERE VCI = ?");
/* 228 */     defaultSQL.put("SELECT RPO_STRING FROM RPO_STRING WHERE RPO_STRING_ID = ?", "SELECT RPO_STRING FROM RPO_STRING WHERE RPO_STRING_ID = ?");
/* 229 */     defaultSQL.put("SELECT MODEL_DESIGNATOR FROM MODEL_DESIGNATOR WHERE MODEL_DESIGNATOR_ID = ?", "SELECT MODEL_DESIGNATOR FROM MODEL_DESIGNATOR WHERE MODEL_DESIGNATOR_ID = ?");
/* 230 */     defaultSQL.put("SELECT XML_ORIGIN_PART_NBR, TYPE4_ORIGIN_PART_NBR FROM XML_PARTS WHERE XML_ID = ?", "SELECT XML_ORIGIN_PART_NBR, TYPE4_ORIGIN_PART_NBR FROM XML_PARTS WHERE XML_ID = ?");
/* 231 */     defaultSQL.put("SELECT SecCode FROM SecCode_Asbuilt WHERE VIN = ?", "SELECT SecCode FROM SecCode_Asbuilt WHERE VIN = ?");
/* 232 */     defaultSQL.put("SELECT Option_List_Id, RPO_Code, RPO_Label_Id, Order FROM RPO_Option_List ORDER BY 4", "SELECT Option_List_Id, RPO_Code, RPO_Label_Id, Order FROM RPO_Option_List ORDER BY 4");
/* 233 */     defaultSQL.put("SELECT ServiceCode, VIN, Attribute, Value FROM DB_Settings", "SELECT ServiceCode, VIN, Attribute, Value FROM DB_Settings");
/* 234 */     defaultSQL.put("Select VIN_Id, Date_Time from VCI_AsBuilt_Id where VIN_1To8 = ? and VIN_10 = ? and VIN_11 = ?", "Select VIN_Id, Date_Time from VCI_AsBuilt_Id where VIN_1To8 = ? and VIN_10 = ? and VIN_11 = ?");
/* 235 */     defaultSQL.put("Select Origin_Part_No_String, Module_Id_String, Date_Time From VCI_Origin_Members_String Where VCI = ?", "Select Origin_Part_No_String, Module_Id_String, Date_Time From VCI_Origin_Members_String Where VCI = ?");
/* 236 */     tbSQL.put("SELECT RPO_Code, Language_Code, Description FROM RPO_Description", "SELECT RPO_Code, Language_Code CAST CHAR(*), Description FROM RPO_Description");
/* 237 */     tbSQL.put("SELECT RPO_Label_Id, Language_Code, Label FROM RPO_Code_Labels", "SELECT RPO_Label_Id, Language_Code CAST CHAR(*), Label FROM RPO_Code_Labels");
/* 238 */     tbSQL.put("SELECT Description_Id, Language_Code, Rep_Description, Short_Description FROM Part_Description", "SELECT Description_Id, Language_Code CAST CHAR(*), Rep_Description, Short_Description FROM Part_Description");
/* 239 */     tbSQL.put("SELECT Language_Code, Method_Id, Description FROM Programming_Method_Description", "SELECT Language_Code CAST CHAR(*), Method_Id, Description FROM Programming_Method_Description");
/* 240 */     tbSQL.put("SELECT Controller_Id, Language_Code, Controller_Name, Description, Desc_Indx, Prog_Type_ID FROM Controller_Description", "SELECT Controller_Id, Language_Code CAST CHAR(*), Controller_Name, Description, Desc_Indx, Prog_Type_ID FROM Controller_Description");
/* 241 */     tbSQL.put("SELECT Controller_Id, Language_Code, Controller_Name, Description, Desc_Indx FROM Controller_Description", "SELECT Controller_Id, Language_Code CAST CHAR(*), Controller_Name, Description, Desc_Indx FROM Controller_Description");
/* 242 */     tbSQL.put("SELECT Description FROM Pre_Post_Prog_Msg where Message_Id=? and Language_Code=?", "SELECT Description FROM Pre_Post_Prog_Msg where Message_Id = ? and Language_Code CAST CHAR(*) = ?");
/* 243 */     tbSQL.put("SELECT Calibration_Part_No, FILESIZE, FILECHECKSUM FROM Calibration_Files WHERE Calibration_Part_No IN (#list#)", "SELECT Calibration_Part_No, SIZE OF Calibration_File as FILESIZE FROM Calibration_Files WHERE Calibration_Part_No IN (#list#)");
/* 244 */     tbSQL.put("SELECT a.Module_Id, a.Calibration_Part_No, FILESIZE, FILECHECKSUM  FROM EIL a LEFT OUTER JOIN Calibration_Files b ON a.Calibration_Part_No = b.Calibration_Part_No WHERE a.End_Item_No = ? ORDER BY a.Module_Id", "SELECT a.Module_Id, a.Calibration_Part_No, SIZE OF b.Calibration_File as FILESIZE FROM EIL a LEFT OUTER JOIN Calibration_Files b ON a.Calibration_Part_No = b.Calibration_Part_No WHERE a.End_Item_No = ? ORDER BY a.Module_Id");
/* 245 */     tbSQL.put("SELECT Controller_Id,RPO_Code,Pre_RPO_Code,Post_RPO_Code,Device_Id,ReqMthID,Programming_Methods,Vci,Pre_Prog_Msgs,Post_Prog_Msgs,VIN_Pos,VIN_Value,VIN_Digit_4_OR_6,Beginning_Sequence,Ending_Sequence,REPLACE_MESSAGE_ID,REPLACE_ID,Desc_Indx,Sec_Id,Suppress_Sequence_Controller,SecCode_required,Option_List_Id,Engine,Series,Line FROM Supported_Controllers WHERE Model_Year = ? AND Make = ? AND Line = ? AND (Series = '~' OR Series = ?) AND (Engine = '~' OR Engine = ?)", "SELECT Controller_Id,RPO_Code,Pre_RPO_Code,Post_RPO_Code,Device_Id,ReqMthID,Programming_Methods,Vci,Pre_Prog_Msgs,Post_Prog_Msgs,VIN_Pos,VIN_Value,VIN_Digit_4_OR_6,Beginning_Sequence,Ending_Sequence,REPLACE_MESSAGE_ID,REPLACE_ID,Desc_Indx,Sec_Id,Suppress_Sequence_Controller,Engine,Series,Line FROM Supported_Controllers WHERE Model_Year = ? AND Make = ? AND Line = ? AND (Series = '~' OR Series = ?) AND (Engine = '~' OR Engine = ?)");
/* 246 */     tbSQL.put("SELECT PROGRAMMING_SEQUENCE_ID, CONTROLLER_ORDER, CONTROLLER_ID, PRE_PROGRAMMING_INST, POST_PROGRAMMING_INST, ON_FAILURE FROM PROGRAMMING_SEQUENCE ORDER BY 1,2", "SELECT Programming_Sequence_Id, Controller_Order, Controller_Id, Pre_Programming_Inst, Post_Programming_Inst, On_Failure FROM Programming_Sequence ORDER BY 1,2");
/* 247 */     tbSQL.put("SELECT PROGRAMMING_SEQUENCE_ID, CONTROLLER_ORDER, CONTROLLER_ID, PRE_PROGRAMMING_INST, POST_PROGRAMMING_INST, ON_FAILURE, REPLACE_MODE_SUPPORTED FROM PROGRAMMING_SEQUENCE ORDER BY 1,2", "SELECT Programming_Sequence_Id, Controller_Order, Controller_Id, Pre_Programming_Inst, Post_Programming_Inst, On_Failure, Replace_Mode_Supported FROM Programming_Sequence ORDER BY 1,2");
/* 248 */     tbSQL.put("SELECT PROGRAMMING_SEQUENCE_ID, PART_NO FROM PROGRAMMING_SEQUENCE_CONSTRNTS", "SELECT Programming_Sequence_Id, Part_No FROM Programming_Sequence_Constrnts");
/* 249 */     msSQL.put("SELECT Controller_Id,RPO_Code,Pre_RPO_Code,Post_RPO_Code,Device_Id,ReqMthID,Programming_Methods,Vci,Pre_Prog_Msgs,Post_Prog_Msgs,VIN_Pos,VIN_Value,VIN_Digit_4_OR_6,Beginning_Sequence,Ending_Sequence,REPLACE_MESSAGE_ID,REPLACE_ID,Desc_Indx,Sec_Id,Suppress_Sequence_Controller,SecCode_required,Option_List_Id,Engine,Series,Line FROM Supported_Controllers WHERE Model_Year = ? AND Make = ? AND Line = ? AND (Series = '~' OR Series = ?) AND (Engine = '~' OR Engine = ?)", "SELECT Controller_Id, RPO_Code,Pre_RPO_Code,Post_RPO_Code,Device_Id,ReqMthID,Programming_Methods,Vci,Pre_Prog_Msgs,Post_Prog_Msgs,VIN_Pos,VIN_Value,VIN_Digit_4_OR_6,Beginning_Sequence,Ending_Sequence,REPLACE_MESSAGE_ID,REPLACE_ID,After_Market,Desc_Indx,Engine,Series,Line,Sec_Id FROM Supported_Controllers WHERE Model_Year = ? AND Make = ? AND Line = ? AND (Series = '~' OR Series = ?) AND (Engine = '~' OR Engine = ?)");
/*     */   }
/*     */   
/*     */   public static String getSQL(IDatabaseLink dblink, String query) {
/* 253 */     if (dblink.getDBMS() == 3) {
/* 254 */       String sql = (String)msSQL.get(query);
/* 255 */       return (sql != null) ? sql : (String)defaultSQL.get(query);
/* 256 */     }  if (dblink.getDBMS() == 2) {
/* 257 */       String sql = (String)tbSQL.get(query);
/* 258 */       return (sql != null) ? sql : (String)defaultSQL.get(query);
/*     */     } 
/* 260 */     DatabaseStatistics.getInstance().register(query);
/* 261 */     return (String)defaultSQL.get(query);
/*     */   }
/*     */ 
/*     */   
/*     */   public static String getSQL(IDatabaseLink dblink, String query, int lparams) {
/* 266 */     if (dblink.getDBMS() == 1) {
/* 267 */       DatabaseStatistics.getInstance().register(query);
/*     */     }
/* 269 */     String sql = getSQL(dblink, query);
/* 270 */     StringBuffer list = new StringBuffer();
/* 271 */     for (int i = 0; i < lparams; i++) {
/* 272 */       if (i > 0) {
/* 273 */         list.append(',');
/*     */       }
/* 275 */       list.append('?');
/*     */     } 
/* 277 */     return StringUtilities.replace(sql, "#list#", list.toString());
/*     */   }
/*     */   
/*     */   public static String getString(IDatabaseLink dblink, SPSLanguage lg, ResultSet rs, String attribute) throws Exception {
/* 281 */     if (dblink.getDBMS() == 1) {
/* 282 */       return rs.getString(attribute);
/*     */     }
/* 284 */     return transform("windows-" + lg.getCP(), rs.getBytes(attribute));
/*     */   }
/*     */ 
/*     */   
/*     */   public static String getString(IDatabaseLink dblink, SPSLanguage lg, ResultSet rs, int attribute) throws Exception {
/* 289 */     if (dblink.getDBMS() == 1) {
/* 290 */       return rs.getString(attribute).trim();
/*     */     }
/* 292 */     return transform("windows-" + lg.getCP(), rs.getBytes(attribute));
/*     */   }
/*     */ 
/*     */   
/*     */   protected static String transform(String cp, byte[] bytes) throws Exception {
/* 297 */     if (bytes == null)
/* 298 */       return null; 
/* 299 */     String result = new String(bytes, cp);
/* 300 */     result = result.trim();
/* 301 */     if (cp.equalsIgnoreCase("windows-932")) {
/* 302 */       StringBuffer buffer = new StringBuffer(result.length());
/* 303 */       for (int i = 0; i < result.length(); i++) {
/* 304 */         char c = result.charAt(i);
/* 305 */         switch (c) {
/*     */           case '～':
/* 307 */             c = '〜';
/*     */             break;
/*     */           case '－':
/* 310 */             c = '−';
/*     */             break;
/*     */           case '＼':
/* 313 */             c = '\\';
/*     */             break;
/*     */           case '∥':
/* 316 */             c = '‖';
/*     */             break;
/*     */           case '￠':
/* 319 */             c = '¢';
/*     */             break;
/*     */           case '￡':
/* 322 */             c = '£';
/*     */             break;
/*     */           case '￢':
/* 325 */             c = '¬';
/*     */             break;
/*     */           case '￤':
/* 328 */             c = '¦';
/*     */             break;
/*     */         } 
/*     */         
/* 332 */         buffer.append(c);
/*     */       } 
/* 334 */       result = buffer.toString();
/*     */     } 
/* 336 */     return result;
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\server\implementation\adapter\global\DBMS.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */