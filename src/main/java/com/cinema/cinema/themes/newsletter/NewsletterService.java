package com.cinema.cinema.themes.newsletter;

import com.cinema.cinema.themes.newsletter.model.Newsletter;
import com.cinema.cinema.themes.newsletter.model.NewsletterInputDto;
import com.cinema.cinema.themes.newsletter.model.NewsletterOutputDto;
import com.cinema.cinema.themes.subscriber.SubscriberService;
import com.cinema.cinema.utils.DtoMapperService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@Service
public class NewsletterService {

    private final NewsletterRepository newsletterRepository;
    private final NewsletterValidator newsletterValidator;
    private final SubscriberService subscriberService;
    private final DtoMapperService mapperService;

    @Transactional(readOnly = true)
    public List<NewsletterOutputDto> getAllNewsletters() {
        List<Newsletter> newsletters = newsletterRepository.findAll();
        return newsletters.stream()
                .map(mapperService::mapToNewsletterDto)
                .toList();
    }

    @Transactional(readOnly = true)
    public NewsletterOutputDto getNewsletter(long newsletterId) {
        Newsletter newsletter = newsletterValidator.validateExists(newsletterId);
        return mapperService.mapToNewsletterDto(newsletter);
    }

    @Transactional
    public NewsletterOutputDto addNewsletter(NewsletterInputDto newsletterDto) {
        Newsletter newsletterFromDto = mapperService.mapToNewsletter(newsletterDto);
        newsletterValidator.validateInput(newsletterFromDto);
        Newsletter newsletter = createNewsletter(newsletterFromDto);
        newsletter = newsletterRepository.save(newsletter);
        return mapperService.mapToNewsletterDto(newsletter);
    }

    private Newsletter createNewsletter(Newsletter newsletterFromDto) {
        Newsletter newsletter = new Newsletter();
        setFields(newsletter, newsletterFromDto);
        newsletter.setCreatedAt(LocalDateTime.now());
        newsletter.setSentAt(null);
        return newsletter;
    }

    private void setFields(Newsletter newsletter, Newsletter newsletterFromDto) {
        newsletter.setName(newsletterFromDto.getName());
        newsletter.setContent(newsletterFromDto.getContent());
    }

    @Transactional
    public void editNewsletter(long newsletterId, NewsletterInputDto newsletterDto) {
        Newsletter newsletter = newsletterValidator.validateExists(newsletterId);
        newsletterValidator.validateNotSent(newsletter);
        Newsletter newsletterFromDto = mapperService.mapToNewsletter(newsletterDto);
        newsletterValidator.validateInput(newsletterFromDto);
        setFields(newsletter, newsletterFromDto);
    }

    @Transactional
    public void sendNewsletter(long newsletterId) {
        Newsletter newsletter = newsletterValidator.validateExists(newsletterId);
        newsletterValidator.validateNotSent(newsletter);
        for (String email : subscriberService.getAllSubscribersEmails()) {
            sendNewsletter(email, newsletter);
        }
        newsletter.setSentAt(LocalDateTime.now());
        newsletterRepository.save(newsletter);
    }

    private void sendNewsletter(String email, Newsletter newsletter) {
        //TODO
        System.out.println(email + " " + newsletter.getName() + " " + newsletter.getContent());
    }

}
