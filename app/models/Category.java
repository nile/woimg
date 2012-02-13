package models;

import java.util.List;

import javax.persistence.Entity;

import play.modules.ebean.Model;
@Entity
public class Category extends Model{
	public String name;
	public String code;
	public static Category getByCode(String category) {
		return Category.findUnique("code = ?", category);
	}
}
