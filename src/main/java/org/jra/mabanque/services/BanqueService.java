package org.jra.mabanque.services;

import org.jra.mabanque.dao.ClientRepository;
import org.jra.mabanque.dao.CompteRepository;
import org.jra.mabanque.dao.OperationRepository;
import org.jra.mabanque.entities.*;
import org.jra.mabanque.exceptions.ClientIntrouvableException;
import org.jra.mabanque.exceptions.CompteIntrouvableException;
import org.jra.mabanque.exceptions.SoldeTotalInsuffisant;
import org.jra.mabanque.exceptions.VirementEntreLeMemeCompteImpossible;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class BanqueService implements IBanqueService
{
  @Autowired
  private CompteRepository compteRepository;

  @Autowired
  private OperationRepository operationRepository;

  @Autowired
  private ClientRepository clientRepository;

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
        throw new SoldeTotalInsuffisant("Solde total insuffisant");
      } else {
        cp.setSolde(cp.getSolde() - montant);
        compteRepository.save(cp);
      }
    } else if(cp instanceof CompteEpargne){
      if(cp.getSolde() < montant) throw new SoldeTotalInsuffisant("Solde total insuffisant");
      else {
        cp.setSolde(cp.getSolde() - montant);
        compteRepository.save(cp);
      }
    }

  }

  @Override
  public void virement(String codeCompteDepuis, String codeCompteVers, double montant)
          throws CompteIntrouvableException, SoldeTotalInsuffisant, VirementEntreLeMemeCompteImpossible
  {
    if(codeCompteDepuis.equals(codeCompteVers)) throw new VirementEntreLeMemeCompteImpossible("Impossible de virer de l'argent de ce compte vers ce meme compte");
    else {
      retirer(codeCompteDepuis, montant);
      verser(codeCompteVers, montant);
    }
  }

  @Override
  public Page<Operation> listerOperations(String codeCompte, int page, int size)
  {
    return operationRepository.listerOperations(codeCompte, PageRequest.of(page, size));
  }

  @Override
  public List<Client> consulterClient(String nomClient) throws ClientIntrouvableException {
    List<Client> clients = this.clientRepository.findByNom(nomClient);
    if(clients.isEmpty()) throw new ClientIntrouvableException("Aucun client ne porte le nom " + nomClient);
    else return clients;
  }

  @Override
  public Long creerClient(Client nouveauClient){
    this.clientRepository.save(nouveauClient);
    return nouveauClient.getCode();
  }
}