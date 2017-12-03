package com.einmalfel.hhtest.ui.impl;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.einmalfel.hhtest.R;
import com.einmalfel.hhtest.data.Vacancy;

import io.reactivex.subjects.PublishSubject;
import io.reactivex.subjects.Subject;

class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder> {
  private static final long SINGLE_ID = Long.MIN_VALUE;
  public final Subject<Vacancy> tapEvents = PublishSubject.create();
  private String singleStringData;
  private Vacancy[] arrayData;

  public ListAdapter() {
    setHasStableIds(true);
  }

  public void swapData(@NonNull String newData) {
    arrayData = null;
    singleStringData = newData;
    notifyDataSetChanged();
  }

  public void swapData(@NonNull Vacancy[] newData) {
    //todo diffutil
    singleStringData = null;
    arrayData = newData;
    notifyDataSetChanged();
  }

  @Override
  public long getItemId(int position) {
    return singleStringData == null ? arrayData[position].id : SINGLE_ID;
  }

  @Override
  public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(
        R.layout.searchable_vacancy_list_item, parent, false));
  }

  @Override
  public void onBindViewHolder(ViewHolder holder, int position) {
    if (singleStringData != null) {
      holder.bind(singleStringData);
    } else if (arrayData != null) {
      holder.bind(arrayData[position]);
    } else {
      throw new AssertionError();
    }
  }

  @Override
  public int getItemCount() {
    return singleStringData != null ? 1 : arrayData == null ? 0 : arrayData.length;
  }

  class ViewHolder extends RecyclerView.ViewHolder {
    private final TextView mTitle;

    private ViewHolder(View itemView) {
      super(itemView);
      mTitle = (TextView) itemView;
    }

    private void bind(@NonNull String title) {
      mTitle.setText(title);
      itemView.setOnClickListener(null);
    }

    private void bind(@NonNull Vacancy item) {
      mTitle.setText(item.name);
      itemView.setOnClickListener(v -> tapEvents.onNext(item));
    }
  }
}
