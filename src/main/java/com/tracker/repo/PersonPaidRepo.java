package com.tracker.repo;

import com.tracker.model.PersonPaid;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonPaidRepo extends CrudRepository<PersonPaid, Integer> {

}
