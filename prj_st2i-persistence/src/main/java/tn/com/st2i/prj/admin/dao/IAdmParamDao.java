package tn.com.st2i.prj.admin.dao;

/**
 * Generated by easyfaces Hibernate Tools 4.3.1
 */


import com.easyfaces.dao.tools.IManagerDao;

import tn.com.st2i.prj.admin.model.AdmParam;

public interface IAdmParamDao extends IManagerDao<AdmParam, Long> {

	public String findValueByCode(String code);

}
