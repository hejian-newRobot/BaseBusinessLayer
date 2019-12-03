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
 * 包名称:org.cloud.microservice.business.config
 * 类描述：
 * 创建人：hejian
 * 创建时间：2019-12-02 10:47
 * 修改人：hejian
 * 修改时间：2019-12-02 10:47
 * 修改备注：
 *
 * @author hejian
 */
@Configuration
@ConditionalOnClass({MybatisConfig.class, HikariDataSource.class, SqlSessionFactory.class})
@ConditionalOnProperty(name = "datasource.cloud-his-mg.enable", havingValue = "true")
@MapperScan(basePackages = "com.vdf.pms.*.*.dao", sqlSessionTemplateRef =
		"cloudHisMgDBSqlSessionTemplate")
public class DataSourceConfigCloudHisMgForPMS {

	@Value("${ibatis.mapper.locations.cloud-his-mg}")
	private String mapperLocations;

	@Bean(name = "cloudHisMgDB")
	@ConfigurationProperties(prefix = "datasource.cloud-his-mg")
	public DataSource cloudHisMgDataSource() {
		return DataSourceBuilder.create().type(HikariDataSource.class).build();
	}

	@Bean(name = "cloudHisMgDBSqlSessionFactory")
	public SqlSessionFactory cloudHisMgSqlSessionFactory(@Qualifier("cloudHisMgDB") DataSource dataSource) throws Exception {
		SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
		bean.setDataSource(dataSource);
		bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources(mapperLocations));
		bean.setTypeAliasesPackage(MybatisConfig.getAliases());
		return bean.getObject();
	}

	@Bean(name = "cloudHisMgDBTransactionManager")
	public DataSourceTransactionManager cloudHisMgTransactionManager(@Qualifier("cloudHisMgDB") DataSource dataSource) {
		return new DataSourceTransactionManager(dataSource);
	}

	@Bean(name = "cloudHisMgDBSqlSessionTemplate")
	public SqlSessionTemplate cloudHisMgSqlSessionTemplate(
			@Qualifier("cloudHisMgDBSqlSessionFactory") SqlSessionFactory sqlSessionFactory) {
		return new SqlSessionTemplate(sqlSessionFactory);
	}

}
