package utils;

import org.apache.commons.lang.RandomStringUtils;

public class HashUtil {
	public static String hash() {
		return RandomStringUtils.random(16, "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ");
	}
	public static String deleteHash(String hash) {
		return "_"+hash+"_";
	}
}
