package net.slipp.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import net.slipp.domain.User;
import net.slipp.domain.UserRepository;

/**
 * Created by johngrib on 2017. 5. 18..
 */
@Controller
@RequestMapping("/users")
public class UserController {

    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserRepository userRepository;

    @PostMapping("")
    public String create(User user) {
        log.debug("create user : {}", user);
        userRepository.save(user);
        return "redirect:/users";
    }

}
