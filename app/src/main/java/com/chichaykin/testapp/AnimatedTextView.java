package com.chichaykin.testapp;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.TransitionDrawable;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.text.Layout;
import android.util.AttributeSet;
import android.widget.TextView;
import java.util.concurrent.TimeUnit;
import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;

/**
 * a. Is square at all times.
 * b. Has a gradient background that changes from RED through BLUE to YELLOW.
 * c. Every 2 seconds the order of the colours is reversed and the reversal is
 * animated across 500ms.
 * d. Is usable across Android 4.1 and above
 */

public class AnimatedTextView extends TextView {

  private static final int TIME_ANIMATION = 500;
  private Subscription subscription;
  private TransitionDrawable transitionDrawable;

  public AnimatedTextView(Context context) {
    super(context);
    init();
  }

  public AnimatedTextView(Context context, @Nullable AttributeSet attrs) {
    super(context, attrs);
    init();
  }

  public AnimatedTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
  }

  @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
  public AnimatedTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
    super(context, attrs, defStyleAttr, defStyleRes);
    init();
  }

  @Override protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
    super.onMeasure(widthMeasureSpec, heightMeasureSpec);

    Layout layout = getLayout();
    if (layout.getHeight() != layout.getWidth()) {
      setHeight(layout.getWidth());
    }
  }

  @Override protected void onDetachedFromWindow() {
    super.onDetachedFromWindow();
    if (subscription != null) {
      subscription.unsubscribe();
      subscription = null;
    }
  }

  private void init() {
    if (!isInEditMode()) {
      int red = ContextCompat.getColor(getContext(), R.color.red);
      int blue = ContextCompat.getColor(getContext(), R.color.blue);
      int yellow = ContextCompat.getColor(getContext(), R.color.yellow);
      GradientDrawable drawable1 = new GradientDrawable(GradientDrawable.Orientation.TOP_BOTTOM,
        new int[] { red, blue, yellow });

      GradientDrawable drawable2 = new GradientDrawable(GradientDrawable.Orientation.TOP_BOTTOM,
        new int[] { yellow, blue, red });

      transitionDrawable = new TransitionDrawable(new GradientDrawable[] { drawable1, drawable2 });
      setBackground(transitionDrawable);

      subscription = Observable.interval(0, 2000, TimeUnit.MILLISECONDS)
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(this::runAnimation);
    }
  }

  private void runAnimation(long i) {
    if (i % 2 == 0) {
      transitionDrawable.startTransition(TIME_ANIMATION);
    } else {
      transitionDrawable.reverseTransition(TIME_ANIMATION);
    }
  }
}
