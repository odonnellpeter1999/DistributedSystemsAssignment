package distributedimagination.delivery;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class QuotationService {

    public static void main(String[] args) {
        SpringApplication.run(DeliveryService.class, args);
    }
}

