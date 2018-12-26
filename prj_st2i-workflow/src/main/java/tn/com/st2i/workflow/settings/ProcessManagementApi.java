/**
 * @author Wajdi Mlichi
 * @Date 30 mai 2017
 */
package tn.com.st2i.workflow.settings;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.RepositoryService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.history.HistoricActivityInstance;
import org.camunda.bpm.engine.history.HistoricProcessInstance;
import org.camunda.bpm.engine.management.JobDefinition;
import org.camunda.bpm.engine.repository.Deployment;
import org.camunda.bpm.engine.repository.DeploymentBuilder;
import org.camunda.bpm.engine.runtime.ActivityInstance;
import org.camunda.bpm.engine.runtime.Execution;
import org.camunda.bpm.engine.runtime.Job;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.spring.SpringProcessEngineConfiguration;
import org.camunda.bpm.engine.task.Task;
import org.camunda.bpm.model.bpmn.Bpmn;
import org.camunda.bpm.model.bpmn.BpmnModelInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import tn.com.st2i.workflow.utilities.ProcessDisplay;
import tn.com.st2i.workflow.utilities.Utils;

@Service("processManagementApi")
public class ProcessManagementApi {

	@Autowired
	@Qualifier("runtimeService")
	protected RuntimeService runtimeService;

	@Autowired
	@Qualifier("processEngine")
	protected ProcessEngine processEngine;

	@Autowired
	@Qualifier("processEngineConfiguration")
	protected SpringProcessEngineConfiguration processEngineConfiguration;

	@Autowired
	@Qualifier("taskService")
	protected TaskService taskService;

	@Autowired
	@Qualifier("repositoryService")
	protected RepositoryService repositoryService;

	public String startProcessByKey(String processKey, HashMap<String, Object> vars) {
		ProcessInstance process = runtimeService.startProcessInstanceByKey(processKey, vars);
		return process.getProcessInstanceId();
	}

	public void completeTask(String instanceId, String actorId, HashMap<String, Object> vars) {
		List<Task> taskList = taskService.createTaskQuery().processInstanceId(instanceId).taskAssignee(actorId).active()
				.initializeFormKeys().list();
		if (taskList != null && !taskList.isEmpty())
			for (Task task : taskList)
				taskService.complete(task.getId(), vars);
	}

	public void signalEvent(String instanceId, String signalId, HashMap<String, Object> vars) {
		Execution ex = runtimeService.createExecutionQuery().processInstanceId(instanceId).activityId(signalId)
				.singleResult();
		runtimeService.signal(ex.getId(), vars);
	}

	public void retryProcess(String instanceId) {
		List<Job> jobList = processEngine.getManagementService().createJobQuery().processInstanceId(instanceId)
				.noRetriesLeft().withException().list();
		for (Job job : jobList)
			processEngine.getManagementService().executeJob(job.getId());
	}

	public String getInstanceException(String instanceId) {
		Job job = processEngine.getManagementService().createJobQuery().processInstanceId(instanceId).noRetriesLeft()
				.withException().singleResult();
		return job.getExceptionMessage();
	}

	public String deployProcess(String processName, InputStream processStream) {
		RepositoryService repositoryService = processEngine.getRepositoryService();
		DeploymentBuilder deploymentBuilder = repositoryService.createDeployment().enableDuplicateFiltering(false)
				.name(processName);
		BpmnModelInstance bpmnModelInstance = Bpmn.readModelFromStream(processStream);
		Bpmn.validateModel(bpmnModelInstance);
		deploymentBuilder.addModelInstance(processName + ".bpmn", bpmnModelInstance);
		Deployment deployment = deploymentBuilder.deploy();
		return deployment.getId();
	}

	public InputStream getProcessDiagram(String instanceId) {
		ProcessInstance instance = runtimeService.createProcessInstanceQuery().processInstanceId(instanceId)
				.singleResult();
		if (instance != null)
			return repositoryService.getProcessDiagram(instance.getProcessDefinitionId());
		else {
			HistoricProcessInstance histInstance = processEngine.getHistoryService()
					.createHistoricProcessInstanceQuery().processInstanceId(instanceId).singleResult();
			return repositoryService.getProcessDiagram(histInstance.getProcessDefinitionId());
		}
	}

	public InputStream getProcessModel(String instanceId) {
		ProcessInstance instance = runtimeService.createProcessInstanceQuery().processInstanceId(instanceId)
				.singleResult();
		if (instance != null)
			return repositoryService.getProcessModel(instance.getProcessDefinitionId());
		else {
			HistoricProcessInstance histInstance = processEngine.getHistoryService()
					.createHistoricProcessInstanceQuery().processInstanceId(instanceId).singleResult();
			return repositoryService.getProcessModel(histInstance.getProcessDefinitionId());
		}
	}

	public String getProcessModelString(String instanceId) throws IOException {
		ProcessInstance instance = runtimeService.createProcessInstanceQuery().processInstanceId(instanceId)
				.singleResult();
		InputStream model = null;
		if (instance != null)
			model = repositoryService.getProcessModel(instance.getProcessDefinitionId());
		else {
			HistoricProcessInstance histInstance = processEngine.getHistoryService()
					.createHistoricProcessInstanceQuery().processInstanceId(instanceId).singleResult();
			model = repositoryService.getProcessModel(histInstance.getProcessDefinitionId());
		}
		if (model != null) {
			return Utils.getStringFromInputStream(model);
		}
		return instanceId;
	}

	public ProcessDisplay getProcessDisplay(String instanceId) throws IOException {
		ProcessDisplay processDisplay = new ProcessDisplay();
		processDisplay.setProcessModel(getProcessModelString(instanceId));		
		List<String> completedList = new ArrayList<String>();
		List<String> inProgressList = new ArrayList<String>();
		List<String> bloquedList = new ArrayList<String>();
		
		// get activities in progress to display in the diagram
		List<Task> taskList = taskService.createTaskQuery().processInstanceId(instanceId).list();		
		if (taskList != null && taskList.size() > 0)
			for (Task task : taskList)
			{
				inProgressList.add(task.getTaskDefinitionKey());				
			}
		
		// get completed activities to display in the diagram
		List<HistoricActivityInstance> actList = processEngine.getHistoryService().createHistoricActivityInstanceQuery()
				.processInstanceId(instanceId).list();		
		if (actList != null && actList.size() > 0)
		{
			for (HistoricActivityInstance act : actList)
			{
				completedList.add(act.getId().substring(0, act.getId().indexOf(":")));
			}
			completedList.removeAll(inProgressList);
		}
		
		// get bloqued activities to display in the diagram		
		List<Job> jobList = processEngine.getManagementService().createJobQuery().processInstanceId(instanceId)
				.noRetriesLeft().withException().list();
		if (jobList != null && jobList.size() > 0)
			for (Job job : jobList) {
				JobDefinition jbDef = processEngine.getManagementService().createJobDefinitionQuery().jobDefinitionId(job.getJobDefinitionId()).singleResult();
				bloquedList.add(jbDef.getActivityId());
				processDisplay.getListExceptions().add(jbDef.getActivityId() + ": " + job.getExceptionMessage());
				processDisplay.setBloqued(true);
			}

		processDisplay.setListTaskCompleted(Utils.getStringFromList(completedList));
		processDisplay.setListTaskInProgress(Utils.getStringFromList(inProgressList));
		processDisplay.setListTaskBloqued(Utils.getStringFromList(bloquedList));

		return processDisplay;
	}

	public ActivityInstance getActivityInstance(String instanceId) {
		return runtimeService.getActivityInstance(instanceId);
	}
}
