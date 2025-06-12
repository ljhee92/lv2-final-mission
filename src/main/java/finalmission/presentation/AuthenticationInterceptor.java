package finalmission.presentation;

import finalmission.application.AuthService;
import finalmission.domain.Role;
import finalmission.exception.AuthException;
import finalmission.infra.jwt.JwtTokenExtractor;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.HandlerInterceptor;

public class AuthenticationInterceptor implements HandlerInterceptor {

    private final AuthService authService;

    public AuthenticationInterceptor(AuthService authService) {
        this.authService = authService;
    }

    @Override
    public boolean preHandle(
            HttpServletRequest request,
            HttpServletResponse response,
            Object handler
    ) throws Exception {
        String uri = request.getRequestURI();
        String token = JwtTokenExtractor.extractToken(request);

        Role role = authService.findRoleByToken(token);
        if (uri.startsWith("/admin") && role != Role.ADMIN) {
            throw new AuthException("[ERROR] 권한이 필요합니다.", HttpStatus.FORBIDDEN);
        }
        return true;
    }
}
