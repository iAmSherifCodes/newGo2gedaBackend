package com.go2geda.Go2GedaApp.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class VirtualAccountBankResponse {
    private String name;
    private Long id;
    private String slug;
}
