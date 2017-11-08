package nariis.mcsas.management.regulations.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;

import nariis.falcon.commons.springcloud.FalconFeignClient;
import nariis.falcon.oaf.EnableYamlScanClient;
import nariis.mcsas.commons.annotation.EnableOafCache;
import nariis.mcsas.management.common.util.EnableManagementWebConfig;

/**
 * <p>ClassName:WebRegulationsApp，</p>
 * <p>描述:规章制度Web启动</p>
 * @author 肖健
 * @date 2017年4月22日下午2:13:59
 * @version 
 */
@FalconFeignClient
@EnableManagementWebConfig
@SpringBootApplication
@EnableYamlScanClient
@EnableDiscoveryClient
@EnableOafCache
@EnableFeignClients("nariis.mcsas")
public class WebRegulationsApp {


	/**
	 * <p>描述：规章制度Web启动</p> 
	 * @param args 
	 * @author:肖健
	 * 2017年4月22日 下午2:14:03
	 */
	public static void main(String[] args) {
		SpringApplication.run(WebRegulationsApp.class, args);
	}
}
