package br.com.vanhackathon.engelsdorff.client;

import br.com.vanhackathon.engelsdorff.dto.Company;
import br.com.vanhackathon.engelsdorff.dto.Quote;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

import java.util.List;
import java.util.Map;


public interface StocksClient {

    @GET("ref-data/symbols")
    Call<List<Company>> listCompanies();

    @GET("stock/market/batch?types=quote&range=1m&last=5")
    Call<Map<String, Map<String,Quote>>> companyQuotes(@Query("symbols") String symbols);
}
