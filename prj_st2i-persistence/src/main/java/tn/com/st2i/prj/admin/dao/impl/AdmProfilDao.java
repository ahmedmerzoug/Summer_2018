package tn.com.st2i.prj.admin.dao.impl;

/**
 * prjerated by easyfaces Hibernate Tools 4.3.1
 */


import tn.com.st2i.prj.admin.dao.IAdmProfilDao;
import tn.com.st2i.prj.admin.model.AdmProfil;

import com.easyfaces.dao.tools.ManagerDao;
import com.easyfaces.dao.tools.UniqueRequest;

import org.springframework.stereotype.Repository;

@Repository("admProfilDao")
public class AdmProfilDao extends ManagerDao<AdmProfil, Long> implements IAdmProfilDao {

	public Boolean isCodeProfilUnique(String codProfil, Long idProfil) {
		UniqueRequest req = new UniqueRequest(AdmProfil.class);
		req.addId(idProfil).addUnique(AdmProfil._codProfil, codProfil);
		return isUnique(req);
	}
}
