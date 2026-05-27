package com.lnsoft.device.task;

import com.alibaba.fastjson.JSONObject;
import com.lnsoft.common.cache.CacheNames;
import com.lnsoft.core.tool.utils.RedisUtil;
import com.lnsoft.device.api.cmdb.service.ICmdbTaskService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.Objects;

/**
 * @Author: xuel
 * @CreateTime: 2024/3/11 14:16
 * @Description: cmdbtask CmdbTask
 */
@EnableScheduling
@Component
@EnableAsync
@AllArgsConstructor
public class CmdbTask {

	private RedisUtil redisUtil;
	private ICmdbTaskService cmdbTaskService;
	public static final String YYYY_MM_DD = "yyyy-MM-dd";
	private static final Logger LOGGER = LoggerFactory.getLogger(CmdbTask.class);

	/**
	 * 刷新转资到期
	 * 每天 0点10分 执行
	 */
	// @Scheduled(cron = "* 10 0 * * ?")
	@Async
	public void refreshBecomeDueAssets() {
		try {
			LOGGER.info("刷新转资到期开始!");
			String format = DateTimeFormatter.ofPattern(YYYY_MM_DD).format(LocalDateTime.now());
			if (Objects.isNull(redisUtil.get(CacheNames.GENERATE_CMDB_TASK_ASSETS + format))) {
				redisUtil.set(CacheNames.GENERATE_CMDB_TASK_ASSETS + format, 1, 1800);
				cmdbTaskService.refreshBecomeDueAssets(format);
			}
			LOGGER.info("刷新转资到期结束!");
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}
	}

	/**
	 * 刷新投运年限
	 * 每月的第一天的 2点10分 执行
	 */
	// @Scheduled(cron = "* 10 2 1 * ?")
	@Async
	public void refreshUseAge() {
		try {
			LOGGER.info("刷新投运年限开始!");
			String format = DateTimeFormatter.ofPattern(YYYY_MM_DD).format(LocalDateTime.now());
			if (Objects.isNull(redisUtil.get(CacheNames.GENERATE_CMDB_TASK_USE_AGE + format))) {
				redisUtil.set(CacheNames.GENERATE_CMDB_TASK_USE_AGE + format, 1, 1800);
				cmdbTaskService.refreshUseAge(format);
			}
			LOGGER.info("刷新投运年限结束!");
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}
	}


	/**
	 * 手动触发异步 刷新固定值 任务
	 */
	@Async
	public void refreshFixedValue(Map<String, Object> fixedValueMap) {
		try {
			LocalDateTime startTime = LocalDateTime.now();
			LOGGER.info("手动触发异步 刷新固定值 任务开始! ");
			Object requestAttr = fixedValueMap.get("requestAttr");
			if (Objects.isNull(redisUtil.get(CacheNames.GENERATE_CMDB_TASK_FIXEDVALUE + requestAttr))) {
				redisUtil.set(CacheNames.GENERATE_CMDB_TASK_FIXEDVALUE + requestAttr, 1, 1800);
				cmdbTaskService.refreshFixedValue(fixedValueMap);
			}
			LOGGER.info("手动触发异步 刷新固定值 任务结束, 开始时间为: " + startTime + ", 结束时间为: " + LocalDateTime.now());
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}
	}

	/**
	 * 手动触发异步 地市需求字段 任务
	 */
	@Async
	public void refreshUnitDept(JSONObject fixedValueMap) {
		try {
			LocalDateTime startTime = LocalDateTime.now();
			LOGGER.info("手动触发异步 地市需求字段 任务! ");
			Object requestAttr = fixedValueMap.get("requestAttr");
			if (Objects.isNull(redisUtil.get(CacheNames.GENERATE_CMDB_TASK_UNITDEPT + requestAttr))) {
				redisUtil.set(CacheNames.GENERATE_CMDB_TASK_UNITDEPT + requestAttr, 1, 1800);
				cmdbTaskService.refreshUnitDept(fixedValueMap);
			}
			LOGGER.info("手动触发异步 地市需求字段 任务, 开始时间为: " + startTime + ", 结束时间为: " + LocalDateTime.now());
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}
	}

}
