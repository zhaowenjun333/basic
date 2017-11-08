package nariis.mcsas.management.powersupply.web.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import nariis.mcsas.commons.model.NestRingModel;
import nariis.mcsas.management.common.model.LoginUserInfo;
import nariis.mcsas.management.common.util.CommonExcelService;
import nariis.mcsas.management.common.util.LoginUserInfoUtils;
import nariis.mcsas.management.powersupply.business.core.client.PowerSupplyGuaranteeOverviewDetailsServiceClient;
import nariis.mcsas.management.powersupply.web.viewmodel.PowerSupplyGuaranteeOverviewDetailsViewModel;
import nariis.mcsas.management.powersupply.web.util.ModelUtil;
import nariis.falcon.oaf.OafCommands;


/**
 * <p>ClassName:powerSupplyGuaranteeOverviewDetailsController，</p>
 * <p>描述:保电任务方法</p>
 * @author 王万胜
 * @date 2017年3月23日下午3:01:17
 * @version 
 */
@RestController
public class PowerSupplyGuaranteeOverviewDetailsController {
	@Autowired
	private PowerSupplyGuaranteeOverviewDetailsServiceClient powerSupplyGuaranteeOverviewDetailsServiceClient;
	
	@Autowired
	CommonExcelService commonExcelService;
	
	@Autowired
	OafCommands oafCommands;
	
	// 用户登录信息
	@Autowired
	LoginUserInfoUtils loginUserInfoUtils;
	
	/************************************************************基础信息************************************************************/
	/**
	 * <p>描述：获取主表格数据</p> 
	 * @param hashmap
	 * @return data
	 * @author:王万胜
	 * 2017年3月10日 下午2:56:54
	 */
	@RequestMapping("/powerSupplyGuaranteeOverviewDetails/getpowerSupplyGuaranteeOverviewDetailsResult")
	public @ResponseBody List<Map<String,Object>> querypowerSupplyGuaranteeOverviewDetailsResult(@RequestParam HashMap<String, Object> hashmap) {
		List<Map<String,Object>> data = powerSupplyGuaranteeOverviewDetailsServiceClient.querypowerSupplyGuaranteeOverviewDetailsResult(hashmap);
		
		return data;
	}
	
	/**
	 * <p>描述：获取选定的一条保电任务的基础信息</p> 
	 * @param hashmap
	 * @return data
	 * @author:王万胜
	 * 2017年5月16日 下午7:20:35
	 */
	@RequestMapping("/powerSupplyGuaranteeOverviewDetails/getNoteInfo")
	public @ResponseBody List<Map<String,Object>> queryNoteInfo(@RequestParam HashMap<String, Object> hashmap) {
		List<Map<String,Object>> data = powerSupplyGuaranteeOverviewDetailsServiceClient.queryNoteInfo(hashmap);
		return data;
	}
	
	
	
	/************************************************************阶段************************************************************/
	/**
	 * <p>描述：获取保电阶段表格数据</p> 
	 * @param hashmap
	 * @return data
	 * @author:王万胜
	 * 2017年4月13日 下午4:48:06
	 */
	@RequestMapping("/powerSupplyGuaranteeOverviewDetails/getElectricityEnsurePhaseResult")
	public @ResponseBody List<Map<String,Object>> queryElectricityEnsurePhaseResult(@RequestParam HashMap<String, Object> hashmap) {
		List<Map<String,Object>> data = powerSupplyGuaranteeOverviewDetailsServiceClient.queryElectricityEnsurePhaseResult(hashmap);
		return data;
	}
	
	
	
	/************************************************************组织机构************************************************************/
	/**
	 * <p>描述：获取组织机构树</p>
	 * @param hashmap 
	 * @return tree
	 * @author:王万胜
	 * 2017年4月18日 下午7:38:45
	 */
	@RequestMapping("/powerSupplyGuaranteeOverviewDetails/getOrganizationTree")
	public @ResponseBody List<Map<String,Object>> getOrganizationTreeList(@RequestParam HashMap<String, Object> hashmap) {
		List<Map<String,Object>> tree = powerSupplyGuaranteeOverviewDetailsServiceClient.getOrganizationTreeList(hashmap);
		return tree;
	}
	
	/**
	 * <p>描述：获取组织机构表格信息</p> 
	 * @param hashmap
	 * @return data
	 * @author:王万胜
	 * 2017年4月18日 下午6:25:52
	 */
	@RequestMapping("/powerSupplyGuaranteeOverviewDetails/getOrganizationResult")
	public @ResponseBody List<Map<String,Object>> queryOrganizationResult(@RequestParam HashMap<String, Object> hashmap) {
		List<Map<String,Object>> data = powerSupplyGuaranteeOverviewDetailsServiceClient.queryOrganizationResult(hashmap);
		return data;
	}
	
	/**
	 * <p>描述：获取下级组织机构</p> 
	 * @param hashmap
	 * @return data
	 * @author:王万胜
	 * 2017年4月20日 下午1:52:13
	 */
	@RequestMapping("/powerSupplyGuaranteeOverviewDetails/getSubOrganizationResult")
	public @ResponseBody List<Map<String,Object>> querySubOrganizationResult(@RequestParam HashMap<String, Object> hashmap) {
		List<Map<String,Object>> data = powerSupplyGuaranteeOverviewDetailsServiceClient.querySubOrganizationResult(hashmap);
		return data;
	}
	
	
	
	/************************************************************保电站线************************************************************/
	/**
	 * <p>描述：获取保电站线表格数据</p> 
	 * @param hashmap
	 * @return data
	 * @author:王万胜
	 * 2017年4月21日 上午10:22:57
	 */
	@RequestMapping("/powerSupplyGuaranteeOverviewDetails/getStationOrLineResult")
	public @ResponseBody Map<String,Object> queryStationOrLineResult(@RequestParam HashMap<String, Object> hashmap) {
		List<Map<String,Object>> data = powerSupplyGuaranteeOverviewDetailsServiceClient.queryStationOrLineResult(hashmap);
		return data.get(0);
	}
	
	
	
	/************************************************************用户************************************************************/
	/**
	 * <p>描述：获取用户保电级别统计</p> 
	 * @param hashmap
	 * @return data
	 * @author:王万胜
	 * 2017年6月19日 下午5:23:34
	 */
	@RequestMapping("/powerSupplyGuaranteeOverviewDetails/getUserLevel")
	public @ResponseBody List<Map<String,Object>>queryUserLevel(@RequestParam HashMap<String, Object> hashmap) {
		List<Map<String,Object>> data = powerSupplyGuaranteeOverviewDetailsServiceClient.queryUserLevel(hashmap);
		return data;
	}
	
	/**
	 * <p>描述：获取用户表格数据</p> 
	 * @param hashmap
	 * @return data
	 * @author:王万胜
	 * 2017年4月26日 下午5:01:52
	 */
	@RequestMapping("/powerSupplyGuaranteeOverviewDetails/getUserResult")
	public @ResponseBody Map<String,Object> queryUserResult(@RequestParam HashMap<String, Object> hashmap) {
		List<Map<String,Object>> data = powerSupplyGuaranteeOverviewDetailsServiceClient.queryUserResult(hashmap);
		return data.get(0);
	}
	
	
	
	/************************************************************人员************************************************************/
	/**
	 * <p>描述：获取人员表格数据</p> 
	 * @param hashmap
	 * @return data
	 * @author:王万胜
	 * 2017年4月28日 上午10:41:19
	 */
	@RequestMapping("/powerSupplyGuaranteeOverviewDetails/getPersonResult")
	public @ResponseBody Map<String,Object> queryPersonResult(@RequestParam HashMap<String, Object> hashmap) {
		List<Map<String,Object>> data = powerSupplyGuaranteeOverviewDetailsServiceClient.queryPersonResult(hashmap);
		return data.get(0);
	}
	
	
	
	/************************************************************车辆************************************************************/
	/**
	 * <p>描述：获取车辆表格数据</p> 
	 * @param hashmap
	 * @return data
	 * @author:王万胜
	 * 2017年4月28日 上午10:43:43
	 */
	@RequestMapping("/powerSupplyGuaranteeOverviewDetails/getCarResult")
	public @ResponseBody Map<String,Object> queryCarResult(@RequestParam HashMap<String, Object> hashmap) {
		List<Map<String,Object>> data = powerSupplyGuaranteeOverviewDetailsServiceClient.queryCarResult(hashmap);
		return data.get(0);
	}
	
	
	
	/************************************************************驻点************************************************************/
	/**
	 * <p>描述：获取驻点表格数据</p> 
	 * @param hashmap
	 * @return data
	 * @author:王万胜
	 * 2017年4月28日 上午10:45:28
	 */
	@RequestMapping("/powerSupplyGuaranteeOverviewDetails/getPointResult")
	public @ResponseBody Map<String,Object> queryPointResult(@RequestParam HashMap<String, Object> hashmap) {
		List<Map<String,Object>> data = powerSupplyGuaranteeOverviewDetailsServiceClient.queryPointResult(hashmap);
		return data.get(0);
	}
	
	
	
	/************************************************************宾馆************************************************************/
	/**
	 * <p>描述：获取宾馆表格数据</p> 
	 * @param hashmap
	 * @return data
	 * @author:王万胜
	 * 2017年4月28日 上午10:48:54
	 */
	@RequestMapping("/powerSupplyGuaranteeOverviewDetails/getHotelResult")
	public @ResponseBody Map<String,Object> queryHotelResult(@RequestParam HashMap<String, Object> hashmap) {
		List<Map<String,Object>> data = powerSupplyGuaranteeOverviewDetailsServiceClient.queryHotelResult(hashmap);
		return data.get(0);
	}
	
	
	
	/************************************************************医院************************************************************/
	/**
	 * <p>描述：获取医院表格数据</p> 
	 * @param hashmap
	 * @return data
	 * @author:王万胜
	 * 2017年4月28日 上午10:50:29
	 */
	@RequestMapping("/powerSupplyGuaranteeOverviewDetails/getHospitalResult")
	public @ResponseBody Map<String,Object> queryHospitalResult(@RequestParam HashMap<String, Object> hashmap) {
		List<Map<String,Object>> data = powerSupplyGuaranteeOverviewDetailsServiceClient.queryHospitalResult(hashmap);
		return data.get(0);
	}
	
	
	
	/************************************************************气象信息************************************************************/
	/**
	 * <p>描述：获取登录人所属地市</p> 
	 * @param request
	 * @return param
	 * @author:王万胜
	 * 2017年7月27日 下午3:07:26
	 */
	@RequestMapping("/powerSupplyGuaranteeOverviewDetails/getLogCity")
	public Map<String, Object> getLogCity(HttpServletRequest request) {
		HashMap<String,Object> param = new HashMap<>();
		LoginUserInfo loginUserInfo = getLoginUserInfo(request);
		param.put("belongOrgUnitID", loginUserInfo.getBelongOrgUnitID());
		param.put("belongCityName", loginUserInfo.getBelongCityName());
		return param;
	}
	
	private LoginUserInfo getLoginUserInfo(HttpServletRequest request) {
		LoginUserInfo loginUserInfo=null;
		try {
			loginUserInfo = this.loginUserInfoUtils.getLoginUserInfo(request);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return loginUserInfo;
	}
	
	
	
	/************************************************************运行内容（故障、缺陷）************************************************************/
	/**
	 * <p>描述：获取故障、缺陷数量</p> 
	 * @param hashmap
	 * @return data
	 * @author:王万胜
	 * 2017年6月26日 下午1:42:35
	 */
	@RequestMapping("/powerSupplyGuaranteeOverviewDetails/getRunningContentCount")
	public @ResponseBody Map<String,Object> queryRunningContentCount(@RequestParam HashMap<String, Object> hashmap) {
		Map<String,Object> data = powerSupplyGuaranteeOverviewDetailsServiceClient.queryRunningContentCount(hashmap);
		return data;
	}
	
	/**
	 * <p>描述：获取故障信息</p> 
	 * @param hashmap
	 * @return data
	 * @author:王万胜
	 * 2017年6月26日 下午6:30:04
	 */
	@RequestMapping("/powerSupplyGuaranteeOverviewDetails/getFaultResult")
	public @ResponseBody Map<String,Object> queryFaultResult(@RequestParam HashMap<String, Object> hashmap) {
		List<Map<String,Object>> data = powerSupplyGuaranteeOverviewDetailsServiceClient.queryFaultResult(hashmap);
		return data.get(0);
	}
	
	/**
	 * <p>描述：获取缺陷信息</p> 
	 * @param hashmap
	 * @return data
	 * @author:王万胜
	 * 2017年6月26日 下午7:45:22
	 */
	@RequestMapping("/powerSupplyGuaranteeOverviewDetails/getDefectResult")
	public @ResponseBody Map<String,Object> queryDefectResult(@RequestParam HashMap<String, Object> hashmap) {
		List<Map<String,Object>> data = powerSupplyGuaranteeOverviewDetailsServiceClient.queryDefectResult(hashmap);
		return data.get(0);
	}
	
	
	
	/************************************************************公共************************************************************/
	/**
	 * <p>描述：获取资源保障的数量（人员、车辆、驻点、宾馆、医院）</p> 
	 * @param hashmap
	 * @return data
	 * @author:王万胜
	 * 2017年6月21日 下午3:05:58
	 */
	@RequestMapping("/powerSupplyGuaranteeOverviewDetails/getResourceCount")
	public @ResponseBody Map<String,Object> queryResourceCount(@RequestParam HashMap<String, Object> hashmap) {
		Map<String,Object> data = powerSupplyGuaranteeOverviewDetailsServiceClient.queryResourceCount(hashmap);
		return data;
	}
	
	/**
	 * <p>描述：获取线路、电站、人员、车辆chart图数据</p> 
	 * @param hashmap
	 * @return vm
	 * @author:王万胜
	 * 2017年6月22日 下午2:17:16
	 */
	@RequestMapping("/powerSupplyGuaranteeOverviewDetails/getChartData")
	public PowerSupplyGuaranteeOverviewDetailsViewModel initSpecial(@RequestParam HashMap<String,Object> hashmap){
		PowerSupplyGuaranteeOverviewDetailsViewModel vm = new PowerSupplyGuaranteeOverviewDetailsViewModel();
		vm.setPersonChart(makePersonChartPie(hashmap));
		vm.setCarChart(makeCarChartPie(hashmap));
		vm.setLineChart(makeLineChartBar(hashmap));
		vm.setStationChart(makeStationChartBar(hashmap));
		return vm;
	}
	
	// 获取人员饼图数据
	private NestRingModel makePersonChartPie(@RequestParam HashMap<String,Object> hashmap) {	
		List<Map<String, Object>> infoList = powerSupplyGuaranteeOverviewDetailsServiceClient.queryPersonChartResult(hashmap);
		return ModelUtil.makeNestRingModel(null, infoList, "type", "typeName","count");
	}
	
	// 获取车辆饼图数据
	private NestRingModel makeCarChartPie(@RequestParam HashMap<String,Object> hashmap) {	
		List<Map<String, Object>> infoList = powerSupplyGuaranteeOverviewDetailsServiceClient.queryCarChartResult(hashmap);
		return ModelUtil.makeNestRingModel(null, infoList, "type", "typeName","count");
	}
	
	// 获取线路柱状图数据
	private  Map<String,List<?>> makeLineChartBar(@RequestParam HashMap<String,Object> hashmap) {	
		hashmap.put("stationLineType", "01");
		Map<String,List<?>> infoList = powerSupplyGuaranteeOverviewDetailsServiceClient.queryStationOrLineChartResult(hashmap);
		return infoList;
	}
	
	// 获取电站线路数据
	private  Map<String,List<?>> makeStationChartBar(@RequestParam HashMap<String,Object> hashmap) {
		hashmap.put("stationLineType", "02");
		Map<String,List<?>> infoList = powerSupplyGuaranteeOverviewDetailsServiceClient.queryStationOrLineChartResult(hashmap);
		return infoList;
	}
}
