package com.lnsoft.device.annotation;

import com.lnsoft.core.tool.utils.RedisUtil;
import com.lnsoft.core.tool.utils.SpringUtil;
import com.lnsoft.device.api.erp.mapper.ErpKostlMapper;
import com.lnsoft.device.api.erp.mapper.ErpMaintainMapper;
import com.lnsoft.device.api.erp.mapper.ErpTranstplnrMapper;
import com.lnsoft.device.config.ExcelDeviceTemplateConfiguration;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Objects;

/**
 * @ClassName: ExcelSelectedResolve
 * @description:
 * @author: zhangs
 * @create: 2024-03-19 18:22
 **/
@Data
@Slf4j
public class ExcelSelectedResolve {

	private String[] source;

	private String ciId;

	private int firstRow;

	private int lastRow;

	public String[] resolveSelectedSource(ExcelSelected excelSelected) {

		if (excelSelected == null) {
			return null;
		}
		Class<? extends ExcelDynamicSelectNew>[] classes1 = excelSelected.sourceClassDept();
		//实物保管部门和使用管理部门
		if (excelSelected.ciId().equals("use-keep-dept-name")){
			if (classes1.length > 0) {
				try {
					ErpKostlMapper bean = SpringUtil.getBean(ErpKostlMapper.class);
					Constructor<? extends ExcelDynamicSelectNew> constructor = classes1[0].getConstructor(ErpKostlMapper.class);
					ExcelDynamicSelectNew excelDynamicSelect = constructor.newInstance(bean);
					String[] dynamicSelectSource = excelDynamicSelect.getSourceDept();
					if (dynamicSelectSource != null && dynamicSelectSource.length > 0) {
						return dynamicSelectSource;
					}
				} catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
					log.error("解析动态下拉框数据异常", e);
				}
			}

		}
		//功能位置
		Class<? extends ExcelDynamicSelectTran>[] classesTran = excelSelected.sourceClassTran();
		if (excelSelected.ciId().equals("transtplnr")){
			if (classesTran.length > 0) {
				try {
					ErpTranstplnrMapper bean = SpringUtil.getBean(ErpTranstplnrMapper.class);
					Constructor<? extends ExcelDynamicSelectTran> constructor = classesTran[0].getConstructor(ErpTranstplnrMapper.class);
					ExcelDynamicSelectTran excelDynamicSelect = constructor.newInstance(bean);
					String[] dynamicSelectSource = excelDynamicSelect.getSourceTran();
					if (dynamicSelectSource != null && dynamicSelectSource.length > 0) {
						return dynamicSelectSource;
					}
				} catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
					log.error("解析动态下拉框数据异常", e);
				}
			}

		}

		//功能位置
		Class<? extends ExcelDynamicSelectMaintain>[] sourceClassMain = excelSelected.sourceClassMain();
		if (excelSelected.ciId().equals("main")){
			if (sourceClassMain.length > 0) {
				try {
					ErpMaintainMapper erpMaintainMapper = SpringUtil.getBean(ErpMaintainMapper.class);
					Constructor<? extends ExcelDynamicSelectMaintain> constructor = sourceClassMain[0].getConstructor(ErpMaintainMapper.class);
					ExcelDynamicSelectMaintain excelDynamicSelectMaintain = constructor.newInstance(erpMaintainMapper);
					String[] dynamicSelectSource = excelDynamicSelectMaintain.getSourceMain();
					if (dynamicSelectSource != null && dynamicSelectSource.length > 0) {
						return dynamicSelectSource;
					}
				} catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
					log.error("解析动态下拉框数据异常", e);
				}
			}

		}

		String[] source = excelSelected.source();
		if (source.length > 0) {
			return source;
		}
		String id = excelSelected.ciId();
		Long ciId = ExcelDeviceTemplateConfiguration.getCmdbCiIdByExcel(id);
		Class<? extends ExcelDynamicSelect>[] classes = excelSelected.sourceClass();
		if (classes.length > 0 && !Objects.isNull(ciId)) {
			try {
				RedisUtil bean = SpringUtil.getBean(RedisUtil.class);
				Constructor<? extends ExcelDynamicSelect> constructor = classes[0].getConstructor(RedisUtil.class);
				ExcelDynamicSelect excelDynamicSelect = constructor.newInstance(bean);
				String[] dynamicSelectSource = excelDynamicSelect.getSource(ciId);
				if (dynamicSelectSource != null && dynamicSelectSource.length > 0) {
					return dynamicSelectSource;
				}
			} catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
				log.error("解析动态下拉框数据异常", e);
			}
		}
		return null;
	}
}
