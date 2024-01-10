package web.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
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
        public String saveUserFilter(@ModelAttribute FilterDTO filter)
        {
            // Retrieve the email from the session
            String email = (String) session.getAttribute("email");

            if (email != null)
            {
                // Save the user filter

                userService.saveUserFilter(email, filter);

                return "redirect:/filter"; // Redirect on success
            }
            else
            {

                return "redirect:/auth";
            }
        }
    @GetMapping("/getFilterData")
    @ResponseBody
    public FilterDTO getFilterData(@RequestParam String uid)
    {

        FilterDTO filter = userService.getFilterByUid(uid);
        return filter;
    }


    @PostMapping("/deleteFilter")
    public ResponseEntity<?> deleteFilter(@RequestParam String uid)
    {
        String email = (String) session.getAttribute("email");
        if (email != null && userService.deleteUserFilter(email, uid))
        {
            return ResponseEntity.ok().build(); // Return success response
        }
        else
        {
            return ResponseEntity.badRequest().build(); // Return failure response
        }
    }
}
