package com.einmalfel.hhtest.data.impl;

import android.support.annotation.NonNull;

import com.einmalfel.hhtest.data.VacancyDataProvider;

import io.reactivex.Observable;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class HhRestClient implements VacancyDataProvider {
  private final HhRestApi restApi;

  public HhRestClient(@NonNull String baseUrl) {
    Retrofit retrofit = new Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build();
    restApi = retrofit.create(HhRestApi.class);
  }

  @NonNull
  @Override
  public Observable<VacancyQueryResult> searchVacancies(@NonNull CharSequence text) {
    return restApi
        .searchVacancies(text, null, null, null, null, null, null, null, null, null, null, null,
                         null, null, null, null, null, null, null, null, null, null, null, null,
                         null, null, null, null, null, null)
        .map(result -> result.isError() ?
            new VacancyQueryError("Network error: " + result.error().getLocalizedMessage()) :
            result.response().isSuccessful() ? result.response().body() :
                new VacancyQueryError("Bad server response: " + result.response().message()));
  }
}
