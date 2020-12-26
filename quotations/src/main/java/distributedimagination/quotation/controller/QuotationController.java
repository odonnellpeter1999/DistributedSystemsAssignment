package distributedimagination.quotation.controller;

import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.DiscoveryClient;
import com.netflix.discovery.shared.Application;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class QuotationController {

    @Autowired
    private DiscoveryClient discoveryClient;

    public ArrayList<InstanceInfo> applicationsInstances = new ArrayList<>();

    @RequestMapping(value = "/service-instances/list", produces = MediaType.APPLICATION_JSON_VALUE)
    public ArrayList<InstanceInfo> getApplications() {

        List<Application> applications = discoveryClient.getApplications().getRegisteredApplications();
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