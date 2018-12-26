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

import tn.com.st2i.prj.admin.model.AdmProfil;
import tn.com.st2i.prj.admin.model.AdmUserProfil;
import tn.com.st2i.prj.services.admin.IProfilService;
import tn.com.st2i.prj.services.admin.IUserService;

@SuppressWarnings("serial")
@Controller("usersForProfilUI")
@Scope(value = "session", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class UsersForProfilUI extends UiAbstractGen {

	@Autowired()
	@Qualifier("userService")
	private IUserService userService;

	@Autowired()
	@Qualifier("profilService")
	private IProfilService profilService;

	@Getter
	@Setter
	private AdmProfil profil;

	public UsersForProfilUI() throws Exception {
		super("uiViewAdmin:listUsersForProfil");
	}

	@Override
	public void init() throws Exception {
		addData(userService.getListVUtilisateurForSearch(null, null, null,
				null, null, null, null));

		if (profil.getIdProfil() != null) {
			setSelectedListIdCheckBox(profilService
					.getListIdUserByProfilId(profil.getIdProfil()));
		}
	}

	public String saveUserProfil(Long idProfil) throws Exception {
		profilService.deleteUserProfilByProfilId(idProfil);
		if (this.getSelectedListIdCheckBox() != null) {
			AdmUserProfil userProfil = null;
			for (String idUser : this.getSelectedListIdCheckBox()) {
				userProfil = new AdmUserProfil();
				userProfil.setIdUser(Util.toLong(idUser));
				userProfil.setIdProfil(idProfil);
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
	protected String addRow() {
		return null;
	}

	@Override
	protected String editRow() {
		return null;
	}

	@Override
	protected void deleteRow() {
	}

	@Override
	public void initSearch() {
	}

	@Override
	public void updateListComboRech() throws Exception {
		// TODO Auto-generated method stub
		
	}

}