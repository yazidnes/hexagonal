package com.yazid.hexagonal.domain.repository;

import com.yazid.hexagonal.domain.model.Reservation;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface ReservationRepository {

    Mono<Reservation> getReservation(UUID idReservation);

    Mono<Reservation> createReservation(Reservation reservation);

    Flux<Reservation> getAllREservation(int page, int size);
}
