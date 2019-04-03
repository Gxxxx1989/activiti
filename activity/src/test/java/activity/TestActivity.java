package activity;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngineConfiguration;
import org.junit.Test;

public class TestActivity {
	/**
	 * 创建工作流需要的24张表
	 */
	@Test
	public void createTableFromCode() {
		ProcessEngineConfiguration processEngineConfiguration = ProcessEngineConfiguration
				.createStandaloneInMemProcessEngineConfiguration();
		processEngineConfiguration.setJdbcDriver("com.mysql.jdbc.Driver");
		processEngineConfiguration
				.setJdbcUrl("jdbc:mysql://localhost:3306/activity?useUnicode=true&characterEncoding=utf-8&serverTimezone=GMT%2B8&useSSL=false");
		processEngineConfiguration.setJdbcUsername("root");
		processEngineConfiguration.setJdbcPassword("1234");
		//如果表不存在，自动创建表 
		processEngineConfiguration.setDatabaseSchemaUpdate(ProcessEngineConfiguration.DB_SCHEMA_UPDATE_TRUE);
		
		//工作流的核心对象 流程引擎
		ProcessEngine processEngine=processEngineConfiguration.buildProcessEngine();
		System.out.println("processEngine"+processEngine);
	}
	/**
	 * 使用配置文件创建表
	 */
	@Test
	public void createTableFromxml() {
		ProcessEngineConfiguration processEngineConfiguration=ProcessEngineConfiguration.createProcessEngineConfigurationFromResource("activiti.cfg.xml");
		System.out.println("processEngineConfiguration"+processEngineConfiguration);
		ProcessEngine processEngine = processEngineConfiguration.buildProcessEngine();
		System.out.println("processEngine"+processEngine);
	}
}
