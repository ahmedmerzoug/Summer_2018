package tn.com.st2i.workflow.utilities;

@SuppressWarnings("serial")
public class WorkflowException extends Exception {
	
	private String message;
	public WorkflowException(String exception)
	{
		message = exception;
	}

	public String toString() {
		return message;
	}
}
