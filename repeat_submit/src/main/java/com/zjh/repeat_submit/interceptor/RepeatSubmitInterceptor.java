package com.zjh.repeat_submit.interceptor;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zjh.repeat_submit.annotation.RepeatSubmit;
import com.zjh.repeat_submit.redis.RedisCache;
import com.zjh.repeat_submit.request.RepeatableReadRequestWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Component
public class RepeatSubmitInterceptor implements HandlerInterceptor {

    public static final String REPEAT_PARAMS = "repeat_params";
    public static final String REPEAT_SUBMIT_KEY = "repeat_submit_key";
    public static final String REPEAT_TIME = "repeat_time";
    public static final String HEADER = "Authorization";

    @Autowired
    RedisCache redisCache;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            Method method = handlerMethod.getMethod();
            RepeatSubmit repeatSubmit = method.getAnnotation(RepeatSubmit.class);
            if (repeatSubmit != null) {
                if (isRepeatSubmit(request , repeatSubmit)){
                    HashMap<String, Object> map = new HashMap<>();
                    map.put("status", 500);
                    map.put("message", repeatSubmit.message());
                    response.setContentType("application/json;charset=utf-8");
                    response.getWriter().write(new ObjectMapper().writeValueAsString(map));
                    return false;
                }
            }
        }
        return true;
    }

    private boolean isRepeatSubmit(HttpServletRequest request, RepeatSubmit repeatSubmit) {
        String nowParams = "";
        if (request instanceof RepeatableReadRequestWrapper) {
            try {
                nowParams = ((RepeatableReadRequestWrapper) request).getReader().readLine();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        //否则说明请求参数是key-value格式
        if (StringUtils.isEmpty(nowParams)){
            try {
                nowParams = new ObjectMapper().writeValueAsString(request.getParameterMap());
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        }
        HashMap<String, Object> nowDataMap = new HashMap<>();
        nowDataMap.put(REPEAT_PARAMS,nowDataMap);
        nowDataMap.put(REPEAT_TIME,System.currentTimeMillis());
        StringBuffer requestURI = request.getRequestURL();
        String header = request.getHeader(HEADER);
        String cacheKey = REPEAT_SUBMIT_KEY + requestURI + header.replace("Bearer", "");
        Object cacheObject = redisCache.getCacheObject(cacheKey);
        if (cacheObject != null){
            Map<String,Object> map = (Map<String, Object>) cacheObject;
            if (compareParams(map,nowDataMap) && compareTime(map,nowDataMap,repeatSubmit.interval())){
                return true;
            }
        }
        redisCache.setCacheObject(cacheKey,nowDataMap, repeatSubmit.interval(), TimeUnit.MILLISECONDS);
        return false;
    }

    private boolean compareTime(Map<String, Object> map, HashMap<String, Object> nowDataMap, int interval) {
        Long time1 = (Long) map.get(REPEAT_TIME);
        Long time2 = (Long) nowDataMap.get(REPEAT_TIME);
        if ( (time2-time1) < interval ) {
            return true;
        }
        return false;
    }

    private boolean compareParams(Map<String, Object> map, HashMap<String, Object> nowDataMap) {
        String nowParams = (String) nowDataMap.get(REPEAT_PARAMS);
        String dataParams = (String) map.get(REPEAT_PARAMS);
        return nowParams.equals(dataParams);
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }
}
