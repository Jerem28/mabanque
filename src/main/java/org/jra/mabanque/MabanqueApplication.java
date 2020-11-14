package org.jra.mabanque;

import org.jra.mabanque.dao.ClientRepository;
import org.jra.mabanque.dao.CompteRepository;
import org.jra.mabanque.dao.OperationRepository;
import org.jra.mabanque.entities.*;
import org.jra.mabanque.services.IBanqueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

import java.util.Date;

@SpringBootApplication(exclude={SecurityAutoConfiguration.class})
public class MabanqueApplication implements CommandLineRunner
{
  @Autowired
  private ClientRepository clientRepository;

  @Autowired
  private CompteRepository compteRepository;

  @Autowired
  private OperationRepository operationRepository;

  @Autowired
  private IBanqueService banqueService;

  public static void main(String[] args)
  {
    SpringApplication.run(MabanqueApplication.class, args);
  }

  @Override
  public void run(String... args) throws Exception
  {
    Client c1 = clientRepository.save(new Client("Dany", "fake@gmail.com"));
    Client c2 = clientRepository.save(new Client("Edouard", "fake@gmail.com"));

    Compte cp1 = compteRepository.save(new CompteCourant("c1", new Date(), 90000, c1, 6000));
    Compte cp2 = compteRepository.save(new CompteCourant("c2", new Date(), 90000, c2, 6000));

    operationRepository.save(new Versement(new Date(), 9000, cp1));
    operationRepository.save(new Versement(new Date(), 6000, cp1));
    operationRepository.save(new Versement(new Date(), 2300, cp1));
    operationRepository.save(new Retrait(new Date(), 4000, cp1));

    operationRepository.save(new Versement(new Date(), 12000, cp2));
    operationRepository.save(new Versement(new Date(), 24000, cp2));
    operationRepository.save(new Versement(new Date(), 100, cp2));
    operationRepository.save(new Retrait(new Date(), 500, cp2));

    banqueService.verser("c1", 11);

  }
}
