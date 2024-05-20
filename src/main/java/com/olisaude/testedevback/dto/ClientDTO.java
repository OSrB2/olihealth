package com.olisaude.testedevback.dto;

import java.util.Date;

import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Component
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ClientDTO {
  private String name;
  private String lastName;
  private Date birthDate;
  private String gender;
}
