package net.luismarquez.projects.MovieManagement.dto.response;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

import net.luismarquez.projects.MovieManagement.util.MovieGenre;

public record GetMovieStatistic(
        Long id,
        String title,
        String director,
        MovieGenre genre,
        @JsonProperty("total_ratings") int totalRatings,
        @JsonProperty("release_year") int releaseYear,
        @JsonProperty("average_ratings")
        Double averageRatings,
        @JsonProperty("lowest_rating") int lowestRating,
        @JsonProperty("highest_rating") int highestRating
) implements Serializable {

        @Override
        public Double averageRatings() {
                return Double.parseDouble(String.format("%1.2f", averageRatings));
        }
}
