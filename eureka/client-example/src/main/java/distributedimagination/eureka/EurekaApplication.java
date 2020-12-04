package distributedimagination.eureka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.netflix.discovery.EurekaClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.beans.factory.annotation.Value;

@SpringBootApplication
@EnableEurekaClient
@RestController
public class EurekaApplication implements GreetingsController {
    @Autowired
    @Lazy
    private EurekaClient eurekaClient;

    @Value("${spring.application.name}")
    private String appName;

    @Value("${server.port}")
    private String portNumber;

	public static void main(String[] args) {
		SpringApplication.run(EurekaApplication.class, args);
    }
    
    @Override
    public String greeting() {
        System.out.println("Request received on port number " + portNumber);
        return String.format("Hello from %s with Port Number %s!", eurekaClient.getApplication(appName)
            .getName(), portNumber);
    }

}
