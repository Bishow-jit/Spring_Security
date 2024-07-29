package com.example.SpringSecurity.Auditing;

import org.springframework.data.domain.AuditorAware;

import java.util.Optional;


public class AuditorAwareImpl implements AuditorAware {


    @Override
    public Optional<String> getCurrentAuditor() {
        return Optional.of("Bishowjit");
    }
}
