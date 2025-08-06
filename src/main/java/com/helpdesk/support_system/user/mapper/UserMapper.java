package com.helpdesk.support_system.user.mapper;

import com.helpdesk.support_system.user.dto.UserPromoResponse;
import com.helpdesk.support_system.user.dto.UserPromoRequest;
import com.helpdesk.support_system.user.dto.UserRequest;
import com.helpdesk.support_system.user.dto.UserResponse;
import com.helpdesk.support_system.user.model.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User promoEntity(UserPromoRequest user);
    UserPromoResponse promoResponse(User promotedUser);

    User entity(UserRequest request);
    UserResponse response(User entity);
}
