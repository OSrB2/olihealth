package com.olisaude.testedevback.repository;

import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;

import com.olisaude.testedevback.model.ClientModel;
import com.olisaude.testedevback.model.HealthProblemModel;

import jakarta.transaction.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;


@DataJpaTest
public class ClientRepositoryTest {
  SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

  @Autowired
  private TestEntityManager testEntityManager;

  @Autowired
  private ClientRepository clientRepository;

  @DisplayName("Should find a client by id")
  @Test
  public void testFindClientByID() throws Exception {
    Date birthDate = dateFormat.parse("1990-05-12");
    ClientModel client = new ClientModel();
    HealthProblemModel health = new HealthProblemModel();
    client.setName("Teste");
    client.setLastName("Teste");
    client.setBirthDate(birthDate);
    client.setGender("Male");
    health.setLevel(2);
    health.setName("Teste");
    client.setHealthProblem(Collections.singletonList(health));

    testEntityManager.persistAndFlush(client);

    Optional<ClientModel> clientModelOptional = clientRepository.findById(client.getId());

    assertTrue(clientModelOptional.isPresent(), "Client should be present");
    ClientModel foundClient = clientModelOptional.get();
    assertThat(foundClient.getId()).isEqualTo(client.getId());
    assertThat(foundClient.getName()).isEqualTo(client.getName());
    assertThat(foundClient.getLastName()).isEqualTo(client.getLastName());
    assertThat(foundClient.getBirthDate()).isEqualTo(client.getBirthDate());
    assertThat(foundClient.getGender()).isEqualTo(client.getGender());
    assertThat(foundClient.getHealthProblem()).hasSize(1);
    assertThat(foundClient.getHealthProblem().get(0).getName()).isEqualTo(health.getName());
    assertThat(foundClient.getHealthProblem().get(0).getLevel()).isEqualTo(health.getLevel());
  }
}
