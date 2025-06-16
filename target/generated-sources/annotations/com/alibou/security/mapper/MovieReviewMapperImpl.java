package com.alibou.security.mapper;

import com.alibou.security.entity.Movie;
import com.alibou.security.entity.MovieReview;
import com.alibou.security.entity.User;
import com.alibou.security.model.request.MovieReviewRequest;
import com.alibou.security.model.response.MovieReviewResponse;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.11 (Oracle Corporation)"
)
@Component
public class MovieReviewMapperImpl implements MovieReviewMapper {

    @Override
    public MovieReview toMovieReview(MovieReviewRequest request) {
        if ( request == null ) {
            return null;
        }

        MovieReview.MovieReviewBuilder movieReview = MovieReview.builder();

        movieReview.user( movieReviewRequestToUser( request ) );
        movieReview.movie( movieReviewRequestToMovie( request ) );
        movieReview.content( request.getContent() );
        movieReview.rating( request.getRating() );
        movieReview.createdAt( request.getCreatedAt() );
        movieReview.updatedAt( request.getUpdatedAt() );
        movieReview.createdBy( request.getCreatedBy() );
        movieReview.updatedBy( request.getUpdatedBy() );

        return movieReview.build();
    }

    @Override
    public MovieReviewResponse toMovieReviewResponse(MovieReview movieReview) {
        if ( movieReview == null ) {
            return null;
        }

        MovieReviewResponse.MovieReviewResponseBuilder movieReviewResponse = MovieReviewResponse.builder();

        movieReviewResponse.username( movieReviewUserUsername( movieReview ) );
        movieReviewResponse.movieTitle( movieReviewMovieTitle( movieReview ) );
        movieReviewResponse.content( movieReview.getContent() );
        movieReviewResponse.rating( movieReview.getRating() );
        movieReviewResponse.createdAt( movieReview.getCreatedAt() );
        movieReviewResponse.updatedAt( movieReview.getUpdatedAt() );
        movieReviewResponse.createdBy( movieReview.getCreatedBy() );
        movieReviewResponse.updatedBy( movieReview.getUpdatedBy() );

        return movieReviewResponse.build();
    }

    @Override
    public void updateMovieReview(MovieReview movieReview, MovieReviewRequest request) {
        if ( request == null ) {
            return;
        }

        movieReview.setContent( request.getContent() );
        movieReview.setRating( request.getRating() );
        movieReview.setUpdatedAt( request.getUpdatedAt() );
        movieReview.setCreatedBy( request.getCreatedBy() );
        movieReview.setUpdatedBy( request.getUpdatedBy() );
    }

    @Override
    public MovieReview toEntity(MovieReview request) {
        if ( request == null ) {
            return null;
        }

        MovieReview.MovieReviewBuilder movieReview = MovieReview.builder();

        movieReview.id( request.getId() );
        movieReview.user( request.getUser() );
        movieReview.movie( request.getMovie() );
        movieReview.content( request.getContent() );
        movieReview.rating( request.getRating() );
        movieReview.createdAt( request.getCreatedAt() );
        movieReview.updatedAt( request.getUpdatedAt() );
        movieReview.createdBy( request.getCreatedBy() );
        movieReview.updatedBy( request.getUpdatedBy() );

        return movieReview.build();
    }

    protected User movieReviewRequestToUser(MovieReviewRequest movieReviewRequest) {
        if ( movieReviewRequest == null ) {
            return null;
        }

        User.UserBuilder user = User.builder();

        user.id( movieReviewRequest.getUserId() );

        return user.build();
    }

    protected Movie movieReviewRequestToMovie(MovieReviewRequest movieReviewRequest) {
        if ( movieReviewRequest == null ) {
            return null;
        }

        Movie.MovieBuilder movie = Movie.builder();

        movie.id( movieReviewRequest.getMovieId() );

        return movie.build();
    }

    private String movieReviewUserUsername(MovieReview movieReview) {
        if ( movieReview == null ) {
            return null;
        }
        User user = movieReview.getUser();
        if ( user == null ) {
            return null;
        }
        String username = user.getUsername();
        if ( username == null ) {
            return null;
        }
        return username;
    }

    private String movieReviewMovieTitle(MovieReview movieReview) {
        if ( movieReview == null ) {
            return null;
        }
        Movie movie = movieReview.getMovie();
        if ( movie == null ) {
            return null;
        }
        String title = movie.getTitle();
        if ( title == null ) {
            return null;
        }
        return title;
    }
}
