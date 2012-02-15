package models;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.OneToOne;

import com.avaje.ebean.Query;

import play.modules.ebean.EbeanSupport;
import play.modules.ebean.Model;
@Entity
public class Board extends Model{
	public String name;
	public String hash;
	@OneToOne
	public User user;
	@OneToOne(optional=true)
	public Category category;
	public Date createDate = new Date();
	
	public static Board getByHash(String hash) {
		return Board.findUnique("hash = ?", hash);
	}
    public List<Paster> latest(){
		Query<Paster> query = Paster.find("board.id = ?", id);
		query.setMaxRows(9);
		return query.findList();
    }
	public List<Paster> pasters() {
		Query<Paster> query = Paster.find("board.id = ?" , id);
		query.order("pasteDate desc");
		return query.findList();
	}
	public void update(Board obj) {
		this.category = Category.getByCode(obj.category.code);
		this.name = obj.name;
		this.save();
	}

}
