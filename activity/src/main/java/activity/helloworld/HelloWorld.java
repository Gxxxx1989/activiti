package activity.helloworld;

import java.util.List;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.TaskService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.junit.Test;

public class HelloWorld {
	
	ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
	/**
	 * �������̶���
	 * @author guoxi class
	 */
	@Test
	public void deploymentProcessDefinition() {
		System.out.println("processEngine:"+processEngine);
		
		 Deployment deploy = processEngine.getRepositoryService()/**�����̶���Ͳ�������ص�service*/
		 			  .createDeployment()/**����һ���������*/
		 			  .name("helloworld���ų���")/**���Ӳ�������*/
		 			  .addClasspathResource("diagrams/helloworld.bpmn")/**��classpath����Դ���أ�һ��ֻ�ܼ���һ���ļ�*/
		 			  .addClasspathResource("diagrams/helloworld.png")
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
		String processDefinitionKey="helloworld";
		ProcessInstance processInstance = processEngine.getRuntimeService()/**������ִ�е�����ʵ����ִ�ж�����ص�service*/
		/**ʹ�����̶����key��������ʵ��,
		 * key��Ӧhelloworld.bpmn�ļ��� process id����ֵ��
		 * ʹ��key������Ĭ���ǰ������°汾�����̶�������*/
					  .startProcessInstanceByKey(processDefinitionKey);
		System.out.println("����ʵ����"+processInstance.getId());/**����ʵ��id*/
		System.out.println("���̶��壺"+processInstance.getProcessDefinitionId());/**���̶���id*/
	}
	/**
	 * ��ѯ��ǰ�˵ĸ�������
	 */
	@Test
	public void queryMypersonalTask(){
		String assignee="����";
		List<Task> list = processEngine.getTaskService()/**��������������ص�service ��act_ru_task*/
												.createTaskQuery()
												.taskAssignee(assignee)
												.list();
		if (list!=null&&list.size()!=0) {
			for (Task task : list) {
				System.out.println("����id��"+task.getId());
				System.out.println("�������ƣ�"+task.getName());
				System.out.println("���񴴽�ʱ�䣺"+task.getCreateTime());
				System.out.println("��������ˣ�"+task.getAssignee());
				System.out.println("����ʵ��id��"+task.getProcessInstanceId());
				System.out.println("ִ�ж���id��"+task.getExecutionId());
				System.out.println("ִ�ж���id��"+task.getProcessDefinitionId());
				System.out.println("############################################");
			}
		}
	}
	/**
	 * ����ҵ�����
	 */
	@Test
	public void comlepeMyPersonalTask() {
		String taskId="50002";
		processEngine.getTaskService().complete(taskId);
		System.out.println("������񣡣��� ����id��:"+taskId);
		
	}
	
}