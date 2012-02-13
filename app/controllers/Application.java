package controllers;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

import models.Img;
import models.Tag;

import org.apache.commons.io.FileUtils;

import play.mvc.Before;
import play.mvc.Controller;
import sun.net.www.protocol.http.AuthCache;
import utils.HttpUtil;
import utils.ImgUtil;

import com.google.gson.GsonBuilder;

public class Application extends Controller {
	
	static String UPLOAD_DIR="public/upload";
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
		img.dosave();
		render(img);
	}
	public static void latest() {
		List<Img> populars = Img.latest();
		GsonBuilder gb = new GsonBuilder();
		gb.registerTypeAdapter(Img.class, new ImgSerializer());
		renderJSON(gb.create().toJson(populars));
	}
	public static void popular() {
		List<Img> populars = Img.popular();
		GsonBuilder gb = new GsonBuilder();
		gb.registerTypeAdapter(Img.class, new ImgSerializer());
		renderJSON(gb.create().toJson(populars));
	}
	public static void rate(String hash, int rate) {
		Img img = Img.getByHash(hash);
		img.votetotal += rate;
		img.votecount ++;
		img.votes = (img.votetotal*10)/img.votecount/10.0;
		img.dosave();
		renderText(img.votes);
	}
	public static void tag(String hash, String tag) {
		Img img = Img.getByHash(hash);
		img.tags.add(Tag.getOrCreate(tag));
		img.dosave();
	}
	public static void tags(String p) {
		renderJSON(Tag.tags(p));
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
