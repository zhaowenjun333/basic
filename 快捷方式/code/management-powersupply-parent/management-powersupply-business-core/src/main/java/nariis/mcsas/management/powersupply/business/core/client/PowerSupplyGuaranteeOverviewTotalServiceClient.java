package nariis.mcsas.management.powersupply.business.core.client;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import nariis.falcon.commons.springcloud.RequestCache;
import nariis.mcsas.commons.model.MultiBarModel;

/**
 * <p>ClassName:PowerSupplyGuaranteeOverviewTotalServiceClient，</p>
 * <p>描述:TODO</p>
 * @author 赵云
 * @date 2017年6月19日上午9:16:47
 * @version 
 */
@FeignClient(name = "managementPowerSupply")
@RequestMapping("/overviewTotal")
public interface PowerSupplyGuaranteeOverviewTotalServiceClient {
	 
	
	
	/**
	 * <p>描述：气象环境数据统计</p> 
	 * @param hashmap
	 * @return list
	 * @author:赵云
	 * 2017年6月12日 下午2:46:50
	 */
	@RequestMapping("/queryWeatherCountResult")
	@RequestCache
	public List<Map<String, Object>> queryWeatherCountResult(@RequestParam HashMap<String, Object> hashmap);

	 
	/**
	 * <p>描述：电网风险数据统计</p> 
	 * @param hashmap
	 * @return List<Map<String, Object>>
	 * @author:赵云
	 * 2017年3月9日 下午4:02:25
	 */
	@RequestMapping("/queryGridRiskCountResult")
	@RequestCache
	public List<Map<String, Object>> queryGridRiskCountResult(@RequestParam HashMap<String, Object> hashmap);
	 

	 
	 
	 
	/**
	 * <p>描述：TODO</p> 
	 * @return  List<Map<String, Object>>
	 * @author:赵云
	 * 2017年6月19日 上午9:16:49
	 * @param hashmap 
	 */
	@RequestCache
	@RequestMapping("/getmissionStatus")
	public List<Map<String, Object>> getmissionStatus(@RequestParam HashMap<String, Object> hashmap);

	/**
	 * <p>描述：TODO</p> 
	 * @param hashmap
	 * @return  List<Map<String, Object>>
	 * @author:赵云
	 * 2017年6月19日 上午9:16:52
	 */
	@RequestCache
	@RequestMapping("/getTaskLevel")
	public List<Map<String, Object>> getTaskLevel(@RequestParam HashMap<String, Object> hashmap);

	/**
	 * <p>描述：TODO</p> 
	 * @param hashmap
	 * @return List<Map<String, Object>> 
	 * @author:赵云
	 * 2017年6月19日 上午10:30:36
	 */
	@RequestCache
	@RequestMapping("/getUserState")
	public List<Map<String, Object>> getUserState(@RequestParam HashMap<String, Object> hashmap);
	
	/**
	 * <p>描述：TODO</p> 
	 * @param hashmap
	 * @return MultiBarModel
	 * @author:赵云
	 * 2017年6月19日 下午2:31:42
	 */
	@RequestCache
	@RequestMapping("/getLineChart")
	public  Map<String,List<?>> getLineChart(@RequestParam HashMap<String, Object> hashmap);

	/**
	 * <p>描述：TODO</p> 
	 * @param hashmap
	 * @return List<Map<String, Object>>
	 * @author:赵云
	 * 2017年6月20日 下午7:00:13
	 */
	@RequestCache
	@RequestMapping("/getresourceguarantee")
	public List<Map<String, Object>> getresourceguarantee(@RequestParam HashMap<String, Object> hashmap);
	
	
	/**
	 * <p>描述：TODO</p> 
	 * @param hashmap
	 * @return List<Map<String, Object>>
	 * @author:赵云
	 * 2017年6月21日 下午7:28:17
	 */
	@RequestCache
	@RequestMapping("/gettaskGrid")
	public List<Map<String, Object>> gettaskGrid(@RequestParam HashMap<String, Object> hashmap);


	/**
	 * <p>描述：TODO</p> 
	 * @param hashmap
	 * @return List<Map<String, Object>>
	 * @author:赵云
	 * 2017年6月23日 上午10:17:48
	 */
	@RequestCache
	@RequestMapping("/getcount_faultTrip")
	public List<Map<String, Object>> getcount_faultTrip(@RequestParam HashMap<String, Object> hashmap);

	/**
	 * <p>描述：TODO</p> 
	 * @param hashmap
	 * @return  List<Map<String, Object>>
	 * @author:赵云
	 * 2017年6月26日 上午10:11:23
	 */
	@RequestCache
	@RequestMapping("/getcount_DefectState")
	public List<Map<String, Object>> getcount_DefectState( @RequestParam HashMap<String, Object> hashmap);

	/**
	 * <p>描述：TODO</p> 
	 * @param hashmap
	 * @return  List<Map<String, Object>>
	 * @author:赵云
	 * 2017年6月26日 上午10:11:23
	 */
	@RequestCache
	@RequestMapping("/getmaxleveluser")
	public List<Map<String, Object>> getmaxleveluser( @RequestParam HashMap<String, Object> hashmap);

	/**
	 * <p>描述：TODO</p> 
	 * @param hashmap
	 * @return  List<Map<String, Object>>
	 * @author:赵云
	 * 2017年6月26日 上午10:11:23
	 */
	@RequestCache
	@RequestMapping("/getmaxdeviceid")
	public List<Map<String, Object>> getmaxdeviceid( @RequestParam HashMap<String, Object> hashmap);

	
	/**
	 * <p>描述：TODO</p> 
	 * @param hashmap
	 * @return  List<Map<String, Object>>
	 * @author:赵云
	 * 2017年6月26日 上午10:11:23
	 */
	@RequestCache
	@RequestMapping("/getUsergrid")
	public List<Map<String, Object>> getUsergrid(@RequestParam HashMap<String, Object> hashmap);


	/**
	 * <p>描述：TODO</p> 
	 * @param hashmap
	 * @return  List<Map<String, Object>>
	 * @author:赵云
	 * 2017年6月26日 上午10:11:23
	 */
	@RequestCache
	@RequestMapping("/getLineorStationGrid")
	public List<Map<String, Object>> getLineorStationGrid(@RequestParam HashMap<String, Object> hashmap);


	
	/**
	 * <p>描述：TODO</p> 
	 * @param hashmap
	 * @return  List<Map<String, Object>>
	 * @author:赵云
	 * 2017年6月26日 上午10:11:23
	 */
	@RequestCache
	@RequestMapping("/getpersonGrid")
	public List<Map<String, Object>> getpersonGrid(@RequestParam HashMap<String, Object> hashmap);
	
	
	/**
	 * <p>描述：TODO</p> 
	 * @param hashmap
	 * @return  List<Map<String, Object>>
	 * @author:赵云
	 * 2017年6月26日 上午10:11:23
	 */
	@RequestCache
	@RequestMapping("/getcarGrid")
	public List<Map<String, Object>> getcarGrid(@RequestParam HashMap<String, Object> hashmap);
	
	/**
	 * <p>描述：TODO</p> 
	 * @param hashmap
	 * @return  List<Map<String, Object>>
	 * @author:赵云
	 * 2017年6月26日 上午10:11:23
	 */
	@RequestCache
	@RequestMapping("/getpointGrid")
	public List<Map<String, Object>> getpointGrid(@RequestParam HashMap<String, Object> hashmap);
	
	/**
	 * <p>描述：TODO</p> 
	 * @param hashmap
	 * @return  List<Map<String, Object>>
	 * @author:赵云
	 * 2017年6月26日 上午10:11:23
	 */
	@RequestCache
	@RequestMapping("/gethospitalGrid")
	public List<Map<String, Object>> gethospitalGrid(@RequestParam HashMap<String, Object> hashmap);
	
	/**
	 * <p>描述：TODO</p> 
	 * @param hashmap
	 * @return  List<Map<String, Object>>
	 * @author:赵云
	 * 2017年6月26日 上午10:11:23
	 */
	@RequestCache
	@RequestMapping("/gethotelGrid")
	public List<Map<String, Object>> gethotelGrid(@RequestParam HashMap<String, Object> hashmap);

	/**
	 * <p>描述：TODO</p> 
	 * @param hashmap
	 * @return  List<Map<String, Object>>
	 * @author:赵云
	 * 2017年6月26日 上午10:11:23
	 */
	@RequestCache
	@RequestMapping("/getFaultgrid")
	public List<Map<String, Object>> getFaultgrid(@RequestParam HashMap<String, Object> hashmap);


	/**
	 * <p>描述：TODO</p> 
	 * @param hashmap
	 * @return  List<Map<String, Object>>
	 * @author:赵云
	 * 2017年6月26日 上午10:11:23
	 */
	@RequestCache
	@RequestMapping("/getDefectgrid")
	public List<Map<String, Object>> getDefectgrid(@RequestParam HashMap<String, Object> hashmap);
	
	
	
	
	
}
