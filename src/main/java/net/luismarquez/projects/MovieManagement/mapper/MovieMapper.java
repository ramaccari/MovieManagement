package net.luismarquez.projects.MovieManagement.mapper;

import java.util.List;

import net.luismarquez.projects.MovieManagement.dto.request.SaveMovie;
import net.luismarquez.projects.MovieManagement.dto.response.GetMovie;
import net.luismarquez.projects.MovieManagement.dto.response.GetMovieStatistic;
import net.luismarquez.projects.MovieManagement.persistence.entity.Movie;

public class MovieMapper {

    public static GetMovieStatistic toGetMovieStatisticDto(Movie entity, int totalRatings,
                                                           double averageRating, int lowestRating,
                                                           int highestRating){
        if(entity == null) return null;

        return new GetMovieStatistic(
                entity.getId(),
                entity.getTitle(),
                entity.getDirector(),
                entity.getGenre(),
                totalRatings,
                entity.getReleaseYear(),
                averageRating,
                lowestRating,
                highestRating
        );
    }

    public static GetMovie toGetDto(Movie entity){
        if(entity == null) return null;

        int totalRatings = entity.getRatings() != null ? entity.getRatings().size() : 0;

        return new GetMovie(
                entity.getId(),
                entity.getTitle(),
                entity.getDirector(),
                entity.getGenre(),
                entity.getReleaseYear(),
                totalRatings
        );

    }

    public static List<GetMovie> toGetDtoList(List<Movie> entities){
        if(entities == null) return null;

        return entities.stream() //List<Movie> -> Stream<Movie>
                .map(MovieMapper::toGetDto) // Stream<Movie> -> Stream<GetMovie>
                .toList();
    }

    public static Movie toEntity(SaveMovie saveDto){
        if(saveDto == null) return null;

        Movie newMovie = new Movie();
        newMovie.setTitle(saveDto.title());
        newMovie.setDirector(saveDto.director());
        newMovie.setReleaseYear(saveDto.releaseYear());
        newMovie.setGenre(saveDto.genre());

        return newMovie;
    }

    public static void updateEntity(Movie oldMovie, SaveMovie saveDto) {
        if(oldMovie == null || saveDto == null) return;

        oldMovie.setGenre(saveDto.genre());
        oldMovie.setReleaseYear(saveDto.releaseYear());
        oldMovie.setTitle(saveDto.title());
        oldMovie.setDirector(saveDto.director());

    }
}
