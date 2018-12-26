package tn.com.st2i.prj.lang;

import javax.faces.context.FacesContext;

import com.easyfaces.common.lang.I18nRessourceBundle;

public class I18nRessourceBundleHome extends I18nRessourceBundle {

	public I18nRessourceBundleHome(){
		super("resourceBundle/messageResourceHome",FacesContext.getCurrentInstance().getViewRoot().getLocale());
	}
}
