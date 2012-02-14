package controllers;

import java.util.Collections;

import models.Board;
import models.Category;
import models.User;
import play.mvc.Before;
import play.mvc.Controller;

public class LoginFilter extends Controller {
	private static String KEY_CURRENT_USER = "current_user";
	@Before
	public static void before(){
		renderArgs.put("categories", Category.findAll());
		renderArgs.put("boards", Collections.EMPTY_LIST);
		if(session.contains(CUser.KEY_SESSION_USER_LOGON)) {
			User loginUser = getLoginUser();
			renderArgs.put(KEY_CURRENT_USER,loginUser);
			renderArgs.put("boards", loginUser.myBoards());
		}
	}
	public static User getLoginUser() {
		return User.getByLogin(session.get(CUser.KEY_SESSION_USER_LOGON));
	}
}
