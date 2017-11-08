package nariis.mcsas.management.fault.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;

import nariis.falcon.commons.springcloud.FalconFeignClient;
import nariis.falcon.oaf.EnableYamlScanClient;
import nariis.mcsas.commons.annotation.EnableOafCache;
import nariis.mcsas.management.common.util.EnableManagementWebConfig;



/**
 * <p>ClassName:WebFaultApp，</p>
 * <p>描述:故障跳闸web启动</p>
 * @author 肖健
 * @date 2017年3月23日下午6:52:09
 * @version 
 */
@EnableManagementWebConfig
@FalconFeignClient
@SpringBootApplication
@EnableYamlScanClient
@EnableDiscoveryClient
@EnableOafCache
@EnableFeignClients("nariis.mcsas")
public class WebFaultApp {



	/**
	 * <p>描述：故障跳闸web启动</p> 
	 * @param args 
	 * @author:肖健
	 * 2017年3月23日 下午6:52:13
	 */
	public static void main(String[] args) {
		SpringApplication.run(WebFaultApp.class, args);
	}
}
