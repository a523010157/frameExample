package com.xubo.frameexample;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import org.apache.catalina.manager.StatusManagerServlet;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.HashMap;

@SpringBootApplication
public class FrameexampleApplication {

	public static void main(String[] args) {
		SpringApplication.run(FrameexampleApplication.class, args);
	}

	@Bean
	public ServletRegistrationBean druidServletRegistrationBean(){

		ServletRegistrationBean servletRegistrationBean = new ServletRegistrationBean();
		servletRegistrationBean.setServlet(new StatViewServlet());
		servletRegistrationBean.addUrlMappings("/druid/*");
		return servletRegistrationBean;

	}

	@Bean
	public ServletRegistrationBean tomcatServletRegistrationBean(){

		ServletRegistrationBean servletRegistrationBean = new ServletRegistrationBean();
		servletRegistrationBean.setServlet(new StatusManagerServlet());
		servletRegistrationBean.addUrlMappings("/tomcat/*");
		return servletRegistrationBean;

	}

	@Bean
	public FilterRegistrationBean druidFilterRegistrationBean(){
		FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
		filterRegistrationBean.setFilter(new WebStatFilter());
		HashMap<String, String> initParams = new HashMap<>();
		initParams.put("exclusions", "*.js,*.gif,*.jpg,*.bmp,*.png,*.css,*.ico,/druid/*");
		filterRegistrationBean.setInitParameters(initParams);
		filterRegistrationBean.addUrlPatterns("/*");
		return filterRegistrationBean;
	}

	@Bean(value = "dataSource")
	public DataSource druidDataSource() throws SQLException {

		DruidDataSource druidDataSource = new DruidDataSource();
		druidDataSource.setUsername("root");
		druidDataSource.setPassword("root");
		druidDataSource.setUrl("jdbc:mysql://127.0.0.1:3306/db_book");
		druidDataSource.setMaxActive(100);
		druidDataSource.setFilters("stat,wall");
		druidDataSource.setInitialSize(10);
		return druidDataSource;

	}

	@Bean
	public ServerEndpointExporter serverEndpointExporter() {
		return new ServerEndpointExporter();
	}

}
