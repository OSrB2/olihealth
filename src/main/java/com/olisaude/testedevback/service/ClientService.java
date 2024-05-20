package com.olisaude.testedevback.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.olisaude.testedevback.dto.ClientDTO;
import com.olisaude.testedevback.exceptions.HandleIDNotFound;
import com.olisaude.testedevback.exceptions.HandleNoHasClients;
import com.olisaude.testedevback.exceptions.Validations;
import com.olisaude.testedevback.mapper.ClientMapper;
import com.olisaude.testedevback.model.ClientModel;
import com.olisaude.testedevback.model.HealthProblemModel;
import com.olisaude.testedevback.repository.ClientRepository;

@Service
public class ClientService {
  @Autowired
  ClientRepository clientRepository;

  @Autowired
  private ClientMapper clientMapper;

  @Autowired
  Validations validation;

  public ClientModel create (ClientModel clientModel) {
    ClientModel newClient = new ClientModel();
    
    if (validation.isNameValid(clientModel.getName()) && 
    validation.isNameCount(clientModel.getName())) {
      newClient.setName(clientModel.getName());
    }


    if (validation.isLastNameValid(clientModel.getLastName()) &&
    validation.isLastNameCount(clientModel.getLastName())) {
      newClient.setLastName(clientModel.getLastName());
    }


    if (validation.isBirthDateValid(clientModel.getBirthDate()) &&
    validation.isBirthDateValidPrevious(clientModel.getBirthDate())) {
      newClient.setBirthDate(clientModel.getBirthDate());
    }

    if (validation.isGenderValid(clientModel.getGender()) && 
    validation.isGenderOptionValid(clientModel.getGender().toLowerCase())) {
      newClient.setGender(clientModel.getGender().toLowerCase());
    }

    if (validation.isHealthProblemValid(clientModel.getHealthProblem())) {
      newClient.setHealthProblem(clientModel.getHealthProblem());
    }


    return clientRepository.save(newClient);
  }

  public List<ClientDTO> findAll() {
    List<ClientModel> clients = clientRepository.findAll();

    if (clients.isEmpty()) {
      throw new HandleNoHasClients("No clients found!");
    }

    List<ClientDTO> clientDTOs = new ArrayList<>();

    for (ClientModel clientModel : clients) {
      clientDTOs.add(clientMapper.toClientDTO(clientModel));
    }
    return clientDTOs;
  }

  public Optional<ClientModel> findClientById(Long id) {
    Optional<ClientModel> clientOptional = clientRepository.findById(id);

    if (clientOptional.isEmpty()) {
      throw new HandleIDNotFound("Client not found!");
    }

    return clientOptional;
  }

  public ClientModel updateClient(Long id, ClientModel clientModel)  {
    Optional<ClientModel> clientOptional = clientRepository.findById(id);

    if (clientOptional.isEmpty()) {
      throw new HandleIDNotFound("Client not found!");
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

  public void deleteClient(Long id) {
    Optional<ClientModel> clientModel = clientRepository.findById(id);

    if (clientModel.isEmpty()) {
      throw new HandleIDNotFound("Id not found");
    }
    clientRepository.deleteById(id);
  }

  public List<ClientModel> findTopClientsByHealthRisk() {
    List<ClientModel> allClients = clientRepository.findAll();
    return allClients.stream()
    .map(client -> {
      double sd = client.getHealthProblem().stream().mapToInt(HealthProblemModel::getLevel).sum();
      double score = Math.round(1 / (1 + Math.exp(-(-2.8 + sd)))) * 100;
      client.setHealthRiskScore(score);
      return client;
    })
    .sorted((c1, c2) -> Double.compare(c2.getHealthRiskScore(), c1.getHealthRiskScore()))
    .limit(10)
    .collect(Collectors.toList());
  }
}
