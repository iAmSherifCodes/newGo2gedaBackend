package com.go2geda.Go2GedaApp.dtos.request;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;
@Getter
@Setter
public class DriverLicenceVerificationRequest {
    private MultipartFile frontPicture;
    private MultipartFile backPicture;
}
