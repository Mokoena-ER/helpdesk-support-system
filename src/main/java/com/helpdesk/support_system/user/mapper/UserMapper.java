package com.helpdesk.support_system.user.mapper;

import com.helpdesk.support_system.user.dto.UserPromoted;
import com.helpdesk.support_system.user.dto.UserPromotion;
import com.helpdesk.support_system.user.dto.UserRequest;
import com.helpdesk.support_system.user.dto.UserResponse;
import com.helpdesk.support_system.user.model.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User promotion(UserPromotion user);
    UserPromoted promoted(User promotedUser);

    User entity(UserRequest request);
    UserResponse response(User entity);
}
