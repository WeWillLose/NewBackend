package com.diploma.Backend.security;

import com.diploma.Backend.utils.SecurityUtils;
import com.diploma.Backend.utils.UserUtils;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class AuditorAwareImpl  implements AuditorAware<String> {
    @Override
    public Optional<String> getCurrentAuditor() {
        return Optional.of(UserUtils.getShortFioFromUser(SecurityUtils.getCurrentUser()));
    }
}
