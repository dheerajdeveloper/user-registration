package com.dheeraj.user.registration.repository;

import com.dheeraj.user.registration.model.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

/**
 * Created by dheeraj on 13/09/17.
 */
@RepositoryRestResource
public interface LocationRepository extends JpaRepository<Location, Long> {

    List<Location> findAllByUserIdOrderByLocationtimeDesc(@Param("userId") long userId);

    Location findFirstByUserIdOrderByLocationtimeDesc(@Param("userId") long userId);

    List<Location> findAllByUserIdAndLocationtimeIsAfterOrderByLocationtimeDesc(@Param("userId") long userId ,
                                                                                @Param("locationtime") String locationtime);


    List<Location> findAllByUserIdAndLocationtimeIsAfterOrderByLocationtimeAsc(@Param("userId") long userId ,
                                                                                @Param("locationtime") String locationtime);

    List<Location> findAllByUserIdAndLocationtimeIsBetweenOrderByLocationtimeDesc(@Param("userId") long userId ,
                                                           @Param("starttime") String starttime,
                                                           @Param("endtime") String endtime);

    List<Location> findAllByLocationScannedIsNullOrderByLocationtime();

    Location findFirstByIdIsLessThanAndLocationScannedIsNotNullOrderByIdDesc(@Param("id") long id);
}
