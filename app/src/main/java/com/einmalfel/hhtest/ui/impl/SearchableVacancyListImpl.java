package com.einmalfel.hhtest.ui.impl;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.einmalfel.hhtest.R;
import com.einmalfel.hhtest.data.Vacancy;
import com.einmalfel.hhtest.ui.SearchableVacancyList;

import io.reactivex.Observable;
import io.reactivex.subjects.BehaviorSubject;
import io.reactivex.subjects.Subject;

public class SearchableVacancyListImpl extends LinearLayoutCompat implements SearchableVacancyList {
  private static final String KEY_QUERY = "KEY_QUERY";
  private final ListAdapter adapter = new ListAdapter();
  private final Subject<CharSequence> searchQuery = BehaviorSubject.createDefault("");
  private SearchView searchView;

  public SearchableVacancyListImpl(Context context) {
    super(context);
    init();
  }

  public SearchableVacancyListImpl(Context context, AttributeSet attrs) {
    super(context, attrs);
    init();
  }

  public SearchableVacancyListImpl(Context context, AttributeSet attrs,
                                   int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    init();
  }

  @NonNull
  @Override
  public Observable<CharSequence> getSearchTermValue() {
    return searchQuery;
  }

  @NonNull
  @Override
  public Observable<Vacancy> getSelectEvents() {
    return adapter.tapEvents;
  }

  @Override
  public void showError(@NonNull String errorMsg) {
    Toast.makeText(getContext(), errorMsg, Toast.LENGTH_LONG).show();
  }

  @Override
  public void showData(@NonNull Vacancy[] data) {
    adapter.swapData(data);
  }

  @Override
  public void showData(@NonNull String data) {
    adapter.swapData(data);
  }

  private void init() {
    setOrientation(LinearLayout.VERTICAL);
    inflate(getContext(), R.layout.searchable_vacancy_list, this);
    searchView = findViewById(R.id.searchable_vacancy_list_search);
    searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
      @Override
      public boolean onQueryTextSubmit(String query) {
        searchQuery.onNext(query);
        return false; //allow search view to perform default action, i.e closing on-screen keyboard
      }

      @Override
      public boolean onQueryTextChange(String newText) {
        return false;
      }
    });
    RecyclerView recyclerView = findViewById(R.id.searchable_vacancy_list_list);
    recyclerView.setAdapter(adapter);
    recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
  }

  @Override
  public void store(@NonNull Bundle bundle) {
    bundle.putCharSequence(Integer.toString(getId()) + KEY_QUERY, searchView.getQuery());
  }

  @Override
  public void restore(@NonNull Bundle bundle) {
    CharSequence query = bundle.getCharSequence(Integer.toString(getId()) + KEY_QUERY);
    if (query != null) {
      searchView.setQuery(query, true);
    }
  }
}
