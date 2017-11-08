package nariis.mcsas.management.fault.business.core.client;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import nariis.falcon.commons.springcloud.RequestCache;
/**
 * <p>ClassName:FaultTripServiceClient，</p>
 * <p>描述:故障客户端接口</p>
 * @author 4621170153
 * @date 2017年3月14日下午2:17:20
 * @version 
 */
@FeignClient(name = "managementFault")
@RequestMapping("/faultTrip")
public interface FaultTripServiceClient {
	/*+showFaultTrip(Map<String, Object>)
	+updateFaultTrip(Map<String, Object>)
	+deleteFaultTrip(Map<String, Object>)
	+insertFaultTrip(Map<String, Object>)*/
	
	/**
	 * <p>描述：查询故障</p> 
	 * @param map
	 * @return List<Map<String, Object>>
	 * @author:4621170153
	 * 2017年3月14日 下午2:17:30
	 */
	@RequestCache
	@RequestMapping("/getFaultTripResults")
	public  List<Map<String, Object>> getFaultTripResults(@RequestParam HashMap<String, Object> map) ;

	
	/**
	 * <p>描述：保存</p> 
	 * @param map
	 * @return List<Map<String, Object>>
	 * @author:4621170153
	 * 2017年3月14日 下午2:17:33
	 */
	@RequestMapping("/saveFaultTrip")
	public String insertFaultTrip( @RequestParam     HashMap<String,Object> map);


	/**
	 * <p>描述：更新 </p> 
	 * @param map
	 * @return List<Map<String, Object>>
	 * @author:4621170153
	 * 2017年3月14日 下午2:17:38
	 */
	@RequestMapping("/updateFaultTrip")
	public String updateFaultTrip(@RequestParam HashMap<String,Object> map);


	/**
	 * <p>描述：删除</p> 
	 * @param map 
	 * @author:4621170153
	 * 2017年3月14日 下午6:49:51
	 * @return String
	 */
	@RequestMapping("/deleteFaultTrip")
	public String deleteFaultTrip(@RequestParam HashMap<String,Object> map);
	/**
	 * <p>描述查询状态个数</p> 
	 * @return  ArrayList<HashMap<String,Object>>
	 * @author:4621170153
	 * 2017年3月15日 下午3:08:07
	 */
	@RequestCache
	@RequestMapping("/queryStatusNum")
	public ArrayList<HashMap<String, Object>> queryStatusNum(@RequestParam HashMap<String, String> map );
	
	/********************************日常业务start****************************************/
	/**
	 * <p>描述：获取故障跳闸统计数据</p> 
	 * @param param
	 * @return 故障跳闸统计数据
	 * @author:范阳
	 * 2017年3月9日 下午2:49:31
	 */
	@RequestMapping("/queryFaultTripCount")
	public Map<String,Object> queryFaultTripCount(Map<String, Object> param);
	
	/**
	 * <p>描述：获取故障跳闸grid数据</p> 
	 * @param param
	 * @return 故障跳闸grid数据
	 * @author:范阳
	 * 2017年3月14日 下午4:00:31
	 */
	@RequestCache
	@RequestMapping("/queryFaultTripInfo")
	public List<Map<String,Object>> queryFaultTripInfo(@RequestParam Map<String, Object> param);
	
	/********************************日常业务end****************************************/
	
	/**
	 * <p>描述：获取</p> 
	 * @return 获取故障信息
	 * @author:王万胜
	 * 2017年3月14日 下午7:51:54
	 * @param params 
	 */
	@RequestMapping("/getFault")
	public List<Map<String, Object>> getFault(@RequestParam HashMap<String, Object> params);
	
	/**
	 * <p>描述：编辑故障跳闸中应急抢修相关数据</p> 
	 * @param hashmap 
	 * @author:王万胜
	 * 2017年3月14日 上午9:29:39
	 */
	@RequestMapping("/updateFault")
	public void updateFault(@RequestParam HashMap<String, Object> hashmap);


	/**
	 * <p>描述：更新状态根据id</p> 
	 * @param gridMap 
	 * @author:4621170153
	 * 2017年3月21日 上午9:49:43
	 */
	@RequestMapping("/updatestatusByid")
	public void updatestatusByid(@RequestParam HashMap<String, Object> gridMap);


	/**
	 * <p>描述： 获取电压等级</p> 
	 * @return String
	 * @author:4621170153
	 * 2017年4月18日 下午2:46:08
	 */
	@RequestCache
	@RequestMapping("/getvoltageLevel_fault")
	public String getvoltageLevel();

	/**
	 * <p>描述：获取故障类别</p> 
	 * @return String
	 * @author:4621170153
	 * 2017年4月18日 下午2:57:29
	 */
	@RequestCache
	@RequestMapping("/getFaultCategory")
	public String getFaultCategory();


	/**
	 * <p>描述：获取故障类型</p> 
	 * @return String
	 * @author:4621170153
	 * 2017年4月18日 下午3:46:33
	 */
	@RequestCache
	@RequestMapping("/getFaultType")
	public String getFaultType();


	/**
	 * <p>描述：获取故障相别</p> 
	 * @return String
	 * @author:4621170153
	 * 2017年4月18日 下午3:49:45
	 */
	@RequestCache
	@RequestMapping("/getfaultPhaseId")
	public String getfaultPhaseId();


	/**
	 * <p>描述：是否常量获取方法</p> 
	 * @return String
	 * @author:4621170153
	 * 2017年4月21日 上午10:00:35
	 */
	@RequestCache
	@RequestMapping("/getYesOrNot")
	public String getYesOrNot();


	/**
	 * <p>描述：获取重合闸动作情况</p> 
	 * @return String
	 * @author:4621170153
	 * 2017年4月21日 上午10:49:57
	 */
	@RequestCache
	@RequestMapping("/getreclosingAction")
	public String getreclosingAction();


	/**
	 * <p>描述：是否跟踪</p> 
	 * @param param 
	 * @author:4621170153
	 * 2017年4月24日 上午11:02:03
	 */
	@RequestCache
	@RequestMapping("/updatetrackOrNot")
	public void updatetrackOrNot( @RequestParam HashMap<String, String> param);
	
	/**
	 * <p>描述： 查询WarnTrackStatus</p> 
	 * @param hashmap
	 * @return List<Map<String, Object>>
	 * @author:4621170153
	 * 2017年4月24日 下午12:07:22
	 */
	@RequestCache
	@RequestMapping("/queryWarnTrackStatusResult")
	public List<Map<String, Object>> queryWarnTrackStatusResult(@RequestParam HashMap<String, String> hashmap);


	/**
	 * <p>描述：插入应急抢修</p> 
	 * @param param 
	 * @author:4621170153
	 * 2017年5月12日 下午3:39:56
	 */
	@RequestMapping("/faultinsertemergencyrepair")
	public void faultinsertemergencyrepair(@RequestParam HashMap<String, String> param);


	/**
	 * <p>描述：查询明细</p> 
	 * @param datamap
	 * @return String
	 * @author:4621170153
	 * 2017年5月13日 下午4:25:38
	 */
	
	@RequestCache
	@RequestMapping("/queryByFaultId")
	public List<Map<String, Object>>  queryByFaultId( @RequestParam HashMap<String, Object> datamap);
	 
	/**变电用
	 * 全电压等级当日发生故障情况, 条件【上级组织机构, 故障发生时间】
	 * 
	 * @param paramMap
	 *            参数map
	 * @return List<Map<String, Object>>
	 */
	@RequestMapping("/getFaultTripInfoTodayResult")
	public List<Map<String, Object>> getFaultTripInfoTodayResult(@RequestParam Map<String, Object> paramMap);
	
	/**
	 * <p>
	 * 描述：获取单位
	 * </p>
	 * 
	 * @return 数据
	 * @author:高翔2017年4月9日 下午4:50:37
	 */
	@RequestMapping("/getBelongCity")
	public List<Map<String, Object>> getBelongCity();

	/**
	 * <p>
	 * 描述：getVoltageLevel
	 * </p>
	 * 
	 * @return getVoltageLevel
	 * @author:高翔 2017年7月7日 下午4:00:40
	 */
	@RequestMapping("/getVoltageLevel")
	public List<Map<String, Object>> getVoltageLevel();

	/**
	 * <p>
	 * 描述：getBelongSubstation
	 * </p>
	 * 
	 * @return getBelongSubstation
	 * @author:高翔 2017年7月7日 下午4:00:40
	 */
	@RequestMapping("/getBelongSubstation")
	public List<Map<String, Object>> getBelongSubstation();

	/**
	 * <p>
	 * 描述：getfaultType
	 * </p>
	 * 
	 * @return getfaultType
	 * @author:高翔 2017年7月7日 下午4:00:40
	 */
	@RequestMapping("/getfaultType")
	public List<Map<String, Object>> getfaultType();
	
	
	/**输电用用
	 * @param param
	 * @return List<Map<String, Object>>
	 */
	@RequestMapping("/queryGzxxlByLineId")
	public List<Map<String, Object>> queryGzxxlByLineId(@RequestParam Map<String,Object> param);
}
