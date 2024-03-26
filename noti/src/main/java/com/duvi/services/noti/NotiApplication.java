package com.duvi.services.noti;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

//Notification Dienstleistungen die request zu Authserver senden werden, um die Email unseres Konto zu erhalten.
@SpringBootApplication
@EnableFeignClients
@EnableDiscoveryClient
public class NotiApplication {

	public static void main(String[] args) {
		SpringApplication.run(NotiApplication.class, args);
	}

}
