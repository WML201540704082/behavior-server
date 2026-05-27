package com.lnsoft.device.api.asset.service;

import com.lnsoft.core.tool.api.R;
import com.lnsoft.data.vo.WainingDetailVO;
import com.lnsoft.data.vo.WarningCountVO;
import com.lnsoft.device.api.asset.vo.*;

import java.util.List;

public interface IHomeService {

	/**
	 * 台账概览数量统计
	 * @return
	 */
	R<DeviceCount> deviceCount();

	/**
	 * 信创设备分发统计
	 * @return
	 */
	R<PatentDeviceCount> distributeCount();

	/**
	 * 芯片架构(ARM)
	 * @return
	 */
	R<DevicePatentOperatingVO> frameworkArm();

	/**
	 * 芯片架构(X86)
	 * @return
	 */
	R<DevicePatentOperatingVO> frameworkX86();

	/**
	 * 告警处置一周统计
	 * @return
	 */
	R<WarningCountVO> warningCount();

	/**
	 * 实时告警信息
	 * @return
	 */
	R<List<WainingDetailVO>> warningDetail();

	/**
	 * 超龄分布图
	 * @return
	 */
	R<List<OldAgeVO>> oldAge();

	/**
	 * 设备老旧趋势图
	 * @return
	 */
	R<OldTrend>oldTrend();

	/**
	 * 实时在线分布趋势
	 * @param date
	 * @return
	 */
	OnlineVO online(String date);
}
