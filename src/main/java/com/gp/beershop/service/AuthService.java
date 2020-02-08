package com.gp.beershop.service;

import com.gp.beershop.dto.AuthRequest;
import com.gp.beershop.dto.IdResponse;
import lombok.extern.java.Log;
import org.springframework.stereotype.Service;

@Log
@Service
public class AuthService {
    private final IdResponse id = new IdResponse(1);
    public IdResponse signIn(final AuthRequest request) {
        log.info("email = " + request.getEmail());
        log.info("email = " + request.getPassword());
        return id;
    }
}
