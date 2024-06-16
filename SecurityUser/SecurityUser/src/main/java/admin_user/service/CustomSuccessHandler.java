package admin_user.service;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Optional;

@Service
public class CustomSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        var authourities = authentication.getAuthorities();


        Optional<String> role = authourities.stream()
                .map(r -> r.getAuthority())
                .findFirst();

        if (role.isPresent()) {
            switch (role.get()) {
                case "1":
                    response.sendRedirect("/user-page");
                    break;
                case "2":
                    response.sendRedirect("/admin-page");
                    break;
                case "3":
                    response.sendRedirect("/sale-page");
                    break;
                case "4":
                    response.sendRedirect("/manager-page");
                    break;
                case "5":
                    response.sendRedirect("/design-page");
                    break;
                case "6":
                    response.sendRedirect("/product-page");
                    break;
                default:
                    response.sendRedirect("/home-page");
                    break;
            }
        } else {
            response.sendRedirect("/home-page");
        }
    }
}



