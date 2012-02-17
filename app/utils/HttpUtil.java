package utils;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import org.apache.commons.io.FileUtils;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.Connection.Response;

public class HttpUtil {
	public static File scrapeUrl(String url) {
		try {
			Connection connect = Jsoup.connect(url);
			Response response = connect.execute();
			byte[] bodyAsBytes = response.bodyAsBytes();
			File file = new File("public/tmp/", UUID
					.randomUUID().toString());
			FileUtils.writeByteArrayToFile(file, bodyAsBytes);
			return file;
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}
