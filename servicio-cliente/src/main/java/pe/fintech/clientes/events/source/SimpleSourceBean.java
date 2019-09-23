package pe.fintech.clientes.events.source;

import pe.fintech.clientes.events.models.ClienteChangeModel;
import pe.fintech.clientes.utils.UserContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;


@Component
public class SimpleSourceBean {
    private Source source;

    private static final Logger logger = LoggerFactory.getLogger(SimpleSourceBean.class);

    @Autowired
    public SimpleSourceBean(Source source){
        this.source = source;
    }

    public void publishClienteChange(String action,Integer codCliente){
       logger.debug("Sending Kafka message {} for codCliente: {}", action, codCliente);
       ClienteChangeModel change =  new ClienteChangeModel(
    		   ClienteChangeModel.class.getTypeName(),
                action,
                codCliente,
                UserContext.getCorrelationId());

        source
        	.output()
        	.send(MessageBuilder.withPayload(change).build());
    }
}
