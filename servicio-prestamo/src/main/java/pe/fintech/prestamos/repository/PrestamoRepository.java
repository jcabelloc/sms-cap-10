package pe.fintech.prestamos.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import pe.fintech.prestamos.model.Prestamo;

@Repository
public interface PrestamoRepository extends JpaRepository<Prestamo, Integer> {

	List<Prestamo> findByCodCliente(Integer codCliente);

	Prestamo findByCodClienteAndCodPrestamo(Integer codCliente, Integer codPrestamo);

}
