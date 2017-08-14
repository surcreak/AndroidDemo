package com.example.testretrofit;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.testretrofit.bean.TranslationGet;
import com.example.testretrofit.bean.TranslationPost;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by gaoliang on 2017/8/11.
 */

public class TestRetrofitFragment extends Fragment {

    private TextView showView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root;
        root = inflater.inflate(R.layout.fragment_testretrofit, null);
        setupView(root);
        //getRequest();
        postRequest();
        return root;
    }

    private void setupView(View v) {
        showView = (TextView) v.findViewById(R.id.show);
    }

    public void getRequest() {
        // Step1: add dependencies.
        // compile 'com.squareup.retrofit2:retrofit:2.0.2'

        // Step2: add permission.
        //<uses-permission android:name="android.permission.INTERNET"/>

        // Step3: create request network interface.{@link com.example.testretrofit.GetRequestInterface}

        // Step4: crete retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://fy.iciba.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        // Step5: cterte netional request instence.
        GetRequestInterface request = retrofit.create(GetRequestInterface.class);
        Call<TranslationGet> call = request.getCall();

        // Setp6: send netional request.
        call.enqueue(new Callback<TranslationGet>() {
            @Override
            public void onResponse(Call<TranslationGet> call, Response<TranslationGet> response) {
                showView.setText(response.body().toString());
            }

            @Override
            public void onFailure(Call<TranslationGet> call, Throwable t) {
                showView.setText(R.string.fail);
            }
        });
    }

    public void postRequest() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://fanyi.youdao.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        PostRequestInterface request = retrofit.create(PostRequestInterface.class);

        Call<TranslationPost> call = request.getCall("I love you");

        call.enqueue(new Callback<TranslationPost>() {
            @Override
            public void onResponse(Call<TranslationPost> call, Response<TranslationPost> response) {
                showView.setText(response.body().getTranslateResult().get(0).get(0).getTgt());
            }

            @Override
            public void onFailure(Call<TranslationPost> call, Throwable t) {
                showView.setText(R.string.fail);
                showView.append("\n"+t.getMessage());
            }
        });
    }
}
