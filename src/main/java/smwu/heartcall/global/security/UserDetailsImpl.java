package smwu.heartcall.global.security;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import smwu.heartcall.domain.user.entity.User;
import smwu.heartcall.domain.user.entity.UserRole;

import java.util.ArrayList;
import java.util.Collection;

@Getter
@RequiredArgsConstructor
public class UserDetailsImpl implements UserDetails {
    private final User user;

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        UserRole role = user.getUserRole();

        SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority(role.toString());

        Collection<GrantedAuthority> authorities = new ArrayList<>();

        authorities.add(simpleGrantedAuthority);

        return authorities;
    }
}
