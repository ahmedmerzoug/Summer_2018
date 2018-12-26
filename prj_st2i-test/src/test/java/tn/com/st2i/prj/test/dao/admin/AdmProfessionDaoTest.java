package tn.com.st2i.prj.test.dao.admin;

import java.lang.reflect.Method;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.easyfaces.common.utils.Util;
import com.easyfaces.dao.model.TableGen;
import com.easyfaces.test.dao.DaoManagerTest;

import tn.com.st2i.prj.admin.dao.IAdmProfessionDao;
import tn.com.st2i.prj.admin.dao.IAdmParamDao;
import tn.com.st2i.prj.admin.model.AdmProfession;

@ContextConfiguration(locations = {"classpath:/spring/applicationContext-dao-test.xml"})
public class AdmProfessionDaoTest extends DaoManagerTest<AdmProfession> {

	@Autowired()
	@Qualifier("admProfessionDao")
	private IAdmProfessionDao admProfessionDao;

	@Autowired()
	@Qualifier("admParamDao")
	private IAdmParamDao admParamDao;
	
	@Test(groups = "database-test-table")
	public void testGetAll() {
		testGetAllGen(admProfessionDao, AdmProfession.class);
	}

	@Test(groups = "database-test-unique")
	public void testUniqueId() {
		testUniqueIdGen(AdmProfession.class);
	}
	
	@BeforeMethod(alwaysRun = true)
	public void runScript(Method method) {
		if ("isCodeUniqueTest".equals(method.getName())) {
			executeScript("admin/profession.sql");
		}
	}
	
	@AfterTest(alwaysRun = true)
	public void cleanBase() {
		executeScript("admin/professionClean.sql");
	}

	@Test(groups = "database-test-request")
	public void testOtherProfession() throws Exception {
		List<TableGen> l = admProfessionDao.getListProfessions(null, null,
				Util.toLong(admParamDao.findValueByCode("OTHER_PROFESSION_ID")));
		Integer req = l.size();
		Integer all = admProfessionDao.getCountAll(AdmProfession.class);
		Assert.assertEquals(req, new Integer(all - 1));
	}

	@Test(groups = "database-test-query")
	public void isCodeUniqueTest() {

		boolean isUnique = admProfessionDao.isCodeProfessionUnique("testNG", null);
		Assert.assertEquals(isUnique, false);

		isUnique = admProfessionDao.isCodeProfessionUnique("testNG1", null);
		Assert.assertEquals(isUnique, true);

		AdmProfession p = admProfessionDao.findByID(AdmProfession.class,-1L);

		isUnique = admProfessionDao.isCodeProfessionUnique("testNG",
				p.getIdProfes());
		Assert.assertEquals(isUnique, true);
	}

}
