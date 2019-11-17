package ch.alni.userlist.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

import ch.alni.userlist.rest.RestConfig;
import ch.alni.userlist.service.AdminServiceConfig;

/**
 * The entry point for Spring Boot.
 */
@SpringBootApplication
@Import({
        RestConfig.class,
        AdminServiceConfig.class
})
public class UserListApplication {
    public static void main(String[] args) {
        SpringApplication.run(UserListApplication.class, args);
    }
}
