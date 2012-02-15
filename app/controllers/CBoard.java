package controllers;

import java.util.List;

import models.Board;
import models.Paster;
import models.User;
import play.mvc.Controller;
import play.mvc.With;
@With(LoginFilter.class)
public class CBoard extends Controller{
	public static void create(String name, String category) {
		User user = LoginFilter.getLoginUser();
		user.createBoard(name, category);
		Application.home(user.login);
	}
	public static void edit(String hash) {
		Board board = Board.getByHash(hash);
		render(board);
	}
	public static void update(Board obj) {
		Board board = Board.getByHash(obj.hash);
		board.update(obj);
		view(obj.hash);
	}
	public static void view(String hash) {
		Board board = Board.getByHash(hash);
		render(board);
	}
	public static void page(String hash) {
		Board board = Board.getByHash(hash);
		List<Paster> pasters = board.pasters();
		render("Application/page.html",pasters);
	}
}
