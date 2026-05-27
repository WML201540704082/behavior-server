package com.lnsoft.device.annotation;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.lnsoft.device.api.erp.entity.ErpKostl;
import com.lnsoft.device.api.erp.mapper.ErpKostlMapper;
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
public class ExcelDynamicSelectNewImpl implements ExcelDynamicSelectNew {

	private ErpKostlMapper erpKostlMapper;

	@Override
	public String[] getSourceDept() {
		LambdaQueryWrapper<ErpKostl> queryWrapper = new LambdaQueryWrapper<>();
		List<ErpKostl> erpKostls = erpKostlMapper.selectList(queryWrapper);
		List<String> list = erpKostls.stream().map(item -> item.getKostlT()).collect(Collectors.toList());
		String[] array = list.toArray(new String[list.size()]);
		return array;
	}
}
