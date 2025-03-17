/*    */ package com.eoos.gm.tis2web.sps.common.gtwo.implementation;
/*    */ 
/*    */ import com.eoos.gm.tis2web.sps.common.gtwo.export.CustomException;
/*    */ 
/*    */ public class CommonException {
/*  6 */   public static final CustomException UnsupportedVehicle = ExceptionImpl.getInstance("sps.exception.unsupported.vehicle");
/*  7 */   public static final CustomException UnsupportedLocale = ExceptionImpl.getInstance("sps.exception.unsupported-locale");
/*  8 */   public static final CustomException UnsupportedController = ExceptionImpl.getInstance("sps.exception.unsupported-controller");
/*  9 */   public static final CustomException InvalidControllerSelection = ExceptionImpl.getInstance("sps.exception.invalid-controller-selection");
/* 10 */   public static final CustomException NoControllerSelectionPossible = ExceptionImpl.getInstance("sps.exception.no-controller-selection");
/* 11 */   public static final CustomException NoUsableControllerSelection = ExceptionImpl.getInstance("sps.exception.no-usable-controller-selection");
/* 12 */   public static final CustomException AmbiguousControllerSelection = ExceptionImpl.getInstance("sps.exception.ambiguous-controller-selection");
/* 13 */   public static final CustomException InvalidVCIInput = ExceptionImpl.getInstance("sps.exception.invalid-vci-input");
/* 14 */   public static final CustomException InvalidDealerVCI = ExceptionImpl.getInstance("sps.exception.invalid-dealer-vci");
/* 15 */   public static final CustomException ServerSideFailure = ExceptionImpl.getInstance("sps.exception.server-side-failure");
/* 16 */   public static final CustomException InvalidVIN = ExceptionImpl.getInstance("sps.exception.invalid-vin");
/* 17 */   public static final CustomException ChangedVIN = ExceptionImpl.getInstance("sps.exception.changed-vin");
/* 18 */   public static final CustomException NoControllers = ExceptionImpl.getInstance("sps.exception.no-controllers");
/* 19 */   public static final CustomException NoVCI = ExceptionImpl.getInstance("sps.exception.no-vci");
/* 20 */   public static final CustomException NonMatchingVCI = ExceptionImpl.getInstance("sps.exception.no-matching-vci");
/* 21 */   public static final CustomException NoProgrammingByArchive = ExceptionImpl.getInstance("sps.exception.software-archive-not-enabled");
/* 22 */   public static final CustomException LOADING_PROGRAMMING_FILE_FAILED = ExceptionImpl.getInstance("sps.exception.loading-archive-or-part-file-failed");
/* 23 */   public static final CustomException INVALID_ARCHIVE_VCI = ExceptionImpl.getInstance("sps.exception.invalid-archive-vci");
/* 24 */   public static final CustomException NoValidCOP = ExceptionImpl.getInstance("sps.exception.no-valid-cop");
/* 25 */   public static final CustomException NoPinLinkInformation = ExceptionImpl.getInstance("sps.exception.no-pin-link-information");
/* 26 */   public static final CustomException NoValidEndItemPart = ExceptionImpl.getInstance("sps.exception.no-valid-eil");
/* 27 */   public static final CustomException NoCalibrationUpdate = ExceptionImpl.getInstance("sps.exception.no-calibration-update");
/* 28 */   public static final CustomException ConfirmSameCalibration = ExceptionImpl.getInstance("sps.exception.confirm-same-calibration");
/* 29 */   public static final CustomException InvalidCurrentCalibration = ExceptionImpl.getInstance("sps.exception.invalid-current-calibration");
/* 30 */   public static final CustomException MissingCalibrationFile = ExceptionImpl.getInstance("sps.exception.missing-calibration-file");
/* 31 */   public static final CustomException BulletinNotAvailable = ExceptionImpl.getInstance("sps.exception.bulletin-not-available");
/* 32 */   public static final CustomException UnknownVIN = ExceptionImpl.getInstance("sps.exception.unknown-vin");
/* 33 */   public static final CustomException UnknownSoftware = ExceptionImpl.getInstance("sps.exception.unknown-ecu-software");
/* 34 */   public static final CustomException UnknownHardware = ExceptionImpl.getInstance("sps.exception.unknown-ecu-hardware");
/* 35 */   public static final CustomException InvalidHardware = ExceptionImpl.getInstance("sps.exception.invalid-ecu-hardware");
/* 36 */   public static final CustomException SoftwareNotAvailable = ExceptionImpl.getInstance("sps.exception.software-not-available");
/* 37 */   public static final CustomException DatabaseSecurityViolated = ExceptionImpl.getInstance("sps.exception.database-security-violated");
/* 38 */   public static final CustomException NoAuthorization = ExceptionImpl.getInstance("sps.exception.no-authorization");
/* 39 */   public static final CustomException SessionExpired = ExceptionImpl.getInstance("sps.exception.session-expired");
/* 40 */   public static final CustomException DataReset = ExceptionImpl.getInstance("sps.exception.data-reset");
/* 41 */   public static final CustomException CommunicationException = ExceptionImpl.getInstance("sps.exception.communication");
/* 42 */   public static final CustomException NoSecurityClearance = ExceptionImpl.getInstance("sps.exception.security-enforced");
/* 43 */   public static final CustomException VCI1001NotEnabled = ExceptionImpl.getInstance("sps.exception.vci1001-disabled");
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\common\gtwo\implementation\CommonException.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */