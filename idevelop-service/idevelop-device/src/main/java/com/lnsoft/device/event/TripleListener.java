package com.lnsoft.device.event;

import com.lnsoft.common.constant.EventConstant;
import com.lnsoft.core.launch.server.ServerInfo;
import com.lnsoft.device.annotation.TripleApiLogA;
import com.lnsoft.device.api.cmdb.dto.TripleApiLogDTO;
import com.lnsoft.device.api.cmdb.service.ITripleApiLogService;
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
public class TripleListener {

	private final ITripleApiLogService tripleApiLogService;
	private final ServerInfo serverInfo;

	@Async
	@Order(1)
	@EventListener(TripleEvent.class)
	public void saveApiLog(TripleEvent event) {
		Map<String, Object> source = (Map<String, Object>) event.getSource();
		TripleApiLogDTO tripleApiLog = (TripleApiLogDTO) source.get(EventConstant.EVENT_TRIPLE_LOG);
		TripleApiLogA tripleApiLogA = tripleApiLog.getTripleApiLogA();
		tripleApiLog.setTripleType(tripleApiLogA.tripleType().getText());
		tripleApiLog.setValue(tripleApiLogA.value().getText());
		tripleApiLog.setIp(serverInfo.getIp());
		tripleApiLogService.save(tripleApiLog);
	}
}
