package org.jra.mabanque.entities;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.util.Date;

@Entity
@DiscriminatorValue("CC")
public class CompteCourant extends Compte
{
  private double decouvert;

  public CompteCourant()
  {
    super();
  }

  public CompteCourant(String numCompte, Date dateCreation, Integer solde, Client client, double decouvert)
  {
    super(numCompte, dateCreation, solde, client);
    this.decouvert = decouvert;
  }

  public double getDecouvert()
  {
    return decouvert;
  }

  public void setDecouvert(double decouvert)
  {
    this.decouvert = decouvert;
  }
}
