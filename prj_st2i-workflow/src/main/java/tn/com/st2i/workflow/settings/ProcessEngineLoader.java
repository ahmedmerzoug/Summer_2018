/**
 * @author Wajdi Mlichi
 * @Date 29 mai 2017
 */
package tn.com.st2i.workflow.settings;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.sql.DataSource;

import org.camunda.bpm.engine.HistoryService;
import org.camunda.bpm.engine.ManagementService;
import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.RepositoryService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.impl.bpmn.parser.BpmnParseListener;
import org.camunda.bpm.engine.impl.bpmn.parser.FoxFailedJobParseListener;
import org.camunda.bpm.engine.impl.jobexecutor.FoxFailedJobCommandFactory;
import org.camunda.bpm.engine.spring.ProcessEngineFactoryBean;
import org.camunda.bpm.engine.spring.SpringProcessEngineConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jndi.JndiObjectFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

import tn.com.st2i.workflow.utilities.Constants;

@Configuration
public class ProcessEngineLoader {

	@Autowired
	@Qualifier("workflowProperties")
	Properties workflowProperties;
	
	@Autowired
	private ApplicationContext appContext;
	
	@Bean
	public DataSource dataSource() throws Exception {
		JndiObjectFactoryBean jndiObjectFactoryBean = new JndiObjectFactoryBean();
		jndiObjectFactoryBean.setJndiName(workflowProperties.getProperty(Constants.WF_JNDI_NAME));
		jndiObjectFactoryBean.setResourceRef(true);
		jndiObjectFactoryBean.setProxyInterface(DataSource.class);
		jndiObjectFactoryBean.afterPropertiesSet();
		return (DataSource) jndiObjectFactoryBean.getObject();
	}

	@Bean
	public PlatformTransactionManager transactionManager() throws Exception {
		return new DataSourceTransactionManager(dataSource());
	}

	@Bean(name = "processEngineConfiguration")
	public SpringProcessEngineConfiguration processEngineConfiguration() throws Exception {
		SpringProcessEngineConfiguration conf = new SpringProcessEngineConfiguration();
		List<BpmnParseListener> parseListeners = new ArrayList<>();
		parseListeners.add(new FoxFailedJobParseListener());
		conf.setCustomPostBPMNParseListeners(parseListeners);
		conf.setFailedJobCommandFactory(new FoxFailedJobCommandFactory());
		conf.setDataSource(dataSource());
		conf.setTransactionManager(transactionManager());
		conf.setProcessEngineName("engine");
		conf.setDatabaseSchemaUpdate("true");
		conf.setDeploymentResources(getBpmnResources());
		conf.setDatabaseSchemaUpdate("true");
		conf.setHistory("full");
		conf.setJobExecutorActivate(true);
		return conf;
	}

	private Resource[] getBpmnResources() throws IOException{
		return appContext.getResources("file:///"+workflowProperties.getProperty(Constants.PROCESS_REPO)+"//*");
	}

	@Bean(name = "processEngine")
	public ProcessEngineFactoryBean processEngine() throws Exception {
		ProcessEngineFactoryBean factoryBean = new ProcessEngineFactoryBean();
		factoryBean.setProcessEngineConfiguration(processEngineConfiguration());
		return factoryBean;
	}

	@Bean(name = "repositoryService")
	public RepositoryService repositoryService(ProcessEngine processEngine) {
		return processEngine.getRepositoryService();
	}

	@Bean(name = "runtimeService")
	public RuntimeService runtimeService(ProcessEngine processEngine) {
		return processEngine.getRuntimeService();
	}

	@Bean(name = "taskService")
	public TaskService taskService(ProcessEngine processEngine) {
		return processEngine.getTaskService();
	}

	@Bean(name = "historyService")
	public HistoryService historyService(ProcessEngine processEngine) {
		return processEngine.getHistoryService();
	}

	@Bean(name = "taskService")
	public ManagementService managementService(ProcessEngine processEngine) {
		return processEngine.getManagementService();
	}

}
