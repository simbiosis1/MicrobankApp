package org.simbiosis.cli.bi.lib;

public class StrUtils {

	public static String rpadded(String text, int length, char pad) {
		String myText = text == null ? "" : text;
		if (myText.length() < length) {
			String buffer = myText;
			for (int i = 1; i <= length - myText.length(); i++)
				buffer += pad;
			return buffer;
		} else if (myText.length() == length) {
			return text;
		}
		return "";
	}

	public static String lpadded(String text, int length, char pad) {
		String myText = text == null ? "" : text;
		if (myText.length() < length) {
			String buffer = "";
			for (int i = 1; i <= length - myText.length(); i++)
				buffer += pad;
			buffer += myText;
			return buffer;
		} else if (myText.length() == length) {
			return text;
		}
		return "";
	}

}
