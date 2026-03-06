package ggigeroa.impresora.runner.impl;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ggigeroa.impresora.runner.model.Registro;

@Repository
public interface RegistroRepository extends JpaRepository<Registro, Long> {
	
}
