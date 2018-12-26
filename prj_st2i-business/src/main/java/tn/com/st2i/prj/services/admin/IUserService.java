package tn.com.st2i.prj.services.admin;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.easyfaces.dao.model.TableGen;

import tn.com.st2i.prj.admin.model.AdmProfession;
import tn.com.st2i.prj.admin.model.AdmLibelle;
import tn.com.st2i.prj.admin.model.AdmUserProfil;
import tn.com.st2i.prj.admin.model.AdmUtilisateur;
import tn.com.st2i.prj.admin.model.VAdmUtilisateur;

public interface IUserService {

	public List<TableGen> getListVUtilisateurForSearch(String login,
			String name, String cin, Date dateDebutLastCnx,
			Date dateFinLastCnx, Long idProf, Long idEtat) throws Exception;

	public List<AdmProfession> getListProfessions();

	public List<AdmLibelle> getListUserEtats();

	public AdmUtilisateur saveOrUpdateUser(AdmUtilisateur entity);

	public AdmUtilisateur findUserById(String id);
	
	public AdmUtilisateur findNewValueUserById(AdmUtilisateur entity,String id);
	
	public VAdmUtilisateur findVUserById(String id);

	public Boolean isUniqueUser(String login,Long idUser);

	public List<String> getListIdProfilByUserId(Long idUser);

	public void deleteUserProfilByUserId(Long idUser);

	public AdmUserProfil saveOrUpdateUserProfil(AdmUserProfil entity);

	public void updateUserDateExpire(Long idUser);

			public Integer verifyUserEtat(Long idUser);
	    	    			
	public void deleteUserCascadeById(Long idUser);

}