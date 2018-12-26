package tn.com.st2i.workflow.utilities;

import java.util.ArrayList;
import java.util.List;

public class ProcessDisplay {

	private String processModel;
	private String listTaskCompleted;
	private String listTaskInProgress;
	private String listTaskBloqued;
	private List<String> listExceptions = new ArrayList<String>();
	private Boolean bloqued = false;
	
	public String getProcessModel() {
		return processModel;
	}

	public void setProcessModel(String processModel) {
		this.processModel = processModel;
	}
	
	public String getListTaskCompleted() {
		return listTaskCompleted;
	}
	
	public void setListTaskCompleted(String listTaskCompleted) {
		this.listTaskCompleted = listTaskCompleted;
	}

	public void setListTaskInProgress(String listTaskInProgress) {
		this.listTaskInProgress = listTaskInProgress;
	}
	
	public String getListTaskInProgress() {
		return listTaskInProgress;
	}

	public void setListTaskBloqued(String listTaskBloqued) {
		this.listTaskBloqued = listTaskBloqued;
	}
	
	public String getListTaskBloqued() {
		return listTaskBloqued;
	}
	
	public void setBloqued(Boolean bloqued) {
		this.bloqued = bloqued;
	}
	
	public Boolean getBloqued() {
		return bloqued;
	}
	
	public void setListExceptions(List<String> listExceptions) {
		this.listExceptions = listExceptions;
	}
	
	public List<String> getListExceptions() {
		return listExceptions;
	}
}
