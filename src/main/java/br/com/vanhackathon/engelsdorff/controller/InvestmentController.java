package br.com.vanhackathon.engelsdorff.controller;

import br.com.vanhackathon.engelsdorff.converter.JsonConverter;
import br.com.vanhackathon.engelsdorff.service.StocksService;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Ideally we should implement some sort of cache since we will search through so many records, and also we also shouldn't use
 * a service that can only handle 100 item per request, even so I think this is a good change to evaluate the code that needs
 * to make 87 http calls then sort the information retrieving the top 10.
 *
 *
 */

@Scope(value = "request")
@RestController
@RequestMapping("/quotes")
public class InvestmentController {

    private StocksService service;

    public InvestmentController(StocksService service) {
        this.service = service;
    }

    @RequestMapping(value="/top10", method = RequestMethod.GET)
    public String getTop10CompanyQuotes() {
        try {
            return JsonConverter.objToJson(service.listTop10CompanyQuotes());
        } catch (Exception exp) {
            return "{errorMsg:'Couldn't get top 10 quotes please try again later'}";
        }
    }
}
