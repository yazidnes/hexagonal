package com.yazid.hexagonal.domain.service;

import com.yazid.hexagonal.domain.model.Client;
import com.yazid.hexagonal.domain.model.Passenger;
import com.yazid.hexagonal.domain.model.Reservation;
import com.yazid.hexagonal.domain.model.State;
import com.yazid.hexagonal.domain.repository.ReservationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.LocalDateTime;
import java.util.UUID;

public class ReservationServiceTest {

    private ReservationRepository reservationRepository;

    private ReservationService reservationService;

    private Reservation reservation;

    private Client client;


    private Passenger passenger;

    private UUID clientId;

    private UUID reservationId;

    @BeforeEach
    void setUp() {
        reservationRepository = Mockito.mock(ReservationRepository.class);
        reservationService = new ReservationService(reservationRepository);
        passenger = Passenger.builder().FirstName("James").lastName("bond").build();
        clientId = UUID.randomUUID();
        reservationId = UUID.randomUUID();
        client = Client.builder().email("james.bond@email.com").address("25 Wellington Square,London").
                number(clientId).firstName("James").lastName("Bond").build();
        reservation = Reservation.builder().reservationDate(LocalDateTime.now()).id(reservationId).client(client).passenger(passenger).build();
    }

    @Test
    public void should_getReservation() {
        Mockito.when(reservationRepository.getReservation(reservationId)).thenReturn(Mono.just(reservation));
        Mono<Reservation> reservationMono = reservationService.getReservation(reservationId);
        StepVerifier.create(reservationMono).expectNextMatches(result -> result.getId().equals(reservationId)).expectComplete().verify();
    }

    @Test
    public void should_createReservation() {
        Mockito.when(reservationRepository.createReservation(reservation)).thenReturn(Mono.just(reservation));
        Mono<Reservation> reservationMono = reservationService.createReservation(reservation);
        StepVerifier.create(reservationMono).expectNextMatches(result -> result.getId().equals(reservationId)).expectComplete().verify();
    }

    @Test
    public void should_confirmeReservation() {
        reservation.setState(State.CONFIRMED);
        Mockito.when(reservationRepository.getReservation(reservationId)).thenReturn(Mono.just(reservation));
        Mockito.when(reservationRepository.createReservation(reservation)).thenReturn(Mono.just(reservation));
        Mono<Reservation> reservationMono = reservationService.confirmeReservation(reservationId);
        StepVerifier.create(reservationMono).expectNextMatches(result -> result.getId().equals(reservationId) && State.CONFIRMED.equals(result.getReservationState())).expectComplete().verify();
    }

    @Test
    public void should_not_confirmeReservation() {
        reservation.setState(State.CANCELED

        );
        Mockito.when(reservationRepository.getReservation(reservationId)).thenReturn(Mono.just(reservation));
        Mono<Reservation> reservationMono = reservationService.confirmeReservation(reservationId);
        StepVerifier.create(reservationMono).expectErrorMatches((throwable -> throwable instanceof IllegalArgumentException && throwable.getMessage().equals("A canceled reservation cannot change its status"))).verify();
    }


    @Test
    public void should_canceledReservation() {
        reservation.setState(State.CANCELED);
        Mockito.when(reservationRepository.getReservation(reservationId)).thenReturn(Mono.just(reservation));
        Mockito.when(reservationRepository.createReservation(reservation)).thenReturn(Mono.just(reservation));
        Mono<Reservation> reservationMono = reservationService.canceledReservation(reservationId);
        StepVerifier.create(reservationMono).expectNextMatches(result -> result.getId().equals(reservationId) && State.CANCELED.equals(result.getReservationState())).expectComplete().verify();
    }


    @Test
    public void should_getAllREservation() {
        Mockito.when(reservationRepository.getAllREservation(0,10)).thenReturn(Flux.just(reservation,reservation));
        Flux<Reservation> reservationMono = reservationService.getAllREservation(0,10);
        StepVerifier.create(reservationMono).expectNextMatches(result -> result.getId().equals(reservationId)).expectNextMatches(result -> result.getId().equals(reservationId)).expectComplete().verify();
    }
}
