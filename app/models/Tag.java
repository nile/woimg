package models;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Entity;

import play.modules.ebean.Model;

@Entity
public class Tag extends Model {
	public String name;
	public Tag(String name) {
		this.name = name;
	}
	public static Tag getOrCreate(String name) {
		Tag tag = ebean().find(Tag.class).where("name = ?").setParameter(1, name).findUnique();
		if(tag==null) {
			tag = new Tag(name);
			ebean().save(tag);
		}
		return tag;
	}
	public static Set<Tag> split(String tagtext) {
		String[] tags = tagtext.split(",， ");
		Set<Tag> tagset = new HashSet<Tag>();
		for (int i = 0; i < tags.length; i++) {
			tagset.add(getOrCreate(tags[i]));
		}
		return tagset;
	}
	public static List<Tag> tags(String p){
		return ebean().find(Tag.class).where("name like ?").setParameter(1, p+"%").findList();
	}
}
