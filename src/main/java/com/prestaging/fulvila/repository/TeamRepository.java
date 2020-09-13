package com.prestaging.fulvila.repository;

import com.prestaging.fulvila.model.Team;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

public interface TeamRepository extends CrudRepository<Team, Integer> {
    @Transactional
    @Modifying
    @Query(value = "DELETE FROM fulvila_team WHERE admin_id = ?1", nativeQuery = true)
    int deleteByAdminId(int id);

    @Transactional
    @Modifying
    @Query(value = "DELETE from fulvila_team WHERE id = ?1 AND admin_id = ?2", nativeQuery = true)
    int deleteByAdminIDAndID(int id, int adminID);
}
