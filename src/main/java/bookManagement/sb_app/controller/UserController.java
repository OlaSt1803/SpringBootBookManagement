package bookManagement.sb_app.controller;

import bookManagement.sb_app.Dto.UserDto;
import bookManagement.sb_app.entity.User;
import bookManagement.sb_app.service.CustomAuthenticationService;
import bookManagement.sb_app.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private CustomAuthenticationService authenticationService;

    @GetMapping("/login")
    public String showLoginPage(Model model, @RequestParam(value = "error", required = false) String error) {
        model.addAttribute("error", error);
        model.addAttribute("user", new User());
        return "login";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute("user") User user, Model model) {
        boolean authenticated = authenticationService.authenticate(user.getUsername(), user.getPassword());
        if (authenticated) {
            return "redirect:/books";
        } else {
            model.addAttribute("error", "Invalid username or password.");
            return "login";
        }
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new UserDto());
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute("user") UserDto userDto, Model model) {
        if (!userService.isUsernameUnique(userDto.getUsername())) {
            model.addAttribute("error", "Username already exists. Please choose a different one.");
            return "register";
        }

        userService.addUser(userDto);
        return "redirect:/login";
    }

    @RequestMapping(value = {"/logout"}, method = RequestMethod.GET)
    public String logoutDo(HttpServletRequest request, HttpServletResponse response) {
        return "redirect:/login";
    }
}



