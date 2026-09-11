package com.makers.gestionprestamos.config;

import com.makers.gestionprestamos.entity.User;
import com.makers.gestionprestamos.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataSeeder implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        if (userRepository.findByEmail("master@makers.com").isEmpty()) {
            User master = new User();
            master.setDocumentId("MASTER-001");
            master.setName("Master Admin");
            master.setEmail("master@makers.com");
            master.setPassword(passwordEncoder.encode("master123"));
            master.setRole(User.Role.MASTER);
            userRepository.save(master);
        }
    }
}
