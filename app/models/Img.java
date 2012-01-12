package models;

import java.io.File;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

import com.avaje.ebean.EbeanServer;

import play.modules.ebean.EbeanSupport;
import play.modules.ebean.Model;
import utils.HashUtil;
import utils.ImgUtil;
import utils.ImgUtil.Info;

@Entity
public class Img extends Model{
	public String caption;
	@Column(name="_desc")
	public String desciption;
	public int width;
	public int height;
	public String type;
	public String ext;
	public String hash;
	@Column(name="deletehash")
	public String deleteHash;
	@Column(name="createdate")
	public Date createDate;
	
	//extends
	public long views;
	public double votes;
	public long votetotal;
	public long votecount;
	
	public String info;
	@OneToMany(cascade=CascadeType.ALL)
	public Set<Tag> tags = new HashSet<Tag>();
	
	public static Img createFromFile(File file, String caption, String desc, String infomation) {
		Info info = ImgUtil.detectImageType(file);
		if (info != null) {
			Img img = new Img();
			img.caption = caption;
			img.desciption = desc;
			img.width = info.w;
			img.height = info.h;
			img.ext = info.ext;
			img.type = info.type;
			img.hash = HashUtil.hash();
			img.deleteHash = HashUtil.deleteHash(img.hash);
			img.createDate = new Date();
			img.info = infomation;
			img.dosave();
			return img;
		}
		return null;
	}

	public String viewLink() {
		return "/view/" + hash;
	}

	public String smallLink() {
		return "/public/upload/" + smallFile();
	}

	public String largeLink() {
		return "/public/upload/" + largeFile();
	}

	public String orignalLink() {
		return "/public/upload/" + orignalFile();
	}
	
	public String orignalFile() {
		return hash + ext;
	}
	public String smallFile() {
		return hash + "s" + ext;
	}
	public String largeFile() {
		return hash + "l" + ext;
	}
	
	public static List<Img> popular(){
		return ebean().createQuery(Img.class).orderBy("views desc").setMaxRows(9).findList();
	}
	public static List<Img> latest(){
		return ebean().createQuery(Img.class).orderBy("createDate desc").setMaxRows(9).findList();
	}
	public static Img getByHash(String hash) {
		return ebean().createQuery(Img.class).where("hash = ?").setParameter(1, hash).findUnique();
	}
	public void dosave() {
		ebean().save(this);
	}
}
