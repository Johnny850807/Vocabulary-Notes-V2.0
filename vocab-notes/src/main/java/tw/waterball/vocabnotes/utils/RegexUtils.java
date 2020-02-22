package tw.waterball.vocabnotes.utils;

import org.apache.commons.validator.routines.UrlValidator;

import java.util.regex.Pattern;

public class RegexUtils {
    public final static String HTTP_URL_PATTERN_REGEX = "https?://(www\\.)?[-a-zA-Z0-9@:%._+~#=]{1,256}\\.[a-zA-Z0-9()]{1,6}\\b([-a-zA-Z0-9()@:%_+.~#?&//=]*)";
    private static Pattern HTTP_URL_PATTERN;

    public static boolean isValidUrl(String url) {
        if (HTTP_URL_PATTERN == null) {
            HTTP_URL_PATTERN = Pattern.compile(HTTP_URL_PATTERN_REGEX);
        }
        UrlValidator defaultValidator = new UrlValidator();

        return HTTP_URL_PATTERN.matcher(url).matches();
    }
}
