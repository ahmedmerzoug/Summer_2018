package tn.com.st2i.prj.controller.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.easyfaces.common.admin.UserSessionLog;
import com.easyfaces.common.utils.Util;
import com.easyfaces.controller.security.CustomUserDetails;
import com.easyfaces.dao.model.MenuItemGen;

import tn.com.st2i.prj.admin.model.AdmUtilisateur;
import tn.com.st2i.prj.controller.admin.FormSession;
import tn.com.st2i.prj.controller.admin.LoginUi;
import tn.com.st2i.prj.services.admin.ILoginService;
import tn.com.st2i.prj.services.admin.IUserService;

@Service("customUserDetailsService")
public class CustomUserDetailsService implements UserDetailsService {

	private static final String DEFAULT_TARGET_URL = "/pages/admin/index.jsf";
	private static final String DEFAULT_ROLE = "ROLE_ST2I_INDEX";

	@Autowired()
	@Qualifier("loginService")
	private ILoginService loginService;

	@Autowired()
	@Qualifier("formSession")
	private FormSession formSession;

	@Autowired
	@Qualifier("userSessionLog")
	private UserSessionLog userSessionLog;

	@Autowired()
	@Qualifier("userService")
	private IUserService userService;

	@Autowired()
	@Qualifier("loginUi")
	private LoginUi loginUi;

	@Override
	public UserDetails loadUserByUsername(String arg0)
			throws UsernameNotFoundException {

		if (arg0 == null) {
			throw new UsernameNotFoundException("Null value");
		}

		AdmUtilisateur user = loginService.findUserByLogin(arg0);

		if (user == null) {
			throw new UsernameNotFoundException("User Not Exist");
		}

		Boolean isActif = true;

		if (userSessionLog.getIdUser() == null) {
			Integer userEtat = Integer.valueOf(userService.verifyUserEtat(
					user.getIdUser()).toString());
			if (userEtat.equals(1)) {
				loginUi.connectUserUpdate(user);
				formSession.initRememberMe(true, user);
			} else {
				isActif = false;
				if (userEtat.equals(4)) {
					loginUi.setUserLogin(user);
					loginUi.setIsExipred(true);
					loginUi.setIsDisplayPopup(true);
				}
			}
		}

		Collection<? extends GrantedAuthority> authorities = AuthorityUtils
				.createAuthorityList(DEFAULT_ROLE);

		CustomUserDetails userDetials = new CustomUserDetails(user.getLogin(),
				user.getPwd(), authorities, DEFAULT_TARGET_URL,
				getListUrlAcces(user.getIdUser()), isActif);

		return userDetials;

	}

	private List<String> getListUrlAcces(Long idUser) {
		List<String> listUrlAcces = new ArrayList<>();
		for (MenuItemGen m : formSession.createMenuItem(idUser)) {
			if (Util.isValid(m.getUrlAcces())) {
				String[] tab = m.getUrlAcces().split(" ");
				for (String s : tab) {
					if (Util.isValid(s)) {
						listUrlAcces.add(s);
					}
				}
			}
		}
		return listUrlAcces;
	}

}
