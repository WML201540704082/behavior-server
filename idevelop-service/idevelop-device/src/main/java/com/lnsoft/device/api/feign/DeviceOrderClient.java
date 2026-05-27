package com.lnsoft.device.api.feign;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.lang.TypeReference;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lnsoft.common.enums.device.WorkOrderTypeEnum;
import com.lnsoft.core.mp.support.Query;
import com.lnsoft.core.tool.api.R;
import com.lnsoft.core.tool.constant.IdevelopConstant;
import com.lnsoft.device.api.operation.service.IDeviceRepairService;
import com.lnsoft.device.api.warehouse.service.IDeviceOperationService;
import com.lnsoft.device.api.warehouse.service.IDeviceReturnedService;
import com.lnsoft.device.dto.DataDeviceOperationDTO;
import com.lnsoft.device.dto.DeviceRepairDTO;
import com.lnsoft.device.dto.DeviceReturnedDTO;
import com.lnsoft.device.dto.NumberDataDTO;
import com.lnsoft.device.entity.DeviceRepair;
import com.lnsoft.device.entity.DeviceReturned;
import com.lnsoft.device.feign.IDeviceOrderClient;
import com.lnsoft.device.utils.OrderNumberUtil;
import com.lnsoft.device.vo.DataDeviceOperationVO;
import com.lnsoft.device.vo.DeviceRepairVO;
import com.lnsoft.device.vo.DeviceReturnedVO;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import javax.annotation.Resource;

@ApiIgnore
@RestController
public class DeviceOrderClient implements IDeviceOrderClient {

	@Resource
	private OrderNumberUtil orderNumberUtil;
	@Resource
	private IDeviceOperationService deviceOperationService;
	@Resource
	private IDeviceRepairService deviceRepairService;
	@Resource
	private IDeviceReturnedService deviceReturnedService;

	@PostMapping(API_PREFIX + "/createOrderNumber")
	@Override
	public String createOrderNumber(NumberDataDTO numberDataDTO) {
		if ("BG".equals(numberDataDTO.getOrderType())) {
			return orderNumberUtil.generateNumberData(WorkOrderTypeEnum.BG, numberDataDTO.getRegionCode());
		}
		if ("YDGJ".equals(numberDataDTO.getOrderType())) {
			return orderNumberUtil.generateNumberData(WorkOrderTypeEnum.YDGJ, numberDataDTO.getRegionCode());
		}
		if ("BX".equals(numberDataDTO.getOrderType())){
			return orderNumberUtil.generateNumberData(WorkOrderTypeEnum.BX,numberDataDTO.getRegionCode());
		}
		if ("TYD".equals(numberDataDTO.getOrderType())){
			return orderNumberUtil.generateNumberData(WorkOrderTypeEnum.TYD,numberDataDTO.getRegionCode());
		}
		if ("FK".equals(numberDataDTO.getOrderType())){
			return orderNumberUtil.generateNumberData(WorkOrderTypeEnum.FK,numberDataDTO.getRegionCode());
		}
		if ("BZXHK".equals(numberDataDTO.getOrderType())){
			return orderNumberUtil.generateNumberData(WorkOrderTypeEnum.FK,numberDataDTO.getRegionCode());
		}
		if (WorkOrderTypeEnum.SE.equals(numberDataDTO.getWorkOrderTypeEnum())) {
			return orderNumberUtil.generateNumber(WorkOrderTypeEnum.SE,numberDataDTO.getRegionCode());
		}
		if (WorkOrderTypeEnum.RL.equals(numberDataDTO.getWorkOrderTypeEnum())) {
			return orderNumberUtil.generateNumber(WorkOrderTypeEnum.RL,numberDataDTO.getRegionCode());
		}
		return null;
	}

	/**
	 * 生成工单编号
	 *
	 * @param numberDataDTO 生成编号参数
	 * @return R
	 */
	@Override
	public R<String> getOrderNumber(NumberDataDTO numberDataDTO) {
		if (WorkOrderTypeEnum.SE.equals(numberDataDTO.getWorkOrderTypeEnum())) {
			return R.data(orderNumberUtil.generateNumber(WorkOrderTypeEnum.SE,numberDataDTO.getRegionCode()));
		}
		if (WorkOrderTypeEnum.RL.equals(numberDataDTO.getWorkOrderTypeEnum())) {
			return R.data(orderNumberUtil.generateNumber(WorkOrderTypeEnum.RL,numberDataDTO.getRegionCode()));
		}

		return R.fail("生成工单编号失败!");
	}

	@PostMapping(API_PREFIX + "/createOrderOperationData")
	@Override
	public R<DataDeviceOperationVO> submitOperationData(DataDeviceOperationDTO deviceOperationDTO) throws Exception {
		R<com.lnsoft.device.api.warehouse.vo.DeviceOperationVO> deviceOperationVOR = deviceOperationService.submitOperationData(Convert.convert(
			com.lnsoft.device.api.warehouse.dto.DeviceOperationDTO.class, deviceOperationDTO), deviceOperationDTO.getUser());
		return Convert.convert(new TypeReference<R<DataDeviceOperationVO>>() {
		}, deviceOperationVOR);
	}

	@Override
	@PostMapping(API_PREFIX + "/createOrderRepair")
	public R createOrderRepair(DeviceRepairDTO deviceRepairDTO) throws Exception {
		R submit = deviceRepairService.submit(deviceRepairDTO);
		return submit;
	}

	@Override
	@PostMapping(API_PREFIX + "/createOrderReturned")
	public R<DeviceReturnedVO> createOrderReturned(DeviceReturnedDTO deviceRepairDTO) throws Exception {
		R<DeviceReturnedVO> deviceReturnedVOR = deviceReturnedService.deviceReturnedSubmit(deviceRepairDTO);
       return deviceReturnedVOR;
	}

	@Override
	@PostMapping(API_PREFIX + "/getRepairCount")
	public String getRepairCount(String deviceCode) {
		DeviceRepairVO deviceRepairVO = new DeviceRepairVO();
		deviceRepairVO.setDeviceCode(deviceCode);
		Query query = new Query();
		IPage<DeviceRepair> deviceRepairIPage = deviceRepairService.deviceRepairPage(deviceRepairVO, query);
		long total = deviceRepairIPage.getTotal();
		return String.valueOf(total);
	}

	@Override
	@PostMapping(API_PREFIX + "/getDetailRepair")
	public R<DeviceRepair> getDetailRepair(String filingNo) {
		LambdaQueryWrapper<DeviceRepair> queryWrapper = new LambdaQueryWrapper<>();
		queryWrapper.eq(DeviceRepair::getFilingNo,filingNo).eq(DeviceRepair::getIsDeleted, IdevelopConstant.DB_NOT_DELETED);
		return R.data(deviceRepairService.getOne(queryWrapper));
	}

	@Override
	@PostMapping(API_PREFIX + "/getDetailReturned")
	public R<DeviceReturned> getDetailReturned(String filingNo) {
		LambdaQueryWrapper<DeviceReturned> queryWrapper = new LambdaQueryWrapper<>();
		queryWrapper.eq(DeviceReturned::getFilingNo,filingNo).eq(DeviceReturned::getIsDeleted,IdevelopConstant.DB_NOT_DELETED);
		return R.data(deviceReturnedService.getOne(queryWrapper));
	}
}
