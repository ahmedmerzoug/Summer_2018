package tn.com.st2i.prj.admin.dao;

/**
 * Generated by easyfaces Hibernate Tools 4.3.1
 */


import com.easyfaces.dao.tools.IManagerDao;

import tn.com.st2i.prj.admin.model.AdmFoncProfil;

public interface IAdmFoncProfilDao extends IManagerDao<AdmFoncProfil, Long> {

	public AdmFoncProfil findFoncProfilByIds(Long idFonc, Long idProfil);
	
	public void deleteFoncByIdProfil(Long idProfil);

}
