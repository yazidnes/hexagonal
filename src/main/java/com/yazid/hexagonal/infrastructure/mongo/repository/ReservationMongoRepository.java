package com.yazid.hexagonal.infrastructure.mongo.repository;

import com.yazid.hexagonal.domain.model.Reservation;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.data.repository.reactive.ReactiveSortingRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

import java.util.UUID;

@Repository
public interface ReservationMongoRepository extends ReactiveMongoRepository<Reservation, UUID>, ReactiveSortingRepository<Reservation, UUID> {


    Flux<Reservation> findAllBy(Pageable pageable);
}
