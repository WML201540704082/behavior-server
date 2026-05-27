package com.lnsoft.device.annotation;

import com.alibaba.excel.write.handler.SheetWriteHandler;
import com.alibaba.excel.write.metadata.holder.WriteSheetHolder;
import com.alibaba.excel.write.metadata.holder.WriteWorkbookHolder;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddressList;

import java.util.Map;

/**
 * @ClassName: SelectedSheetWriteHandler
 * @description:
 * @author: zhangs
 * @create: 2024-03-19 18:29
 **/
@Data
@AllArgsConstructor
public class SelectedSheetWriteHandler implements SheetWriteHandler {

	private final Map<Integer, ExcelSelectedResolve> selectedMap;

	private static final Integer LIMIT_NUMBER = 25;

	@Override
	public void beforeSheetCreate(WriteWorkbookHolder writeWorkbookHolder, WriteSheetHolder writeSheetHolder) {

	}

	@Override
	public void afterSheetCreate(WriteWorkbookHolder writeWorkbookHolder, WriteSheetHolder writeSheetHolder) {
		Sheet sheet = writeSheetHolder.getSheet();
		DataValidationHelper helper = sheet.getDataValidationHelper();
		selectedMap.forEach((k, v) -> {
			// 设置下拉列表的行：首行、末行、首列、末列
			CellRangeAddressList rangeList = new CellRangeAddressList(v.getFirstRow(), v.getLastRow(), k, k);
			// 如果下拉值总数大于25，则使用新的sheet存储
			if (v.getSource().length > LIMIT_NUMBER) {
				String sheetName = "hidden" + k;
				Workbook workbook = writeWorkbookHolder.getWorkbook();
				Sheet hiddenSheet = workbook.createSheet(sheetName);
				for (int i = 0; i < v.getSource().length; i++) {
					hiddenSheet.createRow(i).createCell(k).setCellValue(v.getSource()[i]);
				}
				Name categoryName = workbook.createName();
				categoryName.setNameName(sheetName);
				String excelLine = getExcelLine(k);
				String refers = "=" + sheetName + "!$" + excelLine + "$1:$" + excelLine + "$" + (v.getSource().length + 1);
				// 设置的sheet引用到下拉列表
				DataValidationConstraint constraint = helper.createFormulaListConstraint(refers);
//				constraint.setExplicitListValues();
				DataValidation dataValidation = helper.createValidation(constraint, rangeList);
				writeSheetHolder.getSheet().addValidationData(dataValidation);
				// 设置存储下拉列值的sheet为隐藏
				int hiddenIndex = workbook.getSheetIndex(sheetName);
				if (!workbook.isSheetHidden(hiddenIndex)) {
					workbook.setSheetHidden(hiddenIndex, true);
				}
			}
			// 设置下拉列表的值
			DataValidationConstraint constraint = helper.createExplicitListConstraint(v.getSource());
			// 设置约束
			DataValidation validation = helper.createValidation(constraint, rangeList);
			// 阻止输入非下拉选项的值
			validation.setErrorStyle(DataValidation.ErrorStyle.STOP);
			validation.setShowErrorBox(true);
			validation.createErrorBox("提示", "请输入下拉选项中的内容");
			sheet.addValidationData(validation);
		});
	}

	private String getExcelLine(int num) {
		String line = "";
		int first = num / 26;
		int second = num % 26;
		if (first > 0) {
			line = (char) ('A' + first - 1) + "";
		}
		line += (char) ('A' + second) + "";
		return line;
	}

}
