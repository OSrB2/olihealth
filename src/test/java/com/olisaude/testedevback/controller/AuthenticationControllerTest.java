package com.olisaude.testedevback.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.olisaude.testedevback.dto.AuthenticationDTO;
import com.olisaude.testedevback.mapper.ClientMapper;
import com.olisaude.testedevback.model.UserModel;
import com.olisaude.testedevback.repository.UserRepository;
import com.olisaude.testedevback.security.TokenService;

@WebMvcTest(controllers = AuthenticationController.class)
public class AuthenticationControllerTest {
  @Autowired
  private MockMvc mockMvc;
  @Autowired
  private ObjectMapper objectMapper;
  @MockBean
  private TokenService tokenService;
  @MockBean
  private ClientMapper clientMapper;
  @MockBean
  private UserRepository userRepository;
  @MockBean
  private AuthenticationManager authenticationManager;


  @DisplayName("Should register a new user")
  @Test
  @WithMockUser(username  = "login", password = "password")
  public void testRegisterANewUser() throws Exception {
    AuthenticationDTO data = new AuthenticationDTO("testUser", "testPassword");
    String encryptedPassword = new BCryptPasswordEncoder().encode(data.getPassword());

    when(userRepository.findByLogin(any())).thenReturn(null);

    mockMvc.perform(MockMvcRequestBuilders.post("/auth/register")
        .with(csrf())
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(data)))
      .andDo(print())
      .andExpect(MockMvcResultMatchers.status().isOk())
      .andExpect(MockMvcResultMatchers.content().string("User registered successfully!"));

    verify(userRepository, times(1)).findByLogin("testUser");
    verify(userRepository, times(1)).save(any(UserModel.class));
  }

  @DisplayName("Should return a success login")
  @Test
  @WithMockUser(username  = "login", password = "password")
  public void testSuccessfullLogin() throws Exception {
    when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
        .thenReturn(new UsernamePasswordAuthenticationToken(new UserModel(), null));

    mockMvc.perform(post("/auth/login")
        .content("{\"login\":\"login\",\"password\":\"password\"}").with(csrf())
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk());
  }

  @DisplayName("Should return a message if user already exists")
  @Test
  @WithMockUser(username  = "login", password = "password")
  public void testUserAlreadyExists() throws Exception {
    AuthenticationDTO data = new AuthenticationDTO("existsUser", "password");

    when(userRepository.findByLogin("existsUser")).thenReturn(new UserModel("existsUser", "password"));

    mockMvc.perform(MockMvcRequestBuilders.post("/auth/register")
        .with(csrf())
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(data)))
      .andExpect(MockMvcResultMatchers.status().isBadRequest())
      .andExpect(MockMvcResultMatchers.content().string("This user already exists!"));
  }
  
}
