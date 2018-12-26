package tn.com.st2i.prj.controller.admin;

import java.util.Date;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Controller;

import com.easyfaces.controller.tools.UiAbstractGen;
import com.easyfaces.dao.model.TableGen;

import tn.com.st2i.prj.admin.model.VAdmLogData;
import tn.com.st2i.prj.services.admin.ILogService;

@SuppressWarnings("serial")
@Controller("logDataUI")
@Scope(value = "session", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class LogDataUI extends UiAbstractGen {

	private final String NAVIGATION_NAME = "/pages/admin/log/listeLogData.xhtml";

	@Autowired()
	@Qualifier("logService")
	private ILogService logService;
	
	@Getter
	@Setter
	private VAdmLogData logData;

	@Getter
	@Setter
	private Date dateDebut;
	
	@Getter
	@Setter
	private Date dateFin;
	
	@Getter
	@Setter
	private String adrSearch;
	
	@Getter
	@Setter
	private String nomSearch;
	
	@Getter
	@Setter
	private String loginSearch;
	
	@Getter
	@Setter
	private String typeOp;
	
	@Getter
	@Setter
	private Boolean typOpSaveUpdate;
	
	@Getter
	@Setter
	private Boolean typOpDelete;
	
	@Getter
	@Setter
	private String tableName;

	public LogDataUI() throws Exception {
		super("uiViewAdmin:listLogData");

	}

	@Override
	protected void postConstructMethod() throws Exception {
		//createPurgeMenu();
		setNavigationName(NAVIGATION_NAME);
		this.iconButtonEdit="ui-icon-view-detail";
		this.bundleButtonEdit="logAccess.datatable.menu";
	}

	@Override
	public void init() throws Exception {
		addData(logService.getListLogData(null, null, null, null,
				null, true, true, null));
	}

	@Override
	protected String addRow() throws Exception {
		return null;
	}

	@Override
	protected String editRow() throws Exception {
		logData = logService.findLogDataByID(selectedId);
		return null;
	}

	@Override
	protected void deleteRow() throws Exception {
	}

	@Override
	public void initSearch() throws Exception {
		loginSearch = null;
		nomSearch = null;
		adrSearch = null;
		dateDebut = null;
		dateFin = null;
		typeOp = null;
		tableName = null;
		typOpSaveUpdate=false;
		typOpDelete=false;
	}

	public String search() throws Exception {
		List<TableGen> list = logService.getListLogData(loginSearch, nomSearch,
				dateDebut, dateFin, adrSearch, typOpSaveUpdate, typOpDelete,
				tableName);
		addData(list);
		return null;
	}

	public String annuler() {
		return navigationName;
	}

	@Override
	public void updateListComboRech() throws Exception {
		// TODO Auto-generated method stub
		
	}

	/*public void createPurgeMenu() {
		MenuModel menuPurge = new DefaultMenuModel();

		MenuItem itemPurge = new MenuItem();
		itemPurge.setId("itemPurge");
		itemPurge.setValueExpression(
				"value",
				getValueExpression(UiTools.getValueExpressionResourceBundle(
				"msg",
						"ui.dataTable.purgeMenu"), String.class));
		itemPurge.setIcon("ui-icon-purge");
		
		itemPurge.setAjax(false);
		itemPurge.setActionExpression(getMethodExpression("#{" + this.beanName
				+ ".affich}"));
		itemPurge.setProcess("@form");
		
		menuPurge.addMenuItem(itemPurge);
		
		this.modelMenuAdd = menuPurge;
	}*/

}