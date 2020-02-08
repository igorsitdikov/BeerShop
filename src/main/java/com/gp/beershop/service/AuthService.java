package com.gp.beershop.service;

import com.gp.beershop.dto.AuthRequest;
import lombok.extern.java.Log;
import org.springframework.stereotype.Service;

@Log
@Service
public class AuthService {
    public String signIn(final AuthRequest request) {
        log.info("email = " + request.getEmail());
        log.info("email = " + request.getPassword());
        return "{\"id\":1}";
    }
}
