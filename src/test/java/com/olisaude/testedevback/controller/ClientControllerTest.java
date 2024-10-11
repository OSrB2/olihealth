package com.olisaude.testedevback.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import org.springframework.test.web.servlet.MockMvc;
import org.springframework.http.MediaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.olisaude.testedevback.dto.ClientDTO;
import com.olisaude.testedevback.exceptions.HandleIDNotFound;
import com.olisaude.testedevback.exceptions.HandleNoHasClients;
import com.olisaude.testedevback.mapper.ClientMapper;
import com.olisaude.testedevback.model.ClientModel;
import com.olisaude.testedevback.model.HealthProblemModel;
import com.olisaude.testedevback.repository.ClientRepository;
import com.olisaude.testedevback.service.ClientService;

import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(ClientController.class)
public class ClientControllerTest {
  @Autowired
  private MockMvc mockMvc;
  
  @Autowired
  private ObjectMapper objectMapper;

  @MockBean
  private ClientMapper clientMapper;

  @MockBean
  private ClientRepository clientRepository;

  @MockBean
  private ClientService clientService;

  @MockBean
  private ClientDTO clientDTO;

  SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
  
  @DisplayName("Should register a client")
  @Test
  public void testRegisterClient() throws Exception {
    Date birthDate = dateFormat.parse("1990-05-12");
    ClientModel client = new ClientModel();
    HealthProblemModel health = new HealthProblemModel();

    client.setName("Teste");
    client.setLastName("Teste");
    client.setBirthDate(birthDate);
    client.setGender("Male");
    health.setLevel(2);
    health.setName("Teste");
    client.setHealthProblem(Collections.singletonList(health));

    Mockito.when(clientService.create(Mockito.any(ClientModel.class))).thenReturn(client);

    mockMvc.perform(post("/api/client")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(client)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.name").value("Teste"))
        .andExpect(jsonPath("$.lastName").value("Teste"))
        .andExpect(jsonPath("$.birthDate").value("1990-05-12T03:00:00.000+00:00"))
        .andExpect(jsonPath("$.gender").value("Male"))
        .andExpect(jsonPath("$.healthProblem[0].name").value("Teste"))
        .andExpect(jsonPath("$.healthProblem[0].level").value(2));
  }

  @DisplayName("Should display all clients")
  @Test
  public void testListAllClients() throws Exception {
    Date birthDate = dateFormat.parse("1990-05-12");
    List<ClientDTO> clientDTOList = new ArrayList<>();
    
    ClientDTO client1 = new ClientDTO();
    client1.setName("Teste");
    client1.setLastName("Teste");
    client1.setBirthDate(birthDate);
    client1.setGender("Male");

    ClientDTO client2 = new ClientDTO();
    client2.setName("Name");
    client2.setLastName("LastName");
    client2.setBirthDate(birthDate);
    client2.setGender("Female");

    clientDTOList.add(client1);
    clientDTOList.add(client2);

    Mockito.when(clientService.findAll()).thenReturn(clientDTOList);

    mockMvc.perform(get("/api/client"))
        .andExpect(status().isOk())
        .andExpect(content().contentType("application/json"))
        .andExpect(jsonPath("$.[0].name").value("Teste"))
        .andExpect(jsonPath("$.[0].lastName").value("Teste"))
        .andExpect(jsonPath("$.[0].birthDate").value("1990-05-12T03:00:00.000+00:00"))
        .andExpect(jsonPath("$.[0].gender").value("Male"))
        .andExpect(jsonPath("$.[1].name").value("Name"))
        .andExpect(jsonPath("$.[1].lastName").value("LastName"))
        .andExpect(jsonPath("$.[1].birthDate").value("1990-05-12T03:00:00.000+00:00"))
        .andExpect(jsonPath("$.[1].gender").value("Female"));
  }

  @DisplayName("Should return an exception if there is no clients")
  @Test
  public void testNoHasClients() throws Exception {
    doThrow(HandleNoHasClients.class).when(clientService).findAll();
    mockMvc.perform(get("/api/client"))
      .andExpect(status().isNotFound());
  }

  @DisplayName("Should find a client by id")
  @Test
  public void testFindClientById() throws Exception {
    Date birthDate = dateFormat.parse("1990-05-12");
    HealthProblemModel health = new HealthProblemModel();
    ClientModel client = new ClientModel();
    client.setName("Teste");
    client.setLastName("Teste");
    client.setBirthDate(birthDate);
    client.setGender("Male");
    health.setLevel(2);
    health.setName("Teste");
    client.setHealthProblem(Collections.singletonList(health));

    Mockito.when(clientService.findClientById(1L)).thenReturn(Optional.of(client));

    mockMvc.perform(get("/api/client/{id}", 1L))
        .andExpect(status().isOk())
        .andExpect(content().contentType("application/json"))
        .andExpect(jsonPath("$.name").value("Teste"))
        .andExpect(jsonPath("$.lastName").value("Teste"))
        .andExpect(jsonPath("$.birthDate").value("1990-05-12T03:00:00.000+00:00"))
        .andExpect(jsonPath("$.gender").value("Male"))
        .andExpect(jsonPath("$.healthProblem[0].name").value("Teste"))
        .andExpect(jsonPath("$.healthProblem[0].level").value(2));
  }

  @DisplayName("Should return a exception if theres is no id")
  @Test
  public void testIdNotFound() throws Exception  {
    Long id = 1L;
    doThrow(HandleIDNotFound.class).when(clientService).findClientById(id);

    mockMvc.perform(get("/api/client/{id}", 1))
      .andExpect(status().isNotFound());
  }

  @DisplayName("Should update a client")
  @Test
  public void testUpdateClient() throws Exception {
    Date birthDate = dateFormat.parse("1990-05-12");
    ClientModel client = new ClientModel();
    HealthProblemModel health = new HealthProblemModel();

    client.setName("Teste");
    client.setLastName("Teste");
    client.setBirthDate(birthDate);
    client.setGender("Male");
    health.setLevel(2);
    health.setName("Teste");
    client.setHealthProblem(Collections.singletonList(health));

    Mockito.when(clientService.updateClient(Mockito.any(), Mockito.any(client.getClass()))).thenReturn(client);

    mockMvc.perform(put("/api/client/{id}", 1L)
      .contentType(MediaType.APPLICATION_JSON)
      .content(objectMapper.writeValueAsString(client)))
      .andExpect(status().isOk())
      .andExpect(jsonPath("$.name").value("Teste"))
      .andExpect(jsonPath("$.lastName").value("Teste"))
      .andExpect(jsonPath("$.birthDate").value("1990-05-12T03:00:00.000+00:00"))
      .andExpect(jsonPath("$.gender").value("Male"))
      .andExpect(jsonPath("$.healthProblem[0].name").value("Teste"))
      .andExpect(jsonPath("$.healthProblem[0].level").value(2));
  }

  @DisplayName("Should delete client by id")
  @Test
  public void testDeleteClientById() throws Exception {
    when(clientService.findClientById(1L)).thenReturn(Optional.of(new ClientModel()));

    mockMvc.perform(delete("/api/client/{id}", 1L))
      .andExpect(status().isNoContent());
  }

}
