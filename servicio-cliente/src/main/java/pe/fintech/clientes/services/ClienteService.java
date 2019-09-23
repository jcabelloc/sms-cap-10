package pe.fintech.clientes.services;

import pe.fintech.clientes.model.Cliente;

public interface ClienteService {

	Cliente findById(Integer codCliente);

	Cliente create(Cliente cliente);

	void update(Cliente cliente);
}
