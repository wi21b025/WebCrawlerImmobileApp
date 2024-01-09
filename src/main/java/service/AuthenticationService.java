package service;

import model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
@Service
public class AuthenticationService
{
    private final UserService userService;
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public AuthenticationService(UserService userService, BCryptPasswordEncoder passwordEncoder)
    {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;

    }

    public boolean isLoginSuccessful(String usernameOrEmail, String password)
    {

        User user = userService.findByUsernameOrEmail(usernameOrEmail);

        // Check if a user with the provided username/email exists
        if (user == null)
        {
            return false; // User does not exist
        }

        // Compare the provided password with the stored password
        if (passwordMatches(password, user.getPassword()))
        {
            return true; // Successful login
        }

        return false; // Incorrect password
    }

    private boolean passwordMatches(String providedPassword, String storedPassword) {
        return passwordEncoder.matches(providedPassword, storedPassword);
    }
}
