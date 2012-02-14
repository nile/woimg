package controllers;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;

import models.Board;
import models.Category;
import models.Img;
import models.Paster;
import models.User;

import org.apache.commons.io.FileUtils;

import play.mvc.Controller;
import play.mvc.With;
import utils.HashUtil;
import utils.HttpUtil;
import utils.ImgUtil;
@With(LoginFilter.class)
public class Application extends Controller {
	
	public static String UPLOAD_DIR="public/upload";
	public static void index() {
		render();
	}
	public static void page() {
		List<Paster> pasters = Paster.page();
		render(pasters);
	}
	public static void view(String hash) {
		Paster paster = Paster.getByHash(hash);
		List<Paster> pastersInBorad = Paster.findByBoard(paster.board.hash);
		render(paster, pastersInBorad);
	}
	public static void delete(String hash) {
		Paster paster = Paster.getByHash(hash);
		paster.delete();
		viewBoard(paster.board.hash);
	}
	public static void home(String login) {
		List<Board> boards = Board.findByUser(LoginFilter.getLoginUser().id);
		render(boards);
	}
	public static void viewBoard(String hash) {
		Board board = Board.getByHash(hash);
		render(board);
	}
	public static void boardPage(String board) {
		List<Paster> pasters = Paster.findByBoard(board);
		render("Application/page.html",pasters);
	}
	public static void createBoard(String name, String category) {
		User user = LoginFilter.getLoginUser();
		Board board = new Board();
		board.user = user;
		board.category = Category.getByCode(category);
		board.name = name;
		board.hash = HashUtil.hash();
		board.save();
		home(user.login);
	}
	public static void repaste(String paster, String board, String desc) {
		Paster old = Paster.getByHash(paster);
		Board b = Board.getByHash(board);
		Paster p = new Paster();
		p.board = b;
		p.img = old.img;
		p.info = desc;
		p.hash = HashUtil.hash();
		p.user = LoginFilter.getLoginUser();
		p.pasteDate = new Date();
		p.parent = old;
		p.save();
		old.repaste = old.repaste+1;
		old.save();
		view(p.hash);
	}
	public static void paste(String type, String url, File file, String desc, String board) {
		Img img = null;
		if ("file".equals(type)) {
			img = Img.createFromFile(file, desc, file.getName());
			if (img != null) {
				try {
					File orignalFile = new File(UPLOAD_DIR,img.orignalFile());
					FileUtils.moveFile(file, orignalFile);
					BufferedImage orignalImg = ImgUtil.read(orignalFile);
					ImgUtil.write(ImgUtil.t100(orignalImg),img.type, UPLOAD_DIR + File.separatorChar + img.thumbFile());
					ImgUtil.write(ImgUtil.s170(orignalImg),img.type,UPLOAD_DIR + File.separatorChar + img.smallFile());
					ImgUtil.write(ImgUtil.s600(orignalImg),img.type,UPLOAD_DIR + File.separatorChar+ img.largeFile());
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}else if ("url".equals(type)) {
			File tmpfile = HttpUtil.scrapeUrl(url);
			img = Img.createFromFile(tmpfile, desc, url);
			if (img != null) {
				try {
					File orignalFile = new File(UPLOAD_DIR, img.orignalFile());
					FileUtils.moveFile(tmpfile, orignalFile);
					BufferedImage orignalImg = ImgUtil.read(orignalFile);
					ImgUtil.write(ImgUtil.t100(orignalImg), img.type, UPLOAD_DIR + File.separatorChar + img.thumbFile());
					ImgUtil.write(ImgUtil.s170(orignalImg), img.type, UPLOAD_DIR + File.separatorChar + img.smallFile());
					ImgUtil.write(ImgUtil.s600(orignalImg), img.type, UPLOAD_DIR + File.separatorChar + img.largeFile());
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		Board b = Board.getByHash(board);
		Paster p = new Paster();
		p.board = b;
		p.img = img;
		p.info = desc;
		p.hash = HashUtil.hash();
		p.user = LoginFilter.getLoginUser();
		p.pasteDate = new Date();
		p.save();
		view(p.hash);
	}
}
