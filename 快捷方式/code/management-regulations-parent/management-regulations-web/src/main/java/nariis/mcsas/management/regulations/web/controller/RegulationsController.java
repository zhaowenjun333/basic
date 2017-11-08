package nariis.mcsas.management.regulations.web.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cst.constants.management.ConcernedYear;
import cst.constants.management.IssuedUnit;
import cst.constants.management.RegulationsSpecialty;
import cst.constants.management.RegulationsType;
import nariis.falcon.oaf.models.constant.Constant;
import nariis.falcon.oaf.models.constant.ConstantItem;
import nariis.falcon.oaf.models.constant.ConstantRegistry;
import nariis.mcsas.management.common.model.LoginUserInfo;
import nariis.mcsas.management.common.util.LoginUserInfoUtils;
import nariis.mcsas.management.common.util.ManagementUtils;
import nariis.mcsas.management.regulations.business.core.client.RegulationsServiceClient;
/**
 * <p>ClassName:RegulationsController，</p>
 * <p>描述:规章制度控制层TODO</p>
 * @author 4621170153
 * @date 2017年5月16日上午11:05:17
 * @version 1.0
 */
@RestController
public class RegulationsController {
	
	@Autowired
	RegulationsServiceClient client;
	
	@Autowired  
	LoginUserInfoUtils loginUserInfoUtils;
	
	private static final Logger logger = LogManager.getLogger(RegulationsController.class);
	/**
	 * <p>描述： ss </p> 
	 * @param hashmap
	 * @return   HashMap<String,String> 
	 * @author:4621170153
	 * 2017年5月28日 下午4:13:49
	 */
	@RequestMapping("/regulations/getgridRiskID")
	public HashMap<String,String> getgridRiskID(@RequestParam HashMap<String,String> hashmap){
		HashMap<String,String> hm = new HashMap<String,String>();
		hm.put("id", ManagementUtils.getManagementID());
		hm.put("successful", "true");
		return hm;
	}
	/**
	 * <p>描述：规章制度删除TODO</p> 
	 * @param datamap
	 * @return  Map<String, Object>
	 * @author:4621170153
	 * 2017年5月28日 下午4:13:49
	 */
	@RequestMapping("/regulations/delete")
	public  Map<String, Object>  delete( @RequestParam HashMap<String, Object> datamap) {
	     String  rs	=client.delete(datamap); 
		 Map<String, Object> map =new HashMap<String ,Object>();
		 if("success".equals(rs)){
			 map.put("successful", "true");
		 }else{
			 map.put("successful", "false");
		 }
		 return map ;
	}
	 
		
	/**
	 * <p>描述：规章制度更新TODO</p> 
	 * @param datamap
	 * @param request
	 * @return Map<String, Object>
	 * @author:4621170153
	 * 2017年5月28日 下午3:00:30
	 */
	@RequestMapping("/regulations/update")
	public  Map<String, Object>  update( @RequestParam HashMap<String, Object> datamap,HttpServletRequest request ) {
		String  rs=client.update(datamap);
		 Map<String, Object> map =new HashMap<String ,Object>();
		 if("success".equals(rs)){
			 map.put("successful", "true");
		 }else{
			 map.put("successful", "false");
		 }
		 return map ;
	}
	/**
	 * <p>描述：规章制度保存TODO</p> 
	 * @param datamap
	 * @param request
	 * @return  Map<String, Object> 
	 * @author:4621170153
	 * 2017年5月27日 下午4:06:25
	 */
	@RequestMapping("/regulations/save")
	public  Map<String, Object>  save( @RequestParam HashMap<String, Object> datamap,HttpServletRequest request ) {
		LoginUserInfo user = null;
		try {
			user = loginUserInfoUtils.getLoginUserInfo(request);
		} catch (Exception e) {
			logger.error("电网风险数据插入异常",e);
		}
		if(datamap!= null && user != null){
			datamap.put("organizationId", user.getOrganizationId());
		}
		 String  rs=client.save(datamap);
		 Map<String, Object> map =new HashMap<String ,Object>();
		 if("yes".equals(rs)){
			 map.put("successful", "true");
		 }else if("same".equals(rs)){
			 map.put("successful", "false");
			 map.put("same", "true");
		 }else{
			 map.put("successful", "false");
		 }
		 return map ;
	}
	
	/**
	 * <p>描述规章制度查询TODO</p> 
	 * @param datamap
	 * @return Map<String, Object>
	 * @author:4621170153
	 * 2017年5月16日 上午11:13:17
	 */
	@RequestMapping("/regulations/showregulations")
	public  Map<String, Object>  showregulations( @RequestParam HashMap<String, Object> datamap ) {
		List<Map<String, Object>> rs=client.showregulations(datamap);
		return ( Map<String, Object>  ) rs.get(0);
	}
	
	/**
	 * <p>描述：规章制度下拉框TODO</p> 
	 * @param datamap
	 * @return  Map<String, Object>
	 * @author:4621170153
	 * 2017年5月17日 上午8:50:43
	 */
	@RequestMapping("/regulations/initAllDrop")
	public   Map<String, String> initAllDrop( @RequestParam HashMap<String, Object> datamap ) {
		 Map<String, String> map =new HashMap<String ,String>();
		 map.put("issuedUnit", issuedUnit());
		 map.put("type", type());
		 map.put("specialty", specialty());
		 map.put("year", year());
		 map.put("successful", "true");
		 return map ;	
	}
	private String year() {
		int i = 1;
		String gridSpecitalTopicYear = "";
		Constant cst = ConstantRegistry.findConstant( ConcernedYear.TYPE);
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
			gridSpecitalTopicYear += item;
		}
		gridSpecitalTopicYear = "["+gridSpecitalTopicYear+"]";
		return gridSpecitalTopicYear;

	}


	//获取类型
	private String type(){
		int i = 1;
		String type = "";
		Constant cst = ConstantRegistry.findConstant(RegulationsSpecialty.TYPE );
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
			type += item;
		}
		type = "["+type+"]";
		return type;
	}
	//获取specialty 专业
	private String specialty(){
		int i = 1;
		String specialty = "";
		Constant cst = ConstantRegistry.findConstant( RegulationsType.TYPE);
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
			specialty += item;
		}
		specialty = "["+specialty+"]";
		return specialty;
	}
	//获取下发单位
	private String issuedUnit(){
			int i = 1;
			String issuedUnit = "";
			Constant cst = ConstantRegistry.findConstant( IssuedUnit.TYPE);
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
				issuedUnit += item;
			}
			issuedUnit = "["+issuedUnit+"]";
			return issuedUnit;
	}
}