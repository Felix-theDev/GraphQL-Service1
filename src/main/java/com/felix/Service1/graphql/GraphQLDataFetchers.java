package com.felix.Service1.graphql;

import com.felix.Service1.entity.User;
import com.felix.Service1.repository.UserRepository;
import graphql.schema.DataFetcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class GraphQLDataFetchers {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public DataFetcher<User> getUserDataFetcher() {
        return environment -> {
            String username = environment.getArgument("username");
            return userRepository.findByUsername(username).orElse(null);
        };
    }

    public DataFetcher<User> signUpDataFetcher() {
        return environment -> {
            String username = environment.getArgument("username");
            String email = environment.getArgument("email");
            String password = environment.getArgument("password");

            // Check if the username or email is already taken (You can add validation logic here)

            User user = new User();
            user.setUsername(username);
            user.setEmail(email);
            user.setPassword(passwordEncoder.encode(password)); // Encrypt the password before saving

            return userRepository.save(user);
        };
    }

    public DataFetcher<String> loginDataFetcher() {
        return environment -> {
            String username = environment.getArgument("username");
            String password = environment.getArgument("password");

            // Load the user from the database by username
            Optional<User> optionalUser = userRepository.findByUsername(username);

            if (optionalUser.isPresent() && passwordEncoder.matches(password, optionalUser.get().getPassword())) {
                // Passwords match, return a token or success message here
                return "Login successful";
            } else {
                // Invalid credentials, handle the error
                throw new IllegalArgumentException("Invalid username or password");
            }
        };
    }
}
