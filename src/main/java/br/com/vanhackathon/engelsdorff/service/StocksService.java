package br.com.vanhackathon.engelsdorff.service;

import br.com.vanhackathon.engelsdorff.client.StocksClient;
import br.com.vanhackathon.engelsdorff.dto.Company;
import br.com.vanhackathon.engelsdorff.dto.Quote;
import org.springframework.stereotype.Service;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class StocksService {

    private StocksClient stocksClient;

    public StocksService () {
//        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
//        logging.setLevel(Level.BODY);
//        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
//        httpClient.addInterceptor(logging);

        Retrofit retro = new Retrofit.Builder()
                .baseUrl("https://api.iextrading.com/1.0/")
                .addConverterFactory(JacksonConverterFactory.create())
//                .client(httpClient.build())
                .build();
        stocksClient = retro.create(StocksClient.class);
    }

    /**
     * List all companies on the database
     *
     * @return List<Company>
     * @throws IOException
     */

    public List<Company> listCompanies() throws IOException {
        return stocksClient.listCompanies().execute().body();
    }

    /**
     * List the quotes of the company sent through parameter
     *
     * @param symbols company symbols comma separated (max 100 symbols)
     * @return Map<String(symbol)>,Quote>
     * @throws IOException
     */
    public Map<String, Quote> listCompaniesQuotes(List<String> symbols) throws IOException {
        Map<String, Quote> ret = new HashMap<>();

        Map<String, Map<String,Quote>> map = stocksClient.companyQuotes(String.join(",", symbols)).execute().body();

        map.keySet().forEach(key -> ret.put(key, map.get(key).get("quote")));

        return ret;
    }

    /**
     * List the top 10 share prices of the companyes on the database
     *
     * @return List<Company>
     * @throws IOException
     */
    public List<Company> listTop10CompanyQuotes() throws IOException {
        Map<String, Quote> quoteMap = new HashMap<>();
        List<Company> companies = this.listCompanies();//.stream().limit(500).collect(Collectors.toList());
        int pos = 0;
        int pace = 100;
        while (pos < companies.size()) {
            /**
             * I wanted to make this part of my method to run multiple threads but I confess
             * I was really tired already to keep going
             */

            int listRange = pos+pace;
            if (listRange > companies.size()) {
                listRange = companies.size();
            }
            List<String> symbolsList = companies.subList(pos, listRange).stream().map(company -> company.getSymbol()).collect(Collectors.toList());
            quoteMap.putAll(this.listCompaniesQuotes(symbolsList));
            pos = listRange;
        }

        companies.forEach(company -> company.setQuote(quoteMap.get(company.getSymbol())));

        List<Company> top10 = companies.stream().sorted((comp1, comp2) ->
                Float.compare(
                        comp2.getQuote().getLatestPrice(),
                        comp1.getQuote().getLatestPrice())).
                limit(10).collect(Collectors.toList());

        return top10;
    }
}