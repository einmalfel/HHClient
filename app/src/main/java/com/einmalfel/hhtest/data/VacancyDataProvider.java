package com.einmalfel.hhtest.data;


import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import io.reactivex.Observable;

public interface VacancyDataProvider {
  @NonNull
  Observable<VacancyQueryResult> searchVacancies(@NonNull CharSequence text);

  interface VacancyQueryResult {}

  class VacancyQueryError implements VacancyQueryResult {
    public final String errorMessage;

    public VacancyQueryError(String errorMessage) {
      this.errorMessage = errorMessage;
    }
  }

  class VacancyQueryResponse implements VacancyQueryResult {
    @Nullable
    public final Cluster[] clusters;
    public final Vacancy[] items;

    public VacancyQueryResponse(@Nullable Cluster[] clusters, Vacancy[] items) {
      this.clusters = clusters;
      this.items = items;
    }
  }
}
