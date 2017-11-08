package nariis.mcsas.management.fault.business.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import org.springframework.util.StringUtils;
//import nariis.mcsas.transform.common.constant.CommonConstant;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.Map.Entry;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import cst.constants.common.VoltageLevel;
import cst.constants.management.ConfirmStatus;
import cst.constants.management.FaultCategory;
import cst.constants.management.FaultPhase;
import cst.constants.common.DeviceType;
import cst.constants.common.FaultType;
import cst.constants.management.ReclosingAction;
import cst.constants.management.YesOrNot;
import nariis.falcon.oaf.OafCommands;
import nariis.falcon.oaf.OafException;
import nariis.falcon.oaf.OafQuery;
import nariis.falcon.oaf.models.constant.Constant;
import nariis.falcon.oaf.models.constant.ConstantItem;
import nariis.falcon.oaf.models.constant.ConstantRegistry;
import nariis.mcsas.management.common.util.CommonQueryUtils;
import nariis.mcsas.management.common.util.CommonWorkLogService;
import sgcim30.assets.accounts.AstLineAssetInfoS;
import sgcim30.assets.accounts.AstPolesite;
import sgcim30.assets.analysis.transmission.TmBigCross;
import sgcim30.assets.analysis.transmission.TmOtherCross;
import sgcim30.assets.analysis.transmission.TmThreeCrossPoint;
import sgcim30.assets.common.Outline;
import sgcim30.assets.management.EmergencyRepair;
import sgcim30.assets.management.FaultTrip;
import sgcim30.assets.management.WarnTrackStatus;
import sgcim30.assets.management.WorkLog;
//import cst.constants.management.Type;
/**
 * <p>
 * ClassName:FaultTripService，
 * </p>
 * <p>
 * 描述:故障异常service业务层类TODO
 * </p>
 * 
 * @author 4621170153
 * @date 2017年3月14日下午2:16:00
 * @version
 */
@RestController
@RequestMapping("/faultTrip")
@SpringBootApplication
public class FaultTripService {

	@Autowired
	OafCommands oafCommands;

	@Autowired
	OafQuery oafQuery;

	@Autowired
	CommonWorkLogService commonWorkLogService;
	private static final Logger logger = LogManager.getLogger(FaultTripService.class);
	private static final SimpleDateFormat SDF = new SimpleDateFormat("yyyy-MM-dd HH:mm");

	/**
	 * <p>
	 * 描述：故障异常删除TODO
	 * </p>
	 * 
	 * @param temmap
	 * @author:4621170153 2017年3月14日 下午6:52:06
	 * @return String
	 */
	@RequestMapping("/deleteFaultTrip")
	public String deleteFaultTrip(@RequestParam HashMap<String, Object> temmap) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put(nariis.falcon.oaf.Constant.CLASS_KEY, FaultTrip.class.getName());
		map.put("id", temmap.get("id"));
		String s = null;
		
		try {
			s = querycheckDataResult(temmap);
			if ("true".equals(s)) {
				oafCommands.delete(map);
				// 日志
				WorkLog workLog = (WorkLog) commonWorkLogService.getBeanByID(temmap.get("id").toString());
				commonWorkLogService.deleteWorkLog(workLog);
			} else {
				return "no";
			}
		} catch (ParseException e1) {
			logger.error("故障异常删除异常", e1);
			return "no";
		}
		return "删除成功";
	};

	/**
	 * <p>
	 * 描述：故障异常更新状态confirmstatus
	 * </p>
	 * 
	 * @param gridMap
	 * @author:4621170153 2017年3月21日 上午9:50:11
	 */
	@RequestMapping("/updatestatusByid")
	public void updatestatusByid(@RequestParam HashMap<String, Object> gridMap) {
		String confirmStatus = (String) gridMap.get("confirmStatus");
		switch (confirmStatus) {
		case "2":
			gridMap.put("trackDate", new Date());
			break;
		case "3":
			gridMap.put("closedLoopDate", new Date());
			break;
		case "4":
			gridMap.put("filingDate", new Date());
			break;
		}
		gridMap.put("updateDateTime", new Date());
		gridMap.put(nariis.falcon.oaf.Constant.CLASS_KEY, FaultTrip.class.getName());
		oafCommands.update(gridMap);
	}

	/**
	 * <p>
	 * 描述：故障异常 查询
	 * </p>
	 * 
	 * @param map
	 * @return List<Map<String, Object>>
	 * @author:4621170153 2017年3月14日 下午2:16:13
	 */
	@RequestMapping("/getFaultTripResults")
	public List<Map<String, Object>> getFaultTripResults(@RequestParam HashMap<String, Object> map) {
		Map<String, Object> paramnormal =new LinkedHashMap<String, Object>();;
		String fqlcount = "";
		String fql = "";
		int pageSize =0 ;
		int pageIndex=0;
		int skipCount=0;
		String recordId = null ;
		String faultTime1 = "".equals((String) map.get("gzsj1")) ? "null" : (String) map.get("gzsj1"); // 设置为null字符串下面判断就是依据这个
		String faultTime2 = "".equals((String) map.get("gzsj2")) ? "null" : (String) map.get("gzsj2");
		String repairOrNot = (String) map.get("sfqx");
		String stationOrLineId = (String) map.get("stationOrLineId");
		Map<String, Object> paramexport = null;
		StringBuilder fromFqlexport = new StringBuilder()  ;
		if( map.get("pageSize")!= null){//正常查询非导出查询
			pageSize = Integer.parseInt((String) map.get("pageSize"));// 框架自带pageSize
			pageIndex = Integer.parseInt((String) map.get("pageIndex"));
			recordId = (String) map.get("recordId") ;
			skipCount = pageIndex * pageSize;
			String confirmStatus = (String) map.get("status");
			String voltageLevel = (String) map.get("dydj");
			String organization = (String) map.get("ssds");
			String deviceName = (String) map.get("sbmc");
			String deviceType = (String) map.get("deviceType");
			String stationOrLineName = (String) map.get("zxl");
			String faultCategory = (String) map.get("gzlb");
			String faulttype = (String) map.get("faulttype");
			String mh = (String) map.get("mh"); // 模糊
			
			String selectFql0 = " from t1 in  ( "  ;
			
			
			StringBuilder fromFql = new StringBuilder(" from t in sgcim30.assets.management.FaultTrip ");
			StringBuilder whereFql = new StringBuilder("");
			if (confirmStatus != null && !"null".equals(confirmStatus) && !"".equals(confirmStatus)) {
				fromFql.append(" where t.confirmStatus ==:confirmStatus ");
				paramnormal.put("confirmStatus", confirmStatus);
			}
			if ((String) map.get("dydj") != null && !"null".equals((String) map.get("dydj")) && !"".equals((String) map.get("dydj"))) {
				String[] voltageLevelarr = ((String) map.get("dydj") ).split(",");
				if(voltageLevelarr.length ==1){
					fromFql.append( " && t.voltageLevel ==:voltageLevel ");
					paramnormal.put("voltageLevel",  (String) map.get("dydj")  );
				}else{
					fromFql.append( " && (  ");int i = 0 ;
					for (String string : voltageLevelarr) {
						i++;
						if(i ==1){
							fromFql.append( " t.voltageLevel ==:voltageLevel" +i);
							paramnormal.put("voltageLevel"+i,  string);
						}else{
							fromFql.append( " ||  t.voltageLevel ==:voltageLevel"+i);
							paramnormal.put("voltageLevel"+i, string);
						}
					}
					fromFql.append( " )  ");
				}
			}
			/*if (voltageLevel != null && !"null".equals(voltageLevel) && !"".equals(voltageLevel)) {
				fromFql.append("&& t.voltageLevel ==:voltageLevel ");
				paramnormal.put("voltageLevel", voltageLevel);
			}*/
			if (faultCategory != null && !"null".equals(faultCategory) && !"".equals(faultCategory)) {
				fromFql.append("&& t.faultCategory ==:faultCategory ");
				paramnormal.put("faultCategory", faultCategory);
			}
			if (faulttype != null && !"null".equals(faulttype) && !"".equals(faulttype)) {
				fromFql.append("&& t.faultType  ==:faulttype ");
				paramnormal.put("faulttype", faulttype);
			}
			if (repairOrNot != null && !"null".equals(repairOrNot) && !"".equals(repairOrNot)) {
				fromFql.append("&& t.repairOrNot ==:repairOrNot ");
				paramnormal.put("repairOrNot", repairOrNot);
			}
			if(!StringUtils.isEmpty( organization ) && !"null".equals(organization) && !"".equals(organization)){
				String[] organizationarr = organization.split(",");
				if(organizationarr.length ==1){
					fromFql.append( " && t.organization ==:organization ");
					paramnormal.put("organization",  organization );
				}else{
					fromFql.append( " && (  ");int i = 0 ;
					for (String string : organizationarr) {
						i++;
						if(i ==1){
							fromFql.append( " t.organization ==:organization" +i);
							paramnormal.put("organization"+i,  string);
						}else{
							fromFql.append( " ||  t.organization ==:organization"+i);
							paramnormal.put("organization"+i, string);
						}
					}
					fromFql.append( " )  ");
				}
			}
			if(!StringUtils.isEmpty( deviceName ) && !"null".equals(deviceName) && !"".equals(deviceName)){
				String[] deviceNamearr = deviceName.split(",");
				if(deviceNamearr.length ==1){
					fromFql.append( " && t.deviceName ==:deviceName ");
					paramnormal.put("deviceName",  deviceName );
				}else{
					fromFql.append( " && (  ");int i = 0 ;
					for (String string : deviceNamearr) {
						i++;
						if(i ==1){
							fromFql.append( " t.deviceName ==:deviceName" +i);
							paramnormal.put("deviceName"+i,  string);
						}else{
							fromFql.append( " ||  t.deviceName ==:deviceName"+i);
							paramnormal.put("deviceName"+i, string);
						}
					}
					fromFql.append( " )  ");
				}
			}
			if(!StringUtils.isEmpty( deviceType ) && !"null".equals(deviceType) && !"".equals(deviceType)){
				String[] deviceTypearr = deviceType.split(",");
				if(deviceTypearr.length ==1){
					fromFql.append( " && t.deviceType ==:deviceType ");
					paramnormal.put("deviceType",  deviceType );
				}else{
					fromFql.append( " && (  ");int i = 0 ;
					for (String string : deviceTypearr) {
						i++;
						if(i ==1){
							fromFql.append( " t.deviceType ==:deviceType" +i);
							paramnormal.put("deviceType"+i,  string);
						}else{
							fromFql.append( " ||  t.deviceType ==:deviceType"+i);
							paramnormal.put("deviceType"+i, string);
						}
					}
					fromFql.append( " )  ");
				}
			}
			if(!StringUtils.isEmpty( stationOrLineName ) && !"null".equals(stationOrLineName) && !"".equals(stationOrLineName)){
				String[] stationOrLineNamearr = stationOrLineName.split(",");
				if(stationOrLineNamearr.length ==1){
					fromFql.append( " && t.stationOrLineName ==:stationOrLineName ");
					paramnormal.put("stationOrLineName",  stationOrLineName );
				}else{
					fromFql.append( " && (  ");int i = 0 ;
					for (String string : stationOrLineNamearr) {
						i++;
						if(i ==1){
							fromFql.append( " t.stationOrLineName ==:stationOrLineName" +i);
							paramnormal.put("stationOrLineName"+i,  string);
						}else{
							fromFql.append( " ||  t.stationOrLineName ==:stationOrLineName"+i);
							paramnormal.put("stationOrLineName"+i, string);
						}
					}
					fromFql.append( " )  ");
				}
			}
			if (stationOrLineId != null && !"null".equals(stationOrLineId) && !"".equals(stationOrLineId)) {
				fromFql.append("&& t.stationOrLineId ==:stationOrLineId ");
				paramnormal.put("stationOrLineId", stationOrLineId);
			}
			if (!"null".equals(faultTime1) && !"null".equals(faultTime2) && faultTime1 != null && faultTime2 != null) {
				fromFql.append(" &&  t.faultTime >=:faultTime1 &&  t.faultTime <=:faultTime2 ");
				Date time1 = null;
				Date time2 = null;
				try {
					time1 = SDF.parse(faultTime1);
					time2 = SDF.parse(faultTime2);
				} catch (ParseException e) {
					logger.error(e);
				}
				paramnormal.put("faultTime1", time1);
				paramnormal.put("faultTime2", time2);
			} else if (!"null".equals(faultTime1) && faultTime1 != null) {
				fromFql.append(" && t.faultTime>=:faultTime1");
				Date time1 = null;
				try {
					time1 = SDF.parse(faultTime1);
				} catch (ParseException e) {
					logger.error(e);
				}
				paramnormal.put("faultTime1", time1);
			} else if (!"null".equals(faultTime2) && faultTime2 != null) {
				fromFql.append(" && t.faultTime <=:faultTime2");
				Date time2 = null;
				try {
					time2 = SDF.parse(faultTime2);
				} catch (ParseException e) {
					logger.error(e);
				}
				paramnormal.put("faultTime2", time2);
			}
			if (!"".equals(mh) && !"null".equals(mh) && mh != null) {
				String faultTypestr =null;
				if(mh.contains("设备跳闸")){
					faultTypestr ="01";
				}else if(mh.contains("临时拉停")){
					faultTypestr ="02";
				}else if(mh.contains("直流闭锁")){
					faultTypestr ="03";
				}else if(mh.contains("直流线路再启动")){
					faultTypestr ="04";
				}else if(mh.contains("换相失败")){
					faultTypestr ="05";
				}
				if(faultTypestr==null ){
					faultTypestr=mh;
				}
				fromFql.append(" && (t.faultType.contains('" + faultTypestr + "') || t.voltageLevel.contains('" + mh + "') || "
						+ "t.deviceName.contains('" + mh + "') || t.stationOrLineName.contains('" + mh + "') || "
						+ "t.organization.contains('" + mh + "')||t.weather.contains('" + mh + "'))");
			}
			String selectFql = " orderby t.faultTime descending select t   ";
			 selectFql += " select t1 then skip :skipCount then take :pageSize ";
			String sqlCount = "  select sqlCount = t.count() ";
			fql =selectFql0 + fromFql.toString() + whereFql.toString() /* + orderbyFql */ + selectFql;
			fqlcount = fromFql.toString() + whereFql.toString() + sqlCount;
		}else{//导出的查询方法，分为选中导出和不选中导出，不选中导出所有当前状态的数据，只需要传confirmstatus导出所以当前状态的数据  ,选中的查询需要传ids ,不需要传confirmstatus
				String confirmStatus = null;
				if(  map.get("status") != null){
					  confirmStatus = (String) map.get("status");
				}	
				paramexport = new LinkedHashMap<String, Object>();
				fromFqlexport = new StringBuilder(" from t in sgcim30.assets.management.FaultTrip ");
				if (confirmStatus != null && !"null".equals(confirmStatus) && !"".equals(confirmStatus)) {
					fromFqlexport.append(" where t.confirmStatus ==:confirmStatus ");
					paramexport.put("confirmStatus", confirmStatus);
					//加过滤条件，查询出的数据
					if ((String) map.get("gzlb") != null && !"null".equals((String) map.get("gzlb")) && !"".equals((String) map.get("gzlb"))) {
						fromFqlexport.append("&& t.faultCategory ==:faultCategory ");
						paramexport.put("faultCategory", (String) map.get("gzlb"));
					}
					if (repairOrNot != null && !"null".equals(repairOrNot) && !"".equals(repairOrNot)) {
						fromFqlexport.append("&& t.repairOrNot ==:repairOrNot ");
						paramexport.put("repairOrNot", repairOrNot);
					}
					//故障时间
					if (!"null".equals(faultTime1) && !"null".equals(faultTime2) && faultTime1 != null && faultTime2 != null) {
						fromFqlexport.append(" &&  t.faultTime >=:faultTime1 &&  t.faultTime <=:faultTime2 ");
						Date time1 = null;
						Date time2 = null;
						try {
							time1 = SDF.parse(faultTime1);
							time2 = SDF.parse(faultTime2);
						} catch (ParseException e) {
							logger.error(e);
						}
						paramexport.put("faultTime1", time1);
						paramexport.put("faultTime2", time2);
					} else if (!"null".equals(faultTime1) && faultTime1 != null) {
						fromFqlexport.append(" && t.faultTime>=:faultTime1");
						Date time1 = null;
						try {
							time1 = SDF.parse(faultTime1);
						} catch (ParseException e) {
							logger.error(e);
						}
						paramexport.put("faultTime1", time1);
					} else if (!"null".equals(faultTime2) && faultTime2 != null) {
						fromFqlexport.append(" && t.faultTime <=:faultTime2");
						Date time2 = null;
						try {
							time2 = SDF.parse(faultTime2);
						} catch (ParseException e) {
							logger.error(e);
						}
						paramexport.put("faultTime2", time2);
					}
					//电压等级过滤
					if ((String) map.get("dydj") != null && !"null".equals((String) map.get("dydj")) && !"".equals((String) map.get("dydj"))) {
						String[] voltageLevelarr = ((String) map.get("dydj") ).split(",");
						if(voltageLevelarr.length ==1){
							fromFqlexport.append( " && t.voltageLevel ==:voltageLevel ");
							paramexport.put("voltageLevel",  (String) map.get("dydj")  );
						}else{
							fromFqlexport.append( " && (  ");int i = 0 ;
							for (String string : voltageLevelarr) {
								i++;
								if(i ==1){
									fromFqlexport.append( " t.voltageLevel ==:voltageLevel" +i);
									paramexport.put("voltageLevel"+i,  string);
								}else{
									fromFqlexport.append( " ||  t.voltageLevel ==:voltageLevel"+i);
									paramexport.put("voltageLevel"+i, string);
								}
							}
							fromFqlexport.append( " )  ");
						}
					}
					if(!StringUtils.isEmpty(  (String) map.get("ssds") ) && !"null".equals((String) map.get("ssds") ) && !"".equals((String) map.get("ssds") )){
						String[] organizationarr = ((String) map.get("ssds") ).split(",");
						if(organizationarr.length ==1){
							fromFqlexport.append( " && t.organization ==:organization ");
							paramexport.put("organization",  (String) map.get("ssds")  );
						}else{
							fromFqlexport.append( " && (  ");int i = 0 ;
							for (String string : organizationarr) {
								i++;
								if(i ==1){
									fromFqlexport.append( " t.organization ==:organization" +i);
									paramexport.put("organization"+i,  string);
								}else{
									fromFqlexport.append( " ||  t.organization ==:organization"+i);
									paramexport.put("organization"+i, string);
								}
							}
							fromFqlexport.append( " )  ");
						}
					}
					if(!StringUtils.isEmpty(  (String) map.get("sbmc")  ) && !"null".equals((String) map.get("sbmc") ) && !"".equals((String) map.get("sbmc") )){
						String[] deviceNamearr = ((String) map.get("sbmc") ).split(",");
						if(deviceNamearr.length ==1){
							fromFqlexport.append( " && t.deviceName ==:deviceName ");
							paramexport.put("deviceName",  (String) map.get("sbmc")  );
						}else{
							fromFqlexport.append( " && (  ");int i = 0 ;
							for (String string : deviceNamearr) {
								i++;
								if(i ==1){
									fromFqlexport.append( " t.deviceName ==:deviceName" +i);
									paramexport.put("deviceName"+i,  string);
								}else{
									fromFqlexport.append( " ||  t.deviceName ==:deviceName"+i);
									paramexport.put("deviceName"+i, string);
								}
							}
							fromFqlexport.append( " )  ");
						}
					}
					if(!StringUtils.isEmpty( (String) map.get("deviceType") ) && !"null".equals((String) map.get("deviceType")) && !"".equals((String) map.get("deviceType"))){
						String[] deviceTypearr = ((String) map.get("deviceType")).split(",");
						if(deviceTypearr.length ==1){
							fromFqlexport.append( " && t.deviceType ==:deviceType ");
							paramexport.put("deviceType",  (String) map.get("deviceType") );
						}else{
							fromFqlexport.append( " && (  ");int i = 0 ;
							for (String string : deviceTypearr) {
								i++;
								if(i ==1){
									fromFqlexport.append( " t.deviceType ==:deviceType" +i);
									paramexport.put("deviceType"+i,  string);
								}else{
									fromFqlexport.append( " ||  t.deviceType ==:deviceType"+i);
									paramexport.put("deviceType"+i, string);
								}
							}
							fromFqlexport.append( " )  ");
						}
					}
					
					if (stationOrLineId != null && !"null".equals(stationOrLineId) && !"".equals(stationOrLineId)) {
						fromFqlexport.append("&& t.stationOrLineId ==:stationOrLineId ");
						paramexport.put("stationOrLineId", stationOrLineId);
					}
					
					if(!StringUtils.isEmpty( (String) map.get("zxl") ) && !"null".equals((String) map.get("zxl") ) && !"".equals((String) map.get("zxl") )){
						String[] stationOrLineNamearr = ((String) map.get("zxl")).split(",");
						if(stationOrLineNamearr.length ==1){
							fromFqlexport.append( " && t.stationOrLineName ==:stationOrLineName ");
							paramexport.put("stationOrLineName",  (String) map.get("zxl") );
						}else{
							fromFqlexport.append( " && (  ");int i = 0 ;
							for (String string : stationOrLineNamearr) {
								i++;
								if(i ==1){
									fromFqlexport.append( " t.stationOrLineName ==:stationOrLineName" +i);
									paramexport.put("stationOrLineName"+i,  string);
								}else{
									fromFqlexport.append( " ||  t.stationOrLineName ==:stationOrLineName"+i);
									paramexport.put("stationOrLineName"+i, string);
								}
							}
							fromFqlexport.append( " )  ");
						}
					}
					
					
				}
				if(map.get("ids")!=null){
					fromFqlexport.append(" where   ");//涉及到很恶心的一个问题，json传到后台，会加上中括号，所以要截取
					  String  sids =  ( (String) map.get("ids")).substring(1, ((String) map.get("ids")).length()-1) ;  
					  String  sid[]=sids.split(",");
					for(int i = 0 ;i< sid.length;i++){
						if(i==0){
							fromFqlexport.append("   t.id ==: id"+i);
							paramexport.put("id"+i,sid[i].substring(1, sid[i].length()-1));
						}else{
							fromFqlexport.append("  ||t.id ==:id"+i);
							paramexport.put("id"+i,sid[i].substring(1, sid[i].length()-1));
						}
					}
				}
				fromFqlexport.append(" select t ");
			 
//			return null;
		}
		Integer rsltCount = 0;
		List<Map<String, Object>> rslt = null;
		try {
			if(map.get("pageSize")!= null){
				
				rsltCount = (Integer) oafQuery.executeScalar(fqlcount, paramnormal);
				paramnormal.put("skipCount", skipCount);
				paramnormal.put("pageSize", pageSize);
				paramnormal.put("pageIndex", pageIndex);
				if(!StringUtils.isEmpty(recordId)){
					paramnormal.put("recordId", recordId);
				}
				paramnormal.put("total", rsltCount);
				rslt =executeQueryForAppointed(fql, paramnormal);
//			rslt = oafQuery.executeForList(fql, param);
			}else{
				rslt = oafQuery.executeForList(fromFqlexport.toString(), paramexport);
			}
		} catch (OafException e) {
			logger.error(e);
		}
		
		// 故障相别/极号 faultPhaseId 数据和yuml文件不匹配但不影响使用
		Constant faultPhaseIdCst = ConstantRegistry.findConstant(FaultPhase.TYPE);
		// 故障类别
		Constant FaultCategoryCst = ConstantRegistry.findConstant(FaultCategory.TYPE);
		// 故障类型
		Constant faultTypeCst = ConstantRegistry.findConstant(FaultType.TYPE);
		Constant voltageLevelCst = ConstantRegistry.findConstant(VoltageLevel.TYPE);
		Constant yesOrNotCst = ConstantRegistry.findConstant(YesOrNot.TYPE);
		Constant reclosingActionCst = ConstantRegistry.findConstant(ReclosingAction.TYPE);
		for (Map<String, Object> maptemp : rslt) {
			int FaultCategoryValue = (int) maptemp.get("faultCategory");
			String FaultCategoryName = "";
			FaultCategoryName = FaultCategoryCst.getFieldValueByItemValue(FaultCategoryValue, FaultCategory.NAME);
			maptemp.put("faultCategory", FaultCategoryName);
			// 故障相别极号 数据是字符串
			int faultPhaseIdValue = (int) maptemp.get("faultPhaseId");
			String faultPhaseIdName = "";
			faultPhaseIdName = faultPhaseIdCst.getFieldValueByItemValue(faultPhaseIdValue, FaultPhase.NAME);
			maptemp.put("faultPhaseId", faultPhaseIdName);
			// 故障类型 FaultType
			if (maptemp.get("faultType") != null) {
				String faultTypeValue = (String) maptemp.get("faultType");
				String faultTypeName = "";
				faultTypeName = faultTypeCst.getFieldValueByItemValue(faultTypeValue, FaultType.NAME);
				maptemp.put("faultType", faultTypeName);
			}
			// 电压等级
			if (maptemp.get("voltageLevel") != null) {
				int voltageLevelValue = ((Integer) maptemp.get("voltageLevel")).intValue();
				String voltageLevelName = voltageLevelCst.getFieldValueByItemValue(voltageLevelValue,
						VoltageLevel.NAME);
				maptemp.put("voltageLevel", voltageLevelName);
			}

			// 重合闸动作情况
			if (maptemp.get("reclosingAction") != null) {
				int reclosingActionValue = ((Integer) maptemp.get("reclosingAction")).intValue();
				String reclosingActionName = reclosingActionCst.getFieldValueByItemValue(reclosingActionValue,
						ReclosingAction.NAME);
				maptemp.put("reclosingAction", reclosingActionName);
			}
			/* 故障时间转换 ,其实没有必要因为肯定是有的吗 */
			if (maptemp.get("faultTime") != null) {
				String faultTime = SDF.format(maptemp.get("faultTime"));
				maptemp.put("faultTime", faultTime);
			}
			// 恢复时间
			if (maptemp.get("recoveryDate") != null) {
				String recoveryDate = SDF.format(maptemp.get("recoveryDate"));
				maptemp.put("recoveryDate", recoveryDate);
			}
			if (maptemp.get("updateDateTime") != null) {
				String updateDateTime = SDF.format(maptemp.get("updateDateTime"));
				maptemp.put("updateDateTime", updateDateTime);
			}
			// 是否抢修
			String repairOrNotName = "";
			String repairOrNotValue = (String) maptemp.get("repairOrNot");
			if (repairOrNotValue != "" && repairOrNotValue != null) {
				repairOrNotName = yesOrNotCst.getFieldValueByItemValue(repairOrNotValue, "name");
				maptemp.put("repairOrNot", repairOrNotName);
			}
			// 是否闭锁
			String blockOrNotName = "";
			String blockOrNotValue = (String) maptemp.get("blockOrNot");
			if (blockOrNotValue != "" && blockOrNotValue != null) {
				blockOrNotName = yesOrNotCst.getFieldValueByItemValue(blockOrNotValue, "name");
				maptemp.put("blockOrNot", blockOrNotName);
			}
			// 是否 站内故障
			String stationFaultOrNot = "";
			String stationFaultOrNotValue = (String) maptemp.get("stationFaultOrNot");
			if (stationFaultOrNotValue != "" && stationFaultOrNotValue != null) {
				stationFaultOrNot = yesOrNotCst.getFieldValueByItemValue(stationFaultOrNotValue, "name");
				maptemp.put("stationFaultOrNot", stationFaultOrNot);
			}
			// 是否三跨区段
			String threeSpanOrNot = "";
			String threeSpanOrNotValue = (String) maptemp.get("threeSpanOrNot");
			if (threeSpanOrNotValue != "" && threeSpanOrNotValue != null) {
				threeSpanOrNot = yesOrNotCst.getFieldValueByItemValue(threeSpanOrNotValue, "name");
				maptemp.put("threeSpanOrNot", threeSpanOrNot);
			}
			// 强送是否成功
			String sendSuccessOrNot = "";
			String sendSuccessOrNotValue = (String) maptemp.get("sendSuccessOrNot");
			if (sendSuccessOrNotValue != "" && sendSuccessOrNotValue != null) {
				sendSuccessOrNot = yesOrNotCst.getFieldValueByItemValue(sendSuccessOrNotValue, "name");
				maptemp.put("sendSuccessOrNot", sendSuccessOrNot);
			}
			// 抢修是否结束
			String repairEndOrNot = "";
			String repairEndOrNotValue = (String) maptemp.get("repairEndOrNot");
			if (repairEndOrNotValue != "" && repairEndOrNotValue != null) {
				repairEndOrNot = yesOrNotCst.getFieldValueByItemValue(repairEndOrNotValue, "name");
				maptemp.put("repairEndOrNot", repairEndOrNot);
			}
		}
		List<Map<String, Object>> list = new ArrayList<>();
		Map<String, Object> mapList = new HashMap<>();
		mapList.put("data", rslt);
		if(map.get("pageSize")!= null){//如果是非导出查询就加入总数
			mapList.put("total", rsltCount);
		}
		list.add(mapList);
		return list;
		
	}

	public List<Map<String,Object>> executeQueryForAppointed(String fql,Map<String,Object> param){
		List<Map<String, Object>> rsl = null;
		try {
			rsl = oafQuery.executeForList(fql, param);
		} catch (OafException e) {
			e.printStackTrace();
		}
		String recordId = (String)param.get("recordId");
		if(!StringUtils.isEmpty(recordId)){
			boolean flag = false;
			for(Map<String,Object> map : rsl){
				String id = (String)map.get("id");
				flag = recordId.equalsIgnoreCase(id);
				if(flag){
					map.put("isSelected", true);
					break;
				}				
			}
			int pageIndex = Integer.parseInt(String.valueOf(param.get("pageIndex")));
			int pageSize = Integer.parseInt(String.valueOf(param.get("pageSize")));
			int pages = 0;
			if(param.get("pages") != null){
				pages = Integer.parseInt(String.valueOf(param.get("pages")));
			}else{
				pages = ((Integer)param.get("total")+pageSize-1)/pageSize;//计算总页数
			}
			if(!flag && ++pageIndex < pages){
				int skipCount = pageIndex*pageSize;
				param.put("pageIndex", pageIndex);
				param.put("skipCount", skipCount);	
				return executeQueryForAppointed(fql,param);
			}
		}
		return rsl;
	}
	/**
	 * <p>
	 * 描述：新增插入故障异常
	 * </p>
	 * 
	 * @param param
	 * @return String
	 * @author:4621170153 2017年3月14日 下午2:16:18
	 */
	@RequestMapping("/saveFaultTrip")
	public String insertFaultTrip(@RequestParam HashMap<String, Object> param) {
		Constant DeviceTypeCst = ConstantRegistry.findConstant(DeviceType.TYPE);
		FaultTrip faultTrip = new FaultTrip(); 
		int faultPhaseId = Integer.parseInt((String) param.get("faultPhaseId"));
		faultTrip.setFaultPhaseId(faultPhaseId);
		int faultCategory = Integer.parseInt((String) param.get("faultCategory"));
		faultTrip.setFaultCategory(faultCategory);
		Date faultTime = null;
		try {
			faultTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse((String) param.get("faultTime"));
			faultTrip.setFaultTime(faultTime);
		} catch (ParseException e1) {
			logger.error(e1);
		}
		String faultType = null;
		if (param.get("faultType") != null && !"".equals(param.get("faultType"))) {
			faultType = (String) param.get("faultType");
			faultTrip.setFaultType(faultType);
		}
		int voltageLevel = 0;
		if (param.get("voltageLevel") != null && !"".equals(param.get("voltageLevel"))) {
			voltageLevel = Integer.parseInt((String) param.get("voltageLevel"));
			faultTrip.setVoltageLevel(voltageLevel);
		}
		String organization = "";
		if (param.get("organization") != null && !"".equals(param.get("organization"))) {
			organization = (String) param.get("organization");
			faultTrip.setOrganization(organization);
		}
		String organizationId = "";
		if (param.get("organizationId") != null && !"".equals(param.get("organizationId"))) {
			organizationId = (String) param.get("organizationId");
			faultTrip.setOrganizationId(organizationId); 
		}
		String stationOrLineName = "";
		if (param.get("stationOrLineName") != null && !"".equals(param.get("stationOrLineName"))) {
			stationOrLineName = (String) param.get("stationOrLineName");
			faultTrip.setStationOrLineName(stationOrLineName);
		}
		String stationOrLineId = "";
		if (param.get("stationOrLineId") != null && !"".equals(param.get("stationOrLineId"))) {
			stationOrLineId = (String) param.get("stationOrLineId");
			faultTrip.setStationOrLineId(stationOrLineId); 
		}
		String deviceName = "";
		if (param.get("deviceName") != null && !"".equals(param.get("deviceName"))) {
			deviceName = (String) param.get("deviceName");
			faultTrip.setDeviceName(deviceName);
		}
		String deviceTypeName = "";
		if (param.get("deviceType") != null && !"".equals(param.get("deviceType"))) {
			int  deviceTypeValue =  Integer.valueOf((String) param.get("deviceType") )  ;
			deviceTypeName = DeviceTypeCst.getFieldValueByItemValue(deviceTypeValue, "name");
			faultTrip.setDeviceTypeName(deviceTypeName);
			faultTrip.setDeviceType((String) param.get("deviceType"));
		}
		String deviceId = "";
		if (param.get("deviceId") != null && !"".equals(param.get("deviceId"))) {
			deviceId = (String) param.get("deviceId");
			faultTrip.setDeviceId(deviceId); 
		}
		String weather = null;
		if (param.get("weather") != null && !"".equals(param.get("weather"))) {
			weather = (String) param.get("weather");
			faultTrip.setWeather(weather);
		}
		faultTrip.setUpdateDateTime(new Date());
		faultTrip.setRecordDate(new Date());
		faultTrip.setConfirmStatus(1);
		faultTrip.setId(UUID.randomUUID().toString());
		// 日志
		WorkLog workLog = new WorkLog();
		workLog.setId(UUID.randomUUID().toString());
		workLog.setBusinessID(faultTrip.getId());
		workLog.setStartTime(new Date());
		workLog.setEndTime(new Date());
		workLog.setRecordDate(new Date());
		workLog.setUpdateDateTime(new Date());
		workLog.setStatus(1);
		if (("1").equals(param.get("faultCategory").toString())) {
			workLog.setRecordType("9");
		} else if (("2").equals(param.get("faultCategory").toString())) {
			workLog.setRecordType("10");
		} else if (("3").equals(param.get("faultCategory").toString())) {
			workLog.setRecordType("11");
		}
		StringBuilder sb = new StringBuilder();
//		故障类别+所属单位+电压等级+站/线+设备名称+故障相别+现场天气     要转化为汉字再存储到worklog的字段里面
		// 故障相别/极号 faultPhaseId 数据和yuml文件不匹配但不影响使用
		Constant faultPhaseIdCst = ConstantRegistry.findConstant(FaultPhase.TYPE);
		// 故障类别
		Constant FaultCategoryCst = ConstantRegistry.findConstant(FaultCategory.TYPE);
		// 故障类型
//		Constant faultTypeCst = ConstantRegistry.findConstant(FaultType.TYPE);
		Constant voltageLevelCst = ConstantRegistry.findConstant(VoltageLevel.TYPE);
		if (param.get("faultCategory") != null) {
			sb.append("故障类别:" + FaultCategoryCst.getFieldValueByItemValue(Integer.parseInt(param.get("faultCategory").toString()), FaultCategory.NAME));
		}
		if (param.get("organization") != null && !"".equals(param.get("organization"))) {
			sb.append(",所属单位:" + param.get("organization").toString());
		}
		if (param.get("voltageLevel") != null && !"".equals(param.get("voltageLevel"))) {
			sb.append(",电压等级:" +voltageLevelCst.getFieldValueByItemValue(Integer.parseInt( param.get("voltageLevel").toString()), VoltageLevel.NAME) );
		}
		if (param.get("stationOrLineName") != null) {
			sb.append(",线路/站:" + param.get("stationOrLineName").toString());
		}
		if (param.get("deviceName") != null && !"".equals(param.get("deviceName"))) {
			sb.append(",设备名称:" + param.get("deviceName").toString());
		}
		
		if (param.get("faultPhaseId") != null && !"".equals(param.get("faultPhaseId"))) {
			int faultPhaseIdlog = Integer.parseInt((String) param.get("faultPhaseId"));
			sb.append(",故障相别:" +  faultPhaseIdCst.getFieldValueByItemValue(faultPhaseIdlog, FaultPhase.NAME));
		}
		if (param.get("weather") != null && !"".equals(param.get("weather"))) {
			sb.append(",现场天气:" + param.get("weather").toString());
		}
		workLog.setRecordDetails(sb.toString());
		commonWorkLogService.saveWorkLog(workLog);
		try {
			oafCommands.insert(faultTrip);
		} catch (Exception e) {
			logger.error(e);
			return "no";
		}
		return "yes";
	}

	/**
	 * <p>
	 * 描述：故障异常当是抢修的时候插入到抢修表
	 * </p>
	 * 
	 * @param param
	 * @return String
	 * @author:4621170153 2017年5月12日 下午3:43:48
	 */
	@RequestMapping("/faultinsertemergencyrepair")
	public String faultinsertemergencyrepair(@RequestParam HashMap<String, Object> param) {
		EmergencyRepair mergencyRepair = new EmergencyRepair();
		// 故障id
		mergencyRepair.setId(UUID.randomUUID().toString());
		// 发起时间
		mergencyRepair.setBeginTime(new Date());
		// 记录时间
		mergencyRepair.setRecordDate(new Date());
		// 更新时间
		mergencyRepair.setUpdateDateTime(new Date());
		// 每条数据状态 confirmstatus
		mergencyRepair.setConfirmStatus(1);
		// 电压等级的名称和id（电压等级），
		int voltageLevel ;
		if (param.get("voltageLevel") != null && !"".equals(param.get("voltageLevel"))) {
			voltageLevel = Integer.parseInt((String) param.get("voltageLevel"));
			mergencyRepair.setVoltageLevel(voltageLevel);
		}
		// 所属单位的名称和id（运维单位），
		String organization = "";
		if (param.get("organization") != null && !"".equals(param.get("organization"))) {
			organization = (String) param.get("organization");
			mergencyRepair.setMaintenanceUnitName(organization); 
		}
		String organizationId = "";
		if (param.get("organizationId") != null && !"".equals(param.get("organizationId"))) {
			organizationId = (String) param.get("organizationId");
			mergencyRepair.setMaintenanceUnitId(organizationId);  //运维机构 
		}
		
		// 线路/站的名称和id（线路/站），
		String stationOrLineName = "";
		if (param.get("stationOrLineName") != null && !"".equals(param.get("stationOrLineName"))) {
			stationOrLineName = (String) param.get("stationOrLineName");
			mergencyRepair.setStationOrLineName(stationOrLineName);
		}
		String stationOrLineId = "";
		if (param.get("stationOrLineId") != null && !"".equals(param.get("stationOrLineId"))) {
			stationOrLineId = (String) param.get("stationOrLineId");
			mergencyRepair.setStationOrLineId(stationOrLineId);
		}
		// 设备（杆塔）名称和设备id（抢修设备），
		String deviceName = "";
		if (param.get("deviceName") != null && !"".equals(param.get("deviceName"))) {
			deviceName = (String) param.get("deviceName");
			mergencyRepair.setDeviceName(deviceName);
		}
		String deviceId = "";
		if (param.get("deviceId") != null && !"".equals(param.get("deviceId"))) {
			deviceId = (String) param.get("deviceId");
			mergencyRepair.setDeviceId(deviceId);
		}
		// 故障id和拼接字段（故障（异常）时间+线路/站+设备（杆塔）名称）（关联故障））、
		String faultId = "";
		if (param.get("id") != null && !"".equals(param.get("id"))) {
			faultId = (String) param.get("id");
			mergencyRepair.setFaultId(faultId);
		}
		try {
			oafCommands.insert(mergencyRepair);
		} catch (Exception e) {
			logger.error("抢修表插入异常，请检查", e);
			return "no";
		}
		return "yes";
	}

	/**
	 * <p>
	 * 描述：故障异常更新
	 * </p>
	 * 
	 * @param map
	 * @return String
	 * @author:4621170153 2017年3月14日 下午2:16:43
	 */
	@RequestMapping("/updateFaultTrip")
	public String updateFaultTrip(@RequestParam HashMap<String, Object> map) {
		map.put("$class", FaultTrip.class);
		Date date = null;
		Date recoveryDatedate = null;
		Date powerOutTime = null;
		if ("" != map.get("faultTime") && map.get("faultTime") != null) {
			try {
				date = SDF.parse((String) map.get("faultTime"));// 只能在service层转换，在controller会插不进去
				map.put("faultTime", date);
			} catch (ParseException e2) {
				e2.printStackTrace();
			}
		}
		if ("" != map.get("recoveryDate") && map.get("recoveryDate") != null) {
			try {
				recoveryDatedate = SDF.parse((String) map.get("recoveryDate"));// 只能在service层转换，在controller会插不进去
				map.put("recoveryDate", recoveryDatedate);
			} catch (ParseException e2) {
				e2.printStackTrace();
			}
		}
		if ("" != map.get("powerOutTime") && map.get("powerOutTime") != null) {
			try {
				powerOutTime = SDF.parse((String) map.get("powerOutTime"));// 只能在service层转换，在controller会插不进去
				map.put("powerOutTime", powerOutTime);
			} catch (ParseException e2) {
				logger.error(e2);
			}
		}
		// 需要判断更新时间是否一致
		HashMap<String, Object> mapforupdateTime = new HashMap<String, Object>();
		mapforupdateTime.put("id", map.get("id"));
		mapforupdateTime.put("updateDateTime", map.get("updateDateTime"));
		String flag = null;
		try {
			// 由于更新时间总是不一致，所以先注销掉更新时间
			flag = querycheckDataResult(mapforupdateTime);
			flag = "true";
		} catch (ParseException e1) {
			logger.error(e1);
		}
		if ("true".equals(flag)) {
			try {
				map.put("updateDateTime", new Date());
				oafCommands.update(map);
				// 日志
				WorkLog workLog = (WorkLog) commonWorkLogService.getBeanByID(map.get("id").toString());
				workLog.setUpdateDateTime(new Date());
				StringBuilder sb = new StringBuilder();
				Constant faultPhaseIdCst = ConstantRegistry.findConstant(FaultPhase.TYPE);
				// 故障类别
				Constant FaultCategoryCst = ConstantRegistry.findConstant(FaultCategory.TYPE);
				// 故障类型
//				Constant faultTypeCst = ConstantRegistry.findConstant(FaultType.TYPE);
				Constant voltageLevelCst = ConstantRegistry.findConstant(VoltageLevel.TYPE);
				if (map.get("faultCategory") != null) {
					sb.append("故障类别:" + FaultCategoryCst.getFieldValueByItemValue(Integer.parseInt(map.get("faultCategory").toString()), FaultCategory.NAME));
				}
				if (map.get("organization") != null && !"".equals(map.get("organization"))) {
					sb.append(",所属单位:" + map.get("organization").toString());
				}
				if (map.get("voltageLevel") != null && !"".equals(map.get("voltageLevel"))) {
					sb.append(",电压等级:" +voltageLevelCst.getFieldValueByItemValue(Integer.parseInt( map.get("voltageLevel").toString()), VoltageLevel.NAME) );
				}
				if (map.get("stationOrLineName") != null) {
					sb.append(",线路/站:" + map.get("stationOrLineName").toString());
				}
				if (map.get("deviceName") != null && !"".equals(map.get("deviceName"))) {
					sb.append(",设备名称:" + map.get("deviceName").toString());
				}
				
				if (map.get("faultPhaseId") != null && !"".equals(map.get("faultPhaseId"))) {
					int faultPhaseIdlog = Integer.parseInt((String) map.get("faultPhaseId"));
					sb.append(",故障相别:" +  faultPhaseIdCst.getFieldValueByItemValue(faultPhaseIdlog, FaultPhase.NAME));
				}
				if (map.get("weather") != null && !"".equals(map.get("weather"))) {
					sb.append(",现场天气:" + map.get("weather").toString());
				}
				workLog.setRecordDetails(sb.toString());
				commonWorkLogService.updateWorkLog(workLog);
			} catch (Exception e) {
				logger.error(e);
				return "更新失败";
			}
			return "更新成功";
		} else {
			return "更新失败";
		}
	}

	/**
	 * <p>
	 * 描述：预警跟踪
	 * </p>
	 * 
	 * @param hashmap
	 * @return List<Map<String, Object>>
	 * @author:朱建衡 2017年3月9日 下午4:02:59
	 */
	@RequestMapping("/queryWarnTrackStatusResult")
	public List<Map<String, Object>> queryWarnTrackStatusResult(@RequestParam HashMap<String, Object> hashmap) {
		int pageIndex = 0;
		int pageSize = 0;
		int skipCount = 0;
		int resultCount = 0;
		if(hashmap.get("pageIndex")!=null &&!"".equals(hashmap.get("pageIndex"))){
			pageIndex = Integer.parseInt((String)hashmap.get("pageIndex"));
		}
		if(hashmap.get("pageSize")!=null &&!"".equals(hashmap.get("pageSize"))){
			pageSize = Integer.parseInt((String)hashmap.get("pageSize"));
		}
		skipCount = pageIndex * pageSize;
		String warnNum = "" + hashmap.get("warnNum");
		List<Map<String, Object>> rslt = new ArrayList<>();
		Map<String, Object> param = new HashMap<String, Object>();
		String sql = " from t in sgcim30.assets.management.WarnTrackStatus where t.warnNum ==:warnNum select t then skip : skipCount then  take : pageSize ";
		String sqlCount = "from t in sgcim30.assets.management.WarnTrackStatus where t.warnNum ==:warnNum select resultCount = t.count()";
		param.put("warnNum", warnNum);
		param.put("skipCount", skipCount);
		param.put("pageSize", pageSize );
		param.put("pageIndex", pageIndex );
		try {
			rslt = oafQuery.executeForList(sql.toString(), param);
			for (Map<String, Object> tempTrack : rslt) {
				String time = SDF.format(tempTrack.get("time"));
				tempTrack.put("time", time);
			}
			resultCount = (Integer)oafQuery.executeScalar(sqlCount, param);
		} catch (Exception e) {
			logger.error(e);
		}
		List<Map<String,Object>> list = new ArrayList<>();
		Map<String,Object> mapList = new HashMap<>();
		mapList.put("data", rslt);
		mapList.put("total",resultCount);
		list.add(mapList);
		return list;
	}

	/**
	 * <p>
	 * 描述：WarnTrackStatus预警跟踪 更新新增
	 * </p>
	 * 
	 * @param param
	 * @author:4621170153 2017年4月24日 上午11:04:09
	 * @throws ParseException
	 */
	@RequestMapping("/updatetrackOrNot")
	public void updatetrackOrNot(@RequestParam HashMap<String, String> param) throws ParseException {
		Map<String, Object> trackMap = new HashMap<String, Object>();
		if (param.get("id") == null || param.get("id") == "") {
			trackMap.put("id", UUID.randomUUID().toString());
		}
		trackMap.put("warnNum", (String) param.get("faultid"));
		trackMap.put("deptName", param.get("deptName"));
		trackMap.put("userName", param.get("userName"));
		trackMap.put("deptId", param.get("deptId"));
		trackMap.put("$class", WarnTrackStatus.class.getName());
		Date time = SDF.parse(param.get("time"));
		trackMap.put("time", time); //
		trackMap.put("updateDateTime", new Date()); // 更新时间
		trackMap.put("trackContent", param.get("trackContent")); //
		if (param.get("id") == null || param.get("id") == "") {
			oafCommands.insert(trackMap);
		} else {
			oafCommands.update(trackMap);
		}
	}

	/**
	 * <p>
	 * 描述：各个状态的数量
	 * </p>
	 * 
	 * @return ArrayList<Map<String, Object>>
	 * @author:4621170153 2017年3月15日 下午3:12:45
	 * @throws ParseException 
	 */
	@RequestMapping("/queryStatusNum")
	public ArrayList<HashMap<String, Object>> queryStatusNum( @RequestParam HashMap<String, String> map ) throws ParseException {
		ArrayList<Map<String, Object>> rslt = new ArrayList<Map<String, Object>>();
		// param.put("confirmStatus", "0");
		StringBuilder sql = new StringBuilder(" from t in sgcim30.assets.management.FaultTrip where 1==1 ");
		HashMap  param = new HashMap ();
		if(map.get("startTime")!= null ){
			sql.append("&& t.faultTime >= :startTime  &&  t.faultTime <= :endTime ");
			param.put("startTime", SDF.parse( map.get("startTime").toString()));
			param.put("endTime",  SDF.parse( map.get("endTime").toString()));
		}
		if (map.get("startTime") != null) {
			sql.append("&& t.faultCategory == :faultCategory  ");
			param.put("faultCategory",    map.get("faultCategory").toString()) ;
		}
		sql.append(" groupby t.confirmStatus orderby t.confirmStatus select t.confirmStatus,count=t.count() ");
		try {
			rslt = (ArrayList<Map<String, Object>>) oafQuery.executeForList(sql.toString(),param);
		} catch (OafException e) {
			logger.error(e);
		}
		ArrayList<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
		HashMap<String, Object> mapre = new HashMap<String, Object>();
		for (Map<String, Object> r : rslt) {
			if (1 == (int) r.get("confirmStatus")) {
				mapre.put("xz", r.get("count"));
			}
			if (2 == (int) r.get("confirmStatus")) {
				mapre.put("xgz", r.get("count"));
			}
			if (3 == (int) r.get("confirmStatus")) {
				mapre.put("ybh", r.get("count"));
			}
			if (4 == (int) r.get("confirmStatus")) {
				mapre.put("ygd", r.get("count"));
			}
		}
		list.add(mapre);
		return list;
	};

	/**
	 * <p>
	 * 描述 查询并发时间是否一致
	 * </p>
	 * 
	 * @param hashmap
	 * @return
	 * @throws ParseException
	 * @author:4621170153 2017年5月17日 下午5:04:01
	 */
	private String querycheckDataResult(HashMap<String, Object> hashmap) throws ParseException {
		String sql = "from t in sgcim30.assets.management.FaultTrip  where t.id ==:id select t.updateDateTime";
		List<Map<String, Object>> rslt = null;
		String flag = null;
		String ids = hashmap.get("id").toString();
		String time = hashmap.get("updateDateTime").toString();
		String[] idArray = ids.split(",");
		String[] timeArray = time.split(",");
		for (int i = 0; i < idArray.length; i++) {
			Map<String, Object> param = new LinkedHashMap<String, Object>();
			param.put("id", idArray[i]);
			String timeTemp = timeArray[i];
			try {
				rslt = oafQuery.executeForList(sql, param);
				String updateDateTime1 = null;
				if (rslt.size() != 0) {
					Object rsltTime = rslt.get(0).get("updateDateTime");
					updateDateTime1 = SDF.format(rsltTime);
				}
				if (timeTemp.equals(updateDateTime1)) {
					flag = "true";
				} else {
					flag = "false";
					break;
				}
			} catch (OafException e) {
				logger.error("删除修改时，并发判定出现异常", e);
			}
		}
		return flag;
	}

	/**
	 * <p>
	 * 描述： 通过故障id查询应急抢修
	 * </p>
	 * 
	 * @param map
	 * @return List<Map<String, Object>>
	 * @author:4621170153 2017年5月13日 下午4:26:59
	 */
	@RequestMapping("/queryByFaultId")
	public List<Map<String, Object>> queryByFaultId(@RequestParam HashMap<String, Object> map) {
		String fql = null;
		String faultId = (String) map.get("id");
		List<Map<String, Object>> rslt = new ArrayList<Map<String, Object>>();
		Map<String, Object> param = new LinkedHashMap<String, Object>();
		StringBuilder fromFql = new StringBuilder(" from t in sgcim30.assets.management.EmergencyRepair");

		if (!"".equals(faultId) && faultId != null) {
			fromFql.append(" where t.faultId ==:faultid ");
			param.put("faultid", faultId);
		}
		String selectFql = " select t ";
		fql = fromFql + selectFql;
		try {
			rslt = oafQuery.executeForList(fql, param);
		} catch (OafException e) {
			logger.error(e);
		}
		return rslt;
	}

	/**
	 * <p>
	 * 描述：查询故障相别
	 * </p>
	 * 
	 * @return String
	 * @author:4621170153 2017年4月18日 下午3:50:21
	 */
	@RequestMapping("/getfaultPhaseId")
	public String getfaultPhaseId() {
		int i = 1;
		String faultPhaseId = "";
		Constant cst = ConstantRegistry.findConstant("cst.constants.management.FaultPhase");
		Map<Object, ConstantItem> map = cst.getcSTItems();

		for (Entry<Object, ConstantItem> entry : map.entrySet()) {
			String item = "";
			if (i == 1) {
				item = "{ 'id':'" + entry.getValue().getFieldValueByFieldName("value") + "'," + "'text':'"
						+ entry.getValue().getFieldValueByFieldName("name") + "'}";
				i++;
			} else {
				item = ", { 'id':'" + entry.getValue().getFieldValueByFieldName("value") + "'," + "'text':'"
						+ entry.getValue().getFieldValueByFieldName("name") + "'}";
			}
			faultPhaseId += item;
		}
		faultPhaseId = "[" + faultPhaseId + "]";
		return faultPhaseId;

	}

	/**
	 * <p>
	 * 描述：查询故障类型
	 * </p>
	 * 
	 * @return String
	 * @author:4621170153 2017年4月18日 下午3:47:11
	 */
	@RequestMapping("/getFaultType")
	public String getFaultType() {
		int i = 1;
		String FaultTypes = "";
		// 故障类型
		Constant cst = ConstantRegistry.findConstant(FaultType.TYPE);
		Map<Object, ConstantItem> map = cst.getcSTItems();
		for (Entry<Object, ConstantItem> entry : map.entrySet()) {
			String item = "";
			if (i == 1) {
				item = "{ 'id':'" + entry.getValue().getFieldValueByFieldName("value") + "'," + "'text':'"
						+ entry.getValue().getFieldValueByFieldName("name") + "'}";
				i++;
			} else {
				item = ", { 'id':'" + entry.getValue().getFieldValueByFieldName("value") + "'," + "'text':'"
						+ entry.getValue().getFieldValueByFieldName("name") + "'}";
			}
			FaultTypes += item;
		}
		FaultTypes = "[" + FaultTypes + "]";
		return FaultTypes;
	}

	/**
	 * <p>
	 * 描述：查询故障类别
	 * </p>
	 * 
	 * @return String
	 * @author:4621170153 2017年4月18日 下午2:58:09
	 */
	@RequestMapping("/getFaultCategory")
	public String getFaultCategory() {
		int i = 1;
		String faultCategory = "";
		Constant cst = ConstantRegistry.findConstant("cst.constants.management.FaultCategory");
		Map<Object, ConstantItem> map = cst.getcSTItems();
		for (Entry<Object, ConstantItem> entry : map.entrySet()) {
			String item = "";
			if (i == 1) {
				item = "{ 'id':'" + entry.getValue().getFieldValueByFieldName("value") + "'," + "'text':'"
						+ entry.getValue().getFieldValueByFieldName("name") + "'}";
				i++;
			} else {
				item = ", { 'id':'" + entry.getValue().getFieldValueByFieldName("value") + "'," + "'text':'"
						+ entry.getValue().getFieldValueByFieldName("name") + "'}";
			}
			faultCategory += item;
		}
		faultCategory = "[" + faultCategory + "]";
		return faultCategory;
	}

	/**
	 * <p>
	 * 描述：极闭锁动作情况
	 * </p>
	 * 
	 * @return String
	 * @author:4621170153 2017年4月21日 上午10:50:44
	 */
	@RequestMapping("/getreclosingAction")
	public String getreclosingAction() {
		int i = 1;
		String reclosingAction = "";
		Constant cst = ConstantRegistry.findConstant("cst.constants.management.ReclosingAction");
		Map<Object, ConstantItem> map = cst.getcSTItems();
		for (Entry<Object, ConstantItem> entry : map.entrySet()) {
			String item = "";
			if (i == 1) {
				item = "{ 'id':'" + entry.getValue().getFieldValueByFieldName("value") + "'," + "'text':'"
						+ entry.getValue().getFieldValueByFieldName("name") + "'}";
				i++;
			} else {
				item = ", { 'id':'" + entry.getValue().getFieldValueByFieldName("value") + "'," + "'text':'"
						+ entry.getValue().getFieldValueByFieldName("name") + "'}";
			}
			reclosingAction += item;
		}
		reclosingAction = "[" + reclosingAction + "]";
		return reclosingAction;
	}

	/**
	 * <p>
	 * 描述：是否
	 * </p>
	 * 
	 * @return String
	 * @author:4621170153 2017年4月21日 上午10:03:25
	 */
	@RequestMapping("/getYesOrNot")
	public String getYesOrNot() {
		int i = 1;
		String yesOrNot = "";
		Constant cst = ConstantRegistry.findConstant("cst.constants.management.YesOrNot");
		Map<Object, ConstantItem> map = cst.getcSTItems();
		for (Entry<Object, ConstantItem> entry : map.entrySet()) {
			String item = "";
			if (i == 1) {
				item = "{ 'id':'" + entry.getValue().getFieldValueByFieldName("value") + "'," + "'text':'"
						+ entry.getValue().getFieldValueByFieldName("name") + "'}";
				i++;
			} else {
				item = ", { 'id':'" + entry.getValue().getFieldValueByFieldName("value") + "'," + "'text':'"
						+ entry.getValue().getFieldValueByFieldName("name") + "'}";
			}
			yesOrNot += item;
		}
		yesOrNot = "[" + yesOrNot + "]";
		return yesOrNot;
	}

	/**
	 * <p>
	 * 描述：获取电压等级下拉框
	 * </p>
	 * 
	 * @return String
	 * @author:4621170153 2017年4月18日 下午2:48:19
	 */
	@RequestMapping("/getvoltageLevel_fault")
	public String getvoltageLevel() {
		int i = 1;
		String voltageLevel = "";
		Constant cst = ConstantRegistry.findConstant("cst.constants.common.VoltageLevel");
		Map<Object, ConstantItem> map = cst.getcSTItems();
		for (Entry<Object, ConstantItem> entry : map.entrySet()) {
			String item = "";
			if (i == 1) {
				item = "{ 'id':'" + entry.getValue().getFieldValueByFieldName("value") + "'," + "'text':'"
						+ entry.getValue().getFieldValueByFieldName("fullname") + "'}";
				i++;
			} else {
				item = ", { 'id':'" + entry.getValue().getFieldValueByFieldName("value") + "'," + "'text':'"
						+ entry.getValue().getFieldValueByFieldName("fullname") + "'}";
			}
			voltageLevel += item;
		}
		voltageLevel = "[" + voltageLevel + "]";
		return voltageLevel;
	}

	/**
	 * <p>
	 * 描述：数据类型转换方法
	 * </p>
	 * 
	 * @param parames
	 * @return 4545
	 * @author:朱建衡 2017年3月13日 下午4:06:19
	 */
	public List<Map<String, Object>> getFilterArray(String parames) {
		ObjectMapper objectMapper = new ObjectMapper();
		List<Map<String, Object>> listMapFilter = null;
		try {
			listMapFilter = objectMapper.readValue(parames, new TypeReference<List<Map<String, Object>>>() {
			});
		} catch (Exception e) {
			logger.error(e);
		}
		return listMapFilter;
	}

	/******************************** 日常业务start ****************************************/
	@Autowired
	CommonQueryUtils commonQuery;

	private static final String FAULTTRIP = "故障异常";
	private static final String[] FAULTTRIPCOLS = new String[] { "id", "recordDate", "faultCategory",
			 "stationOrLineName", "deviceName", "faultPhaseId"
			};//故障类别+站/线+设备名称+故障相别

	/**
	 * <p>
	 * 描述：获取故障跳闸统计数据
	 * </p>
	 * 
	 * @param param
	 * @return 故障跳闸统计数据
	 * @author:范阳 2017年3月9日 下午2:49:31
	 */
	@RequestMapping("/queryFaultTripCount")
	public Map<String, Object> queryFaultTripCount(Map<String, Object> param) {

		Map<String, Object> rsl = new HashMap<String, Object>();

		// 直流
		rsl.put("dcTripNewCnt", queryDCTripNewCnt());
		rsl.put("dcTripTrackCnt", queryDCTripTrackCnt());

		// 交流
		rsl.put("acTripNewCnt", acTripNewCnt());
		rsl.put("acTripTrackCnt", queryACTripTrackCnt());

		// 通道异常
		rsl.put("wayAbnormalNewCnt", queryWayAbnormalNewCnt());
		rsl.put("wayAbnormalTrackCnt", queryWayAbnormalTrackCnt());

		return rsl;
	}

	/**
	 * <p>
	 * 描述：获取故障跳闸grid数据
	 * </p>
	 * 
	 * @param param
	 * @return 故障跳闸grid数据
	 * @author:范阳 2017年3月14日 下午4:00:31
	 */
	@RequestMapping("/queryFaultTripInfo")
	public List<Map<String, Object>> queryFaultTripInfo(@RequestParam Map<String, Object> param) {
		Map<String, Object> paramMap = parseQueryParam(param);

		List<String> columnList = new ArrayList<String>(Arrays.asList(FAULTTRIPCOLS));

		List<Map<String, Object>> rsl = commonQuery.queryInfo(FaultTrip.class.getName(), paramMap, columnList, null,
				null);

		Constant faultCategoryCst = ConstantRegistry.findConstant(FaultCategory.TYPE);
		Constant faultPhaseCst = ConstantRegistry.findConstant(cst.constants.management.FaultPhase.TYPE);
		for (Map<String, Object> map : rsl) {
			// 故障类型
			Integer faultCategoryValue = (Integer) map.get("faultCategory");
			String faultCategoryName = "";

			if (faultCategoryValue != 0) {
				faultCategoryName = faultCategoryCst.getFieldValueByItemValue(faultCategoryValue, "name");
			}

			map.put("faultCategory", faultCategoryName);
			map.put("segment1", FAULTTRIP + "-" + faultCategoryName);

			//故障相别
			Integer faultPhaseId = (Integer)map.get("faultPhaseId");
			map.put("faultPhase", faultPhaseCst.getFieldValueByItemValue(faultPhaseId, cst.constants.management.FaultPhase.NAME));
		
		}

		Map<String, Object> scalars = new HashMap<>();
		scalars.put("id", "id");
		scalars.put("segment1", "segment1");
		scalars.put("segment2", "recordDate");
		scalars.put("segment3", new String[]{"faultCategory", "stationOrLineName", "deviceName", "faultPhase"});

		rsl = commonQuery.convertQueryResult(rsl, scalars);

		return rsl;
	}

	private long queryDCTripNewCnt() {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("faultCategory", "1");
		param.put("confirmStatus", "1");

		return commonQuery.queryCount(FaultTrip.class.getName(), param);
	}

	private long queryDCTripTrackCnt() {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("faultCategory", "1");
		param.put("confirmStatus", "2");

		return commonQuery.queryCount(FaultTrip.class.getName(), param);
	}

	private long acTripNewCnt() {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("faultCategory", "2");
		param.put("confirmStatus", "1");

		return commonQuery.queryCount(FaultTrip.class.getName(), param);
	}

	private long queryACTripTrackCnt() {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("faultCategory", "2");
		param.put("confirmStatus", "2");

		return commonQuery.queryCount(FaultTrip.class.getName(), param);
	}

	private long queryWayAbnormalNewCnt() {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("faultCategory", "3");
		param.put("confirmStatus", "1");

		return commonQuery.queryCount(FaultTrip.class.getName(), param);
	}

	private long queryWayAbnormalTrackCnt() {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("faultCategory", "3");
		param.put("confirmStatus", "2");

		return commonQuery.queryCount(FaultTrip.class.getName(), param);
	}

	private Map<String, Object> parseQueryParam(Map<String, Object> param) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		Object confirmStatusObj = param.get("confirmStatus");
		if (null != confirmStatusObj && !"".equals((String) confirmStatusObj)) {
			String confirmStatus = (String) confirmStatusObj;
			switch (confirmStatus) {
			case "进行中":
				confirmStatus = "1";
				break;
			case "需跟踪":
				confirmStatus = "2";
				break;
			case "已闭环":
				confirmStatus = "3";
				break;
			}
			paramMap.put("confirmStatus", confirmStatus);
		}

		return paramMap;
	}

	/******************************** 日常业务end ****************************************/
	/**
	 * <p>
	 * 描述：查询故障异常
	 * </p>
	 * 
	 * @return 各阶段记录数量
	 * @author:王万胜 2017年3月14日 下午7:54:01
	 * @param params
	 */
	@RequestMapping("/getFault")
	public List<Map<String, Object>> getFault(@RequestParam HashMap<String, String> params) {
		String id = params.get("id"); // 故障ID
		Date faultTime1 = null; // 故障时间1
		Date faultTime2 = null; // 故障时间2
		String voltageLevel = params.get("voltageLevel"); // 电压等级
		String stationId = params.get("stationId"); // 电站ID
		String lineId = params.get("lineId"); // 线路ID
		boolean haveFilter = false;

		Map<String, Object> param = new LinkedHashMap<String, Object>();

		StringBuilder sql = new StringBuilder(" from t in sgcim30.assets.management.FaultTrip ");
		if (!"".equals(id) && !"null".equals(id) && id != null) {
			sql.append("where t.id ==: id ");
			param.put("id", id);
		} else {

			try {
				if (!"".equals(params.get("faultTime1").toString())) {
					faultTime1 = SDF.parse(params.get("faultTime1").toString());
				}
				if (!"".equals(params.get("faultTime2").toString())) {
					faultTime2 = SDF.parse(params.get("faultTime2").toString());
				}
			} catch (ParseException e) {
				e.printStackTrace();
			}

			if ((!"".equals(faultTime1) && faultTime1 != null) || (!"".equals(faultTime2) && faultTime2 != null)
					|| !"".equals(voltageLevel) || !"".equals(stationId) || !"".equals(lineId)) {
				sql.append("where ");

				// 故障时间1
				if (!"".equals(faultTime1) && !"null".equals(faultTime1) && faultTime1 != null) {
					sql.append("t.faultTime >=: faultTime1 ");
					param.put("faultTime1", faultTime1);
					haveFilter = true;
				}

				// 故障时间2
				if (!"".equals(faultTime2) && !"null".equals(faultTime2) && faultTime2 != null) {
					if (haveFilter) {
						sql.append("&& t.faultTime <=: faultTime2 ");
					} else {
						sql.append("t.faultTime <=: faultTime2 ");
						haveFilter = true;
					}
					param.put("faultTime2", faultTime2);
				}

				// 电压等级
				if (!"".equals(voltageLevel) && !"null".equals(voltageLevel) && voltageLevel != null) {
					if (haveFilter) {
						sql.append("&& t.voltageLevel ==: voltageLevel ");
					} else {
						sql.append("t.voltageLevel ==: voltageLevel ");
						haveFilter = true;
					}
					param.put("voltageLevel", voltageLevel);
				}

				// 电站ID
				if (!"".equals(stationId) && !"null".equals(stationId) && stationId != null) {
					if (haveFilter) {
						sql.append("&& t.stationOrLineId ==: stationId ");
					} else {
						sql.append("t.stationOrLineId ==: stationId ");
						haveFilter = true;
					}
					param.put("stationId", stationId);
				}

				// 线路ID
				if (!"".equals(lineId) && !"null".equals(lineId) && lineId != null) {
					if (haveFilter) {
						sql.append("&& t.stationOrLineId ==: lineId ");
					} else {
						sql.append("t.stationOrLineId ==: lineId ");
						haveFilter = true;
					}
					param.put("lineId", lineId);
				}
			}
		}

		// 表格分页
		StringBuilder sqlCount = new StringBuilder();
		sqlCount.append(sql);
		sqlCount.append(" select sqlCount = t.count() ");
		
		if(!ObjectUtils.isEmpty(params.get("pageSize"))){
			sql.append("select t then skip :skipCount then take :pageSize ");
		}else{
			sql.append("select t ");
		}

		List<Map<String, Object>> rslt = null;
		int rsltCount = 0; // 统计表格展示条数
		try {
			if(!ObjectUtils.isEmpty(params.get("pageSize"))){
				int pageSize = Integer.parseInt(params.get("pageSize").toString()); // 获取分页参数
				int pageIndex = Integer.parseInt(params.get("pageIndex").toString());
				int skipCount = pageIndex * pageSize;
				rsltCount = (Integer) oafQuery.executeScalar(sqlCount.toString(), param);
				param.put("skipCount", skipCount);
				param.put("pageSize", pageSize);
			}
			rslt = oafQuery.executeForList(sql.toString(), param);
		} catch (OafException e) {
			logger.error("应急抢修--查询故障信息异常", e);
		}

		Constant faultCategoryCst = ConstantRegistry.findConstant( FaultCategory.TYPE);
		Constant faultTypeCst = ConstantRegistry.findConstant( FaultType.TYPE);
//		Constant typeconstant = ConstantRegistry.findConstant(Type.TYPE);
		Constant voltageLevelCst = ConstantRegistry.findConstant(VoltageLevel.TYPE);
		Constant faultPhaseCst = ConstantRegistry.findConstant(FaultPhase.TYPE);
		for (Map<String, Object> map : rslt) {
			// 故障类别（原  故障类型）
			if (!ObjectUtils.isEmpty(map.get("faultCategory"))) {
				int faultCategoryValue = ((Integer) map.get("faultCategory")).intValue();
				String faultCategoryName = faultCategoryCst.getFieldValueByItemValue(faultCategoryValue, "name");
				map.put("faultCategory", faultCategoryName);
			}
			
			// 故障类型（原  原因分类）
			if (!ObjectUtils.isEmpty(map.get("faultType"))) {
				String faultTypeValue = map.get("faultType").toString();
				String faultTypeName = faultTypeCst.getFieldValueByItemValue(faultTypeValue, "name");
				map.put("faultType", faultTypeName);
			}

//			// 专业
//			if (!ObjectUtils.isEmpty(map.get("type"))) {
//				String typeValue = map.get("type").toString();
//				String typeName = typeconstant.getFieldValueByItemValue(typeValue, "name");
//				map.put("type", typeName);
//			}

			// 电压等级
			if (!ObjectUtils.isEmpty(map.get("voltageLevel"))) {
				int voltageLevelValue = ((Integer) map.get("voltageLevel")).intValue();
				String voltageLevelName = voltageLevelCst.getFieldValueByItemValue(voltageLevelValue, "name");
				map.put("voltageLevel", voltageLevelName);
			}
			
			// 故障相别
			if (!ObjectUtils.isEmpty(map.get("faultPhaseId"))) {
				int faultPhaseValue = ((Integer) map.get("faultPhaseId")).intValue();
				String faultPhaseName = faultPhaseCst.getFieldValueByItemValue(faultPhaseValue, "name");
				map.put("faultPhaseId", faultPhaseName);
			}
			
			// 时间转换（开始时间、结束时间）
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			if(map.get("faultTime") != null){
				String faultTime = sdf.format(map.get("faultTime"));
				map.put("faultTime", faultTime);
			}
		}
		
		// 返回查询结果
		Map<String, Object> rst = new HashMap<String, Object>();
		List<Map<String, Object>> list = new ArrayList<>();
		if(!ObjectUtils.isEmpty(params.get("pageSize"))){
			if(!ObjectUtils.isEmpty(params.get("pageSize"))){
				rst.put("total", rsltCount);
			}
		}
		rst.put("data", rslt);
		list.add(rst);
		return list;
	}

	/**
	 * <p>
	 * 描述：编辑故障跳闸中应急抢修相关数据
	 * </p>
	 * 
	 * @param params
	 * @author:王万胜 2017年3月14日 上午9:43:23
	 */
	@RequestMapping("/updateFault")
	public void updateFault(@RequestParam HashMap<String, Object> params) {
		boolean haveRecoveryDate = params.containsKey("recoveryDate");
		
		if (haveRecoveryDate) {
			Date recoveryDate = null; // 恢复时间
			Object recovery = params.get("recoveryDate");
			try {
				recoveryDate = SDF.parse(recovery.toString());
			} catch (ParseException e) {
				e.printStackTrace();
			}

			params.put("recoveryDate", recoveryDate);
		}
		params.put(nariis.falcon.oaf.Constant.CLASS_KEY, FaultTrip.class);

		try {
			oafCommands.update(params);
		} catch (Exception e) {
			logger.error("更新故障异常", e);
		}
	}
	
	/**变电方法
	 * 全电压等级当日发生故障情况, 条件【上级组织机构, 故障发生时间】
	 * 
	 * @param paramMap
	 *            参数map
	 * @return 统计结果
	 */
	@RequestMapping("/getFaultTripInfoTodayResult")
	public List<Map<String, Object>> getFaultTripInfoTodayResult(@RequestParam Map<String, Object> paramMap) {
		StringBuilder fromFql = new StringBuilder(" from t in " + FaultTrip.class.getName());
		StringBuilder whereFql = new StringBuilder();
		whereFql.append(" where ");
		whereFql.append("  t.faultTime >= :startHappenTime "); // 故障发生时间
		whereFql.append(" && t.faultTime < :endHappenTime "); // 故障发生时间
		whereFql.append(" && t.faultCategory == :faultCategory "); // 故障类型
 		paramMap.put("faultCategory", FaultCategory._2); // 故障类型
 		whereFql.append(" && t.confirmStatus == :confirmStatus "); // 确认情况
 		paramMap.put("confirmStatus", ConfirmStatus._2); // 确认情况
		if (!StringUtils.isEmpty(paramMap.get("organization")) && !"all".equals(paramMap.get("organization"))) {
			whereFql.append(" && t.organizationId == :organization ");
		}
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		paramMap.put("startHappenTime", calendar.getTime()); // 故障发生时间
		calendar.add(Calendar.DATE, 1);
		paramMap.put("endHappenTime", calendar.getTime()); // 故障发生时间
		String selectFql = " orderby t.faultTime select t "; // 故障跳闸表
		String fql = new StringBuilder().append(fromFql.toString()).append(whereFql.toString()).append(selectFql)
				.toString();
		List<Map<String, Object>> result = null;
		try {
			result = oafQuery.executeForList(fql, paramMap);
		} catch (OafException e) {
			logger.error("当日发生故障情况结果查询异常", e);
		}
		return result != null ? result : new ArrayList<Map<String, Object>>();
	}

	

	private String fromCondition = " from fault in " + FaultTrip.class.getName() + " join outline in "
			+ Outline.class.getName() + " on fault.deviceId equals outline.id ";

	private String whereCondition = " where (1 == 1) ";

	/**
	 * <p>
	 * 描述：变电方法 获取单位
	 * </p>
	 * 
	 * @return 高翔
	 * @author:高翔 2017年4月9日 下午4:50:37
	 */
	@RequestMapping("/getBelongCity")
	public List<Map<String, Object>> getBelongCity() {
		List<String> colList = new ArrayList<>();
		colList.add("belongCity");
		colList.add("belongCityName");
		return getListByColumnOnOutline(colList);
	}

	/**
	 * <p>
	 * 描述：变电方法 获取电压等级统计结果
	 * </p>
	 * 
	 * @return 数据
	 * @author:高翔 2017年7月9日 下午4:50:37
	 */
	@RequestMapping("/getVoltageLevel")
	public List<Map<String, Object>> getVoltageLevel() {
		List<String> colList = new ArrayList<>();
		colList.add("voltageLevel");
		return getListByColumnOnOutline(colList);
	}

	/**
	 * <p>
	 * 描述：变电方法 获取变电站统计结果
	 * </p>
	 * 
	 * @return 数据
	 * @author:高翔 2017年4月9日 下午4:50:37
	 */
	@RequestMapping("/getBelongSubstation")
	public List<Map<String, Object>> getBelongSubstation() {
		List<String> colList = new ArrayList<>();
		colList.add("belongStation");
		return getListByColumnOnOutline(colList);
	}

	/**
	 * <p>
	 * 描述：变电方法 获取故障类型
	 * </p>
	 * 
	 * @return 数据
	 * @author:高翔 2017年4月9日 下午4:50:37
	 */
	@RequestMapping("/getfaultType")
	public List<Map<String, Object>> getfaultType() {
		List<String> colList = new ArrayList<>();
		colList.add("faultType");
		return getListByColumnOnRecord(colList);
	}

	private List<Map<String, Object>> getListByColumnOnOutline(List<String> col) {
		List<Map<String, Object>> infoList = null;
		String fql = fromCondition + whereCondition;
		String groupby = " groupby ";
		String orderby = " orderby ";
		String select = " select ";
		for (int i = 0; i < col.size(); i++) {
			if (i == (col.size() - 1)) {
				groupby = groupby + "outline." + col.get(i);
				orderby = orderby + "outline." + col.get(i);
				select = select + "outline." + col.get(i);
			} else {
				groupby = groupby + "outline." + col.get(i) + ",";
				orderby = orderby + "outline." + col.get(i) + ",";
				select = select + "outline." + col.get(i) + ",";
			}
		}
		fql = fql + groupby + orderby + select;
		try {
			infoList = oafQuery.executeForList(fql);
		} catch (OafException e) {
			logger.error("获取统计结果", e);
		}
		return infoList;
	}

	private List<Map<String, Object>> getListByColumnOnRecord(List<String> col) {
		List<Map<String, Object>> infoList = null;
		String fql = fromCondition + whereCondition;
		String groupby = " groupby ";
		String orderby = " orderby ";
		String select = " select ";
		for (int i = 0; i < col.size(); i++) {
			if (i == (col.size() - 1)) {
				groupby = groupby + "fault." + col.get(i);
				orderby = orderby + "fault." + col.get(i);
				select = select + "fault." + col.get(i);
			} else {
				groupby = groupby + "fault." + col.get(i) + ",";
				orderby = orderby + "fault." + col.get(i) + ",";
				select = select + "fault." + col.get(i) + ",";
			}
		}
		fql = fql + groupby + orderby + select;
		try {
			infoList = oafQuery.executeForList(fql);
		} catch (OafException e) {
			logger.error("获取故障类型统计结果", e);
		}
		return infoList;
	}

	
	
	
	/**输电方法
	 *  
	 * 
	 * @param param
	 *            参数map
	 * @return List<Map<String, Object>>
	 */
	@RequestMapping("/queryGzxxlByLineId")
	public List<Map<String, Object>> queryGzxxlByLineId(@RequestParam Map<String,Object> param){
		StringBuilder fql = new StringBuilder();
		fql.append("from ft in " + FaultTrip.class.getName() + " ");
		fql.append("left join line in " + AstLineAssetInfoS.class.getName() + " on ft.stationOrLineId equals line.Id ");
		fql.append("left join pole in " + AstPolesite.class.getName() + " on ft.deviceId equals pole.Id ");
		fql.append("left join bc in " + TmBigCross.class.getName() + " on bc.lineName equals line.lineName ");
		fql.append("left join tc in " + TmThreeCrossPoint.class.getName() + " on tc.PMSLineId equals line.Id ");
		fql.append("left join oc in " + TmOtherCross.class.getName() + " on oc.belongLine equals line.Id ");
		fql.append("where ft.faultType != '02' ");
		fql.append("&& ft.stationFaultOrNot == '0' ");
		fql.append("&& (ft.reclosingAction != 5 ||  ");
		fql.append("ft.reclosingAction == 1) ");
		fql.append(this.lineCommonTj());
		
		fql.append("&& ft.faultTime <= :operationdate ");
		fql.append("&& ft.faultTime >= :operationdate1 ");
		Calendar calen = Calendar.getInstance();
		param.put("operationdate", calen.getTime());
		calen.set(Calendar.HOUR_OF_DAY, 0);
		calen.set(Calendar.MINUTE, 0);
		calen.set(Calendar.SECOND, 0);
		param.put("operationdate1", calen.getTime());
		if(param.get("lineId") != null 
				&& !"".equalsIgnoreCase(String.valueOf(param.get("lineId")))){
			fql.append("&& line.Id == :lineId ");
		}
		fql.append("select dky = bc.id, sk = tc.Obj_Id, qtky = oc.id, ssdw = line.belongCityName, gzxl = line.lineName, gzxlid = line.Id, gzgtid = pole.Id, gzgt = pole.towerSerialNo, gzfssj = ft.faultTime ");
		
		List<Map<String, Object>> gzxxList = null;
		try {
			gzxxList = oafQuery.executeForList(fql.toString(), param);
		} catch (OafException e) {
			e.printStackTrace();
			logger.error(e);
		}
		return gzxxList;
	}
	
	
	/**输电方法
	 *  
	 * 
	 * @param  
	 *          
	 * @return String
	 */
	public String lineCommonTj(){
		StringBuilder fql = new StringBuilder();
		fql.append("&& line.erectType != '3' ");
		fql.append("&& line.assetProperty != '05' ");
		fql.append("&& line.specialtyClassification == 1 ");
		fql.append("&& ((line.voltageClass >= 25 && line.voltageClass <= 45) || line.voltageClass >= 76) ");
		return fql.toString();
	}


}
