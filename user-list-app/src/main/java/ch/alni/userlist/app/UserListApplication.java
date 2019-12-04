package ch.alni.userlist.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * The entry point for Spring Boot.
 */
@SpringBootApplication(scanBasePackages = "ch.alni.userlist")
public class UserListApplication {
    public static void main(String[] args) {
        SpringApplication.run(UserListApplication.class, args);
    }
}
