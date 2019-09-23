package pe.fintech.prestamos.controllers;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import pe.fintech.prestamos.config.FintechConfig;
import pe.fintech.prestamos.model.Prestamo;
import pe.fintech.prestamos.services.PrestamoService;
import pe.fintech.prestamos.utils.UserContextHolder;

@RestController
@RequestMapping(path="v1/clientes/{codCliente}/prestamos")
public class PrestamoController {

	private static final Logger logger = LoggerFactory.getLogger(PrestamoController.class);
	
	@Autowired
	FintechConfig fintechConfig;
	
	@Autowired
	PrestamoService prestamoService;
	
	@GetMapping(value="/{codPrestamo}/{tipoLlamada}")
    public Prestamo getPrestamoWithTipoLlamada(@PathVariable("codCliente") Integer codCliente,
                                          @PathVariable("codPrestamo") Integer codPrestamo,
                                          @PathVariable("tipoLlamada") String tipoLlamada) {

        return prestamoService.findByCodClienteAndCodPrestamoAndTipoLlamada(codCliente,codPrestamo, tipoLlamada);
    }
	

	@GetMapping("")
	public List<Prestamo> getPrestamos(@PathVariable Integer codCliente) {
        logger.info("PrestamoController Correlation id: {}", UserContextHolder.getContext().getCorrelationId());
	
		return prestamoService.findByCodCliente(codCliente);
	}

	
	@GetMapping("/{codPrestamo}")
	public Prestamo getPrestamo(@PathVariable Integer codCliente, @PathVariable Integer codPrestamo) {
		return prestamoService.findByCodClienteAndCodPrestamo(codCliente, codPrestamo);
	}
	
	@PostMapping("")
	@ResponseStatus(HttpStatus.CREATED)
	public Prestamo createPrestamo(@RequestBody Prestamo prestamo) {
		return prestamoService.create(prestamo);
	}
		
	@PutMapping("/{codPrestamo}")
    //@ResponseStatus(HttpStatus.NO_CONTENT)
	public void updatePrestamo(@PathVariable Integer codPrestamo, @RequestBody Prestamo prestamo) {
		prestamo.setCodPrestamo(codPrestamo);
		prestamoService.update(prestamo);
	}
	
	@GetMapping("/check/env")
	public String testEnv() {
		return fintechConfig.getPropiedadEjemplo();
	}

}
