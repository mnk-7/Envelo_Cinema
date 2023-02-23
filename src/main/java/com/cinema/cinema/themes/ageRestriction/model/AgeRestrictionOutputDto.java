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
public class AgeRestrictionOutputDto {

    private Long id;
    private String minAge;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AgeRestrictionOutputDto that = (AgeRestrictionOutputDto) o;
        return Objects.equals(id, that.id) && Objects.equals(minAge, that.minAge);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, minAge);
    }

    @Override
    public String toString() {
        return "AgeRestrictionOutputDto{" +
                "id=" + id +
                ", minAge='" + minAge + '\'' +
                '}';
    }

}
