package web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController
{

    @GetMapping("/index")
    public String index()
    {
        return "/web-view/index"; // Redirect to the home page
    }

}
