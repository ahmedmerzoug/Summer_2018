package tn.com.st2i.prj.controller.admin;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.servlet.RequestDispatcher;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Controller;

import com.easyfaces.common.admin.UserSessionLog;
import com.easyfaces.common.lang.SessionLang;
import com.easyfaces.controller.component.panelmenu.UiMenuManager;
import com.easyfaces.controller.tools.UiManager;
import com.easyfaces.dao.model.MenuItemGen;

import tn.com.st2i.prj.admin.dao.IMenuItemDao;
import tn.com.st2i.prj.admin.model.AdmUtilisateur;

@SuppressWarnings("serial")
@Controller("formSession")
@Scope(value = "session", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class FormSession extends UiManager implements Serializable {

	private final String NAVIGATION_NAME_ACCEUIL = "/pages/admin/index.xhtml?faces-redirect=true";
	private final String NAVIGATION_NAME_LANG = "/pages/admin/utility/changeLang.xhtml?faces-redirect=true";

	@Autowired
	@Qualifier("menuItemDao")
	private IMenuItemDao menuItemDao;

	@Autowired
	@Qualifier("sessionLang")
	private SessionLang sessionLang;

	@Autowired
	@Qualifier("userSessionLog")
	private UserSessionLog userSessionLog;

	@Autowired()
	@Qualifier("uiMenuManager")
	private UiMenuManager menuManager;

	@Autowired
	@Qualifier("loginUi")
	private LoginUi loginUi;

	@Autowired()
	@Qualifier("foncBean")
	private FoncBean foncBean;

	private List<MenuItemGen> listMenu;

	private Boolean isRememberMe = false;
	private AdmUtilisateur userRememberMe;

	public void initMenu() {
		createMenuItem(userSessionLog.getIdUser());
		menuManager.createListItem(listMenu);
	}

	public List<MenuItemGen> createMenuItem(Long idUser) {
		if (listMenu == null || listMenu.isEmpty()) {
			listMenu = menuItemDao.getListMenuItem(idUser);
		}
		return listMenu;
	}
	
	
	

	public String modifyLang() {
		return NAVIGATION_NAME_LANG;
	}

	public void changeLangToFr() {
		if (sessionLang.getLang().equalsIgnoreCase("ar")) {
			sessionLang.modifyLang();
		}
	}

	public void changeLangToAr() {
		if (sessionLang.getLang().equalsIgnoreCase("fr")) {
			sessionLang.modifyLang();
		}
	}

	public String validateLang() throws Exception {
		sessionLang.modifyLang();
		foncBean.deleteLastPath();
		return NAVIGATION_NAME_ACCEUIL;
	}

	public String logout() throws Exception {
		RequestDispatcher dispatcher = getRequest().getRequestDispatcher(
				"/logout");
		dispatcher.forward(getRequest(), getResponse());
		getFacesContext().responseComplete();
		return null;
	}

	public String restart() throws Exception {
		return NAVIGATION_NAME_ACCEUIL;
	}

	public void loadContext() {
		if (isRememberMe) {
			loginUi.initUserSessionManager();
			loginUi.traceConnectUser(userRememberMe);
		}
		this.initMenu();
		isRememberMe = false;
		menuManager.updateMenuPath("");
	}

	public void initRememberMe(Boolean isRememberMe,
			AdmUtilisateur userRememberMe) {
		this.isRememberMe = isRememberMe;
		this.userRememberMe = userRememberMe;
	}

	public Boolean codFoncExist(String codFonc) {
		return menuManager.getItemFromList(codFonc) != null;
	}

	public Boolean getIsReadOnly() {
		return menuManager.getIsReadOnly();
	}

}
