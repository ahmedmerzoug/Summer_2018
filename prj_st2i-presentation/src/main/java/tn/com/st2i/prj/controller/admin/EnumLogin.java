package tn.com.st2i.prj.controller.admin;

public enum EnumLogin {
	CONNECT, ERROR_REQUERED_LOGIN,ERROR_LOGIN_PWD, ERROR_DATABASE, ERROR_NO_ACTIF_USER,
	ERROR_SUSPENDED_USER, ERROR_OTHERS;

	public static String getErrorMsgBundle(EnumLogin error) {
		switch (error) {
		case ERROR_REQUERED_LOGIN:
			return "app.admin.connexion.errorRequered";
		case ERROR_LOGIN_PWD:
			return "app.admin.connexion.errorLoginPwd";
		case ERROR_DATABASE:
			return "app.admin.connexion.errorDatabase";
		case ERROR_NO_ACTIF_USER:
			return "app.admin.connexion.errorNoActifUser";
		case ERROR_SUSPENDED_USER:
			return "app.admin.connexion.errorSuspUser";
		case ERROR_OTHERS:
			return "app.admin.connexion.errorOthers";
		default:
			return "app.admin.connexion.errorNoDefined";
		}
	}
}
