package org.cloud.microservice.business.config;

import org.cloud.microservice.business.interceptor.FeignAuthInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.cloud.openfeign.support.SpringMvcContract;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Scope;

import feign.Feign;
import feign.RequestInterceptor;

/**
 * 项目名称：SimpleSpringCloudGateway
 * 包名称:com.service.demo.config
 * 类描述：带有对验证信息做处理的拦截器的feign client的配置
 * 创建人：hejian
 * 创建时间：2019/6/25 14:17
 * 修改人：hejian
 * 修改时间：2019/6/25 14:17
 * 修改备注：
 *
 * @author hejian
 */
@Configuration
public class AuthFeignClientBeanConfiguration {

    @Bean
    @Primary
    @Scope("singleton")
    public RequestInterceptor requestInterceptor() {
        return new FeignAuthInterceptor();
    }

    @Bean
    @Scope("prototype")
    public Feign.Builder feignBuilder(RequestInterceptor requestInterceptor) {
        return Feign.builder().requestInterceptor(requestInterceptor);
    }
}
