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
	public static List<Board> findByUser(long uid) {
		Query<Board> find = Board.find("user.id=?", uid);
		return find.findList();
		
	}
}
