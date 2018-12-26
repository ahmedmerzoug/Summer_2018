package tn.com.st2i.prj.controller.admin;

import lombok.Getter;
import lombok.Setter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Controller;

import com.easyfaces.common.utils.Util;
import com.easyfaces.controller.tools.UiAbstractGen;

import tn.com.st2i.prj.admin.model.AdmUserProfil;
import tn.com.st2i.prj.admin.model.AdmUtilisateur;
import tn.com.st2i.prj.services.admin.IProfilService;
import tn.com.st2i.prj.services.admin.IUserService;

@SuppressWarnings("serial")
@Controller("profilsForUserUI")
@Scope(value = "session", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class ProfilsForUserUI extends UiAbstractGen {

	@Autowired()
	@Qualifier("profilService")
	private IProfilService profilService;

	@Autowired()
	@Qualifier("userService")
	private IUserService userService;

	@Getter
	@Setter
	private AdmUtilisateur user;

	public ProfilsForUserUI() throws Exception {
		super("uiViewAdmin:listProfilsForUser");
	}

	@Override
	public void init() throws Exception {
		addData(profilService.getListVProfilActif(null, null, null));

		if (user!=null && user.getIdUser() != null) {
			setSelectedListIdCheckBox(userService.getListIdProfilByUserId(user
					.getIdUser()));
		}
	}
	
	public String saveUserProfil(Long idUser) throws Exception {
		userService.deleteUserProfilByUserId(idUser);
		if (this.getSelectedListIdCheckBox() != null) {
			AdmUserProfil userProfil = null;
			for (String idProfil : this.getSelectedListIdCheckBox()) {
				userProfil = new AdmUserProfil();
				userProfil.setIdUser(idUser);
				userProfil.setIdProfil(Util.toLong(idProfil));
				userProfil.setFValid(1);
				profilService.saveOrUpdateUserProfil(userProfil);
			}
		}
		return null;
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