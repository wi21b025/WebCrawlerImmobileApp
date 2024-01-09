package web.controller;

import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ProfilController
{

    @GetMapping("/profil")
    public String profil()
    {
        return "/web-view/profil";
    }
}
