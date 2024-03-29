package com.cinema.cinema.themes.subscriber;

import com.cinema.cinema.themes.subscriber.model.Subscriber;
import com.cinema.cinema.themes.subscriber.model.SubscriberInputDto;
import com.cinema.cinema.utils.DtoMapperService;
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

@AllArgsConstructor
@RestController
@Tag(name = "Subscribers")
@RequestMapping("/${app.prefix}/${app.version}/subscribers")
public class SubscriberController {

    private final SubscriberService subscriberService;
    private final DtoMapperService mapperService;

//    @GetMapping //metoda dla testów
//    @Operation(summary = "Get all subscribers emails")
//    @ApiResponses(value = {
//            @ApiResponse(responseCode = "200", description = "List with subscribers' emails returned"),
//            @ApiResponse(responseCode = "204", description = "No subscriber found")})
//    public ResponseEntity<List<String>> getAllSubscribersEmails() {
//        List<String> emails = subscriberService.getAllSubscribersEmails();
//        HttpStatus status = emails.isEmpty() ? HttpStatus.NO_CONTENT : HttpStatus.OK;
//        return new ResponseEntity<>(emails, status);
//    }

    @PostMapping
    @Operation(summary = "Create subscriber")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Subscriber created"),
            @ApiResponse(responseCode = "400", description = "Wrong data")})
    public ResponseEntity<Void> addSubscriber(@RequestBody SubscriberInputDto subscriberDto) {
        Subscriber subscriber = mapperService.mapToSubscriber(subscriberDto);
        subscriber = subscriberService.addSubscriber(subscriber);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{subscriberId}")
                .buildAndExpand(subscriber.getId())
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @DeleteMapping()
    @Operation(summary = "Delete subscriber")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Subscriber deleted"),
            @ApiResponse(responseCode = "404", description = "Subscriber not found")})
    public ResponseEntity<Void> removeSubscriber(@RequestBody SubscriberInputDto subscriberDto) {
        Subscriber subscriber = mapperService.mapToSubscriber(subscriberDto);
        subscriberService.removeSubscriber(subscriber);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
