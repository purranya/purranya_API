package utils;

public class ValidationUtils {
    public static boolean length(String str, Integer start, Integer end) {
        return (str.length() >= start && str.length() <= end);
    }
}
