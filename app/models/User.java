package models;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;

import org.apache.commons.lang.StringUtils;

import com.avaje.ebean.Query;

import controllers.LoginFilter;

import play.modules.ebean.Model;
import utils.HashUtil;
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
	public List<Paster> latestPasters(){
		Query<Paster> query = Paster.find("user.id = ?", id);
		query.setMaxRows(9);
		return query.findList();
	}
	public long countBoard() {
		return Board.count("user.id = ?", id);
	}
	public long countPaster() {
		return Paster.count("user.id = ?", id);
	}
	public Board createBoard(String name, String category) {
		Board board = new Board();
		board.user = this;
		board.category = Category.getByCode(category);
		board.name = name;
		board.hash = HashUtil.hash();
		board.save();
		return board;
	}
	public Paster repaste(Paster old, String board, String desc) {
		Board b = Board.getByHash(board);
		Paster p = new Paster();
		p.board = b;
		p.img = old.img;
		p.info = desc;
		p.hash = HashUtil.hash();
		p.user = this;
		p.pasteDate = new Date();
		p.parent = old;
		p.save();
		old.repaste = old.repaste+1;
		old.save();
		return p;
	}
	public Paster paste(Img img, String board, String desc, String link) {
		Board b = Board.getByHash(board);
		Paster p = new Paster();
		p.board = b;
		p.img = img;
		p.info = desc;
		p.hash = HashUtil.hash();
		p.user = this;
		p.link = StringUtils.isNotEmpty(link)?link:img.url;
		p.pasteDate = new Date();
		p.save();
		return p;
	}
	public Paster deletePaster(String hash) {
		Paster paster = Paster.getByHash(hash);
		paster.delete();
		return paster;
	}
}
