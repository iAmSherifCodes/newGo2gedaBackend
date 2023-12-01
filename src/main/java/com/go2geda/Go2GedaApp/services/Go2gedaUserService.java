package com.go2geda.Go2GedaApp.services;

import com.go2geda.Go2GedaApp.data.models.User;
import com.go2geda.Go2GedaApp.exceptions.UserDoesNotExist;
import com.go2geda.Go2GedaApp.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.go2geda.Go2GedaApp.exceptions.ExceptionMessage.USER_NOT_FOUND;

@Service @AllArgsConstructor
public class Go2gedaUserService implements UserService{

    private final UserRepository userRepository;

    @Override
    public User findUserByEmail(String email) {
        User user = userRepository
                    .findByEmail(email)
                    .orElseThrow(()-> new UserDoesNotExist(USER_NOT_FOUND.name()));
        return user;
    }
}
