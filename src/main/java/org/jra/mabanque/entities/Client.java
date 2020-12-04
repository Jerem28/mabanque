package org.jra.mabanque.entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Collection;

@Entity
public class Client implements Serializable
{
  @Id @GeneratedValue
  private Long code;
  @NotNull
  private String nom;
  @NotNull
  private String email;
  @OneToMany(mappedBy="client", fetch= FetchType.LAZY)
  private Collection<Compte> comptes;

  public Client()
  {
    super();
  }

  public Client(String nom, String email)
  {
    this.nom = nom;
    this.email = email;
  }

  public Long getCode()
  {
    return code;
  }

  public void setCode(Long code)
  {
    this.code = code;
  }

  public String getNom()
  {
    return nom;
  }

  public void setNom(String nom)
  {
    this.nom = nom;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }
}
