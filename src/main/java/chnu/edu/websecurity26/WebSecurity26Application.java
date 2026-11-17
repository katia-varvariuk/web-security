package chnu.edu.websecurity26;

import chnu.edu.websecurity26.user.Role;
import chnu.edu.websecurity26.user.User;
import chnu.edu.websecurity26.user.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class WebSecurity26Application {

    public static void main(String[] args) {
        SpringApplication.run(WebSecurity26Application.class, args);
    }

    @Bean
    CommandLineRunner initDatabase(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            if (userRepository.findByEmail("john@mail.com").isEmpty()) {
                User user = User.builder()
                        .firstName("John")
                        .lastName("Lennon")
                        .email("john@mail.com")
                        .password(passwordEncoder.encode("password"))
                        .role(Role.USER)
                        .build();
                userRepository.save(user);
                System.out.println(">>> Тестовий користувач john@mail.com створений у базі! <<<");
            }
        };
    }
}