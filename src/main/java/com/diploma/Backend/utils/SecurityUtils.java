package com.diploma.Backend.utils;

import com.diploma.Backend.env.ROLES;
import com.diploma.Backend.model.EReportStatus;
import com.diploma.Backend.model.Report;
import com.diploma.Backend.model.Role;
import com.diploma.Backend.model.User;
import com.diploma.Backend.rest.exception.impl.ForbiddenExceptionImpl;
import com.diploma.Backend.rest.exception.impl.ReportNotFoundExceptionImpl;
import com.diploma.Backend.service.user.UserService;
import lombok.NonNull;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Optional;
@Component
public class SecurityUtils {

    private final UserService userService;

    private SecurityUtils(@Lazy UserService userService) {
        this.userService = userService;
    }

    /**
     * Get the login of the current user.
     *
     * @return the login of the current user.
     */
    public static Optional<String> getCurrentUserLogin() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        return Optional.ofNullable(extractPrincipal(securityContext.getAuthentication()));
    }

    private static String extractPrincipal(Authentication authentication) {
        if (authentication == null) {
            return null;
        } else if (authentication.getPrincipal() instanceof UserDetails) {
            UserDetails springSecurityUser = (UserDetails) authentication.getPrincipal();
            return springSecurityUser.getUsername();
        } else if (authentication.getPrincipal() instanceof String) {
            return (String) authentication.getPrincipal();
        }
        return null;
    }

    /**
     * Get the JWT of the current user.
     *
     * @return the JWT of the current user.
     */
    public static Optional<String> getCurrentUserJWT() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        return Optional
            .ofNullable(securityContext.getAuthentication())
            .filter(authentication -> authentication.getCredentials() instanceof String)
            .map(authentication -> (String) authentication.getCredentials());
    }

    /**
     * Check if a user is authenticated.
     *
     * @return true if the user is authenticated, false otherwise.
     */
    public static boolean isAuthenticated() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication != null;
    }

    /**
     * Checks if the current user has a specific role.
     *
     * @param role the role to check.
     * @return true if the current user has the role, false otherwise.
     */
    public static boolean hasCurrentUserThisRole(Role role) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication != null && authentication.getAuthorities().contains(role);
    }

    public static boolean isAdmin(User user) {
        return user != null && user.getRoles().contains(ROLES.ADMIN);
    }
    public static boolean isChairman(User user) {
        return user != null && user.getRoles().contains(ROLES.ADMIN);
    }
    public static boolean isCurrentUserAdmin() {
        return hasCurrentUserThisRole(ROLES.CHAIRMAN);
    }
    public static boolean isCurrentUserChairman() {
        return hasCurrentUserThisRole(ROLES.CHAIRMAN);
    }
    public static User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication != null? (User) authentication.getPrincipal() :null;
    }

    public boolean isCurrentUserCanEditReportIfIsOwnerAndReportStatusUNCHECKEDOrIsOwnerChairmanOrAdmin(@NonNull Report report){
        if(isCurrentUserAdmin())  return true;
        if(report.getAuthor() == null) return false;
        if(getCurrentUser() ==null) return false;
        if(isCurrentUserChairman() &&
            userService.isUserInChairmanGroup(report.getAuthor().getId(),getCurrentUser().getId())){
            return true;
        }
        return report.getStatus() == EReportStatus.UNCHECKED && report.getAuthor().getId().equals(SecurityUtils.getCurrentUser().getId());
    }

    public static boolean isCurrentUserEqualsUserOrAdmin(User user) {
        if(hasCurrentUserThisRole(ROLES.ADMIN)) return true;
        if(user ==null) return false;
        User currUser = getCurrentUser();
        return currUser != null && currUser.getId().equals(user.getId());
    }
}
