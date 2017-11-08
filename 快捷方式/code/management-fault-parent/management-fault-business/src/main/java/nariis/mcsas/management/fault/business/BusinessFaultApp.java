package nariis.mcsas.management.fault.business;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

import nariis.falcon.oaf.EnableOafClient;
import nariis.mcsas.commons.annotation.EnableOafCache;
import nariis.mcsas.management.common.util.EnableManagementCommonConfig;



/**
 * <p>ClassName:BusinessFaultApp，</p>
 * <p>描述:故障跳闸business启动</p>
 * @author 肖健
 * @date 2017年3月23日下午6:51:06
 * @version 
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableOafClient
@EnableOafCache
@EnableManagementCommonConfig
public class BusinessFaultApp {


	/**
	 * <p>描述：故障跳闸business启动</p> 
	 * @param args 
	 * @author:肖健
	 * 2017年3月23日 下午6:51:09
	 */
	public static void main(String[] args) {
		SpringApplication.run(BusinessFaultApp.class, args);
	}
}
