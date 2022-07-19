package com.nice.urless.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CreateURLResponse {
    private String url;
    private String originalUrl;

    public CreateURLResponse(String url, String originalUrl) {
        this.url = url;
        this.originalUrl = originalUrl;
    }
}
