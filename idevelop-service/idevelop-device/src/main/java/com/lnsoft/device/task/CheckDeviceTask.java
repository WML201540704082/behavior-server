package com.lnsoft.device.task;

import com.lnsoft.common.cache.CacheNames;
import com.lnsoft.core.tool.utils.RedisUtil;
import com.lnsoft.device.api.cmdb.service.ICmdbService;
import com.lnsoft.device.api.warehouse.service.ICheckTaskDeviceService;
import com.lnsoft.device.api.warehouse.service.ICheckTaskService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

/**
 * @author cwb
 * @date 2024/5/21
 */
@EnableScheduling
@Component
@EnableAsync
@AllArgsConstructor
public class CheckDeviceTask {

	@Autowired
	private RedisUtil redisUtil;
	@Autowired
	private ICheckTaskService taskService;
	@Resource
	private ICheckTaskDeviceService deviceService;

	public static final String YYYY_MM_DD = "yyyy-MM-dd";
	private static final Logger LOGGER = LoggerFactory.getLogger(CheckDeviceTask.class);

	@Scheduled(cron = "0 0 5,9 * * ?")
	@Async
	public void checkTaskEnd() {
		try {
			LOGGER.info("盘点任务周期校验------!");
			String format = DateTimeFormatter.ofPattern(YYYY_MM_DD).format(LocalDateTime.now());
			if (Objects.isNull(redisUtil.get(CacheNames.CHECK_TASK_END + format))) {
				redisUtil.set(CacheNames.CHECK_TASK_END + format, 1, 1800);
				taskService.checkTaskEnd(format);
			}
			LOGGER.info("盘点任务周期校验结束!");
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}
	}
	@Scheduled(cron = "0 0 6,18 * * ?")
	@Async
	public void checkTaskNetwork() {
		try {
			LOGGER.info("盘点任务周期校验（恢复入网）------!");
			String format = DateTimeFormatter.ofPattern(YYYY_MM_DD).format(LocalDateTime.now());
			if (Objects.isNull(redisUtil.get(CacheNames.CHECK_TASK_NETWORK + format))) {
				redisUtil.set(CacheNames.CHECK_TASK_NETWORK + format, 1, 1800);
				deviceService.checkTaskNetwork();
			}
			LOGGER.info("盘点任务周期校验结束!（恢复入网）");
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}
	}
}
