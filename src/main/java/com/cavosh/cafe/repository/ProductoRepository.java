package com.cavosh.cafe.repository;

import com.cavosh.cafe.model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long>{

    List<Producto> findByDisponibleTrue();

    List<Producto> findByCategoriaNombreIgnoreCase(String categoria);

    List<Producto> findByNuevoTrueAndDisponibleTrue();

    List<Producto> findByPopularTrueAndDisponibleTrue();

    @Query("SELECT p FROM Producto p WHERE LOWER(p.nombre) LIKE LOWER(CONCAT('%', :nombre, '%'))")
    List<Producto> buscarPorNombre(@Param("nombre") String nombre);

    List<Producto> findByCafeIdAndDisponibleTrue(Long cafeid);

}
