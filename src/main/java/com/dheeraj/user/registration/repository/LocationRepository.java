package com.dheeraj.user.registration.repository;

import com.dheeraj.user.registration.model.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by dheeraj on 13/09/17.
 */
public interface LocationRepository extends JpaRepository<Location, Long> {

    List<Location> findAllByUserId(@Param("userId") long userId);
}
