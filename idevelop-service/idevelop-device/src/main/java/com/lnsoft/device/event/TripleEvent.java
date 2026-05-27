package com.lnsoft.device.event;

import org.springframework.context.ApplicationEvent;

import java.time.Clock;

/**
 * @Author: xuel
 * @CreateTime: 2024/3/27 20:00
 * @Description: cmdb监听事件 CmdbEvent
 */
public class TripleEvent extends ApplicationEvent {


	public TripleEvent(Object source) {
		super(source);

	}

	public TripleEvent(Object source, Clock clock) {
		super(source, clock);
	}
}
