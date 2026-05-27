package com.lnsoft.device.task;

import com.lnsoft.core.tool.utils.RedisUtil;
import com.lnsoft.device.api.cmdb.service.ICmdbService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Component;

/**
 * @Author: xuel
 * @CreateTime: 2024/3/11 14:16
 * @Description: cmdbtask CmdbTask
 */
@EnableScheduling
@Component
@EnableAsync
@AllArgsConstructor
public class ErpTask {

	private RedisUtil redisUtil;
	private ICmdbService iCmdbService;
	public static final String YYYY_MM_DD = "yyyy-MM-dd";
	private static final Logger LOGGER = LoggerFactory.getLogger(ErpTask.class);

	// /**
	//  * 获取ERP的维护工厂
	//  * 每月的第一天的 0点10分 执行
	//  */
	// @Scheduled(cron = "* 10 0 1 * ?")
	// @Async
	// public void refreshUseAge() {
	// 	try {
	// 		LOGGER.info("刷新获取ERP的维护工厂开始!");
	// 		String format = DateTimeFormatter.ofPattern(YYYY_MM_DD).format(LocalDateTime.now());
	// 		if (Objects.isNull(redisUtil.get(CacheNames.GENERATE_CMDB_TASK_USE_AGE + format))) {
	// 			redisUtil.set(CacheNames.GENERATE_CMDB_TASK_USE_AGE + format, 1, 1800);
	// 			iCmdbService.refreshUseAge(format);
	// 		}
	// 		LOGGER.info("刷新获取ERP的维护工厂结束!");
	// 	} catch (Exception e) {
	// 		throw new RuntimeException(e.getMessage());
	// 	}
	// }
	//
	// /**
	//  * 获取ERP的成本中心
	//  * 每天 0点10分 执行
	//  */
	// @Scheduled(cron = "* 10 0 * * ?")
	// @Async
	// public void refreshBecomeDueAssets() {
	// 	try {
	// 		LOGGER.info("刷新转资到期开始!");
	// 		String format = DateTimeFormatter.ofPattern(YYYY_MM_DD).format(LocalDateTime.now());
	// 		if (Objects.isNull(redisUtil.get(CacheNames.GENERATE_CMDB_TASK_ASSETS + format))) {
	// 			redisUtil.set(CacheNames.GENERATE_CMDB_TASK_ASSETS + format, 1, 1800);
	// 			iCmdbService.refreshBecomeDueAssets(format);
	// 		}
	// 		LOGGER.info("刷新转资到期结束!");
	// 	} catch (Exception e) {
	// 		throw new RuntimeException(e.getMessage());
	// 	}
	// }


}
