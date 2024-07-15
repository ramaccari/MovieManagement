package net.luismarquez.projects.MovieManagement.dto.response;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

public record GetUser(
        String username,
        String name,
        @JsonProperty("total_ratings") int totalRatings
) implements Serializable {

    public static record GetRating(
            long id,
            @JsonProperty(value = "movie_title") String movieTitle,
            @JsonProperty(value = "movie_id") long movieId,
            int rating
    ) implements Serializable {}

}
