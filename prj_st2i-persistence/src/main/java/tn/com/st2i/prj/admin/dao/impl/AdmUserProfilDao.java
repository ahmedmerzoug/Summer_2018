package tn.com.st2i.prj.admin.dao.impl;

/**
 * prjerated by easyfaces Hibernate Tools 4.3.1
 */


import java.util.ArrayList;
import java.util.List;

import tn.com.st2i.prj.admin.dao.IAdmUserProfilDao;
import tn.com.st2i.prj.admin.model.AdmUserProfil;

import com.easyfaces.dao.tools.ManagerDao;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

@Repository("admUserProfilDao")
public class AdmUserProfilDao extends ManagerDao<AdmUserProfil, Long> implements
		IAdmUserProfilDao {

	@SuppressWarnings("unchecked")
	@Override
	public List<String> getListIdUserByProfilId(Long idProfil){
		List<String> listString = new ArrayList<>();
		Query query=null;
		
		query=createQuery("select idUser from AdmUserProfil where idProfil=:idProfil");
		query.setLong("idProfil",idProfil);
		
		List<Long> listLong = query.list();
		for(Long l:listLong){
			listString.add(""+l);
		}
		return listString;
	}
	
	@Override
	public void deleteByProfilId(Long idProfil){
		Query query=null;
		query=createQuery("delete from AdmUserProfil where idProfil=:idProfil");
		query.setLong("idProfil",idProfil!=null?idProfil:0L);
		
		query.executeUpdate();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<String> getListIdProfilByUserId(Long idUser){
		List<String> listString = new ArrayList<>();
		Query query=null;
		
		query=createQuery("select idProfil from AdmUserProfil where idUser=:idUser");
		query.setLong("idUser",idUser);
		
		List<Long> listLong = query.list();
		for(Long l:listLong){
			listString.add(""+l);
		}
		return listString;
	}
	
	@Override
	public void deleteByUserId(Long idUser){
		Query query=null;
		query=createQuery("delete from AdmUserProfil where idUser=:idUser");
		query.setLong("idUser",idUser);
		
		query.executeUpdate();
	}
	
}
