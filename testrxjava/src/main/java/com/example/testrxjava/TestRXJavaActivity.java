package com.example.testrxjava;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;

/**
 * Created by gaoliang on 2017/9/4.
 */

public class TestRXJavaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Observable<Integer> observable = Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<Integer> e) throws Exception {
                e.onNext(1);
                e.onNext(2);
                e.onNext(3);
                e.onComplete();
            }
        });

        Observer<Integer> observer = new Observer<Integer>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
                Log.d("gaol", "onSubscribe");
            }

            @Override
            public void onNext(@NonNull Integer integer) {
                Log.d("gaol", "onNext integer="+integer);
            }

            @Override
            public void onError(@NonNull Throwable e) {
                Log.d("gaol", "onError");
            }

            @Override
            public void onComplete() {
                Log.d("gaol", "onComplete");
            }
        };
        observable.subscribe(observer);
    }
}
