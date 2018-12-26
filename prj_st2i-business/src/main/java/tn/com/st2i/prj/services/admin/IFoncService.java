package tn.com.st2i.prj.services.admin;

import java.util.List;

import tn.com.st2i.prj.admin.model.AdmFonc;
import tn.com.st2i.prj.admin.model.VAdmFoncUtilisateur;

public interface IFoncService {

	public AdmFonc findFoncByID(Long idFonc);

	public List<AdmFonc> getListFoncByIdApp(Long idApp);
	
	public List<AdmFonc> getListFoncByIdPere(Long id);
	
	public  List<AdmFonc> getListFoncByIdPereNull();

	public abstract List<VAdmFoncUtilisateur> getListFoncByIdPereNull1(Long idUser);

	List<VAdmFoncUtilisateur> getListFoncByIdPere1(Long id,Long idUser);
	
	public List<VAdmFoncUtilisateur> getListFoncForMenu(Long idUser);
	
	public VAdmFoncUtilisateur findVadmutilisateurFoncByID(Long idFonc);


}