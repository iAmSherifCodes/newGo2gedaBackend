package com.go2geda.Go2GedaApp.services;

import org.springframework.web.multipart.MultipartFile;

public interface CloudService {
    String upload(MultipartFile file, String cloudinaryFolder);
}
