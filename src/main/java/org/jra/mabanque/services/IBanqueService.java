package org.jra.mabanque.services;

import org.jra.mabanque.entities.Compte;
import org.jra.mabanque.entities.Operation;
import org.jra.mabanque.exceptions.CompteIntrouvableException;
import org.jra.mabanque.exceptions.SoldeTotalInsuffisant;
import org.springframework.data.domain.Page;

public interface IBanqueService
{
  Compte consulterCompte(String codeCompte) throws CompteIntrouvableException;
  void verser(String codeCompte, double montant) throws CompteIntrouvableException;
  void retirer(String codeCompte, double montant) throws CompteIntrouvableException, SoldeTotalInsuffisant;
  void virement(String codeCompteDepuis, String codeCompteVers, double montant) throws CompteIntrouvableException, SoldeTotalInsuffisant;
  Page<Operation> listerOperations(String codeCompte, int page, int size);
}
