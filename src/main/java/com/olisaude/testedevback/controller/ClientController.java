package com.olisaude.testedevback.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.olisaude.testedevback.dto.ClientDTO;
import com.olisaude.testedevback.model.ClientModel;
import com.olisaude.testedevback.service.ClientService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("api/client")
public class ClientController {
  @Autowired
  ClientService clientService;

  @Autowired
  ClientDTO clientDTO;

  @PostMapping
  public ClientModel register(@Valid @RequestBody ClientModel clientModel) {
    return clientService.create(clientModel);
  }

  @GetMapping 
  public ResponseEntity<List<ClientDTO>> listAllCLients() throws Exception {
    return ResponseEntity.ok(clientService.findAll());
  }

  @GetMapping(path = "/{id}")
  public Optional<ClientModel> findById(@PathVariable Long id) {
    return clientService.findClientById(id);
  }

  @PutMapping(path = "/{id}")
  public ResponseEntity<?> updateClient(@PathVariable Long id, @RequestBody ClientModel clientModel) throws Exception {
    return ResponseEntity.ok(clientService.updateClient(id, clientModel));
  }

  @DeleteMapping(path = "/{id}")
  public ResponseEntity delete(@PathVariable Long id) throws Exception {
    clientService.deleteClient(id);
    return ResponseEntity.noContent().build();
  }
}
