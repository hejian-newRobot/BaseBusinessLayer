package org.cloud.microservice.business.config;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.type.classreading.CachingMetadataReaderFactory;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.ClassUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * 项目名称：
 * 包名称:conf
 * 类描述：
 * 创建人：何健
 * 修改人：何健
 *
 * @author hejian
 */
@Configuration
@EnableConfigurationProperties(value = MybatisConfig.class)
@ConfigurationProperties(prefix = "ibatis.config")
public class MybatisConfig {

    private static Environment env = null;

    private static final String DEFAULT_RESOURCE_PATTERN = "**/*.class";

    private static String aliases = "com.fs.*.*.po";

    @Autowired
    public MybatisConfig(Environment env) {
        MybatisConfig.env = env;
    }

    /**
     * @return 从application.properties获取TypeAliasesPackage 需要被指定别名的类的包目录集合
     * note:并且检查配置的包目录是否合法
     */
    public static String getAliases() {
        if (StringUtils.isEmpty(aliases) && env != null) {
            aliases = checkTypeAliasesPackage(env
                    .getProperty("ibatis.aliases"));
        }
        return aliases;
    }

    /**
     * 检查传入的包名路径是否真实存在，如果存在便利匹配的所有路径下边是否都有class存在，没有则抛出异常，
     * 有则返回所有的具体包名路径，路径通过,号分割
     *
     * @param typeAliasesPackage 包路径
     * @return 返回解析后的多个包路径，路径通过,号分割
     */
    private static String checkTypeAliasesPackage(String typeAliasesPackage) {
        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        MetadataReaderFactory metadataReaderFactory = new CachingMetadataReaderFactory(
                resolver);
        typeAliasesPackage = ResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX
                + ClassUtils.convertClassNameToResourcePath(typeAliasesPackage)
                + "/" + DEFAULT_RESOURCE_PATTERN;
        try {
            List<String> result = new ArrayList<>();
            Resource[] resources = resolver.getResources(typeAliasesPackage);
            if (resources.length > 0) {
                MetadataReader metadataReader;
                for (Resource resource : resources) {
                    if (resource.isReadable()) {
                        metadataReader = metadataReaderFactory
                                .getMetadataReader(resource);
                        try {
                            result.add(Class
                                    .forName(
                                            metadataReader.getClassMetadata()
                                                    .getClassName())
                                    .getPackage().getName());
                        } catch (ClassNotFoundException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
            if (result.size() > 0) {
                HashSet<String> h = new HashSet<>(result);
                result.clear();
                result.addAll(h);
                typeAliasesPackage = String.join(",", (String[]) result.toArray(new String[0]));
                System.out.println(typeAliasesPackage);
            } else {
                throw new RuntimeException(
                        "mybatis aliases 路径扫描错误,参数typeAliasesPackage:"
                                + typeAliasesPackage + "未找到任何包");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return typeAliasesPackage;
    }

    public static void setAliases(String aliases) {
        MybatisConfig.aliases = aliases;
    }
}
