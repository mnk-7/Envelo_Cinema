package com.cinema.cinema.themes.content;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@Tag(name = "Movies")
@RequestMapping("/${app.prefix}/${app.version}/movies")
public class MovieController {

    private final MovieService service;

}
