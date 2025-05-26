package com.alibou.security.mapper;

import com.alibou.security.entity.Theater;
import com.alibou.security.model.request.TheaterRequest;
import com.alibou.security.model.response.TheaterResponse;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.12 (Oracle Corporation)"
)
@Component
public class TheaterMapperImpl implements TheaterMapper {

    @Override
    public Theater toTheater(TheaterRequest request) {
        if ( request == null ) {
            return null;
        }

        Theater.TheaterBuilder theater = Theater.builder();

        theater.name( request.getName() );
        theater.location( request.getLocation() );
        theater.phone( request.getPhone() );

        return theater.build();
    }

    @Override
    public TheaterResponse toTheaterResponse(Theater theater) {
        if ( theater == null ) {
            return null;
        }

        TheaterResponse.TheaterResponseBuilder theaterResponse = TheaterResponse.builder();

        theaterResponse.id( theater.getId() );
        theaterResponse.name( theater.getName() );
        theaterResponse.location( theater.getLocation() );
        theaterResponse.phone( theater.getPhone() );

        return theaterResponse.build();
    }
}
