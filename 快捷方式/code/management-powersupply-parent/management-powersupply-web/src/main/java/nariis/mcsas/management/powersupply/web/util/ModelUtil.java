package nariis.mcsas.management.powersupply.web.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import nariis.falcon.oaf.models.constant.Constant;
import nariis.falcon.oaf.models.constant.ConstantRegistry;
import nariis.mcsas.commons.model.Item;
import nariis.mcsas.commons.model.MultiBarModel;
import nariis.mcsas.commons.model.NestRingModel;

/**
 * <p>
 * ClassName:ModelUtil，
 * </p>
 * <p>
 * 描述:设备数据模型封装
 * </p>
 * 
 * @author 庄文歌
 * @date 2017年3月9日下午5:19:10
 * @version
 */
public class ModelUtil {


	/**
	 * <p>
	 * 描述：将统计数据拼装为多柱状图模型，适用于折线图
	 * </p>
	 * 
	 * @param datas
	 *            统计数据，需要预先按照想要显示的x轴顺序排序
	 * @param legendKey
	 *            图例编码字段名
	 * @param xAxisKey
	 *            X轴编码字段名
	 * @param valueKey
	 *            统计值字段名
	 * @param legendNameKey
	 *            图例名
	 * @param xAxisNameKey
	 *            X轴名
	 * @return MultiBarModel 多图例柱状图的view模型 value为整形
	 * @author:贺杰 2017年3月11日 下午1:48:23
	 */
	public static MultiBarModel makeMultiBarModel(List<Map<String, Object>> datas, String legendKey, String xAxisKey,
			String legendNameKey, String xAxisNameKey, String valueKey) {
		List<Item> legendData = new ArrayList<>();
		List<Item> xAxisData = new ArrayList<>();
		
		// 获取所有图例和x轴元素
		datas.forEach(t -> {
			if (legendKey != null && !containItem(legendData, t.get(legendKey).toString())) {
				Constant lengendConstant = ConstantRegistry.findConstant(legendNameKey);
				legendData
						.add(new Item(t.get(legendKey).toString(),
								lengendConstant == null ? String.valueOf(t.get(legendNameKey))
										: lengendConstant.getFieldValueByItemValue((int) t.get(legendKey), "name"),
								null));
			}
			if (!containItem(xAxisData, t.get(xAxisKey).toString())) {
				Constant xAxisConstant = ConstantRegistry.findConstant(xAxisNameKey);
				xAxisData
						.add(new Item(t.get(xAxisKey).toString(), xAxisConstant == null ?String.valueOf(t.get(xAxisNameKey))
								: xAxisConstant.getFieldValueByItemValue((int) t.get(xAxisKey), "name"), null));
			}
		});
		// 添加统计数值，无数据的位置置“0”
		List<List<String>> seriesData = initSeriesData(legendData, xAxisData);
		datas.forEach(t -> {
			int i = (legendKey == null ? 0 : indexOf(legendData, t.get(legendKey).toString()));
			int j = indexOf(xAxisData, t.get(xAxisKey).toString());
			if (i != -1 && j != -1) {
				seriesData.get(i).set(j, t.get(valueKey).toString());
			}
		});
		MultiBarModel model = new MultiBarModel();
		model.setLegendData(legendData);
		model.setxAxisData(xAxisData);
		model.setSeriesData(seriesData);
		return model;

	}

	
	private static List<List<String>> initSeriesData(List<Item> legendData, List<Item> xAxisData) {
		List<List<String>> seriesData = new ArrayList<>();
		for (int i = 0; i < (legendData.size() == 0 ? 1 : legendData.size()); i++) {
			ArrayList<String> initData = new ArrayList<>(xAxisData.size());
			for (int n = 0; n < xAxisData.size(); n++) {
				initData.add("0");
			}
			seriesData.add(initData);
		}
		return seriesData;
	}

	/**
	 * <p>描述：数据中不带lengend字段的多柱图viewmodel拼装</p> 
	 * @param params
	 * @return MultiBarModel
	 * @author:贺杰
	 * 2017年3月23日 下午3:56:52
	 */
	public static MultiBarModel makeNolegendMultiBarModel(NonLegendParamBean params){
		List<Item> legendData = new ArrayList<>();
		List<Item> xAxisData = new ArrayList<>();
		String xAxisKey = params.getxAxisKey();
		String xAxisNameKey = params.getxAxisNameKey();
		String valueKey = params.getValueKey();
		Constant lengendConstant = ConstantRegistry.findConstant(params.getConstantName());
		Constant xAxisConstant = ConstantRegistry.findConstant(xAxisNameKey);
		String legendFirst = lengendConstant.getFieldValueByItemValue(1, "name");
		String legendSecond = lengendConstant.getFieldValueByItemValue(2, "name");
		legendData.add(new Item("1",
				legendFirst,null));
		legendData.add(new Item("2",
				legendSecond,null));
		// 获取x轴元素
		mergeLegendData(params.getSecondDatas(), xAxisData, xAxisKey, xAxisNameKey, xAxisConstant);
		mergeLegendData(params.getFirstDatas(), xAxisData, xAxisKey, xAxisNameKey, xAxisConstant);
		// 添加统计数值，无数据的位置置“0”
		List<List<String>> seriesData = initSeriesData(legendData, xAxisData);
		addDataToSeries(params.getFirstDatas(), xAxisData, xAxisKey, valueKey, seriesData, 0);
		addDataToSeries(params.getSecondDatas(), xAxisData, xAxisKey, valueKey, seriesData, 1);
		MultiBarModel model = new MultiBarModel();
		model.setLegendData(legendData);
		model.setxAxisData(xAxisData);
		model.setSeriesData(seriesData);
		return model;
	}

	private static void mergeLegendData(List<Map<String, Object>> datas, List<Item> xAxisData, String xAxisKey,
			String xAxisNameKey, Constant xAxisConstant) {
		datas.forEach(t -> {
			if (!containItem(xAxisData, String.valueOf(t.get(xAxisKey)))) {
				xAxisData
						.add(new Item(t.get(xAxisKey).toString(), xAxisConstant == null ? String.valueOf(t.get(xAxisNameKey))
								: xAxisConstant.getFieldValueByItemValue((int) t.get(xAxisKey), "name"), null));
			}
		});
	}

	private static void addDataToSeries(List<Map<String, Object>> datas, List<Item> xAxisData, String xAxisKey,
			String valueKey, List<List<String>> seriesData, int index) {
		datas.forEach(t -> {
			int j = indexOf(xAxisData, String.valueOf(t.get(xAxisKey)));
			if (index != -1 && j != -1) {
				seriesData.get(index).set(j, String.valueOf(t.get(valueKey)));
			}
		});
	}
	/**
	 * <p>
	 * 描述：将统计数据拼装为多环图图模型，适用于饼图
	 * </p>
	 * 
	 * @param ringDatas
	 *            环图数据
	 * @param pieDatas
	 *            嵌套饼图数据
	 * @param legendKey
	 *            图例编码字段名
	 * @param valueKey
	 *            统计值字段名
	 * @param legendNameKey
	 *            图例名
	 * @return MultiRingModel 多环图模型对象
	 * @author:贺杰 2017年3月14日 上午10:45:36
	 * 
	 */
	public static NestRingModel makeNestRingModel(List<Map<String, Object>> ringDatas,
			List<Map<String, Object>> pieDatas, String legendKey, String legendNameKey, String valueKey) {
		List<Item> legendData = new ArrayList<>();
		List<List<Item>> seriesData = new ArrayList<>();
		List<Item> ringSeriesData = new ArrayList<>();
		List<Item> pieSeriesData = new ArrayList<>();
		Constant lengendConstant = ConstantRegistry.findConstant(legendNameKey);
		// 获取环图所有图例和数据
		if (ringDatas != null) {
			ringDatas.forEach(t -> {
				String name = lengendConstant == null ? String.valueOf(t.get(legendNameKey))
						: lengendConstant.getFieldValueByItemValue((int) t.get(legendKey), "name");
				legendData.add(new Item(t.get(legendKey).toString(), name, null));
				ringSeriesData.add(new Item(null, name, t.get(valueKey).toString()));
			});
		}

		// 获取饼图所有图例和数据
		if (pieDatas != null) {
			pieDatas.forEach(t -> {
				String name = lengendConstant == null ? String.valueOf(t.get(legendNameKey))
						: lengendConstant.getFieldValueByItemValue((int) t.get(legendKey), "name");
				legendData.add(new Item(t.get(legendKey).toString(), name, null));
				pieSeriesData.add(new Item(null, name, t.get(valueKey).toString()));
			});
		}
		if (ringSeriesData.size() > 0) {
			seriesData.add(ringSeriesData);
		}
		if (pieSeriesData.size() > 0) {
			seriesData.add(pieSeriesData);
		}
		NestRingModel model = new NestRingModel();
		model.setLegendData(legendData);
		model.setSeriesData(seriesData);
		return model;
	}

	/**
	 * <p>
	 * 描述：做成电压等级结果
	 * </p>
	 * 
	 * @param voltages
	 * @return 电压等级
	 * @author:杨川 2017年3月14日 上午9:54:57
	 */
	public static List<Item> getVoltageLevel(Map<Integer, String> voltages) {
		List<Item> voltageItems = new ArrayList<>();
		Item item = new Item();
		item.setCode(CommonConstant.VOLTAGE_CODE);
		item.setName(CommonConstant.VOLTAGE_NAME);
		voltageItems.add(item);

		voltages.forEach((k, v) -> {
			Item voltage = new Item();
			voltage.setCode(String.valueOf(k));
			voltage.setName(v);
			voltageItems.add(voltage);
		});
		return voltageItems;
	}

	private static boolean containItem(List<Item> items, String code) {
		for (Item item : items) {
			if (item.getCode().equalsIgnoreCase(code)) {
				return true;
			}
		}
		return false;
	}

	private static int indexOf(List<Item> items, String code) {
		for (int i = 0; i < items.size(); i++) {
			if (items.get(i).getCode().equalsIgnoreCase(code)) {
				return i;
			}
		}
		return -1;
	}

	/**
	 * 
	 * <p>
	 * 描述：根据code添加groupName
	 * </p>
	 * 
	 * @param infoList
	 * @param structureType
	 * @param structureTypeName
	 * @return 添加groupName后的新list
	 * @author:庄文歌 2017年3月19日 下午1:23:56
	 */
	public static List<Map<String, Object>> getNewListForGroupName(List<Map<String, Object>> infoList,
			String structureType, String structureTypeName) {
		infoList.forEach(t -> {
			String groupName = CommonUtils.getNameByValue(structureType, t.get(CommonConstant.GROUPVALUE),
					structureTypeName);
			t.put(CommonConstant.GROUPNAME, groupName);
		});
		return infoList;
	}
}
