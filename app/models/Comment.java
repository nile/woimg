package models;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.OneToOne;

import play.modules.ebean.Model;
@Entity
public class Comment extends Model{
	@OneToOne
	public User user;
	public String comment;
	public Date createDate;
	@OneToOne
	public Paster paster;
}
