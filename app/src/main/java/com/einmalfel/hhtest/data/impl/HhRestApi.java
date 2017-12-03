package com.einmalfel.hhtest.data.impl;

import com.einmalfel.hhtest.data.VacancyDataProvider;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.adapter.rxjava2.Result;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface HhRestApi {
  @GET("vacancies")
  Observable<Result<VacancyDataProvider.VacancyQueryResponse>> searchVacancies(
      @Query("text") CharSequence text, @Query("search_field") List<String> fields,
      @Query("experience") CharSequence experience, @Query("employment") CharSequence employment,
      @Query("schedule") CharSequence schedule, @Query("area") CharSequence area,
      @Query("metro") CharSequence metro, @Query("specialization") CharSequence specialization,
      @Query("industry") CharSequence industry, @Query("employer_id") Long employer_id,
      @Query("currency") CharSequence currency, @Query("salary") Long salary,
      @Query("label") CharSequence label, @Query("only_with_salary") Boolean withSalaryOnly,
      @Query("period") Long period, @Query("date_from") CharSequence date_from,
      @Query("date_to") CharSequence date_to, @Query("top_lat") Double topLat,
      @Query("bottom_lat") Double bottomLat, @Query("left_lng") Double leftLng,
      @Query("right_lng") Double rightLng, @Query("order_by") CharSequence order_by,
      @Query("sort_point_lat") Double sortPointLat, @Query("sort_point_lng") Double sortPointLng,
      @Query("clusters") Boolean cluster, @Query("describe_arguments") Boolean describeArguments,
      @Query("per_page") Long perPage, @Query("page") Long page,
      @Query("no_magic") Boolean noMagic, @Query("premium") Boolean premium);
}
