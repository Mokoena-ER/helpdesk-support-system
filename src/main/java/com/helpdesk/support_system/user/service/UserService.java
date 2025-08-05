package com.helpdesk.support_system.user.service;

import com.helpdesk.support_system.user.dto.UserPromoted;
import com.helpdesk.support_system.user.dto.UserPromotion;
import com.helpdesk.support_system.user.dto.UserRequest;
import com.helpdesk.support_system.user.dto.UserResponse;
import com.helpdesk.support_system.user.mapper.UserMapper;
import com.helpdesk.support_system.user.model.Roles;
import com.helpdesk.support_system.user.model.User;
import com.helpdesk.support_system.user.repo.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper mapper;

    public UserResponse register(UserRequest request, Set<Roles> roles) {
        User entity = mapper.entity(request);

        entity.setEmail(entity.getUsername().toLowerCase()+"@gmail.com");
        entity.setRoles(roles);

        User saved = userRepository.save(entity);
        return mapper.response(saved);
    }

    public UserPromoted promote(UserPromotion request) {
        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(()-> new RuntimeException("Only registered users can be promoted"));

        if (user.getRoles().contains(Roles.AGENT)){
            throw new IllegalStateException("User is already an AGENT");
        }
        user.getRoles().add(Roles.AGENT);

        return mapper.promoted(userRepository.save(user));
    }


}
