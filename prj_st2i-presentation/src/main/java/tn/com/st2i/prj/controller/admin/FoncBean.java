package tn.com.st2i.prj.controller.admin;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.el.MethodExpression;
import javax.faces.component.html.HtmlInputHidden;
import javax.faces.context.FacesContext;
import javax.el.MethodExpression;

import org.primefaces.component.commandlink.CommandLink;
import org.primefaces.component.panelgrid.PanelGrid;
import org.primefaces.component.remotecommand.RemoteCommand;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Controller;

import com.easyfaces.common.admin.UserSessionLog;
import com.easyfaces.controller.component.panelmenu.UiPathMenu;
import com.easyfaces.controller.tools.UiManager;

import lombok.Getter;
import lombok.Setter;

import tn.com.st2i.prj.admin.dao.IAdmFoncDao;
import tn.com.st2i.prj.admin.dao.IVAdmFoncUtilisateurDao;
import tn.com.st2i.prj.admin.model.AdmFonc;
import tn.com.st2i.prj.admin.model.AdmFoncProfil;
import tn.com.st2i.prj.admin.model.VAdmFoncProfil;
import tn.com.st2i.prj.admin.model.VAdmFoncUtilisateur;
import tn.com.st2i.prj.admin.model.VAdmProfil;
import tn.com.st2i.prj.services.admin.IFoncProfilService;
import tn.com.st2i.prj.services.admin.IFoncService;

import com.easyfaces.common.admin.UserSessionLog;
import com.easyfaces.controller.component.panelmenu.UiPathMenu;
import com.easyfaces.controller.tools.UiManager;

@SuppressWarnings("serial")
@Controller("foncBean")
@Scope(value = "session", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class FoncBean extends UiManager implements Serializable {

	static final String SEPERATEUR = ":::";

	@Autowired()
	@Qualifier("foncService")
	private IFoncService foncService;

	@Autowired()
	@Qualifier("admFoncDao")
	private IAdmFoncDao admFoncDao;

	@Autowired()
	@Qualifier("vAdmFoncUtilisateurDao")
	private IVAdmFoncUtilisateurDao vAdmFoncUtilisateurDao;

	@Autowired()
	@Qualifier("profilUI")
	private ProfilUI profilUI;

	@Autowired()
	@Qualifier("foncProfilService")
	private IFoncProfilService foncProfilService;

	@Autowired
	@Qualifier("userSession")
	private UserSession userSession;

	@Autowired()
	@Qualifier("userSessionLog")
	private UserSessionLog userSessionLog;

	@Getter
	@Setter
	private TreeNode root;

	@Getter
	@Setter
	private TreeNode selectedNode;

	@Getter
	@Setter
	private VAdmProfil viewProfil;

	@Getter
	@Setter
	private String droitAcces = "pasAcces";

	@Getter
	@Setter
	private Boolean displayPanelAction;

	@Getter
	@Setter
	private List<AdmFonc> listFonctions = new ArrayList<>();
	private List<VAdmFoncProfil> listProfilFonctions = new ArrayList<>();

	@Getter
	@Setter
	private CommandLink commandLink = new CommandLink();

	@Getter
	@Setter
	private CommandLink commandLink2 = new CommandLink();

	@Getter
	@Setter
	private CommandLink commandLink3 = new CommandLink();

	@Getter
	@Setter
	private HtmlInputHidden inputHidden = new HtmlInputHidden();

	@Getter
	@Setter
	private List<VAdmFoncUtilisateur> listFonctionsChild = new ArrayList<>();

	@Getter
	@Setter
	private List<VAdmFoncUtilisateur> listFonctionsParent = new ArrayList<>();

	@Getter
	@Setter
	private UiPathMenu pathMenu = new UiPathMenu();

	@Getter
	@Setter
	private String path;

	@Getter
	@Setter
	List<VAdmFoncUtilisateur> listTest;

	@Getter
	@Setter
	private PanelGrid uiPanel;

	@Getter
	@Setter
	private String scriptFun = "";

	@Getter
	@Setter
	private RemoteCommand remote;

	@Getter
	@Setter
	private RemoteCommand remoteSession;

	public FoncBean() {
	}

	@PostConstruct
	public void init() {
		
		listFonctions = foncService.getListFoncByIdPereNull();
		pathMenu.restoreView();
	}

	// @Cacheable(value = "penale", key = "#root.targetClass + ','
	// +#root.methodName + ',' + #p0 + ',' + #p1")
	public void doUpdateBoutton(String command, VAdmFoncUtilisateur admFonc) {
		updateMenuPath(this.getPathMenu(admFonc));
		commandLink.setActionExpression(getMethodExpression("#{" + command + "}"));
		userSession.setAdmFonc(admFonc);

	}

	// @Cacheable(value = "penale", key = "#root.targetClass + ','
	// +#root.methodName + ',' + #p0 + ',' + #p1")
	public void doUpdateBoutton2(String command, VAdmFoncUtilisateur admFonc) {
		updateMenuPath(this.getPathMenu(admFonc));
		commandLink2.setActionExpression(getMethodExpression("#{" + command + "}"));
	}

	// @Cacheable(value = "penale", key = "#root.targetClass + ','
	// +#root.methodName + ',' + #p0 + ',' + #p1")
	public void doUpdateBoutton3(String command, VAdmFoncUtilisateur admFonc) {
		updateMenuPath(this.getPathMenu(admFonc));
		commandLink3.setActionExpression(getMethodExpression("#{" + command + "}"));
	}

	public void updateMenuPath(String path) {
		pathMenu.setPath(path);
		pathMenu.restoreView();
	}

	public void deleteLastPath() {
		pathMenu.setPath(null);
		pathMenu.restoreView();
	}

	private String getPathMenu(VAdmFoncUtilisateur admFonc) {
		String path = admFonc.getLabel();
		if (admFonc.getIdParent() != null) {
			VAdmFoncUtilisateur parent = vAdmFoncUtilisateurDao.findByID(VAdmFoncUtilisateur.class,
					Long.valueOf(admFonc.getIdParent()));
			path = getPathMenu(parent) + SEPERATEUR + path;
		}
		return path;
	}

	public List<VAdmFoncUtilisateur> menuChild(Long id) {
		listFonctionsChild = foncService.getListFoncByIdPere1(id, userSessionLog.getIdUser());
		listTest = new ArrayList<VAdmFoncUtilisateur>();
		listTest = listFonctionsChild;
		return listFonctionsChild;
	}

	public boolean menuChildbool(Long id) {
		listFonctionsChild = foncService.getListFoncByIdPere1(id, userSessionLog.getIdUser());
		if (listFonctionsChild.isEmpty()) {
			return false;
		} else {
			return true;
		}
	}

	public List<VAdmFoncUtilisateur> menuParent() {
		listFonctionsParent = foncService.getListFoncByIdPereNull1(userSessionLog.getIdUser());
		return listFonctionsParent;
	}

	public void createTreeFonc() {
		listFonctions = foncService.getListFoncByIdApp(profilUI.getProfil().getIdApp());
		listProfilFonctions = foncProfilService.getListFonctionsByProfilId(viewProfil.getIdProfil());
		AdmFonc fonc = new AdmFonc();

		fonc.setIdFonc(0L);
		root = new DefaultTreeNode(fonc, null);
		for (AdmFonc f : listFonctions) {
			if (f.getIdParent() == null) {
				new DefaultTreeNode(f, root);
			}
		}
		for (TreeNode node : root.getChildren()) {
			mergeWithParent(node, listFonctions);
		}
		refreshSelectedNode();
	}

	private void mergeWithParent(TreeNode node, List<AdmFonc> listFonctions) {
		for (AdmFonc fonc : listFonctions) {
			if (fonc.getIdParent() != null) {
				if (fonc.getIdParent().equals(((AdmFonc) node.getData()).getIdFonc())) {
					// node.setExpanded(true); // This will make the tree always
					// expanded
					TreeNode x = new DefaultTreeNode(fonc, node);
					mergeWithParent(x, listFonctions);
				}
			}
		}
	}

	public String fixNodeIcon(AdmFonc fonc) {
		if (isValid(fonc)) {
			return "ui-icon-valid";
		}
		return "ui-icon-supp";
	}

	private boolean hasChildren(AdmFonc parentFonc) {
		return getChildren(parentFonc).size() != 0;
	}

	private boolean hasValidChild(AdmFonc fonc) {
		boolean valid = false;
		if (hasChildren(fonc)) {
			for (AdmFonc childFonc : getChildren(fonc)) {
				valid = hasValidChild(childFonc);
				if (valid) {
					return true;
				}
			}
		} else {
			valid = isValid(fonc);
		}
		return valid;
	}

	private boolean isValid(AdmFonc fonc) {
		for (VAdmFoncProfil vfp : listProfilFonctions) {
			if (vfp.getIdFonc().equals(fonc.getIdFonc())) {
				return true;
			}
		}
		return false;
	}

	private List<AdmFonc> getChildren(AdmFonc parentFonc) {
		List<AdmFonc> listChildren = new ArrayList<>();
		for (AdmFonc childFonc : listFonctions) {
			if (childFonc.getIdParent() != null) {
				if (childFonc.getIdParent().equals(parentFonc.getIdFonc())) {
					listChildren.add(childFonc);
				}
			}
		}
		return listChildren;
	}

	public void prepareEditNode(AdmFonc fonc) {
		displayPanelAction = true;
		refreshSelectedNode();
		if (hasChildren(fonc)) {
			droitAcces = null;
		} else {
			AdmFoncProfil fp = foncProfilService.findFoncProfilByIds(fonc.getIdFonc(), viewProfil.getIdProfil());
			if (fp == null) {
				droitAcces = "pasAcces";
			} else {
				if (fp.getFEditer().equals(0L) && fp.getFValider().equals(0L)) {
					droitAcces = "accesConsult";
				} else {
					if (fp.getFEditer().equals(1L) && fp.getFValider().equals(0L)) {
						droitAcces = "accesTotal";
					} else {
						droitAcces = "accesTotalValid";
					}
				}
			}
		}
	}

	public void editNode() {
		if (selectedNode != null) {
			lookForChildren((AdmFonc) selectedNode.getData());
			lookForParents((AdmFonc) selectedNode.getData());
			createTreeFonc();
		}
	}

	private void lookForChildren(AdmFonc parentFonc) {
		if (hasChildren(parentFonc)) {
			for (AdmFonc childFonc : getChildren(parentFonc)) {
				lookForChildren(childFonc);
			}
		}
		updateFoncProfil(parentFonc);
	}

	private void lookForParents(AdmFonc childFonc) {
		AdmFonc parentFonc;
		Long idParent = childFonc.getIdParent();
		while (idParent != null) {
			parentFonc = foncService.findFoncByID(idParent);
			if (droitAcces.equals("pasAcces") && hasValidChild(parentFonc)) {
				return;
			}
			updateFoncProfil(parentFonc);
			idParent = parentFonc.getIdParent();
		}
	}

	public void updateFoncProfil(AdmFonc fonc) {
		AdmFoncProfil foncProfil = foncProfilService.findFoncProfilByIds(fonc.getIdFonc(), viewProfil.getIdProfil());
		if (foncProfil == null) {
			foncProfil = new AdmFoncProfil();
			foncProfil.setIdFonc(fonc.getIdFonc());
			foncProfil.setIdProfil(viewProfil.getIdProfil());
			foncProfil.setFEditer(0L);
			foncProfil.setFValider(0L);
		}
		switch (droitAcces) {
		case "pasAcces":
			foncProfilService.deleteFoncProfil(fonc.getIdFonc(), viewProfil.getIdProfil());
			break;
		case "accesConsult":
			foncProfil.setFEditer(0L);
			foncProfil.setFValider(0L);
			foncProfilService.saveOrUpdateFoncProfil(foncProfil);
			break;
		case "accesTotal":
			foncProfil.setFEditer(1L);
			foncProfil.setFValider(0L);
			foncProfilService.saveOrUpdateFoncProfil(foncProfil);
			break;
		case "accesTotalValid":
			foncProfil.setFEditer(1L);
			foncProfil.setFValider(1L);
			foncProfilService.saveOrUpdateFoncProfil(foncProfil);
			break;
		default:
			;
		}
		listProfilFonctions = foncProfilService.getListFonctionsByProfilId(viewProfil.getIdProfil());
	}

	public void refreshSelectedNode() {
		unselectAllNodes(root);
		lookForSelectedNode(root);
	}

	private void unselectAllNodes(TreeNode parentNode) {
		if (parentNode.getChildCount() > 0) {
			for (TreeNode childNode : parentNode.getChildren()) {
				childNode.setSelectable(true);
				childNode.setSelected(false);
				childNode.setExpanded(false);
				unselectAllNodes(childNode);
			}
		}
	}

	private void lookForSelectedNode(TreeNode parentNode) {
		if (selectedNode != null) {
			if (parentNode.getChildCount() > 0) {
				for (TreeNode childNode : parentNode.getChildren()) {
					if (((AdmFonc) childNode.getData()).getIdFonc()
							.equals(((AdmFonc) selectedNode.getData()).getIdFonc())) {
						childNode.setSelectable(true);
						childNode.setSelected(true);
						if (childNode.getParent() != null) {
							TreeNode parent = childNode.getParent();
							parent.setExpanded(true);
							while (parent.getParent() != null) {
								parent = parent.getParent();
								parent.setExpanded(true);
							}
						}
						if (childNode.getChildCount() > 0) {
							childNode.setExpanded(true);
						}
						return;
					}
					lookForSelectedNode(childNode);
				}
			}
		}
	}

	// public String evalAsString(String expression) {
	// FacesContext context = FacesContext.getCurrentInstance();
	// ExpressionFactory expressionFactory = context.getApplication()
	// .getExpressionFactory();
	// ELContext elContext = context.getELContext();
	// ValueExpression vex = expressionFactory.createValueExpression(
	// elContext, expression, String.class);
	// String result = (String) vex.getValue(elContext);
	//
	// ApplicationContext beanFactory = WebApplicationContextUtils
	// .getRequiredWebApplicationContext(getServletContext());
	// Object retour = executerMethode(FoncBean.getClass(), "initBean", null);
	// return result;
	// }

	public String drawMenu() {
		uiPanel = new PanelGrid();
		uiPanel.setId("panelremote");

		String output = "";

		List<VAdmFoncUtilisateur> listeAdmFonc = foncService
				.getListFoncForMenu(userSession.getVAdmUtilisateur().getIdUser());
		VAdmFoncUtilisateur suplemtn = new VAdmFoncUtilisateur();
		listeAdmFonc.add(suplemtn);
		int niv = 0;
		List<String> t = new ArrayList<String>();
		t.add("null");

		for (int i = 0; i < listeAdmFonc.size(); i++) {

			String menuName = null;
			menuName = getValFromResourceBundle("com", listeAdmFonc.get(i).getLabel());

			int tailleT = t.size() - 1;
			String stringRepresentation = new String();

			stringRepresentation = String.valueOf(listeAdmFonc.get(i).getIdParent());

			if (stringRepresentation.equals(t.get(niv)) == false) {
				outerloop: for (int j = tailleT; j >= 0; j--) {

					if (t.get(j).equals(stringRepresentation) == false) {
						output = output + "</ul> </li>";
						niv = niv - 1;
						t.remove(j);

					} else {
						break outerloop;
					}
				}
			}
			if (i == listeAdmFonc.size() - 1) {
				break;
			}

			String stringidfonc = String.valueOf(listeAdmFonc.get(i).getIdFonc());

			String stringRepresentation2 = new String();
			stringRepresentation2 = String.valueOf(listeAdmFonc.get(i + 1).getIdParent());

			if (stringRepresentation.equals(t.get(niv))
					&& String.valueOf(listeAdmFonc.get(i).getIdFonc()).equals(stringRepresentation2)) {

				if (niv == 0) {
					output = output
							+ "<li class='active treeview' style='margin-top: 10px;'><a class='tree-toggle ui-commandlink ui-widget ui-commandlink-st2i ui-widget-st2i' style='cursor:n-resize;display: block !important;'><i class='"
							+ listeAdmFonc.get(i).getIcon()
							+ "'style='color: #b8c7ce; margin-right: 8px; margin-top: 8px;'></i> <span style='margin-left: 5px;'>"
							+ menuName
							+ "</span><i class='fa fa-angle-left pull-right' style='right: none !important'></i> </a><ul class='treeview-menu'>";
				} else {
					output = output + "<li ><a class='ui-widget-st2i' style='cursor:n-resize;'><i class='"
							+ listeAdmFonc.get(i).getIcon()
							+ "'style='color: #b8c7ce; margin-right: 8px; margin-top: 8px;'></i> <span style='margin-left: 5px;'>"
							+ menuName + "</span></a><ul class='treeview-menu'>";
				}
				niv = niv + 1;
				t.add(listeAdmFonc.get(i).getIdFonc().toString());
			} else if (stringRepresentation.equals(t.get(niv))
					&& String.valueOf(listeAdmFonc.get(i).getIdFonc()).equals(stringRepresentation2) == false) {
				if (listeAdmFonc.get(i).getAction() != null) {
					remote = new RemoteCommand();
					remoteSession = new RemoteCommand();

					remote.setName("MyCommand" + listeAdmFonc.get(i).getIdFonc());
					String s = "#{" + listeAdmFonc.get(i).getAction() + "}";
					String s2 = "#{foncBean.SetAdmFonc(" + listeAdmFonc.get(i).getIdFonc() + ")}";
					remoteSession.setName("MySetFonc" + listeAdmFonc.get(i).getIdFonc());

					scriptFun = scriptFun + "function function" + listeAdmFonc.get(i).getIdFonc() + "(){MySetFonc"
							+ listeAdmFonc.get(i).getIdFonc() + "();MyCommand" + listeAdmFonc.get(i).getIdFonc()
							+ "();}";
					remote.setActionExpression(createMethodExpression(String.format(s, listeAdmFonc.get(i).getIdFonc()),
							null, String.class));
					remoteSession.setActionExpression(createMethodExpression(
							String.format(s2, listeAdmFonc.get(i).getIdFonc()), null, String.class));

					uiPanel.getChildren().add(remoteSession);
					uiPanel.getChildren().add(remote);
				}
				output = output + "<li style='height: 25px !important'><a href='#' onClick=function"
						+ listeAdmFonc.get(i).getIdFonc() + "();><i class='" + listeAdmFonc.get(i).getIcon()
						+ "'style='color: #b8c7ce; margin-right: 8px; margin-top: 8px;'></i> <span style='margin-left: 5px;'>"
						+ menuName + "</span></a></li>";
			}
		}
		return output;
	}

	public static MethodExpression createMethodExpression(String expression, Class<?> returnType,
			Class<?>... parameterTypes) {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		return facesContext.getApplication().getExpressionFactory().createMethodExpression(facesContext.getELContext(),
				expression, returnType, parameterTypes);
	}

	public PanelGrid GetPan() {
		return uiPanel;
	}

	public String getScript() {
		return scriptFun;
	}

	public void SetAdmFonc(Long fonc) {
		VAdmFoncUtilisateur admFoncUtilisateur = new VAdmFoncUtilisateur();
		admFoncUtilisateur = foncService.findVadmutilisateurFoncByID(fonc);
		String path = this.getPathMenu(admFoncUtilisateur);
		this.deleteLastPath();
		this.updateMenuPath(path);
		userSession.setAdmFonc(admFoncUtilisateur);
		if (admFoncUtilisateur.getFEditer() != null && admFoncUtilisateur.getFEditer() == 1) {
			userSession.setIsEdit(Boolean.TRUE);
		} else {
			userSession.setIsEdit(Boolean.FALSE);
		}
	}

}
