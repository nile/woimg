package controllers;

import models.Category;
import models.User;
import play.mvc.Before;
import play.mvc.Controller;

public class LoginFilter extends Controller {
	private static String KEY_CURRENT_USER = "current_user";
	@Before
	public static void before(){
		if(session.contains(CUser.KEY_SESSION_USER_LOGON)) {
			renderArgs.put(KEY_CURRENT_USER,getLoginUser());
		}
		renderArgs.put("categories", Category.findAll());
	}
	public static User getLoginUser() {
		return User.getByLogin(session.get(CUser.KEY_SESSION_USER_LOGON));
	}
}
