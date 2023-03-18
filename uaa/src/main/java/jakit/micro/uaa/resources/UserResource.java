package jakit.micro.uaa.resources;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
public class UserResource {
    @RequestMapping("/user")
    public Principal user(Principal principal) {
        return principal;
    }

    @GetMapping("/users/me")
    public String getMySelf() {
        return "me";
    }

    @GetMapping("/users/{userId}/sensitive")
    public ResponseEntity getMySelf(@PathVariable Long userId, Principal principal) {

        System.out.println(principal.getClass().getName());
        System.out.println("principal.getName() = " + principal.getName());
        if (principal.getName().equals("peter@example.com")) {
            return ResponseEntity.ok("{\"important\": \"some sensitive data\"}");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }
}
