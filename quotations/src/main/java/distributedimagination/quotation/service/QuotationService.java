package distributedimagination.quotation.service;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

@Service
public class QuotationService {

    @RequestMapping(value = "http://discovery:8761/postal-services/urls", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, String> getMap(@RequestParam Map<String, String> allParams) {
        return allParams;
    }
}