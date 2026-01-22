
package com.lnsoft.ipc.demo.feign;

import com.lnsoft.core.launch.constant.AppConstant;
import com.lnsoft.core.tool.api.R;
import com.lnsoft.ipc.demo.entity.Notice;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * Notice Feign接口类
 *
 * @author guozhao
 */
@FeignClient(
	value = AppConstant.APPLICATION_DESK_NAME
)
public interface INoticeClient {

	String API_PREFIX = "/client";
	String TOP = API_PREFIX + "/top";

	/**
	 * 获取notice列表
	 *
	 * @param number
	 * @return
	 */
	@GetMapping(TOP)
	R<List<Notice>> top(@RequestParam("number") Integer number);

}
