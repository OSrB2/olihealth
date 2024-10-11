package com.olisaude.testedevback.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
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

  SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

  @DisplayName("Should register a client")
  @Test
  public void testeRegisterClient() throws Exception {
    Date birthDate = dateFormat.parse("1990-05-12");
    HealthProblemModel healthProblemModel = new HealthProblemModel();
    ClientModel client = new ClientModel();
    client.setName("Teste");
    client.setLastName("Teste");
    client.setBirthDate(birthDate);
    client.setGender("male");
    healthProblemModel.setId(1L);
    healthProblemModel.setLevel(1);
    healthProblemModel.setName("teste");
    client.setHealthProblem(Collections.singletonList(healthProblemModel));

    when(validations.isNameValid(client.getName())).thenReturn(true);
    when(validations.isNameCount(client.getName())).thenReturn(true);
    when(validations.isLastNameValid(client.getLastName())).thenReturn(true);
    when(validations.isLastNameCount(client.getLastName())).thenReturn(true);
    when(validations.isBirthDateValid(client.getBirthDate())).thenReturn(true);
    when(validations.isBirthDateValidPrevious(client.getBirthDate())).thenReturn(true);
    when(validations.isGenderValid(client.getGender())).thenReturn(true);
    when(validations.isGenderOptionValid(client.getGender())).thenReturn(true);
    when(validations.isHealthProblemValid(client.getHealthProblem())).thenReturn(true);

    clientService.create(client);

    assertNotNull(client);
    verify(clientRepository, times(1)).save(any(ClientModel.class));
  }

  @DisplayName("Should return a list of clients")
  @Test
  public void testListAllClients() throws Exception {
    ClientModel client1 = new ClientModel();
    ClientModel client2 = new ClientModel();
    List<ClientModel> clientModelsList = Arrays.asList(client1, client2);

    when(clientRepository.findAll()).thenReturn((clientModelsList));

    List<ClientDTO> clientDTOsList = clientService.findAll();

    assertEquals(2, clientDTOsList.size());
  }

  @DisplayName("Should return an exception if there is no clients")
  @Test
  public void testNoHasClients() throws Exception {
    when(clientRepository.findAll()).thenReturn(new ArrayList<>());

    assertThrows(HandleNoHasClients.class, () -> {
      clientService.findAll();
    });
  }

  @DisplayName("Should return a client by iD")
  @Test
  public void testFindClientById() throws Exception {
    ClientModel clientModel = new ClientModel();
    clientModel.setId(1L);

    when(clientRepository.findById(clientModel.getId())).thenReturn(Optional.of(clientModel));

    Optional<ClientModel> result = clientService.findClientById(1l);

    assertTrue(result.isPresent());
    assertEquals(clientModel, result.get());

    verify(clientRepository, times(1)).findById(1L);
  }

  @DisplayName("Should return exception when id no found")
  @Test
  public void testIdNotFound() throws Exception {
    Long id = 1L;

    when(clientRepository.findById(id)).thenReturn(Optional.empty());

    assertThrows(HandleIDNotFound.class, () -> {
      clientService.findClientById(id);
    });
  }

  @DisplayName("Should update a client by ID")
  @Test
  public void testUpdateClientById() throws Exception {
    Long cliendId = 1L;
    ClientModel existingClient = new ClientModel();
    existingClient.setId(cliendId);
    existingClient.setName("Test");
    existingClient.setLastName("Test");
    existingClient.setBirthDate(new Date());
    existingClient.setGender("female");

    ClientModel updateClientModel = new ClientModel();
    updateClientModel.setName("Updated Name");
    updateClientModel.setLastName("Updated Name");
    updateClientModel.setBirthDate(new Date());
    updateClientModel.setGender("female");

    when(clientRepository.findById(cliendId)).thenReturn(Optional.of(existingClient));

    when(clientRepository.save(existingClient)).thenReturn(existingClient);

    ClientModel result = clientService.updateClient(cliendId, updateClientModel);

    assertEquals(updateClientModel.getName(), result.getName());
    assertEquals(updateClientModel.getLastName(), result.getLastName());
    assertEquals(updateClientModel.getBirthDate(), result.getBirthDate());
    assertEquals(updateClientModel.getGender(), result.getGender());

    verify(clientRepository).save(existingClient);
  }

  @DisplayName("Should delete client by ID")
  @Test
  public void testDeleteClientById() throws Exception {
    ClientModel clientExist = new ClientModel();
    clientExist.setId(1L);

    Optional<ClientModel> clientModelExist = Optional.of(clientExist);

    when(clientRepository.findById(clientExist.getId())).thenReturn(clientModelExist);

    clientService.deleteClient(clientExist.getId());
  }

}
