

package com.lnsoft.device.publisher;

import com.lnsoft.common.constant.EventConstant;
import com.lnsoft.core.pojo.IdevelopUser;
import com.lnsoft.core.secure.utils.SecureUtil;
import com.lnsoft.core.tool.utils.SpringUtil;
import com.lnsoft.device.annotation.TripleApiLogA;
import com.lnsoft.device.api.cmdb.dto.TripleApiLogDTO;
import com.lnsoft.device.event.TripleEvent;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * API日志信息事件发送
 *
 * @author guozhao
 */
public class TripleLogPublisher {

	public static void publishEvent(String className, String methodName,
									LocalDateTime beginTime, LocalDateTime endTime,
									Object requestObject, Object result, Integer success, TripleApiLogA tripleApiLogA) {
		IdevelopUser user = SecureUtil.getUser();
		TripleApiLogDTO tripleApiLogDTO = new TripleApiLogDTO();
		tripleApiLogDTO.setClassName(className);
		tripleApiLogDTO.setMethodName(methodName);
		tripleApiLogDTO.setStartTime(beginTime);
		tripleApiLogDTO.setEndTime(endTime);
		tripleApiLogDTO.setText(String.valueOf(requestObject));
		tripleApiLogDTO.setResult(String.valueOf(result));
		tripleApiLogDTO.setTripleApiLogA(tripleApiLogA);
		if (!Objects.isNull(user)) {
			tripleApiLogDTO.setRegionCode(StringUtils.hasLength(user.getRegionCode()) ? user.getRegionCode() : "");
			tripleApiLogDTO.setRegionName(StringUtils.hasLength(user.getRegionName()) ? user.getRegionName() : "");
			tripleApiLogDTO.setCreateUser(Objects.isNull(user.getUserId()) ? null : user.getUserId());
		}
		tripleApiLogDTO.setSuccess(success);


		Map<String, Object> event = new HashMap<>(16);
		event.put(EventConstant.EVENT_TRIPLE_LOG, tripleApiLogDTO);
		SpringUtil.publishEvent(new TripleEvent(event));
	}

}
