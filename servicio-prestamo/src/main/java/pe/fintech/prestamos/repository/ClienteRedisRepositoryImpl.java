package pe.fintech.prestamos.repository;

import pe.fintech.prestamos.model.Cliente;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;

@Repository
public class ClienteRedisRepositoryImpl implements ClienteRedisRepository {
    private static final String HASH_NAME ="cliente";

    private RedisTemplate<Integer, Cliente> redisTemplate;
    private HashOperations hashOperations;

    public ClienteRedisRepositoryImpl(){
        super();
    }

    @Autowired
    private ClienteRedisRepositoryImpl(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @PostConstruct
    private void init() {
        hashOperations = redisTemplate.opsForHash();
    }

    @Override
    public void saveCliente(Cliente cliente) {
        hashOperations.put(HASH_NAME, cliente.getCodCliente(), cliente);
    }

    @Override
    public void updateCliente(Cliente cliente) {
        hashOperations.put(HASH_NAME, cliente.getCodCliente(), cliente);
    }

    @Override
    public void deleteCliente(Integer codCliente) {
        hashOperations.delete(HASH_NAME, codCliente);
    }

    @Override
    public Cliente findCliente(Integer codCliente) {
       return (Cliente) hashOperations.get(HASH_NAME, codCliente);
    }
}
