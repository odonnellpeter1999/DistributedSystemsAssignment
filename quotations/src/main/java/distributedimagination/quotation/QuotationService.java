package distributedimagination.quotation;

import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.netflix.discovery.shared.Application;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;


@SpringBootApplication
@RestController
@EnableEurekaClient

public class QuotationService {

    @Autowired
    @Qualifier("eurekaClient")
    private EurekaClient client;

    public ArrayList<InstanceInfo> applicationsInstances = new ArrayList<>();
//    @Qualifier("eurekaClient")
//    @Autowired
//    @Lazy
//    private EurekaClient eurekaClient;
//    @Value("${spring.application.name}")
//    private String appName;
//    @Value("${server.port}")
//    private String portNumber;

    public static void main(String[] args) {
        SpringApplication.run(QuotationService.class, args);
    }

    @RequestMapping (value = "/service-instances/list", produces = MediaType.APPLICATION_JSON_VALUE)
    public ArrayList<InstanceInfo> getApplications() {

        List<Application> applications = client.getApplications().getRegisteredApplications();
        for (Application application : applications) {
            applicationsInstances = (ArrayList<InstanceInfo>) application.getInstances();
            for (InstanceInfo applicationsInstance : applicationsInstances) {
                String name = applicationsInstance.getAppName();
                String url = applicationsInstance.getHomePageUrl();
                System.out.println(name + ": " + url);
            }
        }
        return applicationsInstances;

    }
    

//    @RequestMapping("/service-instances/quotations")
//    public ArrayList<String> getQuotationsList(@PathVariable String applicationName) {
//        ArrayList<String> quotations = new ArrayList<String>();
//        for (int i = 0; i < applicationsInstances.size(); i++) {
//            applicationName = applicationsInstances.get(i).getAppName();
//            quotations.add("/{applicationName}/quote");
//        }
//        return quotations;
//    }

}

