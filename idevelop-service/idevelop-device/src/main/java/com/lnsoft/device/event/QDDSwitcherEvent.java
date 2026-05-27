package com.lnsoft.device.event;

import org.springframework.context.ApplicationEvent;

import java.time.Clock;

/**
 * @Author: xuel
 * @CreateTime: 2024/3/27 20:00
 * @Description: cmdb监听事件 CmdbEvent
 */
public class QDDSwitcherEvent extends ApplicationEvent {


	public QDDSwitcherEvent(Object source) {
		super(source);

	}

	public QDDSwitcherEvent(Object source, Clock clock) {
		super(source, clock);
	}
}
