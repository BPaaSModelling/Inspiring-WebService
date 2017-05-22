package ch.fhnw.inspire.persistence;

import java.util.UUID;

public class GlobalVariables {

	public static String getRandomID() {
		UUID uuid = UUID.randomUUID();
		return uuid.toString().replaceAll("-", "");
	}

	public static String getRandomIDWithPrefix(String prefix) {
		return prefix +"_" + getRandomID();
	}

}
