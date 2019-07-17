package org.cloud.microservice.business.config;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.servlet.ServletContext;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.paths.RelativePathProvider;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * 项目名称：SimpleSpringCloudGateway
 * 包名称:com.service.demo.config
 * 类描述：
 * 创建人：hejian
 * 创建时间：2019/7/3 12:30
 * 修改人：hejian
 * 修改时间：2019/7/3 12:30
 * 修改备注：
 *
 * @author hejian
 */
@Primary
@Configuration
@EnableConfigurationProperties(value = {SwaggerConfig.class, SwaggerConfig.SwaggerInfo.class})
@ConfigurationProperties(prefix = "swagger.config")
@EnableSwagger2
public class SwaggerConfig {

    /**
     * swagger 信息
     */
    private final SwaggerInfo swaggerInfo;
    /**
     * 是否启用Swagger
     */
    private boolean enable = true;
    /**
     * 扫描接口的路径
     */
    private String path = "";

    /**
     * swagger url base path 基础路径
     */
    private String basePath = "";
    /**
     * swagger host
     */
    private String host = "localhost:8080";

    @Autowired
    public SwaggerConfig(SwaggerInfo swaggerInfo) {
        this.swaggerInfo = swaggerInfo;
    }

    @Bean
    public Docket api(ServletContext servletContext) {
        return new Docket(DocumentationType.SWAGGER_2)
                .host(host)
                .pathProvider(new RelativePathProvider(servletContext) {
                    @Override
                    public String getApplicationBasePath() {
                        return basePath;
                    }
                })
                .apiInfo(apiInfo())
                .enable(enable)
                .select()
                // 包路径
                .apis(StringUtils.isEmpty(path)
                        ? RequestHandlerSelectors.any()
                        : RequestHandlerSelectors.basePackage(path))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title(swaggerInfo.getTitle())
                .description(swaggerInfo.getDescription())
                .termsOfServiceUrl(swaggerInfo.getTermsOfServiceUrl())
                .version(swaggerInfo.getVersion())
                .build();
    }

    public final boolean isEnable() {
        return enable;
    }

    public final void setEnable(boolean enable) {
        this.enable = enable;
    }

    public final String getPath() {
        return path;
    }

    public final void setPath(String path) {
        this.path = path;
    }

    public final String getBasePath() {
        return basePath;
    }

    public final void setBasePath(String basePath) {
        this.basePath = basePath;
    }

    public final String getHost() {
        return host;
    }

    public final void setHost(String host) {
        this.host = host;
    }

    @ConfigurationProperties(prefix = "swagger.api")
    static class SwaggerInfo {

        private String title = "api文档";

        private String description = "restful 风格接口";

        private String version = "1.0";

        private String contact = "fastspeedforme@gmail.com";

        private String license;

        private String termsOfServiceUrl;

        private String licenseUrl;

        public final String getTitle() {
            return title;
        }

        public final void setTitle(String title) {
            this.title = title;
        }

        public final String getDescription() {
            return description;
        }

        public final void setDescription(String description) {
            this.description = description;
        }

        public final String getVersion() {
            return version;
        }

        public final void setVersion(String version) {
            this.version = version;
        }

        public final String getContact() {
            return contact;
        }

        public final void setContact(String contact) {
            this.contact = contact;
        }

        public final String getLicense() {
            return license;
        }

        public final void setLicense(String license) {
            this.license = license;
        }

        public final String getTermsOfServiceUrl() {
            return termsOfServiceUrl;
        }

        public final void setTermsOfServiceUrl(String termsOfServiceUrl) {
            this.termsOfServiceUrl = termsOfServiceUrl;
        }

        public final String getLicenseUrl() {
            return licenseUrl;
        }

        public final void setLicenseUrl(String licenseUrl) {
            this.licenseUrl = licenseUrl;
        }
    }
}
