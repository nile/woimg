package models;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.OneToOne;

import com.avaje.ebean.Query;

import play.modules.ebean.EbeanSupport;
import play.modules.ebean.Model;
@Entity
public class Paster extends Model{
	@OneToOne
	public User user;
	@OneToOne
	public Img img;
	public String info;
	@OneToOne
	public Board board;
	public Date pasteDate = new Date();
	public String hash;
	@OneToOne(optional=true)
	public Paster parent;
	public long repaste;
	public static Paster getByHash(String hash) {
		return Paster.findUnique(" hash = ? ", hash);
	}
	public static List<Paster> page() {
		Query<Paster> query = Paster.find("1=1");
		query.setMaxRows(30);
		return query.findList();
	}
	public static List<Paster> findByBoard(String hash) {
		Query<Paster> query = Paster.find("board.hash = ?" , hash);
		query.setMaxRows(9);
		return query.findList();
	}
}
