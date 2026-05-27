package com.lnsoft.device.annotation;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.lnsoft.core.tool.constant.IdevelopConstant;
import com.lnsoft.device.api.erp.entity.ErpTranstplnr;
import com.lnsoft.device.api.erp.mapper.ErpTranstplnrMapper;
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
public class ExcelDynamicSelectTranImpl implements ExcelDynamicSelectTran {

	private ErpTranstplnrMapper erpTranstplnrMapper;

	@Override
	public String[] getSourceTran() {
		LambdaQueryWrapper<ErpTranstplnr> queryWrapper = new LambdaQueryWrapper<>();
		queryWrapper.eq(ErpTranstplnr::getIsDeleted, IdevelopConstant.DB_NOT_DELETED);
		List<ErpTranstplnr> erpTranstplnrs = erpTranstplnrMapper.selectList(queryWrapper);
		List<String> collect = erpTranstplnrs.stream().map(item -> item.getPltxt()).collect(Collectors.toList());
		String[] array = collect.toArray(new String[collect.size()]);
		return array;
	}
}
