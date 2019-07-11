package org.cloud.microservice.business.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Scope;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.util.FileCopyUtils;

import java.io.IOException;

/**
 * @author hejian
 */
@Configuration
@EnableConfigurationProperties(JwtConfig.class)
@ConfigurationProperties(prefix = "spring.security.jwt.cert")
public class JwtConfig {

    /**
     * 公钥名称
     */
    private String pub = "public.cert";

    @Primary
    @Scope("singleton")
    @Bean("tokenStore")
    public TokenStore tokenStore(@Qualifier("jwtTokenConverter") JwtAccessTokenConverter jwtAccessTokenConverter) {
        return new JwtTokenStore(jwtAccessTokenConverter(jwtAccessTokenConverter));
    }

    @Bean("jwtTokenConverter")
    @ConditionalOnBean(name = {"tokenStore"})
    protected JwtAccessTokenConverter jwtAccessTokenConverter(JwtAccessTokenConverter converter) {
        Resource resource = new ClassPathResource(pub);
        String publicKey;
        try {
            publicKey = new String(FileCopyUtils.copyToByteArray(resource.getInputStream()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        converter.setVerifierKey(publicKey);
        return converter;
    }

    public final String getPub() {
        return pub;
    }

    public final void setPub(String pub) {
        this.pub = pub;
    }
}
