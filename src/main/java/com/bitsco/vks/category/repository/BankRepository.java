package com.bitsco.vks.category.repository;

import com.bitsco.vks.category.entities.Bank;
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
public interface BankRepository extends JpaRepository<Bank, Long> {
    Bank findFirstByCode(String code);

    @Query(value = "SELECT a FROM Bank a WHERE 1 = 1 "
            + "AND(:code IS NULL OR a.code = :code)"
            + "AND(:name IS NULL OR a.name LIKE :name)"
            + "AND(:status = -1 OR :status IS NULL OR a.status = :status)"
    )
    List<Bank> getList(
            @Param("code") String code,
            @Param("name") String name,
            @Param("status") Integer status
    );

}
