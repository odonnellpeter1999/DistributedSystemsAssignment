package distributedimagination.quotation.service;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Service
public class QuotationService {

    @GetMapping("http://discovery:8761/postal-services/urls")
    @ResponseBody
    public Map<String, String> getMap(@RequestParam Map<String, String> allParams) {
        return allParams;
    }
}