package com.lnsoft.common.config;

import org.apache.skywalking.apm.toolkit.trace.TraceContext;
import org.slf4j.MDC;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TraceInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        MDC.put("threadId",String.valueOf(Thread.currentThread().getId()));
        MDC.put("traceId" , TraceContext.traceId());
        MDC.put("spanId",String.valueOf(TraceContext.spanId()));
        MDC.put("SYSCODE","QLL");
        MDC.put("reqTime",new SimpleDateFormat("YYYY-MM-DD HH:MM:SS sss").format(new Date()));
        MDC.put("reqURI",request.getRequestURI());
        MDC.put("reqURL",request.getRequestURL().toString());
        MDC.put("Extend","count|0");
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        MDC.clear();
    }
}
