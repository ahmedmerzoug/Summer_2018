package tn.com.st2i.prj.services.admin;


import tn.com.st2i.prj.admin.model.AdmUtilisateur;
import tn.com.st2i.prj.admin.model.VAdmUtilisateur;

public interface ILoginService {

	public AdmUtilisateur findUserByLoginPwd(String login, String pwd);
	
	public AdmUtilisateur findUserByLogin(String login);

	public VAdmUtilisateur findVUserByLoginPwd(String login, String pwd);

}