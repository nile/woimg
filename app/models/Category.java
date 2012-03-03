package models;

import com.avaje.ebean.Query;
import play.modules.ebean.Model;

import javax.persistence.Entity;
import java.util.List;

@Entity
public class Category extends Model{
	public String name;
	public String code;
	public static Category getByCode(String category) {
		return Category.findUnique("code = ?", category);
	}

    public List<Paster> lastest() {
        final Query<Paster> query = Paster.find("board.category.code = ?", code);
        query.setMaxRows(30);
        final List<Paster> pasters = query.findList();
        return pasters;
    }
}
