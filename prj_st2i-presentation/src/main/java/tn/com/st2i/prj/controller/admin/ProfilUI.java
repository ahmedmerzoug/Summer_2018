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

import tn.com.st2i.prj.admin.model.AdmApplication;
import tn.com.st2i.prj.admin.model.AdmProfil;
import tn.com.st2i.prj.admin.model.VAdmProfil;
import tn.com.st2i.prj.services.admin.IFoncProfilService;
import tn.com.st2i.prj.services.admin.IProfilService;

@SuppressWarnings("serial")
@Controller("profilUI")
@Scope(value = "session", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class ProfilUI extends UiAbstractGen {

	private final static String NAVIGATION_NAME = "/pages/admin/profil/listeProfils.xhtml";
	private final static String NAVIGATION_NAME_EDIT = "/pages/admin/profil/ficheProfil.xhtml";

	@Autowired()
	@Qualifier("profilService")
	private IProfilService profilService;

	@Autowired()
	@Qualifier("foncProfilService")
	private IFoncProfilService foncProfilService;

	@Autowired()
	@Qualifier("foncBean")
	private FoncBean foncBean;

	@Autowired()
	@Qualifier("usersForProfilUI")
	private UsersForProfilUI usersForProfilUI;

	@Getter
	@Setter
	private Boolean modifTreeFoncActivated;

	@Getter
	@Setter
	private Boolean profilActif;

	@Getter
	@Setter
	private UiComboBox comboAppSearch;
	
	@Getter
	@Setter
	private UiComboBox comboAppEdit;

	@Getter
	@Setter
	private String codeSearch;

	@Getter
	@Setter
	private String desSearch;
	
	@Getter
	@Setter
	private VAdmProfil viewProfil;

	@Getter
	@Setter
	private AdmProfil profil;
	
	@Getter
	@Setter
	private UiRadioBox radioActifEdit;

	public ProfilUI() throws Exception {
		super("uiViewAdmin:listProfils");
	}

	@Override
	protected void postConstructMethod() throws Exception {
		initComponents();
		this.setNavigationName(NAVIGATION_NAME);
		this.setNavigationNameEdit(NAVIGATION_NAME_EDIT);
	}

	public void initComponents() throws Exception {
		comboAppEdit = createCombo("comboAppEdit");
		comboAppEdit.setRequired(true);

		comboAppSearch = createCombo("comboAppSearch");
		radioActifEdit = createDefaultRadio("radioActifEdit");
	}

	@Override
	public void init() throws Exception {
		addData(profilService.getListVProfil(null, null, null, null));

		List<AdmApplication> listApps = profilService.getListApplications();
		comboAppEdit.addDataListDual(listApps, "idApp", "{desApp}",
				"{desAppAr}");
		comboAppSearch.addDataListDual(listApps, "idApp", "{desApp}",
				"{desAppAr}");

	}

	@Override
	protected String addRow() throws Exception {
		profil = new AdmProfil();
		usersForProfilUI.setProfil(profil);
		comboAppEdit.clearSelectedId();
		radioActifEdit.setSelectedId("1");
		profilActif = true;
		modifTreeFoncActivated = false;
		usersForProfilUI.initBean();
		return null;
	}

	@Override
	protected String editRow() throws Exception {

		if (selectedId != null) {
			profil = profilService.findProfilById(selectedId);
			viewProfil = profilService.findVProfilById(selectedId);
			usersForProfilUI.setProfil(profil);
			if (profil.getIdApp() != null) {
				comboAppEdit.setSelectedId(profil.getIdApp().toString());
			}
			radioActifEdit.setSelectedId("0");
			profilActif = false;
			if (profil.getFActif().equals(1)) {
				radioActifEdit.setSelectedId("1");
				profilActif = true;
			}
		}
		modifTreeFoncActivated = true;
		initTreeFonc();
		usersForProfilUI.initBean();
		return null;
	}

	@Override
	protected void deleteRow() throws Exception {
		foncProfilService.deleteListFoncProfil(this.selectedId);
		profilService.deleteProfil(this.selectedId);
		search();
	}

	public void initTreeFonc() throws Exception {
		if (profil.getIdProfil() != null) {
			foncBean.setDroitAcces(null);
			foncBean.setViewProfil(profilService.findVProfilById(profil
					.getIdProfil()));
			foncBean.setSelectedNode(null);
			foncBean.createTreeFonc();
			foncBean.setDisplayPanelAction(false);
		}
	}

	public String saveProfilAndSelectedUsers() throws Exception {
		if (!profilService.isUniqueProfil(profil.getCodProfil(),
				profil.getIdProfil())) {
			return validateError(getValFromResourceBundle("msg",
					"app.code.unqMsg"));
		}

		profil.setIdApp(Util.toLong(comboAppEdit.getSelectedId()));

		if (radioActifEdit.getSelectedId() != null) {
			profil.setFActif(Util.toInteger(radioActifEdit.getSelectedId()));
		}
		if (profil.getIdProfil() == null) {
			profil.setDatCreat(DateManager.getSystemDate());
		}
		profil.setDatDerModif(DateManager.getSystemDate());

		profil = profilService.saveOrUpdateProfil(profil);

		if (profilActif) {
			usersForProfilUI.saveUserProfil(profil.getIdProfil());
		}

		if (!modifTreeFoncActivated) {
			modifTreeFoncActivated = !modifTreeFoncActivated;
			initTreeFonc();
			return null;
		}

		this.search();

		return navigationName;
	}

	@Override
	public void initSearch() throws Exception {
		comboAppSearch.clearSelectedId();
		codeSearch = null;
		desSearch = null;
	}

	public String search() throws Exception {
		Long idApp = Util.toLong(comboAppSearch.getSelectedId());
		addData(profilService
				.getListVProfil(idApp, codeSearch, desSearch, null));
		return null;
	}

	public void changeProfilState() throws Exception {
		profilActif = !profilActif;
	}

	public String annuler() throws Exception {
		search();
		return navigationName;
	}

	public String saveAddFoncs() throws Exception {
		return this.navigationNameEdit;
	}

	@Override
	public void updateListComboRech() throws Exception {
		// TODO Auto-generated method stub
		
	}

}