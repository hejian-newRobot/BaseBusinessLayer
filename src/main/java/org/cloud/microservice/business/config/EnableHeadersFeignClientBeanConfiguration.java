package org.cloud.microservice.business.config;

import org.springframework.cloud.openfeign.support.SpringMvcContract;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Scope;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Enumeration;
import java.util.Objects;

import javax.servlet.http.HttpServletRequest;

import feign.RequestInterceptor;

/**
 * 项目名称：Business
 * 包名称:org.cloud.microservice.business.config
 * 类描述：
 * 创建人：hejian
 * 创建时间：2019/7/17 20:46
 * 修改人：hejian
 * 修改时间：2019/7/17 20:46
 * 修改备注：
 *
 * @author hejian
 */
@Configuration
public class EnableHeadersFeignClientBeanConfiguration {

    @Bean
    @Scope("singleton")
    @Primary
    public SpringMvcContract springMvcContract() {
        return new SpringMvcContract();
    }

    @Bean("HeaderRequestInterceptor")
    public RequestInterceptor headerInterceptor() {
        return template -> {
            ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            HttpServletRequest request = Objects.requireNonNull(attributes).getRequest();
            Enumeration<String> headerNames = request.getHeaderNames();
            if (headerNames != null) {
                while (headerNames.hasMoreElements()) {
                    String name = headerNames.nextElement();
                    String values = request.getHeader(name);
                    template.header(name, values);
                }
            }
        };
    }
}
