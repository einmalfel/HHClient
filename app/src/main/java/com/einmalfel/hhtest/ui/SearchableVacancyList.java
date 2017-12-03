package com.einmalfel.hhtest.ui;

import android.support.annotation.NonNull;

import com.einmalfel.hhtest.PersistentState;
import com.einmalfel.hhtest.data.Vacancy;

import io.reactivex.Observable;

public interface SearchableVacancyList extends PersistentState {
  /**
   * always emits value on subscription
   */
  @NonNull
  Observable<CharSequence> getSearchTermValue();

  @NonNull
  Observable<Vacancy> getSelectEvents();

  void showError(@NonNull String errorMsg);

  void showData(@NonNull Vacancy[] data);

  void showData(@NonNull String data);


}
