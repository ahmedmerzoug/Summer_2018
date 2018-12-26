package tn.com.st2i.prj.admin.dao.impl;

/**
 * prjerated by easyfaces Hibernate Tools 4.3.1
 */

import java.math.BigDecimal;
import java.util.List;

import tn.com.st2i.prj.admin.dao.IAdmUserProfilDao;import tn.com.st2i.prj.admin.dao.IAdmUtilisateurDao;import tn.com.st2i.prj.admin.model.AdmUtilisateur;

import com.easyfaces.dao.tools.ManagerDao;
import com.easyfaces.dao.tools.UniqueRequest;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

@Repository("admUtilisateurDao")
public class AdmUtilisateurDao extends ManagerDao<AdmUtilisateur, Long> implements IAdmUtilisateurDao {

	@Autowired()
	@Qualifier("admUserProfilDao")
	private IAdmUserProfilDao admUserProfilDao;

	@Override
	public AdmUtilisateur findUserByLoginPwd(String login, String pwd) {
		Query query = null;

		query = createQuery("from AdmUtilisateur where login=:login and pwd=:pwd");
		query.setString("login", login);
		query.setString("pwd", pwd);

		return findOne(query);
	}

	@Override
	public AdmUtilisateur findUserByLogin(String login) {
		Query query = null;

		query = createQuery("from AdmUtilisateur where login=:login");
		query.setString("login", login);

		return findOne(query);
	}

	@Override
	public Boolean isLongUserUnique(String login, Long idUser) {
		UniqueRequest request = new UniqueRequest(AdmUtilisateur.class);
		request.addId(idUser).addUnique(AdmUtilisateur._login, login);
		return isUnique(request);
	}

	@Override
	public void updateUserDateExpire(Long idUser) {
		SQLQuery query = null;
					query = createSqlQuery("select admin.update_date_exp_user(:idUser)");
			query.setLong("idUser",idUser);
			query.list();
										}

	@Override
				public Integer verifyUserEtat(Long idUser) {
										
		SQLQuery query = null;
		List<?> list;
		    			query = createSqlQuery("select admin.verify_user_etat(:idUser)");
			query.setLong("idUser", idUser);
			list = query.list();
			if(list!=null){
				return (Integer)list.get(0);
			}
																return 0;
											
	}

	public void deleteUserCascadeById(Long idUser) {
		admUserProfilDao.deleteByUserId(idUser);
		AdmUtilisateur user = findByID(AdmUtilisateur.class, idUser);
		delete(user);
	}

}
