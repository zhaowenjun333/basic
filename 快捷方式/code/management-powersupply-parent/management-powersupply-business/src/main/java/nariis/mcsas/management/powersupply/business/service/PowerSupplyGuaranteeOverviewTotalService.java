package nariis.mcsas.management.powersupply.business.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cst.constants.common.FaultType;
import cst.constants.common.VoltageLevel;
import cst.constants.management.DefectQuale;
import cst.constants.management.DefectState;
import cst.constants.management.FaultCategory;
import cst.constants.management.FaultPhase;
import cst.constants.management.PowerLever;
import cst.constants.management.RegulationsType;
import cst.constants.management.VehicleType;
import nariis.falcon.commons.springcloud.RequestCache;
import nariis.falcon.oaf.OafException;
import nariis.falcon.oaf.OafQuery;
import nariis.falcon.oaf.models.constant.Constant;
import nariis.falcon.oaf.models.constant.ConstantRegistry;




/**
 * <p>ClassName:PowerSupplyGuaranteeOverviewTotalService，</p>
 * <p>描述: 供电保障保电总览服务层</p>
 * @author 赵云
 * @date 2017年6月19日上午9:13:30
 * @version 
 */
@RestController
@RequestMapping("/overviewTotal")
public class PowerSupplyGuaranteeOverviewTotalService {
	@Autowired
	OafQuery oafQuery;
	private static final Logger logger = LogManager.getLogger(PowerSupplyGuaranteeService.class);
	private static final SimpleDateFormat SDF = new SimpleDateFormat("yyyy-MM-dd HH:mm");
	
	
	/**
	 * <p>描述： 获取站或者线的表格明细</p> 
	 * @param hashmap
	 * @return  List<Map<String, Object>>
	 * @author:赵云
	 * 2017年6月26日 上午10:15:28
	 */
	@RequestMapping("/getDefectgrid")
	public List<Map<String, Object>> getDefectgrid(@RequestParam HashMap<String, Object> hashmap) {
		HashMap<String, Object> hm = new HashMap<String, Object>();
		StringBuilder fql = new StringBuilder();
		int pageSize = Integer.parseInt((String)hashmap.get("pageSize"));//框架自带pageSize
		int pageIndex = Integer.parseInt((String)hashmap.get("pageIndex"));
		int skipCount = pageIndex*pageSize;
		fql.append("from t in sgcim30.assets.management.ElectricityEnsure ");
		fql.append("join t1 in sgcim30.assets.management.ElectricityEnsurePhase on t.id equals t1.belongTaskId ");
		fql.append("join t2 in sgcim30.assets.management.StationLineEnsure on t.id equals t2.belongTaskId ");
		fql.append("join t5 in sgcim30.assets.management.ElectricityEnsurePhase on t2.id equals t5.belongTaskId ");
		fql.append("join t4 in sgcim30.assets.management.DefectState on t4.powerStationId equals t2.stationLineID ");
		 hm.put("sysdate", new Date());
		if( hashmap.get("status")!=null && !"".equals(hashmap.get("status")) &&"进行中".equals(hashmap.get("status")) ){
			fql.append( " where   t.startTime <=:sysdate  && t.endTime >=:sysdate    ");
		}else{
			//准备中
			fql.append( " where   t.startTime >=:sysdate   ");
		}
		fql.append( " &&  t5.startTime <=:sysdate  && t5.endTime >=:sysdate  && t1.startTime <=:sysdate  && t1.endTime>=:sysdate  ");
		if( "已消缺".equals(hashmap.get("flag")) ){
			fql.append( "   &&  t4.correctiveDate >=  t.startTime && t4.correctiveDate< t.endTime ");
			fql.append( " && (t4.confirmStatus ==4 ||  t4.confirmStatus ==5  ) ");
			
		}else{
			fql.append( " && (  t4.confirmStatus ==1 ||  t4.confirmStatus ==2 ||  t4.confirmStatus ==3  ) ");
		}
		if( hashmap.get("organizationid")!=null && !"".equals(hashmap.get("organizationid")) ){
			fql.append( " && t.dutyUnitID ==:organizationid ");
			hm.put("organizationid", hashmap.get("organizationid"));//两个参数都是形式参数；
		}
		if( hashmap.get("taskLevel")!=null && !"".equals(hashmap.get("taskLevel"))   ){
			fql.append( " &&  t1.ensureLevel  ==: taskLevel ");
			hm.put("taskLevel", hashmap.get("taskLevel"));//两个参数都是形式参数；
		}
		String sqlCount =  "  select sqlCount = t4.count() " ;
		sqlCount=fql+sqlCount;//计算总数sql /fql 
		fql.append( " select  t4  then skip :skipCount then take :pageSize  ");
		List<Map<String, Object>> rslt = null;
		Integer rsltCount = 0;
		try {
			rsltCount = (Integer)oafQuery.executeScalar(sqlCount , hm);	
			hm.put("skipCount",skipCount);
			hm.put("pageSize",pageSize);
			rslt = oafQuery.executeForList(fql.toString(),hm);
		} catch (OafException e) {
			logger.error(e);
		}
		for(Map<String,Object> map : rslt){
			// 电压等级
			Constant voltageLevelcst = ConstantRegistry.findConstant(VoltageLevel.TYPE);
			if(!ObjectUtils.isEmpty(map.get("voltageClass"))){
				int voltageLevelValue = Integer.parseInt(map.get("voltageClass").toString());
				String voltageLevelName = voltageLevelcst.getFieldValueByItemValue(voltageLevelValue, "name");
				map.put("voltageClass", voltageLevelName);
			}
			Constant defectPropertiesCst = ConstantRegistry.findConstant(DefectQuale.TYPE);
			// 缺陷性质
			if (!ObjectUtils.isEmpty(map.get("defectProperties"))) {
				String defectPropertiesValue = map.get("defectProperties").toString();
				String defectPropertiesName = defectPropertiesCst.getFieldValueByItemValue(defectPropertiesValue, "name");
				map.put("defectProperties", defectPropertiesName);
			}
			
			Constant defectStateCst = ConstantRegistry.findConstant(DefectState.TYPE);
			// 缺陷状态
			if (!ObjectUtils.isEmpty(map.get("defectState"))) {
				String defectStateValue = map.get("defectState").toString();
				String defectStateName = defectStateCst.getFieldValueByItemValue(defectStateValue, "name");
				map.put("defectState", defectStateName);
			}
		} 
		List<Map<String,Object>> list = new ArrayList<>();
		Map<String,Object> mapList = new HashMap<>();
		mapList.put("data", rslt);
		mapList.put("total",rsltCount);
		list.add(mapList);
		return list;
	}
	/**
	 * <p>描述： 获取站或者线的表格明细</p> 
	 * @param hashmap
	 * @return  List<Map<String, Object>>
	 * @author:赵云
	 * 2017年6月26日 上午10:15:28
	 */
	@RequestMapping("/getFaultgrid")
	public List<Map<String, Object>> getFaultgrid(@RequestParam HashMap<String, Object> hashmap) {
		HashMap<String, Object> hm = new HashMap<String, Object>();
		StringBuilder fql = new StringBuilder();
		int pageSize = Integer.parseInt((String)hashmap.get("pageSize"));//框架自带pageSize
		int pageIndex = Integer.parseInt((String)hashmap.get("pageIndex"));
		int skipCount = pageIndex*pageSize;
		fql.append("from t in sgcim30.assets.management.ElectricityEnsure ");
		fql.append("join t1 in sgcim30.assets.management.ElectricityEnsurePhase on t.id equals t1.belongTaskId ");
		fql.append("join t2 in sgcim30.assets.management.StationLineEnsure on t.id equals t2.belongTaskId ");
		fql.append("join t5 in sgcim30.assets.management.ElectricityEnsurePhase on t2.id equals t5.belongTaskId ");
		fql.append("join t4 in sgcim30.assets.management.FaultTrip on t4.stationOrLineId equals t2.stationLineID ");
		 hm.put("sysdate", new Date());
		 
		if( hashmap.get("status")!=null && !"".equals(hashmap.get("status")) &&"进行中".equals(hashmap.get("status")) ){
			fql.append( " where   t.startTime <=:sysdate  && t.endTime >=:sysdate   &&  t5.startTime <=:sysdate  && t5.endTime >=:sysdate  && t1.startTime <=:sysdate  && t1.endTime>=:sysdate  ");
		}else{
			//准备中
			fql.append( " where   t.startTime >=:sysdate   ");
		}
//		fql.append( "&&  t.startTime <=:sysdate  && t.endTime >=:sysdate  &&  t5.startTime <=:sysdate  && t5.endTime >=:sysdate  && t1.startTime <=:sysdate  && t1.endTime>=:sysdate  ");
		if( "已恢复".equals(hashmap.get("flag")) ){
			fql.append( "   &&  t4.recoveryDate >=  t.startTime && t4.recoveryDate< t.endTime ");
			fql.append( " &&  (t4.confirmStatus ==3 ||  t4.confirmStatus ==4 )  ");
			
		}else{
			fql.append( " &&   (t4.confirmStatus ==1 ||  t4.confirmStatus ==2 )  ");
		}
		if( hashmap.get("organizationid")!=null && !"".equals(hashmap.get("organizationid")) ){
			fql.append( " && t.dutyUnitID ==:organizationid ");
			hm.put("organizationid", hashmap.get("organizationid"));//两个参数都是形式参数；
		}
		if( hashmap.get("taskLevel")!=null && !"".equals(hashmap.get("taskLevel"))   ){
			fql.append( " &&  t1.ensureLevel  ==: taskLevel ");
			hm.put("taskLevel", hashmap.get("taskLevel"));//两个参数都是形式参数；
		}
		String sqlCount =  "  select sqlCount = t4.count() " ;
		sqlCount=fql+sqlCount;//计算总数sql /fql 
		fql.append( " select  t4  then skip :skipCount then take :pageSize  ");
		List<Map<String, Object>> rslt = null;
		Integer rsltCount = 0;
		try {
			rsltCount = (Integer)oafQuery.executeScalar(sqlCount , hm);	
			hm.put("skipCount",skipCount);
			hm.put("pageSize",pageSize);
			rslt = oafQuery.executeForList(fql.toString(),hm);
		}catch (OafException e) {
			logger.error(e);
		}
		for(Map<String,Object> map : rslt){
			// 电压等级
			Constant voltageLevelcst = ConstantRegistry.findConstant(VoltageLevel.TYPE);
			if(!ObjectUtils.isEmpty(map.get("voltageLevel"))){
				int voltageLevelValue = Integer.parseInt(map.get("voltageLevel").toString());
				String voltageLevelName = voltageLevelcst.getFieldValueByItemValue(voltageLevelValue, "name");
				map.put("voltageLevel", voltageLevelName);
			}
			// 故障相别
			Constant faultPhasecst = ConstantRegistry.findConstant(FaultPhase.TYPE);
			if(!ObjectUtils.isEmpty(map.get("faultPhaseId"))){
				int faultPhaseIdValue = Integer.parseInt(map.get("faultPhaseId").toString());
				String faultPhaseIdName = faultPhasecst.getFieldValueByItemValue(faultPhaseIdValue, "name");
				map.put("faultPhaseId", faultPhaseIdName);
			}
			//故障类别
			Constant faultCategorycst = ConstantRegistry.findConstant(FaultType.TYPE);
			if(!ObjectUtils.isEmpty(map.get("faultType"))){
				String faultCategoryValue = (String)map.get("faultType")  ;
				String faultCategoryName = faultCategorycst.getFieldValueByItemValue(faultCategoryValue, "name");
				map.put("faultType", faultCategoryName);
			}
		} 
		List<Map<String,Object>> list = new ArrayList<>();
		Map<String,Object> mapList = new HashMap<>();
		mapList.put("data", rslt);
		mapList.put("total",rsltCount);
		list.add(mapList);
		return list;
	}
	
	/**
	 * <p>描述： 获取站或者线的表格明细</p> 
	 * @param hashmap
	 * @return  List<Map<String, Object>>
	 * @author:赵云
	 * 2017年6月26日 上午10:15:28
	 */
	@RequestMapping("/gethotelGrid")
	public List<Map<String, Object>> gethotelGrid(@RequestParam HashMap<String, Object> hashmap) {
		HashMap<String, Object> hm = new HashMap<String, Object>();
		StringBuilder fql = new StringBuilder();
		int pageSize = Integer.parseInt((String)hashmap.get("pageSize"));//框架自带pageSize
		int pageIndex = Integer.parseInt((String)hashmap.get("pageIndex"));
		int skipCount = pageIndex*pageSize;
		fql.append("from t in sgcim30.assets.management.ElectricityEnsure ");
		fql.append("join t1 in sgcim30.assets.management.ElectricityEnsurePhase on t.id equals t1.belongTaskId ");
		fql.append("join t2 in sgcim30.assets.management.ElectricityEnsureHotel on t.id equals t2.belongTaskId ");
		hm.put("sysdate", new Date());
		if( hashmap.get("status")!=null && !"".equals(hashmap.get("status")) &&"进行中".equals(hashmap.get("status")) ){
			fql.append( " where   t.startTime <=:sysdate  && t.endTime >=:sysdate  && t1.startTime <=:sysdate  && t1.endTime>=:sysdate  ");
		}else{
			//准备中
			fql.append( " where   t.startTime >=:sysdate && t1.startTime <=:sysdate  && t1.endTime>=:sysdate  ");
		}
		if( hashmap.get("organizationid")!=null && !"".equals(hashmap.get("organizationid")) ){
			fql.append( " && t.dutyUnitID ==:organizationid ");
			hm.put("organizationid", hashmap.get("organizationid"));//两个参数都是形式参数；
		}
		if( hashmap.get("taskLevel")!=null && !"".equals(hashmap.get("taskLevel"))   ){
			fql.append( " &&  t1.ensureLevel  ==: taskLevel ");
			hm.put("taskLevel", hashmap.get("taskLevel"));//两个参数都是形式参数；
		}
		String sqlCount =  "  select sqlCount = t2.count() " ;
		sqlCount=fql+sqlCount;//计算总数sql /fql 
		fql.append( " select  t2  then skip :skipCount then take :pageSize  ");
		List<Map<String, Object>> rslt = null;
		Integer rsltCount = 0;
		try {
			rsltCount = (Integer)oafQuery.executeScalar(sqlCount , hm);	
			hm.put("skipCount",skipCount);
			hm.put("pageSize",pageSize);
			rslt = oafQuery.executeForList(fql.toString(),hm);
		} catch (OafException e) {
			logger.error(e);
		}
		List<Map<String,Object>> list = new ArrayList<>();
		Map<String,Object> mapList = new HashMap<>();
		mapList.put("data", rslt);
		mapList.put("total",rsltCount);
		list.add(mapList);
		return list;
	}
	
	/**
	 * <p>描述： 获取站或者线的表格明细</p> 
	 * @param hashmap
	 * @return  List<Map<String, Object>>
	 * @author:赵云
	 * 2017年6月26日 上午10:15:28
	 */
	@RequestMapping("/gethospitalGrid")
	public List<Map<String, Object>> gethospitalGrid(@RequestParam HashMap<String, Object> hashmap) {
		HashMap<String, Object> hm = new HashMap<String, Object>();
		StringBuilder fql = new StringBuilder();
		int pageSize = Integer.parseInt((String)hashmap.get("pageSize"));//框架自带pageSize
		int pageIndex = Integer.parseInt((String)hashmap.get("pageIndex"));
		int skipCount = pageIndex*pageSize;
		fql.append("from t in sgcim30.assets.management.ElectricityEnsure ");
		fql.append("join t1 in sgcim30.assets.management.ElectricityEnsurePhase on t.id equals t1.belongTaskId ");
		fql.append("join t2 in sgcim30.assets.management.ElectricityEnsureHospital on t.id equals t2.belongTaskId ");
		hm.put("sysdate", new Date());
		if( hashmap.get("status")!=null && !"".equals(hashmap.get("status")) &&"进行中".equals(hashmap.get("status")) ){
			fql.append( " where   t.startTime <=:sysdate  && t.endTime >=:sysdate  && t1.startTime <=:sysdate  && t1.endTime>=:sysdate  ");
		}else{
			//准备中
			fql.append( " where   t.startTime >=:sysdate  && t1.startTime <=:sysdate  && t1.endTime>=:sysdate  ");
		}
		if( hashmap.get("organizationid")!=null && !"".equals(hashmap.get("organizationid")) ){
			fql.append( " && t.dutyUnitID ==:organizationid ");
			hm.put("organizationid", hashmap.get("organizationid"));//两个参数都是形式参数；
		}
		if( hashmap.get("taskLevel")!=null && !"".equals(hashmap.get("taskLevel"))   ){
			fql.append( " &&  t1.ensureLevel  ==: taskLevel ");
			hm.put("taskLevel", hashmap.get("taskLevel"));//两个参数都是形式参数；
		}
		String sqlCount =  "  select sqlCount = t2.count() " ;
		sqlCount=fql+sqlCount;//计算总数sql /fql 
		fql.append( " select  t2  then skip :skipCount then take :pageSize  ");
		List<Map<String, Object>> rslt = null;
		Integer rsltCount = 0;
		try {
			rsltCount = (Integer)oafQuery.executeScalar(sqlCount , hm);	
			hm.put("skipCount",skipCount);
			hm.put("pageSize",pageSize);
			rslt = oafQuery.executeForList(fql.toString(),hm);
		}catch (OafException e) {
			logger.error(e);
		}
		List<Map<String,Object>> list = new ArrayList<>();
		Map<String,Object> mapList = new HashMap<>();
		mapList.put("data", rslt);
		mapList.put("total",rsltCount);
		list.add(mapList);
		return list;
	}
	/**
	 * <p>描述： 获取站或者线的表格明细</p> 
	 * @param hashmap
	 * @return  List<Map<String, Object>>
	 * @author:赵云
	 * 2017年6月26日 上午10:15:28
	 */
	@RequestMapping("/getpointGrid")
	public List<Map<String, Object>> getpointGrid(@RequestParam HashMap<String, Object> hashmap) {
		HashMap<String, Object> hm = new HashMap<String, Object>();
		StringBuilder fql = new StringBuilder();
		int pageSize = Integer.parseInt((String)hashmap.get("pageSize"));//框架自带pageSize
		int pageIndex = Integer.parseInt((String)hashmap.get("pageIndex"));
		int skipCount = pageIndex*pageSize;
		fql.append("from t in sgcim30.assets.management.ElectricityEnsure ");
		fql.append("join t1 in sgcim30.assets.management.ElectricityEnsurePhase on t.id equals t1.belongTaskId ");
		fql.append("join t2 in sgcim30.assets.management.ElectricityEnsureStation on t.id equals t2.belongTaskId ");
		hm.put("sysdate", new Date());
		if( hashmap.get("status")!=null && !"".equals(hashmap.get("status")) &&"进行中".equals(hashmap.get("status")) ){
			fql.append( " where   t.startTime <=:sysdate  && t.endTime >=:sysdate  && t1.startTime <=:sysdate  && t1.endTime>=:sysdate  ");
		}else{
			//准备中
			fql.append( " where   t.startTime >=:sysdate && t1.startTime <=:sysdate  && t1.endTime>=:sysdate  ");
		}
		if( hashmap.get("organizationid")!=null && !"".equals(hashmap.get("organizationid")) ){
			fql.append( " && t.dutyUnitID ==:organizationid ");
			hm.put("organizationid", hashmap.get("organizationid"));//两个参数都是形式参数；
		}
		if( hashmap.get("taskLevel")!=null && !"".equals(hashmap.get("taskLevel"))   ){
			fql.append( " &&  t1.ensureLevel  ==: taskLevel ");
			hm.put("taskLevel", hashmap.get("taskLevel"));//两个参数都是形式参数；
		}
		String sqlCount =  "  select sqlCount = t2.count() " ;
		sqlCount=fql+sqlCount;//计算总数sql /fql 
		fql.append( " select  t2  then skip :skipCount then take :pageSize  ");
		List<Map<String, Object>> rslt = null;
		Integer rsltCount = 0;
		try {
			rsltCount = (Integer)oafQuery.executeScalar(sqlCount , hm);	
			hm.put("skipCount",skipCount);
			hm.put("pageSize",pageSize);
			rslt = oafQuery.executeForList(fql.toString(),hm);
		} catch (OafException e) {
			logger.error(e);
		}
		List<Map<String,Object>> list = new ArrayList<>();
		Map<String,Object> mapList = new HashMap<>();
		mapList.put("data", rslt);
		mapList.put("total",rsltCount);
		list.add(mapList);
		return list;
	}
	
	
	/**
	 * <p>描述： 获取站或者线的表格明细</p> 
	 * @param hashmap
	 * @return  List<Map<String, Object>>
	 * @author:赵云
	 * 2017年6月26日 上午10:15:28
	 */
	@RequestMapping("/getcarGrid")
	public List<Map<String, Object>> getcarGrid(@RequestParam HashMap<String, Object> hashmap) {
		HashMap<String, Object> hm = new HashMap<String, Object>();
		StringBuilder fql = new StringBuilder();
		int pageSize = Integer.parseInt((String)hashmap.get("pageSize"));//框架自带pageSize
		int pageIndex = Integer.parseInt((String)hashmap.get("pageIndex"));
		int skipCount = pageIndex*pageSize;
		fql.append("from t in sgcim30.assets.management.ElectricityEnsure ");
		fql.append("join t1 in sgcim30.assets.management.ElectricityEnsurePhase on t.id equals t1.belongTaskId ");
		fql.append("join t2 in sgcim30.assets.management.ElectricityEnsureVehicle on t.id equals t2.belongTaskId ");
		hm.put("sysdate", new Date());
		if( hashmap.get("status")!=null && !"".equals(hashmap.get("status")) &&"进行中".equals(hashmap.get("status")) ){
			fql.append( " where   t.startTime <=:sysdate  && t.endTime >=:sysdate  && t1.startTime <=:sysdate  && t1.endTime>=:sysdate  ");
		}else{
			//准备中
			fql.append( " where   t.startTime >=:sysdate  && t1.startTime <=:sysdate  && t1.endTime>=:sysdate ");
		}
		if( hashmap.get("organizationid")!=null && !"".equals(hashmap.get("organizationid")) ){
			fql.append( " && t.dutyUnitID ==:organizationid ");
			hm.put("organizationid", hashmap.get("organizationid"));//两个参数都是形式参数；
		}
		if( hashmap.get("taskLevel")!=null && !"".equals(hashmap.get("taskLevel"))   ){
			fql.append( " &&  t1.ensureLevel  ==: taskLevel ");
			hm.put("taskLevel", hashmap.get("taskLevel"));//两个参数都是形式参数；
		}
		String sqlCount =  "  select sqlCount = t2.count() " ;
		sqlCount=fql+sqlCount;//计算总数sql /fql 
		fql.append( " select  t2  then skip :skipCount then take :pageSize  ");
		List<Map<String, Object>> rslt = null;
		Integer rsltCount = 0;
		try {
			rsltCount = (Integer)oafQuery.executeScalar(sqlCount , hm);	
			hm.put("skipCount",skipCount);
			hm.put("pageSize",pageSize);
			rslt = oafQuery.executeForList(fql.toString(),hm);
		}  catch (OafException e) {
			logger.error(e);
		}
		for(Map<String,Object> map : rslt){
			// 车辆类型
			Constant vehicleType = ConstantRegistry.findConstant(VehicleType.TYPE);
			if(!ObjectUtils.isEmpty(map.get("type"))){
				int vehicleTypeValue = Integer.parseInt(map.get("type").toString());
				String vehicleTypeName = vehicleType.getFieldValueByItemValue(vehicleTypeValue, "name");
				map.put("type", vehicleTypeName);
			}
		} 
		List<Map<String,Object>> list = new ArrayList<>();
		Map<String,Object> mapList = new HashMap<>();
		mapList.put("data", rslt);
		mapList.put("total",rsltCount);
		list.add(mapList);
		return list;
	}
	
	
	/**
	 * <p>描述： 获取站或者线的表格明细</p> 
	 * @param hashmap
	 * @return  List<Map<String, Object>>
	 * @author:赵云
	 * 2017年6月26日 上午10:15:28
	 */
	@RequestMapping("/getpersonGrid")
	public List<Map<String, Object>> getpersonGrid(@RequestParam HashMap<String, Object> hashmap) {
		HashMap<String, Object> hm = new HashMap<String, Object>();
		StringBuilder fql = new StringBuilder();
		int pageSize = Integer.parseInt((String)hashmap.get("pageSize"));//框架自带pageSize
		int pageIndex = Integer.parseInt((String)hashmap.get("pageIndex"));
		int skipCount = pageIndex*pageSize;
		fql.append("from t in sgcim30.assets.management.ElectricityEnsure ");
		fql.append("join t1 in sgcim30.assets.management.ElectricityEnsurePhase on t.id equals t1.belongTaskId ");
		fql.append("join t2 in sgcim30.assets.management.ElectricityEnsurePerson on t.id equals t2.belongTaskId ");
		hm.put("sysdate", new Date());
		if( hashmap.get("status")!=null && !"".equals(hashmap.get("status")) &&"进行中".equals(hashmap.get("status")) ){
			fql.append( " where   t.startTime <=:sysdate  && t.endTime >=:sysdate  && t1.startTime <=:sysdate  && t1.endTime>=:sysdate  ");
		}else{
			//准备中
			fql.append( " where   t.startTime >=:sysdate   && t1.startTime  >=:sysdate  ");
		}
		if( hashmap.get("organizationid")!=null && !"".equals(hashmap.get("organizationid")) ){
			fql.append( " && t.dutyUnitID ==:organizationid ");
			hm.put("organizationid", hashmap.get("organizationid"));//两个参数都是形式参数；
		}
		if( hashmap.get("taskLevel")!=null && !"".equals(hashmap.get("taskLevel"))   ){
			fql.append( " &&  t1.ensureLevel  ==: taskLevel ");
			hm.put("taskLevel", hashmap.get("taskLevel"));//两个参数都是形式参数；
		}
		String sqlCount =  "  select sqlCount = t2.count() " ;
		sqlCount=fql+sqlCount;//计算总数sql /fql 
		fql.append( " select  t2  then skip :skipCount then take :pageSize  ");
		List<Map<String, Object>> rslt = null;
		Integer rsltCount = 0;
		try {
			rsltCount = (Integer)oafQuery.executeScalar(sqlCount , hm);	
			hm.put("skipCount",skipCount);
			hm.put("pageSize",pageSize);
			rslt = oafQuery.executeForList(fql.toString(),hm);
		}catch (OafException e) {
			logger.error(e);
		}
		if(rslt != null ){
			for (Map<String, Object> map : rslt) {
				// 专业
				Constant typeconstant = ConstantRegistry.findConstant(RegulationsType.TYPE);
				if(!ObjectUtils.isEmpty(map.get("type"))){
					int typeValue = (int) map.get("type");
					String typeName = typeconstant.getFieldValueByItemValue(typeValue, "name");
					map.put("type", typeName);
				}
			}
		}
		List<Map<String,Object>> list = new ArrayList<>();
		Map<String,Object> mapList = new HashMap<>();
		mapList.put("data", rslt);
		mapList.put("total",rsltCount);
		list.add(mapList);
		return list;
	}
	
	/**
	 * <p>描述： 获取站或者线的表格明细</p> 
	 * @param hashmap
	 * @return  List<Map<String, Object>>
	 * @author:赵云
	 * 2017年6月26日 上午10:15:28
	 */
	@RequestMapping("/getLineorStationGrid")
	public List<Map<String, Object>> getLineorStationGrid(@RequestParam HashMap<String, Object> hashmap) {
		HashMap<String, Object> hm = new HashMap<String, Object>();
		StringBuilder fql = new StringBuilder();
		int skipCount = 0,pageSize=0;
		if(hashmap.get("pageSize") !=null){
			  pageSize = Integer.parseInt((String)hashmap.get("pageSize"));//框架自带pageSize
			int pageIndex = Integer.parseInt((String)hashmap.get("pageIndex"));
			  skipCount = pageIndex*pageSize;
		}
		fql.append("from t in sgcim30.assets.management.ElectricityEnsure ");
		fql.append("join t1 in sgcim30.assets.management.ElectricityEnsurePhase on t.id equals t1.belongTaskId ");
		fql.append("join t2 in sgcim30.assets.management.StationLineEnsure on t.id equals t2.belongTaskId ");
		fql.append("join t3 in sgcim30.assets.management.ElectricityEnsurePhase on t2.id equals t3.belongTaskId ");
		 hm.put("sysdate", new Date());
		if( hashmap.get("status")!=null && !"".equals(hashmap.get("status")) &&"进行中".equals(hashmap.get("status")) ){
			fql.append( " where   t.startTime <=:sysdate  && t.endTime >=:sysdate "
					+ " && t1.startTime <=:sysdate  && t1.endTime>=:sysdate "
					+ " && t3.startTime <=:sysdate  && t3.endTime>=:sysdate  ");
		}else{
			//准备中
			fql.append( " where   t.startTime >=:sysdate  &&  t1.startTime >=:sysdate ");
		}
//		fql.append( "  ");
//		fql.append( "  ");
		if( hashmap.get("organizationid")!=null && !"".equals(hashmap.get("organizationid")) ){
			fql.append( " && t.dutyUnitID ==:organizationid ");
			hm.put("organizationid", hashmap.get("organizationid"));//两个参数都是形式参数；
		}
		if( hashmap.get("taskLevel")!=null && !"".equals(hashmap.get("taskLevel"))   ){
			fql.append( " &&  t1.ensureLevel  ==: taskLevel ");
			hm.put("taskLevel", hashmap.get("taskLevel"));//两个参数都是形式参数；
		}
		if( hashmap.get("slensurelevel")!=null && !"".equals(hashmap.get("slensurelevel"))   ){
			fql.append( " &&  t3.ensureLevel  ==: slensurelevel ");
			hm.put("slensurelevel", hashmap.get("slensurelevel"));//两个参数都是形式参数；
		}
		if(hashmap.get("stationlinetype")!=null &&  !"".equals(hashmap.get("stationlinetype"))  ){
			fql.append( " &&  t2.stationLineType  ==: stationlinetype ");
			hm.put("stationlinetype", hashmap.get("stationlinetype"));//两个参数都是形式参数；
		}
		/*暂时只需要在线路grid中 ，显示所有电压等级的，和等级的，具体要看业务
		 * 
		 */
		if( hashmap.get("voltagelevel") !=null  ){
			fql.append( " &&  t2.voltageLevel  ==: voltagelevel ");
			hm.put("voltagelevel", hashmap.get("voltagelevel"));
		}//两个参数都是形式参数；
		String sqlCount="";
		if(hashmap.get("pageSize") !=null){
			
			 sqlCount =  "  select sqlCount = t2.count() " ;
			sqlCount=fql+sqlCount;//计算总数sql /fql 
			fql.append( " select ensureLevelnow= t3.ensureLevel, t2  then skip :skipCount then take :pageSize  ");
		}else{
			fql.append("  select t2 ");
		}
		List<Map<String, Object>> rslt = null;
		Integer rsltCount = 0;
		try {
			if(hashmap.get("pageSize") !=null){
				rsltCount = (Integer)oafQuery.executeScalar(sqlCount , hm);	
				hm.put("skipCount",skipCount);
				hm.put("pageSize",pageSize);
				rslt = oafQuery.executeForList(fql.toString(),hm);
				
			}else{
				rslt = oafQuery.executeForList(fql.toString(),hm);
			}
		} catch (OafException e) {
			logger.error(e);
		}
		if(rslt != null){
			Constant PowerLeverCst = ConstantRegistry.findConstant(PowerLever.TYPE);
			for (Map<String, Object> maptemp : rslt) {
				 
				if (maptemp.get("ensureLevel") != null) {
					int ensureLevelValue =  (int) maptemp.get("ensureLevel") ;
					String ensureLevelName = "";
					ensureLevelName = PowerLeverCst .getFieldValueByItemValue(ensureLevelValue, PowerLever.NAME);
					maptemp.put("ensureLevel", ensureLevelName);
				}
				if (maptemp.get("ensureLevelnow") != null) {
					int ensureLevelValue =  (int) maptemp.get("ensureLevelnow") ;
					String ensureLevelName = "";
					ensureLevelName = PowerLeverCst .getFieldValueByItemValue(ensureLevelValue, PowerLever.NAME);
					maptemp.put("ensureLevelnow", ensureLevelName);
				}
				Constant voltageLevelconstant = ConstantRegistry.findConstant(VoltageLevel.TYPE);
				if (maptemp.get("voltageLevel") != null) {
					int voltageLevelValue=((Integer) maptemp.get("voltageLevel")).intValue();
					String voltagelevelName=voltageLevelconstant.getFieldValueByItemValue(voltageLevelValue, "name");
					maptemp.put("voltageLevel", voltagelevelName);
				}
					
			}
		}
		List<Map<String,Object>> list = new ArrayList<>();
		Map<String,Object> mapList = new HashMap<>();
		mapList.put("data", rslt);
		mapList.put("total",rsltCount);
		list.add(mapList);
		return list;
	}
	/**
	 * <p>描述： 获取用户表格 </p> 
	 * @param hashmap
	 * @return  List<Map<String, Object>>
	 * @author:赵云
	 * 2017年6月26日 上午10:15:28
	 */
	 
	@RequestMapping("/getUsergrid")
	public List<Map<String, Object>> getUsergrid( @RequestParam HashMap<String, Object> hashmap){
		HashMap<String, Object> hm = new HashMap<String, Object>();
		StringBuilder fql = new StringBuilder();
		int pageSize = Integer.parseInt((String)hashmap.get("pageSize"));//框架自带pageSize
		int pageIndex = Integer.parseInt((String)hashmap.get("pageIndex"));
		int skipCount = pageIndex*pageSize;
		fql.append("from t in sgcim30.assets.management.ElectricityEnsure ");
		fql.append("join t1 in sgcim30.assets.management.ElectricityEnsurePhase on t.id equals t1.belongTaskId ");
		fql.append("join t2 in sgcim30.assets.management.ElectricityEnsureUser on t.id equals t2.belongTaskId ");
		fql.append("join t3 in sgcim30.assets.management.ElectricityEnsurePhase on t2.id equals t3.belongTaskId ");
		 hm.put("sysdate", new Date());
		if( hashmap.get("status")!=null && !"".equals(hashmap.get("status")) &&"进行中".equals(hashmap.get("status")) ){
			fql.append( " where   t.startTime <=:sysdate  && t.endTime >=:sysdate  ");
		}else{
			//准备中
			fql.append( " where   t.startTime >=:sysdate    ");
		}
		fql.append(" && t1.startTime <=:sysdate  &&  t1.endTime >=:sysdate");
		fql.append(" && t3.startTime <=:sysdate  &&  t3.endTime >=:sysdate");
		if( hashmap.get("organizationid")!=null && !"".equals(hashmap.get("organizationid")) ){
			fql.append( " && t.dutyUnitID ==:organizationid ");
			hm.put("organizationid", hashmap.get("organizationid"));//两个参数都是形式参数；
		}
		if( hashmap.get("taskLevel")!=null && !"".equals(hashmap.get("taskLevel"))   ){
			fql.append( " &&  t1.ensureLevel  ==: taskLevel ");
			hm.put("taskLevel", hashmap.get("taskLevel"));//两个参数都是形式参数；
		} 
		if( hashmap.get("userlevel")!=null && !"".equals(hashmap.get("userlevel"))   ){
			fql.append( " &&  t3.ensureLevel  ==: userlevel ");
			hm.put("userlevel", hashmap.get("userlevel"));//两个参数都是形式参数；
		} 
		String sqlCount =  "  select sqlCount = t2.count() " ;
		sqlCount=fql+sqlCount;//计算总数sql /fql 
		fql.append(" select t2  then skip :skipCount then take :pageSize ");
		List<Map<String, Object>> rslt = null;
		Integer rsltCount = 0;
		try {
			rsltCount = (Integer)oafQuery.executeScalar(sqlCount , hm);	
			hm.put("skipCount",skipCount);
			hm.put("pageSize",pageSize);
			rslt = oafQuery.executeForList(fql.toString(),hm);
		}  catch (OafException e) {
			logger.error(e);
		}
		List<Map<String,Object>> list = new ArrayList<>();
		Map<String,Object> mapList = new HashMap<>();
		mapList.put("data", rslt);
		mapList.put("total",rsltCount);
		list.add(mapList);
		return list;
		
	}  

	
	
	/**
	 * <p>描述：定位获取最大电压等级最高级别的设备id </p> 
	 * @param hashmap
	 * @return  List<Map<String, Object>>
	 * @author:赵云
	 * 2017年6月26日 上午10:15:28
	 */
	@RequestCache
	@RequestMapping("/getmaxdeviceid")
	public List<Map<String, Object>> getmaxdeviceid( @RequestParam HashMap<String, Object> hashmap) {
		Map<String, Object> hm = new HashMap<String, Object> ();
		hm.put(" belongtaskid ", hashmap.get("belongtaskid") );
		StringBuffer sbDefectState = new StringBuffer();
		 sbDefectState.append(" from t in  sgcim30.assets.management.StationLineEnsure  ");
		 sbDefectState.append("  join t1 in sgcim30.assets.management.ElectricityEnsure  on t1.id equals t.belongTaskId   ");
		 sbDefectState.append("  join t2 in ( from t3 in sgcim30.assets.management.StationLineEnsure  select ensureLevel = t3.ensureLevel.min()  )  on t2.ensureLevel equals t.ensureLevel   ");
		 sbDefectState.append("  join t5 in  ( from t4 in sgcim30.assets.management.StationLineEnsure  select voltageLevel = t4.voltageLevel.max()  ) on t5.voltageLevel equals t.voltageLevel   ");
		 sbDefectState.append( " where  t1.id  ==: belongtaskid   &&  t.stationLineType == '02'   ");
		 sbDefectState.append( "  select  deviceid = t.deviceID ");
		 List<Map<String, Object>> rslt  = null; 
		 try {
			rslt = oafQuery.executeForList(sbDefectState.toString() ,hm);
		 } catch (OafException e) {
			logger.error(e);
		 }
		 return rslt;
	}
	
	/**
	 * <p>描述：获取最高等级用户的定位信息</p> 
	 * @param hashmap
	 * @return  List<Map<String, Object>>
	 * @author:赵云
	 * 2017年6月26日 上午10:15:28
	 */
	@RequestCache
	@RequestMapping("/getmaxleveluser")
	public List<Map<String, Object>> getmaxleveluser(@RequestParam HashMap<String, Object> hashmap) {
		Map<String, Object> hm = new HashMap<String, Object> ();
		hm.put("belongtaskid", (String)hashmap.get("belongtaskid") );
		StringBuffer sbDefectState = new StringBuffer();
		StringBuffer sbDefectStatemaxleveluser = new StringBuffer();
		sbDefectState.append( " from t4  in  sgcim30.assets.management.ElectricityEnsureUser  select ensureLevel = t4.ensureLevel.min() ");
		List<Map<String, Object>> rslt  = null; 
		try {
			rslt = oafQuery.executeForList(sbDefectState.toString() );
		} catch (OafException e) {
			logger.error(e);
		}
		sbDefectStatemaxleveluser.append(" from t in  sgcim30.assets.management.ElectricityEnsure  ");
		sbDefectStatemaxleveluser.append("  join t3 in sgcim30.assets.management.ElectricityEnsureUser    on t.id equals t3.belongTaskId  ");
		sbDefectStatemaxleveluser.append( "  where  t.id  ==:belongtaskid    && t3.geographyLocation  !=null ");
		if( rslt != null   ){
			   hm.put("ensureLevel",(int) rslt.get(0).get("ensureLevel") ) ;
			   sbDefectStatemaxleveluser.append( " &&  t3.ensureLevel  ==:ensureLevel ");
		}
		sbDefectStatemaxleveluser.append( "  select  location = t3.geographyLocation "); 
		try {
			rslt = oafQuery.executeForList(sbDefectStatemaxleveluser.toString(),hm   );
		} catch (OafException e) {
			logger.error(e);
		}
		return rslt ;
	}
	/**
	 * <p>描述： 统计缺陷已消缺个数</p> 
	 * @param hashmap
	 * @return  List<Map<String, Object>>
	 * @author:赵云
	 * 2017年6月26日 上午10:15:28
	 */
	@RequestCache
	@RequestMapping("/getcount_DefectState")
	public List<Map<String, Object>> getcount_DefectState(@RequestParam HashMap<String, Object> hashmap){
		 /* 		select  '已消缺' flag,   count（1）c from (
		  * 		select t5.id from
		  * 		cim.ElectricityEnsure t
				    inner join cim.ElectricityEnsurePhase t2  on t.id = t2.belongtaskid
				    inner join cim.StationLineEnsure t4       on t.id = t4.belongtaskid
				    inner join cim.defectstate t5             on t4.stationlineid = t5.powerstationid
				    where   t5.CONFIRMSTATUS = 4 or  t5.CONFIRMSTATUS =5   -- and t.startTime <= sysdate and t.endtime >= sysdate  and t1.startTime <= sysdate and t1.endtime >= sysdate   --and t.dutyunitid='16364345747547'  and t1.ensurelevel =1
				    and  t5.correctivedate >=  t.starttime and  t5.correctivedate< t.endtime  group by  t5.id)  a
				    
				    union  all
				    
				    select  '未消缺' flag,   count（1） c from (select t5.id from  cim.ElectricityEnsure t
				    inner join cim.ElectricityEnsurePhase t2  on t.id = t2.belongtaskid
				    inner join cim.StationLineEnsure t4       on t.id = t4.belongtaskid
				    inner join cim.defectstate t5             on t4.stationlineid = t5.powerstationid
				    where   t5.CONFIRMSTATUS = 1 or  t5.CONFIRMSTATUS =2 or  t5.CONFIRMSTATUS =3     group by  t5.id  -- and t.startTime <= sysdate and t.endtime >= sysdate  and t1.startTime <= sysdate and t1.endtime >= sysdate   --and t.dutyunitid='16364345747547'  and t1.ensurelevel =1
				    ) b*/
		Map<String, Object> hm = new HashMap<String, Object> ();
		hm.put("sysdate", new Date ());
		StringBuffer sbDefectState = new StringBuffer ("from  a in  (");
		sbDefectState.append(" from t in  sgcim30.assets.management.ElectricityEnsure  ");
		sbDefectState.append("  join t2 in sgcim30.assets.management.ElectricityEnsurePhase  on t.id equals t2.belongTaskId   ");
		sbDefectState.append("  join t4 in  sgcim30.assets.management.StationLineEnsure on t.id equals t4.belongTaskId ");
		sbDefectState.append("  join t6 in sgcim30.assets.management.ElectricityEnsurePhase  on t4.id equals t6.belongTaskId   ");
		sbDefectState.append("  join t5 in  sgcim30.assets.management.DefectState   on t4.stationLineID equals t5.powerStationId ");
		sbDefectState.append(" where (t5.confirmStatus ==4 ||  t5.confirmStatus ==5 )  ");
		if( hashmap.get("status")!=null && !"".equals(hashmap.get("status")) &&"进行中".equals(hashmap.get("status")) ){
			sbDefectState.append( "   &&  t.startTime <=:sysdate  && t.endTime >=:sysdate    ");
		}else{
			//准备中
			sbDefectState.append( "  &&  t.startTime >=:sysdate   ");
		}
		sbDefectState.append( "   &&  t6.startTime <=:sysdate  && t6.endTime >=:sysdate  && t2.startTime <=:sysdate  && t2.endTime>=:sysdate  ");
		if( hashmap.get("organizationid")!=null && !"".equals(hashmap.get("organizationid")) ){
			sbDefectState.append( " && t.dutyUnitID ==:organizationid ");
			hm.put("organizationid", hashmap.get("organizationid"));//两个参数都是形式参数；
		}
		if( hashmap.get("taskLevel")!=null && !"".equals(hashmap.get("taskLevel"))   ){
			sbDefectState.append( " &&  t2.ensureLevel  ==: taskLevel ");
			hm.put("taskLevel", hashmap.get("taskLevel"));//两个参数都是形式参数；
		} 
		sbDefectState.append("  &&  t5.correctiveDate >=  t.startTime && t5.correctiveDate< t.endTime  groupby  t5.id select t5.id");
		sbDefectState.append( ")  select   flag ='已消缺',   c = a.count( ) "); 
		sbDefectState.append(" union  all ");
		sbDefectState.append("from  b in  ( ");
		sbDefectState.append(" from t in  sgcim30.assets.management.ElectricityEnsure  ");
		sbDefectState.append("  join t2 in sgcim30.assets.management.ElectricityEnsurePhase  on t.id equals t2.belongTaskId   ");
		sbDefectState.append("  join t4 in  sgcim30.assets.management.StationLineEnsure on t.id equals t4.belongTaskId ");
		sbDefectState.append("  join t6 in sgcim30.assets.management.ElectricityEnsurePhase  on t4.id equals t6.belongTaskId   ");
		sbDefectState.append("  join t5 in  sgcim30.assets.management.DefectState   on t4.stationLineID equals t5.powerStationId ");
		sbDefectState.append(" where (t5.confirmStatus ==1 ||  t5.confirmStatus ==2  ||  t5.confirmStatus ==3 ) ");
		if( hashmap.get("status")!=null && !"".equals(hashmap.get("status")) &&"进行中".equals(hashmap.get("status")) ){
			sbDefectState.append( "  &&   t.startTime <=:sysdate  && t.endTime >=:sysdate    ");
		}else{
			//准备中
			sbDefectState.append( "  &&  t.startTime >=:sysdate    ");
		}
		sbDefectState.append( "  &&   t6.startTime <=:sysdate  && t6.endTime >=:sysdate  && t2.startTime <=:sysdate  && t2.endTime>=:sysdate  ");
		if( hashmap.get("organizationid")!=null && !"".equals(hashmap.get("organizationid")) ){
			sbDefectState.append( " && t.dutyUnitID ==:organizationid ");
		}
		if( hashmap.get("taskLevel")!=null && !"".equals(hashmap.get("taskLevel"))   ){
			sbDefectState.append( " &&  t2.ensureLevel  ==: taskLevel ");
		} 
		sbDefectState.append("  groupby  t5.id select t5.id ");
		sbDefectState.append( ")  select  flag ='已消缺' ,    c = b.count( ) "); 
		List<Map<String, Object>> rslt  = null;
		try {
			rslt = oafQuery.executeForList(sbDefectState.toString(),hm);
		} catch (OafException e) {
			logger.error(e);
		}
		return rslt ;  
	}  

	/**
	 * <p>描述： 故障异常统计已恢复故障总数</p> 
	 * @param hashmap
	 * @return List<Map<String, Object>>
	 * @author:赵云
	 * 2017年6月23日 上午10:18:56
	 */
	@RequestCache
	@RequestMapping("/getcount_faultTrip")
	public List<Map<String, Object>> getcount_faultTrip(@RequestParam HashMap<String, Object> hashmap) {
		
		Map<String, Object> hm = new HashMap<String, Object> ();
		hm.put("sysdate", new Date ());
		StringBuffer sbDefectState = new StringBuffer ("from  a in  (");
		sbDefectState.append(" from t in  sgcim30.assets.management.ElectricityEnsure  ");
		sbDefectState.append("  join t2 in sgcim30.assets.management.ElectricityEnsurePhase  on t.id equals t2.belongTaskId   ");
		sbDefectState.append("  join t4 in  sgcim30.assets.management.StationLineEnsure on t.id equals t4.belongTaskId ");
		sbDefectState.append("  join t6 in sgcim30.assets.management.ElectricityEnsurePhase  on t4.id equals t6.belongTaskId   ");
		sbDefectState.append("  join t5 in  sgcim30.assets.management.FaultTrip   on t4.stationLineID equals t5.stationOrLineId ");
		sbDefectState.append(" where ( t5.confirmStatus ==3 ||  t5.confirmStatus ==4  ) ");
		  
		if( hashmap.get("status")!=null && !"".equals(hashmap.get("status")) &&"进行中".equals(hashmap.get("status")) ){
			sbDefectState.append( "   &&  t.startTime <=:sysdate  && t.endTime >=:sysdate   &&  t6.startTime <=:sysdate  && t6.endTime >=:sysdate  && t2.startTime <=:sysdate  && t2.endTime>=:sysdate    ");
		}else{
			//准备中
			sbDefectState.append( "  &&  t.startTime >=:sysdate   ");
		}
//		sbDefectState.append( "   &&  t6.startTime <=:sysdate  && t6.endTime >=:sysdate  && t2.startTime <=:sysdate  && t2.endTime>=:sysdate  ");
		if( hashmap.get("organizationid")!=null && !"".equals(hashmap.get("organizationid")) ){
			sbDefectState.append( " && t.dutyUnitID ==:organizationid ");
			hm.put("organizationid", hashmap.get("organizationid"));//两个参数都是形式参数；
		}
		if( hashmap.get("taskLevel")!=null && !"".equals(hashmap.get("taskLevel"))   ){
			sbDefectState.append( " &&  t2.ensureLevel  ==: taskLevel ");
			hm.put("taskLevel", hashmap.get("taskLevel"));//两个参数都是形式参数；
		} 
		sbDefectState.append("  &&  t5.recoveryDate >=  t.startTime && t5.recoveryDate < t.endTime  groupby  t5.id select t5.id");
		sbDefectState.append( ")  select   flag ='已恢复',   c = a.count( ) "); 
		sbDefectState.append(" union  all ");
		sbDefectState.append("from  b in  ( ");
		sbDefectState.append(" from t in  sgcim30.assets.management.ElectricityEnsure  ");
		sbDefectState.append("  join t2 in sgcim30.assets.management.ElectricityEnsurePhase  on t.id equals t2.belongTaskId   ");
		sbDefectState.append("  join t4 in  sgcim30.assets.management.StationLineEnsure on t.id equals t4.belongTaskId ");
		sbDefectState.append("  join t6 in sgcim30.assets.management.ElectricityEnsurePhase  on t4.id equals t6.belongTaskId   ");
		sbDefectState.append("  join t5 in  sgcim30.assets.management.FaultTrip   on t4.stationLineID equals t5.stationOrLineId ");
		sbDefectState.append(" where ( t5.confirmStatus ==1 ||  t5.confirmStatus ==2 )  ");
		if( hashmap.get("status")!=null && !"".equals(hashmap.get("status")) &&"进行中".equals(hashmap.get("status")) ){
			sbDefectState.append( "  &&   t.startTime <=:sysdate  && t.endTime >=:sysdate   ");
		}else{
			//准备中
			sbDefectState.append( "  &&  t.startTime >=:sysdate    ");
		}
		sbDefectState.append( "  &&   t6.startTime <=:sysdate  && t6.endTime >=:sysdate  && t2.startTime <=:sysdate  && t2.endTime>=:sysdate  ");
		if( hashmap.get("organizationid")!=null && !"".equals(hashmap.get("organizationid")) ){
			sbDefectState.append( " && t.dutyUnitID ==:organizationid ");
		}
		if( hashmap.get("taskLevel")!=null && !"".equals(hashmap.get("taskLevel"))   ){
			sbDefectState.append( " &&  t2.ensureLevel  ==: taskLevel ");
		} 
		sbDefectState.append("  groupby  t5.id select t5.id ");
		sbDefectState.append( ")  select  flag ='处理中' ,    c = b.count( ) "); 
		List<Map<String, Object>> rslt  = null;
		try {
			rslt = oafQuery.executeForList(sbDefectState.toString(),hm);
		} catch (OafException e) {
			logger.error(e);
		}
		return rslt ; 
	}

	/**
	 * <p>描述：气象环境数据统计</p> 
	 * @param hashmap
	 * @return list
	 * @author:赵云
	 * 2017年6月12日 下午2:46:50
	 */
	@RequestMapping("/queryWeatherCountResult")
	@RequestCache
	public List<Map<String, Object>> queryWeatherCountResult(@RequestParam HashMap<String, Object> hashmap){
		Calendar cal = Calendar.getInstance();
		Date endTime = cal.getTime();
		cal.add(Calendar.DAY_OF_MONTH, -1);
		Date startTime = cal.getTime();
		Map<String, Object> param = new LinkedHashMap<String, Object>(); // 保存过滤条件
		StringBuilder sql = new StringBuilder("from t in sgcim30.assets.management.MeteorologicalEnvironment where");
		sql.append(" t.startTime >=:startTime && t.startTime <=:endTime");
		param.put("startTime", startTime);
		param.put("endTime", endTime);
		sql.append(" && (t.confirmStatus == '1' || t.confirmStatus == '2')");
		if("weather".equals(hashmap.get("type"))){
			sql.append(" && (t.meteorologicalType =='1' || t.meteorologicalType =='2' || t.meteorologicalType =='3' || t.meteorologicalType =='4' ||");
			sql.append(" t.meteorologicalType =='5' || t.meteorologicalType =='6' || t.meteorologicalType =='7')");
		}else if("environment".equals(hashmap.get("type"))){
			sql.append(" && (t.meteorologicalType =='8' || t.meteorologicalType =='9' || t.meteorologicalType =='10' || t.meteorologicalType =='11')");
		}
		sql.append(" select data=t.count()");
		List<Map<String, Object>> rslt = null;
		try {
			rslt = oafQuery.executeForList(sql.toString(), param);
		} catch (OafException e) {
			logger.error("气象环境数据统计异常",e);
		}
		return rslt;
	}
	
	/**
	 * <p>描述：电网风险数据统计</p> 
	 * @param hashmap
	 * @return List<Map<String, Object>>
	 * @author:赵云
	 * 2017年3月9日 下午4:02:25
	 */
	@RequestMapping("/queryGridRiskCountResult")
	@RequestCache
	public List<Map<String, Object>> queryGridRiskCountResult(@RequestParam HashMap<String, Object> hashmap){
		Calendar cal = Calendar.getInstance();
		Date time = cal.getTime();
		Map<String, Object> param = new LinkedHashMap<String, Object>();
		StringBuilder sql = new StringBuilder("from t in sgcim30.assets.management.SafRisGridWarning where ");
		sql.append(" t.publishDate >=:startTime && t.RemoveDate <=:endTime");
		param.put("startTime", time);
		param.put("endTime", time);
		sql.append(" && t.confirmStatus == '2' select data=t.count()");
		List<Map<String, Object>> rslt = null;
		try {
			rslt = oafQuery.executeForList(sql.toString(), param);
		} catch (OafException e) {
			logger.error("电网风险数据统计异常",e);
		}
		return rslt;
	}
	 
	/**
	 * <p>描述：统计任务状态等级个数</p> 
	 * @return List<Map<String,Object>>
	 * @author:赵云
	 * 2017年6月19日 上午9:13:38
	 * @param hashmap 
	 */
	@RequestMapping("/getmissionStatus")
	public List<Map<String,Object>> getmissionStatus(@RequestParam HashMap<String, Object> hashmap){
		HashMap<String, Object> hm = new HashMap<String, Object>();
		hm.put("sysdate", new Date());
		StringBuilder sql = new StringBuilder(" from t in sgcim30.assets.management.ElectricityEnsure ");
		sql.append(" join t1 in sgcim30.assets.management.ElectricityEnsurePhase on t.id equals t1.belongTaskId   ");
		sql.append(" where   t.startTime <=:sysdate  && t.endTime >=:sysdate  ");
		sql.append("  &&   t1.startTime <=:sysdate  && t1.endTime >=:sysdate  ");
		if( hashmap.get("organizationid")!=null && !"".equals(hashmap.get("organizationid")) ){
			sql.append( "  && t.dutyUnitID ==:organizationid ");
			hm.put("organizationid", hashmap.get("organizationid"));//两个参数都是形式参数；
		}
		sql.append( "select  a= '进行中',  cnumber1 =t.count()");
		sql.append( " union ");
		sql.append( " from t2 in sgcim30.assets.management.ElectricityEnsure "
				+ "  join t3 in sgcim30.assets.management.ElectricityEnsurePhase on t2.id equals t3.belongTaskId  "
				+ "where    t2.startTime>:sysdate &&  t3.startTime>:sysdate ");
		if( hashmap.get("organizationid")!=null && !"".equals(hashmap.get("organizationid")) ){
			sql.append( "  && t2.dutyUnitID ==:organizationid ");
			hm.put("organizationid", hashmap.get("organizationid"));//两个参数都是形式参数；
		}
		sql.append(  " select   a='准备中', cnumber2 =t2.count()");
		List<Map<String, Object>> rslt = null;
		try {
			rslt = oafQuery.executeForList(sql.toString(),hm);
		} catch (OafException e) {
			logger.error(e);
		}
		return rslt;
	}
	
	/**
	 * <p>描述：统计保电任务等级</p> 
	 * @param hashmap
	 * @return List<Map<String, Object>>
	 * @author:赵云
	 * 2017年6月19日 上午9:13:43
	 */
	@RequestMapping("/getTaskLevel")
	public List<Map<String, Object>> getTaskLevel(@RequestParam HashMap<String, Object> hashmap){
		HashMap<String, Object> hm = new HashMap<String, Object>();
		hm.put("sysdate", new Date());
		StringBuilder sql = new StringBuilder("from t in sgcim30.assets.management.ElectricityEnsure join ");
		sql.append(" t1 in sgcim30.assets.management.ElectricityEnsurePhase  on t.id  equals t1.belongTaskId ");
		if( hashmap.get("status")!=null && !"".equals(hashmap.get("status")) &&"进行中".equals(hashmap.get("status")) ){
			sql.append( " where   t.startTime <=:sysdate  && t.endTime >=:sysdate  && t1.startTime <=:sysdate  && t1.endTime>=:sysdate  ");
		}else{
			//准备中
			sql.append( " where   t.startTime >=:sysdate  &&  t1.startTime >=:sysdate ");
		}
		if( hashmap.get("organizationid")!=null && !"".equals(hashmap.get("organizationid")) ){
			sql.append( " && t.dutyUnitID ==:organizationid");
			hm.put("organizationid", hashmap.get("organizationid"));//两个参数都是形式参数；
		}
		sql.append( " groupby t1.ensureLevel select t1.ensureLevel, count= t1.count() ");
		List<Map<String, Object>> rslt = null;
		try {
			rslt = oafQuery.executeForList(sql.toString(),hm);
		} catch (OafException e) {
			logger.error(e);
		}
		return rslt;
	}

	
	/**
	 * <p>描述：统计保电用户等级</p> 
	 * @param hashmap
	 * @return List<Map<String, Object>>
	 * @author:赵云
	 * 2017年6月19日 上午10:32:11
	 */
	@RequestMapping("/getUserState")
	public List<Map<String, Object>> getUserState(@RequestParam HashMap<String, Object> hashmap) {
//	select  count(t1.ensurelevel) ,t1.ensurelevel 
//		from cim.ElectricityEnsure t 
//		join  cim.ElectricityEnsureUser  t2 on t.id = t2.belongtaskid
// 	join   cim.ElectricityEnsurePhase  t1 on t2.id= t1.belongtaskid  
//   where t1.startTime <= sysdate and t1.endtime >= sysdate 
//   and t.startTime <= sysdate and t.endtime >= sysdate   group by t1.ensurelevel
		
		HashMap<String, Object> hm = new HashMap<String, Object>();
		StringBuilder fql = new StringBuilder();
		fql.append("from t5 in  ( ");

		fql.append(" from t6 in  sgcim30.assets.management.ElectricityEnsureUser select  ensureLevel=1  union  ");
		fql.append(" from t7 in  sgcim30.assets.management.ElectricityEnsureUser select  ensureLevel=2  union  ");
		fql.append(" from t8 in  sgcim30.assets.management.ElectricityEnsureUser select  ensureLevel=3  union  ");
		fql.append(" from t9 in  sgcim30.assets.management.ElectricityEnsureUser select  ensureLevel=4   ");
		fql.append(") left join t4 in ( ");
		fql.append("from t in sgcim30.assets.management.ElectricityEnsure ");
		fql.append("join t1 in sgcim30.assets.management.ElectricityEnsurePhase on t.id equals t1.belongTaskId ");
		fql.append("join t2 in sgcim30.assets.management.ElectricityEnsureUser on t.id equals t2.belongTaskId ");
		fql.append("join t3 in sgcim30.assets.management.ElectricityEnsurePhase on t2.id equals t3.belongTaskId ");
		 hm.put("sysdate", new Date());
		if( hashmap.get("status")!=null && !"".equals(hashmap.get("status")) &&"进行中".equals(hashmap.get("status")) ){
			fql.append( " where   t.startTime <=:sysdate  && t.endTime >=:sysdate  && t1.startTime <=:sysdate  && t1.endTime>=:sysdate "
					+ "  && t3.startTime <=:sysdate  && t3.endTime >=:sysdate  ");
		}else{
			//准备中
			fql.append( " where   t.startTime >=:sysdate  &&  t1.startTime >=:sysdate &&  t3.startTime >=:sysdate ");
		}
		if( hashmap.get("organizationid")!=null && !"".equals(hashmap.get("organizationid")) ){
			fql.append( " && t.dutyUnitID ==:organizationid ");
			hm.put("organizationid", hashmap.get("organizationid"));//两个参数都是形式参数；
		}
		if( hashmap.get("taskLevel")!=null && !"".equals(hashmap.get("taskLevel"))   ){
			fql.append( " &&  t1.ensureLevel  ==: taskLevel ");
			hm.put("taskLevel", hashmap.get("taskLevel"));//两个参数都是形式参数；
		} 
		fql.append("groupby t3.ensureLevel ");
		fql.append("select t3.ensureLevel, cnt = t3.ensureLevel.count() ");
		  
		fql.append(" )  on t5.ensureLevel equals t4.ensureLevel ");
		fql.append("  orderby t5.ensureLevel   select t5.ensureLevel ,t4.cnt ");
		List<Map<String, Object>> rslt = null;
		try {
			rslt = oafQuery.executeForList(fql.toString(),hm);
		} catch (OafException e) {
			logger.error(e);
		}
		return rslt;
	}
	
	
	/**
	 * <p>描述： 统计线路图标，组装数据</p> 
	 * @param hashmap
	 * @return List<Map<String, Object>> 
	 * @author:赵云
	 * 2017年6月19日 下午2:38:15
	 *  
	 */
	@RequestMapping("/getLineChart")
	public  Map<String,List<?>> getLineChart(@RequestParam HashMap<String, Object> hashmap) {
		HashMap<String, Object> hm = new HashMap<String, Object>();
		StringBuilder fql = new StringBuilder();
		StringBuilder fqlvoltagelevel = new StringBuilder();
		StringBuilder fqlensurelevel = new StringBuilder();
		fql.append("from t in sgcim30.assets.management.ElectricityEnsure ");
		fql.append("join t1 in sgcim30.assets.management.ElectricityEnsurePhase on t.id equals t1.belongTaskId ");
		fql.append("join t2 in sgcim30.assets.management.StationLineEnsure on t.id equals t2.belongTaskId ");
		fql.append("join t3 in sgcim30.assets.management.ElectricityEnsurePhase on t2.id equals t3.belongTaskId ");
		 hm.put("sysdate", new Date());
		if( hashmap.get("status")!=null && !"".equals(hashmap.get("status")) &&"进行中".equals(hashmap.get("status")) ){
			fql.append( " where   t.startTime <=:sysdate  && t.endTime >=:sysdate   ");
		}else{
			//准备中
			fql.append( " where   t.startTime >=:sysdate  &&  t1.startTime >=:sysdate ");
		}
		fql.append( " &&  t1.startTime <=:sysdate  && t1.endTime >=:sysdate   ");
		fql.append( " &&  t3.startTime <=:sysdate  && t3.endTime >=:sysdate   ");
		
		if( hashmap.get("organizationid")!=null && !"".equals(hashmap.get("organizationid")) ){
			fql.append( " && t.dutyUnitID ==:organizationid ");
			hm.put("organizationid", hashmap.get("organizationid"));//两个参数都是形式参数；
		}
		if( hashmap.get("taskLevel")!=null && !"".equals(hashmap.get("taskLevel"))   ){
			fql.append( " &&  t1.ensureLevel  == :taskLevel ");
			hm.put("taskLevel", hashmap.get("taskLevel"));//两个参数都是形式参数；
		} 
		fql.append(" && t2.stationLineType == :stationLineType "); // 电站
		hm.put("stationLineType", hashmap.get("stationLineType"));
		fqlvoltagelevel.append( fql ).append(" groupby t2.voltageLevel select t2.voltageLevel   ");
		fqlensurelevel.append(fql).append(" && t3.ensureLevel == :taskLeveL ");
		fqlensurelevel.append(" groupby t2.voltageLevel, t2.id");
		fqlensurelevel.append(" select t2.voltageLevel,t2.id, count = t2.count() ");
		List<Map<String, Object>> rsltVol = null;
		List<Map<String, Object>> rslt1 = null;
		List<Map<String, Object>> rslt2 = null;
		List<Map<String, Object>> rslt3 = null;
		List<Map<String, Object>> rslt4 = null;
		try {
			rsltVol = oafQuery.executeForList(fqlvoltagelevel.toString(), hm); //[{voltageLevel=35},{},{}]
			hm.put("taskLeveL", 1);
			rslt1 = oafQuery.executeForList(fqlensurelevel.toString(), hm);
			hm.put("taskLeveL", 2);
			rslt2 = oafQuery.executeForList(fqlensurelevel.toString(), hm);
			hm.put("taskLeveL", 3);
			rslt3 = oafQuery.executeForList(fqlensurelevel.toString(), hm);
			hm.put("taskLeveL", 4);
			rslt4 = oafQuery.executeForList(fqlensurelevel.toString(), hm);
		} catch (OafException e) {
			logger.error("保电任务--线路--柱状图统计数据异常", e);
		}
		Map<String,List<?>> mapList = new HashMap<>();
		List<Map<String,Object>> xAxislist = new ArrayList<>();
		List<List<Object>> seriesData = new ArrayList<>();
		Constant voltageLevelconstant = ConstantRegistry.findConstant(VoltageLevel.TYPE);
		for(int i=0;i<rsltVol.size();i++){
			Map<String,Object> xAxisMap  = new HashMap<>(); 
			Map<String,Object> map = rsltVol.get(i);
			int voltageLevelValue=((Integer) map.get("voltageLevel")).intValue();
			String voltagelevelName=voltageLevelconstant.getFieldValueByItemValue(voltageLevelValue, "name");
			xAxisMap.put("code", voltageLevelValue);
			xAxisMap.put("name", voltagelevelName);
			xAxislist.add(xAxisMap);
		}
		List<Object> seriesList1 = new ArrayList<>();
		List<Object> seriesList2 = new ArrayList<>();
		List<Object> seriesList3 = new ArrayList<>();
		List<Object> seriesList4 = new ArrayList<>();
		for(int i=0;i<xAxislist.size();i++){
			Object code = xAxislist.get(i).get("code");
			if(rslt1.size() == 0){
				seriesList1.add("0");
			}else{
				int num=0;
				for(int j=0; j<rslt1.size(); j++){
					Map<String,Object> map = rslt1.get(j);
					Object volcode = map.get("voltageLevel");
					Object count = map.get("count");
					if(volcode.equals(code)){
						seriesList1.add(count);
						num += 1;
						break;
					}
				}
				if(num == 0){
					seriesList1.add("0");
				}
			}
			if(rslt2.size() == 0){
				seriesList2.add("0");
			}else{
				int num=0;
				for(int j=0; j<rslt2.size(); j++){
					Map<String,Object> map = rslt2.get(j);
					Object volcode = map.get("voltageLevel");
					Object count = map.get("count");
					if(volcode.equals(code)){
						seriesList2.add(count);
						num += 1;
						break;
					}
				}
				if(num == 0){
					seriesList2.add("0");
				}
			}
			if(rslt3.size() == 0){
				seriesList3.add("0");
			}else{
				int num = 0;
				for(int j=0; j<rslt3.size(); j++){
					Map<String,Object> map = rslt3.get(j);
					Object volcode = map.get("voltageLevel");
					Object count = map.get("count");
					if(volcode.equals(code)){
						seriesList3.add(count);
						num += 1;
						break;
					}
				}
				if(num == 0){
					seriesList3.add("0");
				}
			}
			if(rslt4.size() == 0){
				seriesList4.add("0");
			}else{
				int num=0;
				for(int j=0; j<rslt4.size(); j++){
					Map<String,Object> map = rslt4.get(j);
					Object volcode = map.get("voltageLevel");
					Object count = map.get("count");
					if(volcode.equals(code)){
						seriesList4.add(count);
						num += 1;
						break;
					}
				}
				if(num == 0){
					seriesList4.add("0");
				}
			}		
		}
		seriesData.add(seriesList1);
		seriesData.add(seriesList2);
		seriesData.add(seriesList3);
		seriesData.add(seriesList4);
		mapList.put("xAxisData", xAxislist);
		mapList.put("seriesData", seriesData);
		// 返回查询结果
		return mapList;
	}
	
	/**
	 * <p>描述：资源保障统计数据把车人医院全部统计</p> 
	 * @param hashmap
	 * @return List<Map<String, Object>>
	 * @author:赵云
	 * 2017年6月20日 下午7:06:35
	 */
	@RequestMapping("/getresourceguarantee")
	public List<Map<String, Object>> getresourceguarantee(@RequestParam HashMap<String, Object> hashmap) {
		Map<String, Object> hm = new HashMap<String, Object> ();
		hm.put("sysdate", new Date ());
		StringBuffer sbDefectState = new StringBuffer ("from  person in  (");
		sbDefectState.append(" from t in  sgcim30.assets.management.ElectricityEnsure  ");
		sbDefectState.append("  join t2 in sgcim30.assets.management.ElectricityEnsurePhase  on t.id equals t2.belongTaskId   ");
		sbDefectState.append("  join t5 in  sgcim30.assets.management.ElectricityEnsurePerson   on t.id equals t5.belongTaskId ");
		if( hashmap.get("status")!=null && !"".equals(hashmap.get("status")) &&"进行中".equals(hashmap.get("status")) ){
			sbDefectState.append( "   where   t.startTime <=:sysdate  && t.endTime >=:sysdate  && t2.startTime <=:sysdate  && t2.endTime>=:sysdate  ");
		}else{
			//准备中
			sbDefectState.append( "  where  t.startTime >=:sysdate  && t2.startTime <=:sysdate  && t2.endTime>=:sysdate  ");
		}
		if( hashmap.get("organizationid")!=null && !"".equals(hashmap.get("organizationid")) ){
			sbDefectState.append( " && t.dutyUnitID ==:organizationid");
			hm.put("organizationid", hashmap.get("organizationid"));//两个参数都是形式参数；
		}
		if( hashmap.get("taskLevel")!=null && !"".equals(hashmap.get("taskLevel"))   ){
			sbDefectState.append( " &&  t2.ensureLevel  ==: taskLevel ");
			hm.put("taskLevel", hashmap.get("taskLevel"));//两个参数都是形式参数；
		} 
		sbDefectState.append(" select t5.id ");
		sbDefectState.append( ")  select   flag ='person',   c = person.count( ) "); 
		sbDefectState.append(" union  all ");
		sbDefectState.append("from  car in  ( ");
		sbDefectState.append(" from t in  sgcim30.assets.management.ElectricityEnsure  ");
		sbDefectState.append("  join t2 in sgcim30.assets.management.ElectricityEnsurePhase  on t.id equals t2.belongTaskId   ");
		sbDefectState.append("  join t5 in  sgcim30.assets.management.ElectricityEnsureVehicle   on t.id equals t5.belongTaskId ");
		if( hashmap.get("status")!=null && !"".equals(hashmap.get("status")) &&"进行中".equals(hashmap.get("status")) ){
			sbDefectState.append( "   where   t.startTime <=:sysdate  && t.endTime >=:sysdate  && t2.startTime <=:sysdate  && t2.endTime>=:sysdate  ");
		}else{
			//准备中
			sbDefectState.append( "  where  t.startTime >=:sysdate   ");
		}
		if( hashmap.get("organizationid")!=null && !"".equals(hashmap.get("organizationid")) ){
			sbDefectState.append( " && t.dutyUnitID ==:organizationid");
			hm.put("organizationid", hashmap.get("organizationid"));//两个参数都是形式参数；
		}
		if( hashmap.get("taskLevel")!=null && !"".equals(hashmap.get("taskLevel"))   ){
			sbDefectState.append( " &&  t2.ensureLevel  ==: taskLevel ");
			hm.put("taskLevel", hashmap.get("taskLevel"));//两个参数都是形式参数；
		} 
		sbDefectState.append(" select t5.id ");
		sbDefectState.append( ")  select   flag ='car',   c = car.count( ) "); 
		sbDefectState.append(" union  all ");
		sbDefectState.append("from  station in  ( ");
		sbDefectState.append(" from t in  sgcim30.assets.management.ElectricityEnsure  ");
		sbDefectState.append("  join t2 in sgcim30.assets.management.ElectricityEnsurePhase  on t.id equals t2.belongTaskId   ");
		sbDefectState.append("  join t5 in  sgcim30.assets.management.ElectricityEnsureStation   on t.id equals t5.belongTaskId ");
		if( hashmap.get("status")!=null && !"".equals(hashmap.get("status")) &&"进行中".equals(hashmap.get("status")) ){
			sbDefectState.append( "   where   t.startTime <=:sysdate  && t.endTime >=:sysdate  && t2.startTime <=:sysdate  && t2.endTime>=:sysdate  ");
		}else{
			//准备中
			sbDefectState.append( "  where  t.startTime >=:sysdate   ");
		}
		if( hashmap.get("organizationid")!=null && !"".equals(hashmap.get("organizationid")) ){
			sbDefectState.append( " && t.dutyUnitID ==:organizationid");
			hm.put("organizationid", hashmap.get("organizationid"));//两个参数都是形式参数；
		}
		if( hashmap.get("taskLevel")!=null && !"".equals(hashmap.get("taskLevel"))   ){
			sbDefectState.append( " &&  t2.ensureLevel  ==: taskLevel ");
			hm.put("taskLevel", hashmap.get("taskLevel"));//两个参数都是形式参数；
		} 
		sbDefectState.append(" select t5.id ");
		sbDefectState.append( ")  select   flag ='station',   c = station.count( ) "); 
		sbDefectState.append(" union  all ");
		sbDefectState.append("from  hotel in  ( ");
		sbDefectState.append(" from t in  sgcim30.assets.management.ElectricityEnsure  ");
		sbDefectState.append("  join t2 in sgcim30.assets.management.ElectricityEnsurePhase  on t.id equals t2.belongTaskId   ");
		sbDefectState.append("  join t5 in  sgcim30.assets.management.ElectricityEnsureHotel   on t.id equals t5.belongTaskId ");
		if( hashmap.get("status")!=null && !"".equals(hashmap.get("status")) &&"进行中".equals(hashmap.get("status")) ){
			sbDefectState.append( "   where   t.startTime <=:sysdate  && t.endTime >=:sysdate  && t2.startTime <=:sysdate  && t2.endTime>=:sysdate  ");
		}else{
			//准备中
			sbDefectState.append( "  where  t.startTime >=:sysdate   ");
		}
		if( hashmap.get("organizationid")!=null && !"".equals(hashmap.get("organizationid")) ){
			sbDefectState.append( " && t.dutyUnitID ==:organizationid");
			hm.put("organizationid", hashmap.get("organizationid"));//两个参数都是形式参数；
		}
		if( hashmap.get("taskLevel")!=null && !"".equals(hashmap.get("taskLevel"))   ){
			sbDefectState.append( " &&  t2.ensureLevel  ==: taskLevel ");
			hm.put("taskLevel", hashmap.get("taskLevel"));//两个参数都是形式参数；
		} 
		sbDefectState.append(" select t5.id ");
		sbDefectState.append( ")  select   flag ='hotel',   c = hotel.count( ) "); 
		sbDefectState.append(" union  all ");
		sbDefectState.append("from  hospital in  ( ");
		sbDefectState.append(" from t in  sgcim30.assets.management.ElectricityEnsure  ");
		sbDefectState.append("  join t2 in sgcim30.assets.management.ElectricityEnsurePhase  on t.id equals t2.belongTaskId   ");
		sbDefectState.append("  join t5 in  sgcim30.assets.management.ElectricityEnsureHospital   on t.id equals t5.belongTaskId ");
		if( hashmap.get("status")!=null && !"".equals(hashmap.get("status")) &&"进行中".equals(hashmap.get("status")) ){
			sbDefectState.append( "   where   t.startTime <=:sysdate  && t.endTime >=:sysdate  && t2.startTime <=:sysdate  && t2.endTime>=:sysdate  ");
		}else{
			//准备中
			sbDefectState.append( "  where  t.startTime >=:sysdate   ");
		}
		if( hashmap.get("organizationid")!=null && !"".equals(hashmap.get("organizationid")) ){
			sbDefectState.append( " && t.dutyUnitID ==:organizationid");
			hm.put("organizationid", hashmap.get("organizationid"));//两个参数都是形式参数；
		}
		if( hashmap.get("taskLevel")!=null && !"".equals(hashmap.get("taskLevel"))   ){
			sbDefectState.append( " &&  t2.ensureLevel  ==: taskLevel ");
			hm.put("taskLevel", hashmap.get("taskLevel"));//两个参数都是形式参数；
		} 
		sbDefectState.append(" select t5.id ");
		sbDefectState.append( ")  select   flag ='hospital',   c = hospital.count( ) "); 
		List<Map<String, Object>> rslt  = null;
		try {
			rslt = oafQuery.executeForList(sbDefectState.toString(),hm);
		} catch (OafException e) {
			logger.error(e);
		}
		
		return rslt ;
	}
	
	/**
	 * <p>描述： 任务表格查询</p> 
	 * @param hashmap
	 * @return List<Map<String, Object>>
	 * @author:赵云
	 * 2017年6月21日 下午7:30:12
	 */
	@RequestMapping("/gettaskGrid")
	public List<Map<String, Object>> gettaskGrid(@RequestParam HashMap<String, Object> hashmap) {
		
//		select  t.* from cim.ElectricityEnsure t  join cim.ElectricityEnsurePhase  t1 on t.id = t1.belongtaskid
//		where t.startTime <= sysdate and t.endtime >= sysdate 
//		and t1.startTime <= sysdate and t1.endtime >= sysdate  
//		and t.dutyunitid='16364345747547'  and t1.ensurelevel =1
//		from t in sgcim30.Transformer join 
//		    tw in sgcim30.Tower on t.Id equals tw.InTransformerId select t.Id
		int pageSize = Integer.parseInt((String)hashmap.get("pageSize"));//框架自带pageSize
		int pageIndex = Integer.parseInt((String)hashmap.get("pageIndex"));
		int skipCount = pageIndex*pageSize;
		HashMap<String, Object> hm = new HashMap<String, Object>();
		hm.put("sysdate", new Date());
		StringBuilder sql = new StringBuilder("from t in sgcim30.assets.management.ElectricityEnsure join ");
		sql.append(" t1 in sgcim30.assets.management.ElectricityEnsurePhase  on t.id  equals t1.belongTaskId ");
		if( hashmap.get("status")!=null && !"".equals(hashmap.get("status")) &&"进行中".equals(hashmap.get("status")) ){
			sql.append( " where   t.startTime <=:sysdate  && t.endTime >=:sysdate   && t1.startTime <=:sysdate  && t1.endTime>=:sysdate ");
		}else{
			//准备中
			sql.append( " where   t.startTime >=:sysdate   ");
		}
		if( hashmap.get("organizationid")!=null && !"".equals(hashmap.get("organizationid")) ){
			sql.append( " && t.dutyUnitID ==:organizationid");
			hm.put("organizationid", hashmap.get("organizationid"));//两个参数都是形式参数；
		}
		if( hashmap.get("taskLevel")!=null && !"".equals(hashmap.get("taskLevel"))   ){
			sql.append( " &&  t1.ensureLevel  ==: taskLevel ");
			hm.put("taskLevel", hashmap.get("taskLevel"));//两个参数都是形式参数；
		} 
		String sqlCount =  "  select sqlCount = t.count() " ;
		sqlCount=sql+sqlCount;//计算总数sql /fql 
		sql.append( " select ensureLevel1=t1.ensureLevel,  t  then skip :skipCount then take :pageSize  ");
		List<Map<String, Object>> rslt = null;
		Integer rsltCount = 0;
		try {
			rsltCount = (Integer)oafQuery.executeScalar(sqlCount , hm);	
			hm.put("skipCount",skipCount);
			hm.put("pageSize",pageSize);
			rslt = oafQuery.executeForList(sql.toString(),hm);
		} catch (OafException e) {
			logger.error(e);
		}
		Constant PowerLeverCst = ConstantRegistry.findConstant(PowerLever.TYPE);
		for (Map<String, Object> maptemp : rslt) {
			if (maptemp.get("startTime") != null) {
				String startTime = SDF.format(maptemp.get("startTime"));
				maptemp.put("startTime", startTime);
			}
			if (maptemp.get("endTime") != null) {
				String endTime = SDF.format(maptemp.get("endTime"));
				maptemp.put("endTime", endTime);
			}
			if (maptemp.get("ensureLevel") != null) {
				int ensureLevelValue =  (int) maptemp.get("ensureLevel") ;
				String ensureLevelName = "";
				ensureLevelName = PowerLeverCst .getFieldValueByItemValue(ensureLevelValue, PowerLever.NAME);
				maptemp.put("ensureLevel", ensureLevelName);
			}
			if (maptemp.get("ensureLevel1") != null) {
				int ensureLevelValue =  (int) maptemp.get("ensureLevel1") ;
				String ensureLevelName = "";
				ensureLevelName = PowerLeverCst .getFieldValueByItemValue(ensureLevelValue, PowerLever.NAME);
				maptemp.put("ensureLevel1", ensureLevelName);
			}
		}
		List<Map<String,Object>> list = new ArrayList<>();
		Map<String,Object> mapList = new HashMap<>();
		mapList.put("data", rslt);
		mapList.put("total",rsltCount);
		list.add(mapList);
		return list;
//		return rslt;
	}
}
