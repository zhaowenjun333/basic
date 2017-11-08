package nariis.mcsas.management.powersupply.web.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import nariis.mcsas.commons.model.Item;
import nariis.mcsas.commons.model.MultiBarModel;
import nariis.mcsas.management.powersupply.business.core.client.PowerSupplyGuaranteeOverviewTotalServiceClient;
import nariis.mcsas.management.powersupply.business.core.client.PowerSupplyGuaranteeServiceClient;

/**
 * <p>ClassName:PowerSupplyGuaranteeOverviewTotalController，</p>
 * <p>描述:TODO</p>
 * @author 赵云
 * @date 2017年6月19日上午9:14:17
 * @version 
 */
@RestController
public class PowerSupplyGuaranteeOverviewTotalController {
	
	@Autowired
	private PowerSupplyGuaranteeOverviewTotalServiceClient client;
	
	/**
	 * <p>描述：TODO</p> 
	 * @param hashmap
	 * @return List<Map<String,Object>>
	 * @author:赵云
	 * 2017年6月23日 上午10:17:19
	 */
	@RequestMapping("/overviewTotal/getDefectgrid")
	public @ResponseBody Map<String,Object>  getDefectgrid(@RequestParam HashMap<String,Object> hashmap){
		List<Map<String,Object>> data = client.getDefectgrid(hashmap);
		return data.get(0);
	}
	
	/**
	 * <p>描述：TODO</p> 
	 * @param hashmap
	 * @return List<Map<String,Object>>
	 * @author:赵云
	 * 2017年6月23日 上午10:17:19
	 */
	@RequestMapping("/overviewTotal/getFaultgrid")
	public @ResponseBody  Map<String,Object>  getFaultgrid(@RequestParam HashMap<String,Object> hashmap){
		List<Map<String,Object>> data = client.getFaultgrid(hashmap);
		return data.get(0);
	}
	
	/**
	 * <p>描述：TODO</p> 
	 * @param hashmap
	 * @return List<Map<String,Object>>
	 * @author:赵云
	 * 2017年6月23日 上午10:17:19
	 */
	@RequestMapping("/overviewTotal/gethotelGrid")
	public @ResponseBody  Map<String,Object>  gethotelGrid(@RequestParam HashMap<String,Object> hashmap){
		List<Map<String,Object>> data = client.gethotelGrid(hashmap);
		return data.get(0);
	}
	
	/**
	 * <p>描述：TODO</p> 
	 * @param hashmap
	 * @return List<Map<String,Object>>
	 * @author:赵云
	 * 2017年6月23日 上午10:17:19
	 */
	@RequestMapping("/overviewTotal/gethospitalGrid")
	public @ResponseBody  Map<String,Object>  gethospitalGrid(@RequestParam HashMap<String,Object> hashmap){
		List<Map<String,Object>> data = client.gethospitalGrid(hashmap);
		return data.get(0);
	}
	
	/**
	 * <p>描述：TODO</p> 
	 * @param hashmap
	 * @return List<Map<String,Object>>
	 * @author:赵云
	 * 2017年6月23日 上午10:17:19
	 */
	@RequestMapping("/overviewTotal/getpointGrid")
	public @ResponseBody Map<String,Object>  getpointGrid(@RequestParam HashMap<String,Object> hashmap){
		List<Map<String,Object>> data = client.getpointGrid(hashmap);
		return data.get(0);
	}

	/**
	 * <p>描述：TODO</p> 
	 * @param hashmap
	 * @return List<Map<String,Object>>
	 * @author:赵云
	 * 2017年6月23日 上午10:17:19
	 */
	@RequestMapping("/overviewTotal/getcarGrid")
	public @ResponseBody  Map<String,Object>  getcarGrid(@RequestParam HashMap<String,Object> hashmap){
		List<Map<String,Object>> data = client.getcarGrid(hashmap);
		return data.get(0);
	}
	
	/**
	 * <p>描述：TODO</p> 
	 * @param hashmap
	 * @return List<Map<String,Object>>
	 * @author:赵云
	 * 2017年6月23日 上午10:17:19
	 */
	@RequestMapping("/overviewTotal/getpersonGrid")
	public @ResponseBody  Map<String,Object>  getpersonGrid(@RequestParam HashMap<String,Object> hashmap){
		List<Map<String,Object>> data = client.getpersonGrid(hashmap);
		return data.get(0);
	}
	
	/**
	 * <p>描述：TODO</p> 
	 * @param hashmap
	 * @return List<Map<String,Object>>
	 * @author:赵云
	 * 2017年6月23日 上午10:17:19
	 */
	@RequestMapping("/overviewTotal/getLineorStationGrid")
	public @ResponseBody Map<String,Object>  getLineorStationGrid(@RequestParam HashMap<String,Object> hashmap){
		List<Map<String,Object>> data = client.getLineorStationGrid(hashmap);
		return data.get(0);
	}
	/**
	 * <p>描述：TODO</p> 
	 * @param hashmap
	 * @return List<Map<String,Object>>
	 * @author:赵云
	 * 2017年6月23日 上午10:17:19
	 */
	@RequestMapping("/overviewTotal/getLineorStationGridall")
	public @ResponseBody List<Map<String,Object>>  getLineorStationGridall(@RequestParam HashMap<String,Object> hashmap){
		List<Map<String,Object>> data = client.getLineorStationGrid(hashmap);
		return data ;
	}

	/**
	 * <p>描述：TODO</p> 
	 * @param hashmap
	 * @return List<Map<String,Object>>
	 * @author:赵云
	 * 2017年6月23日 上午10:17:19
	 */
	@RequestMapping("/overviewTotal/getUsergrid")
	public @ResponseBody  Map<String,Object>  getUsergrid(@RequestParam HashMap<String,Object> hashmap){
		List<Map<String,Object>> data = client.getUsergrid(hashmap);
		return data.get(0);
	}
	
	
	/**
	 * <p>描述：TODO</p> 
	 * @param hashmap
	 * @return List<Map<String,Object>>
	 * @author:赵云
	 * 2017年6月23日 上午10:17:19
	 */
	@RequestMapping("/overviewTotal/getmaxdeviceid")
	public @ResponseBody List<Map<String,Object>> getmaxdeviceid(@RequestParam HashMap<String,Object> hashmap){
		List<Map<String,Object>> data = client.getmaxdeviceid(hashmap);
		return data;
	}
	/**
	 * <p>描述：TODO</p> 
	 * @param hashmap
	 * @return List<Map<String,Object>>
	 * @author:赵云
	 * 2017年6月23日 上午10:17:19
	 */
	@RequestMapping("/overviewTotal/getmaxleveluser")
	public @ResponseBody List<Map<String,Object>> getmaxleveluser(@RequestParam HashMap<String,Object> hashmap){
		List<Map<String,Object>> data = client.getmaxleveluser(hashmap);
		return data;
	}
	/**
	 * <p>描述：TODO</p> 
	 * @param hashmap
	 * @return List<Map<String,Object>>
	 * @author:赵云
	 * 2017年6月23日 上午10:17:19
	 */
	@RequestMapping("/overviewTotal/getcount_DefectState")
	public @ResponseBody List<Map<String,Object>> getcount_DefectState(@RequestParam HashMap<String,Object> hashmap){
		List<Map<String,Object>> data = client.getcount_DefectState(hashmap);
		return data;
	}
	
	
	/**
	 * <p>描述：TODO</p> 
	 * @param hashmap
	 * @return List<Map<String,Object>>
	 * @author:赵云
	 * 2017年6月23日 上午10:17:19
	 */
	@RequestMapping("/overviewTotal/getcount_faultTrip")
	public @ResponseBody List<Map<String,Object>> getcount_faultTrip(@RequestParam HashMap<String,Object> hashmap){
		List<Map<String,Object>> data = client.getcount_faultTrip(hashmap);
		return data;
	}
	
	
	/**
	 * <p>描述：气象环境数据统计</p> 
	 * @param hashmap
	 * @return List<Map<String, Object>>
	 * @author:赵云
	 * 2017年3月9日 下午4:02:25
	 */
	@RequestMapping("/overviewTotal/showWeatherCountResult")
	public @ResponseBody List<Map<String,Object>> showWeatherCountResult(@RequestParam HashMap<String,Object> hashmap){
		List<Map<String,Object>> data = client.queryWeatherCountResult(hashmap);
		return data;
	}
	
	/**
	 * <p>描述：电网风险数据统计</p> 
	 * @param hashmap
	 * @return List<Map<String, Object>>
	 * @author:赵云
	 * 2017年3月9日 下午4:02:25
	 */
	@RequestMapping("/overviewTotal/showGridRiskCountResult")
	public @ResponseBody List<Map<String,Object>> showGridRiskCountResult(@RequestParam HashMap<String,Object> hashmap){
		List<Map<String,Object>> data = client.queryGridRiskCountResult(hashmap);
		return data;
	}
	
	 
	/**
	 * <p>描述：TODO</p> 
	 * @return List<Map<String,Object>>
	 * @author:赵云
	 * 2017年6月19日 上午9:14:21
	 * @param hashmap 
	 */
	@RequestMapping("/overviewTotal/getmissionStatus")
	public @ResponseBody List<Map<String,Object>> getmissionStatus(@RequestParam HashMap<String, Object> hashmap) {
		List<Map<String,Object>> data = client.getmissionStatus(hashmap);
		return data;
	}
	
	/**
	 * <p>描述：TODO</p> 
	 * @param hashmap
	 * @return List<Map<String,Object>>
	 * @author:赵云
	 * 2017年6月19日 上午9:14:24
	 */
	@RequestMapping("/overviewTotal/getTaskLevel")
	public @ResponseBody List<Map<String,Object>> getTaskLevel(@RequestParam HashMap<String, Object> hashmap) {
		List<Map<String,Object>> data = client.getTaskLevel(hashmap);
		return data;
	}
	
	/**
	 * <p>描述：TODO</p> 
	 * @param hashmap
	 * @return List<Map<String,Object>>
	 * @author:赵云
	 * 2017年6月19日 上午10:30:09
	 */
	@RequestMapping("/overviewTotal/getUserState")
	public @ResponseBody List<Map<String,Object>> getUserState(@RequestParam HashMap<String, Object> hashmap) {
		List<Map<String,Object>> data = client.getUserState(hashmap);
		return data;
	}
	 
	/**
	 * <p>描述：TODO</p> 
	 * @param hashmap
	 * @return  Map<String,List<?>>
	 * @author:赵云
	 * 2017年6月19日 下午2:30:45
	 */
	@RequestMapping("/overviewTotal/getLineChart")
	public @ResponseBody   Map<String,List<?>> getLineChart(@RequestParam HashMap<String, Object> hashmap) {
		 Map<String,List<?>> data = client.getLineChart(hashmap);
		return data;
	}
	
	
	 
	/**
	 * <p>描述：TODO</p> 
	 * @param hashmap
	 * @return  List<Map<String,Object>>
	 * @author:赵云
	 * 2017年6月20日 下午6:59:12
	 */
	@RequestMapping("/overviewTotal/getresourceguarantee")
	public @ResponseBody  List<Map<String,Object>> getresourceguarantee(@RequestParam HashMap<String, Object> hashmap) {
		 List<Map<String,Object>> data = client.getresourceguarantee(hashmap);
		return data;
	}
	
	
	/**
	 * <p>描述：TODO</p> 
	 * @param hashmap
	 * @return List<Map<String,Object>>
	 * @author:赵云
	 * 2017年6月21日 下午7:24:40
	 */
	@RequestMapping("/overviewTotal/gettaskGrid")
	public @ResponseBody   Map<String,Object>  gettaskGrid(@RequestParam HashMap<String, Object> hashmap) {
		List<Map<String,Object>> data = client.gettaskGrid(hashmap);
		return  (Map<String,Object>) data.get(0);
	}
}
