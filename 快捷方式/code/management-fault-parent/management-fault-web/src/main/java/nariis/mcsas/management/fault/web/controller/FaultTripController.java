package nariis.mcsas.management.fault.web.controller;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import cst.constants.common.DeviceType;
import nariis.falcon.oaf.models.constant.Constant;
import nariis.falcon.oaf.models.constant.ConstantRegistry;
import nariis.mcsas.commons.model.BaseViewModel;
import nariis.mcsas.commons.utils.ExcelUtils;
import nariis.mcsas.management.common.model.LoginUserInfo;
import nariis.mcsas.management.common.util.JsonUtil;
import nariis.mcsas.management.common.util.LoginUserInfoUtils;
import nariis.mcsas.management.common.util.ManagementUtils;
import nariis.mcsas.management.fault.business.core.client.FaultTripServiceClient;
import nariis.mcsas.management.fault.web.viewmodel.StatusNumviewModel;

/**
 * <p>ClassName:FaultTripController，</p>
 * <p>描述:故障异常controller层</p>
 * @author 4621170153
 * @date 2017年3月14日下午2:18:29
 * @version 
 */
@RestController
public class FaultTripController {
	@Autowired
	FaultTripServiceClient client;
	
	@Autowired  
	LoginUserInfoUtils loginUserInfoUtils;
	 
	/**
	 * <p>描述：  通道异常明细下拉控件 </p> 
	 * @param hashmap
	 * @return HashMap<String,String>
	 * @author:4621170153
	 * 2017年5月22日 上午11:10:53
	 */
	@RequestMapping("/faultTrip_channelException/initAllDrop")
	public HashMap<String,String> initAll(@RequestParam HashMap<String,String> hashmap){
		HashMap<String,String> hm = new HashMap<String,String>();
		hm.put("id", ManagementUtils.getManagementID());
		hm.put("successful", "true");
		return hm;
	}
	/**
	 * <p>描述：导出</p> 
	 * @param request
	 * @param response
	 * @author:赵云
	 * 2017年5月16日 下午5:04:23
	 * @throws Exception 
	 */
	@RequestMapping("/fault/exportDataResult")
	public void exportDataResult(HttpServletRequest request,HttpServletResponse response) throws Exception {
		String excelData = request.getParameter("excelData");
		Map<String, Object> excelDataMap = JsonUtil.getJSONMap(excelData);
		String type = ""+excelDataMap.get("type");
		String excelName = "" + excelDataMap.get("excelName");
		String columns = "" + excelDataMap.get("columns");
		String ids = "" + excelDataMap.get("ids");
		List<Map<String,Object>> data = new ArrayList<>();
		if(!"[]".equals(ids)){
			/*List<Map<String, Object>> excelDatas = JsonUtil.getJSONListMap(datas);
			data.add(rst);*/
//			Map<String, Object> idsMap = JsonUtil.getJSONMap(ids);
			Map<String, Object> rst = new HashMap<String, Object>();
			rst.put("ids",ids);
			data = client.getFaultTripResults((HashMap<String, Object>) rst);
		}else{
			Map<String, Object> typeMap = JsonUtil.getJSONMap(type);
			data = client.getFaultTripResults((HashMap<String, Object>)typeMap);
		}
		ExcelUtils.exportToExcel(data.get(0), columns, response, request, excelName);
	}
	
	/**
	 * <p>描述：故障异常删除操作</p> 
	 * @param param
	 * @return  BaseViewModel
	 * @throws ParseException 
	 * @author:4621170153
	 * 2017年3月14日 下午6:43:42
	 *   
	 *    
	 */
	 
	@RequestMapping("/fault/delete")
	public  BaseViewModel deleteDataGrid(@RequestParam HashMap<String, String> param) throws ParseException {
		BaseViewModel bvm =new BaseViewModel();
		
		String ids= param.get("ids");
		String updateDateTimes =param.get("updateDateTimes");
		
		String[] idArray =ids.split(",");
		String[] updateDateTimeArray =updateDateTimes.split(",");
		HashMap<String,Object> gridMap = new HashMap<String,Object>();
		String  rs=null;
		int num=0;
		for(int i=0;i<idArray.length;i++){
			// 电网风险
			gridMap.put("id", idArray[i]);
			gridMap.put("updateDateTime", updateDateTimeArray[i]);
			 
			   rs	=client.deleteFaultTrip(gridMap); 
			   if("删除成功".equals(rs)){
				   num++;
			   } 
		}
		if(idArray.length ==num){
			
			bvm.setSuccessful(true);
		}else{
			
			bvm.setSuccessful(false);
		}
		return bvm; 
	}
	
	/**
	 * <p>描述：应急抢修插入操作</p> 
	 * @param param
	 * @return BaseViewModel
	 * @throws ParseException 
	 * @author:4621170153
	 * 2017年5月12日 下午3:33:12
	 */
	@RequestMapping("/emergencyrepair/faultinsert")
	public  BaseViewModel faultinsert(@RequestParam HashMap<String, String> param) throws ParseException {
		client.faultinsertemergencyrepair(param); 
		BaseViewModel bvm =new BaseViewModel();
		bvm.setSuccessful(true);
		return bvm;
	}
	/**
	 * <p>描述： 更新状态</p> 
	 * @param param
	 * @return BaseViewModel
	 * @throws ParseException 
	 * @author:4621170153
	 * 2017年3月21日 上午9:40:12
	 */
	@RequestMapping("/fault/updatestatus")
	public  BaseViewModel updatestatus(@RequestParam HashMap<String, Object> param) throws ParseException {
		
		String id=(String)param.get("id");
		String [] ids = id.split(",");
		String status=(String)param.get("status");
		String [] statuses = status.split(",");
		HashMap<String,Object> gridMap = new HashMap<String,Object>();
		for (int  i  =0 ; i <  ids.length;i++) {
			gridMap.put("id", ids[i]);
			switch(statuses[i]){
			case  "1":
//				gridMap.put("trackDate", new Date());
				gridMap.put("confirmStatus", 2);break;
			case  "2":
				gridMap.put("confirmStatus", 3);break;
//				gridMap.put("closedLoopDate", new Date());break;
			case  "3":
				gridMap.put("confirmStatus", 4);break;
//				gridMap.put("filingDate", new Date());break;
				
			}
			client.updatestatusByid(gridMap); 
		}
			
			
			BaseViewModel bvm =new BaseViewModel();
			bvm.setSuccessful(true);
			return bvm;
	}
	
	
	
	
	/**
	 * <p>描述：故障异常保存</p> 
	 * @param param  
	 * @return BaseViewModel
	 * @param request 
	 * @throws ParseException 
	 * @author:4621170153
	 * 2017年3月14日 下午2:18:34
	 *  
	 */
	@RequestMapping("/fault/save")
	public  BaseViewModel saveDataGrid(@RequestParam HashMap<String, Object> param,HttpServletRequest request) throws ParseException {
		LoginUserInfo user = null ;
		try {
			user = loginUserInfoUtils.getLoginUserInfo(request);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(param!= null && user!= null){
			param.put("organizationId", user.getOrganizationId());
		}
		client.insertFaultTrip(  param);
		BaseViewModel bvm = new BaseViewModel();
		bvm.setSuccessful(true);
		return bvm;
	}
	
	
	
	
	/**
	 * <p>描述：更新跟踪信息</p> 
	 * @param param
	 * @return BaseViewModel
	 * @throws ParseException 
	 * @author:4621170153
	 * 2017年3月14日 下午2:18:52
	 */
	@RequestMapping("/fault/updatetrackOrNot")
	public  BaseViewModel updatetrackOrNot(@RequestParam HashMap<String, String> param) throws ParseException {
		BaseViewModel bvm =new BaseViewModel();
		bvm.setSuccessful(true);
		client.updatetrackOrNot(  param);
		return bvm;
	}
	/**
	 * <p>描述： 查询跟踪状态</p> 
	 * @param param
	 * @return List<Map<String, Object>>
	 * @throws ParseException 
	 * @author:4621170153
	 * 2017年3月14日 下午2:18:52
	 */
	@RequestMapping("/fault/queryWarnTrackStatusResult")
	public  Map<String, Object>  queryWarnTrackStatusResult(@RequestParam HashMap<String, String> param) throws ParseException {
		List<Map<String, Object>> rs =client.queryWarnTrackStatusResult(  param);
		return rs.get(0);   
	}
	/**
	 * <p>描述： 更新故障异常</p> 
	 * @param param
	 * @return BaseViewModel
	 * @throws ParseException 
	 * @author:4621170153
	 * 2017年3月14日 下午2:18:52
	 */
	@RequestMapping("/fault/update")
	public  BaseViewModel updateDataGrid(@RequestParam HashMap<String, String> param) throws ParseException {
		Constant DeviceTypeCst = ConstantRegistry.findConstant(DeviceType.TYPE);
		BaseViewModel bvm =new BaseViewModel();
		HashMap<String,Object> map = new HashMap<String,Object>();
		int   faultCategory     =  Integer.parseInt( param.get("faultCategory"));
		int   faultPhaseId     =   Integer.parseInt(param.get("faultPhaseId"));
		String   faultTime		=(String) param.get("faultTime" );
		map.put("faultPhaseId", faultPhaseId  );
		map.put("faultCategory", faultCategory  );
		map.put("faultTime", faultTime );
		
		String faultType="";
		int   voltageLevel     =0; 
		String   organization    = "";
		String   organizationId    = "";
		String   stationOrLineName="";
		String   stationOrLineId="";
		String   deviceName       ="";
		String   deviceType      ="";
		String   deviceTypeName      ="";
		String   deviceId      ="";
		String   weather      =    "";
		String   powerOutReason      =    "";
		String   id            =   "";
		String   blockCase  ="";
		//交流字段
		String threeSpanOrNot=	""; 
		String blockOrNot=	""; 
		 int reclosingAction = 0;		
		String sendSuccessOrNot="";		
		String repairEndOrNot="";		
		String repairOrNot=	"";		
		String stationFaultOrNot=	"";		
		String faultLocator="";			
		String recoveryDate=null;		 
		String powerOutTime=null;		 
		String faultReason=""	;		
		String protection=""	;		 
		String managementDetail=	"";
		String faultDetail=	""	;
				                          
		if(param.get("faultType")!=null&&  ! "".equals(param.get("faultType"))){
			
			    faultType         =  (String)  param.get("faultType") ;
			    map.put("faultType", faultType  );
		}
		if(param.get("voltageLevel")!=null  ){
			if(   "".equals(param.get("voltageLevel"))){
				map.put("voltageLevel",  null );
			}else{
				
			voltageLevel     =   Integer.parseInt(param.get("voltageLevel"));
			map.put("voltageLevel", voltageLevel );
			}
			 
		}
		if(param.get("organization")!=null&&  ! "".equals(param.get("organization"))){
			organization      = (String) param.get("organization");
			map.put("organization", organization  );
		}
		if(param.get("organizationId")!=null&&  ! "".equals(param.get("organizationId"))){
			organizationId      = (String) param.get("organizationId");
			map.put("organizationId", organizationId  );
		}
		if(param.get("stationOrLineName")!=null&&  ! "".equals(param.get("stationOrLineName"))){
			stationOrLineName      = (String) param.get("stationOrLineName");
			map.put("stationOrLineName", stationOrLineName);
		}
		if(param.get("attachment")!=null&&  ! "".equals(param.get("attachment"))){
			String attachment = (String) param.get("attachment");
			map.put("attachment", attachment);
		}
		if(param.get("attachmentpath")!=null&&  ! "".equals(param.get("attachmentpath"))){
			String attachmentpath = (String) param.get("attachmentpath");
			map.put("attachmentPath", attachmentpath);
		}
		if(param.get("stationOrLineId")!=null&&  ! "".equals(param.get("stationOrLineId"))){
			stationOrLineId      = (String) param.get("stationOrLineId");
			map.put("stationOrLineId", stationOrLineId);
		}
		if(param.get("deviceName")!=null&&  ! "".equals(param.get("deviceName"))){
			deviceName        = (String) param.get("deviceName");
			map.put("deviceName", deviceName  );
		}
		 
		if (param.get("deviceType") != null && !"".equals(param.get("deviceType"))) {
			int  deviceTypeValue =  Integer.valueOf((String) param.get("deviceType") )  ;
			deviceTypeName = DeviceTypeCst.getFieldValueByItemValue(deviceTypeValue, "name");
			map.put("deviceTypeName", deviceTypeName  );
			map.put("deviceType", (String) param.get("deviceType") );
		}
		if(param.get("deviceId")!=null&&  ! "".equals(param.get("deviceId"))){
			deviceId       = (String) param.get("deviceId");
			map.put("deviceId", deviceId  );
		}
		if(param.get("blockCase")!=null&&  ! "".equals(param.get("blockCase"))){
			blockCase        = (String) param.get("blockCase");
			map.put("blockCase", blockCase  );
			
		}
		if(param.get("weather")!=null    ){
			
			weather           = (String) param.get("weather");
			map.put("weather", weather  );
		}
		if(param.get("powerOutReason")!=null    ){
			
			powerOutReason           = (String) param.get("powerOutReason");
			map.put("powerOutReason", powerOutReason  );
		}
		if(param.get("id")!=null&&  ! "".equals(param.get("id"))){
			
			id                 =(String) param.get("id" );
			map.put("id", id  );
		}
		
//只传交流字段                   
		if(param.get("threeSpanOrNot") 	!=null&&  ! "".equals(param.get("threeSpanOrNot"))){
			
			threeSpanOrNot                 =(String) param.get("threeSpanOrNot" );
			map.put("threeSpanOrNot", threeSpanOrNot  );
		}
		if(param.get("stationFaultOrNot") 	!=null&&  ! "".equals(param.get("stationFaultOrNot"))){
			
			stationFaultOrNot                 =(String) param.get("stationFaultOrNot" );
			map.put("stationFaultOrNot", stationFaultOrNot  );
		}
		if(param.get("blockOrNot") 	!=null&&  ! "".equals(param.get("blockOrNot"))){
			blockOrNot                 =(String) param.get("blockOrNot" );
			map.put("blockOrNot", blockOrNot  );
		}
		
		if(param.get("reclosingAction") 	!=null&&  ! "".equals(param.get("reclosingAction"))){
			
			reclosingAction    =Integer.parseInt(  param.get("reclosingAction" ));
			map.put("reclosingAction", reclosingAction  );
		}
		
		if(param.get("sendSuccessOrNot") 	!=null&&  ! "".equals(param.get("sendSuccessOrNot"))){
			
			sendSuccessOrNot                 =(String) param.get("sendSuccessOrNot" );
			map.put("sendSuccessOrNot", sendSuccessOrNot  );
		}
		if(param.get("repairOrNot") 	!=null&&  ! "".equals(param.get("repairOrNot"))){
			
			repairOrNot                 =(String) param.get("repairOrNot" );
			map.put("repairOrNot", repairOrNot  );
		}
		if(param.get("repairEndOrNot") 	!=null&&  ! "".equals(param.get("repairEndOrNot"))){
			
			repairEndOrNot                 =(String) param.get("repairEndOrNot" );
			map.put("repairEndOrNot", repairEndOrNot  );
		}
		if(param.get("faultLocator") 	!=null&&  ! "".equals(param.get("faultLocator"))){
			
			faultLocator                 =(String) param.get("faultLocator" );
			map.put("faultLocator", faultLocator  );
		}
		if(param.get("recoveryDate") 	!=null&&  ! "".equals(param.get("recoveryDate"))){
//			recoveryDate= new SimpleDateFormat("yyyy-MM-dd HH:mm").parse((String) param.get("recoveryDate")) ;//只能在service层转换，在controller会插不进去
			recoveryDate                 =(String) param.get("recoveryDate" );
			map.put("recoveryDate", recoveryDate  );
		}
		if(param.get("powerOutTime") 	!=null&&  ! "".equals(param.get("powerOutTime"))){
			powerOutTime                 =(String) param.get("powerOutTime" );
			map.put("powerOutTime", powerOutTime  );
		}
		if(param.get("faultReason") 	!=null&&  ! "".equals(param.get("faultReason"))){
			
			faultReason                 =(String) param.get("faultReason" );
			map.put("faultReason", faultReason  );
		}
		if(param.get("protection") 	!=null&&  ! "".equals(param.get("protection"))){
			
			protection                 =(String) param.get("protection" );
			map.put("protection", protection  );
		}
		if(param.get("managementDetail") 	!=null&&  ! "".equals(param.get("managementDetail"))){
			managementDetail                 =(String) param.get("managementDetail" );
			map.put("managementDetail", managementDetail  );
		}
		if(param.get("faultDetail") 	!=null&&  ! "".equals(param.get("faultDetail"))){
			faultDetail                 =(String) param.get("faultDetail" );
			map.put("faultDetail", faultDetail  );
		}
		if(param.get("status")!=null){
			
			map.put("confirmStatus",  param.get("status"));
		}
		if(param.get("updateDateTime")!=null){
			
			map.put("updateDateTime",  param.get("updateDateTime"));
		}
		String  rs=client.updateFaultTrip(  map);
		if("更新成功".equals(rs)){
			
			bvm.setSuccessful(true);
		}else{
			
			bvm.setSuccessful(false);
		}
		
		return bvm; 
	}
	
	
	
	
	
	/**
	 * <p>描述：查询各个状态的个数</p> 
	 * @return ArrayListList<Map<String, Object>>
	 * @author:4621170153
	 * 2017年3月14日 下午2:19:00
	 */
	@RequestMapping("/fault/getstatusnum")
	public  StatusNumviewModel getstatusnum(@RequestParam HashMap<String, String> map  ) {
		StatusNumviewModel snm = new StatusNumviewModel();
		ArrayList<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
		list=client.queryStatusNum(map);
		snm.setList(list);
		snm.setSuccessful("true");
		return snm;
//		return list;
	}

	
	
	
	/**
	 * <p>描述： 根据id查询</p> 
	 * @param datamap
	 * @return List<Map<String, Object>>  
	 * @author:4621170153
	 * 2017年5月13日 下午4:23:52
	 */
	@RequestMapping("/emergencyrepair/queryByFaultId")
	public   List<Map<String, Object>>   queryByFaultId( @RequestParam HashMap<String, Object> datamap ) {
		List<Map<String, Object>> rs=client.queryByFaultId(datamap);
		 
		return rs;
	
	}
	/**
	 * <p>描述：查询故障异常</p> 
	 * @param datamap
	 * @return List<Map<String, Object>> 
	 * @author:4621170153
	 * 2017年3月14日 下午2:19:14
	 */
	@RequestMapping("/fault/showFaultTrip")
	public  @ResponseBody Map<String,Object>  showFaultTripResults( @RequestParam HashMap<String, Object> datamap ) {
		 
			List<Map<String, Object>> rs=client.getFaultTripResults(datamap);
			 
			 Map<String, Object>  r=( Map<String, Object>  )rs.get(0) ;
			return  r;
	}
	
	/**
	 * <p>描述：获取电压等级 </p> 
	 * @return String
	 * @author:4621170153
	 * 2017年4月18日 下午2:45:19
	 */
	@RequestMapping("/faultTrip_edit_new/getvoltageLevel")
	public String  getvoltageLevel() {

		String voltageLevel = ManagementUtils.getVoltageLevel();
		return voltageLevel;
	}
	
	/**
	 * <p>描述：获取是否常量</p> 
	 * @return String
	 * @author:4621170153
	 * 2017年4月18日 下午2:45:19
	 */
	@RequestMapping("/faultTrip_edit_new/getsendSuccessOrNot")
	public String  getsendSuccessOrNot() {
		String data = client.getYesOrNot();
		return data;
	}
	
	/**
	 * <p>描述：是否抢修</p> 
	 * @return String
	 * @author:4621170153
	 * 2017年4月18日 下午2:45:19
	 */
	@RequestMapping("/faultTrip_edit_new/getrepairOrNot")
	public String  getrepairOrNot() {
		String data = client.getYesOrNot();
		return data;
	}
	
	/**
	 * <p>描述：是否三跨区域</p> 
	 * @return String
	 * @author:4621170153
	 * 2017年4月18日 下午2:45:19
	 */
	@RequestMapping("/faultTrip_edit_new/getthreeSpanOrNot")
	public String  getthreeSpanOrNot() {
		String data = client.getYesOrNot();
		return data;
	}
	
	/**
	 * <p>描述：抢修是否结束</p> 
	 * @return String
	 * @author:4621170153
	 * 2017年4月18日 下午2:45:19
	 */
	@RequestMapping("/faultTrip_edit_new/getrepairEndOrNot")
	public String  getrepairEndOrNot() {
		String data = client.getYesOrNot();
		return data;
	}
	
	/**
	 * <p>描述：是否站内故障</p> 
	 * @return String
	 * @author:4621170153
	 * 2017年4月18日 下午2:45:19
	 */
	@RequestMapping("/faultTrip_edit_new/getstationFaultOrNot")
	public String  getstationFaultOrNot() {
		String data = client.getYesOrNot();
		return data;
	}
	
	/**
	 * <p>描述：是否站内故障</p> 
	 * @return String
	 * @author:4621170153
	 * 2017年4月18日 下午2:45:19
	 */
	@RequestMapping("/faultTrip_edit_new/getblockOrNot")
	public String  getblockOrNot() {
		String data = client.getYesOrNot();
		return data;
	}
	
	/**
	 * <p>描述：获取reclosingAction</p> 
	 * @return String
	 * @author:4621170153
	 * 2017年4月18日 下午2:45:19
	 */
	@RequestMapping("/faultTrip_edit_new/getreclosingAction")
	public String  getreclosingAction() {
		String data = client.getreclosingAction();
		return data;
	}
	
	
	/**
	 * <p>描述：获取故障类别</p> 
	 * @return String
	 * @author:4621170153
	 * 2017年4月18日 下午2:57:01
	 */
	@RequestMapping("/faultTrip_edit_new/getFaultCategory")
	public String  getFaultCategory() {
		String data = client.getFaultCategory();
		return data;
	}
	
	/**
	 * <p>描述： 故障类型</p> 
	 * @return String
	 * @author:4621170153
	 * 2017年4月18日 下午2:57:01
	 */
	@RequestMapping("/faultTrip_edit_new/getFaultType")
	public String  getFaultType() {
		String data = client.getFaultType();
		return data;
	}
		
	/**
	 * <p>描述：故障相别</p> 
	 * @return String
	 * @author:4621170153
	 * 2017年4月18日 下午2:57:01
	 */
	@RequestMapping("/faultTrip_edit_new/getfaultPhaseId")
	public String  getfaultPhaseId() {
		String data = client.getfaultPhaseId();
		return data;
	}

}
