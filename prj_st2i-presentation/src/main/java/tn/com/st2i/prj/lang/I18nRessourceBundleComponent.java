package tn.com.st2i.prj.lang;

import javax.faces.context.FacesContext;

import com.easyfaces.common.lang.I18nRessourceBundle;

public class I18nRessourceBundleComponent extends I18nRessourceBundle{
	
	public I18nRessourceBundleComponent(){
		super("resourceBundle/messageResourceComponent",FacesContext.getCurrentInstance().getViewRoot().getLocale());
	}

}
