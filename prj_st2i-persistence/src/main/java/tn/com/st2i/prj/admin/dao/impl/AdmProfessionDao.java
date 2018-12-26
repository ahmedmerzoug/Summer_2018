package tn.com.st2i.prj.admin.dao.impl;

/**
 * prjerated by easyfaces Hibernate Tools 4.3.1
 */


import java.util.List;

import tn.com.st2i.prj.admin.dao.IAdmProfessionDao;
import tn.com.st2i.prj.admin.model.AdmProfession;

import com.easyfaces.dao.model.TableGen;
import com.easyfaces.dao.request.EnumTypOp;
import com.easyfaces.dao.request.Request;
import com.easyfaces.dao.tools.ManagerDao;
import com.easyfaces.dao.tools.UniqueRequest;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

@Repository("admProfessionDao")
public class AdmProfessionDao extends ManagerDao<AdmProfession, Long> implements
		IAdmProfessionDao {

	@Override
	public List<AdmProfession> getListProfessions(){
		Query query=null;
		query=createQuery("from AdmProfession order by codProfes");
		return findMany(query);
	}
	
	@Override
	public List<TableGen> getListProfessions(String codProfes, String desProfes, Long idOtherProfes)
			throws Exception {
		
		Request requete = new Request(AdmProfession.class, AdmProfession._idProfes);

		requete.addField(AdmProfession._codProfes);
		requete.addField(AdmProfession._desProfes);
		requete.addField(AdmProfession._desProfesAr);
		
		requete.addClauseString(AdmProfession._codProfes,
				EnumTypOp.StringParameter.CENTER_WORD_LIKE, codProfes);
		
		requete.addClauseString(AdmProfession._desProfes,
				EnumTypOp.StringParameter.CENTER_WORD_LIKE, desProfes);
		
		requete.addClauseLong(AdmProfession._idProfes,
				EnumTypOp.AllParameter.DIFFERENT, idOtherProfes);
		
		requete.addOrdre(AdmProfession._codProfes);

		return getList(requete);
	}
	
	public Boolean isCodeProfessionUnique(String codProfes, Long idProfes) {
		UniqueRequest request=new UniqueRequest(AdmProfession.class);
		request.addId(idProfes).addUnique(AdmProfession._codProfes,codProfes);
		return isUnique(request);
	}
	
}
