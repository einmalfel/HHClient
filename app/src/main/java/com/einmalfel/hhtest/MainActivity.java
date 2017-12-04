package com.einmalfel.hhtest;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.einmalfel.hhtest.data.Vacancy;
import com.einmalfel.hhtest.data.VacancyDataProvider;
import com.einmalfel.hhtest.data.impl.HhRestClient;
import com.einmalfel.hhtest.data.impl.MemCachedVacancyDataProvider;
import com.einmalfel.hhtest.ui.SearchableVacancyList;

public class MainActivity extends AppCompatActivity {
  private static final int SEARCH_CACHE_SIZE = 10;
  private SearchableVacancyList searchableVacancyList;
  private VacancyListPresenter listPresenter;

  @Override
  public Object onRetainCustomNonConfigurationInstance() {
    return listPresenter;
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    listPresenter = (VacancyListPresenter) getLastCustomNonConfigurationInstance();
    if (listPresenter == null) {
      VacancyDataProvider dataProvider = new MemCachedVacancyDataProvider(
          new HhRestClient("https://api.hh.ru"), SEARCH_CACHE_SIZE);
      listPresenter = new VacancyListPresenter(dataProvider,
                                               getString(R.string.list_nothing_found));
    }

    setContentView(R.layout.activity_main);
    searchableVacancyList = findViewById(R.id.list);

    if (savedInstanceState != null) {
      searchableVacancyList.restore(savedInstanceState);
      listPresenter.restore(savedInstanceState);
    }
  }

  @Override
  protected void onStart() {
    super.onStart();
    listPresenter.start(
        searchableVacancyList,
        new VacancyListPresenter.Navigator() {
          @Override
          public void showVacancy(@NonNull Vacancy vacancy) {
            startActivity(VacancyDetailsActivity.getIntent(MainActivity.this, vacancy));
          }

          @Override
          public void updateVacancy(@Nullable Vacancy vacancy) {}
        });
  }

  @Override
  protected void onStop() {
    super.onStop();
    listPresenter.stop();
  }

  @Override
  protected void onSaveInstanceState(Bundle outState) {
    super.onSaveInstanceState(outState);
    searchableVacancyList.store(outState);
    listPresenter.store(outState);
  }
}
