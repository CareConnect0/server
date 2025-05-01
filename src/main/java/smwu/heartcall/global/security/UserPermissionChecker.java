package smwu.heartcall.global.security;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import smwu.heartcall.domain.user.enums.UserType;

@Component
public class UserPermissionChecker {
    public boolean isGuardian(Authentication authentication) {
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        return userDetails.getUserType() == UserType.GUARDIAN;
    }

    public boolean isDependent(Authentication authentication) {
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        return userDetails.getUserType() == UserType.DEPENDENT;
    }
}
