package com.cinema.cinema.themes.newsletter;

import com.cinema.cinema.exceptions.ElementNotFoundException;
import com.cinema.cinema.exceptions.ProcessingException;
import com.cinema.cinema.themes.newsletter.model.Newsletter;
import com.cinema.cinema.utils.ValidatorService;
import jakarta.validation.Validator;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class NewsletterValidator extends ValidatorService<Newsletter> {

    private final NewsletterRepository newsletterRepository;

    public NewsletterValidator(Validator validator, NewsletterRepository newsletterRepository) {
        super(validator);
        this.newsletterRepository = newsletterRepository;
    }

    @Override
    public Newsletter validateExists(long newsletterId) {
        Optional<Newsletter> newsletter = newsletterRepository.findById(newsletterId);
        if (newsletter.isEmpty()) {
            throw new ElementNotFoundException("Newsletter with ID " + newsletterId + " not found");
        }
        return newsletter.get();
    }

    public void validateNotSent(Newsletter newsletter) {
        if (newsletter.getSentAt() != null) {
            throw new ProcessingException("Newsletter has already been sent");
        }
    }

}
