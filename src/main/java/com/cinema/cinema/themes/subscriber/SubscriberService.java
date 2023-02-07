package com.cinema.cinema.themes.subscriber;

import com.cinema.cinema.themes.subscriber.model.Subscriber;
import com.cinema.cinema.themes.subscriber.model.SubscriberDtoRead;
import com.cinema.cinema.themes.subscriber.model.SubscriberDtoWrite;
import com.cinema.cinema.utils.DtoMapperService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@AllArgsConstructor
@Service
public class SubscriberService {

    private final SubscriberRepository repository;
    private final SubscriberValidator validator;
    private final DtoMapperService mapper;


    @Transactional(readOnly = true)
    //wywoływane, gdy wysyłana jest notyfikacja do subskrybentów, docelowo brak endpointu, dlatego brak konwersji na dto
    public List<String> getAllSubscribersEmails() {
        List<Subscriber> subscribers = repository.findAll();
        return subscribers.stream()
                .map(Subscriber::getEmail)
                .toList();
    }

    @Transactional
    public SubscriberDtoRead addSubscriber(SubscriberDtoWrite subscriberDto) {
        Subscriber subscriber = mapper.mapToSubscriber(subscriberDto);
        validator.validateNotExists(subscriber);
        validator.validateInput(subscriber);
        subscriber = repository.save(subscriber);
        return mapper.mapToSubscriberDto(subscriber);
    }

    @Transactional
    public void removeSubscriber(SubscriberDtoWrite subscriberDto) {
        Subscriber subscriber = validator.validateExists(subscriberDto.getEmail());
        repository.deleteById(subscriber.getId());
    }

}
