package com.dheeraj.user.registration.repository;

import com.dheeraj.user.registration.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

/**
 * Created by dheeraj on 13/09/17.
 */

@RepositoryRestResource
public interface RoleRepository extends JpaRepository<Role, Long>{

    Role findFirstByName(@Param("name") String roleName);
}
