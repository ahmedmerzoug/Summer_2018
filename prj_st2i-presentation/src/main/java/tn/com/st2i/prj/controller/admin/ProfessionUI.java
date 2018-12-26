package tn.com.st2i.prj.controller.admin;

import lombok.Getter;
import lombok.Setter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Controller;

import com.easyfaces.controller.tools.UiAbstractGen;

import tn.com.st2i.prj.admin.model.AdmProfession;
import tn.com.st2i.prj.services.admin.IProfessionService;

@SuppressWarnings("serial")
@Controller("professionUI")
@Scope(value = "session", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class ProfessionUI extends UiAbstractGen {

	private final String NAVIGATION_NAME = "/pages/admin/nmt/listeProfes.xhtml";

	@Autowired()
	@Qualifier("professionService")
	private IProfessionService profesService;

	@Getter
	@Setter
	private AdmProfession profes;

	@Getter
	@Setter
	private String codProfesSearch;
	
	@Getter
	@Setter
	private String desProfesSearch;

	public ProfessionUI() throws Exception {
		super("uiViewAdmin:listProfes");
	}

	@Override
	protected void postConstructMethod() throws Exception {
		setNavigationName(NAVIGATION_NAME);
	}

	@Override
	public void init() throws Exception {
		addData(profesService.getListProfessions(null, null));
	}

	@Override
	protected String addRow() throws Exception {
		profes = new AdmProfession();
		return null;
	}

	@Override
	protected String editRow() throws Exception {
		if (selectedId != null) {
			profes = profesService.findProfesById(selectedId);
		}
		return null;
	}

	@Override
	protected void deleteRow() throws Exception {
		profesService.deleteProfes(selectedId);
		search();
	}

	public String saveProfes() throws Exception {

		if (!profesService
				.isUniqueProfession(profes.getCodProfes(), profes.getIdProfes())) {
			return validateError(getValFromResourceBundle("msg",
					"app.code.unqMsg"));
		}
		
		profesService.saveOrUpdateProfes(profes);
		
		addData(profesService.getListProfessions(null, null));
		return null;
	}

	@Override
	public void initSearch() throws Exception {
		codProfesSearch = null;
		desProfesSearch = null;
	}

	public String search() throws Exception {
		addData(profesService.getListProfessions(codProfesSearch,
				desProfesSearch));
		return null;
	}


	public String annuler() {
		return navigationName;
	}

	@Override
	public void updateListComboRech() throws Exception {
		// TODO Auto-generated method stub
		
	}
}