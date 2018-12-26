package tn.com.st2i.prj.admin.dao.impl;

/**
 * prjerated by easyfaces Hibernate Tools 4.3.1
 */


import java.util.List;

import tn.com.st2i.prj.admin.dao.IAdmFoncDao;
import tn.com.st2i.prj.admin.model.AdmFonc;

import com.easyfaces.dao.tools.ManagerDao;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

@Repository("admFoncDao")
public class AdmFoncDao extends ManagerDao<AdmFonc, Long> implements IAdmFoncDao {

	@Override
	public List<AdmFonc> getListFoncMenu() {
		Query query = null;

		query = createQuery("from AdmFonc where FAdmin=1 and FValid=1 and FAffEtat=0 order by cod asc");

		return findMany(query);
	}

	@Override
	public List<AdmFonc> getListFoncByIdApp(Long idApp) {
		Query query = null;

		query = createQuery("from AdmFonc where FAdmin=1 and idApp=:idApp and FValid=1 order by cod asc");
		query.setLong("idApp", idApp);

		return findMany(query);
	}

	@Override
	public List<AdmFonc> getListFoncByIdPere(Long idParent) {
		// TODO Auto-prjerated method stub
		Query query = null;

		query = createQuery("from AdmFonc where FAdmin=1 and idParent=:idParent and FValid=1 and FAffMenu=1 order by cod asc");
	
		query.setLong("idParent", idParent);

		return findMany(query);
	}


	@Override
	public List<AdmFonc> getListFoncByIdPereNull() {
		// TODO Auto-prjerated method stub
		Query query = null;

		query = createQuery("from AdmFonc where FAdmin=1 and idParent is null and FValid=1 and FAffMenu=1 order by cod asc");
	
		

		return findMany(query);
	}

}
