package pe.fintech.prestamos.clients;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.cloud.client.ServiceInstance;


import pe.fintech.prestamos.model.Cliente;

@Component
public class ClienteDiscoveryClient {
    
	@Autowired
    private DiscoveryClient discoveryClient;

    public Cliente getCliente(Integer codCliente) {
        RestTemplate restTemplate = new RestTemplate();
        List<ServiceInstance> instances = discoveryClient.getInstances("serviciocliente");

        if (instances.size()==0) return null;
        
        String serviceUri = String.format("%s/v1/clientes/%s", instances.get(0).getUri().toString(), codCliente);
    
        ResponseEntity<Cliente> restExchange = 
                restTemplate.exchange(
                        serviceUri,
                        HttpMethod.GET,
                        null, Cliente.class, codCliente);

        return restExchange.getBody();
    }
}
