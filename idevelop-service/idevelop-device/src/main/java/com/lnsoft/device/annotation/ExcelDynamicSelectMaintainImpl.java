package com.lnsoft.device.annotation;

import com.lnsoft.core.pojo.IdevelopUser;
import com.lnsoft.core.secure.utils.SecureUtil;
import com.lnsoft.device.api.erp.entity.ErpMaintain;
import com.lnsoft.device.api.erp.mapper.ErpMaintainMapper;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @ClassName: BrandServiceImpl
 * @description:
 * @author: zhangs
 * @create: 2024-03-19 17:58
 **/
@Service
@Slf4j
@NoArgsConstructor
@AllArgsConstructor
public class ExcelDynamicSelectMaintainImpl implements ExcelDynamicSelectMaintain {

	private ErpMaintainMapper erpMaintainMapper;

	@Override
	public String[] getSourceMain() {
		ErpMaintain erpMaintain = new ErpMaintain();
		IdevelopUser user = SecureUtil.getUser();
		String erpUnitCode = user.getErpUnitCode();
		erpMaintain.setCode(erpUnitCode);
		List<ErpMaintain> erpMaintains = erpMaintainMapper.getSwerk(erpMaintain);
		List<String> collect = erpMaintains.stream().map(item -> item.getName()).collect(Collectors.toList());
		String[] array = collect.toArray(new String[collect.size()]);
		return array;
	}
}
