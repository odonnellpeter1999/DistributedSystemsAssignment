package distributedimagination.quotation.controller;

import distributedimagination.quotation.entity.OrderQuery;
import distributedimagination.quotation.service.QuotationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.*;


@RestController
public class QuotationController {

    private final QuotationService quotationService;

    @Autowired
    public QuotationController(QuotationService quotationService) {
        this.quotationService = quotationService;
    }

    @RequestMapping(value = "/service-instances/quotations/list")
    public Map<String, String> returnMap() {
        return quotationService.getQuotes();
    }

    @RequestMapping(value = "/service-instances/quotations")
    public ArrayList<String> getQuotationsList() {
        return quotationService.getQuotationsList();
    }

    @RequestMapping(value = "/service-instances/quotations")
    public ArrayList<String> getQuotationsList(@Valid OrderQuery quote) {
        return quotationService.getQuotationsList(quote);
    }

    @PostMapping(value = "/request", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public OrderQuery getQuotation(@Valid @RequestBody OrderQuery quote) {
        getQuotationsList(quote);
        return quote;
    }

}
