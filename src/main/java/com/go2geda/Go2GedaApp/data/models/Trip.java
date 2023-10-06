package com.go2geda.Go2GedaApp.data.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Setter
@Getter
@Entity
@Data
public class Trip {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Enumerated(EnumType.STRING)
    private TripStatus tripStatus;
    private String pickup;
    private String destination;
    private int pricePerSeat;
    private int numberOfSeatsAvailable;
    private LocalDateTime pickUpTime;
    private LocalDateTime endTime;
    private LocalDateTime startTime;
    @ManyToOne
    private Driver driver;
    @OneToMany(fetch = FetchType.EAGER)
    private List<Commuter> commuter;
    @OneToOne
    private Group groupChat;
}
