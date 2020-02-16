package com.gp.beershop.service;

import com.gp.beershop.dto.Customer;
import lombok.extern.java.Log;
import org.springframework.stereotype.Service;

@Log
@Service
public class RegistrationService {
    public Integer signUp(final Customer request) {
        log.info("email = " + request.getEmail());
        return 1;
    }
}
