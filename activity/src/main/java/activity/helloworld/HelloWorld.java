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
	 * 部署流程定义
	 * @author guoxi class
	 */
	@Test
	public void deploymentProcessDefinition() {
		System.out.println("processEngine:"+processEngine);
		
		 Deployment deploy = processEngine.getRepositoryService()/**与流程对象和部署定义相关的service*/
		 			  .createDeployment()/**创建一个部署对象*/
		 			  .name("helloworld入门程序")/**添加部署名称*/
		 			  .addClasspathResource("diagrams/helloworld.bpmn")/**从classpath的资源加载，一次只能加载一个文件*/
		 			  .addClasspathResource("diagrams/helloworld.png")
		 			  .deploy();
		 System.out.println("部署deploy:id:"+deploy.getId());
		 System.out.println("部署deploy:name:"+deploy.getName());
		
	}
	/**
	 * 启动流程实例
	 * @author guoxi 
	 */
	@Test
	public  void startDeploymentInstance() {
		//流程定义的key
		String processDefinitionKey="helloworld";
		ProcessInstance processInstance = processEngine.getRuntimeService()/**与正在执行的流程实例和执行对象相关的service*/
		/**使用流程定义的key启动流程实例,
		 * key对应helloworld.bpmn文件中 process id属性值，
		 * 使用key启动，默认是按照最新版本的流程定义启动*/
					  .startProcessInstanceByKey(processDefinitionKey);
		System.out.println("流程实例："+processInstance.getId());/**流程实例id*/
		System.out.println("流程定义："+processInstance.getProcessDefinitionId());/**流程定义id*/
	}
	
	/**

	 * 启动流程实例 +设置流程变量+获取流程变量+向后执行

	 * @author guoxi 

	 */
	@Test
	public  void startDeploymentInstanceLater() {

		//流程定义的key

		String processDefinitionKey="ReceiveTask";

		ProcessInstance processInstance = processEngine.getRuntimeService()/**与正在执行的流程实例和执行对象相关的service*/

		/**使用流程定义的key启动流程实例,

		 * key对应helloworld.bpmn文件中 process id属性值，

		 * 使用key启动，默认是按照最新版本的流程定义启动*/

					  .startProcessInstanceByKey(processDefinitionKey);

		System.out.println("流程实例："+processInstance.getId());/**流程实例id*/

		System.out.println("流程定义："+processInstance.getProcessDefinitionId());/**流程定义id*/
		
		/**查询执行对象id*/
		Execution execution = processEngine.getRuntimeService()
					 .createExecutionQuery()
					 .processInstanceId(processInstance.getProcessInstanceId())
					 .activityId("receivetask1")//对应bpmn文件中id属性值
					 .singleResult();
		/**
		 * 使用流程变量设置当日销售额 用来传递业务参数
		 */
		processEngine.getRuntimeService()
					 .setVariable(execution.getId(), "汇总当日销售额", "100万");
		/**
		 * 如果流程处于等待状态 流程向后执行一步
		 */
		processEngine.getRuntimeService().signal(execution.getId());
		
		/**查询执行对象id*/
		Execution execution2 = processEngine.getRuntimeService()
					 .createExecutionQuery()
					 .processInstanceId(processInstance.getProcessInstanceId())
					 .activityId("receivetask2")//对应bpmn文件中id属性值
					 .singleResult();
		
		/**从流程变量中获取值*/
		String variable = (String) processEngine.getRuntimeService()
					 .getVariable(execution2.getId(), "汇总当日销售额");
		
		System.out.println("发送短信，金额是"+variable);
		
		/**
		 * 如果流程处于等待状态 流程向后执行一步
		 */
		processEngine.getRuntimeService().signal(execution2.getId());

	}
	
	/**

	 * 查询当前人的个人任务

	 */
	
	/**
	 * 查询当前人的个人任务
	 */
	@Test
	public void queryMypersonalTask(){
		String assignee="张三";
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
		String taskId="50002";
		processEngine.getTaskService().complete(taskId);
		System.out.println("完成任务！！！ 任务id是:"+taskId);
		
	}
	
}
