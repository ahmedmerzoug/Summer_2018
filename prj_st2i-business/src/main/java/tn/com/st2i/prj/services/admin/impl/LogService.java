package tn.com.st2i.prj.services.admin.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.easyfaces.common.utils.Util;
import com.easyfaces.dao.model.TableGen;

import tn.com.st2i.prj.admin.dao.IAdmFoncDao;
import tn.com.st2i.prj.admin.dao.IVAdmLogAccesDao;
import tn.com.st2i.prj.admin.dao.IVAdmLogDataDao;
import tn.com.st2i.prj.admin.model.AdmFonc;
import tn.com.st2i.prj.admin.model.VAdmLogAcces;
import tn.com.st2i.prj.admin.model.VAdmLogData;
import tn.com.st2i.prj.services.admin.ILogService;

@Service("logService")
public class LogService implements ILogService {
	
	@Autowired()
	@Qualifier("vAdmLogAccesDao")
	private IVAdmLogAccesDao vAdmLogAccesDao;
	
	@Autowired()
	@Qualifier("vAdmLogDataDao")
	private IVAdmLogDataDao vAdmLogDataDao;
	
	@Autowired()
	@Qualifier("admFoncDao")
	private IAdmFoncDao admFoncDao;
	
	@Override
	public List<TableGen> getListLogAccess(String login, String nom, Date dateDebut,
			Date dateFin, String adr, Long idFonc,Long idParent,Boolean echec) throws Exception {
		return vAdmLogAccesDao
				.getListLogAccess(login, nom, dateDebut, dateFin, adr, idFonc,idParent,echec);
	}
	
	@Override
	public List<AdmFonc> getListFonc(){
		return admFoncDao.getListFoncMenu();
	}
	
	@Override
	public VAdmLogAcces findLogAccessByID(String idLog){
		if(idLog!=null){
			return vAdmLogAccesDao.findByID(VAdmLogAcces.class, Util.toLong(idLog));
		}
		return null;
	}
	
	@Override
	public VAdmLogData findLogDataByID(String idLog){
		if(idLog!=null){
			return vAdmLogDataDao.findByID(VAdmLogData.class, Util.toLong(idLog));
		}
		return null;
	}
	
	@Override
	public List<TableGen> getListLogData(String login, String nom, Date dateDebut,
			Date dateFin, String adr, Boolean typeOpSaveUpdate, Boolean typOpDelete, String table) throws Exception {
		String typeOp = null;
		if(typeOpSaveUpdate!=typOpDelete){
			typeOp = "SAVE_OR_EDIT";
			if(typOpDelete){ typeOp = "DELETE";	}
		}
		return vAdmLogDataDao
				.getListLogData(login, nom, dateDebut, dateFin, adr, typeOp, table);
	}
	
	@Override
	public List<TableGen> getListLogDataBySessionId(String sessionId)
			throws Exception {
		return vAdmLogDataDao.getListLogDataBySessionId(sessionId);
	}
	

}
