package com.accounting.merchandiseAccounting.repository;

import com.accounting.merchandiseAccounting.model.Incidents;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IncidentsRepository extends CrudRepository<Incidents, Long> {
}
