package com.go2geda.Go2GedaApp.data.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
public class Notification {
    @Id
    @GeneratedValue
    private Long id;
    private Long senderId;
    private Long receiverId;
    private String messageContent;
}
