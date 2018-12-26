package tn.com.st2i.prj.services.admin.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.easyfaces.common.utils.Util;
import com.easyfaces.dao.model.TableGen;

import tn.com.st2i.prj.admin.dao.IAdmProfessionDao;
import tn.com.st2i.prj.admin.dao.IAdmParamDao;
import tn.com.st2i.prj.admin.model.AdmProfession;
import tn.com.st2i.prj.services.admin.IProfessionService;

@Service("professionService")
public class ProfessionService implements IProfessionService {

	@Autowired()
	@Qualifier("admProfessionDao")
	private IAdmProfessionDao admProfessionDao;

	@Autowired()
	@Qualifier("admParamDao")
	private IAdmParamDao admParamDao;

	@Override
	public List<AdmProfession> getListProfessions() {
		return admProfessionDao.getListProfessions();
	}

	@Override
	public List<TableGen> getListProfessions(String codProfes, String desProfes)
			throws Exception {
		return admProfessionDao.getListProfessions(codProfes, desProfes,
				Util.toLong(admParamDao.findValueByCode("OTHER_PROFESSION_ID")));
	}

	@Override
	public AdmProfession findProfesById(String idProfes) {
		if (idProfes != null) {
			return admProfessionDao.findByID(AdmProfession.class,
					Util.toLong(idProfes));
		}
		return null;
	}

	@Override
	public void deleteProfes(AdmProfession entity) {
		admProfessionDao.delete(entity);
	}

	@Override
	public void deleteProfes(String idProfes) {
		AdmProfession profes;
		if (idProfes != null) {
			profes = findProfesById(idProfes);
			if (profes != null) {
				admProfessionDao.delete(profes);
			}
		}
	}
	
	@Override
	public AdmProfession saveOrUpdateProfes(AdmProfession entity){
		if (entity != null){
			return admProfessionDao.saveOrUpdate(entity);
		}
		return null;
	}
	
	@Override
	public Boolean isUniqueProfession(String codProfes,Long idProfes) {
		return admProfessionDao.isCodeProfessionUnique(codProfes, idProfes);
	}
}
