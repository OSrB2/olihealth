package com.olisaude.testedevback.controller;

import com.olisaude.testedevback.dto.AuthenticationDTO;
import com.olisaude.testedevback.dto.LoginResponseDTO;
import com.olisaude.testedevback.model.UserModel;
import com.olisaude.testedevback.repository.UserRepository;
import com.olisaude.testedevback.security.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("auth")
public class AuthenticationController {

  @Autowired
  private AuthenticationManager authenticationManager;

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private TokenService tokenService;

  @SuppressWarnings("rawtypes")
  @PostMapping(path = "/login")
  public ResponseEntity login(@RequestBody AuthenticationDTO data) {
    try {
      var usernamePassword = new UsernamePasswordAuthenticationToken(
        data.getLogin(),
        data.getPassword()
      );
      var auth = this.authenticationManager.authenticate(usernamePassword);

      var token = tokenService.generateToken((UserModel) auth.getPrincipal());

      return ResponseEntity.ok(new LoginResponseDTO(token));
    } catch (Exception e) {
      return ResponseEntity
        .status(HttpStatus.UNAUTHORIZED)
        .body("Unauthorized user!");
    }
  }

  @SuppressWarnings("rawtypes")
  @PostMapping(path = "/register")
  public ResponseEntity register(@RequestBody AuthenticationDTO data) {
    if (this.userRepository.findByLogin(data.getLogin()) != null) {
      return ResponseEntity
        .status(HttpStatus.BAD_REQUEST)
        .body("This user already exists!");
    }

    String encryptedPassword = new BCryptPasswordEncoder()
      .encode(data.getPassword());
    UserModel newUser = new UserModel(data.getLogin(), encryptedPassword);

    this.userRepository.save(newUser);

    return ResponseEntity.ok("User registered successfully!");
  }
}
