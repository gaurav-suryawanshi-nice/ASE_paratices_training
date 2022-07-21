package shortener;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ShortenURL {
    private String id;
    private String url;
    private String originalUrl;

    public ShortenURL(String id, String url) {
        this.id = id;
        this.url = url;
    }

    public String getOriginalUrl() {
        return originalUrl;
    }

    public void setOriginalUrl(String originalUrl) {
        this.originalUrl = originalUrl;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
