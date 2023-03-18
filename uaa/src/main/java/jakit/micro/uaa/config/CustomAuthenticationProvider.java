package jakit.micro.uaa.config;


import jakit.micro.uaa.entities.User;
import jakit.micro.uaa.repositories.UserRepository;
import jakit.micro.uaa.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.common.exceptions.UnauthorizedUserException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.servlet.http.HttpServletRequest;
import java.sql.Date;
import java.util.Optional;

@Configuration
@Component
public class CustomAuthenticationProvider implements AuthenticationManager {

    @Autowired
    UserRepository userRepository;

    PasswordEncoder passwordEncoder;

    @Autowired
    UserService userService;

    @Autowired
    private HttpServletRequest request;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public Authentication authenticate(Authentication a) throws AuthenticationException {
        Optional<User> loadByUsername = userRepository.findOneByUsername(a.getPrincipal().toString());

        if (!loadByUsername.isPresent()) {
            throw new UnauthorizedUserException("Нэр эсвэл нууц үг буруу байна.");
        }

        User user = loadByUsername.get();
        Date now = new Date(System.currentTimeMillis());


        if (loadByUsername != null && passwordEncoder.matches(a.getCredentials().toString(), user.getPassword())) {
            logger.info("user authorities: {}", user.getAuthorities());
            return new UsernamePasswordAuthenticationToken((Object) a.getPrincipal(), (Object) a.getCredentials(),
                    user.getAuthorities());
        }

        throw new UnauthorizedUserException("Нэр эсвэл нууц үг буруу байна.");
    }
}
