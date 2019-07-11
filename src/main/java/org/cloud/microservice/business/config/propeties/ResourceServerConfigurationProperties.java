package org.cloud.microservice.business.config.propeties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * 项目名称：gateway-server
 * 包名称:config.properties
 * 类描述：
 * 创建人：hejian
 * 创建时间：2019/7/11 16:59
 * 修改人：hejian
 * 修改时间：2019/7/11 16:59
 * 修改备注：
 *
 * @author hejian
 */
@Configuration
@ConfigurationProperties(prefix = "resource.server.configuration")
public class ResourceServerConfigurationProperties {

    /**
     * 允许不需要认证的url虚拟路径后缀列表
     *
     * @apiNote 默认没有允许的路径
     */
    private String[] permitted = new String[0];

    /**
     * 需要认证的url虚拟路径后缀列表
     *
     * @apiNote 默认所有路径都需要认证
     */
    private String[] authenticated = new String[]{"/**"};

    public final String[] getPermitted() {
        return permitted;
    }

    public final void setPermitted(String[] permitted) {
        this.permitted = permitted;
    }

    public final String[] getAuthenticated() {
        return authenticated;
    }

    public final void setAuthenticated(String[] authenticated) {
        this.authenticated = authenticated;
    }
}
