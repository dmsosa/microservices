package com.duvi.services.noti;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//Notification Dienstleistungen die request zu Authserver senden werden, um die Email unseres Konto zu erhalten.
@SpringBootApplication
public class NotiApplication {

	public static void main(String[] args) {
		SpringApplication.run(NotiApplication.class, args);
	}

}
