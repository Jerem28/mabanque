package org.jra.mabanque.dao;

import org.jra.mabanque.entities.Client;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ClientRepository extends JpaRepository<Client, Long>
{
    List<Client> findByNom(String nomClient);
}
