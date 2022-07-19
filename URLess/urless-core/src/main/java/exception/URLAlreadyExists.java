package exception;

public class URLAlreadyExists extends RuntimeException {
    private final String url;

    public String getUrl() {
        return url;
    }

    public URLAlreadyExists(String url) {
        super("URL " + url + "Already Exists");
        this.url = url;
    }
}
