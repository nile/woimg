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
	
	public String link;
	
	public static Paster getByHash(String hash) {
		return Paster.findUnique(" hash = ? ", hash);
	}
	public static List<Paster> page() {
		Query<Paster> query = Paster.find("");
		query.order("pasteDate desc");
		query.setMaxRows(30);
		return query.findList();
	}
	public List<Comment> comments(){
		Query<Comment> query = Comment.find("paster.id = ?", id);
		return query.findList();
	}
	public void comment(User user, String comment) {
		Comment c = new Comment();
		c.paster = this;
		c.user = user;
		c.comment = comment;
		c.createDate = new Date();
		c.save();
	}
	public void update(Paster obj) {
		this.info = obj.info;
		this.board = Board.getByHash(obj.board.hash);
		this.save();
	}
}
