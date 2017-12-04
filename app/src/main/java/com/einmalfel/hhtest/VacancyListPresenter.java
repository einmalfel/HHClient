package com.einmalfel.hhtest;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.UiThread;
import android.support.v4.util.Pair;

import com.einmalfel.hhtest.data.Vacancy;
import com.einmalfel.hhtest.data.VacancyDataProvider;
import com.einmalfel.hhtest.ui.SearchableVacancyList;
import com.google.common.base.Optional;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class VacancyListPresenter implements PersistentState {
  private static final String KEY_LAST_SHOWN = "vacancy list presenter last shown";
  @NonNull
  private final VacancyDataProvider dataProvider;
  @NonNull
  private final String nothingFoundString;
  private final CompositeDisposable disposables = new CompositeDisposable();
  private Vacancy lastSelectedVacancy;


  public VacancyListPresenter(@NonNull VacancyDataProvider dataProvider,
                              @NonNull String nothingFoundString) {
    this.dataProvider = dataProvider;
    this.nothingFoundString = nothingFoundString;
  }

  @UiThread
  public void start(@NonNull SearchableVacancyList view, @NonNull Navigator navigator) {
    disposables.add(
        Observable
            .combineLatest(
                view.getSearchTermValue() // fisrt observable updates list and passes last loaded data
                    .switchMap(term -> dataProvider.searchVacancies(term)
                                                   .subscribeOn(Schedulers.io()))
                    .map(this::resultToAction)
                    .observeOn(AndroidSchedulers.mainThread())
                    .doOnNext(action -> action.show(view))
                    .ofType(ListUpdate.class)
                    .map(update -> update.items),
                view.getSelectEvents() // second observable invokes navigator and passes last selected vacancy
                    .doOnNext(vacancy -> lastSelectedVacancy = vacancy)
                    .doOnNext(navigator::showVacancy)
                    .map(Optional::of)
                    .startWith(Optional.fromNullable(lastSelectedVacancy)),
                Pair::new)
            // code below resets detailed view contents if necessary when list got updated
            .map(pair -> getUpdateVacancyPair(pair.first, pair.second.orNull()))
            .filter(updateVacancyPair -> updateVacancyPair.first)
            .map(updateVacancyPair -> Optional.fromNullable(updateVacancyPair.second))
            .doOnNext(optional -> lastSelectedVacancy = optional.orNull())
            .startWith(Optional.fromNullable(lastSelectedVacancy))
            .subscribe(optional -> navigator.updateVacancy(optional.orNull()))
    );
  }

  @UiThread
  public void stop() {
    disposables.clear();
  }

  /**
   * Determines whether details need to be updated because of list changes (i.e. when list doesn't
   * contain previously selected item.
   *
   * @return Pair, first element is boolean meaning update is needed, second is new selected vacancy
   */
  private Pair<Boolean, Vacancy> getUpdateVacancyPair(
      @NonNull Vacancy[] items, @Nullable Vacancy selectedVacancy) {
    if (selectedVacancy != null) {
      for (Vacancy vacancy : items) {
        if (vacancy.id == selectedVacancy.id) {
          return new Pair<>(false, null);
        }
      }
    }
    return new Pair<>(true, items.length == 0 ? null : items[0]);
  }

  @NonNull
  private UiAction resultToAction(@NonNull VacancyDataProvider.VacancyQueryResult result) {
    if (result instanceof VacancyDataProvider.VacancyQueryError) {
      return new LoadError(((VacancyDataProvider.VacancyQueryError) result).errorMessage);
    } else {
      return new ListUpdate(((VacancyDataProvider.VacancyQueryResponse) result).items);
    }
  }

  public interface Navigator {
    @UiThread
    void showVacancy(@NonNull Vacancy vacancy);

    /**
     * only updates data if it has been already shown
     */
    @UiThread
    void updateVacancy(@Nullable Vacancy vacancy);
  }

  private interface UiAction {
    @UiThread
    void show(@NonNull SearchableVacancyList searchableList);
  }

  private class ListUpdate implements UiAction {
    private final Vacancy items[];

    private ListUpdate(@NonNull Vacancy[] items) {
      this.items = items;
    }

    @UiThread
    @Override
    public void show(@NonNull SearchableVacancyList searchableList) {
      if (items.length == 0) {
        searchableList.showData(nothingFoundString);
      } else {
        searchableList.showData(items);
      }
    }
  }

  private class LoadError implements UiAction {
    private final String errorMsg;

    private LoadError(@NonNull String errorMsg) {
      this.errorMsg = errorMsg;
    }

    @UiThread
    @Override
    public void show(@NonNull SearchableVacancyList searchableList) {
      searchableList.showError(errorMsg);
    }
  }

  @Override
  public void store(@NonNull Bundle bundle) {
    bundle.putSerializable(KEY_LAST_SHOWN, lastSelectedVacancy);
  }

  @Override
  public void restore(@NonNull Bundle bundle) {
    lastSelectedVacancy = (Vacancy) bundle.getSerializable(KEY_LAST_SHOWN);
  }
}
