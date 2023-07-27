package com.quetzalcode.oauth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@EnableEurekaClient
//@EnableAutoConfiguration(exclude = {DataSourceAutoConfiguration.class})//Sirve para no autoconfigurar BDD si ocupas JPA pero sin BDD
@EnableFeignClients
@SpringBootApplication
//public class MicroservicioOauthApplication implements CommandLineRunner {//Se implementa CommandLineRunner para ejecutar algo en el arranque
public class MicroservicioOauthApplication {//Se implementa CommandLineRunner para ejecutar algo en el arranque

//	@Autowired
//	private BCryptPasswordEncoder passwordEncoder;

	public static void main(String[] args) {
		SpringApplication.run(MicroservicioOauthApplication.class, args);
	}

//	@Override
//	public void run(String... args) throws Exception {
//		String password = "12345";
//		for (int i=0; i < 4;i++){
//			String passwordBCrypt = passwordEncoder.encode(password);
//			System.out.println(passwordBCrypt);
//		}
//	}
}
