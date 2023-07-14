package com.nokuthaba.backend.controller;
import com.nokuthaba.backend.service.AuthenticationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.nokuthaba.backend.models.AuthenticationRequest;
import com.nokuthaba.backend.models.AuthenticationResponse;

@RestController
@CrossOrigin
@Slf4j
public class AuthenticationController {

    @Autowired
    private AuthenticationService authenticationService;

    @PostMapping({"/authenticate"})
    public AuthenticationResponse createJwtToken(@RequestBody AuthenticationRequest jwtRequest) throws Exception {
        log.info("AUTHENTICATION REQUEST HERE"+ jwtRequest);
        return authenticationService.createJwtToken(jwtRequest);
    }
}
