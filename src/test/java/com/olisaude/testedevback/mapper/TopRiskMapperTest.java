package com.olisaude.testedevback.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import com.olisaude.testedevback.dto.TopRiskDTO;
import com.olisaude.testedevback.model.ClientModel;

@ExtendWith(MockitoExtension.class)
public class TopRiskMapperTest {
  @InjectMocks
  private TopRiskMapper topRiskMapper;
  
  @DisplayName("Should convert ClientModel to TopRiskDTO")
  @Test
  public void testClientModelToTopRiskDTO() throws Exception {
    ClientModel client = new ClientModel();
    client.setName("Test");
    client.setLastName("Test");
    client.setHealthRiskScore(50.0);

    TopRiskDTO topRiskDTO = topRiskMapper.toTopRiskDTO(client);

    assertEquals("Test", topRiskDTO.getName());
    assertEquals("Test", topRiskDTO.getLastName());
    assertEquals(50.0, topRiskDTO.getScore());
  }

}
