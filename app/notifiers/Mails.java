package notifiers;

import models.Config;
import models.Paster;
import models.User;

import org.apache.commons.mail.EmailAttachment;

import play.Play;
import play.mvc.Mailer;

public class Mails extends Mailer {
	public static void welcome(User user) {
		setSubject("欢迎加入 %s", user.name);
		addRecipient(user.email);
		setFrom("云贴 <"+Play.configuration.getProperty("mail.smtp.user")+">");
		send(user);
	}
	public static void repaste(Paster paster) {
		setSubject("欢迎加入 %s", paster.parent.user.name);
		addRecipient(paster.parent.user.email);
		setFrom("云贴 <" + Play.configuration.getProperty("mail.smtp.user") + ">");
		send(paster);
	}
}
