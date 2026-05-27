package com.lnsoft.device.api.asset.mapper;

import com.lnsoft.device.api.asset.dto.WorkBenchFinishDTO;
import com.lnsoft.device.api.asset.entity.WorkBenchDone;
import com.lnsoft.device.entity.ApproveRecord;

import java.util.List;

public interface DeviceWorkBenchMapper {

	Integer selectWorkTodoListCount(WorkBenchFinishDTO workBenchFinishDTO);

	List<WorkBenchDone> selectWorkTodoList(WorkBenchFinishDTO workBenchFinishDTO);

	Integer selectWorkDoneListCount(WorkBenchFinishDTO workBenchFinishDTO);

	List<WorkBenchDone> selectWorkDoneList(WorkBenchFinishDTO workBenchFinishDTO);

	/**
	 * 我处理的
	 * @param workBenchFinishDTO
	 * @return
	 */
	List<ApproveRecord> selectWorkDoneMyList(WorkBenchFinishDTO workBenchFinishDTO);

	Integer selectWorkDoneMyCount(WorkBenchFinishDTO workBenchFinishDTO);
}
