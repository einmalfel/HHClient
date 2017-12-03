package com.einmalfel.hhtest;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.UiThread;

public interface PersistentState {
  @UiThread
  void store(@NonNull Bundle bundle);

  @UiThread
  void restore(@NonNull Bundle bundle);
}
