package tn.com.st2i.prj.services.admin.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.easyfaces.common.utils.Util;

import tn.com.st2i.prj.admin.dao.IAdmFoncProfilDao;
import tn.com.st2i.prj.admin.dao.IVAdmFoncProfilDao;
import tn.com.st2i.prj.admin.model.AdmFoncProfil;
import tn.com.st2i.prj.admin.model.VAdmFoncProfil;
import tn.com.st2i.prj.services.admin.IFoncProfilService;

@Service("foncProfilService")
public class FoncProfilService implements IFoncProfilService {

	@Autowired()
	@Qualifier("vAdmFoncProfilDao")
	private IVAdmFoncProfilDao vAdmFoncProfilDao;

	@Autowired()
	@Qualifier("admFoncProfilDao")
	private IAdmFoncProfilDao admFoncProfilDao;

	@Override
	public List<VAdmFoncProfil> getListFonctionsByProfilId(Long idProfil) {
		return vAdmFoncProfilDao.getListFonctionsByProfilId(idProfil);
	}

	@Override
	public AdmFoncProfil findFoncProfilByIds(Long idFonc, Long idProfil) {
		return admFoncProfilDao.findFoncProfilByIds(idFonc, idProfil);
	}

	@Override
	public void deleteFoncProfil(Long idFonc, Long idProfil) {
		AdmFoncProfil foncProfil = findFoncProfilByIds(idFonc, idProfil);
		if (foncProfil != null) {
			admFoncProfilDao.delete(foncProfil);
		}
	}

	@Override
	public AdmFoncProfil saveOrUpdateFoncProfil(AdmFoncProfil entity) {
		if (entity != null) {
			return admFoncProfilDao.saveOrUpdate(entity);
		}
		return null;
	}

	@Override
	public void deleteListFoncProfil(String idProfil) {
		Long id = Util.toLong(idProfil);
		if (id != null) {
			admFoncProfilDao.deleteFoncByIdProfil(id);
		}
	}

}
