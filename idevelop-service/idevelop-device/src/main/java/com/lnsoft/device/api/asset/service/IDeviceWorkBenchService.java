package com.lnsoft.device.api.asset.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lnsoft.core.tool.api.R;
import com.lnsoft.data.dto.WarningListDTO;
import com.lnsoft.data.entity.DevelopWarning;
import com.lnsoft.device.api.asset.dto.DevicePressDoDTO;
import com.lnsoft.device.api.asset.dto.WorkBenchFinishDTO;
import com.lnsoft.device.api.asset.vo.WorkBenchCompleteVO;
import com.lnsoft.device.api.asset.vo.WorkBenchFinishVO;
import com.lnsoft.device.api.asset.vo.WorkBenchIPNumberVO;
import com.lnsoft.device.api.asset.vo.WorkBenchUnderwayVO;

import java.util.List;
import java.util.Map;

/**
 * @Author: xuel
 * @CreateTime: 2024/7/18 11:23
 * @Description: 个人工作台服务 IDeviceWorkBenchService
 */

public interface IDeviceWorkBenchService {


	/**
	 * 个人工作台 - 我处理的 获取个人已办列表
	 *
	 * @return String
	 */
	IPage<WorkBenchFinishVO> getDoneList(WorkBenchFinishDTO workBenchFinishDTO);

	/**
	 * 个人工作台 - 代办任务：我发起的
	 *
	 * @return
	 */
	IPage<WorkBenchUnderwayVO> underwayTask(WorkBenchFinishDTO workBenchFinishDTO);

	/**
	 * 个人工作台 - 代办任务：我处理的-已完成
	 *
	 * @param workBenchFinishDTO
	 * @return
	 */
	IPage<WorkBenchCompleteVO> completeTask(WorkBenchFinishDTO workBenchFinishDTO);

	/**
	 * 个人工作台 - IP资源：IP地址总数 = 内网 + 外网-统一出口外网 + 外网-集体企业外网
	 * 只需要 已分配 和 未分配
	 *
	 * @return
	 */
	Map<String, Object> getIpAllNumber();

	/**
	 * 个人工作台 - IP资源：内网/外网使用率：内网 + 外网-统一出口外网 + 外网-集体企业外网
	 * 只需要 已分配 和 未分配
	 *
	 * @return
	 */
	List<WorkBenchIPNumberVO> getIpAssignNumber();

	/**
	 * 个人工作台 - 实时告警信息
	 *
	 * @return
	 */
	Page<DevelopWarning> warningList(WarningListDTO dto);

	/**
	 * 个人工作台 - 告警处置一周统计
	 *
	 * @return
	 */
	Map<String, Integer> warningStatistics(DevelopWarning developWarning);

	/**
	 * 代办任务：我发起的统计
	 *
	 * @param workBenchFinishDTO
	 * @return
	 */
	Map<String, Integer> wnderwayCompleteNum(WorkBenchFinishDTO workBenchFinishDTO);

	/**
	 * 催办接口
	 * @param devicePressDoDTO
	 * @return
	 */
    R pressDo(DevicePressDoDTO devicePressDoDTO);
}
