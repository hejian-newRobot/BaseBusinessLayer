package org.cloud.microservice.business.config;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

/**
 * 项目名称：gateway-server
 * 包名称:common
 * 类描述：
 * 创建人：hejian
 * 创建时间：2019/7/16 15:37
 * 修改人：hejian
 * 修改时间：2019/7/16 15:37
 * 修改备注：
 *
 * @author hejian
 */
@Configuration
public class JacksonConfiguration {

    /**
     * 序列化器 bean注入
     * <p>
     * 1. 设置 NullValueSerializer null值转空串
     *
     * @return 返回{@code ObjectMapper}
     */
    @Bean
    @ConditionalOnProperty(name = "spring.jackson.serializer.null.enable", havingValue = "true", matchIfMissing = true)
    public ObjectMapper objectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.getSerializerProvider().setNullValueSerializer(new JsonSerializer<Object>() {
            @Override
            public void serialize(Object param, JsonGenerator paramJsonGenerator,
                                  SerializerProvider paramSerializerProvider) throws IOException {
                //设置返回null转为 空字符串""
                paramJsonGenerator.writeString("");
            }
        });
        return objectMapper;
    }
}
