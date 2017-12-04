package com.einmalfel.hhtest.ui.impl;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ScrollView;
import android.widget.TextView;

import com.einmalfel.hhtest.R;
import com.einmalfel.hhtest.data.Vacancy;
import com.einmalfel.hhtest.ui.VacancyDetailsView;

import java.util.LinkedList;
import java.util.List;

public class VacancyDetailsViewImpl extends FrameLayout implements VacancyDetailsView {
  private TextView titleView;
  private TextView descriptionView;
  private TextView responsibilitiesView;
  private TextView responsibilitiesCaptionView;
  private TextView requirementsView;
  private TextView requirementsCaptionView;
  private TextView addressView;
  private TextView addressCaptionView;
  private TextView employerView;
  private ImageButton locationView;
  private View dataView;
  private View noDataView;
  private ScrollView scrollView;

  public VacancyDetailsViewImpl(Context context) {
    super(context);
    init();
  }

  public VacancyDetailsViewImpl(Context context, AttributeSet attrs) {
    super(context, attrs);
    init();
  }

  public VacancyDetailsViewImpl(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    init();
  }

  private void init() {
    inflate(getContext(), R.layout.vacancy_details, this);
    titleView = findViewById(R.id.title);
    descriptionView = findViewById(R.id.description);
    requirementsView = findViewById(R.id.requirements);
    requirementsCaptionView = findViewById(R.id.requirements_caption);
    responsibilitiesView = findViewById(R.id.responsibilities);
    responsibilitiesCaptionView = findViewById(R.id.responsibilities_caption);
    addressView = findViewById(R.id.address);
    addressCaptionView = findViewById(R.id.address_caption);
    locationView = findViewById(R.id.locate);
    employerView = findViewById(R.id.employer);
    dataView = findViewById(R.id.data_view);
    noDataView = findViewById(R.id.no_data_view);
    scrollView = findViewById(R.id.scroll);
  }

  @Override
  public void setData(@Nullable Vacancy vacancy) {
    dataView.setVisibility(vacancy == null ? GONE : VISIBLE);
    noDataView.setVisibility(vacancy == null ? VISIBLE : GONE);
    if (vacancy == null) {
      return;
    }
    scrollView.scrollTo(0, 0);

    titleView.setText(vacancy.name);
    employerView.setText(vacancy.employer.name);
    descriptionView.setVisibility(vacancy.description == null ? GONE : VISIBLE);
    descriptionView.setText(vacancy.description);

    setRequirements(vacancy.snippet != null ? vacancy.snippet.requirement : null);
    setResponsibilities(vacancy.snippet != null ? vacancy.snippet.responsibility : null);
    setAddress(vacancy.address);
    setLocation(vacancy.address, vacancy.employer.name);
  }

  private void setRequirements(@Nullable String requirements) {
    requirementsCaptionView.setVisibility(requirements == null ? GONE : VISIBLE);
    requirementsView.setVisibility(requirements == null ? GONE : VISIBLE);
    requirementsView.setText(requirements);
  }

  private void setAddress(@Nullable Vacancy.Address address) {
    String addressString = address == null ? null : makeAddressString(address);
    addressCaptionView.setVisibility(addressString == null ? GONE : VISIBLE);
    addressView.setVisibility(addressString == null ? GONE : VISIBLE);
    addressView.setText(addressString);
  }

  private void setLocation(@Nullable Vacancy.Address address, @NonNull String label) {
    Intent mapIntent = address == null ? null : getLocationIntent(address, label);
    locationView.setVisibility(mapIntent == null ? GONE : VISIBLE);
    locationView.setOnClickListener(
        mapIntent == null ? null : view -> getContext().startActivity(mapIntent));
  }

  @Nullable
  private Intent getLocationIntent(@NonNull Vacancy.Address address, @NonNull String label) {
    if (address.lat == null || address.lng == null) {
      return null;
    }
    Intent result = new Intent(
        Intent.ACTION_VIEW,
        Uri.parse("geo:0,0?q=" + address.lat + "," + address.lng + "(" + label + ")"));
    return result.resolveActivity(getContext().getPackageManager()) == null ? null : result;
  }

  @Nullable
  private String makeAddressString(@NonNull Vacancy.Address address) {
    List<String> components = new LinkedList<>();
    if (address.city != null) {
      components.add(address.city);
    }
    if (address.street != null) {
      components.add(address.street);
      if (address.building != null) {
        components.add(address.building);
      }
    }
    return components.isEmpty() ? null : TextUtils.join(", ", components);
  }

  private void setResponsibilities(@Nullable String responsibilities) {
    responsibilitiesCaptionView.setVisibility(responsibilities == null ? GONE : VISIBLE);
    responsibilitiesView.setVisibility(responsibilities == null ? GONE : VISIBLE);
    responsibilitiesView.setText(responsibilities);

  }
}
