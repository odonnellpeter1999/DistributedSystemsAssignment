package common;

import com.netflix.appinfo.InstanceInfo;

import java.util.List;

public class PostalServiceInstances {

    private final List<InstanceInfo> instances;

    public PostalServiceInstances(List<InstanceInfo> instances) {
        this.instances = instances;
    }

    public List<InstanceInfo> getInstances() {
        return instances;
    }

}
