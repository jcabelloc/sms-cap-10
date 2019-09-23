package pe.fintech.zuulserver;

import java.util.Collections;
import java.util.List;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.web.client.RestTemplate;

import pe.fintech.zuulserver.utils.UserContextInterceptor;

@SpringBootApplication
@EnableZuulProxy
public class ZuulserverApplication {
	
	
    @LoadBalanced
    @Bean
    public RestTemplate getRestTemplate(){
        RestTemplate template = new RestTemplate();
        return template;
    }
    
	public static void main(String[] args) {
		SpringApplication.run(ZuulserverApplication.class, args);
	}

}
