package tn.com.st2i.prj.utils;

import java.util.Date;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Controller;

import com.easyfaces.dao.tools.DateManager;

import tn.com.st2i.prj.services.admin.ITiParamService;

@Controller("utils")
@Scope(value = "session", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class Utils {

	@Autowired
	@Qualifier("tiParamService")
	private ITiParamService tiParamService;

	private String otherProfessionId;

	@PostConstruct
	public void init() {
		otherProfessionId = tiParamService
				.findValueByCode("OTHER_PROFESSION_ID");
	}

	public static String formatDate(Date date) {
		return DateManager.formatDate(date);
	}
	
	public Date getMaxDate(){
		return DateManager.getSystemDate();
	}

	public String getOtherProfessionId() {
		return otherProfessionId;
	}

	public void setOtherProfessionId(String otherProfessionId) {
		this.otherProfessionId = otherProfessionId;
	}
}
