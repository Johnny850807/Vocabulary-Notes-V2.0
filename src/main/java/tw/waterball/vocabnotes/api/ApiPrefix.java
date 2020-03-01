package tw.waterball.vocabnotes.api;

public interface ApiPrefix {
    String URL_PREFIX = "/api";

    static String withApiPrefix(String url) {
        return URL_PREFIX+url;
    }

}
