package dbproject.ownpli.domain;

import dbproject.ownpli.domain.value.Role;
import lombok.Getter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;

@Getter
public class UserDetails extends org.springframework.security.core.userdetails.User {

    private final User user;

    public UserDetails(User user) {
        super(user.getUserId(), user.getPassword(), List.of(new SimpleGrantedAuthority(Role.USER.getRoleName())));
        this.user = user;
    }
}
