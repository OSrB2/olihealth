package com.olisaude.testedevback.exception;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import com.olisaude.testedevback.exceptions.HandleValidationField;
import com.olisaude.testedevback.exceptions.Validations;
import com.olisaude.testedevback.model.ClientModel;
import com.olisaude.testedevback.model.HealthProblemModel;

@ExtendWith(MockitoExtension.class)
public class ValidationsTest {
  @InjectMocks
  private Validations validations;

  SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

  @DisplayName("Should validate if name is valid")
  @Test
  public void testIsNameValid() throws Exception {
    assertTrue(validations.isLastNameValid("Name"));
  }

  @DisplayName("Should validate if name is invalid")
  @Test
  public void testIsNameInvalid() throws Exception {
    assertThrows(HandleValidationField.class, () -> {
      validations.isLastNameValid(null);
    });
  }

  @DisplayName("Should validate that name contains least 3 characters")
  @Test
  public void testIsNameCountValid() throws Exception {
    assertTrue(validations.isLastNameCount("Name"));
  }

  @DisplayName("Should validate that name contains less than 3 characters")
  @Test
  public void testIsnameCountInvalid() throws Exception {
    assertThrows(HandleValidationField.class, () -> {
      validations.isLastNameCount("Na");
    });
  }

  @DisplayName("Should validate if the birthDate is lower then current date")
  @Test
  public void testIsBirthDateValid() throws Exception {
    Date birthDate = dateFormat.parse("1990-05-12");
    assertTrue(validations.isBirthDateValid(birthDate));
  }

  @DisplayName("Should validate if the birthDate is later then current date")
  @Test
  public void testIsBirthDateInvalid() throws Exception {
    Date birthDate = dateFormat.parse("2025-05-05");
    assertThrows(HandleValidationField.class, () -> {
      validations.isBirthDateValidPrevious(birthDate);
    });
  }

  @DisplayName("Should validate if gender is not null")
  @Test
  public void testIsGenderNotNull() throws Exception {
    assertTrue(validations.isGenderValid("Male"));
  }

  @DisplayName("Should validate if gender is null")
  @Test
  public void testIsGenderNull() throws Exception {
    assertThrows(HandleValidationField.class, () -> {
      validations.isGenderValid(null);
    });
  }

  @DisplayName("Should validate if gender option is valid")
  @Test
  public void testIsGenderOptionValid() throws Exception {
    assertTrue(validations.isGenderOptionValid("female"));
  }

  @DisplayName("Should validate if gender option is invalid")
  @Test
  public void testIsGenderOptionInvalid() throws Exception {
    assertThrows(HandleValidationField.class, () -> {
      validations.isGenderOptionValid("test");
    });
  }

  @DisplayName("Should validate if health problem is valid")
  @Test
  public void testIsHealthProblemIsValid() throws Exception {
    ClientModel client = new ClientModel();
    HealthProblemModel health = new HealthProblemModel();
    health.setLevel(2);
    health.setName("test");
    client.setHealthProblem(Collections.singletonList(health));

    assertTrue(validations.isHealthProblemValid(client.getHealthProblem()));
  }

  @DisplayName("Should validate if health problem is invalid")
  @Test
  public void testIsHealthProblemIsInvalid() throws Exception {
    assertThrows(HandleValidationField.class, () -> {
      validations.isHealthProblemValid(null);
    });
  }
}
