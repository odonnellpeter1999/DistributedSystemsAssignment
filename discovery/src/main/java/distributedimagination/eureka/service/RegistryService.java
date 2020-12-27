package distributedimagination.eureka.service;

import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;
import com.netflix.discovery.shared.Application;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class RegistryService {

    @Autowired
    private EurekaClient eurekaClient;

    public List<String> getPostalServices() {
        Set<String> postalServices = new HashSet<>();
        List<Application> applications = eurekaClient.getApplications().getRegisteredApplications();
        for (Application application : applications) {
            for (InstanceInfo instance: application.getInstances()) {
                if (instance.getAppName().contains("POSTAL-SERVICE"))
                    postalServices.add(instance.getHomePageUrl());
            }

        }
        return new ArrayList<String>(postalServices);
    }

}
