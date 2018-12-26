package tn.com.st2i.workflow.workItemHandlers;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.RepositoryService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.engine.spring.SpringProcessEngineConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import tn.com.st2i.prj.services.workflow.IWorkflowService;

@Service("serviceTaskHandler")
public class ServiceTaskHandler implements JavaDelegate, Serializable {

	private static final long serialVersionUID = 1L;

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
	@Qualifier("repositoryService")
	protected RepositoryService repositoryService;

	@Autowired
	@Qualifier("workflowService")
	private IWorkflowService WorkflowService;

	@SuppressWarnings({ "unused", "unchecked", "rawtypes" })
	@Override
	public void execute(DelegateExecution exec) throws Exception {
		
		String instanceid = (String) exec.getProcessInstanceId();
		System.out.println(instanceid);
		String operation = (String) exec.getVariable("operation");
		System.out.println(operation);

		try {
			if (operation != null && !operation.isEmpty()) {
				Map<String, Object> results = new HashMap<String, Object>();
				Class classWorkItemHandler = this.getClass();
				Method method = classWorkItemHandler.getDeclaredMethod(operation, HashMap.class);
				HashMap<String, Object> map = getMapFromExecution(exec);
				if (method != null) {
					results = (Map<String, Object>) method.invoke(this, map);
				}
			}
		} catch (Exception ex) {
			throw new Exception(ex.getCause() != null ? ex.getCause().toString() : ex.getMessage());
		}
	}

	private HashMap<String, Object> getMapFromExecution(DelegateExecution exec) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		Iterator<String> iter = exec.getVariableNames().iterator();
		System.out.println(exec.getVariableNames().size() + "  " + exec.getVariables().size());
		while (iter.hasNext()) {
			String key = iter.next();
			map.put(key, exec.getVariable(key));
			System.out.println(key + "**" + exec.getVariable(key));
		}
		return map;
	}

	
}
