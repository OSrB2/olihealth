package com.olisaude.testedevback.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.olisaude.testedevback.model.ClientModel;
import com.olisaude.testedevback.repository.ClientRepository;

@Service
public class ClientService {
  @Autowired
  ClientRepository clientRepository;

  public ClientModel create (ClientModel clientModel) {
    return clientRepository.save(clientModel);
  }

  public List<ClientModel> findAll() {
    return clientRepository.findAll();
  }

  public Optional<ClientModel> findClientById(Long id) {
    return clientRepository.findById(id);
  }

  public ClientModel updateClient(Long id, ClientModel clientModel) throws Exception {
    Optional<ClientModel> clientOptional = clientRepository.findById(id);

    if (clientOptional.isEmpty()) {
      throw new Exception("Client not found!");
    }
    
    ClientModel client = clientOptional.get();
    if (client.getName() != null) {
      client.setName(clientModel.getName());
    }

    if (client.getLastName() != null) {
      client.setLastName(clientModel.getLastName());
    }

    if (client.getBirthDate() != null) {
      client.setBirthDate(clientModel.getBirthDate());
    }

    if (client.getGender() != null) {
      client.setGender(clientModel.getGender());
    }

    if (client.getHealthProblem() != null) {
      client.setHealthProblem(clientModel.getHealthProblem());
    }

    return clientRepository.save(client);
  }

  public void deleteClient(Long id) throws Exception {
    Optional<ClientModel> clientModel = clientRepository.findById(id);

    if (clientModel.isEmpty()) {
      throw new Exception("Id not found");
    }
    clientRepository.deleteById(id);
  }
}
