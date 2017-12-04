package com.einmalfel.hhtest.data.impl;

import android.support.annotation.NonNull;
import android.support.v4.util.LruCache;

import com.einmalfel.hhtest.data.VacancyDataProvider;

import io.reactivex.Observable;

/**
 * In-memory LRU cache. It produces cached result first, then result obtained with decorated
 * provider
 */
public class MemCachedVacancyDataProvider implements VacancyDataProvider {
  @NonNull
  private final VacancyDataProvider decorated;
  @NonNull
  private final LruCache<CharSequence, VacancyQueryResponse> searchCache;

  public MemCachedVacancyDataProvider(@NonNull VacancyDataProvider decorated, int searchCacheSize) {
    this.decorated = decorated;
    searchCache = new LruCache<>(searchCacheSize);
  }

  @Override
  @NonNull
  public Observable<VacancyQueryResult> searchVacancies(@NonNull CharSequence text) {
    VacancyQueryResponse cached = searchCache.get(text);
    return Observable.concat(
        cached == null ? Observable.empty() : Observable.just(cached),
        decorated.searchVacancies(text)
                 .doOnNext(result -> {
                   if (result instanceof VacancyQueryResponse) {
                     searchCache.put(text, (VacancyQueryResponse) result);
                   }
                 }));
  }
}
