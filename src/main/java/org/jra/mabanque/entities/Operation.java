package org.jra.mabanque.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "type_op", discriminatorType = DiscriminatorType.STRING, length = 1)
public abstract class Operation implements Serializable
{
  @Id @GeneratedValue
  private Long numOperation;
  private Date dateOperation;
  private double montant;
  @ManyToOne
  @JoinColumn(name="code_cpte")
  private Compte compte;

  public Operation()
  {
    super();
  }

  public Operation(Date dateOperation, double montant, Compte compte)
  {
    this.dateOperation = dateOperation;
    this.montant = montant;
    this.compte = compte;
  }

  public Long getNumOperation()
  {
    return numOperation;
  }

  public void setNumOperation(long numOperation)
  {
    this.numOperation = numOperation;
  }

  public Date getDateOperation()
  {
    return dateOperation;
  }

  public void setDateOperation(Date dateOperation)
  {
    this.dateOperation = dateOperation;
  }

  public double getMontant()
  {
    return montant;
  }

  public void setMontant(double montant)
  {
    this.montant = montant;
  }
}
