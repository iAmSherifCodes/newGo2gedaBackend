package com.go2geda.Go2GedaApp.exceptions;

import com.go2geda.Go2GedaApp.dtos.response.BaseResponse;

public class UserAlreadyExist extends Go2gedaBaseException {
    public UserAlreadyExist(String message) {
        super(message);
    }
}
