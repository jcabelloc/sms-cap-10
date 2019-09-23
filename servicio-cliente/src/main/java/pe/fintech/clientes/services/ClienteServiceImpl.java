package pe.fintech.clientes.services;

import java.util.Optional;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pe.fintech.clientes.events.source.SimpleSourceBean;
import pe.fintech.clientes.model.Cliente;
import pe.fintech.clientes.repository.ClienteRepository;

@Service
public class ClienteServiceImpl implements ClienteService {
	
	@Autowired
	ClienteRepository clienteRepository;
	
    @Autowired
    SimpleSourceBean simpleSourceBean;
	
	@Override
	@Transactional
	public Cliente findById(Integer codCliente) {
		Optional<Cliente> opt = clienteRepository.findById(codCliente);
		if (opt.isPresent()) {
			return opt.get();
		}else {
			throw new EntityNotFoundException("Codigo de Cliente no encontrado: " + codCliente);
		}
	}

	@Override
	@Transactional
	public Cliente create(Cliente cliente) {
		cliente = clienteRepository.save(cliente);
        simpleSourceBean.publishClienteChange("CREATE", cliente.getCodCliente());
		return cliente; 
		
	}

	@Override
	@Transactional
	public void update(Cliente cliente) {
		clienteRepository.save(cliente);
        simpleSourceBean.publishClienteChange("UPDATE", cliente.getCodCliente());
	}

}
