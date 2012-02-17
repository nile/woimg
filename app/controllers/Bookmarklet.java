package controllers;

import play.mvc.Controller;
import play.mvc.With;
@With(LoginFilter.class)
public class Bookmarklet extends Controller {
	public static void create(String media,String url, String alt, String title) {
		render(media, title, url);
	}
}
