package com.yazid.hexagonal.domain.model;

import lombok.Builder;

import java.util.UUID;

@Builder
public record Passenger(String FirstName, String lastName, UUID id) {
}
