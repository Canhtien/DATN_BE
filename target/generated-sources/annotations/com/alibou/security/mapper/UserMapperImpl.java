package com.alibou.security.mapper;

import com.alibou.security.entity.User;
import com.alibou.security.model.response.UserResponse;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.12 (Oracle Corporation)"
)
@Component
public class UserMapperImpl implements UserMapper {

    @Override
    public UserResponse toUserResponse(User user) {
        if ( user == null ) {
            return null;
        }

        UserResponse.UserResponseBuilder userResponse = UserResponse.builder();

        userResponse.id( user.getId() );
        userResponse.username( user.getUsername() );
        userResponse.fullName( user.getFullName() );
        userResponse.email( user.getEmail() );
        userResponse.phone( user.getPhone() );
        userResponse.createdAt( user.getCreatedAt() );
        userResponse.updatedAt( user.getUpdatedAt() );
        userResponse.createdBy( user.getCreatedBy() );
        userResponse.updatedBy( user.getUpdatedBy() );

        return userResponse.build();
    }
}
