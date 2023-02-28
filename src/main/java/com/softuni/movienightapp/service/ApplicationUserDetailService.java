package com.softuni.movienightapp.service;


import com.softuni.movienightapp.model.entity.RoleEntity;
import com.softuni.movienightapp.model.entity.UserEntity;
import com.softuni.movienightapp.repository.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;
import java.util.stream.Collectors;

public class ApplicationUserDetailService implements UserDetailsService {

    private final UserRepository userRepository;

    public ApplicationUserDetailService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository
                .findUserByUsername(username)
                .map(this::map)
                .orElseThrow(() -> new UsernameNotFoundException("User with name " + username + " not found!"));
    }

    private UserDetails map(UserEntity userEntity){
        return new User(
                userEntity.getUsername(),
                userEntity.getPassword(),
                extractAuthorities(userEntity)
        );

    }

    private List<GrantedAuthority> extractAuthorities(UserEntity userEntity) {
        return userEntity
                .getRoles()
                .stream()
                .map(this::mapRole)
                .collect(Collectors.toList());
    }

    private GrantedAuthority mapRole(RoleEntity roleEntity){
        return new SimpleGrantedAuthority("ROLE_" + roleEntity.getRole().name());
    }
}
