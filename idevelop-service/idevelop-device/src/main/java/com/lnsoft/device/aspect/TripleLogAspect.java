package com.lnsoft.device.aspect;

import com.alibaba.fastjson.JSONObject;
import com.lnsoft.device.annotation.TripleApiLogA;
import com.lnsoft.device.publisher.TripleLogPublisher;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * @Author: xuel
 * @CreateTime: 2024/3/27 19:31
 * @Description: CmdbAddLogAspect
 */
@Aspect
@Component
public class TripleLogAspect {

	@Around("@annotation(tripleApiLogA)")
	public Object around(ProceedingJoinPoint point, TripleApiLogA tripleApiLogA) throws Throwable {
		Object requestObject = JSONObject.toJSON(point.getArgs());
		String className = point.getTarget().getClass().getName();
		String methodName = point.getSignature().getName();
		LocalDateTime beginTime = LocalDateTime.now();
		try {
			Object result = point.proceed();
			LocalDateTime endTime = LocalDateTime.now();
			TripleLogPublisher.publishEvent(className, methodName, beginTime, endTime, requestObject, result, 0, tripleApiLogA);
			return result;
		} catch (Exception e) {
			LocalDateTime endTime = LocalDateTime.now();
			TripleLogPublisher.publishEvent(className, methodName, beginTime, endTime, requestObject, e.getMessage(), 1, tripleApiLogA);
			throw new RuntimeException(e);
		}

	}

}
