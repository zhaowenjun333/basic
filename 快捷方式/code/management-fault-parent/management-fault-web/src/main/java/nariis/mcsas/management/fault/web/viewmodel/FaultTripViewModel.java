package nariis.mcsas.management.fault.web.viewmodel;

import java.util.Date;

/**
 * <p>ClassName:FaultTripViewModel，</p>
 * <p>描述:TODO</p>
 * @author 4621170153
 * @date 2017年3月14日下午2:18:13
 * @version 
 */
public class FaultTripViewModel {
private String ids;

/**
 * <p>描述：TODO</p> 
 * @return   Object[] 
 * @author:4621170153
 * 2017年3月14日 下午8:18:04
 */
public String getIds() {
	return ids;
}
/**
 * <p>描述：TODO</p> 
 * @param ids 
 * @author:4621170153
 * 2017年3月14日 下午8:18:17
 */
public void setIds(String ids) {
	this.ids = ids;
}
 
 
private String 	id   ;            //    故障ID 
private String	faultType   ;          //     故障类型
private String	faultTypeId   ;          //     故障类型
private String	organizationId		;		//		所属单位ID
private String	organization		;		//		所属单位名称
private String 	    voltageLevel    ;        //        电压等级
private String 	    voltageLevelId    ;        //        电压等级
private String		type			;		//		专业
/**
 * <p>描述：TODO</p> 
 * @return String
 * @author:4621170153
 * 2017年3月20日 上午10:21:12
 */
public String getFaultTypeId() {
	return faultTypeId;
}
/**
 * <p>描述：TODO</p> 
 * @param faultTypeId 
 * @author:4621170153
 * 2017年3月20日 上午10:21:17
 */
public void setFaultTypeId(String faultTypeId) {
	this.faultTypeId = faultTypeId;
}
/**
 * <p>描述：TODO</p> 
 * @return String
 * @author:4621170153
 * 2017年3月20日 上午10:21:21
 */
public String getVoltageLevelId() {
	return voltageLevelId;
}
/**
 * <p>描述：TODO</p> 
 * @param voltageLevelId 
 * @author:4621170153
 * 2017年3月20日 上午10:21:24
 */
public void setVoltageLevelId(String voltageLevelId) {
	this.voltageLevelId = voltageLevelId;
}
/**
 * <p>描述：TODO</p> 
 * @return String
 * @author:4621170153
 * 2017年3月20日 上午10:21:28
 */
public String getTypeId() {
	return typeId;
}
/**
 * <p>描述：TODO</p> 
 * @param typeId 
 * @author:4621170153
 * 2017年3月20日 上午10:21:30
 */
public void setTypeId(String typeId) {
	this.typeId = typeId;
}


private String		typeId			;		//		专业
private String	stationOrLineId		;		//		线路/站ID
private String	stationOrLineName	;		//		线路/站名称
private String	deviceType			;		//		设备类型
private String	deviceTypeName		;		//		设备类型名称
private String	deviceId			;		//		设备ID	
private String	deviceName			;		//		设备名称
private String 	faultTime			;		//		故障时间	
private String	faultPhaseId		;		//		故障相别ID
private String	faultPhase			;		//		故障相别
private String  weatherId			;		//			现场天气ID	
private String	weather				;		//		现场天气
private String	reasonType			;		//		原因分类ID
private String	typeName			;		//		原因分类
private String	faultTitle			;		//		故障简题
private String	faultDetail			;		//		故障详情
private String	protection			;		//		保护动作情况
private String	stationFaultOrNot	;		//		是否站内故障
private String  faultLocator ;		//		故障测距	
private String	repairId			;		//		抢修ID	
private String	repairOrNot			;		//		是否抢修
private String	repairEndOrNot		;		//		抢修是否结束
private String  		recoveryDate	;		//		恢复时间
private String	managementDetail    ;       //         处理详情
private String	attachment			;		//		相关附件
private String	reclosingAction		;		//		重合情况
private String faultReason			;		//		故障原因	
private String faultValveGroupOrNumber;	//		故障阀组/极号
private String threeSpanOrNot;	//		故障阀组/极号
private String powerOutReason;	//		故障阀组/极号
private String sendSuccessOrNot;	//		故障阀组/极号

/**
 * <p>描述：TODO</p> 
 * @return String
 * @author:4621170153
 * 2017年3月20日 上午10:52:08
 */
public String getSendSuccessOrNot() {
	return sendSuccessOrNot;
}
/**
 * <p>描述：TODO</p> 
 * @param sendSuccessOrNot 
 * @author:4621170153
 * 2017年3月20日 上午10:52:39
 */
public void setSendSuccessOrNot(String sendSuccessOrNot) {
	this.sendSuccessOrNot = sendSuccessOrNot;
}
/**
 * <p>描述：TODO</p> 
 * @return String
 * @author:4621170153
 * 2017年3月20日 上午10:48:55
 */
public String getPowerOutReason() {
	return powerOutReason;
}
/**
 * <p>描述：TODO</p> 
 * @param powerOutReason 
 * @author:4621170153
 * 2017年3月20日 上午10:48:59
 */
public void setPowerOutReason(String powerOutReason) {
	this.powerOutReason = powerOutReason;
}
/**
 * <p>描述：TODO</p> 
 * @return String
 * @author:4621170153
 * 2017年3月20日 上午10:49:04
 */
public String getThreeSpanOrNot() {
	return threeSpanOrNot;
}
/**
 * <p>描述：TODO</p> 
 * @param threeSpanOrNot 
 * @author:4621170153
 * 2017年3月20日 上午10:49:08
 */
public void setThreeSpanOrNot(String threeSpanOrNot) {
	this.threeSpanOrNot = threeSpanOrNot;
}
/**
 * @return the id
 */
public String getId() {
	return id;
}
/**
 * @return the faultType
 */
public String getFaultType() {
	return faultType;
}
/**
 * @return the organizationId
 */
public String getOrganizationId() {
	return organizationId;
}
/**
 * @return the organization
 */
public String getOrganization() {
	return organization;
}
/**
 * @return the voltageLevel
 */
public String getVoltageLevel() {
	return voltageLevel;
}
/**
 * @return the type
 */
public String getType() {
	return type;
}
/**
 * @return the stationOrLineId
 */
public String getStationOrLineId() {
	return stationOrLineId;
}
/**
 * @return the stationOrLineName
 */
public String getStationOrLineName() {
	return stationOrLineName;
}
/**
 * @return the deviceType
 */
public String getDeviceType() {
	return deviceType;
}
/**
 * @return the deviceTypeName
 */
public String getDeviceTypeName() {
	return deviceTypeName;
}
/**
 * @return the deviceId
 */
public String getDeviceId() {
	return deviceId;
}
/**
 * @return the deviceName
 */
public String getDeviceName() {
	return deviceName;
}
/**
 * @return the faultTime
 */
public String getFaultTime() {
	return faultTime;
}
/**
 * @return the faultPhaseId
 */
public String getFaultPhaseId() {
	return faultPhaseId;
}
/**
 * @return the faultPhase
 */
public String getFaultPhase() {
	return faultPhase;
}
/**
 * @return the weatherId
 */
public String getWeatherId() {
	return weatherId;
}
/**
 * @return the weather
 */
public String getWeather() {
	return weather;
}
/**
 * @return the reasonType
 */
public String getReasonType() {
	return reasonType;
}
/**
 * @return the typeName
 */
public String getTypeName() {
	return typeName;
}
/**
 * @return the faultTitle
 */
public String getFaultTitle() {
	return faultTitle;
}
/**
 * @return the faultDetail
 */
public String getFaultDetail() {
	return faultDetail;
}
/**
 * @return the protection
 */
public String getProtection() {
	return protection;
}
/**
 * @return the stationFaultOrNot
 */
public String getStationFaultOrNot() {
	return stationFaultOrNot;
}
/**
 * @return the faultLocator
 */
public String getFaultLocator() {
	return faultLocator;
}
/**
 * @return the repairId
 */
public String getRepairId() {
	return repairId;
}
/**
 * @return the repairOrNot
 */
public String getRepairOrNot() {
	return repairOrNot;
}
/**
 * @return the repairEndOrNot
 */
public String getRepairEndOrNot() {
	return repairEndOrNot;
}
/**
 * @return the recoveryDate
 */
public String getRecoveryDate() {
	return recoveryDate;
}
/**
 * @return the managementDetail
 */
public String getManagementDetail() {
	return managementDetail;
}
/**
 * @return the attachment
 */
public String getAttachment() {
	return attachment;
}
/**
 * @return the reclosingAction
 */
public String getReclosingAction() {
	return reclosingAction;
}
/**
 * @return the faultReason
 */
public String getFaultReason() {
	return faultReason;
}
/**
 * @return the faultValveGroupOrNumber
 */
public String getFaultValveGroupOrNumber() {
	return faultValveGroupOrNumber;
}
/**
 * @param id the id to set
 */
public void setId(String id) {
	this.id = id;
}
/**
 * @param faultType the faultType to set
 */
public void setFaultType(String faultType) {
	this.faultType = faultType;
}
/**
 * @param organizationId the organizationId to set
 */
public void setOrganizationId(String organizationId) {
	this.organizationId = organizationId;
}
/**
 * @param organization the organization to set
 */
public void setOrganization(String organization) {
	this.organization = organization;
}
/**
 * @param voltageLevel the voltageLevel to set
 */
public void setVoltageLevel(String voltageLevel) {
	this.voltageLevel = voltageLevel;
}
/**
 * @param type the type to set
 */
public void setType(String type) {
	this.type = type;
}
/**
 * @param stationOrLineId the stationOrLineId to set
 */
public void setStationOrLineId(String stationOrLineId) {
	this.stationOrLineId = stationOrLineId;
}
/**
 * @param stationOrLineName the stationOrLineName to set
 */
public void setStationOrLineName(String stationOrLineName) {
	this.stationOrLineName = stationOrLineName;
}
/**
 * @param deviceType the deviceType to set
 */
public void setDeviceType(String deviceType) {
	this.deviceType = deviceType;
}
/**
 * @param deviceTypeName the deviceTypeName to set
 */
public void setDeviceTypeName(String deviceTypeName) {
	this.deviceTypeName = deviceTypeName;
}
/**
 * @param deviceId the deviceId to set
 */
public void setDeviceId(String deviceId) {
	this.deviceId = deviceId;
}
/**
 * @param deviceName the deviceName to set
 */
public void setDeviceName(String deviceName) {
	this.deviceName = deviceName;
}
/**
 * @param faultTime the faultTime to set
 */
public void setFaultTime(String faultTime) {
	this.faultTime = faultTime;
}
/**
 * @param faultPhaseId the faultPhaseId to set
 */
public void setFaultPhaseId(String faultPhaseId) {
	this.faultPhaseId = faultPhaseId;
}
/**
 * @param faultPhase the faultPhase to set
 */
public void setFaultPhase(String faultPhase) {
	this.faultPhase = faultPhase;
}
/**
 * @param weatherId the weatherId to set
 */
public void setWeatherId(String weatherId) {
	this.weatherId = weatherId;
}
/**
 * @param weather the weather to set
 */
public void setWeather(String weather) {
	this.weather = weather;
}
/**
 * @param reasonType the reasonType to set
 */
public void setReasonType(String reasonType) {
	this.reasonType = reasonType;
}
/**
 * @param typeName the typeName to set
 */
public void setTypeName(String typeName) {
	this.typeName = typeName;
}
/**
 * @param faultTitle the faultTitle to set
 */
public void setFaultTitle(String faultTitle) {
	this.faultTitle = faultTitle;
}
/**
 * @param faultDetail the faultDetail to set
 */
public void setFaultDetail(String faultDetail) {
	this.faultDetail = faultDetail;
}
/**
 * @param protection the protection to set
 */
public void setProtection(String protection) {
	this.protection = protection;
}
/**
 * @param stationFaultOrNot the stationFaultOrNot to set
 */
public void setStationFaultOrNot(String stationFaultOrNot) {
	this.stationFaultOrNot = stationFaultOrNot;
}
/**
 * @param faultLocator the faultLocator to set
 */
public void setFaultLocator(String faultLocator) {
	this.faultLocator = faultLocator;
}
/**
 * @param repairId the repairId to set
 */
public void setRepairId(String repairId) {
	this.repairId = repairId;
}
/**
 * @param repairOrNot the repairOrNot to set
 */
public void setRepairOrNot(String repairOrNot) {
	this.repairOrNot = repairOrNot;
}
/**
 * @param repairEndOrNot the repairEndOrNot to set
 */
public void setRepairEndOrNot(String repairEndOrNot) {
	this.repairEndOrNot = repairEndOrNot;
}
/**
 * @param recoveryDate the recoveryDate to set
 */
public void setRecoveryDate(String recoveryDate) {
	this.recoveryDate = recoveryDate;
}
/**
 * @param managementDetail the managementDetail to set
 */
public void setManagementDetail(String managementDetail) {
	this.managementDetail = managementDetail;
}
/**
 * @param attachment the attachment to set
 */
public void setAttachment(String attachment) {
	this.attachment = attachment;
}
/**
 * @param reclosingAction the reclosingAction to set
 */
public void setReclosingAction(String reclosingAction) {
	this.reclosingAction = reclosingAction;
}
/**
 * @param faultReason the faultReason to set
 */
public void setFaultReason(String faultReason) {
	this.faultReason = faultReason;
}
/**
 * @param faultValveGroupOrNumber the faultValveGroupOrNumber to set
 */
public void setFaultValveGroupOrNumber(String faultValveGroupOrNumber) {
	this.faultValveGroupOrNumber = faultValveGroupOrNumber;
}
 	
	
	
	
	
	
}
