package tn.com.st2i.prj.test.controller.admin;

import java.lang.reflect.Method;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.easyfaces.controller.component.combo.ComboItem;
import com.easyfaces.test.controller.UIManagerTest;

import tn.com.st2i.prj.controller.admin.ProfilUI;

@ContextConfiguration(locations = {"classpath:/spring/applicationContext-controller-test.xml"})
public class ProfilUITest extends UIManagerTest {

	@Autowired()
	@Qualifier("profilUI")
	private ProfilUI profilUI;

	@BeforeMethod(alwaysRun = true)
	public void runScript(Method method) {
		executeScript("admin/profil.sql");
	}

	@AfterTest(alwaysRun = true)
	public void cleanBase() {
		executeScript("admin/profilClean.sql");
	}

	@Test(groups = "crud-test")
	public void addProfil() throws Exception {

		executeAddMethod(profilUI);
		Assert.assertNotNull(profilUI.getProfil());

		String returnValue = null;

		profilUI.getProfil().setCodProfil("testNGAdd");
		profilUI.getProfil().setDesProfil("testNGAdd");
		profilUI.getProfil().setDesProfilAr("testNGAdd");
		profilUI.getComboAppEdit().setSelectedValue(new ComboItem("-1", null));
		returnValue = profilUI.saveProfilAndSelectedUsers();
		Assert.assertEquals(returnValue, null);
	}

	@Test(groups = "crud-test")
	public void addDuplicateProfil() throws Exception {

		executeAddMethod(profilUI);
		Assert.assertNotNull(profilUI.getProfil());

		String returnValue = null;

		profilUI.getProfil().setCodProfil("testNG");
		profilUI.getProfil().setDesProfil("testNGAdd");
		profilUI.getProfil().setDesProfilAr("testNGAdd");
		returnValue = profilUI.saveProfilAndSelectedUsers();
		Assert.assertEquals(returnValue, "1");

	}

	@Test(groups = "crud-test")
	public void editProfil() throws Exception {
		setValueToSelectedId(profilUI, "-1");
		executeEditMethod(profilUI);
		Assert.assertNotNull(profilUI.getProfil());
	}

	@Test(groups = "crud-test")
	public void deleteProfil() throws Exception {
		setValueToSelectedId(profilUI, "-1");
		executeDeleteMethod(profilUI);
	}

}
