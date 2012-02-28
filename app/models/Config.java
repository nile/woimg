package models;

import java.util.List;

import javax.persistence.Entity;

import play.Play;
import play.libs.Mail;
import play.modules.ebean.EbeanSupport;
import play.modules.ebean.Model;

import com.avaje.ebean.Query;
@Entity
public class Config extends Model{
	public String name;
	public String value;
	public static List<Config> listAll(){
		Query<Config> query = Config.all();
		return query.findList();
	}
	public static String get(String name) {
		Config findUnique = getByName(name);
		return findUnique == null? null: findUnique.value;
	}
	private static Config getByName(String name) {
		Query<Config> find = Config.find("name=?", name);
		Config findUnique = find.findUnique();
		return findUnique;
	}
	public static void clear(String name) {
		Config config = getByName(name);
		config.delete();
	}
	public static void set(String name, String value) {
		Config byName = getByName(name);
		if(byName == null) {
			byName = new Config();
			byName.name = name;
		}
		byName.value = value;
		byName.save();
		Mail.session = null;
		updateSystemConfig();
	}
	public static void updateSystemConfig() {
		List<Config> configs = Config.listAll();
		for (Config config : configs) {
			Play.configuration.setProperty(config.name, config.value);
		}
	}
}
