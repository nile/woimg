package controllers;

import notifiers.Mails;

import org.apache.commons.lang.StringUtils;

import models.User;
import play.libs.Crypto;
import play.mvc.Controller;

public class CUser extends Controller{
	public static final String KEY_SESSION_USER_LOGON = "key_login_user";
	public static void joinin() {
		render();
	}
	public static void login() {
		render();
	}
	public static void logout() {
		session.clear();
		Application.index();
	}
	public static void auth(User obj) {
		User user = User.getByLogin(obj.login);
		if(StringUtils.equals(user.password,Crypto.passwordHash(obj.password))) {
			session.put(KEY_SESSION_USER_LOGON,user.login);
			Application.index();
		}
		login();
	}
	public static void register(User obj) {
		obj.password = Crypto.passwordHash(obj.password);
		obj.save();
		Mails.welcome(obj);
		login();
	}
}
