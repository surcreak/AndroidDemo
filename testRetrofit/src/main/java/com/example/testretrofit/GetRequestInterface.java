package com.example.testretrofit;

import com.example.testretrofit.bean.TranslationGet;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by gaoliang on 2017/8/11.
 */

public interface GetRequestInterface {

    @GET("ajax.php?a=fy&f=auto&t=auto&w=hello%20world")
    Call<TranslationGet> getCall();
}
