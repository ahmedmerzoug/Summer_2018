package tn.com.st2i.prj.admin.dao.impl;

/**
 * prjerated by easyfaces Hibernate Tools 4.3.1
 */


import java.util.List;

import com.easyfaces.dao.tools.ManagerDao;

import tn.com.st2i.prj.admin.dao.IVAdmFoncProfilDao;
import tn.com.st2i.prj.admin.model.VAdmFoncProfil;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

@Repository("vAdmFoncProfilDao")
public class VAdmFoncProfilDao extends ManagerDao<VAdmFoncProfil, Long> implements
		IVAdmFoncProfilDao {

	@Override
	public List<VAdmFoncProfil> getListFonctionsByProfilId(Long idProfil){
		Query query=null;
		
		query=createQuery("from VAdmFoncProfil where idProfil=:idProfil order by cod asc");
		query.setLong("idProfil",idProfil);
		
		return findMany(query);
	}
	
}
