package pe.fintech.prestamos.repository;

import pe.fintech.prestamos.model.Cliente;

public interface ClienteRedisRepository {
    void saveCliente(Cliente cliente);
    void updateCliente(Cliente cliente);
    void deleteCliente(Integer codCliente);
    Cliente findCliente(Integer codCliente);
}
