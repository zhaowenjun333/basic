package nariis.mcsas.management.powersupply.web.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;

import cst.constants.transforming.TimeScope;
import nariis.falcon.oaf.models.constant.Constant;
import nariis.falcon.oaf.models.constant.ConstantRegistry;
import nariis.mcsas.commons.model.MultiBarModel;

/**
 * <p>
 * ClassName:TransformerUtils，
 * </p>
 * <p>
 * 描述:业务模型公共方法类
 * </p>
 * 
 * @author 杨川
 * @date 2017年3月9日下午5:16:01
 * @version
 */
public class CommonUtils {

	/**
	 * 电压等级的SQL文拼接
	 * 
	 * @param colName
	 *            电压等级字段
	 * @param voltageLevels
	 *            电压等级
	 * @return 拼接后SQL
	 */
	public static String getVoltageLevelSql(String colName, String[] voltageLevels) {
		return Arrays.asList(voltageLevels).stream().map(t -> colName + " ==  '" + t + "'")
				.collect(Collectors.joining(" || "));
	}

	/**
	 * 取得对应属性的值
	 * 
	 * @param constantName
	 *            constant类名
	 * @param itemValue
	 *            值
	 * @param name
	 *            属性名
	 * @return 对应属性的值
	 */
	public static String getNameByValue(String constantName, Object itemValue, String name) {
		Constant cst = ConstantRegistry.findConstant(constantName);
		return cst.getFieldValueByItemValue(itemValue, name);
	}

	// /**
	// *
	// * <p>描述：根据数据库表中的值获取指定constant中的字段信息</p>
	// * @param constantName Constant类名
	// * @param pmsValueCstName Constant中对应的Field名称
	// * @param pmsValue PMS库中存的字段值
	// * @param fieldName 属性名
	// * @return 字段值
	// * @author wangmg
	// * 2017年3月19日 上午10:18:34
	// */
	// public static String getNameByFiledValue(String constantName, String
	// pmsValueCstName, String pmsValue, String fieldName) {
	// Constant cst = ConstantRegistry.findConstant(constantName);
	// int itemValue =cst.getIntValueByField(pmsValueCstName, pmsValue);
	// String fieldValue = cst.getFieldValueByItemValue(itemValue, fieldName);
	// return fieldValue;
	// }

	/**
	 * 
	 * <p>
	 * 描述：group by
	 * </p>
	 * 
	 * @param isAddGroup
	 * @param isAddYear
	 * @param isAddOrganization
	 * @param isAddGrade
	 * @return 分组list
	 * @author:庄文歌 2017年3月10日 上午9:37:53
	 */
	public static List<String> getGroupByColumns(boolean isAddGroup, boolean isAddYear, boolean isAddOrganization,
			boolean isAddGrade) {
		List<String> groupColumns = new ArrayList<>();
		if (isAddGroup) {
			groupColumns.add(CommonConstant.GROUPVALUE);
		}
		if (isAddOrganization) {
			groupColumns.add(CommonConstant.ORGANIZATION);
			groupColumns.add(CommonConstant.ORGANIZATIONNAME);
		}
		if (isAddYear) {
			groupColumns.add(CommonConstant.YEAR);
		}
		if (isAddGrade) {
			groupColumns.add(CommonConstant.DEFECTGRADE);
		}
		return groupColumns;
	}

	/**
	 * 
	 * <p>
	 * 描述：order by项目做成
	 * </p>
	 * 
	 * @param isAddGroup
	 * @param isAddYear
	 * @param isAddOrganization
	 * @return 排序list
	 * @author:庄文歌 2017年3月10日 上午9:48:08
	 */
	public static List<String> getOrderByColumns(boolean isAddGroup, boolean isAddYear, boolean isAddOrganization) {
		List<String> orderColumns = new ArrayList<>();
		if (isAddGroup) {
			orderColumns.add(CommonConstant.GROUPVALUE);
		}
		if (isAddOrganization) {
			orderColumns.add(CommonConstant.ORGANIZATION);
		}
		if (isAddYear) {
			orderColumns.add(CommonConstant.YEAR);
		}
		return orderColumns;
	}

	/**
	 * sum项目作成
	 * 
	 * @return 项目组
	 */
	public static List<String> getSumColumns() {
		List<String> sumColumns = new ArrayList<>();
		sumColumns.add(CommonConstant.VALUE);
		return sumColumns;
	}

	/**
	 * 算总数
	 * 
	 * @param barModel
	 * @return number
	 */
	public static long calcTotal(MultiBarModel barModel) {
		if (barModel.getSeriesData().size() > 0) {
			List<List<String>> totalDatas = barModel.getSeriesData();
			List<String> list = totalDatas.get(0);
			long sum = 0;
			for (String count : list) {
				sum = sum + (long) Double.parseDouble(count);
			}
			return sum;
		}
		return 0;
	}

	/**
	 * 缺陷等级 危急程度求总数 第一个危急总数，第二个严重总数
	 * 
	 * @param barModel
	 * @return 数据结果集
	 * @author:庄文歌 2017年3月28日 下午3:10:26
	 */
	public static List<Long> calcTotalDefect(MultiBarModel barModel) {
		List<Long> cntList = new ArrayList<>();
		List<List<String>> totalDatas = barModel.getSeriesData();
		long criticalCnt = 0;
		long seriousnessCnt = 0;
		// 01 危急 02 严重
		for (int i = 0; i < totalDatas.size(); i++) {
			if (i % 2 == 0) {
				criticalCnt = calcDataSum(totalDatas.get(i));
			} else {
				seriousnessCnt = calcDataSum(totalDatas.get(i));
			}
		}
		cntList.add(criticalCnt);
		cntList.add(seriousnessCnt);
		return cntList;
	}

	/**
	 * 算新增数量
	 * 
	 * @param barModel
	 * @return number
	 */

	public static long calcTotalch(MultiBarModel barModel) {
		List<List<String>> totalDatas = barModel.getSeriesData();
		List<String> newTotalData = null;
		if (totalDatas.size() == 1) {
			newTotalData = totalDatas.get(0);
		} else if (totalDatas.size() > 1) {
			newTotalData = totalDatas.get(1);
		}
		if (newTotalData == null) {
			return 0;
		}

		return calcDataSum(newTotalData);
	}

	/**
	 * 电抗器算总数
	 * 
	 * @param barModel
	 * @return number
	 */
	public static long dkqcalcTotal(MultiBarModel barModel) {
		if (barModel.getSeriesData().size() > 0) {
			List<List<String>> totalDatas = barModel.getSeriesData();
			List<String> list = totalDatas.get(0);
			long sum = 0;
			if (totalDatas.size() > 1) {
				List<String> infolist = totalDatas.get(1);
				for (String count : infolist) {
					sum = sum + (long) Double.parseDouble(count);
				}
			}
			for (String count : list) {
				sum = sum + (long) Double.parseDouble(count);
			}

			return sum;
		}
		return 0;
	}

	private static long calcDataSum(List<String> numbers) {
		if (numbers == null || numbers.isEmpty()) {
			return 0;
		}

		long sum = 0;
		for (String number : numbers) {
			sum = sum + (long) Double.parseDouble(number);
		}
		return sum;
	}

	/**
	 * 查询参数处理封装
	 * 
	 * @param parentOrganization
	 *            上级区域码
	 * @param organization
	 *            区域码
	 * @param voltageLevel
	 *            电压等级（为空或'all'为所有电压等级）
	 * @param year
	 *            运行年限（近一年，三年，五年）
	 * @return 参数map
	 */
	public static Map<String, Object> createCondition(String parentOrganization, String organization,
			String voltageLevel, Object year) {
		Map<String, Object> para = new HashMap<>();
		if (StringUtils.isNotEmpty(parentOrganization)) {
			para.put(CommonConstant.PARENT_ORGANIZATION, parentOrganization);
		}
		if (StringUtils.isNotEmpty(organization) && !CommonConstant.ALL.equals(organization)) {
			para.put(CommonConstant.ORGANIZATION, organization);
		}
		if (StringUtils.isNotEmpty(voltageLevel) && !CommonConstant.ALL.equals(voltageLevel)) {
			para.put(CommonConstant.VOLTAGELEVEL, voltageLevel);
		}
		if (StringUtils.isNotEmpty(String.valueOf(year)) && !CommonConstant.ALL.equals(String.valueOf(year))) {
			para.put(CommonConstant.YEAR, year);
		}
		return para;
	}

	/**
	 * <p>
	 * 描述：获取近几年
	 * </p>
	 * 
	 * @param yearNow
	 * @param timeScope
	 * @return 近年
	 * @author:杨川 2017年3月21日 上午9:12:51
	 */
	public static int getRecentYearOld(int yearNow, int timeScope) {
		int yearOld = yearNow - 1;
		;
		if (timeScope == TimeScope._last3) {
			yearOld = yearNow - 3;
		} else if (timeScope == TimeScope._last5) {
			yearOld = yearNow - 5;
		}
		return yearOld;
	}
	
	/**
	 * <p>描述：获取登陆用户 的上级组织机构ID</p> 
	 * @param request
	 * @return parentOrg
	 * @author:单星星
	 * 2017年3月29日 下午12:45:10
	 */
	public static String getParentOrg(HttpServletRequest request){
		String parentOrg = "00000";
//		String userId = HttpSessionManager.getAttribute(request, "userId") != null ? HttpSessionManager.getAttribute(request, "userId") .toString() : "";
		return parentOrg;
	}
	
	/**
	 * 
	 * <p>描述：指定字符串转换成List</p> 
	 * @param strs
	 * @return List
	 * @author wangmg
	 * @date 2017年3月29日 下午6:56:23
	 */
	public static List<String> asList(String... strs) {
		List<String> ls = new ArrayList<>();
		for (String str : strs) {
			ls.add(str);
		}
		return ls;
	}
	
	/**
	 * <p>
	 * 描述：TODO
	 * </p>
	 * 
	 * @return 近五年
	 * @author:王跃 2017年3月29日 下午6:36:06
	 */
	public static String getFiveyear() {
		int j = Calendar.getInstance().get(Calendar.YEAR);
		StringBuffer fiveYear = new StringBuffer();
		for (int n = j - 4; n <= j; n++) {
			if (n == j) {
				fiveYear.append(String.valueOf(n));
			} else {
				fiveYear.append(String.valueOf(n) + ",");
			}

		}
		return fiveYear.toString();
	}
	
	/**
	 * <p>描述：处理生产厂家 取前五笔数据 && 剩余合计为其它 </p> 
	 * @param infoList
	 * @return fristFiveDataList
	 * @author:单星星
	 * 2017年3月30日 下午2:54:50
	 */
	public static List<Map<String, Object>> doWithForSixDataList(List<Map<String, Object>> infoList) {
		if (infoList.isEmpty() || infoList == null) {
			return infoList;
		}
		// 前五笔数据+OtherData
		List<Map<String, Object>> fristFiveDataList = new ArrayList<>(); 
		List<Map<String, Object>> otherDataList = new ArrayList<>(); 
		
		if (infoList.size() > 5) {
			fristFiveDataList = infoList.subList(0, 5);
			otherDataList = infoList.subList(5, infoList.size());
		}
		if (0 < infoList.size() && infoList.size() <=5) {
			fristFiveDataList = infoList;
		}
		// 处理其它数据
		if (! otherDataList.isEmpty() && otherDataList.size() > 0) {
			Map<String, Object> otherMap = new HashMap<String, Object>();
			Integer otherCount = 0;
			for (Map<String, Object> map : otherDataList) {
				otherCount = otherCount + Float.valueOf(map.get("value").toString()).intValue();
			}
			otherMap.put("value", otherCount);
			otherMap.put("groupValue", "999999");
			otherMap.put("groupName", "其它");
			fristFiveDataList.add(otherMap);
		}
		return fristFiveDataList;
	}
	
	/**
	 * <p>
	 * 描述：根据传入的多条件字符串拼接为fql：例如：将a,b,c转换为(column == a || column == b || column ==
	 * c)
	 * </p>
	 * 
	 * @param conditionStr
	 *            所有可选条件，逗号分隔
	 * @param column
	 *            条件对应的字段名
	 * @param logicFlag
	 *            逻辑运算符
	 * @param compareFlag
	 *            比较运算符
	 * @return string
	 * @author:贺杰 2017年4月7日 下午3:55:09
	 */
	public static String createMultiConditionStr(String conditionStr, String column, String logicFlag,
			String compareFlag) {
		if (conditionStr.contains(CommonConstant.UNWRITE)) {
			conditionStr.replace(CommonConstant.UNWRITE, "null");
		}
		StringBuilder fqlConditions = new StringBuilder(" ( " + column + compareFlag);
		fqlConditions.append(conditionStr.replaceAll(",", " " + logicFlag + " " + column + compareFlag));
		fqlConditions.append(")");
		return fqlConditions.toString();
	}
	
	/**
	 * <p>描述：根据传入的多条件字符串拼接为fql,逻辑运算符为&&，比较运算符为!=</p> 
	 * @param conditionStr 所有可选条件，逗号分隔
	 * @param column 条件对应的字段名
	 * @return string
	 * @author:贺杰
	 * 2017年4月7日 下午4:11:23
	 */
	public static String createConditionWithAnd(String conditionStr,String column){
		return createMultiConditionStr(conditionStr,column,"&&","!=");
	}
	
	/**
	 * <p>描述：根据传入的多条件字符串拼接为fql,逻辑运算符为||，比较运算符为==</p> 
	 * @param conditionStr 所有可选条件，逗号分隔
	 * @param column 条件对应的字段名
	 * @return string
	 * @author:贺杰
	 * 2017年4月7日 下午4:11:27
	 */
	public static String createConditionWithOr(String conditionStr,String column){
		return createMultiConditionStr(conditionStr,column,"||","==");
	}

}
