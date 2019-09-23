package pe.fintech.prestamos.events;


import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

public interface CustomChannels {
    @Input("inboundClienteChanges")
    SubscribableChannel clientes();
}
