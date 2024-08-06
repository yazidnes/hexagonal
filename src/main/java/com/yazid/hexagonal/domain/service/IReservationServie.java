package com.yazid.hexagonal.domain.service;

import com.yazid.hexagonal.domain.model.Reservation;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface IReservationServie {

    Mono<Reservation> getReservation(UUID idReservation);

    Mono<Reservation> createReservation(Reservation reservation);

    Mono<Reservation> confirmeReservation(UUID idReservation);

    Mono<Reservation> canceledReservation(UUID idReservation);

    Flux<Reservation> getAllREservation(int page, int size);
}
