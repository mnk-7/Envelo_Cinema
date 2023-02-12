package com.cinema.cinema.themes.subscriber;

import com.cinema.cinema.themes.subscriber.model.Subscriber;
import com.cinema.cinema.themes.subscriber.model.SubscriberOutputDto;
import com.cinema.cinema.themes.subscriber.model.SubscriberInputDto;
import com.cinema.cinema.utils.DtoMapperService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@AllArgsConstructor
@Service
public class SubscriberService {

    private final SubscriberRepository subscriberRepository;
    private final SubscriberValidator subscriberValidator;
    private final DtoMapperService mapperService;


    @Transactional(readOnly = true)
    //wywoływane, gdy wysyłana jest notyfikacja do subskrybentów, docelowo brak endpointu, dlatego brak konwersji na dto
    public List<String> getAllSubscribersEmails() {
        List<Subscriber> subscribers = subscriberRepository.findAll();
        return subscribers.stream()
                .map(Subscriber::getEmail)
                .toList();
    }

    @Transactional
    public SubscriberOutputDto addSubscriber(SubscriberInputDto subscriberDto) {
        Subscriber subscriber = mapperService.mapToSubscriber(subscriberDto);
        subscriberValidator.validateNotExists(subscriber);
        subscriberValidator.validateInput(subscriber);
        subscriber = subscriberRepository.save(subscriber);
        return mapperService.mapToSubscriberDto(subscriber);
    }

    @Transactional
    public void removeSubscriber(SubscriberInputDto subscriberDto) {
        Subscriber subscriber = subscriberValidator.validateExists(subscriberDto.getEmail());
        subscriberRepository.deleteById(subscriber.getId());
    }

}
