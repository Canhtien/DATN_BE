package com.alibou.security.mapper;

import com.alibou.security.entity.Movie;
import com.alibou.security.model.request.MovieRequest;
import com.alibou.security.model.response.MovieResponse;
import java.time.LocalDateTime;
import java.time.ZoneId;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.12 (Oracle Corporation)"
)
@Component
public class MovieMapperImpl implements MovieMapper {

    @Override
    public Movie toMovie(MovieRequest request) {
        if ( request == null ) {
            return null;
        }

        Movie.MovieBuilder movie = Movie.builder();

        movie.title( request.getTitle() );
        movie.genre( request.getGenre() );
        movie.director( request.getDirector() );
        movie.actor( request.getActor() );
        movie.runTime( request.getRunTime() );
        movie.summary( request.getSummary() );
        movie.trailerUrl( request.getTrailerUrl() );
        movie.posterUrl( request.getPosterUrl() );
        movie.thumbnailUrl( request.getThumbnailUrl() );
        if ( request.getReleaseDate() != null ) {
            movie.releaseDate( LocalDateTime.ofInstant( request.getReleaseDate().toInstant(), ZoneId.of( "UTC" ) ) );
        }
        movie.createdAt( request.getCreatedAt() );
        movie.updatedAt( request.getUpdatedAt() );
        movie.createdBy( request.getCreatedBy() );
        movie.updatedBy( request.getUpdatedBy() );
        movie.rating( request.getRating() );

        return movie.build();
    }

    @Override
    public MovieResponse toMovieResponse(Movie movie) {
        if ( movie == null ) {
            return null;
        }

        MovieResponse.MovieResponseBuilder movieResponse = MovieResponse.builder();

        movieResponse.title( movie.getTitle() );
        movieResponse.director( movie.getDirector() );
        movieResponse.actor( movie.getActor() );
        if ( movie.getRunTime() != null ) {
            movieResponse.runTime( movie.getRunTime() );
        }
        movieResponse.genre( movie.getGenre() );
        movieResponse.releaseDate( movie.getReleaseDate() );
        movieResponse.posterUrl( movie.getPosterUrl() );
        movieResponse.thumbnailUrl( movie.getThumbnailUrl() );
        movieResponse.summary( movie.getSummary() );
        movieResponse.trailerUrl( movie.getTrailerUrl() );
        movieResponse.createdAt( movie.getCreatedAt() );
        movieResponse.updatedAt( movie.getUpdatedAt() );
        movieResponse.createdBy( movie.getCreatedBy() );
        movieResponse.updatedBy( movie.getUpdatedBy() );
        movieResponse.rating( movie.getRating() );

        return movieResponse.build();
    }

    @Override
    public void updateMovie(Movie movie, MovieRequest request) {
        if ( request == null ) {
            return;
        }

        movie.setTitle( request.getTitle() );
        movie.setGenre( request.getGenre() );
        movie.setDirector( request.getDirector() );
        movie.setActor( request.getActor() );
        movie.setRunTime( request.getRunTime() );
        movie.setSummary( request.getSummary() );
        movie.setTrailerUrl( request.getTrailerUrl() );
        movie.setPosterUrl( request.getPosterUrl() );
        movie.setThumbnailUrl( request.getThumbnailUrl() );
        if ( request.getReleaseDate() != null ) {
            movie.setReleaseDate( LocalDateTime.ofInstant( request.getReleaseDate().toInstant(), ZoneId.of( "UTC" ) ) );
        }
        else {
            movie.setReleaseDate( null );
        }
        movie.setUpdatedAt( request.getUpdatedAt() );
        movie.setCreatedBy( request.getCreatedBy() );
        movie.setUpdatedBy( request.getUpdatedBy() );
        movie.setRating( request.getRating() );
    }
}
