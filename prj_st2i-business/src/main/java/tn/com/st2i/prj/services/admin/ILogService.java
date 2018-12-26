package tn.com.st2i.prj.services.admin;

import java.util.Date;
import java.util.List;

import com.easyfaces.dao.model.TableGen;

import tn.com.st2i.prj.admin.model.AdmFonc;
import tn.com.st2i.prj.admin.model.VAdmLogAcces;
import tn.com.st2i.prj.admin.model.VAdmLogData;

public interface ILogService {

	public List<TableGen> getListLogAccess(String login, String nom, Date dateDebut,
			Date dateFin, String adr, Long idFonc,Long idParent,Boolean echec) 
			throws Exception;

	public List<AdmFonc> getListFonc();

	public VAdmLogAcces findLogAccessByID(String idLog);

	public VAdmLogData findLogDataByID(String idLog);

	public List<TableGen> getListLogData(String login, String nom, Date dateDebut,
			Date dateFin, String adr, Boolean typeOpSaveUpdate,
			Boolean typOpDelete, String table) throws Exception;

	public List<TableGen> getListLogDataBySessionId(String sessionId)
			throws Exception;

}