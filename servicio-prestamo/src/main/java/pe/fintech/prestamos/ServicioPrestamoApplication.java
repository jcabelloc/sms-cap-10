package pe.fintech.prestamos;

import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.web.client.RestTemplate;

import pe.fintech.prestamos.config.FintechConfig;
import pe.fintech.prestamos.events.models.ClienteChangeModel;
import pe.fintech.prestamos.utils.UserContextInterceptor;

@SpringBootApplication
//@EnableDiscoveryClient
@EnableFeignClients
@EnableEurekaClient
@EnableCircuitBreaker
@EnableResourceServer
//@EnableBinding(Sink.class)
public class ServicioPrestamoApplication {
	
	
	@Autowired
    private FintechConfig fintechConfig;
	
    private static final Logger logger = LoggerFactory.getLogger(ServicioPrestamoApplication.class);
    
    @Bean
    public JedisConnectionFactory jedisConnectionFactory() {
        RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration(
        		fintechConfig.getRedisServer(), fintechConfig.getRedisPort());
        return new JedisConnectionFactory(redisStandaloneConfiguration);
    }

    @Bean
    public RedisTemplate<Integer, Object> redisTemplate() {
        RedisTemplate<Integer, Object> template = new RedisTemplate<Integer, Object>();
        template.setConnectionFactory(jedisConnectionFactory());
        return template;
    }
    
	/*
	@StreamListener(Sink.INPUT)
	public void loggerSink(ClienteChangeModel clienteChange) {
		logger.debug("Received an event for codCliente {}", clienteChange.getCodCliente());
    }
	*/
	
	@LoadBalanced
    @Bean
    public OAuth2RestTemplate oauth2RestTemplate(OAuth2ClientContext oauth2ClientContext,
                                                 OAuth2ProtectedResourceDetails details) {
    	
        return new OAuth2RestTemplate(details, oauth2ClientContext);
    }
    
    @LoadBalanced
	@Bean
	public RestTemplate getRestTemplate(){
		
		RestTemplate template = new RestTemplate();
		List<ClientHttpRequestInterceptor> interceptors = template.getInterceptors();
        if (interceptors==null){
            template.setInterceptors(Collections.singletonList(new UserContextInterceptor()));
        }
        else{
            interceptors.add(new UserContextInterceptor());
            template.setInterceptors(interceptors);
        }

        return template;
	}
	  
	public static void main(String[] args) {
		SpringApplication.run(ServicioPrestamoApplication.class, args);
	}

}
