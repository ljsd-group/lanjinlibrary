package com.lanji.mylibrary.network;


import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Url;

public interface Api {

    @POST
    Call<AdversData> getCategories(@Url String url,     @Body Object o);

    @GET
    Call<AdversData> getCategories(@Url String url);
}
