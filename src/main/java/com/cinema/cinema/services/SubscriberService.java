package com.cinema.cinema.services;

import com.cinema.cinema.exceptions.SubscriberException;
import com.cinema.cinema.models.Subscriber;
import com.cinema.cinema.repositories.SubscriberRepository;

import java.util.List;
import java.util.Optional;

public class SubscriberService {

    private SubscriberRepository subscriberRepository;

    public Subscriber getSubscriber(String email){
        Optional<Subscriber> subscriber = subscriberRepository.findSubscriberByEmail(email);
        if (subscriber.isEmpty()){
            throw new SubscriberException("Subscriber with given email not found");
        }
        return subscriber.get();
    }

    public List<String> getAllEmails(){
        List<Subscriber> subscribers = subscriberRepository.findAllSubscribers();
        return subscribers.stream().map(Subscriber::getEmail).toList();
    }



}
