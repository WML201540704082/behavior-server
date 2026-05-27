package com.lnsoft.device.event;

import com.lnsoft.common.constant.EventConstant;
import com.lnsoft.device.api.warehouse.entity.DeviceSdnQingDao;
import com.lnsoft.device.api.warehouse.utils.DSwitcherSyncUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @Author: xuel
 * @CreateTime: 2024/3/29 10:11
 * @Description: TripleListener
 */
@Slf4j
@Component
@AllArgsConstructor
public class QDDSwitcherListener {

	private DSwitcherSyncUtil dSwitcherSyncUtil;

	@Async
	@Order(1)
	@EventListener(QDDSwitcherEvent.class)
	public void saveApiLog(QDDSwitcherEvent event) {
		try {
			Map<String, Object> source = (Map<String, Object>) event.getSource();
			DeviceSdnQingDao sdnQingDao = (DeviceSdnQingDao) source.get(EventConstant.EVENT_QD_DSWITCHER_LOG);
			dSwitcherSyncUtil.deviceSdnQingDao(sdnQingDao);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
