package tn.com.st2i.prj.admin.dao;

/**
 * Generated by easyfaces Hibernate Tools 4.3.1
 */


import java.util.List;

import com.easyfaces.dao.tools.IManagerDao;

import tn.com.st2i.prj.admin.model.VAdmFoncProfil;

public interface IVAdmFoncProfilDao extends IManagerDao<VAdmFoncProfil, Long> {

	public List<VAdmFoncProfil> getListFonctionsByProfilId(Long idProfil);

}