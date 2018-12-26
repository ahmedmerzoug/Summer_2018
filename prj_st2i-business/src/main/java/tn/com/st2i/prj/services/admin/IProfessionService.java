package tn.com.st2i.prj.services.admin;

import java.util.List;

import com.easyfaces.dao.model.TableGen;

import tn.com.st2i.prj.admin.model.AdmProfession;

public interface IProfessionService {

	public List<AdmProfession> getListProfessions();

	public List<TableGen> getListProfessions(String codProfes, String desProfes)
			throws Exception;

	public AdmProfession findProfesById(String idProfes);

	public void deleteProfes(AdmProfession entity);

	public void deleteProfes(String idProfes);

	public AdmProfession saveOrUpdateProfes(AdmProfession entity);
	
	public Boolean isUniqueProfession(String codProfes,Long idProfes) ;

}
