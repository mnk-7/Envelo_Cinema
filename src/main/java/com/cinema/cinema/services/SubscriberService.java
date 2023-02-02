package com.cinema.cinema.services;

import com.cinema.cinema.exceptions.SubscriberException;
import com.cinema.cinema.models.Subscriber;
import com.cinema.cinema.repositories.SubscriberRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class SubscriberService {

    private SubscriberRepository subscriberRepository;

    public Subscriber getSubscriber(String email) {
        Optional<Subscriber> subscriber = subscriberRepository.findByEmail(email);
        if (subscriber.isEmpty()) {
            throw new SubscriberException("Subscriber with given email not found");
        }
        return subscriber.get();
    }

    public List<String> getAllEmails() {
        List<Subscriber> subscribers = subscriberRepository.findAll();
        return subscribers.stream().map(Subscriber::getEmail).toList();
    }

    public void addSubscriber(String email) {
        Subscriber subscriber = new Subscriber();
        subscriber.setEmail(email);
        subscriberRepository.save(subscriber);
    }

    public void removeSubscriber(String email) {
        Subscriber subscriber = getSubscriber(email);
        subscriberRepository.deleteById(subscriber.getId());
    }

}
