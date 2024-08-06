package com.yazid.hexagonal.infrastructure;

import com.yazid.hexagonal.HexagonalApplication;
import com.yazid.hexagonal.domain.repository.ReservationRepository;
import com.yazid.hexagonal.domain.service.ReservationService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackageClasses = HexagonalApplication.class)
public class BeanConfiguration {

    @Bean
    ReservationService reservationService(@Qualifier("mongoReservationRepository") final ReservationRepository reservationRepository) {
        return new ReservationService(reservationRepository);
    }
}
