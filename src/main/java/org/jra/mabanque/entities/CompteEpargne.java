package org.jra.mabanque.entities;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.util.Date;

@Entity
@DiscriminatorValue("CE")
public class CompteEpargne extends Compte
{
  private double taux;

  public CompteEpargne()
  {
    super();
  }

  public CompteEpargne(String numCompte, Date dateCreation, Integer solde, Client client, double taux)
  {
    super(numCompte, dateCreation, solde, client);
    this.taux = taux;
  }

  public double getTaux()
  {
    return taux;
  }

  public void setTaux(double taux)
  {
    this.taux = taux;
  }
}
