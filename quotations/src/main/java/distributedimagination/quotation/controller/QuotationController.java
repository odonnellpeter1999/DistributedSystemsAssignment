package distributedimagination.quotation.controller;

import distributedimagination.quotation.service.QuotationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServlet;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;

@SpringBootApplication
@RestController
public class QuotationController {

    private final QuotationService quotationService;

    @Autowired
    public QuotationController(QuotationService quotationService) {
        this.quotationService = quotationService;
    }

    @RequestMapping(value = "/service-instances/quotations")
    public Map<String, String> returnMap() {
        return quotationService.getQuotes();
    }

    @RequestMapping(value = "/service-instances/quotations")
    public ArrayList<String> getQuotationsList() {
        return quotationService.getQuotationsList();
    }
}
