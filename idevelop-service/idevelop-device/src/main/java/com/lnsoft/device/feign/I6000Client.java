package com.lnsoft.device.feign;

import com.lnsoft.core.tool.api.R;
import com.lnsoft.device.api.i6000.dto.I6000OriViewDTO;
import com.lnsoft.device.api.i6000.entity.I6000Enum;
import com.lnsoft.device.api.i6000.enums.I6000EnumEnum;
import com.lnsoft.device.api.i6000.enums.I6000ExternalEnum;
import com.lnsoft.device.api.i6000.service.II6000EnumService;
import com.lnsoft.device.api.i6000.service.II6000ExternalService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
@ApiIgnore
@RestController
@AllArgsConstructor
public class I6000Client implements Ii6000ExtClient {

	@Resource
	private II6000EnumService i6000EnumService;
	@Resource
	private II6000ExternalService ii6000ExternalService;

	@Override
	@PostMapping(API_PREFIX + "/feignGetI6000ExtCode")
	public R<List<Map<String, String>>> feignGetI6000ExtCode() {
		List<Map<String, String>> maps = I6000ExternalEnum.toList();
		return R.data(maps);
	}

	@Override
	@PostMapping(API_PREFIX + "/refreshI6000Ext")
	public R refreshI6000Ext() {
		List<Map<String, String>> maps = I6000ExternalEnum.toList();
		for (Map<String, String> map : maps) {
			String code = map.get("EXT_CODE");
			I6000OriViewDTO i6000OriViewDTO = new I6000OriViewDTO();
			i6000OriViewDTO.setOriViewId(code);
			Boolean i6000AndAdd = ii6000ExternalService.getI6000AndAdd(i6000OriViewDTO);
			if (!i6000AndAdd){
				return R.data(false);
			}
		}
		return R.data(true);
	}

	@Override
	@PostMapping(API_PREFIX + "/refreshI6000Enum")
	public R refreshI6000Enum() {
		List<Map<String, String>> maps = I6000EnumEnum.toList();
		for (Map<String, String> map : maps) {
			String enumCode = map.get("EXT_CODE");
			I6000Enum i6000Enum = new I6000Enum();
			i6000Enum.setEnumId(enumCode);
			i6000EnumService.insert(i6000Enum);
		}
		return R.data(true);
	}


}
