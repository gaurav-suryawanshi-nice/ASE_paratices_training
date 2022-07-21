package com.nice.urless.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateURLCollectionRequest {
    private List<String> urlCollection;
}
