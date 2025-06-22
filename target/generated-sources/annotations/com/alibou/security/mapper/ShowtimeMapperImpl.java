package com.alibou.security.mapper;

import com.alibou.security.entity.Hall;
import com.alibou.security.entity.Movie;
import com.alibou.security.entity.Showtime;
import com.alibou.security.entity.Theater;
import com.alibou.security.model.request.ShowtimeRequest;
import com.alibou.security.model.response.ShowtimeResponse;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.11 (Oracle Corporation)"
)
@Component
public class ShowtimeMapperImpl implements ShowtimeMapper {

    @Override
    public Showtime toShowtime(ShowtimeRequest request) {
        if ( request == null ) {
            return null;
        }

        Showtime.ShowtimeBuilder showtime = Showtime.builder();

        showtime.movie( showtimeRequestToMovie( request ) );
        showtime.theater( showtimeRequestToTheater( request ) );
        showtime.hall( showtimeRequestToHall( request ) );
        showtime.showTime( request.getShowTime() );

        return showtime.build();
    }

    @Override
    public ShowtimeResponse toshowtimeResponse(Showtime showtime) {
        if ( showtime == null ) {
            return null;
        }

        ShowtimeResponse.ShowtimeResponseBuilder showtimeResponse = ShowtimeResponse.builder();

        showtimeResponse.movieTitle( showtimeMovieTitle( showtime ) );
        showtimeResponse.theaterName( showtimeTheaterName( showtime ) );
        showtimeResponse.hallName( showtimeHallName( showtime ) );
        showtimeResponse.id( showtime.getId() );
        showtimeResponse.showTime( showtime.getShowTime() );

        return showtimeResponse.build();
    }

    @Override
    public void updateShowtime(Showtime showtime, ShowtimeRequest request) {
        if ( request == null ) {
            return;
        }

        showtime.setShowTime( request.getShowTime() );
    }

    protected Movie showtimeRequestToMovie(ShowtimeRequest showtimeRequest) {
        if ( showtimeRequest == null ) {
            return null;
        }

        Movie.MovieBuilder movie = Movie.builder();

        movie.id( showtimeRequest.getMovieId() );

        return movie.build();
    }

    protected Theater showtimeRequestToTheater(ShowtimeRequest showtimeRequest) {
        if ( showtimeRequest == null ) {
            return null;
        }

        Theater.TheaterBuilder theater = Theater.builder();

        theater.id( showtimeRequest.getTheaterId() );

        return theater.build();
    }

    protected Hall showtimeRequestToHall(ShowtimeRequest showtimeRequest) {
        if ( showtimeRequest == null ) {
            return null;
        }

        Hall.HallBuilder hall = Hall.builder();

        hall.id( showtimeRequest.getHallId() );

        return hall.build();
    }

    private String showtimeMovieTitle(Showtime showtime) {
        if ( showtime == null ) {
            return null;
        }
        Movie movie = showtime.getMovie();
        if ( movie == null ) {
            return null;
        }
        String title = movie.getTitle();
        if ( title == null ) {
            return null;
        }
        return title;
    }

    private String showtimeTheaterName(Showtime showtime) {
        if ( showtime == null ) {
            return null;
        }
        Theater theater = showtime.getTheater();
        if ( theater == null ) {
            return null;
        }
        String name = theater.getName();
        if ( name == null ) {
            return null;
        }
        return name;
    }

    private String showtimeHallName(Showtime showtime) {
        if ( showtime == null ) {
            return null;
        }
        Hall hall = showtime.getHall();
        if ( hall == null ) {
            return null;
        }
        String name = hall.getName();
        if ( name == null ) {
            return null;
        }
        return name;
    }
}
