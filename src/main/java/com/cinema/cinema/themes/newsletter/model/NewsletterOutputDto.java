package com.cinema.cinema.themes.newsletter.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class NewsletterOutputDto {

    private Long id;
    private String name;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime sentAt;

}
