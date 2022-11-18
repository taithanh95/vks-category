package com.bitsco.vks.category.repository;

import com.bitsco.vks.category.entities.Position;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource
public interface PositionRepository extends JpaRepository<Position, Long> {
    @Query(value = "SELECT a FROM Position a WHERE 1 = 1 "
            + "AND(:supplierId IS NULL OR a.supplierId = :supplierId)"
            + "AND(:name IS NULL OR a.name LIKE :name)"
            + "AND(:status = -1 OR :status IS NULL OR a.status = :status)"
    )
    List<Position> getList(
            @Param("supplierId") Long supplierId,
            @Param("name") String name,
            @Param("status") Integer status
    );

    List<Position> findBySupplierIdAndUserTypeAndStatusOrderById(Long supplierId, Integer userType, Integer status);
}
