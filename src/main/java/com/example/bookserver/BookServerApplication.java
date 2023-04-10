package com.example.bookserver;

import com.example.bookserver.model.ERole;
import com.example.bookserver.model.Role;
import com.example.bookserver.model.User;
import com.example.bookserver.repository.UserRepository;
import com.example.bookserver.service.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.HashSet;
import java.util.Set;

@SpringBootApplication
public class BookServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(BookServerApplication.class, args);
    }

}
