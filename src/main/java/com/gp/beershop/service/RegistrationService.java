package com.gp.beershop.service;

import com.gp.beershop.dto.CustomerSignUpRequest;
import com.gp.beershop.dto.IdResponse;
import lombok.extern.java.Log;
import org.springframework.stereotype.Service;

@Log
@Service
public class RegistrationService {
    private final IdResponse id = new IdResponse(1);
    public IdResponse signUp(final CustomerSignUpRequest request) {
        log.info("email = " + request.getEmail());
        return id;
    }
}
