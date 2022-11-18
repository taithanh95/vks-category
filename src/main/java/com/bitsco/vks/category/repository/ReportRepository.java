package com.bitsco.vks.category.repository;

import com.bitsco.vks.category.entities.Report;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

/**
 * Created by IntelliJ IDEA.
 * User: Nguyen Tai Thanh <taithanh95.dev@gmail.com>
 * Date: 28/12/2021
 * Time: 11:31 AM
 */
@RepositoryRestResource
public interface ReportRepository extends JpaRepository<Report, Long> {
    @Query(value = "SELECT a FROM Report a WHERE 1 = 1 "
            + "AND(:reportCode IS NULL OR a.reportCode = :reportCode)"
            + "AND(:name IS NULL OR upper(a.name) LIKE :name)"
            + "AND(:level IS NULL OR a.level = :level)"
            + "AND(:status = -1 OR :status IS NULL OR a.status = :status)"
            + "ORDER BY a.name ASC"
    )
    Page<Report> getList(
            @Param("reportCode") String reportCode,
            @Param("name") String name,
            @Param("level") Integer level,
            @Param("status") Integer status,
            Pageable pageable
    );

//    @Query(value = "SELECT a FROM Report a WHERE 1 = 1 "
//            + "AND(:code IS NULL OR a.code = :code)"
//            + "AND(:name IS NULL OR a.name LIKE :name)"
//            + "AND(:level IS NULL OR a.level = :level)"
//            + "AND(:status = -1 OR :status IS NULL OR a.status = :status)", nativeQuery = true
//    )
//    List<Report> getListByPerent(
//            @Param("code") String code,
//            @Param("name") String name,
//            @Param("level") Integer level,
//            @Param("status") Integer status
//    );
}
