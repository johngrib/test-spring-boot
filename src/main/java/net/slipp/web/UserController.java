package net.slipp.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by johngrib on 2017. 5. 18..
 */
@Controller
public class UserController {

    @RequestMapping(path = "/users")
    public String users() {

        return "test";
    }
}
