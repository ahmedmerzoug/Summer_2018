package tn.com.st2i.prj.admin.dao;

import java.util.List;

/**
 * Generated by easyfaces Hibernate Tools 4.3.1
 */


import com.easyfaces.dao.tools.IManagerDao;

import tn.com.st2i.prj.admin.model.VAdmFoncUtilisateur;

public interface IVAdmFoncUtilisateurDao extends IManagerDao<VAdmFoncUtilisateur, Long> {

	public abstract List<VAdmFoncUtilisateur> getListFoncByIdPereNull(
			Long idUser);

	public abstract List<VAdmFoncUtilisateur> getListFoncByIdPere(
			Long idParent, Long idUser);
	
	public List<VAdmFoncUtilisateur> getListFoncForMenu(Long idUser);
}
