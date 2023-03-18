package jakit.micro.uaa.config;

import jakit.micro.uaa.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import jakit.micro.uaa.repositories.UserRepository;

public class CustomTokenEnhancer implements TokenEnhancer {

    UserRepository userRepository;

    @Override
    public OAuth2AccessToken enhance(
            OAuth2AccessToken accessToken,
            OAuth2Authentication authentication) {
        Map<String, Object> additionalInfo = new HashMap<>();

        Optional<User> loadByUsername = userRepository.findOneByUsername(authentication.getPrincipal().toString());

        if(loadByUsername.isEmpty()) {
            loadByUsername =  userRepository.findOneByUsername(authentication.getName());
        }
        User user = loadByUsername.get();
        additionalInfo.put("user_id", user.getId());
        additionalInfo.put("role_id", user.getRole().getId());
        additionalInfo.put("role_code", user.getRole().getCode());
        ((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(
                additionalInfo);
        return accessToken;
    }

}
