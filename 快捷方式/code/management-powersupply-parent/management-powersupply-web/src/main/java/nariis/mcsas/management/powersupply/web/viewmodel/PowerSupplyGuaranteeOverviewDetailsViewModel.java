package nariis.mcsas.management.powersupply.web.viewmodel;

import java.util.List;
import java.util.Map;

import nariis.mcsas.commons.model.BaseViewModel;
import nariis.mcsas.commons.model.NestRingModel;

/**
 * <p>ClassName:PowerSupplyGuaranteeOverviewDetailsViewModel，</p>
 * <p>描述:TODO</p>
 * @author 王万胜
 * @date 2017年6月22日上午11:05:39
 * @version 1.0
 */
public class PowerSupplyGuaranteeOverviewDetailsViewModel extends BaseViewModel {
	// 线路--多柱图
	private Map<String,List<?>> lineChart;
	
	// 电站--多柱图
	private Map<String,List<?>> stationChart;

	// 人员--饼图
	private NestRingModel personChart;
	
	// 车辆--饼图
	private NestRingModel carChart;
	
	
	/**
	 * <p>描述：线路--多柱图</p> 
	 * @return MultiBarModel
	 * @author:王万胜
	 * 2017年6月22日 上午11:19:27
	 */
	public Map<String,List<?>> getLineChart() {
		return lineChart;
	}

	/**
	 * <p>描述：线路--多柱图</p> 
	 * @param lineChart 
	 * @author:王万胜
	 * 2017年6月22日 上午11:19:40
	 */
	public void setLineChart(Map<String,List<?>> lineChart) {
		this.lineChart = lineChart;
	}
	
	/**
	 * <p>描述：电站--多柱图</p> 
	 * @return MultiBarModel
	 * @author:王万胜
	 * 2017年6月22日 下午1:45:43
	 */
	public Map<String,List<?>> getStationChart() {
		return stationChart;
	}

	/**
	 * <p>描述：电站--多柱图</p> 
	 * @param stationChart 
	 * @author:王万胜
	 * 2017年6月22日 下午1:46:07
	 */
	public void setStationChart(Map<String,List<?>> stationChart) {
		this.stationChart = stationChart;
	}

	/**
	 * <p>描述：人员--饼图</p> 
	 * @return NestRingModel
	 * @author:王万胜
	 * 2017年6月22日 上午11:15:44
	 */
	public NestRingModel getPersonChart() {
		return personChart;
	}

	/**
	 * <p>描述：人员--饼图</p> 
	 * @param personChart 
	 * @author:王万胜
	 * 2017年6月22日 上午11:16:20
	 */
	public void setPersonChart(NestRingModel personChart) {
		this.personChart = personChart;
	}

	/**
	 * <p>描述：车辆--饼图</p> 
	 * @return NestRingModel
	 * @author:王万胜
	 * 2017年6月22日 下午1:46:16
	 */
	public NestRingModel getCarChart() {
		return carChart;
	}

	/**
	 * <p>描述：车辆--饼图</p> 
	 * @param carChart 
	 * @author:王万胜
	 * 2017年6月22日 下午1:46:42
	 */
	public void setCarChart(NestRingModel carChart) {
		this.carChart = carChart;
	}
}
