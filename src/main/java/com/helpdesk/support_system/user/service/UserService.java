package com.helpdesk.support_system.user.service;

import com.helpdesk.support_system.user.dto.UserPromoResponse;
import com.helpdesk.support_system.user.dto.UserPromoRequest;
import com.helpdesk.support_system.user.dto.UserRequest;
import com.helpdesk.support_system.user.dto.UserResponse;
import com.helpdesk.support_system.user.mapper.UserMapper;
import com.helpdesk.support_system.user.model.Roles;
import com.helpdesk.support_system.user.model.User;
import com.helpdesk.support_system.user.repo.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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
        return mapper.response(userRepository.save(entity));
    }

    public UserPromoResponse promote(UserPromoRequest request){
        return userRepository.findByUsername(request.getUsername())
                .map(user -> {
                    if (user.getRoles().contains(Roles.AGENT)){
                        throw new RuntimeException("User is already an AGENT!");
                    }
                    user.getRoles().add(Roles.AGENT);
                    return mapper.promoResponse(userRepository.save(user));
                })
                .orElseThrow(() -> new RuntimeException("User Not Found, promote registered users only!"));
    }

    public void deleteUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("User Not Found"));
        userRepository.delete(user);
    }

    public void reActivateUser(Long id) {
        User user = userRepository.findByIdIncludingDeleted(id)
                .orElseThrow(()-> new RuntimeException("User Not Found"));
        if (user.getDeleted() == false){
            throw new RuntimeException("User is Active already!");
        }
        user.setDeleted(false);
        userRepository.save(user);
    }

    public List<UserResponse> users(){
        return userRepository.findAll().stream()
                .map(mapper::response)
                .collect(Collectors.toList());
    }

}
