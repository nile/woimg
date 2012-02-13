package models;

import javax.persistence.Entity;

import play.modules.ebean.Model;
@Entity
public class User  extends Model{
	public String login;
	public String password;
	public String email;
	public static User getByLogin(String login) {
		User user = User.findUnique("login = ?", login);
		return user;
	}
}
