package com.cinema.cinema.themes.newsletter;

import com.cinema.cinema.themes.newsletter.model.Newsletter;
import com.cinema.cinema.themes.subscriber.SubscriberService;
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

    @Transactional(readOnly = true)
    public List<Newsletter> getAllNewsletters() {
        return newsletterRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Newsletter getNewsletter(long newsletterId) {
        return newsletterValidator.validateExists(newsletterId);
    }

    @Transactional
    public Newsletter addNewsletter(Newsletter newsletterFromDto) {
        newsletterValidator.validateInput(newsletterFromDto);
        Newsletter newsletter = createNewsletter(newsletterFromDto);
        return newsletterRepository.save(newsletter);
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
    public void editNewsletter(long newsletterId, Newsletter newsletterFromDto) {
        Newsletter newsletter = newsletterValidator.validateExists(newsletterId);
        newsletterValidator.validateNotSent(newsletter);
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
