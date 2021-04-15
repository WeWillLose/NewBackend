package com.diploma.Backend.security;

import com.diploma.Backend.utils.SecurityUtils;
import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class SpringSecurityAuditorAware implements AuditorAware<String> {
    @Override
    public Optional<String> getCurrentAuditor() {
        return Optional.of(SecurityUtils.getCurrentUserLogin().orElse("SYSTEM"));
    }
}
