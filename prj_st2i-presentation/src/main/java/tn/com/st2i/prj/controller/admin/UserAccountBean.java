package tn.com.st2i.prj.controller.admin;

import lombok.Getter;
import lombok.Setter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Controller;

import com.easyfaces.common.admin.UserSessionLog;
import com.easyfaces.controller.component.panelmenu.UiMenuManager;
import com.easyfaces.controller.tools.UiAbstractView;

import tn.com.st2i.prj.admin.model.AdmUtilisateur;
import tn.com.st2i.prj.admin.model.VAdmUtilisateur;
import tn.com.st2i.prj.services.admin.IUserService;
import tn.com.st2i.prj.utils.Utils;

@Controller("userAccountBean")
@Scope(value = "session", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class UserAccountBean extends UiAbstractView {

	private final String NAVIGATION_NAME = "/pages/admin/utility/userInfo.xhtml?faces-redirect=true";

	@Autowired()
	@Qualifier("uiMenuManager")
	private UiMenuManager menuManager;

	@Autowired()
	@Qualifier("userSessionLog")
	private UserSessionLog userSessionLog;

	@Autowired()
	@Qualifier("userService")
	private IUserService userService;

	@Autowired()
	@Qualifier("loginUi")
	private LoginUi loginUi;

	@Autowired()
	@Qualifier("profilsForUserUI")
	private ProfilsForUserUI profilsForUserUI;

	@Autowired()
	@Qualifier("utils")
	private Utils utils;

	@Getter
	@Setter
	private AdmUtilisateur user;

	@Getter
	@Setter
	private VAdmUtilisateur viewUser;

	@Getter
	@Setter
	private Boolean isDisplayPopup;

	@Getter
	@Setter
	private boolean initPwdActivated;

	@Getter
	@Setter
	private String pwd;

	@Getter
	@Setter
	private String pwdConfirm;

	@Getter
	@Setter
	private boolean userSusp;

	@Getter
	@Setter
	private boolean otherProfesActivated;

	@Getter
	@Setter
	private boolean inexpirable;

	@Override
	protected void postConstruct() throws Exception {
	}

	private void initFiche() throws Exception {
		user = new AdmUtilisateur();
		initPwdActivated = true;
		userSusp = false;
		otherProfesActivated = false;
		inexpirable = false;
		menuManager.editPath("userAccount.path");
	}

	public String editUserAccount() throws Exception {
		initFiche();
		initPwdActivated = false;
		Long idUser = userSessionLog.getIdUser();
		if (idUser != null) {
			user = userService.findUserById(idUser.toString());
			viewUser = userService.findVUserById(idUser.toString());
			if (user.getIdProfes() != null) {
				otherProfesActivated = user.getIdProfes().toString()
						.equals(utils.getOtherProfessionId());
			}
			inexpirable = user.getFExpire().equals(-1L);
		}
		return NAVIGATION_NAME;
	}

	public void prepareInitPwdByUser() {
		isDisplayPopup = true;
	}

	public String initPwdByUser() throws Exception {
		String validation = loginUi.initPwdByUser();
		if (validation != null) {
			Long idUser = userSessionLog.getIdUser();
			user = userService.findUserById(idUser.toString());
			viewUser = userService.findVUserById(idUser.toString());
			isDisplayPopup = false;
		}
		return null;
	}

	public void cancelInitPwdByUser() {
		isDisplayPopup = false;
	}

	public void initPwdByAdmin() {
		initPwdActivated = !initPwdActivated;
		pwd = "";
		pwdConfirm = "";
	}

	public void checkIfUserSusp() {
		// userSusp = "3".equals(radioEtat.getSelectedId());
	}

	public void checkIfOtherProfes() {
		if (viewUser != null) {
			otherProfesActivated = utils.getOtherProfessionId().equals(
					viewUser.getIdProfes().toString());
		}
	}

	public String emptyAction() {
		return null;
	}
}