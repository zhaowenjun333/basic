package nariis.mcsas.management.regulations.business.core.client;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * <p>ClassName:RegulationsServiceClient，</p>
 * <p>描述:TODO</p>
 * @author 4621170153
 * @date 2017年5月16日上午11:10:18
 * @version 1.0
 */
@FeignClient(name = "managementRegulations")
@RequestMapping("/regulationsServiceClient")
public interface RegulationsServiceClient {

	/**
	 * <p>描述：TODO</p> 
	 * @param datamap
	 * @return List<Map<String, Object>>
	 * @author:4621170153
	 * 2017年5月16日 上午11:13:36
	 */
	@RequestMapping("/showregulations")
	public List<Map<String, Object>> showregulations(@RequestParam HashMap<String, Object> datamap);
	
	/**
	 * <p>描述：TODO</p> 
	 * @param datamap
	 * @return List<Map<String, Object>>
	 * @author:4621170153
	 * 2017年5月17日 上午8:51:28
	 */
	@RequestMapping("/initAllDrop")
	public List<Map<String, Object>> initAllDrop(@RequestParam HashMap<String, Object> datamap);

	
	/**
	 * <p>描述：TODO</p> 
	 * @param datamap
	 * @return String
	 * @author:4621170153
	 * 2017年5月27日 下午4:14:00
	 */
	@RequestMapping("/save")
	public String save( @RequestParam     HashMap<String, Object> datamap);
	/**
	 * <p>描述：TODO</p> 
	 * @param datamap
	 * @return String
	 * @author:4621170153
	 * 2017年5月28日 下午3:04:00
	 */
	@RequestMapping("/update")
	public String update( @RequestParam HashMap<String, Object> datamap);

	/**
	 * <p>描述：TODO</p> 
	 * @param datamap
	 * @return String
	 * @author:4621170153
	 * 2017年5月28日 下午4:16:48
	 */
	@RequestMapping("/delete")
	public String delete ( @RequestParam  HashMap<String, Object> datamap);
	
}
