package org.cloud.microservice.business.config;

import org.cloud.microservice.business.config.propeties.ResourceServerConfigurationProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;

/**
 * @author hejian
 */
@Configuration
@EnableResourceServer
@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableConfigurationProperties(ResourceServerConfigurationProperties.class)
public class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {

    private final TokenStore tokenStore;

    private final ResourceServerConfigurationProperties resourceServerConfigurationProperties;

    @Autowired
    public ResourceServerConfiguration(@Qualifier("tokenStore") TokenStore tokenStore,
                                       ResourceServerConfigurationProperties resourceServerConfigurationProperties) {
        this.tokenStore = tokenStore;
        this.resourceServerConfigurationProperties = resourceServerConfigurationProperties;
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests()
                .antMatchers(resourceServerConfigurationProperties.getPermitted()).permitAll()
                .antMatchers(resourceServerConfigurationProperties.getAuthenticated()).authenticated();
    }

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) {
        resources.tokenStore(tokenStore);
    }
}
