package activity.variables;

import java.io.InputStream;
import java.util.Date;
import java.util.List;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngineConfiguration;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricVariableInstance;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;
import org.h2.util.New;
import org.junit.Test;
public class ProcessVariables {
	
//	ProcessEngine processEngine=ProcessEngines.getDefaultProcessEngine();
	ProcessEngine processEngine=ProcessEngineConfiguration
			.createProcessEngineConfigurationFromResource("activiti.cfg.xml").buildProcessEngine();
	/**
	 * �������̶��� inputstream
	 * @author guoxi 
	 */
	@Test
	public void deploymentProcessDefinition_inputstream() {
		String resourceName1="ProcessVariables.bpmn";
		String resourceName2="ProcessVariables.png";
		//this.getClass().getResourceAsStream("/diagrams/ProcessVariables.bpmn");
		InputStream inputStream1=this.getClass().getClassLoader().getResourceAsStream("diagrams/ProcessVariables.bpmn");
		InputStream inputStream2=this.getClass().getClassLoader().getResourceAsStream("diagrams/ProcessVariables.png");
		 Deployment deploy = processEngine.getRepositoryService()/**�����̶���Ͳ�������ص�service*/
		 			  .createDeployment()/**����һ���������*/
		 			  .name("���̶���")/**���Ӳ�������*/
		 			  .addInputStream(resourceName1, inputStream1)
		 			  .addInputStream(resourceName2, inputStream2)
		 			  .deploy();
		 System.out.println("����deploy:id:"+deploy.getId());
		 System.out.println("����deploy:name:"+deploy.getName());
	}
	/**
	 * ��������ʵ��
	 * @author guoxi 
	 */
	@Test
	public  void startDeploymentInstance() {
		//���̶����key
		String processDefinitionKey="ProcessVariables";
		ProcessInstance processInstance = processEngine.getRuntimeService()/**������ִ�е�����ʵ����ִ�ж�����ص�service*/
		/**ʹ�����̶����key��������ʵ��,
		 * key��Ӧhelloworld.bpmn�ļ��� process id����ֵ��
		 * ʹ��key������Ĭ���ǰ������°汾�����̶�������*/
					  .startProcessInstanceByKey(processDefinitionKey);
		System.out.println("����ʵ����"+processInstance.getId());/**����ʵ��id*/
		System.out.println("���̶��壺"+processInstance.getProcessDefinitionId());/**���̶���id*/
	}
	/**�������̱���*/
	@Test
	public void setVariable(){
		//�������Դ���ַ�ʽ
		//��classpath�¼���ָ�����Ƶ��ļ�
		InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("diagrams/ProcessVariables.bpmn");
		//�ӵ�ǰ���¼���ָ���ļ�
		InputStream inputStream2 = this.getClass().getResourceAsStream("");
		//��classpath�¼���ָ�����Ƶ��ļ�
		InputStream inputStream3 = this.getClass().getResourceAsStream("/");
		//����id
		String taskId="60004";
//		processEngine.getTaskService().setVariableLocal(taskId, "�������", 3);//local����������id��
//		processEngine.getTaskService().setVariable(taskId, "�������", new Date());
//		processEngine.getTaskService().setVariable(taskId, "���ԭ��", "�ؼ�");
		/**�������̱��� ʹ��javabean*/
		Person person=new Person();
		person.setId(1);
		person.setName("gxxx");
		processEngine.getTaskService().setVariable(taskId, "��Ա��Ϣ", person);
		System.out.println("�������̱����ɹ�");
		
		
	}
	/**��ȡ���̱���*/
	@Test
	public void getProcessVariables(){
		String taskId="60004";
//		Integer days = (Integer) processEngine.getTaskService().getVariable(taskId, "�������");
//		Date date = (Date) processEngine.getTaskService().getVariable(taskId, "�������");
//		String reason = (String) processEngine.getTaskService().getVariable(taskId, "���ԭ��");
//		System.out.println("days"+days);
//		System.out.println("date"+date);
//		System.out.println("reason"+reason);
		/**ʹ��javabean����*/
		Person person =(Person) processEngine.getTaskService().getVariable(taskId, "��Ա��Ϣ");
		System.out.println(person.getId());
		System.out.println(person.getName());
		
	}
	
	/**ģ�����úͻ�ȡ���̱�������*/
	public void setVariables() {
		/**������ʵ��ִ�ж������(����ִ��)*/
		RuntimeService runtimeService = processEngine.getRuntimeService();
		/**���������(����ִ��)*/
		TaskService taskService = processEngine.getTaskService();
		/**��ʩ���̱���*/
//		runtimeService.setVariable(executionId, variableName, value);//��ʾʹ��ִ�ж���id �����̱����������������̱�����ֵ��ÿ��ֻ������һ��ֵ��
//		runtimeService.setVariables(executionId, variables);//��ʾʹ��ִ�ж���id��map�������������̱�����map��key�������̱������ƣ�value�������̱�ɵ�ֵ
//		taskService.setVariable(taskId, variableName, value);//��ʾʹ������id �����̱����������������̱�����ֵ��ÿ��ֻ������һ��ֵ��
//		taskService.setVariables(taskId, variables);//��ʾʹ������id ��map�������������̱�����map��key�������̱������ƣ�value�������̱�ɵ�ֵ
//		runtimeService.startProcessInstanceByKey(processDefinitionKey, variables);//��������ʵ��ͬʱ�������̱���
//		taskService.complete(taskId, variables);//��������ͬʱ�������̱���
		
		
//		runtimeService.getVariable(executionId, variableName);//ʹ��ִ�ж���id�����̱������ƻ�ȡ���̱�����ֵ
//		runtimeService.getVariables(executionId)//ʹ��ִ�ж���id ��ȡ���е����̱��� �����̱����ŵ�map��
//		runtimeService.getVariables(executionId, variableNames);//ʹ��ִ�ж���id ��ȡ���̱�����ֵ ͨ���������̱������� ���ڼ����� ��ȡָ�����̱������Ƶ����̱���ֵ ֵҲ�Ǵ���map��
	
	}
	
	@Test
	public void comlepeMyPersonalTask() {
		String taskId="75002";
		processEngine.getTaskService().complete(taskId);
		System.out.println("������񣡣��� ����id��:"+taskId);
		
	}
	/**��ѯ���̱�����ʷ��*/
	@Test
	public void queryHistoryVariables(){
		List<HistoricVariableInstance> list = processEngine.getHistoryService()
		.createHistoricVariableInstanceQuery()
		.variableName("�������").list();
		for (HistoricVariableInstance historicVariableInstance : list) {
			System.out.println(historicVariableInstance.getValue());
			System.out.println(historicVariableInstance.getId());
			System.out.println(historicVariableInstance.getVariableName());
			System.out.println(historicVariableInstance.getVariableTypeName());
			System.out.println("#############################################");
		}
		
	}
	
}