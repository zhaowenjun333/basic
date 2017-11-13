package nariis.mcsas.management.powersupply.business;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

import nariis.falcon.oaf.EnableOafClient;
import nariis.mcsas.commons.annotation.EnableMcsasCommon;
import nariis.mcsas.commons.annotation.EnableOafCache;
import nariis.mcsas.management.common.util.EnableCommonExcelServiceConfig;
import nariis.mcsas.management.common.util.EnableManagementCommonConfig;

/**
 * <p>ClassName:BusinessPowerSupplyApp，</p>
 * <p>描述:供电保障service启动</p>
 * @author 肖健
 * @date 2017年3月25日下午4:59:06
 * @version 
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableOafClient
@EnableOafCache
@EnableManagementCommonConfig
@EnableCommonExcelServiceConfig
@EnableMcsasCommon
public class BusinessPowerSupplyApp {

	/**
	 * <p>描述：供电保障service启动</p> 
	 * @param args 
	 * @author:肖健
	 * 2017年3月25日 下午4:59:09
	 */
	public static void main(String[] args) {
		SpringApplication.run(BusinessPowerSupplyApp.class, args);
	}
}
