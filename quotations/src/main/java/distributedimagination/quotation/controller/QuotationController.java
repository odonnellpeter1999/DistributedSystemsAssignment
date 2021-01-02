package distributedimagination.quotation.controller;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import distributedimagination.quotation.service.QuotationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;


@RestController
public class QuotationController {

    private final QuotationService quotationService;

    @Autowired
    public QuotationController(QuotationService quotationService) {
        this.quotationService = quotationService;
    }

    @RequestMapping(value = "/quotation-providers")
    public Map<String, String> returnMap() {
        return quotationService.getQuotes();
    }

    @PostMapping(value = "/request", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public String getQuotation(@RequestBody String order) {
        JsonObject jsonOrder = JsonParser.parseString(order).getAsJsonObject();
        return quotationService.GenerateQuote(jsonOrder);
    }

}
