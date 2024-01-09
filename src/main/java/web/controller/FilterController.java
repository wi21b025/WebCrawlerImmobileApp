package web.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import service.UserService;
import web.dto.FilterDTO;

@Controller
public class FilterController
{

    private final UserService userService;
    private final HttpSession session;


    @Autowired
    public FilterController(UserService userService, HttpSession session)
    {
        this.userService = userService;
        this.session = session;

    }


    @GetMapping("/filter")
    public String filter()
    {
        return "/web-view/filters";
    }

        @PostMapping("/saveFilter")
        public String saveUserFilter(@ModelAttribute FilterDTO filter) {
            // Retrieve the email from the session
            String email = (String) session.getAttribute("email");

            if (email != null)
            {
                // Save the user filter

                userService.saveUserFilter(email, filter);

                return "redirect:/filter"; // Redirect on success
            } else {
                // Handle the case where there is no email in session
                return "redirect:/login"; // Redirect to login or an appropriate error page
            }
        }


}
