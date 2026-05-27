package com.lnsoft.device.api.desk.service.impl;

import cn.hutool.core.convert.Convert;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.lnsoft.common.enums.device.WorkOrderTypeEnum;
import com.lnsoft.common.enums.hussar.*;
import com.lnsoft.common.msg.service.MessageSendService;
import com.lnsoft.core.pojo.IdevelopUser;
import com.lnsoft.core.secure.utils.SecureUtil;
import com.lnsoft.core.tool.api.ResultCode;
import com.lnsoft.core.tool.utils.CollectionUtil;
import com.lnsoft.device.api.desk.service.IDeskService;
import com.lnsoft.device.api.desk.vo.DeskOrderNumVO;
import com.lnsoft.device.api.desk.vo.DictValueVO;
import com.lnsoft.device.api.warehouse.entity.DeviceApply;
import com.lnsoft.device.api.warehouse.entity.DeviceOperation;
import com.lnsoft.device.api.warehouse.mapper.DeviceApplyMapper;
import com.lnsoft.device.api.warehouse.mapper.DeviceOperationMapper;
import com.lnsoft.device.api.warehouse.mapper.DeviceOutboundMapper;
import com.lnsoft.hussar.bpm.domain.vo.HussarFlowTaskVo;
import com.lnsoft.hussar.bpm.feign.IHussarBpmClient;
import com.lnsoft.hussar.bpm.tool.api.R;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class DeskServiceImpl implements IDeskService {

	@Resource
	private IHussarBpmClient hussarBpmClient;
	@Resource
	private DeviceApplyMapper deviceApplyMapper;
	@Resource
	private DeviceOutboundMapper deviceOutboundMapper;
	@Resource
	private DeviceOperationMapper operationMapper;
	@Resource
	private MessageSendService messageSendService;

	/**
	 * 获取个人工作台工单数量
	 *
	 * @return DeskOrderNumVO
	 */
	@Override
	public DeskOrderNumVO queryOrderNum() throws Exception {
		IdevelopUser user = SecureUtil.getUser();
		HussarBpmTypeEnum[] values = HussarBpmTypeEnum.values();
		List<String> processKeyList = new ArrayList<>();
		for (HussarBpmTypeEnum value : values) {
			processKeyList.add(value.getBpmMark());
		}
		DeskOrderNumVO deskOrderNumVO = new DeskOrderNumVO();
		if (CollectionUtil.isEmpty(processKeyList)) {
			return deskOrderNumVO;
		}
		R<List<HussarFlowTaskVo>> allTodoList = hussarBpmClient.getAllTodoList(user.getUserId().toString(), String.join(",", processKeyList));
		if (ResultCode.SUCCESS.getCode() == allTodoList.getCode()) {
			List<HussarFlowTaskVo> data = allTodoList.getData();
			if (CollectionUtil.isEmpty(data)) {
				return deskOrderNumVO;
			}
			Map<String, List<HussarFlowTaskVo>> collect = data.stream().collect(Collectors.groupingBy(HussarFlowTaskVo::getProcessKey));
			Map<String, Long> integerHashMap = new HashMap<>();
			for (Map.Entry<String, List<HussarFlowTaskVo>> stringListEntry : collect.entrySet()) {
				String listEntryKey = stringListEntry.getKey();
				long num = stringListEntry.getValue().size();
				String deskOrderNumFlag = HussarBpmTypeEnum.getDeskOrderNumFlag(listEntryKey);
				if (HussarBpmTypeEnum.DEVICE_APPLY_OUTBOUND_OPERATION.getBpmMark().equals(listEntryKey)) {
					List<String> orderNoList = stringListEntry.getValue().stream().map(HussarFlowTaskVo::getBusinessId).collect(Collectors.toList());
					List<String> applyNoList = orderNoList.stream().filter(s -> s.startsWith(WorkOrderTypeEnum.SQ.getValue())).collect(Collectors.toList());
					List<String> operationList = orderNoList.stream().filter(s -> s.startsWith(WorkOrderTypeEnum.TY.getValue())).collect(Collectors.toList());
					long deviceApplyNum = 0;
					long deviceOutboundNum = 0;
					long deviceOperationNum = operationList.size();
					if (CollectionUtil.isNotEmpty(applyNoList)) {
						List<DeviceApply> deviceApplyList = deviceApplyMapper.selectList(new LambdaQueryWrapper<DeviceApply>().in(DeviceApply::getApplyNo, applyNoList)
							.select(DeviceApply::getApplyNo, DeviceApply::getOutboundNo, DeviceApply::getOperationNo));
						deviceApplyNum = deviceApplyList.stream().filter(deviceApply -> StringUtils.isBlank(deviceApply.getOutboundNo())).count();
						deviceOutboundNum = deviceApplyList.stream().filter(deviceApply -> StringUtils.isNotBlank(deviceApply.getOutboundNo())
							&& StringUtils.isBlank(deviceApply.getOperationNo())).count();
						deviceOperationNum = deviceApplyList.stream().filter(deviceApply -> StringUtils.isNotBlank(deviceApply.getOperationNo())).count();
					}
					integerHashMap.put("deviceApplyNum", deviceApplyNum);
					integerHashMap.put("deviceOutboundNum", deviceOutboundNum);
					integerHashMap.put("deviceOperationNum", deviceOperationNum);
				} else {
					integerHashMap.put(deskOrderNumFlag, num);
				}
			}
			deskOrderNumVO = Convert.convert(DeskOrderNumVO.class, integerHashMap);
		} else {
			throw new Exception(allTodoList.getMsg());
		}

		return deskOrderNumVO;
	}

	/**
	 * 获取工单节点字典
	 *
	 * @param orderType 工单类型
	 * @param orderNo   工单编号
	 * @return List
	 */
	@Override
	public List<DictValueVO> deviceRecordDict(String orderType, String orderNo) {
		List<DictValueVO> dictValueVOList = new ArrayList<>();
		if (HussarBpmTypeEnum.DEVICE_RECORD.getBpmMark().equals(orderType)) {
			DeviceRecordBpmNodeEnum[] values = DeviceRecordBpmNodeEnum.values();
			for (DeviceRecordBpmNodeEnum deviceRecordBpmNodeEnum : values) {
				dictValueVOList.add(DictValueVO.builder().node(deviceRecordBpmNodeEnum.getNode()).nodeName(deviceRecordBpmNodeEnum.getMessage())
					.sort(deviceRecordBpmNodeEnum.getSort()).type(deviceRecordBpmNodeEnum.getType()).build());
			}
		}
		if (HussarBpmTypeEnum.DEVICE_TRANSFER.getBpmMark().equals(orderType)) {
			DeviceTransferBpmNodeEnum[] values = DeviceTransferBpmNodeEnum.values();
			for (DeviceTransferBpmNodeEnum deviceTransferBpmNodeEnum : values) {
				dictValueVOList.add(DictValueVO.builder().node(deviceTransferBpmNodeEnum.getNode()).nodeName(deviceTransferBpmNodeEnum.getMessage())
					.sort(deviceTransferBpmNodeEnum.getSort()).type(deviceTransferBpmNodeEnum.getType()).build());
			}
		}
		if (HussarBpmTypeEnum.DEVICE_APPLY_OUTBOUND_OPERATION.getBpmMark().equals(orderType)) {
			if (StringUtils.isNotBlank(orderNo)) {
				boolean flag = false;
				LambdaQueryWrapper<DeviceApply> queryWrapper = new LambdaQueryWrapper<>();
				if (orderNo.startsWith(WorkOrderTypeEnum.SQ.getValue())) {
					queryWrapper.eq(DeviceApply::getApplyNo, orderNo);
				}
				if (orderNo.startsWith(WorkOrderTypeEnum.CK.getValue())) {
					queryWrapper.eq(DeviceApply::getOutboundNo, orderNo);
				}
				if (orderNo.startsWith(WorkOrderTypeEnum.TY.getValue())) {
					DeviceOperation deviceOperation = operationMapper.selectOne(new LambdaQueryWrapper<DeviceOperation>().eq(DeviceOperation::getOperationNo, orderNo));
					if ("0".equals(deviceOperation.getOperationType())) {
						queryWrapper.eq(DeviceApply::getOperationNo, orderNo);
					} else {
						flag = true;
					}
				}
				if (flag) {
					dictValueVOList.add(DictValueVO.builder().node(DeviceOperationEnum.DEVICE_APPLY.getHussar()).nodeName(DeviceOperationEnum.DEVICE_APPLY.getMessage())
						.sort(DeviceOperationEnum.DEVICE_APPLY.getSort()).type(DeviceOperationEnum.DEVICE_APPLY.getType()).build());
					dictValueVOList.add(DictValueVO.builder().node(DeviceOperationEnum.FINISH.getHussar()).nodeName(DeviceOperationEnum.FINISH.getMessage())
						.sort(DeviceOperationEnum.FINISH.getSort()).type(DeviceOperationEnum.FINISH.getType()).build());
				} else {
					DeviceApplyOutboundOperationBpmNodeEnum[] values = DeviceApplyOutboundOperationBpmNodeEnum.values();
					for (DeviceApplyOutboundOperationBpmNodeEnum deviceApplyOutboundOperationBpmNodeEnum : values) {
						dictValueVOList.add(DictValueVO.builder().node(deviceApplyOutboundOperationBpmNodeEnum.getNode()).nodeName(deviceApplyOutboundOperationBpmNodeEnum.getMessage())
							.sort(deviceApplyOutboundOperationBpmNodeEnum.getSort()).type(deviceApplyOutboundOperationBpmNodeEnum.getType()).build());
					}
					DeviceApply deviceApply = deviceApplyMapper.selectOne(queryWrapper);
					if (deviceApply.getSubmitDigitalFlag() == 0) {
						dictValueVOList = dictValueVOList.stream().filter(dictValueVO ->
							!DeviceApplyOutboundOperationBpmNodeEnum.DEVICE_APPLY_DIRECTOR.getNode().equals(dictValueVO.getNode())).collect(Collectors.toList());
					}
				}
			} else {
				DeviceApplyOutboundOperationBpmNodeEnum[] values = DeviceApplyOutboundOperationBpmNodeEnum.values();
				for (DeviceApplyOutboundOperationBpmNodeEnum deviceApplyOutboundOperationBpmNodeEnum : values) {
					dictValueVOList.add(DictValueVO.builder().node(deviceApplyOutboundOperationBpmNodeEnum.getNode()).nodeName(deviceApplyOutboundOperationBpmNodeEnum.getMessage())
						.sort(deviceApplyOutboundOperationBpmNodeEnum.getSort()).type(deviceApplyOutboundOperationBpmNodeEnum.getType()).build());
				}
			}
		}
		if (HussarBpmTypeEnum.DEVICE_CHANGE.getBpmMark().equals(orderType)) {
			DeviceChangeBpmNodeEnum[] values = DeviceChangeBpmNodeEnum.values();
			for (DeviceChangeBpmNodeEnum deviceChangeBpmNodeEnum : values) {
				dictValueVOList.add(DictValueVO.builder()
					.node(deviceChangeBpmNodeEnum.getNode())
					.nodeName(deviceChangeBpmNodeEnum.getMessage())
					.sort(deviceChangeBpmNodeEnum.getSort())
					.type(deviceChangeBpmNodeEnum.getType()).build());
			}
		}
		if (HussarBpmTypeEnum.DEVICE_REPAIR.getBpmMark().equals(orderType)) {
			DeviceRepairBpmNodeEnum[] values = DeviceRepairBpmNodeEnum.values();
			for (DeviceRepairBpmNodeEnum deviceRepairBpmNodeEnum : values) {
				dictValueVOList.add(DictValueVO.builder()
					.node(deviceRepairBpmNodeEnum.getNode())
					.nodeName(deviceRepairBpmNodeEnum.getMessage())
					.sort(deviceRepairBpmNodeEnum.getSort())
					.type(deviceRepairBpmNodeEnum.getType()).build());
			}
		}
		if (HussarBpmTypeEnum.DEVICE_SCRAP.getBpmMark().equals(orderType)) {
			DeviceScrapBpmNodeEnum[] values = DeviceScrapBpmNodeEnum.values();
			for (DeviceScrapBpmNodeEnum deviceScrapBpmNodeEnum : values) {
				dictValueVOList.add(DictValueVO.builder().node(deviceScrapBpmNodeEnum.getNode()).nodeName(deviceScrapBpmNodeEnum.getMessage())
					.sort(deviceScrapBpmNodeEnum.getSort()).type(deviceScrapBpmNodeEnum.getType()).build());
			}
		}
		if (HussarBpmTypeEnum.DEVICE_RETURNED.getBpmMark().equals(orderType)) {
			DeviceReturnedBpmNodeEnum[] values = DeviceReturnedBpmNodeEnum.values();
			for (DeviceReturnedBpmNodeEnum deviceReturnedBpmNodeEnum : values) {
				if (StringUtils.isNotBlank(orderNo) && DeviceReturnedBpmNodeEnum.DEVICE_RETURNED_PROFESSIONAL_REVIEW.getNode().equals(deviceReturnedBpmNodeEnum.getNode())) {
					continue;
				}
				dictValueVOList.add(DictValueVO.builder().node(deviceReturnedBpmNodeEnum.getNode()).nodeName(deviceReturnedBpmNodeEnum.getMessage())
					.sort(deviceReturnedBpmNodeEnum.getSort()).type(deviceReturnedBpmNodeEnum.getType()).build());
			}
		}
		if (HussarBpmTypeEnum.CHECK_TASK.getBpmMark().equals(orderType)) {
			CheckTaskBpmNodeEnum[] values = CheckTaskBpmNodeEnum.values();
			for (CheckTaskBpmNodeEnum checkTaskBpmNodeEnum : values) {
				dictValueVOList.add(DictValueVO.builder().node(checkTaskBpmNodeEnum.getNode()).nodeName(checkTaskBpmNodeEnum.getMessage())
					.sort(checkTaskBpmNodeEnum.getSort()).type(checkTaskBpmNodeEnum.getType()).build());
			}
		}
		if (HussarBpmTypeEnum.INTERFACE_APPLY.getBpmMark().equals(orderType)) {
			InterfaceBpmNodeEnum[] values = InterfaceBpmNodeEnum.values();
			for (InterfaceBpmNodeEnum interfaceBpmNodeEnum : values) {
				dictValueVOList.add(DictValueVO.builder().node(interfaceBpmNodeEnum.getNode()).nodeName(interfaceBpmNodeEnum.getMessage())
					.sort(interfaceBpmNodeEnum.getSort()).type(interfaceBpmNodeEnum.getType()).build());
			}
		}

		if (HussarBpmTypeEnum.SELF_EVALUTION.getBpmMark().equals(orderType)) {
			SelfEvaluationBpmNodeEnum[] values = SelfEvaluationBpmNodeEnum.values();
			for (SelfEvaluationBpmNodeEnum selfEvaluationBpmNodeEnum : values) {
				dictValueVOList.add(DictValueVO.builder().node(selfEvaluationBpmNodeEnum.getNode()).nodeName(selfEvaluationBpmNodeEnum.getMessage())
					.sort(selfEvaluationBpmNodeEnum.getSort()).type(selfEvaluationBpmNodeEnum.getType()).build());
			}
		}

		if (HussarBpmTypeEnum.REVIEW_LIBRARY.getBpmMark().equals(orderType)) {
			ReviewLibraryBpmNodeEnum[] values = ReviewLibraryBpmNodeEnum.values();
			for (ReviewLibraryBpmNodeEnum reviewLibraryBpmNodeEnum : values) {
				dictValueVOList.add(DictValueVO.builder().node(reviewLibraryBpmNodeEnum.getNode()).nodeName(reviewLibraryBpmNodeEnum.getMessage())
						.sort(reviewLibraryBpmNodeEnum.getSort()).type(reviewLibraryBpmNodeEnum.getType()).build());
			}
		}

		return dictValueVOList.stream().sorted(Comparator.comparing(DictValueVO::getSort)).collect(Collectors.toList());
	}

	@Override
	public com.lnsoft.core.tool.api.R send(String phone, String msg) {
		return com.lnsoft.core.tool.api.R.data(messageSendService.send(msg,phone));
	}
}
