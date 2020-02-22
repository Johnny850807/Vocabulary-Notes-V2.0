package tw.waterball.vocabnotes.utils;

import java.util.regex.Pattern;

public class RegexUtils {
    public final static String URL_PATTERN = "@^(https?|ftp)://[^\\s/$.?#].[^\\s]*$@iS";
    private static Pattern urlPattern;

    public static boolean isValidUrl(String url) {
        if (urlPattern == null) {
            urlPattern = Pattern.compile(URL_PATTERN);
        }
        return urlPattern.matcher(url).matches();
    }
}
