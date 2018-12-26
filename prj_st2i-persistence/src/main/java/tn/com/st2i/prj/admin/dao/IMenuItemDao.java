package tn.com.st2i.prj.admin.dao;

import java.util.List;

import com.easyfaces.dao.model.MenuItemGen;

public interface IMenuItemDao{
	public List<MenuItemGen> getListMenuItem(Long idUser);
	public List<MenuItemGen> getListMenuItemEtat(Long idParent);
}
