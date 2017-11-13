package nariis.mcsas.management.powersupply.business.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cst.constants.management.PowerLever;
import cst.constants.management.RegulationsType;
import cst.constants.management.TaskSource;
import cst.constants.management.VehicleType;
import cst.constants.management.VoltageLevel;
import nariis.falcon.oaf.OafCommands;
import nariis.falcon.oaf.OafException;
import nariis.falcon.oaf.OafQuery;
import nariis.falcon.oaf.models.constant.Constant;
import nariis.falcon.oaf.models.constant.ConstantItem;
import nariis.falcon.oaf.models.constant.ConstantRegistry;
import nariis.mcsas.commons.cache.IOrgnizationInfo;
import nariis.mcsas.commons.utils.ConvertUtils;
import nariis.mcsas.management.common.util.CommonExcelService;
import nariis.mcsas.management.common.util.CommonQueryUtils;
import nariis.mcsas.management.common.util.CommonWorkLogService;
import sgcim30.assets.accounts.AstTransformer;
import sgcim30.assets.management.ElectricityEnsure;
import sgcim30.assets.management.ElectricityEnsureHospital;
import sgcim30.assets.management.ElectricityEnsureHotel;
import sgcim30.assets.management.ElectricityEnsureLog;
import sgcim30.assets.management.ElectricityEnsureOrg;
import sgcim30.assets.management.ElectricityEnsurePerson;
import sgcim30.assets.management.ElectricityEnsurePhase;
import sgcim30.assets.management.ElectricityEnsureProcess;
import sgcim30.assets.management.ElectricityEnsureStation;
import sgcim30.assets.management.ElectricityEnsureUser;
import sgcim30.assets.management.ElectricityEnsureVehicle;
import sgcim30.assets.management.StationLineEnsure;
import sgcim30.assets.management.WorkLog;

/**
 * <p>ClassName:PowerSupplyGuaranteeService，</p>
 * <p>描述:保电任务各方法</p>
 * @author 王万胜
 * @date 2017年3月23日下午3:05:00
 * @version 
 */
@RestController
@RequestMapping("/powerSupplyGuarantee")
public class PowerSupplyGuaranteeService {

	@Autowired
	OafQuery oafQuery;
	
	@Autowired
	OafCommands oafCommands;
	
	@Autowired
	CommonExcelService commonExcelService;
	
	@Autowired
	CommonWorkLogService commonWorkLogService;
	
	private static final Logger logger = LogManager.getLogger(PowerSupplyGuaranteeService.class);
	
	/************************************************************基础信息************************************************************/
	/**
	 * <p>描述：各阶段记录数量</p> 
	 * @return rslt
	 * @author:王万胜
	 * 2017年3月14日 下午7:54:01
	 */
	@RequestMapping("/gridsCountResult")
	public List<Map<String,Object>> gridsCountResult(){
		StringBuilder sql = new StringBuilder(" from t in sgcim30.assets.management.ElectricityEnsure ");
		sql.append(" groupby t.status orderby t.status select t.status,count=t.count() ");
		List<Map<String, Object>> rslt = null;
		try {
			rslt = oafQuery.executeForList(sql.toString());
		} catch (OafException e) {
			logger.error("保电任务--查询各阶段记录数量异常", e);
		}
		return rslt;
	}
	
	/**
	 * <p>描述：查询基础信息主表数据</p> 
	 * @param params
	 * @return List<Map<String,Object>>
	 * @author:王万胜
	 * 2017年3月9日 下午4:00:29
	 */
	@RequestMapping("/queryPowerSupplyGuaranteeResult")
	public List<Map<String,Object>> queryPowerSupplyGuaranteeResult(@RequestParam HashMap<String,Object> params){
		String tabType = params.get("tabType").toString();	 // tab类型
		String confirmStatus = "";
		switch (tabType) {
		case "addTab":
			confirmStatus = "1";
			break;
		case "followTab":
			confirmStatus = "2";
			break;
		case "closedTab":
			confirmStatus = "3";
			break;
		case "filedTab":
			confirmStatus = "4";
			break;
		default:
			break;
		}
		
		Date beginTime1 = null;			// 开始时间1
		Date beginTime2 = null;			// 开始时间2
		Date endTime1 = null;			// 结束时间1
		Date endTime2 = null;			// 结束时间2
		String responsibleDepartment;	// 负责单位
		String taskSource;				// 任务来源
		String powerLevel;				// 保电级别
		String keyWords;				// 关键字
		
		boolean haveKeyWords = params.containsKey("keyWords");
		
		Map<String, Object> param = new LinkedHashMap<String, Object>();
		StringBuilder sql = new StringBuilder(" from t in sgcim30.assets.management.ElectricityEnsure where t.status ==: confirmStatus ");
		param.put("confirmStatus", confirmStatus);
		
		if(!haveKeyWords){
			SimpleDateFormat formater=new SimpleDateFormat("yyyy-MM-dd HH:mm");
			try{
				if(!"".equals(params.get("beginTime1").toString())){
					beginTime1=formater.parse(params.get("beginTime1").toString());
				}
				if(!"".equals(params.get("beginTime2").toString())){
					beginTime2=formater.parse(params.get("beginTime2").toString());
				}
				if(!"".equals(params.get("endTime1").toString())){
					endTime1=formater.parse(params.get("endTime1").toString());
				}
				if(!"".equals(params.get("endTime2").toString())){
					endTime2=formater.parse(params.get("endTime2").toString());
				}
			}catch (ParseException e) {	
				e.printStackTrace();
			}
			
			responsibleDepartment = params.get("responsibleDepartmentId").toString();
			taskSource = params.get("taskSource").toString();
			powerLevel = params.get("powerLevel").toString();
			
			// 开始时间
			if(!"".equals(beginTime1) && !"null".equals(beginTime1) && beginTime1 != null){
				sql.append("&& t.startTime >=: beginTime1 ");
				param.put("beginTime1",beginTime1);
			}
			if(!"".equals(beginTime2) && !"null".equals(beginTime2) && beginTime2 != null){
				sql.append("&& t.startTime <=: beginTime2 ");
				param.put("beginTime2",beginTime2);
			}
			
			// 结束时间
			if(!"".equals(endTime1) && !"null".equals(endTime1) && endTime1 != null){
				sql.append("&& t.endTime >=: endTime1 ");
				param.put("endTime1",endTime1);
			}
			if(!"".equals(endTime2) && !"null".equals(endTime2) && endTime2 != null){
				sql.append("&& t.endTime <=: endTime2 ");
				param.put("endTime2",endTime2);
			}
			
			// 负责单位
			if(!"".equals(responsibleDepartment)){
				sql.append(" && t.dutyUnitID ==: responsibleDepartment ");
				param.put("responsibleDepartment",responsibleDepartment);
			}
			
			// 任务来源
			if(!"".equals(taskSource)){
				sql.append(" && t.taskSource ==: taskSource ");
				param.put("taskSource",taskSource);
			}
			
			// 保电级别
			if(!"".equals(powerLevel)){
				sql.append(" && t.ensureLevel ==: powerLevel ");
				param.put("powerLevel",powerLevel);
			}
		}else{
			keyWords = params.get("keyWords").toString();
			
			// 保电级别模糊查询汉字与编码转换方法
			String powerLevelKeyWords = "";
			if("1".equals(keyWords) || "2".equals(keyWords) || "3".equals(keyWords) || "4".equals(keyWords)){
				powerLevelKeyWords = "number";
			}else if(!"".equals(keyWords) && !"级".equals(keyWords)){
				String [] powerLevelArr = {"特级", "一级", "二级", "三级"};
				for(int i=0; i<4; i++){
					if(powerLevelArr[i].indexOf(keyWords) != -1){
						powerLevelKeyWords = ""+(i+1);
						break;
					}else{
						powerLevelKeyWords = keyWords;
					}
				}
			}
			
			if(!"".equals(keyWords)){
				sql.append(" && (t.theme.contains('"+keyWords+"') || t.dutyUnitName.contains('"+keyWords+"') || "
						+ "t.ensureLevel.contains('"+powerLevelKeyWords+"') || t.ensureDetails.contains('"+keyWords+"')) ");
			}
		}
		
		// 表格分页
		StringBuilder sqlCount = new StringBuilder();
		sqlCount.append(sql);
		sqlCount.append(" select sqlCount = t.count() ");
		
		// sqlExport查出数据用来导出
		StringBuilder sqlExport = new StringBuilder();
		sqlExport.append(sql);
		sqlExport.append(" select t ");
		
		switch(confirmStatus){
			case "1":
				sql.append("orderby t.ensureLevel ascendting, t.startTime ascending ");
				break;
			case "2":
				sql.append("orderby t.ensureLevel ascendting, t.startTime descending ");
				break;
			case "3":
				sql.append("orderby t.endTime descending ");
				break;
			case "4":
				sql.append("orderby t.endTime descending ");
				break;
			default:
				break;
		}
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
//				rslt = oafQuery.executeForList(sql.toString(), param);
				
				param.put("pageIndex", pageIndex);
				param.put("total", rsltCount);
				param.put("recordId", params.get("recordId"));
				rslt = commonQuery.executeQueryForAppointed(sql.toString(), param);
			}else{
				rslt = oafQuery.executeForList(sqlExport.toString(), param);
			}
		} catch (OafException e) {
			if(!ObjectUtils.isEmpty(params.get("pageSize"))){
				logger.error("保电任务--查询数据异常", e);
			}else{
				logger.error("保电任务--导出数据异常", e);
			}
		}
		
		
		// 编码转换为汉字显示
		if(rslt != null){
			for(Map<String,Object> map : rslt){
				// 任务来源
				Constant taskSourceConstant = ConstantRegistry.findConstant(TaskSource.TYPE);
				if(!"".equals(map.get("taskSource")) && map.get("taskSource") != null){
					String taskSourceValue = (String) map.get("taskSource");
					String taskSourceName = taskSourceConstant.getFieldValueByItemValue(taskSourceValue, "name");
					map.put("taskSource", taskSourceName);
				}
				
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
		Map<String, Object> rst = new HashMap<String, Object>();
		if(!ObjectUtils.isEmpty(params.get("pageSize"))){
			rst.put("data", rslt);
			rst.put("total", rsltCount);
			rst.put("pageIndex", param.get("pageIndex"));
		}else{
			rst.put("data", rslt);
		}
		List<Map<String, Object>> list = new ArrayList<>();
		list.add(rst);
		return list;
	}
	
	/**
	 * <p>描述：获取选定的一条保电任务的基础信息</p> 
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
			logger.error("保电任务--获取选定的一条保电任务的基础信息异常", e);
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
			logger.error("保电任务--获取基础信息保电阶段表格数据异常", e);
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
	 * <p>描述：新增基础信息数据</p> 
	 * @param params 
	 * @return id
	 * @author:王万胜
	 * 2017年3月14日 上午9:43:23
	 * @throws ParseException 
	 */
	@RequestMapping("/insertPowerSupplyGuaranteeResult")
	public HashMap<String,Object> insertPowerSupplyGuaranteeResult(@RequestBody HashMap<String,Object> params) throws ParseException{
		Date startTime = null;		// 开始时间
		Date endTime = null;		// 结束时间
		Date updateDateTime = new Date();	// 最后更新时间
		Date recordDate = new Date();		// 记录时间
		
		Object start = params.get("startTime");
		Object end = params.get("endTime");
		
		SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		try{
			if(!"".equals(start.toString())){
				startTime = formater.parse(start.toString());
			}
			if(!"".equals(end.toString())){
				endTime = formater.parse(end.toString());
			}
		}catch (ParseException e) {	
			e.printStackTrace();
		}
		
		String taskId = UUID.randomUUID().toString();
		params.put("id", taskId);
		params.put("startTime", startTime);
		params.put("endTime", endTime);;
		params.put("updateDateTime", updateDateTime);
		params.put("recordDate", recordDate);
		params.put("status", "01");
		params.put(nariis.falcon.oaf.Constant.CLASS_KEY, ElectricityEnsure.class.getName());
		
		try {
			oafCommands.insert(params);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		// 保电阶段表格数据插入
		List<Map<String, Object>> gridList = (List<Map<String, Object>>) params.get("gridData");
		int gridLength = gridList.size();
		Map<String, Object> data = new HashMap<String, Object>();
		Date startTime2 = null;
		Date endTime2 = null;
		Object startDate2 = null;
		Object endDate2 = null;
		for(int i=0; i<gridLength; i++){	// 循环插入数据
			data = gridList.get(i);
			startDate2 = data.get("startTime");
			endDate2 = data.get("endTime");
			try{
				if(!ObjectUtils.isEmpty(startDate2)){
					startTime2 = formater.parse(startDate2.toString());
					data.put("startTime", startTime2);
				}
				if(!ObjectUtils.isEmpty(endDate2)){
					endTime2 = formater.parse(endDate2.toString());
					data.put("endTime", endTime2);
				}
			}catch (ParseException e) {	
				logger.error("保电任务--新增基础信息--保电阶段时间转换异常", e);
			}
			data.put("id", UUID.randomUUID().toString());
			data.put("belongTaskId", taskId);
			data.put("recordDate", recordDate);
			data.put("updateDateTime", updateDateTime);
			data.put(nariis.falcon.oaf.Constant.CLASS_KEY, ElectricityEnsurePhase.class.getName());
			try {
				oafCommands.insert(data);
			} catch (Exception e) {
				logger.error("保电任务--新增基础信息--保电阶段异常", e);
			}
		}
		
		// 插入保电组织记录
		Object theme = params.get("theme");
		String orgName = theme.toString() + "总部";
		HashMap<String,Object> params3 = new HashMap<String,Object>();
		params3.put("id", UUID.randomUUID().toString());
		params3.put("belongTaskId", taskId);
		params3.put("orgName", orgName);
		params3.put("updateDateTime", updateDateTime);
		params3.put("recordDate", recordDate);
		params3.put(nariis.falcon.oaf.Constant.CLASS_KEY, ElectricityEnsureOrg.class.getName());
		try {
			oafCommands.insert(params3);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		// 值班日志
		String dutyUnitID = params.get("dutyUnitID").toString();
		String dutyUnitName = params.get("dutyUnitName").toString();
		String leaderRequest = params.get("leaderRequest").toString();
		String ensureLevel = params.get("ensureLevel").toString();
		switch(ensureLevel){
			case "1":
				ensureLevel = "特级";
				break;
			case "2":
				ensureLevel = "一级";
				break;
			case "3":
				ensureLevel = "二级";
				break;
			case "4":
				ensureLevel = "三级";
				break;
			default:
				break;
		}
		
		WorkLog workLog = new WorkLog();
		workLog.setId(UUID.randomUUID().toString());
		workLog.setBusinessID(taskId);
		workLog.setUnitID(dutyUnitID);
		workLog.setUnitName(dutyUnitName);
		workLog.setStartTime(startTime);
		workLog.setEndTime(endTime);
		workLog.setRecordDate(recordDate);
		workLog.setUpdateDateTime(updateDateTime);
		workLog.setStatus(1);
		workLog.setRecordType("22");
		
		StringBuilder logContent = new StringBuilder();
		if(!StringUtils.isEmpty(leaderRequest)){
			logContent.append("("+start+") "+ensureLevel+" "+theme+" ("+end+") "+leaderRequest);
		}
		workLog.setRecordDetails(logContent.toString());
		commonWorkLogService.saveWorkLog(workLog);
		
		// 返回新增的保电任务ID
		HashMap<String,Object> list = new HashMap<String,Object>();
		list.put("id", taskId);
		return list;
	}
	
	/**
	 * <p>描述：编辑数据</p> 
	 * @param params 
	 * @author:王万胜
	 * 2017年3月14日 上午9:43:23
	 */
	@RequestMapping("/updatePowerSupplyGuaranteeResult")
	public void updatePowerSupplyGuaranteeResult(@RequestBody HashMap<String,Object> params){
		String flag = (String) params.get("flag");
		if("statusChange".equals(flag)){	// 点击跟踪、闭环、归档按钮修改记录状态
			Date time = new Date();	// 当前时间
			
			String confirmStatus = params.get("status").toString();
			if("2".equals(confirmStatus)){
				params.put("trackDate", time);
			}else if("3".equals(confirmStatus)){
				params.put("closedLoopDate", time);
			}else if("4".equals(confirmStatus)){
				params.put("filingDate", time);
			}
			
			String ids = (String) params.get("ids");
			String[] idsArr = ids.split(",");
			int length = idsArr.length;
			
			for(int i=0; i<length; i++){
				params.put("id", idsArr[i]);
				params.put(nariis.falcon.oaf.Constant.CLASS_KEY, ElectricityEnsure.class.getName());
				
				try {
					oafCommands.update(params);
				} catch (Exception e) {
					e.printStackTrace();
				}
				
				// 值班日志
				WorkLog workLog = (WorkLog)commonWorkLogService.getBeanByID(idsArr[i]);
				if(workLog != null){
					workLog.setUpdateDateTime(time);
					workLog.setStatus(Integer.parseInt(confirmStatus));
					commonWorkLogService.updateWorkLog(workLog);
				}
			}
		}else{	// 点击编辑按钮修改记录
			String taskId = params.get("id").toString();
			
			/**********修改基础信息**********/
			Date startTime = null;		// 开始时间
			Date endTime = null;		// 结束时间
			Date updateDateTime = new Date();	// 最后更新时间
			Date recordDate = new Date();		// 记录时间
			
			Object start = params.get("startTime");
			Object end = params.get("endTime");
			
			SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			try{
				if(!"".equals(start.toString())){
					startTime = formater.parse(start.toString());
				}
				if(!"".equals(end.toString())){
					endTime = formater.parse(end.toString());
				}
			}catch (ParseException e) {	
				e.printStackTrace();
			}
			
			params.put("startTime", startTime);
			params.put("endTime", endTime);;
			params.put("updateDateTime", updateDateTime);
			params.put("recordDate", recordDate);
			params.put(nariis.falcon.oaf.Constant.CLASS_KEY, ElectricityEnsure.class.getName());
			
			try {
				oafCommands.update(params);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			/**********修改保电阶段**********/
			// 保电阶段表格数据修改（先删旧数据，后插新数据）
			// 查询旧数据ID
			HashMap<String,Object> params0 = new HashMap<String,Object>();
			StringBuilder sql = new StringBuilder("from t in sgcim30.assets.management.ElectricityEnsurePhase ");
			sql.append("where t.belongTaskId ==: taskId ");
			params0.put("taskId",taskId);
			sql.append("select t.id ");
			
			List<Map<String, Object>> rslt = null;
			try {
				rslt = oafQuery.executeForList(sql.toString(), params0);
			} catch (OafException e) {
				logger.error("保电任务--查询保电阶段表格旧数据ID异常", e);
			}
			
			int size = rslt.size();
			if(size > 0){
				for(int i=0; i<size; i++){
					String id = rslt.get(i).get("id").toString();

					// 删除旧数据
					HashMap<String,Object> params1 = new HashMap<String,Object>();
					params1.put("id",id);
					params1.put(nariis.falcon.oaf.Constant.CLASS_KEY, ElectricityEnsurePhase.class.getName());
					try {
						oafCommands.delete(params1);
					} catch (Exception e) {
						logger.error("保电任务--删除保电阶段表格旧数据异常", e);
					}
				}
			}
			
			// 插入新数据
			List<Map<String, Object>> gridList = (List<Map<String, Object>>) params.get("gridData");
			int gridLength = gridList.size();
			Map<String, Object> data = new HashMap<String, Object>();
			Date startTime2 = null;
			Date endTime2 = null;
			Object startDate2 = null;
			Object endDate2 = null;
			for(int i=0; i<gridLength; i++){	// 循环插入数据
				data = gridList.get(i);
				startDate2 = data.get("startTime");
				endDate2 = data.get("endTime");
				try{
					if(!ObjectUtils.isEmpty(startDate2)){
						startTime2 = formater.parse(startDate2.toString());
						data.put("startTime", startTime2);
					}
					if(!ObjectUtils.isEmpty(endDate2)){
						endTime2 = formater.parse(endDate2.toString());
						data.put("endTime", endTime2);
					}
				}catch (ParseException e) {	
					logger.error("保电任务--新增基础信息--保电阶段时间转换异常", e);
				}
				
				Object id = data.get("id");
				if(!ObjectUtils.isEmpty(id)){
					data.put("id", id);
					
					Date recordDate2 = null;		// 记录时间
					Object record2 = data.get("recordDate");
					try{
						if(!"".equals(record2.toString())){
							recordDate2=formater.parse(record2.toString());
						}
					}catch (ParseException e) {	
						e.printStackTrace();
					}
					
					data.put("recordDate", recordDate2);
				}else{
					data.put("id", UUID.randomUUID().toString());
					data.put("recordDate", recordDate);
				}
				
				String powerLevel2 = data.get("ensureLevel").toString();
				switch(powerLevel2){
					case "特级": powerLevel2 = "01";
					break;
					case "一级": powerLevel2 = "02";
					break;
					case "二级": powerLevel2 = "03";
					break;
					case "三级": powerLevel2 = "04";
					break;
					default: break;
				}
				
				data.put("belongTaskId", taskId);
				data.put("startTime", startTime2);
				data.put("endTime", endTime2);
				data.put("ensureLevel", powerLevel2);
				data.put("updateDateTime", updateDateTime);
				data.put(nariis.falcon.oaf.Constant.CLASS_KEY, ElectricityEnsurePhase.class.getName());
				try {
					oafCommands.insert(data);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			
			/**********修改组织机构**********/
			String theme = params.get("theme").toString();
			theme += "总部";
			
			HashMap<String,Object> params3 = new HashMap<String,Object>();
			StringBuilder sql3 = new StringBuilder(" from t in sgcim30.assets.management.ElectricityEnsureOrg ");
			sql3.append("where t.belongTaskId ==: taskId && t.superOrgID == null ");
			params3.put("taskId",taskId);
			sql3.append("select t ");
			List<Map<String, Object>> rslt3 = null;
			try {
				rslt3 = oafQuery.executeForList(sql3.toString(), params3);
			} catch (OafException e) {
				logger.error("保电任务--查询保电任务的组织机构总部信息异常", e);
			}
			
			if(rslt3.size() != 0){
				String orgId = rslt3.get(0).get("id").toString();
				HashMap<String,Object> params4 = new HashMap<String,Object>();
				params4.put("id", orgId);
				params4.put("orgName", theme);
				params4.put(nariis.falcon.oaf.Constant.CLASS_KEY, ElectricityEnsureOrg.class.getName());
				
				try {
					oafCommands.update(params4);
				} catch (Exception e) {
					logger.error("保电任务--修改保电任务的组织机构总部信息异常", e);
				}
			}
			
			/**********修改值班日志**********/
			String dutyUnitID = params.get("dutyUnitID").toString();
			String dutyUnitName = params.get("dutyUnitName").toString();
			String leaderRequest = params.get("leaderRequest").toString();
			String ensureLevel = params.get("ensureLevel").toString();
			switch(ensureLevel){
				case "1":
					ensureLevel = "特级";
					break;
				case "2":
					ensureLevel = "一级";
					break;
				case "3":
					ensureLevel = "二级";
					break;
				case "4":
					ensureLevel = "三级";
					break;
				default:
					break;
			}
			
			WorkLog workLog = (WorkLog)commonWorkLogService.getBeanByID(taskId);
			if(workLog != null){
				workLog.setUnitID(dutyUnitID);
				workLog.setUnitName(dutyUnitName);
				workLog.setStartTime(startTime);
				workLog.setEndTime(endTime);
				workLog.setUpdateDateTime(updateDateTime);
				
				StringBuilder logContent = new StringBuilder();
				if(!StringUtils.isEmpty(leaderRequest)){
					logContent.append("("+start+") "+ensureLevel+" "+theme+" ("+end+") "+leaderRequest);
				}
				workLog.setRecordDetails(logContent.toString());
				commonWorkLogService.updateWorkLog(workLog);
			}
		}
	}
	
	/**
	 * <p>描述：删除数据</p> 
	 * @param params 
	 * @author:王万胜
	 * 2017年3月14日 上午9:43:23
	 */
	@RequestMapping("/deletePowerSupplyGuaranteeResult")
	public void deletePowerSupplyGuaranteeResult(@RequestParam HashMap<String,Object> params){
		// 基础信息
		int length = Integer.parseInt(params.get("rowsCount").toString());
		String taskIdsWhere = "";
		String[] taskIdsArr = new String[length];
		for(int i=0; i<length; i++){
			taskIdsArr[i] = params.get("id"+i).toString();
			
			if(i == 0){
				taskIdsWhere = "t.belongTaskId == '" + taskIdsArr[i] + "' ";
			}else{
				taskIdsWhere += "|| t.belongTaskId == '" + taskIdsArr[i] + "' ";
			}
			
			// 执行删除操作
			HashMap<String,Object> params0 = new HashMap<String,Object>();
			params0.put("id",taskIdsArr[i]);
			params0.put(nariis.falcon.oaf.Constant.CLASS_KEY, ElectricityEnsure.class.getName());
			try {
				oafCommands.delete(params0);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			// 值班日志
			WorkLog workLog = (WorkLog)commonWorkLogService.getBeanByID(taskIdsArr[i]);
			if(workLog != null){
				commonWorkLogService.deleteWorkLog(workLog);
			}
		}
		
		// 获取各子表ID并删除相关数据
		// 组织机构
		List<Map<String, Object>> rslt1 = new ArrayList<Map<String, Object>>();
		if(!"".equals(taskIdsWhere)){
			StringBuilder sql1 = new StringBuilder("from t in sgcim30.assets.management.ElectricityEnsureOrg ");
			sql1.append("where " + taskIdsWhere);
			sql1.append("select t.id ");
			try {
				rslt1 = oafQuery.executeForList(sql1.toString());
			} catch (OafException e) {
				logger.error("保电任务--查询与选定的保电任务相关联的组织机构异常", e);
			}
		}
		
		int length1 = rslt1.size();
		String[] orgArr = new String[length1];
		for(int i=0; i<length1; i++){
			orgArr[i] = rslt1.get(i).get("id").toString();
			
			// 执行删除操作
			HashMap<String,Object> params0 = new HashMap<String,Object>();
			params0.put("id",orgArr[i]);
			params0.put(nariis.falcon.oaf.Constant.CLASS_KEY, ElectricityEnsureOrg.class.getName());
			try {
				oafCommands.delete(params0);
			} catch (Exception e) {
				logger.error("保电任务--删除与选定的保电任务相关联的组织机构异常", e);
			}
		}
		
		// 保电站线
		List<Map<String, Object>> rslt2 = new ArrayList<Map<String, Object>>();
		if(!"".equals(taskIdsWhere)){
			StringBuilder sql2 = new StringBuilder("from t in sgcim30.assets.management.StationLineEnsure ");
			sql2.append("where " + taskIdsWhere);
			sql2.append("select t.id ");
			try {
				rslt2 = oafQuery.executeForList(sql2.toString());
			} catch (OafException e) {
				logger.error("保电任务--查询与选定的保电任务相关联的保电站线异常", e);
			}
		}
		
		int length2 = rslt2.size();
		String[] solArr = new String[length2];
		for(int i=0; i<length2; i++){
			solArr[i] = rslt2.get(i).get("id").toString();
			
			// 执行删除操作
			HashMap<String,Object> params0 = new HashMap<String,Object>();
			params0.put("id",solArr[i]);
			params0.put(nariis.falcon.oaf.Constant.CLASS_KEY, StationLineEnsure.class.getName());
			try {
				oafCommands.delete(params0);
			} catch (Exception e) {
				logger.error("保电任务--删除与选定的保电任务相关联的保电站线异常", e);
			}
		}
		
		// 用户
		List<Map<String, Object>> rslt3 = new ArrayList<Map<String, Object>>();
		if(!"".equals(taskIdsWhere)){
			StringBuilder sql3 = new StringBuilder("from t in sgcim30.assets.management.ElectricityEnsureUser ");
			sql3.append("where " + taskIdsWhere);
			sql3.append("select t.id ");
			try {
				rslt3 = oafQuery.executeForList(sql3.toString());
			} catch (OafException e) {
				logger.error("保电任务--查询与选定的保电任务相关联的用户异常", e);
			}
		}
		
		int length3 = rslt3.size();
		String[] userArr = new String[length3];
		for(int i=0; i<length3; i++){
			userArr[i] = rslt3.get(i).get("id").toString();
			
			// 执行删除操作
			HashMap<String,Object> params0 = new HashMap<String,Object>();
			params0.put("id",userArr[i]);
			params0.put(nariis.falcon.oaf.Constant.CLASS_KEY, ElectricityEnsureUser.class.getName());
			try {
				oafCommands.delete(params0);
			} catch (Exception e) {
				logger.error("保电任务--删除与选定的保电任务相关联的用户异常", e);
			}
		}
		
		// 人员
		List<Map<String, Object>> rslt4 = new ArrayList<Map<String, Object>>();
		if(!"".equals(taskIdsWhere)){
			StringBuilder sql4 = new StringBuilder("from t in sgcim30.assets.management.ElectricityEnsurePerson ");
			sql4.append("where " + taskIdsWhere);
			sql4.append("select t.id ");
			try {
				rslt4 = oafQuery.executeForList(sql4.toString());
			} catch (OafException e) {
				logger.error("保电任务--查询与选定的保电任务相关联的人员异常", e);
			}
		}
		
		int length4 = rslt4.size();
		String[] personArr = new String[length4];
		for(int i=0; i<length4; i++){
			personArr[i] = rslt4.get(i).get("id").toString();
			
			// 执行删除操作
			HashMap<String,Object> params0 = new HashMap<String,Object>();
			params0.put("id",personArr[i]);
			params0.put(nariis.falcon.oaf.Constant.CLASS_KEY, ElectricityEnsurePerson.class.getName());
			try {
				oafCommands.delete(params0);
			} catch (Exception e) {
				logger.error("保电任务--删除与选定的保电任务相关联的人员异常", e);
			}
		}
		
		// 车辆
		List<Map<String, Object>> rslt5 = new ArrayList<Map<String, Object>>();
		if(!"".equals(taskIdsWhere)){
			StringBuilder sql5 = new StringBuilder("from t in sgcim30.assets.management.ElectricityEnsureVehicle ");
			sql5.append("where " + taskIdsWhere);
			sql5.append("select t.id ");
			try {
				rslt5 = oafQuery.executeForList(sql5.toString());
			} catch (OafException e) {
				logger.error("保电任务--查询与选定的保电任务相关联的车辆异常", e);
			}
		}
		
		int length5 = rslt5.size();
		String[] carArr = new String[length5];
		for(int i=0; i<length5; i++){
			carArr[i] = rslt5.get(i).get("id").toString();
			
			// 执行删除操作
			HashMap<String,Object> params0 = new HashMap<String,Object>();
			params0.put("id",carArr[i]);
			params0.put(nariis.falcon.oaf.Constant.CLASS_KEY, ElectricityEnsureVehicle.class.getName());
			try {
				oafCommands.delete(params0);
			} catch (Exception e) {
				logger.error("保电任务--删除与选定的保电任务相关联的车辆异常", e);
			}
		}
		
		// 驻点
		List<Map<String, Object>> rslt6 = new ArrayList<Map<String, Object>>();
		if(!"".equals(taskIdsWhere)){
			StringBuilder sql6 = new StringBuilder("from t in sgcim30.assets.management.ElectricityEnsureStation ");
			sql6.append("where " + taskIdsWhere);
			sql6.append("select t.id ");
			try {
				rslt6 = oafQuery.executeForList(sql6.toString());
			} catch (OafException e) {
				logger.error("保电任务--查询与选定的保电任务相关联的驻点异常", e);
			}
		}
		
		int length6 = rslt6.size();
		String[] pointArr = new String[length6];
		for(int i=0; i<length6; i++){
			pointArr[i] = rslt6.get(i).get("id").toString();
			
			// 执行删除操作
			HashMap<String,Object> params0 = new HashMap<String,Object>();
			params0.put("id",pointArr[i]);
			params0.put(nariis.falcon.oaf.Constant.CLASS_KEY, ElectricityEnsureStation.class.getName());
			try {
				oafCommands.delete(params0);
			} catch (Exception e) {
				logger.error("保电任务--删除与选定的保电任务相关联的驻点异常", e);
			}
		}
		
		// 宾馆
		List<Map<String, Object>> rslt7 = new ArrayList<Map<String, Object>>();
		if(!"".equals(taskIdsWhere)){
			StringBuilder sql7 = new StringBuilder("from t in sgcim30.assets.management.ElectricityEnsureHotel ");
			sql7.append("where " + taskIdsWhere);
			sql7.append("select t.id ");
			try {
				rslt7 = oafQuery.executeForList(sql7.toString());
			} catch (OafException e) {
				logger.error("保电任务--查询与选定的保电任务相关联的宾馆异常", e);
			}
		}
		
		int length7 = rslt7.size();
		String[] hotelArr = new String[length7];
		for(int i=0; i<length7; i++){
			hotelArr[i] = rslt7.get(i).get("id").toString();
			
			// 执行删除操作
			HashMap<String,Object> params0 = new HashMap<String,Object>();
			params0.put("id",hotelArr[i]);
			params0.put(nariis.falcon.oaf.Constant.CLASS_KEY, ElectricityEnsureHotel.class.getName());
			try {
				oafCommands.delete(params0);
			} catch (Exception e) {
				logger.error("保电任务--删除与选定的保电任务相关联的宾馆异常", e);
			}
		}
		
		// 医院
		List<Map<String, Object>> rslt8 = new ArrayList<Map<String, Object>>();
		if(!"".equals(taskIdsWhere)){
			StringBuilder sql8 = new StringBuilder("from t in sgcim30.assets.management.ElectricityEnsureHospital ");
			sql8.append("where " + taskIdsWhere);
			sql8.append("select t.id ");
			try {
				rslt8 = oafQuery.executeForList(sql8.toString());
			} catch (OafException e) {
				logger.error("保电任务--查询与选定的保电任务相关联的医院异常", e);
			}
		}
		
		int length8 = rslt8.size();
		String[] hospitalArr = new String[length8];
		for(int i=0; i<length8; i++){
			hospitalArr[i] = rslt8.get(i).get("id").toString();
			
			// 执行删除操作
			HashMap<String,Object> params0 = new HashMap<String,Object>();
			params0.put("id",hospitalArr[i]);
			params0.put(nariis.falcon.oaf.Constant.CLASS_KEY, ElectricityEnsureHospital.class.getName());
			try {
				oafCommands.delete(params0);
			} catch (Exception e) {
				logger.error("保电任务--删除与选定的保电任务相关联的医院异常", e);
			}
		}
		
		// 保电阶段-基础信息
		List<Map<String, Object>> rslt9 = new ArrayList<Map<String, Object>>();
		if(!"".equals(taskIdsWhere)){
			StringBuilder sql9 = new StringBuilder("from t in sgcim30.assets.management.ElectricityEnsurePhase ");
			sql9.append("where " + taskIdsWhere);
			sql9.append("select t.id ");
			try {
				rslt9 = oafQuery.executeForList(sql9.toString());
			} catch (OafException e) {
				logger.error("保电任务--查询与选定的保电任务相关联的基础信息保电阶段异常", e);
			}
		}
		
		int length9 = rslt9.size();
		String[] infoPhaseArr = new String[length9];
		for(int i=0; i<length9; i++){
			infoPhaseArr[i] = rslt9.get(i).get("id").toString();
			
			// 执行删除操作
			HashMap<String,Object> params0 = new HashMap<String,Object>();
			params0.put("id",infoPhaseArr[i]);
			params0.put(nariis.falcon.oaf.Constant.CLASS_KEY, ElectricityEnsurePhase.class.getName());
			try {
				oafCommands.delete(params0);
			} catch (Exception e) {
				logger.error("保电任务--删除与选定的保电任务相关联的基础信息保电阶段异常", e);
			}
		}
		
		// 保电阶段-保电站线
		int l1 = solArr.length;
		String sqlWhere1 = "";
		for(int i=0; i<l1; i++){
			if(i == 0){
				sqlWhere1 = "t.belongTaskId == '" + solArr[i] + "' ";
			}else{
				sqlWhere1 += "|| t.belongTaskId == '" + solArr[i] + "' ";
			}
		}
		
		List<Map<String, Object>> rslt10 = new ArrayList<Map<String, Object>>();
		if(!"".equals(sqlWhere1)){
			StringBuilder sql10 = new StringBuilder("from t in sgcim30.assets.management.ElectricityEnsurePhase ");
			sql10.append("where " + sqlWhere1);
			sql10.append("select t.id ");
			try {
				rslt10 = oafQuery.executeForList(sql10.toString());
			} catch (OafException e) {
				logger.error("保电任务--查询与选定的保电任务相关联的保电站线保电阶段异常", e);
			}
		}
		
		int length10 = rslt10.size();
		String[] solPhaseArr = new String[length10];
		for(int i=0; i<length10; i++){
			solPhaseArr[i] = rslt10.get(i).get("id").toString();
			
			// 执行删除操作
			HashMap<String,Object> params0 = new HashMap<String,Object>();
			params0.put("id",solPhaseArr[i]);
			params0.put(nariis.falcon.oaf.Constant.CLASS_KEY, ElectricityEnsurePhase.class.getName());
			try {
				oafCommands.delete(params0);
			} catch (Exception e) {
				logger.error("保电任务--删除与选定的保电任务相关联的保电站线保电阶段异常", e);
			}
		}
		
		// 保电阶段-用户
		int l2 = userArr.length;
		String sqlWhere2 = "";
		for(int i=0; i<l2; i++){
			if(i == 0){
				sqlWhere2 = "t.belongTaskId == '" + userArr[i] + "' ";
			}else{
				sqlWhere2 += "|| t.belongTaskId == '" + userArr[i] + "' ";
			}
		}
		
		List<Map<String, Object>> rslt11 = new ArrayList<Map<String, Object>>();
		if(!"".equals(sqlWhere2)){
			StringBuilder sql11 = new StringBuilder("from t in sgcim30.assets.management.ElectricityEnsurePhase ");
			sql11.append("where " + sqlWhere2);
			sql11.append("select t.id ");
			try {
				rslt11 = oafQuery.executeForList(sql11.toString());
			} catch (OafException e) {
				logger.error("保电任务--查询与选定的保电任务相关联的用户保电阶段异常", e);
			}
		}
		
		int length11 = rslt11.size();
		String[] userPhaseArr = new String[length11];
		for(int i=0; i<length11; i++){
			userPhaseArr[i] = rslt11.get(i).get("id").toString();
			
			// 执行删除操作
			HashMap<String,Object> params0 = new HashMap<String,Object>();
			params0.put("id",userPhaseArr[i]);
			params0.put(nariis.falcon.oaf.Constant.CLASS_KEY, ElectricityEnsurePhase.class.getName());
			try {
				oafCommands.delete(params0);
			} catch (Exception e) {
				logger.error("保电任务--删除与选定的保电任务相关联的用户保电阶段异常", e);
			}
		}
	}
	
	/**
	 * <p>描述：获取保电级别下拉框</p> 
	 * @return powerLever
	 * @author:王万胜
	 * 2017年4月6日 下午2:01:08
	 */
	@RequestMapping("/getPowerLeverList")
	public String getPowerLeverList(){
		int i = 1;
		String powerLever = "";
		Constant cst = ConstantRegistry.findConstant(PowerLever.TYPE);
		Map<Object, ConstantItem> map = cst.getcSTItems();
		
		for(Entry<Object, ConstantItem> entry:map.entrySet()){
			String item = "";
			if(i == 1){
				item = "{ 'id':'"+entry.getValue().getFieldValueByFieldName("value")+"',"
						+ "'text':'"+entry.getValue().getFieldValueByFieldName("name")+"'}";
				i++;
			}else{
				item = ", { 'id':'"+entry.getValue().getFieldValueByFieldName("value")+"',"
						+ "'text':'"+entry.getValue().getFieldValueByFieldName("name")+"'}";
			}
			powerLever += item;
		}
		powerLever = "["+powerLever+"]";
		
		return powerLever;
	}
	
	
	/**
	 * <p>描述：获取任务来源下拉框</p> 
	 * @return taskSource
	 * @author:王万胜
	 * 2017年4月11日 下午7:09:46
	 */
	@RequestMapping("/getTaskSourceList")
	public String getTaskSourceList(){
		int i = 1;
		String taskSource = "";
		Constant cst = ConstantRegistry.findConstant(TaskSource.TYPE);
		Map<Object, ConstantItem> map = cst.getcSTItems();
		
		for(Entry<Object, ConstantItem> entry:map.entrySet()){
			String item = "";
			if(i == 1){
				item = "{ 'id':'"+entry.getValue().getFieldValueByFieldName("value")+"',"
						+ "'text':'"+entry.getValue().getFieldValueByFieldName("name")+"'}";
				i++;
			}else{
				item = ", { 'id':'"+entry.getValue().getFieldValueByFieldName("value")+"',"
						+ "'text':'"+entry.getValue().getFieldValueByFieldName("name")+"'}";
			}
			taskSource += item;
		}
		taskSource = "["+taskSource+"]";
		
		return taskSource;
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
			logger.error("保电任务--获取组织机构树异常", e);
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
		String keyWords = params.get("keyWords");	// 关键字
		
		Map<String, Object> param = new LinkedHashMap<String, Object>();
		StringBuilder sql = new StringBuilder("from k in (from t in (from o1 in sgcim30.assets.management.ElectricityEnsureOrg ");
		sql.append("where o1.belongTaskId ==: taskId ");
		param.put("taskId",taskId);
		sql.append("orderby o1.superOrgID descending ");
		sql.append("select o1 )");
		sql.append("left join e in sgcim30.assets.management.ElectricityEnsure on t.belongTaskId equals e.id ");
		sql.append("left join o2 in sgcim30.assets.management.ElectricityEnsureOrg on t.superOrgID equals o2.id ");
		sql.append("select belongTask = e.theme, superOrg = o2.orgName, t) ");
		if(keyWords != null){
			sql.append(" where (k.orgName.contains('"+keyWords+"') || k.superOrg.contains('"+keyWords+"') || "
					+ "k.orgIntro.contains('"+keyWords+"') || k.dutyPerson.contains('"+keyWords+"') || "
					+ "k.phoneNumber.contains('"+keyWords+"') || k.contactNumber.contains('"+keyWords+"')) ");
		}
		
		// 表格分页
		StringBuilder sqlCount = new StringBuilder();
		sqlCount.append(sql);
		sqlCount.append(" select sqlCount = k.count() ");
		
		// sqlExport查出数据用来导出
		StringBuilder sqlExport = new StringBuilder();
		sqlExport.append(sql);
		sqlExport.append(" select k ");
		
		sql.append("select k then skip :skipCount then take :pageSize ");
		
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
				logger.error("保电任务--组织机构--查询数据异常", e);
			}else{
				logger.error("保电任务--组织机构--导出数据异常", e);
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
	 * <p>描述：插入或修改组织机构</p> 
	 * @param params 
	 * @author:王万胜
	 * 2017年4月19日 下午2:06:55
	 */
	@RequestMapping("/insertOrUpdateOrganizationResult")
	public void insertOrUpdateOrganizationResult(@RequestBody HashMap<String,Object> params){
		Date updateDateTime = new Date();	// 最后更新时间
		Date recordDate = new Date();		// 记录时间
		
		String taskId = params.get("taskId").toString();
		List<Map<String, Object>> gridList = (List<Map<String, Object>>) params.get("gridData");
		int gridLength = gridList.size();
		Map<String, Object> data = new LinkedHashMap<String, Object>();
		for(int i=0; i<gridLength; i++){
			Object orgName = gridList.get(i).get("orgName");
			Object superOrgID = gridList.get(i).get("superOrgID");
			Object orgIntro = gridList.get(i).get("orgIntro");
			Object dutyPerson = gridList.get(i).get("dutyPerson");
			Object phoneNumber = gridList.get(i).get("phoneNumber");
			Object contactNumber = gridList.get(i).get("contactNumber");
			Object dutyPersonPhoto = gridList.get(i).get("dutyPersonPhoto");
			Object dutyPersonPhotoNames = gridList.get(i).get("dutyPersonPhotoNames");
			Object ensurePlan = gridList.get(i).get("ensurePlan");
			Object ensureArea = gridList.get(i).get("ensureArea");
			
			data.put("belongTaskId", taskId);
			data.put("orgName", orgName);
			data.put("superOrgID", superOrgID);
			data.put("orgIntro", orgIntro);
			data.put("dutyPerson", dutyPerson);
			data.put("phoneNumber", phoneNumber);
			data.put("contactNumber", contactNumber);
			data.put("dutyPersonPhoto", dutyPersonPhoto);
			data.put("dutyPersonPhotoNames", dutyPersonPhotoNames);
			data.put("ensurePlan", ensurePlan);
			data.put("ensureArea", ensureArea);
			data.put("updateDateTime", updateDateTime);
			data.put(nariis.falcon.oaf.Constant.CLASS_KEY, ElectricityEnsureOrg.class.getName());
			
			if(gridList.get(i).get("id") != null){
				Object id = gridList.get(i).get("id");
				data.put("id", id);
				try {
					oafCommands.update(data);
				} catch (Exception e) {
					logger.error("保电任务--组织机构--更新数据异常", e);
				}
			}else{
				data.put("id", UUID.randomUUID().toString());
				data.put("recordDate", recordDate);
				try {
					oafCommands.insert(data);
				} catch (Exception e) {
					logger.error("保电任务--组织机构--插入数据异常", e);
				}
			}
		}
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
			logger.error("保电任务--获取下级组织机构异常", e);
		}
		
		// 返回查询结果
		return rslt;
	}
	
	/**
	 * <p>描述：删除组织机构记录</p> 
	 * @param params 
	 * @author:王万胜
	 * 2017年4月19日 下午7:08:23
	 */
	@RequestMapping("/deleteOrganizationResult")
	public void deleteOrganizationResult(@RequestParam HashMap<String,Object> params){
		String ids = params.get("ids").toString();
		String[] idsArr = ids.split(",");
		int length = idsArr.length;
		
		for(int i=0; i<length; i++){
			params.put("id",idsArr[i]);
			params.put(nariis.falcon.oaf.Constant.CLASS_KEY, ElectricityEnsureOrg.class.getName());
			
			try {
				oafCommands.delete(params);
			} catch (Exception e) {
				logger.error("保电任务--删除组织机构记录异常", e);
			}
		}
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
		String taskId = params.get("taskId").toString();	 	// 保电任务ID
		String voltageLevel = params.get("voltageLevel").toString();	// 电压等级
		String voltageLevelSQL = "";
		String departmentID = params.get("departmentID").toString();	// 所属单位ID
		String departmentSQL = "";
		String solName = params.get("solRightName").toString();	// 站线名称（页面右侧）
		
		if(!StringUtils.isEmpty(voltageLevel)){
			String[] voltageLevelArr = voltageLevel.split(",");
			int l = voltageLevelArr.length;
			for(int i=0; i<l; i++){
				if(i == 0){
					voltageLevelSQL = "( t.voltageLevel == '" + voltageLevelArr[i] + "' ";
				}else{
					voltageLevelSQL += "|| t.voltageLevel == '" + voltageLevelArr[i] + "' ";
				}
			}
		}
		
		if(!StringUtils.isEmpty(departmentID)){
			String[] departmentIDArr = departmentID.split(",");
			int l = departmentIDArr.length;
			for(int i=0; i<l; i++){
				if(i == 0){
					departmentSQL = "( t.organizationId == '" + departmentIDArr[i] + "' ";
				}else{
					departmentSQL += "|| t.organizationId == '" + departmentIDArr[i] + "' ";
				}
			}
		}
		
		
		Map<String, Object> param = new LinkedHashMap<String, Object>();
		StringBuilder sql = new StringBuilder("from t in sgcim30.assets.management.StationLineEnsure ");
		sql.append("where t.belongTaskId ==: taskId ");
		param.put("taskId",taskId);
		
		if(!StringUtils.isEmpty(voltageLevel)){	// 电压等级
			sql.append("&& " + voltageLevelSQL + ") ");
		}
		if(!StringUtils.isEmpty(departmentID)){	// 所属单位
			sql.append("&& " + departmentSQL + ") ");
		}
		if(!StringUtils.isEmpty(solName)){	// 站线名称
			sql.append("&& t.stationLineName.contains('"+solName+"') ");
		}
		
		// 表格分页
		StringBuilder sqlCount = new StringBuilder();
		sqlCount.append(sql);
		sqlCount.append(" select sqlCount = t.count() ");
		
		// 查出保电任务下的首页修改站线数据
		StringBuilder sqlAll = new StringBuilder();
		sqlAll.append(sql);
		sqlAll.append(" select t ");
		
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
				rslt = oafQuery.executeForList(sqlAll.toString(), param);
			}
			
		} catch (OafException e) {
			logger.error("保电任务--保电站线--查询数据异常", e);
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
				if(!ObjectUtils.isEmpty(map.get("ensureLevel"))){
					int ensureLevelValue = Integer.parseInt(map.get("ensureLevel").toString());
					String ensureLevelName = ensureLevelConstant.getFieldValueByItemValue(ensureLevelValue, "name");
					map.put("ensureLevel", ensureLevelName);
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
	 * <p>描述：插入或修改保电站线</p> 
	 * @param params 
	 * @author:王万胜
	 * 2017年6月2日 上午11:05:50
	 */
	@RequestMapping("/insertStationOrLineResult")
	public void insertStationOrLineResult(@RequestParam HashMap<String,Object> params){
		Date updateDateTime = new Date();	// 最后更新时间
		Date recordDate = new Date();		// 记录时间
		
		Map<String, Object> data = new LinkedHashMap<String, Object>();
		String taskId = params.get("taskId").toString();
		int gridLength = Integer.parseInt((String)params.get("gridLength"));
		
		for(int i=0; i<gridLength; i++){
			Object voltageLevel = params.get("gridData["+i+"][voltageLevel]");
			Object organizationId = params.get("gridData["+i+"][organizationId]");
			Object organization = params.get("gridData["+i+"][organization]");
			Object stationLineID = params.get("gridData["+i+"][stationLineID]");
			Object stationLineName = params.get("gridData["+i+"][stationLineName]");
			Object stationLineType = params.get("gridData["+i+"][stationLineType]");
			Object deviceID = params.get("gridData["+i+"][deviceID]");
			Object deviceName = params.get("gridData["+i+"][deviceName]");
			
			data.put("id", UUID.randomUUID().toString());
			data.put("belongTaskId", taskId);
			data.put("voltageLevel", voltageLevel);
			data.put("organizationId", organizationId);
			data.put("organization", organization);
			data.put("stationLineID", stationLineID);
			data.put("stationLineName", stationLineName);
			data.put("stationLineType", stationLineType);
			data.put("deviceID", deviceID);
			data.put("deviceName", deviceName);
			data.put("updateDateTime", updateDateTime);
			data.put("recordDate", recordDate);
			data.put(nariis.falcon.oaf.Constant.CLASS_KEY, StationLineEnsure.class.getName());
			try {
				oafCommands.insert(data);
			} catch (Exception e) {
				logger.error("保电任务--组织机构--插入数据异常", e);
			}
		}
	}
	
	/**
	 * <p>描述：修改保电站线信息</p> 
	 * @param params 
	 * @author:王万胜
	 * 2017年4月21日 下午3:46:47
	 */
	@RequestMapping("/updateStationOrLineResult")
	public void updateStationOrLineResult(@RequestParam HashMap<String,Object> params){
		Date updateDateTime = new Date();	// 最后更新时间
		Date recordDate = new Date();		// 记录时间
		
		String[] taskIds = params.get("ids").toString().split(",");
		int l = taskIds.length;
		
		for(int k=0; k<l; k++){
			params.put("id", taskIds[k]);
			params.put("updateDateTime", updateDateTime);
			params.put(nariis.falcon.oaf.Constant.CLASS_KEY, StationLineEnsure.class.getName());
			
			try {
				oafCommands.update(params);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			// 保电阶段表格数据修改（先删旧数据，后插新数据）
			SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			
			// 查询旧数据ID
			HashMap<String,Object> params0 = new HashMap<String,Object>();
			StringBuilder sql = new StringBuilder("from t in sgcim30.assets.management.ElectricityEnsurePhase ");
			sql.append("where t.belongTaskId ==: taskId ");
			params0.put("taskId",taskIds[k]);
			sql.append("select t.id ");
			
			List<Map<String, Object>> rslt = null;
			try {
				rslt = oafQuery.executeForList(sql.toString(), params0);
			} catch (OafException e) {
				logger.error("保电任务--修改保电站线信息--查询保电站线旧的保电阶段数据异常", e);
			}
			
			int size = rslt.size();
			if(size > 0){
				for(int i=0; i<size; i++){
					String id = rslt.get(i).get("id").toString();

					// 删除旧数据
					HashMap<String,Object> params1 = new HashMap<String,Object>();
					params1.put("id",id);
					params1.put(nariis.falcon.oaf.Constant.CLASS_KEY, ElectricityEnsurePhase.class.getName());
					try {
						oafCommands.delete(params1);
					} catch (Exception e) {
						logger.error("保电任务--修改保电站线信息--删除保电站线旧的保电阶段数据异常", e);
					}
				}
			}
			
			// 插入新数据
			int gridLength = Integer.parseInt((String)params.get("gridLength"));
			for(int i=0; i<gridLength; i++){	// 循环插入数据
				HashMap<String,Object> params2 = new HashMap<String,Object>();
				String id = "";
				if(params.get("gridData["+i+"][id]") != null){
					id = params.get("gridData["+i+"][id]").toString();
				}
				
				Date startTime2 = null;		// 开始时间
				Date endTime2 = null;		// 结束时间
				Object start2 = params.get("gridData["+i+"][startTime]");
				Object end2 = params.get("gridData["+i+"][endTime]");
				try{
					if(!"".equals(start2.toString())){
						startTime2=formater.parse(start2.toString());
					}
					if(!"".equals(end2.toString())){
						endTime2=formater.parse(end2.toString());
					}
				}catch (ParseException e) {	
					logger.error("保电任务--修改保电站线信息--时间转换异常", e);
				}
				
				String powerLevel2 = params.get("gridData["+i+"][ensureLevel]").toString();
				switch(powerLevel2){
					case "特级": powerLevel2 = "01";
					break;
					case "一级": powerLevel2 = "02";
					break;
					case "二级": powerLevel2 = "03";
					break;
					case "三级": powerLevel2 = "04";
					break;
					default: break;
				}
				
				if(!"".equals(id)){
					params2.put("id", id);
					
					Date recordDate2 = null;		// 记录时间
					Object record2 = params.get("gridData["+i+"][recordDate]");
					try{
						if(!"".equals(record2.toString())){
							recordDate2=formater.parse(record2.toString());
						}
					}catch (ParseException e) {	
						logger.error("保电任务--修改保电站线信息--时间转换异常", e);
					}
					
					params2.put("recordDate", recordDate2);
				}else{
					params2.put("id", UUID.randomUUID().toString());
					params2.put("recordDate", recordDate);
				}
				params2.put("belongTaskId", taskIds[k]);
				params2.put("startTime", startTime2);
				params2.put("endTime", endTime2);
				params2.put("ensureLevel", powerLevel2);
				params2.put("updateDateTime", updateDateTime);
				params2.put(nariis.falcon.oaf.Constant.CLASS_KEY, ElectricityEnsurePhase.class.getName());
				try {
					oafCommands.insert(params2);
				} catch (Exception e) {
					logger.error("保电任务--修改保电站线信息--插入保电站线新的保电阶段数据异常", e);
				}
			}
		}
	}
	
	/**
	 * <p>描述：删除保电站线信息</p> 
	 * @param params 
	 * @author:王万胜
	 * 2017年6月5日 下午2:17:22
	 */
	@RequestMapping("/deleteStationOrLineResult")
	public void deleteStationOrLineResult(@RequestParam HashMap<String,Object> params){
		String ids = params.get("ids").toString();
		String[] idsArr = ids.split(",");
		int length = idsArr.length;
		
		// 删除站线表中数据
		for(int i=0; i<length; i++){
			params.put("id", idsArr[i]);
			params.put(nariis.falcon.oaf.Constant.CLASS_KEY, StationLineEnsure.class.getName());
			try {
				oafCommands.delete(params);
			} catch (Exception e) {
				logger.error("保电任务--删除保电站线信息异常", e);
			}
			
			// 查询保电阶段表中相关数据
			HashMap<String,Object> params0 = new HashMap<String,Object>();
			StringBuilder sql = new StringBuilder("from t in sgcim30.assets.management.ElectricityEnsurePhase ");
			sql.append("where t.belongTaskId ==: taskId ");
			params0.put("taskId",idsArr[i]);
			sql.append("select t.id ");
			
			List<Map<String, Object>> rslt = null;
			try {
				rslt = oafQuery.executeForList(sql.toString(), params0);
			} catch (OafException e) {
				logger.error("从保电阶段表中查询保电站线的阶段信息异常", e);
			}
			
			int size = rslt.size();
			if(size > 0){
				for(int j=0; j<size; j++){
					String id = rslt.get(j).get("id").toString();

					// 删除保电阶段表中相关数据
					HashMap<String,Object> params1 = new HashMap<String,Object>();
					params1.put("id",id);
					params1.put(nariis.falcon.oaf.Constant.CLASS_KEY, ElectricityEnsurePhase.class.getName());
					try {
						oafCommands.delete(params1);
					} catch (Exception e) {
						logger.error("从保电阶段表中删除保电站线的阶段信息异常", e);
					}
				}
			}
		}
	}
	
	/**
	 * <p>描述：点击闭环时判断勾选的保电任务（可多选）是否已关联保电站线</p> 
	 * @param params
	 * @return data
	 * @author:王万胜
	 * 2017年6月11日 下午6:03:31
	 */
	@RequestMapping("/queryWhetherHadSol")
	public List<Map<String,Object>> queryWhetherHadSol(@RequestParam HashMap<String,String> params){
		String id = params.get("id");
		
		List<Map<String, Object>> rslt = null;
		Map<String, Object> param = new LinkedHashMap<String, Object>();
		StringBuilder sql = new StringBuilder(" from t in sgcim30.assets.management.StationLineEnsure ");
		sql.append("where t.belongTaskId ==: taskId ");
		param.put("taskId",id);
		sql.append("select t ");
		
		try {
			rslt = oafQuery.executeForList(sql.toString(), param);
		} catch (OafException e) {
			logger.error(e);
		}
		
		// 返回查询结果
		return rslt;
	}
	
	/**
	 * <p>描述：获取变电站保电任务总数</p> 
	 * @param param
	 * @return long
	 * @author:马超
	 * 2017年6月5日 下午5:02:04
	 */
	@RequestMapping("/getElectricityEnsureCount")
	public long getElectricityEnsureCount(@RequestParam Map<String, Object> param) {
		long count = 0;

		StringBuilder fromFql = new StringBuilder();
		fromFql.append(" from task in " + ElectricityEnsure.class.getName() + " ");
		fromFql.append(
				" join station in " + StationLineEnsure.class.getName() + " on task.id equals station.belongTaskId");
		StringBuilder whereFql = new StringBuilder();
		whereFql.append(" where task.status == :status ");
		whereFql.append(" && station.stationLineID == :stationLineID");
		StringBuilder selectFql = new StringBuilder();
		selectFql.append(" select task.id then count ");
		StringBuilder queryFql = new StringBuilder();
		queryFql.append(fromFql).append(whereFql).append(selectFql);
		try {
			count = ((Integer) oafQuery.executeScalar(queryFql.toString(), param)).longValue();
		} catch (OafException e) {
			logger.error("获取变电站保电任务总数", e);
		}
		return count;
	}
	
	
	
	/************************************************************用户************************************************************/
	/**
	 * <p>描述：获取用户表格数据</p> 
	 * @param params
	 * @return data
	 * @author:王万胜
	 * 2017年4月26日 下午5:09:12
	 */
	@RequestMapping("/queryUserResult")
	public List<Map<String,Object>> queryUserResult(@RequestParam HashMap<String,String> params){
		String taskId = params.get("taskId");	 	// 保电任务ID
		String keyWords = params.get("keyWords");	// 关键字
		
//		Map<String, Object> param = new LinkedHashMap<String, Object>();
//		StringBuilder sql = new StringBuilder("from t in sgcim30.assets.management.ElectricityEnsureUser ");
//		sql.append("where t.belongTaskId ==: taskId ");
//		param.put("taskId",taskId);
		
		Map<String, Object> param = new LinkedHashMap<String, Object>();
		StringBuilder sql = new StringBuilder("from t in ( ");
		sql.append("from a in sgcim30.assets.management.ElectricityEnsureUser ");
		sql.append("where a.belongTaskId ==: taskId ");
		param.put("taskId",taskId);
		sql.append("&& (a.ensureLevel == null || a.belongOrgID == null) select a ");
		sql.append("union all ");
		sql.append("from b in sgcim30.assets.management.ElectricityEnsureUser ");
		sql.append("where b.belongTaskId ==: taskId ");
		param.put("taskId",taskId);
		sql.append("&& (b.ensureLevel != null && b.belongOrgID != null) select b ");
		sql.append(") where 1==1 ");
		
		if(keyWords != null){
			// 保电级别模糊查询汉字与编码转换方法
			String powerLevelKeyWords = "";
			if("1".equals(keyWords) || "2".equals(keyWords) || "3".equals(keyWords) || "4".equals(keyWords)){
				powerLevelKeyWords = "number";
			}else if(!"".equals(keyWords) && !"级".equals(keyWords)){
				String [] powerLevelArr = {"特级", "一级", "二级", "三级"};
				for(int i=0; i<4; i++){
					if(powerLevelArr[i].indexOf(keyWords) != -1){
//						powerLevelKeyWords = "0"+(i+1);
						powerLevelKeyWords = ""+(i+1);
						break;
					}else{
						powerLevelKeyWords = keyWords;
					}
				}
			}
			
			sql.append("&& (t.userName.contains('"+keyWords+"') || t.ensureLevel.contains('"+powerLevelKeyWords+"') || "
					+ "t.belongOrgName.contains('"+keyWords+"') || t.dutyPersonName.contains('"+keyWords+"')) ");
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
				logger.error("保电任务--用户--查询数据异常", e);
			}else{
				logger.error("保电任务--用户--导出数据异常", e);
			}
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
	 * <p>描述：新增用户数据</p> 
	 * @param params
	 * @throws ParseException 
	 * @author:王万胜
	 * 2017年4月27日 下午2:54:42
	 */
	@RequestMapping("/insertUserResult")
	public void insertUserResult(@RequestParam HashMap<String,Object> params) throws ParseException{
		Object taskId = params.get("taskId");
		Date updateDateTime = new Date();	// 最后更新时间
		Date recordDate = new Date();		// 记录时间
		
		String userId = UUID.randomUUID().toString();
		params.put("id", userId);
		params.put("belongTaskId", taskId);
		params.put("updateDateTime", updateDateTime);
		params.put("recordDate", recordDate);
		params.put(nariis.falcon.oaf.Constant.CLASS_KEY, ElectricityEnsureUser.class.getName());
		
		try {
			oafCommands.insert(params);
		} catch (Exception e) {
			logger.error("保电任务--新增用户数据异常", e);
		}
		
		// 保电阶段表格数据插入
		int gridLength = Integer.parseInt((String)params.get("gridLength"));
		for(int i=0; i<gridLength; i++){	// 循环插入数据
			HashMap<String,Object> params2 = new HashMap<String,Object>();
			Date startTime = null;		// 开始时间
			Date endTime = null;		// 结束时间
			Object start = params.get("gridData["+i+"][startTime]");
			Object end = params.get("gridData["+i+"][endTime]");
			Object powerLevel2 = params.get("gridData["+i+"][ensureLevel]");
			
			SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			try{
				if(!"".equals(start.toString())){
					startTime=formater.parse(start.toString());
				}
				if(!"".equals(end.toString())){
					endTime=formater.parse(end.toString());
				}
			}catch (ParseException e) {	
				logger.error("保电任务--新增用户保电阶段数据转换时间时异常", e);
			}
			
			params2.put("id", UUID.randomUUID().toString());
			params2.put("belongTaskId", userId);
			params2.put("startTime", startTime);
			params2.put("endTime", endTime);
			params2.put("ensureLevel", powerLevel2);
			params2.put("updateDateTime", updateDateTime);
			params2.put("recordDate", recordDate);
			params2.put(nariis.falcon.oaf.Constant.CLASS_KEY, ElectricityEnsurePhase.class.getName());
			try {
				oafCommands.insert(params2);
			} catch (Exception e) {
				logger.error("保电任务--新增用户保电阶段数据时异常", e);
			}
		}
	}
	
	/**
	 * <p>描述：修改用户数据</p> 
	 * @param params 
	 * @author:王万胜
	 * 2017年4月27日 下午7:22:57
	 */
	@RequestMapping("/updateUserResult")
	public void updateUserResult(@RequestParam HashMap<String,Object> params){
		Date updateDateTime = new Date();	// 最后更新时间
		Date recordDate = new Date();		// 记录时间
		String ids = params.get("ids").toString();
		String[] idsArr = ids.split(",");
		int l = idsArr.length;
		for(int i=0; i<l; i++){
			// 修改保电用户表中信息
			params.put("id", idsArr[i]);
			params.put("updateDateTime", updateDateTime);
			params.put(nariis.falcon.oaf.Constant.CLASS_KEY, ElectricityEnsureUser.class.getName());
			try {
				oafCommands.update(params);
			} catch (Exception e) {
				logger.error("保电任务--修改用户数据时异常", e);
			}
			
			boolean haveGrid = params.containsKey("gridLength");
			if(haveGrid){	// 有参数gridData为对保电用户基础信息的修改，无参数gridData为对保电用户位置的修改
				// 保电阶段表格数据修改（先删旧数据，后插新数据）
				// 查询旧数据ID
				HashMap<String,Object> params0 = new HashMap<String,Object>();
				StringBuilder sql = new StringBuilder("from t in sgcim30.assets.management.ElectricityEnsurePhase ");
				sql.append("where t.belongTaskId ==: taskId ");
				params0.put("taskId",idsArr[i]);
				sql.append("select t.id ");
				
				List<Map<String, Object>> rslt = null;
				try {
					rslt = oafQuery.executeForList(sql.toString(), params0);
				} catch (OafException e) {
					logger.error("保电任务--修改用户数据--查询用户保电阶段旧数据时异常", e);
				}
				
				int size = rslt.size();
				if(size > 0){
					for(int j=0; j<size; j++){
						String id = rslt.get(j).get("id").toString();

						// 删除旧数据
						HashMap<String,Object> params1 = new HashMap<String,Object>();
						params1.put("id",id);
						params1.put(nariis.falcon.oaf.Constant.CLASS_KEY, ElectricityEnsurePhase.class.getName());
						try {
							oafCommands.delete(params1);
						} catch (Exception e) {
							logger.error("保电任务--修改用户数据--删除用户保电阶段旧数据时异常", e);
						}
					}
				}
				
				// 插入新数据
				int gridLength = Integer.parseInt((String)params.get("gridLength"));
				for(int k=0; k<gridLength; k++){	// 循环插入数据
					HashMap<String,Object> params2 = new HashMap<String,Object>();
					String id = "";
					if(params.get("gridData["+k+"][id]") != null){
						id = params.get("gridData["+k+"][id]").toString();
					}
					
					Date startTime = null;		// 开始时间
					Date endTime = null;		// 结束时间
					Object start = params.get("gridData["+k+"][startTime]");
					Object end = params.get("gridData["+k+"][endTime]");
					
					SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd HH:mm");
					try{
						if(!"".equals(start.toString())){
							startTime=formater.parse(start.toString());
						}
						if(!"".equals(end.toString())){
							endTime=formater.parse(end.toString());
						}
					}catch (ParseException e) {	
						logger.error("保电任务--修改用户数据--插入用户保电阶段新数据时间转换时异常", e);
					}
					
					String powerLevel = params.get("gridData["+k+"][ensureLevel]").toString();
					switch(powerLevel){
						case "特级": powerLevel = "01";
						break;
						case "一级": powerLevel = "02";
						break;
						case "二级": powerLevel = "03";
						break;
						case "三级": powerLevel = "04";
						break;
						default: break;
					}
					
					if(!"".equals(id)){
						params2.put("id", id);
						
						Date recordDate2 = null;		// 记录时间
						Object record2 = params.get("gridData["+k+"][recordDate]");
						try{
							if(!"".equals(record2.toString())){
								recordDate2=formater.parse(record2.toString());
							}
						}catch (ParseException e) {	
							logger.error("保电任务--修改用户数据--插入用户保电阶段新数据时间转换时异常", e);
						}
						
						params2.put("recordDate", recordDate2);
					}else{
						params2.put("id", UUID.randomUUID().toString());
						params2.put("recordDate", recordDate);
					}
					params2.put("belongTaskId", idsArr[i]);
					params2.put("startTime", startTime);
					params2.put("endTime", endTime);
					params2.put("ensureLevel", powerLevel);
					params2.put("updateDateTime", updateDateTime);
					params2.put(nariis.falcon.oaf.Constant.CLASS_KEY, ElectricityEnsurePhase.class.getName());
					try {
						oafCommands.insert(params2);
					} catch (Exception e) {
						logger.error("保电任务--修改用户数据--插入用户保电阶段新数据时异常", e);
					}
				}
			}
		}
	}
	
	/**
	 * <p>描述：删除用户数据</p> 
	 * @param params 
	 * @author:王万胜
	 * 2017年4月28日 上午9:29:46
	 */
	@RequestMapping("/deleteUserResult")
	public void deleteUserResult(@RequestParam HashMap<String,Object> params){
		String ids = params.get("ids").toString();
		String[] idsArr = ids.split(",");
		int length = idsArr.length;
		
		// 删除用户表中数据
		for(int i=0; i<length; i++){
			params.put("id", idsArr[i]);
			params.put(nariis.falcon.oaf.Constant.CLASS_KEY, ElectricityEnsureUser.class.getName());
			try {
				oafCommands.delete(params);
			} catch (Exception e) {
				logger.error("保电任务--删除用户数据时异常", e);
			}
			
			// 查询保电阶段表中相关数据
			HashMap<String,Object> params0 = new HashMap<String,Object>();
			StringBuilder sql = new StringBuilder("from t in sgcim30.assets.management.ElectricityEnsurePhase ");
			sql.append("where t.belongTaskId ==: taskId ");
			params0.put("taskId",idsArr[i]);
			sql.append("select t.id ");
			
			List<Map<String, Object>> rslt = null;
			try {
				rslt = oafQuery.executeForList(sql.toString(), params0);
			} catch (OafException e) {
				logger.error("保电任务--删除用户数据--查询保电阶段表中相关数据时异常", e);
			}
			
			int size = rslt.size();
			if(size > 0){
				for(int j=0; j<size; j++){
					String id = rslt.get(j).get("id").toString();

					// 删除保电阶段表中相关数据
					HashMap<String,Object> params1 = new HashMap<String,Object>();
					params1.put("id",id);
					params1.put(nariis.falcon.oaf.Constant.CLASS_KEY, ElectricityEnsurePhase.class.getName());
					try {
						oafCommands.delete(params1);
					} catch (Exception e) {
						logger.error("保电任务--删除用户数据--删除保电阶段表中相关数据时异常", e);
					}
				}
			}
		}
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
	public List<Map<String,Object>> queryPersonResult(@RequestParam HashMap<String,String> params){
		String taskId = params.get("taskId");	 		// 保电任务ID
		String keyWords = params.get("keyWords");		// 关键字
		
//		Map<String, Object> param = new LinkedHashMap<String, Object>();
//		StringBuilder sql = new StringBuilder("from t in sgcim30.assets.management.ElectricityEnsurePerson ");
//		sql.append("where t.belongTaskId ==: taskId ");
//		param.put("taskId",taskId);
		
		Map<String, Object> param = new LinkedHashMap<String, Object>();
		StringBuilder sql = new StringBuilder("from t in (");
		sql.append("from a in sgcim30.assets.management.ElectricityEnsurePerson ");
		sql.append("where a.belongTaskId ==: taskId ");
		param.put("taskId",taskId);
		sql.append("&& (a.type == null || a.belongOrg == null || a.phoneNumber == null) select a ");
		sql.append("union all ");
		sql.append("from b in sgcim30.assets.management.ElectricityEnsurePerson ");
		sql.append("where b.belongTaskId ==: taskId ");
		param.put("taskId",taskId);
		sql.append("&& (b.type != null && b.belongOrg != null && b.phoneNumber != null) select b ");
		sql.append(") where 1==1 ");
		
		if(!StringUtils.isEmpty(params.get("personDate"))){
			Date personDate1 = null;
			SimpleDateFormat formater=new SimpleDateFormat("yyyy-MM-dd");
			try{
				if(!ObjectUtils.isEmpty(params.get("personDate"))){
					personDate1=formater.parse(params.get("personDate").toString());
				}
			}catch (ParseException e) {	
				logger.error("查询保电任务--人员时，时间转换异常！", e);
			}
			
			// 获取personDate的下一天
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(personDate1);
			calendar.add(Calendar.DAY_OF_YEAR, 1);
			Date personDate2 = calendar.getTime();
			
			sql.append("&& t.personDate >=: personDate1 ");
			param.put("personDate1",personDate1);
			sql.append("&& t.personDate <: personDate2 ");
			param.put("personDate2",personDate2);
		}
		if(keyWords != null){
			sql.append(" && (t.personName.contains('"+keyWords+"') || t.organizationName.contains('"+keyWords+"') || "
					+ "t.belongOrgName.contains('"+keyWords+"') || t.phoneNumber.contains('"+keyWords+"') || "
					+ "t.taskDeclare.contains('"+keyWords+"')) ");
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
				logger.error("保电任务--人员--查询数据异常", e);
			}else{
				logger.error("保电任务--人员--导出数据异常", e);
			}
		}
		
		// 编码转换为汉字显示
		if(rslt != null){
			for(Map<String,Object> map : rslt){
				// 时间转换
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				if(!ObjectUtils.isEmpty(map.get("personDate"))){
					String personDate = sdf.format(map.get("personDate"));
					map.put("personDate", personDate);
				}
				
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
	 * <p>描述：删除人员数据</p> 
	 * @param params 
	 * @author:王万胜
	 * 2017年4月28日 下午3:58:53
	 */
	@RequestMapping("/deletePersonResult")
	public void deletePersonResult(@RequestParam HashMap<String,Object> params){
		String ids = params.get("ids").toString();
		String[] idsArr = ids.split(",");
		int length = idsArr.length;
		
		for(int i=0; i<length; i++){
			params.put("id",idsArr[i]);
			params.put(nariis.falcon.oaf.Constant.CLASS_KEY, ElectricityEnsurePerson.class.getName());
			
			try {
				oafCommands.delete(params);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
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
	public List<Map<String,Object>> queryCarResult(@RequestParam HashMap<String,String> params){
		String taskId = params.get("taskId");	 	// 保电任务ID
		String keyWords = params.get("keyWords");	// 关键字
		
//		Map<String, Object> param = new LinkedHashMap<String, Object>();
//		StringBuilder sql = new StringBuilder("from t in sgcim30.assets.management.ElectricityEnsureVehicle ");
//		sql.append("where t.belongTaskId ==: taskId ");
//		param.put("taskId",taskId);
		
		Map<String, Object> param = new LinkedHashMap<String, Object>();
		StringBuilder sql = new StringBuilder("from t in ( ");
		sql.append("from a in sgcim30.assets.management.ElectricityEnsureVehicle ");
		sql.append("where a.belongTaskId ==: taskId ");
		param.put("taskId",taskId);
		sql.append("&& (a.type == null || a.belongOrg == null || a.phoneNumber == null) select a ");
		sql.append("union all ");
		sql.append("from b in sgcim30.assets.management.ElectricityEnsureVehicle ");
		sql.append("where b.belongTaskId ==: taskId ");
		param.put("taskId",taskId);
		sql.append("&& (b.type != null && b.belongOrg != null && b.phoneNumber != null) select b ");
		sql.append(") where 1==1 ");
		
		if(!StringUtils.isEmpty(params.get("vehicleDate"))){
			Date vehicleDate1 = null;
			SimpleDateFormat formater=new SimpleDateFormat("yyyy-MM-dd");
			try{
				if(!ObjectUtils.isEmpty(params.get("vehicleDate"))){
					vehicleDate1=formater.parse(params.get("vehicleDate").toString());
				}
			}catch (ParseException e) {	
				logger.error("查询保电任务--人员时，时间转换异常！", e);
			}
			
			// 获取vehicleDate的下一天
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(vehicleDate1);
			calendar.add(Calendar.DAY_OF_YEAR, 1);
			Date vehicleDate2 = calendar.getTime();
			
			sql.append("&& t.vehicleDate >=: vehicleDate1 ");
			param.put("vehicleDate1",vehicleDate1);
			sql.append("&& t.vehicleDate <: vehicleDate2 ");
			param.put("vehicleDate2",vehicleDate2);
		}
		if(keyWords != null){
			sql.append(" && (t.brandNumber.contains('"+keyWords+"') || t.organizationName.contains('"+keyWords+"') || "
					+ "t.belongOrgName.contains('"+keyWords+"') || t.phoneNumber.contains('"+keyWords+"') || "
					+ "t.driver.contains('"+keyWords+"') || t.taskDeclare.contains('"+keyWords+"')) ");
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
				logger.error("保电任务--车辆--查询数据异常", e);
			}else{
				logger.error("保电任务--车辆--导出数据异常", e);
			}
		}
		
		// 编码转换为汉字显示
		if(rslt != null){
			for(Map<String,Object> map : rslt){
				// 时间转换
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				if(!ObjectUtils.isEmpty(map.get("vehicleDate"))){
					String vehicleDate = sdf.format(map.get("vehicleDate"));
					map.put("vehicleDate", vehicleDate);
				}
				
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
	 * <p>描述：删除车辆数据</p> 
	 * @param params 
	 * @author:王万胜
	 * 2017年4月28日 下午4:11:37
	 */
	@RequestMapping("/deleteCarResult")
	public void deleteCarResult(@RequestParam HashMap<String,Object> params){
		String ids = params.get("ids").toString();
		String[] idsArr = ids.split(",");
		int length = idsArr.length;
		
		for(int i=0; i<length; i++){
			params.put("id",idsArr[i]);
			params.put(nariis.falcon.oaf.Constant.CLASS_KEY, ElectricityEnsureVehicle.class.getName());
			
			try {
				oafCommands.delete(params);
			} catch (Exception e) {
				logger.error("保电任务--删除车辆数据时异常", e);
			}
		}
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
	public List<Map<String,Object>> queryPointResult(@RequestParam HashMap<String,String> params){
		String taskId = params.get("taskId");	 	// 保电任务ID
		String keyWords = params.get("keyWords");	// 关键字
		
//		Map<String, Object> param = new LinkedHashMap<String, Object>();
//		StringBuilder sql = new StringBuilder("from t in sgcim30.assets.management.ElectricityEnsureStation ");
//		sql.append("where t.belongTaskId ==: taskId ");
//		param.put("taskId",taskId);
		
		Map<String, Object> param = new LinkedHashMap<String, Object>();
		StringBuilder sql = new StringBuilder("from t in ( ");
		sql.append("from a in sgcim30.assets.management.ElectricityEnsureStation ");
		sql.append("where a.belongTaskId ==: taskId ");
		param.put("taskId",taskId);
		sql.append("&& (a.belongOrgID == null || a.phoneNumber == null) select a ");
		sql.append("union all ");
		sql.append("from b in sgcim30.assets.management.ElectricityEnsureStation ");
		sql.append("where b.belongTaskId ==: taskId ");
		param.put("taskId",taskId);
		sql.append("&& b.belongOrgID != null && b.phoneNumber != null select b ");
		sql.append(") where 1==1 ");
		
		if(keyWords != null){
			sql.append("&& (t.stationName.contains('"+keyWords+"') || t.belongOrgName.contains('"+keyWords+"') || "
					+ "t.dutyPerson.contains('"+keyWords+"') || t.phoneNumber.contains('"+keyWords+"') || "
					+ "t.taskDeclare.contains('"+keyWords+"')) ");
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
				logger.error("保电任务--驻点--查询数据异常", e);
			}else{
				logger.error("保电任务--驻点--导出数据异常", e);
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
	 * <p>描述：删除驻点数据</p> 
	 * @param params 
	 * @author:王万胜
	 * 2017年4月28日 下午4:14:47
	 */
	@RequestMapping("/deletePointResult")
	public void deletePointResult(@RequestParam HashMap<String,Object> params){
		String ids = params.get("ids").toString();
		String[] idsArr = ids.split(",");
		int length = idsArr.length;
		
		for(int i=0; i<length; i++){
			params.put("id",idsArr[i]);
			params.put(nariis.falcon.oaf.Constant.CLASS_KEY, ElectricityEnsureStation.class.getName());
			
			try {
				oafCommands.delete(params);
			} catch (Exception e) {
				logger.error("保电任务--删除驻点数据时异常", e);
			}
		}
	}
	
	/**
	 * <p>描述：修改驻点数据（位置信息）</p> 
	 * @param params 
	 * @author:王万胜
	 * 2017年7月4日 上午10:32:24
	 */
	@RequestMapping("/updatePointResult")
	public void updatePointResult(@RequestParam HashMap<String,Object> params){
		Date updateDateTime = new Date();	// 最后更新时间
		
		params.put("updateDateTime", updateDateTime);
		params.put(nariis.falcon.oaf.Constant.CLASS_KEY, ElectricityEnsureStation.class.getName());
		
		try {
			oafCommands.update(params);
		} catch (Exception e) {
			logger.error("保电任务--修改驻点数据时异常", e);
		}
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
	public List<Map<String,Object>> queryHotelResult(@RequestParam HashMap<String,String> params){
		String taskId = params.get("taskId");	 	// 保电任务ID
		String keyWords = params.get("keyWords");	// 关键字
		
//		Map<String, Object> param = new LinkedHashMap<String, Object>();
//		StringBuilder sql = new StringBuilder("from t in sgcim30.assets.management.ElectricityEnsureHotel ");
//		sql.append("where t.belongTaskId ==: taskId ");
//		param.put("taskId",taskId);
		
		Map<String, Object> param = new LinkedHashMap<String, Object>();
		StringBuilder sql = new StringBuilder("from t in ( ");
		sql.append("from a in sgcim30.assets.management.ElectricityEnsureHotel ");
		sql.append("where a.belongTaskId ==: taskId ");
		param.put("taskId",taskId);
		sql.append("&& a.phoneNumber == null select a ");
		sql.append("union all ");
		sql.append("from b in sgcim30.assets.management.ElectricityEnsureHotel ");
		sql.append("where b.belongTaskId ==: taskId ");
		param.put("taskId",taskId);
		sql.append("&& b.phoneNumber != null select b ");
		sql.append(") where 1==1 ");
		
		if(keyWords != null){
			sql.append(" && (t.hotelName.contains('"+keyWords+"') || t.linkman.contains('"+keyWords+"') || "
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
				logger.error("保电任务--宾馆--查询数据异常", e);
			}else{
				logger.error("保电任务--宾馆--导出数据异常", e);
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
	 * <p>描述：删除宾馆数据</p> 
	 * @param params 
	 * @author:王万胜
	 * 2017年4月28日 下午4:20:44
	 */
	@RequestMapping("/deleteHotelResult")
	public void deleteHotelResult(@RequestParam HashMap<String,Object> params){
		String ids = params.get("ids").toString();
		String[] idsArr = ids.split(",");
		int length = idsArr.length;
		
		for(int i=0; i<length; i++){
			params.put("id",idsArr[i]);
			params.put(nariis.falcon.oaf.Constant.CLASS_KEY, ElectricityEnsureHotel.class.getName());
			
			try {
				oafCommands.delete(params);
			} catch (Exception e) {
				logger.error("保电任务--删除宾馆数据异常", e);
			}
		}
	}
	
	/**
	 * <p>描述：修改宾馆数据（位置信息）</p> 
	 * @param params 
	 * @author:王万胜
	 * 2017年7月4日 上午10:32:24
	 */
	@RequestMapping("/updateHotelResult")
	public void updateHotelResult(@RequestParam HashMap<String,Object> params){
		Date updateDateTime = new Date();	// 最后更新时间
		
		params.put("updateDateTime", updateDateTime);
		params.put(nariis.falcon.oaf.Constant.CLASS_KEY, ElectricityEnsureHotel.class.getName());
		
		try {
			oafCommands.update(params);
		} catch (Exception e) {
			logger.error("保电任务--修改宾馆数据时异常", e);
		}
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
		
//		Map<String, Object> param = new LinkedHashMap<String, Object>();
//		StringBuilder sql = new StringBuilder("from t in sgcim30.assets.management.ElectricityEnsureHospital ");
//		sql.append("where t.belongTaskId ==: taskId ");
//		param.put("taskId",taskId);
		
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
				logger.error("保电任务--医院--查询数据异常", e);
			}else{
				logger.error("保电任务--医院--导出数据异常", e);
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
	 * <p>描述：删除医院数据</p> 
	 * @param params 
	 * @author:王万胜
	 * 2017年4月28日 下午4:22:59
	 */
	@RequestMapping("/deleteHospitalResult")
	public void deleteHospitalResult(@RequestParam HashMap<String,Object> params){
		String ids = params.get("ids").toString();
		String[] idsArr = ids.split(",");
		int length = idsArr.length;
		
		for(int i=0; i<length; i++){
			params.put("id",idsArr[i]);
			params.put(nariis.falcon.oaf.Constant.CLASS_KEY, ElectricityEnsureHospital.class.getName());
			
			try {
				oafCommands.delete(params);
			} catch (Exception e) {
				logger.error("保电任务--删除医院数据异常", e);
			}
		}
	}
	
	/**
	 * <p>描述：修改医院数据（位置信息）</p> 
	 * @param params 
	 * @author:王万胜
	 * 2017年7月4日 上午10:32:24
	 */
	@RequestMapping("/updateHospitalResult")
	public void updateHospitalResult(@RequestParam HashMap<String,Object> params){
		Date updateDateTime = new Date();	// 最后更新时间
		
		params.put("updateDateTime", updateDateTime);
		params.put(nariis.falcon.oaf.Constant.CLASS_KEY, ElectricityEnsureHospital.class.getName());
		
		try {
			oafCommands.update(params);
		} catch (Exception e) {
			logger.error("保电任务--修改医院数据时异常", e);
		}
	}
	
	
	
	/************************************************************日报************************************************************/
	/**
	 * <p>描述：编辑日报信息（包含新增、修改）</p> 
	 * @param params 
	 * @author:王万胜
	 * 2017年6月5日 下午3:54:10
	 */
	@RequestMapping("/insertOrUpdateDailyResult")
	public void insertOrUpdateDailyResult(@RequestBody HashMap<String,Object> params){
		Date updateDateTime = new Date();	// 最后更新时间
		Date recordDate = new Date();		// 记录时间
		Date logDayTime = null;
		SimpleDateFormat formater1=new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat formater2=new SimpleDateFormat("yyyy-MM-dd HH:mm");
		if(!ObjectUtils.isEmpty(params.get("logDayTime"))){
			try{
				if(!ObjectUtils.isEmpty(params.get("logDayTime"))){
					logDayTime=formater1.parse(params.get("logDayTime").toString());
				}
			}catch (ParseException e) {	
				logger.error("编辑保电任务--日报--汇报记录和保电过程时，时间转换异常！", e);
			}
		}
		
		String taskId = params.get("taskId").toString();
		
		List<Map<String, Object>> gridList1 = (List<Map<String, Object>>) params.get("gridData1");
		int gridLength1 = gridList1.size();
		List<Map<String, Object>> gridList2 = (List<Map<String, Object>>) params.get("gridData2");
		int gridLength2 = gridList2.size();
		
		// 汇报记录
		for(int i=0; i<gridLength1; i++){
			Object logDate = gridList1.get(i).get("logDate");
			if(!ObjectUtils.isEmpty(logDate)){
				try{
					logDate=formater2.parse(logDate.toString());
				}catch (ParseException e) {	
					logger.error("编辑保电任务--日报--汇报记录和保电过程时，时间转换异常！", e);
				}
			}
			
			Object workReport = gridList1.get(i).get("workReport");
			Object reportObject = gridList1.get(i).get("reportObject");
			Object otherAccessory = gridList1.get(i).get("otherAccessory");
			Object remark = gridList1.get(i).get("remark");
			
			Map<String, Object> data = new LinkedHashMap<String, Object>();
			data.put("belongTaskId", taskId);
			data.put("logDate", logDate);
			data.put("workReport", workReport);
			data.put("reportObject", reportObject);
			data.put("otherAccessory", otherAccessory);
			data.put("remark", remark);
			data.put("logDayTime", logDayTime);
			data.put("updateDateTime", updateDateTime);
			data.put(nariis.falcon.oaf.Constant.CLASS_KEY, ElectricityEnsureLog.class.getName());
			
			if(!ObjectUtils.isEmpty(gridList1.get(i).get("id"))){		// 更新
				Object id = gridList1.get(i).get("id");
				data.put("id", id);
				try {
					oafCommands.update(data);
				} catch (Exception e) {
					logger.error("保电日报--汇报记录--更新数据异常", e);
				}
			}else{		// 新增
				data.put("id", UUID.randomUUID().toString());
				data.put("recordDate", recordDate);
				try {
					oafCommands.insert(data);
				} catch (Exception e) {
					logger.error("保电日报--汇报记录--插入数据异常", e);
				}
			}
		}
		
		// 保电过程
		for(int i=0; i<gridLength2; i++){
			Object unit = gridList2.get(i).get("unit");
			Object dutyPerson = gridList2.get(i).get("dutyPerson");
			Object personCount = gridList2.get(i).get("personCount");
			Object vehicleCount = gridList2.get(i).get("vehicleCount");
			Object majorWork = gridList2.get(i).get("majorWork");
			Object problem = gridList2.get(i).get("problem");
			Object needHelpThing = gridList2.get(i).get("needHelpThing");
			Object relatedAccessory = gridList2.get(i).get("relatedAccessory");
			Object remark = gridList2.get(i).get("remark");
			
			Map<String, Object> data = new LinkedHashMap<String, Object>();
			data.put("belongTaskId", taskId);
			data.put("unit", unit);
			data.put("dutyPerson", dutyPerson);
			data.put("personCount", personCount);
			data.put("vehicleCount", vehicleCount);
			data.put("majorWork", majorWork);
			data.put("problem", problem);
			data.put("needHelpThing", needHelpThing);
			data.put("relatedAccessory", relatedAccessory);
			data.put("remark", remark);
			data.put("updateDateTime", updateDateTime);
			data.put("logDayTime", logDayTime);
			data.put(nariis.falcon.oaf.Constant.CLASS_KEY, ElectricityEnsureProcess.class.getName());
			
			if(!ObjectUtils.isEmpty(gridList2.get(i).get("id"))){		// 更新
				Object id = gridList2.get(i).get("id");
				data.put("id", id);
				try {
					oafCommands.update(data);
				} catch (Exception e) {
					logger.error("保电日报--保电过程--更新数据异常", e);
				}
			}else{		// 新增
				data.put("id", UUID.randomUUID().toString());
				data.put("recordDate", recordDate);
				try {
					oafCommands.insert(data);
				} catch (Exception e) {
					logger.error("保电日报--保电过程--插入数据异常", e);
				}
			}
		}
	}
	
	
	
	/************************************************************日报-汇报记录************************************************************/
	/**
	 * <p>描述：获取日报-汇报记录表格数据</p> 
	 * @param params
	 * @return data
	 * @author:王万胜
	 * 2017年5月4日 下午3:30:36
	 */
	@RequestMapping("/queryLogResult")
	public List<Map<String,Object>> queryLogResult(@RequestParam HashMap<String,Object> params){
		String taskId = params.get("taskId").toString();	 	// 保电任务ID
		Date logDayTime1 = null;
		
		SimpleDateFormat formater=new SimpleDateFormat("yyyy-MM-dd");
		try{
			if(!ObjectUtils.isEmpty(params.get("logDayTime"))){
				logDayTime1=formater.parse(params.get("logDayTime").toString());
			}
		}catch (ParseException e) {	
			logger.error("查询保电任务--日报--汇报记录时，时间转换异常！", e);
		}
		
		// 获取logDayTime的下一天
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(logDayTime1);
		calendar.add(Calendar.DAY_OF_YEAR, 1);
		Date logDayTime2 = calendar.getTime();
		
		Map<String, Object> param = new LinkedHashMap<String, Object>();
		StringBuilder sql = new StringBuilder("from t in sgcim30.assets.management.ElectricityEnsureLog ");
		sql.append("where t.belongTaskId ==: taskId ");
		param.put("taskId",taskId);
		sql.append("&& t.logDayTime >=: logDayTime1 ");
		param.put("logDayTime1",logDayTime1);
		sql.append("&& t.logDayTime <: logDayTime2 ");
		param.put("logDayTime2",logDayTime2);
		
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
			logger.error("查询保电任务--日报--汇报记录数据异常", e);
		}
		
		// 编码转换为汉字显示
		if(rslt != null){
			for(Map<String,Object> map : rslt){
				// 时间转换
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
				if(map.get("logDate") != null){
					String logDate = sdf.format(map.get("logDate"));
					map.put("logDate", logDate);
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
	 * <p>描述：删除日报-汇报记录表格中的数据</p> 
	 * @param params 
	 * @author:王万胜
	 * 2017年6月6日 下午2:13:54
	 */
	@RequestMapping("/deleteLogResult")
	public void deleteLogResult(@RequestParam HashMap<String,Object> params){
		String ids = params.get("ids").toString();
		String[] idsArr = ids.split(",");
		int length = idsArr.length;
		
		for(int i=0; i<length; i++){
			Map<String, Object> data = new LinkedHashMap<String, Object>();
			data.put("id",idsArr[i]);
			data.put(nariis.falcon.oaf.Constant.CLASS_KEY, ElectricityEnsureLog.class.getName());
			
			try {
				oafCommands.delete(data);
			} catch (Exception e) {
				logger.error("删除保电任务--日报--汇报记录时异常！", e);
			}
		}
	}
	
	/**
	 * <p>描述：复制日报-汇报记录表格中的数据</p> 
	 * @param params 
	 * @author:王万胜
	 * 2017年6月6日 下午3:52:26
	 */
	@RequestMapping("/copyLogResult")
	public void copyLogResult(@RequestParam HashMap<String,Object> params){
		Date today = new Date();
		params.put("id", UUID.randomUUID().toString());
		params.put("logDate", today);
		params.put("logDayTime", today);
		params.put("recordDate", today);
		params.put("updateDateTime", today);
		params.put(nariis.falcon.oaf.Constant.CLASS_KEY, ElectricityEnsureLog.class.getName());
		try {
			oafCommands.insert(params);
		} catch (Exception e) {
			logger.error("保电日报--保电过程--插入数据异常", e);
		}
	}

	
	
	/************************************************************日报-保电过程************************************************************/
	/**
	 * <p>描述：获取日报-保电过程表格数据</p> 
	 * @param params
	 * @return data
	 * @author:王万胜
	 * 2017年5月4日 下午3:30:36
	 */
	@RequestMapping("/queryProcessResult")
	public List<Map<String,Object>> queryProcessResult(@RequestParam HashMap<String,Object> params){
		String taskId = params.get("taskId").toString();	 	// 保电任务ID
		Date logDayTime1 = null;
		
		SimpleDateFormat formater=new SimpleDateFormat("yyyy-MM-dd");
		try{
			if(!ObjectUtils.isEmpty(params.get("logDayTime"))){
				logDayTime1=formater.parse(params.get("logDayTime").toString());
			}
		}catch (ParseException e) {	
			logger.error("查询保电任务--日报--保电过程时，时间转换异常！", e);
		}
		
		// 获取logDayTime的下一天
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(logDayTime1);
		calendar.add(Calendar.DAY_OF_YEAR, 1);
		Date logDayTime2 = calendar.getTime();
		
//		Map<String, Object> param = new LinkedHashMap<String, Object>();
//		StringBuilder sql = new StringBuilder("from t in sgcim30.assets.management.ElectricityEnsureProcess ");
//		sql.append("where t.belongTaskId ==: taskId ");
//		param.put("taskId",taskId);
		
		Map<String, Object> param = new LinkedHashMap<String, Object>();
		StringBuilder sql = new StringBuilder("from t in ( ");
		sql.append("from a in sgcim30.assets.management.ElectricityEnsureProcess ");
		sql.append("where a.belongTaskId ==: taskId ");
		param.put("taskId",taskId);
		sql.append("&& (a.unit == null || a.personCount == null || a.vehicleCount == null) select a ");
		sql.append("union all ");
		sql.append("from b in sgcim30.assets.management.ElectricityEnsureProcess ");
		sql.append("where b.belongTaskId ==: taskId ");
		param.put("taskId",taskId);
		sql.append("&& b.unit != null && b.personCount != null && b.vehicleCount != null select b ");
		sql.append(") where 1==1 ");
		
		sql.append("&& t.logDayTime >=: logDayTime1 ");
		param.put("logDayTime1",logDayTime1);
		sql.append("&& t.logDayTime <: logDayTime2 ");
		param.put("logDayTime2",logDayTime2);
		
		// 表格分页
		StringBuilder sqlCount = new StringBuilder();
		sqlCount.append(sql);
		sqlCount.append(" select sqlCount = t.count() ");
		
		// 导出
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
				logger.error("查询保电任务--日报--保电过程数据异常", e);
			}else{
				logger.error("导出保电任务--日报--保电过程数据异常", e);
			}
		}
		
		// 编码转换为汉字显示
		if(rslt != null){
			for(Map<String,Object> map : rslt){
				// 单位（组织机构）
				if(map.get("unit") != null){
					HashMap<String, Object> par = new HashMap<String, Object>();
					par.put("taskId", taskId);
					List<Map<String, Object>> orgTree = getOrganizationTreeList(par);
					int size = orgTree.size();
					for(int i=0; i<size; i++){
						if(map.get("unit").equals(orgTree.get(i).get("id"))){
							map.put("unit", orgTree.get(i).get("text"));
						}
					}
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
	 * <p>描述：删除日报-保电过程表格中的数据</p> 
	 * @param params 
	 * @author:王万胜
	 * 2017年6月6日 下午2:13:54
	 */
	@RequestMapping("/deleteProcessResult")
	public void deleteProcessResult(@RequestParam HashMap<String,Object> params){
		String ids = params.get("ids").toString();
		String[] idsArr = ids.split(",");
		int length = idsArr.length;
		
		for(int i=0; i<length; i++){
			params.put("id",idsArr[i]);
			params.put(nariis.falcon.oaf.Constant.CLASS_KEY, ElectricityEnsureProcess.class.getName());
			
			try {
				oafCommands.delete(params);
			} catch (Exception e) {
				logger.error("保电任务--删除日报-保电过程表格中的数据异常", e);
			}
		}
	}
	
	
	
	/************************************************************公共方法************************************************************/
	/**
	 * <p>描述：通过ID获取各表格记录的更新时间</p> 
	 * @param params
	 * @return data
	 * @author:王万胜
	 * 2017年6月11日 上午10:51:11
	 */
	@RequestMapping("/queryUpdateTime")
	public List<Map<String,Object>> queryUpdateTime(@RequestParam HashMap<String,String> params){
		String id = params.get("id");	 // tab类型
		String tableName = params.get("tableName");
		
		List<Map<String, Object>> rslt = null;
		Map<String, Object> param = new LinkedHashMap<String, Object>();
		StringBuilder sql = new StringBuilder(" from t in " + tableName);
		sql.append(" where t.id ==: id ");
		param.put("id",id);
		sql.append("select t ");
		
		try {
			rslt = oafQuery.executeForList(sql.toString(), param);
		} catch (OafException e) {
			logger.error("保电任务--通过ID获取各表格记录的更新时间异常", e);
		}
		
		// 返回查询结果
		return rslt;
	}
	
	
	/******************************** 运检指令-保电-日常业务start ****************************************/
	@Autowired
	CommonQueryUtils commonQuery;

	private static final String PWSUPPLYCOLNAME = "运检指令-保电";
	private static final String[] PWSUPPLYCOLS = new String[] { "id", "recordDate", "ensureLevel", "theme"
			};//运检指令-保电拼接字段：保电级别+保电主题

	/**
	 * <p>
	 * 描述：获取保电统计数据
	 * </p>
	 * 
	 * @param param
	 * @return 获取保电统计数据据
	 * @author:范阳 2017年3月14日 下午3:54:51
	 */
	@RequestMapping("/queryPwSupplyCount")
	public Map<String, Object> queryPwSupplyCount(@RequestParam Map<String, Object> param) {

		Map<String, Object> rsl = new HashMap<String, Object>();
		
		rsl.put("eeCommandNewCnt", queryPwSupplyNewCount());
		rsl.put("eeCommandTrackCnt", queryPwSupplyTrackCount());

		return rsl;
	}

	/**
	 * <p>
	 * 描述：获取预告警grid数据
	 * </p>
	 * 
	 * @param param
	 * @return grid数据
	 * @author:范阳 2017年3月14日 下午3:55:53
	 */
	@RequestMapping("/queryPwSupplyInfo")
	public List<Map<String, Object>> queryPwSupplyInfo(@RequestParam Map<String, Object> param) {
		HashMap<String,Object> paramMap = parseQueryParam(param);
		
		List<String> columnList = new ArrayList<String>(Arrays.asList(PWSUPPLYCOLS));
		
		List<Map<String, Object>> rsl = commonQuery.queryInfo(ElectricityEnsure.class.getName(), paramMap
				, columnList, null, null);		
		
		Constant ensureLevelCst= ConstantRegistry.findConstant(PowerLever.TYPE);
		for(Map<String,Object> map : rsl){
			
			Integer ensureLevel = (Integer)map.get("ensureLevel");
			// 保电级别
			String ensureLevelName = ensureLevel != null ? 
					ensureLevelCst.getFieldValueByItemValue(ensureLevel, PowerLever.NAME) : "";
			map.put("ensureLevel", ensureLevelName);
		}

		Map<String, Object> scalars = new HashMap<>();
		scalars.put("id", "id" );
		scalars.put("segment2", "recordDate");
		scalars.put("segment3", Arrays.copyOfRange(PWSUPPLYCOLS, 2, PWSUPPLYCOLS.length));

		rsl = commonQuery.convertQueryResult(rsl, scalars);

		Map<String, Object> colEntry = new HashMap<>();
		colEntry.put("segment1", PWSUPPLYCOLNAME);

		commonQuery.addColumn(rsl, colEntry);

		return rsl;
	}
	

	private long queryPwSupplyNewCount() {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("status", "1");

		return commonQuery.queryCount(ElectricityEnsure.class.getName(), param);
	}

	private long queryPwSupplyTrackCount() {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("status", "2");

		return commonQuery.queryCount(ElectricityEnsure.class.getName(), param);
	}
	
	private HashMap<String, Object> parseQueryParam(Map<String, Object> param){
		HashMap<String,Object> paramMap = new HashMap<>();
		Object confirmStatusObj = param.get("confirmStatus");
		if(null != confirmStatusObj && !"".equals((String)confirmStatusObj)){
			String confirmStatus = (String)confirmStatusObj;
			switch(confirmStatus){
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
		    paramMap.put("status",confirmStatus);
		}
		
		return paramMap;
	}
	/********************************日常业务end****************************************/
	
	
	
	/******************************** 变电保电start ****************************************/
	@Autowired
	private IOrgnizationInfo org;
	
	// 一级页面
	/**
	 * <p>
	 * 描述: 获取各个保电等级的数据
	 * </p>
	 * 
	 * @param paramMap
	 * @return 各个保电等级的数据
	 * @author: 俞聪 2017年6月1日 下午4:10:10
	 */
	@RequestMapping("/getEnsureLevelCount")
	public List<Map<String, Object>> getEnsureLevelCount(@RequestParam Map<String, Object> paramMap) {
		// 创建返回的结果集
		List<Map<String, Object>> list = null;
		// 将当前时间放进参数集合
		paramMap.put("currentTime", new Date());

		StringBuffer sBuffer = new StringBuffer();
		sBuffer.append(" from a in " + AstTransformer.class.getName());
		sBuffer.append(" join s in " + StationLineEnsure.class.getName());
		sBuffer.append(" on a.id equals s.stationLineID ");
		sBuffer.append(" join e in " + ElectricityEnsure.class.getName());
		sBuffer.append(" on e.id equals s.belongTaskId ");
		sBuffer.append(" where 1==1 ");
		sBuffer.append(createWhereSql(paramMap));
		sBuffer.append(" groupby s.ensureLevel ");
		sBuffer.append(" select s.ensureLevel, ensurelevelsum = s.id.count() ");

		try {
			list = oafQuery.executeForList(sBuffer.toString(), paramMap);
		} catch (OafException e) {
			logger.error("获取保电级别失败", e);
		}
		return list;
	}

	/**
	 * <p>
	 * 描述: 获取不同电压等级的变电站数量
	 * </p>
	 * 
	 * @param paramMap
	 * @return 不同电压等级的变电站数量
	 * @author: 俞聪 2017年6月2日 下午1:39:54
	 */
	@RequestMapping("/getVoltageLevelCount")
	public List<Map<String, Object>> getVoltageLevelCount(@RequestParam Map<String, Object> paramMap) {
		// 创建返回的结果集
		List<Map<String, Object>> list = null;
		// 将当前时间放进参数集合
		paramMap.put("currentTime", new Date());

		StringBuffer sBuffer = new StringBuffer();
		sBuffer.append(" from a in " + AstTransformer.class.getName());
		sBuffer.append(" join s in " + StationLineEnsure.class.getName());
		sBuffer.append(" on a.id equals s.stationLineID ");
		sBuffer.append(" join e in " + ElectricityEnsure.class.getName());
		sBuffer.append(" on e.id equals s.belongTaskId ");
		sBuffer.append(" where 1==1 ");
		sBuffer.append(createWhereSql(paramMap));
		sBuffer.append(" groupby a.voltageLevel ");
		sBuffer.append(" select a.voltageLevel, voltagelevelsum = a.id.count() ");

		try {
			list = oafQuery.executeForList(sBuffer.toString(), paramMap);
		} catch (OafException e) {
			logger.error("获取电压等级失败", e);
		}
		return list;
	}

	/**
	 * <p>
	 * 描述: 获得当前所有可用变电站的id,数据来源是cim.stationlineensure
	 * </p>
	 * 
	 * @param paramMap
	 * @return 当前所有可用变电站的id,数据来源是cim.stationlineensure
	 * @author: 俞聪 2017年6月12日 下午3:05:08
	 */
	@RequestMapping("/getStationLineId")
	public List<Map<String, Object>> getStationLineId(@RequestParam Map<String, Object> paramMap) {
		// 创建返回的结果集
		List<Map<String, Object>> list = null;
		// 将当前时间放进参数集合
		paramMap.put("currentTime", new Date());

		StringBuffer sBuffer = new StringBuffer();
		sBuffer.append(" from a in " + AstTransformer.class.getName());
		sBuffer.append(" join s in " + StationLineEnsure.class.getName());
		sBuffer.append(" on a.id equals s.stationLineID ");
		sBuffer.append(" join e in " + ElectricityEnsure.class.getName());
		sBuffer.append(" on e.id equals s.belongTaskId ");
		sBuffer.append(" where 1==1 ");
		sBuffer.append(createWhereSql(paramMap));
		sBuffer.append(
				" select bdID=e.id, a.maintemanceUnit, bdzName=a.name, s.stationLineID, s.ensureLevel, a.voltageLevel ");

		try {
			list = oafQuery.executeForList(sBuffer.toString(), paramMap);
		} catch (OafException e) {
			logger.error("获取变电站ID失败", e);
		}
		return list;
	}

	/**
	 * <p>
	 * 描述: 拼接当前时间的fql语句
	 * </p>
	 * 
	 * @param 动态数据集合
	 * @return 返回当前时间的fql语句的字符串形式
	 * @author: 俞聪 2017年6月1日 下午4:10:10
	 */
	private String createWhereSql(Map<String, Object> paramMap) {
		StringBuffer whereFql = new StringBuffer();

		if (!ObjectUtils.isEmpty(paramMap.get("currentTime"))) {
			whereFql.append(" && e.startTime <= :currentTime && e.endTime >= :currentTime ");
		}
		if (!ObjectUtils.isEmpty(paramMap.get("maintemanceUnit"))) {
			int level = ConvertUtils.cInteger(paramMap.get("orgLevel"), 3);
			if (3 == level) {
				whereFql.append(" && a.belongProvince == :maintemanceUnit ");
			} else if (4 == level) {
				whereFql.append(" && a.belongCity == :maintemanceUnit ");
			} else {
				whereFql.append(" && a.maintemanceUnit == :maintemanceUnit ");
			}
		}
		if (!ObjectUtils.isEmpty(paramMap.get("ensureLevel"))) {
			whereFql.append(" && s.ensureLevel == :ensureLevel ");
		}
		if (!ObjectUtils.isEmpty(paramMap.get("voltageLevel"))) {
			whereFql.append(" && a.voltageLevel == :voltageLevel ");
		}

		whereFql.append(" && e.status == 2 ");
		whereFql.append(" && s.stationLineType == '02' ");
		whereFql.append(" && a.assetProperty != '05' && a.inUseState == '20' && a.voltageLevel >= 25 ");
		whereFql.append(" && (a.stationType == 100 || a.stationType == 131) ");
		return whereFql.toString();
	}
	
	// 二级页面
	/**
	 * <p>
	 * 描述: 返回条件查询后获得的保电级别数据
	 * </p>
	 * 
	 * @param paramMap
	 * @return 条件查询后获得的保电级别数据
	 * @author: 俞聪 2017年6月5日 下午12:29:42
	 */
	@RequestMapping(value = "/getGridData", method = RequestMethod.GET)
	public List<Map<String, Object>> getGridData(@RequestParam Map<String, Object> paramMap) {
		// 创建结果集
		List<Map<String, Object>> gridList = null;
		// 格式化日期类型和分页数据
		paramMap = dealCondition(paramMap);
		StringBuffer sBuffer = new StringBuffer();
		sBuffer.append(" from t in ");
		sBuffer.append(" from a in " + AstTransformer.class.getName());
		sBuffer.append(" join s in " + StationLineEnsure.class.getName());
		sBuffer.append(" on a.id equals s.stationLineID ");
		sBuffer.append(" join e in " + ElectricityEnsure.class.getName());
		sBuffer.append(" on e.id equals s.belongTaskId ");
		sBuffer.append(" where 1==1 ");
		sBuffer.append(createGridWhereSql(paramMap));
		sBuffer.append(" orderby e.startTime descending select a.maintemanceUnit, s.stationLineName, a.voltageLevel, e.theme, ");
		sBuffer.append(" s.ensureLevel, e.ensureDetails, e.startTime, e.endTime ");
		sBuffer.append(" select t then skip " + paramMap.get("pageIndex") + " then take " + paramMap.get("pageSize"));

		try {
			gridList = oafQuery.executeForList(sBuffer.toString(), paramMap);
			if (!CollectionUtils.isEmpty(gridList)) {
				for (int i = 0; i < gridList.size(); i++) {
					// 保电级别
					if (!ObjectUtils.isEmpty(gridList.get(i).get("ensureLevel"))) {
						gridList.get(i).put("ensureLevel", getNameByValue(PowerLever.TYPE,
								ConvertUtils.cInteger(gridList.get(i).get("ensureLevel"), 0), PowerLever.NAME));
					}
					// 电压等级
					if (!ObjectUtils.isEmpty(gridList.get(i).get("voltageLevel"))) {
						gridList.get(i).put("voltageLevel", getNameByValue(VoltageLevel.TYPE,
								ConvertUtils.cInteger(gridList.get(i).get("voltageLevel"), 0), VoltageLevel.NAME));
					}
					// 运维单位
					if (!ObjectUtils.isEmpty(gridList.get(i).get("maintemanceUnit"))) {
						gridList.get(i).put("maintemanceUnit",
								org.getOrgFullNameById(gridList.get(i).get("maintemanceUnit").toString()));
					}
				}
			}
		} catch (OafException e) {
			logger.error("获取二级页面保电级别数据失败", e);
		}
		return gridList;
	}

	/**
	 * <p>
	 * 描述: 返回条件查询获得的所有数据
	 * </p>
	 * 
	 * @param paramMap
	 * @return 条件查询获得的所有数据
	 * @author: 俞聪 2017年6月5日 下午12:38:21
	 */
	@RequestMapping(value = "/getGridCount", method = RequestMethod.GET)
	public Integer getGridCount(@RequestParam Map<String, Object> paramMap) {
		// 声明返回值
		Integer count = 0;
		// 格式化日期类型和分页数据
		paramMap = dealCondition(paramMap);

		StringBuffer sBuffer = new StringBuffer();
		sBuffer.append(" from a in " + AstTransformer.class.getName());
		sBuffer.append(" join s in " + StationLineEnsure.class.getName());
		sBuffer.append(" on a.id equals s.stationLineID ");
		sBuffer.append(" join e in " + ElectricityEnsure.class.getName());
		sBuffer.append(" on e.id equals s.belongTaskId ");
		sBuffer.append(" where 1==1 ");
		sBuffer.append(createGridWhereSql(paramMap));
		sBuffer.append(" select a.id.count() ");

		try {
			count = (Integer) oafQuery.executeScalar(sBuffer.toString(), paramMap);
		} catch (OafException e) {
			logger.error("获取二级页面保电级别数据总数失败", e);
		}
		return count;
	}

	/**
	 * <p>
	 * 描述: 初始化运维单位下拉框
	 * </p>
	 * 
	 * @return 初始化运维单位
	 * @author: 俞聪 2017年6月5日 下午7:06:49
	 */
	@RequestMapping("/getMaintemanceUnit")
	public List<Map<String, Object>> getMaintemanceUnit() {
		List<Map<String, Object>> list = null;
		Map<String, Object> paramMap = new HashMap<String, Object>(); // 空的集合
		StringBuffer sBuffer = new StringBuffer();

		sBuffer.append(" from a in " + AstTransformer.class.getName());
		sBuffer.append(" join s in " + StationLineEnsure.class.getName());
		sBuffer.append(" on a.id equals s.stationLineID ");
		sBuffer.append(" join e in " + ElectricityEnsure.class.getName());
		sBuffer.append(" on e.id equals s.belongTaskId ");
		sBuffer.append(" where 1==1 ");
		sBuffer.append(createGridWhereSql(paramMap));
		sBuffer.append(" groupby a.maintemanceUnit ");
		sBuffer.append(" select a.maintemanceUnit ");

		try {
			list = oafQuery.executeForList(sBuffer.toString());
		} catch (OafException e) {
			logger.error("获取运维单位", e);
		}
		return list;
	}

	/**
	 * <p>
	 * 描述: 初始化电压等级下拉框
	 * </p>
	 * 
	 * @return 初始化电压等级
	 * @author: 俞聪 2017年6月7日 下午4:43:48
	 */
	@RequestMapping("/getVoltageLevelCombobox")
	public List<Map<String, Object>> getVoltageLevelCombobox() {
		List<Map<String, Object>> list = null;
		Map<String, Object> paramMap = new HashMap<String, Object>(); // 空的集合
		StringBuffer sBuffer = new StringBuffer();

		sBuffer.append(" from a in " + AstTransformer.class.getName());
		sBuffer.append(" join s in " + StationLineEnsure.class.getName());
		sBuffer.append(" on a.id equals s.stationLineID ");
		sBuffer.append(" join e in " + ElectricityEnsure.class.getName());
		sBuffer.append(" on e.id equals s.belongTaskId ");
		sBuffer.append(" where 1==1 ");
		sBuffer.append(createGridWhereSql(paramMap));
		sBuffer.append(" groupby a.voltageLevel ");
		sBuffer.append(" orderby a.voltageLevel ");
		sBuffer.append(" select a.voltageLevel ");

		try {
			list = oafQuery.executeForList(sBuffer.toString());
		} catch (OafException e) {
			logger.error("获取电压等级", e);
		}
		return list;
	}

	/**
	 * <p>
	 * 描述: 格式化日期类型和分页数据
	 * </p>
	 * 
	 * @author: 俞聪 2017年6月5日 下午12:29:42
	 */
	private Map<String, Object> dealCondition(Map<String, Object> paramMap) {
		if (!ObjectUtils.isEmpty(paramMap.get("pageIndex")) && !ObjectUtils.isEmpty(paramMap.get("pageSize"))) {
			int pageIndex = Integer.parseInt(paramMap.get("pageIndex").toString());
			int pageSize = Integer.parseInt(paramMap.get("pageSize").toString());
			paramMap.put("pageIndex", pageIndex * pageSize);
			paramMap.put("pageSize", pageSize);
		}

		if (!ObjectUtils.isEmpty(paramMap.get("currentTime"))) {
			paramMap.put("currentTime", new Date());
		}
		if (!ObjectUtils.isEmpty(paramMap.get("startbefore"))) {
			paramMap.put("startbefore", ConvertUtils.formatDateObj(paramMap.get("startbefore")));
		}
		if (!ObjectUtils.isEmpty(paramMap.get("startafter"))) {
			paramMap.put("startafter", ConvertUtils.formatDateObj(paramMap.get("startafter")));
		}
		if (!ObjectUtils.isEmpty(paramMap.get("endbefore"))) {
			paramMap.put("endbefore", ConvertUtils.formatDateObj(paramMap.get("endbefore")));
		}
		if (!ObjectUtils.isEmpty(paramMap.get("endafter"))) {
			paramMap.put("endafter", ConvertUtils.formatDateObj(paramMap.get("endafter")));
		}
		return paramMap;
	}

	/**
	 * <p>
	 * 描述: 二级页面数据导出
	 * </p>
	 * 
	 * @param paramMap
	 * @return 二级页面数据导出
	 * @author: 俞聪 2017年6月7日 下午8:16:28
	 */
	@RequestMapping(value = "/exportGridData", method = RequestMethod.GET)
	public List<Map<String, Object>> exportGridData(@RequestParam Map<String, Object> paramMap) {
		// 创建结果集
		List<Map<String, Object>> gridList = null;
		// 格式化日期类型和分页数据
		paramMap = dealCondition(paramMap);
		StringBuffer sBuffer = new StringBuffer();
		sBuffer.append(" from a in " + AstTransformer.class.getName());
		sBuffer.append(" join s in " + StationLineEnsure.class.getName());
		sBuffer.append(" on a.id equals s.stationLineID ");
		sBuffer.append(" join e in " + ElectricityEnsure.class.getName());
		sBuffer.append(" on e.id equals s.belongTaskId ");
		sBuffer.append(" where 1==1 ");
		sBuffer.append(createGridWhereSql(paramMap));
		sBuffer.append(" select a.maintemanceUnit, s.stationLineName, a.voltageLevel, e.theme, ");
		sBuffer.append(" s.ensureLevel, e.ensureDetails, e.startTime, e.endTime ");

		try {
			gridList = oafQuery.executeForList(sBuffer.toString(), paramMap);
			if (!CollectionUtils.isEmpty(gridList)) {
				for (int i = 0; i < gridList.size(); i++) {
					// 保电级别
					if (!ObjectUtils.isEmpty(gridList.get(i).get("ensureLevel"))) {
						gridList.get(i).put("ensureLevel", getNameByValue(PowerLever.TYPE,
								ConvertUtils.cInteger(gridList.get(i).get("ensureLevel"), 0), PowerLever.NAME));
					}
					// 电压等级
					if (!ObjectUtils.isEmpty(gridList.get(i).get("voltageLevel"))) {
						gridList.get(i).put("voltageLevel", getNameByValue(VoltageLevel.TYPE,
								ConvertUtils.cInteger(gridList.get(i).get("voltageLevel"), 0), VoltageLevel.NAME));
					}
					// 运维单位
					if (!ObjectUtils.isEmpty(gridList.get(i).get("maintemanceUnit"))) {
						gridList.get(i).put("maintemanceUnit",
								org.getOrgFullNameById(gridList.get(i).get("maintemanceUnit").toString()));
					}
				}
			}
		} catch (OafException e) {
			logger.error("二级页面数据导出失败", e);
		}
		return gridList;
	}

	/**
	 * <p>
	 * 描述: 对查询参数进行字符串拼接
	 * </p>
	 * 
	 * @author: 俞聪 2017年6月5日 下午12:29:42
	 */
	private String createGridWhereSql(Map<String, Object> paramMap) {
		StringBuffer whereFql = new StringBuffer();
		// 保电级别
		if (!ObjectUtils.isEmpty(paramMap.get("ensureLevel"))) {
			if (!paramMap.get("ensureLevel").toString().contains(",")) {
				whereFql.append(" && s.ensureLevel == :ensureLevel ");
			} else {
				whereFql.append(
						" && " + createConditionWithOr(String.valueOf(paramMap.get("ensureLevel")), "s.ensureLevel"));
			}
		}

		// 电压等级
		if (!ObjectUtils.isEmpty(paramMap.get("voltageLevel"))) {
			if (!paramMap.get("voltageLevel").toString().contains(",")) {
				whereFql.append(" && a.voltageLevel == :voltageLevel ");
			} else {
				whereFql.append(
						" && " + createConditionWithOr(String.valueOf(paramMap.get("voltageLevel")), "a.voltageLevel"));
			}
		}

		// 第一次进入二级页面才用到的当前时间,当前时间和其他四个时间不会同时存在
		if (!ObjectUtils.isEmpty(paramMap.get("currentTime"))) {
			whereFql.append(" && e.startTime <= :currentTime && e.endTime >= :currentTime ");
		}

		// 开始时间前
		if (!ObjectUtils.isEmpty(paramMap.get("startbefore"))) {
			whereFql.append(" && :startbefore <= e.startTime ");
		}
		// 开始时间后
		if (!ObjectUtils.isEmpty(paramMap.get("startafter"))) {
			whereFql.append(" && e.startTime <= :startafter ");
		}
		// 结束时间前
		if (!ObjectUtils.isEmpty(paramMap.get("endbefore"))) {
			whereFql.append(" && :endbefore <= e.endTime ");
		}
		// 结束时间后
		if (!ObjectUtils.isEmpty(paramMap.get("endafter"))) {
			whereFql.append(" && e.endTime <= :endafter ");
		}
		// 运维单位
		if (!ObjectUtils.isEmpty(paramMap.get("maintemanceUnit"))) {
			whereFql.append(" && a.maintemanceUnit == :maintemanceUnit ");
		}
		// 配合站所管理增加的查询条件
		if (!ObjectUtils.isEmpty(paramMap.get("subStationId"))) {
			whereFql.append(" && a.id ==:subStationId ");
		}

		whereFql.append(" && e.status == 2 ");
		whereFql.append(" && s.stationLineType == '02' ");
		whereFql.append(" && a.assetProperty != '05' && a.inUseState == '20' && a.voltageLevel >= 25 ");
		whereFql.append(" && (a.stationType == 100 || a.stationType == 131) ");
		return whereFql.toString();
	}

	/**
	 * 取得对应属性的值
	 * 
	 * @param constantName
	 *            constant类名
	 * @param itemValue
	 *            值
	 * @param name
	 *            属性名
	 * @return 对应属性的值
	 */
	private String getNameByValue(String constantName, Object itemValue, String name) {
		Constant cst = ConstantRegistry.findConstant(constantName);
		return cst.getFieldValueByItemValue(itemValue, name);
	}

	/**
	 * <p>
	 * 描述：根据传入的多条件字符串拼接为fql,逻辑运算符为||，比较运算符为==
	 * </p>
	 * 
	 * @param conditionStr
	 *            所有可选条件，逗号分隔
	 * @param column
	 *            条件对应的字段名
	 * @return string
	 * @author:贺杰 2017年4月7日 下午4:11:27
	 */
	private String createConditionWithOr(String conditionStr, String column) {
		return createMultiConditionStr(conditionStr, column, "||", "==");
	}

	/**
	 * <p>
	 * 描述：根据传入的多条件字符串拼接为fql：例如：将a,b,c转换为(column == a || column == b || column ==
	 * c)
	 * </p>
	 * 
	 * @param conditionStr
	 *            所有可选条件，逗号分隔
	 * @param column
	 *            条件对应的字段名
	 * @param logicFlag
	 *            逻辑运算符
	 * @param compareFlag
	 *            比较运算符
	 * @return string
	 * @author:贺杰 2017年4月7日 下午3:55:09
	 */
	private String createMultiConditionStr(String conditionStr, String column, String logicFlag, String compareFlag) {
		if (conditionStr.contains("N/A")) {
			conditionStr.replace("N/A", "null");
		}
		StringBuilder fqlConditions = new StringBuilder(" (" + column + compareFlag + "'");
		fqlConditions.append(conditionStr.replaceAll(",", "'" + logicFlag + " " + column + compareFlag + "'"));
		fqlConditions.append("')");
		return fqlConditions.toString();
	}
	
	/********************************保电保电end****************************************/
}

