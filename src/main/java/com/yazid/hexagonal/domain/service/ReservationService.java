package com.yazid.hexagonal.domain.service;

import com.yazid.hexagonal.domain.model.Reservation;
import com.yazid.hexagonal.domain.model.State;
import com.yazid.hexagonal.domain.repository.ReservationRepository;
import lombok.AllArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@AllArgsConstructor
public class ReservationService implements IReservationServie {

    private final ReservationRepository reservationRepository;

    @Override
    public Mono<Reservation> getReservation(UUID idReservation) {
        return reservationRepository.getReservation(idReservation);
    }

    @Override
    public Mono<Reservation> createReservation(Reservation reservation) {
        return reservationRepository.createReservation(reservation);
    }

    @Override
    public Mono<Reservation> confirmeReservation(UUID idReservation) {
        Mono<Reservation> reservation = reservationRepository.getReservation(idReservation);
        return reservation.flatMap(res ->
        {
            res.setState(State.CONFIRMED);
            return reservationRepository.createReservation(res);
        });

    }

    @Override
    public Mono<Reservation> canceledReservation(UUID idReservation) {
        Mono<Reservation> reservation = reservationRepository.getReservation(idReservation);
        return reservation.flatMap(res ->
        {
            res.setState(State.CANCELED);
            return reservationRepository.createReservation(res);
        });
    }

    @Override
    public Flux<Reservation> getAllREservation(int page, int size) {
        return reservationRepository.getAllREservation(page, size);
    }
}
