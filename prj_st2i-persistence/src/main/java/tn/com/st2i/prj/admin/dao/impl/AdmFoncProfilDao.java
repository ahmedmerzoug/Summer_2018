package tn.com.st2i.prj.admin.dao.impl;

/**
 * prjerated by easyfaces Hibernate Tools 4.3.1
 */


import tn.com.st2i.prj.admin.dao.IAdmFoncProfilDao;
import tn.com.st2i.prj.admin.model.AdmFoncProfil;

import com.easyfaces.dao.tools.ManagerDao;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

@Repository("admFoncProfilDao")
public class AdmFoncProfilDao extends ManagerDao<AdmFoncProfil, Long> implements
		IAdmFoncProfilDao {

	@Override
	public AdmFoncProfil findFoncProfilByIds(Long idFonc, Long idProfil){
		Query query=null;
		
		query=createQuery("from AdmFoncProfil where idFonc=:idFonc and idProfil=:idProfil");
		query.setLong("idFonc",idFonc);
		query.setLong("idProfil",idProfil);
		
		return findOne(query);
	}
	
	@Override
	public void deleteFoncByIdProfil(Long idProfil) {
		Query query=createQuery("delete from AdmFoncProfil where idProfil=:idProfil");
		query.setLong("idProfil",idProfil);
		query.executeUpdate();
	}
	
}
