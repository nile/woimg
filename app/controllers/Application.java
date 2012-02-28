package controllers;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;

import models.Board;
import models.Category;
import models.Comment;
import models.Img;
import models.Paster;
import models.User;
import notifiers.Mails;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;

import play.mvc.Controller;
import play.mvc.With;
import utils.HashUtil;
import utils.HttpUtil;
import utils.ImgUtil;
@With(LoginFilter.class)
public class Application extends Controller {
	
	public static String UPLOAD_DIR="public"+File.separatorChar+"upload";
	public static void index() {
		render();
	}
	public static void page() {
		List<Paster> pasters = Paster.page();
		render(pasters);
	}
	public static void view(String hash) {
		Paster paster = Paster.getByHash(hash);
		List<Paster> pastersInBorad = paster.board.latest();
		render(paster, pastersInBorad);
	}
	public static void edit(String hash) {
		Paster paster = Paster.getByHash(hash);
		render(paster);
	}
	public static void update(Paster obj) {
		Paster paster = Paster.getByHash(obj.hash);
		paster.update(obj);
		view(paster.hash);
	}
	public static void delete(String hash) {
		User user = LoginFilter.getLoginUser();
		Paster paster = user.deletePaster(hash);
		CBoard.view(paster.board.hash);
	}
	public static void home(String login) {
		User user = User.getByLogin(login);
		List<Board> boards = user.myBoards();
		List<Paster> latestPasters = user.latestPasters();
		render(boards, latestPasters);
	}
	public static void comments(String hash) {
		Paster paster = Paster.getByHash(hash);
		List<Comment> comments = paster.comments();
		render(comments);
	}
	public static void comment(String hash, String comment) {
		Paster paster = Paster.getByHash(hash);
		User user = LoginFilter.getLoginUser();
		paster.comment(user, comment);
	}
	public static void prerepaste(String hash) {
		Paster paster = Paster.getByHash(hash);
		render(paster);
	}
	public static void repaste(String paster, String board, String desc) {
		Paster old = Paster.getByHash(paster);
		User user = LoginFilter.getLoginUser();
		Paster p = user.repaste(old, board, desc);
		Mails.repaste(p);
		view(p.hash);
	}
	public static void paste(String type, String url, File file, String desc, String board, String site, String link, String client) {
		Img img = null;
		File orignalFile = null;
		try {
			if ("file".equals(type)) {
				img = Img.createFromFile(file, desc, file.getName());
				if (img != null) {
					orignalFile = new File(UPLOAD_DIR, img.orignalFile());
					FileUtils.moveFile(file, orignalFile);
				}
				img.source = type;
				img.media = file.getName();
				img.save();
			} else if ("url".equals(type)) {
				File tmpfile = HttpUtil.scrapeUrl(url);
				img = Img.createFromFile(tmpfile, desc, url);
				if (img != null) {
					orignalFile = new File(UPLOAD_DIR, img.orignalFile());
					FileUtils.moveFile(tmpfile, orignalFile);
				}
				img.source = type;
				img.media = url;
				img.url = site;
				img.save();
			}
		} catch (IOException e) {
			error(e);
		}
		BufferedImage orignalImg = ImgUtil.read(orignalFile);
		String orignalPath = orignalFile.getPath();
		String thumb = UPLOAD_DIR + File.separatorChar + img.thumbFile();
		ImgUtil.write(ImgUtil.t100(orignalPath,thumb, orignalImg), img.type, thumb);
		String small = UPLOAD_DIR + File.separatorChar + img.smallFile();
		ImgUtil.write(ImgUtil.s170(orignalPath,small, orignalImg), img.type, small);
		String large = UPLOAD_DIR + File.separatorChar + img.largeFile();
		ImgUtil.write(ImgUtil.s600(orignalPath, large,orignalImg), img.type, large);
		User user = LoginFilter.getLoginUser();
		Paster p = user.paste(img, board, desc, link);
		if(StringUtils.equals(client,"bookmarklet")) {
			render("closewin.html");
		}
		view(p.hash);
	}
}
