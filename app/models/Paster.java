package models;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.OneToOne;

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
	public Date pasteDate;
}
