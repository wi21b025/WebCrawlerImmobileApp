package config.web.util;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class AccessInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession();
        Object loggedIn = session.getAttribute("user");

        String requestURI = request.getRequestURI();

        // Redirect non-logged-in users trying to access protected resources to the auth page
        if (loggedIn == null && isProtectedPage(requestURI)) {
            response.sendRedirect(request.getContextPath() + "/auth"); // Corrected redirect
            return false;  // Return false to indicate that the request should not proceed
        }

        // Redirect logged-in users trying to access auth pages to their profile
        if (loggedIn != null && isAuthPage(requestURI)) {
            response.sendRedirect(request.getContextPath() + "/profil"); // Corrected redirect
            return false;
        }

        return true;  // Allow the request to proceed normally
    }

    private boolean isProtectedPage(String uri) {
        // Define paths that require the user to be logged in
        return uri.startsWith("/profil") || uri.startsWith("/filter") || uri.startsWith("/saveFilter");
    }

    private boolean isAuthPage(String uri) {
        // Define paths that should not be accessible once logged in
        return uri.startsWith("/login") || uri.startsWith("/signup") || uri.startsWith("/auth");
    }
}
