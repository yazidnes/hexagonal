package com.yazid.hexagonal.infrastructure.mongo;

import com.yazid.hexagonal.domain.model.Reservation;
import com.yazid.hexagonal.domain.repository.ReservationRepository;
import com.yazid.hexagonal.infrastructure.mongo.repository.ReservationMongoRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Component
@AllArgsConstructor
public class MongoReservationRepository  implements ReservationRepository {

    private final ReservationMongoRepository reservationMongoRepository;

    @Override
    public Mono<Reservation> getReservation(UUID idReservation) {
        return reservationMongoRepository.findById(idReservation);
    }

    @Override
    public Mono<Reservation> createReservation(Reservation reservation) {
        return reservationMongoRepository.save(reservation);
    }

    @Override
    public Flux<Reservation> getAllREservation(int page, int size) {
        return reservationMongoRepository.findAllBy(PageRequest.of(page,size));
    }
}
