package com.prestaging.fulvila.repository;

import com.prestaging.fulvila.model.BusinessDetails;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface BusinessDetailRepository extends CrudRepository<BusinessDetails, Integer> {
    @Transactional
    @Modifying
    @Query(value = "UPDATE fulvila_business_details SET admin_id = 0 WHERE admin_id = ?1", nativeQuery = true)
    int updateReference(int id);

    @Transactional
    @Modifying
    @Query(value = "DELETE from fulvila_business_details WHERE id = ?1 AND admin_id = ?2", nativeQuery = true)
    int deleteByAdminIDAndID(int id, int adminID);
}
