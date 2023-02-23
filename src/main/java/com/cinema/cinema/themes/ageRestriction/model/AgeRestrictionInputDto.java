package com.cinema.cinema.themes.ageRestriction.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AgeRestrictionInputDto {

    private String minAge;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AgeRestrictionInputDto that = (AgeRestrictionInputDto) o;
        return Objects.equals(minAge, that.minAge);
    }

    @Override
    public int hashCode() {
        return Objects.hash(minAge);
    }

    @Override
    public String toString() {
        return "AgeRestrictionInputDto{" +
                "minAge='" + minAge + '\'' +
                '}';
    }

}
