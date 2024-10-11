package com.olisaude.testedevback.mapper;

import org.springframework.stereotype.Component;

import com.olisaude.testedevback.dto.ClientDTO;
import com.olisaude.testedevback.model.ClientModel;

@Component
public class ClientMapper {
  public ClientDTO toClientDTO(ClientModel clientModel) {
    ClientDTO clientDTO = new ClientDTO();

    clientDTO.setName(clientModel.getName());
    clientDTO.setLastName(clientModel.getLastName());
    clientDTO.setBirthDate(clientModel.getBirthDate());
    clientDTO.setGender(clientModel.getGender());

    return clientDTO;
  }
}
