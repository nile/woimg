package controllers;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.swing.border.Border;

import models.Board;
import models.Category;
import models.Img;
import models.User;

import org.apache.commons.io.FileUtils;

import play.mvc.Controller;
import play.mvc.With;
import utils.HashUtil;
import utils.HttpUtil;
import utils.ImgUtil;

import com.google.gson.GsonBuilder;
@With(LoginFilter.class)
public class Application extends Controller {
	
	public static String UPLOAD_DIR="public/upload";
	public static void index() {
		render();
	}
	public static void page() {
		List<Img> imgs = Img.latest();
		render(imgs);
	}
	public static void view(String hash) {
		Img img = Img.getByHash(hash);
		img.views = img.views+1;
		img.save();
		render(img);
	}
	public static void home(String login) {
		List<Board> boards = Board.findByUser(LoginFilter.getLoginUser().id);
		render(boards);
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
	public static void upload(String type, String url, File file, String caption,
			String desc) {
		if ("file".equals(type)) {
			Img img = Img.createFromFile(file, caption, desc, file.getName());
			if (img != null) {
				try {
					File orignalFile = new File(UPLOAD_DIR,img.orignalFile());
					FileUtils.moveFile(file, orignalFile);
					BufferedImage orignalImg = ImgUtil.read(orignalFile);
					ImgUtil.write(ImgUtil.t100(orignalImg), img.type, UPLOAD_DIR + File.separatorChar + img.thumbLink());
					ImgUtil.write(ImgUtil.t170(orignalImg),img.type,UPLOAD_DIR + File.separatorChar + img.smallFile());
					ImgUtil.write(ImgUtil.t600(orignalImg),img.type,UPLOAD_DIR + File.separatorChar+ img.largeFile());
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			GsonBuilder gb = new GsonBuilder();
			renderText(gb.create().toJson(img));
		}else if ("url".equals(type)) {
			File tmpfile = HttpUtil.scrapeUrl(url);
			Img img = Img.createFromFile(tmpfile, caption, desc, url);
			if (img != null) {
				try {
					File orignalFile = new File(UPLOAD_DIR, img.orignalFile());
					FileUtils.moveFile(tmpfile, orignalFile);
					BufferedImage orignalImg = ImgUtil.read(orignalFile);
					ImgUtil.write(ImgUtil.t100(orignalImg), img.type, UPLOAD_DIR + File.separatorChar + img.thumbLink());
					ImgUtil.write(ImgUtil.t170(orignalImg), img.type, UPLOAD_DIR + File.separatorChar + img.smallFile());
					ImgUtil.write(ImgUtil.t600(orignalImg), img.type, UPLOAD_DIR + File.separatorChar + img.largeFile());
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			GsonBuilder gb = new GsonBuilder();
			renderText(gb.create().toJson(img));
		}
	}
}
