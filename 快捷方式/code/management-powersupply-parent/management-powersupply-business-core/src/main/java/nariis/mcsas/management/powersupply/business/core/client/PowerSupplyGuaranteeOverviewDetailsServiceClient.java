package nariis.mcsas.management.powersupply.business.core.client;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import nariis.falcon.commons.springcloud.RequestCache;


/**
 * <p>ClassName:powerSupplyGuaranteeOverviewDetailsServiceClient，</p>
 * <p>描述:TODO</p>
 * @author 王万胜
 * @date 2017年3月23日下午3:04:02
 * @version 
 */
@FeignClient(name = "managementPowerSupply")
@RequestMapping("/powerSupplyGuaranteeOverviewDetails")
public interface PowerSupplyGuaranteeOverviewDetailsServiceClient {
	
	/************************************************************基础信息************************************************************/
	
	
	/**
	 * <p>描述：查询基础信息主表数据</p> 
	 * @param params
	 * @return List<Map<String, Object>>
	 * @author:王万胜
	 * 2017年3月9日 下午4:02:25
	 */
	@RequestCache
	@RequestMapping("/querypowerSupplyGuaranteeOverviewDetailsResult")
	public List<Map<String, Object>> querypowerSupplyGuaranteeOverviewDetailsResult(@RequestParam HashMap<String, Object> params);
	
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
	 * <p>描述：获取下级组织机构</p> 
	 * @param params
	 * @return data
	 * @author:王万胜
	 * 2017年4月20日 下午1:53:37
	 */
	@RequestCache
	@RequestMapping("/querySubOrganizationResult")
	public List<Map<String, Object>> querySubOrganizationResult(@RequestParam HashMap<String, Object> params);
	
	
	
	
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
	 * <p>描述：电站线路统计</p> 
	 * @param params
	 * @return list
	 * @author:朱建衡
	 * 2017年6月26日 上午9:25:43
	 */
	@RequestCache
	@RequestMapping("/queryStationOrLineChartResult")
	public Map<String,List<?>> queryStationOrLineChartResult(@RequestParam HashMap<String, Object> params);
	
	/************************************************************用户************************************************************/
	/**
	 * <p>描述：获取用户保电级别统计</p> 
	 * @param params
	 * @return data
	 * @author:王万胜
	 * 2017年6月19日 下午5:28:17
	 */
	@RequestCache
	@RequestMapping("/queryUserLevel")
	public List<Map<String, Object>> queryUserLevel(@RequestParam HashMap<String, Object> params);
	
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
	 * <p>描述：获取人员饼图数据</p> 
	 * @param params
	 * @return data
	 * @author:王万胜
	 * 2017年6月22日 下午2:23:54
	 */
	@RequestCache
	@RequestMapping("/queryPersonChartResult")
	public List<Map<String, Object>> queryPersonChartResult(@RequestParam HashMap<String, Object> params);

	
	
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
	 * <p>描述：获取车辆饼图数据</p> 
	 * @param params
	 * @return data
	 * @author:王万胜
	 * 2017年6月22日 下午2:23:54
	 */
	@RequestCache
	@RequestMapping("/queryCarChartResult")
	public List<Map<String, Object>> queryCarChartResult(@RequestParam HashMap<String, Object> params);
	
	
	
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
	
	
	
	/************************************************************运行内容（故障、缺陷）************************************************************/
	/**
	 * <p>描述：获取故障、缺陷数量</p> 
	 * @param params
	 * @return data
	 * @author:王万胜
	 * 2017年6月26日 下午1:43:57
	 */
	@RequestCache
	@RequestMapping("/queryRunningContentCount")
	public Map<String, Object> queryRunningContentCount(@RequestParam HashMap<String, Object> params);
	
	/**
	 * <p>描述：获取故障信息</p> 
	 * @param params
	 * @return data
	 * @author:王万胜
	 * 2017年6月26日 下午6:30:56
	 */
	@RequestCache
	@RequestMapping("/queryFaultResult")
	public List<Map<String, Object>> queryFaultResult(@RequestParam HashMap<String, Object> params);
	
	/**
	 * <p>描述：获取缺陷信息</p> 
	 * @param params
	 * @return data
	 * @author:王万胜
	 * 2017年6月26日 下午7:46:18
	 */
	@RequestCache
	@RequestMapping("/queryDefectResult")
	public List<Map<String, Object>> queryDefectResult(@RequestParam HashMap<String, Object> params);
	
	
	
	/************************************************************公共************************************************************/
	/**
	 * <p>描述：获取资源保障的数量（人员、车辆、驻点、宾馆、医院）</p> 
	 * @param params
	 * @return data
	 * @author:王万胜
	 * 2017年6月21日 下午3:06:59
	 */
	@RequestCache
	@RequestMapping("/queryResourceCount")
	public Map<String, Object> queryResourceCount(@RequestParam HashMap<String, Object> params);
}


