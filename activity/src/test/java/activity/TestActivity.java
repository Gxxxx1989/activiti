package activity;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngineConfiguration;
import org.junit.Test;

public class TestActivity {
	/**
	 * ������������Ҫ��24�ű�
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
		//����������ڣ��Զ������� 
		processEngineConfiguration.setDatabaseSchemaUpdate(ProcessEngineConfiguration.DB_SCHEMA_UPDATE_TRUE);
		
		//�������ĺ��Ķ��� ��������
		ProcessEngine processEngine=processEngineConfiguration.buildProcessEngine();
		System.out.println("processEngine"+processEngine);
	}
	/**
	 * ʹ�������ļ�������
	 */
	@Test
	public void createTableFromxml() {
		ProcessEngineConfiguration processEngineConfiguration=ProcessEngineConfiguration.createProcessEngineConfigurationFromResource("activiti.cfg.xml");
		System.out.println("processEngineConfiguration"+processEngineConfiguration);
		ProcessEngine processEngine = processEngineConfiguration.buildProcessEngine();
		System.out.println("processEngine"+processEngine);
	}
}