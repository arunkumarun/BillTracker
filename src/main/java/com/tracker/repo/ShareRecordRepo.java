package com.tracker.repo;

import com.tracker.model.Bill;
import com.tracker.model.ShareRecord;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

@Repository
public interface ShareRecordRepo extends CrudRepository<ShareRecord, Integer> {

    @Query(value = "from Bill b where b.date BETWEEN :dateFrom AND :dateTo")
    public List<Bill> findAllBillsBetweenDates(@Param("dateFrom") @NotNull Date dateFrom, @Param("dateTo") @NotNull Date dateTo);

}
