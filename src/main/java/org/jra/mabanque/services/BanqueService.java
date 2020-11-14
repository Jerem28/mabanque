package org.jra.mabanque.services;

import org.jra.mabanque.dao.CompteRepository;
import org.jra.mabanque.dao.OperationRepository;
import org.jra.mabanque.entities.*;
import org.jra.mabanque.exceptions.CompteIntrouvableException;
import org.jra.mabanque.exceptions.SoldeTotalInsuffisant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;

@Service
@Transactional
public class BanqueService implements IBanqueService
{
  @Autowired
  private CompteRepository compteRepository;

  @Autowired
  private OperationRepository operationRepository;

  @Override
  public Compte consulterCompte(String codeCompte) throws CompteIntrouvableException
  {
    Compte cp = compteRepository.findById(codeCompte).orElse(null);
    if(cp == null) throw new CompteIntrouvableException("Compte [" + codeCompte + "] introuvable");
    return cp;
  }

  @Override
  public void verser(String codeCompte, double montant) throws CompteIntrouvableException
  {
    Compte cp = consulterCompte(codeCompte);

    Versement versement = new Versement(new Date(), montant, cp);
    operationRepository.save(versement);

    cp.setSolde(cp.getSolde() + montant);
    compteRepository.save(cp);
  }

  @Override
  public void retirer(String codeCompte, double montant) throws CompteIntrouvableException, SoldeTotalInsuffisant
  {
    Compte cp = consulterCompte(codeCompte);
    double facilitesCaisse = 0;

    Retrait retrait = new Retrait(new Date(), montant, cp);
    operationRepository.save(retrait);

    if(cp instanceof CompteCourant){
      facilitesCaisse = ((CompteCourant) cp).getDecouvert();
      if(cp.getSolde() + facilitesCaisse < montant) {
        throw new SoldeTotalInsuffisant("Solde total insuffisant pour le compte [" + codeCompte + "]");
      } else {
        cp.setSolde(cp.getSolde() - montant);
        compteRepository.save(cp);
      }
    }

  }

  @Override
  public void virement(String codeCompteDepuis, String codeCompteVers, double montant) throws CompteIntrouvableException, SoldeTotalInsuffisant
  {
    retirer(codeCompteDepuis, montant);
    verser(codeCompteVers, montant);
  }

  @Override
  public Page<Operation> listerOperations(String codeCompte, int page, int size)
  {
    return operationRepository.listerOperations(codeCompte, PageRequest.of(page, size));
  }
}
