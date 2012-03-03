package controllers;

import com.avaje.ebean.Query;
import models.Category;
import models.Paster;
import play.mvc.Controller;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: nile
 * Date: 12-3-3
 * Time: 下午2:50
 */
public class CCategory extends Controller{
    public static void page(String code){
        Category category = Category.getByCode(code);
        List<Paster> pasters = category.lastest();
        render("Application/page.html",pasters);
    }
    public static void view(String code){
        final Category category = Category.getByCode(code);
        render(category);
    }
    public static void categories(){
        Query<Category> query = Category.all();
        List<Category> categories = query.findList();
        render(categories);
    }

}
