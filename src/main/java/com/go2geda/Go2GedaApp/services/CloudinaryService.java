package com.go2geda.Go2GedaApp.services;

import com.go2geda.Go2GedaApp.configs.AppConfig;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;
import com.cloudinary.Cloudinary;
import com.cloudinary.Uploader;

@AllArgsConstructor
@Service
public class CloudinaryService implements CloudService{
    private final AppConfig appConfig;

    @Override
    public String upload(MultipartFile file, String cloudinaryFolder) {
        Cloudinary cloudinary = new Cloudinary();
        Uploader uploader = cloudinary.uploader();

        try {
            Map<?,?> response = uploader.upload(file.getBytes(), ObjectUtils.asMap(
                    "public_id","go2geda/assets/" + cloudinaryFolder +"/" +file.getName(),
                    "api_key",appConfig.getCloudApiKey(),
                    "api_secret",appConfig.getCloudSecret(),
                    "cloud_name",appConfig.getCloudName(),
                    "secure",true,
                    "resource_type", "auto"
            ));

            return response.get("url").toString();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
