package net.luismarquez.projects.MovieManagement.persistence.specification;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import jakarta.persistence.criteria.Subquery;
import net.luismarquez.projects.MovieManagement.dto.request.MovieSearchCriteria;
import net.luismarquez.projects.MovieManagement.persistence.entity.Movie;
import net.luismarquez.projects.MovieManagement.persistence.entity.Rating;

public class FindAllMoviesSpecification implements Specification<Movie> {

	private static final long serialVersionUID = -1191336887675491674L;
	private MovieSearchCriteria searchCriteria;

    public FindAllMoviesSpecification(MovieSearchCriteria searchCriteria) {
        this.searchCriteria = searchCriteria;
    }

    @Override
    public Predicate toPredicate(Root<Movie> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        //root = from Movie
        //query = criterios de la consulta en si misma
        //criteriaBuilder = fabrica que te permite construir predicados y expresiones

        List<Predicate> predicates = new ArrayList<>();

        if(StringUtils.hasText(this.searchCriteria.title())){
            Predicate titleLike = criteriaBuilder.like(root.get("title"), "%" + this.searchCriteria.title() + "%");
            //m.title like '%asdasdasd%'
            predicates.add(titleLike);
        }

        if(this.searchCriteria.genres() != null && this.searchCriteria.genres().length > 0){
            Predicate genreEqual = criteriaBuilder.in(root.get("genre")).value(Arrays.stream(this.searchCriteria.genres()).toList());
            //and m.genre in (?,?,?)
            predicates.add(genreEqual);
        }

        if(this.searchCriteria.minReleaseYear() != null && this.searchCriteria.minReleaseYear().intValue() > 0){
            Predicate releaseYearGreaterThanEqual = criteriaBuilder.greaterThanOrEqualTo(root.get("releaseYear"), this.searchCriteria.minReleaseYear());
            // m.releaseYear >= ?
            predicates.add(releaseYearGreaterThanEqual);
        }

        if(this.searchCriteria.maxReleaseYear() != null && this.searchCriteria.maxReleaseYear() > 0){
            Predicate releaseYearLessThanEqual = criteriaBuilder.lessThanOrEqualTo(root.get("releaseYear"), this.searchCriteria.maxReleaseYear());
            //m.releaseYear <= ?
            predicates.add(releaseYearLessThanEqual);
        }

        if(this.searchCriteria.minAverageRating() != null && this.searchCriteria.minAverageRating() > 0){
            Subquery<Double> averageRatingSubquery = getAverageRatingSubquery(root, query, criteriaBuilder);

            Predicate averageRatingGreaterThanEqual = criteriaBuilder.greaterThanOrEqualTo(averageRatingSubquery, this.searchCriteria.minAverageRating().doubleValue());
            predicates.add(averageRatingGreaterThanEqual);
        }

        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));

        // select m.*
        // from movie m
        // where 1 = 1  and
        //              m.title like '%?1%'
        //              and m.genre in (?,?,?)
        //              and m.releaseYear >= ?3
        //              and m.releaseYear <= ?4
        //              and (select avg(r1_0.rating)  from rating r1_0 where r1_0.movie_id = m1_0.id)
        //                      >= searchCriteria.minAverageRating()


    }

    private static Subquery<Double> getAverageRatingSubquery(Root<Movie> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        Subquery<Double> averageRatingSubquery = query.subquery(Double.class);// select avg(rating)
        Root<Rating> ratingRoot = averageRatingSubquery.from(Rating.class);//from rating

        averageRatingSubquery.select( criteriaBuilder.avg(ratingRoot.get("rating")) );//avg(r1_0.rating)

        Predicate movieIdEqual = criteriaBuilder.equal(root.get("id"), ratingRoot.get("movieId"));
        averageRatingSubquery.where(movieIdEqual);

        return averageRatingSubquery;
    }

}
