package com.gp.beershop.service;

import com.gp.beershop.dto.CustomerSignUpRequest;
import lombok.extern.java.Log;
import org.springframework.stereotype.Service;

@Log
@Service
public class RegistrationService {
    public String signUp(final CustomerSignUpRequest request) {
        log.info("email = " + request.getEmail());
        return "{\"id\":1}";
    }
}
