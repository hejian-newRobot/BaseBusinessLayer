package org.cloud.microservice.business.interceptor;

import org.apache.commons.lang.StringUtils;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Enumeration;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

import javax.servlet.http.HttpServletRequest;

import feign.RequestInterceptor;
import feign.RequestTemplate;


/**
 * 处理Feign调用其他系统的时候，往请求头里面加上 token这个参数
 * RequestInterceptor
 *
 * @author hejian
 * 2019/6/25 12:30
 */
@Configuration
@EnableConfigurationProperties(FeignAuthInterceptor.class)
@ConfigurationProperties(prefix = "spring.cloud.feign.interceptor")
public class FeignAuthInterceptor implements RequestInterceptor {

    private String authorization = "Authorization";

    private String accessToken = "access_token";

    @Override
    public void apply(RequestTemplate template) {
        HttpServletRequest request = Objects.requireNonNull(getHttpServletRequest());
        String authorization = getHeaders(request).get(this.authorization);
        if (!StringUtils.isEmpty(authorization)) {
            template.header(this.authorization, authorization);
        } else {
            template.query(accessToken, getParams(request).get(accessToken));
        }
    }

    private HttpServletRequest getHttpServletRequest() {
        try {
            return ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
        } catch (Exception e) {
            return null;
        }
    }

    private Map<String, String> getHeaders(HttpServletRequest request) {
        Map<String, String> map = new LinkedHashMap<>();
        Enumeration<String> enumeration = request.getHeaderNames();
        while (enumeration.hasMoreElements()) {
            String key = enumeration.nextElement();
            String value = request.getHeader(key);
            map.put(key, value);
        }
        return map;
    }

    private Map<String, String> getParams(HttpServletRequest request) {
        Map<String, String> map = new LinkedHashMap<>();
        Enumeration<String> parameterNames = request.getParameterNames();
        while (parameterNames.hasMoreElements()) {
            String key = parameterNames.nextElement();
            String value = String.valueOf(request.getParameter(key));
            map.put(key, value);
        }
        return map;
    }

    public final String getAuthorization() {
        return authorization;
    }

    public final void setAuthorization(String authorization) {
        this.authorization = authorization;
    }

    public final String getAccessToken() {
        return accessToken;
    }

    public final void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
}
