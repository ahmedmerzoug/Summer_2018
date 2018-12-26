package tn.com.st2i.prj.controller.admin;

import lombok.Getter;
import lombok.Setter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Controller;

import com.easyfaces.controller.tools.UiAbstractGen;

import tn.com.st2i.prj.admin.model.VAdmLogAcces;
import tn.com.st2i.prj.services.admin.ILogService;

@SuppressWarnings("serial")
@Controller("logDataBySessionUI")
@Scope(value = "session", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class LogDataBySessionUI extends UiAbstractGen {

	@Autowired()
	@Qualifier("logService")
	private ILogService logService;

	@Getter
	@Setter
	private VAdmLogAcces injectedLogAccess;

	public LogDataBySessionUI() throws Exception {
		super("uiViewAdmin:listLogDataBySession");
	}
	
	@Override
	public void init() throws Exception {
		if (injectedLogAccess != null) {
			addData(logService.getListLogDataBySessionId(injectedLogAccess
					.getSessionId()));
		}
	}

	@Override
	protected void postConstructMethod() throws Exception {
	}

	@Override
	protected String addRow() throws Exception {
		return null;
	}

	@Override
	protected String editRow() throws Exception {
		return null;
	}

	@Override
	protected void deleteRow() throws Exception {
	}

	@Override
	public void initSearch() throws Exception {
	}

	@Override
	public void updateListComboRech() throws Exception {
		// TODO Auto-generated method stub
		
	}

}