package com.bitsco.vks.category.repository;

import com.bitsco.vks.category.entities.Commune;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Truong Nguyen
 * Date: 06-Dec-18
 * Time: 10:03 AM
 * To change this template use File | Settings | File Templates.
 */
@RepositoryRestResource
public interface CommuneRepository extends JpaRepository<Commune, Long> {

    Commune findFirstByCode(String code);

    List<Commune> findByDistrictCodeAndStatusOrderByName(String districtCode, int status);

    @Query(value = "SELECT a FROM Commune a WHERE 1 = 1 "
            + "AND(:code IS NULL OR a.code = :code)"
            + "AND(:name IS NULL OR a.nameNoSign LIKE :name)"
            + "AND(:status = -1 OR :status IS NULL OR a.status = :status)"
            + "AND(:provinceCode = '-1' OR :provinceCode IS NULL OR a.provinceCode = :provinceCode)"
            + "AND(:districtCode = '-1' OR :districtCode IS NULL OR a.districtCode = :districtCode)"
    )
    List<Commune> getPage(
            @Param("code") String code,
            @Param("name") String name,
            @Param("status") Integer status,
            @Param("provinceCode") String provinceCode,
            @Param("districtCode") String districtCode
    );
}
