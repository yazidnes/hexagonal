package com.yazid.hexagonal;

import org.springframework.boot.SpringApplication;

public class TestHexagonalApplication {

	public static void main(String[] args) {
		SpringApplication.from(HexagonalApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
