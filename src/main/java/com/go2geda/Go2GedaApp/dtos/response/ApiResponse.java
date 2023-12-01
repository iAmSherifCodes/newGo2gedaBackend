package com.go2geda.Go2GedaApp.dtos.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class ApiResponse <T>{

        private T data;
}
