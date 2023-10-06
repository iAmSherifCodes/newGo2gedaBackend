package com.go2geda.Go2GedaApp.dtos.response;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class VirtualAccountResponse {
    private String status;
   private String message;
    private VirtualAccountDataResponse data;

}
