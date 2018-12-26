package tn.com.st2i.prj.admin.dao;

/**
 * Generated by easyfaces Hibernate Tools 4.3.1
 */


import java.util.List;

import com.easyfaces.dao.tools.IManagerDao;

import tn.com.st2i.prj.admin.model.AdmUserProfil;

public interface IAdmUserProfilDao extends IManagerDao<AdmUserProfil, Long> {

	public List<String> getListIdUserByProfilId(Long idProfil);

	public void deleteByProfilId(Long idProfil);

	public List<String> getListIdProfilByUserId(Long idUser);

	public void deleteByUserId(Long idUser);

}