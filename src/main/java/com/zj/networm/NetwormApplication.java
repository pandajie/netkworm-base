package com.zj.networm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class NetwormApplication {

    public static void main(String[] args) {
        SpringApplication.run(NetwormApplication.class, args);
    }

}
