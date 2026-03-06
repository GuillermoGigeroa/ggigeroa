package ggigeroa.impresora.runner.impl;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ggigeroa.impresora.runner.model.Imagen;

@Repository
public interface ImagenRepository extends JpaRepository<Imagen, Long> {
	
}
