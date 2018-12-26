package tn.com.st2i.prj.admin.dao.impl;

/**
 * prjerated by easyfaces Hibernate Tools 4.3.1
 */


import com.easyfaces.dao.tools.ManagerDao;

import tn.com.st2i.prj.admin.dao.IAdmParamDao;
import tn.com.st2i.prj.admin.model.AdmParam;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

@Repository("admParamDao")
public class AdmParamDao extends ManagerDao<AdmParam, Long> implements
		IAdmParamDao {

	@Override
	public String findValueByCode(String code){
		Query query=null;
		
		query=createQuery("from AdmParam where codParam=:code");
		query.setString("code",code);
		
		AdmParam p = findOne(query);
		return p!=null?p.getValParam():null;
	}
	
}
