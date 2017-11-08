package nariis.mcsas.management.powersupply.web.util;

import java.util.List;
import java.util.Map;

/**
 * <p>ClassName:NonLegendParamBean，</p>
 * <p>描述:data中不包含legend数据，拼装viewmodel时所需的参数实体</p>
 * @author 贺杰
 * @date 2017年3月23日下午3:32:06
 * @version 1.0
 */
public class NonLegendParamBean {
	
	/**
	 * 第一组数据
	 */
	private List<Map<String, Object>> firstDatas;
	/**
	 * 第二组数据
	 */
	private List<Map<String, Object>> secondDatas;
	/**
	 * x轴编码的key，便于从datas中获取对应数据
	 */
	private String xAxisKey;
	/**
	 * x轴名称key，便于从datas中获取对应数据
	 */
	private String xAxisNameKey;
	/**
	 * 统计数字的key，便于从datas中获取对应数据
	 */
	private String valueKey;
	
	/**
	 * 获取legend数据的constant类名
	 */
	private String constantName;
	
	/**
	 *<p>构造器</p>
	 * @param firstDatas
	 * @param secondDatas
	 * @param xAxisKey
	 * @param xAxisNameKey
	 * @param valueKey
	 * @param constantName
	 * 2017年3月24日
	 */
	public NonLegendParamBean(List<Map<String, Object>> firstDatas, List<Map<String, Object>> secondDatas,
			String xAxisKey, String xAxisNameKey, String valueKey, String constantName) {
		this.firstDatas = firstDatas;
		this.secondDatas = secondDatas;
		this.xAxisKey = xAxisKey;
		this.xAxisNameKey = xAxisNameKey;
		this.valueKey = valueKey;
		this.constantName = constantName;
	}

	/**
	 * @return the firstDatas
	 */
	public List<Map<String, Object>> getFirstDatas() {
		return firstDatas;
	}
	/**
	 * @param firstDatas the firstDatas to set
	 */
	public void setFirstDatas(List<Map<String, Object>> firstDatas) {
		this.firstDatas = firstDatas;
	}
	/**
	 * @return the secondDatas
	 */
	public List<Map<String, Object>> getSecondDatas() {
		return secondDatas;
	}
	/**
	 * @param secondDatas the secondDatas to set
	 */
	public void setSecondDatas(List<Map<String, Object>> secondDatas) {
		this.secondDatas = secondDatas;
	}
	/**
	 * @return the xAxisKey
	 */
	public String getxAxisKey() {
		return xAxisKey;
	}
	/**
	 * @param xAxisKey the xAxisKey to set
	 */
	public void setxAxisKey(String xAxisKey) {
		this.xAxisKey = xAxisKey;
	}
	/**
	 * @return the xAxisNameKey
	 */
	public String getxAxisNameKey() {
		return xAxisNameKey;
	}
	/**
	 * @param xAxisNameKey the xAxisNameKey to set
	 */
	public void setxAxisNameKey(String xAxisNameKey) {
		this.xAxisNameKey = xAxisNameKey;
	}
	/**
	 * @return the valueKey
	 */
	public String getValueKey() {
		return valueKey;
	}
	/**
	 * @param valueKey the valueKey to set
	 */
	public void setValueKey(String valueKey) {
		this.valueKey = valueKey;
	}
	/**
	 * @return the constantName
	 */
	public String getConstantName() {
		return constantName;
	}
	/**
	 * @param constantName the constantName to set
	 */
	public void setConstantName(String constantName) {
		this.constantName = constantName;
	}

}
