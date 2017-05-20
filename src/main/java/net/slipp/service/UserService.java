package net.slipp.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import net.slipp.domain.User;
import net.slipp.domain.UserRepository;

/**
 * Created by johngrib on 2017. 5. 20..
 */
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User login(String userId, String password) {
        Optional<User> maybeUser = userRepository.findByUserId(userId);
        if (!maybeUser.isPresent()) {
            throw new IllegalStateException();
        }

        User user = maybeUser.get();
        if (!user.matchPassword(password)) {
            throw new IllegalStateException();
        }

        return user;
    }
}
