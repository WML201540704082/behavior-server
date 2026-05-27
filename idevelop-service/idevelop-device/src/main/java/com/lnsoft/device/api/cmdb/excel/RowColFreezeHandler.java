package com.lnsoft.device.api.cmdb.excel;

import com.alibaba.excel.write.handler.SheetWriteHandler;
import com.alibaba.excel.write.metadata.holder.WriteSheetHolder;
import com.alibaba.excel.write.metadata.holder.WriteWorkbookHolder;
import org.apache.poi.ss.usermodel.Sheet;

/**
 * @ClassName: RowColFreezeHandler
 * @description:
 * @author: zhangs
 * @create: 2024-06-14 17:27
 **/
public class RowColFreezeHandler implements SheetWriteHandler {
	@Override
	public void beforeSheetCreate(WriteWorkbookHolder writeWorkbookHolder, WriteSheetHolder writeSheetHolder) {

	}

	@Override
	public void afterSheetCreate(WriteWorkbookHolder writeWorkbookHolder, WriteSheetHolder writeSheetHolder) {
		// 行列冻结 需要搭配列长 列过长冻结失效 无法拖动
		Sheet sheet = writeSheetHolder.getSheet();
		sheet.createFreezePane(1, 1);
	}
}
