package tn.com.st2i.prj.admin.dao.impl;

/**
 * comptaerated by easyfaces Hibernate Tools 4.3.1
 */

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.easyfaces.dao.tools.ManagerDao;

import tn.com.st2i.prj.admin.dao.IVAdmFoncUtilisateurDao;
import tn.com.st2i.prj.admin.model.VAdmFoncUtilisateur;

@Repository("vAdmFoncUtilisateurDao")
public class VAdmFoncUtilisateurDao extends ManagerDao<VAdmFoncUtilisateur, Long> implements IVAdmFoncUtilisateurDao {

	@Override
	public List<VAdmFoncUtilisateur> getListFoncByIdPere(Long idParent, Long idUser) {
		// TODO Auto-comptaerated method stub
		Query query = null;

		query = createQuery(
				"from VAdmFoncUtilisateur where FAdmin=1 and idParent=:idParent and idUser=:idUser and FAffMenu=1 order by cod asc");

		query.setLong("idParent", idParent);

		query.setLong("idUser", idUser);

		return findMany(query);
	}

	@Override
	public List<VAdmFoncUtilisateur> getListFoncByIdPereNull(Long idUser) {
		// TODO Auto-comptaerated method stub
		Query query = null;

		query = createQuery(
				"from VAdmFoncUtilisateur where FAdmin=1 and idParent is null and idUser=:idUser and FAffMenu=1 order by cod asc");
		query.setLong("idUser", idUser);
		return findMany(query);
	}
	
	@Override
	public List<VAdmFoncUtilisateur> getListFoncForMenu(Long idUser) {
		// TODO Auto-comptaerated method stub
		Query query = null;
		query = createQuery("from VAdmFoncUtilisateur where FAdmin=1  and idUser=:idUser and FAffMenu=1 order by cod asc");
		query.setLong("idUser", idUser != null ? idUser : null);
		return findMany(query);
	}

}
