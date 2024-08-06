package com.yazid.hexagonal.application.web;

import com.yazid.hexagonal.domain.model.Client;
import com.yazid.hexagonal.domain.model.Passenger;
import com.yazid.hexagonal.domain.model.Reservation;
import com.yazid.hexagonal.infrastructure.mongo.repository.ReservationMongoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.devtools.restart.RestartScope;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Testcontainers;

import org.testcontainers.utility.DockerImageName;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.UUID;

@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
public class ReservationControllerTest {


    @ServiceConnection
    MongoDBContainer mongoDbContainer() {
        return new MongoDBContainer(DockerImageName.parse("mongo:4.4.6"));
    }

    @Autowired
    @SpyBean
    private ReservationMongoRepository reservationRepository;
    Reservation reservation;
    UUID uuid;

    @Autowired
    private WebTestClient webTestClient;



    @BeforeEach
    void setUp() {
        uuid = UUID.fromString("87045f12-1d6a-49ce-8f14-43f267dbe308");
        Reservation reservation1 = reservationRepository.save(Reservation.builder().id(uuid).
                client(Client.builder().email("james.bond@email.com").number(uuid).firstName("James").lastName("bond").address("").build()).
                passenger(Passenger.builder().FirstName("James").lastName("bond").build()).reservationDate(LocalDateTime.now()).build()).block();

    }


    @Test
    public void testRecupererReservation() {

        Mono<Long> count = reservationRepository.count();
        webTestClient.get().uri("/reservation/87045f12-1d6a-49ce-8f14-43f267dbe308", Collections.singletonMap("id", uuid))
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .consumeWith(System.out::println)
                .jsonPath("id").isEqualTo(uuid.toString());
        Mockito.verify(reservationRepository, Mockito.times(1)).findById(uuid);




    }

//    @Test
//    public void testConfirmerReservation() {
//        webTestClient.post().uri("/reservation")
//                .contentType(MediaType.APPLICATION_JSON)
//                .accept(MediaType.APPLICATION_JSON)
//                .body(Mono.just(reservation), Reservation.class)
//                .exchange()
//                .expectStatus().isOk()
//                .expectBody()
//                .consumeWith(System.out::println)
//                .jsonPath("id").isEqualTo(uuid.toString());
//    }
//
//    @Test
//    public void testAnnulerReservation() {
//        webTestClient.put().uri("/reservation")
//                .contentType(MediaType.APPLICATION_JSON)
//                .accept(MediaType.APPLICATION_JSON)
//                .body(Mono.just(reservation), Reservation.class)
//                .exchange()
//                .expectStatus().isOk()
//                .expectBody()
//                .consumeWith(System.out::println);
//    }
//
//    @Test
//    public void should_not_confirmer_apres_annuler() {
//        //reservation.updateStatus(StatusReservation.ANNULLE);
//        webTestClient.post().uri("/reservation")
//                .contentType(MediaType.APPLICATION_JSON)
//                .accept(MediaType.APPLICATION_JSON)
//                .body(Mono.just(reservation), Reservation.class)
//                .exchange()
//                .expectStatus().isEqualTo(400)
//                .expectBody()
//                .consumeWith(System.out::println).jsonPath("detail").isEqualTo("La reservation est annullé, on ne peut pas la reouvrir");
//        ;
//    }
}
