package com.github.aguilasa.fiis.model;

import lombok.*;
import lombok.NonNull;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@AllArgsConstructor
@NoArgsConstructor
@RequiredArgsConstructor
@Data
@Builder
@Document(collection = "tutorials")
public class Tutorial {

    @Id
    private String id;
    @NonNull private String title;
    @NonNull private String description;
    @NonNull private boolean published;
}
