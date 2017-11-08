package nariis.mcsas.management.powersupply.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;

import nariis.falcon.commons.springcloud.FalconFeignClient;
import nariis.falcon.oaf.EnableYamlScanClient;
import nariis.mcsas.commons.annotation.EnableOafCache;
import nariis.mcsas.management.common.util.EnableCommonExcelServiceConfig;
import nariis.mcsas.management.common.util.EnableManagementWebConfig;

/**
 * <p>ClassName:WebPowerSupplyApp，</p>
 * <p>描述:供电保障web启动</p>
 * @author 肖健
 * @date 2017年3月25日下午4:59:54
 * @version 
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableOafCache
@EnableManagementWebConfig
@EnableCommonExcelServiceConfig
@EnableYamlScanClient
@FalconFeignClient
@EnableFeignClients("nariis.mcsas")
public class WebPowerSupplyApp {

	/**
	 * <p>描述：供电保障web启动</p> 
	 * @param args 
	 * @author:肖健
	 * 2017年3月25日 下午4:59:57
	 */
	public static void main(String[] args) {
		SpringApplication.run(WebPowerSupplyApp.class, args);
	}
}
