package com.yazid.hexagonal.application.web;

import com.yazid.hexagonal.domain.model.Reservation;
import com.yazid.hexagonal.domain.service.IReservationServie;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/reservation")
@AllArgsConstructor
@OpenAPIDefinition(info=@Info(title="Reservation API", version = "1.0.0"))
public class ReservationController {

    private final IReservationServie reservationServie;

    @GetMapping("/{id}")
    Mono<Reservation> getReservation(@PathVariable("id") UUID id){
        return reservationServie.getReservation(id);
    }


    @PostMapping
    Mono<Reservation> createReservation(@RequestBody Reservation reservation){
        return reservationServie.createReservation(reservation);
    }

    @PostMapping("/confirm/{id}")
    Mono<Reservation> confirmeReservation(@PathVariable("id") UUID idReservation){
        return reservationServie.confirmeReservation(idReservation);
    }

    @PostMapping("/cancel/{id}")
    Mono<Reservation> canceledReservation(@PathVariable("id") UUID idReservation){
        return reservationServie.canceledReservation(idReservation);
    }

    @GetMapping
    Flux<Reservation> getAllREservation(int page, int size){
        return reservationServie.getAllREservation(page,size);
    }
}
