package nariis.mcsas.management.regulations.business.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.core.aggregation.DateOperators.Year;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cst.constants.management.IssuedUnit;
import cst.constants.management.RegulationsSpecialty;
import cst.constants.management.RegulationsType;
import nariis.falcon.oaf.OafCommands;
import nariis.falcon.oaf.OafException;
import nariis.falcon.oaf.OafQuery;
import nariis.falcon.oaf.models.constant.Constant;
import nariis.falcon.oaf.models.constant.ConstantRegistry;
import sgcim30.assets.management.Regulations;

/**
 * <p>ClassName:RegulationsService，</p>
 * <p>描述:规章制度增删改查业务TODO</p>
 * @author 4621170153
 * @date 2017年5月16日上午11:16:18
 * @version 1.0
 */
@RestController
@RequestMapping("/regulationsServiceClient")
@SpringBootApplication
public class RegulationsService {
	@Autowired
	OafQuery oafQuery;
	
	@Autowired
	OafCommands oafCommands;
	
	private static final Logger logger = LogManager.getLogger(RegulationsService.class);
	/**
	 * <p>描述：规章制度删除业务TODO</p> 
	 * @param datamap
	 * @return String
	 * @author:4621170153
	 * 2017年5月28日 下午4:17:34
	 */
	@RequestMapping("/delete")
	public String delete( @RequestParam     HashMap<String, Object> datamap){
		String ids= (String) datamap.get("ids");
		String[] idArray =ids.split(",");
		HashMap<String,Object> gridMap = new HashMap<String,Object>();
		int num=0;
		for(int i=0;i<idArray.length;i++){
			gridMap.put("id", idArray[i]);
			gridMap.put(nariis.falcon.oaf.Constant.CLASS_KEY,Regulations.class.getName());
			oafCommands.delete(gridMap);
			num++;
		}
		if(idArray.length ==num){
			return "success";
		}else{
			return "fault";
		}
	}
	
	
	/**
	 * <p>描述：规章制度更新业务TODO</p> 
	 * @param datamap
	 * @return String
	 * @author:4621170153
	 * 2017年5月28日 下午3:07:27
	 */
	@RequestMapping("/update")
	public String update( @RequestParam     HashMap<String, Object> datamap){
		HashMap<String,Object> map = new HashMap<String,Object>();
		int issuedUnit=0,specialty=0,type=0;
		if(!StringUtils.isEmpty(  datamap.get("issuedUnit").toString())&&  ! "".equals(datamap.get("issuedUnit"))){
			issuedUnit =Integer.parseInt( (String) datamap.get("issuedUnit"));
		    map.put("issuedUnit", issuedUnit  );
		}
		if(!StringUtils.isEmpty(  datamap.get("specialty").toString())&&  ! "".equals(datamap.get("specialty"))){
			specialty =Integer.parseInt( (String) datamap.get("specialty"));
			map.put("specialty", specialty  );
		}
		if(!StringUtils.isEmpty(  datamap.get("type").toString())&&  ! "".equals(datamap.get("type"))){
			type =Integer.parseInt( (String) datamap.get("type"));
			map.put("type", type  );
		}
		String fileNumber ,name=null,id=null;
		if(!StringUtils.isEmpty(  datamap.get("fileNumber").toString())&&  ! "".equals(datamap.get("fileNumber"))){
			fileNumber      = (String) datamap.get("fileNumber");
			map.put("fileNumber", fileNumber  );
		}
		if(!StringUtils.isEmpty(  datamap.get("name").toString()) &&  ! "".equals(datamap.get("name"))){
			name      = (String) datamap.get("name");
			map.put("name", name  );
		}
		if(! "".equals(datamap.get("id"))&&  !StringUtils.isEmpty(  datamap.get("id").toString())  ){
			id      = (String) datamap.get("id");
			map.put("id", id  );
		}
		if(  ""!=datamap.get("year")&&!StringUtils.isEmpty(  datamap.get("year").toString())){
			map.put("year",  (String) datamap.get("year"));
		}
		if(  ""!=datamap.get("attachment")&&!StringUtils.isEmpty(  datamap.get("attachment").toString())){
			map.put("attachment",  (String) datamap.get("attachment"));
		}
		map.put(nariis.falcon.oaf.Constant.CLASS_KEY,Regulations.class.getName());
		map.put("updateDateTime",new Date()  );
		try {
			oafCommands.update(map);
		} catch (Exception e) {
			logger.error("规章制度更新业务",e);
			return "更新失败";
		}
		return "更新成功";
	}
	
	
	/**
	 * <p>描述：规章制度新增业务TODO</p> 
	 * @param datamap
	 * @return String
	 * @author:4621170153
	 * 2017年5月27日 下午4:25:57
	 */
	@RequestMapping("/save")
	public String save( @RequestParam     HashMap<String, Object> datamap){
		
		boolean flag = conparesame(datamap) ;//
		if(flag ==true ){
			return "same";
		}
		 
		Regulations regulations =new Regulations();
		int issuedUnit=0;
		if( !StringUtils.isEmpty(  datamap.get("issuedUnit").toString())&& !"".equals(datamap.get("issuedUnit"))){
			issuedUnit         =  Integer.parseInt((String)  datamap.get("issuedUnit"));
			    regulations.setIssuedUnit(issuedUnit); 
		}
		int type;
		if(!StringUtils.isEmpty(  datamap.get("type").toString())&& !"".equals(datamap.get("type"))){
			type    =  Integer.parseInt((String)  datamap.get("type"));
			    regulations.setType(type); 
		}
		int specialty;
		if(!StringUtils.isEmpty(  datamap.get("specialty").toString())&& !"".equals(datamap.get("specialty"))){
			specialty    =  Integer.parseInt((String)  datamap.get("specialty"));
			    regulations.setSpecialty(specialty);  
		}
		String year=null;
		 
		year =  (String) datamap.get("year") ;
		regulations.setYear(year);
		 
		
		String fileNumber;
		if(!StringUtils.isEmpty(  datamap.get("fileNumber").toString())&& !"".equals(datamap.get("fileNumber"))){
			fileNumber= (String) datamap.get("fileNumber");
			regulations.setFileNumber(fileNumber);
		}
		String name;
		if(!StringUtils.isEmpty(  datamap.get("name").toString())&& !"".equals(datamap.get("name"))){
			name= (String) datamap.get("name");
			regulations.setName(name);
		}
		String organizationId;
		if(!StringUtils.isEmpty(  datamap.get("organizationId").toString())&& !"".equals(datamap.get("organizationId"))){
			organizationId= (String) datamap.get("organizationId");
			regulations.setOrganizationId(organizationId);
		}
		String attachment;
		if(!StringUtils.isEmpty(  datamap.get("attachment").toString())&& !"".equals(datamap.get("attachment"))){
			attachment= (String) datamap.get("attachment");
			regulations.setAttachment(attachment);
		}
		if( datamap.get("id")!= null && !"".equals(datamap.get("id") )){
			
			regulations.setId(  datamap.get("id").toString());
		}else{
			regulations.setId(UUID.randomUUID().toString());
		}
		regulations.setUpdateDateTime(new Date());
		try {
			oafCommands.insert(regulations );
		} catch (Exception e) {
			logger.error("电网风险数据插入异常",e);
			return "no";
		}
		return "yes";
	}
	
	
//	判断是否相同的规则：只要有不是一模一样，包括空值=空值，就可以保存
	public  boolean conparesame(HashMap<String, Object> datamap) {
		String getallregulation ="from t in  sgcim30.assets.management.Regulations  select t" ;
		List<Map<String, Object>> rslt = null;
		boolean flag  = false ;
		try {
			rslt =oafQuery.executeForList(getallregulation);
		} catch (OafException e1) {
			e1.printStackTrace();
		}
		//判断一下，是否和库里的数据重复，
		if (rslt!= null){
			for (Map<String, Object> map : rslt) {
				
				//写的思路：附件后后续加上去的，一开始只有两个非必填字段， 后续增加后，总的条件可以分为  附件空，然后复用之前的代码（三种情况）
				if( !StringUtils.isEmpty(  datamap.get("attachment").toString())){//附件有数值
					if( !StringUtils.isEmpty(  datamap.get("specialty").toString()) &&  !StringUtils.isEmpty(  datamap.get("fileNumber").toString())){
						 if( 
								 datamap.get("issuedUnit").toString().equals(String.valueOf(map.get("issuedUnit") ) ) &&
								 datamap.get("type").toString().equals(String.valueOf( map.get("type")) ) && 
								 datamap.get("year").equals(map.get("year"))   &&
								 datamap.get("specialty").toString().equals( String.valueOf(map.get("specialty")) )   &&
								 datamap.get("fileNumber").equals(map.get("fileNumber"))  &&
								 datamap.get("name").equals(map.get("name")) &&
								 datamap.get("attachment").equals(map.get("attachment")) 
								 ){
							 flag = true;
							 System.out.println("记录相同不可以保存");
							 break;
						 }
					} 
					if(  StringUtils.isEmpty(  datamap.get("specialty").toString()) &&  !StringUtils.isEmpty(  datamap.get("fileNumber").toString())){
						 if( 
								  "0".equals(String.valueOf( map.get("specialty")) )  &&
								 datamap.get("type").toString().equals(String.valueOf( map.get("type")) ) && 
								 datamap.get("year").equals(map.get("year"))   &&
								 datamap.get("fileNumber").equals(map.get("fileNumber"))  &&
								 datamap.get("issuedUnit").toString().equals(String.valueOf(map.get("issuedUnit") ) ) &&
								 datamap.get("name").equals(map.get("name"))&&
								 datamap.get("attachment").equals(map.get("attachment"))  
								 ){
							 flag = true;
							 System.out.println("记录相同不可以保存");
							 break;
						 }
					}
					if( !StringUtils.isEmpty(  datamap.get("specialty").toString()) &&  StringUtils.isEmpty(  datamap.get("fileNumber").toString())){
						if( 
								   map.get("fileNumber") == null   &&
								 datamap.get("type").toString().equals(String.valueOf( map.get("type")) ) && 
								 datamap.get("year").equals(map.get("year"))   &&
								 datamap.get("specialty").equals(String.valueOf(map.get("specialty")))  &&
								 datamap.get("issuedUnit").toString().equals(String.valueOf(map.get("issuedUnit") ) ) &&
								 datamap.get("name").equals(map.get("name"))&&
								 datamap.get("attachment").equals(map.get("attachment"))  
								 ){
							 flag = true;
							 System.out.println("记录相同不可以保存");
							 break;
						 }
					}
				}else{
					if( !StringUtils.isEmpty(  datamap.get("specialty").toString()) &&  !StringUtils.isEmpty(  datamap.get("fileNumber").toString())){
						 if( 	
								   map.get("attachment") == null   &&
								 datamap.get("issuedUnit").toString().equals(String.valueOf(map.get("issuedUnit") ) ) &&
								 datamap.get("type").toString().equals(String.valueOf( map.get("type")) ) && 
								 datamap.get("year").equals(map.get("year"))   &&
								 datamap.get("specialty").toString().equals( String.valueOf(map.get("specialty")) )   &&
								 datamap.get("fileNumber").equals(map.get("fileNumber"))  &&
								 datamap.get("name").equals(map.get("name"))  
								 ){
							 flag = true;
							 System.out.println("记录相同不可以保存");
							 break;
						 }
					} 
					if(  StringUtils.isEmpty(  datamap.get("specialty").toString()) &&  !StringUtils.isEmpty(  datamap.get("fileNumber").toString())){
						 if( 
								  "0".equals(String.valueOf( map.get("specialty")) )  &&
								   map.get("attachment") == null   &&
								 datamap.get("type").toString().equals(String.valueOf( map.get("type")) ) && 
								 datamap.get("year").equals(map.get("year"))   &&
								 datamap.get("fileNumber").equals(map.get("fileNumber"))  &&
								 datamap.get("issuedUnit").toString().equals(String.valueOf(map.get("issuedUnit") ) ) &&
								 datamap.get("name").equals(map.get("name"))  
								 ){
							 flag = true;
							 System.out.println("记录相同不可以保存");
							 break;
						 }
					}
					if( !StringUtils.isEmpty(  datamap.get("specialty").toString()) &&  StringUtils.isEmpty(  datamap.get("fileNumber").toString())){
						if( 
								   map.get("fileNumber") == null   &&
								   map.get("attachment") == null   &&
								 datamap.get("type").toString().equals(String.valueOf( map.get("type")) ) && 
								 datamap.get("year").equals(map.get("year"))   &&
								 datamap.get("specialty").equals(String.valueOf(map.get("specialty")))  &&
								 datamap.get("issuedUnit").toString().equals(String.valueOf(map.get("issuedUnit") ) ) &&
								 datamap.get("name").equals(map.get("name")) 
								 ){
							 flag = true;
							 System.out.println("记录相同不可以保存");
							 break;
						 }
					}
				}
			}
		}else{
			flag =true;
		}
		return flag;
	}


	/**
	 * <p>描述：规章制度查询业务TODO</p> 
	 * @param map
	 * @return List<Map<String, Object>>
	 * @author:4621170153
	 * 2017年5月16日 上午11:24:40
	 */
	@RequestMapping("/showregulations")
	public List<Map<String, Object>> showregulations(@RequestParam HashMap<String, Object> map){
		
		int pageSize = Integer.parseInt((String)map.get("pageSize"));//框架自带pageSize
		int pageIndex = Integer.parseInt((String)map.get("pageIndex"));
		int skipCount = pageIndex*pageSize;
		String fql = null;
		String fqlcount= null;
		String issuedUnit = (String) map.get("issuedUnit");  
		String specialty = (String) map.get("specialty");  
		String type = (String) map.get("type");  
		String fileNumber = (String) map.get("fileNumber"); 
		String name = (String) map.get("name");  
		String year = "".equals((String)map.get("year"))?"null":(String) map.get("year");  //设置为null字符串下面判断就是依据这个
		String mh = (String) map.get("mh"); // 模糊
		Map<String, Object> param = new LinkedHashMap<String, Object>();
		String selectFql0 = " from t1 in  ( "  ;
		StringBuilder fromFql =new StringBuilder(" from t in sgcim30.assets.management.Regulations") ;
		StringBuilder whereFql =new StringBuilder("") ;
		if(!StringUtils.isEmpty( issuedUnit ) && !"null".equals(issuedUnit) && !"".equals(issuedUnit)){
			String[] issuedUnitarr = issuedUnit.split(",");
			if(issuedUnitarr.length ==1){
				fromFql.append( " && t.issuedUnit ==:issuedUnit ");
				param.put("issuedUnit", Integer.parseInt(issuedUnit));
			}else{
				fromFql.append( " && (  ");int i = 0 ;
				for (String string : issuedUnitarr) {
					i++;
					if(i ==1){
						fromFql.append( " t.issuedUnit ==:issuedUnit" +i);
						param.put("issuedUnit"+i, Integer.parseInt(string));
					}else{
						fromFql.append( " ||  t.issuedUnit ==:issuedUnit"+i);
						param.put("issuedUnit"+i, Integer.parseInt(string));
					}
				}
				fromFql.append( " )  ");
			}
		}
		if(!StringUtils.isEmpty( specialty ) && !"null".equals(specialty) && !"".equals(specialty)){
			String[] specialtytarr = specialty.split(",");
			if(specialtytarr.length ==1){
				fromFql.append( " && t.specialty ==:specialty ");
				param.put("specialty", Integer.parseInt(specialty));
			}else{
				fromFql.append( " && (  ");int i = 0 ;
				for (String string : specialtytarr) {
					i++;
					if(i ==1){
						fromFql.append( "  t.specialty ==:specialty"+i);
						param.put("specialty"+i, Integer.parseInt(string));
					}else{
						fromFql.append( " || t.specialty ==:specialty"+i);
						param.put("specialty"+i , Integer.parseInt(string));
					}
				}
				fromFql.append( " )  ");
			}
		}
		if(!StringUtils.isEmpty( type )  && !"null".equals(type) && !"".equals(type)){
			String[] typetarr = type.split(",");
			if(typetarr.length ==1){
				fromFql.append( " && t.type ==:type ");
				param.put("type", Integer.parseInt(type));
			}else{
				fromFql.append( " && (  ");int i = 0 ;
				for (String string : typetarr) {
					i++;
					if(i ==1){
						fromFql.append( "  t.type ==:type"+i);
						param.put("type"+i, Integer.parseInt(string));
					}else{
						fromFql.append( " || t.type ==:type"+i);
						param.put("type"+i, Integer.parseInt(string));
					}
				}
				fromFql.append( " )  ");
			}
		}
		if(!StringUtils.isEmpty( year )  && !"null".equals(year) && !"".equals(year)){
			String[] yeararr = year.split(",");
			if(yeararr.length ==1){
				fromFql.append( " && t.year ==:year ");
				param.put("year",  year);
			}else{
				fromFql.append( " && (  ");int i = 0 ;
				for (String string : yeararr) {
					i++;
					if(i ==1){
						fromFql.append( "  t.year ==:year"+i);
						param.put("year"+i, string );
					}else{
						fromFql.append( " || t.year ==:year"+i);
						param.put("year"+i,  string );
					}
				}
				fromFql.append( " )  ");
			}
		}
		if(!StringUtils.isEmpty( fileNumber )  && !"null".equals(fileNumber) && !"".equals(fileNumber)){
			fromFql.append( " && t.fileNumber ==:fileNumber ");
			param.put("fileNumber", fileNumber );
		}
		if(!StringUtils.isEmpty( name ) && !"null".equals(name) && !"".equals(name)){
			fromFql.append( " && t.name ==:name ");
			param.put("name", name);
		}
		if(!"".equals(mh) && !"null".equals(mh) && !StringUtils.isEmpty( mh )){
			fromFql.append(" && (t.type.contains('"+mh+"') || t.issuedUnit.contains('"+mh+"') || "
					+ "t.fileNumber.contains('"+mh+"') || t.specialty.contains('"+mh+"') || "
					+"t.name.contains('"+mh+"') || t.year.contains( '"+mh+"') )" );
		}
		String selectFql = "  orderby t.issuedUnit  ,t.year descending ,t.updateDateTime select t  ";
		  selectFql+= "  )  select t1  then skip :skipCount then take :pageSize  ";
		
		
		
		String sqlCount =  "  select sqlCount = t.count() " ;
		fql = selectFql0 + fromFql.toString() + whereFql.toString() /*+ orderbyFql */+ selectFql;
		fql=fql.replaceFirst("&&", "where" );
		List<Map<String, Object>> rslt = null;
		fqlcount= fromFql.toString() + whereFql.toString() + sqlCount ;
		fqlcount=fqlcount.replaceFirst("&&", "where");
		Integer rsltCount = 0;
		try {
			rsltCount = (Integer)oafQuery.executeScalar(fqlcount , param);	
			param.put("skipCount",skipCount);
			param.put("pageSize",pageSize);
			rslt = oafQuery.executeForList(fql, param);
		} catch (OafException e) {
			logger.error("规章制度查询业务",e);
		}
//		 类型
		Constant   regulationsTypeCst = ConstantRegistry.findConstant(RegulationsSpecialty.TYPE);
//		下发单位
		Constant  issuedUnitCst = ConstantRegistry.findConstant( IssuedUnit.TYPE);
//		专业
		Constant  regulationsSpecialtyCst = ConstantRegistry.findConstant(RegulationsType.TYPE );
		
		for(Map<String,Object> maptemp : rslt){ 
			// 下发单位
			if(maptemp.get("issuedUnit")!=null){
				int issuedUnitValue =((Integer)maptemp.get("issuedUnit")).intValue();
				String issuedUnitName = "";
				issuedUnitName = issuedUnitCst.getFieldValueByItemValue(issuedUnitValue, IssuedUnit.NAME);
				maptemp.put("issuedUnit", issuedUnitName);
			}
			//类型
			if(maptemp.get("type")!=null){
				int typeValue =((Integer)maptemp.get("type")).intValue();
				String typeName = "";
				typeName = regulationsTypeCst.getFieldValueByItemValue(typeValue,RegulationsType.NAME);
				maptemp.put("type", typeName);
			}
			//专业
			if(maptemp.get("specialty")!=null){
				int specialtyValue =((Integer)maptemp.get("specialty")).intValue();
				String specialtyName = "";
				specialtyName = regulationsSpecialtyCst.getFieldValueByItemValue(specialtyValue, RegulationsSpecialty.NAME);
				maptemp.put("specialty", specialtyName);
			}
			//年份 
			if( maptemp.get("year")!=null){
//				String year1 = new SimpleDateFormat("yyyy").format(maptemp.get("year"));
				maptemp.put("year", (String)maptemp.get("year"));
			}
		}
		List<Map<String,Object>> list = new ArrayList<>();
		Map<String,Object> mapList = new HashMap<>();
		mapList.put("data", rslt);
		mapList.put("total",rsltCount);
		list.add(mapList);
		return list;
	}


}
