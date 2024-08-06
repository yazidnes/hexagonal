package com.yazid.hexagonal.domain.model;

import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;



public class Reservation {

    @Builder
    public Reservation(UUID id, LocalDateTime reservationDate, Client client, Passenger passenger) {
        this.id = id;
        this.reservationDate = reservationDate;
        this.client = client;
        this.passenger = passenger;
    }

    @Getter
    private UUID id;

    @Getter
    @Setter
    private LocalDateTime reservationDate;

    @Getter
    private State reservationState = State.IN_PROGRESS;

    @Getter
    private Client client;

    @Getter
    private Passenger passenger;

    public void setState(State state) {
        if (State.CANCELED.equals(reservationState) && !State.CANCELED.equals(state)) {
            throw new IllegalArgumentException("A canceled reservation cannot change its status");
        }
        this.reservationState = state;
    }
}
