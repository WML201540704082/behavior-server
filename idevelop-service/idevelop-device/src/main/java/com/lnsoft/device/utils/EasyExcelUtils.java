package com.lnsoft.device.utils;

import com.alibaba.excel.write.metadata.style.WriteCellStyle;
import com.alibaba.excel.write.metadata.style.WriteFont;
import org.apache.poi.ss.usermodel.*;

/**
 * @Author: xuel
 * @CreateTime: 2026/2/10 14:24
 * @Description: EasyExcelUtils
 */
public class EasyExcelUtils {




    public static WriteCellStyle headStyle() {
        WriteCellStyle writeCellStyle = new WriteCellStyle();

        // 字体
        WriteFont writeFont = new WriteFont();
        writeFont.setBold(true);
        writeFont.setFontHeightInPoints((short) 12);
        writeFont.setFontName("微软雅黑");

        writeCellStyle.setWriteFont(writeFont);

        // 对齐
        writeCellStyle.setHorizontalAlignment(HorizontalAlignment.CENTER);
        writeCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);

        // 背景色
        writeCellStyle.setFillForegroundColor(IndexedColors.LIGHT_CORNFLOWER_BLUE.getIndex());
        writeCellStyle.setFillPatternType(FillPatternType.SOLID_FOREGROUND);

        //边框
        writeCellStyle.setBorderLeft(BorderStyle.THIN);
        writeCellStyle.setBorderRight(BorderStyle.THIN);
        writeCellStyle.setBorderTop(BorderStyle.THIN);
        writeCellStyle.setBorderBottom(BorderStyle.THIN);

        return writeCellStyle;
    }

    public static WriteCellStyle contentStyle() {
        WriteCellStyle writeCellStyle = new WriteCellStyle();

        // 字体
        WriteFont writeFont = new WriteFont();
        writeFont.setFontHeightInPoints((short) 11);
        writeCellStyle.setWriteFont(writeFont);

        // 对齐
        writeCellStyle.setHorizontalAlignment(HorizontalAlignment.CENTER);
        writeCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);


        return writeCellStyle;
    }


}
