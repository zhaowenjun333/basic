package nariis.mcsas.management.regulations.business;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

import nariis.falcon.oaf.EnableOafClient;
import nariis.mcsas.commons.annotation.EnableOafCache;
import nariis.mcsas.management.common.util.EnableManagementCommonConfig;


/**
 * <p>ClassName:BusinessRegulationsApp，</p>
 * <p>描述:规章制度service启动</p>
 * @author 肖健
 * @date 2017年4月22日下午2:06:27
 * @version 
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableOafClient
@EnableOafCache
@EnableManagementCommonConfig
public class BusinessRegulationsApp {


	/**
	 * <p>描述：规章制度service启动</p> 
	 * @param args 
	 * @author:肖健
	 * 2017年4月22日 下午2:06:53
	 */
	public static void main(String[] args) {
		SpringApplication.run(BusinessRegulationsApp.class, args);
	}
}
