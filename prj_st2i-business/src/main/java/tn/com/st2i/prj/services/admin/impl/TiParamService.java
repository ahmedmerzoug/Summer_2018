package tn.com.st2i.prj.services.admin.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import tn.com.st2i.prj.admin.dao.IAdmParamDao;
import tn.com.st2i.prj.services.admin.ITiParamService;

@Service("tiParamService")
public class TiParamService implements ITiParamService {

	@Autowired()
	@Qualifier("admParamDao")
	private IAdmParamDao admParamDao;

	@Override
	public String findValueByCode(String code){
		return admParamDao.findValueByCode(code);
	}

}
