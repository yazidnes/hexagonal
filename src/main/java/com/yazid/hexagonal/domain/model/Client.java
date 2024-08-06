package com.yazid.hexagonal.domain.model;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class Client {

    private UUID number;

    private String firstName;

    private String lastName;

    private String address;

    private String email;


}
