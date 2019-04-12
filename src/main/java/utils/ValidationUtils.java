package utils;

public class ValidationUtils {
    public static boolean length(String str, Integer start, Integer end) {
        return (str.length() >= start && str.length() <= end);
    }

    public static boolean name(String str) {
        return str.matches("^[-a-zA-Z0-9_]+$");
    }
}
