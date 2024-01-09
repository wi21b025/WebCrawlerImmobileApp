package web.controller;

import jakarta.servlet.http.HttpSession;
import model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import service.AuthenticationService;
import service.UserService;

@Controller
public class AuthController
{

    private final UserService userService;
    private final AuthenticationService authenticationService;
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public AuthController(UserService userService, AuthenticationService authenticationService, BCryptPasswordEncoder passwordEncoder)
    {
        this.userService = userService;
        this.authenticationService = authenticationService;
        this.passwordEncoder= passwordEncoder;
    }


    @GetMapping("/auth")
    public String authForm()
    {
        return "web-view/auth";
    }


    @PostMapping("/login")
    public String login(@RequestParam String usernameOrEmail, @RequestParam String password, HttpSession session, Model model, RedirectAttributes redirectAttributes)
    {
        if (authenticationService.isLoginSuccessful(usernameOrEmail, password))
        {

            User user = userService.findByUsernameOrEmail(usernameOrEmail); // Retrieve the user

            if (user != null)
            {
                session.setAttribute("username", user.getUsername());  // Store username in session
                session.setAttribute("email", user.getEmail());        // Store email in session
                session.setAttribute("user", true);

                return "redirect:/profil";
            }
            else
            {
                // Handle case where user is not found
                redirectAttributes.addFlashAttribute("error", "Benutzer nicht gefunden. Bitte überprüfen Sie Ihre Anmeldedaten.");
                return "redirect:/auth";
            }
         }
        else
        {
            //model.addAttribute("error", "Benutzer-Anmeldung nicht erfolgreich.");
            redirectAttributes.addFlashAttribute("error", "Benutzer-Anmeldung nicht erfolgreich.");

            return "redirect:/auth";
        }
    }

    @RequestMapping(value = "/logout", method = RequestMethod.POST)
    public String logout(HttpSession session)
    {
        session.invalidate();
        return "redirect:/auth";
    }



    @PostMapping("/signup")
    public String signup(
            @RequestParam String username,
            @RequestParam String email,
            @RequestParam String password,
            Model model,
            RedirectAttributes redirectAttributes) {
        // Create a new User object and set its properties
        User user = new User();
        user.setUsername(username);
        user.setEmail(email);

        // Hash the password before saving it
        String hashedPassword = passwordEncoder.encode(password);
        user.setPassword(hashedPassword);

        boolean saved = userService.saveUser(user);

        if (saved) {
            //model.addAttribute("success", "Benutzer-Registrierung war erfolgreich.");
            redirectAttributes.addFlashAttribute("success", "Benutzer-Registrierung war erfolgreich.");
        } else {
            //model.addAttribute("error", "Benutzer-Registrierung war nicht erfolgreich.");
            redirectAttributes.addFlashAttribute("error", "Benutzer-Registrierung nicht erfolgreich.");
        }

        return "redirect:/auth"; // Return the same view with the error message
    }
}