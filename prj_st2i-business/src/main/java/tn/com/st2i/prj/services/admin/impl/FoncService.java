package tn.com.st2i.prj.services.admin.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import tn.com.st2i.prj.admin.dao.IAdmFoncDao;
import tn.com.st2i.prj.admin.dao.IVAdmFoncUtilisateurDao;
import tn.com.st2i.prj.admin.model.AdmFonc;
import tn.com.st2i.prj.admin.model.VAdmFoncUtilisateur;
import tn.com.st2i.prj.services.admin.IFoncService;

@Service("foncService")
public class FoncService implements IFoncService {

	@Autowired()
	@Qualifier("admFoncDao")
	private IAdmFoncDao admFoncDao;

	@Autowired()
	@Qualifier("vAdmFoncUtilisateurDao")
	private IVAdmFoncUtilisateurDao vAdmFoncUtilisateurDao;

	@Override
	public List<AdmFonc> getListFoncByIdApp(Long idApp) {
		return admFoncDao.getListFoncByIdApp(idApp);
	}

	@Override
	public AdmFonc findFoncByID(Long idFonc) {
		return admFoncDao.findByID(AdmFonc.class, idFonc);
	}

	@Override
	public List<AdmFonc> getListFoncByIdPere(Long id) {
		// TODO Auto-Generated method stub
		return admFoncDao.getListFoncByIdPere(id);
	}

	public List<AdmFonc> getListFoncByIdPereNull() {
		return admFoncDao.getListFoncByIdPereNull();
	}

	@Cacheable(value = "penale", key = "#root.targetClass + ',' +#root.methodName + ',' + #p0 + ',' + #p1")
	@Override
	public List<VAdmFoncUtilisateur> getListFoncByIdPere1(Long id, Long idUser) {
		// TODO Auto-Generated method stub
		return vAdmFoncUtilisateurDao.getListFoncByIdPere(id, idUser);
	}

	@Cacheable(value = "penale", key = "#root.targetClass + ',' +#root.methodName + ',' + #p0")
	@Override
	public List<VAdmFoncUtilisateur> getListFoncByIdPereNull1(Long idUser) {
		return vAdmFoncUtilisateurDao.getListFoncByIdPereNull(idUser);
	}
	
	@Override
	public List<VAdmFoncUtilisateur> getListFoncForMenu(Long idUser){
		return vAdmFoncUtilisateurDao.getListFoncForMenu(idUser);
	}
	
	@Override
	public VAdmFoncUtilisateur findVadmutilisateurFoncByID(Long idFonc) {
		return vAdmFoncUtilisateurDao.findByID(VAdmFoncUtilisateur.class, idFonc);
	}
}
