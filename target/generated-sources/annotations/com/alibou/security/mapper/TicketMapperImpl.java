package com.alibou.security.mapper;

import com.alibou.security.entity.DiscountApplication;
import com.alibou.security.entity.Hall;
import com.alibou.security.entity.Movie;
import com.alibou.security.entity.Showtime;
import com.alibou.security.entity.Theater;
import com.alibou.security.entity.Ticket;
import com.alibou.security.entity.User;
import com.alibou.security.model.request.TicketRequest;
import com.alibou.security.model.response.TicketResponse;
import java.time.LocalDateTime;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.11 (Oracle Corporation)"
)
@Component
public class TicketMapperImpl implements TicketMapper {

    @Override
    public Ticket toTicket(TicketRequest ticketRequest) {
        if ( ticketRequest == null ) {
            return null;
        }

        Ticket.TicketBuilder ticket = Ticket.builder();

        ticket.showtime( ticketRequestToShowtime( ticketRequest ) );
        ticket.user( ticketRequestToUser( ticketRequest ) );
        ticket.discountApplication( ticketRequestToDiscountApplication( ticketRequest ) );
        ticket.ticketType( ticketRequest.getTicketType() );
        ticket.price( ticketRequest.getPrice() );
        ticket.serviceFee( ticketRequest.getServiceFee() );
        ticket.status( ticketRequest.getStatus() );
        ticket.createdAt( ticketRequest.getCreatedAt() );
        ticket.updatedAt( ticketRequest.getUpdatedAt() );
        ticket.createdBy( ticketRequest.getCreatedBy() );
        ticket.updatedBy( ticketRequest.getUpdatedBy() );

        return ticket.build();
    }

    @Override
    public TicketResponse toTicketResponse(Ticket ticket) {
        if ( ticket == null ) {
            return null;
        }

        TicketResponse.TicketResponseBuilder ticketResponse = TicketResponse.builder();

        ticketResponse.showTime( ticketShowtimeShowTime( ticket ) );
        ticketResponse.movieTitle( ticketShowtimeMovieTitle( ticket ) );
        ticketResponse.theaterName( ticketShowtimeTheaterName( ticket ) );
        ticketResponse.hallName( ticketShowtimeHallName( ticket ) );
        ticketResponse.id( ticket.getId() );
        ticketResponse.ticketType( ticket.getTicketType() );
        ticketResponse.price( ticket.getPrice() );
        ticketResponse.serviceFee( ticket.getServiceFee() );
        ticketResponse.status( ticket.getStatus() );
        ticketResponse.createdAt( ticket.getCreatedAt() );
        ticketResponse.updatedAt( ticket.getUpdatedAt() );
        ticketResponse.createdBy( ticket.getCreatedBy() );
        ticketResponse.updatedBy( ticket.getUpdatedBy() );

        return ticketResponse.build();
    }

    @Override
    public void updateTicket(Ticket ticket, TicketRequest ticketRequest) {
        if ( ticketRequest == null ) {
            return;
        }

        ticket.setTicketType( ticketRequest.getTicketType() );
        ticket.setPrice( ticketRequest.getPrice() );
        ticket.setServiceFee( ticketRequest.getServiceFee() );
        ticket.setStatus( ticketRequest.getStatus() );
        ticket.setUpdatedAt( ticketRequest.getUpdatedAt() );
        ticket.setCreatedBy( ticketRequest.getCreatedBy() );
        ticket.setUpdatedBy( ticketRequest.getUpdatedBy() );
    }

    protected Showtime ticketRequestToShowtime(TicketRequest ticketRequest) {
        if ( ticketRequest == null ) {
            return null;
        }

        Showtime.ShowtimeBuilder showtime = Showtime.builder();

        showtime.id( ticketRequest.getShowtime_id() );

        return showtime.build();
    }

    protected User ticketRequestToUser(TicketRequest ticketRequest) {
        if ( ticketRequest == null ) {
            return null;
        }

        User.UserBuilder user = User.builder();

        user.id( ticketRequest.getUser_id() );

        return user.build();
    }

    protected DiscountApplication ticketRequestToDiscountApplication(TicketRequest ticketRequest) {
        if ( ticketRequest == null ) {
            return null;
        }

        DiscountApplication.DiscountApplicationBuilder discountApplication = DiscountApplication.builder();

        discountApplication.id( ticketRequest.getDiscount_application_id() );

        return discountApplication.build();
    }

    private LocalDateTime ticketShowtimeShowTime(Ticket ticket) {
        if ( ticket == null ) {
            return null;
        }
        Showtime showtime = ticket.getShowtime();
        if ( showtime == null ) {
            return null;
        }
        LocalDateTime showTime = showtime.getShowTime();
        if ( showTime == null ) {
            return null;
        }
        return showTime;
    }

    private String ticketShowtimeMovieTitle(Ticket ticket) {
        if ( ticket == null ) {
            return null;
        }
        Showtime showtime = ticket.getShowtime();
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

    private String ticketShowtimeTheaterName(Ticket ticket) {
        if ( ticket == null ) {
            return null;
        }
        Showtime showtime = ticket.getShowtime();
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

    private String ticketShowtimeHallName(Ticket ticket) {
        if ( ticket == null ) {
            return null;
        }
        Showtime showtime = ticket.getShowtime();
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
