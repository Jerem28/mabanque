package org.jra.mabanque.services;

import org.jra.mabanque.entities.Client;
import org.jra.mabanque.entities.Compte;
import org.jra.mabanque.entities.Operation;
import org.jra.mabanque.exceptions.ClientIntrouvableException;
import org.jra.mabanque.exceptions.CompteIntrouvableException;
import org.jra.mabanque.exceptions.SoldeTotalInsuffisant;
import org.jra.mabanque.exceptions.VirementEntreLeMemeCompteImpossible;
import org.springframework.data.domain.Page;

import java.util.List;

public interface IBanqueService
{
  Compte consulterCompte(String codeCompte) throws CompteIntrouvableException;
  void verser(String codeCompte, double montant) throws CompteIntrouvableException;
  void retirer(String codeCompte, double montant) throws CompteIntrouvableException, SoldeTotalInsuffisant;
  void virement(String codeCompteDepuis, String codeCompteVers, double montant) throws CompteIntrouvableException, SoldeTotalInsuffisant, VirementEntreLeMemeCompteImpossible;
  Page<Operation> listerOperations(String codeCompte, int page, int size);
  List<Client> consulterClient(String codeClient) throws ClientIntrouvableException;
}
