//package tn.com.st2i.prj.admin.dao;
//
//import java.util.List;
//
//import tn.com.st2i.prj.admin.model.AdmEntity;
//
//import com.easyfaces.dao.model.TableGen;
//import com.easyfaces.dao.tools.IManagerDao;
//
//public interface IAdmEntityDao extends IManagerDao<AdmEntity, Long> {
//	
//	public List<String> getListEntities();
//	public List<TableGen> getListVEntity() throws Exception;
//	public List<TableGen> getListVRow(String entityName) throws Exception;
//	public List<String> getListAttribute(String entityName) throws Exception;
//	public void GenerateUi(String filename ,String className,String packaGename) throws Exception ;
//	public void GenerateXhtml(String filename) throws Exception;
//	public void GenerateXml(String fileName ,String [] rows) throws Exception;
//	public List<String> findPackaGenames() ;
//	public void GenerateDao(String fileName ,String []rows  ,String className) throws Exception;
//}
