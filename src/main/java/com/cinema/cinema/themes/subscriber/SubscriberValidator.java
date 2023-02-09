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

    private final SubscriberRepository subscriberRepository;

    public SubscriberValidator(Validator validator, SubscriberRepository subscriberRepository) {
        super(validator);
        this.subscriberRepository = subscriberRepository;
    }

    @Override
    public Subscriber validateExists(long subscriberId) {
        Optional<Subscriber> subscriber = subscriberRepository.findById(subscriberId);
        if (subscriber.isEmpty()) {
            throw new ElementNotFoundException("Subscriber with ID " + subscriberId + " not found");
        }
        return subscriber.get();
    }

    public void validateNotExists(Subscriber subscriberFromDto) {
        Optional<Subscriber> subscriber = subscriberRepository.findByEmail(subscriberFromDto.getEmail());
        if (subscriber.isPresent()) {
            throw new ElementFoundException("Subscriber with email " + subscriber.get().getEmail() + " already exists");
        }
    }

    public Subscriber validateExists(String email) {
        Optional<Subscriber> subscriber = subscriberRepository.findByEmail(email);
        if (subscriber.isEmpty()) {
            throw new ElementNotFoundException("Subscriber with email " + email + " not found");
        }
        return subscriber.get();
    }

}
