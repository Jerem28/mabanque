package org.jra.mabanque.web;

import org.jra.mabanque.entities.Client;
import org.jra.mabanque.entities.Compte;
import org.jra.mabanque.entities.Operation;
import org.jra.mabanque.services.IBanqueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@Controller
public class BanqueController
{
  @Autowired
  private IBanqueService banqueService;

  @RequestMapping(value = {"/", "/comptes"})
  public String index(){
    return "comptes";
  }

  @RequestMapping("/consulterCompte")
  public String consulter(Model model, String codeCompte){
    model.addAttribute("codeCompte", codeCompte);
    try{
      Compte cp = banqueService.consulterCompte(codeCompte);
      Page<Operation> pageOperations = banqueService.listerOperations(codeCompte, 0, 4);
      model.addAttribute("listeOperations", pageOperations.getContent());
      model.addAttribute("compte", cp);
    } catch (Exception e){
      model.addAttribute("exception", e);
    }
    return "comptes";
  }

  @RequestMapping(value={"consulterClient","/clients"})
  public String consulterClient(Model model, String nomClient){
    model.addAttribute("nomClient", nomClient);
    try{
      List<Client> clients = banqueService.consulterClient(nomClient);
      model.addAttribute("clients", clients);
    } catch (Exception e){
      model.addAttribute("exception", e);
    }
    return "clients";
  }

  @RequestMapping(value="/enregistrerOperations", method= RequestMethod.POST)
  public String enregistrerOperation(Model model,
                                     String typeOperation,
                                     String codeCompte,
                                     double montant,
                                     String codeCompte2){
    try{
      if(typeOperation.equals("VERS")){
        banqueService.verser(codeCompte, montant);
      } else if (typeOperation.equals("RETR")){
        banqueService.retirer(codeCompte, montant);
      } else if (typeOperation.equals("VIRE")){
        banqueService.virement(codeCompte, codeCompte2, montant);
      }
    } catch (Exception e){
      model.addAttribute("error", e);
      return "redirect:/consulterCompte?codeCompte=" + codeCompte + "&error=" + e.getMessage();
    }
    return "redirect:/consulterCompte?codeCompte=" + codeCompte;
  }
}
