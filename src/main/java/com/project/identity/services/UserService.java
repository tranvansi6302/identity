package com.project.identity.services;

import com.project.identity.dtos.requests.UserCreateRequest;
import com.project.identity.dtos.requests.UserUpdateRequest;
import com.project.identity.dtos.responses.UserResponse;
import com.project.identity.entities.Role;
import com.project.identity.entities.User;
import com.project.identity.exceptions.AppException;
import com.project.identity.exceptions.ErrorCode;
import com.project.identity.mappers.UserMapper;
import com.project.identity.repositories.RoleRepository;
import com.project.identity.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;


@Service
@RequiredArgsConstructor

public class UserService implements IUserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional(readOnly = true)
    public UserResponse getUserById(String id) {
        User user = userRepository.findById(id).orElseThrow(
                () -> new AppException(ErrorCode.USER_NOT_FOUND)
        );
        return userMapper.toUserResponse(user);
    }
    @Override
    @Transactional
    public UserResponse createUser(UserCreateRequest request) {
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new AppException(ErrorCode.USER_EXISTS);
        }
        User user = userMapper.toUser(request);
        user.setPassword(passwordEncoder.encode(request.getPassword()));

//        Set<String> roles = new HashSet<>();
//        roles.add(Role.USER.name());
//        user.setRoles(roles);
        return userMapper.toUserResponse(userRepository.save(user));
    }

    @Override
    @Transactional
    public UserResponse updateUser(String id, UserUpdateRequest request) {
        User user = userRepository.findById(id).orElseThrow(
                () -> new AppException(ErrorCode.USER_NOT_FOUND)
        );
        userMapper.updateUser(user, request);
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        List<Role> roles = roleRepository.findAllById(request.getRoles());
        user.setRoles(new HashSet<>(roles));
        return userMapper.toUserResponse(userRepository.save(user));
    }

    @Override
    public UserResponse getMe() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUsername(username).orElseThrow(
                () -> new AppException(ErrorCode.USER_NOT_FOUND)
        );
        return userMapper.toUserResponse(user);
    }

    @Override
    @Transactional
    public void deleteUser(String id) {
        User user = userRepository.findById(id).orElseThrow(
                () -> new AppException(ErrorCode.USER_NOT_FOUND)
        );
        userRepository.delete(user);
    }

    @Override
    @Transactional(readOnly = true)
    @PreAuthorize("hasAuthority('CREATE_POST')")
    public List<UserResponse> getAllUsers() {

        return userRepository.findAll().stream()
                .map(userMapper::toUserResponse).toList();
    }
}
