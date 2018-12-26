package tn.com.st2i.prj.admin.dao.impl;

import java.util.List;

import org.hibernate.SQLQuery;
import org.springframework.stereotype.Repository;

import com.easyfaces.dao.model.MenuItemGen;
import com.easyfaces.dao.tools.ManagerDao;

import tn.com.st2i.prj.admin.dao.IMenuItemDao;

@Repository("menuItemDao")
public class MenuItemDao extends ManagerDao<MenuItemGen, Long> implements
		IMenuItemDao {

	private static final String QUERY = "SELECT ID_FONC,ID_APP,ID_PARENT,"
			+ "COD,LABEL,ACTION,ICON,F_EDITER,F_VALIDER,F_AFF_MENU,DES_FONC,"
			+ "DES_FONC_AR,URL_ACCES from V_ADM_FONC_UTILISATEUR";

	@SuppressWarnings("unchecked")
	private List<MenuItemGen> getListMenuItem(String whereClause) {

		String query = QUERY;

		if (whereClause != null && !"".equals(whereClause)) {
			query += " " + whereClause;
		}
		SQLQuery sqlQuery = this.createSqlQuery(query);
		return (List<MenuItemGen>) findManySql(sqlQuery, MenuItemGen.class);
	}

	public List<MenuItemGen> getListMenuItem(Long idUser) {
		return getListMenuItem("WHERE F_AFF_ETAT=0 AND ID_USER=" + idUser);
	}

	public List<MenuItemGen> getListMenuItemEtat(Long idParent) {
		return getListMenuItem("WHERE ID_PARENT=" + idParent
				+ " AND F_AFF_ETAT=1 AND F_AFF_MENU=1");
	}

}
