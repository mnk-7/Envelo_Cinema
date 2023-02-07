package com.cinema.cinema.themes.subscriber;

import com.cinema.cinema.exceptions.ElementFoundException;
import com.cinema.cinema.exceptions.ElementNotFoundException;
import com.cinema.cinema.themes.subscriber.model.Subscriber;
import com.cinema.cinema.utils.ValidatorService;
import jakarta.validation.Validator;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SubscriberValidator extends ValidatorService<Subscriber> {

    private final SubscriberRepository repository;

    public SubscriberValidator(Validator validator, SubscriberRepository repository) {
        super(validator);
        this.repository = repository;
    }

    @Override
    public Subscriber validateExists(long id) {
        Optional<Subscriber> subscriber = repository.findById(id);
        if (subscriber.isEmpty()) {
            throw new ElementNotFoundException("Subscriber with ID " + id + " not found");
        }
        return subscriber.get();
    }

    @Override
    public void validateNotExists(Subscriber subscriberFromDto) {
        Optional<Subscriber> subscriber = repository.findByEmail(subscriberFromDto.getEmail());
        if (subscriber.isPresent()) {
            throw new ElementFoundException("Subscriber with email " + subscriber.get().getEmail() + " already exists");
        }
    }

    public Subscriber validateExists(String email) {
        Optional<Subscriber> subscriber = repository.findByEmail(email);
        if (subscriber.isEmpty()) {
            throw new ElementNotFoundException("Subscriber with email " + email + " not found");
        }
        return subscriber.get();
    }

}
