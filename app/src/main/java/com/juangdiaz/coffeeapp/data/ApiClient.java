package com.juangdiaz.coffeeapp.data;


import com.juangdiaz.coffeeapp.model.Coffee;

import java.util.List;

import retrofit.RestAdapter;
import retrofit.converter.JacksonConverter;
import retrofit.http.GET;
import retrofit.http.Headers;
import rx.Observable;

/**
 * Created by juangdiaz on 3/28/15.
 */
public class ApiClient {

    private static CoffeeApiInterface sCoffeeService;


    public static CoffeeApiInterface getCoffeeApiClient() {

        //Jackson Converter
        JacksonConverter jacksonConverter = new JacksonConverter();

        RestAdapter restAdapter = new RestAdapter.Builder()
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setConverter(jacksonConverter)
                .setEndpoint("https://coffeeapi.percolate.com/api/coffee")
                .build();

        sCoffeeService = restAdapter.create(CoffeeApiInterface.class);
        return sCoffeeService;
    }
    public interface CoffeeApiInterface{

        @Headers("Authorization: WuVbkuUsCXHPx3hsQzus4SE")
        @GET("/")
        Observable<List<Coffee>> listCoffee();
    }


}
