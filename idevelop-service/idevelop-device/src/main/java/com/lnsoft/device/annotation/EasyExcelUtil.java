package com.lnsoft.device.annotation;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.alibaba.excel.write.metadata.style.WriteCellStyle;
import com.alibaba.excel.write.style.HorizontalCellStyleStrategy;
import com.alibaba.excel.write.style.column.LongestMatchColumnWidthStyleStrategy;
import com.lnsoft.common.cache.CacheNames;
import com.lnsoft.core.log.exception.ServiceException;
import com.lnsoft.core.pojo.IdevelopUser;
import com.lnsoft.core.secure.utils.SecureUtil;
import com.lnsoft.core.tool.utils.CollectionUtil;
import com.lnsoft.core.tool.utils.Func;
import com.lnsoft.core.tool.utils.RedisUtil;
import com.lnsoft.core.tool.utils.SpringUtil;
import com.lnsoft.device.api.cmdb.excel.RowBackGroundWriteHandler;
import com.lnsoft.device.api.cmdb.excel.RowColFreezeHandler;
import com.lnsoft.device.api.cmdb.handler.CommentHeaderWriteHandler;
import com.lnsoft.device.api.cmdb.handler.ErrorHeaderWriteHandler;
import com.lnsoft.device.utils.ExcelReadBean;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.formula.functions.T;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.util.*;

/**
 * @ClassName: EasyExcelUtil
 * @description:
 * @author: zhangs
 * @create: 2024-03-19 18:18
 **/
@Slf4j
public class EasyExcelUtil {

	public static <T> WriteSheet writeSelectedSheet(Class<T> head, Integer sheetNo, String sheetName) {
		Map<Integer, ExcelSelectedResolve> selectedMap = resolveSelectedAnnotation(head);

		return EasyExcel.writerSheet(sheetNo, sheetName)
			.head(head)
			.registerWriteHandler(new SelectedSheetWriteHandler(selectedMap))
			.build();
	}

	private static <T> Map<Integer, ExcelSelectedResolve> resolveSelectedAnnotation(Class<T> head) {
		Map<Integer, ExcelSelectedResolve> selectedMap = new HashMap<>();

		Field[] fields = head.getDeclaredFields();
		for (int i = 0; i < fields.length; i++) {
			Field field = fields[i];
			ExcelSelected selected = field.getAnnotation(ExcelSelected.class);
			ExcelProperty property = field.getAnnotation(ExcelProperty.class);
			if (selected != null) {
				ExcelSelectedResolve excelSelectedResolve = new ExcelSelectedResolve();
				String[] source = excelSelectedResolve.resolveSelectedSource(selected);
				if (source != null && source.length > 0) {
					excelSelectedResolve.setSource(source);
					excelSelectedResolve.setFirstRow(selected.firstRow());
					excelSelectedResolve.setLastRow(selected.lastRow());
					if (property != null && property.index() >= 0) {
						selectedMap.put(property.index(), excelSelectedResolve);
					} else {
						selectedMap.put(i, excelSelectedResolve);
					}
				}
			}
			IdevelopUser user = SecureUtil.getUser();
			ExcelCorpSelected corpSelected = field.getAnnotation(ExcelCorpSelected.class);
			if (corpSelected != null) {
				String corpName = (String) user.getExt().get("corpFullName");
				String[] array = Func.toStrArray(corpName);
				// String roleCode = user.getRoleCode();
				// List<Dept> deptList = DeptWrapper.build().getByRegionCodeControl(roleCode);
				// String[] array = deptList.stream().map(Dept::getFullName).toArray(String[]::new);
				ExcelSelectedResolve excelSelectedResolve = new ExcelSelectedResolve();
				if (array != null && array.length > 0) {
					excelSelectedResolve.setSource(array);
					excelSelectedResolve.setFirstRow(corpSelected.firstRow());
					excelSelectedResolve.setLastRow(corpSelected.lastRow());
					if (property != null && property.index() >= 0) {
						selectedMap.put(property.index(), excelSelectedResolve);
					} else {
						selectedMap.put(i, excelSelectedResolve);
					}
				}
			}
			RedisUtil redisUtil = SpringUtil.getBean(RedisUtil.class);
			ExcelDeptSelected deptSelected = field.getAnnotation(ExcelDeptSelected.class);
			if (deptSelected != null) {
				List<String> list = (List<String>) redisUtil.get(CacheNames.IMPORT_DICT_STORAGE_DEPT + user.getCorpId());
				if (CollectionUtil.isNotEmpty(list)) {
					String[] deptNames = list.stream().toArray(String[]::new);
					ExcelSelectedResolve excelSelectedResolve = new ExcelSelectedResolve();
					if (deptNames != null && deptNames.length > 0) {
						excelSelectedResolve.setSource(deptNames);
						excelSelectedResolve.setFirstRow(deptSelected.firstRow());
						excelSelectedResolve.setLastRow(deptSelected.lastRow());
						if (property != null && property.index() >= 0) {
							selectedMap.put(property.index(), excelSelectedResolve);
						} else {
							selectedMap.put(i, excelSelectedResolve);
						}
					}
				}
			}
		}
		return selectedMap;
	}

	public static void exportExcelWithBackGround(TreeMap<Integer, ExcelReadBean> treeMap,List<Object> exportData, HttpServletResponse response, Class<T> tClass,
												 String fileName, String sheetName, List<String> backGroundIndex) {
		try {
			//文件输出格式
			response.setContentType("application/vnd.ms-excel;charset=UTF-8");
			response.setHeader("Cache-Control", "no-cache");
			response.setHeader("Pragma", "no-cache");
			response.setDateHeader("Expires", -1);
			response.setHeader("Content-Disposition", " attachment;filename=" + fileName);

			RowBackGroundWriteHandler handler = null;
			if (CollectionUtil.isNotEmpty(backGroundIndex)) {
				Set<String> integers = new HashSet<>(backGroundIndex);
				handler = new RowBackGroundWriteHandler(integers);
			}

			WriteCellStyle headWriteCellStyle = new WriteCellStyle();
			headWriteCellStyle.setWrapped(false);
			HorizontalCellStyleStrategy horizontalCellStyleStrategy = new HorizontalCellStyleStrategy(headWriteCellStyle, (WriteCellStyle) null);

			ExcelWriter excelWriter = EasyExcel.write(response.getOutputStream())
				.registerWriteHandler(horizontalCellStyleStrategy)
				.registerWriteHandler(new LongestMatchColumnWidthStyleStrategy())
				.registerWriteHandler(new RowColFreezeHandler())
				.registerWriteHandler(new CommentHeaderWriteHandler(treeMap))
				.registerWriteHandler(handler)
				.build();
			WriteSheet writeSheet = EasyExcelUtil.writeSelectedSheet(tClass, 0, sheetName);
			excelWriter.write(exportData, writeSheet);
			excelWriter.finish();

		} catch (UnsupportedEncodingException e) {
			throw new ServiceException("转码异常");
		} catch (IOException e) {
			throw new ServiceException("IO异常");
		}
	}

	public static void exportExcelWithBackGroundError(TreeMap<Integer, ExcelReadBean> treeMap,List<Object> exportData, HttpServletResponse response, Class<T> tClass,
												 String fileName, String sheetName, List<String> backGroundIndex) {
		try {
			//文件输出格式
			response.setContentType("application/vnd.ms-excel;charset=UTF-8");
			response.setHeader("Cache-Control", "no-cache");
			response.setHeader("Pragma", "no-cache");
			response.setDateHeader("Expires", -1);
			response.setHeader("Content-Disposition", " attachment;filename=" + fileName);

			RowBackGroundWriteHandler handler = null;
			if (CollectionUtil.isNotEmpty(backGroundIndex)) {
				Set<String> integers = new HashSet<>(backGroundIndex);
				handler = new RowBackGroundWriteHandler(integers);
			}

			WriteCellStyle headWriteCellStyle = new WriteCellStyle();
			headWriteCellStyle.setWrapped(false);
			HorizontalCellStyleStrategy horizontalCellStyleStrategy = new HorizontalCellStyleStrategy(headWriteCellStyle, (WriteCellStyle) null);

			ExcelWriter excelWriter = EasyExcel.write(response.getOutputStream())
				.registerWriteHandler(horizontalCellStyleStrategy)
				.registerWriteHandler(new LongestMatchColumnWidthStyleStrategy())
				.registerWriteHandler(new RowColFreezeHandler())
				.registerWriteHandler(new ErrorHeaderWriteHandler(treeMap))
				.registerWriteHandler(handler)
				.build();
			WriteSheet writeSheet = EasyExcelUtil.writeSelectedSheet(tClass, 0, sheetName);
			excelWriter.write(exportData, writeSheet);
			excelWriter.finish();

		} catch (UnsupportedEncodingException e) {
			throw new ServiceException("转码异常");
		} catch (IOException e) {
			throw new ServiceException("IO异常");
		}
	}

	public static void exportExcelWithBackGroundError(List<Object> exportData, HttpServletResponse response, Class<T> tClass, String fileName, String sheetName) {
		try {
			//文件输出格式
			response.setContentType("application/vnd.ms-excel;charset=UTF-8");
			response.setHeader("Cache-Control", "no-cache");
			response.setHeader("Pragma", "no-cache");
			response.setDateHeader("Expires", -1);
			response.setHeader("Content-Disposition", " attachment;filename=" + fileName);


			WriteCellStyle headWriteCellStyle = new WriteCellStyle();
			headWriteCellStyle.setWrapped(false);
			HorizontalCellStyleStrategy horizontalCellStyleStrategy = new HorizontalCellStyleStrategy(headWriteCellStyle, (WriteCellStyle) null);

			ExcelWriter excelWriter = EasyExcel.write(response.getOutputStream())
				.registerWriteHandler(horizontalCellStyleStrategy)
				.registerWriteHandler(new LongestMatchColumnWidthStyleStrategy())
				.registerWriteHandler(new RowColFreezeHandler())
				.build();
			WriteSheet writeSheet = EasyExcelUtil.writeSelectedSheet(tClass, 0, sheetName);
			excelWriter.write(exportData, writeSheet);
			excelWriter.finish();

		} catch (UnsupportedEncodingException e) {
			throw new ServiceException("转码异常");
		} catch (IOException e) {
			throw new ServiceException("IO异常");
		}
	}

	public static String convertToExcelColumn(Integer columnIndex) {
		StringBuilder columnName = new StringBuilder();
		while (columnIndex >= 0) {
			int remainder = columnIndex % 26;
			columnName.insert(0, (char) ('A' + remainder));
			columnIndex = (columnIndex / 26) - 1;
		}
		return columnName.toString();
	}
}
