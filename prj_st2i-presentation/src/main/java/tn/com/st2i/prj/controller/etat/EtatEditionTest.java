package tn.com.st2i.prj.controller.etat;

import java.util.HashMap;
import java.util.Map;

import com.easyfaces.controller.etat.EtatEditionAbstractUi;
import com.easyfaces.controller.etat.EtatProvider;

public class EtatEditionTest extends EtatEditionAbstractUi{

	@Override
	protected void postConstructMethod() throws Exception {
		
	}
	
	public String exporterListePermitere() throws Exception{
		
		EtatProvider provider = new EtatProvider();
		
		Map<String, Object> parameters = new HashMap<String, Object>();
		String reportName="classic.jrxml";
		
		provider.setJasperReportName(reportName);
		provider.setFileName("listePi");
		provider.setParameters(parameters);
		provider.setTypeExport(TYPE_EXPORT.PDF);

		generateEtat(provider);

		return null;
	}

}
