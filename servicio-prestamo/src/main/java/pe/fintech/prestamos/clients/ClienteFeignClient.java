package pe.fintech.prestamos.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import pe.fintech.prestamos.model.Cliente;

@FeignClient("serviciocliente")
public interface ClienteFeignClient {
    @RequestMapping(
            method= RequestMethod.GET,
            value="/v1/clientes/{codCliente}",
            consumes="application/json")
    Cliente getCliente(@PathVariable("codCliente") Integer codCliente);
}
