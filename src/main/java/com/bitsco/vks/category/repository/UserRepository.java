package com.bitsco.vks.category.repository;

import com.bitsco.vks.category.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource
public interface UserRepository extends JpaRepository<User, Long> {
    @Query(value = "SELECT a FROM User a WHERE upper(a.username) = upper(:username)")
    User findFirstByUsername(String username);

    @Query(value = "SELECT a FROM User a WHERE 1 = 1 "
            + "AND(:username IS NULL OR a.username LIKE :username)"
            + "AND(:status = -1 OR :status IS NULL OR a.status = :status)"
    )
    List<User> getPage(
            @Param("username") String username,
            @Param("status") Integer status
    );

    @Query(value = "SELECT a FROM User a WHERE 1 = 1 "
            + "AND(:username IS NULL OR a.username LIKE (:username) )"
            + "AND(:name IS NULL OR a.name LIKE (:name) )"
            + "AND(:address IS NULL OR lower(a.address) LIKE (:address) )"
            + "AND(:email IS NULL OR lower(a.email) LIKE (:email) )"
            + "AND(:phone IS NULL OR lower(a.phone) LIKE (:phone) )"
            + "AND((:type = -1 AND a.type <> 4) OR (:type IS NULL AND a.type <> 4) OR a.type = :type)"
            + "AND(:status = -1 OR :status IS NULL OR a.status = :status)"
            + "ORDER BY a.type, a.username"
    )
    List<User> getList(
            @Param("username") String username,
            @Param("name") String name,
            @Param("address") String address,
            @Param("email") String email,
            @Param("phone") String phone,
            @Param("type") Integer type,
            @Param("status") Integer status
    );
}
