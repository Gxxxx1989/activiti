package activity.history;

import java.util.List;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngineConfiguration;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.history.HistoricProcessInstance;
import org.junit.Test;

public class History {
	ProcessEngine processEngine=ProcessEngineConfiguration.createProcessEngineConfigurationFromResource("activiti.cfg.xml")
			.buildProcessEngine();
	/**
	 * ��ѯ��ʷ����ʵ��
	 */
	@Test
	public void getHistoryProcessInstance(){
		String processInstanceId="67501";
		HistoricProcessInstance singleResult = processEngine.getHistoryService()
		.createHistoricProcessInstanceQuery()
		.processInstanceId(processInstanceId)
		.singleResult();
		System.out.println(singleResult.getBusinessKey());
		System.out.println(singleResult.getName());
		System.out.println(singleResult.getId());
	}
	/**
	 * ��ѯ��ʷ�
	 */
	@Test
	public void getHistoryActiviti(){
		String processInstanceId="40001";
		List<HistoricActivityInstance> list = processEngine.getHistoryService()
		.createHistoricActivityInstanceQuery()
		.processInstanceId(processInstanceId)
		.orderByHistoricActivityInstanceStartTime()
		.asc()
		.list();
		for (HistoricActivityInstance historicActivityInstance : list) {
			System.out.println(historicActivityInstance.getProcessInstanceId());
			System.out.println(historicActivityInstance.getStartTime());
			System.out.println(historicActivityInstance.getEndTime());
			System.out.println(historicActivityInstance.getActivityType());
			System.out.println("###########################################");
		}
	}
}