package net.slipp.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import net.slipp.domain.User;
import net.slipp.domain.UserRepository;

import java.util.List;

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

    @GetMapping("")
    public String list(final Model model) {
        final List<User> users = userRepository.findAll();
        model.addAttribute("users", users);
        return "/user/list";
    }

    @GetMapping("/{id}/form")
    public String updateForm(@PathVariable long id, Model model) {
        model.addAttribute("user", userRepository.findOne(id));
        return "/user/updateForm";
    }

    @GetMapping("/form")
    public String form() {
        return "/user/form";
    }

    @PutMapping("/{id}")
    public String update(@PathVariable long id, User target) {
        User original = userRepository.findOne(id);
        original.update(target);
        return "redirect:/users";
    }

}
