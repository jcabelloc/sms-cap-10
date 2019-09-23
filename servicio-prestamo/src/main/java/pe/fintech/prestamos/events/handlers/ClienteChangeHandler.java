package pe.fintech.prestamos.events.handlers;

import pe.fintech.prestamos.events.CustomChannels;
import pe.fintech.prestamos.events.models.ClienteChangeModel;
import pe.fintech.prestamos.repository.ClienteRedisRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;


@EnableBinding(CustomChannels.class)
public class ClienteChangeHandler {

    @Autowired
    private ClienteRedisRepository clienteRedisRepository;

    private static final Logger logger = LoggerFactory.getLogger(ClienteChangeHandler.class);

    @StreamListener("inboundClienteChanges")
    public void loggerSink(ClienteChangeModel clienteChange) {
        logger.debug("Received a message of type " + clienteChange.getType());
        switch(clienteChange.getAction()){
            case "GET":
                logger.debug("Received a GET event from the servicio cliente  for codigo de cliente {}", clienteChange.getCodCliente());
                break;
            case "SAVE":
                logger.debug("Received a SAVE event from the servicio cliente for codigo de cliente {}", clienteChange.getCodCliente());
                break;
            case "UPDATE":
                logger.debug("Received a UPDATE event from the servicio cliente  for codigo de cliente{}", clienteChange.getCodCliente());
                clienteRedisRepository.deleteCliente(clienteChange.getCodCliente());
                break;
            case "DELETE":
                logger.debug("Received a DELETE event from the servicio cliente for codigo de cliente {}", clienteChange.getCodCliente());
                clienteRedisRepository.deleteCliente(clienteChange.getCodCliente());
                break;
            default:
                logger.error("Received an UNKNOWN event from the servicio cliente  of type {}", clienteChange.getType());
                break;

        }
    }

}
