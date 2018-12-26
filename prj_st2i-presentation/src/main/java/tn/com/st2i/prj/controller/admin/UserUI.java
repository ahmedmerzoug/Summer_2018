package tn.com.st2i.prj.controller.admin;

import java.util.*;

import lombok.Getter;
import lombok.Setter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Controller;

import com.easyfaces.common.utils.Util;
import com.easyfaces.controller.component.combo.UiComboBox;
import com.easyfaces.controller.component.radio.UiRadioBox;
import com.easyfaces.controller.tools.UiAbstractGen;
import com.easyfaces.dao.tools.DateManager;

import tn.com.st2i.prj.admin.model.AdmProfession;
import tn.com.st2i.prj.admin.model.AdmLibelle;
import tn.com.st2i.prj.admin.model.AdmUtilisateur;
import tn.com.st2i.prj.services.admin.IUserService;
import tn.com.st2i.prj.utils.Utils;

@SuppressWarnings("serial")
@Controller("userUI")
@Scope(value = "session", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class UserUI extends UiAbstractGen {

	private final static String NAVIGATION_NAME = "/pages/admin/user/listeUsers.xhtml";
	private final static String NAVIGATION_NAME_EDIT = "/pages/admin/user/ficheUser.xhtml";

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
	private UiComboBox comboProfEdit;

	@Getter
	@Setter
	private UiRadioBox radioSexe;

	@Getter
	@Setter
	private UiRadioBox radioEtat;

	@Getter
	@Setter
	private UiComboBox comboProfSearch;

	@Getter
	@Setter
	private UiComboBox comboEtatSearch;

	@Getter
	@Setter
	private Date dateDebutLastCnx;

	@Getter
	@Setter
	private Date dateFinLastCnx;

	@Getter
	@Setter
	private String loginSearch;

	@Getter
	@Setter
	private String nameSearch;

	@Getter
	@Setter
	private String cinSearch;

	@Getter
	@Setter
	private Boolean initPwdActivated;

	@Getter
	@Setter
	private String pwd;

	@Getter
	@Setter
	private String pwdConfirm;

	@Getter
	@Setter
	private Boolean userSusp;

	@Getter
	@Setter
	private Boolean otherProfesActivated;

	@Getter
	@Setter
	private Boolean inexpirable;

	public UserUI() throws Exception {
		super("uiViewAdmin:listUsers");
	}

	@Override
	protected void postConstructMethod() throws Exception {
		initComponents();
		setNavigationName(NAVIGATION_NAME);
		setNavigationNameEdit(NAVIGATION_NAME_EDIT);
	}

	public void initComponents() throws Exception {
		comboProfSearch = createCombo("comboProfSearch");
		comboEtatSearch = createCombo("comboEtatSearch");
		comboProfEdit = createCombo("comboProfEdit");
		comboProfSearch.setWidthPanel(190);
		
		comboEtatSearch.setWidthPanel(190);

		radioSexe = createRadio("radioSexe", "msg");
		radioSexe.addValue("1", "adminUser.fiche.male");
		radioSexe.addValue("2", "adminUser.fiche.femelle");
		radioSexe.setSelectedId("1");

		radioEtat = createRadio("radioEtat", "msg");
		radioEtat.addValue("ACTIF", "adminUser.fiche.actif");
		radioEtat.addValue("INACTIF", "adminUser.fiche.inactif");
		radioEtat.addValue("SUSP", "adminUser.fiche.susp");
		radioEtat.setSelectedId("ACTIF");
	}

	@Override
	public void init() throws Exception {

		addData(userService.getListVUtilisateurForSearch(null, null, null,
				null, null, null, null));

		List<AdmProfession> listProfessions = userService.getListProfessions();
		comboProfEdit.addDataListDual(listProfessions, "idProfes",
				"{desProfes}", "{desProfesAr}");
		comboProfSearch.addDataListDual(listProfessions, "idProfes",
				"{desProfes}", "{desProfesAr}");

		List<AdmLibelle> listEtats = userService.getListUserEtats();
		comboEtatSearch.addDataListDual(listEtats, "id", "{valFr}", "{valAr}");

	}

	@Override
	protected String addRow() throws Exception {
		user = new AdmUtilisateur();
		profilsForUserUI.setUser(user);
		comboProfEdit.clearSelectedId();
		radioSexe.setSelectedId("1");
		radioEtat.setSelectedId("ACTIF");
		initPwdActivated = true;
		userSusp = false;
		otherProfesActivated = false;
		inexpirable = false;
		profilsForUserUI.initBean();
		return null;
	}

	@Override
	protected String editRow() throws Exception {
		initPwdActivated = false;

		if (selectedId != null) {
			user = userService.findUserById(selectedId);
			profilsForUserUI.setUser(user);
			if (user.getIdProfes() != null) {
				comboProfEdit.setSelectedId(user.getIdProfes().toString());
				otherProfesActivated = user.getIdProfes().toString()
						.equals(utils.getOtherProfessionId());
			} else {
				comboProfEdit.clearSelectedId();
			}
			if (user.getGenre() != null) {
				radioSexe.setSelectedId(user.getGenre().toString());
			}

			String radioEtatSelectedId = "ACTIF";
			if (user.getFActif().equals(0)) {
				radioEtatSelectedId = "INACTIF";
			} else {
				if (user.getFSusp().equals(0)) {
					radioEtatSelectedId = "ACTIF";
				} else {
					radioEtatSelectedId = "SUSP";
				}
			}
			radioEtat.setSelectedId(radioEtatSelectedId);
			checkIfUserSusp();
			inexpirable = user.getFExpire().equals(-1);
		}
		profilsForUserUI.initBean();
		return null;
	}

	@Override
	protected void deleteRow() throws Exception {
		userService.deleteUserCascadeById(Util.toLong(this.selectedId));
		search();
	}

	public String saveUser() throws Exception {

		if (!userService.isUniqueUser(user.getLogin(), user.getIdUser())) {
			return validateError(getValFromResourceBundle("msg",
					"adminUser.fiche.login.unqMsg"));
		}

		if (initPwdActivated) {
			if (pwdConfirm.equals(pwd)) {
				user.setPwd(LoginUi.getPasswordMD5Hash(pwd));
				user.setFExpire(1);
			} else {
				return validateError(getValFromResourceBundle("msg",
						"adminUser.fiche.pwd.equalMsg"));
			}
		} else {
			if (user.getIdUser() == null) {
				user.setPwd(LoginUi.getPasswordMD5Hash(user.getLogin()));
			}
		}

		if (radioEtat.getSelectedId().equals("SUSP")) {
			if (user.getDatSuspDebut() == null && user.getDatSuspFin() == null) {
				return validateError(getValFromResourceBundle("msg",
						"adminUser.fiche.etat.periodSusp"));
			}
			if (user.getDatSuspDebut() != null
					&& user.getDatSuspDebut().before(
							DateManager.getSystemDate())) {
				return validateError(getValFromResourceBundle("msg",
						"adminUser.fiche.etat.suspMinDebut"));

			}

			if (user.getDatSuspFin() != null
					&& user.getDatSuspFin().before(DateManager.getSystemDate())) {
				return validateError(getValFromResourceBundle("msg",
						"adminUser.fiche.etat.suspMinFin"));

			}
		}

		if (user.getIdUser() == null) {
			user.setDatCreat(DateManager.getSystemDate());
		}
		user.setDatDerModif(DateManager.getSystemDate());
		user.setIdProfes(Util.toLong(comboProfEdit.getSelectedId()));
		user.setGenre(Util.toInteger(radioSexe.getSelectedId()));

		if (radioEtat.getSelectedId() != null) {

			if (user.getFExpire() == null) {
				user.setFExpire(0);
			}
			user.setFSusp(0);
			user.setFActif(1);

			switch (radioEtat.getSelectedId()) {
				case "INACTIF" :
					user.setFActif(0);
					break;
				case "SUSP" :
					user.setFSusp(1);
					break;
				default :
					break;
			}
		}
		if (user.getFSusp().equals(0)) {
			user.setDatSuspDebut(null);
			user.setDatSuspFin(null);
		}

		if (inexpirable) {
			user.setFExpire(-1);
		} else {
			if (user.getFExpire().equals(-1)) {
				user.setFExpire(0);
			}
		}

		user = userService.saveOrUpdateUser(user);

		profilsForUserUI.saveUserProfil(user.getIdUser());

		if (initPwdActivated) {
			userService.updateUserDateExpire(user.getIdUser());
		}

		this.search();
		return navigationName;
	}

	@Override
	public void initSearch() throws Exception {
		comboProfSearch.clearSelectedId();
		comboEtatSearch.clearSelectedId();
		dateDebutLastCnx = null;
		dateFinLastCnx = null;
		loginSearch = null;
		nameSearch = null;
		cinSearch = null;
	}

	public String search() throws Exception {

		Long idProf = Util.toLong(comboProfSearch.getSelectedId());
		Long idEtat = Util.toLong(comboEtatSearch.getSelectedId());

		addData(userService.getListVUtilisateurForSearch(loginSearch,
				nameSearch, cinSearch, dateDebutLastCnx, dateFinLastCnx,
				idProf, idEtat));
		return null;
	}

	public void initPwdByAdmin() {
		initPwdActivated = !initPwdActivated;
		pwd = "";
		pwdConfirm = "";
	}

	public String annuler() {
		return navigationName;
	}

	public void checkIfUserSusp() {
		userSusp = "SUSP".equals(radioEtat.getSelectedId());
	}

	public void checkIfOtherProfes() {
		otherProfesActivated = utils.getOtherProfessionId().equals(
				comboProfEdit.getSelectedId());
	}

	@Override
	public void updateListComboRech() throws Exception {
		// TODO Auto-generated method stub
		
	}
}