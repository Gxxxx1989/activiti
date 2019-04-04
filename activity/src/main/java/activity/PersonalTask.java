package com.risingstarinfo.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngineConfiguration;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.junit.Test;

import sun.reflect.generics.tree.VoidDescriptor;

public class PersonalTask {
	ProcessEngine processEngine = ProcessEngineConfiguration
			.createProcessEngineConfigurationFromResource("activiti-context.xml").buildProcessEngine();

	@Test
	public void deploy() {
		Deployment deploy = processEngine.getRepositoryService().createDeployment().name("任务")
				.addClasspathResource("diagrams/PersonalTask2.bpmn").addClasspathResource("diagrams/PersonalTask2.png")
				.deploy();
		System.out.println("部署id:"+deploy.getId());
		System.out.println("部署名称:"+deploy.getName());
	}
	/**
	 * 启动流程实例的同时设置流程变量,用来指定任务办理人 map 的key对应bpmn中设置的流程变量#{userId}
	 */
	@Test
	public void startProceeInstancebyVariables() {
		Map<String, Object> variables=new HashMap<>();
		//启动流程实例的同时设置流程变量,用来指定任务办理人 map 的key对应bpmn中设置的流程变量#{userId}
		variables.put("userId", "周芷若");
		ProcessInstance processInstance = processEngine.getRuntimeService()
				.startProcessInstanceByKey("PersonalTask",variables);
		System.out.println("流程实例id：" + processInstance.getId());
		System.out.println("流程定义id：" + processInstance.getProcessDefinitionId());

	}
	
	/**
	 * 使用类动态制定任务的办理人
	 */
	@Test
	public void startProceeInstancebyClass() {
		Map<String, Object> variables=new HashMap<>();
		//启动流程实例的同时设置流程变量,用来指定任务办理人 map 的key对应bpmn中设置的流程变量#{userId}
		ProcessInstance processInstance = processEngine.getRuntimeService()
				.startProcessInstanceByKey("PersonalTask2");
		System.out.println("流程实例id：" + processInstance.getId());
		System.out.println("流程定义id：" + processInstance.getProcessDefinitionId());

	}

	/**
	 * 
	 * 查询当前人的个人任务
	 * 
	 */

	@Test

	public void queryMypersonalTask() {
		String assignee = "灭绝师太";
		List<Task> list = processEngine
				.getTaskService()/** 与个人任务管理相关的service 表act_ru_task */
				.createTaskQuery()
				.taskAssignee(assignee)
				.list();
		if (list != null && list.size() != 0) {
			for (Task task : list) {
				System.out.println("任务id：" + task.getId());
				System.out.println("任务名称：" + task.getName());
				System.out.println("任务创建时间：" + task.getCreateTime());
				System.out.println("任务办理人：" + task.getAssignee());
				System.out.println("流程实例id：" + task.getProcessInstanceId());
				System.out.println("执行对象id：" + task.getExecutionId());
				System.out.println("执行定义id：" + task.getProcessDefinitionId());
				System.out.println("############################################");
			}
		}
	}
	
	/**
	 * 完成我的任务
	 */
	@Test

	public void comlepeMyPersonalTask() {

		String taskId="115004";

		processEngine.getTaskService().complete(taskId);

		System.out.println("完成任务！！！ 任务id是:"+taskId);

	}
	//任务分配 从一个人给另一个人	
	@Test
	public void setAssigneeTask(){
		//参数 任务id和指定办理人
		processEngine.getTaskService()
					 .setAssignee("115004", "赵敏");
	}
	

}
