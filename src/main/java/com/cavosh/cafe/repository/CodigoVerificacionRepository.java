package com.cavosh.cafe.repository;

import com.cavosh.cafe.model.CodigoVerificacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface CodigoVerificacionRepository extends JpaRepository<CodigoVerificacion, Long>{

    Optional<CodigoVerificacion> findByEmailAndCodigoAndUsadoFalse(String email, String codigo);

    void deleteByEmailAndUsadoFalse(String email);

}
