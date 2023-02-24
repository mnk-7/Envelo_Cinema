package com.cinema.cinema.themes.ageRestriction;

import com.cinema.cinema.themes.ageRestriction.model.AgeRestriction;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class AgeRestrictionRepositoryTest {

    @Autowired
    private AgeRestrictionRepository repository;
    private static int idCounter = 0;


    @Test
    void shouldSaveInDbNewAgeRestriction() {
        AgeRestriction ageRestriction = AgeRestrictionData.initializeSingleData();

        repository.save(ageRestriction);
        idCounter++;

        assertNotNull(ageRestriction);
        assertNotNull(ageRestriction.getId());
        assertTrue(ageRestriction.getId() > 0);
        assertEquals(1, repository.findAll().size());
    }

    @Test
    void shouldUpdateInDBExistingAgeRestriction() {
        AgeRestriction ageRestriction = AgeRestrictionData.initializeSingleData();
        ageRestriction = repository.save(ageRestriction);
        idCounter++;

        ageRestriction.setMinAge(AgeRestrictionData.MIN_AGE_12);
        ageRestriction = repository.save(ageRestriction);

        assertEquals(AgeRestrictionData.MIN_AGE_12, ageRestriction.getMinAge());
        assertEquals(1, repository.findAll().size());
    }

    @Test
    void shouldGetFromDBAgeRestrictionById() {
        List<AgeRestriction> ageRestrictions = AgeRestrictionData.initializeListData();
        for (AgeRestriction ageRestriction : ageRestrictions) {
            repository.save(ageRestriction);
            idCounter++;
        }

        Optional<AgeRestriction> ageRestriction = repository.findById((long) idCounter);

        assertTrue(ageRestriction.isPresent());
        assertEquals(ageRestriction.get(), ageRestrictions.get(ageRestrictions.size() - 1));
    }

    @Test
    void shouldNotGetFromDBAgeRestrictionByIdWhileNotExistingId() {
        List<AgeRestriction> ageRestrictions = AgeRestrictionData.initializeListData();
        for (AgeRestriction ageRestriction : ageRestrictions) {
            repository.save(ageRestriction);
            idCounter++;
        }

        Optional<AgeRestriction> ageRestriction = repository.findById(idCounter + 1L);

        assertFalse(ageRestriction.isPresent());
    }

    @Test
    void shouldGetFromDBAgeRestrictionByMinAge() {
        List<AgeRestriction> ageRestrictions = AgeRestrictionData.initializeListData();
        for (AgeRestriction ageRestriction : ageRestrictions) {
            repository.save(ageRestriction);
            idCounter++;
        }

        Optional<AgeRestriction> ageRestriction = repository.findByMinAge(AgeRestrictionData.MIN_AGE_12);

        assertTrue(ageRestriction.isPresent());
        AgeRestriction restriction = ageRestriction.get();
        assertEquals(restriction, ageRestrictions.get((int) (restriction.getId() - 1 - idCounter + ageRestrictions.size())));
    }

    @Test
    void shouldNotGetFromDBAgeRestrictionByMinAgeWhileNotExistingMinAge() {
        List<AgeRestriction> ageRestrictions = AgeRestrictionData.initializeListData();
        for (AgeRestriction ageRestriction : ageRestrictions) {
            repository.save(ageRestriction);
            idCounter++;
        }

        Optional<AgeRestriction> ageRestriction = repository.findByMinAge(AgeRestrictionData.MIN_AGE_6);

        assertFalse(ageRestriction.isPresent());
    }

    @Test
    void shouldGetFromDBAllAgeRestrictions() {
        List<AgeRestriction> ageRestrictions = AgeRestrictionData.initializeListData();
        for (AgeRestriction ageRestriction : ageRestrictions) {
            repository.save(ageRestriction);
            idCounter++;
        }

        List<AgeRestriction> restrictions = repository.findAll();

        assertEquals(ageRestrictions.size(), restrictions.size());
    }

}
