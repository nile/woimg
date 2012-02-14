package models;

import java.util.List;

import javax.persistence.Entity;

import com.avaje.ebean.Query;

import play.modules.ebean.Model;
@Entity
public class User  extends Model{
	public String login;
	public String password;
	public String email;
	public String avatar;
	public static User getByLogin(String login) {
		User user = User.findUnique("login = ?", login);
		return user;
	}
	public List<Board> myBoards() {
		Query<Board> find = Board.find("user.id=?", id );
		return find.findList();
	}
	public  List<Paster> latestPasters(){
		Query<Paster> query = Paster.find("user.id = ?", id);
		query.setMaxRows(9);
		return query.findList();
	}
}
