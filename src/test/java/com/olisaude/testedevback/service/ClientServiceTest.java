package com.olisaude.testedevback.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import com.olisaude.testedevback.dto.ClientDTO;
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
  public void testeCreateClient() throws Exception {
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

    ClientDTO clientDTO = new ClientDTO();
    clientDTO.setName("Name");
    clientDTO.setLastName("LastName");
    clientDTO.setBirthDate(Date.from(LocalDate.of(2023, 8, 21).atStartOfDay(ZoneId.systemDefault()).toInstant()));
    clientDTO.setGender("male");

    when(validations.isNameValid(client.getName())).thenReturn(true);
    when(validations.isNameCount(client.getName())).thenReturn(true);
    when(validations.isLastNameValid(client.getLastName())).thenReturn(true);
    when(validations.isLastNameCount(client.getLastName())).thenReturn(true);
    when(validations.isBirthDateValid(client.getBirthDate())).thenReturn(true);
    when(validations.isBirthDateValidPrevious(client.getBirthDate())).thenReturn(true);
    when(validations.isGenderValid(client.getGender())).thenReturn(true);
    when(validations.isGenderOptionValid(client.getGender())).thenReturn(true);
    when(validations.isHealthProblemValid(client.getHealthProblem())).thenReturn(true);

    when(clientMapper.toClientDTO(any(ClientModel.class))).thenReturn(clientDTO);
    when(clientRepository.save(any(ClientModel.class))).thenReturn(client);

    clientService.create(client);

    verify(clientMapper).toClientDTO(client);
    verify(clientRepository).save(client);
    verify(validations).isHealthProblemValid(client.getHealthProblem());
  }
}
