package org.jra.mabanque.dao;

import org.jra.mabanque.entities.Operation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface OperationRepository extends JpaRepository<Operation, Long>
{
  @Query("select o from Operation o where o.compte.numCompte=:x order by o.dateOperation desc")
  Page<Operation> listerOperations(@Param("x")String codeCompte, Pageable pageable);
}
