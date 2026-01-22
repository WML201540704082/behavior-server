
package com.lnsoft.auth.controller;

import com.lnsoft.core.tool.api.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@Slf4j
@RestController
public class TestController {


	@GetMapping("/test")
	public R test(){
		return R.success("请求成功");
	}

}
