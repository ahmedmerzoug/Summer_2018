package tn.com.st2i.prj.controller.admin;

import java.io.Serializable;
import java.sql.SQLException;
import java.text.SimpleDateFormat;

import org.eclipse.jdt.core.dom.ThisExpression;
import org.primefaces.component.panelgrid.PanelGrid;
import org.primefaces.context.RequestContext;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import javax.servlet.RequestDispatcher;

import lombok.Getter;
import lombok.Setter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Controller;

import com.easyfaces.controller.admin.LoginManager;
import com.easyfaces.controller.admin.pwd.PasswordControlManager;
import com.easyfaces.controller.tools.UiManager;
import com.easyfaces.dao.tools.DateManager;
import com.easyfaces.dao.tools.LoggerManager;

import tn.com.st2i.prj.admin.model.AdmFonc;import tn.com.st2i.prj.admin.model.AdmUtilisateur;import tn.com.st2i.prj.admin.model.VAdmUtilisateur;import tn.com.st2i.prj.services.admin.ILoginService;import tn.com.st2i.prj.services.admin.IUserService;

@SuppressWarnings("serial")
@Controller("loginUi")
@Scope(value = "session", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class LoginUi extends LoginManager implements Serializable {

	@Autowired()
	@Qualifier("loginService")
	private ILoginService loginService;

	@Autowired()
	@Qualifier("userService")
	private IUserService userService;

	@Autowired()
	@Qualifier("passwordControlManager")
	private PasswordControlManager passwordControlManager;

	@Autowired()
	@Qualifier("formSession")
	private FormSession formSession;

	@Autowired()
	@Qualifier("foncBean")
	private FoncBean foncBean;

	@Autowired()
	@Qualifier("userSession")
	private UserSession userSession;

	@Getter
	@Setter
	public VAdmUtilisateur vAdmUtilisateur;

	@Getter
	@Setter
	private Boolean isDisplayPopup;

	@Getter
	@Setter
	private String oldPwd;

	@Getter
	@Setter
	private String pwd;

	@Getter
	@Setter
	private String pwdConfirm;

	@Getter
	@Setter
	private Boolean isExipred;

	@Getter
	@Setter
	private AdmUtilisateur userLogin;

	@Getter
	@Setter
	private String dateUser;

	@Getter
	@Setter
	private String dateUserExpire;

	@Getter
	@Setter
	public String htmlInput = "";

	@Getter
	@Setter
	private String output;

	@Getter
	@Setter
	private String scriptfun;

	@Getter
	@Setter
	private PanelGrid pan;

	public void postConstruct() throws Exception {
		isDisplayPopup = false;
		isExipred = false;
	}

	public String doConnexion() throws Exception {
		
		this.updateUserInfo();
		
		AdmUtilisateur currentUser = this.userLogin;
		isDisplayPopup = false;
		
		if (!isExipred) {
			if (currentUser.getDatExpire() != null) {
				if ((!currentUser.getFExpire().equals(-1))
						&& (currentUser.getDatExpire().compareTo(DateManager.getSystemDate()) <= 0)) {
					currentUser.setFExpire(1);
					currentUser = userService.saveOrUpdateUser(currentUser);
				}
			}
			if (currentUser.getFExpire().equals(1)) {
				isExipred = true;
				isDisplayPopup = true;
				return null;
			}
		}
		if (currentUser.getDatExpire() != null) {
			dateUserExpire = DateManager.formatDate(currentUser.getDatExpire());
		}
		traceConnectUser(currentUser);
		isExipred = false;
		
		/////////////////// Draw Menu /////////////////
		try{
		output = foncBean.drawMenu();
		pan = foncBean.GetPan();
		
		scriptfun = foncBean.getScript();

		formSession.initMenu();
		connectUserUpdate(currentUser);
	
		RequestDispatcher dispatcher = getRequest().getRequestDispatcher("/login");
		dispatcher.forward(getRequest(), getResponse());
		getFacesContext().responseComplete();
		}
		catch(Exception e){
			System.out.println(e);
		}
		return null;
	}
	
	public void updateUserInfo() throws Exception {
		AdmUtilisateur currentUser = this.userLogin;
		vAdmUtilisateur = userService.findVUserById(currentUser.getIdUser().toString());
		userSession.setVAdmUtilisateur(vAdmUtilisateur);
	}

	public void traceConnectUser(AdmUtilisateur user) {

		userSessionManager.setIdUser(user.getIdUser());
		userSessionManager.setLogin(user.getLogin());
		userSessionManager.setName(user.getNomUser());

		userSessionManager.setNameAr(user.getNomUserAr());
		userSessionManager.setAccesDate(DateManager.getSystemDate());

		LoggerManager.traceConnectLog(LoggerManager.ACCES.SUCCES);

	}

	public void connectUserUpdate(AdmUtilisateur user) {

		user.setDatDerCx(DateManager.getSystemDate());
		user = userService.saveOrUpdateUser(user);
		this.userLogin = user;
	}

	public void verify(FacesContext facesContext, UIComponent uiComponent, Object newValue) {

		if (!isExipred) {

			EnumLogin connect = null;

			String password = (String) newValue;

			UIInput loginComponent = (UIInput) facesContext.getViewRoot().findComponent("formLogin:login");
			String login = (String) loginComponent.getValue();

			if (login != null) {
				initUserSessionManager();
				userSessionManager.setLogin(login);
				connect = login(login, password);
			} else {
				connect = EnumLogin.ERROR_REQUERED_LOGIN;
			}

			if (!connect.equals(EnumLogin.CONNECT)) {
				String msg = UiManager.getValFromResourceBundle("msg", EnumLogin.getErrorMsgBundle(connect));
				userSessionManager.setObs(msg);
				LoggerManager.traceConnectLog(LoggerManager.ACCES.ECHEC);

				throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, null));
			}
		}
	}

	protected EnumLogin login(String login, String password) {

		AdmUtilisateur currentUser = null;
		try {
			String pwdEncryptedWithMD5 = getPasswordMD5Hash(password);
			currentUser = loginService.findUserByLoginPwd(login, pwdEncryptedWithMD5);
			if (currentUser != null) {
				Integer userEtat = Integer.valueOf(userService.verifyUserEtat(currentUser.getIdUser()).toString());
				switch (userEtat) {
				case 0:
					return EnumLogin.ERROR_OTHERS;
				case 2:
					return EnumLogin.ERROR_NO_ACTIF_USER;
				case 3:
					return EnumLogin.ERROR_SUSPENDED_USER;
				default:
					break;
				}
			} else {
				return EnumLogin.ERROR_LOGIN_PWD;
			}
		} catch (SQLException sql_ex) {
			return EnumLogin.ERROR_DATABASE;
		} catch (Exception e) {
			return EnumLogin.ERROR_OTHERS;
		}
		this.userLogin = currentUser;

		String pattern = "MM/dd/yyyy";
		SimpleDateFormat format = new SimpleDateFormat(pattern);

		// dateUser = format.format(this.userLogin.getDatExpire());
		return EnumLogin.CONNECT;
	}

	public void initUserSessionManager() {
		userSessionManager.init();
	}

	public String initPwdByUser() throws Exception {
		RequestContext reqContext = RequestContext.getCurrentInstance();
		if (pwdConfirm.equals(pwd)) {

			String msgError = passwordControlManager.controlPassword(pwd);

			if (msgError != null) {
				reqContext.addCallbackParam(ERROR_VALIDATION_VAR, 1);
				return validateError(msgError);
			}

			if (userLogin.getPwd().equals(getPasswordMD5Hash(oldPwd))) {
				userLogin.setPwd(getPasswordMD5Hash(pwd));
			} else {
				reqContext.addCallbackParam(ERROR_VALIDATION_VAR, 1);
				return validateError(getValFromResourceBundle("msg", "adminUser.fiche.pwd.notValidMsg"));
			}
		} else {
			reqContext.addCallbackParam(ERROR_VALIDATION_VAR, 1);
			return validateError(getValFromResourceBundle("msg", "adminUser.fiche.pwd.equalMsg"));
		}

		this.password = "aaaaa";
		this.login = userLogin.getLogin();
		userLogin.setFExpire(0);
		userLogin = userService.saveOrUpdateUser(userLogin);
		userService.updateUserDateExpire(userLogin.getIdUser());
		userLogin = userService.findNewValueUserById(userLogin, userLogin.getIdUser().toString());
		isDisplayPopup = false;
		reqContext.addCallbackParam(ERROR_VALIDATION_VAR, 0);
		return null;
	}

	public void cancelInitPwdByUser() {
		isDisplayPopup = false;
		isExipred = false;
	}

}
