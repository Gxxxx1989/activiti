package activity.sequeflow;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngineConfiguration;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.task.Task;
import org.junit.Test;

public class SequenceFlow {
	// 从classpath下获取流程引擎 默认文件是activiti.cfg.xml
	// ProcessEngine processEngine=ProcessEngines.getDefaultProcessEngine();
	ProcessEngine processEngine = ProcessEngineConfiguration.createProcessEngineConfigurationFromResource("activiti.cfg.xml")
			.buildProcessEngine();

	/**
	 * 部署流程定义
	 * 
	 * @author guoxi 
	 */
	@Test
	public void deploymentProcessDefinition_InputStream() {

		InputStream inputStream1 = this.getClass().getResourceAsStream("sequence.bpmn");
		InputStream inputStream2= this.getClass().getResourceAsStream("sequence.png");

		Deployment deploy = processEngine.getRepositoryService()/** 与流程对象和部署定义相关的service */
				.createDeployment()/** 创建一个部署对象 */
				.name("连线")/** 添加部署名称 */
				.addInputStream("sequence.bpmn", inputStream1)
				.addInputStream("sequence.png", inputStream2)
				.deploy();
		System.out.println("部署deploy:id:" + deploy.getId());
		System.out.println("部署deploy:name:" + deploy.getName());

	}
	/**启动流程实例*/
	@Test
	public void startProcessInstance(){
		org.activiti.engine.runtime.ProcessInstance processInstance = processEngine.getRuntimeService()
		.startProcessInstanceByKey("sequenceflow");
		System.out.println("流程实例Id："+processInstance.getId());
		System.out.println("流程定义Id："+processInstance.getProcessDefinitionId());
	}
	/**
	 * 查询个人任务
	 */
	@Test
	public void queryMypersonalTask(){
		String assignee="赵六";
		List<Task> list = processEngine.getTaskService()/**与个人任务管理相关的service 表act_ru_task*/
												.createTaskQuery()
												.taskAssignee(assignee)
												.list();
		if (list!=null&&list.size()!=0) {
			for (Task task : list) {
				System.out.println("任务id："+task.getId());
				System.out.println("任务名称："+task.getName());
				System.out.println("任务创建时间："+task.getCreateTime());
				System.out.println("任务办理人："+task.getAssignee());
				System.out.println("流程实例id："+task.getProcessInstanceId());
				System.out.println("执行对象id："+task.getExecutionId());
				System.out.println("执行定义id："+task.getProcessDefinitionId());
				System.out.println("############################################");
			}
		}
	}
	
	/**
	 * 完成我的任务
	 */
	@Test
	public void comlepeMyPersonalTask() {
		String taskId="2504";
		//完成任务的同时设置流程变量，使用流程变量指定完成任务后的下一个连线
		Map<String, Object> variables=new HashMap<String, Object>();
		variables.put("message", "不重要");
		processEngine.getTaskService().complete(taskId,variables);
		System.out.println("完成任务！！！ 任务id是:"+taskId);
	}
	
}

