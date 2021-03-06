package pl.grzegorzchmaj.easydiet;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
public class EasydietApplication {

    public static void main(String[] args) {
        SpringApplication.run(EasydietApplication.class, args);
    }
}
