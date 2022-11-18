package com.bitsco.vks.category.repository;

import com.bitsco.vks.category.entities.Village;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource
public interface VillageRepository extends JpaRepository<Village, Long> {
    Village findFirstByCode(String code);

    List<Village> findByCommuneCodeAndStatusOrderByName(String communeCode, int status);

    @Query(value = "SELECT a FROM Village a WHERE 1 = 1 "
            + "AND(:code IS NULL OR a.code = :code)"
            + "AND(:name IS NULL OR a.nameNoSign LIKE :name)"
            + "AND(:status = -1 OR :status IS NULL OR a.status = :status)"
            + "AND(:communeCode IS NULL OR a.communeCode = :communeCode)"
            + "AND(:provinceCode IS NULL OR a.provinceCode = :provinceCode)"
            + "AND(:districtCode IS NULL OR a.districtCode = :districtCode)"
    )
    List<Village> getPage(
            @Param("code") String code,
            @Param("name") String name,
            @Param("status") Integer status,
            @Param("communeCode") String communeCode,
            @Param("districtCode") String districtCode,
            @Param("provinceCode") String provinceCode
    );
}
