package finalmission.presentation;

import finalmission.application.AuthService;
import finalmission.presentation.request.LoginMember;
import finalmission.presentation.request.LoginRequest;
import finalmission.presentation.response.MemberResponse;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<Void> login(@RequestBody @Valid LoginRequest loginRequest, HttpServletResponse response) {
        String token = authService.login(loginRequest);
        createSession(response, token);
        return ResponseEntity.ok()
                .build();
    }

    private void createSession(HttpServletResponse response, String token) {
        Cookie cookie = new Cookie("token", token);
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        cookie.setMaxAge(60 * 10);
        response.addCookie(cookie);
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(HttpServletResponse response) {
        Cookie cookie = new Cookie("token", null);
        cookie.setPath("/");
        cookie.setMaxAge(0);
        response.addCookie(cookie);
        return ResponseEntity.ok()
                .build();
    }

    @GetMapping("/login/check")
    public ResponseEntity<MemberResponse> checkLogin(@AuthenticationPrincipal LoginMember loginMember) {
        MemberResponse memberResponse = MemberResponse.from(loginMember);
        return ResponseEntity.ok().body(memberResponse);
    }
}
