package org.jra.mabanque.dao;

import org.jra.mabanque.entities.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client, Long>
{

}
