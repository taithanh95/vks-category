package com.bitsco.vks.category.repository;

import com.bitsco.vks.category.entities.PositionGroupRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource
public interface PositionGroupRoleRepository extends JpaRepository<PositionGroupRole, Long> {
    PositionGroupRole findFirstBySupplierIdAndPositionIdAndGroupRoleIdAndStatus(long supplierId, long positionId, long groupRoleId, int status);

    @Query(value = "SELECT a FROM PositionGroupRole a WHERE 1 = 1 "
            + "AND(:supplierId IS NULL OR a.supplierId = :supplierId)"
            + "AND(:positionId IS NULL OR a.positionId = :positionId)"
            + "AND(:groupRoleId IS NULL OR a.groupRoleId = :groupRoleId)"
            + "AND(:status = -1 OR :status IS NULL OR a.status = :status)"
    )
    List<PositionGroupRole> getList(
            @Param("supplierId") Long supplierId,
            @Param("positionId") Long positionId,
            @Param("groupRoleId") Long groupRoleId,
            @Param("status") Integer status
    );

    @Query(value = " SELECT                                               "
            + "     n_group_role_id                                  "
            + " FROM                                                 "
            + "     eztech.position_group_role                       "
            + " WHERE                                                "
            + "     1 = 1 AND n_supplier_id = :supplierId                      "
            + "         AND n_status = 1                             "
            + "         AND n_position_id IN (SELECT                 "
            + "             n_id                                     "
            + "         FROM                                         "
            + "             eztech.position                          "
            + "         WHERE                                        "
            + "             1 = 1 AND n_supplier_id = :supplierId              "
            + "                 AND n_user_type = :userType                  "
            + "                 AND n_status = 1)                    "
            , nativeQuery = true
    )
    List<Long> getListGroupRoleIdBySupplierIdAndUserType(
            @Param("supplierId") Long supplierId,
            @Param("userType") Integer userType
    );

    Boolean existsBySupplierIdAndPositionIdAndGroupRoleIdAndStatus(long supplierId, long positionId, long groupRoleId, int status);
}
