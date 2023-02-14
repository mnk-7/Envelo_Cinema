package com.cinema.cinema.themes.content.validator;

import com.cinema.cinema.exceptions.ElementNotFoundException;
import com.cinema.cinema.themes.content.model.Content;
import com.cinema.cinema.themes.content.repository.ContentRepository;
import com.cinema.cinema.utils.ValidatorService;
import jakarta.validation.Validator;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ContentValidator extends ValidatorService<Content> {

    private final ContentRepository contentRepository;

    public ContentValidator(Validator validator, ContentRepository contentRepository) {
        super(validator);
        this.contentRepository = contentRepository;
    }

    @Override
    public Content validateExists(long contentId) {
        Optional<Content> content = contentRepository.findById(contentId);
        if (content.isEmpty()) {
            throw new ElementNotFoundException("Content with ID " + contentId + " not found");
        }
        return content.get();
    }

}
