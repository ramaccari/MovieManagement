package net.luismarquez.projects.MovieManagement.dto.response;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

import net.luismarquez.projects.MovieManagement.util.MovieGenre;

public record GetMovie(
        long id,
        String title,
        String director,
        MovieGenre genre,
        @JsonProperty(value = "release_year")  int releaseYear,
        @JsonProperty("total_ratings") int totalRatings
) implements Serializable {

    public static record GetRating(
            long id,
            int rating,
            String username
    ) implements Serializable {}

}
