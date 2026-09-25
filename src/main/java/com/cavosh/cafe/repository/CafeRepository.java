package com.cavosh.cafe.repository;

import com.cavosh.cafe.model.Cafe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CafeRepository extends JpaRepository<Cafe, Long> {

    List<Cafe> findByActivoTrue();

    List<Cafe> findByCiudadIgnoreCase(String ciudad);

    @Query("SELECT c FROM Cafe c WHERE " +
            "(6371 * acos(cos(radians(:lat)) * cos(radians(c.latitud)) * " +
            "cos(radians(c.longitud) - radians(:lng)) + " +
            "sin(radians(:lat)) * sin(radians(c.latitud)))) < :radioKm")
    List<Cafe> findByCercania(@Param("lat") Double lat,
                              @Param("lng") Double lng,
                              @Param("radioKm") Double radioKm);
}