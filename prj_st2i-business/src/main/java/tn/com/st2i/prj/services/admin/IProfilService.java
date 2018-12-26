package tn.com.st2i.prj.services.admin;

import java.util.List;

import com.easyfaces.dao.model.TableGen;

import tn.com.st2i.prj.admin.model.AdmApplication;
import tn.com.st2i.prj.admin.model.AdmProfil;
import tn.com.st2i.prj.admin.model.AdmUserProfil;
import tn.com.st2i.prj.admin.model.VAdmProfil;

public interface IProfilService {

	public List<TableGen> getListVProfil(Long idApp, String code, String des,
			Long actif) throws Exception;

	public List<AdmApplication> getListApplications();

	public AdmProfil saveOrUpdateProfil(AdmProfil entity);

	public AdmProfil findProfilById(String id);

	public void deleteProfil(String id);

	public VAdmProfil findVProfilById(Long id);

	public AdmUserProfil saveOrUpdateUserProfil(AdmUserProfil entity);

	public List<String> getListIdUserByProfilId(Long idProfil);

	public void deleteUserProfilByProfilId(Long idProfil);

	public List<TableGen> getListVProfilActif(Long idApp, String code, String des)
			throws Exception;

	public VAdmProfil findVProfilById(String id);
	
	public Boolean isUniqueProfil(String codProfil,Long idProfil);

}