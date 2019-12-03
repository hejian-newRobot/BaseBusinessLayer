package org.cloud.microservice.business.config;

import com.zaxxer.hikari.HikariDataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

/**
 * 项目名称：business-spring-boot-starter
 * 包名称: org.cloud.microservice.business.config
 * 类描述：hisuser 库的数据源配置文件  datasource.hisuser.enable 是该配置的开关
 * 创建人：何健
 *
 * @author hejian
 */
@Configuration
@ConditionalOnClass({MybatisConfig.class, HikariDataSource.class, SqlSessionFactory.class})
@ConditionalOnProperty(name = "datasource.hisuser.enable", havingValue = "true")
@MapperScan(basePackages = "com.vdf.pms.*.*.dao", sqlSessionTemplateRef =
		"hisDBSqlSessionTemplate")
public class DataSourceConfigForHisUser {

	@Value("${ibatis.mapper.locations.hisuser}")
	private String mapperLocations;

	@Bean(name = "hisDB")
	@ConfigurationProperties(prefix = "datasource.hisuser")
	public DataSource hisDataSource() {
		return DataSourceBuilder.create().type(HikariDataSource.class).build();
	}

	@Bean(name = "hisDBSqlSessionFactory")
	public SqlSessionFactory hisSqlSessionFactory(@Qualifier("hisDB") DataSource dataSource) throws Exception {
		SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
		bean.setDataSource(dataSource);
		bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources(mapperLocations));
		bean.setTypeAliasesPackage(MybatisConfig.getAliases());
		return bean.getObject();
	}

	@Bean(name = "hisDBTransactionManager")
	public DataSourceTransactionManager hisTransactionManager(@Qualifier("hisDB") DataSource dataSource) {
		return new DataSourceTransactionManager(dataSource);
	}

	@Bean(name = "hisDBSqlSessionTemplate")
	public SqlSessionTemplate hisSqlSessionTemplate(
			@Qualifier("hisDBSqlSessionFactory") SqlSessionFactory sqlSessionFactory) {
		return new SqlSessionTemplate(sqlSessionFactory);
	}

}

