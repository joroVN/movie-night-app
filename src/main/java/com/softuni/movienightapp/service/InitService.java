package com.softuni.movienightapp.service;

import com.softuni.movienightapp.model.entity.RoleEntity;
import com.softuni.movienightapp.model.entity.UserEntity;
import com.softuni.movienightapp.model.enums.RoleNames;
import com.softuni.movienightapp.repository.RoleRepository;
import com.softuni.movienightapp.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class InitService {

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public InitService(RoleRepository roleRepository,
                       UserRepository userRepository,
                       PasswordEncoder passwordEncoder,
                       @Value("${app.default.password}") String defaultPassword) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostConstruct
    private void initUsers() {
        if (this.userRepository.count() == 0) {
            initRoles();
            initAdmin();
            initModerator();
            initNormalUser();
        }
    }

    private void initRoles() {
        if (this.roleRepository.count() == 0) {
            List<RoleEntity> roles = Arrays.stream(RoleNames.values()).map(r -> new RoleEntity().setRole(r)).collect(Collectors.toList());
            this.roleRepository.saveAll(roles);
        }
    }

    private void initAdmin() {
        UserEntity adminUser = new UserEntity()
                .setUsername("adminov")
                .setEmail("adminov@move.com")
                .setPassword(passwordEncoder.encode("1234"))
                .setRoles(roleRepository.findAll());
        userRepository.save(adminUser);
    }

    private void initModerator() {

        RoleEntity role = roleRepository.findRoleEntityByRole(RoleNames.MODERATOR).get();

        UserEntity moderatorUser = new UserEntity()
                .setUsername("moderatov")
                .setEmail("moderatov@move.com")
                .setPassword(passwordEncoder.encode("1234"))
                .setRoles(List.of(role));
        userRepository.save(moderatorUser);
    }

    private void initNormalUser() {

        RoleEntity role = roleRepository.findRoleEntityByRole(RoleNames.USER).get();

        UserEntity normalUser = new UserEntity()
                .setUsername("userov")
                .setEmail("userov@move.com")
                .setPassword(passwordEncoder.encode("1234"))
                .setRoles(List.of(role));
        userRepository.save(normalUser);

    }

}
