package com.github.aguilasa.fiis;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class FiisApplication {

  public static void main(String[] args) {
    SpringApplication.run(FiisApplication.class, args);
  }
}
