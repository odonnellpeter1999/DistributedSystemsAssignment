package distributedimagination.quotation;

import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.netflix.discovery.shared.Application;
import com.netflix.discovery.shared.Applications;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.ws.rs.GET;
import java.util.ArrayList;
import java.util.List;


@SpringBootApplication
@EnableEurekaClient
@RestController
public class QuotationService {

    public ArrayList<InstanceInfo> applicationsInstances = new ArrayList<>();
    @Qualifier("eurekaClient")
    @Autowired
    @Lazy
    private EurekaClient eurekaClient;

    @Value("${spring.application.name}")
    private String appName;

    @Value("${server.port}")
    private String portNumber;

    public static void main(String[] args) {
        SpringApplication.run(QuotationService.class, args);
    }


    public ArrayList<InstanceInfo> serviceInstancesList() {
        List<Application> applications = eurekaClient.getApplications().getRegisteredApplications();
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

    @RequestMapping("/service-instances/quotations")
    public ArrayList<String> getQuotationsList(@PathVariable String applicationName) {
        ArrayList<String> quotations = new ArrayList<String>();
        for (int i=0; i < applicationsInstances.size();i++){
           applicationName = applicationsInstances.get(i).getAppName();
           quotations.add("/{applicationName}/quote");
        }
        return quotations;
    }








}

