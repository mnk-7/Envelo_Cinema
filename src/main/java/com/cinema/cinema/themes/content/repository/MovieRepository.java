package com.cinema.cinema.themes.content.repository;

import com.cinema.cinema.themes.content.model.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {

}
