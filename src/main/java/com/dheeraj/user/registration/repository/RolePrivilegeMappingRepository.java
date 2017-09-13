package com.dheeraj.user.registration.repository;

import com.dheeraj.user.registration.model.RolePrivilegeMapping;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by dheeraj on 13/09/17.
 */

public interface RolePrivilegeMappingRepository extends JpaRepository<RolePrivilegeMapping, Long> {
}
