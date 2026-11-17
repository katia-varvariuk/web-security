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
    CommandLineRunner initDatabase(UserRepository repo, PasswordEncoder encoder) {
        return args -> {
            createIfMissing(repo, encoder, "John", "Lennon", "john@mail.com", Role.USER);
            createIfMissing(repo, encoder, "Anna", "Adminova", "anna@mail.com", Role.ADMIN);
            createIfMissing(repo, encoder, "Peter", "Super", "peter@mail.com", Role.SUPERADMIN);
        };
    }

    private void createIfMissing(UserRepository repo, PasswordEncoder encoder,
                                 String first, String last, String email, Role role) {
        if (repo.findByEmail(email).isEmpty()) {
            repo.save(User.builder()
                    .firstName(first)
                    .lastName(last)
                    .email(email)
                    .password(encoder.encode("password"))
                    .role(role)
                    .build());
            System.out.println(">>> Створено користувача " + email + " (" + role + ") <<<");
        }
    }
}