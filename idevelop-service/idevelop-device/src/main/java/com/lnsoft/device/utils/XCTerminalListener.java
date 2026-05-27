package com.lnsoft.device.utils;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.lnsoft.device.api.asset.dto.XCTerminalDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;

import java.util.List;

/**
 * @ClassName: XCTerminalListener
 * @description: 批量导入信创终端设备 [ls临时]
 * @author: xuel
 * @create: 2024-05-28
 **/
@Data
@RequiredArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class XCTerminalListener extends AnalysisEventListener<XCTerminalDTO> {

	private List<XCTerminalDTO> xcTerminalDTOList;

	public XCTerminalListener(List<XCTerminalDTO> dataList) {
		this.xcTerminalDTOList = dataList;
	}


	@Override
	public void invoke(XCTerminalDTO xcTerminalDTO, AnalysisContext analysisContext) {
		xcTerminalDTOList.add(xcTerminalDTO);
	}

	@Override
	public void doAfterAllAnalysed(AnalysisContext analysisContext) {
	}
}
