package jakit.micro.uaa.service;

import jakit.micro.uaa.entities.User;
import jakit.micro.uaa.exception.UnauthorizedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findOneByUsername(username);
        if (user.isPresent()) {
            User u = user.orElse(null);
            if (u == null) {
                throw new UnauthorizedException("Нэвтрэх нэр эсвэл нууц үг буруу байна.");
            }

            return u;
        } else {
            throw new UnauthorizedException("Нэвтрэх нэр эсвэл нууц үг буруу байна.");
        }
    }

}
