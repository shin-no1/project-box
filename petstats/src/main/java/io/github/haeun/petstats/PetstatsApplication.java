package io.github.haeun.petstats;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@SpringBootApplication
public class PetstatsApplication {

    public static void main(String[] args) {
        SpringApplication.run(PetstatsApplication.class, args);
    }

}
