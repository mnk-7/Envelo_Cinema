package com.cinema.cinema.themes.subscriber;

import com.cinema.cinema.exceptions.ArgumentNotValidException;
import com.cinema.cinema.exceptions.ElementFoundException;
import com.cinema.cinema.exceptions.ElementNotFoundException;
import com.cinema.cinema.themes.subscriber.model.Subscriber;
import com.cinema.cinema.themes.subscriber.model.SubscriberDtoRead;
import com.cinema.cinema.themes.subscriber.model.SubscriberDtoWrite;
import com.cinema.cinema.utils.DtoMapperService;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@AllArgsConstructor
@Service
public class SubscriberService {

    private final SubscriberRepository subscriberRepository;
    private final DtoMapperService mapperService;
    private final Validator validator;


    @Transactional(readOnly = true)
    //wywoływane, gdy wysyłana jest notyfikacja do subskrybentów, docelowo brak endpointu, dlatego brak konwersji na dto
    public List<String> getAllSubscribersEmails() {
        List<Subscriber> subscribers = subscriberRepository.findAll();
        return subscribers.stream()
                .map(Subscriber::getEmail)
                .toList();
    }

    @Transactional
    public SubscriberDtoRead addSubscriber(SubscriberDtoWrite subscriberDto) {
        validateSubscriberNotExists(subscriberDto);
        Subscriber subscriber = mapperService.mapToSubscriber(subscriberDto);
        validateInputForSubscriber(subscriber);
        subscriber = subscriberRepository.save(subscriber);
        return mapperService.mapToSubscriberDto(subscriber);
    }

    @Transactional
    public void removeSubscriber(SubscriberDtoWrite subscriberDto) {
        Subscriber subscriber = validateSubscriberExists(subscriberDto.getEmail());
        subscriberRepository.deleteById(subscriber.getId());
    }

    private void validateInputForSubscriber(Subscriber subscriber) {
        Set<ConstraintViolation<Subscriber>> violations = validator.validate(subscriber);
        if (!violations.isEmpty()) {
            Map<String, String> messages = new HashMap<>();
            for (ConstraintViolation<Subscriber> violation : violations) {
                messages.put(violation.getPropertyPath().toString(), violation.getMessage());
            }
            throw new ArgumentNotValidException("Not valid data provided", messages);
        }
    }

    private Subscriber validateSubscriberExists(String email) {
        Optional<Subscriber> subscriber = subscriberRepository.findByEmail(email);
        if (subscriber.isEmpty()) {
            throw new ElementNotFoundException("Subscriber with email " + email + " not found");
        }
        return subscriber.get();
    }

    private void validateSubscriberNotExists(SubscriberDtoWrite subscriberDto) {
        Optional<Subscriber> subscriber = subscriberRepository.findByEmail(subscriberDto.getEmail());
        if (subscriber.isPresent()) {
            throw new ElementFoundException("Subscriber with email " + subscriber.get().getEmail() + " already exists");
        }
    }

}
