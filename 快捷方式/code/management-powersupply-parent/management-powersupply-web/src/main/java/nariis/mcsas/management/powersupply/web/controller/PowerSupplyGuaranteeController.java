package nariis.mcsas.management.powersupply.web.controller;

import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import cst.constants.management.VehicleType;
import nariis.mcsas.management.common.model.ExcelBean;
import nariis.mcsas.management.common.model.ExcelFieldBean;
import nariis.mcsas.management.common.util.CommonExcelService;
import nariis.mcsas.management.common.util.JsonUtil;
import nariis.mcsas.management.common.util.ManagementUtils;
import nariis.mcsas.management.powersupply.business.core.client.PowerSupplyGuaranteeServiceClient;
import sgcim30.assets.management.ElectricityEnsureHospital;
import sgcim30.assets.management.ElectricityEnsureHotel;
import sgcim30.assets.management.ElectricityEnsurePerson;
import sgcim30.assets.management.ElectricityEnsureProcess;
import sgcim30.assets.management.ElectricityEnsureStation;
import sgcim30.assets.management.ElectricityEnsureUser;
import sgcim30.assets.management.ElectricityEnsureVehicle;
import nariis.falcon.oaf.OafCommands;
import nariis.falcon.oaf.models.constant.Constant;
import nariis.falcon.oaf.models.constant.ConstantRegistry;
import nariis.mcsas.commons.model.BaseViewModel;
import nariis.mcsas.commons.utils.ExcelUtils;


/**
 * <p>ClassName:PowerSupplyGuaranteeController，</p>
 * <p>描述:保电任务方法</p>
 * @author 王万胜
 * @date 2017年3月23日下午3:01:17
 * @version 
 */
@RestController
public class PowerSupplyGuaranteeController {
	@Autowired
	private PowerSupplyGuaranteeServiceClient powerSupplyGuaranteeServiceClient;
	
	@Autowired
	CommonExcelService commonExcelService;
	
	@Autowired
	OafCommands oafCommands;
	
	/************************************************************基础信息************************************************************/
	/**
	 * <p>描述：各阶段记录数量</p> 
	 * @return data
	 * @author:王万胜
	 * 2017年3月14日 下午7:49:38
	 */
	@RequestMapping("/powerSupplyGuarantee/getGridsCount")
	public @ResponseBody Map<String,Object> gridsCountResult() {
		List<Map<String,Object>> dataList = powerSupplyGuaranteeServiceClient.gridsCountResult();
		Map<String,Object> data = new HashMap<String,Object>();
		data.put("data", dataList);
		data.put("successful", true);
		return data;
	}
	
	/**
	 * <p>描述：获取主表格数据</p> 
	 * @param hashmap
	 * @return data
	 * @author:王万胜
	 * 2017年3月10日 下午2:56:54
	 */
	@RequestMapping("/powerSupplyGuarantee/getPowerSupplyGuaranteeResult")
	public @ResponseBody Map<String,Object> queryPowerSupplyGuaranteeResult(@RequestParam HashMap<String, Object> hashmap) {
		List<Map<String,Object>> data = powerSupplyGuaranteeServiceClient.queryPowerSupplyGuaranteeResult(hashmap);
		int length = data.size();
		String[] times = {"startTime", "endTime"};
		
		for(int i=0; i<length; i++){
			for(int j=0; j<times.length; j++){
				if(data.get(i).get(times[j]) != null){
					data.get(i).put(times[j], data.get(i).get(times[j]).toString().substring(0, 16));
				}
			}
		}
		
		return data.get(0);
	}
	
	/**
	 * <p>描述：获取选定的一条保电任务的基础信息</p> 
	 * @param hashmap
	 * @return data
	 * @author:王万胜
	 * 2017年5月16日 下午7:20:35
	 */
	@RequestMapping("/powerSupplyGuarantee/getNoteInfo")
	public @ResponseBody List<Map<String,Object>> queryNoteInfo(@RequestParam HashMap<String, Object> hashmap) {
		List<Map<String,Object>> data = powerSupplyGuaranteeServiceClient.queryNoteInfo(hashmap);
		return data;
	}
	
	/**
	 * <p>描述：获取基础信息保电阶段表格数据</p> 
	 * @param hashmap
	 * @return data
	 * @author:王万胜
	 * 2017年4月13日 下午4:48:06
	 */
	@RequestMapping("/powerSupplyGuarantee/getElectricityEnsurePhaseResult")
	public @ResponseBody List<Map<String,Object>> queryElectricityEnsurePhaseResult(@RequestParam HashMap<String, Object> hashmap) {
		List<Map<String,Object>> data = powerSupplyGuaranteeServiceClient.queryElectricityEnsurePhaseResult(hashmap);
		int length = data.size();
		String[] times = {"startTime", "endTime", "updateDateTime", "recordDate"};
		
		for(int i=0; i<length; i++){
			for(int j=0; j<times.length; j++){
				if(data.get(i).get(times[j]) != null){
					data.get(i).put(times[j], data.get(i).get(times[j]).toString().substring(0, 16));
				}
			}
		}
		
		return data;
	}
	
	/**
	 * <p>描述：新增基础信息数据</p> 
	 * @param hashmap
	 * @return id
	 * @author:王万胜
	 * 2017年3月14日 上午9:15:13
	 */
	@RequestMapping(value="/powerSupplyGuarantee/insertPowerSupplyGuarantee", method=RequestMethod.POST)
	public @ResponseBody HashMap<String,Object> insertPowerSupplyGuaranteeResult(@RequestBody HashMap<String, Object> hashmap) {
		HashMap<String,Object> id = powerSupplyGuaranteeServiceClient.insertPowerSupplyGuaranteeResult(hashmap);
		return id;
	}
	
	/**
	 * <p>描述：编辑基础信息数据</p> 
	 * @param hashmap
	 * @author:王万胜
	 * @return vm
	 * 2017年3月14日 上午9:15:13
	 */
	@RequestMapping(value="/powerSupplyGuarantee/updatePowerSupplyGuarantee", method=RequestMethod.POST)
	public BaseViewModel updatePowerSupplyGuaranteeResult(@RequestBody HashMap<String, Object> hashmap) {
		powerSupplyGuaranteeServiceClient.updatePowerSupplyGuaranteeResult(hashmap);
		BaseViewModel vm = new BaseViewModel();
		vm.setSuccessful(true);
		return vm;
	}
	
	/**
	 * <p>描述：删除基础信息主表数据</p> 
	 * @param hashmap
	 * @author:王万胜
	 * @return vm
	 * 2017年3月14日 上午9:15:13
	 */
	@RequestMapping("/powerSupplyGuarantee/deletePowerSupplyGuarantee")
	public BaseViewModel deletePowerSupplyGuaranteeResult(@RequestParam HashMap<String, Object> hashmap) {
		powerSupplyGuaranteeServiceClient.deletePowerSupplyGuaranteeResult(hashmap);
		BaseViewModel vm = new BaseViewModel();
		vm.setSuccessful(true);
		return vm;
	}
	
	/**
	 * <p>描述：获取保电级别下拉框</p> 
	 * @return powerLever
	 * @author:王万胜
	 * 2017年4月6日 下午1:58:53
	 */
	@RequestMapping("/powerSupplyGuarantee/getPowerLever")
	public String getPowerLeverList() {
		String powerLever = powerSupplyGuaranteeServiceClient.getPowerLeverList();
		return powerLever;
	}
	
	/**
	 * <p>描述：获取任务来源下拉框</p> 
	 * @return taskSource
	 * @author:王万胜
	 * 2017年4月11日 下午7:06:32
	 */
	@RequestMapping("/powerSupplyGuarantee/getTaskSource")
	public String getTaskSourceList() {
		String deviceType = powerSupplyGuaranteeServiceClient.getTaskSourceList();
		return deviceType;
	}
	
	
	
	/************************************************************组织机构************************************************************/
	/**
	 * <p>描述：获取组织机构树</p>
	 * @param hashmap 
	 * @return tree
	 * @author:王万胜
	 * 2017年4月18日 下午7:38:45
	 */
	@RequestMapping("/powerSupplyGuarantee/getOrganizationTree")
	public @ResponseBody List<Map<String,Object>> getOrganizationTreeList(@RequestParam HashMap<String, Object> hashmap) {
		List<Map<String,Object>> tree = powerSupplyGuaranteeServiceClient.getOrganizationTreeList(hashmap);
		return tree;
	}
	
	/**
	 * <p>描述：获取组织机构表格信息</p> 
	 * @param hashmap
	 * @return data
	 * @author:王万胜
	 * 2017年4月18日 下午6:25:52
	 */
	@RequestMapping("/powerSupplyGuarantee/getOrganizationResult")
	public @ResponseBody Map<String,Object> queryOrganizationResult(@RequestParam HashMap<String, Object> hashmap) {
		List<Map<String,Object>> data = powerSupplyGuaranteeServiceClient.queryOrganizationResult(hashmap);
		return data.get(0);
	}
	
	/**
	 * <p>描述：插入或修改组织机构</p> 
	 * @param hashmap
	 * @return vm
	 * @author:王万胜
	 * 
	 * 2017年4月19日 下午2:01:31
	 */
	@RequestMapping(value="/powerSupplyGuarantee/insertOrUpdateOrganization", method=RequestMethod.POST)
	public BaseViewModel insertOrUpdateOrganizationResult(@RequestBody HashMap<String, Object> hashmap) {
		powerSupplyGuaranteeServiceClient.insertOrUpdateOrganizationResult(hashmap);
		BaseViewModel vm = new BaseViewModel();
		vm.setSuccessful(true);
		return vm;
	}
	
	/**
	 * <p>描述：获取下级组织机构</p> 
	 * @param hashmap
	 * @return data
	 * @author:王万胜
	 * 2017年4月20日 下午1:52:13
	 */
	@RequestMapping("/powerSupplyGuarantee/getSubOrganizationResult")
	public @ResponseBody List<Map<String,Object>> querySubOrganizationResult(@RequestParam HashMap<String, Object> hashmap) {
		List<Map<String,Object>> data = powerSupplyGuaranteeServiceClient.querySubOrganizationResult(hashmap);
		return data;
	}
	
	/**
	 * <p>描述：删除组织机构记录</p> 
	 * @param hashmap 
	 * @return vm
	 * @author:王万胜
	 * 2017年4月19日 下午7:06:20
	 */
	@RequestMapping("/powerSupplyGuarantee/deleteOrganization")
	public BaseViewModel deleteOrganizationResult(@RequestParam HashMap<String, Object> hashmap) {
		powerSupplyGuaranteeServiceClient.deleteOrganizationResult(hashmap);
		BaseViewModel vm = new BaseViewModel();
		vm.setSuccessful(true);
		return vm;
	}
	
	
	
	/************************************************************保电站线************************************************************/
	/**
	 * <p>描述：获取电压等级下拉框</p> 
	 * @return voltageLevel
	 * @author:王万胜
	 * 2017年4月18日 下午3:59:47
	 */
	@RequestMapping("/powerSupplyGuarantee/getVoltageLevelList")
	public String getVoltageLevel() {
		String voltageLevel = ManagementUtils.getVoltageLevel();
		return voltageLevel;
	}
	
	/**
	 * <p>描述：获取保电站线表格数据</p> 
	 * @param hashmap
	 * @return data
	 * @author:王万胜
	 * 2017年4月21日 上午10:22:57
	 */
	@RequestMapping("/powerSupplyGuarantee/getStationOrLineResult")
	public @ResponseBody Map<String,Object> queryStationOrLineResult(@RequestParam HashMap<String, Object> hashmap) {
		List<Map<String,Object>> data = powerSupplyGuaranteeServiceClient.queryStationOrLineResult(hashmap);
		return data.get(0);
	}
	
	/**
	 * <p>描述：插入或修改保电站线</p> 
	 * @param hashmap
	 * @return vm
	 * @author:王万胜
	 * 2017年6月2日 上午11:02:55
	 */
	@RequestMapping("/powerSupplyGuarantee/insertStationOrLine")
	public BaseViewModel insertStationOrLineResult(@RequestParam HashMap<String, Object> hashmap) {
		powerSupplyGuaranteeServiceClient.insertStationOrLineResult(hashmap);
		BaseViewModel vm = new BaseViewModel();
		vm.setSuccessful(true);
		return vm;
	}
	
	/**
	 * <p>描述：修改保电站线信息</p> 
	 * @param hashmap 
	 * @return vm
	 * @author:王万胜
	 * 2017年4月21日 下午3:43:53
	 */
	@RequestMapping("/powerSupplyGuarantee/updateStationOrLine")
	public BaseViewModel updateStationOrLineResult(@RequestParam HashMap<String, Object> hashmap) {
		powerSupplyGuaranteeServiceClient.updateStationOrLineResult(hashmap);
		BaseViewModel vm = new BaseViewModel();
		vm.setSuccessful(true);
		return vm;
	}
	
	/**
	 * <p>描述：删除保电站线信息</p> 
	 * @param hashmap
	 * @return vm
	 * @author:王万胜
	 * 2017年6月5日 下午2:12:08
	 */
	@RequestMapping("/powerSupplyGuarantee/deleteStationOrLine")
	public BaseViewModel deleteStationOrLineResult(@RequestParam HashMap<String, Object> hashmap) {
		powerSupplyGuaranteeServiceClient.deleteStationOrLineResult(hashmap);
		BaseViewModel vm = new BaseViewModel();
		vm.setSuccessful(true);
		return vm;
	}
	
	/**
	 * <p>描述：点击闭环时判断勾选的保电任务（可多选）是否已关联保电站线</p> 
	 * @param hashmap
	 * @return data
	 * @author:王万胜
	 * 2017年6月11日 下午6:00:22
	 */
	@RequestMapping("/powerSupplyGuarantee/getWhetherHadSol")
	public @ResponseBody List<Map<String,Object>> queryWhetherHadSol(@RequestParam HashMap<String, Object> hashmap) {
		List<Map<String,Object>> data = powerSupplyGuaranteeServiceClient.queryWhetherHadSol(hashmap);
		return data;
	}
	
	
	
	/************************************************************用户************************************************************/
	/**
	 * <p>描述：获取用户表格数据</p> 
	 * @param hashmap
	 * @return data
	 * @author:王万胜
	 * 2017年4月26日 下午5:01:52
	 */
	@RequestMapping("/powerSupplyGuarantee/getUserResult")
	public @ResponseBody Map<String,Object> queryUserResult(@RequestParam HashMap<String, Object> hashmap) {
		List<Map<String,Object>> data = powerSupplyGuaranteeServiceClient.queryUserResult(hashmap);
		return data.get(0);
	}
	
	/**
	 * <p>描述：新增用户数据</p> 
	 * @param hashmap
	 * @return vm
	 * @author:王万胜
	 * 2017年4月27日 下午2:49:13
	 */
	@RequestMapping("/powerSupplyGuarantee/insertUser")
	public BaseViewModel insertUserResult(@RequestParam HashMap<String, Object> hashmap) {
		powerSupplyGuaranteeServiceClient.insertUserResult(hashmap);
		BaseViewModel vm = new BaseViewModel();
		vm.setSuccessful(true);
		return vm;
	}
	
	/**
	 * <p>描述：修改用户数据</p> 
	 * @param hashmap
	 * @return vm
	 * @author:王万胜
	 * 2017年4月27日 下午3:04:21
	 */
	@RequestMapping("/powerSupplyGuarantee/updateUser")
	public BaseViewModel updateUserResult(@RequestParam HashMap<String, Object> hashmap) {
		powerSupplyGuaranteeServiceClient.updateUserResult(hashmap);
		BaseViewModel vm = new BaseViewModel();
		vm.setSuccessful(true);
		return vm;
	}
	
	/**
	 * <p>描述：删除用户数据</p> 
	 * @param hashmap
	 * @return vm
	 * @author:王万胜
	 * 2017年4月28日 上午9:26:37
	 */
	@RequestMapping("/powerSupplyGuarantee/deleteUser")
	public BaseViewModel deleteUserResult(@RequestParam HashMap<String, Object> hashmap) {
		powerSupplyGuaranteeServiceClient.deleteUserResult(hashmap);
		BaseViewModel vm = new BaseViewModel();
		vm.setSuccessful(true);
		return vm;
	}
	
	
	
	/************************************************************人员************************************************************/
	/**
	 * <p>描述：获取人员表格数据</p> 
	 * @param hashmap
	 * @return data
	 * @author:王万胜
	 * 2017年4月28日 上午10:41:19
	 */
	@RequestMapping("/powerSupplyGuarantee/getPersonResult")
	public @ResponseBody Map<String,Object> queryPersonResult(@RequestParam HashMap<String, Object> hashmap) {
		List<Map<String,Object>> data = powerSupplyGuaranteeServiceClient.queryPersonResult(hashmap);
		return data.get(0);
	}
	
	/**
	 * <p>描述：删除人员数据</p> 
	 * @param hashmap
	 * @return vm
	 * @author:王万胜
	 * 2017年4月28日 下午3:55:22
	 */
	@RequestMapping("/powerSupplyGuarantee/deletePerson")
	public BaseViewModel deletePersonResult(@RequestParam HashMap<String, Object> hashmap) {
		powerSupplyGuaranteeServiceClient.deletePersonResult(hashmap);
		BaseViewModel vm = new BaseViewModel();
		vm.setSuccessful(true);
		return vm;
	}
	
	
	
	/************************************************************车辆************************************************************/
	/**
	 * <p>描述：获取车辆表格数据</p> 
	 * @param hashmap
	 * @return data
	 * @author:王万胜
	 * 2017年4月28日 上午10:43:43
	 */
	@RequestMapping("/powerSupplyGuarantee/getCarResult")
	public @ResponseBody Map<String,Object> queryCarResult(@RequestParam HashMap<String, Object> hashmap) {
		List<Map<String,Object>> data = powerSupplyGuaranteeServiceClient.queryCarResult(hashmap);
		return data.get(0);
	}
	
	/**
	 * <p>描述：删除车辆数据</p> 
	 * @param hashmap
	 * @return vm
	 * @author:王万胜
	 * 2017年4月28日 下午4:09:53
	 */
	@RequestMapping("/powerSupplyGuarantee/deleteCar")
	public BaseViewModel deleteCarResult(@RequestParam HashMap<String, Object> hashmap) {
		powerSupplyGuaranteeServiceClient.deleteCarResult(hashmap);
		BaseViewModel vm = new BaseViewModel();
		vm.setSuccessful(true);
		return vm;
	}
	
	
	
	/************************************************************驻点************************************************************/
	/**
	 * <p>描述：获取驻点表格数据</p> 
	 * @param hashmap
	 * @return data
	 * @author:王万胜
	 * 2017年4月28日 上午10:45:28
	 */
	@RequestMapping("/powerSupplyGuarantee/getPointResult")
	public @ResponseBody Map<String,Object> queryPointResult(@RequestParam HashMap<String, Object> hashmap) {
		List<Map<String,Object>> data = powerSupplyGuaranteeServiceClient.queryPointResult(hashmap);
		return data.get(0);
	}
	
	/**
	 * <p>描述：删除驻点数据</p> 
	 * @param hashmap
	 * @return vm
	 * @author:王万胜
	 * 2017年4月28日 下午4:13:26
	 */
	@RequestMapping("/powerSupplyGuarantee/deletePoint")
	public BaseViewModel deletePointResult(@RequestParam HashMap<String, Object> hashmap) {
		powerSupplyGuaranteeServiceClient.deletePointResult(hashmap);
		BaseViewModel vm = new BaseViewModel();
		vm.setSuccessful(true);
		return vm;
	}
	
	/**
	 * <p>描述：修改驻点数据（位置信息）</p> 
	 * @param hashmap
	 * @return vm
	 * @author:王万胜
	 * 2017年7月4日 上午10:25:37
	 */
	@RequestMapping("/powerSupplyGuarantee/updatePoint")
	public BaseViewModel updatePointResult(@RequestParam HashMap<String, Object> hashmap) {
		powerSupplyGuaranteeServiceClient.updatePointResult(hashmap);
		BaseViewModel vm = new BaseViewModel();
		vm.setSuccessful(true);
		return vm;
	}
	
	
	/************************************************************宾馆************************************************************/
	/**
	 * <p>描述：获取宾馆表格数据</p> 
	 * @param hashmap
	 * @return data
	 * @author:王万胜
	 * 2017年4月28日 上午10:48:54
	 */
	@RequestMapping("/powerSupplyGuarantee/getHotelResult")
	public @ResponseBody Map<String,Object> queryHotelResult(@RequestParam HashMap<String, Object> hashmap) {
		List<Map<String,Object>> data = powerSupplyGuaranteeServiceClient.queryHotelResult(hashmap);
		return data.get(0);
	}
	
	/**
	 * <p>描述：删除宾馆数据</p> 
	 * @param hashmap
	 * @return vm
	 * @author:王万胜
	 * 2017年4月28日 下午4:18:06
	 */
	@RequestMapping("/powerSupplyGuarantee/deleteHotel")
	public BaseViewModel deleteHotelResult(@RequestParam HashMap<String, Object> hashmap) {
		powerSupplyGuaranteeServiceClient.deleteHotelResult(hashmap);
		BaseViewModel vm = new BaseViewModel();
		vm.setSuccessful(true);
		return vm;
	}
	
	/**
	 * <p>描述：修改宾馆数据（位置信息）</p> 
	 * @param hashmap
	 * @return vm
	 * @author:王万胜
	 * 2017年7月4日 上午10:25:37
	 */
	@RequestMapping("/powerSupplyGuarantee/updateHotel")
	public BaseViewModel updateHotelResult(@RequestParam HashMap<String, Object> hashmap) {
		powerSupplyGuaranteeServiceClient.updateHotelResult(hashmap);
		BaseViewModel vm = new BaseViewModel();
		vm.setSuccessful(true);
		return vm;
	}
	
	
	
	/************************************************************医院************************************************************/
	/**
	 * <p>描述：获取医院表格数据</p> 
	 * @param hashmap
	 * @return data
	 * @author:王万胜
	 * 2017年4月28日 上午10:50:29
	 */
	@RequestMapping("/powerSupplyGuarantee/getHospitalResult")
	public @ResponseBody Map<String,Object> queryHospitalResult(@RequestParam HashMap<String, Object> hashmap) {
		List<Map<String,Object>> data = powerSupplyGuaranteeServiceClient.queryHospitalResult(hashmap);
		return data.get(0);
	}
	
	/**
	 * <p>描述：删除医院数据</p> 
	 * @param hashmap
	 * @return vm
	 * @author:王万胜
	 * 2017年4月28日 下午4:21:42
	 */
	@RequestMapping("/powerSupplyGuarantee/deleteHospital")
	public BaseViewModel deleteHospitalResult(@RequestParam HashMap<String, Object> hashmap) {
		powerSupplyGuaranteeServiceClient.deleteHospitalResult(hashmap);
		BaseViewModel vm = new BaseViewModel();
		vm.setSuccessful(true);
		return vm;
	}
	
	/**
	 * <p>描述：修改医院数据（位置信息）</p> 
	 * @param hashmap
	 * @return vm
	 * @author:王万胜
	 * 2017年7月4日 上午10:25:37
	 */
	@RequestMapping("/powerSupplyGuarantee/updateHospital")
	public BaseViewModel updateHospitalResult(@RequestParam HashMap<String, Object> hashmap) {
		powerSupplyGuaranteeServiceClient.updateHospitalResult(hashmap);
		BaseViewModel vm = new BaseViewModel();
		vm.setSuccessful(true);
		return vm;
	}
	
	
	
	/************************************************************日报************************************************************/
	/**
	 * <p>描述：编辑日报信息（包含新增、修改）</p> 
	 * @param hashmap
	 * @return vm
	 * @author:王万胜
	 * 2017年6月5日 下午3:50:46
	 */
	@RequestMapping(value="/powerSupplyGuarantee/insertOrUpdateDaily", method=RequestMethod.POST)
	public BaseViewModel insertOrUpdateDailyResult(@RequestBody HashMap<String, Object> hashmap) {
		powerSupplyGuaranteeServiceClient.insertOrUpdateDailyResult(hashmap);
		BaseViewModel vm = new BaseViewModel();
		vm.setSuccessful(true);
		return vm;
	}
	
	
	
	/************************************************************日报-汇报记录************************************************************/
	/**
	 * <p>描述：获取日报-汇报记录表格数据</p> 
	 * @param hashmap
	 * @return data
	 * @author:王万胜
	 * 2017年5月4日 下午3:25:31
	 */
	@RequestMapping("/powerSupplyGuarantee/getLog")
	public @ResponseBody Map<String,Object> queryLogResult(@RequestParam HashMap<String, Object> hashmap) {
		List<Map<String,Object>> data = powerSupplyGuaranteeServiceClient.queryLogResult(hashmap);
		int length = data.size();
		for(int i=0; i<length; i++){
			if(data.get(i).get("logDate") != null){
				data.get(i).put("logDate", data.get(i).get("logDate").toString().substring(0, 16));
			}
		}
		return data.get(0);
	}
	
	/**
	 * <p>描述：删除日报-汇报记录表格中的数据</p> 
	 * @param hashmap
	 * @return vm
	 * @author:王万胜
	 * 2017年6月6日 下午2:09:40
	 */
	@RequestMapping("/powerSupplyGuarantee/deleteLog")
	public BaseViewModel deleteLogResult(@RequestParam HashMap<String, Object> hashmap) {
		powerSupplyGuaranteeServiceClient.deleteLogResult(hashmap);
		BaseViewModel vm = new BaseViewModel();
		vm.setSuccessful(true);
		return vm;
	}
	
	/**
	 * <p>描述：复制日报-汇报记录表格中的数据</p> 
	 * @param hashmap
	 * @return vm
	 * @author:王万胜
	 * 2017年6月6日 下午3:50:01
	 */
	@RequestMapping("/powerSupplyGuarantee/copyLog")
	public BaseViewModel copyLogResult(@RequestParam HashMap<String, Object> hashmap) {
		powerSupplyGuaranteeServiceClient.copyLogResult(hashmap);
		BaseViewModel vm = new BaseViewModel();
		vm.setSuccessful(true);
		return vm;
	}
	
	
	
	/************************************************************日报-保电过程************************************************************/
	/**
	 * <p>描述：获取日报-保电过程表格数据</p> 
	 * @param hashmap
	 * @return data
	 * @author:王万胜
	 * 2017年5月4日 下午3:48:00
	 */
	@RequestMapping("/powerSupplyGuarantee/getProcess")
	public @ResponseBody Map<String,Object> queryProcessResult(@RequestParam HashMap<String, Object> hashmap) {
		List<Map<String,Object>> data = powerSupplyGuaranteeServiceClient.queryProcessResult(hashmap);
		return data.get(0);
	}
	
	/**
	 * <p>描述：删除日报-保电过程表格中的数据</p> 
	 * @param hashmap
	 * @return vm
	 * @author:王万胜
	 * 2017年6月6日 下午2:19:57
	 */
	@RequestMapping("/powerSupplyGuarantee/deleteProcess")
	public BaseViewModel deleteProcessResult(@RequestParam HashMap<String, Object> hashmap) {
		powerSupplyGuaranteeServiceClient.deleteProcessResult(hashmap);
		BaseViewModel vm = new BaseViewModel();
		vm.setSuccessful(true);
		return vm;
	}
	
	
	
	/************************************************************公共方法************************************************************/
	/**
	 * <p>描述：下载excel模板</p> 
	 * @param request
	 * @param response
	 * @return  excel模板
	 * @author:毕亚鹏
	 * 2017年4月6日 上午8:38:32
	 */
	@RequestMapping("/powerSupplyGuarantee/downloadExcelTemplate")
	public String downloadExcelTemplate(HttpServletRequest request,HttpServletResponse response){
		System.out.println("进入到downloadExcelTemplate...");
		String templateCfgFileName=request.getParameter("templateCfgFileName");
		Map<String,Object> requestMapData=changeRequestParameterToMap(request);
		ExcelBean excelBean=commonExcelService.getExcelBean(templateCfgFileName);
		return commonExcelService.downloadExcelTemplate(excelBean,response,requestMapData);
	}
	
	/**
	 * 
	 * <p>描述：将request对象转换为map</p> 
	 * @param request 
	 * @return map对象
	 * @author:毕亚鹏
	 * 2017年3月17日 上午10:03:20
	 */
	private Map<String,Object> changeRequestParameterToMap(HttpServletRequest request){
		Map<String,Object>parameterMap=new HashMap<String,Object>();
		Enumeration<String> requestParameterEnu= request.getParameterNames();
		while(requestParameterEnu.hasMoreElements()){
			String key=requestParameterEnu.nextElement();
			String keyValue=request.getParameter(key);
			parameterMap.put(key, keyValue);
		}
		System.out.println("datas="+parameterMap.get("datas"));
		return parameterMap;
	}
	
	
	/**
	 * 
	 * <p>描述：依据模板进行excel导入</p> 
	 * @param excelFile <input type='file' name=''>标签的name属性值
	 * @param excelTemplateName excel模板名
	 * @param taskId 所属保电任务ID
	 * @param request 请求对象
	 * @param response 响应对象
	 * @return 导入是否成功，"success":成功,否则，为失败信息
	 * @author:毕亚鹏
	 * 2017年4月8日 上午11:12:51
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/powerSupplyGuarantee/importTemplete.do")
	public @ResponseBody String importTemplete(@RequestParam("excelFile") MultipartFile excelFile, @RequestParam("taskId") String taskId,
			@RequestParam("excelTemplateName") String excelTemplateName, HttpServletRequest request, HttpServletResponse response) {
		response.setCharacterEncoding("UTF-8");
		String info = "导入成功！";
		try {
			Date updateDateTime = new Date();	// 最后更新时间
			Date recordDate = new Date();		// 记录时间
			
			InputStream in = excelFile.getInputStream();
			ExcelBean excelBean = commonExcelService.getExcelBean(excelTemplateName);
			List<Map<ExcelFieldBean, String>> dataList = commonExcelService.getExcelDatas(in, excelBean);
			
			// 人员、车辆导入文档中日期不一致/当前系统中已有***（日期）的数据
			if("maintainPower_personnel".equals(excelTemplateName)){
				boolean same = true;
				int m = 0;
				String personDate = "";
				for (Map<ExcelFieldBean, String> data : dataList) {
					Set<ExcelFieldBean> set = data.keySet();
					String title = "";
					for (ExcelFieldBean efb : set) {
						String fildName = efb.getFieldName();
						String value = data.get(efb);
						if(m == 0){
							title += value;
						}else if("personDate".equals(fildName)){
							// 日期校验
							try{
								SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-DD");
								sdf.parse(value);
							}catch(Exception e){
								info = "导入文档中日期格式错误";
								return info;
							}
							
							if(m == 1){
								personDate = value.substring(0, 10);
							}else if(m > 1){
								if(!personDate.equals(value.substring(0, 10))){
									info = "导入文档中日期不一致";
									same = false;
									return info;
								}
							}
						}
					}
					
					if(m == 0 && !"日期姓名专业所属单位所属组织联系电话任务说明".equals(title)){
						return "所导入的模板不符合人员模板规范，请重新选择！";
					}
					m++;
				}
				
				if(same){
					HashMap<String,Object> personnel_params = new HashMap<String,Object>();
					personnel_params.put("taskId", taskId);
					personnel_params.put("personDate", personDate);
					Map<String, Object> personResult = queryPersonResult(personnel_params);
					Object personnel_data = personResult.get("data");
					int size = ((List<Map<String, Object>>) personnel_data).size();
					
					if(size > 0){
						info = "当前系统中已有"+personDate+"的数据，请删除后重新导入";
						return info;
					}
				}
			}else if("maintainPower_car".equals(excelTemplateName)){
				boolean same = true;
				int m = 0;
				String vehicleDate = "";
				for (Map<ExcelFieldBean, String> data : dataList) {
					Set<ExcelFieldBean> set = data.keySet();
					String title = "";
					for (ExcelFieldBean efb : set) {
						String fildName = efb.getFieldName();
						String value = data.get(efb);
						if(m == 0){
							title += value;
						}
						
						if("vehicleDate".equals(fildName)){
							if(m == 1){
								vehicleDate = value.substring(0, 10);
							}else if(m > 1){
								if(!vehicleDate.equals(value.substring(0, 10))){
									info = "导入文档中日期不一致";
									same = false;
									return info;
								}
							}
						}
					}
					
					if(m == 0 && !"日期车牌号车辆类型所属单位所属组织驾驶员联系电话任务说明".equals(title)){
						return "所导入的模板不符合车辆模板规范，请重新选择！";
					}
					m++;
				}
				
				if(same){
					HashMap<String,Object> car_params = new HashMap<String,Object>();
					car_params.put("taskId", taskId);
					car_params.put("vehicleDate", vehicleDate);
					Map<String, Object> personResult = queryCarResult(car_params);
					Object car_data = personResult.get("data");
					int size = ((List<Map<String, Object>>) car_data).size();
					
					if(size > 0){
						info = "当前系统中已有"+vehicleDate+"的数据，请删除后重新导入";
						return info;
					}
				}
			}
			
			int i = 0;
			String title = "";
			for (Map<ExcelFieldBean, String> data : dataList) {
				HashMap<String,Object> rowData = new HashMap<String,Object>();
				Set<ExcelFieldBean> set = data.keySet();
				for (ExcelFieldBean efb : set) {
					String fildName = efb.getFieldName();
					String value = data.get(efb);
					if(i == 0){
						title += value;
					}else if(i > 0){
						if("personDate".equals(fildName) || "vehicleDate".equals(fildName) || "logDayTime".equals(fildName)){
							Date date = null;		// 日期
							
							SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd HH:mm");
							try{
								if(!"".equals(value)){
									date = formater.parse(value);
								}
							}catch (ParseException e) {	
								e.printStackTrace();
							}
							
							rowData.put(fildName, date);
						}else{
							rowData.put(fildName, value);
						}
					}
				}
				
				if(i == 0){
					switch(excelTemplateName){
					case "maintainPower_user":
						if(!"用户名称保电级别所属组织负责人".equals(title))
							return "所导入的模板不符合用户模板规范，请重新选择！";
						break;
					case "maintainPower_personnel":
						if(!"日期姓名专业所属单位所属组织联系电话任务说明".equals(title))
							return "所导入的模板不符合人员模板规范，请重新选择！";
						break;
					case "maintainPower_car":
						if(!"日期车牌号车辆类型所属单位所属组织驾驶员联系电话任务说明".equals(title))
							return "所导入的模板不符合车辆模板规范，请重新选择！";
						break;
					case "maintainPower_stagnationPoint":
						if(!"驻点名称所属组织负责人联系电话任务说明".equals(title))
							return "所导入的模板不符合驻点模板规范，请重新选择！";
						break;
					case "maintainPower_hotel":
						if(!"宾馆名称联系人联系电话备注".equals(title))
							return "所导入的模板不符合宾馆模板规范，请重新选择！";
						break;
					case "maintainPower_hospital":
						if(!"医院名称联系人联系电话备注".equals(title))
							return "所导入的模板不符合医院模板规范，请重新选择！";
						break;
					case "maintainPower_process":
						if(!"日期单位负责人人数车辆数主要保电工作存在问题需协调事项备注".equals(title))
							return "所导入的模板不符合保电过程模板规范，请重新选择！";
						break;
					default: break;
					}
				}else if(i > 0){
					rowData.put("id", UUID.randomUUID().toString());
					rowData.put("belongTaskId", taskId);
					rowData.put("updateDateTime", updateDateTime);
					rowData.put("recordDate", recordDate);
					
					// 获取组织机构树
					HashMap<String,Object> params = new HashMap<String,Object>();
					params.put("taskId", taskId);
					List<Map<String,Object>> orgTree = getOrganizationTreeList(params);
					int orgTreeSize = orgTree.size();
					
					switch(excelTemplateName){
					case "maintainPower_user":
						boolean user_matched = true;
						
						// 对所属组织进行匹配
						for(int m=0; m<orgTreeSize; m++){
							if(rowData.get("belongOrgName").equals(orgTree.get(m).get("text"))){
								rowData.put("belongOrgID", orgTree.get(m).get("id"));
								rowData.put("belongOrgName", orgTree.get(m).get("text"));
								user_matched = true;
								break;
							}else{
								user_matched = false;
							}
						}
						if(!user_matched){
							rowData.put("belongOrgID", null);
							rowData.put("belongOrgName", null);
						}
						
						// 对保电级别进行匹配
						if(!ObjectUtils.isEmpty(rowData.get("ensureLevel"))){
							switch(rowData.get("ensureLevel").toString()){
							case "特级":
								rowData.put("ensureLevel", 1);
								break;
							case "一级":
								rowData.put("ensureLevel", 2);
								break;
							case "二级":
								rowData.put("ensureLevel", 3);
								break;
							case "三级":
								rowData.put("ensureLevel", 4);
								break;
							default :
								rowData.put("ensureLevel", null);
								user_matched = false;
								break;
							}
						}
						
						if(!user_matched){
							info = "部分记录的部分信息识别错误，记录已标红，错误字段留空，请人工核查。";
						}
						
						rowData.put(nariis.falcon.oaf.Constant.CLASS_KEY, ElectricityEnsureUser.class.getName());
						break;
					case "maintainPower_personnel":
						boolean personnel_matched = true;
						
						// 对所属组织进行匹配
						for(int m=0; m<orgTreeSize; m++){
							if(rowData.get("belongOrgName").equals(orgTree.get(m).get("text"))){
								rowData.put("belongOrg", orgTree.get(m).get("id"));
								rowData.put("belongOrgName", orgTree.get(m).get("text"));
								personnel_matched = true;
								break;
							}else{
								personnel_matched = false;
							}
						}
						if(!personnel_matched){
							rowData.put("belongOrg", null);
							rowData.put("belongOrgName", null);
						}
						
						// 对专业进行匹配
						if(!ObjectUtils.isEmpty(rowData.get("type"))){
							switch(rowData.get("type").toString()){
							case "变电":
								rowData.put("type", 1);
								break;
							case "输电":
								rowData.put("type", 2);
								break;
							case "换流":
								rowData.put("type", 3);
								break;
							case "其他":
								rowData.put("type", 4);
								break;
							default :
								rowData.put("type", null);
								personnel_matched = false;
								break;
							}
						}
						
						// 联系电话校验
//						boolean personnel_b = phoneNumberCheck(rowData.get("phoneNumber").toString());
//						if(personnel_b){
//							rowData.put("phoneNumber", rowData.get("phoneNumber").toString());
//						}else{
//							rowData.put("phoneNumber", null);
//							personnel_matched = false;
//						}
						
						if(!personnel_matched){
							info = "部分记录的部分信息识别错误，记录已标红，错误字段留空，请人工核查。";
						}
						
						rowData.put(nariis.falcon.oaf.Constant.CLASS_KEY, ElectricityEnsurePerson.class.getName());
						break;
					case "maintainPower_car":
						boolean car_matched = true;
						
						// 对所属组织进行匹配
						for(int m=0; m<orgTreeSize; m++){
							if(rowData.get("belongOrgName").equals(orgTree.get(m).get("text"))){
								rowData.put("belongOrg", orgTree.get(m).get("id"));
								rowData.put("belongOrgName", orgTree.get(m).get("text"));
								car_matched = true;
								break;
							}else{
								car_matched = false;
							}
						}
						if(!car_matched){
							rowData.put("belongOrg", null);
							rowData.put("belongOrgName", null);
						}
						
						// 车辆类型校验及汉字转编码
						Constant vehicleType = ConstantRegistry.findConstant(VehicleType.TYPE);
						if(!ObjectUtils.isEmpty(rowData.get("type"))){
							String vehicleTypeName = rowData.get("type").toString();
							Object vehicleTypeValue = vehicleType.getIntValueByField("name", vehicleTypeName);
							if(ObjectUtils.isEmpty(vehicleTypeValue)){
								car_matched = false;
							}
							rowData.put("type", vehicleTypeValue);
						}
						
						// 联系电话校验
//						boolean car_b = phoneNumberCheck(rowData.get("phoneNumber").toString());
//						if(car_b){
//							rowData.put("phoneNumber", rowData.get("phoneNumber").toString());
//						}else{
//							rowData.put("phoneNumber", null);
//							car_matched = false;
//						}
						
						if(!car_matched){
							info = "部分记录的部分信息识别错误，记录已标红，错误字段留空，请人工核查。";
						}
						
						rowData.put(nariis.falcon.oaf.Constant.CLASS_KEY, ElectricityEnsureVehicle.class.getName());
						break;
					case "maintainPower_stagnationPoint":
						boolean point_matched = true;
						
						// 对所属组织进行匹配
						for(int m=0; m<orgTreeSize; m++){
							if(rowData.get("belongOrgName").equals(orgTree.get(m).get("text"))){
								rowData.put("belongOrgID", orgTree.get(m).get("id"));
								rowData.put("belongOrgName", orgTree.get(m).get("text"));
								point_matched = true;
								break;
							}else{
								point_matched = false;
							}
						}
						if(!point_matched){
							rowData.put("belongOrgID", null);
							rowData.put("belongOrgName", null);
						}
						
						// 联系电话校验
//						boolean point_b = phoneNumberCheck(rowData.get("phoneNumber").toString());
//						if(point_b){
//							rowData.put("phoneNumber", rowData.get("phoneNumber").toString());
//						}else{
//							rowData.put("phoneNumber", null);
//							point_matched = false;
//						}
						
						if(!point_matched){
							info = "部分记录的部分信息识别错误，记录已标红，错误字段留空，请人工核查。";
						}
						
						rowData.put(nariis.falcon.oaf.Constant.CLASS_KEY, ElectricityEnsureStation.class.getName());
						break;
					case "maintainPower_hotel":
						boolean hotel_matched = true;
						
						// 联系电话校验
//						boolean hotel_b = phoneNumberCheck(rowData.get("phoneNumber").toString());
//						if(hotel_b){
//							rowData.put("phoneNumber", rowData.get("phoneNumber").toString());
//						}else{
//							rowData.put("phoneNumber", null);
//							hotel_matched = false;
//						}
						
						if(!hotel_matched){
							info = "部分记录的部分信息识别错误，记录已标红，错误字段留空，请人工核查。";
						}
						
						rowData.put(nariis.falcon.oaf.Constant.CLASS_KEY, ElectricityEnsureHotel.class.getName());
						break;
					case "maintainPower_hospital":
						boolean hospital_matched = true;
						
						// 联系电话校验
//						boolean hospital_b = phoneNumberCheck(rowData.get("phoneNumber").toString());
//						if(hospital_b){
//							rowData.put("phoneNumber", rowData.get("phoneNumber").toString());
//						}else{
//							rowData.put("phoneNumber", null);
//							hospital_matched = false;
//						}
						
						if(!hospital_matched){
							info = "部分记录的部分信息识别错误，记录已标红，错误字段留空，请人工核查。";
						}
						
						rowData.put(nariis.falcon.oaf.Constant.CLASS_KEY, ElectricityEnsureHospital.class.getName());
						break;
					case "maintainPower_process":
						boolean process_matched = true;
						
						// 对单位进行匹配
						for(int m=0; m<orgTreeSize; m++){
							if(!ObjectUtils.isEmpty(rowData.get("unit")) && rowData.get("unit").equals(orgTree.get(m).get("text"))){
								rowData.put("unit", orgTree.get(m).get("id"));
								process_matched = true;
								break;
							}else{
								process_matched = false;
							}
						}
						if(!process_matched){
							rowData.put("unit", null);
						}
						
						// 人数整数校验
						try{
							int personCount = new Integer(rowData.get("personCount").toString());
							if(personCount < 0){
								rowData.put("personCount", null);
								process_matched = false;
							}
						}catch(Exception e){
							rowData.put("personCount", null);
							process_matched = false;
						}
						
						// 车辆数整数校验
						try{
							int vehicleCount = new Integer(rowData.get("vehicleCount").toString());
							if(vehicleCount < 0){
								rowData.put("vehicleCount", null);
								process_matched = false;
							}
						}catch(Exception e){
							rowData.put("vehicleCount", null);
							process_matched = false;
						}
						
						if(!process_matched){
							info = "部分记录的部分信息识别错误，记录已标红，错误字段留空，请人工核查。";
						}
						
						rowData.put(nariis.falcon.oaf.Constant.CLASS_KEY, ElectricityEnsureProcess.class.getName());
						break;
					default: break;
					}
					
					try {
						oafCommands.insert(rowData);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				
				i++;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "导入失败！";
		}
		
		return info;
	}
	
	/**
	 * <p>描述：电话号码校验方法</p> 
	 * 包括对手机号码、带区号座机、不带区号座机号码的校验
	 * @param phoneNumber
	 * @return b
	 * @author:王万胜
	 * 2017年7月19日 下午2:06:24
	 */
	private boolean phoneNumberCheck(String phoneNumber){
		boolean b = false;
		Pattern p1 = null, p2 = null, p3 = null;
		Matcher m = null;
		p1 = Pattern.compile("^[1][3,4,5,7,8][0-9]{9}$");	// 手机号校验
		m = p1.matcher(phoneNumber);
		b = m.matches();
		if(!b){
			p2 = Pattern.compile("^[0][1-9]{2,3}-[1-9]{1}[0-9]{6,7}$");	// 带区号电话
			p3 = Pattern.compile("^[1-9]{1}[0-9]{6,7}$");	// 不带区号电话
			if(phoneNumber.length() > 8){
				m = p2.matcher(phoneNumber);
				b = m.matches();
			}else{
				m = p3.matcher(phoneNumber);
				b = m.matches();
			}
		}
		return b;
	}
	
	/**
	 * <p>描述：导出表格中数据</p> 
	 * @param request
	 * @param response
	 * @throws Exception 
	 * @author:王万胜
	 * 2017年6月7日 下午2:17:31
	 */
	@RequestMapping("/powerSupplyGuarantee/exportDataResult")
	public void exportDataResult(HttpServletRequest request,HttpServletResponse response) throws Exception {
		String excelData = request.getParameter("excelData");
		Map<String, Object> excelDataMap = JsonUtil.getJSONMap(excelData);
		String type = ""+excelDataMap.get("type");
		String params = ""+excelDataMap.get("params");
		String excelName = "" + excelDataMap.get("excelName");
		String columns = "" + excelDataMap.get("columns");
		List<Map<String,Object>> data = new ArrayList<>();
		Map<String, Object> paramsMap = JsonUtil.getJSONMap(params);
		switch(type){
			case "primary":
				data = powerSupplyGuaranteeServiceClient.queryPowerSupplyGuaranteeResult((HashMap<String, Object>)paramsMap);
				break;
			case "organization":
				data = powerSupplyGuaranteeServiceClient.queryOrganizationResult((HashMap<String, Object>)paramsMap);
				break;
			case "user":
				data = powerSupplyGuaranteeServiceClient.queryUserResult((HashMap<String, Object>)paramsMap);
				break;
			case "personnel":
				data = powerSupplyGuaranteeServiceClient.queryPersonResult((HashMap<String, Object>)paramsMap);
				break;
			case "car":
				data = powerSupplyGuaranteeServiceClient.queryCarResult((HashMap<String, Object>)paramsMap);
				break;
			case "stagnationPoint":
				data = powerSupplyGuaranteeServiceClient.queryPointResult((HashMap<String, Object>)paramsMap);
				break;
			case "hotel":
				data = powerSupplyGuaranteeServiceClient.queryHotelResult((HashMap<String, Object>)paramsMap);
				break;
			case "hospital":
				data = powerSupplyGuaranteeServiceClient.queryHospitalResult((HashMap<String, Object>)paramsMap);
				break;
			case "process":
				data = powerSupplyGuaranteeServiceClient.queryProcessResult((HashMap<String, Object>)paramsMap);
				break;
			default:
				break;
		}
		ExcelUtils.exportToExcel(data.get(0), columns, response, request, excelName);
	}
	
	/**
	 * <p>描述：获取主键id</p> 
	 * @return id
	 * @author:王万胜
	 * 2017年5月26日 上午9:13:21
	 */
	@RequestMapping("/powerSupplyGuarantee/getId")
	private Map<String, String> getId(){
		String id = ManagementUtils.getManagementID();
		Map<String, String> mapId = new HashMap<String, String>();
		mapId.put("id", id);
		return mapId;
	}
	
	/**
	 * <p>描述：通过ID获取各表格记录的更新时间</p> 
	 * @param hashmap
	 * @return data
	 * @author:王万胜
	 * 2017年6月11日 上午10:46:23
	 */
	@RequestMapping("/powerSupplyGuarantee/getUpdateTime")
	public @ResponseBody List<Map<String,Object>> queryUpdateTime(@RequestParam HashMap<String, Object> hashmap) {
		List<Map<String,Object>> data = powerSupplyGuaranteeServiceClient.queryUpdateTime(hashmap);
		return data;
	}
}
