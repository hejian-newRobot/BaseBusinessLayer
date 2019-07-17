package org.cloud.microservice.business.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import feign.Contract;

/**
 * 项目名称：Business
 * 包名称:org.cloud.microservice.business
 * 类描述：
 * 创建人：hejian
 * 创建时间：2019/7/17 21:50
 * 修改人：hejian
 * 修改时间：2019/7/17 21:50
 * 修改备注：
 *
 * @author hejian
 */
@Configuration
public class EnableDefaultContractFeignClientBeanConfiguration {

    @Bean
    public Contract feignConfiguration() {
        return new feign.Contract.Default();
    }
}
