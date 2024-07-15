package net.luismarquez.projects.MovieManagement.controller;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import net.luismarquez.projects.MovieManagement.dto.request.MovieSearchCriteria;
import net.luismarquez.projects.MovieManagement.dto.request.SaveMovie;
import net.luismarquez.projects.MovieManagement.dto.response.GetMovie;
import net.luismarquez.projects.MovieManagement.dto.response.GetMovieStatistic;
import net.luismarquez.projects.MovieManagement.service.MovieService;
import net.luismarquez.projects.MovieManagement.service.RatingService;
import net.luismarquez.projects.MovieManagement.util.MovieGenre;

@RestController
@RequestMapping("/movies")
public class MovieController {

    @Autowired
    private MovieService movieService;

    @Autowired
    private RatingService ratingService;

    @GetMapping
    public ResponseEntity<Page<GetMovie>> findAll(@RequestParam(required = false) String title,
                                                  @RequestParam(required = false) MovieGenre[] genres,
                                                  @RequestParam(required = false, name = "min_release_year") Integer minReleaseYear,
                                                  @RequestParam(required = false, name = "max_release_year") Integer maxReleaseYear,
                                                  @RequestParam(required = false, name = "min_average_rating") Integer minAverageRating,
                                                  Pageable moviePageable){

        MovieSearchCriteria searchCriteria = new MovieSearchCriteria(title, genres, minReleaseYear, maxReleaseYear, minAverageRating);
        Page<GetMovie> movies = movieService.findAll(searchCriteria, moviePageable);
        return ResponseEntity.ok(movies);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<GetMovieStatistic> findOneById(@PathVariable Long id){
        return ResponseEntity.ok(movieService.findOneById(id));
    }

//    /movies/{id}/ratings
    @GetMapping(value = "/{id}/ratings")
    public ResponseEntity<Page<GetMovie.GetRating>> findAllRatingsForMovieById(@PathVariable Long id, Pageable pageable ){
        return ResponseEntity.ok(ratingService.findAllByMovieId(id, pageable));
    }

    @PostMapping
    public ResponseEntity<GetMovie> createOne(@RequestBody @Valid SaveMovie saveDto,
                                           HttpServletRequest request){

//        System.out.println("Fecha: " + saveDto.availabilityEndTime());
        GetMovie movieCreated = movieService.createOne(saveDto);

        String baseUrl = request.getRequestURL().toString();
        URI newLocation = URI.create(baseUrl + "/" + movieCreated.id());

        return ResponseEntity
                .created(newLocation)
                .body(movieCreated);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<GetMovie> updateOneById(@PathVariable Long id,
                                                  @Valid @RequestBody SaveMovie saveDto){
        GetMovie updatedMovie = movieService.updateOneById(id, saveDto);
        return ResponseEntity.ok(updatedMovie);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteOneById(@PathVariable Long id){
        movieService.deleteOneById(id);
        return ResponseEntity.noContent().build();
    }

}
