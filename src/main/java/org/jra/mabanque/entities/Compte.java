package org.jra.mabanque.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;
import java.util.Date;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="TYPE_CPTE", discriminatorType = DiscriminatorType.STRING, length = 2)
public abstract class Compte implements Serializable
{
  @Id
  private String numCompte;
  private Date dateCreation;
  private double solde;
  @ManyToOne
  @JoinColumn(name="CODE_CLI")
  private Client client;
  @OneToMany(mappedBy = "compte", fetch = FetchType.LAZY)
  private Collection<Operation> operations;

  public Compte()
  {
    super();
  }

  public Compte(String numCompte, Date dateCreation, Integer solde, Client client)
  {
    this.numCompte = numCompte;
    this.dateCreation = dateCreation;
    this.solde = solde;
    this.client = client;
  }

  public String getNumCompte()
  {
    return numCompte;
  }

  public void setNumCompte(String numCompte)
  {
    this.numCompte = numCompte;
  }

  public Date getDateCreation()
  {
    return dateCreation;
  }

  public void setDateCreation(Date dateCreation)
  {
    this.dateCreation = dateCreation;
  }

  public double getSolde()
  {
    return solde;
  }

  public void setSolde(double solde)
  {
    this.solde = solde;
  }
}
