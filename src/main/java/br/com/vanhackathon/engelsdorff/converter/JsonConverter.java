package br.com.vanhackathon.engelsdorff.converter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonConverter {


    static {
        mapper = new ObjectMapper();
    }

    private final static ObjectMapper mapper;

    private JsonConverter(){};


    public static <T> String objToJson(T obj) {
        try {
            return mapper.writeValueAsString(obj);
        } catch (JsonProcessingException jpe) {
            jpe.printStackTrace();
        }
        return null;
    }
}
