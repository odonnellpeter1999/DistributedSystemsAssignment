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

    public List<InstanceInfo> getPostalServices() {
        Set<InstanceInfo> postalServices = new HashSet<>();
        List<Application> applications = eurekaClient.getApplications().getRegisteredApplications();
        for (Application application : applications) {
            for (InstanceInfo instance: application.getInstances()) {
                if (instance.getAppName().contains("POSTALSERVICE"))
                    postalServices.add(instance);
            }

        }
        return new ArrayList<InstanceInfo>(postalServices);
    }

    public Map<String, String> getPostalURLs() {
        Map<String, String> postalServices = new HashMap<>();
        List<Application> applications = eurekaClient.getApplications().getRegisteredApplications();
        for (Application application : applications) {
            for (InstanceInfo instance: application.getInstances()) {
                if (instance.getAppName().contains("POSTALSERVICE"))
                    postalServices.put(instance.getMetadata().get("service-name"), instance.getHomePageUrl());
            }
        }
        return postalServices;
    }

}
