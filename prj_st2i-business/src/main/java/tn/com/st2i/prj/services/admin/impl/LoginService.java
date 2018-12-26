package tn.com.st2i.prj.services.admin.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import tn.com.st2i.prj.admin.dao.IAdmUtilisateurDao;
import tn.com.st2i.prj.admin.dao.IVAdmUtilisateurDao;
import tn.com.st2i.prj.admin.model.AdmUtilisateur;
import tn.com.st2i.prj.admin.model.VAdmUtilisateur;
import tn.com.st2i.prj.services.admin.ILoginService;

@Service("loginService")
public class LoginService implements ILoginService {

	@Autowired()
	@Qualifier("admUtilisateurDao")
	private IAdmUtilisateurDao admUtilisateurDao;
	
	@Autowired()
	@Qualifier("vAdmUtilisateurDao")
	private IVAdmUtilisateurDao vAdmUtilisateurDao;
	
	@Override
	public AdmUtilisateur findUserByLoginPwd(String login, String pwd){
		if(login!=null && pwd!=null){
			return admUtilisateurDao.findUserByLoginPwd(login, pwd);
		}
		return null;
	}
	
	@Override
	public AdmUtilisateur findUserByLogin(String login){
		if(login!=null){
			return admUtilisateurDao.findUserByLogin(login);
		}
		return null;
	}
	
	@Override
	public VAdmUtilisateur findVUserByLoginPwd(String login, String pwd){
		if(login!=null && pwd!=null){
			return vAdmUtilisateurDao.findVUserByLoginPwd(login, pwd);
		}
		return null;
	}
}
