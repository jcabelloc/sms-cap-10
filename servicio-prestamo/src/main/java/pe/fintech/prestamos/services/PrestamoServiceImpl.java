package pe.fintech.prestamos.services;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pe.fintech.prestamos.clients.ClienteDiscoveryClient;
import pe.fintech.prestamos.clients.ClienteFeignClient;
import pe.fintech.prestamos.clients.ClienteRestClient;
import pe.fintech.prestamos.model.Cliente;
import pe.fintech.prestamos.model.Prestamo;
import pe.fintech.prestamos.repository.PrestamoRepository;
import pe.fintech.prestamos.utils.UserContextHolder;

@Service
public class PrestamoServiceImpl implements PrestamoService {
    private static final Logger logger = LoggerFactory.getLogger(PrestamoServiceImpl.class);

	
	@Autowired
	PrestamoRepository prestamoRepository;
	
	@Autowired
	ClienteDiscoveryClient clienteDiscoveryClient;
	
	@Autowired 
	ClienteRestClient clienteRestClient;
	
	@Autowired
	ClienteFeignClient clienteFeignClient;
	
	@Override
	@HystrixCommand(
			//fallbackMethod = "buildFallbackPrestamos",
			threadPoolKey = "findByCodClienteThreadPool",
            threadPoolProperties =
                    {@HystrixProperty(name = "coreSize",value="30"),
                     @HystrixProperty(name="maxQueueSize", value="10")},
			commandProperties={
				//@HystrixProperty(name="execution.isolation.thread.timeoutInMilliseconds", value="1000")
                    @HystrixProperty(name="circuitBreaker.requestVolumeThreshold", value="10"),
                    @HystrixProperty(name="circuitBreaker.errorThresholdPercentage", value="75"),
                    @HystrixProperty(name="circuitBreaker.sleepWindowInMilliseconds", value="7000"),
                    @HystrixProperty(name="metrics.rollingStats.timeInMilliseconds", value="15000"),
                    @HystrixProperty(name="metrics.rollingStats.numBuckets", value="5")})
	public List<Prestamo> findByCodCliente(Integer codCliente) {
        logger.info("PrestamoServiceImpl.findByCodCliente  Correlation id: {}", UserContextHolder.getContext().getCorrelationId());
	
		randomlyRunLong(); // Aleatoriamente genera una demora de 11 segundos
		return prestamoRepository.findByCodCliente(codCliente);
	}

	@Override
	public Prestamo findByCodClienteAndCodPrestamo(Integer codCliente, Integer codPrestamo) {
		Prestamo prestamo = prestamoRepository.findByCodClienteAndCodPrestamo(codCliente, codPrestamo);
		Cliente cliente = getCliente(codCliente);
		return prestamo
				.withNombreCliente(cliente.getNombre())
				.withEmailCliente(cliente.getEmail())
				.withTelefonoCliente(cliente.getTelefono());
				
	}
	
	@HystrixCommand
	private Cliente getCliente(Integer codCliente) {
		return clienteRestClient.getCliente(codCliente);
	}
	
    private List<Prestamo> buildFallbackPrestamos(Integer codCliente){
        List<Prestamo> fallbackPrestamos = new ArrayList<>();
        Prestamo prestamo = new Prestamo()
        		.withCodPrestamo(0)
                .withCodCliente(0)
                .withEstado(Prestamo.Estado.NO_DISPONIBLE)
                .withMonto(BigDecimal.ZERO);

        fallbackPrestamos.add(prestamo);
        return fallbackPrestamos;
    }
    

	@Override
	@Transactional
	public Prestamo create(Prestamo prestamo) {
		return prestamoRepository.save(prestamo);
	}

	@Override
	@Transactional
	public void update(Prestamo prestamo) {
		prestamoRepository.save(prestamo);
	}

	@Override
	public Prestamo findByCodClienteAndCodPrestamoAndTipoLlamada(Integer codCliente, Integer codPrestamo,
			String tipoLlamada) {
		Prestamo prestamo = prestamoRepository.findByCodClienteAndCodPrestamo(codCliente, codPrestamo);
		Cliente cliente = retrieveClienteInfo(codCliente, tipoLlamada);
		
		return prestamo
				.withNombreCliente(cliente.getNombre())
				.withEmailCliente(cliente.getEmail())
				.withTelefonoCliente(cliente.getTelefono());
	}
	
    private Cliente retrieveClienteInfo(Integer codCliente, String tipoLlamada){
        Cliente cliente= null;

        switch (tipoLlamada) {
            case "feign":
                System.out.println("Usando el feign client");
                cliente = clienteFeignClient.getCliente(codCliente);
                break;
            case "rest":
                System.out.println("Usando el rest client");
                cliente = clienteRestClient.getCliente(codCliente);
                break;
            case "discovery":
                System.out.println("Usando el discovery client");
                cliente = clienteDiscoveryClient.getCliente(codCliente);
                break;
            default:
            	cliente = clienteRestClient.getCliente(codCliente);
        }
        return cliente;
    }
    
    
    private void randomlyRunLong(){
        Random rand = new Random();

        int randomNum = rand.nextInt((3 - 1) + 1) + 1;

        if (randomNum==3) sleep();
      }

      private void sleep(){
          try {
              Thread.sleep(11000);
          } catch (InterruptedException e) {
              e.printStackTrace();
          }
      }

}
