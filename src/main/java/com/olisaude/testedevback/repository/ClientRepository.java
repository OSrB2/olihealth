package com.olisaude.testedevback.repository;

import org.springframework.stereotype.Repository;
import com.olisaude.testedevback.model.ClientModel;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface ClientRepository extends JpaRepository<ClientModel, Long> {

  
}
