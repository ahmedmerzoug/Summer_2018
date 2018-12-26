package tn.com.st2i.prj.controller.admin;
import java.io.Serializable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Controller;

import com.easyfaces.common.admin.UserSessionLog;
import com.easyfaces.controller.tools.UiManager;

import lombok.Getter;
import lombok.Setter;
import tn.com.st2i.prj.admin.model.VAdmFoncUtilisateur;
import tn.com.st2i.prj.admin.model.VAdmUtilisateur;


@SuppressWarnings("serial")
@Controller("userSession")
@Scope(value = "session", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class UserSession extends UiManager implements Serializable {

	@Autowired
	@Qualifier("userSessionLog")
	private UserSessionLog userSessionLog;
	
	@Getter
	@Setter
	public VAdmUtilisateur vAdmUtilisateur = new VAdmUtilisateur();

	@Setter
	@Getter
	private VAdmFoncUtilisateur admFonc;
	
	@Setter
	@Getter
	private Boolean isEdit = false;

	public String getLogin() {
		return userSessionLog.getLogin();
	}
}
