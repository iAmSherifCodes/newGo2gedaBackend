package com.go2geda.Go2GedaApp.services;

import com.go2geda.Go2GedaApp.data.models.User;

public interface UserService {
    User findUserByEmail(String email);
}
