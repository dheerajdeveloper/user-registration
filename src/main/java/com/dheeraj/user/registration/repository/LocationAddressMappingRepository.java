package com.dheeraj.user.registration.repository;

import com.dheeraj.user.registration.model.LocationAddressMapping;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

/**
 * Created by dheeraj on 06/10/17.
 */

@RepositoryRestResource
public interface LocationAddressMappingRepository extends JpaRepository<LocationAddressMapping, Long> {

    LocationAddressMapping findFirstByLatAndLng(@Param("lat") String lat, @Param("lng") String lng);
}
