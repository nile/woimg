package controllers;

import java.util.List;

import models.Config;
import play.libs.Mail;
import play.mvc.Controller;

public class Sys extends Controller{
	public static void config() {
		List<Config> configs = Config.listAll();
		render(configs);
	}
	public static void update(String name, String value) {
		Config.set(name, value);
		config();
	}
	public static void clear(String name) {
		Config.clear(name);
		config();
	}
}
