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

  }
}
