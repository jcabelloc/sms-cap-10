package pe.fintech.prestamos.services;

import java.util.List;

import pe.fintech.prestamos.model.Prestamo;

public interface PrestamoService {

	List<Prestamo> findByCodCliente(Integer codCliente);

	Prestamo findByCodClienteAndCodPrestamo(Integer codCliente, Integer codPrestamo);

	Prestamo create(Prestamo prestamo);

	void update(Prestamo prestamo);

	Prestamo findByCodClienteAndCodPrestamoAndTipoLlamada(Integer codCliente, Integer codPrestamo, String tipoLlamada);

}
