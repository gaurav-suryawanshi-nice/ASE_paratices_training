package shortener;


import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ShortenURL {
    private final String id;
    private final String url;

}
