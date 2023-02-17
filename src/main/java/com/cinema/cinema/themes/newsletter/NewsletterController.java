package com.cinema.cinema.themes.newsletter;

import com.cinema.cinema.themes.newsletter.model.NewsletterInputDto;
import com.cinema.cinema.themes.newsletter.model.NewsletterOutputDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@AllArgsConstructor
@RestController
@Tag(name = "Newsletters")
@RequestMapping("/${app.prefix}/${app.version}/newsletters")
public class NewsletterController {

    private final NewsletterService newsletterService;

    @GetMapping
    @Operation(summary = "Get all newsletters")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List with newsletters returned"),
            @ApiResponse(responseCode = "204", description = "No newsletter found")})
    public ResponseEntity<List<NewsletterOutputDto>> getAllNewsletters() {
        List<NewsletterOutputDto> newsletters = newsletterService.getAllNewsletters();
        HttpStatus status = newsletters.isEmpty() ? HttpStatus.NO_CONTENT : HttpStatus.OK;
        return new ResponseEntity<>(newsletters, status);
    }

    @GetMapping("/{newsletterId}")
    @Operation(summary = "Get newsletter by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Newsletter returned"),
            @ApiResponse(responseCode = "404", description = "Newsletter not found")})
    public ResponseEntity<NewsletterOutputDto> getNewsletter(@PathVariable long newsletterId) {
        NewsletterOutputDto newsletter = newsletterService.getNewsletter(newsletterId);
        return new ResponseEntity<>(newsletter, HttpStatus.OK);
    }

    @PostMapping
    @Operation(summary = "Create newsletter")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Newsletter created"),
            @ApiResponse(responseCode = "400", description = "Wrong data")})
    public ResponseEntity<Void> addNewsletter(@RequestBody NewsletterInputDto newsletter) {
        NewsletterOutputDto newsletterCreated = newsletterService.addNewsletter(newsletter);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{newsletterId}")
                .buildAndExpand(newsletterCreated.getId())
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @PutMapping("/{newsletterId}")
    @Operation(summary = "Update newsletter")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Newsletter updated"),
            @ApiResponse(responseCode = "400", description = "Wrong data"),
            @ApiResponse(responseCode = "404", description = "Newsletter not found")})
    public ResponseEntity<Void> editNewsletter(@PathVariable long newsletterId, @RequestBody NewsletterInputDto newsletter) {
        newsletterService.editNewsletter(newsletterId, newsletter);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/{newsletterId}")
    @Operation(summary = "Send newsletter")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Newsletter sent"),
            @ApiResponse(responseCode = "400", description = "Wrong data")})
    public ResponseEntity<Void> sendNewsletter(@PathVariable long newsletterId) {
        newsletterService.sendNewsletter(newsletterId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
