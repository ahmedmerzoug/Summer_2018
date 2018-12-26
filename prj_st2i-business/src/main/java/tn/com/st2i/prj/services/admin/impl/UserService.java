package tn.com.st2i.prj.services.admin.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import tn.com.st2i.prj.admin.dao.IAdmLibelleDao;
import tn.com.st2i.prj.admin.dao.IAdmProfessionDao;
import tn.com.st2i.prj.admin.dao.IAdmUserProfilDao;
import tn.com.st2i.prj.admin.dao.IAdmUtilisateurDao;
import tn.com.st2i.prj.admin.dao.IVAdmUtilisateurDao;
import tn.com.st2i.prj.admin.model.AdmLibelle;
import tn.com.st2i.prj.admin.model.AdmProfession;
import tn.com.st2i.prj.admin.model.AdmUserProfil;
import tn.com.st2i.prj.admin.model.AdmUtilisateur;
import tn.com.st2i.prj.admin.model.VAdmUtilisateur;
import tn.com.st2i.prj.services.admin.IUserService;

import com.easyfaces.common.utils.Util;
import com.easyfaces.dao.model.TableGen;

@Service("userService")
public class UserService implements IUserService {

	@Autowired()
	@Qualifier("vAdmUtilisateurDao")
	private IVAdmUtilisateurDao vAdmUtilisateurDao;

	@Autowired()
	@Qualifier("admProfessionDao")
	private IAdmProfessionDao admProfessionDao;

	@Autowired()
	@Qualifier("admLibelleDao")
	private IAdmLibelleDao admLibelleDao;

	@Autowired()
	@Qualifier("admUtilisateurDao")
	private IAdmUtilisateurDao admUtilisateurDao;

	@Autowired()
	@Qualifier("admUserProfilDao")
	private IAdmUserProfilDao admUserProfilDao;

	@Override
	public List<TableGen> getListVUtilisateurForSearch(String login,
			String name, String cin, Date dateDebutLastCnx,
			Date dateFinLastCnx, Long idProf, Long idEtat) throws Exception {
		return vAdmUtilisateurDao.getListVUtilisateurForSearch(login, name, cin,
				dateDebutLastCnx, dateFinLastCnx, idProf, idEtat);
	}

	@Override
	public List<AdmProfession> getListProfessions() {
		return admProfessionDao.getListProfessions();
	}

	@Override
	public List<AdmLibelle> getListUserEtats() {
		return admLibelleDao.getListUserEtats();
	}

	@Override
	public AdmUtilisateur saveOrUpdateUser(AdmUtilisateur entity) {
		return admUtilisateurDao.saveOrUpdate(entity);
	}

	@Override
	public AdmUtilisateur findUserById(String id) {
		return admUtilisateurDao.findByID(AdmUtilisateur.class,  Util.toLong(id));
	}
	
	@Override
	public AdmUtilisateur findNewValueUserById(AdmUtilisateur entity,String id) {
		admUtilisateurDao.evict(entity);
		return admUtilisateurDao.findByID(AdmUtilisateur.class, Util.toLong(id));
	}

	@Override
	public VAdmUtilisateur findVUserById(String id) {
		return vAdmUtilisateurDao.findByID(VAdmUtilisateur.class,  Util.toLong(id));
	}

	@Override
	public Boolean isUniqueUser(String login,Long idUser){
		return admUtilisateurDao.isLongUserUnique(login, idUser);
	}

	@Override
	public List<String> getListIdProfilByUserId(Long idUser) {
			return admUserProfilDao.getListIdProfilByUserId(idUser);
	}

	@Override
	public void deleteUserProfilByUserId(Long idUser) {
		admUserProfilDao.deleteByUserId(idUser);
	}

	@Override
	public AdmUserProfil saveOrUpdateUserProfil(AdmUserProfil entity) {
		return admUserProfilDao.saveOrUpdate(entity);
	}

	@Override
	public void updateUserDateExpire(Long idUser) {
		admUtilisateurDao.updateUserDateExpire(idUser);
	}

	@Override
			public Integer verifyUserEtat(Long idUser) {
	    	    			
		return admUtilisateurDao.verifyUserEtat(idUser);
	}
	
	@Override
	public void deleteUserCascadeById(Long idUser) {
		admUtilisateurDao.deleteUserCascadeById(idUser);
	}
}
