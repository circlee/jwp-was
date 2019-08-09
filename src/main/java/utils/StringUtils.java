package utils;

public class StringUtils {

	private static final String EMPTY = "";  
	private static final String DOT = ".";  

	public static boolean isEmpty(String str) {
		return str == null || str.isEmpty();
	}

	public static String nvl(String str, String defaultValue) {

		if(isEmpty(str)) {
			return defaultValue;
		}

		return str;
	}

	public static String nvl(String str) {
		return nvl(str, "");
	}

	public static String makeRelativePath(String path) {

		if(path.startsWith(DOT)) {
			return path;
		}

		return DOT + path;
	}

	public static String getEmpty() {
		return EMPTY;
	}
}
