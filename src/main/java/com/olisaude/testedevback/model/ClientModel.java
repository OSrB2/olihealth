package com.olisaude.testedevback.model;

import java.util.Date;
import java.util.List;

import org.hibernate.validator.constraints.Length;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "tb_client")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ClientModel {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Length(min = 3, max = 100, message = "O nome deve conter no máximo {max} caractéres")
  private String name;

  @Length(min = 3, max = 100, message = "O sobrenome deve conter no máximo {max} caractéres")
  private String lastName;

  @Temporal(TemporalType.DATE)
  private Date birthDate;

  private String gender;

  @OneToMany(cascade = CascadeType.ALL)
  @JoinColumn(name = "client_id")
  private List<HealthProblemModel> healthProblem;

  @Transient
  private double healthRiskScore;

  @Temporal(TemporalType.TIMESTAMP)
  @Column(name = "created_at", nullable = false, updatable = false)
  private Date created_At;

  @Temporal(TemporalType.TIMESTAMP)
  @Column(name = "updated_at", nullable = false)
  private Date updated_At;
  

  @PrePersist
  protected void onCreate() {
    Date now = new Date();
    this.created_At = now;
    this.updated_At = now;
  }

  @PreUpdate
  protected void onUpdate() {
    this.updated_At = new Date();
  }
}
