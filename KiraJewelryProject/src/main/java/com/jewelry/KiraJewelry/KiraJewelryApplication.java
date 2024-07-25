package com.jewelry.KiraJewelry;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class KiraJewelryApplication {

	public static void main(String[] args) {
		SpringApplication.run(KiraJewelryApplication.class, args);
	}

}
