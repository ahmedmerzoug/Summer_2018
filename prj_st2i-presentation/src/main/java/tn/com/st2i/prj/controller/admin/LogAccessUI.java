package tn.com.st2i.prj.controller.admin;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Controller;

import com.easyfaces.common.lang.SessionLang;
import com.easyfaces.common.utils.Util;
import com.easyfaces.controller.component.combo.UiComboBox;
import com.easyfaces.controller.tools.UiAbstractGen;

import tn.com.st2i.prj.admin.model.AdmFonc;
import tn.com.st2i.prj.admin.model.VAdmLogAcces;
import tn.com.st2i.prj.services.admin.ILogService;

@SuppressWarnings("serial")
@Controller("logAccessUI")
@Scope(value = "session", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class LogAccessUI extends UiAbstractGen {

	private final String NAVIGATION_NAME = "/pages/admin/log/listeLogAccess.xhtml";

	@Autowired()
	@Qualifier("logService")
	private ILogService logService;

	@Autowired()
	@Qualifier("sessionLang")
	private SessionLang sessionLang;

	@Autowired()
	@Qualifier("logDataBySessionUI")
	private LogDataBySessionUI logDataBySessionUI;

	@Getter
	@Setter
	private VAdmLogAcces logAccess;

	@Getter
	@Setter
	private UiComboBox comboFoncSearch;

	@Getter
	@Setter
	private Date dateDebut;

	@Getter
	@Setter
	private Date dateFin;

	@Getter
	@Setter
	private String adrSearch;

	@Getter
	@Setter
	private String nomSearch;

	@Getter
	@Setter
	private String loginSearch;

	@Getter
	@Setter
	private Boolean echec;

	@Getter
	@Setter
	private List<AdmFonc> listFonctions;

	public LogAccessUI() throws Exception {
		super("uiViewAdmin:listLogAccess");
	}

	@Override
	protected void postConstructMethod() throws Exception {
		this.setNavigationName(NAVIGATION_NAME);
		comboFoncSearch = createCombo("comboFoncSearch");
		comboFoncSearch.setWidthPanel(300);
		comboFoncSearch.setIsContainsFilter(true);
		this.iconButtonEdit = "ui-icon-view-detail";
		this.bundleButtonEdit = "logAccess.datatable.menu";
	}

	@Override
	public void init() throws Exception {
		addData(logService.getListLogAccess(null, null, null, null, null, null,
				null, null));

		initSearch();

		listFonctions = logService.getListFonc();
		listFonctions = formatListFonctions(listFonctions);

		comboFoncSearch.addDataListDual(listFonctions, "idFonc", "{desFonc}",
				"{desFoncAr}");
	}

	@Override
	protected String addRow() throws Exception {
		return null;
	}

	@Override
	protected String editRow() throws Exception {
		logAccess = logService.findLogAccessByID(selectedId);
		logDataBySessionUI.setInjectedLogAccess(logAccess);
		logDataBySessionUI.initBean();
		return null;
	}

	@Override
	protected void deleteRow() throws Exception {
	}

	@Override
	public void initSearch() throws Exception {
		comboFoncSearch.clearSelectedId();
		echec = false;
		loginSearch = null;
		nomSearch = null;
		adrSearch = null;
		dateDebut = null;
		dateFin = null;
	}

	public String search() throws Exception {

		Long idFonc = null;
		Long idParent = null;

		if (comboFoncSearch.getSelectedId() != null) {
			Long selectedFonc = null;
			selectedFonc = Util.toLong(comboFoncSearch.getSelectedId());
			for (AdmFonc f : listFonctions) {
				if (f.getIdFonc().equals(selectedFonc)) {
					if (f.getDesFonc().startsWith("--")) {
						idFonc = selectedFonc;
					} else {
						idParent = selectedFonc;
					}
					break;
				}
			}
		}
		addData(logService.getListLogAccess(loginSearch, nomSearch, dateDebut,
				dateFin, adrSearch, idFonc, idParent, echec));
		return null;
	}

	private List<AdmFonc> formatListFonctions(List<AdmFonc> listFonctions) {
		List<AdmFonc> listFormatedFonc = new ArrayList<>();
		List<AdmFonc> listRootFonc = new ArrayList<>();
		for (AdmFonc parentFonc : listFonctions) {
			if (parentFonc.getIdParent() == null) {
				listRootFonc.add(parentFonc);
			}
		}
		merge(listFormatedFonc, new ArrayList<Long>(), 0, listRootFonc);
		return listFormatedFonc;
	}

	private List<AdmFonc> getChildren(AdmFonc parentFonc) {
		List<AdmFonc> listChildren = new ArrayList<>();
		for (AdmFonc childFonc : listFonctions) {
			if (childFonc.getIdParent() != null) {
				if (childFonc.getIdParent().equals(parentFonc.getIdFonc())) {
					listChildren.add(childFonc);
				}
			}
		}
		return listChildren;
	}

	private boolean hasChildren(AdmFonc parentFonc) {
		return getChildren(parentFonc).size() != 0;
	}

	private void merge(List<AdmFonc> result, List<Long> listIdDone,
			int currCounter, List<AdmFonc> listFonc) {
		for (AdmFonc parentFonc : listFonc) {
			if (!listIdDone.contains(parentFonc.getIdFonc())) {
				String tabFr = "";
				String tabAr = "";
				for (int c = 0; c < currCounter; c++) {
					tabFr += "---"; // "|__";
					tabAr += "---"; // "__|";
				}
				AdmFonc currFonc = parentFonc;
				currFonc.setDesFonc(tabFr + (hasChildren(parentFonc) ? "" : "")
						+ parentFonc.getDesFonc()
						+ (hasChildren(parentFonc) ? "" : ""));
				currFonc.setDesFoncAr((hasChildren(parentFonc) ? "" : "")
						+ parentFonc.getDesFoncAr()
						+ (hasChildren(parentFonc) ? "" : "") + tabAr);
				result.add(currFonc);
				currFonc = null;
				if (hasChildren(parentFonc)) {
					merge(result, listIdDone, currCounter + 1,
							getChildren(parentFonc));
				}
				listIdDone.add(parentFonc.getIdFonc());
			}
		}
	}

	@Override
	public void updateListComboRech() throws Exception {
		// TODO Auto-generated method stub
		
	}

}