package org.cloud.microservice.business.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * 项目名称：SimpleSpringCloudGateway
 * 包名称:com.service.demo.config
 * 类描述：
 * 创建人：hejian
 * 创建时间：2019/7/2 18:43
 * 修改人：hejian
 * 修改时间：2019/7/2 18:43
 * 修改备注：
 *
 * @author hejian
 */
@Configuration
public class WebFluxConfiguration {

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
