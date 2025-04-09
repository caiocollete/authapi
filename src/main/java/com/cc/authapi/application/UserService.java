package com.cc.authapi.application;

import com.cc.authapi.domain.ApiResponse;
import com.cc.authapi.domain.Key;
import com.cc.authapi.domain.User;
import com.cc.authapi.dtos.UserWithKeyDTO;
import com.cc.authapi.repository.IKeyRepository;
import com.cc.authapi.repository.IUserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

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
    public ApiResponse<UserWithKeyDTO> login(User userAttemptLogin) {
        User userDb = findByUsername(userAttemptLogin.getUsername()); // procura user pelo username na db
        if (userDb == null)
            return new ApiResponse<>(false,"Invalid username",null);
        if (passwordEncoder.matches(userAttemptLogin.getPassword(), userDb.getPassword())) {
            if (!userDb.getKey().isExpired()) {
                userDb.setLastRequestDate(new Date());
                userRepository.save(userDb);

                return new ApiResponse<>(true, "Successfully logged in", DtosMappers.toUserWithKeyDTO(userDb));
            }
            return new ApiResponse<>(false, "Your license was expired", null);
        }

        return new ApiResponse<>(false,"Your credentials were incorrect",null);
    }

    private ApiResponse<User> validateRegister(User user) {
        if (user.getEmail() == null || user.getUsername() == null || user.getPassword() == null)
            return new ApiResponse<>(false,"Please complete all fields", null);

        if (user.getPassword().length() < 6)
            return new ApiResponse<>(false,"Password must be at least 6 characters",null);

        if (findByUsername(user.getUsername()) != null)
            return new ApiResponse<>(false,"Username already exists",null);

        if (findByEmail(user.getEmail()) != null)
            return new ApiResponse<>(false,"Email already exists",null);

        return new ApiResponse<>(true, "Successfully logged in", null);
    }

    private ApiResponse<Key> validateKey(Optional<Key> optionalKey) {
        if (optionalKey.isEmpty())
            return new ApiResponse<>(false,"Your key isn't valid",null);
        var key = optionalKey.get();
        if (key.isExpired())
            return new ApiResponse<>(false,"Your key is expired",null);
        if (key.isUse())
            return new ApiResponse<>(false,"Your key was already used",null);

        return new ApiResponse<>(true,"Your key was valid",key);
    }

    public ApiResponse<UserWithKeyDTO> register(User user) {
        ApiResponse<User> validation = validateRegister(user);

        if(!validation.isSuccess())
            return new ApiResponse<>(validation.isSuccess(), validation.getMessage(), null);

        var optionalKey = keyRepository.findById(user.getKey().getId());
        var keyValidation = validateKey(optionalKey);
        if (!keyValidation.isSuccess())
            return new ApiResponse<>(false, keyValidation.getMessage(), null);

        var key = keyValidation.getData();

        // Marca a key como usada
        key.setUse(true);
        keyRepository.save(key);

        // Associa a key ao usuário e salva
        user.setLastRequestDate(new Date());
        user.setKey(key);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);

        return new ApiResponse<>(true,"Successfully registered", DtosMappers.toUserWithKeyDTO(user));
    }


    public User Save(User user) {
        return userRepository.save(user);
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public ApiResponse<List<UserWithKeyDTO>> getUsers() {
        List<User> response = findAll();

        if(response.isEmpty())
            return new ApiResponse<>(false,"No users found",null);

        List<UserWithKeyDTO> userList = new ArrayList<>();
        for(User user : response) {
            userList.add(DtosMappers.toUserWithKeyDTO(user));
        }
        return new ApiResponse<>(true,"Users found",userList);
    }
}
