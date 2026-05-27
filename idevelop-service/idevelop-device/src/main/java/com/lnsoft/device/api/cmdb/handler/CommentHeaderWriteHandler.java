package com.lnsoft.device.api.cmdb.handler;

import com.alibaba.excel.write.handler.AbstractRowWriteHandler;
import com.alibaba.excel.write.metadata.holder.WriteSheetHolder;
import com.alibaba.excel.write.metadata.holder.WriteTableHolder;
import com.lnsoft.device.utils.ExcelReadBean;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import java.util.Map;
import java.util.TreeMap;

/**
 * 增加说明拦截类
 */
public class CommentHeaderWriteHandler extends AbstractRowWriteHandler {

	TreeMap<Integer,ExcelReadBean> cellMap;

	public CommentHeaderWriteHandler(TreeMap<Integer, ExcelReadBean> cellMap) {
		this.cellMap = cellMap;
	}

	@Override
	public void beforeRowCreate(WriteSheetHolder writeSheetHolder, WriteTableHolder writeTableHolder, Integer rowIndex, Integer relativeRowIndex, Boolean isHead) {
		if(relativeRowIndex == 0 && isHead){
			Sheet sheet = writeSheetHolder.getSheet();
			Row hideRow = sheet.createRow(1);
			hideRow.setHeightInPoints(0);
			Row isRequired = sheet.createRow(2);
			isRequired.createCell(0).setCellValue("是否必填");
			Row row = sheet.createRow(3);
			row.createCell(0).setCellValue("说明");
			for (Map.Entry<Integer,ExcelReadBean> entry : cellMap.entrySet()){
				row.createCell(entry.getKey()).setCellValue(entry.getValue() != null ? entry.getValue().getCheck() : "");
				isRequired.createCell(entry.getKey()).setCellValue(entry.getValue() != null ? entry.getValue().getIsRequired() : "");
			}
		}
	}
}
