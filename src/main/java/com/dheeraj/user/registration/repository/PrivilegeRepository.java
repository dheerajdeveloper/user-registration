package com.dheeraj.user.registration.repository;

import com.dheeraj.user.registration.model.Privilege;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

/**
 * Created by dheeraj on 13/09/17.
 */

@RepositoryRestResource
public interface PrivilegeRepository extends JpaRepository<Privilege, Long>{

    Privilege findFirstByName(@Param("privilegeName") String privilegeName);
}
