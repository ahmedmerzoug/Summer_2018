package tn.com.st2i.prj.test.controller.admin;

import java.lang.reflect.Method;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.easyfaces.test.controller.UIManagerTest;

import tn.com.st2i.prj.controller.admin.ProfessionUI;

@ContextConfiguration(locations = {"classpath:/spring/applicationContext-controller-test.xml"})
public class ProfessionUITest extends UIManagerTest {

	@Autowired()
	@Qualifier("professionUI")
	private ProfessionUI professionUI;

	@BeforeMethod(alwaysRun = true)
	public void runScript(Method method) {
		executeScript("admin/profession.sql");
	}

	@AfterTest(alwaysRun = true)
	public void cleanBase() {
		executeScript("admin/professionClean.sql");
	}

	@Test(groups = "crud-test")
	public void addProfession() throws Exception {

		executeAddMethod(professionUI);
		Assert.assertNotNull(professionUI.getProfes());

		String returnValue = null;

		professionUI.getProfes().setCodProfes("testNGAdd");
		professionUI.getProfes().setDesProfes("testNGAdd");
		professionUI.getProfes().setDesProfesAr("testNGAdd");
		returnValue = professionUI.saveProfes();
		Assert.assertEquals(returnValue, null);
	}

	@Test(groups = "crud-test")
	public void addDuplicateProfession() throws Exception {

		executeAddMethod(professionUI);
		Assert.assertNotNull(professionUI.getProfes());

		String returnValue = null;

		professionUI.getProfes().setCodProfes("testNG");
		professionUI.getProfes().setDesProfes("testNGAdd");
		professionUI.getProfes().setDesProfesAr("testNGAdd");
		returnValue = professionUI.saveProfes();
		Assert.assertEquals(returnValue, "1");
	}

	@Test(groups = "crud-test")
	public void editProfession() throws Exception {
		setValueToSelectedId(professionUI, "-1");
		executeEditMethod(professionUI);
		Assert.assertNotNull(professionUI.getProfes());
	}

	@Test(groups = "crud-test")
	public void deleteProfession() throws Exception {
		setValueToSelectedId(professionUI, "-1");
		executeDeleteMethod(professionUI);
	}
}
