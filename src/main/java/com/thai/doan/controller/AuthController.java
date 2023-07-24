package com.thai.doan.controller;

import com.thai.doan.dto.request.AuthRequest;
import com.thai.doan.dto.request.LoginRequest;
import com.thai.doan.dto.response.LoginResponse;
import com.thai.doan.security.CustomUserDetails;
import com.thai.doan.security.jwt.JwtTokenUtils;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@Data
public class AuthController {
  private final AuthenticationManager authenticationManager;

  private final JwtTokenUtils jwtTokenUtils;

  private final UserDetailsService userDetailsService;

  @PostMapping("/auth")
  public ResponseEntity<?> authenticate(@RequestBody LoginRequest loginRequest) {
    try {
      Authentication authentication = authenticationManager.authenticate(
          new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword())
      );
      CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
      String jwt = "Bearer " + jwtTokenUtils.generateJwt(customUserDetails);
      LoginResponse loginResponse = new LoginResponse();
      loginResponse.setAccessToken(jwt);
      loginResponse.setUser(customUserDetails.getUser());
      loginResponse.setStudent(customUserDetails.getUser().getStudent());
      return new ResponseEntity<>(loginResponse, HttpStatus.OK);
    } catch (AuthenticationException e) {
      return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }
  }

  @PostMapping("/auth/checkToken")
  public ResponseEntity<?> authenticate(@RequestBody AuthRequest authRequest) {
    String token = authRequest.getBearerToken().substring(6);
    if (jwtTokenUtils.verify(token))
      return new ResponseEntity<>(HttpStatus.OK);
    return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
  }
}
