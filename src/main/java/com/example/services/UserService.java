package com.example.services;

import com.example.entities.User;
import com.example.exception.ValidationException;
import com.example.repository.RoleRepository;
import com.example.repository.UserRepository;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import javax.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public UserService(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @PostConstruct
    public void init() {
       /* User user = new User();
        user.setUsername("user");
        user.setPassword(new BCryptPasswordEncoder().encode("123"));
        user.setRole(roleRepository.findByName("ADMIN"));
        userRepository.save(user);*/
    }

    public List<User> getAllUsers() {
        List<User> users = userRepository.findAll();
        for (User user : users) {
            user.setPassword(null);
        }
        return users;
    }

    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public User saveNewUser(User user) {
        if (findByUsername(user.getUsername()).isPresent()) {
            throw new ValidationException("User with this username is already exist!");
        }
        user.setRole(roleRepository.findByName("USER").orElseThrow(() ->
                new NoSuchElementException("Role with name USER doesn't exist!")));
        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        User currentUser = userRepository.save(user);
        currentUser.setPassword(null);
        return currentUser;
    }

    public User update(User user) {
        Optional<User> userFromDb = userRepository.findById(user.getId());
        if (userFromDb.isPresent()) {
            user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
            User currentUser = userRepository.save(user);
            currentUser.setPassword(null);
            return currentUser;
        }
        throw new ValidationException("Can't update user! This user doesn't exist!");
    }

    public void delete(Long userId) {
        userRepository.deleteById(userId);
    }

    public User getUserById(Long userId) {
        User currentUser = userRepository.getById(userId);
        currentUser.setPassword(null);
        return currentUser;
    }

    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }
}
