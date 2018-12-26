package tn.com.st2i.prj.admin.dao.impl;

/**
 * prjerated by easyfaces Hibernate Tools 4.3.1
 */


import java.util.List;

import com.easyfaces.dao.tools.ManagerDao;

import tn.com.st2i.prj.admin.dao.IAdmLibelleDao;
import tn.com.st2i.prj.admin.model.AdmLibelle;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

@Repository("admLibelleDao")
public class AdmLibelleDao extends ManagerDao<AdmLibelle, Long> implements
		IAdmLibelleDao {

	@Override
	public List<AdmLibelle> getListValByCode(String code) {
		Query query = null;
		query = createQuery("from AdmLibelle where code=:code order by ordre");
		query.setString("code", code);
		return findMany(query);
	}
	
	@Override
	public AdmLibelle getValByCode(String code) {
		Query query = null;
		query = createQuery("from AdmLibelle where code=:code");
		query.setString("code", code);
		return findOne(query);
	}

	@Override
	public List<AdmLibelle> getListUserEtats() {
		Query query = null;
		query = createQuery("from AdmLibelle where code IN ('USER_ACTIF','USER_NO_ACTIF','USER_SUSP','USER_EXPIRE') order by ordre");
		return findMany(query);
	}
	
}
