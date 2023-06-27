package com.quetzalcode.items;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.FeignAutoConfiguration;

@EnableFeignClients
@ImportAutoConfiguration({FeignAutoConfiguration.class})
@SpringBootApplication
@EntityScan({"com.quetzalcode.commons.entity"})
public class MicroservicioItemApplication {

	public static void main(String[] args) {
		SpringApplication.run(MicroservicioItemApplication.class, args);
	}

}
