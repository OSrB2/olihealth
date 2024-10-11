package com.olisaude.testedevback.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import com.olisaude.testedevback.dto.ClientDTO;
import com.olisaude.testedevback.model.ClientModel;
import com.olisaude.testedevback.model.HealthProblemModel;



@ExtendWith(MockitoExtension.class)
public class ClientMapperTest {
  SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
  
  @InjectMocks
  private ClientMapper clientMapper;

  @DisplayName("Should convert ClientModel to ClientDTO")
  @Test
  public void testClientToDTO() throws ParseException {
    Date birthDate = dateFormat.parse("1990-05-12");
    ClientModel client = new ClientModel();
    HealthProblemModel health = new HealthProblemModel();

    client.setName("Test");
    client.setLastName("Test");
    client.setBirthDate(birthDate);
    client.setGender("Male");
    health.setLevel(2);
    health.setName("Teste");
    client.setHealthProblem(Collections.singletonList(health));

    ClientDTO clientDTO = clientMapper.toClientDTO(client);

    assertEquals("Test", clientDTO.getName());
    assertEquals("Test", clientDTO.getLastName());
    assertEquals(birthDate, clientDTO.getBirthDate());
    assertEquals("Male", clientDTO.getGender());
  }

}
