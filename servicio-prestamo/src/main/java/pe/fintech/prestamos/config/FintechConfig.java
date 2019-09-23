package pe.fintech.prestamos.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class FintechConfig {

	
	@Value("${propiedad.ejemplo}")
	private String propiedadEjemplo;
	
	@Value("${redis.server}")
	private String redisServer="";

	@Value("${redis.port}")
	private String redisPort="";
	
	public String getPropiedadEjemplo() {
		return propiedadEjemplo;
	}

	public String getRedisServer(){
	  return redisServer;
	}

	public Integer getRedisPort(){
	  return Integer.valueOf(redisPort);
	}
	  
	
}
