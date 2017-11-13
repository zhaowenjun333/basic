package nariis.mcsas.management.powersupply.business.core.client;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import nariis.falcon.commons.springcloud.RequestCache;


/**
 * <p>ClassName:PowerSupplyGuaranteeServiceClient，</p>
 * <p>描述:TODO</p>
 * @author 王万胜
 * @date 2017年3月23日下午3:04:02
 * @version 
 */
@FeignClient(name = "managementPowerSupply")
@RequestMapping("/powerSupplyGuarantee")
public interface PowerSupplyGuaranteeServiceClient {
	
	/************************************************************基础信息************************************************************/
	/**
	 * <p>描述：TODO</p> 
	 * @return 各阶段记录数量
	 * @author:王万胜
	 * 2017年3月14日 下午7:51:54
	 */
	@RequestCache
	@RequestMapping("/gridsCountResult")
	public List<Map<String, Object>> gridsCountResult();
	
	/**
	 * <p>描述：查询基础信息主表数据</p> 
	 * @param params
	 * @return List<Map<String, Object>>
	 * @author:王万胜
	 * 2017年3月9日 下午4:02:25
	 */
	@RequestCache
	@RequestMapping("/queryPowerSupplyGuaranteeResult")
	public List<Map<String, Object>> queryPowerSupplyGuaranteeResult(@RequestParam HashMap<String, Object> params);
	
	/**
	 * <p>描述：获取选定的一条保电任务的基础信息</p> 
	 * @param params
	 * @return data
	 * @author:王万胜
	 * 2017年5月16日 下午7:21:33
	 */
	@RequestCache
	@RequestMapping("/queryNoteInfo")
	public List<Map<String, Object>> queryNoteInfo(@RequestParam HashMap<String, Object> params);

	/**
	 * <p>描述：获取基础信息保电阶段表格数据/p> 
	 * @param params
	 * @return data
	 * @author:王万胜
	 * 2017年4月13日 下午4:49:13
	 */
	@RequestCache
	@RequestMapping("/queryElectricityEnsurePhaseResult")
	public List<Map<String, Object>> queryElectricityEnsurePhaseResult(@RequestParam HashMap<String, Object> params);
	
	/**
	 * <p>描述：新增基础信息数据</p> 
	 * @param hashmap 
	 * @return id
	 * @author:王万胜
	 * 2017年3月14日 上午9:29:39
	 */
	@RequestMapping("/insertPowerSupplyGuaranteeResult")
	public HashMap<String,Object> insertPowerSupplyGuaranteeResult(@RequestBody HashMap<String, Object> hashmap);
	
	/**
	 * <p>描述：编辑基础信息数据</p> 
	 * @param hashmap 
	 * @author:王万胜
	 * 2017年3月14日 上午9:29:39
	 */
	@RequestMapping("/updatePowerSupplyGuaranteeResult")
	public void updatePowerSupplyGuaranteeResult(@RequestBody HashMap<String, Object> hashmap);
	
	/**
	 * <p>描述：删除基础信息主表数据</p> 
	 * @param hashmap 
	 * @author:王万胜
	 * 2017年3月14日 上午9:29:39
	 */
	@RequestMapping("/deletePowerSupplyGuaranteeResult")
	public void deletePowerSupplyGuaranteeResult(@RequestParam HashMap<String, Object> hashmap);
	
	/**
	 * <p>描述：获取保电级别下拉框</p> 
	 * @return powerLever
	 * @author:王万胜
	 * 2017年4月6日 下午2:00:03
	 */
	@RequestMapping("/getPowerLeverList")
	public String getPowerLeverList();
	
	/**
	 * <p>描述：获取任务来源下拉框</p> 
	 * @return taskSource
	 * @author:王万胜
	 * 2017年4月11日 下午7:08:25
	 */
	@RequestMapping("/getTaskSourceList")
	public String getTaskSourceList();
	
	
	
	/************************************************************组织机构************************************************************/
	/**
	 * <p>描述：获取组织机构树</p> 
	 * @param params
	 * @return tree
	 * @author:王万胜
	 * 2017年4月18日 下午7:40:02
	 */
	@RequestCache
	@RequestMapping("/getOrganizationTreeList")
	public List<Map<String,Object>> getOrganizationTreeList(@RequestParam HashMap<String, Object> params);
	
	/**
	 * <p>描述：获取组织机构表格信息</p> 
	 * @param params
	 * @return data
	 * @author:王万胜
	 * 2017年4月18日 下午6:28:16
	 */
	@RequestCache
	@RequestMapping("/queryOrganizationResult")
	public List<Map<String, Object>> queryOrganizationResult(@RequestParam HashMap<String, Object> params);
	
	/**
	 * <p>描述：插入或修改组织机构</p> 
	 * @param hashmap 
	 * @author:王万胜
	 * 2017年4月19日 下午2:05:31
	 */
	@RequestMapping("/insertOrUpdateOrganizationResult")
	public void insertOrUpdateOrganizationResult(@RequestBody HashMap<String, Object> hashmap);
	
	/**
	 * <p>描述：获取下级组织机构</p> 
	 * @param params
	 * @return data
	 * @author:王万胜
	 * 2017年4月20日 下午1:53:37
	 */
	@RequestCache
	@RequestMapping("/querySubOrganizationResult")
	public List<Map<String, Object>> querySubOrganizationResult(@RequestParam HashMap<String, Object> params);
	
	/**
	 * <p>描述：删除组织机构记录</p> 
	 * @param hashmap 
	 * @author:王万胜
	 * 2017年4月19日 下午7:07:32
	 */
	@RequestMapping("/deleteOrganizationResult")
	public void deleteOrganizationResult(@RequestParam HashMap<String, Object> hashmap);
	
	
	
	/************************************************************保电站线************************************************************/
	/**
	 * <p>描述：获取保电站线表格数据</p> 
	 * @param params
	 * @return data
	 * @author:王万胜
	 * 2017年4月21日 上午10:24:24
	 */
	@RequestCache
	@RequestMapping("/queryStationOrLineResult")
	public List<Map<String, Object>> queryStationOrLineResult(@RequestParam HashMap<String, Object> params);
	
	/**
	 * <p>描述：插入或修改保电站线</p> 
	 * @param hashmap 
	 * @author:王万胜
	 * 2017年6月2日 上午11:04:17
	 */
	@RequestMapping("/insertStationOrLineResult")
	public void insertStationOrLineResult(@RequestParam HashMap<String, Object> hashmap);
	
	/**
	 * <p>描述：修改保电站线信息</p> 
	 * @param hashmap 
	 * @author:王万胜
	 * 2017年4月21日 下午3:44:52
	 */
	@RequestMapping("/updateStationOrLineResult")
	public void updateStationOrLineResult(@RequestParam HashMap<String, Object> hashmap);
	
	/**
	 * <p>描述：删除保电站线信息</p> 
	 * @param hashmap 
	 * @author:王万胜
	 * 2017年6月5日 下午2:15:36
	 */
	@RequestMapping("/deleteStationOrLineResult")
	public void deleteStationOrLineResult(@RequestParam HashMap<String, Object> hashmap);
	
	
	
	/**
	 * <p>描述：获取变电站保电任务总数</p> 
	 * @param param
	 * @return long
	 * @author:马超
	 * 2017年6月5日 下午5:03:59
	 */
	@RequestMapping("/getElectricityEnsureCount")
	public long getElectricityEnsureCount(@RequestParam Map<String, Object> param);
	
	/**
	 * <p>描述：点击闭环时判断勾选的保电任务（可多选）是否已关联保电站线</p> 
	 * @param params
	 * @return data
	 * @author:王万胜
	 * 2017年6月11日 下午6:01:48
	 */
	@RequestCache
	@RequestMapping("/queryWhetherHadSol")
	public List<Map<String, Object>> queryWhetherHadSol(@RequestParam HashMap<String, Object> params);
	
	
	
	/************************************************************用户************************************************************/
	/**
	 * <p>描述：获取用户表格数据</p> 
	 * @param params
	 * @return data
	 * @author:王万胜
	 * 2017年4月26日 下午5:03:39
	 */
	@RequestCache
	@RequestMapping("/queryUserResult")
	public List<Map<String, Object>> queryUserResult(@RequestParam HashMap<String, Object> params);
	
	/**
	 * <p>描述：新增用户数据</p> 
	 * @param hashmap 
	 * @author:王万胜
	 * 2017年4月27日 下午2:50:53
	 */
	@RequestMapping("/insertUserResult")
	public void insertUserResult(@RequestParam HashMap<String, Object> hashmap);
	
	/**
	 * <p>描述：修改用户数据</p> 
	 * @param hashmap 
	 * @author:王万胜
	 * 2017年4月27日 下午3:05:30
	 */
	@RequestMapping("/updateUserResult")
	public void updateUserResult(@RequestParam HashMap<String, Object> hashmap);
	
	/**
	 * <p>描述：删除用户数据</p> 
	 * @param hashmap 
	 * @author:王万胜
	 * 2017年4月28日 上午9:28:14
	 */
	@RequestMapping("/deleteUserResult")
	public void deleteUserResult(@RequestParam HashMap<String, Object> hashmap);
	
	
	
	/************************************************************人员************************************************************/
	/**
	 * <p>描述：获取人员表格数据</p> 
	 * @param params
	 * @return data
	 * @author:王万胜
	 * 2017年4月28日 上午10:52:35
	 */
	@RequestCache
	@RequestMapping("/queryPersonResult")
	public List<Map<String, Object>> queryPersonResult(@RequestParam HashMap<String, Object> params);
	
	/**
	 * <p>描述：删除人员数据</p> 
	 * @param hashmap 
	 * @author:王万胜
	 * 2017年4月28日 下午3:56:17
	 */
	@RequestMapping("/deletePersonResult")
	public void deletePersonResult(@RequestParam HashMap<String, Object> hashmap);

	
	
	/************************************************************车辆************************************************************/
	/**
	 * <p>描述：获取车辆表格数据</p> 
	 * @param params
	 * @return data
	 * @author:王万胜
	 * 2017年4月28日 上午10:52:35
	 */
	@RequestCache
	@RequestMapping("/queryCarResult")
	public List<Map<String, Object>> queryCarResult(@RequestParam HashMap<String, Object> params);
	
	/**
	 * <p>描述：删除车辆数据</p> 
	 * @param hashmap 
	 * @author:王万胜
	 * 2017年4月28日 下午4:10:38
	 */
	@RequestMapping("/deleteCarResult")
	public void deleteCarResult(@RequestParam HashMap<String, Object> hashmap);

	
	
	/************************************************************驻点************************************************************/
	/**
	 * <p>描述：获取驻点表格数据</p> 
	 * @param params
	 * @return data
	 * @author:王万胜
	 * 2017年4月28日 上午10:52:35
	 */
	@RequestCache
	@RequestMapping("/queryPointResult")
	public List<Map<String, Object>> queryPointResult(@RequestParam HashMap<String, Object> params);
	
	/**
	 * <p>描述：删除驻点数据</p> 
	 * @param hashmap 
	 * @author:王万胜
	 * 2017年4月28日 下午4:14:07
	 */
	@RequestMapping("/deletePointResult")
	public void deletePointResult(@RequestParam HashMap<String, Object> hashmap);
	
	/**
	 * <p>描述：修改驻点数据（位置信息）</p> 
	 * @param hashmap 
	 * @author:王万胜
	 * 2017年7月4日 上午10:28:29
	 */
	@RequestMapping("/updatePointResult")
	public void updatePointResult(@RequestParam HashMap<String, Object> hashmap);

	
	
	/************************************************************宾馆************************************************************/
	/**
	 * <p>描述：获取宾馆表格数据</p> 
	 * @param params
	 * @return data
	 * @author:王万胜
	 * 2017年4月28日 上午10:52:35
	 */
	@RequestCache
	@RequestMapping("/queryHotelResult")
	public List<Map<String, Object>> queryHotelResult(@RequestParam HashMap<String, Object> params);
	
	/**
	 * <p>描述：删除宾馆数据</p> 
	 * @param hashmap 
	 * @author:王万胜
	 * 2017年4月28日 下午4:19:54
	 */
	@RequestMapping("/deleteHotelResult")
	public void deleteHotelResult(@RequestParam HashMap<String, Object> hashmap);
	
	/**
	 * <p>描述：修改宾馆数据（位置信息）</p> 
	 * @param hashmap 
	 * @author:王万胜
	 * 2017年7月4日 上午10:28:29
	 */
	@RequestMapping("/updateHotelResult")
	public void updateHotelResult(@RequestParam HashMap<String, Object> hashmap);

	
	
	/************************************************************医院************************************************************/
	/**
	 * <p>描述：获取医院表格数据</p> 
	 * @param params
	 * @return data
	 * @author:王万胜
	 * 2017年4月28日 上午10:52:35
	 */
	@RequestCache
	@RequestMapping("/queryHospitalResult")
	public List<Map<String, Object>> queryHospitalResult(@RequestParam HashMap<String, Object> params);
	
	/**
	 * <p>描述：删除医院数据</p> 
	 * @param hashmap 
	 * @author:王万胜
	 * 2017年4月28日 下午4:22:18
	 */
	@RequestMapping("/deleteHospitalResult")
	public void deleteHospitalResult(@RequestParam HashMap<String, Object> hashmap);
	
	/**
	 * <p>描述：修改医院数据（位置信息）</p> 
	 * @param hashmap 
	 * @author:王万胜
	 * 2017年7月4日 上午10:28:29
	 */
	@RequestMapping("/updateHospitalResult")
	public void updateHospitalResult(@RequestParam HashMap<String, Object> hashmap);
	
	
	
	/************************************************************日报************************************************************/
	/**
	 * <p>描述：编辑日报信息（包含新增、修改）</p> 
	 * @param hashmap 
	 * @author:王万胜
	 * 2017年6月5日 下午3:52:21
	 */
	@RequestMapping("/insertOrUpdateDailyResult")
	public void insertOrUpdateDailyResult(@RequestBody HashMap<String, Object> hashmap);
	
	
	
	/************************************************************日报-汇报记录************************************************************/
	/**
	 * <p>描述：获取日报-汇报记录表格数据</p> 
	 * @param params
	 * @return data
	 * @author:王万胜
	 * 2017年5月4日 下午3:28:01
	 */
	@RequestCache
	@RequestMapping("/queryLogResult")
	public List<Map<String, Object>> queryLogResult(@RequestParam HashMap<String, Object> params);
	
	
	
	/**
	 * <p>描述：删除日报-汇报记录表格中的数据</p> 
	 * @param hashmap 
	 * @author:王万胜
	 * 2017年6月6日 下午2:11:18
	 */
	@RequestMapping("/deleteLogResult")
	public void deleteLogResult(@RequestParam HashMap<String, Object> hashmap);
	
	
	
	/**
	 * <p>描述：复制日报-汇报记录表格中的数据</p> 
	 * @param hashmap 
	 * @author:王万胜
	 * 2017年6月6日 下午3:51:02
	 */
	@RequestMapping("/copyLogResult")
	public void copyLogResult(@RequestParam HashMap<String, Object> hashmap);

	
	
	/************************************************************日报-保电过程************************************************************/
	/**
	 * <p>描述：获取日报-保电过程表格数据</p> 
	 * @param params
	 * @return data
	 * @author:王万胜
	 * 2017年5月4日 下午3:28:01
	 */
	@RequestCache
	@RequestMapping("/queryProcessResult")
	public List<Map<String, Object>> queryProcessResult(@RequestParam HashMap<String, Object> params);
	
	
	
	/**
	 * <p>描述：删除日报-保电过程表格中的数据</p> 
	 * @param hashmap 
	 * @author:王万胜
	 * 2017年6月6日 下午2:11:18
	 */
	@RequestMapping("/deleteProcessResult")
	public void deleteProcessResult(@RequestParam HashMap<String, Object> hashmap);
	
	
	
	/************************************************************公共方法************************************************************/
	/**
	 * <p>描述：通过ID获取各表格记录的更新时间</p> 
	 * @param params
	 * @return data
	 * @author:王万胜
	 * 2017年6月11日 上午10:48:54
	 */
	@RequestCache
	@RequestMapping("/queryUpdateTime")
	public List<Map<String, Object>> queryUpdateTime(@RequestParam HashMap<String, Object> params);
	
	
	
	/******************************** 运检指令-保电-日常业务start ****************************************/
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
	public Map<String, Object> queryPwSupplyCount(@RequestParam Map<String, Object> param);

	/**
	 * <p>
	 * 描述：获取预告警grid数据
	 * </p>
	 * 
	 * @param param
	 * @return grid数据
	 * @author:范阳 2017年3月14日 下午3:55:53
	 */
	@RequestCache
	@RequestMapping("/queryPwSupplyInfo")
	public List<Map<String, Object>> queryPwSupplyInfo(@RequestParam Map<String, Object> param);
	/********************************日常业务end****************************************/
	
	
	
	/******************************** 变电保电start ****************************************/
	// 一级页面
	/**
	 * <p>
	 * 描述: 获取各个保电等级的数据
	 * </p>
	 * 
	 * @param paramMap
	 * @return 各个保电等级的数据
	 * @author: 俞聪 2017年5月31日 下午8:10:49
	 */
	@RequestMapping(value = "/getEnsureLevelCount", method = RequestMethod.GET)
	public List<Map<String, Object>> getEnsureLevelCount(@RequestParam Map<String, Object> paramMap);

	/**
	 * <p>
	 * 描述: 获取不同电压等级的变电站数量
	 * </p>
	 * 
	 * @param paramMap
	 * @return 不同电压等级的变电站数量
	 * @author: 俞聪 2017年6月2日 下午1:39:54
	 */
	@RequestMapping(value = "/getVoltageLevelCount", method = RequestMethod.GET)
	public List<Map<String, Object>> getVoltageLevelCount(@RequestParam Map<String, Object> paramMap);

	/**
	 * <p>
	 * 描述: 获得当前所有可用变电站的id,数据来源是cim.stationlineensure.stationLineID
	 * </p>
	 * 
	 * @param paramMap
	 * @return 当前所有可用变电站的id,数据来源是cim.stationlineensure.stationLineID
	 * @author: 俞聪 2017年6月12日 下午3:05:08
	 */
	@RequestMapping(value = "/getStationLineId", method = RequestMethod.GET)
	public List<Map<String, Object>> getStationLineId(@RequestParam Map<String, Object> paramMap);

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
	public List<Map<String, Object>> getGridData(@RequestParam Map<String, Object> paramMap);

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
	public Integer getGridCount(@RequestParam Map<String, Object> paramMap);

	/**
	 * <p>
	 * 描述: 初始化运维单位下拉框
	 * </p>
	 * 
	 * @return 初始化运维单位
	 * @author: 俞聪 2017年6月5日 下午7:06:49
	 */
	@RequestMapping("/getMaintemanceUnit")
	public List<Map<String, Object>> getMaintemanceUnit();

	/**
	 * <p>
	 * 描述: 初始化保电等级下拉框
	 * </p>
	 * 
	 * @return 初始化保电等级
	 * @author: 俞聪 2017年6月5日 下午7:08:17
	 */
	@RequestMapping("/getEnsureLevelCombobox")
	public List<Map<String, Object>> getEnsureLevelCombobox();

	/**
	 * <p>
	 * 描述: 初始化电压等级下拉框
	 * </p>
	 * 
	 * @return 初始化电压等级
	 * @author: 俞聪 2017年6月7日 下午4:43:48
	 */
	@RequestMapping("/getVoltageLevelCombobox")
	public List<Map<String, Object>> getVoltageLevelCombobox();

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
	public List<Map<String, Object>> exportGridData(@RequestParam Map<String, Object> paramMap);

	
	/******************************** 变电保电end ****************************************/
}


