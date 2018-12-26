package tn.com.st2i.prj.test.dao.app.search;

import java.util.ArrayList;
import java.util.List;

import org.springframework.test.context.ContextConfiguration;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.easyfaces.search.test.ManagerSearch;


@ContextConfiguration(locations = { "classpath:/spring/applicationContext-search-test.xml" })
public class UiViewSearchColumnTest extends ManagerSearch {

	private List<String> listAllSourceNameXmlFile;

	@Test(groups = "database-test-search")
	public void testXml() throws Exception {

		listAllSourceNameXmlFile = new ArrayList<String>();

		listAllSourceNameXmlFile = xmlUiViewManager.getAllXmlFileFromPath();

		String msg = executeTest(listAllSourceNameXmlFile);

		Assert.assertEquals(msg.trim(), "");
	}

}
