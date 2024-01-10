package web.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import service.UserService;
import web.dto.FilterDTO;

import java.util.List;

@Controller
public class ProfilController
{
    private final UserService userService;
    private final HttpSession session;
    @Autowired
    public ProfilController (UserService userService, HttpSession session)
    {
        this.userService = userService;
        this.session = session;

    }
    @GetMapping("/profil")
    public String profil(Model model, HttpSession session) {
        String email = (String) session.getAttribute("email");

        if (email != null)
        {
            List<FilterDTO> filters = userService.getUserFilters(email);
            model.addAttribute("filters", filters);

            return "web-view/profil";
        }
        else
        {
            return "redirect:/auth";
        }
    }

}
