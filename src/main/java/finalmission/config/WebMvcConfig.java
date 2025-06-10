package finalmission.config;

import finalmission.application.AuthService;
import finalmission.presentation.AuthenticationPrincipalResolver;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    private final AuthService authService;

    public WebMvcConfig(AuthService authService) {
        this.authService = authService;
    }

    @Override
    public void addArgumentResolvers(final List<HandlerMethodArgumentResolver> resolvers) {
       resolvers.add(new AuthenticationPrincipalResolver(authService));
    }
}
