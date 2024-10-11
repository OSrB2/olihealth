package com.olisaude.testedevback.mapper;

import org.springframework.stereotype.Component;

import com.olisaude.testedevback.dto.TopRiskDTO;
import com.olisaude.testedevback.model.ClientModel;

@Component
public class TopRiskMapper {
  public TopRiskDTO toTopRiskDTO(ClientModel clientModel) {
    TopRiskDTO topRiskDTO = new TopRiskDTO();

    topRiskDTO.setName(clientModel.getName());
    topRiskDTO.setLastName(clientModel.getLastName());
    topRiskDTO.setScore(clientModel.getHealthRiskScore());

    return topRiskDTO;
  }
}
