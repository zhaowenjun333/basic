package nariis.mcsas.management.powersupply.business.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cst.constants.common.FaultType;
import cst.constants.management.DefectQuale;
import cst.constants.management.DefectState;
import cst.constants.management.FaultPhase;
import cst.constants.management.PowerLever;
import cst.constants.management.RegulationsType;
import cst.constants.management.VehicleType;
import cst.constants.management.VoltageLevel;
import nariis.falcon.oaf.OafCommands;
import nariis.falcon.oaf.OafException;
import nariis.falcon.oaf.OafQuery;
import nariis.falcon.oaf.models.constant.Constant;
import nariis.falcon.oaf.models.constant.ConstantRegistry;
import nariis.mcsas.management.common.util.CommonExcelService;
import nariis.mcsas.management.common.util.CommonWorkLogService;


/**
 * <p>ClassName:PowerSupplyGuaranteeService，</p>
 * <p>描述:保电概览各方法</p>
 * @author 王万胜
 * @date 2017年3月23日下午3:05:00
 * @version 
 */
@RestController
@RequestMapping("/powerSupplyGuaranteeOverviewDetails")
public class PowerSupplyGuaranteeOverviewDetailsService {

	@Autowired
	OafQuery oafQuery;
	
	@Autowired
	OafCommands oafCommands;
	
	@Autowired
	CommonExcelService commonExcelService;
	
	@Autowired
	CommonWorkLogService commonWorkLogService;
	
	private static final Logger logger = LogManager.getLogger(PowerSupplyGuaranteeOverviewDetailsService.class);
	
	/************************************************************基础信息************************************************************/
	/**
	 * <p>描述：查询基础信息主表数据</p> 
	 * @param params
	 * @return List<Map<String,Object>>
	 * @author:王万胜
	 * 2017年3月9日 下午4:00:29
	 */
	@RequestMapping("/querypowerSupplyGuaranteeOverviewDetailsResult")
	public List<Map<String,Object>> querypowerSupplyGuaranteeOverviewDetailsResult(@RequestParam HashMap<String,Object> params){
		
		Map<String, Object> param = new LinkedHashMap<String, Object>();
		StringBuilder sql = new StringBuilder(" from t in sgcim30.assets.management.ElectricityEnsure ");
		sql.append("orderby t.ensureLevel select t ");
		
		List<Map<String, Object>> rslt = null;
		try {
			rslt = oafQuery.executeForList(sql.toString(), param);
		} catch (OafException e) {
			logger.error("保电概览--查询数据异常", e);
		}
		
		
		// 编码转换为汉字显示
		if(rslt != null){
			for(Map<String,Object> map : rslt){
				// 保电级别
				Constant ensureLevelConstant = ConstantRegistry.findConstant(PowerLever.TYPE);
				if(!"".equals(map.get("ensureLevel")) && map.get("ensureLevel") != null){
					int ensureLevelValue = Integer.parseInt(map.get("ensureLevel").toString());
					String ensureLevelName = ensureLevelConstant.getFieldValueByItemValue(ensureLevelValue, "name");
					map.put("ensureLevel", ensureLevelName);
				}
				
				// 时间转换（开始时间、结束时间）
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
				if(map.get("startTime") != null){
					String startTime = sdf.format(map.get("startTime"));
					map.put("startTime", startTime);
				}
				if(map.get("endTime") != null){
					String endTime = sdf.format(map.get("endTime"));
					map.put("endTime", endTime);
				}
			}
		}
		
		// 返回查询结果
		return rslt;
	}
	
	/**
	 * <p>描述：获取选定的一条保电概览的基础信息</p> 
	 * @param params
	 * @return data
	 * @author:王万胜
	 * 2017年5月16日 下午7:23:00
	 */
	@RequestMapping("/queryNoteInfo")
	public List<Map<String,Object>> queryNoteInfo(@RequestParam HashMap<String,String> params){
		String id = params.get("id");	 // tab类型
		
		Map<String, Object> param = new LinkedHashMap<String, Object>();
		StringBuilder sql = new StringBuilder(" from t in sgcim30.assets.management.ElectricityEnsure ");
		sql.append("where t.id ==: id ");
		param.put("id",id);
		sql.append("select t ");
		List<Map<String, Object>> rslt = null;
		try {
			rslt = oafQuery.executeForList(sql.toString(), param);
		} catch (OafException e) {
			logger.error("保电概览--获取选定的一条保电概览的基础信息异常", e);
		}
		
		
		// 编码转换为汉字显示
		if(rslt != null){
			for(Map<String,Object> map : rslt){
				// 保电级别
				Constant ensureLevelConstant = ConstantRegistry.findConstant(PowerLever.TYPE);
				if(!ObjectUtils.isEmpty(map.get("ensureLevel"))){
					int ensureLevelValue = Integer.parseInt(map.get("ensureLevel").toString());
					String ensureLevelName = ensureLevelConstant.getFieldValueByItemValue(ensureLevelValue, "name");
					map.put("ensureLevel", ensureLevelName);
				}
			}
		}
		
		// 返回查询结果
		return rslt;
	}
	
	/**
	 * <p>描述：获取基础信息保电阶段表格数据</p> 
	 * @param params
	 * @return data
	 * @author:王万胜
	 * 2017年4月13日 下午5:02:32
	 */
	@RequestMapping("/queryElectricityEnsurePhaseResult")
	public List<Map<String,Object>> queryElectricityEnsurePhaseResult(@RequestParam HashMap<String,String> params){
		String taskId = params.get("taskId");	 // tab类型
		
		Map<String, Object> param = new LinkedHashMap<String, Object>();
		StringBuilder sql = new StringBuilder(" from t in sgcim30.assets.management.ElectricityEnsurePhase ");
		sql.append("where t.belongTaskId ==: taskId orderby t.startTime, t.endTime ");
		param.put("taskId",taskId);
		sql.append("select t ");
		List<Map<String, Object>> rslt = null;
		try {
			rslt = oafQuery.executeForList(sql.toString(), param);
		} catch (OafException e) {
			logger.error("保电概览--获取基础信息保电阶段表格数据异常", e);
		}
		
		
		// 编码转换为汉字显示
		if(rslt != null){
			for(Map<String,Object> map : rslt){
				// 时间转换（开始时间、结束时间）
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
				if(map.get("startTime") != null){
					String startTime = sdf.format(map.get("startTime"));
					map.put("startTime", startTime);
				}
				if(map.get("endTime") != null){
					String endTime = sdf.format(map.get("endTime"));
					map.put("endTime", endTime);
				}
				
				// 保电级别
				Constant ensureLevelConstant = ConstantRegistry.findConstant(PowerLever.TYPE);
				if(!ObjectUtils.isEmpty(map.get("ensureLevel"))){
					int ensureLevelValue = Integer.parseInt(map.get("ensureLevel").toString());
					String ensureLevelName = ensureLevelConstant.getFieldValueByItemValue(ensureLevelValue, "name");
					map.put("ensureLevel", ensureLevelName);
				}
			}
		}
		
		// 返回查询结果
		return rslt;
	}
	
	
	
	
	/************************************************************组织机构************************************************************/
	/**
	 * <p>描述：获取组织机构树</p> 
	 * @param params
	 * @return tree
	 * @author:王万胜
	 * 2017年4月18日 下午7:41:14
	 */
	@RequestMapping("/getOrganizationTreeList")
	public List<Map<String,Object>> getOrganizationTreeList(@RequestParam HashMap<String,Object> params){
		String taskId = params.get("taskId").toString();	 // 保电任务ID
		Map<String, Object> param = new LinkedHashMap<String, Object>();
		// 总部为根节点
		StringBuilder sql = new StringBuilder("from o in sgcim30.assets.management.ElectricityEnsureOrg ");
		sql.append("where o.belongTaskId ==: taskId orderby o.superOrgID descending, o.orgName ");
		param.put("taskId",taskId);
		sql.append("select o.id, text = o.orgName, pid = o.superOrgID, person = o.dutyPerson ");
		
		List<Map<String, Object>> rslt = null;
		try {
			rslt = oafQuery.executeForList(sql.toString(), param);
		} catch (OafException e) {
			logger.error("保电概览--获取组织机构树异常", e);
		}
		
		return rslt;
	}
	
	/**
	 * <p>描述：获取组织机构表格信息</p> 
	 * @param params
	 * @return data
	 * @author:王万胜
	 * 2017年4月18日 下午6:30:45
	 */
	@RequestMapping("/queryOrganizationResult")
	public List<Map<String,Object>> queryOrganizationResult(@RequestParam HashMap<String,String> params){
		String taskId = params.get("taskId");	 	// 保电任务ID
		
		Map<String, Object> param = new LinkedHashMap<String, Object>();
		StringBuilder sql = new StringBuilder("from k in (from t in (from o1 in sgcim30.assets.management.ElectricityEnsureOrg ");
		sql.append("where o1.belongTaskId ==: taskId ");
		param.put("taskId",taskId);
		sql.append("orderby o1.superOrgID descending ");
		sql.append("select o1 )");
		sql.append("left join e in sgcim30.assets.management.ElectricityEnsure on t.belongTaskId equals e.id ");
		sql.append("left join o2 in sgcim30.assets.management.ElectricityEnsureOrg on t.superOrgID equals o2.id ");
		sql.append("select belongTask = e.theme, superOrg = o2.orgName, t) ");
		
		sql.append("select k ");
		
		List<Map<String, Object>> rslt = null;
		try {
			rslt = oafQuery.executeForList(sql.toString(), param);
			
		} catch (OafException e) {
			logger.error("保电概览--组织机构--查询数据异常", e);
		}
		
		// 返回查询结果
		return rslt;
	}
	
	
	/**
	 * <p>描述：获取下级组织机构</p> 
	 * @param params
	 * @return data
	 * @author:王万胜
	 * 2017年4月20日 下午1:55:58
	 */
	@RequestMapping("/querySubOrganizationResult")
	public List<Map<String,Object>> querySubOrganizationResult(@RequestParam HashMap<String,String> params){
		String superIds = params.get("superIds");	 	// 上级组织机构ID
		String[] superIdsArr = superIds.split(",");
		int length = superIdsArr.length;
		
		String whereSql = "where ";
		for(int i=0; i<length; i++){
			if(i == 0){
				whereSql += ("t.superOrgID == '" + superIdsArr[i] + "' ");
			}else{
				whereSql += ("|| t.superOrgID == '" + superIdsArr[i] + "' ");
			}
		}
		
		Map<String, Object> param = new LinkedHashMap<String, Object>();
		StringBuilder sql = new StringBuilder("from t in sgcim30.assets.management.ElectricityEnsureOrg ");
		if(length > 0){
			sql.append(whereSql);
		}
		sql.append("select subId = t.id ");
		
		List<Map<String, Object>> rslt = null;
		try {
			rslt = oafQuery.executeForList(sql.toString(), param);
		} catch (OafException e) {
			logger.error("保电概览--获取下级组织机构异常", e);
		}
		
		// 返回查询结果
		return rslt;
	}
	

	
	
	/************************************************************保电站线************************************************************/
	/**
	 * <p>描述：获取保电站线表格数据</p> 
	 * @param params
	 * @return data
	 * @author:王万胜
	 * 2017年4月21日 上午10:25:31
	 */
	@RequestMapping("/queryStationOrLineResult")
	public List<Map<String,Object>> queryStationOrLineResult(@RequestParam HashMap<String,Object> params){
		String taskId = params.get("taskId").toString();					// 保电任务ID
		String stationLineType = params.get("stationLineType").toString();	// 站线类型
		Object belongOrg = params.get("belongOrg");							// 所属组织
		
		/****** 获取所有关联的站线信息 ******/
//		Map<String, Object> par = new LinkedHashMap<String, Object>();
//		StringBuilder sql = new StringBuilder("from t in sgcim30.assets.management.StationLineEnsure ");
//		sql.append("where t.belongTaskId ==: taskId ");
//		par.put("taskId",taskId);
//		sql.append("&& t.stationLineType ==: stationLineType ");
//		par.put("stationLineType", stationLineType);
//		
//		if(!ObjectUtils.isEmpty(belongOrg)){
//			sql.append("&& t.orgBelongID ==: belongOrg ");
//			par.put("belongOrg", belongOrg);
//		}
		/***********************************/
		
//		/****** 获取当前时间有保电级别的关联的站线信息 ******/
//		// 获取所有关联的站线ID
//		Map<String, Object> par1 = new LinkedHashMap<String, Object>();
//		StringBuilder sql1 = new StringBuilder("from a in sgcim30.assets.management.StationLineEnsure ");
//		sql1.append("where a.belongTaskId ==: taskId ");
//		par1.put("taskId",taskId);
//		sql1.append("select a.id ");
//		List<Map<String, Object>> rslt1 = null;
//		try {
//			rslt1 = oafQuery.executeForList(sql1.toString(), par1);
//		} catch (OafException e) {
//			logger.error("保电概览--保电站线--查询数据异常", e);
//		}
//		int l1 = rslt1.size();
//		String sqlWhere1 = "";
//		for(int i=0; i<l1; i++){
//			if(i == 0){
//				sqlWhere1 = "( b.belongTaskId == '" + rslt1.get(0).get("id").toString() + "' ";
//			}else{
//				sqlWhere1 += "|| b.belongTaskId == '" + rslt1.get(i).get("id").toString() + "' ";
//			}
//		}
//		
//		// 获取当前时间有保电级别的站线ID
//		Date today = new Date();
//		Map<String, Object> par2 = new LinkedHashMap<String, Object>();
//		StringBuilder sql2 = new StringBuilder("from b in sgcim30.assets.management.ElectricityEnsurePhase ");
//		sql2.append("where b.startTime <=: today ");
//		par2.put("today", today);
//		sql2.append("&& b.endTime >=: today ");
//		par2.put("today", today);
//		if("".equals(sqlWhere1)){
//			sql2.append("&& b.belongTaskId == 'stationOrLineIdIsNull' ");
//		}else{
//			sql2.append("&& " + sqlWhere1 + ") ");
//		}
//		sql2.append("select b.belongTaskId ");
//		List<Map<String, Object>> rslt2 = null;
//		try {
//			rslt2 = oafQuery.executeForList(sql2.toString(), par2);
//		} catch (OafException e) {
//			logger.error("保电概览--保电站线--查询数据异常", e);
//		}
//		int l2 = rslt2.size();
//		String sqlWhere2 = "";
//		for(int i=0; i<l2; i++){
//			if(i == 0){
//				sqlWhere2 = "( t.id == '" + rslt2.get(0).get("belongTaskId").toString() + "' ";
//			}else{
//				sqlWhere2 += "|| t.id == '" + rslt2.get(i).get("belongTaskId").toString() + "' ";
//			}
//		}
//		
//		// 获取当前时间有保电级别的站线ID的站线信息
//		Map<String, Object> par = new LinkedHashMap<String, Object>();
//		StringBuilder sql = new StringBuilder("from t in sgcim30.assets.management.StationLineEnsure ");
//		sql.append("where t.stationLineType ==: stationLineType ");
//		par.put("stationLineType", stationLineType);
//		
//		if(!ObjectUtils.isEmpty(belongOrg)){
//			sql.append("&& t.orgBelongID ==: belongOrg ");
//			par.put("belongOrg", belongOrg);
//		}
//		
//		if("".equals(sqlWhere2)){
//			sql.append("&& t.id == 'stationOrLineIdIsNull' ");
//		}else{
//			sql.append("&& " + sqlWhere2 + ") ");
//		}
//		/*************************************************/
		
		Map<String, Object> par = new LinkedHashMap<String, Object>();
		Date today = new Date();
		StringBuilder sql = new StringBuilder("from t in (");
		sql.append("from a in sgcim30.assets.management.StationLineEnsure ");
		sql.append("join b in( ");
		sql.append("from c in sgcim30.assets.management.ElectricityEnsurePhase where c.startTime <=: stime && c.endTime >=: etime select c.belongTaskId,c.ensureLevel");
		par.put("stime", today);
		par.put("etime", today);
		sql.append(") on a.id equals b.belongTaskId select a,ensureLevelName = b.ensureLevel ");
		sql.append(") where t.belongTaskId ==: taskId ");
		par.put("taskId",taskId);
		if(!ObjectUtils.isEmpty(belongOrg)){
			sql.append("&& t.orgBelongID ==: belongOrg ");
			par.put("belongOrg", belongOrg);
		}
		sql.append("&& t.stationLineType ==:stationLineType "); // 电站
		par.put("stationLineType", stationLineType);
//		sql.append("groupby t.voltageLevel orderby t.voltageLevel descending select t");
		
		// 表格分页
		StringBuilder sqlCount = new StringBuilder();
		sqlCount.append(sql);
		sqlCount.append(" select sqlCount = t.count() ");
		
		sql.append("select t then skip :skipCount then take :pageSize ");
		
		List<Map<String, Object>> rslt = null;
		int rsltCount = 0; // 统计表格展示条数
		try {
			int pageSize = Integer.parseInt(params.get("pageSize").toString()); // 获取分页参数
			int pageIndex = Integer.parseInt(params.get("pageIndex").toString());
			int skipCount = pageIndex * pageSize;
			rsltCount = (Integer) oafQuery.executeScalar(sqlCount.toString(), par);
			par.put("skipCount", skipCount);
			par.put("pageSize", pageSize);
			rslt = oafQuery.executeForList(sql.toString(), par);
		} catch (OafException e) {
			logger.error("保电概览--保电站线--查询数据异常", e);
		}
		
		// 编码转换为汉字显示
		if(rslt != null){
			for(Map<String,Object> map : rslt){
				// 电压等级
				Constant voltageLevelConstant = ConstantRegistry.findConstant(VoltageLevel.TYPE);
				if(!ObjectUtils.isEmpty(map.get("voltageLevel"))){
					int voltageLevelValue = ((Integer)map.get("voltageLevel")).intValue();
					String voltageLevelName = voltageLevelConstant.getFieldValueByItemValue(voltageLevelValue, "name");
					map.put("voltageLevel", voltageLevelName);
				}
				
				// 保电级别
				Constant ensureLevelConstant = ConstantRegistry.findConstant(PowerLever.TYPE);
				if(!ObjectUtils.isEmpty(map.get("ensureLevelName"))){
					int ensureLevelValue = Integer.parseInt(map.get("ensureLevelName").toString());
					String ensureLevelName = ensureLevelConstant.getFieldValueByItemValue(ensureLevelValue, "name");
					map.put("ensureLevelName", ensureLevelName);
				}
			}
		}
		
		// 返回查询结果
		Map<String, Object> rst = new HashMap<String, Object>();
		if(!ObjectUtils.isEmpty(params.get("pageSize"))){
			rst.put("data", rslt);
			rst.put("total", rsltCount);
		}else{
			rst.put("data", rslt);
		}
		List<Map<String, Object>> list = new ArrayList<>();
		list.add(rst);
		return list;
	}
	
	
	
	/************************************************************用户************************************************************/
	/**
	 * <p>描述：获取用户保电级别统计</p> 
	 * @param params
	 * @return data
	 * @author:王万胜
	 * 2017年6月19日 下午5:30:24
	 */
	@RequestMapping("/queryUserLevel")
	public List<Map<String,Object>> queryUserLevel(@RequestParam HashMap<String,Object> params){
		String taskId = params.get("taskId").toString();	// 保电任务ID
		Object belongOrgID = params.get("belongOrgID");		// 所属组织ID
		Date today = new Date();					// 获取当前时间
		
		// 获取保电概览关联的用户ID
		Map<String, Object> par1 = new LinkedHashMap<String, Object>();
		StringBuilder sql1 = new StringBuilder("from a in sgcim30.assets.management.ElectricityEnsureUser ");
		sql1.append("where a.belongTaskId ==: taskId ");
		par1.put("taskId",taskId);
		
		if(!ObjectUtils.isEmpty(belongOrgID)){
			sql1.append("&& a.belongOrgID ==: belongOrgID ");
			par1.put("belongOrgID", belongOrgID);
		}
		
		sql1.append("select a.id ");
		
		List<Map<String, Object>> rslt1 = null;
		try {
			rslt1 = oafQuery.executeForList(sql1.toString(), par1);
		} catch (OafException e) {
			logger.error("保电概览--用户--获取保电概览关联的用户ID异常", e);
		}
		
		// 获取当前时间用户按保电级别统计数量
		Map<String, Object> par2 = new LinkedHashMap<String, Object>();
		StringBuilder sql2 = new StringBuilder("from c in ( from b in sgcim30.assets.management.ElectricityEnsurePhase ");
	    int userCount = rslt1.size();
	    if(userCount > 0){
	      for(int i=0; i<userCount; i++){
	        String userId = rslt1.get(i).get("id").toString();
	        if(i == 0){
	          sql2.append("where ( b.belongTaskId ==: userId"+i);
	        }else{
	          sql2.append(" || b.belongTaskId ==: userId"+i);
	        }
	        par2.put("userId"+i, userId);
	      }
	    }else{
	      sql2.append("where ( b.belongTaskId == 'userIdIsNull' ");
	    }
	    sql2.append(") && b.startTime <=: today ");
	    par2.put("today", today);
	    sql2.append(" && b.endTime >=: today ");
	    par2.put("today", today);
	    
	    sql2.append("select b ) ");
	    sql2.append("groupby c.ensureLevel orderby c.ensureLevel select c.ensureLevel, count=c.count() ");
		
		List<Map<String, Object>> rslt2 = null;
		try {
			rslt2 = oafQuery.executeForList(sql2.toString(), par2);
		} catch (OafException e) {
			logger.error("保电概览--用户--获取当前时间用户按保电级别统计数量异常", e);
		}
		
		// 返回查询结果
		return rslt2;
	}
	
	/**
	 * <p>描述：获取用户表格数据</p> 
	 * @param params
	 * @return data
	 * @author:王万胜
	 * 2017年4月26日 下午5:09:12
	 */
	@RequestMapping("/queryUserResult")
	public List<Map<String,Object>> queryUserResult(@RequestParam HashMap<String,String> params){
		String taskId = params.get("taskId");	 			// 保电任务ID
		String ensureLevel = params.get("ensureLevel");		// 保电级别
		Object belongOrgID = params.get("belongOrgID");		// 所属组织ID
		Date today = new Date();							// 当前时间
		
		// 获取保电概览关联的用户ID
		Map<String, Object> par1 = new LinkedHashMap<String, Object>();
		StringBuilder sql1 = new StringBuilder("from a in sgcim30.assets.management.ElectricityEnsureUser ");
		sql1.append("where a.belongTaskId ==: taskId ");
		par1.put("taskId",taskId);
		
		if(!ObjectUtils.isEmpty(belongOrgID)){
			sql1.append("&& a.belongOrgID ==: belongOrgID ");
			par1.put("belongOrgID", belongOrgID);
		}
		
		sql1.append("select a.id ");
		
		List<Map<String, Object>> rslt1 = null;
		try{
			rslt1 = oafQuery.executeForList(sql1.toString(), par1);
		}catch (OafException e) {
			logger.error("保电概览--用户--获取保电概览关联的用户ID异常", e);
		}
		
		// 获取保电概览关联的当前时间为某保电级别的用户ID
		int length1 = rslt1.size();
		Map<String, Object> par2 = new LinkedHashMap<String, Object>();
		StringBuilder sql2 = new StringBuilder("from b in sgcim30.assets.management.ElectricityEnsurePhase ");
		if(length1 > 0){
			for(int i=0; i<length1; i++){
				String userId = rslt1.get(i).get("id").toString();
				if(i == 0){
		          sql2.append("where ( b.belongTaskId ==: userId"+i);
		        }else{
		          sql2.append(" || b.belongTaskId ==: userId"+i);
		        }
		        par2.put("userId"+i, userId);
			}
		}else{
			sql2.append("where ( b.belongTaskId == 'userIdIsNull' ");
		}
		sql2.append(") && b.startTime <=: today ");
	    par2.put("today", today);
	    sql2.append(" && b.endTime >=: today ");
	    par2.put("today", today);
	    sql2.append("&& b.ensureLevel ==: ensureLevel ");
	    par2.put("ensureLevel", ensureLevel);
		sql2.append("select b.belongTaskId ");
		
		List<Map<String, Object>> rslt2 = null;
		try {
			rslt2 = oafQuery.executeForList(sql2.toString(), par2);
		} catch (OafException e) {
			logger.error("保电概览--用户--获取保电概览关联的当前时间为某保电级别的用户ID异常", e);
		}
		
		// 获取保电概览关联的当前时间为某保电级别的用户信息
		int length2 = rslt2.size();
		Map<String, Object> par = new LinkedHashMap<String, Object>();
		StringBuilder sql = new StringBuilder("from t in sgcim30.assets.management.ElectricityEnsureUser ");
		sql.append("where t.belongTaskId ==: taskId ");
		par.put("taskId", taskId);
		if(length2 > 0){
			for(int i=0; i<length2; i++){
				String userId = rslt2.get(i).get("belongTaskId").toString();
				if(i == 0){
		          sql.append("&& ( t.id ==: userId"+i);
		        }else{
		          sql.append(" || t.id ==: userId"+i);
		        }
		        par.put("userId"+i, userId);
			}
		}else{
			sql.append("&& ( t.id == 'userIdIsNull' ");
		}
		
		// 表格分页
		StringBuilder sqlCount = new StringBuilder();
		sqlCount.append(sql);
		sqlCount.append(" ) select sqlCount = t.count() ");
		
		sql.append(" ) select t then skip :skipCount then take :pageSize ");
		
		List<Map<String, Object>> rslt = null;
		int rsltCount = 0; // 统计表格展示条数
		try {
			int pageSize = Integer.parseInt(params.get("pageSize").toString()); // 获取分页参数
			int pageIndex = Integer.parseInt(params.get("pageIndex").toString());
			int skipCount = pageIndex * pageSize;
			rsltCount = (Integer) oafQuery.executeScalar(sqlCount.toString(), par);
			par.put("skipCount", skipCount);
			par.put("pageSize", pageSize);
			rslt = oafQuery.executeForList(sql.toString(), par);
		} catch (OafException e) {
			logger.error("保电概览--用户--查询数据异常", e);
		}
		
		// 返回查询结果
		Map<String, Object> rst = new HashMap<String, Object>();
		if(!ObjectUtils.isEmpty(params.get("pageSize"))){
			rst.put("data", rslt);
			rst.put("total", rsltCount);
		}else{
			rst.put("data", rslt);
		}
		List<Map<String, Object>> list = new ArrayList<>();
		list.add(rst);
		return list;
	}
	
	
	
	/************************************************************人员************************************************************/
	/**
	 * <p>描述：获取人员表格数据</p> 
	 * @param params
	 * @return data
	 * @author:王万胜
	 * 2017年4月28日 下午1:41:36
	 */
	@RequestMapping("/queryPersonResult")
	public List<Map<String,Object>> queryPersonResult(@RequestParam HashMap<String,Object> params){
		String taskId = params.get("taskId").toString();	// 保电任务ID
		Object belongOrg = params.get("belongOrg");		// 所属组织
		Object type = params.get("type");					// 专业
		
		Map<String, Object> par = new LinkedHashMap<String, Object>();
		StringBuilder sql = new StringBuilder("from t in sgcim30.assets.management.ElectricityEnsurePerson ");
		sql.append("where t.belongTaskId ==: taskId ");
		par.put("taskId",taskId);
		if(!ObjectUtils.isEmpty(belongOrg)){
			sql.append("&& t.belongOrg ==: belongOrg ");
			par.put("belongOrg", belongOrg);
		}
		if(!ObjectUtils.isEmpty(type)){
			sql.append("&& t.type ==: type ");
			par.put("type", type);
		}
		
		// 表格分页
		StringBuilder sqlCount = new StringBuilder();
		sqlCount.append(sql);
		sqlCount.append(" select sqlCount = t.count() ");
		
		sql.append("select t then skip :skipCount then take :pageSize ");
		
		List<Map<String, Object>> rslt = null;
		int rsltCount = 0; // 统计表格展示条数
		try {
			int pageSize = Integer.parseInt(params.get("pageSize").toString()); // 获取分页参数
			int pageIndex = Integer.parseInt(params.get("pageIndex").toString());
			int skipCount = pageIndex * pageSize;
			rsltCount = (Integer) oafQuery.executeScalar(sqlCount.toString(), par);
			par.put("skipCount", skipCount);
			par.put("pageSize", pageSize);
			rslt = oafQuery.executeForList(sql.toString(), par);
		} catch (OafException e) {
			logger.error("保电概览--人员--查询数据异常", e);
		}
		
		// 编码转换为汉字显示
		if(rslt != null){
			for(Map<String,Object> map : rslt){
				// 专业
				Constant typeconstant = ConstantRegistry.findConstant(RegulationsType.TYPE);
				if(!ObjectUtils.isEmpty(map.get("type"))){
					int typeValue = (int) map.get("type");
					String typeName = typeconstant.getFieldValueByItemValue(typeValue, "name");
					map.put("type", typeName);
				}
			}
		}
		
		// 返回查询结果
		Map<String, Object> rst = new HashMap<String, Object>();
		if(!ObjectUtils.isEmpty(params.get("pageSize"))){
			rst.put("data", rslt);
			rst.put("total", rsltCount);
		}else{
			rst.put("data", rslt);
		}
		List<Map<String, Object>> list = new ArrayList<>();
		list.add(rst);
		return list;
	}
	
	/**
	 * <p>描述：获取人员饼图数据</p> 
	 * @param params
	 * @return data
	 * @author:王万胜
	 * 2017年6月22日 下午2:28:45
	 */
	@RequestMapping("/queryPersonChartResult")
	public List<Map<String,Object>> queryPersonChartResult(@RequestParam HashMap<String,Object> params){
		String taskId = params.get("taskId").toString();	// 保电任务ID
		Object belongOrg = params.get("belongOrg");		// 所属组织
		
		Map<String, Object> par = new LinkedHashMap<String, Object>();
		StringBuilder sql = new StringBuilder("from t in sgcim30.assets.management.ElectricityEnsurePerson ");
		sql.append("where t.belongTaskId ==: taskId ");
		par.put("taskId",taskId);
		if(!ObjectUtils.isEmpty(belongOrg)){
			sql.append("&& t.belongOrg ==: belongOrg ");
			par.put("belongOrg", belongOrg);
		}
		
		sql.append("groupby t.type orderby t.type select t.type, count=t.count()");
		
		List<Map<String, Object>> rslt = null;
		try {
			rslt = oafQuery.executeForList(sql.toString(), par);
		} catch (OafException e) {
			logger.error("保电概览--人员--饼图统计数据异常", e);
		}
		
		// 编码转换为汉字显示
		if(rslt != null){
			for(Map<String,Object> map : rslt){
				// 专业
				Constant typeconstant = ConstantRegistry.findConstant(RegulationsType.TYPE);
				if(!ObjectUtils.isEmpty(map.get("type"))){
					int typeValue = (int) map.get("type");
					String typeName = typeconstant.getFieldValueByItemValue(typeValue, "name");
					map.put("typeName", typeName);
				}
			}
		}
		
		// 返回查询结果
		return rslt;
	}
	
	
	
	/************************************************************车辆************************************************************/
	/**
	 * <p>描述：获取车辆表格数据</p> 
	 * @param params
	 * @return data
	 * @author:王万胜
	 * 2017年4月28日 下午1:41:36
	 */
	@RequestMapping("/queryCarResult")
	public List<Map<String,Object>> queryCarResult(@RequestParam HashMap<String,Object> params){
		String taskId = params.get("taskId").toString();	// 保电任务ID
		Object belongOrg = params.get("belongOrg");			// 所属组织
		Object type = params.get("type");					// 车辆类型
		
		Map<String, Object> par = new LinkedHashMap<String, Object>();
		StringBuilder sql = new StringBuilder("from t in sgcim30.assets.management.ElectricityEnsureVehicle ");
		sql.append("where t.belongTaskId ==: taskId ");
		par.put("taskId",taskId);
		
		if(!ObjectUtils.isEmpty(belongOrg)){
			sql.append("&& t.belongOrg ==: belongOrg ");
			par.put("belongOrg", belongOrg);
		}
		
		if(!ObjectUtils.isEmpty(type)){
			sql.append("&& t.type ==: type ");
			par.put("type", type);
		}
		
		// 表格分页
		StringBuilder sqlCount = new StringBuilder();
		sqlCount.append(sql);
		sqlCount.append(" select sqlCount = t.count() ");
		
		sql.append("select t then skip :skipCount then take :pageSize ");
		
		List<Map<String, Object>> rslt = null;
		int rsltCount = 0; // 统计表格展示条数
		try {
			int pageSize = Integer.parseInt(params.get("pageSize").toString()); // 获取分页参数
			int pageIndex = Integer.parseInt(params.get("pageIndex").toString());
			int skipCount = pageIndex * pageSize;
			rsltCount = (Integer) oafQuery.executeScalar(sqlCount.toString(), par);
			par.put("skipCount", skipCount);
			par.put("pageSize", pageSize);
			rslt = oafQuery.executeForList(sql.toString(), par);
		} catch (OafException e) {
			logger.error("保电概览--车辆--查询数据异常", e);
		}
		
		// 编码转换为汉字显示
		if(rslt != null){
			for(Map<String,Object> map : rslt){
				// 车辆类型
				Constant vehicleType = ConstantRegistry.findConstant(VehicleType.TYPE);
				if(!ObjectUtils.isEmpty(map.get("type"))){
					int vehicleTypeValue = Integer.parseInt(map.get("type").toString());
					String vehicleTypeName = vehicleType.getFieldValueByItemValue(vehicleTypeValue, "name");
					map.put("type", vehicleTypeName);
				}
			}
		}
		
		// 返回查询结果
		Map<String, Object> rst = new HashMap<String, Object>();
		if(!ObjectUtils.isEmpty(params.get("pageSize"))){
			rst.put("data", rslt);
			rst.put("total", rsltCount);
		}else{
			rst.put("data", rslt);
		}
		List<Map<String, Object>> list = new ArrayList<>();
		list.add(rst);
		return list;
	}
	
	/**
	 * <p>描述：获取车辆饼图数据</p> 
	 * @param params
	 * @return data
	 * @author:王万胜
	 * 2017年6月22日 下午2:28:45
	 */
	@RequestMapping("/queryCarChartResult")
	public List<Map<String,Object>> queryCarChartResult(@RequestParam HashMap<String,Object> params){
		String taskId = params.get("taskId").toString();	// 保电任务ID
		Object belongOrg = params.get("belongOrg");		// 所属组织
		
		Map<String, Object> par = new LinkedHashMap<String, Object>();
		StringBuilder sql = new StringBuilder("from t in sgcim30.assets.management.ElectricityEnsureVehicle ");
		sql.append("where t.belongTaskId ==: taskId ");
		par.put("taskId",taskId);
		if(!ObjectUtils.isEmpty(belongOrg)){
			sql.append("&& t.belongOrg ==: belongOrg ");
			par.put("belongOrg", belongOrg);
		}
		
		sql.append("groupby t.type orderby t.count() descending select t.type, count=t.count()");
		
		List<Map<String, Object>> rslt = null;
		try {
			rslt = oafQuery.executeForList(sql.toString(), par);
		} catch (OafException e) {
			logger.error("保电概览--车辆--饼图统计数据异常", e);
		}
		
		// 最多保留6组数据，第6组是“其它”，值是从第6组到最后的和
		int l = rslt.size();
		int n = 0;
		int othersCount = 0;
		List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
		for(int i=0; i<l; i++){
			// 车辆类型
			Constant vehicleType = ConstantRegistry.findConstant(VehicleType.TYPE);
			int type = (int) rslt.get(i).get("type");
			int count = (int) rslt.get(i).get("count");
			
//			if(type != 0){
				Map<String, Object> map = new HashMap<String, Object>();
				String vehicleTypeName = vehicleType.getFieldValueByItemValue(type, "name");
//				if(n < 5){
					map.put("type", type);
					map.put("typeName", vehicleTypeName);
					map.put("count", count);
					
					data.add(map);
//					n++;
//				}else{
//					othersCount += count;
//				}
//			}else{
//				othersCount += count;
//			}
		}
		
//		if(l > 5){
//			Map<String, Object> m = new HashMap<String, Object>();
//			m.put("type", 0);
//			m.put("typeName", "其它");
//			m.put("count", othersCount);
//			data.add(m);
//		}
		
		// 返回查询结果
		return data;
	}
	
	
	
	/************************************************************驻点************************************************************/
	/**
	 * <p>描述：获取驻点表格数据</p> 
	 * @param params
	 * @return data
	 * @author:王万胜
	 * 2017年4月28日 下午1:41:36
	 */
	@RequestMapping("/queryPointResult")
	public List<Map<String,Object>> queryPointResult(@RequestParam HashMap<String,Object> params){
		String taskId = params.get("taskId").toString();	// 保电任务ID
		Object belongOrg = params.get("belongOrg");			// 所属组织
		
		Map<String, Object> par = new LinkedHashMap<String, Object>();
		StringBuilder sql = new StringBuilder("from t in sgcim30.assets.management.ElectricityEnsureStation ");
		sql.append("where t.belongTaskId ==: taskId ");
		par.put("taskId",taskId);
		if(!ObjectUtils.isEmpty(belongOrg)){
			sql.append("&& t.belongOrgID ==: belongOrg ");
			par.put("belongOrg", belongOrg);
		}
		
		// 表格分页
		StringBuilder sqlCount = new StringBuilder();
		sqlCount.append(sql);
		sqlCount.append(" select sqlCount = t.count() ");
		
		sql.append("select t then skip :skipCount then take :pageSize ");
		
		List<Map<String, Object>> rslt = null;
		int rsltCount = 0; // 统计表格展示条数
		try {
			int pageSize = Integer.parseInt(params.get("pageSize").toString()); // 获取分页参数
			int pageIndex = Integer.parseInt(params.get("pageIndex").toString());
			int skipCount = pageIndex * pageSize;
			rsltCount = (Integer) oafQuery.executeScalar(sqlCount.toString(), par);
			par.put("skipCount", skipCount);
			par.put("pageSize", pageSize);
			rslt = oafQuery.executeForList(sql.toString(), par);
		} catch (OafException e) {
			logger.error("保电概览--驻点--查询数据异常", e);
		}
		
		// 返回查询结果
		Map<String, Object> rst = new HashMap<String, Object>();
		if(!ObjectUtils.isEmpty(params.get("pageSize"))){
			rst.put("data", rslt);
			rst.put("total", rsltCount);
		}else{
			rst.put("data", rslt);
		}
		List<Map<String, Object>> list = new ArrayList<>();
		list.add(rst);
		return list;
	}
	

	
	
	/************************************************************宾馆************************************************************/
	/**
	 * <p>描述：获取宾馆表格数据</p> 
	 * @param params
	 * @return data
	 * @author:王万胜
	 * 2017年4月28日 下午1:41:36
	 */
	@RequestMapping("/queryHotelResult")
	public List<Map<String,Object>> queryHotelResult(@RequestParam HashMap<String,Object> params){
		String taskId = params.get("taskId").toString();	// 保电任务ID
		
		Map<String, Object> param = new LinkedHashMap<String, Object>();
		StringBuilder sql = new StringBuilder("from t in sgcim30.assets.management.ElectricityEnsureHotel ");
		sql.append("where t.belongTaskId ==: taskId ");
		param.put("taskId",taskId);
		
		// 表格分页
		StringBuilder sqlCount = new StringBuilder();
		sqlCount.append(sql);
		sqlCount.append(" select sqlCount = t.count() ");
		
		sql.append("select t then skip :skipCount then take :pageSize ");
		
		List<Map<String, Object>> rslt = null;
		int rsltCount = 0; // 统计表格展示条数
		try {
			int pageSize = Integer.parseInt(params.get("pageSize").toString()); // 获取分页参数
			int pageIndex = Integer.parseInt(params.get("pageIndex").toString());
			int skipCount = pageIndex * pageSize;
			rsltCount = (Integer) oafQuery.executeScalar(sqlCount.toString(), param);
			param.put("skipCount", skipCount);
			param.put("pageSize", pageSize);
			rslt = oafQuery.executeForList(sql.toString(), param);
		} catch (OafException e) {
			logger.error("保电概览--宾馆--查询数据异常", e);
		}
		
		// 返回查询结果
		Map<String, Object> rst = new HashMap<String, Object>();
		if(!ObjectUtils.isEmpty(params.get("pageSize"))){
			rst.put("data", rslt);
			rst.put("total", rsltCount);
		}else{
			rst.put("data", rslt);
		}
		List<Map<String, Object>> list = new ArrayList<>();
		list.add(rst);
		return list;
	}

	
	
	/************************************************************医院************************************************************/
	/**
	 * <p>描述：获取医院表格数据</p> 
	 * @param params
	 * @return data
	 * @author:王万胜
	 * 2017年4月28日 下午1:41:36
	 */
	@RequestMapping("/queryHospitalResult")
	public List<Map<String,Object>> queryHospitalResult(@RequestParam HashMap<String,String> params){
		String taskId = params.get("taskId");	 	// 保电任务ID
		String keyWords = params.get("keyWords");	// 关键字
		
		Map<String, Object> param = new LinkedHashMap<String, Object>();
		StringBuilder sql = new StringBuilder("from t in ( ");
		sql.append("from a in sgcim30.assets.management.ElectricityEnsureHospital ");
		sql.append("where a.belongTaskId ==: taskId ");
		param.put("taskId",taskId);
		sql.append("&& a.phoneNumber == null select a ");
		sql.append("union all ");
		sql.append("from b in sgcim30.assets.management.ElectricityEnsureHospital ");
		sql.append("where b.belongTaskId ==: taskId ");
		param.put("taskId",taskId);
		sql.append("&& b.phoneNumber != null select b ");
		sql.append(") where 1==1 ");
		
		if(keyWords != null){
			sql.append(" && (t.hospitalName.contains('"+keyWords+"') || t.linkman.contains('"+keyWords+"') || "
					+ "t.phoneNumber.contains('"+keyWords+"') || t.remark.contains('"+keyWords+"')) ");
		}
		
		// 表格分页
		StringBuilder sqlCount = new StringBuilder();
		sqlCount.append(sql);
		sqlCount.append(" select sqlCount = t.count() ");
		
		// sqlExport查出数据用来导出
		StringBuilder sqlExport = new StringBuilder();
		sqlExport.append(sql);
		sqlExport.append(" select t ");
		
		sql.append("select t then skip :skipCount then take :pageSize ");
		
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
				rslt = oafQuery.executeForList(sql.toString(), param);
			}else{
				rslt = oafQuery.executeForList(sqlExport.toString(), param);
			}
		} catch (OafException e) {
			if(!ObjectUtils.isEmpty(params.get("pageSize"))){
				logger.error("保电概览--医院--查询数据异常", e);
			}else{
				logger.error("保电概览--医院--导出数据异常", e);
			}
		}
		
		// 返回查询结果
		Map<String, Object> rst = new HashMap<String, Object>();
		if(!ObjectUtils.isEmpty(params.get("pageSize"))){
			rst.put("data", rslt);
			rst.put("total", rsltCount);
		}else{
			rst.put("data", rslt);
		}
		List<Map<String, Object>> list = new ArrayList<>();
		list.add(rst);
		return list;
	}
	
	
	
	/************************************************************运行内容（故障、缺陷）************************************************************/
	/**
	 * <p>描述：获取故障、缺陷数量</p> 
	 * @return data
	 * @author:王万胜
	 * 2017年6月21日 下午3:09:16
	 */
	@RequestMapping("/queryRunningContentCount")
	public Map<String,Object> queryRunningContentCount(@RequestParam HashMap<String,Object> params){
		String taskId = params.get("taskId").toString();
		Object belongOrg = params.get("belongOrg");
		String startTime = params.get("startTime").toString();
		String endTime = params.get("endTime").toString();
		Date startDate = null;
		Date endDate = null;
		int recoveredCount = 0;		// 故障-已恢复数量
		int disposingCount = 0;		// 故障-处理中数量
		int eliminatedCount = 0;	// 缺陷-已消缺数量
		int uneliminatedCount = 0;	// 缺陷-未消缺数量
		
		SimpleDateFormat formater=new SimpleDateFormat("yyyy-MM-dd HH:mm");
		try{
			if(!StringUtils.isEmpty(startTime)){
				startDate=formater.parse(startTime);
			}
			if(!StringUtils.isEmpty(endTime)){
				endDate=formater.parse(endTime);
			}
		}catch (ParseException e) {	
			logger.error("保电概览--获取故障、缺陷数量时时间转换异常", e);
		}
		
		// 获取站线
		List<Map<String, Object>> rslt1 = getCurrentSolRecordID(taskId, belongOrg);
		int length1 = rslt1.size();
		for(int i=0; i<length1; i++){
			String stationOrLineId = rslt1.get(i).get("stationLineID").toString();
			
			/*** 故障 ***/
			// 故障--已恢复
			Map<String, Object> par2 = new LinkedHashMap<String, Object>();
			StringBuilder sql2 = new StringBuilder("from t in sgcim30.assets.management.FaultTrip ");
			sql2.append("where t.stationOrLineId ==: stationOrLineId ");
			par2.put("stationOrLineId", stationOrLineId);
			sql2.append("&& t.recoveryDate >=: startDate ");
			par2.put("startDate", startDate);
			sql2.append("&& t.recoveryDate <=: endDate ");
			par2.put("endDate", endDate);
			sql2.append("select t ");
			List<Map<String, Object>> rslt2 = new ArrayList<Map<String, Object>>();
			try {
				rslt2 = oafQuery.executeForList(sql2.toString(), par2);
			} catch (OafException e) {
				logger.error("保电概览--查询故障--已恢复数量异常", e);
			}
			recoveredCount += rslt2.size();
			
			// 故障--处理中
			Map<String, Object> par3 = new LinkedHashMap<String, Object>();
			StringBuilder sql3 = new StringBuilder("from t in sgcim30.assets.management.FaultTrip ");
			sql3.append("where t.stationOrLineId ==: stationOrLineId ");
			par3.put("stationOrLineId", stationOrLineId);
			sql3.append("&& t.recoveryDate == null ");
			sql3.append("select t ");
			List<Map<String, Object>> rslt3 = new ArrayList<Map<String, Object>>();;
			try {
				rslt3 = oafQuery.executeForList(sql3.toString(), par3);
			} catch (OafException e) {
				logger.error("保电概览--查询故障--处理中数量异常", e);
			}
			disposingCount += rslt3.size();
			
			/*** 缺陷 ***/
			// 缺陷--已消缺
			Map<String, Object> par4 = new LinkedHashMap<String, Object>();
			StringBuilder sql4 = new StringBuilder("from t in sgcim30.assets.management.DefectState ");
			sql4.append("where t.powerStationId ==: stationOrLineId ");
			par4.put("stationOrLineId", stationOrLineId);
			sql4.append("&& t.correctiveDate >=: startDate ");
			par4.put("startDate", startDate);
			sql4.append("&& t.correctiveDate <=: endDate ");
			par4.put("endDate", endDate);
			sql4.append("&& (t.confirmStatus == 4 || t.confirmStatus == 5) ");
			sql4.append("select t ");
			List<Map<String, Object>> rslt4 = new ArrayList<Map<String, Object>>();;
			try {
				rslt4 = oafQuery.executeForList(sql4.toString(), par4);
			} catch (OafException e) {
				logger.error("保电概览--查询缺陷--已消缺数量异常", e);
			}
			eliminatedCount += rslt4.size();
			
			// 缺陷--未消缺
			Map<String, Object> par5 = new LinkedHashMap<String, Object>();
			StringBuilder sql5 = new StringBuilder("from t in sgcim30.assets.management.DefectState ");
			sql5.append("where t.powerStationId ==: stationOrLineId ");
			par5.put("stationOrLineId", stationOrLineId);
			sql5.append("&& (t.confirmStatus == 1 || t.confirmStatus == 2 || t.confirmStatus == 3) ");
			sql5.append("select t ");
			List<Map<String, Object>> rslt5 = new ArrayList<Map<String, Object>>();;
			try {
				rslt5 = oafQuery.executeForList(sql5.toString(), par5);
			} catch (OafException e) {
				logger.error("保电概览--查询缺陷--未消缺数量异常", e);
			}
			uneliminatedCount += rslt5.size();
		}
		
		Map<String, Object> count = new HashMap<String, Object>();
		count.put("recoveredCount", recoveredCount);
		count.put("disposingCount", disposingCount);
		count.put("eliminatedCount", eliminatedCount);
		count.put("uneliminatedCount", uneliminatedCount);
		return count;
	}
	
	/**
	 * <p>描述：获取故障信息</p> 
	 * @param params
	 * @return data
	 * @author:王万胜
	 * 2017年6月26日 下午6:32:26
	 */
	@RequestMapping("/queryFaultResult")
	public List<Map<String,Object>> queryFaultResult(@RequestParam HashMap<String,String> params){
		String taskId = params.get("taskId");	 	// 保电任务ID
		Object belongOrg = params.get("belongOrg");
		String startTime = params.get("startTime").toString();
		String endTime = params.get("endTime").toString();
		String flag = params.get("flag").toString();
		Date startDate = null;
		Date endDate = null;
		
		SimpleDateFormat formater=new SimpleDateFormat("yyyy-MM-dd HH:mm");
		try{
			if(!StringUtils.isEmpty(startTime)){
				startDate=formater.parse(startTime);
			}
			if(!StringUtils.isEmpty(endTime)){
				endDate=formater.parse(endTime);
			}
		}catch (ParseException e) {	
			logger.error("保电概览--获取故障、缺陷数量时时间转换异常", e);
		}
		
		// 获取站线
		List<Map<String, Object>> rslt1 = getCurrentSolRecordID(taskId, belongOrg);
		
		int length1 = rslt1.size();
		String solWhere = "";
		for(int i=0; i<length1; i++){
			if(i == 0){
				solWhere = "( t.stationOrLineId == '" + rslt1.get(i).get("stationLineID").toString() + "' ";
			}else{
				solWhere += ("|| t.stationOrLineId == '" + rslt1.get(i).get("stationLineID").toString() + "' ");
			}
		}
		
		// 查询故障信息
		Map<String, Object> par2= new LinkedHashMap<String, Object>();
		StringBuilder sql2 = new StringBuilder("from t in sgcim30.assets.management.FaultTrip where 1 == 1 ");
		if(!"".equals(solWhere)){	// 有关联站线
			sql2.append("&& " + solWhere + ") ");
		}else{	//无关联站线
			sql2.append("&& t.stationOrLineId == 'TheStationOrLineIdsIsNull' ");
		}
		
		if("recovered".equals(flag)){	// 已恢复
			sql2.append("&& t.recoveryDate >=: startDate ");
			par2.put("startDate", startDate);
			sql2.append("&& t.recoveryDate <=: endDate ");
			par2.put("endDate", endDate);
		}else if("disposing".equals(flag)){		// 处理中
			sql2.append("&& t.recoveryDate == null ");
		}
		
		// 表格分页
		StringBuilder sqlCount = new StringBuilder();
		sqlCount.append(sql2);
		sqlCount.append(" select sqlCount = t.count() ");
		
		sql2.append("select t then skip :skipCount then take :pageSize ");
		
		List<Map<String, Object>> rslt2 = null;
		int rsltCount = 0; // 统计表格展示条数
		try {
			int pageSize = Integer.parseInt(params.get("pageSize").toString()); // 获取分页参数
			int pageIndex = Integer.parseInt(params.get("pageIndex").toString());
			int skipCount = pageIndex * pageSize;
			rsltCount = (Integer) oafQuery.executeScalar(sqlCount.toString(), par2);
			par2.put("skipCount", skipCount);
			par2.put("pageSize", pageSize);
			rslt2 = oafQuery.executeForList(sql2.toString(), par2);
		} catch (OafException e) {
			logger.error("保电概览--医院--查询数据异常", e);
		}
		
		Constant faultTypeCst = ConstantRegistry.findConstant( FaultType.TYPE);
		Constant voltageLevelCst = ConstantRegistry.findConstant(VoltageLevel.TYPE);
		Constant faultPhaseCst = ConstantRegistry.findConstant(FaultPhase.TYPE);
		for (Map<String, Object> map : rslt2) {
			// 时间转换
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			if(map.get("faultTime") != null){
				String faultTime = sdf.format(map.get("faultTime"));
				map.put("faultTime", faultTime);
			}
			
			// 故障类型（原  原因分类）
			if (!ObjectUtils.isEmpty(map.get("faultType"))) {
				String faultTypeValue = map.get("faultType").toString();
				String faultTypeName = faultTypeCst.getFieldValueByItemValue(faultTypeValue, "name");
				map.put("faultType", faultTypeName);
			}

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
		}
		
		// 返回查询结果
		Map<String, Object> rst = new HashMap<String, Object>();
		if(!ObjectUtils.isEmpty(params.get("pageSize"))){
			rst.put("data", rslt2);
			rst.put("total", rsltCount);
		}else{
			rst.put("data", rslt2);
		}
		List<Map<String, Object>> list = new ArrayList<>();
		list.add(rst);
		return list;
	}
	
	/**
	 * <p>描述：获取缺陷信息</p> 
	 * @param params
	 * @return data
	 * @author:王万胜
	 * 2017年6月26日 下午7:49:07
	 */
	@RequestMapping("/queryDefectResult")
	public List<Map<String,Object>> queryDefectResult(@RequestParam HashMap<String,String> params){
		String taskId = params.get("taskId");	 	// 保电任务ID
		Object belongOrg = params.get("belongOrg");
		String startTime = params.get("startTime").toString();
		String endTime = params.get("endTime").toString();
		String flag = params.get("flag").toString();
		Date startDate = null;
		Date endDate = null;
		
		SimpleDateFormat formater=new SimpleDateFormat("yyyy-MM-dd HH:mm");
		try{
			if(!StringUtils.isEmpty(startTime)){
				startDate=formater.parse(startTime);
			}
			if(!StringUtils.isEmpty(endTime)){
				endDate=formater.parse(endTime);
			}
		}catch (ParseException e) {	
			logger.error("保电概览--获取故障、缺陷数量时时间转换异常", e);
		}
		
		// 获取站线
		List<Map<String, Object>> rslt1 = getCurrentSolRecordID(taskId, belongOrg);
		
		int length1 = rslt1.size();
		String solWhere = "";
		for(int i=0; i<length1; i++){
			if(i == 0){
				solWhere = "( t.powerStationId == '" + rslt1.get(i).get("stationLineID").toString() + "' ";
			}else{
				solWhere += ("|| t.powerStationId == '" + rslt1.get(i).get("stationLineID").toString() + "' ");
			}
		}
		
		// 查询缺陷信息
		Map<String, Object> par2= new LinkedHashMap<String, Object>();
		StringBuilder sql2 = new StringBuilder("from t in sgcim30.assets.management.DefectState where 1 == 1 ");
		if(!"".equals(solWhere)){	// 有关联站线
			sql2.append("&& " + solWhere + ") ");
		}else{	//无关联站线
			sql2.append("&& t.powerStationId == 'TheStationOrLineIdsIsNull' ");
		}
		
		if("eliminated".equals(flag)){	// 已消缺
			sql2.append("&& t.correctiveDate >=: startDate ");
			par2.put("startDate", startDate);
			sql2.append("&& t.correctiveDate <=: endDate ");
			par2.put("endDate", endDate);
			sql2.append("&& (t.confirmStatus == 4 || t.confirmStatus == 5) ");
		}else if("uneliminated".equals(flag)){		// 未消缺
			sql2.append("&& (t.confirmStatus == 1 || t.confirmStatus == 2 || t.confirmStatus == 3) ");
		}
		
		// 表格分页
		StringBuilder sqlCount = new StringBuilder();
		sqlCount.append(sql2);
		sqlCount.append(" select sqlCount = t.count() ");
		
		sql2.append("select t then skip :skipCount then take :pageSize ");
		
		List<Map<String, Object>> rslt2 = null;
		int rsltCount = 0; // 统计表格展示条数
		try {
			int pageSize = Integer.parseInt(params.get("pageSize").toString()); // 获取分页参数
			int pageIndex = Integer.parseInt(params.get("pageIndex").toString());
			int skipCount = pageIndex * pageSize;
			rsltCount = (Integer) oafQuery.executeScalar(sqlCount.toString(), par2);
			par2.put("skipCount", skipCount);
			par2.put("pageSize", pageSize);
			rslt2 = oafQuery.executeForList(sql2.toString(), par2);
		} catch (OafException e) {
			logger.error("保电概览--医院--查询数据异常", e);
		}
		
		Constant voltageLevelCst = ConstantRegistry.findConstant(VoltageLevel.TYPE);
		Constant defectPropertiesCst = ConstantRegistry.findConstant(DefectQuale.TYPE);
		Constant defectStateCst = ConstantRegistry.findConstant(DefectState.TYPE);
		for (Map<String, Object> map : rslt2) {
			// 时间转换
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			if(map.get("discoveredDate") != null){
				String discoveredTime = sdf.format(map.get("discoveredDate"));
				map.put("discoveredDate", discoveredTime);
			}
			
			// 电压等级
			if (!ObjectUtils.isEmpty(map.get("voltageClass"))) {
				int voltageLevelValue = ((Integer) map.get("voltageClass")).intValue();
				String voltageLevelName = voltageLevelCst.getFieldValueByItemValue(voltageLevelValue, "name");
				map.put("voltageClass", voltageLevelName);
			}
			
			// 缺陷性质
			if (!ObjectUtils.isEmpty(map.get("defectProperties"))) {
				String defectPropertiesValue = map.get("defectProperties").toString();
				String defectPropertiesName = defectPropertiesCst.getFieldValueByItemValue(defectPropertiesValue, "name");
				map.put("defectProperties", defectPropertiesName);
			}
			
			// 缺陷状态
			if (!ObjectUtils.isEmpty(map.get("defectState"))) {
				String defectStateValue = map.get("defectState").toString();
				String defectStateName = defectStateCst.getFieldValueByItemValue(defectStateValue, "name");
				map.put("defectState", defectStateName);
			}
		}
		
		// 返回查询结果
		Map<String, Object> rst = new HashMap<String, Object>();
		if(!ObjectUtils.isEmpty(params.get("pageSize"))){
			rst.put("data", rslt2);
			rst.put("total", rsltCount);
		}else{
			rst.put("data", rslt2);
		}
		List<Map<String, Object>> list = new ArrayList<>();
		list.add(rst);
		return list;
	}
	
	/**
	 * <p>描述：获取当前时间有保电级别的保电站线记录ID</p>  
	 * @author:王万胜
	 * 2017年8月2日 上午11:19:03
	 */
	private List<Map<String, Object>> getCurrentSolRecordID(String taskId, Object belongOrg){
		Date today = new Date();
		
		// 获取所有保电任务、组织机构关联的保电站线记录ID
		Map<String, Object> par1 = new LinkedHashMap<String, Object>();
		StringBuilder sql1 = new StringBuilder("from t in sgcim30.assets.management.StationLineEnsure ");
		sql1.append("where t.belongTaskId ==: taskId ");
		par1.put("taskId", taskId);
		if(!ObjectUtils.isEmpty(belongOrg)){
			sql1.append("&& t.orgBelongID ==: belongOrg ");
			par1.put("belongOrg", belongOrg);
		}
		sql1.append("select t.id ");
		List<Map<String, Object>> rslt1 = null;
		try {
			rslt1 = oafQuery.executeForList(sql1.toString(), par1);
		} catch (OafException e) {
			logger.error("保电概览--查询保电站线记录ID异常", e);
		}
		int length1 = rslt1.size();
		
		// 获取上述获得的记录中当前时间有保电级别的保电站线记录ID
		List<Map<String, Object>> rslt2 = new ArrayList<Map<String, Object>>();
		if(length1 > 0){
			Map<String, Object> par2 = new LinkedHashMap<String, Object>();
			StringBuilder sql2 = new StringBuilder("from t in sgcim30.assets.management.ElectricityEnsurePhase ");
			sql2.append("where t.startTime <=: today ");
			par2.put("today", today);
			sql2.append("&& t.endTime >=: today ");
			par2.put("today", today);
			
			String whereStr2 = "";
			for(int i=0; i<length1; i++){
				if(i == 0){
					whereStr2 = "&& (t.belongTaskId == '" + rslt1.get(i).get("id") + "' ";
				}else{
					whereStr2 += "|| t.belongTaskId == '" + rslt1.get(i).get("id") + "' ";
				}
			}
			
			sql2.append(whereStr2 + ") ");
			sql2.append("select t.belongTaskId ");
			
			try {
				rslt2 = oafQuery.executeForList(sql2.toString(), par2);
			} catch (OafException e) {
				logger.error("保电概览--获取保电站线记录中当前时间有保电级别的保电站线记录ID异常", e);
			}
		}
		
		int length2 = rslt2.size();
		List<Map<String, Object>> rslt3 = new ArrayList<Map<String, Object>>();
		if(length2 > 0){
			Map<String, Object> par3 = new LinkedHashMap<String, Object>();
			StringBuilder sql3 = new StringBuilder("from t in sgcim30.assets.management.StationLineEnsure ");
			
			String whereStr3 = "";
			for(int i=0; i<length2; i++){
				if(i == 0){
					whereStr3 = "where t.id == '" + rslt2.get(i).get("belongTaskId") + "' ";
				}else{
					whereStr3 += "|| t.id == '" + rslt2.get(i).get("belongTaskId") + "' ";
				}
			}
			
			sql3.append(whereStr3);
			sql3.append("select t.stationLineID ");

			try {
				rslt3 = oafQuery.executeForList(sql3.toString(), par3);
			} catch (OafException e) {
				logger.error("保电概览--获取保电站线记录中的站线ID异常", e);
			}
		}
		
		// 站线ID去重
		int length3 = rslt3.size();
		List<Map<String, Object>> rslt4 = new ArrayList<Map<String, Object>>();
		for(int i=0; i<length3; i++){
			if(rslt4.size() == 0){
				rslt4.add(rslt3.get(i));
			}else{
				boolean have = false;
				for(int j=0; j<rslt4.size(); j++){
					if(rslt4.get(j).equals(rslt3.get(i))){
						have = true;
						break;
					}
				}
				
				if(!have){
					rslt4.add(rslt3.get(i));
				}
			}
		}
		
		return rslt4;
	}
	
	
	
	/************************************************************公共************************************************************/
	/**
	 * <p>描述：获取资源保障的数量（人员、车辆、驻点、宾馆、医院）</p> 
	 * @return data
	 * @author:王万胜
	 * 2017年6月21日 下午3:09:16
	 */
	@RequestMapping("/queryResourceCount")
	public Map<String,Object> queryResourceCount(@RequestParam HashMap<String,Object> params){
		String taskId = params.get("taskId").toString();
		Object belongOrg = params.get("belongOrg");
		
		Map<String, Object> par = new LinkedHashMap<String, Object>();
		
		// 人员
		StringBuilder sql = new StringBuilder("from a in sgcim30.assets.management.ElectricityEnsurePerson ");
		sql.append("where a.belongTaskId ==: taskId ");
		par.put("taskId",taskId);
		if(!ObjectUtils.isEmpty(belongOrg)){
			sql.append("&& a.belongOrg ==: belongOrg ");
			par.put("belongOrg", belongOrg);
		}
		sql.append("select count=a.count() ");
		sql.append("union all ");
		
		// 车辆
		sql.append("from b in sgcim30.assets.management.ElectricityEnsureVehicle ");
		sql.append("where b.belongTaskId ==: taskId ");
		par.put("taskId",taskId);
		if(!ObjectUtils.isEmpty(belongOrg)){
			sql.append("&& b.belongOrg ==: belongOrg ");
			par.put("belongOrg", belongOrg);
		}
		sql.append("select count=b.count() ");
		sql.append("union all ");
		
		// 驻点
		sql.append("from c in sgcim30.assets.management.ElectricityEnsureStation ");
		sql.append("where c.belongTaskId ==: taskId ");
		par.put("taskId",taskId);
		if(!ObjectUtils.isEmpty(belongOrg)){
			sql.append("&& c.belongOrgID ==: belongOrg ");
			par.put("belongOrg", belongOrg);
		}
		sql.append("select count=c.count() ");
		sql.append("union all ");
		
		// 宾馆
		sql.append("from d in sgcim30.assets.management.ElectricityEnsureHotel ");
		sql.append("where d.belongTaskId ==: taskId ");
		par.put("taskId",taskId);
		sql.append("select count=d.count() ");
		sql.append("union all ");
		
		// 医院
		sql.append("from e in sgcim30.assets.management.ElectricityEnsureHospital ");
		sql.append("where e.belongTaskId ==: taskId ");
		par.put("taskId",taskId);
		sql.append("select count=e.count() ");
		
		List<Map<String, Object>> rslt = null;
		try {
			rslt = oafQuery.executeForList(sql.toString(), par);
		} catch (OafException e) {
			logger.error("保电概览--查询各阶段记录数量异常", e);
		}
		
		// 若人员数量为0，从日报--保电过程表格中取数据
		int personCount = 0;
		if((float)rslt.get(0).get("count") == 0.0){
			Map<String, Object> par1 = new LinkedHashMap<String, Object>();
			StringBuilder sql1 = new StringBuilder("from t in sgcim30.assets.management.ElectricityEnsureProcess ");
			sql1.append("where t.belongTaskId ==: taskId ");
			par1.put("taskId", taskId);
			sql1.append("&& t.unit ==: belongOrg ");
			par1.put("belongOrg", belongOrg);
			sql1.append("select count = t.personCount.summary() ");
			
			List<Map<String, Object>> rslt1 = null;
			try {
				rslt1 = oafQuery.executeForList(sql1.toString(), par1);
			} catch (OafException e) {
				logger.error("保电概览--查询各阶段记录数量异常", e);
			}
			personCount = (int)rslt1.get(0).get("count");
		}
		
		// 若车辆数量为0，从日报--保电过程表格中取数据
		int carCount = 0;
		if((float)rslt.get(1).get("count") == 0.0){
			Map<String, Object> par2 = new LinkedHashMap<String, Object>();
			StringBuilder sql2 = new StringBuilder("from t in sgcim30.assets.management.ElectricityEnsureProcess ");
			sql2.append("where t.belongTaskId ==: taskId ");
			par2.put("taskId", taskId);
			sql2.append("&& t.unit ==: belongOrg ");
			par2.put("belongOrg", belongOrg);
			sql2.append("select count = t.vehicleCount.summary() ");
			
			List<Map<String, Object>> rslt2 = null;
			try {
				rslt2 = oafQuery.executeForList(sql2.toString(), par2);
			} catch (OafException e) {
				logger.error("保电概览--查询各阶段记录数量异常", e);
			}
			carCount = (int)rslt2.get(0).get("count");
		}
		
		Map<String, Object> count = new HashMap<String, Object>();
		if((float)rslt.get(0).get("count") == 0.0){		// 人员
			count.put("personCount", personCount);
		}else{
			count.put("personCount", rslt.get(0).get("count"));
		}
		
		if((float)rslt.get(1).get("count") == 0.0){		// 车辆
			count.put("carCount", carCount);
		}else{
			count.put("carCount", rslt.get(1).get("count"));
		}
		
		count.put("pointCount", rslt.get(2).get("count"));		// 驻点
		count.put("hotelCount", rslt.get(3).get("count"));		// 宾馆
		count.put("hospitalCount", rslt.get(4).get("count"));	// 医院
		return count;
	}
	
	/**
	 * <p>描述：线路电站统计</p> 
	 * @param params
	 * @return list
	 * @author:朱建衡
	 * 2017年6月26日 上午9:25:43
	 */
	@RequestMapping("/queryStationOrLineChartResult")
	public Map<String,List<?>> queryStationOrLineChartResult(@RequestParam HashMap<String, Object> params){
		Date time = Calendar.getInstance().getTime();
		String taskId = params.get("taskId").toString();	// 保电任务ID
		Object belongOrg = params.get("belongOrg");		// 所属组织
		Object stationLineType = params.get("stationLineType"); // 01线路  02电站 
		
		Map<String, Object> par = new LinkedHashMap<String, Object>();
		StringBuilder sqlVol = new StringBuilder();
		StringBuilder sqlId = new StringBuilder(); 
		StringBuilder sql = new StringBuilder("from t in (");
		sql.append("from a in sgcim30.assets.management.StationLineEnsure ");
		sql.append("join b in( ");
		sql.append("from c in sgcim30.assets.management.ElectricityEnsurePhase where c.startTime <=: stime && c.endTime >=: etime select c.belongTaskId,c.ensureLevel");
		par.put("stime", time);
		par.put("etime", time);
		sql.append(") on a.id equals b.belongTaskId select a.belongTaskId,a.orgBelongID,a.stationLineType,a.voltageLevel,a.stationLineID,b.ensureLevel ");
		sql.append(") where t.belongTaskId ==: taskId ");
		par.put("taskId",taskId);
		if(!ObjectUtils.isEmpty(belongOrg)){
			sql.append("&& t.orgBelongID ==: belongOrg ");
			par.put("belongOrg", belongOrg);
		}
		sql.append("&& t.stationLineType ==:stationLineType "); // 电站
		par.put("stationLineType", stationLineType);
		sqlVol.append(sql);
		sqlVol.append("groupby t.voltageLevel orderby t.voltageLevel descending select t.voltageLevel");
		sqlId.append(sql);
		sqlId.append("select t.stationLineID");
		sql.append("&& t.ensureLevel ==: ensureLevel ");
		sql.append("groupby t.voltageLevel orderby t.voltageLevel descending select t.voltageLevel, count=t.count()");

		List<Map<String, Object>> rsltVol = null;
		List<Map<String, Object>> rsltId = null;
		List<Map<String, Object>> rslt1 = null;
		List<Map<String, Object>> rslt2 = null;
		List<Map<String, Object>> rslt3 = null;
		List<Map<String, Object>> rslt4 = null;
		try {
			rsltVol = oafQuery.executeForList(sqlVol.toString(), par); //[{voltageLevel=35}]
			rsltId = oafQuery.executeForList(sqlId.toString(), par);
			par.put("ensureLevel", "1");
			rslt1 = oafQuery.executeForList(sql.toString(), par);
			par.put("ensureLevel", "2");
			rslt2 = oafQuery.executeForList(sql.toString(), par);
			par.put("ensureLevel", "3");
			rslt3 = oafQuery.executeForList(sql.toString(), par);
			par.put("ensureLevel", "4");
			rslt4 = oafQuery.executeForList(sql.toString(), par);
		} catch (OafException e) {
			logger.error("保电任务--线路--柱状图统计数据异常", e);
		}
		Map<String,List<?>> mapList = new HashMap<>();

		List<Map<String,Object>> xAxislist = new ArrayList<>();
		List<List<Object>> seriesData = new ArrayList<>();
		List<String> idData = new ArrayList<>();
		String startionLineId = "";
		for(int i=0;i<rsltId.size();i++){
			String id = (String)rsltId.get(i).get("stationLineID");
			startionLineId += ","+id; 
		}
		idData.add(startionLineId);
		
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
				seriesList1.add(0);
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
					seriesList1.add(0);
				}
			}
			
			if(rslt2.size() == 0){
				seriesList2.add(0);
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
					seriesList2.add(0);
				}
			}
			if(rslt3.size() == 0){
				seriesList3.add(0);
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
					seriesList3.add(0);
				}
			}
			if(rslt4.size() == 0){
				seriesList4.add(0);
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
					seriesList4.add(0);
				}
			}		
		}
		seriesData.add(seriesList1);
		seriesData.add(seriesList2);
		seriesData.add(seriesList3);
		seriesData.add(seriesList4);

		mapList.put("xAxisData", xAxislist);
		mapList.put("seriesData", seriesData);
		mapList.put("idData", idData);
		
		
		// 返回查询结果
		return mapList;
	}
}


