package tn.com.st2i.prj.services.admin.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.easyfaces.common.utils.Util;
import com.easyfaces.dao.model.TableGen;

import tn.com.st2i.prj.admin.dao.IAdmApplicationDao;
import tn.com.st2i.prj.admin.dao.IAdmProfilDao;
import tn.com.st2i.prj.admin.dao.IAdmUserProfilDao;
import tn.com.st2i.prj.admin.dao.IVAdmProfilDao;
import tn.com.st2i.prj.admin.model.AdmApplication;
import tn.com.st2i.prj.admin.model.AdmProfil;
import tn.com.st2i.prj.admin.model.AdmUserProfil;
import tn.com.st2i.prj.admin.model.VAdmProfil;
import tn.com.st2i.prj.services.admin.IProfilService;

@Service("profilService")
public class ProfilService implements IProfilService {

	@Autowired()
	@Qualifier("vAdmProfilDao")
	private IVAdmProfilDao vAdmProfilDao;

	@Autowired()
	@Qualifier("admProfilDao")
	private IAdmProfilDao admProfilDao;

	@Autowired()
	@Qualifier("admUserProfilDao")
	private IAdmUserProfilDao admUserProfilDao;

	@Autowired()
	@Qualifier("admApplicationDao")
	private IAdmApplicationDao applicationDao;

	@Override
	public List<TableGen> getListVProfil(Long idApp, String code, String des,
			Long actif) throws Exception {
		return vAdmProfilDao.getListVProfil(idApp, code, des, actif);
	}

	@Override
	public VAdmProfil findVProfilById(Long id) {
		return vAdmProfilDao.findByID(VAdmProfil.class, id);
	}
	
	@Override
	public VAdmProfil findVProfilById(String id) {
		return vAdmProfilDao.findByID(VAdmProfil.class, Util.toLong(id));
	}

	@Override
	public List<AdmApplication> getListApplications() {
		return applicationDao.getListApplications();
	}

	@Override
	public AdmProfil saveOrUpdateProfil(AdmProfil entity) {
		return admProfilDao.saveOrUpdate(entity);
	}

	@Override
	public AdmProfil findProfilById(String id) {
		return admProfilDao.findByID(AdmProfil.class, Util.toLong(id));
	}

	@Override
	public void deleteProfil(String id) {
		AdmProfil profil = findProfilById(id);
		admProfilDao.delete(profil);
	}

	@Override
	public AdmUserProfil saveOrUpdateUserProfil(AdmUserProfil entity) {
		return admUserProfilDao.saveOrUpdate(entity);
	}

	@Override
	public List<String> getListIdUserByProfilId(Long idProfil) {
			return admUserProfilDao.getListIdUserByProfilId(idProfil);
	}

	@Override
	public void deleteUserProfilByProfilId(Long idProfil) {
		admUserProfilDao.deleteByProfilId(idProfil);
	}

	@Override
	public List<TableGen> getListVProfilActif(Long idApp, String code,
			String des) throws Exception {
		return vAdmProfilDao.getListVProfilActif(idApp, code, des);
	}

	@Override
	public Boolean isUniqueProfil(String codProfil,Long idProfil) {
		return admProfilDao.isCodeProfilUnique(codProfil, idProfil);
	}

}
