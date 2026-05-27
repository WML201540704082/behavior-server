package com.lnsoft.device.api.cmdb.excel;

import com.alibaba.excel.metadata.CellData;
import com.alibaba.excel.metadata.Head;
import com.alibaba.excel.write.handler.CellWriteHandler;
import com.alibaba.excel.write.metadata.holder.WriteSheetHolder;
import com.alibaba.excel.write.metadata.holder.WriteTableHolder;
import lombok.AllArgsConstructor;
import org.apache.poi.ss.usermodel.*;

import java.util.List;
import java.util.Set;

/**
 * @ClassName: RowBackGroundWriteHandler
 * @description:
 * @author: zhangs
 * @create: 2024-06-06 10:01
 **/
@AllArgsConstructor
public class RowBackGroundWriteHandler implements CellWriteHandler {

	private Set<String> yellowRowIndex;


	@Override
	public void beforeCellCreate(WriteSheetHolder writeSheetHolder, WriteTableHolder writeTableHolder, Row row, Head head, Integer integer, Integer integer1, Boolean aBoolean) {

	}

	@Override
	public void afterCellCreate(WriteSheetHolder writeSheetHolder, WriteTableHolder writeTableHolder, Cell cell, Head head, Integer integer, Boolean aBoolean) {

	}

	@Override
	public void afterCellDataConverted(WriteSheetHolder writeSheetHolder, WriteTableHolder writeTableHolder, CellData cellData, Cell cell, Head head, Integer integer, Boolean aBoolean) {

	}

	@Override
	public void afterCellDispose(WriteSheetHolder writeSheetHolder, WriteTableHolder writeTableHolder,
								 List<CellData> list, Cell cell, Head head, Integer relativeRowIndex, Boolean isHead) {
		// 跳过表头
		if (isHead) {
			return;
		}
		int currentRowIndex = cell.getColumnIndex();
		// 异常信息列 自动换行
		if (currentRowIndex == 0) {
			Workbook workbook = writeSheetHolder.getSheet().getWorkbook();
			CellStyle cellStyle = workbook.createCellStyle();
			cellStyle.cloneStyleFrom(cell.getCellStyle());
			cellStyle.setWrapText(Boolean.TRUE);
			cell.setCellStyle(cellStyle);
		}
		String cellAddress = cell.getAddress().formatAsString();
		// 异常单元格 背景色 黄
		if (yellowRowIndex.contains(cellAddress)) {
			Workbook workbook = writeSheetHolder.getSheet().getWorkbook();
			CellStyle cellStyle = workbook.createCellStyle();
			cellStyle.cloneStyleFrom(cell.getCellStyle());
			cellStyle.setBorderLeft(BorderStyle.THIN);
			cellStyle.setBorderRight(BorderStyle.THIN);
			cellStyle.setBorderTop(BorderStyle.THIN);
			cellStyle.setBorderBottom(BorderStyle.THIN);
			cellStyle.setFillForegroundColor(IndexedColors.YELLOW.getIndex());
			cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
			cell.setCellStyle(cellStyle);
		}
	}
}
