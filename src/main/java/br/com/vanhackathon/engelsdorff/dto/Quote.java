package br.com.vanhackathon.engelsdorff.dto;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;


@JsonIgnoreProperties(ignoreUnknown=true)
public class Quote {

    @JsonProperty("symbol")
    private String symbol;
    @JsonProperty("high")
    private Float high;
    @JsonProperty("low")
    private Float low;
    @JsonProperty("latestPrice")
    private Float latestPrice;

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public Float getHigh() {
        return high;
    }

    public void setHigh(Float high) {
        this.high = high;
    }

    public Float getLow() {
        return low;
    }

    public void setLow(Float low) {
        this.low = low;
    }

    public Float getLatestPrice() {
        return latestPrice;
    }

    public void setLatestPrice(Float latestPrice) {
        if (latestPrice == null) {
            this.latestPrice = 0F;
        } else {
            this.latestPrice = latestPrice;
        }
    }
}
