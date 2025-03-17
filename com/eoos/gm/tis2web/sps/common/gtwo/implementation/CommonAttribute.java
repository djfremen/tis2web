/*     */ package com.eoos.gm.tis2web.sps.common.gtwo.implementation;
/*     */ 
/*     */ import com.eoos.gm.tis2web.sps.service.cai.Attribute;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class CommonAttribute
/*     */ {
/*  10 */   public static final Attribute CLIENT = AttributeImpl.getInstance("sps.attribute.client");
/*     */   
/*  12 */   public static final Attribute TOOLS = AttributeImpl.getInstance("sps.attribute.tools");
/*     */   
/*  14 */   public static final Attribute TOOL = LocalAttributeImpl.getInstance("sps.attribute.tool");
/*     */   
/*  16 */   public static final Attribute TOOL_ID = AttributeImpl.getInstance("sps.attribute.tool.id");
/*     */   
/*  18 */   public static final Attribute VIT1_DEVICE_LIST = AttributeImpl.getInstance("sps.attribute.vit1-device-list");
/*     */   
/*  20 */   public static final Attribute VIT1_DEVICE_TYPE = AttributeImpl.getInstance("sps.attribute.vit1-device-type");
/*     */   
/*  22 */   public static final Attribute VIT1_OPTION_BYTES = AttributeImpl.getInstance("sps.attribute.vit1-option-bytes");
/*     */   
/*  24 */   public static final Attribute VIT1_ADAPTIVE_BYTES = AttributeImpl.getInstance("sps.attribute.vit1-adaptive-bytes");
/*     */   
/*  26 */   public static final Attribute VIT1_SEED_COUNT = AttributeImpl.getInstance("sps.attribute.vit1-seed-count");
/*     */   
/*  28 */   public static final Attribute VIT1_NAVIGATION_INFO = AttributeImpl.getInstance("sps.attribute.vit1-navigation-info");
/*     */   
/*  30 */   public static final Attribute DEVICE = AttributeImpl.getInstance("sps.attribute.device");
/*     */   
/*  32 */   public static final Attribute REMOTE_REPLACE_PREPARE_FOR_COMMUNICATION = AttributeImpl.getInstance("sps.attribute.remote-replace-prepare-for-communication");
/*     */   
/*  34 */   public static final Attribute MODE = AttributeImpl.getInstance("sps.attribute.mode");
/*     */   
/*  36 */   public static final Attribute BADGE = (Attribute)DisplayableAttributeImpl.getInstance("sps.attribute.badge");
/*     */   
/*  38 */   public static final Attribute SALESMAKE = (Attribute)DisplayableAttributeImpl.getInstance("sps.attribute.salesmake");
/*     */   
/*  40 */   public static final Attribute MODEL = (Attribute)DisplayableAttributeImpl.getInstance("sps.attribute.model");
/*     */   
/*  42 */   public static final Attribute MODELYEAR = (Attribute)DisplayableAttributeImpl.getInstance("sps.attribute.model-year");
/*     */   
/*  44 */   public static final Attribute CARLINE = (Attribute)DisplayableAttributeImpl.getInstance("sps.attribute.car-line");
/*     */   
/*  46 */   public static final Attribute ENGINE = (Attribute)DisplayableAttributeImpl.getInstance("sps.attribute.engine");
/*     */   
/*  48 */   public static final Attribute TRANSMISSION = (Attribute)DisplayableAttributeImpl.getInstance("sps.attribute.transmission");
/*     */   
/*  50 */   public static final Attribute PREPCOMMUNICATION = AttributeImpl.getInstance("sps.attribute.preparing-for-communication");
/*     */   
/*  52 */   public static final Attribute REPLACE_INSTRUCTIONS = AttributeImpl.getInstance("sps.attribute.replace-instructions");
/*     */   
/*  54 */   public static final Attribute CONTROLLER = (Attribute)DisplayableAttributeImpl.getInstance("sps.attribute.controller");
/*     */   
/*  56 */   public static final Attribute CONTROLLER_NAME = (Attribute)DisplayableAttributeImpl.getInstance("sps.attribute.controller.name");
/*     */   
/*  58 */   public static final Attribute CONTROLLER_METHOD = (Attribute)DisplayableAttributeImpl.getInstance("sps.attribute.controller.method");
/*     */   
/*  60 */   public static final Attribute CONTROLLER_REQUIRES_CUSTOM_CALIBRATION = AttributeImpl.getInstance("sps.attribute.controller.requires-custom-calibration");
/*     */   
/*  62 */   public static final Attribute VIT1 = AttributeImpl.getInstance("sps.attribute.vit1");
/*     */   
/*  64 */   public static final Attribute VIT1_TRANSFER = AttributeImpl.getInstance("sps.attribute.vit1-transfer");
/*     */   
/*  66 */   public static final Attribute TOOL_VIN = AttributeImpl.getInstance("sps.attribute.tool-vin");
/*     */   
/*  68 */   public static final Attribute VIN = (Attribute)DisplayableAttributeImpl.getInstance("vin");
/*     */   
/*  70 */   public static final Attribute DEVICE_ID = AttributeImpl.getInstance("sps.attribute.device-id");
/*     */   
/*  72 */   public static final Attribute VCI = AttributeImpl.getInstance("sps.attribute.vci");
/*     */   
/*  74 */   public static final Attribute DEALER_VCI = AttributeImpl.getInstance("sps.attribute.dealer-vci");
/*     */   
/*  76 */   public static final Attribute WARRANTY_CLAIM_CODE = AttributeImpl.getInstance("sps.attribute.warranty-claim-code");
/*     */   
/*  78 */   public static final Attribute TYPE4_DATA = AttributeImpl.getInstance("sps.attribute.type4-files");
/*     */   
/*  80 */   public static final Attribute TYPE4_STRINGS = AttributeImpl.getInstance("sps.attribute.type4-strings");
/*     */   
/*  82 */   public static final Attribute SATURN_DATA = AttributeImpl.getInstance("sps.attribute.saturn-data");
/*     */   
/*  84 */   public static final Attribute OPTION = AttributeImpl.getInstance("sps.attribute.option");
/*     */   
/*  86 */   public static final Attribute CONFIRMED_OPTIONS = AttributeImpl.getInstance("sps.attribute.options-confirmed");
/*     */   
/*  88 */   public static final Attribute ODOMETER = AttributeImpl.getInstance("sps.attribute.odometer");
/*     */   
/*  90 */   public static final Attribute KEYCODE = AttributeImpl.getInstance("sps.attribute.keycode");
/*     */   
/*  92 */   public static final Attribute SUMMARY = AttributeImpl.getInstance("sps.attribute.summary");
/*     */   
/*  94 */   public static final Attribute SUMMARY_DIGEST = AttributeImpl.getInstance("sps.attribute.summary-digest");
/*     */   
/*  96 */   public static final Attribute PRE_PROGRAMMING_INSTRUCTIONS = AttributeImpl.getInstance("sps.attribute.pre-prog-instructions");
/*     */   
/*  98 */   public static final Attribute INTERMEDIATE_PROGRAMMING_INSTRUCTIONS = AttributeImpl.getInstance("sps.attribute.intermediate-prog-instructions");
/*     */   
/* 100 */   public static final Attribute INTERMEDIATE_PRE_PROGRAMMING_INSTRUCTIONS = AttributeImpl.getInstance("sps.attribute.intermediate-pre-prog-instructions");
/*     */   
/* 102 */   public static final Attribute INTERMEDIATE_POST_PROGRAMMING_INSTRUCTIONS = AttributeImpl.getInstance("sps.attribute.intermediate-post-prog-instructions");
/*     */   
/* 104 */   public static final Attribute POST_PROGRAMMING_INSTRUCTIONS = AttributeImpl.getInstance("sps.attribute.post-prog-instructions");
/*     */   
/* 106 */   public static final Attribute INSTRUCTION_DOWNLOAD = AttributeImpl.getInstance("sps.attribute.instruction-download");
/*     */   
/* 108 */   public static final Attribute FINAL_INSTRUCTIONS = AttributeImpl.getInstance("sps.attribute.final-instructions");
/*     */   
/* 110 */   public static final Attribute FINISH = AttributeImpl.getInstance("sps.attribute.finish");
/*     */   
/* 112 */   public static final Attribute FAILURE = AttributeImpl.getInstance("sps.attribute.failure");
/*     */   
/* 114 */   public static final Attribute START = AttributeImpl.getInstance("sps.attribute.start");
/*     */   
/* 116 */   public static final Attribute CONTINUE = AttributeImpl.getInstance("sps.attribute.continue");
/*     */   
/* 118 */   public static final Attribute ARCHIVE = AttributeImpl.getInstance("sps.attribute.archive");
/*     */   
/* 120 */   public static final Attribute PART_FILE = AttributeImpl.getInstance("sps.attribute.part-file");
/*     */   
/* 122 */   public static final Attribute HARDWARE = (Attribute)DisplayableAttributeImpl.getInstance("sps.attribute.hardware");
/*     */   
/* 124 */   public static final Attribute HARDWARE_NUMBER = (Attribute)DisplayableAttributeImpl.getInstance("sps.attribute.hardware.number");
/*     */   
/* 126 */   public static final Attribute PROGRAMMING_DATA_SELECTION = AttributeImpl.getInstance("sps.attribute.programming-data");
/*     */   
/* 128 */   public static final Attribute CONFIRM_PROGRAMMING_DATA_DOWNLOAD_START = AttributeImpl.getInstance("sps.attribute.download.confirmed.start");
/*     */   
/* 130 */   public static final Attribute CONFIRM_PROGRAMMING_DATA_DOWNLOAD_FINISHED = AttributeImpl.getInstance("sps.attribute.download.confirmed.end");
/*     */   
/* 132 */   public static final Attribute CONFIRM_REPROGRAM_DISPLAY = AttributeImpl.getInstance("sps.attribute.confirm-reprogram-display");
/*     */   
/* 134 */   public static final Attribute SESSION_ID = AttributeImpl.getInstance("sps.session.id");
/*     */   
/* 136 */   public static final Attribute SESSION_TAG = AttributeImpl.getInstance("sps.session.tag");
/*     */   
/* 138 */   public static final Attribute SESSION_SIGNATURE = AttributeImpl.getInstance("sps.session.signature");
/*     */   
/* 140 */   public static final Attribute HARDWARE_KEY = AttributeImpl.getInstance("sps.attribute.hwk");
/*     */   
/* 142 */   public static final Attribute HARDWARE_KEY32 = AttributeImpl.getInstance("sps.attribute.hwk32");
/*     */   
/* 144 */   public static final Attribute SECURITY_CHECK = AttributeImpl.getInstance("sps.attribute.security-check");
/*     */   
/* 146 */   public static final Attribute PROCEED_SAME_VIN = AttributeImpl.getInstance("sps.attribute.proceed-same-vin");
/*     */   
/* 148 */   public static final Attribute LOCALE = AttributeImpl.getInstance("sps.locale.id");
/*     */   
/* 150 */   public static final Attribute BACCODE = AttributeImpl.getInstance("sps.client.baccode");
/*     */   
/* 152 */   public static final Attribute PRE_PROGRAMMING_INSTRUCTIONS_DATA = AttributeImpl.getInstance("sps.attribute.pre-prog-instructions.data");
/*     */   
/* 154 */   public static final Attribute POST_PROGRAMMING_INSTRUCTIONS_DATA = AttributeImpl.getInstance("sps.attribute.post-prog-instructions.data");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 159 */   public static final Attribute EXECUTION_MODE = AttributeImpl.getInstance("sps.attribute.execution.mode");
/*     */   
/* 161 */   public static final Attribute REPROGRAM = AttributeImpl.getInstance("sps.attribute.reprogram");
/*     */   
/* 163 */   public static final Attribute REPROGRAM_STATUS = AttributeImpl.getInstance("sps.attribute.reprogram.sequence.status");
/*     */   
/* 165 */   public static final Attribute SAME_CALIBRATIONS = AttributeImpl.getInstance("sps.attribute.same-calibrations");
/*     */   
/* 167 */   public static final Attribute REPROGRAM_PROTOCOL = AttributeImpl.getInstance("sps.attribute.reprogram-log");
/*     */   
/* 169 */   public static final Attribute LABOR_TIME_CONFIGURATION = AttributeImpl.getInstance("sps.attribute.labor-time.configuration");
/*     */   
/* 171 */   public static final Attribute LABOR_TIME_TRACKING = AttributeImpl.getInstance("sps.attribute.labor-time.tracking");
/*     */   
/* 173 */   public static final Attribute CLEAR_DTCS = AttributeImpl.getInstance("sps.clear.dtcs");
/*     */   
/* 175 */   public static final Attribute VEHICLE_LINK = AttributeImpl.getInstance("sps.vehicle.link");
/*     */   
/* 177 */   public static final Attribute VIT1_HISTORY = AttributeImpl.getInstance("sps.attribute.vit1.history");
/*     */   
/* 179 */   public static final Attribute DEVICE_J2534_NAME = AttributeImpl.getInstance("sps.attribute.j2534.driver.name");
/*     */   
/* 181 */   public static final Attribute DEVICE_J2534_VERSION = AttributeImpl.getInstance("sps.attribute.j2534.driver.version");
/*     */   
/* 183 */   public static final Attribute SECURITY_CODE = AttributeImpl.getInstance("sps.security.code");
/*     */   
/* 185 */   public static final Attribute CONTROLLER_SECURITY_CODE = AttributeImpl.getInstance("sps.controller.security.code");
/*     */   
/* 187 */   public static final Attribute SECURITY_ENABLED = AttributeImpl.getInstance("sps.security.enabled");
/*     */   
/* 189 */   public static final Attribute SPS_FUNCTION_SUPPORTED = AttributeImpl.getInstance("sps.function.enabled");
/*     */   
/* 191 */   public static final Attribute SEQUENCE = (Attribute)DisplayableAttributeImpl.getInstance("sps.attribute.sequence");
/*     */   
/* 193 */   public static final Attribute FUNCTION = (Attribute)DisplayableAttributeImpl.getInstance("sps.attribute.function");
/*     */   
/* 195 */   public static final Attribute SPS_ADAPTER_TYPE = AttributeImpl.getInstance("sps.adapter.type");
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\common\gtwo\implementation\CommonAttribute.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */