

package com.lnsoft.device.publisher;

import com.lnsoft.common.constant.EventConstant;
import com.lnsoft.core.tool.utils.SpringUtil;
import com.lnsoft.device.api.warehouse.entity.DeviceSdnQingDao;
import com.lnsoft.device.event.QDDSwitcherEvent;

import java.util.HashMap;
import java.util.Map;

/**
 * 青岛异步发送中间表信息事件发送
 *
 * @author guozhao
 */
public class QDDSwitcherPublisher {

	public static void publishEvent(DeviceSdnQingDao deviceSdnQingDao) {

		Map<String, Object> event = new HashMap<>(16);
		event.put(EventConstant.EVENT_QD_DSWITCHER_LOG, deviceSdnQingDao);
		SpringUtil.publishEvent(new QDDSwitcherEvent(event));
	}

}
