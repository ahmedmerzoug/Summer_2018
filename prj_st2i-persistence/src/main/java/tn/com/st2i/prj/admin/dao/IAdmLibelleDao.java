package tn.com.st2i.prj.admin.dao;

/**
 * Generated by easyfaces Hibernate Tools 4.3.1
 */


import java.util.List;

import com.easyfaces.dao.tools.IManagerDao;

import tn.com.st2i.prj.admin.model.AdmLibelle;

public interface IAdmLibelleDao extends IManagerDao<AdmLibelle, Long> {

	public List<AdmLibelle> getListValByCode(String code);

	public AdmLibelle getValByCode(String code);

	public List<AdmLibelle> getListUserEtats();

}