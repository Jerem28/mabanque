package org.jra.mabanque.dao;

import org.jra.mabanque.entities.Compte;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompteRepository extends JpaRepository<Compte, String>
{
}
