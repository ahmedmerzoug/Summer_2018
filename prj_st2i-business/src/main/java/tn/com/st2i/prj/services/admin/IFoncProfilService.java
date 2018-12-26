package tn.com.st2i.prj.services.admin;

import java.util.List;

import tn.com.st2i.prj.admin.model.AdmFoncProfil;
import tn.com.st2i.prj.admin.model.VAdmFoncProfil;

public interface IFoncProfilService {

	public List<VAdmFoncProfil> getListFonctionsByProfilId(Long idProfil);

	public AdmFoncProfil findFoncProfilByIds(Long idFonc, Long idProfil);

	public void deleteFoncProfil(Long idFonc, Long idProfil);

	public AdmFoncProfil saveOrUpdateFoncProfil(AdmFoncProfil entity);
	
	public void deleteListFoncProfil(String idProfil);
	
}