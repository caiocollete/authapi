package com.cc.authapi.application;

import com.cc.authapi.domain.User;
import com.cc.authapi.repository.IKeyRepository;
import com.cc.authapi.repository.IUserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class UserService {

    private final IUserRepository userRepository;
    private final IKeyRepository keyRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(IUserRepository userRepository, IKeyRepository keyRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.keyRepository = keyRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User findByEmail(String username) {
        return userRepository.findByEmail(username);
    }
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
    public ResponseEntity<Object> login(User userAttemptLogin) {
        User userDb = findByUsername(userAttemptLogin.getUsername()); // procura user pelo username na db
        if (userDb == null)
            return ResponseEntity.badRequest().body("Invalid username");
        if(passwordEncoder.matches(userAttemptLogin.getPassword(), userDb.getPassword())) {
            if(!userDb.getKey().isExpired()){
                userDb.setRequestDate(userAttemptLogin.getRequestDate());
                userRepository.save(userDb);
                return ResponseEntity.ok("Sucessfully logged in");
            }
            return ResponseEntity.badRequest().body("Your license was expired");
        }
        return ResponseEntity.badRequest().body("Your credential was incorrect");
    }

    public ResponseEntity<Object> register(User user) {
        if (user.getEmail() == null || user.getUsername() == null || user.getPassword() == null)
            return ResponseEntity.badRequest().body("Please complete all fields");

        if (user.getPassword().length() < 6)
            return ResponseEntity.badRequest().body("Password must be at least 6 characters");

        if (findByUsername(user.getUsername()) != null)
            return ResponseEntity.badRequest().body("Username already exists");

        if (findByEmail(user.getEmail()) != null)
            return ResponseEntity.badRequest().body("Email already exists");

        var optionalKey = keyRepository.findById(user.getKey().getId());

        if (optionalKey.isEmpty())
            return ResponseEntity.badRequest().body("Your key isn't valid");

        var key = optionalKey.get();

        if (key.isExpired())
            return ResponseEntity.badRequest().body("Your key is expired");

        if (key.isUse())
            return ResponseEntity.badRequest().body("Your key was already used");

        // Marca a key como usada
        key.setUse(true);
        keyRepository.save(key);

        // Associa a key ao usuário e salva
        user.setRequestDate(new Date());
        user.setKey(key);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);

        return ResponseEntity.ok("Successfully registered");
    }


    public User Save(User user) {
        return userRepository.save(user);
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

}
