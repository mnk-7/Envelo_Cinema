package com.cinema.cinema.themes.ageRestriction;

import com.cinema.cinema.themes.ageRestriction.model.AgeRestriction;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class AgeRestrictionRepositoryTest {

    @Autowired
    private AgeRestrictionRepository repository;
    private static int idCounter = 0;

    @Test
    void testCreatingNewAgeRestriction() {
        AgeRestriction ageRestriction = initializeSingleData();

        assertNotNull(ageRestriction);
        assertNotNull(ageRestriction.getId());
        assertTrue(ageRestriction.getId() > 0);
        assertEquals(1, repository.findAll().size());
    }

    @Test
    void testEditingAgeRestriction() {
        AgeRestriction ageRestriction = initializeSingleData();

        ageRestriction.setMinAge("50");
        ageRestriction = repository.save(ageRestriction);

        assertEquals("50", ageRestriction.getMinAge());
        assertEquals(1, repository.findAll().size());
    }

    @Test
    void testGettingAgeRestrictionById() {
        List<AgeRestriction> ageRestrictions = initializeListData();

        Optional<AgeRestriction> ageRestrictionFromDB = repository.findById((long) idCounter);

        assertTrue(ageRestrictionFromDB.isPresent());
        assertEquals(ageRestrictionFromDB.get(), ageRestrictions.get(2));
    }

    @Test
    void testGettingAgeRestrictionByIdNotInDB() {
        initializeListData();

        Optional<AgeRestriction> ageRestrictionFromDB = repository.findById(idCounter + 1L);

        assertFalse(ageRestrictionFromDB.isPresent());
    }

    @Test
    void testGettingByMinAge() {
        List<AgeRestriction> ageRestrictions = initializeListData();

        Optional<AgeRestriction> ageRestrictionFromDB = repository.findByMinAge("12");

        assertTrue(ageRestrictionFromDB.isPresent());
        assertEquals(ageRestrictionFromDB.get(), ageRestrictions.get(1));
    }

    @Test
    void testGettingByMinAgeNotInDB() {
        initializeListData();

        Optional<AgeRestriction> ageRestrictionFromDB = repository.findByMinAge("16");

        assertFalse(ageRestrictionFromDB.isPresent());
    }

    @Test
    void testGettingAllAgeRestrictions() {
        initializeListData();

        List<AgeRestriction> ageRestrictionsFromDB = repository.findAll();

        assertEquals(3, ageRestrictionsFromDB.size());
    }

    private AgeRestriction initializeSingleData() {
        AgeRestriction ageRestriction = new AgeRestriction();
        ageRestriction.setMinAge("21");
        idCounter++;
        return repository.save(ageRestriction);
    }

    private List<AgeRestriction> initializeListData() {
        List<AgeRestriction> ageRestrictions = new ArrayList<>();
        AgeRestriction first = new AgeRestriction();
        first.setMinAge("all");
        ageRestrictions.add(first);

        AgeRestriction second = new AgeRestriction();
        second.setMinAge("12");
        ageRestrictions.add(second);

        AgeRestriction third = new AgeRestriction();
        third.setMinAge("18");
        ageRestrictions.add(third);

        for (AgeRestriction ageRestriction : ageRestrictions) {
            repository.save(ageRestriction);
            idCounter++;
        }
        return ageRestrictions;
    }

}