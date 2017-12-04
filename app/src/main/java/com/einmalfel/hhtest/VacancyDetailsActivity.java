package com.einmalfel.hhtest;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;

import com.einmalfel.hhtest.data.Vacancy;
import com.einmalfel.hhtest.ui.VacancyDetailsView;

public class VacancyDetailsActivity extends AppCompatActivity {
  private static final String KEY_VACANCY = "vacancy";

  public static Intent getIntent(@NonNull Context context, @NonNull Vacancy vacancy) {
    Intent result = new Intent(context, VacancyDetailsActivity.class);
    result.putExtra(KEY_VACANCY, vacancy);
    return result;
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_vacancy_details);
    VacancyDetailsView vacancyDetailsView = findViewById(R.id.details);
    vacancyDetailsView.setData((Vacancy) getIntent().getSerializableExtra(KEY_VACANCY));
  }
}
