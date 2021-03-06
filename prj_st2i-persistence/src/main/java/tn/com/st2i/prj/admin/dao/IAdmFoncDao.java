package tn.com.st2i.prj.admin.dao;

/**
 * Generated by easyfaces Hibernate Tools 4.3.1
 */


import java.util.List;

import org.hibernate.Query;

import com.easyfaces.dao.tools.IManagerDao;

import tn.com.st2i.prj.admin.model.AdmFonc;

public interface IAdmFoncDao extends IManagerDao<AdmFonc, Long> {

	public List<AdmFonc> getListFoncMenu();

	public List<AdmFonc> getListFoncByIdApp(Long idApp);
	public List<AdmFonc> getListFoncByIdPere(Long id);

	public  List<AdmFonc> getListFoncByIdPereNull();


}
