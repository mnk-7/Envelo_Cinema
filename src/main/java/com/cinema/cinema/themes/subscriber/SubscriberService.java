package com.cinema.cinema.themes.subscriber;

import com.cinema.cinema.themes.subscriber.model.Subscriber;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@AllArgsConstructor
@Service
public class SubscriberService {

    private final SubscriberRepository subscriberRepository;
    private final SubscriberValidator subscriberValidator;

    @Transactional(readOnly = true)
    public List<String> getAllSubscribersEmails() {
        List<Subscriber> subscribers = subscriberRepository.findAll();
        return subscribers.stream()
                .map(Subscriber::getEmail)
                .toList();
    }

    @Transactional
    public Subscriber addSubscriber(Subscriber subscriber) {
        subscriberValidator.validateNotExists(subscriber);
        subscriberValidator.validateInput(subscriber);
        return subscriberRepository.save(subscriber);
    }

    @Transactional
    public void removeSubscriber(Subscriber subscriber) {
        subscriber = subscriberValidator.validateExists(subscriber.getEmail());
        subscriberRepository.deleteById(subscriber.getId());
    }

}
