package pe.fintech.prestamos.clients;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import pe.fintech.prestamos.model.Cliente;
import pe.fintech.prestamos.repository.ClienteRedisRepository;
import pe.fintech.prestamos.utils.UserContextHolder;

@Component
public class ClienteRestClient {
	/*
    @Autowired
    RestTemplate restTemplate;
    */
    @Autowired
    OAuth2RestTemplate restTemplate;
    
    @Autowired
    ClienteRedisRepository clienteRedisRepo;
    
    private static final Logger logger = LoggerFactory.getLogger(ClienteRestClient.class);
    
    private Cliente checkRedisCache(Integer codCliente) {
        try {
            return clienteRedisRepo.findCliente(codCliente);
        }
        catch (Exception ex){
            logger.error("Error encountered while trying to retrieve organization {} check Redis Cache.  Exception {}", codCliente, ex);
            return null;
        }
    }
    
    private void cacheClienteObject(Cliente cliente) {
        try {
        	clienteRedisRepo.saveCliente(cliente);
        }catch (Exception ex){
            logger.error("Unable to cache cliente {} in Redis. Exception {}", cliente.getCodCliente(), ex);
        }
    }
    
    public Cliente getCliente(Integer codCliente){
        logger.debug("In Servicio Prestamo .getCliente: {}",  UserContextHolder.getContext().getCorrelationId());
        
        Cliente cliente = checkRedisCache(codCliente);
        if (cliente!=null){
            logger.debug("I have successfully retrieved one cliente {} from the redis cache: {}", codCliente, cliente);
            return cliente;
        }
        
        logger.debug("Unable to locate cliente from the redis cache: {}.", codCliente);

        ResponseEntity<Cliente> restExchange =
                restTemplate.exchange(
                		"http://zuulservice/api/cliente/v1/clientes/{codCliente}" ,
                        HttpMethod.GET,
                        null, Cliente.class, codCliente);
        /*Save the record from cache*/
        cliente = restExchange.getBody();
        if (cliente!=null) {
        	cacheClienteObject(cliente);
        }
        return cliente;
    }
    
	/*
    @Autowired
    OAuth2RestTemplate restTemplate;

    private static final Logger logger = LoggerFactory.getLogger(ClienteRestClient.class);

    public Cliente getCliente(Integer codCliente){
        logger.debug("En prestamo service getCliente con ID correlativo: {}", UserContextHolder.getContext().getCorrelationId());

        ResponseEntity<Cliente> restExchange =
                restTemplate.exchange(
                        "http://zuulservice/api/cliente/v1/clientes/{codCliente}",
                        HttpMethod.GET,
                        null, Cliente.class, codCliente);

        return restExchange.getBody();
    }
    */
}
