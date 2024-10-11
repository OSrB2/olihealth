package com.olisaude.testedevback.exceptions;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Component;

import com.olisaude.testedevback.model.HealthProblemModel;

@Component
public class Validations {
  public boolean isNameValid(String name) {
    if (name == null || name.isEmpty() || name.isBlank()) {
      throw new HandleValidationField("Name is mandatory!");
    }
    return true;
  }  

  public boolean isNameCount(String name) {
    if (name.length() < 3) {
      throw new HandleValidationField("Name must contain at least 3 characters!");
    }
    return true;
  }

  public boolean isLastNameValid(String lastName) {
    if (lastName == null || lastName.isEmpty() || lastName.isBlank()) {
      throw new HandleValidationField("Lastname is mandatory!");
    }
    return true;
  }

  public boolean isLastNameCount(String lastName) {
    if (lastName.length() < 3) {
      throw new HandleValidationField("Lastname must contain an least 3 characters!");
    }
    return true;
  }

  public boolean isBirthDateValid(Date birthDate) {
    if (birthDate == null) {
      throw new HandleValidationField("Birth date is mandatory!");
    }
    return true;
  }

  public boolean isBirthDateValidPrevious(Date birthDate) {
    Date current = Date.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant());

    if (birthDate.after(current)) {
      throw new HandleValidationField("Date of birth cannot be later than the current date");
    }
    return true;
  }

  public boolean isGenderValid(String gender) {
    if (gender == null) {
      throw new HandleValidationField("Gender is mandatory!");
    }
    return true;
  }

  public boolean isGenderOptionValid(String gender) {
    if (!(gender.equals("male") || gender.equals("female") || gender.equals("uninformed"))) {
        throw new HandleValidationField("Enter valid information: male, female, or uninformed");
    }
    return true;
}

  public boolean isHealthProblemValid(List<HealthProblemModel> healthProblem) {
    if (healthProblem == null || healthProblem.isEmpty()) {
      throw new HandleValidationField("Health problems are mandatory!");
    }
    return true;
  }
}
