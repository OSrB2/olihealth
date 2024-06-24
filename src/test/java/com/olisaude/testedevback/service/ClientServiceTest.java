package com.olisaude.testedevback.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import com.olisaude.testedevback.dto.ClientDTO;
import com.olisaude.testedevback.exceptions.HandleIDNotFound;
import com.olisaude.testedevback.exceptions.HandleNoHasClients;
import com.olisaude.testedevback.exceptions.Validations;
import com.olisaude.testedevback.mapper.ClientMapper;
import com.olisaude.testedevback.model.ClientModel;
import com.olisaude.testedevback.model.HealthProblemModel;
import com.olisaude.testedevback.repository.ClientRepository;

@ExtendWith(MockitoExtension.class)
public class ClientServiceTest {
  @Mock
  private ClientRepository clientRepository;
  @Mock
  private ClientMapper clientMapper;
  @Mock
  private Validations validations;
  @InjectMocks
  private ClientService clientService;
  
  @BeforeEach
  void config() {
    MockitoAnnotations.openMocks(this);
  }

  @DisplayName("Should create a new client")
  @Test
  public void testCreateClient() throws Exception {
    HealthProblemModel diabetes = new HealthProblemModel();
    diabetes.setName("diabetes");
    diabetes.setLevel(2);

    HealthProblemModel hipertensao = new HealthProblemModel();
    hipertensao.setName("hipertensão");
    hipertensao.setLevel(3);

    List<HealthProblemModel> healthProblems = Arrays.asList(diabetes, hipertensao);

    ClientModel client = new ClientModel();
    client.setName("Name");
    client.setLastName("LastName");
    client.setBirthDate(Date.from(LocalDate.of(2023, 8, 21).atStartOfDay(ZoneId.systemDefault()).toInstant()));
    client.setGender("male");
    client.setHealthProblem(healthProblems);

    when(validations.isNameValid(client.getName())).thenReturn(true);
    when(validations.isNameCount(client.getName())).thenReturn(true);
    when(validations.isLastNameValid(client.getLastName())).thenReturn(true);
    when(validations.isLastNameCount(client.getLastName())).thenReturn(true);
    when(validations.isBirthDateValid(client.getBirthDate())).thenReturn(true);
    when(validations.isBirthDateValidPrevious(client.getBirthDate())).thenReturn(true);
    when(validations.isGenderValid(client.getGender())).thenReturn(true);
    when(validations.isGenderOptionValid(client.getGender())).thenReturn(true);
    when(validations.isHealthProblemValid(client.getHealthProblem())).thenReturn(true);
    when(clientRepository.save(any(ClientModel.class))).thenReturn(client);

    clientService.create(client);

    assertNotNull(client);
  }

  @DisplayName("Should return a list of ClientDTOs when clients are found")
    @Test
    public void testFindAllClients() {
    ClientModel client1 = new ClientModel();
    client1.setName("John");
    client1.setLastName("Doe");

    ClientModel client2 = new ClientModel();
    client2.setName("Jane");
    client2.setLastName("Doe");

    List<ClientModel> clients = Arrays.asList(client1, client2);

    ClientDTO clientDTO1 = new ClientDTO();
    clientDTO1.setName("John");
    clientDTO1.setLastName("Doe");

    ClientDTO clientDTO2 = new ClientDTO();
    clientDTO2.setName("Jane");
    clientDTO2.setLastName("Doe");

    when(clientRepository.findAll()).thenReturn(clients);
    when(clientMapper.toClientDTO(client1)).thenReturn(clientDTO1);
    when(clientMapper.toClientDTO(client2)).thenReturn(clientDTO2);

    List<ClientDTO> result = clientService.findAll();

    assertEquals(2, result.size());
    assertEquals("John", result.get(0).getName());
    assertEquals("Jane", result.get(1).getName());

    verify(clientRepository).findAll();
    verify(clientMapper).toClientDTO(client1);
    verify(clientMapper).toClientDTO(client2);
  }

  @DisplayName("Should throw HandleNoHasClients exception when no clients are found")
  @Test
  public void testFindAllClients_NoClientsFound() {
    when(clientRepository.findAll()).thenReturn(Collections.emptyList());

    assertThrows(HandleNoHasClients.class, () -> {
        clientService.findAll();
    });

    verify(clientRepository).findAll();
  }

  @DisplayName("Should return a client by ID")
  @Test
  public void testFindClientById() throws Exception {
    ClientModel clientModel = new ClientModel();
    clientModel.setId(2L);

    ClientDTO clientDTO = new ClientDTO();

    when(clientRepository.findById(2L)).thenReturn(Optional.of(clientModel));
    when(clientMapper.toClientDTO(clientModel)).thenReturn(clientDTO);

    Optional<ClientDTO> result = clientService.findClientById(2L);

    assertTrue(result.isPresent());
    assertEquals(clientDTO, result.get());
  }

  @DisplayName("Shoul return exception when ID not found")
  @Test
  public void testIdNotFound() throws Exception {
    Long id = 1L;

    when(clientRepository.findById(id)).thenReturn(Optional.empty());

    assertThrows(HandleIDNotFound.class, () -> {
      clientService.findClientById(id);
    });
  }

  @DisplayName("Should update a Client by ID")
  @Test
  public void testUpdateClientByID() throws Exception {
    Long idClient = 1L;
    ClientDTO updaClientDTO = new ClientDTO();
    ClientModel clientModel = new ClientModel();

    Optional<ClientModel> clientModeOptional = Optional.of(clientModel);

    when(clientRepository.findById(idClient)).thenReturn(clientModeOptional);
    when(clientMapper.toClientDTO(clientModel)).thenReturn(updaClientDTO);

    ClientDTO result = clientService.updateClient(idClient, updaClientDTO);

    verify(clientRepository).save(clientModel);

    assertEquals(updaClientDTO, result);
  }

  @DisplayName("Should delete a Client by ID")
  @Test
  public void testDeleteClientByID() throws Exception {
    ClientModel clientExist = new ClientModel();
    clientExist.setId(1L);

    Optional<ClientModel> clientModelExist = Optional.of(clientExist);

    when(clientRepository.findById(1L)).thenReturn(clientModelExist);

    clientService.deleteClient(clientExist.getId());
  }
}
