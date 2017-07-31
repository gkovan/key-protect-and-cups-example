package hello;



import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import config.ApplicationConfig;


@SpringBootApplication
public class Application {

	private static final Logger log = LoggerFactory.getLogger(Application.class);

	public static void main(String args[]) {
		SpringApplication.run(Application.class);
	}
	
	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder builder) {
		return builder.build();
	}

	@Bean
	public CommandLineRunner run(RestTemplate restTemplate) throws Exception {
		return args -> {
			
			String secretKey = KeyProtectAdapter.getSecretKeyFromKeyProtectService();
		    log.info("Secret key is: " + secretKey);
		    
		    //  command to create a Bluemix Cloud Foundry CUPS service
		    
		    String bluemixCFCommandToCreateCUPS = String.format(ApplicationConfig.bluemixCFCupsCommand,ApplicationConfig.bluemixCupsServiceName, ApplicationConfig.cupsServiceVCAPvarName, secretKey);
		    log.info(bluemixCFCommandToCreateCUPS);
		    
		};
	}
}