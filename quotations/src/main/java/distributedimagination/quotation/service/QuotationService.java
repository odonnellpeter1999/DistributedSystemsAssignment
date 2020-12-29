package distributedimagination.quotation.service;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Service
public class QuotationService {

    @GetMapping("http://discovery:8761/postal-services/urls")
    @ResponseBody
    public String getMap(HttpServletRequest httpServletRequest) {
        String res = null;
        Map<String, String[]> requestParameterMap = httpServletRequest.getParameterMap();
        for(String key : requestParameterMap.keySet()){
            res = ("Key : "+ key +", Value: "+ requestParameterMap.get(key)[0]);
        }
        return res;
    }
}