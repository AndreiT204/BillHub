package com.billhub;

import com.billhub.model.User;
import com.billhub.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class BillHubApplication {

    public static void main(String[] args) {
        SpringApplication.run(BillHubApplication.class, args);
    }

    @Bean
    CommandLineRunner init(UserRepository userRepository) {
        return args -> {
            createProviderIfNotFound(userRepository, "admin", "admin");
            createProviderIfNotFound(userRepository, "Enel", "Enel");
            createProviderIfNotFound(userRepository, "Apa Nova", "Apa Nova");
            createProviderIfNotFound(userRepository, "Digi", "Digi");

            createProviderIfNotFound(userRepository, "user", "user  ");
        };
    }

    private void createProviderIfNotFound(UserRepository repo, String name, String pass) {
        if (repo.findByUsername(name).isEmpty()) {
            User provider = new User(name, pass, "PROVIDER");
            repo.save(provider);
            System.out.println("Pre-loaded User: " + name);
        }
    }
}